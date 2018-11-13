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
    //����һ��ϵͳ��Ĭ�ϵ��쳣�����handler
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
     * ��ĳһ���쳣 û�д�����ʾ�Ĳ����ʱ��, ϵͳ����� Ĭ�ϵ��쳣����Ĵ��� ��������쳣
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        System.out.println("�������쳣,���Ǳ�������");
        StringWriter wr = new StringWriter();
        PrintWriter pw = new PrintWriter(wr);
        ex.printStackTrace(pw);
        StringBuilder sb = new StringBuilder();

        try {
            PackageInfo packinfo = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            String version = packinfo.versionName;
            sb.append("������Ϣ:\n");
            sb.append("�汾��:" + version + "\n");

            String errorlog = wr.toString();
            sb.append(errorlog);
            sb.append("\n");

            // ��ȡ��ǰ�ֻ�����ϵͳ����Ϣ.
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);// ��������,���Ի�ȡ˽�г�Ա��������Ϣ
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
            //����ϵͳ��Ĭ�ϵ��쳣������ ��������쳣
            defaultHandler.uncaughtException(thread, ex);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}