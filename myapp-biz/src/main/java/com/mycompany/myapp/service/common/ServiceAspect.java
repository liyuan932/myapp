package com.mycompany.myapp.service.common;

import com.alibaba.fastjson.JSON;
import com.mycompany.myapp.daoobject.OperationLogDO;
import com.mycompany.myapp.enums.category.LogLevelEnum;
import com.mycompany.myapp.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.service.OperationLogService;
import com.mycompany.myapp.utils.log.OperationLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import java.util.Objects;

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
        OperationLogDO operationLogDO = new OperationLogDO();
        if (isEnable) {
            operationLogDO.setModule(ann.module().getIndex());
            operationLogDO.setAction(ann.action().getIndex());
            operationLogDO.setOperatorType(ann.operatorType().getIndex());
            operationLogDO.setLocation(ann.location().getIndex());

            operationLogDO.setMsg(StringUtils.isBlank(ann.msg()) ? ann.action().getText() : ann.msg());
            operationLogDO.setParamData(getParamData(pjp, signature));
        }

        long start = System.currentTimeMillis();
        try {
            //执行业务逻辑
            Object result = pjp.proceed();

            //执行后输出日志
            if (isEnable && ann.isRecordInfo()) {
                operationLogDO.setBizId(NumberUtils.toLong(parseExpress(pjp, ann.bizIdExp(), result)));
                operationLogDO.setBizCode(parseExpress(pjp, ann.bizCodeExp(), result));
                operationLogDO.setOperatorId(NumberUtils.toLong(parseExpress(pjp, ann.bizIdExp(), result)));
                operationLogDO.setLevel(LogLevelEnum.INFO.getIndex());
                operationLogDO.setResultData(JSON.toJSONString(result));
                operationLogDO.setCost(System.currentTimeMillis() - start);
                operationLogService.output(operationLogDO);
            }

            return result;
        } catch (Exception ex) {
            if (ex instanceof BizException) {
                if (isEnable && ann.isRecordWarn()) {
                    operationLogDO.setMsg(((BizException) ex).getMsg());
                    operationLogDO.setLevel(LogLevelEnum.WARN.getIndex());
                    operationLogService.output(operationLogDO);
                }
                throw ex;
            } else {
                if (isEnable) {
                    if (ex instanceof DataAccessException) {
                        operationLogDO.setMsg(CommonMsgEnum.FAIL_BIZ_DB_ERROR.getMsg() + "-" + ex.getMessage());
                    } else if (ex instanceof IllegalArgumentException) {
                        operationLogDO.setMsg(CommonMsgEnum.FAIL_BIZ_PARAM_ERROR.getMsg() + "-" + ex.getMessage());
                    } else {
                        operationLogDO.setMsg(CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR.getMsg() + "-" + ex.getMessage());
                    }
                    operationLogDO.setLevel(LogLevelEnum.ERROR.getIndex());
                    operationLogDO.setStackTrace(getStackTrace(ex));
                    operationLogService.output(operationLogDO);
                }

                throw ex;
            }
        }
    }

    private String parseExpress(ProceedingJoinPoint pjp, String exp, Object result) throws Exception {

        if (StringUtils.isBlank(exp)) {
            return null;
        }
        MethodSignature signature = (MethodSignature) pjp.getSignature();

        if (StringUtils.startsWith(exp, "#")) {  //从参数中获取
            exp = exp.substring(1);
            if (StringUtils.indexOf(exp, ".") == -1) {
                for (int i = 0; i < signature.getParameterNames().length; i++) {
                    if (signature.getParameterNames()[i].equals(exp)) {
                        return Objects.toString(pjp.getArgs()[i], null);
                    }
                }
            } else {  //从参数对象的字段中获取
                String paramName = exp.substring(0, StringUtils.indexOf(exp, "."));
                String methodName = exp.substring(StringUtils.indexOf(exp, ".") + 1, StringUtils.indexOf(exp, "("));
                for (int i = 0; i < signature.getParameterNames().length; i++) {
                    if (signature.getParameterNames()[i].equals(paramName)) {
                        Class clz = signature.getParameterTypes()[i];
                        return Objects.toString(clz.getDeclaredMethod(methodName).invoke(pjp.getArgs()[i]), null);
                    }
                }
            }
        } else if (StringUtils.startsWith(exp, "$")) {  //从结果中获取
            Class returnType = signature.getReturnType();
            exp = exp.substring(1);
            if (StringUtils.indexOf(exp, ".") == -1) {
                return Objects.toString(result, null);
            } else {
                String methodName = exp.substring(StringUtils.indexOf(exp, ".") + 1, StringUtils.indexOf(exp, "("));
                return Objects.toString(returnType.getDeclaredMethod(methodName).invoke(result), null);
            }
        }

        return exp;
    }


    private String getParamData(ProceedingJoinPoint pjp, MethodSignature signature) {
        Map<String, Object> paramData = new LinkedHashMap<>();
        for (int i = 0; i < signature.getParameterNames().length; i++) {
            paramData.put(signature.getParameterNames()[i], pjp.getArgs()[i]);
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
