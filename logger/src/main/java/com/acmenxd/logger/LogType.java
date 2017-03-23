package com.acmenxd.logger;

import android.util.Log;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2016/12/15 16:06
 * @detail 两个作用： 1、作为Log类型的标识 2、intValue来做输出日志级别控制
 */
public enum LogType {
    //逐渐增大
    V(Log.VERBOSE), D(Log.DEBUG), I(Log.INFO), W(Log.WARN), E(Log.ERROR), A(Log.ASSERT),
    JSON(Log.ASSERT + 1), XML(Log.ASSERT + 2), FILE(Log.ASSERT + 3), NULL(Log.ASSERT + 4);

    private int mType = 0x1; // 默认Log类型

    LogType(int type) {
        this.mType = type;
    }

    public int intValue() {
        return this.mType;
    }

    @Override
    public String toString() {
        switch (this.mType) {
            case 0x1:
                return "V";
            case 0x2:
                return "D";
            case 0x3:
                return "I";
            case 0x4:
                return "W";
            case 0x5:
                return "E";
            case 0x6:
                return "A";
            case 0x7:
                return "JSON";
            case 0x8:
                return "XML";
            case 0x9:
                return "FILE";
            case 0x10:
                return "NULL";
        }
        return super.toString();
    }
}
