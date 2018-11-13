package com.supoin.commoninventory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.supoin.commoninventory.R;

public class SpinnerAdapter extends ArrayAdapter<String> {
	private Context mContext;
	private String[] mStringArray;

	public SpinnerAdapter(Context context, String[] stringArray) {
//		super(context, android.R.layout.simple_spinner_item, stringArray);
		super(context, R.layout.simple_spinner_item1, stringArray);
		mContext = context;
		mStringArray = stringArray;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// �޸�Spinnerչ�����������ɫ
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(
					R.layout.spinner_dropdown_item, parent,
					false);
		}

		// �˴�text1��SpinnerĬ�ϵ�������ʾ���ֵ�TextView
		TextView tv = (TextView) convertView.findViewById(R.id.tv_dropdown);
		tv.setText(mStringArray[position]);
//		tv.setTextSize(22f);
//		tv.setTextColor(Color.RED);

		return convertView;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// �޸�Spinnerѡ�������������ɫ
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(
					R.layout.simple_spinner_item1, parent, false);
		}

		// �˴�text1��SpinnerĬ�ϵ�������ʾ���ֵ�TextView
//		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		TextView tv = (TextView) convertView.findViewById(R.id.text1);
		tv.setText(mStringArray[position]);
//		tv.setTextSize(22f);
//		tv.setTextColor(Color.BLUE);
		return convertView;
	}

}