package com.mycompany.myapp.enums.category;

/**
 * 日志存储位置
 *
 * @author wb-liyuan.j
 * @date 2016-09-13
 */
public enum LogLocationEnum {
    DB(1),FILE(2);

    private int index;

    LogLocationEnum(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
