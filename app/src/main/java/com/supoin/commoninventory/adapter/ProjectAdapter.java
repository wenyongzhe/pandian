package com.supoin.commoninventory.adapter;
import java.util.List;

import com.supoin.commoninventory.R;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProjectAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater inflater;
	List<String> projectList;
	
	public ProjectAdapter(Context context, List<String> projectList) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.projectList = projectList;
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
			convertView = inflater.inflate(R.layout.project_dropdown_item2, parent, false);
		}	
		
		TextView tv = (TextView) ViewHolder.get(convertView, R.id.tv_dropdown_projectname);
		String project = getItem(position);
		tv.setText(project);
		return convertView;
	};

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.project_list_item2, null);
		}
		TextView projectNameTv = ViewHolder.get(convertView, R.id.tv_projectname);
		String project = getItem(position);
		projectNameTv.setText(project);
		return convertView;
	}
	public static class ViewHolder {
		// I added a generic return type to reduce the casting noise in client code
		@SuppressWarnings("unchecked")
		public static <T extends View> T get(View view, int id) {
			SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
			if (null == viewHolder) {
				viewHolder = new SparseArray<View>();
				view.setTag(viewHolder);
			}
			View childView = viewHolder.get(id);
			if (null == childView) {
				childView = view.findViewById(id);
				viewHolder.put(id, childView);
			}
			return (T) childView;
		}
}
}
