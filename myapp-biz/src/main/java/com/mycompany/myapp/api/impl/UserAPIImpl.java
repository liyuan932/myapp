package com.mycompany.myapp.api.impl;

import com.mycompany.mapp.api.UserAPI;
import com.mycompany.mapp.dto.LoginDTO;
import com.mycompany.mapp.result.BaseResult;
import com.mycompany.mapp.result.LoginResult;
import com.mycompany.myapp.api.CommonService;
import com.mycompany.myapp.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.common.BizException;
import com.mycompany.myapp.utils.BeanUtil;

import javax.annotation.Resource;

/**
 * 用户API接口
 *
 * @author wb-liyuan.j
 * @date 2016-09-12
 */
public class UserAPIImpl extends CommonService implements UserAPI {

    @Resource
    private UserService userService;

    @Override
    public BaseResult<LoginResult> login(LoginDTO dto) {

        try {
            return success(BeanUtil.dbToVo(userService.login(dto.getUsername(), dto.getPassword()), LoginResult.class));
        } catch (BizException bex) {
            return fail(bex.getCode(), bex.getMsg());
        } catch (Exception ex) {
            return fail(CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR);
        }
    }
}
