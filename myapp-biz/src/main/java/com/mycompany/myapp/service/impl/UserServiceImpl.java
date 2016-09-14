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
import com.mycompany.myapp.utils.log.LogBean;
import com.mycompany.myapp.utils.log.LogUtils;
import com.mycompany.myapp.utils.log.OperationLog;
import com.mycompany.myapp.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.mycompany.myapp.enums.function.ActionEnum.USER_LOGIN;
import static com.mycompany.myapp.enums.function.ModuleEnum.USER;

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

    //@Cacheable(value = "userCache")
    @Override
    public UserDO getById(Long id) {
        System.out.println("getById()");
        return userDAO.getById(id);
    }

    @Override
    public UserDO add(UserDO userDO) {
        System.out.println("add()");
        LogBean logBean = LogUtils.newLogBean(ModuleEnum.DEFAULT, ActionEnum.DEFAULT);
        logBean.addParamData("user", userDO);

        userDAO.insert(userDO);
        BizCheck.checkArgument(userDO.getId() != null, UserMsgEnum.FAIL_BIZ_ADD_USER);

        return userDO;
    }

    @Override
    @CacheEvict(value = "userCache", key = "#user.getId()")
    //@CachePut(value="userCache",key="#user.getId()")
    public UserDO update(UserDO userDO) {

        userDAO.update(userDO);

        return userDO;
    }

    @OperationLog(module = USER,action = USER_LOGIN, bizCodeExp = "#account",bizIdExp = "$user.getId()")
    @Override
    public UserDO login(String account, String password){
        BizCheck.checkArgument(StringUtils.isNotBlank(account), UserMsgEnum.FAIL_BIZ_ACCOUNT_IS_NULL);
        BizCheck.checkArgument(StringUtils.isNotBlank(password), UserMsgEnum.FAIL_BIZ_PASSWORD_IS_NULL);

        UserDO userDO = userDAO.getByAccountAndPassword(account, password);
        BizCheck.checkArgument(userDO != null, UserMsgEnum.FAIL_BIZ_USER_NOT_EXIST, account);

        return userDO;
    }

    @Override
    public void logout(String account) {
//        LogBean logBean = LogUtils.newLogBean(MainFuncEnum.USER_ADMIN, UserFunctionEnum.LOGOUT);
//        logBean.addParamData("account", account);
//        BizCheck.checkArgument(StringUtils.isNotBlank(account), UserMsgEnum.FAIL_BIZ_ACCOUNT_IS_NULL);
    }
}
