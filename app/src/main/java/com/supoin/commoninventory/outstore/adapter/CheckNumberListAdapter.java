package com.supoin.commoninventory.outstore.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.db.SQLOutStockDataSqlite.StaCheckNODetail;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.Utility;

public class CheckNumberListAdapter extends BaseAdapter {
	private List<StaCheckNODetail> list;
	private Context context;

	public List<StaCheckNODetail> getList() {
		return list;
	}

	public void setList(List<StaCheckNODetail> list) {
		this.list = list;
	}

	public CheckNumberListAdapter(Context context, List<StaCheckNODetail> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public StaCheckNODetail getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(
					R.layout.item_listview_check_number, null);
			viewHolder.ll_location = (LinearLayout) convertView
					.findViewById(R.id.ll_location);
			viewHolder.ll_location.setVisibility(View.GONE);
			viewHolder.tv_order_number = (TextView) convertView
					.findViewById(R.id.tv_order_number);
			viewHolder.tv_GdBar = (TextView) convertView
					.findViewById(R.id.tv_GdBar);
			viewHolder.tv_positionID = (TextView) convertView
					.findViewById(R.id.tv_positionID);
			viewHolder.tv_nums = (TextView) convertView
					.findViewById(R.id.tv_nums);
			tv_huowei_cknuml = (TextView) convertView
					.findViewById(R.id.tv_huowei_cknuml);
			viewHolder.tv_GdBar.setSelected(true);
			initText();
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_GdBar.setText(list.get(position).getGdBar());
		viewHolder.tv_positionID.setText(list.get(position).getPostionID());
		
		if(Utility.isInteger(list.get(position).getSums())){
			viewHolder.tv_nums.setText((int)list.get(position).getSums() + "");
		} else {
			viewHolder.tv_nums.setText(list.get(position).getSums() + "");
		}
		
		viewHolder.tv_order_number.setText(position + 1 + "");
		return convertView;
	}
	private TextView tv_huowei_cknuml;
	
	private SharedPreferences sp;
	/**动态修改编号货位提示*/
	private void initText() {
		sp = myApplication.mContext.getSharedPreferences("InvenConfig", Context.MODE_PRIVATE);
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		tv_huowei_cknuml.setText(importStrArrayList.get(2)+":");
		}
	private class ViewHolder {
		LinearLayout ll_location;
		TextView tv_order_number;
		TextView tv_GdBar;
		TextView tv_positionID;
		TextView tv_nums;
	}
}
