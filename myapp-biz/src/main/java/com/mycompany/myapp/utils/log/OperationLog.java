package com.mycompany.myapp.utils.log;

import com.mycompany.myapp.enums.category.LogLocationEnum;
import com.mycompany.myapp.enums.category.LogOperationTypeEnum;
import com.mycompany.myapp.enums.function.ActionEnum;
import com.mycompany.myapp.enums.function.ModuleEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Retention(RUNTIME)
@Target({METHOD, ANNOTATION_TYPE})
public @interface OperationLog {

    /**
     * 模块
     */
    ModuleEnum module() default ModuleEnum.DEFAULT;

    /**
     * 行为
     */
    ActionEnum action() default ActionEnum.DEFAULT;

    /**
     * 操作类型
     */
    LogOperationTypeEnum operatorType() default LogOperationTypeEnum.BASIC_DATA;

    /**
     * 业务id表达式
     * #account  取参数account的值
     * #user.getId() 取参数user对象的getId()方法的返回值
     * $ 取返回对象的值
     * $.getId()  取返回对象的getId()方法的值
     */
    String bizIdExp() default "";

    /**
     * 业务code表达式
     * 规则同bizIdExp
     */
    String bizCodeExp() default "";

    /**
     * 操作人id表达式
     * 规则同bizIdExp
     */
    String operatorExp() default "";

    /**
     * 日志存储位置
     */
    LogLocationEnum location() default LogLocationEnum.DB;

    /**
     * 是否记录info日志
     */
    boolean isRecordInfo() default true;

    /**
     * 是否记录warn日志
     */
    boolean isRecordWarn() default false;

    /**
     * 日志描述
     */
    String msg() default "";
}
