package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "新增员工请求擦参数")
public class EmployeeDTO implements Serializable {

    private Long id;

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "性别")
    @NotBlank(message = "请选择性别")
    private String sex;

    @ApiModelProperty(value = "身份证号")
    private String idNumber;

}
