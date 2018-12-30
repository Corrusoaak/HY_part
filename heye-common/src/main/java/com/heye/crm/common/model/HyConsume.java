package com.heye.crm.common.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyConsume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long csmId;
    @Column(name = "ctm_id")
    private long ctmId;
    @Column(name = "pro_id")
    private long proId;
    @Column(name = "csm_money")
    private BigInteger csmMoney;
    @Column(name = "csm_state")
    private int cmsState = 1;
    @Column(name = "csm_at")
    private Timestamp csmAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public long getCsmId() {
        return csmId;
    }

    public void setCsmId(long csmId) {
        this.csmId = csmId;
    }

    public long getCtmId() {
        return ctmId;
    }

    public void setCtmId(long ctmId) {
        this.ctmId = ctmId;
    }

    public long getProId() {
        return proId;
    }

    public void setProId(long proId) {
        this.proId = proId;
    }

    public BigInteger getCsmMoney() {
        return csmMoney;
    }

    public void setCsmMoney(BigInteger csmMoney) {
        this.csmMoney = csmMoney;
    }

    public Timestamp getCsmAt() {
        return csmAt;
    }

    public void setCsmAt(Timestamp csmAt) {
        this.csmAt = csmAt;
    }

    public Timestamp getUpdateAt() {
        return updatedAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updatedAt = updateAt;
    }

    public int getCmsState() {
        return cmsState;
    }

    public void setCmsState(int cmsState) {
        this.cmsState = cmsState;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
