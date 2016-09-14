package com.mycompany.myapp.service.common;

import com.alibaba.fastjson.JSON;
import com.mycompany.myapp.daoobject.OperationLogDO;
import com.mycompany.myapp.enums.category.LogLevelEnum;
import com.mycompany.myapp.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.service.OperationLogService;
import com.mycompany.myapp.utils.log.OperationLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
@Component
public class ServiceAspect {

    @Resource
    private OperationLogService operationLogService;

    @Pointcut("execution(public *  com.mycompany.myapp.service.impl.*.*(..))")
    public void aspect() {
    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        OperationLog ann = signature.getMethod().getAnnotation(OperationLog.class);
        boolean isEnable = ann != null;

        //执行前初始化日志
        OperationLogDO operationLogDO = null;
        if(isEnable){
            operationLogDO = new OperationLogDO();
            operationLogDO.setModule(ann.module().name());
            operationLogDO.setAction(ann.module().name());
            operationLogDO.setOperatorType(ann.operatorType().getIndex());
            operationLogDO.setLocation(ann.location().getIndex());
            operationLogDO.setOperatorId(-1L);
            operationLogDO.setParamData(getParamData(pjp, signature));
        }

        long start = System.currentTimeMillis();
        try {
            //执行业务逻辑
            Object result = pjp.proceed();

            //执行后输出日志
            if(isEnable){
                operationLogDO.setLevel(LogLevelEnum.INFO.getIndex());
                operationLogDO.setResultData(JSON.toJSONString(result));
                operationLogDO.setCost(System.currentTimeMillis() - start);
                operationLogService.output(operationLogDO);
            }


            return result;
        } catch (Exception ex) {
            if(ex instanceof BizException){
                if(isEnable){
                    operationLogDO.setMsg(((BizException)ex).getMsg());
                    operationLogDO.setLevel(LogLevelEnum.WARN.getIndex());
                    operationLogService.output(operationLogDO);
                }
                throw ex;
            }else{
                if(isEnable){
                    if (ex instanceof DataAccessException) {
                        operationLogDO.setMsg(CommonMsgEnum.FAIL_BIZ_DB_ERROR.getMsg() + "-" + ex.getMessage());
                    } else if (ex instanceof IllegalArgumentException) {
                        operationLogDO.setMsg(CommonMsgEnum.FAIL_BIZ_PARAM_ERROR + "-" + ex.getMessage());
                    } else {
                        operationLogDO.setMsg(CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR + "-" + ex.getMessage());
                    }
                    operationLogDO.setStackTrace(getStackTrace(ex));
                    operationLogService.output(operationLogDO);
                }

                throw ex;
            }

        }
    }

    private boolean isEnableOperationLog(OperationLog ann){
        return ann != null;
    }

    private String getParamData(ProceedingJoinPoint pjp, MethodSignature signature) {
        Map<String, Object> paramData = new LinkedHashMap<>();
        for (int i = 0; i < signature.getParameterNames().length; i++) {
            paramData.put(signature.getParameterNames()[i],pjp.getArgs()[i]);
        }
        return JSON.toJSONString(paramData);
    }

    private String getStackTrace(Exception ex) {
        StringBuilder trackTrace = new StringBuilder();
        StackTraceElement[] trace = ex.getStackTrace();
        for (int i = 0; i < trace.length; i++) {
            StackTraceElement traceElement = trace[i];
            trackTrace.append(traceElement.toString()).append("@&");
        }
        return trackTrace.toString();
    }
}
