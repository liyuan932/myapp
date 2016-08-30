package com.mycompany.myapp.service;

import com.mycompany.myapp.daoobject.User;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.vo.UserVO;

import java.util.List;

public interface UserService {

  List<UserVO> queryList(UserQuery query);

  User getById(Long id);

  boolean add(User user);

  boolean update(User user);

  User login(String username,String password);
}
