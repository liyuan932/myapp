package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.dao.UserDAO;
import com.mycompany.myapp.daoobject.UserDO;
import com.mycompany.myapp.enums.category.EnableOrDisableEnum;
import com.mycompany.myapp.enums.category.UserTypeEnum;
import com.mycompany.myapp.enums.function.ActionEnum;
import com.mycompany.myapp.enums.function.ModuleEnum;
import com.mycompany.myapp.enums.msg.UserMsgEnum;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.common.BaseService;
import com.mycompany.myapp.service.common.BizCheck;
import com.mycompany.myapp.utils.BeanUtil;
import com.mycompany.myapp.utils.DateUtil;
import com.mycompany.myapp.utils.log.OperationLog;
import com.mycompany.myapp.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private UserDAO userDAO;

    @Override
    public List<UserVO> queryList(UserQuery query) {

        List<UserDO> dbList = userDAO.queryList(query);

        return BeanUtil.dbToVo(dbList, UserVO.class, new BeanUtil.Callback<UserDO, UserVO>() {
            @Override
            public void execute(UserDO db, UserVO vo) {
                vo.setStatusText(EnableOrDisableEnum.getTextByIndex(db.getStatus()));
                vo.setTypeText(UserTypeEnum.getTextByIndex(db.getType()));
                vo.setGmtCreateText(DateUtil.parseDate2Str(db.getGmtCreate()));
            }
        });
    }

    @Override
    public UserDO getById(Long id) {
        System.out.println("getById()");
        return userDAO.getById(id);
    }

    @Override
    public UserDO add(UserDO userDO) {
        userDAO.insert(userDO);
        BizCheck.checkParam(userDO.getId() != null, UserMsgEnum.ADD_USER_ERROR);

        return userDO;
    }

    @Override
    public UserDO update(UserDO userDO) {

        userDAO.update(userDO);

        return userDO;
    }

    @OperationLog(module = ModuleEnum.USER,action = ActionEnum.USER_LOGIN, bizCode = "#account", bizId = "$.getId()",
        operator = "$.getId()")
    @Override
    public UserDO login(String account, String password){
        BizCheck.checkParam(StringUtils.isNotBlank(account), "登录账户不能为空");
        BizCheck.checkParam(StringUtils.isNotBlank(password), "登录密码不能为空");

        UserDO userDO = userDAO.getByAccountAndPassword(account, password);
        BizCheck.checkParam(userDO != null, UserMsgEnum.ACCOUNT_OR_PASSWORD_ERROR, account);

        return userDO;
    }

    @Override
    public void logout(String account) {
    }
}
