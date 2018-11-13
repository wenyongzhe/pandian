package com.supoin.commoninventory.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.db.entity.CheckListEntity;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.util.Utility;

import java.util.List;

public class GoodsDetailsListAdapter extends BaseAdapter {
	// 商品扫描详情列表
	private List<CheckListEntity> list;
	private Context context;
	private int itemSelected = -1;
	private SharedPreferences sp;
	private boolean isRFID;
	
	public void setData(int itemSelected) {
		this.itemSelected = itemSelected;
		notifyDataSetChanged();
	}

	public int getItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(int itemSelected) {
		this.itemSelected = itemSelected;
	}

	public List<CheckListEntity> getList() {
		return list;
	}

	public void setList(List<CheckListEntity> list) {
		this.list = list;
	}

	public GoodsDetailsListAdapter(Context context) {
		super();
		this.context = context;
	}

	public GoodsDetailsListAdapter(Context context, List<CheckListEntity> list) {
		super();
		this.context = context;
		this.list = list;
		sp = context.getSharedPreferences("InvenConfig", context.MODE_PRIVATE);
		isRFID = sp.getString(
				ConfigEntity.ScanPatternKey,
				ConfigEntity.ScanPattern).equals("1");
	}

	@Override
	public int getCount() {

		if(isRFID){
			return list.size();
		}else{
			if (list.size() > Integer.parseInt(sp.getString(
					ConfigEntity.ScanningShowLineNumbKey,
					ConfigEntity.ScanningShowLineNumb))) {
				return Integer.parseInt(sp.getString(
						ConfigEntity.ScanningShowLineNumbKey,
						ConfigEntity.ScanningShowLineNumb));
			} else {
				return list.size();
			}
		}

	}

	@Override
	public CheckListEntity getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.item_list_goods_details,null);
			viewHolder.tv_GdBar = (TextView) convertView
					.findViewById(R.id.tv_GdBar);
			viewHolder.tv_dCheckNum = (TextView) convertView
					.findViewById(R.id.tv_dCheckNum);
			viewHolder.tv_item_name = (TextView) convertView
					.findViewById(R.id.tv_item_name);
			viewHolder.linearLayout = (LinearLayout) convertView
					.findViewById(R.id.lin_layout);
			viewHolder.ll_checknum = (LinearLayout) convertView
					.findViewById(R.id.ll_checknum);
			viewHolder.tv_GdBar.setSelected(true);
			if(isRFID){
				viewHolder.ll_checknum.setVisibility(View.GONE);
				viewHolder.tv_item_name.setText("标签：");
			}
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_GdBar.setText(list.get(position).getStrBar());
		
		if(Utility.isInteger(list.get(position).getdNum())){
			viewHolder.tv_dCheckNum.setText((int)list.get(position).getdNum()+"");
		} else {
			viewHolder.tv_dCheckNum.setText(list.get(position).getdNum()+"");
		}

		if (itemSelected == position) {

			viewHolder.linearLayout.setBackgroundColor(context.getResources()
					.getColor(R.color.gray_bg));

		} else {
			viewHolder.linearLayout.setBackgroundColor(Color.TRANSPARENT);
		}
		return convertView;
	}

	private class ViewHolder {
		TextView tv_GdBar;
		TextView tv_dCheckNum;
		TextView tv_item_name;
		LinearLayout linearLayout;
		LinearLayout ll_checknum;
	}
}
