package com.mycompany.myapp.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.base.Result;
import com.mycompany.myapp.common.enums.CommonExceptionEnum;
import com.mycompany.myapp.common.exception.ServiceException;

@Aspect
@Component
public class ServiceInterceptor {

	private static final Logger log = LoggerFactory.getLogger(ServiceInterceptor.class);

	@Pointcut("execution(public *  com.mycompany.myapp.service.impl.*.*(..))")
	public void aspect() {
	}

	// @AfterThrowing(pointcut = "aspect()", throwing = "ex")
	public void afterThrowing(JoinPoint jp, Exception ex) throws Exception {
		log.error(jp + ex.getMessage(), ex);
	}

	@Around("aspect()")
	public Object around(ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();

		try {
			Object o = pjp.proceed();
			long end = System.currentTimeMillis();
			log.info(pjp + "\ttake time : " + (end - start) + " ms");
			return o;
		} catch (Throwable e) {
			long end = System.currentTimeMillis();
			log.info(pjp + "\ttake time : " + (end - start) + " ms");

			if (e instanceof ServiceException) {
				return fail(pjp, ((ServiceException) e).getCode(),e.getMessage() );
			}if (e instanceof IllegalArgumentException) {
				return fail(pjp, CommonExceptionEnum.FAIL_BIZ_PARAM_ERROR, e);
			} else if (e instanceof DataAccessException) {
				return fail(pjp, CommonExceptionEnum.FAIL_BIZ_DB_ERROR, e);
			} else {
				return fail(pjp, CommonExceptionEnum.FAIL_BIZ_SYSTEM_ERROR, e);
			}
		}
	}

	private Result<?> fail(ProceedingJoinPoint pjp, CommonExceptionEnum commonExceptionEnum, Throwable e) {

		log.error(pjp + " " + commonExceptionEnum.getMsg(), e);

		return new Result<Object>(commonExceptionEnum.getCode(), commonExceptionEnum.getMsg());
	}

	private Result<?> fail(ProceedingJoinPoint pjp, String code, String msg) {

		return new Result<Object>(code, msg);
	}
}
