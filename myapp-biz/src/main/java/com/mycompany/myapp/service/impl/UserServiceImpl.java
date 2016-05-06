package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.dao.UserDAO;
import com.mycompany.myapp.daoobject.User;
import com.mycompany.myapp.enums.category.StatusEnum;
import com.mycompany.myapp.enums.category.UserTypeEnum;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.common.BaseService;
import com.mycompany.myapp.utils.BeanUtil;
import com.mycompany.myapp.utils.DateUtil;
import com.mycompany.myapp.vo.UserVO;

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
    userDAO.insert(user);
  }
}
