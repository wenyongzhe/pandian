package com.supoin.commoninventory.adapter;


import java.util.HashMap;
import java.util.List;

import com.supoin.commoninventory.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomerListAdapater extends BaseAdapter {
	private List<String> customerName;
	private Context context;
	private HashMap<Integer, ItemCache> viewMap;

	@SuppressLint("UseSparseArrays")
	public CustomerListAdapater(Context context, List<String> customerName) {
		super();
		this.customerName = customerName;
		this.context = context;
		viewMap = new HashMap<Integer, ItemCache>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return customerName.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return customerName.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub	
		Activity activity = (Activity) context;
		String listSum = (String) getItem(position);
		ItemCache itemCache = viewMap.get(position);
		View rowView;
		ViewCache viewCache;
		if (itemCache == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.data_list, null);
			viewCache = new ViewCache(rowView);
			rowView.setTag(viewCache);
			ItemCache item = new ItemCache(rowView);
			viewMap.put(position, item);
		} else {
			rowView = itemCache.getView();
			viewCache = (ViewCache) rowView.getTag();
		}
		TextView name_tv = viewCache.getNameTextView();
		name_tv.setText(listSum);
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
		private TextView name_tv;

		public ViewCache(View baseView) {
			this.baseView = baseView;
		}

		public TextView getNameTextView() {
			if (name_tv == null) {
				name_tv = (TextView) baseView
						.findViewById(R.id.lv_text);
			}
			return name_tv;
		}
	}

}
