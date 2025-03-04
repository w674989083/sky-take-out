package com.sky.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;

/**
 * AOP切面自动填充方案
 */
//@Aspect
//@Component
public class AuditFillAspect {

    /**
     * 切入点
     */
    /*@Pointcut("")
    public void autoFillPointcut() {}*/


    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("@annotation(com.sky.annotation.AutoFill)")
    public void beforeInsert(JoinPoint joinPoint) {

    }
}