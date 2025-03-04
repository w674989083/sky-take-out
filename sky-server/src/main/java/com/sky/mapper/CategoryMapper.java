package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 菜品及套餐分类 Mapper 接口
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
public interface CategoryMapper extends BaseMapper<Category> {


    Page<CategoryVO> getPageList(Page<CategoryVO> page, @Param("category") CategoryPageQueryDTO categoryPageQueryDTO);

}
