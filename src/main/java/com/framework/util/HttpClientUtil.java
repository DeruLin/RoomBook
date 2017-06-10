package com.framework.util;


import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.spi.http.HttpContext;

public class HttpClientUtil {

    private static CloseableHttpClient httpclient;

    static {
        httpclient = HttpClients.createDefault();
    }

    /**
     * httpClient的get请求方式
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String sendGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpGet);
        String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        httpGet.abort();
        return jsonStr;
    }

    public static String sendGet(String url, HttpClientContext context) {
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(httpGet, context);
            String responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            httpGet.abort();
            System.out.println("execute url:" + url);
            System.out.println(responseStr);
            return responseStr;
        } catch (Exception e) {
            return "";
        }

    }

    public static String sendPost(String url, String xml) throws Exception {
        HttpPost httpPost = new HttpPost(url); // 设置响应头信息
        httpPost.addHeader("Connection", "keep-alive");
        httpPost.addHeader("Accept", "*/*");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.addHeader("Host", "api.mch.weixin.qq.com");
        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.addHeader("Cache-Control", "max-age=0");
        httpPost.addHeader("UserVo-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        httpPost.setEntity(new StringEntity(xml, "UTF-8"));
        HttpResponse response = httpclient.execute(httpPost);
        String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        return jsonStr;
    }

    public static HttpClientContext getLoginContext(String loginUrl){
        try{
            HttpClientContext context = new HttpClientContext();
            CookieStore cookieStore = new BasicCookieStore();
            context.setCookieStore(cookieStore);
            HttpGet httpGet = new HttpGet(loginUrl);
            HttpResponse httpResponse = httpclient.execute(httpGet, context);
            String responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            if (responseStr.contains("ok")) {
                return context;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static String loginAndGet(String loginUrl, String url) throws Exception {
        HttpClientContext context = new HttpClientContext();
        CookieStore cookieStore = new BasicCookieStore();
        context.setCookieStore(cookieStore);
        HttpGet httpGet = new HttpGet(loginUrl);
        HttpResponse httpResponse = httpclient.execute(httpGet, context);
        String responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        if (responseStr.contains("ok")) {
            httpGet = new HttpGet(url);
            httpResponse = httpclient.execute(httpGet, context);
            return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        }
        return "";

    }


}