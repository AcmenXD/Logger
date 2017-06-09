package com.acmenxd.logger;

import android.support.annotation.NonNull;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2016/11/22 14:36
 * @detail 输出日志Xml
 */
public final class XmlLog {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");//行分隔

    public static void printXml(@NonNull LogTag tag, @NonNull String headString, @NonNull String xml) {
        String message;
        if (xml == null) {
            message = headString + LINE_SEPARATOR + " Log with null object";
        } else {
            message = headString + LINE_SEPARATOR + xml;
        }
        BaseLog.printLine(LogType.XML, tag, true);
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            if (!BaseLog.isEmpty(line)) {
                BaseLog.printSub(LogType.XML, tag, "║ " + line);
            }
        }
        BaseLog.printLine(LogType.XML, tag, false);
    }
}
