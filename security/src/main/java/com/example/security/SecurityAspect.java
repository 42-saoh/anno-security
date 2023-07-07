package com.example.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    @Around("@annotation(com.example.security.SecurityOneAnnotation)")
    public Object checkSecurity(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("This is a test 2");
        SecurityOneAnnotation securityOneAnnotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(SecurityOneAnnotation.class);

        String value = securityOneAnnotation.value();
        System.out.println("This is a value: " + value);
        Object proceed = joinPoint.proceed();

        System.out.println("This is a test 4");

        return proceed;
    }

    @Around("@annotation(com.example.security.SecurityTwoAnnotation)")
    public Object checkSecurityTwo(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        System.out.println("This is a arg: " + (String)args[0]);

        Object proceed = joinPoint.proceed();

        System.out.println("This is a return: " + (String)proceed);

        return "This is a test";
    }
}
