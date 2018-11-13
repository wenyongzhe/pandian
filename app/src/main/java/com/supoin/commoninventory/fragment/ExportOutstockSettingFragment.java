package com.supoin.commoninventory.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.DragListAdapter;
import com.supoin.commoninventory.adapter.ExportSettingLeftAdapter;
import com.supoin.commoninventory.adapter.SpinnerAdapter;
import com.supoin.commoninventory.db.SQLOutStockDataSqlite;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.ExportSet;
import com.supoin.commoninventory.entity.ItemTag;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.DragListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 出库导出设置
 */
public class ExportOutstockSettingFragment extends Fragment implements OnClickListener {
	private Context mContext;
	private Button btnSave;
	private CheckBox cbNum;
	private ListView leftListView, rightListView;
	private ExportSettingLeftAdapter mExportSettingLeftAdapter;
	private DragListView listview_right;
	private DragListAdapter mDragListAdapter;
	private SQLOutStockDataSqlite sqlStockDataSqlite;
	private SharedPreferences sp;
	private List<ExportSet> exportSetList;
	private ExportSet exportSet;
	private List<ItemTag> itemTagList;
	private ItemTag mItemTag;
	private List<String> importStrArrayList;
	private HashMap<Integer, Boolean> isSelected;
	private String[] exportStrArr;
	private int[] exportSetArr;
	private Spinner mSpinner1, mSpinner2, mSpinner4;
	private String strSpinner1, strSpinner2, strSpinner4[];
	private SpinnerAdapter spinnerAdapter1, spinnerAdapter2, spinnerAdapter3,
			spinnerAdapter4;
	private String[] strExportDatasTitle;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		mContext = activity;

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getActivity().getSharedPreferences("InvenConfig",
				getActivity().MODE_PRIVATE);
		sqlStockDataSqlite = new SQLOutStockDataSqlite(getActivity(), true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_export_setting, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(lp);
		importStrArrayList = new ArrayList<String>();
		itemTagList = new ArrayList<ItemTag>();
		isSelected = new HashMap<Integer, Boolean>();
		strSpinner1 = "0";
		strSpinner2 = "0";
		initView(view);
		setData();

		return view;
	}

	public void initView(View view) {
		leftListView = (ListView) view.findViewById(R.id.listview_left);
		rightListView = (DragListView) view.findViewById(R.id.listview_right);
		btnSave = (Button) view.findViewById(R.id.btn_save);
		mSpinner1 = (Spinner) view.findViewById(R.id.spinner1);
		mSpinner2 = (Spinner) view.findViewById(R.id.spinner2);
		mSpinner4 = (Spinner) view.findViewById(R.id.spinner4);
		cbNum = (CheckBox) view.findViewById(R.id.cb_num);

	}

	public void setData() {
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		exportStrArr = importStr.split(",");
		String[] str1 = new String[exportStrArr.length];
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
			str1[i] = (i + 1) + "";
		}
		spinnerAdapter1 = new SpinnerAdapter(getActivity(), str1);

		String separatorStr = sp.getString(ConfigEntity.SeparatorStrKey, "");
		String[] sepArr = separatorStr.split(Pattern.quote("("));
		spinnerAdapter2 = new SpinnerAdapter(getActivity(), sepArr);
		String[] str3 = { AlertUtil.getString(R.string.no_iv), AlertUtil.getString(R.string.yes_iv) };
		spinnerAdapter3 = new SpinnerAdapter(getActivity(), str3);
		String[] decArr = sp.getString(ConfigEntity.ImportDecarationKey, "")
				.split(",");
		String[] str4 = new String[decArr.length];
		String strTmp = "";
		for (int i = 0; i < decArr.length; i++) {
			String s = decArr[i];
			if (s.equals(AlertUtil.getString(R.string.double_marks)))
				strTmp = "\"";
			else if (s.equals(AlertUtil.getString(R.string.single_marks)))
				strTmp = "\'";
			else if (s.equals(AlertUtil.getString(R.string.no_iv)))
				strTmp = AlertUtil.getString(R.string.no_iv);
			str4[i] = strTmp;
		}
		spinnerAdapter4 = new SpinnerAdapter(getActivity(), str4);
		strSpinner4 = sp.getString(ConfigEntity.ExportDecaOutIndexKey, "").split(
				",");

