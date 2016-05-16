package com.newframe.core.aop;

import com.newframe.core.config.AppConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xm on 2016/4/2.
 */
@Aspect
@Component
public class DaoPerfAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(DaoPerfAspect.class);

    @Autowired
    AppConfig appConfig;

    @Around("execution( * com.newframe.web.dao..*Dao.*(..))")
    public Object doPerformanceMeasure(ProceedingJoinPoint pjp) throws Throwable {
        long s = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            if (!pjp.getSignature().toShortString().contains("UserActivityDao")) {
                long d = System.currentTimeMillis() - s;
                if (d > Long.parseLong(appConfig.getSqlQueryThreshold())) {
                    LOGGER.info("Execution time of [{}] method is {} milliseconds.", pjp.getSignature().toShortString(), d);
                }
            }
        }
    }
}
