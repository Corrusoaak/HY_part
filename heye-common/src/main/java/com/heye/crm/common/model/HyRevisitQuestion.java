package com.heye.crm.common.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyRevisitQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questId;

    @Column(name = "quest_state")
    private Integer questState;
    @Column(name = "quest_type")
    private Integer questType;
    @Column(name = "quest_desc")
    private String questDesc;
    @Column(name = "quest_option")
    private String questOption;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JSONField(serialize = false)
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Long getQuestId() {
        return questId;
    }

    public void setQuestId(Long questId) {
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
}
