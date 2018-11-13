package com.supoin.commoninventory.contentprovider;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.supoin.commoninventory.constvalue.DrugSystemConst;
import com.supoin.commoninventory.util.FileUtility;
import com.supoin.commoninventory.util.downinfo;

import android.R.integer;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public class providertool {
	Context m_context;
	public providertool(Context ctx)
	{
		m_context = ctx;
	}
	
	public  boolean isHasInfors(String[] urlstr) {
		Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
//      	 Activity activity  = (Activity)m_context;
      	 Cursor c = m_context.getContentResolver().query(uri, null,downinfoMetaType.downinfoTableMetaType.url+ "=?", urlstr,  null);
      	if (c == null)
	 	{
	 		return true;
	 	}
      	 int num = c.getCount();
      	 if (num > 0)
      	 {
      		 return false;
      	 }
      	 else {
      		 return true;
		}
    }
    //清理数据库，防止不存在的文件记录出现
    public  void cleandatabase()
    {
   	 
   	 Map<String, downinfo> map = new HashMap<String, downinfo>();
   	 if (!isempty())
   	 {
   		 map = getallinfo();
   		 Iterator it = map.keySet().iterator(); 
 		   while (it.hasNext()){ 
 		    String key; 
 		    key=(String)it.next(); 		    
// 		    downResult.add(filemap.get(key));
 		    downinfo info = map.get(key);
 		    FileUtility fileUtility = new FileUtility();
 		  
 		    if (!fileUtility.isFileExist(info.filename, DrugSystemConst.download_dir))
 		    {
 		    	//不存在
 		    	deleteByname(info.filename);
 		    }
 		   }
   	 }
   	 else {
   		 File file1 = new File("sdcard/" +DrugSystemConst.download_dir);
   		 if(file1.exists())
   		 {
   			 File[] arrayFiles = file1.listFiles();
   	 			for (int i = 0; i < arrayFiles.length; i++)
   	 			{
   	 				if (arrayFiles[i].getName().toLowerCase().endsWith(".tmpd"))
   	 				{
   	 					arrayFiles[i].delete();
   	 				}
   	 				
   	 			}
   		 }
			
		}
   	 
    }
    public synchronized int getTableNum()
    {
   	 	Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
//   	 	Activity activity = (Activity)m_context;
   	
   	 	Cursor cursor = m_context.getContentResolver().query(uri, null, null, null, null);
   	 	
   	 	if (cursor == null)
   	 	{
   	 		return 0;
   	 	}
   	 	int num = cursor.getCount();
   	 	cursor.close();
   	 	return num;
    }
    //判断表是否为空
public synchronized boolean isempty()
    {
   	  if (getTableNum()> 0)
   	  {
   		return false;
   	  }
   	  else {
   		  
   		return true;
   	  }
    }
public synchronized Map<String, downinfo> getallinfo()
    {
   	 Map<String, downinfo> list = new HashMap<String, downinfo>();
   	 Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
//   	 Activity activity  = (Activity)m_context;
   	 Cursor c = m_context.getContentResolver().query(uri, null, null, null, null);
   	if (c == null)
	 	{
	 		return list;
	 	}
   	    int idownloadid = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.download_id);
	   	int istartpos = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.start_pos);
	   	int idownsize = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.downsize);
	   	int iendpos = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.end_pos);
	   	int ifilesize = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.filesize);
	   	int ipercent = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.percent);
	   	int iurl = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.url);
	   	int iimagename = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.imagename);
	   	int ifilename = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.filename);
	   	
	   	for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
	   	{
	   		downinfo info = new downinfo();
//	   		int id = c.getInt(1);
	   		info.download_id = c.getInt(idownloadid);
	   		info.startpos = c.getInt(istartpos);
	   		info.downsize = c.getLong(idownsize);
	   		info.endpos = c.getInt(iendpos);
	   		info.filesize = c.getLong(ifilesize);
	   		info.pecent = c.getInt(ipercent);
	   		info.urllink = c.getString(iurl);
	   		info.imageName = c.getString(iimagename);
	   		info.filename = c.getString(ifilename);
	   		
	   		list.put(info.filename, info);
	   	}
   	 
	   	c.close();
        return list;
   	 
    }
    /**
     * 保存 下载的具体信息
     */
    
    //插入一条数据
    public void saveInfos(downinfo info) {
   	 
   	 
        ContentValues cv = new ContentValues();
        cv.put(downinfoMetaType.downinfoTableMetaType.download_id, info.download_id);
        cv.put(downinfoMetaType.downinfoTableMetaType.start_pos, info.startpos);
        cv.put(downinfoMetaType.downinfoTableMetaType.downsize, info.downsize);
        cv.put(downinfoMetaType.downinfoTableMetaType.end_pos, info.endpos);
        cv.put(downinfoMetaType.downinfoTableMetaType.filesize, info.filesize);
        cv.put(downinfoMetaType.downinfoTableMetaType.percent, info.pecent);
        cv.put(downinfoMetaType.downinfoTableMetaType.url, info.urllink);
        cv.put(downinfoMetaType.downinfoTableMetaType.imagename, info.imageName);
        cv.put(downinfoMetaType.downinfoTableMetaType.filename, info.filename);
        
        ContentResolver cr = m_context.getContentResolver();
        Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
        Uri insertUri = cr.insert(uri, cv);
        
		  
    }
    /**
    * 得到下载具体信息
     */
   
    public  downinfo getInfo(String[] urlstr) 
    {
    	 Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
//       	 Activity activity  = (Activity)m_context;
       	 Cursor c = m_context.getContentResolver().query(uri, null,downinfoMetaType.downinfoTableMetaType.url+ "=?", urlstr,  null);
       	if (c == null)
	 	{
	 		return null;
	 	}
       	int idownloadid = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.download_id);
	   	int istartpos = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.start_pos);
	   	int idownsize = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.downsize);
	   	int iendpos = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.end_pos);
	   	int ifilesize = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.filesize);
	   	int ipercent = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.percent);
	   	int iurl = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.url);
	   	int iimagename = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.imagename);
	   	int ifilename = c.getColumnIndex(downinfoMetaType.downinfoTableMetaType.filename);
	   	downinfo info = new downinfo();
