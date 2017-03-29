package com.acmenxd.logger;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2016/11/22 14:36
 * @detail Logger输出总类
 */
public class Logger extends BaseLog {
    // Log开关
    private static boolean LOG_OPEN = true;
    // Log显示Level, >= 这个Level的log才显示
    private static LogType LOG_LEVEL = LogType.V;
    // Log日志默认保存路径
    private static File LOGFILE_PATH = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Logger/");
    // 包名
    private static String APP_PKG_NAME = "Logger";
    private static Context sContext; // 上下文对象

    private static final String PARAM = "param";
    private static final String NULL = "null";

    /**
     * 初始化
     * context必须设置
     */
    public static void setContext(Context pContext) {
        sContext = pContext;
        APP_PKG_NAME = sContext.getPackageName();
    }

    /**
     * 设置Log开关,可根据debug-release配置
     * 默认为true
     */
    public static void setOpen(boolean isOpen) {
        LOG_OPEN = isOpen;
    }

    /**
     * 设置Log等级, >= 这个配置的log才会显示
     * 默认为Log.VERBOSE = 2
     */
    public static void setLevel(int level) {
        LogType[] types = LogType.values();
        for (int i = 0, len = types.length; i < len; i++) {
            if (level == types[i].intValue()) {
                LOG_LEVEL = types[i];
            }
        }
    }

    /**
     * 设置本地Log日志的存储路径
     * 默认为sd卡Logger目录下
     * Environment.getExternalStorageDirectory().getAbsolutePath() + "/Logger/"
     */
    public static void setPath(String path) {
        LOGFILE_PATH = new File(path);
    }

    // V
    public static void v(Object... msgs) {
        printLog(LogType.V, null, null, parseMsgs(msgs));
    }

    public static void v(LogTag tag, Object... msgs) {
        printLog(LogType.V, tag, null, parseMsgs(msgs));
    }

    public static void v(Throwable e) {
        printLog(LogType.V, null, e);
    }

    public static void v(LogTag tag, Throwable e) {
        printLog(LogType.V, tag, e);
    }

    public static void v(Throwable e, Object... msgs) {
        printLog(LogType.V, null, e, parseMsgs(msgs));
    }

    public static void v(LogTag tag, Throwable e, Object... msgs) {
        printLog(LogType.V, tag, e, parseMsgs(msgs));
    }

    // json日志
    public static void json(String jsonFormat) {
        printLog(LogType.JSON, null, null, jsonFormat);
    }

    public static void json(LogTag tag, String jsonFormat) {
        printLog(LogType.JSON, tag, null, jsonFormat);
    }

    // xml日志
    public static void xml(String xml) {
        printLog(LogType.XML, null, null, xml);
    }

    public static void xml(LogTag tag, String xml) {
        printLog(LogType.XML, tag, null, xml);
    }

    /**
     * 将日志写入文件
     */
    /**
     * * 注意 : 调用此函数时,如有多个参数,首个参数不能为String类型, 否则会自动调用成 file(String tag, Object... msgs)函数
     * * 尽量避免使用此函数,而使用带有Tag标记的函数
     */
    public static void file(Object... msgs) {
        printFile(LogType.FILE, null, null, null, null, parseMsgs(msgs));
    }

    public static void file(LogTag tag, Object... msgs) {
        printFile(LogType.FILE, tag, null, null, null, parseMsgs(msgs));
    }

    public static void file(LogTag tag, String fileName, File dirFile, Object... msgs) {
        printFile(LogType.FILE, tag, dirFile, fileName, null, parseMsgs(msgs));
    }

    //-------------------------------------------
    public static void file(Throwable thr) {
        printFile(LogType.FILE, null, null, null, thr);
    }

    public static void file(LogTag tag, Throwable thr) {
        printFile(LogType.FILE, tag, null, null, thr);
    }

    public static void file(LogTag tag, String fileName, Throwable thr) {
        printFile(LogType.FILE, tag, null, fileName, thr);
    }

    public static void file(LogTag tag, String fileName, File dirFile, Throwable thr) {
        printFile(LogType.FILE, tag, dirFile, fileName, thr);
    }
    //-------------------------------------------

    public static void file(Throwable thr, Object... msgs) {
        printFile(LogType.FILE, null, null, null, thr, parseMsgs(msgs));
    }

