package com.acmenxd.logger;

import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2016/11/22 14:36
 * @detail Logger输出总类
 */
public final class Logger extends BaseLog {
    /**
     * 初始化配置
     */
    // 包名 - 默认的Logger输出Tag
    public static String APP_PKG_NAME = "Logger";
    // Log开关 - 设置Log开关,可根据debug-release配置
    public static boolean LOG_OPEN = true;
    // Log显示Level, >= 这个Level的log才显示
    public static LogType LOG_LEVEL = LogType.V;
    // Log日志的存储路径 -  默认为sd卡Logger目录下
    public static String LOGFILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Logger/";

    private static final String PARAM = "param";
    private static final String NULL = "null";

    // V
    public static void v(@NonNull Object... msgs) {
        printLog(LogType.V, null, null, parseMsgs(msgs));
    }

    public static void v(@NonNull LogTag tag, @NonNull Object... msgs) {
        printLog(LogType.V, tag, null, parseMsgs(msgs));
    }

    public static void v(@NonNull Throwable e) {
        printLog(LogType.V, null, e);
    }

    public static void v(@NonNull LogTag tag, @NonNull Throwable e) {
        printLog(LogType.V, tag, e);
    }

    public static void v(@NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.V, null, e, parseMsgs(msgs));
    }

    public static void v(@NonNull LogTag tag, @NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.V, tag, e, parseMsgs(msgs));
    }

    // json日志
    public static void json(@NonNull String jsonFormat) {
        printLog(LogType.JSON, null, null, jsonFormat);
    }

    public static void json(@NonNull LogTag tag, @NonNull String jsonFormat) {
        printLog(LogType.JSON, tag, null, jsonFormat);
    }

    // xml日志
    public static void xml(@NonNull String xml) {
        printLog(LogType.XML, null, null, xml);
    }

    public static void xml(@NonNull LogTag tag, @NonNull String xml) {
        printLog(LogType.XML, tag, null, xml);
    }

    /**
     * 将日志写入文件
     */
    /**
     * * 注意 : 调用此函数时,如有多个参数,首个参数不能为String类型, 否则会自动调用成 file(String tag, Object... msgs)函数
     * * 尽量避免使用此函数,而使用带有Tag标记的函数
     */
    public static void file(@NonNull Object... msgs) {
        printFile(LogType.FILE, null, null, null, null, parseMsgs(msgs));
    }

    public static void file(@NonNull LogTag tag, @NonNull Object... msgs) {
        printFile(LogType.FILE, tag, null, null, null, parseMsgs(msgs));
    }

    public static void file(LogTag tag, String fileName, File dirFile, @NonNull Object... msgs) {
        printFile(LogType.FILE, tag, dirFile, fileName, null, parseMsgs(msgs));
    }

    //-------------------------------------------
    public static void file(@NonNull Throwable thr) {
        printFile(LogType.FILE, null, null, null, thr);
    }

    public static void file(@NonNull LogTag tag, @NonNull Throwable thr) {
        printFile(LogType.FILE, tag, null, null, thr);
    }

    public static void file(LogTag tag, @NonNull String fileName, @NonNull Throwable thr) {
        printFile(LogType.FILE, tag, null, fileName, thr);
    }

    public static void file(LogTag tag, String fileName, File dirFile, @NonNull Throwable thr) {
        printFile(LogType.FILE, tag, dirFile, fileName, thr);
    }
    //-------------------------------------------

    public static void file(@NonNull Throwable thr, @NonNull Object... msgs) {
        printFile(LogType.FILE, null, null, null, thr, parseMsgs(msgs));
    }

    public static void file(@NonNull LogTag tag, @NonNull Throwable thr, @NonNull Object... msgs) {
        printFile(LogType.FILE, tag, null, null, thr, parseMsgs(msgs));
    }

    public static void file(LogTag tag, @NonNull String fileName, @NonNull Throwable thr, @NonNull Object... msgs) {
        printFile(LogType.FILE, tag, null, fileName, thr, parseMsgs(msgs));
    }

    public static void file(LogTag tag, String fileName, File dirFile, @NonNull Throwable thr, @NonNull Object... msgs) {
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
    private static synchronized void printLog(@NonNull LogType type, LogTag pTag, Throwable thr, String... msgs) {
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
    private static synchronized void printFile(@NonNull LogType type, LogTag pTag, File dirFile, String fileName, Throwable thr, String... msgs) {
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
            dirFile = new File(LOGFILE_PATH);
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
    private static String[] wrapperContent(@NonNull LogTag tag, Throwable thr, String... msgs) {
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
        String tagStr = (tag == null || tag.gTag() == null) ? APP_PKG_NAME + "." + fileName : tag.gTag();
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
                Writer writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                thr.printStackTrace(printWriter);
                Throwable cause = thr.getCause();
                while (cause != null) {
                    cause.printStackTrace(printWriter);
                    cause = cause.getCause();
                }
                printWriter.close();
                stringBuilder.append("\t").append(writer.toString());
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
    public static String[] parseMsgs(@NonNull Object... msgs) {
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
    public static void d(@NonNull Object... msgs) {
        printLog(LogType.D, null, null, parseMsgs(msgs));
    }

    public static void d(@NonNull LogTag tag, @NonNull Object... msgs) {
        printLog(LogType.D, tag, null, parseMsgs(msgs));
    }

    public static void d(@NonNull Throwable e) {
        printLog(LogType.D, null, e);
    }

    public static void d(@NonNull LogTag tag, @NonNull Throwable e) {
        printLog(LogType.D, tag, e);
    }

    public static void d(@NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.D, null, e, parseMsgs(msgs));
    }

    public static void d(@NonNull LogTag tag, @NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.D, tag, e, parseMsgs(msgs));
    }

    // I
    public static void i(@NonNull Object... msgs) {
        printLog(LogType.I, null, null, parseMsgs(msgs));
    }

    public static void i(@NonNull LogTag tag, @NonNull Object... msgs) {
        printLog(LogType.I, tag, null, parseMsgs(msgs));
    }

    public static void i(@NonNull Throwable e) {
        printLog(LogType.I, null, e);
    }

    public static void i(@NonNull LogTag tag, @NonNull Throwable e) {
        printLog(LogType.I, tag, e);
    }

    public static void i(@NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.I, null, e, parseMsgs(msgs));
    }

    public static void i(@NonNull LogTag tag, @NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.I, tag, e, parseMsgs(msgs));
    }

    // W
    public static void w(@NonNull Object... msgs) {
        printLog(LogType.W, null, null, parseMsgs(msgs));
    }

    public static void w(@NonNull LogTag tag, @NonNull Object... msgs) {
        printLog(LogType.W, tag, null, parseMsgs(msgs));
    }

    public static void w(@NonNull Throwable e) {
        printLog(LogType.W, null, e);
    }

    public static void w(@NonNull LogTag tag, @NonNull Throwable e) {
        printLog(LogType.W, tag, e);
    }

    public static void w(@NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.W, null, e, parseMsgs(msgs));
    }

    public static void w(@NonNull LogTag tag, @NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.W, tag, e, parseMsgs(msgs));
    }

    // E
    public static void e(@NonNull Object... msgs) {
        printLog(LogType.E, null, null, parseMsgs(msgs));
    }

    public static void e(@NonNull LogTag tag, @NonNull Object... msgs) {
        printLog(LogType.E, tag, null, parseMsgs(msgs));
    }

    public static void e(@NonNull Throwable e) {
        printLog(LogType.E, null, e);
    }

    public static void e(@NonNull LogTag tag, @NonNull Throwable e) {
        printLog(LogType.E, tag, e);
    }

    public static void e(@NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.E, null, e, parseMsgs(msgs));
    }

    public static void e(@NonNull LogTag tag, @NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.E, tag, e, parseMsgs(msgs));
    }

    // A
    public static void a(@NonNull Object... msgs) {
        printLog(LogType.A, null, null, parseMsgs(msgs));
    }

    public static void a(@NonNull LogTag tag, @NonNull Object... msgs) {
        printLog(LogType.A, tag, null, parseMsgs(msgs));
    }

    public static void a(@NonNull Throwable e) {
        printLog(LogType.A, null, e);
    }

    public static void a(@NonNull LogTag tag, @NonNull Throwable e) {
        printLog(LogType.A, tag, e);
    }

    public static void a(@NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.A, null, e, parseMsgs(msgs));
    }

    public static void a(@NonNull LogTag tag, @NonNull Throwable e, @NonNull Object... msgs) {
        printLog(LogType.A, tag, e, parseMsgs(msgs));
    }

}
