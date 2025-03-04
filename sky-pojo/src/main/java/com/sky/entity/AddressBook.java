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

/**
 * <p>
 * 地址簿
 * </p>
 *
 * @author lgf
 * @since 2025-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("address_book")
@ApiModel(value="AddressBook对象", description="地址簿")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "收货人")
    @TableField("consignee")
    private String consignee;

    @ApiModelProperty(value = "性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "省级区划编号")
    @TableField("province_code")
    private String provinceCode;

    @ApiModelProperty(value = "省级名称")
    @TableField("province_name")
    private String provinceName;

    @ApiModelProperty(value = "市级区划编号")
    @TableField("city_code")
    private String cityCode;

    @ApiModelProperty(value = "市级名称")
    @TableField("city_name")
    private String cityName;

    @ApiModelProperty(value = "区级区划编号")
    @TableField("district_code")
    private String districtCode;

    @ApiModelProperty(value = "区级名称")
    @TableField("district_name")
    private String districtName;

    @ApiModelProperty(value = "详细地址")
    @TableField("detail")
    private String detail;

    @ApiModelProperty(value = "标签")
    @TableField("label")
    private String label;

    @ApiModelProperty(value = "默认 0 否 1是")
    @TableField("is_default")
    private Boolean isDefault;


}
