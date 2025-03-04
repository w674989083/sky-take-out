package com.sky.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeEditPwdDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.BaseException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import com.sky.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //密码比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        checkUpdate(employeeDTO);
        // 默认值
        Employee employee = Employee.builder()
                .password(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()))// 默认密码
                .status(StatusConstant.ENABLE)
                /*.createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .createUser(BaseContext.getCurrentId()).updateUser(BaseContext.getCurrentId())*/
                .build();
        BeanUtil.copyProperties(employeeDTO, employee);
        save(employee);
    }

    private void checkUpdate(EmployeeDTO employeeDTO) {
        // 验证用户名
        Employee emp = getOne(new LambdaQueryWrapper<Employee>()
                .eq(Employee::getUsername, employeeDTO.getUsername())
                .ne(employeeDTO.getId() != null, Employee::getId, employeeDTO.getId())
        );
        if (emp != null){
            throw new BaseException("用户名已存在");
        }
        // 校验手机号是否合法
        if (employeeDTO.getPhone() == null || employeeDTO.getPhone().length() != 11){
            throw new BaseException("手机号不合法");
        }
        // 校验身份证号是否合法
        if (employeeDTO.getIdNumber() == null || employeeDTO.getIdNumber().length() != 18){
            throw new BaseException("身份证号不合法");
        }
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) {
        // 验证员工信息
        checkUpdate(employeeDTO);
        Employee employee = BeanUtils.copyBean(employeeDTO, Employee.class);
                /*.setUpdateTime(LocalDateTime.now())
                .setUpdateUser(BaseContext.getCurrentId())*/;
        updateById(employee);
    }

    @Override
    public void editPassword(EmployeeEditPwdDTO editPwdDTO) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<Employee>()
                .eq(Employee::getId, BaseContext.getCurrentId())
                .eq(Employee::getPassword, DigestUtils.md5DigestAsHex(editPwdDTO.getOldPassword().getBytes()));
        // 校验旧密码
        Employee emp = getOne(wrapper);
        if (emp == null){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        emp.setPassword(DigestUtils.md5DigestAsHex(editPwdDTO.getNewPassword().getBytes()));
        /*update(new LambdaUpdateWrapper<Employee>()
                .set(Employee::getPassword, emp.getPassword())
                .eq(Employee::getId, emp.getId()));
        emp.setUpdateTime(LocalDateTime.now());*/
        updateById(emp);

    }


}
