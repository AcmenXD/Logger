package com.acmenxd.logger.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2016/12/15 16:10
 * @detail 时间工具类
 */
public class DateUtils {
    /**
     * 计算机日期初始时间
     */
    public static final String INIT_DATE = "1970-1-1 8:00:00";
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
    public static final String E = "E"; //周几
    public static final String EEEE = "EEEE"; //星期几
    public static final String a = "a"; //上下午
    public static final String yMd = yyyy + "-" + MM + "-" + dd; //yyyy-MM-dd (年-月-日)
    public static final String Hms = HH + ":" + mm + ":" + ss; //HH:mm:ss (时:分:秒)
    public static final String yMdHms = yMd + " " + Hms; //yyyy-MM-dd HH:mm:ss (年-月-日 时:分:秒)
    public static final String yMdHms2 = yyyy + MM + dd + HH + mm + ss; //yyyyMMddHHmmss (年月日时分秒)
    public static final String yMdHmsEa = yMdHms + " " + EEEE + " " + a; //yyyy-MM-dd HH:mm:ss E a(年-月-日 时:分:秒 星期几 上下午)

    /**
     * 获取当前日期 Date类型
     */
    public static Date nowDate() {
        return new Date();
    }

    /**
     * 获取当前日期  : long(毫秒) -> 2016-12-15 16:10:10 (formatStr)
     */
    public static String nowDate(String formatStr) {
        return dateFormat(nowDate().getTime(), formatStr);
    }

    /**
     * 日期格式转换 : long(毫秒) -> 2016-12-15 16:10:10 (formatStr)
     */
    public static String dateFormat(long date, String formatStr) {
        return new SimpleDateFormat(formatStr).format(new Date(date));
    }

    /**
     * 日期格式转换 : 2016-12-15 16:10:10 -> long(毫秒)
     */
    public static long timeFormat(String timeStr) {
        return timeFormat(timeStr, yMdHms);
    }

    /**
     * 日期格式转换 : 2016-12-15 16:10:10(formatStr) -> long(毫秒)
     */
    public static long timeFormat(String timeStr, String formatStr) {
        long time = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat(formatStr);
            time = sf.parse(timeStr).getTime();
        } catch (ParseException pE) {
            pE.printStackTrace();
        }
        return time;
    }

    /**
     * 日期格式转换 : 2016-12-15 16:10:10 -> int[]{year,month,day,hour,minute,second}
     */
    public static int[] splitTime(String timeStr) {
        return splitTime(timeFormat(timeStr));
    }

    /**
     * 日期格式转换 : long(毫秒) -> int[]{year,month,day,hour,minute,second}
     */
    public static int[] splitTime(long pTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(pTime));
        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return new int[]{year, month, day, hour, minute, second};
    }

    /**
     * 两个日期的实际差距 -> 包括相差的年月日时分秒
     * 两个参数不分先后,总是时间大的-时间小的
     *
     * @return int[]{year,month,day,hour,minute,second}
     */
    public static int[] gapSplitTime(String timeStr1, String timeStr2) {
        return gapSplitTime(timeFormat(timeStr1), timeFormat(timeStr2));
    }

    /**
     * 两个日期的实际差距 -> 包括相差的年月日时分秒
     * 两个参数不分先后,总是时间大的-时间小的
     *
     * @return int[]{year,month,day,hour,minute,second}
     */
    public static int[] gapSplitTime(long pTime1, long pTime2) {
        int[] time = splitTime(pTime1);
        int[] time2 = splitTime(pTime2);
        int year = time[0] - time2[0];
        int month = time[1] - time2[1];
        int day = time[2] - time2[2];
        int hour = time[3] - time2[3];
        int minute = time[4] - time2[4];
        int second = time[5] - time2[5];
        if (second < 0) {
            second = 60 - Math.abs(second);
            minute -= 1;
        }
        if (minute < 0) {
            minute = 60 - Math.abs(minute);
            hour -= 1;
        }
        if (hour < 0) {
            hour = 24 - Math.abs(hour);
            day -= 1;
        }
        if (day < 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, time2[0]);
            calendar.set(Calendar.MONTH, time2[1] - 1);
            int monthDay = calendar.getActualMaximum(Calendar.DATE);
            day = monthDay - Math.abs(day);
            month -= 1;
        }
        if (month < 0) {
            month = 12 - Math.abs(month);
            year -= 1;
        }
        if (year < 0) {
            return gapSplitTime(pTime2, pTime1);
        }
        return new int[]{year, month, day, hour, minute, second};
    }

    /**
     * 两个日期的差距时间
     * 两个参数不分先后,总是时间大的-时间小的
     * 1年前/2月前/3天前/4小时前/5分钟前/刚刚
     */
    public static String gapTimeStr(String timeStr1, String timeStr2) {
        return gapTimeStr(timeFormat(timeStr1), timeFormat(timeStr2));
    }

    /**
     * 两个日期的差距时间
     * 两个参数不分先后,总是时间大的-时间小的
     * 1年前/2月前/3天前/4小时前/5分钟前/刚刚
     */
    public static String gapTimeStr(long pTime1, long pTime2) {
        StringBuilder sb = new StringBuilder();
        int[] time = gapSplitTime(pTime1, pTime2);
        int year = time[0];
        int month = time[1];
        int day = time[2];
        int hour = time[3];
        int minute = time[4];
        int second = time[5];
        if (year > 0) {
            sb.append(year + "年前");
        } else if (month > 0) {
            sb.append(month + "月前");
        } else if (day > 0) {
            sb.append(day + "天前");
        } else if (hour > 0) {
            sb.append(hour + "小时前");
        } else if (minute > 0) {
            sb.append(minute + "分钟前");
        } else if (second > 0) {
            sb.append("刚刚");
        }
        return sb.toString();
    }

    /**
     * 判断是否润年
     */
    public static boolean isLeapYear(Date date) {
        return isLeapYear(date.getTime());
    }

    public static boolean isLeapYear(String timeStr) {
        return isLeapYear(timeFormat(timeStr, yMdHms));
    }

    public static boolean isLeapYear(long date) {
        return isLeapYear(Integer.parseInt(dateFormat(date, DateUtils.yyyy)));
    }

    public static boolean isLeapYear(int year) {
        if ((year % 400) == 0) {
            return true;
        } else if ((year % 100) != 0 && (year % 4) == 0) {
            return true;
        }
        return false;
    }

}
