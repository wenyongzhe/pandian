package com.supoin.commoninventory.contentprovider;


import java.util.HashMap;

import com.supoin.commoninventory.contentprovider.downinfoMetaType.downinfoTableMetaType;



import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class downinfoContentProvider extends ContentProvider{
	private static final String TAG = "downinfoContentProvider";
	private static HashMap<String, String> downinfoMap;
	static 
	{
		downinfoMap = new HashMap<String, String>();
		downinfoMap.put(downinfoTableMetaType._ID, downinfoTableMetaType._ID);
		downinfoMap.put(downinfoTableMetaType.download_id, downinfoTableMetaType.download_id);
		downinfoMap.put(downinfoTableMetaType.start_pos, downinfoTableMetaType.start_pos);
		downinfoMap.put(downinfoTableMetaType.downsize, downinfoTableMetaType.downsize);
		downinfoMap.put(downinfoTableMetaType.end_pos, downinfoTableMetaType.end_pos);
		downinfoMap.put(downinfoTableMetaType.filesize, downinfoTableMetaType.filesize);
		downinfoMap.put(downinfoTableMetaType.percent, downinfoTableMetaType.percent);
		downinfoMap.put(downinfoTableMetaType.url, downinfoTableMetaType.url);
		downinfoMap.put(downinfoTableMetaType.imagename, downinfoTableMetaType.imagename);
		downinfoMap.put(downinfoTableMetaType.filename, downinfoTableMetaType.filename);
	};
	
	private static final UriMatcher sUriMatcher;
	private static final int DOWNINFO_COLLECTION_URI_INDRCATOR  = 1;
	private static final int DOWNINFO_SINGLE_URI_INDRCATOR = 2;
	
	static 
	{
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(downinfoMetaType.AUTHORTY, "download_info", DOWNINFO_COLLECTION_URI_INDRCATOR);
		sUriMatcher.addURI(downinfoMetaType.AUTHORTY, "download_info/*", DOWNINFO_SINGLE_URI_INDRCATOR);
		
	};
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, downinfoMetaType.DATABASE_NAME, null, downinfoMetaType.DATABASE_VERSION);
			
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + downinfoTableMetaType.TABLE_NAME +" ("
					+ downinfoTableMetaType._ID + " INTEGER PRIMARY KEY,"
					+ downinfoTableMetaType.download_id + " INTEGER,"
					+ downinfoTableMetaType.start_pos + " INTEGER,"
					+ downinfoTableMetaType.downsize + " INTEGER,"
					+ downinfoTableMetaType.end_pos + " INTEGER,"
					+ downinfoTableMetaType.filesize + " INTEGER,"
					+ downinfoTableMetaType.percent + " INTEGER,"
					+ downinfoTableMetaType.url + " TEXT,"
					+ downinfoTableMetaType.imagename + " TEXT,"
					+ downinfoTableMetaType.filename + " TEXT"
					+ ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS" + downinfoTableMetaType.TABLE_NAME);
			onCreate(db);
		}
		
	}
	private DatabaseHelper mOpenHelper;
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mOpenHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (sUriMatcher.match(uri))
		{
			case DOWNINFO_COLLECTION_URI_INDRCATOR:
				qb.setTables(downinfoTableMetaType.TABLE_NAME);
				qb.setProjectionMap(downinfoMap);
				break;
			case DOWNINFO_SINGLE_URI_INDRCATOR:
				qb.setTables(downinfoTableMetaType.TABLE_NAME);
				qb.setProjectionMap(downinfoMap);
				qb.appendWhere(downinfoTableMetaType.filename + "="
						+ uri.getPathSegments().get(1));
				break;
			default:
				throw new IllegalArgumentException("Unknown URI" + uri);
		}
		String orderBy;
		if (TextUtils.isEmpty(sortOrder))
		{
			orderBy = downinfoTableMetaType.DEFAULT_SORT_ORDER;
		}
		else {
			orderBy = sortOrder;
		}
		SQLiteDatabase db  = mOpenHelper.getReadableDatabase();
		Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		int i = cursor.getCount();
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}
	
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (sUriMatcher.match(uri))
		{
			case DOWNINFO_COLLECTION_URI_INDRCATOR:
				return downinfoTableMetaType.CONTENT_TYPE;
			case DOWNINFO_SINGLE_URI_INDRCATOR:
				return downinfoTableMetaType.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalArgumentException("Unknown URI" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		if (sUriMatcher.match(uri) != DOWNINFO_COLLECTION_URI_INDRCATOR)
		{
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		ContentValues values2;
		if (values != null)
		{
			values2 = new ContentValues(values);
		}
		else {
			values2 = new ContentValues();
		}
		
		Long now = Long.valueOf(System.currentTimeMillis());
		
		if (values2.containsKey(downinfoTableMetaType.download_id) == false)
		{
			throw new SQLException("Failed to insert row because download_id" + uri);
		}
		if (values2.containsKey(downinfoTableMetaType.start_pos) == false)
		{
			throw new SQLException("Failed to insert row because start_pos" + uri);
		}
		if (values2.containsKey(downinfoTableMetaType.downsize) == false)
		{
			throw new SQLException("Failed to insert row because downsize" + uri);
		}
		if (values2.containsKey(downinfoTableMetaType.end_pos) == false)
		{
			throw new SQLException("Failed to insert row because end_pos" + uri);
		}
		if (values2.containsKey(downinfoTableMetaType.filesize) == false)
		{
			throw new SQLException("Failed to insert row because filesize" + uri);
		}
		if (values2.containsKey(downinfoTableMetaType.percent) == false)
		{
			throw new SQLException("Failed to insert row because percent" + uri);
		}
		if (values2.containsKey(downinfoTableMetaType.url) == false)
		{
			throw new SQLException("Failed to insert row because url" + uri);
		}
		if (values2.containsKey(downinfoTableMetaType.imagename) == false)
		{
			throw new SQLException("Failed to insert row because imagename" + uri);
		}
		if (values2.containsKey(downinfoTableMetaType.filename) == false)
		{
			throw new SQLException("Failed to insert row because filename" + uri);
		}
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowid = db.insert(downinfoTableMetaType.TABLE_NAME, downinfoTableMetaType.filename, values2);
//		long rowid = db.insert(downinfoTableMetaType.TABLE_NAME, null, values2);
		
		if (rowid > 0)
		{
			
//			Uri insertdownUri = ContentUris.withAppendedId(downinfoTableMetaType.CONTENT_URI, rowid);
			Uri insertdownUri = Uri.withAppendedPath(downinfoTableMetaType.CONTENT_URI, (String)(values2.get(downinfoTableMetaType.filename)));
//			Uri insertdownUri = Uri.withAppendedPath(downinfoTableMetaType.CONTENT_URI, "asdfsdfsd");
			getContext().getContentResolver().notifyChange(insertdownUri, null);
			return insertdownUri;
		}
		throw new SQLException("Failed to insert row into" + uri);
		
		
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case DOWNINFO_COLLECTION_URI_INDRCATOR:
			count = db.delete(downinfoTableMetaType.TABLE_NAME, selection, selectionArgs);
			break;
		case DOWNINFO_SINGLE_URI_INDRCATOR:
			String rowid = uri.getPathSegments().get(1);
//			count = db.delete(downinfoTableMetaType.TABLE_NAME, downinfoTableMetaType.filename + "=" + rowid 
//					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
			count = db.delete(downinfoTableMetaType.TABLE_NAME, downinfoTableMetaType.filename + "=?", new String[] { rowid });
			break;
		default:
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case DOWNINFO_COLLECTION_URI_INDRCATOR:
			count = db.update(downinfoTableMetaType.TABLE_NAME, values, selection, selectionArgs);
			break;
		case DOWNINFO_SINGLE_URI_INDRCATOR:
			String rowid = uri.getPathSegments().get(1);
			count = db.update(downinfoTableMetaType.TABLE_NAME, values, downinfoTableMetaType.filename + "=" + rowid 
					+ (!TextUtils.isEmpty(selection)?"AND(" + selection + ')' : ""), selectionArgs);
			
			break;
		default:
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
