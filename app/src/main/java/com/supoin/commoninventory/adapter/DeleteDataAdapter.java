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
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.myApplication;

public class DeleteDataAdapter extends BaseAdapter {
	private List<DataDetailInfo> list;
	private  Context context;
	private HashMap<Integer, ItemCache> viewMap;
	public  HashMap<Integer, Boolean> isSelected;
	long mLastTime = 0;
	private int selectedPosition = -1;
	private String strDeleteType;
	@SuppressLint("UseSparseArrays")
	public DeleteDataAdapter(Context context, List<DataDetailInfo> list,String strDeleteType) {
		super();
		this.list = list;
		this.context = context;
		this.strDeleteType= strDeleteType;
		viewMap = new HashMap<Integer, ItemCache>();
		isSelected = new HashMap<Integer, Boolean>();
		initDate(list);
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
		ViewCache viewHolder = null;
		if (null == convertView) {
			viewHolder = new ViewCache();
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.delete_listitem, null);
			viewHolder.tv_bianhao_de = (TextView) convertView.findViewById(R.id.tv_bianhao_de);
			viewHolder.tv_huowei_de = (TextView) convertView.findViewById(R.id.tv_huowei_de);
			viewHolder.tv_checkid = (TextView) convertView.findViewById(R.id.tv_checkid);
			viewHolder.tv_positionid = (TextView) convertView.findViewById(R.id.tv_positionid);
			viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewCache) convertView.getTag();
		
		}
		initText(viewHolder,position);
		
		return convertView;
	}
	
	
	private SharedPreferences sp;
	/**动态修改编号货位提示*/
	private void initText(ViewCache viewHolder,  int position) {
		sp = myApplication.mContext.getSharedPreferences("InvenConfig",
				Context.MODE_PRIVATE);
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		viewHolder.tv_bianhao_de.setText(importStrArrayList.get(1) + ":");
		viewHolder.tv_huowei_de.setText(importStrArrayList.get(2) + ":");
		viewHolder.tv_checkid.setText(list.get(position).CheckID);
		viewHolder.tv_positionid.setText(list.get(position).PositionID);
		
		if(strDeleteType.equals("0")){
			viewHolder.tv_positionid.setVisibility(View.GONE);
			viewHolder.linearLayout.setVisibility(View.GONE);
		}
		viewHolder.checkBox.setChecked(getIsSelected().get(position));
	}
	
	public class ItemCache {
		private View view;

		public ItemCache(View view) {
			super();
			this.view = view;
		}

		public View getView() {
			return view;
		}

		public void setView(View view) {
			this.view = view;
		}
	}

	public class ViewCache {
		private CheckBox checkBox;
		private LinearLayout linearLayout;
		private TextView tv_bianhao_de,  tv_huowei_de, tv_checkid,tv_positionid;
		
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