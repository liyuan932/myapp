package com.mycompany.myapp.utils.log;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    private static final Logger log = LoggerFactory.getLogger("operationLog");

    public static LogBean newLogBean(String mainFunction, String specificFunction) {
        LogBean logBean = new LogBean(mainFunction, specificFunction);
        return logBean;
    }

    public static LogBean newLogBean(String mainFunction, String specificFunction, String msg) {
        LogBean logBean = new LogBean(mainFunction, specificFunction, msg);
        return logBean;
    }

    public static void debug(LogBean logBean){
        log.info(logBean.toString());
    }
    public static void debug(LogBean logBean, Exception e){
        processException(logBean,e);
        log.info(logBean.toString());
    }

    public static void info(LogBean logBean){
        log.info(logBean.toString());
    }
    public static void info(LogBean logBean, Exception e){
        processException(logBean,e);
        log.info(logBean.toString());
    }

    public static void warn(LogBean logBean){
        log.info(logBean.toString());
    }
    public static void warn(LogBean logBean, Exception e){
        processException(logBean,e);
        log.info(logBean.toString());
    }

    public static void error(LogBean logBean){
        log.info(logBean.toString());
    }
    public static void error(LogBean logBean, Exception e){
        processException(logBean,e);
        log.info(logBean.toString());
    }

    private static void processException(LogBean logBean, Exception e) {
        if (e != null) {
            if (StringUtils.isBlank(logBean.getMsg())){
                logBean.setMsg(e.getMessage());
            }

            StringBuilder trackTrace = new StringBuilder();
            StackTraceElement[] trace = e.getStackTrace();
            for (int i = 0; i < trace.length; i++) {
                StackTraceElement traceElement = trace[i];
                trackTrace.append(traceElement.toString()).append("@&");
            }
            logBean.setStackTrace(trackTrace.toString());
        }
    }
}
