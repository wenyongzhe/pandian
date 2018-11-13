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

public class StyleColorSizeSeparatorFragment extends Fragment implements
		OnClickListener {
	private View view;
	private SharedPreferences sp;
	private EditText et_style_separator, et_color_separator, et_size_separator,tv_style_separator_order, tv_color_separator_order, tv_size_separator_order;
	private Button btn_save;
	private String strStyle, strStyleOrder, strColor, strColorOrder, strSize,
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

		view = inflater.inflate(R.layout.fragment_style_color_size_separator,
				null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		et_style_separator = (EditText) view
				.findViewById(R.id.et_style_separator);
		et_color_separator = (EditText) view
				.findViewById(R.id.et_color_separator);
		et_size_separator = (EditText) view
				.findViewById(R.id.et_size_separator);
		tv_style_separator_order = (EditText) view
				.findViewById(R.id.tv_style_separator_order);
		tv_color_separator_order = (EditText) view
				.findViewById(R.id.tv_color_separator_order);
		tv_size_separator_order = (EditText) view
				.findViewById(R.id.tv_size_separator_order);

		btn_save = (Button) view.findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);
		// 初始化默认值
		strStyle = sp.getString(ConfigEntity.StyleSeparatorKey,"");
		strStyleOrder = sp.getString(ConfigEntity.StyleSeparatorOrderKey, "");
		strColor = sp.getString(ConfigEntity.ColorSeparatorKey,"");
		strColorOrder = sp.getString(ConfigEntity.ColorSeparatorOrderKey,"");
		strSize = sp.getString(ConfigEntity.SizeSeparatorKey,"");
		strSizeOrder = sp.getString(ConfigEntity.SizeSeparatorOrderKey, "");
		et_style_separator.setText(strStyle);
		et_color_separator.setText(strColor);
		et_size_separator.setText(strSize);
		tv_style_separator_order.setText(strStyleOrder);
		tv_color_separator_order.setText(strColorOrder);
		tv_size_separator_order.setText(strSizeOrder);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			strStyle = et_style_separator.getText().toString();
			strColor = et_color_separator.getText().toString();
			strSize = et_size_separator.getText().toString();
			
			sp.edit().putString(ConfigEntity.StyleSeparatorKey, et_style_separator.getText().toString())
			.putString(ConfigEntity.StyleSeparatorOrderKey, tv_style_separator_order.getText().toString().trim())
			.putString(ConfigEntity.ColorSeparatorKey, et_color_separator.getText().toString())
			.putString(ConfigEntity.ColorSeparatorOrderKey, tv_color_separator_order.getText().toString())
			.putString(ConfigEntity.SizeSeparatorKey, et_size_separator.getText().toString())
			.putString(ConfigEntity.SizeSeparatorOrderKey, tv_size_separator_order.getText().toString()).commit();
			AlertUtil.showToast(AlertUtil.getString(R.string.saved_success), getActivity());
			break;
		}
	}

}
