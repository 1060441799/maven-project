package com.newframe.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {
	// Controller层切点
	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controllerAspect() {
	}

	@Before(value = "controllerAspect()")
	public void before() {
		System.out.println("方法执行前执行.....");
	}

	@AfterReturning(value = "controllerAspect()")
	public void afterReturning() {
		System.out.println("方法执行完执行.....");
	}

	@AfterThrowing(value = "controllerAspect()")
	public void throwss() {
		System.out.println("方法异常时执行.....");
	}

	@After(value = "controllerAspect()")
	public void after() {
		System.out.println("方法最后执行.....");
	}

	@Around(value = "controllerAspect()")
	public Object around(ProceedingJoinPoint pjp) {
		System.out.println("方法环绕start.....");
		Object o = null;
		try {
			o = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("方法环绕end.....");
		return o;
	}
}
