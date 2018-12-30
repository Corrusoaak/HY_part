package com.heye.crm.server.controller;

import com.alibaba.fastjson.JSON;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyLocation;
import com.heye.crm.common.utils.CommonUtil;
import com.heye.crm.common.utils.Result;
import com.heye.crm.common.utils.ResultGenerator;
import com.heye.crm.server.configurer.WebConfig;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.consts.CRole;
import com.heye.crm.server.request.Req;
import com.heye.crm.server.response.HyAdminSessionResp;
import com.heye.crm.server.service.RedisPool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 *
 * @author : lishuming
 * @param <T>
 */
public abstract class Controller<T extends Req> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @Resource
    private RedisPool redisPool;

    public static boolean checkPageReq(Req req) {
        if (req.getPageNo() < 0 || req.getPageSize() < 0) {
            return false;
        }
        return true;
    }

    public abstract CRight getControllerCRight();

    public boolean setAdminSession(HyAdminSessionResp session) {
        String key = CommonUtil.genAdminSessionKey(session.getHyUser().getUserId(), session.getSessionId());
        if (key == null) {
            LOGGER.warn("[setAdminSession] gen admin session key null");
            return false;
        }

        try {
            String value = JSON.toJSONString(session);
            LOGGER.info("[setAdminSession] redis set : {} -> {}", key, value);
            String res = redisPool.instance().setex(key, Consts.ADMIN_SESSION_LIFE_TIME, value);
            if (res == null || !res.toUpperCase().equals("OK")) {
                LOGGER.warn("[setAdminSession] redis set failed: {} -> {}", key, value);
                return false;
            }
        } catch (Exception e) {
            LOGGER.warn("[setAdminSession] set admin session failed:", e);
            return false;
        }

        return true;
    }

    /**
     *
     * @param userId
     * @param sessionId
     * @return
     */
    public HyAdminSessionResp authAdminSession(long userId, String sessionId) {
        if (sessionId == null) {
            LOGGER.warn("[authAdminSession] muserId/sessionId invalid");
            return null;
        }

        String key;
        if (sessionId.startsWith(Consts.ADMIN_REDIS_SESSION_PREFIX_KEY)) {
            key = sessionId;
        } else {
            key = CommonUtil.genAdminSessionKey(userId, sessionId);
        }

        if (key == null) {
            return null;
        }

        HyAdminSessionResp resp;
        try {
            String sessionValue = redisPool.instance().get(key);
            if (sessionValue == null || sessionValue.isEmpty()) {
                LOGGER.warn("[authAdminSession] redis get invalid: {}", key);
                return null;
            }
            LOGGER.info("[authAdminSession] redis get : {}, value: {}", key, sessionValue);

            resp = JSON.parseObject(sessionValue, HyAdminSessionResp.class);

            if (resp == null) {
                LOGGER.warn("[authAdminSession] parse redis value invalid: {}", sessionValue);
                return null;
            }
        } catch (Exception e) {
            LOGGER.warn("[authAdminSession] auth session failed:", e);
            return null;
        }

        return resp;
    }

    public boolean setSession(String sessionK, String sessionV) {
        LOGGER.info("[setSession] set {}: {}", sessionK, sessionV);

        try {
            String res = redisPool.instance().setex(sessionK, Consts.ADMIN_SESSION_LIFE_TIME, sessionV);
            if (res == null || !res.toUpperCase().equals("OK")) {
                LOGGER.warn("[setSession] redis set failed: {} -> {}", sessionK, sessionV);
                return false;
            }
        } catch (Exception e) {
            LOGGER.warn("[setSession] set admin session failed:", e);
            return false;
        }

        return true;
    }

    public String getSession(String sessionK) {
        String sessionV;

        try {
            sessionV = redisPool.instance().get(sessionK);
        } catch (Exception e) {
            LOGGER.warn("[getSession] set admin session failed:", e);
            return null;
        }

        return sessionV;
    }

    public boolean authAdminRight(HyAdminSessionResp session, CRight a) {
        return true;
    }

    public Result checkLocationRight(HyAdminSessionResp session, CRole role) {
        String filterProvince = null;
        String filterCity = null;
        String filterLocation = null;

        switch (role) {
        case SUPER_ADMIN:
            break;
        case PROVINCE_ADMIN:
            filterProvince = session.getHyUser().getUserProvince();
            if (filterProvince == null || filterProvince.isEmpty()) {
                return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
            }
            break;
        case CITY_ADMIN:
            filterProvince = session.getHyUser().getUserProvince();
            if (filterProvince == null || filterProvince.isEmpty()) {
                return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
            }
            filterCity = session.getHyUser().getUserCity();
            if (filterCity == null || filterCity.isEmpty()) {
                return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
            }
            break;
        case STORE_ADMIN:
            filterProvince = session.getHyUser().getUserProvince();
            if (filterProvince == null || filterProvince.isEmpty()) {
                return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
            }
            filterCity = session.getHyUser().getUserCity();
            if (filterCity == null || filterCity.isEmpty()) {
                return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
            }
            filterLocation = session.getHyUser().getUserLocation();
            if (filterLocation == null || filterLocation.isEmpty()) {
                return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
            }
            break;
        case UNKNOWN:
            return ResultGenerator.genFailResult(Consts.INVALID_SESSION);
        }
        HyLocation hyLocation = new HyLocation();
        hyLocation.setLocCity(filterCity);
        hyLocation.setLocProvince(filterProvince);
        hyLocation.setLocProvince(filterLocation);

        return ResultGenerator.genSuccessResult(hyLocation);
    }

    /**
     * 校验请求是否用含有足够的权限
     * @param req
     * @return
     */
    protected String checkSessionAndRight(T req) {
        // 判断是否已经登录
        if (StringUtils.isEmpty(req.getAdminSessionId())) {
            return Consts.INVALID_SESSION;
        }

        // 首先判断是否已经设置权限
        if (getControllerCRight().equals(CRight.UNKOWN)) {
            return null;
        }

        HyAdminSessionResp session = authAdminSession(req.getAdminUserId(), req.getAdminSessionId());

        if (WebConfig.AUTHORIZE_SESSION_ENABLED) {
            if (session == null) {
                return Consts.INVALID_SESSION;
            } else if (!session.hasRight(this.getControllerCRight())) {
                return Consts.INVALID_RIGHT;
            }
        }

        return null;
    }
}
