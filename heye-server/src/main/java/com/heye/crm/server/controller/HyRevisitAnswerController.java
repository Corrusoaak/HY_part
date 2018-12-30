package com.heye.crm.server.controller;

import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyRevisitAnswer;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyRevisitAnswerReq;
import com.heye.crm.server.service.HyRevisitAnswerService;
import com.heye.crm.server.vo.HyAnswerCount;
import com.heye.crm.server.vo.HyAnswerVoQuest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/revisit/answer")
public class HyRevisitAnswerController extends Controller<HyRevisitAnswerReq> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyRevisitAnswerController.class);

    @Resource
    private HyRevisitAnswerService hyRevisitAnswerService;

    @Override
    public CRight getControllerCRight() {
        return CRight.UNKOWN;
    }

    /**
     * 添加答案: 客户 + 问卷 + 问题 + 答案, 每个问题向表中添加一条记录
     *
     * @param req: ctmId, questId, naireId, answerContent
     * @return
     */
    @PostMapping("/addAnswer")
    public Result addAnswer(@RequestBody HyRevisitAnswerReq req) {
        LOGGER.info("[listNaire] Req json: {}", new Gson().toJson(req));
        String errMsg = checkSessionAndRight(req);
        if (errMsg != null) {
            return ResultGenerator.genFailResult(errMsg);
        }
        if (req.getAnswerContent() == null || req.getAnswerContent().length() == 0) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        try {
            HyRevisitAnswer answer = new HyRevisitAnswer();
            answer.setCtmId(req.getCtmId());
            answer.setQuestId(req.getQuestId());
            answer.setNaireId(req.getNaireId());
            answer.setAnswerContent(req.getAnswerContent());

            hyRevisitAnswerService.save(answer);
        } catch (Exception e) {
            LOGGER.error("Exception in hyRevisitAnswerService save: " + e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 基于问题id获取答案类型的聚类，针对选择型问题
     * select * from hy_revisit_answer where quest_id=xxx ; 然后聚合返回的结果
     *
     * @param req: questId
     * @return
     */
    @PostMapping("/analyzeAnswer")
    public Result analyzeAnswer(@RequestBody HyRevisitAnswerReq req) {
        LOGGER.info("[analyzeAnswer] Req json: {}", new Gson().toJson(req));
        String errMsg = checkSessionAndRight(req);
        if (errMsg != null) {
            return ResultGenerator.genFailResult(errMsg);
        }
        List<HyAnswerVoQuest> list = null;
        try {
            list = hyRevisitAnswerService.findByQuestId(req.getQuestId());
            if (list != null && list.size() > 0) {
                return ResultGenerator.genSuccessResult(list);
            } else {
                return ResultGenerator.genFailResult(Consts.NOT_EXISTED_IN_DB);
            }
        } catch (Exception e) {
            LOGGER.error("Exception in hyRevisitAnswerService findByQuestId: " + e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
    }

    /**
     * 基于问题id获取答案的答题情况
     * select answerContent, count(*) from hy_revisit_answer group by answerContent;
     *
     * @param req: questId
     * @return
     */
    @PostMapping("/countAnswer")
    public Result countAnswer(@RequestBody HyRevisitAnswerReq req) {
        LOGGER.info("[countAnswer] Req json: {}", new Gson().toJson(req));
        String errMsg = checkSessionAndRight(req);
        if (errMsg != null) {
            return ResultGenerator.genFailResult(errMsg);
        }
        try {
            List<HyAnswerCount> list = null;
            list = hyRevisitAnswerService.countAnswer(req.getQuestId());
            return ResultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            LOGGER.error("" + e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }
    }
}
