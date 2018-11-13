package com.supoin.commoninventory.fragment;

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
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.SpinnerAdapter;
import com.supoin.commoninventory.db.SQLDataSqlite;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.StringUtils;

public class LengthSettingFragment extends Fragment implements OnClickListener {
	private View view;
	private String[] str1 = { AlertUtil.getString(R.string.length1), AlertUtil.getString(R.string.length2), AlertUtil.getString(R.string.length3) };
	private String[] str2 = { AlertUtil.getString(R.string.length4),AlertUtil.getString(R.string.length5), AlertUtil.getString(R.string.length6), AlertUtil.getString(R.string.length7),
			AlertUtil.getString(R.string.length8)};
	private String strSpinner01, strSpinner02;
	private int index01 = 0, index02 = 0;
	private String strLimitNumb1, strLimitNumb2, strLimitNumb3, strLimitNumb4,
			strLimitNumb5, strLimitNumb6;
	private String strLimitRangeMin, strLimitRangeMax, strCutOrKeepNum;
	private SpinnerAdapter adapter1, adapter2;
	private Spinner mSpinner1, mSpinner2;
	private SharedPreferences sp;
	private Button btn_save;
	private EditText tv_limit_numb1, tv_limit_numb2, tv_limit_numb3,
			tv_limit_numb4, tv_limit_numb5, tv_limit_numb6, tv_limit_range_min,
			tv_limit_range_max, tv_cut_or_keep_num;

