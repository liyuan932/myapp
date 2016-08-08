package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.dao.UserDAO;
import com.mycompany.myapp.daoobject.User;
import com.mycompany.myapp.enums.category.EnableOrDisableEnum;
import com.mycompany.myapp.enums.category.UserTypeEnum;
import com.mycompany.myapp.enums.msg.MainFunctionEnum;
import com.mycompany.myapp.enums.msg.UserFunctionEnum;
import com.mycompany.myapp.enums.msg.UserMsgEnum;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.common.BaseService;
import com.mycompany.myapp.service.common.ServicePreconditions;
import com.mycompany.myapp.utils.BeanUtil;
import com.mycompany.myapp.utils.DateUtil;
import com.mycompany.myapp.utils.log.LogBean;
import com.mycompany.myapp.utils.log.LogUtils;
import com.mycompany.myapp.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private UserDAO userDAO;

	@Override
	public List<UserVO> queryList(UserQuery query) {

		List<User> users = userDAO.queryList(query);

		return BeanUtil.dbToVo(users, UserVO.class, new BeanUtil.Callback<User, UserVO>() {
			@Override
			public void execute(User db, UserVO vo) {
				vo.setStatusText(EnableOrDisableEnum.getTextByIndex(db.getStatus()));
				vo.setTypeText(UserTypeEnum.getTextByIndex(db.getType()));
				vo.setGmtCreateText(DateUtil.parseDate2Str(db.getGmtCreate()));
			}
		});
	}

	@Override
	@Cacheable(value="userCache")
	public User getById(Long id) {
		System.out.println("real querying account... {}");

		return userDAO.getById(id);
	}

	@Override
	public boolean add(User user) {
		LogBean logBean = LogUtils.newLogBean(MainFunctionEnum.USER_ADMIN, UserFunctionEnum.ADD_USER);
		logBean.addParameters("user", user);

		userDAO.insert(user);
		ServicePreconditions.checkArgument(user.getId() != null, UserMsgEnum.FAIL_BIZ_ADD_USER);

		return true;
	}

	@Override
	@CacheEvict(value="userCache",key="#user.getId()")
	public boolean update(User user) {

		userDAO.update(user);

		return true;
	}

	@Override
	public User login(String account, String password) {
		LogBean logBean = LogUtils.newLogBean(MainFunctionEnum.USER_ADMIN, UserFunctionEnum.LOGIN);
		logBean.addParameters("account", account, "password", password);

		ServicePreconditions.checkArgument(StringUtils.isNotBlank(account), UserMsgEnum.FAIL_BIZ_ACCOUNT_IS_NULL);
		ServicePreconditions.checkArgument(StringUtils.isNotBlank(password), UserMsgEnum.FAIL_BIZ_PASSWORD_IS_NULL);

		User user = userDAO.getByAccountAndPassword(account, password);
		ServicePreconditions.checkArgument(user != null, UserMsgEnum.FAIL_BIZ_USER_NOT_EXIST, account);

		return user;
	}
}
