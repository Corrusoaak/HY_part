package com.heye.crm.common.model.location;

import java.util.List;

/**
 * @author : lishuming
 */
public class HCity {
    List<HLocation> locations;
    String value;

    public List<HLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<HLocation> locations) {
        this.locations = locations;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
