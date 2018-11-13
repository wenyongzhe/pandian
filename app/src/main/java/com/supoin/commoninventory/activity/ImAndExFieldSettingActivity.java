package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.ImAndExFieldSettingAadpter;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.util.AlertUtil;

import java.util.ArrayList;
import java.util.List;
/***字段名称设置*/
public class ImAndExFieldSettingActivity extends Activity {

    private ListView lv_change_name;
    private List<String> importStrArrayList;
    private SharedPreferences sp;
    private String importStr;
    private String[] exportStrArr;
    private String str;
    private String strru;
    private ImAndExFieldSettingAadpter imAndExFieldSettingAadpter;
    private AlertDialog alertDialog;
    private String[] strInvenExportDatasTitle;
    private String[] strInExportDatasTitle;
    private String[] strOutExportDatasTitle;
    private String exportInvenDatasTitle = "";
    private String exportInDatasTitle = "";
    private String exportOutDatasTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomTitleBar.getTitleBar(ImAndExFieldSettingActivity.this, R.string.field_name_settings, false);
        setContentView(R.layout.activity_imandexfield_setting);

        lv_change_name = (ListView) findViewById(R.id.lv_change_name);

        initData();
        initListener();
    }

    private void initData() {
        importStrArrayList = new ArrayList<String>();
        sp = getSharedPreferences("InvenConfig",
                MODE_PRIVATE);
        // 获取表头
        strInvenExportDatasTitle = sp.getString("ExportDatasTitle", "").split(",");
        strInExportDatasTitle = sp.getString("ExportDatasInTitle", "").split(",");
        strOutExportDatasTitle = sp.getString("ExportDatasOutTitle", "").split(",");

        importStr = sp.getString(ConfigEntity.ExportStrKey, "");
        exportStrArr = importStr.split(",");
        for (int i = 0; i < exportStrArr.length; i++) {
            importStrArrayList.add(exportStrArr[i]);
        }

        imAndExFieldSettingAadpter = new ImAndExFieldSettingAadpter(ImAndExFieldSettingActivity.this, importStrArrayList);
        lv_change_name.setAdapter(imAndExFieldSettingAadpter);
    }

    private void initListener() {
        lv_change_name.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                showModifyDiadlog(position);
            }
        });
    }

    void showModifyDiadlog(final int position) {
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_field_setting, null, false);
        alertDialog = new AlertDialog.Builder(this, R.style.Theme_MyDialog).setView(view).create();
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        final EditText et_newfield = (EditText) view.findViewById(R.id.et_newfield);
        TextView tv_original_name = (TextView) view.findViewById(R.id.tv_original_name);
        et_newfield.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                saveField(et_newfield,position,view);
                return false;
            }
        });
        tv_original_name.setText(importStrArrayList.get(position));
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                KeyboardUtils.hideSoftInput(view);
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveField(et_newfield,position,view);
            }
        });
        alertDialog.show();
        alertDialog.getWindow().setLayout(ScreenUtils.getScreenWidth() - 100, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_edittext_normal);
    }

    /*保存字段*/
    private void saveField(EditText inputServer, int position,View view) {
        if (TextUtils.isEmpty(inputServer.getText().toString().trim())) {
            Toast.makeText(ImAndExFieldSettingActivity.this, AlertUtil.getString(R.string.not_modified_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if(importStrArrayList.get(position).equals(inputServer.getText().toString().trim())){
            return;
        }
        for(String item : importStrArrayList){
            if(item.equals(inputServer.getText().toString().trim())){
                Toast.makeText(ImAndExFieldSettingActivity.this, AlertUtil.getString(R.string.has_exists_field), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        exportStrArr[position] = inputServer.getText().toString().trim();
        /*修改表头*/
        modifyAllTitle(inputServer, position);
        importStrArrayList.set(position, inputServer.getText().toString().trim());
        imAndExFieldSettingAadpter.notifyDataSetChanged();
        str = null;
        if (position > 3 && position < 15) {
            strru = null;
        }
        for (int i = 0; i < exportStrArr.length; i++) {
            //修改导出串
            if (str != null) {
                str = str + exportStrArr[i];
            } else {
                str = exportStrArr[i];
            }
            str = str + ",";
            //修改导入串
            if (i > 3 && i < 15) {
                if (strru != null) {
                    strru = strru + exportStrArr[i];
                } else {
                    strru = exportStrArr[i];
                }
                strru = strru + ",";
            }
        }
        str = str.substring(0, str.length() - 1);
        sp.edit().putString(ConfigEntity.ExportStrKey, str).commit();
        strru = AlertUtil.getString(R.string.import_base_field) + strru;
        strru = strru.substring(0, strru.length() - 1);
        sp.edit().putString(ConfigEntity.ImportStrKey, strru).commit();


        Toast.makeText(ImAndExFieldSettingActivity.this, R.string.modify_successed, Toast.LENGTH_SHORT).show();
        KeyboardUtils.hideSoftInput(view);
        strru = null;
        alertDialog.dismiss();

    }
    /**修改所有模式的导出表头*/
    protected void modifyAllTitle(EditText inputServer, int position) {
        modifyTitle(strInvenExportDatasTitle,exportInvenDatasTitle,"ExportDatasTitle",inputServer, position);
        modifyTitle(strInExportDatasTitle,exportInDatasTitle,"ExportDatasInTitle",inputServer, position);
        modifyTitle(strOutExportDatasTitle,exportOutDatasTitle,"ExportDatasOutTitle",inputServer, position);
    }

    private void modifyTitle(String[] strData,String title,String spTitle,EditText inputServer, int position) {
        title = "";
        for (int j = 0; j < strData.length; j++) {
            if(importStrArrayList.get(position).equals(strData[j])){
                strData[j] = inputServer.getText().toString().trim();
            }
            title+=strData[j]+",";
        }
        String strInvenTitle = title.substring(0,
                title.length() - 1);
        sp.edit().putString(spTitle, strInvenTitle)
                .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
