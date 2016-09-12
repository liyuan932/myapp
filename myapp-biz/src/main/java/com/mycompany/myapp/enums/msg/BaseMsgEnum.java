package com.mycompany.myapp.enums.msg;

/**
 * 所有错误信息枚举的超类
 */
public interface BaseMsgEnum {

    /**
     * 获取错误代码
     */
    String getCode();

    /**
     * 获取错误信息
     */
    String getMsg();
}
