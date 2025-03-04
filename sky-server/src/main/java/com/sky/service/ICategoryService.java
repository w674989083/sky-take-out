package com.sky.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.vo.CategoryVO;

import java.util.List;

/**
 * <p>
 * 菜品及套餐分类 服务类
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 新增菜品或套餐分类
     * @param categoryDTO
     */
    void saveCategory(CategoryDTO categoryDTO);

    Page<CategoryVO> getPageList(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 修改分类
     * @param categoryDTO
     */
    void updById(CategoryDTO categoryDTO);

    /**
     * 启用禁用
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 删除分类
     * @param ids
     */
    void checkDelete(List<Long> ids);
}
