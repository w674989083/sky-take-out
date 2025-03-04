package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "修改密码对象")
public class EmployeeEditPwdDTO {

    @ApiModelProperty(value = "员工id")
    private Long empId;
    @ApiModelProperty(value = "新密码")
    private String newPassword;
    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

}
