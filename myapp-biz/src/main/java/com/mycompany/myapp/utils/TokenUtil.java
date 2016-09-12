package com.mycompany.myapp.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenUtil {

    private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);

    public static String generateToken(String userId,String account) {
        try {
            return DES3Util.encrypt(userId + "_" + account);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("generate token error",e);
            return null;
        }
    }

    public static Long getUid(String token) {
        try {
            String text = DES3Util.decrypt(token);
            return NumberUtils.toLong(StringUtils.split(text, "_")[0]);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get uid error",e);
            return null;
        }

    }

    public static String getAccount(String token) {
        try {
            String text = DES3Util.decrypt(token);
            return StringUtils.split(text, "_")[1];
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String token = generateToken("10000", "liyuan");
        System.out.println(token);
        System.out.println(DES3Util.decrypt(token));
        System.out.println(getUid(token));
        System.out.println(getAccount(token));
    }
}