		//bug1361
		if (strSpinner4[0].equals("1")) {
			cbNum.setClickable(true);
			if(strSpinner4[1].equals("1")){
				cbNum.setChecked(true);
			}else {
				cbNum.setChecked(false);
				}
		} else {
			cbNum.setChecked(false);
			cbNum.setClickable(false);
		}

		mSpinner1.setAdapter(spinnerAdapter1);
		mSpinner2.setAdapter(spinnerAdapter2);
		mSpinner4.setAdapter(spinnerAdapter4);

		mExportSettingLeftAdapter = new ExportSettingLeftAdapter(
				importStrArrayList, mContext);
		exportSetArr = new int[exportStrArr.length];
		exportSet = new ExportSet();
		exportSetList = sqlStockDataSqlite.getExportSetList();
		if (null != exportSetList && 0 < exportSetList.size()) {
			exportSet = exportSetList.get(0);
			exportSetArr[0] = exportSet.iShopID;
			exportSetArr[1] = exportSet.iCheckID;
			exportSetArr[2] = exportSet.iPositionID;
			exportSetArr[3] = exportSet.iBar;
			exportSetArr[4] = exportSet.iArtNO;
			exportSetArr[5] = exportSet.iStyle;
			exportSetArr[6] = exportSet.iName;
			exportSetArr[7] = exportSet.iColorID;
			exportSetArr[8] = exportSet.iColorName;
			exportSetArr[9] = exportSet.iSizeID;
			exportSetArr[10] = exportSet.iSizeName;
			exportSetArr[11] = exportSet.iBig;
			exportSetArr[12] = exportSet.iSmall;
			exportSetArr[13] = exportSet.iStock;
			exportSetArr[14] = exportSet.iPrice;
			exportSetArr[15] = exportSet.iQty;
			exportSetArr[16] = exportSet.iScanDate;
			strSpinner1 = String.valueOf(exportSet.getiColumnNum()-1);
			strSpinner2 = exportSet.getStrListSeparator();

			for (int i = 0; i < exportSetArr.length; i++) {
				if (exportSetArr[i] > 0) {
					isSelected.put(i, true);
					mItemTag = new ItemTag();
					mItemTag.setLeftListviewPosition(exportSetArr[i]);
					mItemTag.setItemString(exportStrArr[i]);
					itemTagList.add(mItemTag);
				} else {
					isSelected.put(i, false);
				}
				mExportSettingLeftAdapter.setIsSelected(isSelected);
			}
			Collections.sort(itemTagList, new Comparator<ItemTag>(){
				public int compare(ItemTag p1, ItemTag p2) {
					if(p1.getLeftListviewPosition() > p2.getLeftListviewPosition()){
						return 1;
					}
					if(p1.getLeftListviewPosition() == p2.getLeftListviewPosition()){
						return 0;
					}
					return -1;
				}
			});
		} else {
			for (int i = 0; i < exportSetArr.length; i++) {
				isSelected.put(i, false);
				mExportSettingLeftAdapter.setIsSelected(isSelected);
			}
		}
		
		/*strExportDatasTitle = sp.getString("ExportDatasOutTitle", "").split(",");
		for (int s = 0; s < strExportDatasTitle.length; s++) {
			if(!TextUtils.isEmpty(strExportDatasTitle[s])){
				mItemTag = new ItemTag();
				for (int i = 0; i < importStrArrayList.size(); i++) {
					if(importStrArrayList.get(i).equals(strExportDatasTitle[s])){
						mItemTag.setLeftListviewPosition(i);
					}
				}
				mItemTag.setItemString(strExportDatasTitle[s]);
				itemTagList.add(mItemTag);
			}
		}*/
		
		leftListView.setAdapter(mExportSettingLeftAdapter);
		mDragListAdapter = new DragListAdapter(getActivity(), itemTagList);
		rightListView.setAdapter(mDragListAdapter);
		// 设置默认值
		mSpinner1.setSelection(Integer.parseInt(strSpinner1), true);
		mSpinner2.setSelection(Integer.parseInt(strSpinner2), true);
		mSpinner4.setSelection(Integer.parseInt(strSpinner4[0]), true);
		leftListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				mExportSettingLeftAdapter.setSelectedPosition(position);
				if (isSelected.get(position)) {
					/*isSelected.put(position, false);
					for (int i = 0; i < itemTagList.size(); i++) {
						if (itemTagList.get(i).getLeftListviewPosition() == position) {
							itemTagList.remove(i);
						}
					}*/
					String str = importStrArrayList.get(position);
					isSelected.put(position, false);
					for (int i = 0; i < itemTagList.size(); i++) {
						if (itemTagList.get(i).getItemString().equals(str)) {
							itemTagList.remove(i);
						}
					}
				} else {
					isSelected.put(position, true);
					// 添加已选择的
					mItemTag = new ItemTag();
					mItemTag.setLeftListviewPosition(position);
					mItemTag.setItemString(exportStrArr[position]);
					itemTagList.add(mItemTag);
				}

