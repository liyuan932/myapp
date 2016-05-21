package com.mycompany.myapp.service.common;

import com.mycompany.myapp.utils.log.LogBean;
import com.mycompany.myapp.utils.log.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {

  private final static long LONG_BUSINESS_WARN = 3000;

  @Pointcut("execution(public *  com.mycompany.myapp.service.impl.*.*(..))")
  public void aspect() {
  }

  @Around("aspect()")
  public Object around(ProceedingJoinPoint pjp) throws Throwable {

    long start = System.currentTimeMillis();
    try {
      Object result = pjp.proceed();
      long end = System.currentTimeMillis();

      long cost = end - start;
      LogBean logBean = LogUtils.getLogBean();
      if (logBean != null) {
        LogUtils.getLogBean().setCost(cost + "ms");
        if (cost > LONG_BUSINESS_WARN) {
          LogUtils.warn(logBean);
        } else {
          LogUtils.info(logBean);
        }
      }

      return result;
    } catch (Exception ex) {
      LogBean logBean = LogUtils.getLogBean();
      if (logBean != null) {
        long end = System.currentTimeMillis();
        long cost = end - start;
        logBean.setCost(cost + "ms");
        logBean.setMsg(ex.getMessage());
        if (ex instanceof ServiceException) {
          LogUtils.warn(logBean);
        } else{
          LogUtils.error(logBean,ex);
        }
      }


      throw ex;
    }
  }

  /*
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
  */
}
