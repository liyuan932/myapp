package com.mycompany.myapp.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtil {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public CookieUtil(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public String getCookie(String name) {
        Cookie[] cookies = this.request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for(int i = 0; i < cookies.length; ++i) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(name) && cookie.getValue() != null) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "utf-8");
                    } catch (UnsupportedEncodingException var6) {
                        var6.printStackTrace();
                    }
                }
            }
        }

        return null;
    }

    public void removeCookie(String name, String domain) {
        this.setCookie(name, (String)null, 0, domain);
    }

    public void setCookie(String name, String value, Integer maxAge, String domain) {
        Cookie[] cookies = this.request.getCookies();
        boolean flag = false;
        if (cookies != null) {
            Cookie[] var7 = cookies;
            int var8 = cookies.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                Cookie c = var7[var9];
                if (c.getName() != null && c.getName().equals(name)) {
                    try {
                        if (value != null) {
                            c.setValue(URLEncoder.encode(value, "utf-8"));
                        } else {
                            c.setValue(value);
                        }
                    } catch (UnsupportedEncodingException var13) {
                        var13.printStackTrace();
                    }

                    if (domain != null && !"".equals(domain)) {
                        c.setDomain(domain);
                    }

                    c.setPath("/");
                    c.setMaxAge(maxAge);
                    this.response.addCookie(c);
                    flag = true;
                }
            }
        }

        if (!flag) {
            try {
                Cookie c;
                if (value != null) {
                    c = new Cookie(name, URLEncoder.encode(value, "utf-8"));
                } else {
                    c = new Cookie(name, value);
                }

                if (domain != null && !"".equals(domain)) {
                    c.setDomain(domain);
                }

                c.setPath("/");
                c.setMaxAge(maxAge);
                this.response.addCookie(c);
            } catch (UnsupportedEncodingException var12) {
                var12.printStackTrace();
            }
        }

    }
}