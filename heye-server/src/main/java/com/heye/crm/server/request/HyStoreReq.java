package com.heye.crm.server.request;

/**
 * @author : lishuming
 */
public class HyStoreReq extends Req {
    private long storeId;
    private long storeManagerId;
    private String storePhoneNum;
    private String storeTelephone;

    private String storeProvince;
    private String storeCity;
    private String storePosition;

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getStoreManagerId() {
        return storeManagerId;
    }

    public void setStoreManagerId(long storeManagerId) {
        this.storeManagerId = storeManagerId;
    }

    public String getStorePhoneNum() {
        return storePhoneNum;
    }

    public void setStorePhoneNum(String storePhoneNum) {
        this.storePhoneNum = storePhoneNum;
    }

    public String getStoreTelephone() {
        return storeTelephone;
    }

    public void setStoreTelephone(String storeTelephone) {
        this.storeTelephone = storeTelephone;
    }

    public String getStorePosition() {
        return storePosition;
    }

    public void setStorePosition(String storePosition) {
        this.storePosition = storePosition;
    }

    public String getStoreProvince() {
        return storeProvince;
    }

    public void setStoreProvince(String storeProvince) {
        this.storeProvince = storeProvince;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }
}
