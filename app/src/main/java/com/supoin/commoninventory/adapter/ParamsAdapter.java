package com.supoin.commoninventory.adapter;

import com.supoin.commoninventory.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ParamsAdapter extends BaseAdapter {
	private Context mContext;
	private String[] arrParams;
	private int iSelectItem = -1;

	public ParamsAdapter(Context context, String[] params) {
		mContext = context;
		arrParams = params;
	}

	public void setSelectItem(int selectItem) {
		iSelectItem = selectItem;
	}
	
	public void setData() {
		this.notifyDataSetChanged();
	}

	public void setData(String[] params) {
		arrParams = params;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrParams == null ? 0 : arrParams.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrParams[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.layout_params_list_item, null);
			holder.titleText = (TextView) convertView
					.findViewById(R.id.tv_title);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.titleText.setText(arrParams[position]);

		if (position == iSelectItem) {
			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
		} else {
			convertView.setBackgroundColor(Color.TRANSPARENT);
		}

		return convertView;
	}

	public final class ViewHolder {
		public TextView titleText;
	}

}
