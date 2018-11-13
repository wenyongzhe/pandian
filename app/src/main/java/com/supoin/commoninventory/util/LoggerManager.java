package com.supoin.commoninventory.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import de.mindpipe.android.logging.log4j.LogConfigurator;

import android.os.Environment;
import android.util.Log;
public class LoggerManager {

	private static int SDCARD_LOG_FILE_SAVE_DAYS = 1;// sd������־�ļ�����ౣ������
	private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// ��־�ļ���ʽ 
	private static Logger logger ; 
	
	private LoggerManager(){}
	/**
	 * ��ȡ��־���ʵ��
	 * @return
	 */
	public static Logger getLoggerInstance(){
		if(logger==null){
			initLog();
			logger = Logger.getLogger(LoggerManager.class.getClass());   
		}
		return logger;
	}
	
	private static void initLog(){
		
		 LogConfigurator logConfigurator = new LogConfigurator();  
		   
	
		 File sdPath=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Supoin/log");
		 if(!sdPath.exists()){
			 sdPath.mkdirs();
		 }
		 
		 File logFile = new File(sdPath,logfile.format(new Date())+"AppLog"+".txt");
		 if(!logFile.exists()){
			 try {
				 logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 
        logConfigurator.setFileName(logFile.getAbsolutePath());  
    //    logConfigurator.setRootLevel(Level.DEBUG); 
        
        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");  

        logConfigurator.setMaxFileSize(1024 * 1024 * 5);  

        logConfigurator.setImmediateFlush(true);  

        logConfigurator.configure();  
        
	}
	
	
	public static void cleanFile() {
		// ���ֻ��Ҫɾ���ļ��е�һ������������Ҫ��������ַ�����һЩ����
		String cleanStr = "";
		FileOutputStream fileOutputStream = null;
		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Supoin/log",
				"AppLog"+".txt");

		try {
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(cleanStr.getBytes());
			fileOutputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getExceptionMessage(Exception ex){
		String result="";
		StackTraceElement[] stes = ex.getStackTrace();
		for(int i=0;i<stes.length;i++){
			result=result+stes[i].getClassName() 
			+ "." + stes[i].getMethodName() 
			+ "  " +"line:"+ stes[i].getLineNumber() 
			+"\r\n";
		}
		return result;
	}
	public static String getStackTrace(StackTraceElement[] stackTrace){
		String result="";
		StackTraceElement[] stes =stackTrace;
		for(int i=0;i<stes.length;i++){
			result=result+stes[i].getClassName() 
			+ "." + stes[i].getMethodName() 
			+ "  " +"line:"+ stes[i].getLineNumber()
			+"\r\n";
		}
		return result;
	}
	public static String getLogMessage(String msg){  
        String result="";  
        StackTraceElement[] stes =  Thread.currentThread().getStackTrace();  
        for(int i=0;i<stes.length;i++){  
            result=result+stes[i].getClassName()   
            + "." + stes[i].getMethodName()   
            + "  "+"line:"  + stes[i].getLineNumber() 
            +"  "+msg
            +"\r\n";  
        }  
        return result;  
    }  
      
	 /** 
     * ɾ���ƶ�����־�ļ� 
     * */  
	public static void clearCacheFile(final String dir) {
		 String needDelFiel = logfile.format(getDateBefore());  
		
		File cacheDir = new File(dir);
		if (!cacheDir.exists()) {
			return;
		}
		Log.e(dir,"--------����"+cacheDir.listFiles().length+"�������ļ�");
		// ��ֹlistFiles()����ANR
		File[] files = cacheDir.listFiles();
		for (int i = 0; i < files.length; i++) {
//			String fileDate=files[i].getName().substring(0, 10);
//			if(fileDate.compareTo(Utility.getCurDate().toString())!=0)
				files[i].delete();
		}
	}
    
    /** 
     * �õ�����ʱ��ǰ�ļ������ڣ������õ���Ҫɾ������־�ļ��� 
     * */  
    private static Date getDateBefore() {  
        Date nowtime = new Date();  
        Calendar now = Calendar.getInstance();  
        now.setTime(nowtime);  
        now.set(Calendar.DATE, now.get(Calendar.DATE)  
                - SDCARD_LOG_FILE_SAVE_DAYS);  
        return now.getTime();  
    }  
    
}
