package com.mycompany.myapp.service;

import com.mycompany.myapp.daoobject.UserDO;

public interface UserService{

	UserDO getUserById(Long id);

	 void addUser(UserDO user);
}
