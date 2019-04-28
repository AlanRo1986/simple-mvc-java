package com.lanxinbase.system.utils;

import java.util.Random;

/**
 * Created by alan.luo on 2017/8/10.
 */
public class NumberUtils {

    private static Random __random;

    /**
     * 取随机数
     *
     * @param min 最小范围
     * @param max 最大范围
     * @return
     */
    public static int random(int min, int max) {
        int s = getRandom().nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public static int nextInt(int max) {
        return getRandom().nextInt(max);
    }

    public static double nextDouble() {
        return getRandom().nextDouble();
    }

    public static float nextFloat() {
        return getRandom().nextFloat();
    }

    public static long nextLong() {
        return getRandom().nextLong();
    }

    private static Random getRandom() {
        if (__random == null) {
            __random = new Random();
        }
        __random.setSeed(DateTimeUtils.getTime());
        return __random;
    }

}
