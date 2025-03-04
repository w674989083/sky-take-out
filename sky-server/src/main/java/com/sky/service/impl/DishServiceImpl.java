package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.BaseException;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.IDishFlavorService;
import com.sky.service.IDishService;
import com.sky.utils.BeanUtils;
import com.sky.utils.CollUtils;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜品 服务实现类
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
@Service
@RequiredArgsConstructor
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {

    private final IDishFlavorService dishFlavorService;

    private final DishMapper dishMapper;

    private final SetmealDishServiceImpl setmealDishService;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void addDish(DishDTO dishDTO) {
        // 校验菜品名称
        Dish checkDish = getOne(new LambdaQueryWrapper<Dish>().eq(Dish::getName, dishDTO.getName()));
        if (checkDish != null) {
            throw new BaseException("菜品名称已存在");
        }
        // 保存菜品
        Dish dish = BeanUtils.copyBean(dishDTO, Dish.class);
        dish.setStatus(StatusConstant.DISABLE); // 新增时默认停售状态
        save(dish);
        // 保存菜品口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.forEach(s -> s.setDishId(dish.getId()));
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public PageResult<DishVO> getPage(DishPageQueryDTO pageQueryDTO) {
        Page<DishVO> mpPage = pageQueryDTO.toMpPage();
        // 可以将wrapper对象传到mapper中，多表联查的时候注意字段指代不明导致异常
       /* LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<Dish>()
                .like(StrUtil.isNotBlank(pageQueryDTO.getName()), Dish::getName, pageQueryDTO.getName())
                .eq(pageQueryDTO.getCategoryId() != null, Dish::getCategoryId, pageQueryDTO.getCategoryId())
                .eq(pageQueryDTO.getStatus() != null, Dish::getStatus, pageQueryDTO.getStatus())
                .orderByDesc(Dish::getUpdateTime);*/
        Page<DishVO> page = dishMapper.getPage(mpPage, pageQueryDTO);
        return PageResult.of(page);
    }

    @Override
    public List<DishVO> getListByCategoryId(Long categoryId) {
        List<Dish> list = lambdaQuery().eq(Dish::getCategoryId, categoryId).list();
        return BeanUtils.copyList(list, DishVO.class);
    }

    @Override
    public DishVO getDishById(Long id) {
        return dishMapper.getDishById(id);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        lambdaUpdate().set(Dish::getStatus, status)
                .eq(Dish::getId, id)
                .update();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateDish(DishDTO dishDTO) {
        // 校验菜品名称
        List<Dish> list = lambdaQuery().eq(Dish::getName, dishDTO.getName()).ne(Dish::getId, dishDTO.getId()).list();
        if (CollUtils.isNotEmpty(list)) {
            throw new BaseException("菜品名称已存在");
        }
        // 修改菜品表
        Dish dish = BeanUtils.copyBean(dishDTO, Dish.class);
        updateById(dish);
        // 修改菜品口味表
        // 删除当前菜品口味数据
        dishFlavorService.remove(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, dishDTO.getId()));
        // 插入新的菜品口味数据
        if (dishDTO.getFlavors() != null){
            List<DishFlavor> flavors = dishDTO.getFlavors();
            flavors.forEach(s -> s.setDishId(dish.getId()));
            dishFlavorService.saveBatch(flavors);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void deleteDish(List<Long> ids) {
        // 在售状态下，不可删除菜品
        Long count = lambdaQuery().in(Dish::getId, ids).eq(Dish::getStatus, StatusConstant.ENABLE).count();
        if (count > 0){
            throw new BaseException("存在在售菜品，无法删除");
        }
        // 被套餐关联的菜品不能删除
        Long SetmealDishs = setmealDishService.lambdaQuery().in(SetmealDish::getDishId, ids).count();
        if (SetmealDishs > 0){
            throw new BaseException("存在被套餐关联的菜品，无法删除");
        }
        // 删除菜品表和菜品口味表
        lambdaUpdate().in(Dish::getId, ids).remove();
        removeBatchByIds(ids);
    }

}
