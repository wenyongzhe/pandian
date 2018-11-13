package com.supoin.commoninventory.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.SpinnerAdapter;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.VerifyWay;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.GlobalRunConfig;

import java.util.ArrayList;
import java.util.List;

public class ScanSettingFragment extends Fragment implements OnClickListener {
	// private View view;
	private String[] str1 = { AlertUtil.getString(R.string.ssetting1), AlertUtil.getString(R.string.ssetting2), AlertUtil.getString(R.string.ssetting3) };
	private String[] str10 = { AlertUtil.getString(R.string.ssetting1), AlertUtil.getString(R.string.ssetting2) };
	private String[] str2 = { AlertUtil.getString(R.string.ssetting4), AlertUtil.getString(R.string.ssetting5) };
	private String[] str3 = { AlertUtil.getString(R.string.ssetting6), AlertUtil.getString(R.string.ssetting7) };
	private String[] str4 = { AlertUtil.getString(R.string.ssetting8), AlertUtil.getString(R.string.ssetting9) };
	private String[] str5 = { AlertUtil.getString(R.string.ssetting10), AlertUtil.getString(R.string.ssetting11) };
	private String[] str6 = { AlertUtil.getString(R.string.ssetting12), AlertUtil.getString(R.string.ssetting13) };
	private int index1, index2, index3, index4, index5, index6;
	private String str01, str02, str03, str04, str05, str06, strNum;
	private SpinnerAdapter adapter1, adapter2, adapter3, adapter4, adapter5,
			adapter6;
	private Spinner mSpinner1, mSpinner2, mSpinner3, mSpinner4, mSpinner5,
			mSpinner6;
	private Button btnSave;
	private LinearLayout llBarCodeAndLineNum;
	private String iRightLevel;
	private SharedPreferences sp;
	private SQLStockDataSqlite sqlStockDataSqlite;
	private EditText et_num;
	private LinearLayout ll_all,ll_scan_line;
	private String scanPattern;

