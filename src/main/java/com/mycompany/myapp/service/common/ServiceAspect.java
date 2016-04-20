package com.mycompany.myapp.service.common;

import com.mycompany.myapp.common.enums.msg.CommonMsgEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {

	private static final Logger log = LoggerFactory.getLogger(ServiceAspect.class);

	@Pointcut("execution(public *  com.mycompany.myapp.service.impl.*.*(..))")
	public void aspect() {
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
				return fail(((ServiceException) e).getCode(),e.getMessage());
			}if (e instanceof IllegalArgumentException) {
				return fail(pjp, CommonMsgEnum.FAIL_BIZ_PARAM_ERROR, e);
			} else if (e instanceof DataAccessException) {
				return fail(pjp, CommonMsgEnum.FAIL_BIZ_DB_ERROR, e);
			} else {
				return fail(pjp, CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR, e);
			}
		}
	}

	private Result<?> fail(ProceedingJoinPoint pjp, CommonMsgEnum commonMsgEnum, Throwable e) {
		log.error(pjp + " " + commonMsgEnum.getMsg(), e);
		return new Result<>(commonMsgEnum.getCode(), commonMsgEnum.getMsg());
	}

	private Result<?> fail(String code, String msg) {
		return new Result<>(code, msg);
	}
}
