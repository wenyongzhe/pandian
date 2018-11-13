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

public class GoodsIdSizeBitLengthFragment extends Fragment implements
		OnClickListener {

	private View view;
	private SharedPreferences sp;
	private Button btn_save;
	private EditText tv_goodid_bit_length, tv_goodid_order, tv_size_bit_length,
			tv_size_order;

	private String strGoodIdBitLength, strGoodIdOrder, strSizeBitLength,
			strSizeOrder;

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
		view = inflater
				.inflate(R.layout.fragment_goodsid_size_bit_length, null);
		// 铺满view
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		btn_save = (Button) view.findViewById(R.id.btn_save);
		tv_goodid_bit_length = (EditText) view
				.findViewById(R.id.tv_goodid_bit_length);
		tv_goodid_order = (EditText) view.findViewById(R.id.tv_goodid_order);
		tv_size_bit_length = (EditText) view
				.findViewById(R.id.tv_size_bit_length);
		tv_size_order = (EditText) view.findViewById(R.id.tv_size_order);
		btn_save.setOnClickListener(this);
		// 初始化默认参数
		strGoodIdBitLength = sp.getString(ConfigEntity.GoodIdBitLengthKey, "");
		strGoodIdOrder = sp.getString(ConfigEntity.GoodIdBitLengthOrderKey, "");
		strSizeBitLength = sp.getString(ConfigEntity.SizeBitLength0Key, "");
		strSizeOrder = sp.getString(ConfigEntity.SizeBitLength0OrderKey, "");

		tv_goodid_bit_length.setText(strGoodIdBitLength);
		tv_goodid_order.setText(strGoodIdOrder);
		tv_goodid_order.setEnabled(false);
		tv_size_bit_length.setText(strSizeBitLength);
		tv_size_order.setText(strSizeOrder);
		tv_size_order.setEnabled(false);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:

			sp.edit()
					.putString(ConfigEntity.GoodIdBitLengthKey,tv_goodid_bit_length.getText().toString().trim())
					.putString(ConfigEntity.GoodIdBitLengthOrderKey,tv_goodid_order.getText().toString().trim())
					.putString(ConfigEntity.SizeBitLength0Key, tv_size_bit_length.getText().toString().trim())
					.putString(ConfigEntity.SizeBitLength0OrderKey,tv_size_order.getText().toString().trim()).commit();
			AlertUtil.showToast(AlertUtil.getString(R.string.saved_success), getActivity());
			break;

		}

	}

}
