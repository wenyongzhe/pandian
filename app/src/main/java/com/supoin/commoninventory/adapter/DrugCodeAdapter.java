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
import android.widget.RadioButton;
import android.widget.TextView;
import com.supoin.commoninventory.R;

public class DrugCodeAdapter extends BaseAdapter{
		private List<DrugCodeDataContent> drugCodeDataContent;
		private Context context;
		private HashMap<Integer, ItemCache> viewMap;
		private  HashMap<String, Boolean> isSelected;  
		private static boolean isMoved = false; 
		private int x = 10000;
		private int y = 10000;
		long mLastTime = 0;
		private long onclickTimes = 0;
		@SuppressLint("UseSparseArrays")
		public DrugCodeAdapter(Context context,
				List<DrugCodeDataContent> drugCodeDataContent) {
			super();
			this.drugCodeDataContent = drugCodeDataContent;
			this.context = context;
			viewMap = new HashMap<Integer, ItemCache>();
			isSelected = new HashMap<String, Boolean>();
//			initDate(drugCodeDataContent);
		}
		  // 初始化isSelected的数据  
//	    private void initDate(List<DrugCodeDataContent> drugCodeDataContent) {  
//	        for (int i = 0; i < drugCodeDataContent.size(); i++) {  
//	            getIsSelected().put(i, false);  
//	        }  
//	    }  
	    public  HashMap<String, Boolean> getIsSelected() {  
	        return isSelected;  
	    }  
	  
	    public  void setIsSelected(HashMap<String, Boolean> isSelected) {  
	        this.isSelected = isSelected;  
	    }  
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return drugCodeDataContent.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return drugCodeDataContent.get(position);
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
			DrugCodeDataContent drugCodeItem = (DrugCodeDataContent) getItem(position);
			ItemCache itemCache = viewMap.get(position);
			View rowView;
			ViewCache viewCache;
			if (itemCache == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				rowView = inflater.inflate(R.layout.drugdata_list, null);
				viewCache = new ViewCache(rowView);
				rowView.setTag(viewCache);
				ItemCache item = new ItemCache(rowView);
				viewMap.put(position, item);
			} else {
				rowView = itemCache.getView();
				viewCache = (ViewCache) rowView.getTag();
			}

			TextView drugcode_tv = viewCache.getDrugCodeTextView();
			drugcode_tv.setText(drugCodeItem.getProdutCode());

			TextView codeLevel_tv = viewCache.getCodeLevelTextView();
			codeLevel_tv.setText(drugCodeItem.getCodeLeverl());

			TextView count_tv = viewCache.getCountTextView();
			count_tv.setText(drugCodeItem.getCount());
			RadioButton radioButton = viewCache.getRadioButton();
//			radioButton.setChecked(getIsSelected().get(position));  
//			radioButton.setFocusable(false);
			//当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中   
			final RadioButton radio=radioButton;
			radio.setOnClickListener(new View.OnClickListener() {  
		             
		           public void onClick(View v) {           
		               //重置，确保最多只有一项被选中  
		               for(String  key:isSelected.keySet()){  
		            	   isSelected.put(key, false);                 
		               }  
		               isSelected.put(String.valueOf(position), radio.isChecked());  
		               notifyDataSetChanged();  
		           }  
		       });  
		      
		       boolean res=false;  
		       if(isSelected.get(String.valueOf(position)) == null || isSelected.get(String.valueOf(position))== false){  
		           res=false;  
		           isSelected.put(String.valueOf(position), false);  
		       }  
		       else  
		           res = true;  
		         
		       radioButton.setChecked(res);  
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
			private TextView drugcode_tv;
			private TextView codelevel_tv;
			private TextView count_tv;
			private RadioButton radioBtn;
			
			public ViewCache(View baseView) {
				this.baseView = baseView;
			}

			public TextView getDrugCodeTextView() {
				if (drugcode_tv == null) {
					drugcode_tv = (TextView) baseView
							.findViewById(R.id.tv_drugcode);
				}
				return drugcode_tv;
			}
			public RadioButton getRadioButton() {
				if (radioBtn == null) {
					radioBtn = (RadioButton) baseView
							.findViewById(R.id.radiobutton);
				}
				return radioBtn;
			}
			public TextView getCodeLevelTextView() {
				if (codelevel_tv == null) {
					codelevel_tv = (TextView) baseView
							.findViewById(R.id.tv_codelevel);
				}
				return codelevel_tv;
			}

			public TextView getCountTextView() {
				if (count_tv == null) {
					count_tv = (TextView) baseView
							.findViewById(R.id.tv_count);
				}
				return count_tv;
			}
		
		}
		public static class DrugCodeDataContent{
			public String produtCode;//品种码
			public String codeLeverl;//级别
			public String count;//包装数量
			public String getProdutCode() {
				return produtCode;
			}
			public void setProdutCode(String produtCode) {
				this.produtCode = produtCode;
			}
			public String getCodeLeverl() {
				return codeLeverl;
			}
			public void setCodeLeverl(String codeLeverl) {
				this.codeLeverl = codeLeverl;
			}
			public String getCount() {
				return count;
			}
			public void setCount(String count) {
				this.count = count;
			}

		
		}
}