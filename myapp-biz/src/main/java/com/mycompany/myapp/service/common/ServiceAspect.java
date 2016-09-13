package com.mycompany.myapp.service.common;

import com.alibaba.fastjson.JSON;
import com.mycompany.myapp.dao.OperationLogDAO;
import com.mycompany.myapp.daoobject.OperationLogDO;
import com.mycompany.myapp.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.utils.log.LogBean;
import com.mycompany.myapp.enums.category.LogLocationEnum;
import com.mycompany.myapp.utils.log.LogUtils;
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
    private OperationLogDAO operationLogDAO;

    @Pointcut("execution(public *  com.mycompany.myapp.service.impl.*.*(..))")
    public void aspect() {
    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        //获取日志bean
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        OperationLog ann = signature.getMethod().getAnnotation(OperationLog.class);
        OperationLogDO operationLogDO = new OperationLogDO();
        if(ann != null){
            operationLogDO.setModule(ann.module().name());
            operationLogDO.setAction(ann.module().name());
            //operationLogDO.setOperatorId(getUserId());
            operationLogDO.setOperatorType(ann.operatorType().getIndex());
            operationLogDO.setLocation(ann.location().getIndex());
        }{
            operationLogDO.setModule(signature.getDeclaringTypeName());
            operationLogDO.setAction(signature.getName());
            operationLogDO.setLocation(LogLocationEnum.FILE.getIndex());
        }

        Map<String, Object> paramData = new LinkedHashMap<>();
        for (int i = 0; i < signature.getParameterNames().length; i++) {
            paramData.put(signature.getParameterNames()[i],pjp.getArgs()[i]);
        }

        operationLogDO.setParamData(JSON.toJSONString(paramData));

        LogBean logBean = null;
        long start = System.currentTimeMillis();
        try {
            //执行业务逻辑
            Object result = pjp.proceed();

            //获取执行结果及执行时间
            operationLogDO.setResultData(JSON.toJSONString(result));
            operationLogDO.setCost(System.currentTimeMillis() - start);

            if(operationLogDO.getLocation() == LogLocationEnum.DB.getIndex()){
                operationLogDAO.insert(operationLogDO);
            }else{

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
