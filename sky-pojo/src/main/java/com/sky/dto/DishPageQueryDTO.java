package com.sky.dto;

import com.sky.result.PageQuery;
import lombok.Data;

import java.io.Serializable;

@Data
public class DishPageQueryDTO extends PageQuery implements Serializable {


    // 菜品名称
    private String name;

    //分类id
    private Integer categoryId;

    //状态 0表示禁用 1表示启用
    private Integer status;

}
