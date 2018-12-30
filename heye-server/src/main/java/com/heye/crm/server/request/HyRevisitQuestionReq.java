package com.heye.crm.server.request;

import com.heye.crm.common.model.HyRevisitQuestion;

import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyRevisitQuestionReq extends Req {
    private Long questId;
    private Integer questState;
    private Integer questType;
    private String questDesc;
    private String questOption;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Long getQuestId() {
        return questId;
    }

    public void setQuestId(long questId) {
        this.questId = questId;
    }

    public Integer getQuestState() {
        return questState;
    }

    public void setQuestState(int questState) {
        this.questState = questState;
    }

    public Integer getQuestType() {
        return questType;
    }

    public void setQuestType(int questType) {
        this.questType = questType;
    }

    public String getQuestDesc() {
        return questDesc;
    }

    public void setQuestDesc(String questDesc) {
        this.questDesc = questDesc;
    }

    public String getQuestOption() {
        return questOption;
    }

    public void setQuestOption(String questOption) {
        this.questOption = questOption;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public HyRevisitQuestion toQuestion() {
        HyRevisitQuestion hyReVisitQuestion = new HyRevisitQuestion();
        hyReVisitQuestion.setQuestDesc(this.questDesc);
        hyReVisitQuestion.setQuestOption(this.questOption);
        hyReVisitQuestion.setQuestType(this.questType);
        hyReVisitQuestion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return hyReVisitQuestion;
    }
}
