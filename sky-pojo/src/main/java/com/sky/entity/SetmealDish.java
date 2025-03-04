package com.sky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 套餐菜品关系
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("setmeal_dish")
@ApiModel(value="SetmealDish对象", description="套餐菜品关系")
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "套餐id")
    @TableField("setmeal_id")
    private Long setmealId;

    @ApiModelProperty(value = "菜品id")
    @TableField("dish_id")
    private Long dishId;

    @ApiModelProperty(value = "菜品名称 （冗余字段）")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "菜品单价（冗余字段）")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "菜品份数")
    @TableField("copies")
    private Integer copies;


}
