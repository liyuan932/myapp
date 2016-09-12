package com.mycompany.myapp.utils.log;

/**
 * @author wb-liyuan.j
 * @date 2016-09-13
 */
public enum LogTypeEnum {

    AFTER(1,"后置"),AROUND(2,"环绕");
    private int index;
    private String text;

    LogTypeEnum(int index, String text) {
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
