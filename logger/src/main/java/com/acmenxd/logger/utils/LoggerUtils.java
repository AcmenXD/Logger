package com.acmenxd.logger.utils;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2017/3/24 15:17
 * @detail 工具类
 */
public final class LoggerUtils {
    /**
     * formatStr 格式
     * 以下属性可以随意组合使用,但转换时需注意,可能出现错误
     */
    public static final String yyyy = "yyyy"; //年
    public static final String MM = "MM"; //月
    public static final String dd = "dd"; //日
    public static final String HH = "HH"; //时
    public static final String mm = "mm"; //分
    public static final String ss = "ss"; //秒
    public static final String yMdHms2 = yyyy + MM + dd + HH + mm + ss; //yyyyMMddHHmmss (年月日时分秒)

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
        return Long.parseLong(nowDate(yMdHms2) + randomByMinMax(10000, 99999));
    }

    /**
     * 获取当前日期 Date类型
     */
    public static Date nowDate() {
        return new Date();
    }

    /**
     * 获取当前日期  : long(毫秒) -> 2016-12-15 16:10:10 (formatStr)
     */
    public static String nowDate(@NonNull String formatStr) {
        return dateFormat(nowDate().getTime(), formatStr);
    }

    /**
     * 日期格式转换 : long(毫秒) -> 2016-12-15 16:10:10 (formatStr)
     */
    public static String dateFormat(long date, @NonNull String formatStr) {
        return new SimpleDateFormat(formatStr).format(new Date(date));
    }
}
