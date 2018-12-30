package com.heye.crm.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyRevisitNaire;
import com.heye.crm.common.model.HyRevisitNaireQuest;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.request.HyRevisitNaireReq;
import com.heye.crm.server.service.HyNaireVoQuestionService;
import com.heye.crm.server.service.HyRevisitNaireQuestService;
import com.heye.crm.server.service.HyRevisitNaireService;
import com.heye.crm.server.vo.HyNaireVoQuestion;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.entity.Condition;

/**
 * @author : lishuming
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/revisit/naire")
public class HyRevisitNaireController extends Controller<HyRevisitNaireReq> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyRevisitNaireController.class);

    @Override
    public CRight getControllerCRight() {
        return CRight.UNKOWN;
    }

    @Resource
    private HyRevisitNaireService hyRevisitNaireService;

    @Resource
    private HyRevisitNaireQuestService hyRevisitNaireQuestService;

    @Resource
    private HyNaireVoQuestionService hyNaireVoQuestionService;

    private static HyRevisitNaireQuest toNaireQuest(Long qid, Long nid) {
        HyRevisitNaireQuest hyReVisitNaireQuest = new HyRevisitNaireQuest();
        hyReVisitNaireQuest.setNaireId(nid);
        hyReVisitNaireQuest.setQuestId(qid);
        hyReVisitNaireQuest.setNqState(Consts.HY_REVISIT_NAIRE_QUESTION_STATE_OK);
        hyReVisitNaireQuest.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return hyReVisitNaireQuest;
    }

    private static void updateOrSaveNaireQuest(Long qid,Long nid){
    }


    /**
     * 创建问卷，问题列表 `1,2`
     * naire_list记录顺序
     * revist_naire_quest: 记录问卷->问题映射记录
     *
     * @param req
     * @return
     */
    @PostMapping("/addNaire")
    public Result addNaire(@RequestBody HyRevisitNaireReq req) {
        LOGGER.info("[addNaire] Req json: {}", new Gson().toJson(req));
        String errMsg = checkSessionAndRight(req);
        if (errMsg != null) {
            return ResultGenerator.genFailResult(errMsg);
        }
        if (req.getNaireType() == null || StringUtils.isEmpty(req.getNairList())
                || StringUtils.isEmpty(req.getNairName())) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }
        try {
            HyRevisitNaire hyRevisitNaire = req.toNaire();
            hyRevisitNaire.setNaireState(Consts.HY_REVISIT_NAIRE_STATE_OK);
            hyRevisitNaireService.save(hyRevisitNaire);
        } catch (Exception e) {
            LOGGER.info("[addHyRevisitnNaire] add revisit naire fail, exception:" + new Gson().toJson(req));
            return ResultGenerator.genFailResult(Consts.INSERT_FAILED);
        }

        Condition cond = new Condition(HyRevisitNaire.class);
        cond.createCriteria()
                .andEqualTo("naireName", req.getNairName())
                .andEqualTo("naireList", req.getNairList())
                .andEqualTo("naireType", req.getNaireType());
        List<HyRevisitNaire> naireList = hyRevisitNaireService.findByCondition(cond);
        Long naireId = naireList.get(0).getNaireId();

        try {
            if (req.getNairList().contains(",")) {
                String[] questIds = req.getNairList().split(",");
                for (String qid : questIds) {
                    Long questId = Long.parseLong(qid);
                    HyRevisitNaireQuest hyReVisitNaireQuest = toNaireQuest(questId, naireId);
                    hyRevisitNaireQuestService.save(hyReVisitNaireQuest);
                }
            } else {
                Long questId = Long.parseLong(req.getNairList());
                HyRevisitNaireQuest hyReVisitNaireQuest = toNaireQuest(questId, naireId);
                hyRevisitNaireQuestService.save(hyReVisitNaireQuest);
            }
        } catch (Exception e) {
            LOGGER.info("[addHyRevisitNaireQuest] add revisit naire quest fail, exception:" + new Gson().toJson(req));
            return ResultGenerator.genFailResult(Consts.INSERT_FAILED);
        }

        return ResultGenerator.genSuccessResult(Consts.SUSSESS);
    }

    /**
     * 获取所有问卷列表
     *
     * @param req
     * @return
     */
    @PostMapping("/listNaire")
    public Result listNaire(@RequestBody HyRevisitNaireReq req) {
        LOGGER.info("[listNaire] Req json: {}", new Gson().toJson(req));
        String errMsg = checkSessionAndRight(req);
        if (errMsg != null) {
            return ResultGenerator.genFailResult(errMsg);
        }
        PageHelper.startPage(req.getPageNo(), req.getPageSize(), "created_at desc");
        List<HyRevisitNaire> naires = hyRevisitNaireService.findAll();
        if (naires == null || naires.size() == 0) {
            return ResultGenerator.genFailResult(Consts.SELECT_FAILED);
        }
        PageInfo pageInfo = new PageInfo(naires);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 获取问卷详情：naire表与naire_quest表相交，查询详情
     *
     * @param req
     * @return
     */
    @PostMapping("/detailNaire")
    public Result detailNaire(@RequestBody HyRevisitNaireReq req) {
        LOGGER.info("[detailNaire] Req json: {}", new Gson().toJson(req));
        String errMsg = checkSessionAndRight(req);
        if (errMsg != null) {
            return ResultGenerator.genFailResult(errMsg);
        }

        if (req.getNaireId() == null) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("naireId", req.getNaireId());

        List<HyNaireVoQuestion> naireVoQuestions = hyNaireVoQuestionService.getNaireDetail(map);
        if (naireVoQuestions == null || naireVoQuestions.size() == 0) {
            return ResultGenerator.genFailResult(Consts.SELECT_FAILED);
        }

        return ResultGenerator.genSuccessResult(naireVoQuestions);
    }

    /**
     * 问卷编辑，
     *
     */
    @PostMapping("/updateNaire")
    public Result updateNaire(@RequestBody HyRevisitNaireReq req) {
        LOGGER.info("[updateNaire] Req json: {}", new Gson().toJson(req));
        String errMsg = checkSessionAndRight(req);
        if (errMsg != null) {
            return ResultGenerator.genFailResult(errMsg);
        }

        if (req.getNaireId() == null) {
            return ResultGenerator.genFailResult(Consts.INVALID_PARAM);
        }

        HyRevisitNaire hyRevisitNaire=hyRevisitNaireService.findById(req.getNaireId());
        if (hyRevisitNaire==null){
            return ResultGenerator.genFailResult(Consts.NO_SUCH_OBJECT);
        }
        if(!StringUtils.isEmpty(req.getNairList())){
            hyRevisitNaire.setNaireList(req.getNairList());
        }
        if(!StringUtils.isEmpty(req.getNairName())){
            hyRevisitNaire.setNaireName(req.getNairName());
        }
        Long naireId=req.getNaireId();
        Condition cond1=new Condition(HyRevisitNaireQuest.class);
        cond1.createCriteria()
                .andEqualTo("naireId",naireId);
        List<HyRevisitNaireQuest> naireQuests=hyRevisitNaireQuestService.findByCondition(cond1);
        try {
            for (HyRevisitNaireQuest naireQuest:naireQuests){
                naireQuest.setNqState(Consts.HY_REVISIT_NAIRE_QUESTION_STATE_DELETED);
                hyRevisitNaireQuestService.update(naireQuest);
            }
        } catch (Exception e){
            LOGGER.info("[DeleteHyRevisitNaireQuest] Delete revisit naire quest fail, exception:" + new Gson().toJson(req));
            return ResultGenerator.genFailResult(Consts.UPDATE_FAILED);
        }


        try {
            if (req.getNairList().contains(",")) {
                String[] questIds = req.getNairList().split(",");
                for (String qid : questIds) {
                    Long questId = Long.parseLong(qid);

                    Condition cond=new Condition(HyRevisitNaireQuest.class);
                    cond.createCriteria()
                            .andEqualTo("naireId",naireId)
                            .andEqualTo("questId",questId);
                    List<HyRevisitNaireQuest> naireQuestList=hyRevisitNaireQuestService.findByCondition(cond);
                    if(naireQuestList.size() == 0) {
                        HyRevisitNaireQuest hyReVisitNaireQuest = toNaireQuest(questId, naireId);
                        hyRevisitNaireQuestService.save(hyReVisitNaireQuest);
                    } else {
                        HyRevisitNaireQuest hyRevisitNaireQuest=naireQuestList.get(0);
                        hyRevisitNaireQuest.setNqState(Consts.HY_REVISIT_NAIRE_QUESTION_STATE_OK);
                        hyRevisitNaireQuestService.update(hyRevisitNaireQuest);
                    }

                }
            } else {
                Long questId = Long.parseLong(req.getNairList());
                Condition cond=new Condition(HyRevisitNaireQuest.class);
                cond.createCriteria()
                        .andEqualTo("naireId",naireId)
                        .andEqualTo("questId",questId);
                List<HyRevisitNaireQuest> naireQuestList=hyRevisitNaireQuestService.findByCondition(cond);
                if(naireQuestList.size() == 0) {
                    HyRevisitNaireQuest hyReVisitNaireQuest = toNaireQuest(questId, naireId);
                    hyRevisitNaireQuestService.save(hyReVisitNaireQuest);
                } else {
                    HyRevisitNaireQuest hyRevisitNaireQuest=naireQuestList.get(0);
                    hyRevisitNaireQuest.setNqState(Consts.HY_REVISIT_NAIRE_QUESTION_STATE_OK);
                    hyRevisitNaireQuestService.update(hyRevisitNaireQuest);
                }
            }
        } catch (Exception e) {
            LOGGER.info("[addHyRevisitNaireQuest] add revisit naire quest fail, exception:" + new Gson().toJson(req));
            return ResultGenerator.genFailResult(Consts.INSERT_FAILED);
        }
        try {
            hyRevisitNaireService.update(hyRevisitNaire);
        } catch (Exception e){
            LOGGER.warn("[updateHyNaireById] update failed:", e);
            return ResultGenerator.genFailResult(Consts.SERVER_FAILED);
        }

        return ResultGenerator.genSuccessResult();
    }

}
