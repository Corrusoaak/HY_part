package com.heye.crm.server.vo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : fanjinqian
 */
public class HyAnswerVoQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;
    @Column(name = "ctm_id")
    private long ctmId;
    @Column(name = "quest_id")
    private long questId;
    @Column(name = "naire_id")
    private long naireId;
    @Column(name = "answer_state")
    private int answerState;
    @Column(name = "answer_content")
    private String answerContent;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JSONField(serialize = false)
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "quest_state")
    private int questState;
    @Column(name = "quest_type")
    private int questType;
    @Column(name = "quest_desc")
    private String questDesc;
    @Column(name = "quest_option")
    private String questOption;

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public long getCtmId() {
        return ctmId;
    }

    public void setCtmId(long ctmId) {
        this.ctmId = ctmId;
    }

    public long getQuestId() {
        return questId;
    }

    public void setQuestId(long questId) {
        this.questId = questId;
    }

    public long getNaireId() {
        return naireId;
    }

    public void setNaireId(long naireId) {
        this.naireId = naireId;
    }

    public int getAnswerState() {
        return answerState;
    }

    public void setAnswerState(int answerState) {
        this.answerState = answerState;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
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

    public int getQuestState() {
        return questState;
    }

    public void setQuestState(int questState) {
        this.questState = questState;
    }

    public int getQuestType() {
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
}
