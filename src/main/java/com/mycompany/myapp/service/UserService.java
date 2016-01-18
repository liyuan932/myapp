package com.mycompany.myapp.service;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.mycompany.myapp.base.Result;
import com.mycompany.myapp.common.enums.StatusEnum;
import com.mycompany.myapp.domain.UserDO;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.vo.LoginVO;
import com.mycompany.myapp.vo.UserVO;

public interface UserService{
	
	Result<Long> addUser(UserDO db) throws Exception;
	Result<Integer> addUser(List<UserDO> uList);

	Result<Integer> updateUser(UserDO db);
	Result<Integer> updateUser(List<Long> idList);
	
	
	Result<Integer> removeUser(Long id);
	Result<Integer> removeUser(List<Long> idList);
	
	Result<UserDO> getUserById(Long id);
	
	Result<List<UserVO>> getUserList(UserQuery query) throws Exception;
	Result<Integer> getUserCount(UserQuery query);
	
	Result<PageList<UserVO>> getUserList(UserQuery query, PageBounds pb) throws Exception;
	 
	Result<Integer> updateStatus(Long id,StatusEnum status);
	Result<Integer> updateStatus(List<Long> idList,StatusEnum status);
	
	Result<LoginVO> login(String account,String password);
}
