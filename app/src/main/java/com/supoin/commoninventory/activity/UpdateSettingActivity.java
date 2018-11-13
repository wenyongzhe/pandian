package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.LogUtil;
import com.supoin.commoninventory.util.ToastUtils;
import com.supoin.commoninventory.util.Utility;

public class UpdateSettingActivity extends Activity implements OnClickListener {
	private TextView tvVersion;
	private EditText etUrl;
	private EditText etEnterpriseId;
	private CheckBox cbPrompt;
	private Button btnSave;

	private String sUrl;
	private String sEnterpriseId;
	private boolean bUpdatePrompt = false;

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(this,
				getResources().getString(R.string.update_setting), false);
		setContentView(R.layout.activity_update_setting);

		init();
	}

	private void init() {
		tvVersion = (TextView) findViewById(R.id.tv_version);
		etUrl = (EditText) findViewById(R.id.et_update_url);
		etEnterpriseId = (EditText) findViewById(R.id.et_enterprise_id);
		cbPrompt = (CheckBox) findViewById(R.id.cb_update_prompt);
		btnSave = (Button) findViewById(R.id.btn_save);

		btnSave.setOnClickListener(this);

		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		sUrl = sp.getString(ConfigEntity.SYSTEM_UPDATE_URL, "");
		sEnterpriseId = sp.getString(ConfigEntity.SYSTEM_UPDATE_ENTERPRISE_ID,"");
		bUpdatePrompt = sp.getBoolean(ConfigEntity.SYSTEM_UPDATE_PROMPT, false);
		
		tvVersion.setText(Utility.getAppVersionName(this));
		etUrl.setText(sUrl);
		etEnterpriseId.setText(sEnterpriseId);
		cbPrompt.setChecked(bUpdatePrompt);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_save:
			save();
			break;
		}
	}

	private void save() {
		sUrl = etUrl.getText().toString().trim();
		sEnterpriseId = etEnterpriseId.getText().toString().trim();

		if (Utility.isEmpty(sUrl)) {
			ToastUtils.show(this, R.string.empty_url);
			return;
		}

		if (Utility.isEmpty(sEnterpriseId)) {
			sEnterpriseId = "";
		}
		
		LogUtil.i(sUrl + " " + sEnterpriseId);

		sp.edit()
				.putString(ConfigEntity.SYSTEM_UPDATE_URL, sUrl)
				.putString(ConfigEntity.SYSTEM_UPDATE_ENTERPRISE_ID,
						sEnterpriseId)
				.putBoolean(ConfigEntity.SYSTEM_UPDATE_PROMPT,
						cbPrompt.isChecked()).commit();

		AlertUtil.showToast(AlertUtil.getString(R.string.saved_success), this);
	}

}
