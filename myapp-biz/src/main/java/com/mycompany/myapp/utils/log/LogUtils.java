package com.mycompany.myapp.utils.log;


import com.mycompany.myapp.enums.function.FunctionEnum;
import com.mycompany.myapp.enums.function.MainFunctionEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;

public class LogUtils {

    private static final Logger log = LoggerFactory.getLogger("operationLog");

    private static NamedThreadLocal<LogBean> threadLocal = new NamedThreadLocal<>("LogBean");

    public static LogBean newLogBean(MainFunctionEnum mainFunctionEnum, FunctionEnum functionEnum) {
        LogBean logBean = new LogBean(mainFunctionEnum, functionEnum);
        threadLocal.set(logBean);
        return logBean;
    }

    public static LogBean newLogBean(MainFunctionEnum mainFunctionEnum, FunctionEnum functionEnum, String msg) {
        LogBean logBean = new LogBean(mainFunctionEnum, functionEnum, msg);
        threadLocal.set(logBean);
        return logBean;
    }

    public static LogBean getLogBean() {
        return threadLocal.get();
    }


    public static void debug(LogBean logBean) {
        log.debug(logBean.toString());
    }

    public static void debug(LogBean logBean, Exception ex) {
        processException(logBean, ex);
        log.debug(logBean.toString());
    }

    public static void info(LogBean logBean) {
        log.info(logBean.toString());
    }

    public static void info(LogBean logBean, Exception ex) {
        processException(logBean, ex);
        log.info(logBean.toString());
    }

    public static void warn(LogBean logBean) {
        log.warn(logBean.toString());
    }

    public static void warn(LogBean logBean, Exception ex) {
        processException(logBean, ex);
        log.warn(logBean.toString());
    }

    public static void error(LogBean logBean) {
        log.error(logBean.toString());
    }

    public static void error(LogBean logBean, Exception ex) {
        processException(logBean, ex);
        log.error(logBean.toString());
    }

    private static void processException(LogBean logBean, Exception ex) {
        if (ex != null) {
            if (StringUtils.isBlank(logBean.getMsg())) {
                logBean.setMsg(ex.getMessage());
            }

            StringBuilder trackTrace = new StringBuilder();
            StackTraceElement[] trace = ex.getStackTrace();
            for (int i = 0; i < trace.length; i++) {
                StackTraceElement traceElement = trace[i];
                trackTrace.append(traceElement.toString()).append("@&");
            }
            logBean.setStackTrace(trackTrace.toString());
        }
    }
}
