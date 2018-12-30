package com.heye.crm.server.request;

/**
 * @author : lishuming
 */
public class HyLocationReq extends Req {
    String locProvince;
    String locCity;

    public String getLocProvince() {
        return locProvince;
    }

    public void setLocProvince(String locProvince) {
        this.locProvince = locProvince;
    }

    public String getLocCity() {
        return locCity;
    }

    public void setLocCity(String locCity) {
        this.locCity = locCity;
    }
}
