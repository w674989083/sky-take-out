package com.sky.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeEditPwdDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.BeanUtils;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import com.sky.vo.EmployeeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final JwtProperties jwtProperties;


    /**
     * 新增员工
     *
     * @param employeeDTO
     * @return
     */
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);
        employeeService.addEmployee(employeeDTO);
        return Result.success();
    }

    /**
     * 分页查询
     * @param employeePageDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult<EmployeeVO>> page(EmployeePageDTO employeePageDTO){
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<Employee>()
                .like(StrUtil.isNotBlank(employeePageDTO.getName()), Employee::getName, employeePageDTO.getName())
                .orderByDesc(Employee::getUpdateTime);
        Page<Employee> page = employeeService.page(employeePageDTO.toMpPage(),queryWrapper);
        return Result.success(PageResult.of(page, EmployeeVO.class));
    }
    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<EmployeeVO> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee == null){
            return Result.error("员工不存在");
        }
        EmployeeVO employeeVO = BeanUtils.copyBean(employee, EmployeeVO.class);
        return Result.success(employeeVO);
    }

    /**
     * 修改员工信息
     * @param employeeDTO
     * @return
     */
    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("员工修改：{}",employeeDTO);
        employeeService.updateEmployee(employeeDTO);
        return Result.success();
    }

    /**
     * 员工状态修改
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("员工状态修改：{}",id);
        LambdaUpdateWrapper<Employee> wrapper = new LambdaUpdateWrapper<Employee>()
                .set(Employee::getStatus, status)
                .eq(Employee::getId, id);
        employeeService.update(wrapper);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        log.info("删除员工：{}",id);
        employeeService.removeById(id);
        return Result.success();
    }







    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        log.info("退出登录");

        return Result.success();
    }


    @PutMapping("/editPassword")
    public Result editPassword(@RequestBody EmployeeEditPwdDTO editPwdDTO){
        log.info("修改密码：{}",editPwdDTO);
        employeeService.editPassword(editPwdDTO);
        return Result.success();
    }
}
