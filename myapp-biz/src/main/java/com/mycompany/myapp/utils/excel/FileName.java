package com.mycompany.myapp.utils.excel;

import java.lang.annotation.*;

/**
 * 指定导出的文件名
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FileName {
  /**
   * 文件名
   */
  String value();
}
