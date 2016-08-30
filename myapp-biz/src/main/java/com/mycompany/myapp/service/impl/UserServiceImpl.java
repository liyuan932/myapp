package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.dao.UserDAO;
import com.mycompany.myapp.daoobject.User;
import com.mycompany.myapp.enums.category.StatusEnum;
import com.mycompany.myapp.enums.category.UserTypeEnum;
import com.mycompany.myapp.enums.function.MainFunctionEnum;
import com.mycompany.myapp.enums.function.UserFunctionEnum;
import com.mycompany.myapp.enums.msg.UserMsgEnum;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.common.BaseService;
import com.mycompany.myapp.service.common.ServicePreconditions;
import com.mycompany.myapp.utils.BeanUtil;
import com.mycompany.myapp.utils.DateUtil;
import com.mycompany.myapp.utils.log.LogUtils;
import com.mycompany.myapp.vo.UserVO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.annotation.Resource;

@Transactional
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

  @SuppressWarnings("SpringJavaAutowiringInspection")
  @Resource
  private UserDAO userDAO;

  @Override
  public List<UserVO> queryUser(UserQuery query) {

    List<User> users = userDAO.queryList(query);

    return BeanUtil.dbToVo(users, UserVO.class, new BeanUtil.Callback<User, UserVO>() {
      @Override
      public void execute(User db, UserVO vo) {
        vo.setStatusText(StatusEnum.getTextByIndex(db.getStatus()));
        vo.setTypeText(UserTypeEnum.getTextByIndex(db.getType()));
        vo.setGmtCreateText(DateUtil.parseDate2Str(db.getGmtCreate()));
      }
    });
  }

  @Override
  public User getUserById(Long id) {
    return userDAO.getById(id);
  }

  @Override
  public void addUser(User user) {
    LogUtils.newLogBean(MainFunctionEnum.USER_ADMIN, UserFunctionEnum.ADD_USER).addParameters("user", user).info();

    userDAO.insert(user);
    ServicePreconditions.checkArgument(user.getId() != null, UserMsgEnum.FAIL_BIZ_ADD_USER);
  }

  @Override
  public User login(String account, String password) {
    LogUtils.newLogBean(MainFunctionEnum.USER_ADMIN, UserFunctionEnum.LOGIN).addParameters(
        "account", account, "password", password).info();

    ServicePreconditions.checkArgument(StringUtils.isNotBlank(account));
    ServicePreconditions.checkArgument(StringUtils.isNotBlank(password));

    User user = userDAO.getByAccountAndPassword(account, password);
    ServicePreconditions.checkArgument(user != null,UserMsgEnum.FAIL_BIZ_NO_USER);

    return user;
  }
}
