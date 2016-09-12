package com.mycompany.myapp.enums.function;

/**
 * 主功能枚举
 *
 * @author Administrator
 * @date 2016-05-07
 */
public enum MainFuncEnum implements FunctionEnum {

    USER_ADMIN("user admin");

    private String msg;

    MainFuncEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
