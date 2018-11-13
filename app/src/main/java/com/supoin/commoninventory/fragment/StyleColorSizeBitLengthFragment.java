package com.supoin.commoninventory.fragment;

import android.annotation.SuppressLint;
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

public class StyleColorSizeBitLengthFragment extends Fragment implements
		OnClickListener {
	private View view;
	private EditText tv_style_bit_length, tv_style_order, tv_color_bit_length,
			tv_color_order, tv_size_bit_length, tv_size_order;
	private String strStyleBitLength, strStyleOrder, strColorBitLength,
			strColorOrder, strSizeBitLength, strSizeOrder;
	private SharedPreferences sp;
	private Button btn_save;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getActivity().getSharedPreferences("InvenConfig",getActivity().MODE_PRIVATE);
	}

	@SuppressLint("ResourceAsColor") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_style_color_size_bit_length,
				null);
		// ÆÌÂúview
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		btn_save = (Button) view.findViewById(R.id.btn_save);

		tv_style_bit_length = (EditText) view
				.findViewById(R.id.tv_style_bit_length);
		tv_style_order = (EditText) view.findViewById(R.id.tv_style_order);
		tv_color_bit_length = (EditText) view
				.findViewById(R.id.tv_color_bit_length);
		tv_color_order = (EditText) view.findViewById(R.id.tv_color_order);
		tv_size_bit_length = (EditText) view
				.findViewById(R.id.tv_size_bit_length);
		tv_size_order = (EditText) view.findViewById(R.id.tv_size_order);
		btn_save.setOnClickListener(this);

		strStyleBitLength = sp.getString(ConfigEntity.StyleBitLengthKey,ConfigEntity.StyleBitLength);
		strStyleOrder = sp.getString(ConfigEntity.StyleBitLengthOrderKey, ConfigEntity.StyleBitLengthOrder);
		strColorBitLength = sp.getString(ConfigEntity.ColorBitLengthKey,ConfigEntity.ColorBitLength);
		strColorOrder = sp.getString(ConfigEntity.ColorBitLengthOrderKey, ConfigEntity.ColorBitLengthOrder);
		strSizeBitLength = sp.getString(ConfigEntity.SizeBitLengthKey,ConfigEntity.SizeBitLength);
		strSizeOrder = sp.getString(ConfigEntity.SizeBitLengthOrderKey,ConfigEntity.SizeBitLengthOrder);
		// ³õÊ¼»¯Ä¬ÕJ
		tv_style_bit_length.setText(strStyleBitLength);
		tv_style_order.setText(strStyleOrder);
		tv_style_order.setEnabled(false);
		tv_color_bit_length.setText(strColorBitLength);
		tv_color_order.setText(strColorOrder);
		tv_size_bit_length.setText(strSizeBitLength);
		tv_size_order.setText(strSizeOrder);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			sp.edit().putString(ConfigEntity.StyleBitLengthKey,tv_style_bit_length.getText().toString().trim())
					.putString(ConfigEntity.StyleBitLengthOrderKey,tv_style_order.getText().toString().trim())
					
					.putString(ConfigEntity.ColorBitLengthKey,tv_color_bit_length.getText().toString().trim())
					.putString(ConfigEntity.ColorBitLengthOrderKey,tv_color_order.getText().toString().trim())
					
					.putString(ConfigEntity.SizeBitLengthKey, tv_size_bit_length.getText().toString().trim())
					.putString(ConfigEntity.SizeBitLengthOrderKey, tv_size_order.getText().toString().trim())
					.commit();
			AlertUtil.showToast(AlertUtil.getString(R.string.saved_success), getActivity());
			break;

		}

	}

}
