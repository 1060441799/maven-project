package com.newframe.core.aop;

import com.google.common.collect.Maps;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by xm on 2016/4/2.
 */
@Aspect
@Component
public class PerfAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(PerfAspect.class);

    private static Map<String, Perf> perfs = Maps.newHashMap();

    @Around("execution(* com.newframe.web.controller..*.*(..))")
    public Object doPerformanceMeasure(ProceedingJoinPoint pjp) throws Throwable{
        Perf p;
        if (perfs.containsKey(pjp.getSignature().toShortString())) {
            p = perfs.get(pjp.getSignature().toShortString());
        } else {
            p = new Perf(pjp.getSignature().toShortString());
            perfs.put(pjp.getSignature().toShortString(), p);
        }
        long s = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long d = System.currentTimeMillis() - s;
            if (!pjp.getSignature().toShortString().contains("heartbeat")) {
                p.addExecetion(d);
                LOGGER.info("[{}] execution time (millis) = {} " + p.prettyPrint(), p.getName(), d);
            }
        }
    }

    static final class Perf {
        private double total;
        private double totalTime;
        private String name;

        public Perf(String name) {
            total = totalTime = 0.0;
            this.name = name;
        }

        public double getTotal() {
            return total;
        }

        public double getTotalTime() {
            return totalTime;
        }

        public String getName() {
            return name;
        }

        public double getAverageTime() {
            return totalTime / getTotal();
        }

        public void addExecetion(long time) {
            totalTime += time;
            total += 1.0;
        }

        public String prettyPrint() {
            return new StringBuilder("[").append(name).append("] total running time (millis) = ").append(totalTime)
                    .append(", total (count) = ").append(total).append(", average time (millis) = ")
                    .append(getAverageTime()).toString();
        }
    }
}
