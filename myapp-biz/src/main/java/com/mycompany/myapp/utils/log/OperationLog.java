package com.mycompany.myapp.utils.log;

import com.mycompany.myapp.enums.function.FunctionEnum;

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

    FunctionEnum module() default FunctionEnum.DEFAULT;

    FunctionEnum action() default FunctionEnum.DEFAULT;

    String bizId() default "";

    LogLocationEnum location() default LogLocationEnum.DB;

}