	public ScanSettingFragment() {

	}
	@SuppressLint("ValidFragment")
	public ScanSettingFragment(String iRightLevel) {
		this.iRightLevel = iRightLevel;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getActivity().getSharedPreferences("InvenConfig",
				getActivity().MODE_PRIVATE);
		sqlStockDataSqlite = new SQLStockDataSqlite(getActivity(), true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_scan_setting, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		initViews(view);
		setData();
		btnSave.setOnClickListener(this);
		return view;
	}

	private void initViews(View view) {
		ll_all = (LinearLayout) view.findViewById(R.id.ll_all);
		ll_scan_line = (LinearLayout) view.findViewById(R.id.ll_scan_line);
		mSpinner1 = (Spinner) view.findViewById(R.id.spinner1);
		mSpinner2 = (Spinner) view.findViewById(R.id.spinner2);
		mSpinner3 = (Spinner) view.findViewById(R.id.spinner3);
		mSpinner4 = (Spinner) view.findViewById(R.id.spinner4);
		mSpinner5 = (Spinner) view.findViewById(R.id.spinner5);
		mSpinner6 = (Spinner) view.findViewById(R.id.spinner6);
		btnSave = (Button) view.findViewById(R.id.btn_save);
		et_num = (EditText) view.findViewById(R.id.et_num);

		llBarCodeAndLineNum = (LinearLayout) view
				.findViewById(R.id.ll_barcode_and_line_num);

		if (iRightLevel.equals("2")) {
			mSpinner6.setVisibility(View.VISIBLE);
			llBarCodeAndLineNum.setVisibility(View.VISIBLE);
		} else {
			mSpinner6.setVisibility(View.GONE);
			llBarCodeAndLineNum.setVisibility(View.GONE);
		}
		//RFID
		scanPattern = sp.getString(ConfigEntity.ScanPatternKey,
				ConfigEntity.ScanPattern);
		if(scanPattern.equals("1")){
			ll_all.setVisibility(View.GONE);
			ll_scan_line.setVisibility(View.GONE);
			str1 = str10;
		}

	}

	private void setData() {
		adapter1 = new SpinnerAdapter(getActivity(), str1);
		adapter2 = new SpinnerAdapter(getActivity(), str2);
		adapter3 = new SpinnerAdapter(getActivity(), str3);
		adapter4 = new SpinnerAdapter(getActivity(), str4);
		adapter5 = new SpinnerAdapter(getActivity(), str5);
		adapter6 = new SpinnerAdapter(getActivity(), str6);
		mSpinner1.setAdapter(adapter1);
		mSpinner2.setAdapter(adapter2);
		mSpinner3.setAdapter(adapter3);
		mSpinner4.setAdapter(adapter4);
		mSpinner5.setAdapter(adapter5);
		mSpinner6.setAdapter(adapter6);
		str01 = sp.getString(ConfigEntity.BarCodeAuthKey,
				ConfigEntity.BarCodeAuth);
		if(scanPattern.equals("1")){
			if(str01.equals("2")){
				str01 = "0";
			}
		}
		index1 = Integer.parseInt(str01);
		str02 = sp.getString(ConfigEntity.UsingBarCodeKey,
				ConfigEntity.UsingBarCode);
		index2 = Integer.parseInt(str02);
		str03 = sp.getString(ConfigEntity.IsPutInNumKey,
				ConfigEntity.IsPutInNum);
		index3 = Integer.parseInt(str03);
		str04 = sp.getString(ConfigEntity.ModifyNumPWKey,
				ConfigEntity.ModifyNumPW);
		index4 = Integer.parseInt(str04);
		str05 = sp.getString(ConfigEntity.ScanningShowModeKey,
				ConfigEntity.ScanningShowMode);
		index5 = Integer.parseInt(str05);
		str06 = sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode);
		index6 = Integer.parseInt(str06);
		strNum = sp.getString(ConfigEntity.ScanningShowLineNumbKey,
				ConfigEntity.ScanningShowLineNumb);
		// …Ë÷√ƒ¨»œ÷µ
		mSpinner1.setSelection(Integer.parseInt(str01), true);
		mSpinner2.setSelection(Integer.parseInt(str02), true);
		mSpinner3.setSelection(Integer.parseInt(str03), true);
		mSpinner4.setSelection(Integer.parseInt(str04), true);
		mSpinner5.setSelection(Integer.parseInt(str05), true);
		mSpinner6.setSelection(Integer.parseInt(str06), true);
		et_num.setText(strNum);
		et_num.selectAll();
		mSpinner1
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						str01 = arg2 + "";
						index1 = arg2;
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// myTextView.setText("NONE");
					}
				});
		mSpinner2
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						str02 = arg2 + "";
						index2 = arg2;
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// myTextView.setText("NONE");
					}
				});
		mSpinner3
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						str03 = arg2 + "";
						index3 = arg2;
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// myTextView.setText("NONE");
					}
				});
		mSpinner4
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						str04 = arg2 + "";
						index4 = arg2;
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// myTextView.setText("NONE");
					}
				});
		mSpinner5
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						str05 = arg2 + "";
						index5 = arg2;
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// myTextView.setText("NONE");
					}
				});
		mSpinner6
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						str06 = arg2 + "";
						index6 = arg2;
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// myTextView.setText("NONE");
					}
				});

		btnSave.setOnClickListener(this);
	}

	private void btnSave_Click() {
		strNum = et_num.getText().toString().trim();
		if (strNum.equals("")) {
			AlertUtil.showAlert(getActivity(), R.string.warning, R.string.ssetting14, R.string.ok,
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
			return;
		}
		if (Integer.parseInt(strNum) < 1) {
			AlertUtil.showAlert(getActivity(), R.string.warning, R.string.ssetting15, R.string.ok,
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
						}
					});
			return;
		}
		if (index2 == 1 && index3 == 0) {
			AlertUtil.showAlert(getActivity(), R.string.warning,
					R.string.ssetting16, R.string.ok,
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
						}
					});
			return;
		}
		List<VerifyWay> listVeri = new ArrayList<VerifyWay>();
		GlobalRunConfig.GetInstance().iBarVerifyWay = index1;
		listVeri.add(sqlStockDataSqlite.GetBarVerifyWayInUsageByIndex(10,
				index1));

		GlobalRunConfig.GetInstance().iSingleBarVerify = (index2 + 1) % 2;
		listVeri.add(sqlStockDataSqlite.GetBarVerifyWayInUsageByIndex(20,
				(index2 + 1) % 2));
		GlobalRunConfig.GetInstance().iScanDisplayWay = (index5 + 1) % 2;

		GlobalRunConfig.GetInstance().iDisplayRows = Integer.parseInt(strNum);
		VerifyWay enTmp = sqlStockDataSqlite.GetBarVerifyWayInUsageByIndex(30,
				(index5 + 1) % 2);
		enTmp.strValue = strNum;
		sqlStockDataSqlite.UpdateCurVerifyWay(enTmp);
		listVeri.add(enTmp);

		GlobalRunConfig.GetInstance().iScanInputQty = (index2 + 1) % 2;
		listVeri.add(sqlStockDataSqlite.GetBarVerifyWayInUsageByIndex(40,
				(index2 + 1) % 2));

		GlobalRunConfig.GetInstance().iBarOutIn = (index6 + 1) % 2;
		listVeri.add(sqlStockDataSqlite.GetBarVerifyWayInUsageByIndex(60,
				(index6 + 1) % 2));

		int iRet = 0;
		iRet = sqlStockDataSqlite.UpdateVerifyWayInUsage(0, listVeri);

		if (et_num.getText().toString().trim() == null
				|| et_num.getText().toString().trim().equals("")) {
			AlertUtil.showAlert(getActivity(), AlertUtil.getString(R.string.dialog_title), AlertUtil.getString(R.string.ssetting17));
		} else {
			sp.edit()
					.putString(ConfigEntity.BarCodeAuthKey, str01)
					.putString(ConfigEntity.UsingBarCodeKey, str02)
					.putString(ConfigEntity.IsPutInNumKey, str03)
					.putString(ConfigEntity.ModifyNumPWKey, str04)
					.putString(ConfigEntity.ScanningShowModeKey, str05)
					.putString(ConfigEntity.InOutCodeKey, str06)
					.putString(ConfigEntity.ScanningShowLineNumbKey,
							et_num.getText().toString().trim()).commit();
			if (iRet == 1)
				AlertUtil.showToast(AlertUtil.getString(R.string.saved_success), getActivity());
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_save:
			btnSave_Click();
			break;
		}

	}

}
