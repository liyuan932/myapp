package com.mycompany.myapp.utils.log;


import org.apache.commons.lang3.StringUtils;

import com.mycompany.myapp.enums.msg.MainFunctionEnum;
import com.mycompany.myapp.enums.msg.SecondaryFunctionEnum;
import com.mycompany.myapp.enums.msg.UserFunctionEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

  private static final Logger log = LoggerFactory.getLogger("operationLog");

  public static LogBean newLogBean(MainFunctionEnum mainFunctionEnum, SecondaryFunctionEnum secondaryFunctionEnum) {
    LogBean logBean = new LogBean(mainFunctionEnum, secondaryFunctionEnum);
    return logBean;
  }

  public static LogBean newLogBean(MainFunctionEnum mainFunctionEnum, SecondaryFunctionEnum secondaryFunctionEnum, String msg) {
    LogBean logBean = new LogBean(mainFunctionEnum, secondaryFunctionEnum, msg);
    return logBean;
  }

  public static void debug(LogBean logBean) {
    log.info(logBean.toString());
  }

  public static void debug(LogBean logBean, Exception ex) {
    processException(logBean, ex);
    log.info(logBean.toString());
  }

  public static void info(LogBean logBean) {
    log.info(logBean.toString());
  }

  public static void info(LogBean logBean, Exception ex) {
    processException(logBean, ex);
    log.info(logBean.toString());
  }

  public static void warn(LogBean logBean) {
    log.info(logBean.toString());
  }

  public static void warn(LogBean logBean, Exception ex) {
    processException(logBean, ex);
    log.info(logBean.toString());
  }

  public static void error(LogBean logBean) {
    log.info(logBean.toString());
  }

  public static void error(LogBean logBean, Exception ex) {
    processException(logBean, ex);
    log.info(logBean.toString());
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

  public static void main(String[] args) {
    System.out.println(new LogBean(MainFunctionEnum.USER_ADMIN, UserFunctionEnum.ADD_USER).addParameters("aa", "aa")
        .toString());
  }
}
