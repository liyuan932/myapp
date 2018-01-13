package com.mycompany.myapp.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;

public class PropertiesUtil {
    private static Log log = LogFactory.getLog(PropertiesUtil.class);

    private PropertiesUtil() {
    }

    private static Properties getProperties(String fileName) {
        Properties prop = new Properties();
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(PropertiesUtil.class.getResourceAsStream("/" + fileName), "UTF-8"));
            prop.load(in);
        } catch (Exception var7) {
            throw new RuntimeException("Can not find config file：/" + fileName, var7);
        } finally {
            IOUtils.closeQuietly(in);
        }

        return prop;
    }

    public static String getProperty(String key, String defaultValue, String filename) {
        try {
            if (filename != null && !"".equals(filename)) {
                Properties prop = getProperties(filename);
                if (null == prop) {
                    return null;
                } else {
                    String str = prop.getProperty(key, defaultValue);
                    str = new String(str.getBytes("ISO8859-1"), "UTF-8");
                    return str;
                }
            } else {
                return null;
            }
        } catch (Exception var5) {
            log.error(var5);
            return null;
        }
    }
}

