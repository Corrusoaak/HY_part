package com.heye.crm.server.configurer;

/**
 * Created by lishuming on 2017/9/6.
 */
public final class WebConfig {
    public static final String BASE_PACKAGE = "com.heye.crm.server";
    public static final String COMMON_PACKAGE = "com.heye.crm.common";

    public static final String MODEL_PACKAGE = COMMON_PACKAGE + ".model";
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.MybatisMapper";

    public static final boolean AUTHORIZE_SESSION_ENABLED = false;
}
