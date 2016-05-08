package com.mycompany.myapp.utils.excel;


import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识需要导出的字段，对其进行说明，并指定单元格类型。
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Title {

  /**
   * 标题说明
   */
  String value();

  /**
   * 单元格类型
   */
  int cellType() default Cell.CELL_TYPE_STRING;
}
