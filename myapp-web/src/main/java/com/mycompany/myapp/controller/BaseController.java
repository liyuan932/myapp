package com.mycompany.myapp.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mycompany.myapp.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.service.common.BizException;
import com.mycompany.myapp.utils.CookieUtils;
import com.mycompany.myapp.utils.IpUtil;
import com.mycompany.myapp.utils.StaticProp;
import com.mycompany.myapp.utils.TokenUtil;
import com.mycompany.myapp.vo.BaseResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class BaseController {
    @Resource
    protected HttpServletRequest req;

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public String getIp() {
        return IpUtil.getIp(getRequest());
    }

    public Long getUserId(){
        return TokenUtil.getUid(CookieUtils.getName(req, "token"));
    }

    public String getAccount(){
        return TokenUtil.getAccount(CookieUtils.getName(req, "token"));
    }

    protected <T> BaseResult<T> success(T model) {
        return new BaseResult<>(model);
    }

    protected BaseResult<?> success() {
        return new BaseResult<>();
    }

    private BaseResult<?> fail(CommonMsgEnum commonMsgEnum, Exception ex) {
        return new BaseResult<>(commonMsgEnum.getCode(), commonMsgEnum.getMsg());
    }

    private BaseResult<?> fail(String code, String msg) {
        return new BaseResult<>(code, msg);
    }

    protected BaseResult<?> fail(Exception ex) {
        if (ex instanceof BizException) {
            return fail(((BizException) ex).getCode(), ex.getMessage());
        } else if (ex instanceof DataAccessException) {
            return fail(CommonMsgEnum.DB_ERROR, ex);
        } else {
            return fail(CommonMsgEnum.SYSTEM_ERROR, ex);
        }
    }
    /**
     * 异常处理
     */
    @ExceptionHandler
    public String exception(HttpServletRequest request, Exception ex) {

        request.setAttribute("ex", ex);
        if (ex instanceof BizException) {
            return "error/error-business";
        } else if (ex instanceof DataAccessException) {
            return "error/error-db";
        } else {
            return "error/error";
        }
    }

    /**
     * 文件上传
     *
     * @param upfile
     * @return
     */
    public Map<String, String> upload(MultipartFile upfile) {
        Map<String, String> resultJson = new HashMap<String, String>();
        if (upfile == null || upfile.getSize() < 1) {
            resultJson.put("msg", "未包含文件上传域");
            return resultJson;
        }
        String fileName = upfile.getOriginalFilename();
        Iterator<String> type = Arrays.asList(StaticProp.allowFiles).iterator();
        boolean allowFilesFlag = false;
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                allowFilesFlag = true;
            }
        }
        if (!allowFilesFlag) {
            resultJson.put("msg", "不允许的文件格式");
            return resultJson;
        }
        String url = StaticProp.upYunPath + DigestUtils.md5Hex(UUID.randomUUID().toString());
        url += fileName.substring(fileName.lastIndexOf("."));
        try {
            /*
            boolean a = StaticProp.UP_YUN.writeFile(url, upfile.getBytes(), true);
            if (!a) {
                resultJson.put("msg", "上传到upyun失败");
                return resultJson;
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.put("msg", e.getMessage());
            return resultJson;
        }
        resultJson.put("msg", "SUCCESS");
        resultJson.put("size", String.valueOf(upfile.getSize()));
        resultJson.put("originalName", fileName);
        resultJson.put("name", url);
        resultJson.put("url", StaticProp.sysConfig.get("upyun.domain") + "/" + url);
        resultJson.put("type", fileName.substring(fileName.lastIndexOf(".")));
        return resultJson;
    }

    /**
     * 上传文件通过 URL
     *
     * @param remoteUrl
     * @param ext
     *            .jpg
     * @return
     */
    public Map<String, String> uploadFromUrl(String remoteUrl, String ext) {
        Map<String, String> resultJson = new HashMap<String, String>();
        if (Strings.isNullOrEmpty(remoteUrl)) {
            resultJson.put("msg", "远程地址不能为空");
            return resultJson;
        }
        List<String> types = Arrays.asList(StaticProp.allowFiles);
        if (!types.contains(ext)) {
            resultJson.put("msg", "不允许的文件格式");
            return resultJson;
        }
        String url = StaticProp.upYunPath + DigestUtils.md5Hex(UUID.randomUUID().toString());
        url += ext;
        byte[] bytes = null;
        List<Byte> byteList = Lists.newArrayList();
        try {
            InputStream inputStream = new URL(remoteUrl).openStream();
            int b;
            while ((b = inputStream.read()) != -1) {
                byteList.add((byte) b);
            }
            bytes = new byte[byteList.size()];
            for (int i = 0; i < byteList.size(); i++) {
                bytes[i] = byteList.get(i);
            }
          /*
            boolean a = StaticProp.UP_YUN.writeFile(url, bytes, true);
            if (!a) {
                resultJson.put("msg", "上传到upyun失败");
                return resultJson;
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.put("msg", e.getMessage());
            return resultJson;
        }
        resultJson.put("msg", "SUCCESS");
        resultJson.put("size", String.valueOf(bytes.length));
        resultJson.put("originalName", remoteUrl);
        resultJson.put("name", url);
        resultJson.put("url", "http://" + StaticProp.sysConfig.get("upyun.domain") + "/" + url);
        resultJson.put("type", ext);
        return resultJson;
    }

}
