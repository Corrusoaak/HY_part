package com.heye.crm.server.request;

/**
 * @author : lishuming
 */
public class HyTrackReq extends Req {
    private long trackId;
    private long ctmId;
    private long proId;
    private int trackState;
    private int trackType;
    private String trackDesc;
    private String trackPictures;
    private String trackReplies;
    private int filterByDateType;
    private String managerName;
    private String ctmName;
    private String location;

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

    public int getFilterByDateType() {
        return filterByDateType;
    }

    public void setFilterByDateType(int filterByDateType) {
        this.filterByDateType = filterByDateType;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
