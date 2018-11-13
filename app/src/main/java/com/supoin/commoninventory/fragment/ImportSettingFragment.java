package com.supoin.commoninventory.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.DragListAdapter;
import com.supoin.commoninventory.adapter.ImportSettingLeftAdapter;
import com.supoin.commoninventory.adapter.SpinnerAdapter;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.ImportSet;
import com.supoin.commoninventory.entity.ItemTag;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.DragListView;

public class ImportSettingFragment extends Fragment {
	private Context mContext;

	private Button btnSave;
	private ListView leftListView, rightListView;
	private ImportSettingLeftAdapter mImportSettingLeftAdapter;
	private DragListView listview_right;
	private DragListAdapter mDragListAdapter;
	private SQLStockDataSqlite sqlStockDataSqlite;
	private SharedPreferences sp;
	private List<ImportSet> importSetList;
	private List<ItemTag> itemTagList;
	private ItemTag mItemTag;
	private ImportSet importSet;
	private List<String> importStrArrayList;
	private HashMap<Integer, Boolean> isSelected;
	private String[] importStrArr;
	private int[] importSetArr;
	private Spinner mSpinner1, mSpinner2, mSpinner3, mSpinner4;
	private String strSpinner1, strSpinner2, strSpinner3, strSpinner4;
	private SpinnerAdapter spinnerAdapter1, spinnerAdapter2, spinnerAdapter3,
			spinnerAdapter4;

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
		sqlStockDataSqlite = new SQLStockDataSqlite(getActivity(), true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_import_setting, null);
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
		mSpinner3 = (Spinner) view.findViewById(R.id.spinner3);
		mSpinner4 = (Spinner) view.findViewById(R.id.spinner4);

	}

	public void setData() {
		String importStr = sp.getString(ConfigEntity.ImportStrKey, "");
		importStrArr = importStr.split(",");
		String[] str1 = new String[importStrArr.length];
		for (int i = 0; i < importStrArr.length; i++) {
			importStrArrayList.add(importStrArr[i]);
			str1[i] = (i + 1) + "";
		}
		spinnerAdapter1 = new SpinnerAdapter(getActivity(), str1);

		String separatorStr = sp.getString(ConfigEntity.SeparatorStrKey, "");
		String[] sepArr = separatorStr.split(Pattern.quote("("));
		spinnerAdapter2 = new SpinnerAdapter(getActivity(), sepArr);
		String[] str3 = { AlertUtil.getString(R.string.no_iv), AlertUtil.getString(R.string.yes_iv)};
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

		mSpinner1.setAdapter(spinnerAdapter1);
		mSpinner2.setAdapter(spinnerAdapter2);
		mSpinner3.setAdapter(spinnerAdapter3);
		mSpinner4.setAdapter(spinnerAdapter4);

		mImportSettingLeftAdapter = new ImportSettingLeftAdapter(
				importStrArrayList, mContext);
		importSetArr = new int[importStrArr.length];
		importSet = new ImportSet();
		importSetList = sqlStockDataSqlite.GetImportSetList();
		if (null != importSetList && 0 < importSetList.size()) {
			importSet = importSetList.get(0);
			importSetArr[0] = importSet.getiBarcode();
			importSetArr[1] = importSet.getiCode();
			importSetArr[2] = importSet.getiArtNO();
			importSetArr[3] = importSet.getiStyle();
			importSetArr[4] = importSet.getiName();
			importSetArr[5] = importSet.getiColorID();
			importSetArr[6] = importSet.getiColorName();
			importSetArr[7] = importSet.getiSizeID();
			importSetArr[8] = importSet.getiSizeName();
			importSetArr[9] = importSet.getiBig();
			importSetArr[10] = importSet.getiSmall();
			importSetArr[11] = importSet.getiStock();
			importSetArr[12] = importSet.getiPrice();

			strSpinner1 = String.valueOf(importSet.getiColumnNum()-1);
			strSpinner2 = importSet.getStrListSeparator();

			for (int i = 0; i < importSetArr.length; i++) {
				if (importSetArr[i] > 0) {
					isSelected.put(i, true);
					mItemTag = new ItemTag();
					mItemTag.setLeftListviewPosition(i);
					mItemTag.setItemString(importStrArr[i]);
					itemTagList.add(mItemTag);
				} else {
					isSelected.put(i, false);
				}
				mImportSettingLeftAdapter.setIsSelected(isSelected);
			}

		} else {
			for (int i = 0; i < importSetArr.length; i++) {
				isSelected.put(i, false);
				mImportSettingLeftAdapter.setIsSelected(isSelected);
			}
		}
		leftListView.setAdapter(mImportSettingLeftAdapter);
		mDragListAdapter = new DragListAdapter(getActivity(), itemTagList);
		rightListView.setAdapter(mDragListAdapter);

		// strSpinner1, strSpinner2, strSpinner3, strSpinner4;
		strSpinner3 = sp.getString(ConfigEntity.ImportTopTitleKey, "");
		strSpinner4 = sp.getString(ConfigEntity.ImportDecaIndexKey, "");
		// 设置默认值
		String string=strSpinner1;
		mSpinner1.setSelection(Integer.parseInt(strSpinner1), true);
		mSpinner2.setSelection(Integer.parseInt(strSpinner2), true);
		mSpinner3.setSelection(Integer.parseInt(strSpinner3), true);
		mSpinner4.setSelection(Integer.parseInt(strSpinner4), true);
		leftListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				mImportSettingLeftAdapter.setSelectedPosition(position);
				if (isSelected.get(position)) {
					isSelected.put(position, false);
					for (int i = 0; i < itemTagList.size(); i++) {
						if (itemTagList.get(i).getLeftListviewPosition() == position) {
							itemTagList.remove(i);
						}
					}
				} else {
					isSelected.put(position, true);
					// 添加已选择的
					mItemTag = new ItemTag();
					mItemTag.setLeftListviewPosition(position);
					mItemTag.setItemString(importStrArr[position]);
					itemTagList.add(mItemTag);
				}

				// }
				mImportSettingLeftAdapter.setIsSelected(isSelected);
				mImportSettingLeftAdapter.notifyDataSetChanged();
				mDragListAdapter.notifyDataSetChanged();
			}

		});

		mSpinner1
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner1 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// myTextView.setText("NONE");
					}
				});

		mSpinner2
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner2 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// myTextView.setText("NONE");
					}
				});

		mSpinner3
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner3 = arg2 + "";
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						// myTextView.setText("NONE");
					}
				});
		mSpinner4
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						strSpinner4 = arg2 + "";
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

				int[] importSetArr = new int[importStrArr.length];

				for (int position = 0; position < mDragListAdapter.getCount(); position++) {
					int i = mDragListAdapter.getItem(position)
							.getLeftListviewPosition();
					importSetArr[i] = position + 1;
				}
				for (int j = 0; j < importStrArr.length; j++) {
					if (!mImportSettingLeftAdapter.getIsSelected().get(j)) {
						importSetArr[j] = 0;
					} else {
						selectNumb++;
					}
				}
				if (selectNumb < 1) {
					AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.import1, R.string.ok,
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
				if (!(selectNumb+"").equals(str1+"")) {
					AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.import2,R.string.ok,
							new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
						}
					});
					return;
				}
				ImportSet importSet = new ImportSet();
				importSet.iBarcode = importSetArr[0];
				importSet.iCode = importSetArr[1];
				importSet.iArtNO = importSetArr[2];
				importSet.iStyle = importSetArr[3];
				importSet.iName = importSetArr[4];
				importSet.iColorID = importSetArr[5];
				importSet.iColorName = importSetArr[6];
				importSet.iSizeID = importSetArr[7];
				importSet.iSizeName = importSetArr[8];
				importSet.iBig = importSetArr[9];
				importSet.iSmall = importSetArr[10];
				importSet.iStock = importSetArr[11];
				importSet.iPrice = importSetArr[12];
				importSet.iColumnNum = Integer.parseInt(strSpinner1)+1;
				importSet.strListSeparator = strSpinner2;
				sqlStockDataSqlite.saveImportSet(importSet);

				sp.edit()
						.putString(ConfigEntity.ImportTopTitleKey, strSpinner3)
						.putString(ConfigEntity.ImportDecaIndexKey, strSpinner4)
						.commit();
				AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.import3, R.string.ok,
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

}
