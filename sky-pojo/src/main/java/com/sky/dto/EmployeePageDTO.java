package com.sky.dto;

import com.sky.result.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "员工列表分页查询条件")
public class EmployeePageDTO extends PageQuery implements Serializable {

    @ApiModelProperty(value = "员工姓名")
    private String name;
}
