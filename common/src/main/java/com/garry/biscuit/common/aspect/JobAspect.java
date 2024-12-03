package com.garry.biscuit.common.aspect;

import cn.hutool.core.date.DateTime;
import com.garry.biscuit.common.consts.CommonConst;
import com.garry.biscuit.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class JobAspect {

    @Pointcut("execution(public * com.garry.train.batch.job..*.*(..))")
    public void jobPointcut() {
    }

    @Around("jobPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MDC.put(CommonConst.LOG_ID, CommonUtil.generateUUID(CommonConst.LOG_ID_LENGTH));
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        className = className.substring(className.lastIndexOf(".") + 1);
        String methodName = proceedingJoinPoint.getSignature().getName();
        String name = className + "." + methodName;
        DateTime start = DateTime.now();
        long s = System.currentTimeMillis();
        log.info("------------- {} 开始时间: {} -------------", name, start);

        Object result = proceedingJoinPoint.proceed();

        DateTime end = DateTime.now();
        long e = System.currentTimeMillis();
        log.info("------------- {} 结束时间: {}，用时: {} ms -------------", name, end, e - s);

        return result;
    }
}
