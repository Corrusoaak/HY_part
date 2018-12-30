package com.heye.crm.common.utils;

import java.util.Random;

/**
 * @author : lishuming
 */
public class RandomUtil {
    private static Random r = new Random();

    public static String getRandNum(int charCount) {
        StringBuffer charValue = new StringBuffer();
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue.append(c);
        }
        return new String(charValue);
    }

    public static int randomInt(int from, int to) {
        return from + r.nextInt(to - from);
    }
}
