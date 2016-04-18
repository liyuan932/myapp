package com.mycompany.myapp.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.base.Preconditions;
import com.mycompany.myapp.base.BaseResult;
import com.mycompany.myapp.base.BaseService;
import com.mycompany.myapp.common.enums.common.EnableOrDisableEnum;
import com.mycompany.myapp.common.enums.UserMsgEnum;
import com.mycompany.myapp.common.enums.UserTypeEnum;
import com.mycompany.myapp.common.exception.BizException;
import com.mycompany.myapp.common.utils.CopyUtils;
import com.mycompany.myapp.common.utils.CopyUtils.Callback;
import com.mycompany.myapp.dao.UserDao;
import com.mycompany.myapp.domain.UserDO;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.vo.LoginVO;
import com.mycompany.myapp.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	@Resource
	private UserDao userDao;

	public BaseResult<Long> addUser(UserDO db) {
		userDao.insert(db);
		return success(db.getId());
	}

	public BaseResult<Integer> addUser(List<UserDO> uList) {
		return success(userDao.batchInsert(uList));
	}

	public BaseResult<Integer> updateUser(UserDO db) {
		return success(userDao.update(db));
	}

	public BaseResult<Integer> updateUser(List<Long> idList) {
		return success(userDao.batchUpdate(idList));
	}

	public BaseResult<Integer> removeUser(Long id) {
		return success(userDao.delete(id));
	}

	public BaseResult<Integer> removeUser(List<Long> idList) {
		return success(userDao.batchDelete(idList));
	}

	public BaseResult<UserDO> getUserById(Long id) {
		return success(userDao.getById(id));
	}

	public BaseResult<List<UserVO>> getUserList(UserQuery query) throws Exception {

		List<UserDO> dbList = userDao.queryList(query);

		List<UserVO> voList = CopyUtils.dbToVo(dbList, UserVO.class, new Callback<UserDO, UserVO>() {

			@Override
			public void execute(UserDO db, UserVO vo) {
				vo.setStatusText(EnableOrDisableEnum.getTextByIndex(db.getStatus())); // 状态
				vo.setTypeText(UserTypeEnum.getTextByIndex(db.getType())); // 用户类型
			}
		});

		return success(voList);
	}

	public BaseResult<Integer> getUserCount(UserQuery query) {
		return success(userDao.count(query));
	}

	public BaseResult<PageList<UserVO>> getUserList(UserQuery query, PageBounds pb) throws Exception {

		PageList<UserDO> dbRes = userDao.queryPage(query, pb);

		PageList<UserVO> voRes = CopyUtils.dbToVo(dbRes, UserVO.class, new Callback<UserDO, UserVO>() {

			@Override
			public void execute(UserDO db, UserVO vo) {
				vo.setStatusText(EnableOrDisableEnum.getTextByIndex(db.getStatus())); // 状态
				vo.setTypeText(UserTypeEnum.getTextByIndex(db.getType())); // 用户类型
			}
		});

		return success(voRes);
	}

	public BaseResult<Integer> updateStatus(Long id, EnableOrDisableEnum status) {
		return success(userDao.updateStatus(id, status.getIndex()));
	}

	public BaseResult<Integer> updateStatus(List<Long> idList, EnableOrDisableEnum status) {
		return success(userDao.batchUpdateStatus(idList, status.getIndex()));
	}

	public BaseResult<LoginVO> login(String account, String password) {

		Preconditions.checkArgument(StringUtils.isNotEmpty(account.trim()), "账户不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(account.trim()), "密码不能为空");

		UserDO db = checkAccountPassword(account, password);

		return success(CopyUtils.dbToVo(db, LoginVO.class));
	}

	private UserDO checkAccountPassword(String account, String password) {

		UserDO db = userDao.checkAccountPassword(account, password);

		if (db == null) {
			throw new BizException(UserMsgEnum.FAIL_BIZ_LOGIN_ERROR);
		}
		return db;
	}

}
