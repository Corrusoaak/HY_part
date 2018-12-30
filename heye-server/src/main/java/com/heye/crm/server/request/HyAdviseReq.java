package com.heye.crm.server.request;

/**
 * @author : lishuming
 */
public class HyAdviseReq extends Req {
    private long adviseId;
    private long ctmId;
    private int adviseState;
    private int adviseType;
    private String adviseDesc;
    private String advisePictures;
    private String aviseReplies;
    private int filterByDateType;
    private String managerName;
    private String ctmName;
    private String location;

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

    public long getAdviseId() {
        return adviseId;
    }

    public void setAdviseId(long adviseId) {
        this.adviseId = adviseId;
    }

    public long getCtmId() {
        return ctmId;
    }

    public void setCtmId(long ctmId) {
        this.ctmId = ctmId;
    }

    public int getAdviseState() {
        return adviseState;
    }

    public void setAdviseState(int adviseState) {
        this.adviseState = adviseState;
    }

    public int getAdviseType() {
        return adviseType;
    }

    public void setAdviseType(int adviseType) {
        this.adviseType = adviseType;
    }

    public String getAdviseDesc() {
        return adviseDesc;
    }

    public void setAdviseDesc(String adviseDesc) {
        this.adviseDesc = adviseDesc;
    }

    public String getAdvisePictures() {
        return advisePictures;
    }

    public void setAdvisePictures(String advisePictures) {
        this.advisePictures = advisePictures;
    }

    public String getAviseReplies() {
        return aviseReplies;
    }

    public void setAviseReplies(String aviseReplies) {
        this.aviseReplies = aviseReplies;
    }
}
