package com.mycompany.myapp.service;

import com.mycompany.myapp.daoobject.UserDO;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.vo.UserVO;

import java.util.List;

public interface UserService{

	List<UserVO> queryUser(UserQuery query);

	UserDO getUserById(Long id);		

	 void addUser(UserDO user);
}
