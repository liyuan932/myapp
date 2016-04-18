package com.mycompany.myapp.common.aspect;

import com.mycompany.myapp.base.BaseResult;
import com.mycompany.myapp.common.enums.common.CommonMsgEnum;
import com.mycompany.myapp.common.exception.BizException;
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

			if (e instanceof BizException) {
				return fail(((BizException) e).getCode(),e.getMessage());
			}if (e instanceof IllegalArgumentException) {
				return fail(pjp, CommonMsgEnum.FAIL_BIZ_PARAM_ERROR, e);
			} else if (e instanceof DataAccessException) {
				return fail(pjp, CommonMsgEnum.FAIL_BIZ_DB_ERROR, e);
			} else {
				return fail(pjp, CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR, e);
			}
		}
	}

	private BaseResult<?> fail(ProceedingJoinPoint pjp, CommonMsgEnum commonMsgEnum, Throwable e) {
		log.error(pjp + " " + commonMsgEnum.getMsg(), e);
		return new BaseResult<>(commonMsgEnum.getCode(), commonMsgEnum.getMsg());
	}

	private BaseResult<?> fail(String code, String msg) {
		return new BaseResult<>(code, msg);
	}
}
