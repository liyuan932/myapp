package com.mycompany.myapp.utils.log;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class LogBean {
    /**
     * 主流程功能点
     */
    private String mainFunction;

    /**
     * 具体功能点
     */
    private String specificFunction;

    /**
     * 参数 键值对
     */
    private Map<String, Object> parameters = Maps.newHashMap();

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

    public LogBean(){
    }

    public LogBean(String mainFunction, String specificFunction) {
        this.mainFunction = mainFunction;
        this.specificFunction = specificFunction;
    }

    public LogBean(String mainFunction, String specificFunction, String msg) {
        this.mainFunction = mainFunction;
        this.specificFunction = specificFunction;
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

    public String getSpecificFunction() {
        return specificFunction;
    }

    public void setSpecificFunction(String specificFunction) {
        this.specificFunction = specificFunction;
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

    public LogBean addParameters(String k1, Object v1) {
        ImmutableBiMap<String, Object> of = ImmutableBiMap.of(k1, v1 == null ? "" : v1);
        this.getParameters().putAll(of);
        return this;
    }

    public LogBean addParameters(String k1, Object v1, String k2, Object v2) {
        ImmutableMap<String, Object> of = ImmutableMap.of(k1, v1 == null ? "" : v1, k2, v2 == null ? "" : v2);
        this.getParameters().putAll(of);
        return this;
    }

    public LogBean addParameters(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        ImmutableMap<String, Object> of = ImmutableMap.of(k1, v1 == null ? "" : v1, k2, v2 == null ? "" : v2, k3, v3 == null ? "" : v3);
        this.getParameters().putAll(of);
        return this;
    }

    public LogBean addParameters(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        ImmutableMap<String, Object> of = ImmutableMap.of(k1, v1 == null ? "" : v1, k2, v2 == null ? "" : v2, k3, v3 == null ? "" : v3, k4, v4 == null ? ""
                : v4);
        this.getParameters().putAll(of);
        return this;
    }

    public LogBean addParameters(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5) {
        ImmutableMap<String, Object> of = ImmutableMap.of(k1, v1 == null ? "" : v1, k2, v2 == null ? "" : v2, k3, v3 == null ? "" : v3, k4, v4 == null ? ""
                : v4, k5, v5 == null ? "" : v5);
        this.getParameters().putAll(of);
        return this;
    }

    public LogBean addParameters(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5, String k6,
                                 Object v6) {

        ImmutableMap<String, Object> of2 = ImmutableMap.of(k6, v6 == null ? "" : v6);
        this.addParameters(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
        this.getParameters().putAll(of2);
        return this;
    }

    public LogBean addParameters(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5, String k6,
                                 Object v6, String k7, Object v7) {
        ImmutableMap<String, Object> of2 = ImmutableMap.of(k6, v6 == null ? "" : v6, k7, v7 == null ? "" : v7);
        this.addParameters(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
        this.getParameters().putAll(of2);
        return this;
    }

    public LogBean addParameters(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5, String k6,
                                 Object v6, String k7, Object v7, String k8, Object v8) {
        ImmutableMap<String, Object> of2 = ImmutableMap.of(k6, v6 == null ? "" : v6, k7, v7 == null ? "" : v7, k8, v8 == null ? "" : v8);
        this.addParameters(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
        this.getParameters().putAll(of2);
        return this;
    }

    public LogBean addParameters(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5, String k6,
                                 Object v6, String k7, Object v7, String k8, Object v8, String k9, Object v9) {
        ImmutableMap<String, Object> of2 = ImmutableMap.of(k6, v6 == null ? "" : v6, k7, v7 == null ? "" : v7, k8, v8 == null ? "" : v8, k9, v9 == null ? ""
                : v9);
        this.addParameters(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
        this.getParameters().putAll(of2);
        return this;
    }

    public LogBean addParameters(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5, String k6,
                                 Object v6, String k7, Object v7, String k8, Object v8, String k9, Object v9, String k10, Object v10) {
        ImmutableMap<String, Object> of2 = ImmutableMap.of(k6, v6 == null ? "" : v6, k7, v7 == null ? "" : v7, k8, v8 == null ? "" : v8, k9, v9 == null ? ""
                : v9, k10, v10 == null ? "" : v10);
        this.addParameters(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
        this.getParameters().putAll(of2);
        return this;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.trim(this.mainFunction));
        sb.append("->");
        sb.append(StringUtils.trim(this.specificFunction));
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