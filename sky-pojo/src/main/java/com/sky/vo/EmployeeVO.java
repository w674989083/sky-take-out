package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "员工信息列表")
public class EmployeeVO implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value ="账号")
    private String username;

    @ApiModelProperty(value ="员工姓名")
    private String name;

    @ApiModelProperty(value ="手机号")
    private String phone;

    @ApiModelProperty(hidden = true)
    private String sex;

    @ApiModelProperty(value ="账号状态")
    private Integer status;

    @ApiModelProperty(value ="身份证号")
    private String idNumber;

    @ApiModelProperty(value ="最后操作时间")
    private LocalDateTime updateTime;


}
