package com.heye.crm.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyRevisitQuestion;
import com.heye.crm.common.utils.CommonUtil;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyRevisitQuestionReq;
import com.heye.crm.server.service.HyRevisitQuestionService;
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
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/revisit/quest")
public class HyRevisitQuestionController extends Controller<HyRevisitQuestionReq> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyRevisitQuestionController.class);

    @Override
    public CRight getControllerCRight() {
        return CRight.UNKOWN;
    }

    @Resource
    private HyRevisitQuestionService hyRevisitQuestionService;

    private boolean ifQuestionExist(Long id) {
        try {
            HyRevisitQuestion hyReVisitQuestion = hyRevisitQuestionService.findById(id);
            return hyReVisitQuestion != null;
        } catch (Exception e) {
            LOGGER.warn("find question by Id failed:", e);
            return false;
        }
    }

    /**
     * 新建问题：
     * - 问题描述
     * - 问题类型
     * - 问题选项，`A:答案1, B:答案2`
     * - 创建时间
     */
    @PostMapping("/addQuestion")
    public Result addQuestion(@RequestBody HyRevisitQuestionReq req) {
        LOGGER.info("[addQuestion] Req json: {}", new Gson().toJson(req));
        String errMsg = checkSessionAndRight(req);
        if (errMsg != null) {
            return ResultGenerator.genFailResult(errMsg);
        }

        if (StringUtils.isEmpty(req.getQuestDesc())
                || StringUtils.isEmpty(req.getQuestOption())
                || req.getQuestType() == null) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        try {
            HyRevisitQuestion hyRevisitQuestion = req.toQuestion();
            hyRevisitQuestion.setQuestState(Consts.HY_REVISIT_QUESTION_STATE_OK);
            hyRevisitQuestionService.save(hyRevisitQuestion);
        } catch (Exception e) {
            LOGGER.info("[addHyRevisitQuestion] add revisit question fail, exception:" + new Gson().toJson(req));
            return ResultGenerator.genFailResult(Consts.INSERT_FAILED);
        }
        return ResultGenerator.genSuccessResult(Consts.SUSSESS);
    }

    /**
     * 删除问题
     *
     * @param req
     * @return
     */
    @PostMapping("/deleteQuestion")
    public Result deleteQuestion(@RequestBody HyRevisitQuestionReq req) {
        LOGGER.info("[deleteQuestion] Req json: {}", new Gson().toJson(req));
        String errMsg = checkSessionAndRight(req);
        if (errMsg != null) {
            return ResultGenerator.genFailResult(errMsg);
        }

        if (!ifQuestionExist(req.getQuestId())) {
            return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
        }

        try {
            hyRevisitQuestionService.deleteById(req.getQuestId());
            return ResultGenerator.genSuccessResult(Consts.SUSSESS);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(Consts.DELETE_FAILED);
        }
    }

    /**
     * 获取问卷列表
     *
     * @param req: questType(可选参数), questDesc(可选参数, 近似搜索)
     * @return
     */
    @PostMapping("/listQuestion")
    public Result listQuestion(@RequestBody HyRevisitQuestionReq req) {
        LOGGER.info("[listQuestion] Req json: {}", new Gson().toJson(req));
        String errMsg = checkSessionAndRight(req);
        if (errMsg != null) {
            return ResultGenerator.genFailResult(errMsg);
        }

        try {
            List<HyRevisitQuestion> questions = null;

            Condition cond = new Condition(HyRevisitQuestion.class);
            Example.Criteria criteria = cond.createCriteria();
//            criteria.andEqualTo("questState", Consts.HY_REVISIT_QUESTION_STATE_OK);
            if (req.getQuestType() != null) {
                criteria.andEqualTo("questType", req.getQuestType());
            }
            if (!StringUtil.isEmpty(req.getQuestDesc())) {
                criteria.andLike("questDesc", CommonUtil.getEclipseLike(req.getQuestDesc()));
            }

            PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
            questions = hyRevisitQuestionService.findByCondition(cond);
            PageInfo pageInfo = new PageInfo<>(questions);
            return ResultGenerator.genSuccessResult(pageInfo);
        } catch (Exception e) {
            LOGGER.info("listQuestion exception:" + e);
            return ResultGenerator.genFailResult(Consts.SELECT_FAILED);
        }
    }
}
