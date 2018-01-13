package com.mycompany.myapp.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class IpUtil {

    public IpUtil() {
    }

    public static JSONObject getIpAddess(String ip) {
        String content = HttpUtil.get("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip, (Map)null, (Map)null);
        JSONObject jsonObject = JSONObject.parseObject(content);
        Integer code = jsonObject.getInteger("code");
        if (code != null && code != 1) {
            jsonObject = jsonObject.getJSONObject("data");
            return jsonObject;
        } else {
            return null;
        }
    }

    public static String getIp(HttpServletRequest request) {
        String ip = "";
        ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forworded-For");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteHost();
        }

        if (ip != null && !"".equals(ip)) {
            ip = ip.replaceAll("#", "");
            ip = ip.trim();
            ip = ip.replaceAll(" ", "");
            ip = ip.toLowerCase();
            ip = ip.replaceAll("unknown,", "");
            String[] var2 = ip.split(",");
            int var3 = var2.length;

            int var4;
            String ipStr;
            for(var4 = 0; var4 < var3; ++var4) {
                ipStr = var2[var4];
                if (ipStr != null && !"".equals(ipStr)) {
                    ip = ipStr;
                    break;
                }
            }

            var2 = ip.split(":");
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
                ipStr = var2[var4];
                if (ipStr != null && !"".equals(ipStr)) {
                    ip = ipStr;
                    break;
                }
            }
        }

        return ip;
    }
}
