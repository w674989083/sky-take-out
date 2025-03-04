package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 菜品 Mapper 接口
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
public interface DishMapper extends BaseMapper<Dish> {

    /**
     * 根据条件分页查询菜品数据
     * @param mpPage
     * @param query
     * @return
     */
    Page<DishVO> getPage(Page<DishVO> mpPage, @Param("query") DishPageQueryDTO query);

    /**
     * 根据id查询菜品数据
     * @param id
     * @return
     */
    DishVO getDishById(Long id);
}
