package com.supoin.commoninventory.util;

/*
 * SparseArray<View>�ڴ�������ϵȼ���HashMap<Interger, View>,
 * SparseArray��Android�ṩ��һ�����ݽṹ��ּ����߲�ѯ��Ч��.
 * ����View childView = viewHolder.get(id);�������ʱ���ϵĿ����Ǽ�С�ģ���ȫ����Ӱ�쵽ִ�е�Ч��.
 * 
 *
 * ��adapterʹ��
 * public View getView(int position,View convertView,ViewGroup parent){
 *
 *  if(convertView == null)
 *  {
 *  	convertView = mInflater.inflate(R.layout...,parent,false);
 *  }
 *
 *  ImageView img = ViewHolder.get(convertView,R.id.....);
 * 	 return convertView;
 * }
 * 
 * */

/**
 * ViewHolder������
 *
 * @author zhangfan
 */
import android.util.SparseArray;
import android.view.View;

public class ViewHolder {
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
}
