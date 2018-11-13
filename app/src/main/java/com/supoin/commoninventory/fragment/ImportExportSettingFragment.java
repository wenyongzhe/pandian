package com.supoin.commoninventory.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.supoin.commoninventory.R;
import com.supoin.commoninventory.activity.FileNameSettingActivity;
import com.supoin.commoninventory.adapter.SpinnerAdapter;
import com.supoin.commoninventory.db.SQLInStockDataSqlite;
import com.supoin.commoninventory.db.SQLOutStockDataSqlite;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.db.entity.CheckListEntity;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.ExportSet;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.GlobalRunConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 导入导出控制
 * @date 2017-5-24 上午11:52:12 
 */
public class ImportExportSettingFragment extends Fragment {
	private SpinnerAdapter adapter1, adapter2, adapter3, adapter4, adapter5,
			adapter6, adapter7,adapter8;
	private Spinner mSpinner1, mSpinner2, mSpinner3, mSpinner4, mSpinner5,
			mSpinner6, mSpinner7,mSpinner8;
	private LinearLayout llSplitAndWay;
	private Button btn_select, btn_save;
	private SQLStockDataSqlite sqlStockDataSqlite;
	private SQLInStockDataSqlite sqlInDataSqlite;
	private SQLOutStockDataSqlite sqlOutDataSqlite;
	private String[] str1 = { AlertUtil.getString(R.string.export4), AlertUtil.getString(R.string.export5) };
	private String[] str2 = { AlertUtil.getString(R.string.export6), AlertUtil.getString(R.string.export7) };
	private String[] str3 = { AlertUtil.getString(R.string.export8), AlertUtil.getString(R.string.export9) };
	private String[] str4 = { AlertUtil.getString(R.string.export10), AlertUtil.getString(R.string.export11), AlertUtil.getString(R.string.export25) };
	private String[] str5 = { AlertUtil.getString(R.string.export12), AlertUtil.getString(R.string.export13) };
	private String[] str6 = { AlertUtil.getString(R.string.export14), AlertUtil.getString(R.string.export15) };
	private String[] str7 = { AlertUtil.getString(R.string.export16), AlertUtil.getString(R.string.export17)};
	private String[] str8 = { AlertUtil.getString(R.string.export23), AlertUtil.getString(R.string.export24)};

	private String strSpinner1;
	private String strSpinner2;
	private String strSpinner3;
	private String strSpinner4;
	private String strSpinner5;
	private String strSpinner6;
	private String strSpinner7;
	private String strSpinner8;

	private SharedPreferences sp;
	private List<ExportSet> exportSetList;
	private ExportSet exportSet;
	private String strSeparator = "-1";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getActivity().getSharedPreferences("InvenConfig",
				getActivity().MODE_PRIVATE);

