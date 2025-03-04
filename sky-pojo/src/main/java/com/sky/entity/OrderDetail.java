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
 * 订单明细表
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_detail")
@ApiModel(value="OrderDetail对象", description="订单明细表")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "名字")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "图片")
    @TableField("image")
    private String image;

    @ApiModelProperty(value = "订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "菜品id")
    @TableField("dish_id")
    private Long dishId;

    @ApiModelProperty(value = "套餐id")
    @TableField("setmeal_id")
    private Long setmealId;

    @ApiModelProperty(value = "口味")
    @TableField("dish_flavor")
    private String dishFlavor;

    @ApiModelProperty(value = "数量")
    @TableField("number")
    private Integer number;

    @ApiModelProperty(value = "金额")
    @TableField("amount")
    private BigDecimal amount;


}
