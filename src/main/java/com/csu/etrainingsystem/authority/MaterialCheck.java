package com.csu.etrainingsystem.authority;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MaterialCheck {

    @Pointcut("execution (* com.csu.etrainingsystem.material.controller..*.*(..))")
    public void teacherApply(){}

    @Before("teacherApply()")
    public void test(JoinPoint joinPoint){
        System.out.println("e");
    }


}
