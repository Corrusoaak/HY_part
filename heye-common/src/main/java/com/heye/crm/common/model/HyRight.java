package com.heye.crm.common.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : fanjinqian
 */
public class HyRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rightId;
    @Column(name = "right_parent_id")
    private long rightParentId;
    @Column(name = "right_name")
    private String rightName;
    @Column(name = "right_desc")
    private String rightDesc;
    @Column(name = "right_module")
    private String rightModule;
    @Column(name = "right_state")
    private Integer rightState;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public long getRightId() {
        return rightId;
    }

    public void setRightId(long rightId) {
        this.rightId = rightId;
    }

    public long getRightParentId() {
        return rightParentId;
    }

    public void setRightParentId(long rightParentId) {
        this.rightParentId = rightParentId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getRightDesc() {
        return rightDesc;
    }

    public void setRightDesc(String rightDesc) {
        this.rightDesc = rightDesc;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRightState() {
        return rightState;
    }

    public void setRightState(Integer rightState) {
        this.rightState = rightState;
    }

    public String getRightModule() {
        return rightModule;
    }

    public void setRightModule(String rightModule) {
        this.rightModule = rightModule;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
