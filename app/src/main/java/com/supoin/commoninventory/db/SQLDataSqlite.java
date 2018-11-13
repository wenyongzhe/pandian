package com.supoin.commoninventory.db;

import java.util.List;


import com.supoin.commoninventory.util.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SQLDataSqlite  {



	public static String dbName = "PD.sdf";
	protected Cursor cursor = null;
	private String VerifyWayTableName = "VerifyWay";
	protected static SQLiteDatabase db = null;
	protected static SQLiteDatabase dbLocal = null;
	protected static Context context;
	protected SharedPreferences sp;

		
	public void exeSql(String strSql,List<ContentValues> list,Handler handle) {
		if(strSql!=null){
			dbLocal.beginTransaction();
		    try{
		    	
		    	dbLocal.execSQL(strSql);		    
		    	dbLocal.setTransactionSuccessful();
		    	dbLocal.endTransaction();
		    }catch(Exception ex){
		       ex.printStackTrace();
		    }
		    db.beginTransaction();
		    try{
		    	
		    	db.execSQL(strSql);		    
		    	db.setTransactionSuccessful();
		    	db.endTransaction();
		    }catch(Exception ex){
		       ex.printStackTrace();
		    }
		}else{
			 try{
		    	   dbLocal.beginTransaction(); // 手动设置开始事务
				   for (int i =0;i<list.size();i++) {
					   
						   dbLocal.insert("GdInfo", "null",  list.get(i));
						   if(handle!=null){
						   Message msg =new Message();						  
						   msg.what = 10;					
						   handle.sendMessage(msg);   						
					       }					   
				   }
				   dbLocal.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交					  	  	
		    }catch(Exception ex){
		    	Utility.deBug("CheckDetail GetRecordsScanOrder Method Error",
						ex.getMessage());
		    } finally{
		    	dbLocal.endTransaction(); // 处理完成
		    }
			 try{
		    	   db.beginTransaction(); // 手动设置开始事务
				   for (int i =0;i<list.size();i++) {
					   
						   db.insert("GdInfo", "null",  list.get(i));
						   				   
				   }
				   db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交					  	  	
		    }catch(Exception ex){
		    	Utility.deBug("CheckDetail GetRecordsScanOrder Method Error",
						ex.getMessage());
		    } finally{
		    	db.endTransaction(); // 处理完成
		    }
		}
		
	}

}