package com.mycompany.myapp.utils.excel;


import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Title {

    String value();

    int cellType() default Cell.CELL_TYPE_STRING;
}
