package com.mycompany.mapp.api;

import com.mycompany.mapp.dto.LoginDTO;
import com.mycompany.mapp.result.BaseResult;
import com.mycompany.mapp.result.LoginResult;

/**
 * @author wb-liyuan.j
 * @date 2016-09-12
 */
public interface UserAPI {

    BaseResult<LoginResult> login(LoginDTO dto);
}
