package com.mycompany.myapp.enums.category;

/**
 * 状态枚举
 */
public enum EnableOrDisableEnum {
    ENABLE(1, "启用"), DISABLE(0, "禁用");

    private Integer index;
    private String text;

    EnableOrDisableEnum(Integer index, String text) {
        this.index = index;
        this.text = text;
    }

    public Integer getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }

    /**
     * 根据索引获取状态文本
     */
    public static String getTextByIndex(Integer index) {
        for (EnableOrDisableEnum e : EnableOrDisableEnum.values()) {
            if (e.getIndex().equals(index)) {
                return e.getText();
            }
        }
        return null;
    }

}
