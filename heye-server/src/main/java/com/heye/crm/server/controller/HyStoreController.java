package com.heye.crm.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyStore;
import com.heye.crm.common.model.HyStoreWithHyUser;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyStoreReq;
import com.heye.crm.server.service.HyStoreService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/store")
public class HyStoreController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyStoreController.class);
    @Resource
    private HyStoreService hyStoreService;

    @Override
    public CRight getControllerCRight() {
        return CRight.UNKOWN;
    }

    @PostMapping("/getHyStoreList")
    @ResponseBody
    public Result getHyStoreList(@RequestBody HyStoreReq req) {
        LOGGER.info("[getHyStoreList] Req json: {}", new Gson().toJson(req));

        Condition condition = new Condition(HyStore.class);
        condition.createCriteria()
                .andEqualTo("storeState", Consts.HY_STORE_STATE_OK);

        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");

        condition.selectProperties("storeId", "storeManagerId", "storePhoneNum", "storeTelephone", "storeName", "storeProvince",
                "storeCity", "storePosition", "storeAddress", "storeState", "createdAt");
        List<HyStore> list = hyStoreService.findByCondition(condition);
        if (list.size() == 0) {
            return ResultGenerator.genFailResult(Consts.NOT_FOUND);
        }
        PageInfo pageInfo = new PageInfo(list);

        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/getHyStoreListByTelephone")
    @ResponseBody
    public Result getHyStoreListByTelephone(@RequestBody HyStoreReq req) {
        LOGGER.info("[getHyStoreListByLoc] Req json: {}", new Gson().toJson(req));

        if (StringUtils.isEmpty(req.getStoreTelephone())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        try {
            Map<String, String> cond = Maps.newHashMap();
            cond.put("storeTelephone", req.getStoreTelephone().trim());

            List<HyStoreWithHyUser> list = hyStoreService.getStoreInfoWithHyUser(cond);
            if (list.size() == 0) {
                return ResultGenerator.genFailResult(Consts.NOT_FOUND);
            }

            return ResultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(Consts.SELECT_FAILED);
        }
    }

    @PostMapping("/getHyStoreListByLoc")
    @ResponseBody
    public Result getHyStoreListByLoc(@RequestBody HyStoreReq req) {
        LOGGER.info("[getHyStoreListByLoc] Req json: {}", new Gson().toJson(req));

        if (StringUtils.isEmpty(req.getStoreProvince()) || StringUtils.isEmpty(req.getStoreCity()) || StringUtils.isEmpty(req.getStorePosition())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        Map cond = new HashMap();
        cond.put("storeProvince", req.getStoreProvince());
        cond.put("storeCity", req.getStoreCity());
        cond.put("storePosition", req.getStorePosition());

        List<HyStore> list = new ArrayList<>();
        try {
            PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
            list = hyStoreService.getStoreInfoWithHyUser(cond);
        } catch (Exception e) {
            LOGGER.warn("[getHyStoreListByLoc] select failed:", e);
            return ResultGenerator.genFailResult(Consts.SELECT_FAILED);
        }

        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/getHyStoreById")
    @ResponseBody
    public Result getHyStoreById(@RequestBody HyStoreReq req) {
        LOGGER.info("[getHyStoreById] Req json: {}", new Gson().toJson(req));

        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");

        Condition cond = new Condition(HyStore.class);
        cond.selectProperties("storeId", "storeManagerId", "storePhoneNum", "storeTelephone", "storeName", "storeProvince",
                "storeCity", "storePosition", "storeAddress", "storeState", "createdAt");
        cond.createCriteria()
                .andEqualTo("storeId", req.getStoreId());

        List<HyStore> hyStore = hyStoreService.findByCondition(cond);
        if (hyStore == null) {
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        PageInfo pageInfo = new PageInfo(Arrays.asList(hyStore));

        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
