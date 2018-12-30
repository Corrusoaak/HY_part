package com.heye.crm.server.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.utils.RandomUtil;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.common.utils.SmsApi;
import com.heye.crm.common.utils.SmsApiResult;
import com.heye.crm.common.utils.ValidateCodeUtil;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.SMSReq;
import com.heye.crm.server.service.RedisPool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/sms")
public class HySmsController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HySmsController.class);

    @Resource
    private RedisPool jedis;

    @Override
    public CRight getControllerCRight() {
        return CRight.UNKOWN;
    }

    /**
     * 响应验证码页面
     */
    @RequestMapping("/getImageCode")
    public Result getImageCode(HttpServletRequest request, HttpServletResponse response, @RequestParam("adminUserId") String userId) throws Exception {
        LOGGER.info("[getImageCode] req:" + new Gson().toJson(userId));

        StringBuilder sb = new StringBuilder();
        sb.append("data:image/png;base64,");
        try {
            ValidateCodeUtil vCode = new ValidateCodeUtil(120, 40, 4, 100);

            String redisK = Consts.CTM_REDIS_SESSION_VALIDATE_CODE_PREFIX_KEY + userId;
            boolean bolRet = setSession(redisK, vCode.getCode());
            if (!bolRet) {
                LOGGER.warn("[getImageCode] set validate code failed");
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            vCode.write(baos);
            byte[] bytes = baos.toByteArray();
            final Base64.Encoder encoder = Base64.getEncoder();
            final String encodedText = encoder.encodeToString(bytes);
            sb.append(encodedText);
        } catch (Exception e) {
            LOGGER.warn("[getImageCode] generate validate code failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        return ResultGenerator.genSuccessResult(sb.toString());
    }

    /**
     * 把验证码发送给客户手机
     *
     * @param req ctmTelephone
     * @return
     */
    @PostMapping("/sendPhoneIdentifyCode")
    public Result sendPhoneIdentifyCode(@RequestBody SMSReq req) {
        LOGGER.info("[sendPhoneIdentifyCode] req:" + new Gson().toJson(req));
        if (StringUtils.isEmpty(req.getTelephoneNum()) || StringUtils.isEmpty(req.getImageCode())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        String redisV = getSession(Consts.CTM_REDIS_SESSION_VALIDATE_CODE_PREFIX_KEY + req.getAdminUserId());
        if (StringUtils.isEmpty(redisV)) {
            LOGGER.warn("[sendPhoneIdentifyCode] get from redis result empty:" + req.getAdminSessionId());
            return ResultGenerator.genFailResult(Consts.VALIDATE_VERIFY_CODE_FAILED);
        }

        LOGGER.info("[sendPhoneIdentifyCode] redisV:" + redisV  + ", code:" + req.getImageCode());
        if (!redisV.equalsIgnoreCase(req.getImageCode())) {
            return ResultGenerator.genFailResult(Consts.VALIDATE_VERIFY_CODE_FAILED);
        }

        // 生成验证码
        String verifyCode = RandomUtil.getRandNum(6);
        // 将手机号+验证码 写入redis，供校验时使用
        String setexRes = jedis.instance().setex(Consts.HY_VERIFY_CODE_PREFIX + req.getTelephoneNum(),
                5 * 60, verifyCode);
        if (!"ok".equalsIgnoreCase(setexRes)) {
            LOGGER.warn("[sendPhoneIdentifyCode] set redis failed :" +  setexRes);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        // 发验证码
        String text = SmsApi.HY_SMS_DEFAULT_LABEL + "您的验证码是" + verifyCode + "。如非本人操作，请忽略本短信";
        SmsApiResult smsApiResult = SmsApi.sendSms(text, req.getTelephoneNum());

        LOGGER.info("[sendPhoneIdentifyCode] send sms info:", smsApiResult);
        if (smsApiResult == null || smsApiResult.getCode() != 0) {
            LOGGER.warn("[sendPhoneIdentifyCode] send sms failed:" + smsApiResult.toString());
            return ResultGenerator.genFailResult(Consts.SEND_SMS_FAILED);
        }

        return ResultGenerator.genSuccessResult(Consts.SUSSESS);
    }
}

