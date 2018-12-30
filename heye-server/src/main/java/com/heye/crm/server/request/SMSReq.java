package com.heye.crm.server.request;

/**
 * @author : lishuming
 */
public class SMSReq extends Req {
    String telephoneNum;
    String imageCode;

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }
}
