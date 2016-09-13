package com.mycompany.myapp.utils.log;

import com.alibaba.fastjson.JSON;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.mycompany.myapp.enums.function.ActionEnum;
import com.mycompany.myapp.enums.function.ModuleEnum;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class LogBean {

    /**
     * 主流程功能点
     */
    private String module;

    /**
     * 具体功能点
     */
    private String action;

    /**
     * 参数数据，保持输入与输出顺序一致
     */
    private Map<String, Object> paramData = new LinkedHashMap<>();

    /**
     * 结果数据
     */
    private Map<String, Object> resultData = new LinkedHashMap<>();

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

    /**
     * 花费时间
     */
    private String cost;

    public Map<String, Object> getResultData() {
        return resultData;
    }

    public void setResultData(Map<String, Object> resultData) {
        this.resultData = resultData;
    }

    public LogBean() {

    }

    public LogBean(String module) {
        this.module = module;
    }

    public LogBean(String module, String action) {
        this.module = module;
        this.action = action;
    }

    public LogBean(ModuleEnum module, ActionEnum action) {
        this.module = module.name();
        this.action = action.name();
    }

    public LogBean(ModuleEnum module, ActionEnum action, String msg) {
        this(module, action);
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Object> getParamData() {
        return paramData;
    }

    public void setParamData(Map<String, Object> paramData) {
        this.paramData = paramData;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    /**
     * 添加参数数据
     *
     * @param pairs 必须以key-value对出现，key为String类型
     */
    public LogBean addParamData(Object... pairs) {
        this.paramData.putAll(parseArray(pairs));
        return this;
    }
    /**
     * 添加结果数据
     *
     * @param pairs 必须以key-value对出现，key为String类型
     */
    public LogBean addResultData(Object... pairs) {
        this.paramData.putAll(parseArray(pairs));
        return this;
    }


    private Map<String, Object> parseArray(Object... pairs) {

        Preconditions.checkArgument(ArrayUtils.isNotEmpty(pairs));
        Preconditions.checkArgument(pairs.length % 2 == 0);

        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i = i + 2) {
            String key = Objects.toString(Preconditions.checkNotNull(pairs[i]));
            Object value = MoreObjects.firstNonNull(pairs[i + 1], "");
            map.put(key, value);
        }
        return map;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.trim(this.module));
        sb.append("->");
        sb.append(StringUtils.trim(this.action));
        sb.append("->");
        sb.append(StringUtils.trim(this.msg));
        sb.append("->");
        sb.append(StringUtils.trim(JSON.toJSONString(this.paramData)));
        sb.append("->");
        sb.append(StringUtils.trim(JSON.toJSONString(this.resultData)));
        sb.append("->");
        sb.append(StringUtils.trim(this.bid));
        sb.append("->");
        sb.append(StringUtils.trim(this.cost));
        sb.append("->");
        sb.append(this.stackTrace);
        return sb.toString();
    }

}