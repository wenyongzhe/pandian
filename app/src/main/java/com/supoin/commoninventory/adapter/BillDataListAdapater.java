package com.supoin.commoninventory.adapter;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.supoin.commoninventory.R;

public class BillDataListAdapater extends BaseAdapter{

		private List<BillDataContent> billDataContent;
		private Context context;
		private HashMap<Integer, ItemCache> viewMap;
		private  HashMap<Integer, Boolean> isSelected;  
		private static boolean isMoved = false; 
		private int x = 10000;
		private int y = 10000;
		long mLastTime = 0;
		private long onclickTimes = 0;
		@SuppressLint("UseSparseArrays")
		public BillDataListAdapater(Context context,
				List<BillDataContent> billDataContent) {
			super();
			this.billDataContent = billDataContent;
			this.context = context;
			viewMap = new HashMap<Integer, ItemCache>();
			isSelected = new HashMap<Integer, Boolean>();
			initDate(billDataContent);
		}
		  // 初始化isSelected的数据  
	    private void initDate(List<BillDataContent> billDataContent) {  
	        for (int i = 0; i < billDataContent.size(); i++) {  
	            getIsSelected().put(i, false);  
	        }  
	    }  
	    public  HashMap<Integer, Boolean> getIsSelected() {  
	        return isSelected;  
	    }  
	  
	    public  void setIsSelected(HashMap<Integer, Boolean> isSelected) {  
	        this.isSelected = isSelected;  
	    }  
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return billDataContent.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return billDataContent.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView( final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Activity activity = (Activity) context;
			BillDataContent billSum = (BillDataContent) getItem(position);
			ItemCache itemCache = viewMap.get(position);
			View rowView;
			ViewCache viewCache;
			if (itemCache == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				rowView = inflater.inflate(R.layout.billdata_list, null);
				viewCache = new ViewCache(rowView);
				rowView.setTag(viewCache);
				ItemCache item = new ItemCache(rowView);
				viewMap.put(position, item);
			} else {
				rowView = itemCache.getView();
				viewCache = (ViewCache) rowView.getTag();
			}

			TextView orderId_tv = viewCache.getOrderIdTextView();
			orderId_tv.setText(billSum.getOrderId());

			TextView orderType_tv = viewCache.getOrderTypeTextView();
			orderType_tv.setText(billSum.getOrderType());

			TextView drugType_tv = viewCache.getDrugTypeTextView();
			drugType_tv.setText(billSum.getDrugType());
			
			TextView orderId_tv1 = viewCache.getOrderIdTextView1();

			TextView orderType_tv1 = viewCache.getOrderTypeTextView1();

			TextView drugType_tv1 = viewCache.getDrugTypeTextView1();
			final CheckBox checkBox = viewCache.getCheckBox();
//			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){   
//                @Override   
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {   
//                    if(isChecked){   
//                    	isSelected.put(position,true);  
//                    }else{   
//                    	isSelected.put(position,false);  
//                    }  
//                }   
//            });   
			checkBox.setChecked(getIsSelected().get(position));  
			checkBox.setFocusable(false);
            //onTouch 是用户操作触发的第一个操作。该事件return true 后续的时间是没有机会在执行了
            //retrun false 后，其它监听时间才有机会执行
//            rowView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    // TODO Auto-generated method stub
//            	if(getIsSelected().get(position)){
//	            	isSelected.put(position, false);
//	            	setIsSelected(isSelected);
//	            }else{
//	            	isSelected.put(position, true);
//	            	setIsSelected(isSelected);
//	            }
//            	checkBox.setChecked(getIsSelected().get(position));  
//	            notifyDataSetInvalidated();                                                          
//            }
//            });
          
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
			private TextView orderId_tv;
			private TextView orderType_tv;
			private TextView drugType_tv;
			private TextView orderId_tv1;
			private TextView orderType_tv1;
			private TextView drugType_tv1;
			private CheckBox checkBox;
			
			public ViewCache(View baseView) {
				this.baseView = baseView;
			}

			public TextView getOrderIdTextView() {
				if (orderId_tv == null) {
					orderId_tv = (TextView) baseView
							.findViewById(R.id.tv_billnum4);
				}
				return orderId_tv;
			}
			public CheckBox getCheckBox() {
				if (checkBox == null) {
					checkBox = (CheckBox) baseView
							.findViewById(R.id.checkbox);
				}
				return checkBox;
			}
			public TextView getOrderTypeTextView() {
				if (orderType_tv == null) {
					orderType_tv = (TextView) baseView
							.findViewById(R.id.tv_billtype);
				}
				return orderType_tv;
			}

			public TextView getDrugTypeTextView() {
				if (drugType_tv == null) {
					drugType_tv = (TextView) baseView
							.findViewById(R.id.tv_drugtype);
				}
				return drugType_tv;
			}
			public TextView getOrderIdTextView1() {
				if (orderId_tv1 == null) {
					orderId_tv1 = (TextView) baseView
							.findViewById(R.id.tv_billnum41);
				}
				return orderId_tv1;
			}

			public TextView getOrderTypeTextView1() {
				if (orderType_tv1 == null) {
					orderType_tv1 = (TextView) baseView
							.findViewById(R.id.tv_billtype1);
				}
				return orderType_tv1;
			}

			public TextView getDrugTypeTextView1() {
				if (drugType_tv1 == null) {
					drugType_tv1 = (TextView) baseView
							.findViewById(R.id.tv_drugtype1);
				}
				return drugType_tv1;
			}
		}
		public static class BillDataContent{
			public String orderId;
			public String orderType;
			public String drugType;

			public String getOrderId() {
				return orderId;
			}

			public void setOrderId(String orderId) {
				this.orderId = orderId;
			}

			public String getOrderType() {
				return orderType;
			}

			public void setOrderType(String orderType) {
				this.orderType = orderType;
			}

			public String getDrugType() {
				return drugType;
			}

			public void setDrugType(String drugType) {
				this.drugType = drugType;
			}

		}
}