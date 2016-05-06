package com.mycompany.myapp.service.common;

import com.mycompany.myapp.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.vo.Result;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

//@Aspect
//@Component
public class ServiceAspect {

  private static final Logger log = LoggerFactory.getLogger(ServiceAspect.class);

  @Pointcut("execution(public *  com.mycompany.myapp.service.impl.*.*(..))")
  public void aspect() {
  }

  @Around("aspect()")
  public Object around(ProceedingJoinPoint pjp) {
    long start = System.currentTimeMillis();

    try {
      Object result = pjp.proceed();
      long end = System.currentTimeMillis();
      log.info(pjp + "\ttake time : " + (end - start) + " ms");
      return result;
    } catch (Throwable ex) {
      if (ex instanceof ServiceException) {
        return fail(((ServiceException) ex).getCode(), ex.getMessage());
      }
      if (ex instanceof IllegalArgumentException) {
        return fail(pjp, CommonMsgEnum.FAIL_BIZ_PARAM_ERROR, ex);
      } else if (ex instanceof DataAccessException) {
        return fail(pjp, CommonMsgEnum.FAIL_BIZ_DB_ERROR, ex);
      } else {
        return fail(pjp, CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR, ex);
      }
    }
  }

  private Result<?> fail(ProceedingJoinPoint pjp, CommonMsgEnum commonMsgEnum, Throwable ex) {
    log.error(pjp + " " + commonMsgEnum.getMsg(), ex);
    return new Result<>(commonMsgEnum.getCode(), commonMsgEnum.getMsg());
  }

  private Result<?> fail(String code, String msg) {
    return new Result<>(code, msg);
  }
}
