package com.supoin.commoninventory.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.activity.BarCodeSplitByBitLengthActivity;
import com.supoin.commoninventory.activity.BarCodeSplitBySeparatorActivity;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.entity.BarSplitLenSet;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.GlobalRunConfig;

public class BarCodeSettingFragment extends Fragment implements OnClickListener {
	private Button btn_setting2, btn_setting3, btn_setting4, btn_setting5,
			btn_save;
	private RadioGroup mRadioGroup;
	private RadioButton mRadioButton1, mRadioButton2, mRadioButton3,
			mRadioButton4, mRadioButton5;
	private View view;
	private SharedPreferences sp;
	private String strSelect;
	private SQLStockDataSqlite sqlStockDataSqlite;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getActivity().getSharedPreferences("InvenConfig",
				getActivity().MODE_PRIVATE);
		sqlStockDataSqlite=new SQLStockDataSqlite(getActivity(), true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_barcode_setting, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		initViews(view);
		setData();
		return view;
	}

	private void initViews(View view) {
		mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
		mRadioButton1 = (RadioButton) view.findViewById(R.id.rbtn_1);
		mRadioButton2 = (RadioButton) view.findViewById(R.id.rbtn_2);
		mRadioButton3 = (RadioButton) view.findViewById(R.id.rbtn_3);
		mRadioButton4 = (RadioButton) view.findViewById(R.id.rbtn_4);
		mRadioButton5 = (RadioButton) view.findViewById(R.id.rbtn_5);
		btn_setting2 = (Button) view.findViewById(R.id.btn_setting2);
		btn_setting3 = (Button) view.findViewById(R.id.btn_setting3);
		btn_setting4 = (Button) view.findViewById(R.id.btn_setting4);
		btn_setting5 = (Button) view.findViewById(R.id.btn_setting5);
		btn_save = (Button) view.findViewById(R.id.btn_save);
	}

	private void setData() {
		strSelect = sp.getString(ConfigEntity.BarCodeCutSettingKey, ConfigEntity.BarCodeCutSetting);

		if (strSelect.equals("0")) {
			mRadioButton1.setChecked(true);
			btn_setting2.setEnabled(false);
			btn_setting3.setEnabled(false);
			btn_setting4.setEnabled(false);
			btn_setting5.setEnabled(false);
		} else if (strSelect.equals("1")) {
			mRadioButton2.setChecked(true);
			btn_setting3.setEnabled(false);
			btn_setting4.setEnabled(false);
			btn_setting5.setEnabled(false);
		} else if (strSelect.equals("2")) {
			mRadioButton3.setChecked(true);
			btn_setting2.setEnabled(false);
			btn_setting4.setEnabled(false);
			btn_setting5.setEnabled(false);
		} else if (strSelect.equals("3")) {
			mRadioButton4.setChecked(true);
			btn_setting2.setEnabled(false);
			btn_setting3.setEnabled(false);
			btn_setting5.setEnabled(false);
		} else if (strSelect.equals("4")) {
			mRadioButton5.setChecked(true);
			btn_setting2.setEnabled(false);
			btn_setting3.setEnabled(false);
			btn_setting4.setEnabled(false);
		}

		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rbtn_1:
					strSelect = "0";
					btn_setting2.setEnabled(false);
					btn_setting3.setEnabled(false);
					btn_setting4.setEnabled(false);
					btn_setting5.setEnabled(false);
					break;
				case R.id.rbtn_2:
					strSelect = "1";
					btn_setting2.setEnabled(true);
					btn_setting3.setEnabled(false);
					btn_setting4.setEnabled(false);
					btn_setting5.setEnabled(false);
					break;
				case R.id.rbtn_3:
					strSelect = "2";
					btn_setting2.setEnabled(false);
					btn_setting3.setEnabled(true);
					btn_setting4.setEnabled(false);
					btn_setting5.setEnabled(false);
					break;
				case R.id.rbtn_4:
					strSelect = "3";
					btn_setting2.setEnabled(false);
					btn_setting3.setEnabled(false);
					btn_setting4.setEnabled(true);
					btn_setting5.setEnabled(false);
					break;
				case R.id.rbtn_5:
					strSelect = "4";
					btn_setting2.setEnabled(false);
					btn_setting3.setEnabled(false);
					btn_setting4.setEnabled(false);
					btn_setting5.setEnabled(true);
					break;
				}

			}
		});

		btn_setting2.setOnClickListener(this);
		btn_setting3.setOnClickListener(this);
		btn_setting4.setOnClickListener(this);
		btn_setting5.setOnClickListener(this);
		btn_save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_setting2:
			// 款色码位长拆分
			 intent = new Intent(getActivity(),BarCodeSplitByBitLengthActivity.class);
			intent.putExtra("tag", "0");
			startActivity(intent);
			break;
		case R.id.btn_setting3:
			// 货号尺码位长拆分
			 intent = new Intent(getActivity(),
					BarCodeSplitByBitLengthActivity.class);
			intent.putExtra("tag", "1");
			startActivity(intent);
			break;
		case R.id.btn_setting4:
			// 款色码分隔符拆分
			 intent = new Intent(getActivity(),
					BarCodeSplitBySeparatorActivity.class);
			intent.putExtra("tag", "0");
			startActivity(intent);
			break;
		case R.id.btn_setting5:
			// 货号尺码分隔符拆分
			intent = new Intent(getActivity(),
					BarCodeSplitBySeparatorActivity.class);
			intent.putExtra("tag", "1");
			startActivity(intent);
			break;
		case R.id.btn_save:
			int iRet = 0;
			BarSplitLenSet barSplitLenSet = new BarSplitLenSet();
			if (mRadioButton1.isChecked() == true)
			{
				GlobalRunConfig.GetInstance().barSplitSet.iItem = barSplitLenSet.iItem = 5;
				iRet = sqlStockDataSqlite.UpdateBarSplitLenSetInUsage(barSplitLenSet);
			}
			sp.edit()
			.putString(ConfigEntity.BarCodeCutSettingKey, strSelect)
			.commit();
			if (iRet == 1)
				AlertUtil.showToast("保存该页配置成功。", getActivity());
			break;
		}
	}
}
