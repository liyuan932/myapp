package com.mycompany.myapp.service;

import com.mycompany.myapp.daoobject.User;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.vo.UserVO;

import java.util.List;

public interface UserService {

  List<UserVO> queryUser(UserQuery query);

  User getUserById(Long id);

  void addUser(User user);
}
