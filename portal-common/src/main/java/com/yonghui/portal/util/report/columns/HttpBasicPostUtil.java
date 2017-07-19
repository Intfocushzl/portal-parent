package com.yonghui.portal.util.report.columns;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by liuwei on 2017/07/19
 */
public class HttpBasicPostUtil {


    private static final String URL = "http://10.0.66.65:50000/RESTAdapter/HR_NOR028";
    private static final String APP_KEY = "YHDOP_PJD";
    private static final String SECRET_KEY = "1234qwer";

    /**
     * 使用httpclient进行调用
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     * @param url
     * @param json
     * @return
     */
    public String getPostJsonResult(String url, String json) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httppost.addHeader("Authorization", getHeader());
        // 4.3版本不设置超时的话，一旦服务器没有响应，等待时间N久(>24小时)。容易出现504错误（gateway timeout）
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000) // 设置请求和传输超时时间
                .setConnectTimeout(5000).build();
        httppost.setConfig(requestConfig);

        try {
            if (json != null && !json.isEmpty()) {
                StringEntity se = new StringEntity(json,"UTF-8");
                se.setContentType("text/json");
                httppost.setEntity(se);
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
     * 构造Basic Auth认证头信息
     *
     * @return
     */
    private String getHeader() {
        String auth = APP_KEY + ":" + SECRET_KEY;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    public static void main(String[] args) throws Exception {
        JSONArray jsonAry1 = new JSONArray();
        JSONObject node = new JSONObject();
        node.put("PERNR", "80000001");
        node.put("BEGDA", "20170731");
        node.put("LGART", "3031");
        node.put("BETRG1", "600");
        node.put("BETRG2", "");
        node.put("ZUORD", "ni");
        node.put("FLAG", "I");
        jsonAry1.add(node);
        JSONObject node1 = new JSONObject();
        node1.put("ITEM", jsonAry1);
        HttpBasicPostUtil util = new HttpBasicPostUtil();
        System.out.println(util.getPostJsonResult(URL ,node1.toJSONString()));
    }
}
