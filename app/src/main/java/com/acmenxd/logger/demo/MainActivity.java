package com.acmenxd.logger.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acmenxd.logger.LogTag;
import com.acmenxd.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2016/11/22 14:36
 * @detail something
 */
public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Logger日志输出演示");
        setContentView(R.layout.activity_main);

        /**
         * 日志输出
         */
        Logger.v("test1");
        Logger.d("test1", "test2");
        Logger.i(LogTag.mk("MainActivity"), "test1", "test2");
        Logger.w(new NullPointerException());
        Logger.e(LogTag.mk("MainActivity"), new NullPointerException());
        Logger.e(new NullPointerException(), "test1", "test2");
        Logger.a(LogTag.mk("MainActivity"), new NullPointerException(), "test1", "test2");
        /**
         * json格式输出
         */
        InputStreamReader in = new InputStreamReader(getResources().openRawResource(R.raw.log_json));
        char[] b = new char[1024];
        String str = "";
        int index = 0;
        try {
            while ((index = in.read(b)) != -1) {
                str += new String(b, 0, index);
            }
            in.close();
        } catch (IOException pE) {
            Logger.e(pE);
        }
        Logger.json(LogTag.mk("jsonMainActivity"), str.toString());
        /**
         * xml格式输出
         */
        in = new InputStreamReader(getResources().openRawResource(R.raw.log_xml));
        b = new char[1024];
        str = "";
        index = 0;
        try {
            while ((index = in.read(b)) != -1) {
                str += new String(b, 0, index);
            }
            in.close();
        } catch (IOException pE) {
            Logger.e(pE);
        }
        Logger.xml(LogTag.mk("xmlMainActivity"), str.toString());
        /**
         * file格式输出 - 输出到本地文件
         */
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Log/");
        Logger.file("test1");
        Logger.file("test1", "test2");
        Logger.file(LogTag.mk("fileMainActivity"), "test1", "test2");
        Logger.file(LogTag.mk("fileMainActivity"), "", dir, "test1", "test2");
        Logger.file(LogTag.mk("fileMainActivity"), "LogTest.txt", dir, "test1", "test2");

        Logger.file(new NullPointerException());
        Logger.file(LogTag.mk("fileMainActivity"), new NullPointerException());
        Logger.file(LogTag.mk("fileMainActivity"), "", new NullPointerException());
        Logger.file(LogTag.mk("fileMainActivity"), null, new NullPointerException());
        Logger.file(LogTag.mk("fileMainActivity"), "LogTest.txt", new NullPointerException());
        Logger.file(LogTag.mk("fileMainActivity"), "LogTest.txt", dir, new NullPointerException());

        Logger.file(new NullPointerException(), "test1");
        Logger.file(new NullPointerException(), "test1", "test2");
        Logger.file(LogTag.mk("fileMainActivity"), new NullPointerException(), "test1", "test2");
        Logger.file(LogTag.mk("fileMainActivity"), "LogTest.txt", new NullPointerException(), "test1", "test2");
        Logger.file(LogTag.mk("fileMainActivity"), "LogTest.txt", dir, new NullPointerException(), "test1", "test2");
        /**
         * 提示语
         */
        TextView tv = new TextView(this);
        tv.setTextSize(40);
        tv.setTextColor(Color.BLACK);
        tv.setText("日志输出成功");
        LinearLayout.LayoutParams pa = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pa.gravity = Gravity.CENTER;
        this.addContentView(tv, pa);
    }
}
