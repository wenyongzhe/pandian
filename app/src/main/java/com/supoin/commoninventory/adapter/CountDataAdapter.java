package com.supoin.commoninventory.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.supoin.commoninventory.bean.DataCount;
import com.supoin.commoninventory.R;

public class CountDataAdapter extends BaseAdapter {
	private List<DataCount> list;
	private Context context;

	public CountDataAdapter(Context context, List<DataCount> list) {
		super();
		this.context = context;
		this.list = list;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public DataCount getItem(int arg0) {
		// TODO Auto-generated method stub
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
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.countdata_listitem, null);
			viewHolder.number = (TextView) convertView
					.findViewById(R.id.number);
			viewHolder.count = (TextView) convertView.findViewById(R.id.count);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
//		String first = "货架号：";
//		String first2 = "货架实盘数量总计：";

		String number = list.get(position).getShelfNumber();
		String count =  list.get(position).getCount();
		viewHolder.number.setText(number);
		viewHolder.count.setText(count);
		return convertView;
	}

	private class ViewHolder {
		TextView number;
		TextView count;
	}

}
