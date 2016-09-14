package com.mycompany.myapp.service;

import com.mycompany.mapp.dto.LoginDTO;
import com.mycompany.myapp.daoobject.UserDO;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.vo.UserVO;

import java.util.List;

public interface UserService {

    List<UserVO> queryList(UserQuery query);

    UserDO getById(Long id);

    UserDO add(UserDO userDO);

    UserDO update(UserDO userDO);

    UserDO login(String username, String password);

    void login(LoginDTO dto);

    void logout(String account);
}
