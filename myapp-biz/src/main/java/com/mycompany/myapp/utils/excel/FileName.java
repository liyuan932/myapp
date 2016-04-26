package com.mycompany.myapp.utils.excel;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FileName {
    String value();
}
