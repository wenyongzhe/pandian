package com.supoin.commoninventory.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.ScanDisplaySettingAdapter;
import com.supoin.commoninventory.adapter.ScanDisplaySettingAdapter.ViewHolder;
import com.supoin.commoninventory.db.SQLDataSqlite;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.util.AlertUtil;

public class ScanDisplaySettingFragment extends Fragment {

	// 配置扫描界面显示选项的索引及值
	// 3:不显示,但已设置单行显示,2:显示，并且单行,1:只显示,0:不显示
	// public static String DisplayItems = "0,0,1,0,1,0,1,0,1,0,0,0,1";
	// public static String DisplayItemsKey = "DisplayItems";
	private List<String> list = new ArrayList<String>();
	private ScanDisplaySettingAdapter mAdapter;
	private int checkNum; // 记录选中的条目数量
	private Button btn_save, bt_cancel;
	private ListView lv;
	private SharedPreferences sp;
	SQLDataSqlite sqlStockData;
	// 扫描显示设置Item
	private String[] strItems;
	private int num;
	private StringBuffer strBuffer;
	private String[] strItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getActivity().getSharedPreferences("InvenConfig",getActivity().MODE_PRIVATE);
		strItems = sp.getString(ConfigEntity.DisplayItemsKey, "").split(",");
		strItem = sp.getString(ConfigEntity.ImportStrKey, "").split(",");
		for (int i = 2; i <strItems.length; i++) {
			list.add(strItem[i]);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_scan_display_setting,
				null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);

		btn_save = (Button) view.findViewById(R.id.btn_save);
		bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		lv = (ListView) view.findViewById(R.id.listview_display);

		mAdapter = new ScanDisplaySettingAdapter(list, getActivity());
		for (int i = 2; i < strItems.length; i++) {

			if (strItems[i].equals("1")) {
				mAdapter.getIsSelected().put(i - 2, true);

			} else {
				mAdapter.getIsSelected().put(i - 2, false);
			}
		}
		lv.setAdapter(mAdapter);

		OnClickListener();
		return view;
	}

	private void OnClickListener() {
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ViewHolder holder = (ViewHolder) arg1.getTag();
				holder.checkbox_single_display.toggle();
				mAdapter.getIsSelected().put(arg2,holder.checkbox_single_display.isChecked());
			}

		});
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				for (int i = 0; i < list.size(); i++) {
					if (mAdapter.getIsSelected().get(i)) {
						mAdapter.getIsSelected().put(i, false);
					}
				}
				// 刷新Adapter
				mAdapter.notifyDataSetChanged();
			}
		});

		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				num = 0;
				strBuffer = new StringBuffer("0,0");
				for (int i = 2; i < list.size() + 2; i++) {
					if (mAdapter.getIsSelected().get(i - 2)) {
						strItems[i] = "1";
						num++;
					} else {
						strItems[i] = "0";
					}
					strBuffer.append("," + strItems[i]);
				}
				if (strItems[2].equals("1") && strItems[3].equals("1")) {
					AlertUtil.showAlert(getActivity(), AlertUtil.getString(R.string.display1),AlertUtil.getString(R.string.display2));
					return;
				}
				if (num > 5) {
					AlertUtil.showAlert(getActivity(), AlertUtil.getString(R.string.display1), AlertUtil.getString(R.string.display3));
					return;
				} else if (num < 1) {
					AlertUtil.showAlert(getActivity(), AlertUtil.getString(R.string.display1), AlertUtil.getString(R.string.display4));
					return;
				} else {
					sp.edit().putString(ConfigEntity.DisplayItemsKey,strBuffer.toString()).commit();
					sp.edit().putBoolean(ConfigEntity.scanDisplay, true).commit();
					AlertUtil.showAlert(getActivity(), AlertUtil.getString(R.string.display1), AlertUtil.getString(R.string.saved_success));
				}

			}
		});

	}

}
