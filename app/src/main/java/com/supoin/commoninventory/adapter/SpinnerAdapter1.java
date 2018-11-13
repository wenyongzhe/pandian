package com.supoin.commoninventory.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.supoin.commoninventory.R;

public class SpinnerAdapter1 extends ArrayAdapter<String> {
	private Context mContext;
	private List<String> strlist;

	public SpinnerAdapter1(Context context, List<String> strlist) {
		super(context, R.layout.simple_spinner_item0, strlist);
		mContext = context;
		this.strlist = strlist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strlist.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return strlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// 修改Spinner展开后的字体颜色
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.spinner_dropdown_item,
					parent, false);
		}

		// 此处text1是Spinner默认的用来显示文字的TextView
		TextView tv = (TextView) convertView.findViewById(R.id.tv_dropdown);
		tv.setText(strlist.get(position));
		tv.setTextSize(22f);
		// tv.setTextColor(Color.RED);

		return convertView;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 修改Spinner选择后结果的字体颜色
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.simple_spinner_item0,
					parent, false);
		}

		// 此处text1是Spinner默认的用来显示文字的TextView
		TextView tv = (TextView) convertView.findViewById(R.id.text1);
		tv.setText(strlist.get(position));
		tv.setTextSize(22f);
		// tv.setTextColor(Color.BLUE);
		return convertView;
	}

}