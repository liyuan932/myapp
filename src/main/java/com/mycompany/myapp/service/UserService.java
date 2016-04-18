package com.mycompany.myapp.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.mycompany.myapp.base.BaseResult;
import com.mycompany.myapp.common.enums.common.EnableOrDisableEnum;
import com.mycompany.myapp.domain.UserDO;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.vo.LoginVO;
import com.mycompany.myapp.vo.UserVO;

import java.util.List;

public interface UserService{
	
	BaseResult<Long> addUser(UserDO db) throws Exception;
	BaseResult<Integer> addUser(List<UserDO> uList);

	BaseResult<Integer> updateUser(UserDO db);
	BaseResult<Integer> updateUser(List<Long> idList);
	
	
	BaseResult<Integer> removeUser(Long id);
	BaseResult<Integer> removeUser(List<Long> idList);
	
	BaseResult<UserDO> getUserById(Long id);
	
	BaseResult<List<UserVO>> getUserList(UserQuery query) throws Exception;
	BaseResult<Integer> getUserCount(UserQuery query);
	
	BaseResult<PageList<UserVO>> getUserList(UserQuery query, PageBounds pb) throws Exception;
	 
	BaseResult<Integer> updateStatus(Long id,EnableOrDisableEnum status);
	BaseResult<Integer> updateStatus(List<Long> idList,EnableOrDisableEnum status);
	
	BaseResult<LoginVO> login(String account,String password);
}
