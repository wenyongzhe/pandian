package com.supoin.commoninventory.fragment;

import android.app.Fragment;
import android.content.Intent;
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
import com.supoin.commoninventory.activity.ImAndExFieldSettingActivity;
import com.supoin.commoninventory.activity.MidifyPSWActivity;
import com.supoin.commoninventory.adapter.SpinnerAdapter;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 其他设置
 * @date 2017-6-2 上午10:29:20 
 *
 */
public class OtherSettingFragment extends Fragment {
	private EditText et_store_number, et_customer_name;
	private String str01, str02, str03, str04, str05, str06;
	private String strStoreNumber, strCustomerName;
	private TextView tv_shopname;
	private String[] str1 = { AlertUtil.getString(R.string.other1), AlertUtil.getString(R.string.other2) };
	private String[] str2 = { AlertUtil.getString(R.string.other3), AlertUtil.getString(R.string.other4) };
	private String[] str3 = { AlertUtil.getString(R.string.other5), AlertUtil.getString(R.string.other6) };
	private String[] str4 = { AlertUtil.getString(R.string.other7), AlertUtil.getString(R.string.other8) };
	//private String[] str5 = { "不允许", "允许(1位小数)", "允许(2位小数)"};
	private String[] str6 = { AlertUtil.getString(R.string.other9), AlertUtil.getString(R.string.other10) };

	private Button btn_change_password, btn_save, btn_setting_system_time;

