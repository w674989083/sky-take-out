package com.sky.controller.admin;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.ICategoryService;
import com.sky.utils.BeanUtils;
import com.sky.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 菜品及套餐分类 前端控制器
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping
    public Result save(@RequestBody @Valid CategoryDTO categoryDTO) {
        categoryService.saveCategory(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult<CategoryVO>> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<Category>()
                .like(StrUtil.isNotBlank(categoryPageQueryDTO.getName()), Category::getName, categoryPageQueryDTO.getName())
                .eq(categoryPageQueryDTO.getType() != null, Category::getType, categoryPageQueryDTO.getType());
        OrderItem orderItem = new OrderItem();
        orderItem.setAsc(true);
        orderItem.setColumn("sort");
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setAsc(false);
        orderItem1.setColumn("update_time");
        Page<Category> page = categoryService.page(categoryPageQueryDTO.toMpPage(orderItem,orderItem1),wrapper);
//        Page<CategoryVO> pageVP = categoryService.getPageList(categoryPageQueryDTO);
        return Result.success(PageResult.of(page, CategoryVO.class));
    }

    @PutMapping
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updById(categoryDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<CategoryVO>> list(@RequestParam Integer type) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<Category>()
                .eq(Category::getType, type);
        List<Category> list = categoryService.list(wrapper);
        return Result.success(BeanUtils.copyList(list, CategoryVO.class));
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, @RequestParam Long id) {
        categoryService.startOrStop(status, id);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam(value = "id") List<Long> ids) {
        categoryService.checkDelete(ids);
        return Result.success();
    }



}
