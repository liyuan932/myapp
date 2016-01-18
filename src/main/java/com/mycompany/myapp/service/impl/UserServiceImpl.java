package com.mycompany.myapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.base.Preconditions;
import com.mycompany.myapp.base.BaseService;
import com.mycompany.myapp.base.Result;
import com.mycompany.myapp.common.enums.StatusEnum;
import com.mycompany.myapp.common.enums.UserExceptionEnum;
import com.mycompany.myapp.common.enums.UserTypeEnum;
import com.mycompany.myapp.common.exception.ServiceException;
import com.mycompany.myapp.common.utils.CopyUtils;
import com.mycompany.myapp.common.utils.CopyUtils.Callback;
import com.mycompany.myapp.dao.UserDao;
import com.mycompany.myapp.domain.UserDO;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.vo.LoginVO;
import com.mycompany.myapp.vo.UserVO;

@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	@Resource
	private UserDao userDao;

	public Result<Long> addUser(UserDO db) {
		userDao.insert(db);
		return success(db.getId());
	}

	public Result<Integer> addUser(List<UserDO> uList) {
		return success(userDao.batchInsert(uList));
	}

	public Result<Integer> updateUser(UserDO db) {
		return success(userDao.update(db));
	}

	public Result<Integer> updateUser(List<Long> idList) {
		return success(userDao.batchUpdate(idList));
	}

	public Result<Integer> removeUser(Long id) {
		return success(userDao.deleteById(id));
	}

	public Result<Integer> removeUser(List<Long> idList) {
		return success(userDao.batchDelete(idList));
	}

	public Result<UserDO> getUserById(Long id) {
		return success(userDao.selectById(id));
	}

	public Result<List<UserVO>> getUserList(UserQuery query) throws Exception {

		List<UserDO> dbList = userDao.selectList(query);

		List<UserVO> voList = CopyUtils.dbToVo(dbList, UserVO.class, new Callback<UserDO, UserVO>() {

			@Override
			public void execute(UserDO db, UserVO vo) {
				vo.setStatusText(StatusEnum.getTextByIndex(db.getStatus())); // 状态
				vo.setTypeText(UserTypeEnum.getTextByIndex(db.getType())); // 用户类型
			}
		});

		return success(voList);
	}

	public Result<Integer> getUserCount(UserQuery query) {
		return success(userDao.count(query));
	}

	public Result<PageList<UserVO>> getUserList(UserQuery query, PageBounds pb) throws Exception {

		PageList<UserDO> dbRes = userDao.pageList(query, pb);

		PageList<UserVO> voRes = CopyUtils.dbToVo(dbRes, UserVO.class, new Callback<UserDO, UserVO>() {

			@Override
			public void execute(UserDO db, UserVO vo) {
				vo.setStatusText(StatusEnum.getTextByIndex(db.getStatus())); // 状态
				vo.setTypeText(UserTypeEnum.getTextByIndex(db.getType())); // 用户类型
			}
		});

		return success(voRes);
	}

	public Result<Integer> updateStatus(Long id, StatusEnum status) {
		return success(userDao.updateStatus(id, status.getIndex()));
	}

	public Result<Integer> updateStatus(List<Long> idList, StatusEnum status) {
		return success(userDao.batchUpdateStatus(idList, status.getIndex()));
	}

	public Result<LoginVO> login(String account, String password) {

		Preconditions.checkArgument(StringUtils.isNotEmpty(account.trim()), "账户不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(account.trim()), "密码不能为空");

		UserDO db = checkAccountPassword(account, password);

		return success(CopyUtils.dbToVo(db, LoginVO.class));
	}

	private UserDO checkAccountPassword(String account, String password) {

		UserDO db = userDao.checkAccountPassword(account, password);

		if (db == null) {
			throw new ServiceException(UserExceptionEnum.FAIL_BIZ_LOGIN_ERROR);
		}
		return db;
	}

}