	private TextView tv_cut_or_keep;
	private LinearLayout ll_length_limint, ll_limit_range, ll_cut_or_keep_num;
	private SQLDataSqlite sqlStockDataSqlite;
	private LinearLayout ll_all;
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
		view = inflater.inflate(R.layout.fragment_length_setting, null);
		// 铺满view
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		initViews(view);
		setData();
		return view;
	}

	private void initViews(View view) {
		ll_all = (LinearLayout) view.findViewById(R.id.ll_all);
		mSpinner1 = (Spinner) view.findViewById(R.id.spinner01);
		mSpinner2 = (Spinner) view.findViewById(R.id.spinner02);
		btn_save = (Button) view.findViewById(R.id.btn_save);
		tv_limit_numb1 = (EditText) view.findViewById(R.id.tv_limit_numb1);
		tv_limit_numb2 = (EditText) view.findViewById(R.id.tv_limit_numb2);
		tv_limit_numb3 = (EditText) view.findViewById(R.id.tv_limit_numb3);
		tv_limit_numb4 = (EditText) view.findViewById(R.id.tv_limit_numb4);
		tv_limit_numb5 = (EditText) view.findViewById(R.id.tv_limit_numb5);
		tv_limit_numb6 = (EditText) view.findViewById(R.id.tv_limit_numb6);
		tv_limit_range_min = (EditText) view
				.findViewById(R.id.tv_limit_range_min);
		tv_limit_range_max = (EditText) view
				.findViewById(R.id.tv_limit_range_max);
		tv_cut_or_keep_num = (EditText) view
				.findViewById(R.id.tv_cut_or_keep_num);
		ll_cut_or_keep_num = (LinearLayout) view
				.findViewById(R.id.ll_cut_or_keep_num);
		ll_length_limint = (LinearLayout) view
				.findViewById(R.id.ll_length_limint);
		ll_limit_range = (LinearLayout) view.findViewById(R.id.ll_limit_range);
		tv_cut_or_keep = (TextView) view.findViewById(R.id.tv_cut_or_keep);
		
		String scanPattern = sp.getString(ConfigEntity.ScanPatternKey,
				ConfigEntity.ScanPattern);
		if(scanPattern.equals("1")){
			ll_all.setVisibility(View.GONE);
		}
	}

	private void setData() {
		adapter1 = new SpinnerAdapter(getActivity(), str1);
		adapter2 = new SpinnerAdapter(getActivity(), str2);
		mSpinner1.setAdapter(adapter1);
		mSpinner2.setAdapter(adapter2);
		btn_save.setOnClickListener(this);
		// 设置默认值
		strLimitNumb1 = sp.getString(ConfigEntity.LengthEqualToLimit1Key,
				ConfigEntity.LengthEqualToLimit1);
		strLimitNumb2 = sp.getString(ConfigEntity.LengthEqualToLimit2Key,
				ConfigEntity.LengthEqualToLimit2);
		strLimitNumb3 = sp.getString(ConfigEntity.LengthEqualToLimit3Key,
				ConfigEntity.LengthEqualToLimit3);
		strLimitNumb4 = sp.getString(ConfigEntity.LengthEqualToLimit4Key,
				ConfigEntity.LengthEqualToLimit4);
		strLimitNumb5 = sp.getString(ConfigEntity.LengthEqualToLimit5Key,
				ConfigEntity.LengthEqualToLimit5);
		strLimitNumb6 = sp.getString(ConfigEntity.LengthEqualToLimit6Key,
				ConfigEntity.LengthEqualToLimit6);

		strLimitRangeMin = sp.getString(ConfigEntity.LengthLimitRangeMinKey,
				ConfigEntity.LengthLimitRangeMin);
		strLimitRangeMax = sp.getString(ConfigEntity.LengthLimitRangeMaxKey,
				ConfigEntity.LengthLimitRangeMax);
		strCutOrKeepNum = sp.getString(
				ConfigEntity.LengthCutOrKeepBarCodeNumKey,
				ConfigEntity.LengthCutOrKeepBarCodeNum);
		strSpinner01 = sp.getString(ConfigEntity.LengthLimitKey,
				ConfigEntity.LengthLimit);
		strSpinner02 = sp.getString(ConfigEntity.LengthCutOrKeepBarCodeKey,
				ConfigEntity.LengthCutOrKeepBarCode);
		if (strSpinner01 != null) {
			index01 = Integer.parseInt(strSpinner01);
		}

		index02 = Integer.parseInt(strSpinner02);
		mSpinner1.setSelection(Integer.parseInt(strSpinner01), true);
		mSpinner2.setSelection(Integer.parseInt(strSpinner02), true);

		if (strSpinner01.equals("0")) {
			ll_length_limint.setVisibility(View.GONE);
			ll_limit_range.setVisibility(View.GONE);
		} else if (strSpinner01.equals("1")) {
			ll_length_limint.setVisibility(View.VISIBLE);
			ll_limit_range.setVisibility(View.GONE);
			tv_limit_numb1.setText(strLimitNumb1);
			tv_limit_numb2.setText(strLimitNumb2);
			tv_limit_numb3.setText(strLimitNumb3);
			tv_limit_numb4.setText(strLimitNumb4);
			tv_limit_numb5.setText(strLimitNumb5);
			tv_limit_numb6.setText(strLimitNumb6);
		} else if (strSpinner01.equals("2")) {
			ll_length_limint.setVisibility(View.GONE);
			ll_limit_range.setVisibility(View.VISIBLE);

			tv_limit_range_min.setText(strLimitRangeMin);
			tv_limit_range_max.setText(strLimitRangeMax);
		}

		if (strSpinner02.equals("0")) {
			ll_cut_or_keep_num.setVisibility(View.GONE);
		} else if (strSpinner02.equals("1") || strSpinner02.equals("2")) {
			tv_cut_or_keep.setText(AlertUtil.getString(R.string.length9));
			tv_cut_or_keep_num.setText(strCutOrKeepNum);
		} else if (strSpinner02.equals("3") || strSpinner02.equals("4")) {
			tv_cut_or_keep.setText(AlertUtil.getString(R.string.length10));
			tv_cut_or_keep_num.setText(strCutOrKeepNum);
		}

		mSpinner1
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						index01 = arg2;
						strSpinner01 = arg2 + "";
						if (strSpinner01.equals("0")) {
							// 等于范围 范围限制 隐藏
							ll_length_limint.setVisibility(View.GONE);
							ll_limit_range.setVisibility(View.GONE);

						} else if (strSpinner01.equals("1")) {
							// 等于范围显示 范围限制隐藏
							ll_length_limint.setVisibility(View.VISIBLE);
							ll_limit_range.setVisibility(View.GONE);
							tv_limit_numb1.setText(strLimitNumb1);
							tv_limit_numb2.setText(strLimitNumb2);
							tv_limit_numb3.setText(strLimitNumb3);
							tv_limit_numb4.setText(strLimitNumb4);
							tv_limit_numb5.setText(strLimitNumb5);
							tv_limit_numb6.setText(strLimitNumb6);
						} else if (strSpinner01.equals("2")) {
							// 等于范围隐藏 范围限制显示
							ll_length_limint.setVisibility(View.GONE);
							ll_limit_range.setVisibility(View.VISIBLE);
							tv_limit_range_min.setText(strLimitRangeMin);
							tv_limit_range_max.setText(strLimitRangeMax);
						}

					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		mSpinner2
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						strSpinner02 = arg2 + "";
						index02 = arg2;
						if (strSpinner02.equals("0")) {
							ll_cut_or_keep_num.setVisibility(View.GONE);
						} else if (strSpinner02.equals("1")
								|| strSpinner02.equals("2")) {
							tv_cut_or_keep.setText(AlertUtil.getString(R.string.length9));
							tv_cut_or_keep_num.setText(strCutOrKeepNum);
							ll_cut_or_keep_num.setVisibility(View.VISIBLE);
						} else if (strSpinner02.equals("3")
								|| strSpinner02.equals("4")) {
							tv_cut_or_keep.setText(AlertUtil.getString(R.string.length10));
							tv_cut_or_keep_num.setText(strCutOrKeepNum);
							ll_cut_or_keep_num.setVisibility(View.VISIBLE);
						}
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		btn_save.setOnClickListener(this);
	}

	/*
	 * private void btnSave_Click() { GlobalRunConfig.GetInstance().iCurTailWay
	 * = index02; GlobalRunConfig.GetInstance().iCurTailLen =
	 * Integer.parseInt(strCutOrKeepNum);
	 * 
	 * if (GlobalRunConfig.GetInstance().iCurTailWay > 0 &&
	 * GlobalRunConfig.GetInstance().iCurTailWay <= 2 &&
	 * GlobalRunConfig.GetInstance().iCurTailLen < 1) {
	 * AlertUtil.showAlert(getActivity(), R.string.warning, "选择了截取长度，截取长度必须大于0,请确认检查。" +
	 * "\r\n\r\n如果不需要截取，请选择第一项“不处理”。", R.string.ok, new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub AlertUtil.dismissDialog(); } }); return; } else if
	 * (GlobalRunConfig.GetInstance().iCurTailWay > 2 &&
	 * GlobalRunConfig.GetInstance().iCurTailLen < 1) {
	 * AlertUtil.showAlert(getActivity(), R.string.warning, "选择了保留长度，保留长度必须大于0,请确认检查。" +
	 * "\r\n\r\n如果不需要保留，请选择第一项“不处理”。", R.string.ok, new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub AlertUtil.dismissDialog(); } }); return; } List<VerifyWay> listVeri
	 * = new ArrayList<VerifyWay>();
	 * 
	 * listVeri.add(sqlStockDataSqlite.GetBarVerifyWayInUsageByIndex(50,
	 * index02)); VerifyWay enTmp =
	 * sqlStockDataSqlite.GetBarVerifyWayInUsageByIndex(50, 0); enTmp.strValue =
	 * strCutOrKeepNum; sqlStockDataSqlite.UpdateCurVerifyWay(enTmp);
	 * listVeri.add(enTmp); sqlStockDataSqlite.UpdateVerifyWayInUsage(1,
	 * listVeri); LengthSet lengthSet = new LengthSet();
	 * 
	 * GlobalRunConfig.GetInstance().lengthSet.iItem = lengthSet.iItem = index01
	 * + 1;
	 * 
	 * if (index01 == 1) { GlobalRunConfig.GetInstance().lengthSet.iLen1 =
	 * lengthSet.iLen1 = Integer .parseInt(strLimitNumb1);
	 * GlobalRunConfig.GetInstance().lengthSet.iLen2 = lengthSet.iLen2 = Integer
	 * .parseInt(strLimitNumb2); GlobalRunConfig.GetInstance().lengthSet.iLen3 =
	 * lengthSet.iLen3 = Integer .parseInt(strLimitNumb3);
	 * GlobalRunConfig.GetInstance().lengthSet.iLen4 = lengthSet.iLen4 = Integer
	 * .parseInt(strLimitNumb4); GlobalRunConfig.GetInstance().lengthSet.iLen5 =
	 * lengthSet.iLen5 = Integer .parseInt(strLimitNumb5);
	 * GlobalRunConfig.GetInstance().lengthSet.iLen6 = lengthSet.iLen6 = Integer
	 * .parseInt(strLimitNumb6);
	 * 
	 * if ((GlobalRunConfig.GetInstance().lengthSet.iLen1 +
	 * GlobalRunConfig.GetInstance().lengthSet.iLen2 +
	 * GlobalRunConfig.GetInstance().lengthSet.iLen3 +
	 * GlobalRunConfig.GetInstance().lengthSet.iLen4 +
	 * GlobalRunConfig.GetInstance().lengthSet.iLen5 + GlobalRunConfig
	 * .GetInstance().lengthSet.iLen6) < 1) { AlertUtil.showAlert(getActivity(),
	 * R.string.warning, "所有长度不能同时都为零，这样会无法进行扫描,请确认检查。", R.string.ok, new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub AlertUtil.dismissDialog(); } });
	 * 
	 * return; } } else if (index01 == 2) {
	 * GlobalRunConfig.GetInstance().lengthSet.iLenMin = lengthSet.iLenMin =
	 * Integer .parseInt(strLimitRangeMin);
	 * GlobalRunConfig.GetInstance().lengthSet.iLenMax = lengthSet.iLenMax =
	 * Integer .parseInt(strLimitRangeMax);
	 * 
	 * if (GlobalRunConfig.GetInstance().lengthSet.iLenMin > GlobalRunConfig
	 * .GetInstance().lengthSet.iLenMax) { AlertUtil.showAlert(getActivity(),
	 * R.string.warning, "小值不能大于大值，这样会无法进行扫描,请确认检查。", R.string.ok, new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub AlertUtil.dismissDialog(); } });
	 * 
	 * return; } else if (GlobalRunConfig.GetInstance().lengthSet.iLenMin ==
	 * GlobalRunConfig .GetInstance().lengthSet.iLenMax &&
	 * GlobalRunConfig.GetInstance().lengthSet.iLenMin == 0) {
	 * AlertUtil.showAlert(getActivity(), R.string.warning, "小值和大值不能同时为0，这样会无法进行扫描,请确认检查。",
	 * R.string.ok, new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub AlertUtil.dismissDialog(); } });
	 * 
	 * return; } }
	 * 
	 * int iRet = 0; iRet =
	 * sqlStockDataSqlite.UpdateLengthSetInUsage1(lengthSet); if
	 * (strSpinner01.equals("0")) {
	 * sp.edit().putString(ConfigEntity.LengthLimitKey, strSpinner01) .commit();
	 * } else if (strSpinner01.equals("1")) { sp.edit()
	 * .putString(ConfigEntity.LengthLimitKey, strSpinner01)
	 * .putString(ConfigEntity.LengthEqualToLimit1Key,strLimitNumb1)
	 * .putString(ConfigEntity.LengthEqualToLimit2Key,strLimitNumb2)
	 * .putString(ConfigEntity.LengthEqualToLimit3Key,strLimitNumb3)
	 * .putString(ConfigEntity.LengthEqualToLimit4Key,strLimitNumb4)
	 * .putString(ConfigEntity.LengthEqualToLimit5Key,strLimitNumb5)
	 * .putString(ConfigEntity.LengthEqualToLimit6Key,strLimitNumb6).commit(); }
	 * else if (strSpinner01.equals("2")) {
	 * sp.edit().putString(ConfigEntity.LengthLimitKey,
	 * strSpinner01).putString(ConfigEntity
	 * .LengthLimitRangeMinKey,tv_limit_range_min.getText().toString().trim())
	 * .putString
	 * (ConfigEntity.LengthLimitRangeMaxKey,tv_limit_range_max.getText(
	 * ).toString().trim()).commit(); } if (strSpinner02.equals("0")) {
	 * sp.edit()
	 * .putString(ConfigEntity.LengthCutOrKeepBarCodeKey,strSpinner02).commit();
	 * } else {
	 * sp.edit().putString(ConfigEntity.LengthCutOrKeepBarCodeKey,strSpinner02)
	 * .putString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,tv_cut_or_keep_num.
	 * getText().toString().trim()).commit(); } if (iRet == 1) {
	 * AlertUtil.showToast("保存该页配置成功。", getActivity()); } }
	 */

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			// btnSave_Click();
			btnSave_Click0();

			break;
		}
	}

	private void btnSave_Click0() {
		strLimitNumb1 = tv_limit_numb1.getText().toString();
		strLimitNumb2 = tv_limit_numb2.getText().toString();
		strLimitNumb3 = tv_limit_numb3.getText().toString();
		strLimitNumb4 = tv_limit_numb4.getText().toString();
		strLimitNumb5 = tv_limit_numb5.getText().toString();
		strLimitNumb6 = tv_limit_numb6.getText().toString();
		strLimitRangeMin = tv_limit_range_min.getText().toString();
		strLimitRangeMax = tv_limit_range_max.getText().toString();
		strCutOrKeepNum = tv_cut_or_keep_num.getText().toString();

		if (strSpinner01.equals("1")) {
			if (StringUtils.isBlank(strLimitNumb1)
					|| StringUtils.isBlank(strLimitNumb2)
					|| StringUtils.isBlank(strLimitNumb3)
					|| StringUtils.isBlank(strLimitNumb4)
					|| StringUtils.isBlank(strLimitNumb5)
					|| StringUtils.isBlank(strLimitNumb6)) {
				AlertUtil.showAlert(getActivity(), R.string.warning,
						AlertUtil.getString(R.string.length11), R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});

				return;
			}
			if (Integer.parseInt(strLimitNumb1)
					+ Integer.parseInt(strLimitNumb2)
					+ Integer.parseInt(strLimitNumb3)
					+ Integer.parseInt(strLimitNumb4)
					+ Integer.parseInt(strLimitNumb5)
					+ Integer.parseInt(strLimitNumb6) == 0) {
				AlertUtil.showAlert(getActivity(), R.string.warning,
						AlertUtil.getString(R.string.length12), R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});

				return;
			}
		} else if (strSpinner01.equals("2")) {
			if (StringUtils.isBlank(strLimitRangeMin)
					|| StringUtils.isBlank(strLimitRangeMax)) {
				AlertUtil.showAlert(getActivity(), R.string.warning,
						AlertUtil.getString(R.string.length13), R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});

				return;
			}
			if (Integer.parseInt(strLimitRangeMin) > Integer
					.parseInt(strLimitRangeMax)) {

				AlertUtil.showAlert(getActivity(), R.string.warning,
						AlertUtil.getString(R.string.length14), R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});

				return;

			} else if (Integer.parseInt(strLimitRangeMin) == Integer
					.parseInt(strLimitRangeMax)
					&& Integer.parseInt(strLimitRangeMin) == 0) {
				AlertUtil.showAlert(getActivity(), R.string.warning,
						AlertUtil.getString(R.string.length15), R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});

				return;

			}

		}

		if (Integer.parseInt(strSpinner02) > 0) {
			if (StringUtils.isBlank(strCutOrKeepNum)) {
				AlertUtil.showAlert(getActivity(), R.string.warning,
						AlertUtil.getString(R.string.length16), R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});

				return;
			}
			if (Integer.parseInt(strSpinner02) > 0
					&& Integer.parseInt(strSpinner02) <= 2
					&& Integer.parseInt(strCutOrKeepNum) < 1) {
				AlertUtil.showAlert(getActivity(), R.string.warning,
						AlertUtil.getString(R.string.length17)
								+ AlertUtil.getString(R.string.length18), R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});
				return;

			} else if (Integer.parseInt(strSpinner02) > 2 && Integer.parseInt(strCutOrKeepNum) < 1) {

				AlertUtil.showAlert(getActivity(), R.string.warning,
						AlertUtil.getString(R.string.length19)
								+ AlertUtil.getString(R.string.length20), R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});
				return;
			}
		}

		if (strSpinner01.equals("0")) {
			sp.edit().putString(ConfigEntity.LengthLimitKey, strSpinner01)
					.commit();
		} else if (strSpinner01.equals("1")) {
			sp.edit()
					.putString(ConfigEntity.LengthLimitKey, "1")
					.putString(ConfigEntity.LengthEqualToLimit1Key,
							tv_limit_numb1.getText().toString().trim())
					.putString(ConfigEntity.LengthEqualToLimit2Key,
							tv_limit_numb2.getText().toString().trim())
					.putString(ConfigEntity.LengthEqualToLimit3Key,
							tv_limit_numb3.getText().toString().trim())
					.putString(ConfigEntity.LengthEqualToLimit4Key,
							tv_limit_numb4.getText().toString().trim())
					.putString(ConfigEntity.LengthEqualToLimit5Key,
							tv_limit_numb5.getText().toString().trim())
					.putString(ConfigEntity.LengthEqualToLimit6Key,
							tv_limit_numb6.getText().toString().trim())
					.commit();
		} else if (strSpinner01.equals("2")) {
			sp.edit()
					.putString(ConfigEntity.LengthLimitKey, strSpinner01)
					.putString(ConfigEntity.LengthLimitRangeMinKey,
							tv_limit_range_min.getText().toString().trim())
					.putString(ConfigEntity.LengthLimitRangeMaxKey,
							tv_limit_range_max.getText().toString().trim())
					.commit();
		}
		if (strSpinner02.equals("0")) {
			sp.edit()
					.putString(ConfigEntity.LengthCutOrKeepBarCodeKey,
							strSpinner02).commit();
		} else {
			sp.edit()
					.putString(ConfigEntity.LengthCutOrKeepBarCodeKey,
							strSpinner02)
					.putString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,
							tv_cut_or_keep_num.getText().toString().trim())
					.commit();
		}
		AlertUtil.showToast(AlertUtil.getString(R.string.length21), getActivity());
	}
}
