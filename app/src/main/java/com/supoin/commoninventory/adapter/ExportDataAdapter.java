package com.supoin.commoninventory.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.activity.DataExportOutActivity;
import com.supoin.commoninventory.adapter.ScanDisplaySettingAdapter.ViewHolder;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.myApplication;

public class ExportDataAdapter extends BaseAdapter {
	private List<DataDetailInfo> list;
	private Context context;
	public HashMap<Integer, Boolean> isSelected;
	private LayoutInflater inflater = null;
	long mLastTime = 0;
	private int selectedPosition = -1;
	private String strExportType;

	@SuppressLint("UseSparseArrays")
	public ExportDataAdapter(Context context,
			List<DataDetailInfo> dataList, String strExportType) {
		super();
		this.list = dataList;
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.strExportType = strExportType;
		isSelected = new HashMap<Integer, Boolean>();
		initDate(dataList);
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public List<DataDetailInfo> getList() {
		return list;
	}

	public void setList(List<DataDetailInfo> list) {
		this.list = list;
	}

	// 初始化isSelected的数据
	public void initDate(List<DataDetailInfo> list) {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	public HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.item_list_export_data, null);
			holder.checkID = (TextView) convertView
					.findViewById(R.id.tv_checkid);
			holder.positionID = (TextView) convertView
					.findViewById(R.id.tv_positionid);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			holder.linearLayout = (LinearLayout) convertView
					.findViewById(R.id.linearLayout);
			tv_bianhao_ex = (TextView) convertView.findViewById(R.id.tv_bianhao_ex);
			tv_huowei_ex = (TextView) convertView.findViewById(R.id.tv_huowei_ex);
			initText();
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.checkID.setText(list.get(position).CheckID);
		holder.positionID.setText(list.get(position).PositionID);
		Boolean mBoolean=getIsSelected().get(position);
		holder.checkBox.setChecked(mBoolean);
		if (strExportType.equals("0")) {
			holder.linearLayout.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	private TextView tv_bianhao_ex;
	private TextView tv_huowei_ex;
	
	private SharedPreferences sp;
	/**动态修改编号货位提示*/
	private void initText() {
		sp = myApplication.mContext.getSharedPreferences("InvenConfig", Context.MODE_PRIVATE);
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		tv_bianhao_ex.setText(importStrArrayList.get(1)+":");
		tv_huowei_ex.setText(importStrArrayList.get(2)+":");	
		}
	
	public class ViewHolder {
		public TextView checkID;
		public CheckBox checkBox;
		public TextView positionID;
		public LinearLayout linearLayout;
	}

	public static class DataDetailInfo {
		public String CheckID;// 编号
		public String PositionID;// 货位
		public Boolean isSelected;

		public String getCheckID() {
			return CheckID;
		}

		public void setCheckID(String checkID) {
			CheckID = checkID;
		}

		public String getPositionID() {
			return PositionID;
		}

		public void setPositionID(String positionID) {
			PositionID = positionID;
		}

		public Boolean getIsSelected() {
			return isSelected;
		}

		public void setIsSelected(Boolean isSelected) {
			this.isSelected = isSelected;
		}
	}
}