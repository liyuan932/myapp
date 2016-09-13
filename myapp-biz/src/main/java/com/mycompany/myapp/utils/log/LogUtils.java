package com.mycompany.myapp.utils.log;


import com.mycompany.myapp.daoobject.OperationLogDO;
import com.mycompany.myapp.enums.category.LogLocationEnum;
import com.mycompany.myapp.enums.function.ActionEnum;
import com.mycompany.myapp.enums.function.ModuleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    private static final Logger log = LoggerFactory.getLogger("operationLog");

    public static LogBean newLogBean(String mainFunc) {
        return new LogBean(mainFunc);
    }

    public static LogBean newLogBean(String module, String action) {
        return new LogBean(module, action);
    }

    public static LogBean newLogBean(ModuleEnum module, ActionEnum action) {
        return new LogBean(module, action);
    }

    public static void debug(LogBean logBean) {
        log.debug(logBean.toString());
    }

    public static void info(LogBean logBean) {
        log.info(logBean.toString());
    }
    public static void info(OperationLogDO operationLogDO) {
        if(operationLogDO.getLocation() == LogLocationEnum.DB.getIndex()){

        }
    }


    public static void warn(LogBean logBean) {
        log.warn(logBean.toString());
    }

    public static void error(LogBean logBean, Exception ex) {
        //setStackTrace(logBean, ex);
        log.error(logBean.toString());
    }

}
