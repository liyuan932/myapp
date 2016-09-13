package com.mycompany.myapp.utils.log;


import com.mycompany.myapp.enums.function.MainFuncEnum;
import com.mycompany.myapp.enums.function.SpecFuncEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    private static final Logger log = LoggerFactory.getLogger("operationLog");

    public static LogBean newLogBean(String mainFunc) {
        return new LogBean(mainFunc);
    }

    public static LogBean newLogBean(String mainFunc, String specFunc) {
        return new LogBean(mainFunc, specFunc);
    }

    public static LogBean newLogBean(MainFuncEnum mainFuncEnum, SpecFuncEnum specFuncEnum) {
        return newLogBean(mainFuncEnum.getMsg(),specFuncEnum.getMsg());
    }

    public static LogBean newLogBean(MainFuncEnum mainFuncEnum, SpecFuncEnum specFuncEnum, String msg) {
        return new LogBean(mainFuncEnum, specFuncEnum, msg);
    }

    public static void debug(LogBean logBean) {
        log.debug(logBean.toString());
    }

    public static void info(LogBean logBean) {
        log.info(logBean.toString());
    }

    public static void warn(LogBean logBean) {
        log.warn(logBean.toString());
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
