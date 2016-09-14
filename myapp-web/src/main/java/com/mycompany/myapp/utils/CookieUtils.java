package com.mycompany.myapp.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtils {
    /**
     * 添加一个cookie
     *
     * @param res
     * @param name
     * @param value
     * @param maxAge
     */
    public static void add(HttpServletResponse res, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    public static void add(HttpServletResponse res, String name, String value) {
        add(res, name, value, -1);
    }

    /**
     * 获取cookie的值
     *
     * @param req
     * @param name
     * @return
     */
    public static String getName(HttpServletRequest req, String name) {
        Cookie cookie = get(req, name);
        String cookieVal = (null == cookie) ? null : cookie.getValue();
        return cookieVal;
    }

    public static Cookie get(HttpServletRequest req, String name) {
        Map<String, Cookie> cookieMap = _readCookieMap(req);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        } else {
            return null;
        }
    }

    /**
     * 清除cookie
     *
     * @param req
     * @param res
     * @param name
     */
    public static void remove(HttpServletRequest req, HttpServletResponse res, String name) {
        String cookieName = getName(req, name);
        if (null != cookieName) {
            Cookie cookie = new Cookie(cookieName, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            res.addCookie(cookie);
        }
    }

    /**
     * 清除所有cookie
     *
     * @param req
     * @param res
     */
    public static void clear(HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = req.getCookies();
        for (int i = 0, len = cookies.length; i < len; i++) {
            Cookie cookie = new Cookie(cookies[i].getName(), null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            res.addCookie(cookie);
        }
    }

    private static Map<String, Cookie> _readCookieMap(HttpServletRequest req) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = req.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
