package com.mycompany.myapp.utils.log;

import com.mycompany.myapp.enums.msg.MainFunctionEnum;
import com.mycompany.myapp.enums.msg.SecondaryFunctionEnum;

import com.alibaba.fastjson.JSON;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class LogBean {
  /**
   * 主流程功能点
   */
  private String mainFunction;

  /**
   * 具体功能点
   */
  private String secondaryFunction;

  /**
   * 参数 键值对
   */
  private Map<String, Object> parameters = new LinkedHashMap<>();

  /**
   * 日志描述
   */
  private String msg;

  /**
   * 业务标识
   */
  private String bid;

  /**
   * 异常堆栈
   */
  private String stackTrace;

  public LogBean() {

  }

  /**
   * 构造方法
   *
   * @param mainFunctionEnum      主要功能
   * @param secondaryFunctionEnum 次级功能
   */
  public LogBean(MainFunctionEnum mainFunctionEnum, SecondaryFunctionEnum secondaryFunctionEnum) {
    this.mainFunction = mainFunctionEnum.getMsg();
    this.secondaryFunction = secondaryFunctionEnum.getMsg();
  }


  /**
   * 构造方法
   *
   * @param mainFunctionEnum      主要功能
   * @param secondaryFunctionEnum 次级功能
   * @param msg                   错误信息
   */
  public LogBean(MainFunctionEnum mainFunctionEnum, SecondaryFunctionEnum secondaryFunctionEnum, String msg) {
    this(mainFunctionEnum, secondaryFunctionEnum);
    this.msg = msg;
  }

  public String getBid() {
    return bid;
  }

  public void setBid(String bid) {
    this.bid = bid;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }

  public String getMainFunction() {
    return mainFunction;
  }

  public void setMainFunction(String mainFunction) {
    this.mainFunction = mainFunction;
  }

  public String getSecondaryFunction() {
    return secondaryFunction;
  }

  public void setSecondaryFunction(String secondaryFunction) {
    this.secondaryFunction = secondaryFunction;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  /**
   * 添加参数
   *
   * @param pairs 必须以key-value对出现，key为String类型
   */
  public LogBean addParameters(Object... pairs) {

    Preconditions.checkArgument(ArrayUtils.isNotEmpty(pairs));
    Preconditions.checkArgument(pairs.length % 2 == 0);

    Map<String, Object> map = new LinkedHashMap<>();
    for (int i = 0; i < pairs.length; i = i + 2) {
      String key = Objects.toString(Preconditions.checkNotNull(pairs[i]));
      Object value = MoreObjects.firstNonNull(pairs[i + 1], "");
      map.put(key, value);
    }

    this.parameters.putAll(map);
    return this;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(StringUtils.trim(this.mainFunction));
    sb.append("->");
    sb.append(StringUtils.trim(this.secondaryFunction));
    sb.append("->");
    sb.append(StringUtils.trim(this.bid));
    sb.append("->");
    sb.append(StringUtils.trim(JSON.toJSONString(this.parameters)));
    sb.append("->");
    sb.append(StringUtils.trim(this.msg));
    sb.append("->");
    sb.append(this.stackTrace);
    return sb.toString();
  }
}