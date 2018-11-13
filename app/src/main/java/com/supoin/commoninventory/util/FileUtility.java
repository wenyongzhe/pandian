package com.supoin.commoninventory.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import com.supoin.commoninventory.constvalue.DrugSystemConst;



import android.app.Application;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.util.Xml;


public class FileUtility {

	private String SDCardRoot;

	public FileUtility() {
		SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
	}
	public static String getSdCardPath(){
		return Environment.getExternalStorageDirectory()
				.getAbsolutePath();
	}
	public File createFileInSDCard(String fileName, String filePath)
			throws IOException {
		
		File file = new File(filePath + fileName);
		if (!file.exists()) {
			if (!file.createNewFile()) {
				return null;
			}
		}
		return file;
	}
	public boolean ExistSDCard() 
	{  
		  if (android.os.Environment.getExternalStorageState().equals(  
		    android.os.Environment.MEDIA_MOUNTED)) {  
		   return true;  
		  } else  
		   return false;  
	}
	public long getSDFreeSize()
	{  
	     //取得SD卡文件路径  
	     File path = Environment.getExternalStorageDirectory();   
	     StatFs sf = new StatFs(path.getPath());   
	     //获取单个数据块的大小(Byte)  
	     long blockSize = sf.getBlockSize();   
	     //空闲的数据块的数量  
	     long freeBlocks = sf.getAvailableBlocks();  
	     //返回SD卡空闲大小  
	     //return freeBlocks * blockSize;  //单位Byte  
	     //return (freeBlocks * blockSize)/1024;   //单位KB  
	     return (freeBlocks * blockSize)/1024 /1024; //单位MB  
	   }    
	public String createDirInSDCard(String dir) {
		File dirFile = new File(SDCardRoot + File.separator + dir
				+ File.separator);
		if (!dirFile.exists()) {
			if (!dirFile.mkdirs()) {
				return null;
			}
		}
		return SDCardRoot + File.separator + dir + File.separator;
	}
	
	public String createDirInSDCardExtra(String dir) {
		File dirFile = new File(SDCardRoot + File.separator + "Supoin" + File.separator + dir + File.separator);
		if (!dirFile.exists()) {
			if (!dirFile.mkdirs()) {
				return null;
			}
		}
		return SDCardRoot + File.separator + "Supoin" + File.separator + dir + File.separator;
	}

	public boolean isFileExist(String fileName, String path) {
		File file = new File(SDCardRoot + File.separator + path
				+ File.separator + fileName);
		return file.exists();
	}
	
	public String getFilePathFromFileName(String fileName, String format) {
		boolean fileexist = new FileUtility().isFileExist(fileName + format, DrugSystemConst.download_dir);
		
		if ( !fileexist) {
			return null;
		}
		return SDCardRoot + File.separator + DrugSystemConst.download_dir + File.separator + fileName + format;
	}
	
	public int copyFile(String fromDir, String toDir){
		String filePath = createDirInSDCard(toDir);
		File from = new File(fromDir);
		File to = new File(filePath);
		File[] files;
		if(!from.exists()){
			return -1;
		}
		if (!to.exists()){
			to.mkdirs();
		}
		files = from.listFiles();
		for(int i = 0; i < files.length; i++){
			if(files[i].isDirectory()){
				copyFile(files[i].getPath() + File.separator,  toDir);
			}else{
				 copy(files[i].getPath(), filePath + files[i].getName());
			}
		}
		return 0;
	}
	
	private int copy(String fromFile, String toFile){
		try {
			InputStream is = new FileInputStream(fromFile);
			OutputStream os = new FileOutputStream(toFile);
			byte[] b = new byte[1024];
			int c;
			while((c = is.read(b)) > 0){
				os.write(b, 0, c);
			}
			is.close();
			os.close();
			return 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
		
	}
	public String getSDCardRoot() {
		return SDCardRoot;
	}
	public void setSDCardRoot(String sDCardRoot) {
		SDCardRoot = sDCardRoot;
	} 
	
	
}
