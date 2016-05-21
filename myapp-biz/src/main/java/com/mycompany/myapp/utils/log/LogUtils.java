package com.mycompany.myapp.utils.log;


import com.mycompany.myapp.enums.msg.MainFunctionEnum;
import com.mycompany.myapp.enums.msg.SecondaryFunctionEnum;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;

public class LogUtils {

  private static final Logger log = LoggerFactory.getLogger("operationLog");

  private static NamedThreadLocal<LogBean> threadLocal = new NamedThreadLocal<>("LogBean");

  public static LogBean newLogBean(MainFunctionEnum mainFunctionEnum, SecondaryFunctionEnum secondaryFunctionEnum) {
    LogBean logBean = new LogBean(mainFunctionEnum, secondaryFunctionEnum);

    if(threadLocal.get() == null){
      threadLocal.set(logBean);
    }

    return logBean;
  }

  public static LogBean newLogBean(MainFunctionEnum mainFunctionEnum, SecondaryFunctionEnum secondaryFunctionEnum, String msg) {
    LogBean logBean = new LogBean(mainFunctionEnum, secondaryFunctionEnum, msg);
    return logBean;
  }

  public static LogBean getLogBean(){
    return threadLocal.get();
  }


  public static void debug() {
    log.debug(getLogBean().toString());
  }

  public static void debug(Exception ex) {
    processException(getLogBean(), ex);
    log.debug(getLogBean().toString());
  }

  public static void info() {
    log.info(getLogBean().toString());
  }

  public static void info(Exception ex) {
    processException(getLogBean(), ex);
    log.info(getLogBean().toString());
  }

  public static void warn() {
    log.warn(getLogBean().toString());
  }

  public static void warn(Exception ex) {
    processException(getLogBean(), ex);
    log.warn(getLogBean().toString());
  }

  public static void error() {
    log.error(getLogBean().toString());
  }

  public static void error(Exception ex) {
    processException(getLogBean(), ex);
    log.error(getLogBean().toString());
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
