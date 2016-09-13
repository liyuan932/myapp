package com.mycompany.myapp.enums.category;

/**
 * 日志存储位置
 *
 * @author wb-liyuan.j
 * @date 2016-09-13
 */
public enum LogLocationEnum {
    DB(1,"数据库"),FILE(2,"文件");

    private Integer index;
    private String text;

    LogLocationEnum(Integer index, String text) {
        this.index = index;
        this.text = text;
    }

    public Integer getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }
}
