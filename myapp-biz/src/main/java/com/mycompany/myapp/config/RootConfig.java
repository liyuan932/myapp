package com.mycompany.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author wb-liyuan.j
 * @date 2017-02-24
 */
@Configuration
@Import(DBConfig.class)
public class RootConfig {
}
