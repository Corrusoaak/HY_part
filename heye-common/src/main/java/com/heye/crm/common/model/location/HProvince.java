package com.heye.crm.common.model.location;

import java.util.List;

/**
 * @author : lishuming
 */

public class HProvince {
    String value;
    List<HCity> cities;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<HCity> getCities() {
        return cities;
    }

    public void setCities(List<HCity> cities) {
        this.cities = cities;
    }
}
