package com.supoin.commoninventory.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.util.AlertUtil;

public class GoodsIdSizeSeparatorFragment extends Fragment implements
		OnClickListener {
	private View view;
	private EditText et_goodsid_separator, et_size_separator,tv_goodsid_separator_order,tv_size_separator_order;
	private String strGoodsIdSeparator, strGoodsIdSeparatorOrder,
			strSizeSeparator, strSizeSeparatorOrder;
	private SharedPreferences sp;
	private Button btn_save;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getActivity().getSharedPreferences("InvenConfig",
				getActivity().MODE_PRIVATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_goodsid_size_separator, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		et_goodsid_separator = (EditText) view
				.findViewById(R.id.et_goodsid_separator);
		et_size_separator = (EditText) view
				.findViewById(R.id.et_size_separator);

		tv_goodsid_separator_order = (EditText) view
				.findViewById(R.id.tv_goodsid_separator_order);
		tv_size_separator_order = (EditText) view
				.findViewById(R.id.tv_size_separator_order);
		
		btn_save = (Button) view.findViewById(R.id.btn_save);

		btn_save.setOnClickListener(this); 

		strGoodsIdSeparator = sp.getString(ConfigEntity.GoodsIdSeparator2Key,"");
		strGoodsIdSeparatorOrder = sp.getString(ConfigEntity.GoodsIdSeparatorOrder2Key,"");
		strSizeSeparator = sp.getString(ConfigEntity.SizeSeparator2Key, "");
		strSizeSeparatorOrder = sp.getString(ConfigEntity.SizeSeparatorOrder2Key, "");

		et_goodsid_separator.setText(strGoodsIdSeparator);
		tv_goodsid_separator_order.setText(strGoodsIdSeparatorOrder);
		tv_goodsid_separator_order.setEnabled(false);
		et_size_separator.setText(strSizeSeparator);
		tv_size_separator_order.setText(strSizeSeparatorOrder);
		tv_size_separator_order.setEnabled(false);

		return view;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_save:
			strGoodsIdSeparator = et_goodsid_separator.getText().toString();
			strSizeSeparator = et_size_separator.getText().toString();
			sp.edit()
					.putString(ConfigEntity.GoodsIdSeparator2Key,
							strGoodsIdSeparator)
					.putString(ConfigEntity.GoodsIdSeparatorOrder2Key,
							strGoodsIdSeparatorOrder)
					.putString(ConfigEntity.SizeSeparator2Key, strSizeSeparator)
					.putString(ConfigEntity.SizeSeparatorOrder2Key,
							strSizeSeparatorOrder).commit();
			AlertUtil.showToast(AlertUtil.getString(R.string.saved_success), getActivity());
			break;

		default:
			break;
		}

	}

}
