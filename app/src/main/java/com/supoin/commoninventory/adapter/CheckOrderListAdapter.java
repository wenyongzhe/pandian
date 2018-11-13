package com.supoin.commoninventory.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.db.SQLStockDataSqlite.StaCheckPositionDetail;

public class CheckOrderListAdapter extends BaseAdapter {
	private List<StaCheckPositionDetail> list;
	private Context context;

	public List<StaCheckPositionDetail> getList() {
		return list;
	}

	public void setList(List<StaCheckPositionDetail> list) {
		this.list = list;
	}

	public CheckOrderListAdapter(Context context, List<StaCheckPositionDetail> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public StaCheckPositionDetail getItem(int arg0) {
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
			convertView = mInflater.inflate(
					R.layout.item_listview_stockplace, null);
			viewHolder.tv_order_number = (TextView) convertView
					.findViewById(R.id.tv_order_number);
			viewHolder.tv_GdBar = (TextView) convertView
					.findViewById(R.id.tv_GdBar);
			viewHolder.tv_nums = (TextView) convertView
					.findViewById(R.id.tv_nums);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_order_number.setText(position+"");
		viewHolder.tv_GdBar.setText(list.get(position).getGdBar());
		viewHolder.tv_nums.setText(list.get(position).getCheckNum());
		return convertView;
	}

	private class ViewHolder {
		TextView tv_order_number;
		TextView tv_GdBar;
		TextView tv_nums;
	}
}
