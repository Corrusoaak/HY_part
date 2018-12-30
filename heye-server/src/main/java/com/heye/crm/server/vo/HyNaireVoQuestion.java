package com.heye.crm.server.vo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author : Zhanhanbing
 */
public class HyNaireVoQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long naireId;
    @Column(name = "naire_type")
    private int naireType;
    @Column(name = "naire_name")
    private String naireName;
    @Column(name = "naire_list")
    private String naireList;
    @Column(name = "quest_id")
    private Long questId;
    @Column(name = "quest_type")
    private Integer questType;
    @Column(name = "quest_desc")
    private String questDesc;
    @Column(name = "quest_option")
    private String questOption;

    public long getNaireId() {
        return naireId;
    }

    public void setNaireId(long naireId) {
        this.naireId = naireId;
    }

    public int getNaireType() {
        return naireType;
    }

    public void setNaireType(int naireType) {
        this.naireType = naireType;
    }

    public Integer getQuestType() {
        return questType;
    }

    public void setQuestType(Integer questType) {
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

    public Long getQuestId() {
        return questId;
    }

    public void setQuestId(Long questId) {
        this.questId = questId;
    }

    public String getNaireName() {
        return naireName;
    }

    public void setNaireName(String naireName) {
        this.naireName = naireName;
    }

    public String getNaireList() {
        return naireList;
    }

    public void setNaireList(String naireList) {
        this.naireList = naireList;
    }
}
