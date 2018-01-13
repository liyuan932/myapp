package com.mycompany.myapp.utils;

import com.google.common.collect.Maps;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StaticProp {
    public static Map<String, String> sysConfig = Maps.newHashMap();
    private static DataSource dataSource;
    public static ServletContext SERVLET_CONTEXT;
    public static ExecutorService execute = Executors.newFixedThreadPool(20);
    public static String[] allowFiles = new String[]{".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg", ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid", ".rar", ".doc", ".docx", ".zip", ".pdf", ".txt", ".swf", ".wmv", ".gif", ".png", ".jpg", ".jpeg", ".bmp", "xls", "xlsx"};
    //public static UpYun UP_YUN;
    public static String upYunPath;
    public static boolean IS_USER_UPYUN = true;
    public static String cookieID;

    public StaticProp() {
    }

    public static Connection getDatabaseConnection() throws SQLException {
        if (dataSource == null) {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("com.mysql.jdbc.Driver");
            ds.setUrl((String)sysConfig.get("jdbc.url"));
            ds.setUsername((String)sysConfig.get("jdbc.username"));
            ds.setPassword((String)sysConfig.get("jdbc.password"));
            dataSource = ds;
        }

        return dataSource.getConnection();
    }
}