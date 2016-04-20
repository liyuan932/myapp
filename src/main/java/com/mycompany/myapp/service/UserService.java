package com.mycompany.myapp.service;

import com.mycompany.myapp.service.common.Result;
import com.mycompany.myapp.web.vo.LoginVO;

public interface UserService{
	 Result<LoginVO> login(String account,String password);
}
