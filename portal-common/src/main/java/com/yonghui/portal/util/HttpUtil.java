package com.yonghui.portal.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 *  呼叫 Http 请求
 *  huangzenglei@intfocus.com
 */
public class HttpUtil {
    public static  String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APPKEY ="yhappQKXYfkjqn8Yq6ojACkwXRnt35322896dfd9419f9d2c4080b064d89a";

    public static void setCharset(String charset){
        DEF_CHATSET  = charset;
    }

    /*public static void main(String[] args) {
        String url ="http://yhapi.yonghui.cn/yhportal/openApi/portal/report?";//请求接口地址
        String params = null;
        String encodeParams = null ;
        try {
            //编码处理  params：用于加密  encodeParams：用于请求rul
            encodeParams= "openApiCode=OPENAPI_000008&report_label=1&area_mans="+ URLEncoder.encode("福州中区","UTF-8");
            params= "openApiCode=OPENAPI_000008&report_label=1&area_mans=福州中区";

            String str = getData(url ,encodeParams,params );
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * MD5 加密
     * 顶用请求
     * 返回请求到的数据
     * */
    public static String getData(String baseUrl , String encodParams ,String params){
        // key + parameter + key MD5 加密
        String sign = Md5Util.getMd5("MD5", 0, null, APPKEY + params + APPKEY);
        Map<String,String> lastParam = new HashMap<String , String>();
        lastParam.put("sign",sign);
        String str = null;
        try {
            str = net(baseUrl + encodParams ,lastParam,"GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method ) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("contentType", "UTF-8");
            conn.connect();
            if (params!= null && method.equals("POST")) {
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(urlencode(params));
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append("&").append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
