package com.mycompany.myapp.dao;

import org.apache.ibatis.annotations.Param;

import com.mycompany.myapp.base.BaseDao;
import com.mycompany.myapp.domain.UserDO;

public interface UserDao extends BaseDao<UserDO, Long> {
	
	UserDO checkAccountPassword(@Param("account")String account,@Param("password")String password);
}