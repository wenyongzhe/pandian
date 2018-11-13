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

		// 智能并发调度控制器：设置[最大并发数]，和[等待队列]大小
		smallExecutor = new SmartExecutor();

		// set temporary parameter just for test
		// 一下参数设置仅用来测试，具体设置看实际情况。

		// number of concurrent threads at the same time, recommended core size
		// is CPU count
		// 开发者均衡性能和业务场景，自己调整同一时段的最大并发数量
		smallExecutor.setCoreSize(600);

		// adjust maximum number of waiting queue size by yourself or based on
		// phone performance
		// 开发者均衡性能和业务场景，自己调整最大排队线程数量
		smallExecutor.setQueueSize(700);

		// 任务数量超出[最大并发数]后，自动进入[等待队列]，等待当前执行任务完成后按策略进入执行状态：后进先执行。
		// smallExecutor.setSchedulePolicy(SchedulePolicy.LastInFirstRun);
		smallExecutor.setSchedulePolicy(SchedulePolicy.FirstInFistRun);

		// 后续添加新任务数量超出[等待队列]大小时，执行过载策略：抛弃队列内最旧任务。
		smallExecutor.setOverloadPolicy(OverloadPolicy.DiscardOldTaskInQueue);
		myApplication.getInstance().addActivity(this);
	}
    /**
     * 获取手机的硬件信息
     */
    public String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        // 通过反射获取系统的硬件信息
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                // 暴力反射 ,获取私有的信息
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + ":" + value);
                sb.append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "手机信息未知";
        }
        return sb.toString();
    }

    /**
     * 设置全屏显示,去掉标头
     * <p>
     * <b>[此方法应写在setContentView之前]</b>
     *
     * @param flag
     *            true设置开启全屏模式
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
     * 禁止屏幕灯光变暗
     *
     * <p>
     * <b>[此方法应写在setContentView之前]</b>
     *
     * @param flag
     *            true设置开启全屏模式
     */
    public void setKeepScreenOn(boolean flag) {
        if (flag) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    /**
     * 判断安卓版本是否是4.0以上
     *
     * @return true:是4.0以上
     */
    public boolean isAndroidVersionFour() {
        String version = Build.VERSION.RELEASE;
        int androidVersion = Integer.parseInt(version.subSequence(0, 1).toString());
        return androidVersion > 3 ? true : false;
    }

    /**
     * 退出程序 [自带对话框提示]
     */
    public void exit() {
        // 回调错误信息,默认使用Application实例化的对象,避免内存泄漏
        DialogUtils.createDialog(myApplication.getInstance(), 0, "操作提示", "确认退出程序?", "确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 关闭对话框
                        dialog.dismiss();
                        // 退出程序
                        myApplication.getInstance().exitActivity();
                    }
                }, "取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 关闭对话框
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
