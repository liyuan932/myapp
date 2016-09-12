package com.mycompany.myapp.utils.log;

/**
 * @author wb-liyuan.j
 * @date 2016-09-13
 */
public enum LogLevelEnum {

    DEBUG(0,"调试"),INFO(1,"信息"),WARN(2,"警告"),ERROR(3,"错误");
    private int index;
    private String text;

    LogLevelEnum(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }
}
