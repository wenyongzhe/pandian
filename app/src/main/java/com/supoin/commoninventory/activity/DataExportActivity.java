package com.supoin.commoninventory.activity;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class DataExportActivity extends Activity implements OnClickListener {
	private ListView listView;
//	private DataExportListAdapter adapter;
//	private List<ExportDataContent> list;
//	private Button exportButton, selectAllButton;
	private HashMap<Integer, Boolean> isSelected;
	private Boolean isSelectAll = false;
	private TextView emptyView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(DataExportActivity.this, R.string.data_delete, false);
		setContentView(R.layout.activity_data_export);
//		exportButton = (Button) this.findViewById(R.id.btn_export);
//		selectAllButton = (Button) this.findViewById(R.id.btn_selectall);
//		exportButton.setOnClickListener(this);
//		selectAllButton.setOnClickListener(this);

		isSelected = new HashMap<Integer, Boolean>();

	
//		倒序
//		Collections.reverse(list);
//		listView = (ListView) this.findViewById(R.id.listView1);
//		if (list == null) {
//			emptyView = (TextView) this.findViewById(R.id.empty_list_view);
//			listView.setEmptyView(emptyView);
//		} else {
//			adapter = new DataExportListAdapter(this, list);
//			listView.setAdapter(adapter);
//		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	/*	switch (v.getId()) {
		case R.id.btn_export:

			break;
		case R.id.btn_selectall:

			btnAllSelect_Click();

			break;

		}*/

	}

	// 参照deleteallbillactivity
	private void btnAllSelect_Click() {/*
		int size = list.size();
		if (!isSelectAll) {
			for (int i = 0; i < size; i++) {
				adapter.getIsSelected().put(i, true);
			}
			adapter.notifyDataSetChanged();
			isSelectAll = true;
			selectAllButton.setText("取消全选");

		} else {
			for (int i = 0; i < size; i++) {
				adapter.getIsSelected().put(i, false);
			}
			adapter.notifyDataSetChanged();
			isSelectAll = false;
			selectAllButton.setText("全选");
		}
		// isSelected = adapter.getIsSelected();
		// int size = list.size();
		// for(int i=0;i < size;i++){
		// if(!isSelected.get(i)){
		// isSelected.put(i, true);
		// adapter.getIsSelected().put(i, true);
		// }else{
		// isSelected.put(i, false);
		// adapter.getIsSelected().put(i, false);
		// }
		// }
		// adapter.notifyDataSetChanged();
	*/}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_DOWN:
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			break;
		case KeyEvent.KEYCODE_ENTER:
			break;
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}

		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		CustomTitleBar.setActivity(this);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从栈中移除该Activity
		myApplication.getInstance().finishActivity(this);
	}

}