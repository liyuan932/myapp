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

    boolean value() default true;

    ModuleEnum module() default ModuleEnum.DEFAULT;

    ActionEnum action() default ActionEnum.DEFAULT;

    LogOperationTypeEnum operatorType() default LogOperationTypeEnum.BASIC_DATA;

    String sourceId() default "";

    String sourceCode() default "";

    LogLocationEnum location() default LogLocationEnum.DB;
}
