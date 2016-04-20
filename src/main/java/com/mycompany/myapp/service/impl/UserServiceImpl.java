package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.dao.UserDAO;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.common.BaseService;
import com.mycompany.myapp.service.common.Result;
import com.mycompany.myapp.web.vo.LoginVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	@Resource
	private UserDAO userDao;


    @Override
    public Result<LoginVO> login(String account, String password) {
        return null;
    }
}
