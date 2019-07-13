package cn.tedu.store.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeElapseAspect {
	@Around("execution(* cn.tedu.store.service.impl.*.*(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		//在業務方法之前執行的代碼
		long start = System.currentTimeMillis();
		
		//執行業務方法,表示執行任何一個應用範圍之內的方法
		Object obj = pjp.proceed();
		
		//在業務方法之後執行的代碼
		long end = System.currentTimeMillis();
		System.err.println(
			"執行時間:" + (end - start) + "ms.");
		
		//返回
		return obj;
	}
}
