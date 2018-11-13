package com.supoin.commoninventory.adapter;

import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.supoin.commoninventory.R;
public class ExportSettingLeftAdapter extends BaseAdapter {

	private List<String> list;
	private static HashMap<Integer, Boolean> isSelected;
	private Context context;
	private LayoutInflater inflater = null;
	private int selectedPosition = -1;
	public ExportSettingLeftAdapter(List<String> list, Context context) {
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
	public void setSelectedPosition(int position) {
		selectedPosition = position;
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

	@SuppressLint("ResourceAsColor") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			// 导入布局并赋值给convertview
			convertView = inflater.inflate(R.layout.item_list_import_setting,
					null);
			holder.linearLayout_item = (LinearLayout) convertView
					.findViewById(R.id.linearLayout_item);
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

		if (position % 2 == 0) {
			holder.linearLayout_item.setBackgroundColor(Color.rgb(225,225,225));
//			e1e1e1  225,225,225
		} else {
			holder.linearLayout_item.setBackgroundColor(Color.TRANSPARENT);
		}
		holder.tv_display_item.setText(list.get(position));
		// 根据isSelected来设置checkbox的选中状况
		holder.checkbox_single_display
				.setChecked(getIsSelected().get(position));
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		ExportSettingLeftAdapter.isSelected = isSelected;
	}

	public final class ViewHolder {
		public LinearLayout linearLayout_item;
		public TextView tv_display_item;
		public CheckBox checkbox_single_display;
	}

}
