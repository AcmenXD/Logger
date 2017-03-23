# Logger
---
超简单的 Android 日志输出工具Logger
**依赖**
---
- AndroidStudio
```
	allprojects {
			repositories {
				...
				maven { url 'https://jitpack.io' }
		    }
	}
```
```
	 compile 'com.github.AcmenXD:Logger:1.0'
```
**功能**
---
- 设置Logger开关
- 设置Logger显示级别
- 支持java任意类型的日志输出,不在局限于String类型
- 支持Throwable日志格式化输出
- 支持json格式输出
- 支持xml格式输出
- 支持日志追加到本地文件
- 支持Android Monitor中点击日志,跳转到调用代码位置
- 设置Log开关
- 设置Log开关
**配置**
---
**在Application中配置**
```java
/**
 * 设置Log开关,可根据debug-release配置
 *  默认为false
 */
Logger.setOpen(true);
/**
 * 设置Log等级, >= 这个此配置的log才会显示
 *  默认为LogType.V
 */
Logger.setLevel(LogType.V);
/**
 * 设置本地Log日志的存储路径
 *  默认为sd卡Log目录下
 * Environment.getExternalStorageDirectory().getAbsolutePath() + "/Log/"
 */
Logger.setPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Log/");
/**
 * 设置包名,当Log无tag时,默认tag为 '包名+调用类名'
 *  默认为""
 */
Logger.setPkgName(getPackageName());
```
**使用**
---
```java
/**
 * v可替换成d/i/w/e/a,对应不同等级的日志
 * test可替换成java任意类型
 */
Logger.v("test");
```
```java
// MainActivity.java:28 可点击跳转到对应代码行
V/com.acmenxd.logger.demo.MainActivity.java:╔═════════════════════════════════════════════════════════════════════════════════════════
V/com.acmenxd.logger.demo.MainActivity.java:║ * [ Logger -=(MainActivity.java:28)=- OnCreate ]
V/com.acmenxd.logger.demo.MainActivity.java:║ 	test
V/com.acmenxd.logger.demo.MainActivity.java:╚═════════════════════════════════════════════════════════════════════════════════════════
```
---
```java
/**
 * 支持任意多个参数+多类型
 */
Logger.d("test1", true);
```
```java
V/com.acmenxd.logger.demo.MainActivity.java:╔═════════════════════════════════════════════════════════════════════════════════════════
V/com.acmenxd.logger.demo.MainActivity.java:║ * [ Logger -=(MainActivity.java:29)=- OnCreate ]
V/com.acmenxd.logger.demo.MainActivity.java:║ 	param[0] = 	test1
V/com.acmenxd.logger.demo.MainActivity.java:║ 	param[1] = 	test2
V/com.acmenxd.logger.demo.MainActivity.java:╚═════════════════════════════════════════════════════════════════════════════════════════
```
---
```java
/**
 * 自定义Tag需借助LogTag.mk函数定义
 */
Logger.i(LogTag.mk("MainActivity"), "test1", "test2");
```
```java
I/MainActivity:╔═════════════════════════════════════════════════════════════════════════════════════════
I/MainActivity:║ * [ Logger -=(MainActivity.java:30)=- OnCreate ]
I/MainActivity:║ 	param[0] = 	test1
I/MainActivity:║ 	param[1] = 	test2
I/MainActivity:╚═════════════════════════════════════════════════════════════════════════════════════════
```
---
```java
/**
 * 直接传递异常
 */
Logger.e(new NullPointerException());
```
```java
W/com.acmenxd.logger.demo.MainActivity.java:╔═════════════════════════════════════════════════════════════════════════════════════════
W/com.acmenxd.logger.demo.MainActivity.java:║ * [ Logger -=(MainActivity.java:31)=- OnCreate ]
W/com.acmenxd.logger.demo.MainActivity.java:║ * Throwable Message Start ====================
W/com.acmenxd.logger.demo.MainActivity.java:║  	com.acmenxd.logger.demo.MainActivity.onCreate(MainActivity.java:31)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	android.app.Activity.performCreate(Activity.java:6303)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1108)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2398)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2505)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	android.app.ActivityThread.access$1000(ActivityThread.java:153)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	android.app.ActivityThread$H.handleMessage(ActivityThread.java:1369)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	android.os.Handler.dispatchMessage(Handler.java:102)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	android.os.Looper.loop(Looper.java:154)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	android.app.ActivityThread.main(ActivityThread.java:5466)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	java.lang.reflect.Method.invoke(Native Method)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:739)
W/com.acmenxd.logger.demo.MainActivity.java:║ 	com.android.internal.os.ZygoteInit.main(ZygoteInit.java:629)
W/com.acmenxd.logger.demo.MainActivity.java:║ * Throwable Message End ====================
W/com.acmenxd.logger.demo.MainActivity.java:╚═════════════════════════════════════════════════════════════════════════════════════════
```
---
```java
/**
 * 更多支持
 */
Logger.e(LogTag.mk("MainActivity"), new NullPointerException());
Logger.e(new NullPointerException(), "test1", "test2");
Logger.a(LogTag.mk("MainActivity"), new NullPointerException(), "test1", "test2");
```
- json格式输出
```java
/**
 * json格式输出
 */
Logger.json(LogTag.mk("jsonMainActivity"), json.toString());
```
```java
W/jsonMainActivity:╔═════════════════════════════════════════════════════════════════════════════════════════
W/jsonMainActivity:║ * [ Logger -=(MainActivity.java:50)=- OnCreate ]
W/jsonMainActivity:║ {
W/jsonMainActivity:║     "sys_time": 1481635515074,
W/jsonMainActivity:║     "data": {
W/jsonMainActivity:║         "total": 4,
W/jsonMainActivity:║         "record": [
W/jsonMainActivity:║             {
W/jsonMainActivity:║                 "amount": 1236302,
W/jsonMainActivity:║                 "create_time": 0,
W/jsonMainActivity:║                 "reality_name": "陈**",
W/jsonMainActivity:║                 "user_id": 0,
W/jsonMainActivity:║             },
W/jsonMainActivity:║             {
W/jsonMainActivity:║                 "amount": 1236302,
W/jsonMainActivity:║                 "create_time": 0,
W/jsonMainActivity:║                 "reality_name": "陈**",
W/jsonMainActivity:║                 "user_id": 0,
W/jsonMainActivity:║             }
W/jsonMainActivity:║         ],
W/jsonMainActivity:║         "prevPn": 0,
W/jsonMainActivity:║         "totalPage": 1
W/jsonMainActivity:║     },
W/jsonMainActivity:║     "code": 0,
W/jsonMainActivity:║     "msg": ""
W/jsonMainActivity:║ }
W/jsonMainActivity:╚═════════════════════════════════════════════════════════════════════════════════════════
```
- xml格式输出
```java
/**
 * xml格式输出
 */
Logger.xml(LogTag.mk("xmlMainActivity"), xml.toString());
```
```java
W/xmlMainActivity:╔═════════════════════════════════════════════════════════════════════════════════════════
W/xmlMainActivity:║ * [ Logger -=(MainActivity.java:66)=- OnCreate ]
W/xmlMainActivity:║ 	<?xml version="1.0" encoding="utf-8"?>
W/xmlMainActivity:║ 	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
W/xmlMainActivity:║ 	          package="com.acmenxd.logger.demo">
W/xmlMainActivity:║ 	    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
W/xmlMainActivity:║ 	    <application
W/xmlMainActivity:║ 	        android:name=".BaseApplication"
W/xmlMainActivity:║ 	        android:allowBackup="true"
W/xmlMainActivity:║ 	        android:icon="@mipmap/ic_launcher"
W/xmlMainActivity:║ 	        android:label="@string/app_name"
W/xmlMainActivity:║ 	        android:supportsRtl="true"
W/xmlMainActivity:║ 	        android:theme="@style/AppTheme">
W/xmlMainActivity:║ 	        <activity android:name=".MainActivity">
W/xmlMainActivity:║ 	            <intent-filter>
W/xmlMainActivity:║ 	                <action android:name="android.intent.action.MAIN"/>
W/xmlMainActivity:║ 	                <category android:name="android.intent.category.LAUNCHER"/>
W/xmlMainActivity:║ 	            </intent-filter>
W/xmlMainActivity:║ 	        </activity>
W/xmlMainActivity:║ 	    </application>
W/xmlMainActivity:║ 	</manifest>
W/xmlMainActivity:╚═════════════════════════════════════════════════════════════════════════════════════════
```
- 输出到本地文件
```java
/**
 * 日志输出到本地文件(默认路径通过Logger.setPath函数配置)
 * 如未配置路径,默认路径为sd卡Log目录下
 * 如未配置日志文件名,默认生成规则:(Log_ + 年月日时分秒 + 5位数的随机数 -> Log_2017032318065140848.txt)
 */
// 支持任意多个参数+多类型
Logger.file(new NullPointerException(), "test1", "test2");
// 自定义Tag需借助LogTag.mk函数定义
Logger.file(LogTag.mk("fileMainActivity"), new NullPointerException(), "test1", "test2");
// 指定本地日志文件名
Logger.file(LogTag.mk("fileMainActivity"), "LogTest.txt", new NullPointerException(), "test1", "test2");
// 指定本地日志文件名 + 存储路径
Logger.file(LogTag.mk("fileMainActivity"), "LogTest.txt", dir, new NullPointerException(), "test1", "test2");
```
```java
E/com.acmenxd.logger.demo.MainActivity.java:╔═════════════════════════════════════════════════════════════════════════════════════════
E/com.acmenxd.logger.demo.MainActivity.java:║ * [ Logger -=(MainActivity.java:72)=- OnCreate ]
E/com.acmenxd.logger.demo.MainActivity.java:║ save log success ! location is >> /storage/emulated/0/Log/Log_2017032318065140848.txt
E/com.acmenxd.logger.demo.MainActivity.java:╚═════════════════════════════════════════════════════════════════════════════════════════
```
**打个小广告^_^**
**gitHub** : https://github.com/AcmenXD  如对您有帮助,欢迎点Star支持,谢谢~
**技术博客** : http://blog.csdn.net/wxd_beijing
# END