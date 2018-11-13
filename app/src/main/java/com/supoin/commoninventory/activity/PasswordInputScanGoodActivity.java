package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.Constants;
import com.supoin.commoninventory.util.GlobalRunConfig;
import com.supoin.commoninventory.util.Utility;

public class PasswordInputScanGoodActivity extends PasswordActivity{

	@Override
	public void onKeyDownOk() {
		// TODO Auto-generated method stub
		GoodsDetailsActivity.is_intercept = true;
		String passWord = et_goodsBar.getText().toString().trim();
		String initPsw = sp.getString(ConfigEntity.PwInitKey,
				ConfigEntity.PwInit);

		if (Utility.isEmpty(initPsw)) {
			initPsw = ConfigEntity.PwInit;
		}

		if (TextUtils.isEmpty(passWord)) {
			AlertUtil.showAlert(this,
					R.string.dialog_title, R.string.password_empty,
					R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
						}
					});
			return;
		}

		// 系统更新
		if (Constants.VALUE_PASSWORD_SYSTEM_UPDATE.equals(sType)) {
			if (initPsw.equals(passWord)) {
				Intent intent = new Intent(this,
						UpdateActivity.class);
				setResult(Activity.RESULT_OK, intent);
				finish();
			} else {
				AlertUtil.showToast(
						AlertUtil.getString(R.string.password_error), this);
			}
			return;
		}

		if (ADMINPSW.equals(passWord)) {
			/**
			 * 
			AlertUtil.showAlert(this,
					R.string.dialog_title, R.string.enter_administrator_scan_good,
					R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
							// GlobalRunConfig.GetInstance().iRightLevel = 2;
							Intent intent = new Intent(PasswordInputScanGoodActivity.this,StockMenuActivity.class);
							intent.putExtra("iRightLevel", "2");
							setResult(Activity.RESULT_OK, intent);
							finish();
						}
					});
			 */
			Intent intent = new Intent(PasswordInputScanGoodActivity.this,StockMenuActivity.class);
			intent.putExtra("iRightLevel", "2");
			setResult(Activity.RESULT_OK, intent);
			finish();
			
		} else if (initPsw.equals(passWord)) {
			GlobalRunConfig.GetInstance().iRightLevel = 0;
			Intent intent = new Intent(this,
					StockMenuActivity.class);
			intent.putExtra("iRightLevel", "0");
			setResult(Activity.RESULT_OK, intent);
			finish();
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		} else {
			AlertUtil.showToast(R.string.password_error, this);
		}
	}

}
