package com.heye.crm.server.request;

import com.heye.crm.common.model.HyRevisitNaire;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyRevisitNaireReq extends Req {

    private Long naireId;
    private Integer naireState;
    private Integer naireType;
    private String nairName;
    private String nairList;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Long getNaireId() {
        return naireId;
    }

    public void setNaireId(Long naireId) {
        this.naireId = naireId;
    }

    public Integer getNaireState() {
        return naireState;
    }

    public void setNaireState(Integer naireState) {
        this.naireState = naireState;
    }

    public Integer getNaireType() {
        return naireType;
    }

    public void setNaireType(Integer naireType) {
        this.naireType = naireType;
    }

    public String getNairName() {
        return nairName;
    }

    public void setNairName(String nairName) {
        this.nairName = nairName;
    }

    public String getNairList() {
        return nairList;
    }

    public void setNairList(String nairList) {
        this.nairList = nairList;
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

    public HyRevisitNaire toNaire() {
        HyRevisitNaire hyRevisitNaire = new HyRevisitNaire();
        hyRevisitNaire.setNaireType(this.naireType);
        hyRevisitNaire.setNaireName(this.nairName);
        hyRevisitNaire.setNaireList(this.nairList);
        hyRevisitNaire.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return hyRevisitNaire;
    }
}
