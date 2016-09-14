package com.mycompany.myapp.enums.function;

/**
 * 次要的功能枚举
 *
 * @author Administrator
 * @date 2016-05-07
 */
public enum ModuleEnum {
    DEFAULT(0,"默认"),//
    USER(1,"用户模块")//
    ;
    private Integer index;
    private String text;
    ModuleEnum(Integer index, String text) {
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
        for (ModuleEnum e : ModuleEnum.values()) {
            if (e.getIndex().equals(index)) {
                return e.getText();
            }
        }
        return null;
    }
}

