package com.heye.crm.server.controller;

import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyAnalyze;
import com.heye.crm.common.model.HyAnalyzeResultItem;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.configurer.WebConfig;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyAnalyzeReq;
import com.heye.crm.server.response.HyAdminSessionResp;
import com.heye.crm.server.service.HyAnalyzeService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/analyze")
public class HyAnalyzeController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyAnalyzeController.class);

    @Resource
    private HyAnalyzeService hyAnalyzeService;

    @Override
    public CRight getControllerCRight() {
        return CRight.CUSTOM_ANALYZE;
    }

    /**
     * 获取顾客分析
     * @param req anaType, aggLevel, startDate, endDate, sessionId, userId
     *            包含startDate, 不包含endDate
     * @return list of {labelName:someName, labelValue:someValue}
     */
    @PostMapping("/getCtmAnalyze")
    public Result getCtmAnalyze(@RequestBody HyAnalyzeReq req) {
        LOGGER.info("[getCtmAnalyze] req:" + new Gson().toJson(req));
        // check session and right
        HyAdminSessionResp session = null;
        session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());
        if (session == null && WebConfig.AUTHORIZE_SESSION_ENABLED) {
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }
        if (session != null && !session.hasRight(this.getControllerCRight())) {
            return ResultGenerator.genFailResult(Consts.INVALID_RIGHT);
        }
        // check param
        if (req.getAnaType() == null || req.getAggLevel() == null
                || req.getStartDate() == null || req.getEndDate() == null) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        // 根据hy_analyze表进行选择，当前考虑集中类型，注意返回结果
        // select date(created_at), label_name, label_value from hy_analyze
        // where created_at > #{startDate} and created_at < #{endDate} and agg_level = #{aggLevel} order by created_at asc;

        Condition cond = new Condition(HyAnalyze.class);
        Example.Criteria criteria = cond.createCriteria();
        // 统计类型 HyAnalyzeType
        criteria.andEqualTo("anaType", req.getAnaType());
        // 聚合水平 aggLevel 0按日 1按月 2按年
        criteria.andEqualTo("aggLevel", req.getAggLevel());
        criteria.andGreaterThanOrEqualTo("createdAt", req.getStartDate());
        criteria.andLessThan("createdAt", req.getEndDate());
        List<HyAnalyze> list = hyAnalyzeService.findByCondition(cond);
        // HyAnalyzeResultItem contains: date(created_at), label_name, label_value
        List<HyAnalyzeResultItem> result = new ArrayList<>();
        for (HyAnalyze hyAnalyze : list) {
            result.add(HyAnalyzeResultItem.toHyAnalyzeResultItem(hyAnalyze, req.getAggLevel()));
        }
        return ResultGenerator.genSuccessResult(result);
    }
}
