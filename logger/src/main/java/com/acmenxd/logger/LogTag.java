package com.acmenxd.logger;

import android.support.annotation.NonNull;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2016/12/16 17:55
 * @detail 定义Logger的tag类
 */
public final class LogTag {
    private String tag;

    private LogTag(@NonNull String tag) {
        this.tag = tag;
    }

    public static LogTag mk(@NonNull String tag) {
        return new LogTag(tag);
    }

    public String gTag() {
        return tag;
    }
}
