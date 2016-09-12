package com.mycompany.myapp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * MD5加密工具
 */
public class MD5Util {

    private static final Logger log = LoggerFactory.getLogger(MD5Util.class);

    private static final String Algorithm = "MD5";


    public  static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance(Algorithm);
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("md5 error",e);
            return null;
        }
    }

    public final static String doubleMD5(String s) {
        return MD5Util.MD5(s + s);
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.MD5("123456"));
        System.out.println(MD5Util.MD5("加密"));
        System.out.println(MD5Util.doubleMD5("123456"));
        System.out.println(MD5Util.doubleMD5("加密"));
    }
}