		sqlStockDataSqlite = new SQLStockDataSqlite(getActivity(), true);
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
	        	str3[0] = AlertUtil.getString(R.string.export_by)+bianhao;
	    		str3[1] = AlertUtil.getString(R.string.export_by)+bianhao+"and "+ huowei;
	        }else{
	        	str3[0] = "按"+bianhao+"导出";
	    		str3[1] = "按"+bianhao+","+huowei+"导出";
	        }
		setSpinner();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_out_in_setting, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);

		btn_select = (Button) view.findViewById(R.id.btn_export_name);
		btn_save = (Button) view.findViewById(R.id.btn_save);
		mSpinner1 = (Spinner) view.findViewById(R.id.spinner_export_header);
		mSpinner2 = (Spinner) view.findViewById(R.id.spinner_export_order);
		mSpinner3 = (Spinner) view.findViewById(R.id.spinner_export_type);
		mSpinner4 = (Spinner) view.findViewById(R.id.spinner_export_file);
		mSpinner5 = (Spinner) view.findViewById(R.id.spinner_export_merge);
		mSpinner6 = (Spinner) view.findViewById(R.id.spinner_export_num_split);
		mSpinner7 = (Spinner) view.findViewById(R.id.spinner_export_way);
		mSpinner8 = (Spinner) view.findViewById(R.id.spinner_export_mode);

		llSplitAndWay = (LinearLayout) view.findViewById(R.id.ll_split_and_way);

		// 非超级管理员
		if (2 != GlobalRunConfig.GetInstance().iRightLevel) {
			llSplitAndWay.setVisibility(View.GONE);
		}



		mSpinner1
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner1 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		mSpinner2
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner2 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		mSpinner3
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner3 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		mSpinner4
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner4 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		mSpinner5
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner5 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		mSpinner6
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner6 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		mSpinner7
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner7 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		mSpinner8
		.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapter,
					View arg1, int arg2, long arg3) {
				strSpinner8 = arg2 + "";
			}
			
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		btn_select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(getActivity(),
//						FileNameSettingActivity.class);
//				startActivity(intent);
				/*View rootview = LayoutInflater.from(getActivity())
						.inflate(R.layout.activity_main, null);
					showWindow(rootview);*/
				showSelectDialog();
			}
		});

		btn_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				exportSetList = sqlStockDataSqlite.getExportSetList();
				if (exportSetList.size() > 0) {
					exportSet = new ExportSet();
					exportSet.iShopID = exportSetList.get(0).getiShopID();
					exportSet.iCheckID = exportSetList.get(0).getiCheckID();
					exportSet.iPositionID = exportSetList.get(0)
							.getiPositionID();
					exportSet.iBar = exportSetList.get(0).getiBar();
					exportSet.iArtNO = exportSetList.get(0).getiArtNO();
					exportSet.iStyle = exportSetList.get(0).getiStyle();
					exportSet.iName = exportSetList.get(0).getiName();
					exportSet.iColorID = exportSetList.get(0).getiColorID();
					exportSet.iColorName = exportSetList.get(0).getiColorName();
					exportSet.iSizeID = exportSetList.get(0).getiSizeID();
					exportSet.iSizeName = exportSetList.get(0).getiSizeName();
					exportSet.iBig = exportSetList.get(0).getiBig();
					exportSet.iSmall = exportSetList.get(0).getiSmall();
					exportSet.iStock = exportSetList.get(0).getiStock();
					exportSet.iPrice = exportSetList.get(0).getiPrice();
					exportSet.iColumnNum = exportSetList.get(0).getiColumnNum();
					exportSet.iQty = exportSetList.get(0).getiQty();
					exportSet.iHead = exportSetList.get(0).getiHead();
					exportSet.iScanDate = exportSetList.get(0).getiScanDate();
					strSeparator = exportSet.strListSeparator = exportSetList
							.get(0).getStrListSeparator();
				} else {
					AlertUtil.showToast(AlertUtil.getString(R.string.export18), getActivity());
				}

				if (strSpinner4.equals("1") && !strSeparator.equals("2")) {
					AlertUtil
							.showAlert(
									getActivity(),
									R.string.dialog_title,
									AlertUtil.getString(R.string.export19)+
											AlertUtil.getString(R.string.export20),
									R.string.ok, new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stuba
											AlertUtil.dismissDialog();

										}
									}, R.string.cancel,
									new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											AlertUtil.dismissDialog();

											handler.sendEmptyMessage(0);

										}
									});

				} else {
					sp.edit()
							.putString(ConfigEntity.IsExportHeaderKey,
									strSpinner1)
							.putString(ConfigEntity.IsExportSOKey, strSpinner2)
							.putString(ConfigEntity.ExportTypeKey, strSpinner3)
							.putString(ConfigEntity.ExportFTKey, strSpinner4)
							.putString(ConfigEntity.MergeAllExportKey,
									strSpinner5)
							.putString(ConfigEntity.ExportNumSplitKey,
									strSpinner6)
							.putString(ConfigEntity.ExportModeKey,
											strSpinner8)
							.putString(ConfigEntity.ExportWayKey, strSpinner7)
							.commit();
					AlertUtil.showToast(AlertUtil.getString(R.string.saved_success), getActivity());
				}

			}
		});
		return view;
	}

	void setSpinner() {
		adapter1 = new SpinnerAdapter(getActivity(), str1);
		adapter2 = new SpinnerAdapter(getActivity(), str2);
		adapter3 = new SpinnerAdapter(getActivity(), str3);
		adapter4 = new SpinnerAdapter(getActivity(), str4);
		adapter5 = new SpinnerAdapter(getActivity(), str5);
		adapter6 = new SpinnerAdapter(getActivity(), str6);
		adapter7 = new SpinnerAdapter(getActivity(), str7);
		adapter8 = new SpinnerAdapter(getActivity(), str8);
		mSpinner1.setAdapter(adapter1);
		mSpinner2.setAdapter(adapter2);
		mSpinner3.setAdapter(adapter3);
		mSpinner4.setAdapter(adapter4);
		mSpinner5.setAdapter(adapter5);
		mSpinner6.setAdapter(adapter6);
		mSpinner7.setAdapter(adapter7);
		mSpinner8.setAdapter(adapter8);

		// 设置默认值
		strSpinner1 = sp.getString(ConfigEntity.IsExportHeaderKey, "");
		strSpinner2 = sp.getString(ConfigEntity.IsExportSOKey, "");
		strSpinner3 = sp.getString(ConfigEntity.ExportTypeKey, "");
		strSpinner4 = sp.getString(ConfigEntity.ExportFTKey, "");
		strSpinner5 = sp.getString(ConfigEntity.MergeAllExportKey, "");
		strSpinner6 = sp.getString(ConfigEntity.ExportNumSplitKey, "");
		strSpinner7 = sp.getString(ConfigEntity.ExportWayKey, "");
		strSpinner8 = sp.getString(ConfigEntity.ExportModeKey, "");

		mSpinner1.setSelection(Integer.parseInt(strSpinner1), true);
		mSpinner2.setSelection(Integer.parseInt(strSpinner2), true);
		mSpinner3.setSelection(Integer.parseInt(strSpinner3), true);
		mSpinner4.setSelection(Integer.parseInt(strSpinner4), true);
		mSpinner5.setSelection(Integer.parseInt(strSpinner5), true);
		mSpinner6.setSelection(Integer.parseInt(strSpinner6), true);
		mSpinner7.setSelection(Integer.parseInt(strSpinner7), true);
		mSpinner8.setSelection(Integer.parseInt(strSpinner8), true);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 0) {
				AlertUtil.showAlert(getActivity(), R.string.dialog_title,
						AlertUtil.getString(R.string.export21), R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
								sqlInDataSqlite = new SQLInStockDataSqlite(getActivity(), true);
								sqlOutDataSqlite = new SQLOutStockDataSqlite(getActivity(), true);
								List<ExportSet> list1 = sqlStockDataSqlite.getExportSetList();
								List<ExportSet> list2 = sqlInDataSqlite.getExportSetList();
								List<ExportSet> list3 = sqlStockDataSqlite.getExportSetList();
								ExportSet exportSet1 = list1.get(0);
								ExportSet exportSet2 = list2.get(0);
								ExportSet exportSet3 = list3.get(0);
								exportSet1.setStrListSeparator("2");
								exportSet2.setStrListSeparator("2");
								exportSet3.setStrListSeparator("2");
								sqlStockDataSqlite.saveExportSet(exportSet1);
								sqlInDataSqlite.saveExportSet(exportSet2);
								sqlOutDataSqlite.saveExportSet(exportSet3);

								sp.edit()
										.putString(
												ConfigEntity.IsExportHeaderKey,
												strSpinner1)
										.putString(ConfigEntity.IsExportSOKey,
												strSpinner2)
										.putString(ConfigEntity.ExportTypeKey,
												strSpinner3)
										.putString(ConfigEntity.ExportFTKey,
												strSpinner4)
										.putString(
												ConfigEntity.MergeAllExportKey,
												strSpinner5)
										.putString(
												ConfigEntity.ExportNumSplitKey,
												strSpinner6)
												.putString(
														ConfigEntity.ExportModeKey,
														strSpinner8)
										.putString(ConfigEntity.ExportWayKey,
												strSpinner7).commit();

								AlertUtil.showToast(AlertUtil.getString(R.string.export22),
										getActivity());
							}
						}, R.string.cancel, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();

							}
						});
			}
		}
	};
	
	private View view;
	private TextView tv_inventory_prefix,tv_instock_prefix,tv_outstock_prefix;
	private ListView lv_tips;
	private PopupWindow popupWindow;
	private List<CheckListEntity> checkDetailTipsList = new ArrayList<CheckListEntity>();

	void showSelectDialog(){
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_prefix, null, false);
		final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_MyDialog).setView(view).create();
		tv_inventory_prefix = (TextView) view.findViewById(R.id.tv_inventory_prefix);
		tv_instock_prefix = (TextView) view.findViewById(R.id.tv_instock_prefix);
		tv_outstock_prefix = (TextView) view.findViewById(R.id.tv_outstock_prefix);
		tv_inventory_prefix.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
				Intent intent = new Intent(getActivity(),
						FileNameSettingActivity.class);
				intent.putExtra("flag", 0);
				startActivity(intent);
			}
		});
		tv_instock_prefix.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
				Intent intent = new Intent(getActivity(),
						FileNameSettingActivity.class);
				intent.putExtra("flag", 1);
				startActivity(intent);

			}
		});
		tv_outstock_prefix.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
				Intent intent = new Intent(getActivity(),
						FileNameSettingActivity.class);
				intent.putExtra("flag", 2);
				startActivity(intent);

			}
		});
		alertDialog.show();
		alertDialog.getWindow().setLayout(ScreenUtils.getScreenWidth() - 100, LinearLayout.LayoutParams.WRAP_CONTENT);
		alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_edittext_normal);
	}
	/**
	 * 显示
	 * 
	 * @param parent
	 */
	private void showWindow(View parent) {
		LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		view = layoutInflater.inflate(R.layout.popupwindow_prefix, null);
		tv_inventory_prefix = (TextView) view.findViewById(R.id.tv_inventory_prefix);
		tv_instock_prefix = (TextView) view.findViewById(R.id.tv_instock_prefix);
		tv_outstock_prefix = (TextView) view.findViewById(R.id.tv_outstock_prefix);
		
		// 创建一个PopuWidow对象
		popupWindow = new PopupWindow(view);
		popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);  
		popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT); 
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		  // 设置动画效果     
//	     popupWindow.setAnimationStyle(R.style.contextMenuAnim); 
		WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = 0.5f; //0.0-1.0
		getActivity().getWindow().setAttributes(lp);
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
//		popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha =1f; //0.0-1.0
				getActivity().getWindow().setAttributes(lp);
			}
		});
		tv_inventory_prefix.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha =1f; //0.0-1.0
				getActivity().getWindow().setAttributes(lp);
				popupWindow.dismiss();
				Intent intent = new Intent(getActivity(),
						FileNameSettingActivity.class);
				intent.putExtra("flag", 0);
				startActivity(intent);
			}
		});
		tv_instock_prefix.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha =1f; //0.0-1.0
				getActivity().getWindow().setAttributes(lp);
				popupWindow.dismiss();
				Intent intent = new Intent(getActivity(),
						FileNameSettingActivity.class);
				intent.putExtra("flag", 1);
				startActivity(intent);
				
			}
		});
		tv_outstock_prefix.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha =1f; //0.0-1.0  
				getActivity().getWindow().setAttributes(lp);
				popupWindow.dismiss();
				Intent intent = new Intent(getActivity(),
						FileNameSettingActivity.class);
				intent.putExtra("flag", 2);
				startActivity(intent);
				
			}
		});
	}
}
