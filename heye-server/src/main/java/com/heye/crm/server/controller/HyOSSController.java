package com.heye.crm.server.controller;

import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.utils.OSSUtil;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyOSSReq;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.UUID;

/**
 * @author : fanjinqian
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/oss")
public class HyOSSController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyAdminController.class);

    @Override
    public CRight getControllerCRight() {
        return null;
    }

    /**
     * 用户上传图片，返回URL
     *
     * @param req: filePath
     * @return url
     */
    @PostMapping("/userUpload")
    public Result userUpload(@RequestBody HyOSSReq req) {
        LOGGER.info("[userUploadPic] Req json: {}", new Gson().toJson(req));
        if (StringUtils.isEmpty(req.getFilePath())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        try {
            File file = new File(req.getFilePath());
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String[] names = file.getName().split("[.]");
            String name = uuid + "." + names[names.length - 1];

            PutObjectResult putObjectResult = OSSUtil.getInstance().putObject(
                    new PutObjectRequest(OSSUtil.HY_OSS_USER_UPLOAD_BUCKET_NAME, name, file));

            //TODO: url
            String url = "https://heye-user-upload.oss-cn-shanghai.aliyuncs.com/" + name;
            return ResultGenerator.genSuccessResult(url);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.toString());
            return null;
        }
    }
}
