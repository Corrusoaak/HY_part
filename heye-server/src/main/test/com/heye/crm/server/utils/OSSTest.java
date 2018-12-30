package com.heye.crm.server.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.heye.crm.common.utils.OSSUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class OSSTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OSSTest.class);

    @Before
    public void setup() {
    }

    @Test
    public void testLogger() {
        LOGGER.info("Logger working");
    }

    @Test
    public void testOSSClientUpload() {
        OSSClient ossClient = OSSUtil.getInstance();

        // 上传字符串
        String content = "Hello OSS!!!";
        PutObjectResult putObjectResult = ossClient.putObject(OSSUtil.HY_OSS_USER_UPLOAD_BUCKET_NAME,
                "my_test_string", new ByteArrayInputStream(content.getBytes()));

//        // 上传Byte数组。
//        byte[] content = "Hello OSS".getBytes();
//        ossClient.putObject("<yourBucketName>", "<yourObjectName>", new ByteArrayInputStream(content));

//        // 上传网络流。
//        InputStream inputStream = new URL("https://www.aliyun.com/").openStream();
//        ossClient.putObject("<yourBucketName>", "<yourObjectName>", inputStream);

//        // 上传文件流。
//        InputStream inputStream = new FileInputStream("<yourlocalFile>");
//        ossClient.putObject("<yourBucketName>", "<yourObjectName>", inputStream);

//        // 上传文件。
//        ossClient.putObject("<yourBucketName>", "<yourObjectName>", new File("<yourLocalFile>"));

        LOGGER.info("putObjectResult ETag = " + putObjectResult.getETag());
    }

    @Test
    public void testOSSClientDownload() {
        OSSClient ossClient = OSSUtil.getInstance();

        // 流式下载
        OSSObject ossObject = ossClient.getObject(OSSUtil.HY_OSS_USER_UPLOAD_BUCKET_NAME, "hy-advise-pic/c7e4fb58e81f4810b15420ccd7c48ddd.jpg");
        System.out.println("Object content:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        while (true) {
            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) break;
            System.out.println("\n" + line);
        }

//        // 下载Object到文件。
//        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("<yourLocalFile>"));
    }
}
