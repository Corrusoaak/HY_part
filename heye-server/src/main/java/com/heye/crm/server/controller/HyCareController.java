package com.heye.crm.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyCare;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyCareReq;
import com.heye.crm.server.service.HyCareService;
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
import java.sql.Timestamp;
import java.util.List;

/**
 * @author : lishuming
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/care")
public class HyCareController extends Controller<HyCareReq> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyCareController.class);

    @Resource
    private HyCareService hyCareService;

    @Override
    public CRight getControllerCRight() {
        return CRight.CUSTOM_CARE;
    }

    private boolean ifCareExist(Long id) {
        try {
            HyCare hyCare = hyCareService.findById(id);
            return hyCare != null;
        } catch (Exception e) {
            LOGGER.warn("find care by Id failed:", e);
            return false;
        }
    }

    /**
     * 获取节日列表
     *
     * @param req: pageNo, pageSize
     * @return
     */
    @PostMapping("/getCustomCareList")
    @ResponseBody
    public Result getCustomCareList(@RequestBody HyCareReq req) {
        LOGGER.info("[getCustomCareList] req:" + new Gson().toJson(req));

        if (!checkPageReq(req)) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        String checkResult = checkSessionAndRight(req);
        if (checkResult != null) {
            ResultGenerator.genFailResult(checkResult);
        }

        Condition cond = new Condition(HyCare.class);
        cond.createCriteria();

        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
        List<HyCare> cares = hyCareService.findByCondition(cond);

        if (cares == null || cares.size() == 0) {
            return ResultGenerator.genFailResult(Consts.SELECT_FAILED);
        }
        PageInfo pageInfo = new PageInfo(cares);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 添加节日
     */
    @PostMapping("/addCustomCare")
    @ResponseBody
    public Result addCustomCare(@RequestBody HyCareReq req) {
        LOGGER.info("[addCustomCare] req:" + new Gson().toJson(req));

        String checkResult = checkSessionAndRight(req);
        if (checkResult != null) {
            ResultGenerator.genFailResult(checkResult);
        }

        if (StringUtils.isEmpty(req.getFestName())
                || req.getFestDate() == null
                || StringUtils.isEmpty(req.getSendTime())
                || StringUtils.isEmpty(req.getSendDesc())
                || req.getCtmRangeType() == null) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        try {
            // 检测节日是否已经存在
//            Condition condition = new Condition(HyCare.class);
//            condition.createCriteria().andEqualTo("festName", req.getFestName());
//            List<HyCare> results = hyCareService.findByCondition(condition);
//            if (results.size() > 0) {
//                return ResultGenerator.genFailResult(Consts.ALREADY_EXISTED_IN_DB);
//            }

            HyCare hyCare = req.toHyCare();
            hyCare.setSendState(Consts.HY_CARE_SEND_STATE_INIT);
            hyCare.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            hyCareService.save(hyCare);
            return ResultGenerator.genSuccessResult(Consts.SUSSESS);
        } catch (Exception e) {
            LOGGER.info("[addCustomCare] add custom care fail, exception:" + new Gson().toJson(req));
            return ResultGenerator.genFailResult(Consts.INSERT_FAILED);
        }
    }

    /**
     * 更新节日
     */
    @PostMapping("/updateCustomCare")
    @ResponseBody
    public Result updateCustomCare(@RequestBody HyCareReq req) {
        LOGGER.info("[updateCustomCare] req:" + new Gson().toJson(req));

        String checkResult = checkSessionAndRight(req);
        if (checkResult != null) {
            ResultGenerator.genFailResult(checkResult);
        }

        if (req.getCareId() < 0 || StringUtils.isEmpty(req.getFestName())
                || req.getFestDate() == null
                || StringUtils.isEmpty(req.getSendTime())
                || StringUtils.isEmpty(req.getSendDesc())
                || req.getCtmRangeType() == null) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        if (!ifCareExist(req.getCareId())) {
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }

        HyCare hycare = req.toHyCare();
        hycare.setCareId(req.getCareId());
        LOGGER.info(hycare.toString());
        try {
            hyCareService.update(hycare);
            return ResultGenerator.genSuccessResult(Consts.SUSSESS);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(Consts.UPDATE_FAILED);
        }
    }

    /**
     * 删除节日列表
     */
    @PostMapping("/deleteCustomCare")
    @ResponseBody
    public Result deleteCustomCare(@RequestBody HyCareReq req) {
        LOGGER.info("[deleteCustomCare] req:" + new Gson().toJson(req));

        String checkResult = checkSessionAndRight(req);
        if (checkResult != null) {
            ResultGenerator.genFailResult(checkResult);
        }

        if (!ifCareExist(req.getCareId())) {
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }

        // 客户生日不能删除
        if (req.getCareId() == 1) {
            return ResultGenerator.genFailResult(Consts.CAN_NOT_DELETE);
        }

        try {
            hyCareService.deleteById(req.getCareId());
            return ResultGenerator.genSuccessResult(Consts.SUSSESS);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(Consts.DELETE_FAILED);
        }
    }
}
