package com.mycompany.myapp.service;

import com.mycompany.myapp.daoobject.User;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.vo.UserVO;

import java.util.List;

public interface UserService {

    List<UserVO> queryList(UserQuery query);

    User getById(Long id);

    User add(User user);

    User update(User user);

    User login(String username, String password);

    void logout(String account);
}
