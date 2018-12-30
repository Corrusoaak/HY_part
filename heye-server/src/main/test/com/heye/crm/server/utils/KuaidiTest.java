package com.heye.crm.server.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heye.crm.common.utils.HttpUtil;
import com.heye.crm.common.utils.MD5Util;

import java.util.HashMap;

public class KuaidiTest {
    public static void subscribe() {
        String schema = "json";
        String param = "{" +
                "\"company\":\"ems\"," +
                "\"number\":\"em263999513jptest3\"," +
                "\"key\":\"tEstrbHmnXja9557\"," +
                "\"parameters\":{" +
                "\"callbackurl\":\"localhost/kuaidi?callbackid=3\"," +
                "}" +
                "}";
        System.out.println(param);
        HashMap params = new HashMap();
        params.put("param", param);
        params.put("schema", schema);
        byte[] tmp = new HttpUtil().doPost("https://poll.kuaidi100.com/test/poll", params, "utf-8");
        String s = new String(tmp);
        System.out.println(s);


/*
        TaskRequest req = new TaskRequest();
        req.setCompany("yuantong");
        req.setFrom("上海浦东新区");
        req.setTo("广东深圳南山区");
        req.setNumber("12345678");
        req.getParameters().put("callbackurl", "http://www.yourdmain.com/kuaidi");
        req.setKey("testkuaidi1031");

        HashMap<String, String> p = new HashMap<String, String>();
        p.put("schema", "json");
        p.put("param", JacksonHelper.toJSON(req));
        try {
            String ret = HttpRequest.postData("http://www.kuaidi100.com/poll", p, "UTF-8");
            TaskResponse resp = JacksonHelper.fromJSON(ret, TaskResponse.class);
            if(resp.getResult()==true){
                System.out.println("订阅成功");
            }else{
                System.out.println("订阅失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    public static void getExpressInfo() {
        String param = "{\"com\":\"rufengda\",\"num\":\"402929624894\",\"from\":\"\",\"to\":\"\",\"resultv2\":0}";
        String customer = "942D4D331CE24DC1B8E038D1E6691348";
        String key = "tEstrbHmnXja9557";
        String sign = MD5Util.encrypt(param + key + customer);

        HashMap params = new HashMap();
        params.put("param", param);
        params.put("sign", sign);
        params.put("customer", customer);
        String resp;
        try {
            byte[] tmp = new HttpUtil().doPost("http://poll.kuaidi100.com/test/poll/query.do", params, "utf-8");
            String s = new String(tmp);
            System.out.println(s);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void getExpressCompany() {
        String param = "{\"key\":\"tEstrbHmnXja9557\",\"num\":\"90001543737440\"}";
        String num = "90001543737440";
        String key = "tEstrbHmnXja9557";
        HashMap params = new HashMap();
        params.put("num", num);
        params.put("key", key);
        try {
            byte[] tmp = new HttpUtil().doPost("http://poll.kuaidi100.com/test/autonumber/auto", params, "utf-8");
            String s = new String(tmp);

            JSONArray jsonArray = null;
            jsonArray = JSONArray.parseArray(s);
            //System.out.println(jsonArray.getJSONObject(1).get("comCode"));
            System.out.println(s);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        getExpressCompany();

        //getExpressInfo();
        //subscribe();
    }
}
