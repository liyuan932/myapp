package com.mycompany.myapp.service.common;

import com.mycompany.myapp.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.utils.log.LogBean;
import com.mycompany.myapp.utils.log.LogTypeEnum;
import com.mycompany.myapp.utils.log.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataAccessException;
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

        //获取日志bean
        LogBean logBean = LogUtils.getLogBean();
        if (logBean == null) {
            logBean = LogUtils.newLogBean(pjp.getTarget().toString(), pjp.getTarget().toString());
        }

        long start = System.currentTimeMillis();
        try {
            //前置日志
            if(logBean.getType() == LogTypeEnum.AROUND){
                logBean.setMsg(logBean.getMsg() + " start");
                LogUtils.info(logBean);
            }

            //执行业务逻辑
            Object result = pjp.proceed();

            //获取执行时间
            long end = System.currentTimeMillis();
            long cost = end - start;
            logBean.setCost(end + "ms");

            //后置日志
            if(logBean.getType() == LogTypeEnum.AROUND){
                logBean.setMsg(logBean.getMsg() + " end");
            }
            if (cost > LONG_BUSINESS_WARN) {
                LogUtils.warn(logBean);
            }else{
                LogUtils.info(logBean);
            }

            return result;
        } catch (BizException bex) {
            logBean.setMsg(bex.getMsg());
            throw bex;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                logBean.setMsg(CommonMsgEnum.FAIL_BIZ_DB_ERROR.getMsg() + "-" + ex.getMessage());
            } else if (ex instanceof IllegalArgumentException) {
                logBean.setMsg(CommonMsgEnum.FAIL_BIZ_PARAM_ERROR + "-" + ex.getMessage());
            } else {
                logBean.setMsg(CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR + "-" + ex.getMessage());
            }
            LogUtils.error(logBean);
            throw ex;
        }
    }
}
