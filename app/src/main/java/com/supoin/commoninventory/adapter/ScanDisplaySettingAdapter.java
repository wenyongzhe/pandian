package com.supoin.commoninventory.adapter;

import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.supoin.commoninventory.R;
import com.supoin.commoninventory.service.androidService;

public class ScanDisplaySettingAdapter extends BaseAdapter {

	private List<String> list;
	private static HashMap<Integer, Boolean> isSelected;
	private Context context;
	private LayoutInflater inflater = null;

	public ScanDisplaySettingAdapter(List<String> list, Context context) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		initDate();
	}

	private void initDate() {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
 public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			// 导入布局并赋值给convertview
			convertView = inflater.inflate(
					R.layout.item_list_scan_display_setting, null);
			holder.tv_position = (TextView) convertView
					.findViewById(R.id.tv_position);
			holder.tv_display_item = (TextView) convertView
					.findViewById(R.id.tv_display_item);
			holder.checkbox_single_display = (CheckBox) convertView
					.findViewById(R.id.checkbox_single_display);
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}

		// 设置list中TextView的显示
		holder.tv_position.setText(position + 1 + "");
		holder.tv_display_item.setText(list.get(position));
		// 根据isSelected来设置checkbox的选中状况
		holder.checkbox_single_display.setChecked(getIsSelected().get(position));
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		ScanDisplaySettingAdapter.isSelected = isSelected;
	}

	public final class ViewHolder {
		public TextView tv_position;
		public TextView tv_display_item;
		public CheckBox checkbox_single_display;
	}

}
