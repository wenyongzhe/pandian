package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.db.SQLDataSqlite;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.Utility;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText number, passWord;
	private Button wifiButton, handButton, loginButton;
	private SQLDataSqlite sqlStockDataSqlite;
	protected static final String TAG = "LoginActivity";
	private SharedPreferences sp;

	private ScrollView scrollView;
	private static Boolean isThreadStop = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login2);
		sqlStockDataSqlite = new SQLStockDataSqlite(LoginActivity.this, true);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		initView();
	}

	public void initView() {
		scrollView = (ScrollView) this.findViewById(R.id.sv_container);
		number = (EditText) this.findViewById(R.id.editText1);
		passWord = (EditText) this.findViewById(R.id.editText2);
		number.setText(sp.getString("loginid", ""));
		if (TextUtils.isEmpty(sp.getString("loginid", "")))
			number.requestFocus();
		else
			passWord.requestFocus();
		wifiButton = (Button) this.findViewById(R.id.button1);
		handButton = (Button) this.findViewById(R.id.button2);
		loginButton = (Button) this.findViewById(R.id.button3);
		wifiButton.setOnClickListener(this);
		handButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		
		View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					scrollView.postDelayed(new Runnable() {
						public void run() {
							scrollView.smoothScrollTo(scrollView.getScrollX(),
									LoginActivity.this.getWindow()
											.getDecorView().getHeight());
						}
					}, 500);
				}
			}
		};
		scrollView.setOnTouchListener(new OnTouchListener()
		{
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				// 判断隐藏软键盘是否弹出
				if (imm.isActive(number) || imm.isActive(passWord)) {
					return imm.hideSoftInputFromWindow(getCurrentFocus()
							.getWindowToken(), 0);
				} else {
					return false;
				}
			}
		});

		number.setOnFocusChangeListener(listener);
		passWord.setOnFocusChangeListener(listener);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
//			sqlStockDataSqlite.clearStockData();
			UpdateStaff();
			break;
		case R.id.button2:
//			sqlStockDataSqlite.clearStockData();
			LoadUserFromLocal();
			// LoadGoodsFromLocal();
			break;
		case R.id.button3:
			login();
			break;
		}

	}

	// 选择完毕后在onActivityResult方法中回调 从data中拿到文件路径
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			AlertUtil.showToast(uri.toString(), LoginActivity.this);
			// tvUpload.setText(uri.toString());
		}
	}

	private void LoadOffLineDataToDb(String fileName) {

	}

	private void LoadUserFromLocal() {
		
	}

	private void LoadStoreHouseFromLocal() {
		
	}

	private void LoadGoodsFromLocal() {
		
	}

	private void login() {
		Intent intent = new Intent();

		intent.setClass(LoginActivity.this,
				StockMenuActivity.class);					
		startActivity(intent);
	}

	private void UpdateStaff() {
		
	}

	private void UpdateStoreDatabase() {
		
	}



	private void UpdateGoodsDatabase() {
		
	}

	private void startDownGoodsData(final String urlStr) {
		
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// case 55:
			// mDialog.setProgress(hasRead);
			// break;
			// case 56:
			// mDialog.setProgress(hasRead);
			// mDialog.dismiss();
			// break;

			case 0:
				UpdateStaff();
				break;
			case 1:
				UpdateStoreDatabase();
				break;
			case 2:
				UpdateGoodsDatabase();
				break;
			case 3:
				AlertUtil.showToast(AlertUtil.getString(R.string.last_data) + Utility.getCurDateTime(),
						LoginActivity.this);
				sp.edit().putString("lastTime", Utility.getCurDateTime())
						.commit();

				break;
			case 4:
				LoadStoreHouseFromLocal();
				break;
			case 5:
				LoadGoodsFromLocal();
				break;
			case 6:
				AlertUtil.showAlert(LoginActivity.this, R.string.dialog_title,
						R.string.file_not_found, R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			showAlert();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showAlert() {
		AlertUtil.showAlert(LoginActivity.this, R.string.dialog_title,
				R.string.exit_application, R.string.ok, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AlertUtil.dismissDialog();
						finish();
					}
				}, R.string.cancel, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						AlertUtil.dismissDialog();
					}
				});
	}
}
