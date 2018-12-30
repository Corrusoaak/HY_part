package com.heye.crm.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.heye.crm.common.utils.WeiXinCode;
import com.heye.crm.common.utils.WeiXinUtils;
import org.junit.Test;

/**
 * @author : lishuming
 */
public class WeiXinUtilsTest {

    @Test
    public void testgetSessionKeyOropenid() {
        WeiXinCode r = WeiXinUtils.getSessionKeyOropenid("081rnm1W0qyucU1J6HZV0may1W0rnm1H");
        System.out.println(r);
    }

    @Test
    public void testParseJson() {
        String r = "{\"session_key\":\"lBJ1qomFkmnzH4L4mWOxzw==\",\"expires_in\":7200,\"openid\":\"o7FLr0Cl6kUgmL9-SL3ZZ4cG2yoo\"}";

        WeiXinCode code = JSON.parseObject(r, WeiXinCode.class);
        System.out.println(code);

        System.out.println(JSONObject.toJSON(code));
    }
}
