package com.heye.crm.server.vo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyTrackVoCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long trackId;
    @Column(name = "ctm_id")
    private long ctmId;
    @Column(name = "pro_id")
    private long proId;
    @Column(name = "pro_name")
    private long proName;
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
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "ctm_manager_name")
    private String managerName;
    @Column(name = "ctm_name")
    private String ctmName;
    @Column(name = "ctm_location")
    private String ctmLocation;

    public long getAdviseId() {
        return trackId;
    }

    public void setAdviseId(long trackId) {
        this.trackId = trackId;
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

    public int getAdviseState() {
        return trackState;
    }

    public void setAdviseState(int trackState) {
        this.trackState = trackState;
    }

    public int getAdviseType() {
        return trackType;
    }

    public void setAdviseType(int trackType) {
        this.trackType = trackType;
    }

    public String getAdviseDesc() {
        return trackDesc;
    }

    public void setAdviseDesc(String trackDesc) {
        this.trackDesc = trackDesc;
    }

    public String getAdvisePictures() {
        return trackPictures;
    }

    public void setAdvisePictures(String trackPictures) {
        this.trackPictures = trackPictures;
    }

    public String getAviseReplies() {
        return trackReplies;
    }

    public void setAviseReplies(String trackReplies) {
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

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getCtmName() {
        return ctmName;
    }

    public void setCtmName(String ctmName) {
        this.ctmName = ctmName;
    }

    public String getCtmLocation() {
        return ctmLocation;
    }

    public void setCtmLocation(String ctmLocation) {
        this.ctmLocation = ctmLocation;
    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public long getProName() {
        return proName;
    }

    public void setProName(long proName) {
        this.proName = proName;
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
}
