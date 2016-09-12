package com.mycompany.myapp.api;

import com.mycompany.mapp.result.BaseResult;
import com.mycompany.myapp.enums.msg.BaseMsgEnum;

/**
 * @author wb-liyuan.j
 * @date 2016-09-12
 */
public class CommonService {

    public BaseResult success() {
        return new BaseResult();
    }

    public <T> BaseResult<T> success(T model) {
        return new BaseResult<>(model);
    }

    public <T> BaseResult<T> fail(String errorCode, String errorMsg) {
        return new BaseResult<>(errorCode, errorMsg);
    }

    public <T> BaseResult<T> fail(BaseMsgEnum statusEnum, Object... args) {
        return fail(statusEnum.getCode(), String.format(statusEnum.getMsg(), args));
    }
}
