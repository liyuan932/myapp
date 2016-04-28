package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.daoobject.UserDO;
import com.mycompany.myapp.dao.UserDAO;
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

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	@Resource
	private UserDAO userDAO;

    @Override
    public List<UserVO> queryUser(UserQuery query) {

        List<UserDO> userDOs = userDAO.queryList(query);

        List<UserVO> userVOs = BeanUtil.dbToVo(userDOs, UserVO.class, new BeanUtil.Callback<UserDO, UserVO>() {
            @Override
            public void execute(UserDO db, UserVO vo) {
                vo.setStatusText(StatusEnum.getTextByIndex(db.getStatus()));
                vo.setTypeText(UserTypeEnum.getTextByIndex(db.getType()));
                vo.setGmtCreateText(DateUtil.parseDate2Str(db.getGmtCreate()));
            }
        });

        return userVOs;
    }

    @Override
    public UserDO getUserById(Long id) {
        return userDAO.getById(id);
    }

    @Override
    public void addUser(UserDO user) {
        userDAO.insert(user);
    }
}
