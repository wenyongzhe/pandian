package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.SpinnerAdapter;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.ToastUtils;
import com.supoin.commoninventory.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class MidifyPSWActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {
	private EditText etPwdOld;
	private EditText etPwdNew;
	private EditText etPwdVerification;
	private EditText etPwdTipsAnswer;
	private Spinner spinnerPasswordTips;
	private Button btnSave;

	private SpinnerAdapter spinnerAdapter;

	private SharedPreferences sp;

	private String[] arrTips;
	private String sPwdOld;
	private int iTipsPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(MidifyPSWActivity.this, R.string.modify_password, false);
		setContentView(R.layout.activity_modify_psw);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		init();
	}

	private void init() {
		etPwdOld = (EditText) findViewById(R.id.et_pwd_old);
		etPwdNew = (EditText) findViewById(R.id.et_pwd_new);
		etPwdVerification = (EditText) findViewById(R.id.et_pwd_new_verification);
		etPwdTipsAnswer = (EditText) findViewById(R.id.et_pwd_tips_answer);
		spinnerPasswordTips = (Spinner) findViewById(R.id.spinner_pwd_tips);
		btnSave = (Button) findViewById(R.id.btn_save);

		btnSave.setOnClickListener(this);

		arrTips = getResources().getStringArray(R.array.PasswordTips);
		initText();
		spinnerAdapter = new SpinnerAdapter(this, arrTips);
		spinnerPasswordTips.setAdapter(spinnerAdapter);


		sPwdOld = sp.getString(ConfigEntity.PwInitKey, ConfigEntity.PwInit);
		iTipsPosition = sp.getInt(ConfigEntity.PwTipsKey, ConfigEntity.PwTipInit);

		spinnerPasswordTips.setSelection(iTipsPosition);
	}
	/**动态修改提示*/
	private void initText() {
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		String shop = importStrArrayList.get(0);
		String language = getResources().getConfiguration().locale.getLanguage();
		if (language.endsWith("en")){
			arrTips[0] = "What is your "+shop;

		}else{
			arrTips[0] = "您的"+shop+"是多少？";
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}

		// TODO Auto-generated method stub
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		iTipsPosition = position;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	private void save() {
		String sPwdOld = etPwdOld.getText().toString().trim();
		String sPwdNew = etPwdNew.getText().toString().trim();
		String sPwdNewVerification = etPwdVerification.getText().toString()
				.trim();
		String sPwdTipsAnswer = etPwdTipsAnswer.getText().toString().trim();

		if (Utility.isEmpty(sPwdOld)) {
			ToastUtils.show(this, R.string.empty_password_old);
			return;
		}
		if (Utility.isEmpty(sPwdNew)) {
			ToastUtils.show(this, R.string.empty_password_new);
			return;
		}
		if (Utility.isEmpty(sPwdNewVerification)) {
			ToastUtils.show(this, R.string.empty_password_new);
			return;
		}
		if (!sPwdNew.equals(sPwdNewVerification)) {
			ToastUtils.show(this, R.string.password_inconformity);
			return;
		}
		if (Utility.isEmpty(sPwdTipsAnswer)) {
			ToastUtils.show(this, R.string.empty_password_answer);
			return;
		}
		if (!this.sPwdOld.equals(sPwdOld)) {
			ToastUtils.show(this, R.string.password_old_error);
			return;
		}

		boolean bSave = sp.edit().putString(ConfigEntity.PwInitKey, sPwdNew)
				.putInt(ConfigEntity.PwTipsKey, iTipsPosition)
				.putString(ConfigEntity.pwTipsAnswer, sPwdTipsAnswer)
				.commit();

		if (bSave) {
			this.sPwdOld = sPwdNew;

			AlertUtil.showToast(R.string.assword_modification_success, this);
		} else {
			AlertUtil.showToast(R.string.assword_modification_fail, this);
		}

	}

}
