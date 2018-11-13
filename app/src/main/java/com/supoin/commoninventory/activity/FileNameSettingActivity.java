package com.supoin.commoninventory.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;

public class FileNameSettingActivity extends Activity {
	private ArrayList<String> arraylist;
	private EditText et_prefix_name;
	private String strPrefixName;
	private ListView mlistivew;
	private SharedPreferences sp;
	private String sPosition = "0";
	private Button btn_save;

	private MyAdapter arrayAdapter;
	private int flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(FileNameSettingActivity.this, R.string.naming_method_select,
				false);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		arraylist = new ArrayList<String>();
		arraylist.add("默认的命名方式");
		arraylist.add("默认的命名方式+_(单据总数量)");
		arraylist.add("默认的命名方式+_年月日");
		arraylist.add("默认的命名方式+_年月日+_(单据总数量)");
		arraylist.add("默认的命名方式+_年月日时分秒");
		arraylist.add("默认的命名方式+_年月日时分秒+_(单据总数量)");
		arraylist.add("前缀+_年月日");
		arraylist.add("前缀+_年月日+_(单据总数量)");
		arraylist.add("前缀+_年月日时分秒");
		arraylist.add("前缀+_年月日时分秒+_(单据总数量)");

		setContentView(R.layout.activity_filename_setting);
		et_prefix_name = (EditText) findViewById(R.id.et_prefix_name);
		mlistivew = (ListView) findViewById(R.id.listview_select_name);
		btn_save = (Button) findViewById(R.id.btn_save);
		arrayAdapter = new MyAdapter(FileNameSettingActivity.this, arraylist);
		mlistivew.setAdapter(arrayAdapter);

		flag = getIntent().getIntExtra("flag",0);
		switch (flag) {
		case 0:
			strPrefixName = sp.getString(ConfigEntity.ExportPrefixKey, "");
			break;
		case 1:
			strPrefixName = sp.getString(ConfigEntity.ExportInPrefixKey, "");
			break;
		case 2:
			strPrefixName = sp.getString(ConfigEntity.ExportOutPrefixKey, "");
			break;

		default:
			break;
		}
//		strPrefixName = sp.getString(ConfigEntity.ExportPrefixKey, "");
		sPosition = sp.getString(ConfigEntity.ExportNameWayKey, "0");

		et_prefix_name.setText(strPrefixName);
		et_prefix_name.setSelection(strPrefixName.length());
		mlistivew.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sPosition = arg2 + "";
				arrayAdapter.notifyDataSetChanged();

				AlertUtil.showToast(AlertUtil.getString(R.string.have_selected)+ arraylist.get(arg2),
						FileNameSettingActivity.this);
			}
		});

		btn_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				strPrefixName = et_prefix_name.getText().toString();
				if (TextUtils.isEmpty(strPrefixName)) {
					AlertUtil.showToast(AlertUtil.getString(R.string.enter_prefix),
							FileNameSettingActivity.this);
					return;

				}

				switch (flag) {
					case 0:
						sp.edit()
						.putString(ConfigEntity.ExportPrefixKey, strPrefixName)
						.commit();
						break;
					case 1:
						sp.edit()
						.putString(ConfigEntity.ExportInPrefixKey, strPrefixName)
						.commit();
						break;
					case 2:
						sp.edit()
						.putString(ConfigEntity.ExportOutPrefixKey, strPrefixName)
						.commit();
						break;
					default:
						break;
					}
				sp.edit()
						.putString(ConfigEntity.ExportNameWayKey, sPosition)
						.commit();
				AlertUtil.showToast(AlertUtil.getString(R.string.saved_success), FileNameSettingActivity.this);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}

		// TODO Auto-generated method stub
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

	public class MyAdapter extends BaseAdapter {
		private ArrayList<String> arraylist;
		private LayoutInflater layoutInflater;
		private Context context;

		public MyAdapter(Context context, ArrayList<String> arraylist) {
			this.context = context;
			this.arraylist = arraylist;
			this.layoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arraylist.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arraylist.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = layoutInflater.inflate(R.layout.item_file_name,
						null);
				viewHolder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_name);
				convertView.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tv_name.setText(arraylist.get(position));

			if (position == Integer.parseInt(sPosition)) {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.blue));
			} else {
				convertView.setBackgroundColor(Color.TRANSPARENT);
			}

			return convertView;
		}

		public class ViewHolder {
			public TextView tv_name;
		}
	}

}
