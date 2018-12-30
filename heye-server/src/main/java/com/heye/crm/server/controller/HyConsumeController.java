package com.heye.crm.server.controller;

import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyConsume;
import com.heye.crm.common.model.HyProduct;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyConsumeReq;
import com.heye.crm.server.service.HyConsumeService;
import com.heye.crm.server.service.HyProductService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/consume")
public class HyConsumeController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyConsumeController.class);

    @Override
    public CRight getControllerCRight() {
        return CRight.UNKOWN;
    }

    @Resource
    private HyProductService hyProductService;

    @Resource
    private HyConsumeService hyConsumeService;

    // 1. 获取个人购买产品列表
    @PostMapping("/addConsume")
    public Result addConsume(@RequestBody HyConsumeReq req) {
        if (StringUtils.isEmpty(req.getProBarCode())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        Condition condition = new Condition(HyProduct.class);
        condition.createCriteria().andEqualTo("proBarCode", req.getProBarCode());

        List<HyProduct> lists = hyProductService.findByCondition(condition);
        if (lists == null || lists.size() == 0) {
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }
        HyProduct product = lists.get(0);

        HyConsume consume = new HyConsume();
        consume.setProId(product.getProId());
        consume.setCtmId(req.getCtmId());
        try {
            hyConsumeService.save(consume);
        } catch (Exception e) {
            LOGGER.warn("[addConsume] add consume failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        return ResultGenerator.genSuccessResult(consume);
    }
}
