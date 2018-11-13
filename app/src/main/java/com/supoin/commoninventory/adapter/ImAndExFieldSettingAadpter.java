package com.supoin.commoninventory.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.supoin.commoninventory.R;

public class ImAndExFieldSettingAadpter extends BaseAdapter {
		private List<String> list;
		private Context context;
		public List<String> getList() {
			return list;
		}

		public void setList(List<String> list) {
			this.list = list;
		}

		public ImAndExFieldSettingAadpter(Context context, List<String> list) {
			super();
			this.context = context;
			this.list = list;
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
			return position;
		}

		@SuppressLint("InflateParams") @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if (null == convertView) {
				viewHolder = new ViewHolder();
				LayoutInflater mInflater = LayoutInflater.from(context);
				convertView = mInflater.inflate(
						R.layout.item_imandexfieldsetting, null);
				viewHolder.et_name = (TextView) convertView
						.findViewById(R.id.et_name);

	            convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag(); 
			}

			viewHolder.et_name.setText(list.get(position));
			
			return convertView;
		}

		private class ViewHolder {
			TextView et_name;
		}
}
