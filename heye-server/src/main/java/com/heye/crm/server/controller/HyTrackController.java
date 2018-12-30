package com.heye.crm.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyCustomer;
import com.heye.crm.common.model.HyProduct;
import com.heye.crm.common.model.HyTrack;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyTrackReq;
import com.heye.crm.server.service.HyCustomerService;
import com.heye.crm.server.service.HyProductService;
import com.heye.crm.server.service.HyTrackService;
import com.heye.crm.server.service.HyTrackVoCustomerService;
import com.heye.crm.server.vo.HyTrackVoCustomer;
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
@RequestMapping("/api/v1/track")
public class HyTrackController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyTrackController.class);
    // 1. 根据购买的商品，填写售后
    // 2. 获取售后列表

    // same to 投诉建议
    @Override
    public CRight getControllerCRight() {
        return CRight.PRODUCT_TRACK;
    }

    @Resource
    private HyTrackService hyTrackService;
    @Resource
    private HyCustomerService hyCustomerService;
    @Resource
    private HyProductService hyProductService;
    @Resource
    private HyTrackVoCustomerService hyTrackVoCustomerService;

    /**
     * 创建针对proId产品的投诉建议
     *
     * @param req: ctmId, proId, trackDesc, trackPictures, trackType
     * @return
     */
    @PostMapping("/addTrackToPro")
    public Result addTrackToPro(@RequestBody HyTrackReq req) {
        LOGGER.info("[addTrackToPro] Req json: {}", new Gson().toJson(req));

        if (req.getTrackDesc() == null || req.getTrackDesc().isEmpty()) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        // check if ctmId & proId are valid
        try {
            HyCustomer hyCustomer = hyCustomerService.findById(req.getCtmId());
            if (hyCustomer == null) {
                return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
            }
            HyProduct hyProduct = hyProductService.findById(req.getProId());
            if (hyProduct == null) {
                return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
            }
        } catch (Exception e) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        HyTrack hyTrack = new HyTrack();
        try {
            hyTrack.setTrackDesc(req.getTrackDesc());
            if (req.getTrackPictures() != null && !req.getTrackPictures().isEmpty()) {
                hyTrack.setTrackPictures(req.getTrackPictures());
            }
            hyTrack.setTrackState(Consts.HY_ADVISE_STATE_INIT);
            hyTrack.setTrackType(req.getTrackType());
            hyTrack.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            hyTrack.setCtmId(req.getCtmId());
            hyTrack.setProId(req.getProId());

            hyTrackService.save(hyTrack);
        } catch (Exception e) {
            LOGGER.warn("[addTrackToPro] save hy_track failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 获取投诉列表，根据ctmId，客户Id
     *
     * @param req: pageNo, pageSize, ctmId
     * @return
     */
    @Deprecated
    @PostMapping("/searchTrackList")
    public Result searchTrackList(@RequestBody HyTrackReq req) {
        LOGGER.info("[trackList] Req json: {}", new Gson().toJson(req));
        if (!checkPageReq(req) || req.getCtmId() < 0) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        Condition cond1 = new Condition(HyTrack.class);
        cond1.createCriteria()
                .andEqualTo("ctmId", req.getCtmId());

        List<HyTrack> results = null;
        try {
            PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
            results = hyTrackService.findByCondition(cond1);
        } catch (Exception e) {
            LOGGER.warn("track list failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        PageInfo<HyTrack> pageInfo = new PageInfo<>(results);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 根据trackId获取投诉详情
     *
     * @param req: trackId
     * @return
     */
    @PostMapping("/getTrackByID")
    public Result getTrackByID(@RequestBody HyTrackReq req) {
        LOGGER.info("[getTrackByID] Req json: {}", new Gson().toJson(req));
        if (req.getTrackId() < 0) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        HyTrack hyTrack;
        try {
            hyTrack = hyTrackService.findById(req.getTrackId());
        } catch (Exception e) {
            LOGGER.warn("getTrackByID failed:", e);
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }
        if (hyTrack == null) {
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }
        return ResultGenerator.genSuccessResult(hyTrack);
    }

    /**
     * 回复更新投诉建议，更改投诉状态
     *
     * @param req: trackId, trackReplies
     * @return
     */
    @PostMapping("/replyTrack")
    public Result replyTrack(@RequestBody HyTrackReq req) {
        LOGGER.info("Req json: {}", new Gson().toJson(req));

        if (req.getTrackId() < 0 || req.getTrackReplies() == null || req.getTrackReplies().isEmpty()) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        HyTrack hyTrack;
        try {
            hyTrack = hyTrackService.findById(req.getTrackId());
        } catch (Exception e) {
            LOGGER.warn("getTrackByID failed:", e);
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }
        if (hyTrack == null) {
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }
        hyTrack.setTrackState(Consts.HY_ADVISE_STATE_DONE);
        hyTrack.setTrackReplies(req.getTrackReplies());
        try {
            hyTrackService.update(hyTrack);
        } catch (Exception e) {
            LOGGER.warn("track update id failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 更新投诉建议状态
     *
     * @param req: trackId, trackState
     * @return
     */
    @PostMapping("/updateTrack")
    public Result updateTrack(@RequestBody HyTrackReq req) {
        LOGGER.info("Req json: {}", new Gson().toJson(req));

        if (req.getTrackId() < 0) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        List<Integer> arr = Arrays.asList(Consts.HY_ADVISE_STATE_DOING, Consts.HY_ADVISE_STATE_DONE, Consts.HY_ADVISE_STATE_INIT);
        if (!arr.contains(req.getTrackState())) {
            LOGGER.warn("no such track state");
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        HyTrack hyTrack;
        try {
            hyTrack = hyTrackService.findById(req.getTrackId());
        } catch (Exception e) {
            LOGGER.warn("getTrackByID failed:", e);
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }
        if (hyTrack == null) {
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }
        hyTrack.setTrackState(req.getTrackState());
        try {
            hyTrackService.update(hyTrack);
        } catch (Exception e) {
            LOGGER.warn("track update id failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 搜索投诉列表，按照状态、时间、类型搜索
     *
     * @param req: trackState, 0:全部状态, 1: 未处理, 2: 处理中, 3: 已处理;
     * @param req: filterByDateType, 0:全部, 1: 近一周, 2: 近一月, 3: 近一年;
     * @param req: trackType, 0:全部分类, 1: 产品使用问题, 2: 销售使用问题;
     * @param req: 可选 managerName, ctmName, location
     * @param req: pageNo, pageSize
     * @return
     */
    @PostMapping("/trackList")
    public Result trackList(@RequestBody HyTrackReq req) {
        LOGGER.info("[searchTrackList] Req json: {}", new Gson().toJson(req));

        final int trackState = req.getTrackState();
        final int filterByDateType = req.getFilterByDateType();
        final int trackType = req.getTrackType();
        final String managerName = req.getManagerName();
        final String ctmName = req.getCtmName();
        final String location = req.getLocation();

        if (!checkPageReq(req) || trackState < 0 || trackState > 3
                || filterByDateType < 0 || filterByDateType > 3
                || trackType < 0 || trackType > 2) {
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
        if (trackState != 0) {
            map.put("trackState", trackState);
        }
        if (timeStamp != null) {
            map.put("timeStamp", timeStamp);
        }
        if (trackType != 0) {
            map.put("trackType", trackType);
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
        List<HyTrackVoCustomer> list = hyTrackVoCustomerService.searchTrackList(map);
        PageInfo pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
