package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.util.Constants;

public abstract class PasswordActivity extends Activity implements OnClickListener{

	private Button btn_ok;
	private Button btn_cancl;
	public EditText et_goodsBar;
	public SharedPreferences sp;
	private SQLStockDataSqlite sqlStockDataSqlite;
	private Handler handler = new Handler();
	private String title;
	private TextView tv_title;
	protected String sType = "";
	final String ADMINPSW = "31715191";
	
	public PasswordActivity() {
		super();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		
	}

	public void initView() {
		setContentView(R.layout.activity_inputgoodsbar);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		title = bundle.getString("title");
	
		sType = bundle.getString(Constants.KEY_PASSWORD_TYPE, "");
	
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		sqlStockDataSqlite = new SQLStockDataSqlite(PasswordActivity.this,
				true);
		tv_title = (TextView) findViewById(R.id.title);
		tv_title.setText(title);
		et_goodsBar = (EditText) findViewById(R.id.dialog_edit);
		et_goodsBar.setFocusable(true);
		et_goodsBar.setFocusableInTouchMode(true);
		et_goodsBar.requestFocus();
		et_goodsBar
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE
								|| (event != null
										&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event
										.getAction() == KeyEvent.ACTION_DOWN)) {
							InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(getCurrentFocus()
									.getWindowToken(), 0);
							String passWord = et_goodsBar.getText().toString()
									.trim();
							// String staffId=sp.getString("loginid","");
							// String
							// pwd=sqlStockDataSqlite.getAuthByStaffId(staffId);
							// // 有密码再做判断
							// if (Utility.md5(passWord).equals(pwd)) {
							// InputMethodManager inputMethodManager =
							// (InputMethodManager)
							// getSystemService(PasswordInputActivity.INPUT_METHOD_SERVICE);
							// inputMethodManager.hideSoftInputFromWindow(PasswordInputActivity.this.getCurrentFocus().getWindowToken(),
							// InputMethodManager.HIDE_NOT_ALWAYS);
							// //接受软键盘输入的编辑文本或其它视图
							// //
							// inputMethodManager.showSoftInput(submitBt,InputMethodManager.SHOW_FORCED);
							// Intent intent = new Intent();
							// setResult(RESULT_OK, intent);
							// finish();
							// }else {
							// AlertUtil.showToast("密码错误，请重新输入",
							// PasswordInputActivity.this);
							// }
							return true;
						}
						return false;
					}
				});
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancl = (Button) findViewById(R.id.btn_cancl);
		btn_ok.setOnClickListener(this);
		btn_cancl.setOnClickListener(this);
		popupInputMethodWindow();
	}

	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		GoodsDetailsActivity.is_intercept = true;
	}

	private void popupInputMethodWindow() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) getSystemService(PasswordInputActivity.INPUT_METHOD_SERVICE);
				// 显示软键盘
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 200);
		// InputMethodManager imm =
		// (InputMethodManager)getSystemService(PasswordInputActivity.INPUT_METHOD_SERVICE);
		// //显示软键盘
		// // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		// imm.showSoftInput(et_goodsBar, InputMethodManager.RESULT_SHOWN);
		// imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	public void onKeyDownOk(){
		
	}
	
	@Override
	public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_ok:
				onKeyDownOk();
				break;
			case R.id.btn_cancl:
				GoodsDetailsActivity.is_intercept = true;
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(PasswordInputActivity.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(PasswordActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
				Intent intent = new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
				
	//			List<CheckListEntity> list = new ArrayList<SQLStockDataSqlite.CheckListEntity>();
	//			list = sqlStockDataSqlite.GetStaCheckPositionDetail(GoodsDetailsActivity.strShopID, GoodsDetailsActivity.strCheckID, GoodsDetailsActivity.strPositionID);
	//			GoodsDetailsActivity.mListView.setAdapter(new GoodsDetailsListAdapter(PasswordInputActivity.this, list));
	//			GoodsDetailsActivity.tv_checknum.setText(String.valueOf(sqlStockDataSqlite.GetSpecifiedSum(GoodsDetailsActivity.strShopID, GoodsDetailsActivity.strCheckID,GoodsDetailsActivity.strPositionID)));
	//			GoodsDetailsActivity.tv_qty.setText(String.valueOf(sqlStockDataSqlite.GetSpecifiedSum(GoodsDetailsActivity.strShopID, GoodsDetailsActivity.strCheckID,GoodsDetailsActivity.strPositionID)));
				
				break;
	
			}
	     
			
		}

}