package com.heye.crm.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyProduct;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyProductReq;
import com.heye.crm.server.service.HyProductService;
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
@RequestMapping("/api/v1/product")
public class HyProductController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyProductController.class);

    @Resource
    private HyProductService hyProductService;

    @Override
    public CRight getControllerCRight() {
        return CRight.UNKOWN;
    }

    /**
     * 获取产品列表
     *
     * @param req
     * @return
     */
    @PostMapping("/productList")
    public Result productList(@RequestBody HyProductReq req) {
        LOGGER.info("[productList] Req json: {}", new Gson().toJson(req));

        Condition cond = new Condition(HyProduct.class);
        cond.createCriteria()
                .andEqualTo("proState", Consts.HY_PRODUCTION_STATE_OK)
                .andEqualTo("proDisplay", Consts.HY_PRODUCTION_DISPLAY_OK);

        List<HyProduct> results = null;
        try {
            PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
            results = hyProductService.findByCondition(cond);
        } catch (Exception e) {
            LOGGER.warn("[productList] product list failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        PageInfo pageInfo = new PageInfo(results);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
