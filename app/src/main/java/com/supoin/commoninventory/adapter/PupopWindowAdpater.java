package com.supoin.commoninventory.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.supoin.commoninventory.R;

public class PupopWindowAdpater extends BaseAdapter {
	// 商品扫描详情列表
	private List<String> list;
	private Context context;
	private LayoutInflater mInflater;
	public PupopWindowAdpater(Context context, List<String> list) {
		super();
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_pupopwindow, null);
			viewHolder.tv_pupopwindow = (TextView) convertView
					.findViewById(R.id.tv_pupopwindow);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_pupopwindow.setText(list.get(position));
		return convertView;
	}

	private class ViewHolder {
		TextView tv_pupopwindow;
	}
}
