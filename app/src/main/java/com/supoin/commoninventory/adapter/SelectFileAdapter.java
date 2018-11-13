package com.supoin.commoninventory.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.util.FileUtility;
import com.supoin.commoninventory.util.LogUtil;
import com.supoin.commoninventory.util.ToastUtils;

public class SelectFileAdapter extends BaseAdapter {

	private List<String> items;
	private List<String> paths;
	private Context context;

	public SelectFileAdapter(Context context, List<String> it, List<String> pa) {
		super();
		this.context = context;
		items = it;
		paths = pa;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public String getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.select_list_item, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.text);
			// holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
			if (paths.get(position) != null) {
				File f = new File(paths.get(position).toString());
				if (items.get(position).toString().equals("b1")) {
					holder.text.setText("返回根目录..");
					// holder.icon.setImageBitmap(mIcon1);
				} else if (items.get(position).toString().equals("b2")) {
					holder.text.setText("返回上一层..");								
					// holder.icon.setImageBitmap(mIcon2);
				} else {
					holder.text.setText(f.getName());
					if (f.isDirectory()) {
						// holder.icon.setImageBitmap(mIcon3);
					} else {
						// holder.icon.setImageBitmap(mIcon4);
					}
				}			
			}else {
				holder.text.setText("返回上一层..");	
			}
					
		
		return convertView;
	}

	private class ViewHolder {
		TextView text;
		// ImageView icon;
	}

}