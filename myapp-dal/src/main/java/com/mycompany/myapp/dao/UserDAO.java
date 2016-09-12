package com.mycompany.myapp.dao;

import com.mycompany.myapp.base.BaseDAO;
import com.mycompany.myapp.daoobject.User;
import org.apache.ibatis.annotations.Param;

public interface UserDAO extends BaseDAO<User> {

    User getByAccount(String account);

    User getByAccountAndPassword(@Param("account") String account, @Param("password") String password);
}