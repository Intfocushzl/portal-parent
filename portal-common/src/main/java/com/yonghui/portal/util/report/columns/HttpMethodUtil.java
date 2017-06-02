package com.yonghui.portal.util.report.columns;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwei on 2017/6/1.
 */
public class HttpMethodUtil {
    /**
     * 使用httpclient进行调用
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     * @param url
     * @param paramMap
     * @return
     */
    public String getPostResult(String url, Map<String, Object> paramMap) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);

        // 4.3版本不设置超时的话，一旦服务器没有响应，等待时间N久(>24小时)。容易出现504错误（gateway timeout）
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000) // 设置请求和传输超时时间
                .setConnectTimeout(5000).build();
        httppost.setConfig(requestConfig);

        // 创建参数队列
        List<NameValuePair> paramList = null;
        if (paramMap != null && !paramMap.isEmpty()) {
            paramList = new ArrayList<NameValuePair>();
            for (String key : paramMap.keySet()) {
                paramList.add(new BasicNameValuePair(key, String.valueOf(paramMap.get(key))));
            }
        }
        try {
            if (paramList != null && !paramList.isEmpty()) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
                httppost.setEntity(entity);
            }

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                int status = response.getStatusLine().getStatusCode();

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");

                // 状态码为200时，才返回正常内容
                if (/*status == HttpStatus.SC_OK && */entity != null) {
                    return result;
                }
                System.out.println("POST请求接口信息出错！"+ "请求URL:"+url +"==状态码：=="+status + "==返回结果：=="+result);
            } finally {
                response.close();
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("参数转码错误！"+ e);
        } catch (IOException e) {
            System.out.println("获取httpclient错误！"+ e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                System.out.println("关闭httpclient失败！"+e);
            }
        }
        return null;
    }


    /**
     * 使用httpclient进行调用
     *
     * 发送 GET请求访问本地应用并根据传递参数不同返回不同结果
     *
     * @param url
     * @param paramMap
     * @return
     */
    public  String getGetResult(String url, Map<String, Object> paramMap) {
        // 创建参数队列
        StringBuilder sbUrl = new StringBuilder(url).append("?");
        if (paramMap != null && !paramMap.isEmpty()) {
            for (String key : paramMap.keySet()) {
                sbUrl.append(key).append("=").append(paramMap.get(key)).append("&");
            }
        }
        sbUrl.deleteCharAt(sbUrl.length() - 1);

        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httpget
        HttpGet httpget = new HttpGet(sbUrl.toString());
        // 4.3版本不设置超时的话，一旦服务器没有响应，等待时间N久(>24小时)。容易出现504错误（gateway timeout）
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000) // 设置请求和传输超时时间
                .setConnectTimeout(5000).build();
        httpget.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                int status = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                // 状态码为200时，才返回正常内容
                if (status == HttpStatus.SC_OK && entity != null) {
                    return result;
                }
                System.out.println("GET请求接口信息出错！"+ "请求URL:"+url +"==状态码：=="+status + "==返回结果：=="+result);
            } finally {
                response.close();
            }
        } catch (IOException e) {
            System.out.println("获取httpclient错误！"+ e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                System.out.println("关闭httpclient失败！"+e);
            }
        }
        return null;
    }
}
