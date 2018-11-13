package com.supoin.commoninventory.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;

public class MyCrashHandler implements UncaughtExceptionHandler {
    private MyCrashHandler() {
    };
    private static UncaughtExceptionHandler defaultHandler;
    //定义一个系统的默认的异常处理的handler
    private static MyCrashHandler myCrashHandler;
    private static Context mContext;

    public synchronized static MyCrashHandler getInstance(Context context) {
        if (myCrashHandler == null) {
            myCrashHandler = new MyCrashHandler();
            mContext = context;
            defaultHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
        }
        return myCrashHandler;
    }

    /**
     * 当某一个异常 没有代码显示的捕获的时候, 系统会调用 默认的异常处理的代码 处理这个异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        System.out.println("出现了异常,但是被捕获了");
        StringWriter wr = new StringWriter();
        PrintWriter pw = new PrintWriter(wr);
        ex.printStackTrace(pw);
        StringBuilder sb = new StringBuilder();

        try {
            PackageInfo packinfo = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            String version = packinfo.versionName;
            sb.append("错误信息:\n");
            sb.append("版本号:" + version + "\n");

            String errorlog = wr.toString();
            sb.append(errorlog);
            sb.append("\n");

            // 获取当前手机操作系统的信息.
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);// 暴力反射,可以获取私有成员变量的信息
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name+"="+value+"\n");
            }
            String time ="time:"+ System.currentTimeMillis();
            sb.append(time);
            String log = sb.toString();
            File file = new File(Environment.getExternalStorageDirectory()+"/Supoin/log/","error.log");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(log.getBytes());
            fos.flush();
            fos.close();
            
            android.os.Process.killProcess(android.os.Process.myPid());
            //调用系统的默认的异常处理方法 处理这个异常
            defaultHandler.uncaughtException(thread, ex);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}