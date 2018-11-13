package com.supoin.commoninventory.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import junit.framework.Test;

import android.content.Context;
import android.os.Environment;

public class downFileManager {
	Context  app_ctx;
	File  file;
	String SDPATH;
	String FILESPATH;
	public downFileManager(Context ctx)
	{
		//检查目录是否存在
		
		app_ctx = ctx;
		SDPATH = Environment.getExternalStorageDirectory().getPath() + "/";
		FILESPATH = app_ctx.getFilesDir().getPath() + "/";
	}
	//sd卡是否存在
	public boolean isSdExiting()
	{
		return Environment.getExternalStorageState().equals(  
		        android.os.Environment.MEDIA_MOUNTED);
	}
	//常见平台根目录
	 public File creatSDFile(String fileName) throws IOException {
		  File file = new File(SDPATH + fileName);
		  file.createNewFile();
		  return file;
		 }
	 public File creatDataFile(String fileName) throws IOException {
		  File file = new File(FILESPATH + fileName);
		  file.createNewFile();
		  return file;
		 
		 }
	
	 public void writeFileData(String fileName,String message){ 

	       try{ 

	        FileOutputStream fout =app_ctx.openFileOutput(fileName, app_ctx.MODE_PRIVATE);
	        
	        byte [] bytes = message.getBytes(); 

	        fout.write(bytes); 

	         fout.close(); 

	        } 

	       catch(Exception e){ 

	        e.printStackTrace(); 

	       } 

	   }    
	 public String readFileData(String fileName){ 

	        String res=""; 

	        try{ 

	         FileInputStream fin = app_ctx.openFileInput(fileName); 

	         int length = fin.available(); 

	         byte [] buffer = new byte[length]; 

	         fin.read(buffer);     

	         res = EncodingUtils.getString(buffer, "UTF-8"); 

	         fin.close();     

	        } 

	        catch(Exception e){ 

	         e.printStackTrace(); 

	        } 

	        return res; 

	    }   
	 public void writeFileSdcard(String fileName,String message){ 

	       try{ 

	        //FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);

	       FileOutputStream fout = new FileOutputStream(fileName);

	        byte [] bytes = message.getBytes(); 

	        fout.write(bytes); 

	         fout.close(); 

	        } 

	       catch(Exception e){ 

	        e.printStackTrace(); 

	       } 

	   }
	 public String readFileSdcard(String fileName){

	        String res=""; 

	        try{ 

	         FileInputStream fin = new FileInputStream(fileName); 

	         int length = fin.available(); 

	         byte [] buffer = new byte[length]; 

	         fin.read(buffer);     

	         res = EncodingUtils.getString(buffer, "UTF-8"); 

	         fin.close();     

	        } 

	        catch(Exception e){ 

	         e.printStackTrace(); 

	        } 

	        return res; 

	   }

}
