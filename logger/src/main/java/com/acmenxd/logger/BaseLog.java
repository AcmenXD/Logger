package com.acmenxd.logger;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2016/11/22 14:36
 * @detail 顶级Logger类
 */
public abstract class BaseLog {
    //单条输出最大字符数
    private static final int MAX_LENGTH = 5000;

    protected final static void printSub(@NonNull LogType type, @NonNull LogTag tag, @NonNull String msg) {
        switch (type) {
            case V:
                Log.v(tag.gTag(), msg);
                break;
            case D:
                Log.d(tag.gTag(), msg);
                break;
            case I:
                Log.i(tag.gTag(), msg);
                break;
            case W:
                Log.w(tag.gTag(), msg);
                break;
            case E:
                Log.e(tag.gTag(), msg);
                break;
            case A:
                Log.wtf(tag.gTag(), msg);
                break;
            case JSON:
                Log.w(tag.gTag(), msg);
                break;
            case XML:
                Log.w(tag.gTag(), msg);
                break;
            case FILE:
                Log.e(tag.gTag(), msg);
                break;
        }
    }

    protected final static void printLog(@NonNull LogType type, @NonNull LogTag tag, @NonNull String message) {
        String msgs[] = message.split("\n");
        printLine(type, tag, true);
        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            int index = 0;
            int msgLen = msg.length();
            int countOfSub = msgLen / MAX_LENGTH; //输出条数
            if (countOfSub > 0) {
                for (int j = 0; j < countOfSub; j++) {
                    int len = index + MAX_LENGTH;
                    String sub = msg.substring(index, len < msgLen ? len : msgLen);
                    printSub(type, tag, "║ " + sub);
                    index += MAX_LENGTH;
                }
                printSub(type, tag, "║ " + msg.substring(index, msg.length()));
            } else {
                printSub(type, tag, "║ " + msg);
            }
        }
        printLine(type, tag, false);
    }

    /**
     * 输出行标记
     *
     * @param tag
     * @param isTop
     */
    protected final static void printLine(@NonNull LogType type, @NonNull LogTag tag, boolean isTop) {
        if (isTop) {
            printSub(type, tag, "╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        } else {
            printSub(type, tag, "╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    protected final static boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || TextUtils.isEmpty(line.trim()) || line.equals("\n") || line.equals("\t");
    }

}