package com.acmenxd.logger.utils;

import java.util.Random;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2016/12/27 14:31
 * @detail 随机工具类
 */
public class RandomUtils {

    /**
     * 获取一个随机数
     * * 包含 min 和 max
     */
    public static int randomByMinMax(int min, int max) {
        return new Random().nextInt(max + 1 - min) + min;
    }

    /**
     * 根据当前时间获取一个随机数
     * 规则 : yyyyMMddHHmmss + 5位数的随机数(10000 - 99999)
     *
     * @return
     */
    public static long getRandomByTime() {
        return Long.parseLong(DateUtils.nowDate(DateUtils.yMdHms2) + randomByMinMax(10000, 99999));
    }

}
