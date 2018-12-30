package com.heye.crm.server.controller;

import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyCustomer;
import com.heye.crm.common.model.HyWeixin;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.common.utils.WeiXinCode;
import com.heye.crm.common.utils.WeiXinUtils;
import com.heye.crm.common.utils.RandomUtil;
import com.heye.crm.common.utils.SmsApi;
import com.heye.crm.common.utils.SmsApiResult;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.WeiXinReq;
import com.heye.crm.server.service.HyCustomerService;
import com.heye.crm.server.service.HyWeixinService;
import com.heye.crm.server.service.RedisPool;
import com.heye.crm.server.vo.HyCustomerVoWeixin;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/weixin")
public class HyWeiXinController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyWeiXinController.class);

    @Resource
    private RedisPool jedis;
    @Resource
    private HyWeixinService hyWeixinService;
    @Resource
    private HyCustomerService hyCustomerService;

    @Override
    public CRight getControllerCRight() {
        return CRight.UNKOWN;
    }

    /**
     * 获取微信sign
     *
     * @param req
     * @return
     */
    @PostMapping("/getWeiXinSign")
    public Result getWeiXinSign(@RequestBody WeiXinReq req) {
        LOGGER.info("[getWeiXinSign]req:" + new Gson().toJson(req));
        return ResultGenerator.genSuccessResult(WeiXinUtils.getWeiXinSign(req.getUrl()));
    }

    /**
     * 微信登陆
     * 如果hy_weixin表里有记录，则返回customer信息以及openId，否则返回data==null的SUCCESS结果。
     *
     * @param req: code
     * @return
     */
    @PostMapping("/wxLogin")
    public Result wxLogin(@RequestBody WeiXinReq req) {
        LOGGER.info("[wxLogin] req:" + new Gson().toJson(req));
        if (StringUtils.isEmpty(req.getCode())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        // 获取openId
        WeiXinCode weiXinCode = WeiXinUtils.getSessionKeyOropenid(req.getCode());
        if (weiXinCode == null) {
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        String openId = weiXinCode.getOpenId();

        // 关联wx表，有无记录
        boolean exist = true;
        Condition condition = new Condition(HyWeixin.class);
        condition.createCriteria().andEqualTo("openId", openId);
        List<HyWeixin> hyWeixins = null;
        try {
            hyWeixins = hyWeixinService.findByCondition(condition);
        } catch (Exception e) {
            LOGGER.warn("[wxLogin] find by condition failed:", e);
            exist = false;
        }

        if (hyWeixins == null || hyWeixins.size() == 0) {
            exist = false;
        }

        if (!exist) {
            // 没有，写入一条微信记录
            HyWeixin weixin = new HyWeixin();
            weixin.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            weixin.setOpenId(openId);

            try {
                hyWeixinService.save(weixin);
            } catch (Exception e) {
                LOGGER.warn("[wxLogin] save weixin failed", e);
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }
            Condition cond = new Condition(HyWeixin.class);
            cond.createCriteria()
                    .andEqualTo("openId", weixin.getOpenId());
            List<HyWeixin> hyWeixinList = hyWeixinService.findByCondition(cond);
            if (hyWeixinList == null || hyWeixinList.size() == 0) {
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }
            long hyWeiXinId = hyWeixinList.get(0).getWeixinId();
            weixin.setWeixinId(hyWeiXinId);

            return ResultGenerator.genSuccessResult(weixin);
        } else {
            // 有记录的
            if (hyWeixins.size() > 1) {
                // 未预料的多个结果
                LOGGER.warn("[wxLogin] openId: " + openId + " duplicated");
            }

            // 微信表中已经有该记录，但是没有填写客户信息时
            if (hyWeixins.get(0).getCtmId() == 0) {
                LOGGER.info("[wxLogin] hy_weixin already exists, but no customer info");
                return ResultGenerator.genSuccessResult(hyWeixins.get(0));
            }

            long ctmId = hyWeixins.get(0).getCtmId();
            long weixinId = hyWeixins.get(0).getWeixinId();

            // find by ctm id, 跟hy_custom表join
            HyCustomer hyCustomer = null;
            try {
                Condition cond = new Condition(HyCustomer.class);
                cond.createCriteria().andEqualTo("ctmId", ctmId);
                cond.selectProperties("storeId", "ctmName", "ctmAccountName", "ctmPasswd", "ctmSex", "ctmEmail", "ctmPicture", "ctmPhoneNum",
                        "ctmTelephone", "ctmBirthDay", "ctmProvince", "ctmCity", "managerName", "ctmLocation", "ctmDetailAddress",
                        "ctmZipCode", "ctmIdentity", "createdAt");

                List<HyCustomer> lists = hyCustomerService.findByCondition(cond);
                if (lists != null && lists.size() > 0) {
                    hyCustomer  = lists.get(0);
                }
            } catch (Exception e) {
                LOGGER.info(e.toString());
                return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
            }
            if (hyCustomer == null) {
                LOGGER.info("[wxLogin] customer not exist: " + ctmId);
                return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
            }
            return ResultGenerator.genSuccessResult(new HyCustomerVoWeixin(hyCustomer, weixinId));
        }
    }

    /**
     * 绑定手机号
     * <p>
     * 绑定手机号到当前微信号上（即把openId和ctmId写入hy_weixin表），
     * 并返回customer信息以及openId，并在hy_weixin中添加一条记录绑定openId与手机号，下次可以wxLogin；
     * 其中如果手机号不在customer里，则新建customer记录只保存手机号
     *
     * @param req: code, ctmTelephone, verifyCode
     * @return
     */
    @PostMapping("/bindTelephoneNum")
    public Result bindTelephoneNum(@RequestBody WeiXinReq req) {
        LOGGER.info("[bindTelephoneNum] req:" + new Gson().toJson(req));
        if (StringUtils.isEmpty(req.getCtmTelephone()) || req.getWeixinId() == 0
                || StringUtils.isEmpty(req.getVerifyCode())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        // 检验验证码
        String vCode = jedis.instance().get(Consts.HY_VERIFY_CODE_PREFIX + req.getCtmTelephone());
        if (!req.getVerifyCode().equals(vCode)) {
            return ResultGenerator.genFailResult(Consts.VERIFY_CODE_INVALID);
        } else {
            jedis.instance().del(Consts.HY_VERIFY_CODE_PREFIX + req.getCtmTelephone());
        }

        HyWeixin hyWeixin = hyWeixinService.findById(req.getWeixinId());
        if (hyWeixin == null) {
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }

        String openId = hyWeixin.getOpenId();
        long weixinId = hyWeixin.getWeixinId();
        // 查询hy_cumtomer表
        boolean exist = true;
        Condition condition = new Condition(HyCustomer.class);
        condition.createCriteria().andEqualTo("ctmTelephone", req.getCtmTelephone());
        List<HyCustomer> hyCustomers = null;
        try {
            condition.selectProperties("storeId", "ctmName", "ctmAccountName", "ctmPasswd", "ctmSex", "ctmEmail", "ctmPicture", "ctmPhoneNum",
                    "ctmTelephone", "ctmBirthDay", "ctmProvince", "ctmCity", "managerName", "ctmLocation", "ctmDetailAddress",
                    "ctmZipCode", "ctmIdentity", "createdAt");
            hyCustomers = hyCustomerService.findByCondition(condition);
        } catch (Exception e) {
            exist = false;
        }
        if (hyCustomers == null || hyCustomers.size() == 0) {
            exist = false;
        }

        if (!exist) {
            // 没有绑定过该手机号的话，在hy_customer新建一条记录，只保存手机号
            HyCustomer hyCustomer = new HyCustomer();
            hyCustomer.setCtmTelephone(req.getCtmTelephone());
            hyCustomer.setStoreId(1);
            try {
                hyCustomerService.save(hyCustomer);
            } catch (Exception e) {
                LOGGER.info(e.toString());
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }
            // 找出新建的HyCustomer的ID
            Condition condition1 = new Condition(HyCustomer.class);
            condition1.createCriteria().andEqualTo("ctmTelephone", req.getCtmTelephone());
            List<HyCustomer> hyCustomers1 = null;
            try {
                condition1.selectProperties("storeId", "ctmName", "ctmAccountName", "ctmPasswd", "ctmSex", "ctmEmail", "ctmPicture", "ctmPhoneNum",
                        "ctmTelephone", "ctmBirthDay", "ctmProvince", "ctmCity", "managerName", "ctmLocation", "ctmDetailAddress",
                        "ctmZipCode", "ctmIdentity", "createdAt");
                hyCustomers1 = hyCustomerService.findByCondition(condition1);
            } catch (Exception e) {
                LOGGER.info(e.toString());
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }
            hyCustomer = hyCustomers1.get(0);

            long ctmId = hyCustomer.getCtmId();
            // 更新hy_weixin

            Condition cond = new Condition(HyWeixin.class);
            cond.createCriteria()
                    .andEqualTo("openId", openId);
            List<HyWeixin> hyWeixinList = hyWeixinService.findByCondition(cond);
            if (hyWeixinList == null || hyWeixinList.size() == 0) {
                LOGGER.info("[bindTelephoneNum] no such openId");
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }

            try {
                hyWeixin.setCtmId(ctmId);
                hyWeixinService.update(hyWeixin);
            } catch (Exception e) {
                LOGGER.info("[bindTelephoneNum] update failed:" + e.toString());
                return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
            }

            return ResultGenerator.genSuccessResult(new HyCustomerVoWeixin(hyCustomer, weixinId));
        } else {
            // hy_customer表之前有该手机号，直接返回hy_customer join hy_weixin
            if (hyCustomers.size() > 1) {
                // 未预料的多个结果
                LOGGER.info("[bindTelephoneNum] hyCustomer telephone: " + req.getCtmTelephone() + " duplicated");
            }
            HyCustomer hyCustomer = hyCustomers.get(0);

            return ResultGenerator.genSuccessResult(new HyCustomerVoWeixin(hyCustomer, weixinId));
        }
    }

    /**
     * 更新用户信息
     * <p>
     * 更新用户的各项信息
     *
     * @param req
     * @return
     */
    @PostMapping("/updateCustomer")
    public Result updateCustomer(@RequestBody WeiXinReq req) {
        LOGGER.info("[updateCustomer] req:" + new Gson().toJson(req));
        if (req.getCtmId() < 0
                || req.getCtmName() == null
                || req.getCtmTelephone() == null || req.getCtmProvince() == null
                || req.getCtmLocation() == null) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        // 保存到hy_customer
        HyCustomer hyCustomer = req.toHyCustomer();
        try {
            hyCustomer.setCtmState(Consts.HY_ADMIN_ACCOUNT_STATE_OK);
            hyCustomerService.update(hyCustomer);
        } catch (Exception e) {
            LOGGER.info("[updateCustomer] update failed:" + e.toString());
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 把验证码发送给客户手机
     *
     * @param req ctmTelephone
     * @return
     */
    @Deprecated
    @PostMapping("/sendPhoneIdentifyCode")
    public Result sendPhoneIdentifyCode(@RequestBody WeiXinReq req) {
        LOGGER.info("[sendPhoneIdentifyCode] req:" + new Gson().toJson(req));
        if (StringUtils.isEmpty(req.getCtmTelephone())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        // 生成验证码
        String verifyCode = RandomUtil.getRandNum(6);

        // 将手机号+验证码 写入redis，供校验时使用
        String setexRes = jedis.instance().setex(Consts.HY_VERIFY_CODE_PREFIX + req.getCtmTelephone(),
                5 * 60, verifyCode);
        if (!"ok".equalsIgnoreCase(setexRes)) {
            LOGGER.warn("[sendPhoneIdentifyCode] set redis failed :" +  setexRes);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        // 发验证码
        String text = SmsApi.HY_SMS_DEFAULT_LABEL + "您的验证码是" + verifyCode + "。如非本人操作，请忽略本短信";
        SmsApiResult smsApiResult = SmsApi.sendSms(text, req.getCtmTelephone());
        if (smsApiResult == null || smsApiResult.getCode() != 0) {
            LOGGER.warn("[sendPhoneIdentifyCode] send sms failed:" + smsApiResult.toString());
            return ResultGenerator.genFailResult(Consts.SEND_SMS_FAILED);
        }

        return ResultGenerator.genSuccessResult(Consts.SUSSESS);
    }
}
