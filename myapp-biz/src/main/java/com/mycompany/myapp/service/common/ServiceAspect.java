package com.mycompany.myapp.service.common;

import com.alibaba.fastjson.JSON;
import com.mycompany.myapp.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.utils.log.OperationLog;
import com.mycompany.myapp.utils.log.LogBean;
import com.mycompany.myapp.utils.log.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        OperationLog operationLog = signature.getMethod().getAnnotation(OperationLog.class);
        System.out.println(Arrays.toString(signature.getParameterNames()));
        System.out.println(Arrays.toString(signature.getParameterTypes()));
        LogBean logBean = LogUtils.newLogBean(pjp.toString());
        logBean.addParamData("paramData", Arrays.toString(pjp.getArgs()));
        long start = System.currentTimeMillis();
        try {
            //执行业务逻辑
            Object result = pjp.proceed();
            logBean.addResultData("resultData", JSON.toJSON(result));

            //获取执行时间
            long end = System.currentTimeMillis();
            long cost = end - start;
            logBean.setCost(cost + "ms");
            if (cost > LONG_BUSINESS_WARN) {
                LogUtils.warn(logBean);
            } else {
                if(operationLog != null){
                    LogUtils.info(logBean);
                }
            }

            return result;
        } catch (BizException bex) {
            logBean.setMsg(bex.getMsg());
            LogUtils.warn(logBean);
            throw bex;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                logBean.setMsg(CommonMsgEnum.FAIL_BIZ_DB_ERROR.getMsg() + "-" + ex.getMessage());
            } else if (ex instanceof IllegalArgumentException) {
                logBean.setMsg(CommonMsgEnum.FAIL_BIZ_PARAM_ERROR + "-" + ex.getMessage());
            } else {
                logBean.setMsg(CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR + "-" + ex.getMessage());
            }
            LogUtils.error(logBean, ex);
            throw ex;
        }
    }
}
