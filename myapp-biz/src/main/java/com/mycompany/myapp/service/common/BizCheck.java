package com.mycompany.myapp.service.common;

import com.mycompany.myapp.enums.msg.BaseMsgEnum;

public class BizCheck {

    public static void checkArgument(boolean expression, BaseMsgEnum msgEnum) {
        if (!expression) {
            throw new BizException(msgEnum.getCode(), msgEnum.getMsg());
        }
    }

    public static void checkArgument(boolean expression, BaseMsgEnum msgEnum, Object... args) {
        if (!expression) {
            throw new BizException(msgEnum.getCode(), String.format(msgEnum.getMsg(), args));
        }
    }
}
