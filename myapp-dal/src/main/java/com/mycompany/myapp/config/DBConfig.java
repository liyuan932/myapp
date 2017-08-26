package com.mycompany.myapp.config;


import com.github.miemiedev.mybatis.paginator.OffsetLimitInterceptor;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author wb-liyuan.j
 * @date 2017-02-24
 */
@Configuration
public class DBConfig {

    /**
     * dbcp数据源
     */
    @Bean
    public DataSource dataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("1234");
        ds.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf8&amp;autoReconnect=true&amp;failOverReadOnly=true");
        return ds;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource ds) throws IOException {
        SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
        sf.setDataSource(ds);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sf.setMapperLocations(resolver.getResources("sqlmap/mapping-*.xml"));  //映射文件位置
        sf.setTypeAliasesPackage("com.mycompany.myapp.daoobject");   //类型别名包扫描路径

        OffsetLimitInterceptor offsetLimitInterceptor = new OffsetLimitInterceptor();  //分页插件
        offsetLimitInterceptor.setDialectClass("com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect");
        sf.setPlugins(new Interceptor[]{offsetLimitInterceptor});
        return sf;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(SqlSessionFactory sessionFactory){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactory(sessionFactory);
        configurer.setBasePackage("com.mycompany.myapp.dao");  //Mapper接口包扫描路径
        return configurer;
    }
}
