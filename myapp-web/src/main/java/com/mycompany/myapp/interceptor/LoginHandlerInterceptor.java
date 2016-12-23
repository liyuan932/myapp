package com.mycompany.myapp.interceptor;

import com.mycompany.myapp.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        String uri = request.getRequestURI();

        String suffixes = ".js|.css|.ico|.png|.jpg|.bmp|.json";
        String ignores = "/,/index";

        if (containSuffix(uri, suffixes) || containIgnore(uri,ignores)) {
            return true;
        }

        String token = CookieUtils.getName(request, "token");
        if (token == null) {
            response.sendRedirect("/");
        }

        return true;
    }

    private boolean containSuffix(String uri, String suffixes) {
        for (String suffix : StringUtils.split(suffixes, "\\|")) {
            if (uri.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    private boolean containIgnore(String uri, String ignores) {
        for (String ignore : StringUtils.split(ignores, ",")) {
            if (uri.equals(ignore)) {
                return true;
            }
        }
        return false;
    }
}