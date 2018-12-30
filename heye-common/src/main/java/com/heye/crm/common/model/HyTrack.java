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
public class HyTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long trackId;
    @Column(name = "ctm_id")
    private long ctmId;
    @Column(name = "csm_id")
    private long csmId;
    @Column(name = "pro_id")
    private long proId;
    @Column(name = "track_state")
    private int trackState;
    @Column(name = "track_type")
    private int trackType;
    @Column(name = "track_desc")
    private String trackDesc;
    @Column(name = "track_pictures")
    private String trackPictures;
    @Column(name = "track_replies")
    private String trackReplies;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JSONField(serialize = false)
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public long getCtmId() {
        return ctmId;
    }

    public void setCtmId(long ctmId) {
        this.ctmId = ctmId;
    }

    public long getCsmId() {
        return csmId;
    }

    public void setCsmId(long csmId) {
        this.csmId = csmId;
    }

    public long getProId() {
        return proId;
    }

    public void setProId(long proId) {
        this.proId = proId;
    }

    public int getTrackState() {
        return trackState;
    }

    public void setTrackState(int trackState) {
        this.trackState = trackState;
    }

    public int getTrackType() {
        return trackType;
    }

    public void setTrackType(int trackType) {
        this.trackType = trackType;
    }

    public String getTrackDesc() {
        return trackDesc;
    }

    public void setTrackDesc(String trackDesc) {
        this.trackDesc = trackDesc;
    }

    public String getTrackPictures() {
        return trackPictures;
    }

    public void setTrackPictures(String trackPictures) {
        this.trackPictures = trackPictures;
    }

    public String getTrackReplies() {
        return trackReplies;
    }

    public void setTrackReplies(String trackReplies) {
        this.trackReplies = trackReplies;
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
