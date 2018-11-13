package com.supoin.commoninventory.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.SelectFileAdapter;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.FileUtility;
import com.supoin.commoninventory.util.ToastUtils;



public class SelectExportFileActivity extends Activity {

	private List<String> items = null;
	private List<String> paths = null;
	private String ImportPath = FileUtility.getSdCardPath();
	private String rootPath = FileUtility.getSdCardPath();
	private String curPath = ImportPath;
	private TextView mPath;
	private ListView listView;
	private SelectFileAdapter adapter;
	private Button btn_ok;
	private File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(SelectExportFileActivity.this, R.string.export_directory_select,
				false);
		setContentView(R.layout.activity_export_file_select);
		mPath = (TextView) findViewById(R.id.mPath);
		listView = (ListView) findViewById(R.id.list);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		getFileDir(rootPath);
		
		
		
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String filePathString=mPath.getText().toString();
				Intent data = new Intent(SelectExportFileActivity.this,
						DataExportOutActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("file", filePathString);
				data.putExtras(bundle);
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});
	}

	private void getFileDir(String filePath) {
		mPath.setText(filePath);
		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		File f = new File(filePath);
		File[] files = f.listFiles();
		if (!filePath.equals(rootPath)) {
			items.add("b1");
			paths.add(rootPath);
			items.add("b2");
			paths.add(f.getParent());
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			// check file is txt file or not
			// if is,add into list to show
			if (checkShapeFile(file)) {
				items.add(file.getName());
				paths.add(file.getPath());
			}
		}
		if (items != null) {
			adapter = new SelectFileAdapter(SelectExportFileActivity.this,
					items, paths);
			listView.setAdapter(adapter);
			adapter.notifyDataSetInvalidated();
		}
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				if (paths.get(position) != null) {
					file = new File(paths.get(position));
					if (file.isDirectory()) {
						curPath = paths.get(position);
						getFileDir(paths.get(position));
						// }else{
						// Intent data = new
						// Intent(SelectExportFileActivity.this,DataExportActivity.class);
						// Bundle bundle = new Bundle();
						// bundle.putString("file",file.getPath());
						// data.putExtras(bundle);
						// setResult(Activity.RESULT_OK,data);
						// finish();
					}
				} else {
					ToastUtils.show(SelectExportFileActivity.this, AlertUtil.getString(R.string.is_root_directory));
				}

			}
		});
	}

	public boolean checkShapeFile(File file) {
		String fileNameString = file.getName();
		String endNameString = fileNameString.substring(
				fileNameString.lastIndexOf(".") + 1, fileNameString.length())
				.toLowerCase();
		// file is directory or not
		if (file.isDirectory()) {
			return true;
		}
		if (endNameString.equals("txt")) {
			return true;
		} else {
			return false;
		}
	}

	protected final String getSDDir() {
		if (!checkSDcard()) {
			Toast.makeText(this, "no sdcard", Toast.LENGTH_SHORT).show();
			return "";
		}
		try {
			String SD_DIR = Environment.getExternalStorageDirectory()
					.toString();
			return SD_DIR;

		} catch (Exception e) {
			return "";
		}
	}

	public boolean checkSDcard() {
		String sdStutusString = Environment.getExternalStorageState();
		if (sdStutusString.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从栈中移除该Activity
		myApplication.getInstance().finishActivity(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		CustomTitleBar.setActivity(this);
		super.onResume();
	}

}
