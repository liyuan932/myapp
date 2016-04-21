package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.daoobject.UserDO;
import com.mycompany.myapp.dao.UserDAO;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.common.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	@Resource
	private UserDAO userDao;

    @Override
    public UserDO getUserById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public void addUser(UserDO user) {
        userDao.insert(user);
    }
}
