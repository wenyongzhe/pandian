package com.supoin.commoninventory.activity;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.litesuits.go.OverloadPolicy;
import com.litesuits.go.SchedulePolicy;
import com.litesuits.go.SmartExecutor;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.DialogUtils;

/**
 * Created by Administrator on 2015/7/23.
 *
 *
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener{

	protected SmartExecutor smallExecutor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// ���ܲ������ȿ�����������[��󲢷���]����[�ȴ�����]��С
		smallExecutor = new SmartExecutor();

		// set temporary parameter just for test
		// һ�²������ý��������ԣ��������ÿ�ʵ�������

		// number of concurrent threads at the same time, recommended core size
		// is CPU count
		// �����߾������ܺ�ҵ�񳡾����Լ�����ͬһʱ�ε���󲢷�����
		smallExecutor.setCoreSize(600);

		// adjust maximum number of waiting queue size by yourself or based on
		// phone performance
		// �����߾������ܺ�ҵ�񳡾����Լ���������Ŷ��߳�����
		smallExecutor.setQueueSize(700);

		// ������������[��󲢷���]���Զ�����[�ȴ�����]���ȴ���ǰִ��������ɺ󰴲��Խ���ִ��״̬�������ִ�С�
		// smallExecutor.setSchedulePolicy(SchedulePolicy.LastInFirstRun);
		smallExecutor.setSchedulePolicy(SchedulePolicy.FirstInFistRun);

		// ���������������������[�ȴ�����]��Сʱ��ִ�й��ز��ԣ������������������
		smallExecutor.setOverloadPolicy(OverloadPolicy.DiscardOldTaskInQueue);
		myApplication.getInstance().addActivity(this);
	}
    /**
     * ��ȡ�ֻ���Ӳ����Ϣ
     */
    public String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        // ͨ�������ȡϵͳ��Ӳ����Ϣ
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                // �������� ,��ȡ˽�е���Ϣ
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + ":" + value);
                sb.append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "�ֻ���Ϣδ֪";
        }
        return sb.toString();
    }

    /**
     * ����ȫ����ʾ,ȥ����ͷ
     * <p>
     * <b>[�˷���Ӧд��setContentView֮ǰ]</b>
     *
     * @param flag
     *            true���ÿ���ȫ��ģʽ
     */
    public void setFullScreen(boolean flag) {
        if (flag) {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * ��ֹ��Ļ�ƹ�䰵
     *
     * <p>
     * <b>[�˷���Ӧд��setContentView֮ǰ]</b>
     *
     * @param flag
     *            true���ÿ���ȫ��ģʽ
     */
    public void setKeepScreenOn(boolean flag) {
        if (flag) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    /**
     * �жϰ�׿�汾�Ƿ���4.0����
     *
     * @return true:��4.0����
     */
    public boolean isAndroidVersionFour() {
        String version = Build.VERSION.RELEASE;
        int androidVersion = Integer.parseInt(version.subSequence(0, 1).toString());
        return androidVersion > 3 ? true : false;
    }

    /**
     * �˳����� [�Դ��Ի�����ʾ]
     */
    public void exit() {
        // �ص�������Ϣ,Ĭ��ʹ��Applicationʵ�����Ķ���,�����ڴ�й©
        DialogUtils.createDialog(myApplication.getInstance(), 0, "������ʾ", "ȷ���˳�����?", "ȷ��",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // �رնԻ���
                        dialog.dismiss();
                        // �˳�����
                        myApplication.getInstance().exitActivity();
                    }
                }, "ȡ��", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // �رնԻ���
                        dialog.dismiss();
                    }
                }).show();
    }
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	myApplication.getInstance().finishActivity(this);
    }
}
