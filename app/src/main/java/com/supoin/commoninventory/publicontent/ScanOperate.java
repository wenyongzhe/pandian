package com.supoin.commoninventory.publicontent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;

import com.supoin.commoninventory.util.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScanOperate {
    // private BarcodeScan mBarcodeScan;
    public static final int MESSAGE_TEXT = 1;
    private ScanBroadcastReceiver sanBroadcast = new ScanBroadcastReceiver();
    private String scanCode;
    private Vibrator mvibrator;
    private boolean g_mvibrator = true;
    private boolean g_sound = true;
    private MediaPlayer mmediaplayer;
    public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";
    public static final String SCN_CUST_ACTION_START = "android.intent.action.SCANNER_BUTTON_DOWN";
    public static final String SCN_CUST_ACTION_CANCEL = "android.intent.action.SCANNER_BUTTON_UP";
    private static final String SCANNER_CTRL_FILE = "/sys/devices/platform/scan_se955/se955_state";
    private static final String ACTION_SCANNER_SWITCH = "com.android.server.scannerservice.onoff";
    private Context mContext;

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    public void onCreate(Context context, int scanok) {
        // mBarcodeScan = new BarcodeScan(context);
        IntentFilter intentFilter = new IntentFilter(SCN_CUST_ACTION_SCODE);
        context.registerReceiver(sanBroadcast, intentFilter);
        mContext = context;
        // mvibrator = (Vibrator)
        // context.getSystemService(Context.VIBRATOR_SERVICE);
        // mediaPlayerInit(context,scanok);
    }

    private void writeToScannerCtrlFile(String data) {
        try {
            FileOutputStream fis = new FileOutputStream(new File(
                    SCANNER_CTRL_FILE));
            fis.write(data.getBytes());
            fis.close();
        } catch (IOException e) {
            Utility.deBug("Exception", "File write failed: " + e.toString());
        }
    }

    // public void onToggleClicked(View view) {
    // // Is the toggle on?
    // boolean on = ((Switch) view).isChecked();
    //
    // if (on) {
    // writeToScannerCtrlFile("4");
    // } else {
    // writeToScannerCtrlFile("5");
    // }
    // }
    public void setScannerContinuousMode() {
        // mBarcodeScan.setScannerContinuousMode();
        writeToScannerCtrlFile("4");
    }

    public void setScannerContinusousModeShutdown() {
        // mBarcodeScan.scannerContinusousModeShutdown();
        writeToScannerCtrlFile("5");
    }

    public void openScannerPower(Boolean is_on) {
        if (is_on) {
            writeToScannerCtrlFile("4");
            Intent scannerIntent = new Intent(ScanOperate.ACTION_SCANNER_SWITCH);
            scannerIntent.putExtra("scanneronoff", 1);
            mContext.sendBroadcast(scannerIntent);
        } else {
            writeToScannerCtrlFile("5");
            Intent scannerIntent = new Intent(ScanOperate.ACTION_SCANNER_SWITCH);
            scannerIntent.putExtra("scanneronoff", 0);
            mContext.sendBroadcast(scannerIntent);

        }
    }

    public void openScanLight(boolean is_on){
        if(is_on){
            mContext.sendBroadcast(new Intent("com.android.server.scannerservice.onoff")
                    .putExtra("scanneronoff",1));
        }else{
            mContext.sendBroadcast(new Intent("com.android.server.scannerservice.onoff")
                    .putExtra("scanneronoff",0));
        }
    }
    private void mediaPlayerInit(Context context, int resid) {
        // mmediaplayer = new MediaPlayer();
        // mmediaplayer = MediaPlayer.create(context, resid);
        // mmediaplayer.setLooping(false);
    }

    public void mediaPlayer() {
        // if (g_sound == true) {
        // mmediaplayer.start();
        // }
    }

    public void mediaPlayerfinish() {
        // mmediaplayer.stop();
        // mmediaplayer.release();
    }

    public void setVibratortime(int times) {
        // if ( g_mvibrator == true)
        // mvibrator.vibrate(times);
    }

    public void onResume(Context context) {
        // start the scanner.
//         mBarcodeScan.open();
//         Intent scannerIntent = new Intent(
//         ScanOperate.SCN_CUST_ACTION_START);
//         context.sendBroadcast(scannerIntent);
    }

    public void onStop(Context context) {
        // close scanner
        // mBarcodeScan.close();
        // Intent scannerIntent = new Intent(
        // ScanOperate.SCN_CUST_ACTION_CANCEL);
        // context.sendBroadcast(scannerIntent);
    }

    public void onDestroy(Context context) {
        context.unregisterReceiver(sanBroadcast);
        mediaPlayerfinish();
    }

    // Receive the scan data.
    public class ScanBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//			if (intent.getAction().equals(SCN_CUST_ACTION_SCODE)) {
//				String message = intent.getStringExtra(SCN_CUST_EX_SCODE);
//				Message m = Message.obtain(mHandler, MESSAGE_TEXT);
//				// 第一代需要这样改
//				// m.obj = message.substring(0,
//				// message.length()-1);//因为获取到的条码后会加个\n的缘故
//				// 第二代的不需要
//				String tempStr = message.substring(0, message.length());
//
//				LogUtil.i(tempStr);
//
//				if (tempStr.substring(tempStr.length() - 1).equals("\n")) {
//					m.obj = message.substring(0, message.length() - 1);
//					// m.obj=message.substring(0,message.indexOf("\\"));
//				} else
//					m.obj = message.substring(0, message.length());
//				// m.obj = message.substring(0, message.indexOf("\\"));
//				mHandler.sendMessage(m);
//			}
            if (intent.getAction().equals(SCN_CUST_ACTION_SCODE)&&mHandler!=null) {
                String message = intent.getStringExtra(SCN_CUST_EX_SCODE);
                Message m = Message.obtain(mHandler, MESSAGE_TEXT);
                m.obj = message.substring(0, message.length());//因为获取到的条码后会加个\n的缘故
                mHandler.sendMessage(m);
            }
        }

    }

    public void Scanning() {
        // mBarcodeScan.scanning();
    }

    public void startScan(Handler handler) {
        // Intent scannerIntent = new Intent(SCN_CUST_ACTION_START);
        // sendBroadcast(scannerIntent);
        Message m = Message.obtain(handler);
        m.what = 1;
        handler.sendMessage(m);
    }

    public Handler mHandler;

}