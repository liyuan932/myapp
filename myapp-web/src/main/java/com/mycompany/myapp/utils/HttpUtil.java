package com.mycompany.myapp.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpUtil {
    private static final Log LOGGER = LogFactory.getLog(HttpUtil.class);

    public HttpUtil() {
    }

    public static String get(String url, Map<String, Object> ParamMap, Map<String, String> headerParamMap) {
        String returnValue = (String) getHttpEntity(url, ParamMap, headerParamMap);
        return returnValue;
    }

    public static byte[] getBytes(String url, Map<String, Object> ParamMap, Map<String, String> headerParamMap) {
        byte[] returnValue = (byte[]) ((byte[]) getHttpEntity(url, ParamMap, headerParamMap));
        return returnValue;
    }

    public static Object getHttpEntity(String url, Map<String, Object> ParamMap, Map<String, String> headerParamMap) {
        try {
            if (ParamMap != null && ParamMap.size() > 0) {
                url = formatGetParameter(url, ParamMap);
            }

            HttpGet get = new HttpGet(url);
            String mimeType;
            if (headerParamMap != null && headerParamMap.size() > 0) {
                Iterator var6 = headerParamMap.keySet().iterator();

                while (var6.hasNext()) {
                    mimeType = (String) var6.next();
                    get.addHeader(mimeType, (String) headerParamMap.get(mimeType));
                }
            }

            CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).setConnectionRequestTimeout(60000).build()).build();
            if (url.startsWith("https")) {
                client = createSSLClientDefault();
            }

            CloseableHttpResponse execute = client.execute(get);
            HttpEntity httpEntity = execute.getEntity();
            mimeType = ContentType.getOrDefault(httpEntity).getMimeType();
            Object o;
            if (mimeType != null && mimeType.contains("image/")) {
                o = EntityUtils.toByteArray(httpEntity);
            } else {
                o = EntityUtils.toString(httpEntity, "UTF-8");
            }

            execute.close();
            client.close();
            return o;
        } catch (Exception var9) {
            LOGGER.error(var9);
            return null;
        }
    }

    public static String postMethodUrl(String url, String body, Map<String, Object> paramMap, Map<String, String> headerParamMap) {
        return postMethodUrl(url, body, paramMap, headerParamMap, (CloseableHttpClient) null);
    }

    public static String postMethodUrl(String url, String body, Map<String, Object> paramMap, Map<String, String> headerParamMap, CloseableHttpClient closeableHttpClient) {
        String returnValue = (String) postMethodUrlHttpEntity(url, body, paramMap, headerParamMap, closeableHttpClient);
        return returnValue;
    }

    public static byte[] postMethodUrlByteArry(String url, String body, Map<String, Object> paramMap, Map<String, String> headerParamMap) {
        return postMethodUrlByteArry(url, body, paramMap, headerParamMap, (CloseableHttpClient) null);
    }

    public static byte[] postMethodUrlByteArry(String url, String body, Map<String, Object> paramMap, Map<String, String> headerParamMap, CloseableHttpClient closeableHttpClient) {
        byte[] returnValue = (byte[]) ((byte[]) postMethodUrlHttpEntity(url, body, paramMap, headerParamMap, closeableHttpClient));
        return returnValue;
    }

    public static Object postMethodUrlHttpEntity(String url, String body, Map<String, Object> paramMap, Map<String, String> headerParamMap, CloseableHttpClient closeableHttpClient) {
        try {
            HttpPost post = new HttpPost(url);
            String mimeType;
            if (headerParamMap != null && headerParamMap.size() > 0) {
                Iterator var8 = headerParamMap.keySet().iterator();

                while (var8.hasNext()) {
                    mimeType = (String) var8.next();
                    post.addHeader(mimeType, (String) headerParamMap.get(mimeType));
                }
            }

            if (paramMap != null && paramMap.size() > 0) {
                List<NameValuePair> nvps = new ArrayList();
                Iterator var15 = paramMap.keySet().iterator();

                while (var15.hasNext()) {
                    String key = (String) var15.next();
                    Object value = paramMap.get(key);
                    if (value != null) {
                        nvps.add(new BasicNameValuePair(key, String.valueOf(value)));
                    }
                }

                post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            }

            if (body != null && !"".equals(body)) {
                post.setEntity(new StringEntity(body, "UTF-8"));
            }

            CloseableHttpClient client;
            if (closeableHttpClient == null) {
                client = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).setConnectionRequestTimeout(60000).build()).build();
                if (url.startsWith("https")) {
                    client = createSSLClientDefault();
                }
            } else {
                client = closeableHttpClient;
            }

            CloseableHttpResponse execute = client.execute(post);
            HttpEntity httpEntity = execute.getEntity();
            mimeType = ContentType.getOrDefault(httpEntity).getMimeType();
            Object o;
            if (mimeType != null && mimeType.contains("image/")) {
                o = EntityUtils.toByteArray(httpEntity);
            } else {
                o = EntityUtils.toString(httpEntity);
            }

            execute.close();
            client.close();
            return o;
        } catch (Exception var12) {
            LOGGER.error(var12);
            return null;
        }
    }

    public static String formatGetParameter(String url, Map<String, Object> argsMap) {
        if (url != null && url.length() > 0) {
            if (!url.endsWith("?")) {
                url = url + "?";
            }

            if (argsMap != null && !argsMap.isEmpty()) {
                Set<Map.Entry<String, Object>> entrySet = argsMap.entrySet();
                Iterator iterator = entrySet.iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry) iterator.next();
                    if (entry != null) {
                        String key = (String) entry.getKey();
                        String value = (String) entry.getValue();
                        url = url + key + "=" + value;
                        if (iterator.hasNext()) {
                            url = url + "&";
                        }
                    }
                }
            }
        }

        return url;
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore) null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).setConnectionRequestTimeout(10000).build()).build();
        } catch (KeyManagementException var2) {
            LOGGER.error(var2);
        } catch (NoSuchAlgorithmException var3) {
            LOGGER.error(var3);
        } catch (KeyStoreException var4) {
            LOGGER.error(var4);
        }

        return null;
    }
}