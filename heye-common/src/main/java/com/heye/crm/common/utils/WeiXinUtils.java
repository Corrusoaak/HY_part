package com.heye.crm.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author : lishuming
 * Copied From: http://1017401036.iteye.com/blog/2263358
 */
public class WeiXinUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeiXinUtils.class);
    private static final String GRANTTYPE = "client_credential";
    //private static final String APP_ID = "wx8552da4415fceb68";
    //private static final String SECRET = "b8b4f02b13a43ed53fccb2a4adbd9a31";
    private static final String APP_ID = "wxb331fea4e0c1350b";
    private static final String SECRET = "56ccddfc2f3b48e68fc371a8a83a0fd3";

    public static String getAccessToken() {
        String accessToken = "";

        String url = "https://api.weixin.qq.com/cgi-bin/token?GRANTTYPE=" + GRANTTYPE + "&appid=" + APP_ID + "&secret=" + SECRET;

        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.parseObject(message);
            System.out.print(demoJson);
            LOGGER.info("JSON:" + demoJson);
            accessToken = demoJson.getString("accessToken");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    public static String sha1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte[] messageDigest = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTicket(String accessToken) {
        String ticket = null;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?accessToken=" + accessToken + "&type=jsapi";
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.parseObject(message);
            LOGGER.info("JSON:" + demoJson);
            ticket = demoJson.getString("ticket");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ticket;
    }

    public static WeiXinSign getWeiXinSign(String url) {
        WeiXinSign sign = new WeiXinSign();

        String accessToken = getAccessToken();
        String jsapi_ticket = getTicket(accessToken);
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        LOGGER.info("accessToken:" + accessToken + ", jsapi_ticket:" + jsapi_ticket + ", timestamp：" + timestamp + ", noncestr：" + noncestr);

        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
        String signature = sha1(str);

        sign.setNoncestr(noncestr);
        sign.setTimestamp(timestamp);
        sign.setSign(signature);

        return sign;
    }

    /**
     * 获取微信小程序 session_key 和 openid: https://blog.csdn.net/weilai_zhilu/article/details/77932630
     *
     * @param code 调用微信登陆返回的Code
     * @return
     * @author zhy
     */
    public static WeiXinCode getSessionKeyOropenid(String code) {
        //微信端登录code值
        String wxCode = code;

        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> requestUrlParam = new HashMap<String, String>();
        requestUrlParam.put("appid", APP_ID);
        requestUrlParam.put("secret", SECRET);
        requestUrlParam.put("js_code", wxCode);
        requestUrlParam.put("GRANTTYPE", "authorization_code");
        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
        String httpResult = new String(HttpUtil.doPost(requestUrl, requestUrlParam));

        WeiXinCode weiXinCode;
        try {
            LOGGER.info("weixin result" + httpResult);
            weiXinCode = JSON.parseObject(httpResult, WeiXinCode.class);
        } catch (Exception e) {
            LOGGER.warn("decode weixin code faile", e);
            return null;
        }
        return weiXinCode;
    }
}