				// }
				mExportSettingLeftAdapter.setIsSelected(isSelected);
				mExportSettingLeftAdapter.notifyDataSetChanged();
				mDragListAdapter.notifyDataSetChanged();
			}

		});
		cbNum.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					strSpinner4[1] = "1";
				} else {
					strSpinner4[1] = "0";
				}
			}
		});
		mSpinner1
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner1 = arg2  + "";
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
		mSpinner4
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner4[0] = arg2 + "";
						if (1 == arg2) {
							cbNum.setClickable(true);
							if (strSpinner4[1].equals("1")) {
								cbNum.setChecked(true);
							} else {
								cbNum.setChecked(false);
							}
						} else {
							cbNum.setClickable(false);
							cbNum.setChecked(false);
						}
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// myTextView.setText("NONE");
					}
				});

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int selectNumb = 0;

				int[] exportSetArr = new int[exportStrArr.length];

				/*for (int position = 0; position < mDragListAdapter.getCount(); position++) {
					int i = mDragListAdapter.getItem(position)
							.getLeftListviewPosition();
					exportSetArr[i] = position + 1;
				}*/
				for (int i = 0; i < exportStrArr.length; i++) {
					for (int position = 0; position < mDragListAdapter.getCount(); position++){
						if (exportStrArr[i].equals(mDragListAdapter.getItem(position).getItemString())) {
							exportSetArr[i] = position+1;
						}
					}

				}
				for (int j = 0; j < exportStrArr.length; j++) {
					Boolean sBoolean=mExportSettingLeftAdapter.getIsSelected().get(j);
					if (!mExportSettingLeftAdapter.getIsSelected().get(j)) {
						exportSetArr[j] = 0;
					} else {
						selectNumb++;
					}
				}
				if (selectNumb < 1) {
					AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.export1, R.string.ok,
							new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();
								}
							});
					return;
				}
				int str1=Integer.parseInt(strSpinner1)+1;
				if (!(selectNumb + "").equals( str1+ "")) {
					AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.export2,
							R.string.ok, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();
								}
							});
					return;
				}
				ExportSet exportSet = new ExportSet();
				exportSet.iShopID = exportSetArr[0];
				exportSet.iCheckID = exportSetArr[1];
				exportSet.iPositionID = exportSetArr[2];
				exportSet.iBar = exportSetArr[3];
				exportSet.iArtNO = exportSetArr[4];
				exportSet.iStyle = exportSetArr[5];
				exportSet.iName = exportSetArr[6];
				exportSet.iColorID = exportSetArr[7];
				exportSet.iColorName = exportSetArr[8];
				exportSet.iSizeID = exportSetArr[9];
				exportSet.iSizeName = exportSetArr[10];
				exportSet.iBig = exportSetArr[11];
				exportSet.iSmall = exportSetArr[12];
				exportSet.iStock = exportSetArr[13];
				exportSet.iPrice = exportSetArr[14];
				exportSet.iQty = exportSetArr[15];
				exportSet.iScanDate = exportSetArr[16];

				exportSet.iColumnNum = Integer.parseInt(strSpinner1)+1;
				exportSet.strListSeparator = strSpinner2;
				sqlStockDataSqlite.saveExportSet(exportSet);
				// 保存选中项的值
				StringBuffer sBuffer = new StringBuffer();
				for (int i = 0; i < mDragListAdapter.getCount(); i++) {
					sBuffer.append(mDragListAdapter.getItem(i).getItemString()
							+ ",");
				}
				String exportDatasTitle = sBuffer.toString().substring(0,
						sBuffer.toString().length() - 1);
				sp.edit().putString("ExportDatasOutTitle", exportDatasTitle)
						.commit();
				sp.edit()
						.putString(ConfigEntity.ExportDecaOutIndexKey,
								strSpinner4[0] + "," + strSpinner4[1]).commit();
				AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.export3, R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
