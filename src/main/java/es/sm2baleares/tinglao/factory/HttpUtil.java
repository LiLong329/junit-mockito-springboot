package es.sm2baleares.tinglao.factory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import net.sf.json.JSONObject;
import org.apache.http.message.BasicNameValuePair;

/**
 * http工具类
 *
 * @author Chan
 */
@Slf4j
public class HttpUtil {
    private static final String JSON_UTF8_MIME_TYPE = "application/json;charset=utf-8";
    private static final String FORM_MIME_TYPE = "application/x-www-form-urlencoded";
    private static final int SUCCESS = 200;

    public static String getJsonUtf8MimeType(Map map) {
//        JSONObject jsonObject = new JSONObject().element("status","10102000");
        return "ss";
    }

    /**
     * 用于可设置contentType的post请求
     *
     * @param contentType
     * @param headerParamsHashMap
     * @param bodyParamsHashMap
     * @param url
     * @param encoding
     * @return
     */
    public static JSONObject getJSONObjectByPost(String contentType, Map<String, String> headerParamsHashMap, Map<String, String> bodyParamsHashMap, String url, String encoding) {
        //创建httpClient连接
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject result = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", contentType);
            if (headerParamsHashMap != null) {
                // 为HttpPost设置Header参数
                for (Entry<String, String> entry : headerParamsHashMap.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            LOGGER.debug("请求头:{}", Arrays.toString(httpPost.getAllHeaders()));
            // 为HttpPost设置请求RequestBody参数（可为空）
            if (bodyParamsHashMap != null) {
                if (Objects.equals(contentType, JSON_UTF8_MIME_TYPE)) {
                    JSONObject jsonObject = JSONObject.fromObject(bodyParamsHashMap);
                    // 解决中文乱码问题
                    StringEntity stringEntity = new StringEntity(jsonObject.toString(), encoding);
                    stringEntity.setContentType(contentType);
                    httpPost.setEntity(stringEntity);
                    LOGGER.debug("请求体：{}", httpPost.getEntity().toString());
                } else if (Objects.equals(contentType, FORM_MIME_TYPE)) {
                    final List<NameValuePair> params = new ArrayList<>();
                    bodyParamsHashMap.forEach((key, value) -> params.add(new BasicNameValuePair(key, value)));
                    httpPost.setEntity(new UrlEncodedFormEntity(params, encoding));
                    LOGGER.debug("请求体：{}", httpPost.getEntity().toString());
                }
            }

            // HttpClient 发送Post请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            result = getJsonObject(encoding, result, httpResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static JSONObject getJsonObject(String encoding, JSONObject result, CloseableHttpResponse httpResponse) {
        if (httpResponse.getStatusLine().getStatusCode() == SUCCESS) {
            // 获取响应体
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), encoding), 10 * 1024);
                    StringBuilder strBuilder = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        strBuilder.append(line);
                    }
                    // 用JSON将返回json字符串转为json对象
                    result = JSONObject.fromObject(strBuilder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            //关闭流
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * HttpPost请求工具方法
     *
     * @param headerParamsHashMap 请求Header参数集
     * @param bodyParamsHashMap   请求RequestBody参数集
     * @param url                 请求URL
     * @param encoding            请求参数编码
     * @return
     * @Author：冯亚鹏
     * @Date：2018-03-24
     */
    public static JSONObject getJSONObjectByPost(Map<String, String> headerParamsHashMap, Map<String, String> bodyParamsHashMap, String url, String encoding) {
        //创建httpClient连接
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject result = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json;charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            if (headerParamsHashMap != null) {
                // 为HttpPost设置Header参数
                for (Entry<String, String> entry : headerParamsHashMap.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            LOGGER.debug("请求头:{}", Arrays.toString(httpPost.getAllHeaders()));
            // 为HttpPost设置请求RequestBody参数（可为空）
            if (bodyParamsHashMap != null) {
                JSONObject jsonObject = JSONObject.fromObject(bodyParamsHashMap);
                LOGGER.debug("请求体:{}", jsonObject);
                // 解决中文乱码问题
                StringEntity stringEntity = new StringEntity(jsonObject.toString(), encoding);
                stringEntity.setContentType("application/json; charset=utf-8");
                httpPost.setEntity(stringEntity);
            }
            // HttpClient 发送Post请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            result = getJsonObject(encoding, result, httpResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * HttpGet请求工具方法
     * @param headerParamsHashMap 请求头参数Map
     * @param url 请求地址
     * @return JSON响应结果,在发生异常时将返回一个空对象.
     */
    public static JSONObject getJSONObjectByGet(Map<String, String> headerParamsHashMap,Map<String, String> bodyParamsHashMap, String url) {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(url);
        if (bodyParamsHashMap !=  null){
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String,String> query :bodyParamsHashMap.entrySet()  ) {
                if (sbQuery.length() > 0){
                    sbQuery.append("&");
                }
                if (StringUtils.isNotBlank(query.getKey())){
                    sbQuery.append(query.getKey());
                    if(StringUtils.isNotBlank(query.getValue())){
                        sbQuery.append("=");
                        try{
                            sbQuery.append(URLEncoder.encode(query.getValue(),"UTF-8"));
                        }catch (Exception e){
                            LOGGER.error("转化编码失败:\t" + query.getValue() ,e);
                        }

                    }
                }
            }
            if (sbQuery.length() > 0){
                sbUrl.append("?").append(sbQuery.toString());
            }
        }
        return getJSONObjectByGet(headerParamsHashMap, sbUrl.toString());
    }
    /**
     * HttpGet请求工具方法
     * @param headerParamsHashMap 请求头参数Map
     * @param url 请求地址
     * @return JSON响应结果,在发生异常时将返回一个空对象.
     */
    public static JSONObject getJSONObjectByGet(Map<String, String> headerParamsHashMap, String url) {
        JSONObject resultJsonObject = null;

        //创建httpClient连接
        CloseableHttpClient httpClient = HttpClients.createDefault();

        StringBuilder entityStringBuilder = new StringBuilder();
        //利用URL生成一个HttpGet请求
        HttpGet httpGet = new HttpGet(url);
        if (headerParamsHashMap != null) {
            for (Entry<String, String> entry : headerParamsHashMap.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
        // HttpClient 发送Post请求
        CloseableHttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (Exception e) {
            //Fixed 出现异常应该直接返回一个空对象
            //e.printStackTrace();
            LOGGER.error(e.getLocalizedMessage(), e);
            return new JSONObject();
        }
        //得到httpResponse的状态响应码
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            //得到httpResponse的实体数据
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8 * 1024);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        entityStringBuilder.append(line);
                    }
                    // 从HttpEntity中得到的json String数据转为json
                    String json = entityStringBuilder.toString();
                    resultJsonObject = JSONObject.fromObject(json);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            //关闭流
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return resultJsonObject;
    }
}
