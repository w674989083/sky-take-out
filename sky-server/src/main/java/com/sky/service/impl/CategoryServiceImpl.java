package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.mapper.CategoryMapper;
import com.sky.service.ICategoryService;
import com.sky.service.IDishService;
import com.sky.service.ISetmealService;
import com.sky.utils.BeanUtils;
import com.sky.utils.CollUtils;
import com.sky.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    private final CategoryMapper categoryMapper;

    private final IDishService dishService;

    private final ISetmealService setmealService;

    @Override
    public void saveCategory(CategoryDTO categoryDTO) {
        // 校验分类名称
        Category one = getOne(new LambdaQueryWrapper<Category>().eq(Category::getName, categoryDTO.getName()));
        if (one != null){
            throw new RuntimeException("分类名称已存在");
        }
        Category category = BeanUtils.copyBean(categoryDTO, Category.class);
        category.setStatus(StatusConstant.ENABLE)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setCreateUser(BaseContext.getCurrentId())
                .setUpdateUser(BaseContext.getCurrentId());
        save(category);
    }

    @Override
    public Page<CategoryVO> getPageList(CategoryPageQueryDTO categoryPageQueryDTO) {
        Page<CategoryVO> page = categoryPageQueryDTO.toMpPage();
        return categoryMapper.getPageList(page,categoryPageQueryDTO);
    }

    @Override
    public void updById(CategoryDTO categoryDTO) {
        // 校验分类名称
        Category category = getOne(new LambdaQueryWrapper<Category>().eq(Category::getName, categoryDTO.getName()).ne(Category::getId, categoryDTO.getId()));
        if (category != null){
            throw new RuntimeException("分类名称已存在");
        }
        Category category1 = BeanUtils.copyBean(categoryDTO, Category.class);
        category1.setUpdateTime(LocalDateTime.now())
                .setUpdateUser(BaseContext.getCurrentId());
        updateById(category1);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        update(new LambdaUpdateWrapper<Category>()
                .set(Category::getStatus, status)
                .set(Category::getUpdateTime, LocalDateTime.now())
                .set(Category::getUpdateUser, BaseContext.getCurrentId())
                .eq(Category::getId, id));
    }

    @Override
    public void checkDelete(List<Long> ids) {
        // 检查分类下是否有产品
        // 套餐表关联
        List<Setmeal> setmealList = setmealService.list(new LambdaQueryWrapper<Setmeal>().in(Setmeal::getCategoryId, ids));
        if (CollUtils.isNotEmpty(setmealList)){
            throw new RuntimeException("分类下有套餐，不能删除！");
        }
        //菜品表关联
        List<Dish> dishList = dishService.list(new LambdaQueryWrapper<Dish>().in(Dish::getCategoryId, ids));
        if (CollUtils.isNotEmpty(dishList)){
            throw new RuntimeException("分类下有菜品，不能删除！");
        }
        removeBatchByIds(ids);
    }

}
