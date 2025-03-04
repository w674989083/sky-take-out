package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.IDishService;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜品 前端控制器
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
@RestController
@RequestMapping("/admin/dish")
@RequiredArgsConstructor
public class DishController {

    private final IDishService dishService;

    @PostMapping
    public Result addDish(@RequestBody DishDTO dishDTO){
        dishService.addDish(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult<DishVO>> getPage(DishPageQueryDTO pageQueryDTO){
        return Result.success(dishService.getPage(pageQueryDTO));
    }

    @GetMapping("/list")
    public Result<List<DishVO>> getListByCategoryId(Long categoryId){
        List<DishVO> list = dishService.getListByCategoryId(categoryId);
        return Result.success(list);
    }

    @GetMapping("{id}")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        DishVO dishVO = dishService.getDishById(id);
        return Result.success(dishVO);
    }

    @PostMapping("/status/{status}")
    public Result<String> startOrStop(@PathVariable Integer status, @RequestParam Long id) {
        dishService.startOrStop(status, id);
        return Result.success();
    }

    @PutMapping
    public Result<String> updateDish(@RequestBody DishDTO dishDTO) {
        dishService.updateDish(dishDTO);
        return Result.success();
    }

    @DeleteMapping
    public Result<String> deleteDish(@RequestParam List<Long> ids) {
        dishService.deleteDish(ids);
        return Result.success();
    }

}
