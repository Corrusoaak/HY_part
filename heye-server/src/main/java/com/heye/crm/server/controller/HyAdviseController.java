package com.heye.crm.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyAdvise;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyAdviseReq;
import com.heye.crm.server.service.HyAdviseService;
import com.heye.crm.server.service.HyAdviseVoCustomerService;
import com.heye.crm.server.vo.HyAdviseVoCustomer;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/advise")
public class HyAdviseController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyAdminController.class);

    @Resource
    private HyAdviseService hyAdviseService;
    @Resource
    private HyAdviseVoCustomerService hyAdviseVoCustomerService;

    @Override
    public CRight getControllerCRight() {
        return CRight.PRODUCT_ADVISE;
    }

    /**
     * 创建针对proId产品的投诉建议
     *
     * @param req
     * @return
     */
    @PostMapping("/addAdvise")
    public Result addAdvise(@RequestBody HyAdviseReq req) {
        LOGGER.info("[addAdviseToPro] Req json: {}", new Gson().toJson(req));

        if (req.getAdviseDesc() == null || req.getAdviseDesc().isEmpty()) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        try {
            HyAdvise hyAdvise = new HyAdvise();
            hyAdvise.setAdviseDesc(req.getAdviseDesc());
            if (req.getAdvisePictures() != null && !req.getAdvisePictures().isEmpty()) {
                hyAdvise.setAdvisePictures(req.getAdvisePictures());
            }
            hyAdvise.setAdviseState(Consts.HY_ADVISE_STATE_INIT);
            hyAdvise.setAdviseType(req.getAdviseType());
            hyAdvise.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            hyAdvise.setCtmId(req.getCtmId());

            hyAdviseService.save(hyAdvise);
        } catch (Exception e) {
            LOGGER.warn("[addAdviseToPro] save hy_advise failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        return ResultGenerator.genSuccessResult(Consts.SUSSESS);
    }

    /**
     * 获取投诉列表，根据ctmId，客户Id
     *
     * @param req
     * @return
     */
    @PostMapping("/adviseList")
    public Result adviseList(@RequestBody HyAdviseReq req) {
        LOGGER.info("[adviseList] Req json: {}", new Gson().toJson(req));
        if (!checkPageReq(req) || req.getCtmId() < 0) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        Condition cond1 = new Condition(HyAdvise.class);
        cond1.createCriteria()
                .andEqualTo("ctmId", req.getCtmId());

        List<HyAdvise> results = null;
        try {
            PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
            results = hyAdviseService.findByCondition(cond1);
        } catch (Exception e) {
            LOGGER.warn("[adviseList] advise list failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        PageInfo pageInfo = new PageInfo(results);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 根据adviseId获取投诉详情
     *
     * @param req
     * @return
     */
    @PostMapping("/getAdviseByID")
    public Result getAdviseByID(@RequestBody HyAdviseReq req) {
        LOGGER.info("[getAdviseByID] Req json: {}", new Gson().toJson(req));

        if (!checkPageReq(req) || req.getAdviseId() < 0) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        Condition cond1 = new Condition(HyAdvise.class);
        cond1.createCriteria()
                .andEqualTo("adviseId", req.getAdviseId());

        List<HyAdvise> results = null;
        try {
            PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
            results = hyAdviseService.findByCondition(cond1);
        } catch (Exception e) {
            LOGGER.warn("[getAdviseByID] advise by id failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        if (results == null || results.size() == 0) {
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }

        PageInfo pageInfo = new PageInfo(results);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 回复更新投诉建议，更改投诉状态
     *
     * @param req
     * @return
     */
    @PostMapping("/replyAdvise")
    public Result replyAdvise(@RequestBody HyAdviseReq req) {
        LOGGER.info("[replyAdvise] Req json: {}", new Gson().toJson(req));

        if (!checkPageReq(req) || req.getAdviseId() < 0 || req.getAviseReplies() == null || req.getAviseReplies().isEmpty()) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        Condition cond = new Condition(HyAdvise.class);
        cond.createCriteria()
                .andEqualTo("adviseId", req.getAdviseId());

        List<HyAdvise> results = null;
        try {
            PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
            results = hyAdviseService.findByCondition(cond);

            if (results == null || results.size() == 0) {
                return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
            }

            HyAdvise hyAdvise = results.get(0);
            hyAdvise.setAdviseState(Consts.HY_ADVISE_STATE_DONE);
            hyAdvise.setAviseReplies(req.getAviseReplies());
            hyAdviseService.update(hyAdvise);
        } catch (Exception e) {
            LOGGER.warn("[replyAdvise] advise update id failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        PageInfo pageInfo = new PageInfo(results);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 更新投诉建议状态
     *
     * @param req
     * @return
     */
    @PostMapping("/updateAdvise")
    public Result updateAdvise(@RequestBody HyAdviseReq req) {
        LOGGER.info("[updateAdvise] Req json: {}", new Gson().toJson(req));

        if (!checkPageReq(req) || req.getAdviseId() < 0) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        List<Integer> arr = Arrays.asList(Consts.HY_ADVISE_STATE_DOING, Consts.HY_ADVISE_STATE_DONE, Consts.HY_ADVISE_STATE_INIT);
        if (!arr.contains(req.getAdviseState())) {
            LOGGER.warn("[updateAdvise] no such advise state");
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        Condition cond = new Condition(HyAdvise.class);
        cond.createCriteria()
                .andEqualTo("adviseId", req.getAdviseId());

        List<HyAdvise> results = null;
        try {
            PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
            results = hyAdviseService.findByCondition(cond);

            if (results == null || results.size() == 0) {
                return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
            }

            HyAdvise hyAdvise = results.get(0);
            hyAdvise.setAdviseState(req.getAdviseState());
            hyAdviseService.update(hyAdvise);
        } catch (Exception e) {
            LOGGER.warn("[updateAdvise] advise update id failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        PageInfo pageInfo = new PageInfo(results);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 搜索投诉列表，按照状态、时间、类型搜索
     *
     * @param req: adviseState, 0:全部状态, 1: 未处理, 2: 处理中, 3: 已处理;
     * @param req: filterByDateType, 0:全部, 1: 近一周, 2: 近一月, 3: 近一年;
     * @param req: adviseType, 0:全部分类, 1: 产品使用问题, 2: 销售使用问题;
     * @param req: 可选 managerName, ctmName, location
     * @param req: pageNo, pageSize
     * @return
     */
    @PostMapping("/searchAdviseList")
    public Result searchAdviseList(@RequestBody HyAdviseReq req) {
        LOGGER.info("[searchAdviseList] Req json: {}", new Gson().toJson(req));

        final int adviseState = req.getAdviseState();
        final int filterByDateType = req.getFilterByDateType();
        final int adviseType = req.getAdviseType();
        final String managerName = req.getManagerName();
        final String ctmName = req.getCtmName();
        final String location = req.getLocation();

        if (!checkPageReq(req) || adviseState < 0 || adviseState > 3
                || filterByDateType < 0 || filterByDateType > 3
                || adviseType < 0 || adviseType > 2) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        //TODO: check session?

        // transform filterByDateType to timeStamp
        Timestamp nowTimeStamp = new Timestamp(System.currentTimeMillis());
        Timestamp timeStamp = null;
        long temp;
        switch (filterByDateType) {
        case 0:
            break;
        case 1: // 近一周
            temp = nowTimeStamp.getTime() - 86400000L * 7;
            timeStamp = new Timestamp(temp);
            break;
        case 2: // 近一月
            temp = nowTimeStamp.getTime() - 86400000L * 30;
            timeStamp = new Timestamp(temp);
            break;
        case 3: // 近一年
            temp = nowTimeStamp.getTime() - 86400000L * 365;
            timeStamp = new Timestamp(temp);
            break;
        }

        Map<String, Object> map = new HashMap<>();
        if (adviseState != 0) {
            map.put("adviseState", adviseState);
        }
        if (timeStamp != null) {
            map.put("timeStamp", timeStamp);
        }
        if (adviseType != 0) {
            map.put("adviseType", adviseType);
        }
        if (!StringUtils.isEmpty(managerName)) {
            map.put("managerName", managerName);
        }
        if (!StringUtils.isEmpty(ctmName)) {
            map.put("ctmName", ctmName);
        }
        if (!StringUtils.isEmpty(location)) {
            map.put("location", location);
        }

        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
        List<HyAdviseVoCustomer> list = hyAdviseVoCustomerService.searchAdviseList(map);
        PageInfo pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
