package com.mycompany.myapp.dao;

import com.mycompany.myapp.base.BaseDAO;
import com.mycompany.myapp.daoobject.UserDO;
import org.apache.ibatis.annotations.Param;

public interface UserDAO extends BaseDAO<UserDO> {

    UserDO getByAccount(String account);

    UserDO getByAccountAndPassword(@Param("account") String account, @Param("password") String password);
}