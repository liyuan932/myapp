package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.dao.OperationLogDAO;
import com.mycompany.myapp.daoobject.OperationLogDO;
import com.mycompany.myapp.enums.category.LogLevelEnum;
import com.mycompany.myapp.enums.category.LogLocationEnum;
import com.mycompany.myapp.enums.function.ModuleEnum;
import com.mycompany.myapp.service.OperationLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wb-liyuan.j
 * @date 2016-09-13
 */
@Transactional(rollbackFor = Exception.class)
@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService {

    private static final Logger log = LoggerFactory.getLogger("operationLog");

    @Resource
    private OperationLogDAO operationLogDAO;


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void output(OperationLogDO operationLogDO) {
        Integer location = operationLogDO.getLocation();
        if(LogLocationEnum.DB.getIndex().equals(location)){
            operationLogDAO.insert(operationLogDO);
        }else if(LogLocationEnum.FILE.getIndex().equals(location)){
            toFile(operationLogDO);
        }
    }

    private String format(OperationLogDO operationLogDO) {
        StringBuffer sb = new StringBuffer();
        sb.append(ModuleEnum.getTextByIndex(operationLogDO.getModule()));
        sb.append("->");
        sb.append(operationLogDO.getAction());
        sb.append("->");
        sb.append(StringUtils.trim(operationLogDO.getMsg()));
        sb.append("->");
        sb.append(StringUtils.trim(operationLogDO.getParamData()));
        sb.append("->");
        sb.append(StringUtils.trim(operationLogDO.getResultData()));
        sb.append("->");
        sb.append(operationLogDO.getBizId());
        sb.append("->");
        sb.append(operationLogDO.getBizCode());
        sb.append("->");
        sb.append(operationLogDO.getCost());
        sb.append("->");
        sb.append(operationLogDO.getStackTrace());
        return sb.toString();
    }

    private void toFile(OperationLogDO operationLogDO){

        LogLevelEnum level = LogLevelEnum.getByIndex(operationLogDO.getLevel());
        assert level != null;
        switch (level){
            case DEBUG:
                log.debug(format(operationLogDO));
                break;
            case INFO:
                log.info(format(operationLogDO));
                break;
            case WARN:
                log.warn(format(operationLogDO));
                break;
            case ERROR:
                log.error(format(operationLogDO));
                break;
        }
    }
}
