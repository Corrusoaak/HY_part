package com.heye.crm.common.utils;

import com.heye.crm.common.consts.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lishuming on 2017/9/14.
 */
public class CommonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    public static String genSessionId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String genAdminSessionKey(long userId, String sessionId) {
        if (userId < 0 || sessionId == null || sessionId.isEmpty()) {
            return null;
        }
        return Consts.ADMIN_REDIS_SESSION_PREFIX_KEY + "-" + userId + "-" + sessionId;
    }

    public static String genCustomSessionKey(long ctmId, String sessionId) {
        if (ctmId < 0 || sessionId == null || sessionId.isEmpty()) {
            return null;
        }
        return Consts.CTM_REDIS_SESSION_PREFIX_KEY + "-" + ctmId + "-" + sessionId;
    }

    public static boolean isNumericArray(String str) {
        if (str == null) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }

    public static boolean checkParamStr(String s) {
        return s != null && !s.isEmpty();
    }

    public Boolean isAlpha(String s) {

        try {

            Pattern pattern = Pattern.compile("\\w+");
            Matcher matcher = pattern.matcher(s);

            return matcher.matches();
        } catch (Exception e) {
            LOGGER.info("Match {} failed: {}", s, e.toString());
        }

        return false;
    }

    public static String getEclipseLike(String s) {
        return "%" + s + "%";
    }

    public static int getAge(Date birthDay) throws IllegalArgumentException {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }
}
