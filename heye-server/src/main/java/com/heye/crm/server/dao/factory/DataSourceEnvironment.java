package com.heye.crm.server.dao.factory;

/**
 * @author : lishuming
 */
public enum DataSourceEnvironment {
    DEFAULT("default");

    private String env;

    DataSourceEnvironment(String env) {
        this.env = env;
    }

    public static DataSourceEnvironment getDataSourceEnv(String env) {
        return DEFAULT;
    }

    public String getEnv() {
        return this.env;
    }
}
