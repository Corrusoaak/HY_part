package com.heye.crm.server.request;

/**
 * @author : lishuming
 */
public abstract class Req {
    private long adminUserId;
    private String adminSessionId;
    private int pageNo;
    private int pageSize;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminSessionId() {
        return adminSessionId;
    }

    public void setAdminSessionId(String adminSessionId) {
        this.adminSessionId = adminSessionId;
    }
}
