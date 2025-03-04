package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * <p>
 * 菜品 服务类
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
public interface IDishService extends IService<Dish> {

    /**
     * 新增菜品
     * @param dishDTO
     */
    void addDish(DishDTO dishDTO);

    /**
     * 分页查询菜品
     * @param pageQueryDTO
     * @return
     */
    PageResult<DishVO> getPage(DishPageQueryDTO pageQueryDTO);

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    List<DishVO> getListByCategoryId(Long categoryId);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    DishVO getDishById(Long id);

    /**
     * 菜品起售停售
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 修改菜品
     * @param dishDTO
     */
    void updateDish(DishDTO dishDTO);

    /**
     * 删除菜品
     * @param ids
     */
    void deleteDish(List<Long> ids);

}