    public static void file(LogTag tag, Throwable thr, Object... msgs) {
        printFile(LogType.FILE, tag, null, null, thr, parseMsgs(msgs));
    }

    public static void file(LogTag tag, String fileName, Throwable thr, Object... msgs) {
        printFile(LogType.FILE, tag, null, fileName, thr, parseMsgs(msgs));
    }

    public static void file(LogTag tag, String fileName, File dirFile, Throwable thr, Object... msgs) {
        printFile(LogType.FILE, tag, dirFile, fileName, thr, parseMsgs(msgs));
    }


    /**
     * 输出日志
     *
     * @param type
     * @param pTag
     * @param thr
     * @param msgs
     */
    private static void printLog(LogType type, LogTag pTag, Throwable thr, String... msgs) {
        if (!LOG_OPEN) {
            //检测开关
            return;
        }
        if (type.intValue() < LOG_LEVEL.intValue()) {
            //检测显示等级
            return;
        }
        //包装内容
        String[] contents = wrapperContent(pTag, thr, msgs);
        LogTag tag = LogTag.mk(contents[0]);
        String msgStr = contents[1];
        String headStr = contents[2];
        String className = contents[3];
        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case A:
                printLog(type, tag, headStr + msgStr);
                break;
            case JSON:
                JsonLog.printJson(tag, headStr, msgStr);
                break;
            case XML:
                XmlLog.printXml(tag, headStr, msgStr);
                break;
        }
    }

    /**
     * 输出日志到文件
     *
     * @param type
     * @param pTag
     * @param dirFile
     * @param fileName
     * @param thr
     * @param msgs
     */
    private static void printFile(LogType type, LogTag pTag, File dirFile, String fileName, Throwable thr, String... msgs) {
        if (!LOG_OPEN) {
            //检测开关
            return;
        }
        if (type.intValue() < LOG_LEVEL.intValue()) {
            //检测显示等级
            return;
        }
        //路径
        if (dirFile == null) {
            dirFile = LOGFILE_PATH;
        }
        //包装内容
        String[] contents = wrapperContent(pTag, thr, msgs);
        LogTag tag = LogTag.mk(contents[0]);
        String msgStr = contents[1];
        String headStr = contents[2];
        String className = contents[3];
        FileLog.printFile(tag, headStr, className, msgStr, dirFile, fileName);
    }

    /**
     * 获取tagStr/msgStr/headStr
     *
     * @param tag
     * @param thr
     * @param msgs
     * @return
     */
    private static String[] wrapperContent(LogTag tag, Throwable thr, String... msgs) {
        int index = 5;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String fileName = stackTrace[index].getFileName();
        String className = stackTrace[index].getClassName();
        String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();
        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        StringBuilder sbHeadStr = new StringBuilder();
        sbHeadStr.append("* [ Logger -=(").append(fileName).append(":").append(lineNumber).append(")=- ")
                .append(methodNameShort).append(" ]");
        String tagStr = (tag == null || tag.gTag() == null) ? APP_PKG_NAME + fileName : tag.gTag();
        String msgStr = getMessagesStr(thr, msgs);
        return new String[]{tagStr, msgStr, sbHeadStr.toString(), className};
    }

    /**
     * 过去message + throwable 内容
     *
     * @param thr
     * @param msgs
     * @return
     */
    private static String getMessagesStr(Throwable thr, String... msgs) {
        int len = msgs.length;
        if ((msgs != null && len >= 1) || (thr != null)) {
            StringBuilder stringBuilder = new StringBuilder();
            if (msgs != null && len >= 1) {
                stringBuilder.append("\n");
                for (int i = 0; i < len; i++) {
                    String message = msgs[i].replace("\n", "\n\t");
                    if (len > 1) {
                        stringBuilder.append("\t").append(PARAM).append("[").append(i).append("]").append(" = ");
                    }
                    if (message == null) {
                        stringBuilder.append("\t").append(NULL);
                    } else {
                        stringBuilder.append("\t").append(message.toString());
                    }
                    if (i < len - 1) {
                        stringBuilder.append("\n");
                    }
                }
            }
            if (thr != null) {
                stringBuilder.append("\n");
                stringBuilder.append("* Throwable Message Start ====================\n ");
                StackTraceElement[] elements = thr.getStackTrace();
                for (int i = 0; i < elements.length; i++) {
                    stringBuilder.append("\t").append(elements[i]).append("\n");
                }
                stringBuilder.append("* Throwable Message End ====================");
            }
            return stringBuilder.toString();
        } else {
            return "\n" + " Log with null message";
        }
    }

    /**
     * Object 可变参 -> 转换为 String
     *
     * @param msgs
     * @return
     */
    public static String[] parseMsgs(Object... msgs) {
        String[] rmsgs = null;
        if (msgs != null) {
            int len = msgs.length;
            rmsgs = new String[len];
            for (int i = 0; i < len; i++) {
                rmsgs[i] = String.valueOf(msgs[i]);
            }
        }
        return rmsgs;
    }

    // D
    public static void d(Object... msgs) {
        printLog(LogType.D, null, null, parseMsgs(msgs));
    }

    public static void d(LogTag tag, Object... msgs) {
        printLog(LogType.D, tag, null, parseMsgs(msgs));
    }

    public static void d(Throwable e) {
        printLog(LogType.D, null, e);
    }

    public static void d(LogTag tag, Throwable e) {
        printLog(LogType.D, tag, e);
    }

    public static void d(Throwable e, Object... msgs) {
        printLog(LogType.D, null, e, parseMsgs(msgs));
    }

    public static void d(LogTag tag, Throwable e, Object... msgs) {
        printLog(LogType.D, tag, e, parseMsgs(msgs));
    }

    // I
    public static void i(Object... msgs) {
        printLog(LogType.I, null, null, parseMsgs(msgs));
    }

    public static void i(LogTag tag, Object... msgs) {
        printLog(LogType.I, tag, null, parseMsgs(msgs));
    }

    public static void i(Throwable e) {
        printLog(LogType.I, null, e);
    }

    public static void i(LogTag tag, Throwable e) {
        printLog(LogType.I, tag, e);
    }

    public static void i(Throwable e, Object... msgs) {
        printLog(LogType.I, null, e, parseMsgs(msgs));
    }

    public static void i(LogTag tag, Throwable e, Object... msgs) {
        printLog(LogType.I, tag, e, parseMsgs(msgs));
    }

    // W
    public static void w(Object... msgs) {
        printLog(LogType.W, null, null, parseMsgs(msgs));
    }

    public static void w(LogTag tag, Object... msgs) {
        printLog(LogType.W, tag, null, parseMsgs(msgs));
    }

    public static void w(Throwable e) {
        printLog(LogType.W, null, e);
    }

    public static void w(LogTag tag, Throwable e) {
        printLog(LogType.W, tag, e);
    }

    public static void w(Throwable e, Object... msgs) {
        printLog(LogType.W, null, e, parseMsgs(msgs));
    }

    public static void w(LogTag tag, Throwable e, Object... msgs) {
        printLog(LogType.W, tag, e, parseMsgs(msgs));
    }

    // E
    public static void e(Object... msgs) {
        printLog(LogType.E, null, null, parseMsgs(msgs));
    }

    public static void e(LogTag tag, Object... msgs) {
        printLog(LogType.E, tag, null, parseMsgs(msgs));
    }

    public static void e(Throwable e) {
        printLog(LogType.E, null, e);
    }

    public static void e(LogTag tag, Throwable e) {
        printLog(LogType.E, tag, e);
    }

    public static void e(Throwable e, Object... msgs) {
        printLog(LogType.E, null, e, parseMsgs(msgs));
    }

    public static void e(LogTag tag, Throwable e, Object... msgs) {
        printLog(LogType.E, tag, e, parseMsgs(msgs));
    }

    // A
    public static void a(Object... msgs) {
        printLog(LogType.A, null, null, parseMsgs(msgs));
    }

    public static void a(LogTag tag, Object... msgs) {
        printLog(LogType.A, tag, null, parseMsgs(msgs));
    }

    public static void a(Throwable e) {
        printLog(LogType.A, null, e);
    }

    public static void a(LogTag tag, Throwable e) {
        printLog(LogType.A, tag, e);
    }

    public static void a(Throwable e, Object... msgs) {
        printLog(LogType.A, null, e, parseMsgs(msgs));
    }

    public static void a(LogTag tag, Throwable e, Object... msgs) {
        printLog(LogType.A, tag, e, parseMsgs(msgs));
    }

}
