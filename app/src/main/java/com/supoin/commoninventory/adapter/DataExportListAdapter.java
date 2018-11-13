package com.supoin.commoninventory.adapter;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.supoin.commoninventory.R;

public class DataExportListAdapter extends BaseAdapter {

	private List<ExportDataContent> list;
	private Context context;
	private HashMap<Integer, ItemCache> viewMap;
	private HashMap<Integer, Boolean> isSelected;
	long mLastTime = 0;

	@SuppressLint("UseSparseArrays")
	public DataExportListAdapter(Context context, List<ExportDataContent> list) {
		super();
		this.list = list;
		this.context = context;
		viewMap = new HashMap<Integer, ItemCache>();
		isSelected = new HashMap<Integer, Boolean>();
		initDate(list);
	}
	public List<ExportDataContent> getList() {
		return list;
	}

	public void setList(List<ExportDataContent> list) {
		this.list = list;
	}
	// 初始化isSelected的数据
	private void initDate(List<ExportDataContent> list) {
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
		Activity activity = (Activity) context;
		ItemCache itemCache = viewMap.get(position);
		View rowView;
		ViewCache viewCache;
		if (itemCache == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.item_listview_data_export,
					null);
			viewCache = new ViewCache(rowView);
			rowView.setTag(viewCache);
			ItemCache item = new ItemCache(rowView);
			viewMap.put(position, item);
		} else {
			rowView = itemCache.getView();
			viewCache = (ViewCache) rowView.getTag();
		}

		TextView number = viewCache.getNumberTextView();
		number.setText(list.get(position).number);
		TextView status = viewCache.getStatusTextView();
		String statusStr="未导出";
		if(list.get(position).status.equals("false"))
			statusStr="未导出";
		else if(list.get(position).status.equals("true"))
			statusStr="已导出";
		status.setText(statusStr);
		final CheckBox checkBox = viewCache.getCheckBox();
		checkBox.setChecked(getIsSelected().get(position));

		// onTouch 是用户操作触发的第一个操作。该事件return true 后续的时间是没有机会在执行了
		// retrun false 后，其它监听时间才有机会执行
		rowView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (getIsSelected().get(position)) {
					isSelected.put(position, false);
					setIsSelected(isSelected);
				} else {
					isSelected.put(position, true);
					setIsSelected(isSelected);
				}
				checkBox.setChecked(getIsSelected().get(position));
				notifyDataSetChanged();
			}
		});

		return rowView;
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

		private View baseView;
		private TextView number;
		private CheckBox checkBox;
		private TextView status;
		public ViewCache(View baseView) {
			this.baseView = baseView;
		}

		public TextView getNumberTextView() {
			if (number == null) {
				number = (TextView) baseView.findViewById(R.id.textView1);
			}
			return number;
		}
		public TextView getStatusTextView() {
			if (status == null) {
				status = (TextView) baseView.findViewById(R.id.textView2);
			}
			return status;
		}
		public CheckBox getCheckBox() {
			if (checkBox == null) {
				checkBox = (CheckBox) baseView.findViewById(R.id.checkbox);
			}
			return checkBox;
		}

	}
public static class ExportDataContent{
	public String number;
	public String status;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
}