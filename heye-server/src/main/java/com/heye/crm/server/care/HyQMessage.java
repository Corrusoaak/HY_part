package com.heye.crm.server.care;

import com.google.common.base.MoreObjects;

/**
 * @author : lishuming
 */
public class HyQMessage {
    private String msgPhoneNum;
    private String msgContext;
    private String msgSendTime;
    private String scheduleTime;

    public String getMsgPhoneNum() {
        return msgPhoneNum;
    }

    public void setMsgPhoneNum(String msgPhoneNum) {
        this.msgPhoneNum = msgPhoneNum;
    }

    public String getMsgContext() {
        return msgContext;
    }

    public void setMsgContext(String msgContext) {
        this.msgContext = msgContext;
    }

    public String getMsgSendTime() {
        return msgSendTime;
    }

    public void setMsgSendTime(String msgSendTime) {
        this.msgSendTime = msgSendTime;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("msgPhoneNum", this.msgPhoneNum)
                .add("msgContext", this.msgContext)
                .add("msgSendTime", this.msgSendTime)
                .add("scheduleTime", this.scheduleTime)
                .toString();
    }
}
