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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.activity.GuanJiaPoSettingActivity;
import com.supoin.commoninventory.activity.xunErSettingActivity;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.util.AlertUtil;

public class ERPSettingFragment extends Fragment {
	private SharedPreferences sp;
	private Button btn_save;
	private Button back;
	private TextView tv_no_setting, tv_xuner, tv_gunjiapo;
	private CheckBox cb_no_setting, cb_xuner, cb_gunjiapo;
	private String strERPSetting;

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
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_erp_setting0, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);

		btn_save = (Button) view.findViewById(R.id.btn_save);
		tv_no_setting = (TextView) view.findViewById(R.id.tv_no_setting);
		tv_xuner = (TextView) view.findViewById(R.id.tv_xuner);
		tv_gunjiapo = (TextView) view.findViewById(R.id.tv_gunjiapo);
		cb_no_setting = (CheckBox) view.findViewById(R.id.cb_no_setting);
		cb_xuner = (CheckBox) view.findViewById(R.id.cb_xuner);
		cb_gunjiapo = (CheckBox) view.findViewById(R.id.cb_gunjiapo);
		strERPSetting = sp.getString(ConfigEntity.ERPSettingKey,
				ConfigEntity.ERPSetting);
		if (strERPSetting.equals("0")) {
			cb_no_setting.setChecked(true);
			cb_xuner.setChecked(false);
			cb_gunjiapo.setChecked(false);
		} else if (strERPSetting.equals("1")) {
			cb_no_setting.setChecked(false);
			cb_xuner.setChecked(true);
			cb_gunjiapo.setChecked(false);

		} else if (strERPSetting.equals("2")) {
			cb_no_setting.setChecked(false);
			cb_xuner.setChecked(false);
			cb_gunjiapo.setChecked(true);
		}
		cb_no_setting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					strERPSetting = "0";
					cb_xuner.setChecked(false);
					cb_gunjiapo.setChecked(false);
				}
			}
		});
		cb_xuner.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					strERPSetting = "1";
					cb_no_setting.setChecked(false);
					cb_gunjiapo.setChecked(false);
				}
			}
		});
		cb_gunjiapo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					strERPSetting = "2";
					cb_no_setting.setChecked(false);
					cb_xuner.setChecked(false);
				}
			}
		});

		tv_xuner.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(getActivity(),
						xunErSettingActivity.class);
				getActivity().startActivity(intent1);
			}
		});
		tv_gunjiapo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent(getActivity(),
						GuanJiaPoSettingActivity.class);
				getActivity().startActivity(intent2);
			}
		});
		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sp.edit().putString(ConfigEntity.ERPSettingKey, strERPSetting)
						.commit();
				// 0为不设置,1为迅尔,2为管家婆
				AlertUtil.showAlert(getActivity(), "提示", "保存成功");
			}
		});
		return view;
	}
}
