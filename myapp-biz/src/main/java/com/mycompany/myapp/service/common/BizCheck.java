package com.mycompany.myapp.service.common;

import com.mycompany.myapp.enums.msg.BaseMsgEnum;
import com.mycompany.myapp.enums.msg.CommonMsgEnum;

public class BizCheck {

    public static void checkParam(boolean expression, String msg) {
        if (!expression) {
            throw new BizException(CommonMsgEnum.PARAM_ERROR.getCode(),msg);
        }
    }

    public static void checkParam(boolean expression, BaseMsgEnum msgEnum) {
        if (!expression) {
            throw new BizException(msgEnum.getCode(), msgEnum.getMsg());
        }
    }

    public static void checkParam(boolean expression, BaseMsgEnum msgEnum, Object... args) {
        if (!expression) {
            throw new BizException(msgEnum.getCode(), String.format(msgEnum.getMsg(), args));
        }
    }
}
