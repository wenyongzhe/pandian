package com.supoin.commoninventory.instore.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.db.entity.MainSummaryInfo;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.Utility;

public class StockInListAdapter extends BaseAdapter {
	// 商品扫描盘点列表
	private List<MainSummaryInfo> list;
	private Context context;

	public List<MainSummaryInfo> getList() {
		return list;
	}

	public void setList(List<MainSummaryInfo> list) {
		this.list = list;
	}

	public StockInListAdapter(Context context, List<MainSummaryInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public MainSummaryInfo getItem(int arg0) {
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
					R.layout.item_listview_in_stock, null);
			viewHolder.tv_checkID = (TextView) convertView
					.findViewById(R.id.tv_checkID);
//			viewHolder.tv_position = (TextView) convertView
//					.findViewById(R.id.tv_positionID);
			viewHolder.tv_number = (TextView) convertView
					.findViewById(R.id.tv_number);
			
			tv_bianhao = (TextView) convertView
					.findViewById(R.id.tv_bianhao);
//			tv_huowei = (TextView) convertView
//					.findViewById(R.id.tv_huowei);
			tv_shuliang = (TextView) convertView
					.findViewById(R.id.tv_shuliang);
			initText();
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_checkID.setText(list.get(position).getCheckID());
//		viewHolder.tv_position.setText(list.get(position).getPositionID());
		
		if(Utility.isInteger(list.get(position).getQty())){
			viewHolder.tv_number.setText((int)list.get(position).getQty()+"");
		} else {
			viewHolder.tv_number.setText(list.get(position).getQty()+"");
		}
		
		return convertView;
	}
	private TextView tv_bianhao;
//	private TextView tv_huowei;
	private TextView tv_shuliang;
	
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
		tv_bianhao.setText(importStrArrayList.get(1)+":");
//		tv_huowei.setText(importStrArrayList.get(2)+":");	
		tv_shuliang.setText(importStrArrayList.get(15)+":");	
		}
	private class ViewHolder {
		TextView tv_checkID;
//		TextView tv_position;
		TextView tv_number;
	}
}