	private SpinnerAdapter adapter1, adapter2, adapter3, adapter4, adapter5,
			adapter6;
	private Spinner mSpinner1, mSpinner2, mSpinner3, mSpinner4, mSpinner5,
			mSpinner6;
	private LinearLayout llCustomer,ll_import_export_field_setting;
	//private LinearLayout llDecimals;
	private LinearLayout llExitPwd;
	private String iRightLevel;
	private SharedPreferences sp;
	private View view_import_export_field_setting,view_customer_name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getActivity().getSharedPreferences("InvenConfig",
				getActivity().MODE_PRIVATE);

	}

	@Override
	public void onResume() {
		super.onResume();
		initText();
	}

	/**动态修改提示*/
	private void initText() {
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		String bianhao = importStrArrayList.get(1);
		String huowei = importStrArrayList.get(2);
		String language = getResources().getConfiguration().locale.getLanguage();
        if (language.endsWith("en")){
        	str3[0] = AlertUtil.getString(R.string.delet_by)+bianhao;
    		str3[1] = AlertUtil.getString(R.string.delet_by)+bianhao +"and "+huowei;
			tv_shopname.setText(AlertUtil.getString(R.string.setting)+" "+importStrArrayList.get(0));
        }else{
        	str3[0] = "按"+bianhao+"删除";
    		str3[1] = "按"+bianhao+","+huowei+"删除";
			tv_shopname.setText(AlertUtil.getString(R.string.setting)+importStrArrayList.get(0));
        }
		setSpinner();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_other_setting, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		iRightLevel = getArguments().getString("iRightLevel");
		btn_change_password = (Button) view
				.findViewById(R.id.btn_change_password);
		et_customer_name = (EditText) view.findViewById(R.id.et_customer_name);
		et_store_number = (EditText) view.findViewById(R.id.et_store_number);
		mSpinner1 = (Spinner) view.findViewById(R.id.spinner1);
		mSpinner2 = (Spinner) view.findViewById(R.id.spinner2);
		mSpinner3 = (Spinner) view.findViewById(R.id.spinner3);
		mSpinner4 = (Spinner) view.findViewById(R.id.spinner4);
		mSpinner6 = (Spinner) view.findViewById(R.id.spinner6);
		btn_save = (Button) view.findViewById(R.id.btn_save);
		tv_shopname = (TextView) view.findViewById(R.id.tv_shopname);
		view_import_export_field_setting = (View) view.findViewById(R.id.view_import_export_field_setting);
		view_customer_name = (View) view.findViewById(R.id.view_customer_name);

		llCustomer = (LinearLayout) view.findViewById(R.id.ll_customer_name);
		ll_import_export_field_setting = (LinearLayout) view.findViewById(R.id.ll_import_export_field_setting);
		ll_import_export_field_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(),
						ImAndExFieldSettingActivity.class);
				intent.putExtra("tag", "1");
				startActivity(intent);
			}
		});
		llExitPwd = (LinearLayout) view.findViewById(R.id.ll_exit_pwd);
		if (iRightLevel.equals("2")) {
			llCustomer.setVisibility(View.VISIBLE);
			llExitPwd.setVisibility(View.VISIBLE);
		}else{
			// 非超级管理员
			llCustomer.setVisibility(View.GONE);
			llExitPwd.setVisibility(View.GONE);
			ll_import_export_field_setting.setVisibility(View.GONE);
			view_import_export_field_setting.setVisibility(View.GONE);
			view_customer_name.setVisibility(View.GONE);
		}

		mSpinner1
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						str01 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		mSpinner2
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						str02 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		mSpinner3
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						str03 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		mSpinner4
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						str04 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		mSpinner6
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						str06 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		btn_change_password.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						MidifyPSWActivity.class);
				startActivity(intent);
			}
		});
		btn_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				strStoreNumber = et_store_number.getText().toString();
				strCustomerName = et_customer_name.getText().toString();
				sp.edit().putString(ConfigEntity.ShopIDKey, strStoreNumber)
						.putString(ConfigEntity.ShopNameKey, strCustomerName)
						.putString(ConfigEntity.ShowSettingTimeKey, str01)
						.putString(ConfigEntity.IsCloseWIFIKey, str02)
						.putString(ConfigEntity.DeleteDataWayKey, str03)
						.putString(ConfigEntity.DeleteDataPSWKey, str04)
						.putString(ConfigEntity.IsDataDecimalPointKey, str05)
						.putString(ConfigEntity.ExitSystemPSWKey, str06)
						.commit();
				AlertUtil.showToast(AlertUtil.getString(R.string.saved_success), getActivity());

				LogUtil.e(getTag(), str03);
			}
		});

		return view;
	}

	void setSpinner() {
		adapter1 = new SpinnerAdapter(getActivity(), str1);
		adapter2 = new SpinnerAdapter(getActivity(), str2);
		adapter3 = new SpinnerAdapter(getActivity(), str3);
		adapter4 = new SpinnerAdapter(getActivity(), str4);
		adapter6 = new SpinnerAdapter(getActivity(), str6);
		mSpinner1.setAdapter(adapter1);
		mSpinner2.setAdapter(adapter2);
		mSpinner3.setAdapter(adapter3);
		mSpinner4.setAdapter(adapter4);
		mSpinner6.setAdapter(adapter6);
		// 设置默认值
		strStoreNumber = sp.getString(ConfigEntity.ShopIDKey, ConfigEntity.ShopID);
		strCustomerName = sp.getString(ConfigEntity.ShopNameKey, ConfigEntity.ShopName);
		str01 = sp.getString(ConfigEntity.ShowSettingTimeKey, ConfigEntity.ShowSettingTime);
		str02 = sp.getString(ConfigEntity.IsCloseWIFIKey, ConfigEntity.IsCloseWIFI);
		str03 = sp.getString(ConfigEntity.DeleteDataWayKey,ConfigEntity.DeleteDataWay);
		str04 = sp.getString(ConfigEntity.DeleteDataPSWKey,ConfigEntity.DeleteDataPSW);
		str05 = sp.getString(ConfigEntity.IsDataDecimalPointKey, ConfigEntity.IsDataDecimalPoint);
		str06 = sp.getString(ConfigEntity.ExitSystemPSWKey, ConfigEntity.ExitSystemPSW);
		et_store_number.setText(strStoreNumber);
		et_customer_name.selectAll();
		et_customer_name.setText(strCustomerName);
		mSpinner1.setSelection(Integer.parseInt(str01), true);
		mSpinner2.setSelection(Integer.parseInt(str02), true);
		mSpinner3.setSelection(Integer.parseInt(str03), true);
		mSpinner4.setSelection(Integer.parseInt(str04), true);
		mSpinner6.setSelection(Integer.parseInt(str06), true);
	}

}
