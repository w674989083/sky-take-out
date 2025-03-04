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
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders")
@ApiModel(value="Orders对象", description="订单表")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单号")
    @TableField("number")
    private String number;

    @ApiModelProperty(value = "订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "下单用户")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "地址id")
    @TableField("address_book_id")
    private Long addressBookId;

    @ApiModelProperty(value = "下单时间")
    @TableField("order_time")
    private LocalDateTime orderTime;

    @ApiModelProperty(value = "结账时间")
    @TableField("checkout_time")
    private LocalDateTime checkoutTime;

    @ApiModelProperty(value = "支付方式 1微信,2支付宝")
    @TableField("pay_method")
    private Integer payMethod;

    @ApiModelProperty(value = "支付状态 0未支付 1已支付 2退款")
    @TableField("pay_status")
    private Integer payStatus;

    @ApiModelProperty(value = "实收金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "用户名称")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "收货人")
    @TableField("consignee")
    private String consignee;

    @ApiModelProperty(value = "订单取消原因")
    @TableField("cancel_reason")
    private String cancelReason;

    @ApiModelProperty(value = "订单拒绝原因")
    @TableField("rejection_reason")
    private String rejectionReason;

    @ApiModelProperty(value = "订单取消时间")
    @TableField("cancel_time")
    private LocalDateTime cancelTime;

    @ApiModelProperty(value = "预计送达时间")
    @TableField("estimated_delivery_time")
    private LocalDateTime estimatedDeliveryTime;

    @ApiModelProperty(value = "配送状态  1立即送出  0选择具体时间")
    @TableField("delivery_status")
    private Boolean deliveryStatus;

    @ApiModelProperty(value = "送达时间")
    @TableField("delivery_time")
    private LocalDateTime deliveryTime;

    @ApiModelProperty(value = "打包费")
    @TableField("pack_amount")
    private Integer packAmount;

    @ApiModelProperty(value = "餐具数量")
    @TableField("tableware_number")
    private Integer tablewareNumber;

    @ApiModelProperty(value = "餐具数量状态  1按餐量提供  0选择具体数量")
    @TableField("tableware_status")
    private Boolean tablewareStatus;


}
