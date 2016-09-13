package com.mycompany.myapp.enums.category;

/**
 * @author wb-liyuan.j
 * @date 2016-09-13
 */
public enum LogLevelEnum {
    DEBUG(1,"调试"),INFO(2,"信息"),WARN(3,"警告"),ERROR(4,"错误");

    private Integer index;
    private String text;

    LogLevelEnum(Integer index, String text) {
        this.index = index;
        this.text = text;
    }

    public Integer getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }
    public static String getTextByIndex(Integer index) {
        for (LogLevelEnum e : LogLevelEnum.values()) {
            if (e.getIndex().equals(index)) {
                return e.getText();
            }
        }
        return null;
    }

    public static LogLevelEnum getByIndex(Integer index) {
        for (LogLevelEnum e : LogLevelEnum.values()) {
            if (e.getIndex().equals(index)) {
                return e;
            }
        }
        return null;
    }

}
