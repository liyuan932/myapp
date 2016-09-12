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

    LogBean logBean = LogUtils.getLogBean();
    long start = System.currentTimeMillis();
    try {
      Object result = pjp.proceed();
      long end = System.currentTimeMillis();

      long cost = end - start;

      if (logBean != null) {
        logBean.setCost(cost + "ms");
        if (cost > LONG_BUSINESS_WARN) {
          LogUtils.warn(logBean);
        } else {
          LogUtils.info(logBean);
        }
      }

      return result;
    } catch (Exception ex) {
      if (logBean != null) {
        long end = System.currentTimeMillis();
        long cost = end - start;
        logBean.setCost(cost + "ms");
        logBean.setMsg(ex.getMessage());
        if (ex instanceof BizException) {
          LogUtils.warn(logBean);
        } else{
          LogUtils.error(logBean,ex);
        }
      }

      throw ex;
    }
  }
}
