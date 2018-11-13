package com.supoin.commoninventory.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.supoin.commoninventory.adapter.ProjectAdapter.ViewHolder;
import com.supoin.commoninventory.R;

public class LoginProjectAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	List<String> projectList;
	private String name;

	public LoginProjectAdapter(Context context, List<String> projectList,String name) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.projectList = projectList;
		this.name=name;
	}

	@Override
	public int getCount() {
		return projectList.size();
	}

	@Override
	public String getItem(int position) {
		return projectList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.project_dropdown_item,
					parent, false);
		}

		TextView tv = (TextView) ViewHolder.get(convertView,
				R.id.tv_dropdown_projectname1);
		String project = getItem(position);
		tv.setText(project);
		return convertView;
	};

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.project_list_item, null);
		}
		TextView projectNameTv = ViewHolder.get(convertView,
				R.id.tv_projectname1);
		TextView nameTv = ViewHolder.get(convertView,
				R.id.textView1);	
		String project = getItem(position);
		projectNameTv.setText(project);
		nameTv.setText(name);
		return convertView;
	}
}
