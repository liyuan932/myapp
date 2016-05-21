package com.mycompany.myapp.dao;

import com.mycompany.myapp.daoobject.User;

import org.apache.ibatis.annotations.Param;

public interface UserDAO extends BaseDAO<User> {

  User getByAccountAndPassword(@Param("account") String account, @Param("password") String password);
}