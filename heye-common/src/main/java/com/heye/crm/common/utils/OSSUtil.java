package com.heye.crm.common.utils;

import com.aliyun.oss.OSSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: fanjinqian
 */
public class OSSUtil {
    public static final String HY_OSS_ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com";
    public static final String HY_OSS_USER_UPLOAD_BUCKET_NAME = "heye-user-upload";
    public static final String HY_OSS_TYPE_HY_ADVISE_PIC = "hy-advise-pic";

    private static final Logger LOGGER = LoggerFactory.getLogger(OSSUtil.class);
    private static final String HY_OSS_ACCESS_KEY_ID = "LTAI3BUiRb0LFEuX";
    private static final String HY_OSS_ACCESS_KEY_SECRET = "mkewCVXFpGmUBw6pw2395TTa7aupfr";
    private static OSSClient ossClient = new OSSClient(HY_OSS_ENDPOINT, HY_OSS_ACCESS_KEY_ID, HY_OSS_ACCESS_KEY_SECRET);

    public static OSSClient getInstance() {
        return ossClient;
    }
}
