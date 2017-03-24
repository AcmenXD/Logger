package com.acmenxd.logger;

import android.text.TextUtils;

import com.acmenxd.logger.utils.LoggerUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2016/11/22 14:36
 * @detail 输出日志到文件
 */
public class FileLog {

    public static void printFile(LogTag tag, String headString, String className, String msg, File dirFile, String fileName) {
        fileName = (TextUtils.isEmpty(fileName)) ? getFileName() : fileName;
        String str = "";
        BaseLog.printLine(LogType.FILE, tag, true);
        BaseLog.printSub(LogType.FILE, tag, "║ " + headString);
        if (save(tag, dirFile, fileName, headString, className, msg)) {
            str = "\n║ save log success ! location is >> " + dirFile.getAbsolutePath() + "/" + fileName;
        } else {
            str = "\n║ save log fails !";
        }
        BaseLog.printSub(LogType.FILE, tag, str);
        BaseLog.printLine(LogType.FILE, tag, false);
    }

    private static boolean save(LogTag tag, File dir, String fileName, String headString, String className, String msg) {
        String str = null;
        if (dir == null) {
            str = "Source must not be null";
        } else if (!dir.exists()) {
            dir.mkdirs();
            if (!dir.exists()) {
                str = "Source '" + dir.getAbsolutePath() + "' can't create";
            }
        }
        if (!TextUtils.isEmpty(str)) {
            BaseLog.printSub(LogType.FILE, tag, "║ " + str);
            return false;
        }
        File file = new File(dir, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException pE) {
                pE.printStackTrace();
            }
        }
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        try {
            outputStream = new FileOutputStream(file, true);
            outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(headString);
            outputStreamWriter.write("\n* AbsolutePath:" + className);
            outputStreamWriter.write("\n* Logger : " + dateStr);
            outputStreamWriter.write("\n* Details:" + msg + "\n\n");
            outputStreamWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException pE) {
                pE.printStackTrace();
            }
        }
        return true;
    }

    private static String getFileName() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder("Log_");
        stringBuilder.append(LoggerUtils.getRandomByTime());
        stringBuilder.append(".txt");
        return stringBuilder.toString();
    }

}