//   		int id = c.getInt(1);
	   	c.moveToFirst();
   		info.download_id = c.getInt(idownloadid);
   		info.startpos = c.getInt(istartpos);
   		info.downsize = c.getLong(idownsize);
   		info.endpos = c.getInt(iendpos);
   		info.filesize = c.getLong(ifilesize);
   		info.pecent = c.getInt(ipercent);
   		info.urllink = c.getString(iurl);
   		info.imageName = c.getString(iimagename);
   		info.filename = c.getString(ifilename);
   		c.close();
   		return info;
        
   }
    /**
     * 更新数据库中的下载信息
     */
    public  void updataInfos(int threadId, long compeleteSize, String[] urlstr) {
    	 ContentValues cv = new ContentValues();
         cv.put(downinfoMetaType.downinfoTableMetaType.downsize, compeleteSize);
         
         ContentResolver cr = m_context.getContentResolver();
         Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
         int row =  cr.update(uri, cv, downinfoMetaType.downinfoTableMetaType.url + "=?", urlstr);
         
//      database.close();
    }
    public synchronized void updataInfoscc(int threadId, long compeleteSize, long filesize,String[] urlstr) {
    	 ContentValues cv = new ContentValues();
         cv.put(downinfoMetaType.downinfoTableMetaType.downsize, compeleteSize);
         cv.put(downinfoMetaType.downinfoTableMetaType.filesize, filesize);
         ContentResolver cr = m_context.getContentResolver();
         
         Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
         int row =  cr.update(uri, cv, downinfoMetaType.downinfoTableMetaType.url + "=?", urlstr);
//      database.close();
    }
    public  void updataInfoslx(int threadId, long filesize, String[] urlstr) {
    	 ContentValues cv = new ContentValues();
         cv.put(downinfoMetaType.downinfoTableMetaType.filesize, filesize);
         ContentResolver cr = m_context.getContentResolver();
         
         Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
         int row =  cr.update(uri, cv, downinfoMetaType.downinfoTableMetaType.url + "=?", urlstr);
        
    }
    public  void updataInfoslxel(int threadId, long compeleteSize, int pecent, String[] urlstr) {
    	 ContentValues cv = new ContentValues();
         cv.put(downinfoMetaType.downinfoTableMetaType.downsize, compeleteSize);
         cv.put(downinfoMetaType.downinfoTableMetaType.percent, pecent);
         ContentResolver cr = m_context.getContentResolver();
         
         Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
         int row =  cr.update(uri, cv, downinfoMetaType.downinfoTableMetaType.url + "=?", urlstr);
//      database.close();
    }
    public  synchronized void updataInfoslxe(int threadId, int pecent, String[] urlstr) {
    	 ContentValues cv = new ContentValues();
         cv.put(downinfoMetaType.downinfoTableMetaType.percent, pecent);
         ContentResolver cr = m_context.getContentResolver();
         
         Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
         int row =  cr.update(uri, cv, downinfoMetaType.downinfoTableMetaType.url + "=?", urlstr);
    }
    /**
     * 关闭数据库
     */
    public synchronized void closeDb() {
//        dbHelper.close();
        
    }

    /**
     * 下载完成后删除数据库中的数据
     */
   public void delete(String url) {
        ContentResolver cr = m_context.getContentResolver();
        Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
        Uri deluri = Uri.withAppendedPath(uri, url);
        cr.delete(deluri, null, null);
//        database.close();
       
    }
   public void deletebyid(String id) {
       ContentResolver cr = m_context.getContentResolver();
       Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
       Uri deluri = Uri.withAppendedPath(uri, id);
       cr.delete(deluri, null, null);
//       database.close();
      
   }
   public void deleteByname(String filename) {
       ContentResolver cr = m_context.getContentResolver();
       Uri uri = downinfoMetaType.downinfoTableMetaType.CONTENT_URI;
       Uri deluri = Uri.withAppendedPath(uri, filename);
       cr.delete(deluri, null, null);
//       database.close();
      
   }

}
