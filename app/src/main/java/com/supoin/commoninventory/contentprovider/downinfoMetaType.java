package com.supoin.commoninventory.contentprovider;



import android.net.Uri;
import android.provider.BaseColumns;

public class downinfoMetaType {
	
	public static final String AUTHORTY = "com.supoin.commoninventory.downinfoProvider";
	public static final String DATABASE_NAME = "systemdowninfo.db";
	public static final int DATABASE_VERSION = 1;
	public static final String DOWN_TABLE_NAME = "download_info";
	
	private downinfoMetaType(){};
	public static final class downinfoTableMetaType implements BaseColumns
	{
		private downinfoTableMetaType(){};
		public static final String TABLE_NAME = "download_info";
		public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORTY+"/download_info");
		public static final String CONTENT_TYPE = "vnd.supoin.cursor.dir/vnd.drugsystem.downinfo";
		public static final String CONTENT_ITEM_TYPE = "vnd.supoin.cursor.item/vnd.drugsystem.downinfo";
		public static final String DEFAULT_SORT_ORDER = "filename DESC";
		
		
		public static final String download_id = "download_id";
		public static final String start_pos = "start_pos";
		public static final String downsize = "downsize";
		public static final String end_pos = "end_pos";
		public static final String filesize = "filesize";
		public static final String percent = "percent";
		public static final String url = "url";
		public static final String imagename = "imagename";
		public static final String filename = "filename";
	};

}
