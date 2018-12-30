package com.heye.crm.server.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyFollow;
import com.heye.crm.common.utils.CommonUtil;
import com.heye.crm.common.utils.HttpUtil;
import com.heye.crm.common.utils.MD5Util;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyFollowReq;
import com.heye.crm.server.service.HyFollowService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/follow")
public class HyFollowController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyFollowController.class);

    @Override
    public CRight getControllerCRight() {
        return CRight.PRODUCT_TRACK;
    }

    @Resource
    private HyFollowService hyFollowService;

    /**
     * 创建针对proId产品的投诉建议
     *
     * @param req
     * @return
     */
    @PostMapping("/addFollow")
    public Result addFollow(@RequestBody HyFollowReq req) {
        LOGGER.info("[addFollow] Req json: {}", new Gson().toJson(req));

        if (StringUtils.isEmpty(req.getDeliverId()) || StringUtils.isEmpty(req.getExpressId()) || StringUtils.isEmpty(req.getOrderId())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        HyFollow hyFollow = new HyFollow();
        try {
            hyFollow.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            hyFollow.setDeliverHouse(req.getDeliverHouse());
            hyFollow.setDeliverId(req.getDeliverId());
            hyFollow.setOrderId(req.getOrderId());
            hyFollow.setExpressId(req.getExpressId());
            hyFollow.setExpressCompany(req.getExpressCompany());
            hyFollow.setStoreDetailAddress(req.getStoreDetailAddress());
            hyFollow.setStoreManager(req.getStoreManager());
            hyFollow.setStoreTelephone(req.getStoreTelephone());
            hyFollow.setStoreId(req.getStoreId());

            hyFollowService.save(hyFollow);
        } catch (Exception e) {
            LOGGER.warn("[addTrackToPro] save hy_track failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        return ResultGenerator.genSuccessResult(hyFollow);
    }

    /**
     * @param req: pageSize, pageNo,
     *             optional: orderId, storeManager, startStamp, endStamp
     * @return
     */
    @PostMapping("/followList")
    public Result followList(@RequestBody HyFollowReq req) {
        LOGGER.info("[trackList] Req json: {}", new Gson().toJson(req));
        if (!checkPageReq(req)) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        List<HyFollow> results = null;
        try {
            Condition cond = new Condition(HyFollow.class);
            Example.Criteria criteria = cond.createCriteria();
            if (!StringUtils.isEmpty(req.getOrderId())) {
                criteria.andLike("orderId", CommonUtil.getEclipseLike(req.getOrderId()));
            }
            if (!StringUtils.isEmpty(req.getStoreManager())) {
                criteria.andLike("storeManager", CommonUtil.getEclipseLike(req.getStoreManager()));
            }
            if (req.getStartStamp() != null) {
                criteria.andGreaterThanOrEqualTo("createdAt", new Timestamp(req.getStartStamp()));
            }
            if (req.getEndStamp() != null) {
                criteria.andLessThanOrEqualTo("createdAt", new Timestamp(req.getEndStamp()));
            }

            PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
            results = hyFollowService.findByCondition(cond);
        } catch (Exception e) {
            LOGGER.warn("track list failed:", e);
            return ResultGenerator.genFailResult(Consts.SELECT_FAILED);
        }

        PageInfo pageInfo = new PageInfo<>(results);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 根据快递公司编码和快递单号查询物流信息
     */
    @PostMapping("/expressInfo")
    public Result expressInfo(@RequestBody HyFollowReq req) {
        LOGGER.info("[addFollow] Req json: {}", new Gson().toJson(req));

        HashMap<String, String> codeToName = new HashMap<>();
        codeToName.put("安能物流", "annengwuliu");
        codeToName.put("德邦速运", "debangwuliu");
        codeToName.put("顺丰速运", "shunfeng");
        if (StringUtils.isEmpty(req.getExpressCompany()) || StringUtils.isEmpty(req.getExpressId())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        String customer = "942D4D331CE24DC1B8E038D1E6691348";
        String key = "tEstrbHmnXja9557";
        String expressCom = codeToName.get(req.getExpressCompany());
        JSONObject jsonStr = null;

        String param = "{\"com\":\"" + expressCom + "\",\"num\":\"" + req.getExpressId() + "\",\"from\":\"\",\"to\":\"\",\"resultv2\":0}";
        String sign = MD5Util.encrypt(param + key + customer);
        HashMap params = new HashMap();
        params.put("param", param);
        params.put("sign", sign);
        params.put("customer", customer);
        jsonStr = null;
        try {
            byte[] tmp = HttpUtil.doPost("http://poll.kuaidi100.com/test/poll/query.do", params, "utf-8");
            String s = new String(tmp);
            jsonStr = JSONObject.parseObject(s);
            //System.out.println(s);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(jsonStr);
    }

    /**
     * 根据快递单号猜测快递公司编码
     */
    @PostMapping("/expressCompany")
    public Result expressCompany(@RequestBody HyFollowReq req) {
        LOGGER.info("[expressCompany] Req json: {}", new Gson().toJson(req));

        if (StringUtils.isEmpty(req.getExpressId())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        String num = req.getExpressId();
        String key = "tEstrbHmnXja9557";
        HashMap params = new HashMap();
        params.put("num", num);
        params.put("key", key);

        JSONArray jsonArray = null;
        try {
            byte[] tmp = HttpUtil.doPost("http://poll.kuaidi100.com/test/autonumber/auto", params, "utf-8");
            String s = new String(tmp);
            jsonArray = JSONArray.parseArray(s);
            if (jsonArray.size() == 0) {
                return ResultGenerator.genFailResult(Consts.NO_SUCH_OBJECT);
            }
            //System.out.println(s);
        } catch (Exception e) {
            LOGGER.warn("[expressCompany] get express info failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        return ResultGenerator.genSuccessResult(jsonArray.get(0));
    }
}
