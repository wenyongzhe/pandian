package com.supoin.commoninventory.publicontent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;

import com.blankj.utilcode.util.FileUtils;
import com.supoin.commoninventory.R;
import com.supoin.commoninventory.constvalue.SystemConst;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.FileUtility;
import com.supoin.commoninventory.util.Utility;

import java.io.File;
import java.io.IOException;

public class SettingLoad{
	
	private static SharedPreferences sp;
	private static SQLStockDataSqlite sqlStockDataSqlite;

	private static Context context1;
	public static  void SettingLoad(Context context,Boolean is_first,int mode){
		context1 = context;
		try {
			CreatePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sp = context.getSharedPreferences("InvenConfig",mode);
		if(is_first){
			sp.edit().putBoolean("is_first", true)
			.putString(ConfigEntity.AddImportIncreWayKey, ConfigEntity.AddImportIncreWay)
			.putString(ConfigEntity.AgentURLKey, ConfigEntity.AgentURL)
			.putString(ConfigEntity.ConfigureItemKey, Utility.getRadomString())
			.putString(ConfigEntity.DeletePWKey, ConfigEntity.DeletePW)
			.putString(ConfigEntity.DeleteTypeKey, ConfigEntity.DeleteType)
			.putString(ConfigEntity.DisplayItemsKey, ConfigEntity.DisplayItems)
			.putString(ConfigEntity.DisplayItemsInKey, ConfigEntity.DisplayItemsIn)
			.putString(ConfigEntity.DisplayItemsOutKey, ConfigEntity.DisplayItemsOut)
			.putString(ConfigEntity.DotCounterKey, ConfigEntity.DotCounter)
			.putString(ConfigEntity.ERPKey, ConfigEntity.ERP)
			.putString(ConfigEntity.ExitPwdKey, ConfigEntity.ExitPwd)
			.putString(ConfigEntity.ExportDecaIndexKey, ConfigEntity.ExportDecaIndex)
			.putString(ConfigEntity.ExportDecaInIndexKey, ConfigEntity.ExportDecaInIndex)
			.putString(ConfigEntity.ExportDecaOutIndexKey, ConfigEntity.ExportDecaOutIndex)
			.putString(ConfigEntity.ExportDecarationKey, ConfigEntity.ExportDecaration)
			.putString(ConfigEntity.ExportFTKey, ConfigEntity.ExportFT)
			.putString(ConfigEntity.ExportStrKey, ConfigEntity.ExportStr)
			.putString(ConfigEntity.ExportTypeKey, ConfigEntity.ExportType)
//			.putString(ConfigEntity.ExPrefixAndIndexKey, ConfigEntity.ExPrefixAndIndex)
			.putString(ConfigEntity.FlashOrSTKey, ConfigEntity.FlashOrST)
			.putString(ConfigEntity.GraspInfoKey, ConfigEntity.GraspInfo)
			.putString(ConfigEntity.ImportDecaIndexKey, ConfigEntity.ImportDecaIndex)
			.putString(ConfigEntity.ImportDecarationKey, ConfigEntity.ImportDecaration)
			.putString(ConfigEntity.ImportStrKey, ConfigEntity.ImportStr)
			.putString(ConfigEntity.ImportTopTitleKey, ConfigEntity.ImportTopTitle)
			.putString(ConfigEntity.InterfaceRGBKey, ConfigEntity.InterfaceRGB)
			.putString(ConfigEntity.IsExportSOKey, ConfigEntity.IsExportSO)
			.putString(ConfigEntity.IsForTestKey, ConfigEntity.IsForTest)
			.putString(ConfigEntity.IsImportBasedataKey, ConfigEntity.IsImportBasedata)
			.putBoolean(ConfigEntity.IsInitKey, ConfigEntity.IsInit)
			.putString(ConfigEntity.LastImportWayKey, ConfigEntity.LastImportWay)
			.putString(ConfigEntity.MergeAllExportKey, ConfigEntity.MergeAllExport)
			
			
			.putString(ConfigEntity.RefreshUIKey, ConfigEntity.RefreshUI)
			.putString("ScrollToPosition", "")
			//	扫描设置
			.putString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth)
			.putString(ConfigEntity.UsingBarCodeKey, ConfigEntity.UsingBarCode)
			.putString(ConfigEntity.IsPutInNumKey, ConfigEntity.IsPutInNum)
			.putString(ConfigEntity.ModifyNumPWKey, ConfigEntity.ModifyNumPW)
			.putString(ConfigEntity.ScanningShowModeKey, ConfigEntity.ScanningShowMode)
			.putString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode)
			.putString(ConfigEntity.ScanningShowLineNumbKey, ConfigEntity.ScanningShowLineNumb)
			//长度设置
			.putString(ConfigEntity.LengthLimitKey, ConfigEntity.LengthLimit)
			.putString(ConfigEntity.LengthEqualToLimit1Key, ConfigEntity.LengthEqualToLimit1)
			.putString(ConfigEntity.LengthEqualToLimit2Key, ConfigEntity.LengthEqualToLimit2)
			.putString(ConfigEntity.LengthEqualToLimit3Key, ConfigEntity.LengthEqualToLimit3)
			.putString(ConfigEntity.LengthEqualToLimit4Key, ConfigEntity.LengthEqualToLimit4)
			.putString(ConfigEntity.LengthEqualToLimit5Key, ConfigEntity.LengthEqualToLimit5)
			.putString(ConfigEntity.LengthEqualToLimit6Key, ConfigEntity.LengthEqualToLimit6)
			.putString(ConfigEntity.LengthLimitRangeMinKey, ConfigEntity.LengthLimitRangeMin)
			.putString(ConfigEntity.LengthLimitRangeMaxKey, ConfigEntity.LengthLimitRangeMax)
			.putString(ConfigEntity.LengthCutOrKeepBarCodeKey, ConfigEntity.LengthCutOrKeepBarCode)
			.putString(ConfigEntity.LengthCutOrKeepBarCodeNumKey, ConfigEntity.LengthCutOrKeepBarCodeNum)
			//条码拆分方式
			.putString(ConfigEntity.BarCodeCutSettingKey, ConfigEntity.BarCodeCutSetting)
			// 款式位长 顺序 // 颜色位长 顺序  // 尺码位长 顺序
			.putString(ConfigEntity.StyleBitLengthKey, ConfigEntity.StyleBitLength)
			.putString(ConfigEntity.StyleBitLengthOrderKey, ConfigEntity.StyleBitLengthOrder)
			.putString(ConfigEntity.ColorBitLengthKey, ConfigEntity.ColorBitLength)
			.putString(ConfigEntity.ColorBitLengthOrderKey, ConfigEntity.ColorBitLengthOrder)
			.putString(ConfigEntity.SizeBitLengthKey, ConfigEntity.SizeBitLength)
			.putString(ConfigEntity.SizeBitLengthOrderKey, ConfigEntity.SizeBitLengthOrder)
			// 货号位长 顺序  //尺码位长 顺序
			.putString(ConfigEntity.GoodIdBitLengthKey, ConfigEntity.GoodIdBitLength)
			.putString(ConfigEntity.GoodIdBitLengthOrderKey, ConfigEntity.GoodIdBitLengthOrder)
			.putString(ConfigEntity.SizeBitLength0Key, ConfigEntity.SizeBitLength0)
			.putString(ConfigEntity.SizeBitLength0OrderKey, ConfigEntity.SizeBitLength0Order)
			
			// 款式分隔 顺序 // 颜色分隔 顺序  // 尺码分隔 顺序
			.putString(ConfigEntity.StyleSeparatorKey, ConfigEntity.StyleSeparator)
			.putString(ConfigEntity.StyleSeparatorOrderKey, ConfigEntity.StyleSeparatorOrder)
			.putString(ConfigEntity.ColorSeparatorKey, ConfigEntity.ColorSeparator)
			.putString(ConfigEntity.ColorSeparatorOrderKey, ConfigEntity.ColorSeparatorOrder)
			.putString(ConfigEntity.SizeSeparatorKey, ConfigEntity.SizeSeparator)
			.putString(ConfigEntity.SizeSeparatorOrderKey, ConfigEntity.SizeSeparatorOrder)
			// 货号分隔 顺序  //尺码分隔顺序
			.putString(ConfigEntity.GoodsIdSeparator2Key, ConfigEntity.GoodsIdSeparator2)
			.putString(ConfigEntity.GoodsIdSeparatorOrder2Key, ConfigEntity.GoodsIdSeparatorOrder2)
			.putString(ConfigEntity.SizeSeparator2Key, ConfigEntity.SizeSeparator2)
			.putString(ConfigEntity.SizeSeparatorOrder2Key, ConfigEntity.SizeSeparatorOrder2)
			
		
			//导出导入设置
			.putString(ConfigEntity.IsExportHeaderKey, ConfigEntity.IsExportHeader)
			.putString(ConfigEntity.IsExportSOKey, ConfigEntity.IsExportSO)
			.putString(ConfigEntity.ExportTypeKey, ConfigEntity.ExportType)
			.putString(ConfigEntity.ExportFTKey, ConfigEntity.ExportFT)
			.putString(ConfigEntity.MergeAllExportKey, ConfigEntity.MergeAllExport)
			.putString(ConfigEntity.ExportNumSplitKey, ConfigEntity.ExportNumSplit)
			.putString(ConfigEntity.ExportWayKey, ConfigEntity.ExportWay)
			.putString(ConfigEntity.ExportModeKey, ConfigEntity.ExportMode)
			//导出命名方式
			.putString(ConfigEntity.ExportPrefixKey, ConfigEntity.ExportPrefix)
			.putString(ConfigEntity.ExportNameWayKey, ConfigEntity.ExportNameWay)
			
			.putString(ConfigEntity.ExportInPrefixKey, ConfigEntity.ExportInPrefix)
			.putString(ConfigEntity.ExportOutPrefixKey, ConfigEntity.ExportOutPrefix)
			//其它设置
			.putString(ConfigEntity.ShopIDKey, ConfigEntity.ShopID)
			.putString(ConfigEntity.ShopNameKey, ConfigEntity.ShopName)
			.putString(ConfigEntity.ShowSettingTimeKey, ConfigEntity.ShowSettingTime)
			.putString(ConfigEntity.IsCloseWIFIKey, ConfigEntity.IsCloseWIFI)
			.putString(ConfigEntity.DeleteDataWayKey, ConfigEntity.DeleteDataWay)
			.putString(ConfigEntity.DeleteDataPSWKey, ConfigEntity.DeleteDataPSW)
			.putString(ConfigEntity.IsDataDecimalPointKey, ConfigEntity.IsDataDecimalPoint)
			.putString(ConfigEntity.ExitSystemPSWKey, ConfigEntity.ExitSystemPSW)		
			.putString(ConfigEntity.ERPSettingKey, ConfigEntity.ERPSetting)		
			.putString(ConfigEntity.PDAVersionKey, ConfigEntity.PDAVersion)
			.putString(ConfigEntity.POSIDKey, Utility.getGUUID())
			.putString(ConfigEntity.PwInitKey, ConfigEntity.PwInit)
			.putString(ConfigEntity.ReplaceIndirDBKey, ConfigEntity.ReplaceIndirDB)
			.putString(ConfigEntity.RFIDCorrespondingKey, ConfigEntity.RFIDCorresponding)
			.putString(ConfigEntity.ScanSoundKey, ConfigEntity.ScanSound)
			.putString(ConfigEntity.ScanSoundTypeKey, ConfigEntity.ScanSoundType)
			.putString(ConfigEntity.SeparatorStrKey, ConfigEntity.SeparatorStr)
			.putString(ConfigEntity.SetTimeKey, ConfigEntity.SetTime)
			.putString(ConfigEntity.ShopIDKey, ConfigEntity.ShopID)
			.putString(ConfigEntity.ShopNameKey, ConfigEntity.ShopName)
			.putString(ConfigEntity.SplitQtyKey, ConfigEntity.SplitQty)
			.putString(ConfigEntity.UnionTypeKey, ConfigEntity.UnionType)
			.putString(ConfigEntity.UserIdKey, ConfigEntity.UserId)
			.putString(ConfigEntity.XunerInfoKey, ConfigEntity.XunerInfo)
			.putString(ConfigEntity.ZipFileKey, ConfigEntity.ZipFile)
			.putString(ConfigEntity.GuanJiaPoshopNoKey, ConfigEntity.GuanJiaPoshopNo)
			.putString(ConfigEntity.GuanJiaPoshopNameKey, ConfigEntity.GuanJiaPoshopName)
			.commit();
			
			sqlStockDataSqlite = new SQLStockDataSqlite(context,true);
			sqlStockDataSqlite.insertVerifyWayInfo();
		}
		String language = myApplication.mContext.getResources().getConfiguration().locale.getLanguage();
        if (language.endsWith("en")){
        	sp.edit()
        	.putString(ConfigEntity.ImportDecarationKey, AlertUtil.getString(R.string.importDecaration2))
        	.putString(ConfigEntity.SeparatorStrKey, AlertUtil.getString(R.string.separatorStr))
			.commit();
        }else{
        	sp.edit()
        	.putString(ConfigEntity.ImportDecarationKey, ConfigEntity.ImportDecaration)
        	.putString(ConfigEntity.SeparatorStrKey, ConfigEntity.SeparatorStr)
			.commit();
        }
	}
	 private static void CreatePath() throws IOException
     {

		 boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
         if (sdCardExist) {             
             File dir = Environment.getExternalStorageDirectory();            
             dir.mkdir();
             if(!dir.exists())
            	 dir.mkdirs();
         }
         String strSdcard = FileUtility.getSdCardPath();
         File file= new File(strSdcard);
         if (file.exists())
         {
			 FileUtils.createOrExistsDir(SystemConst.crashPath);
             file=new File(SystemConst.baseDataPath);
             if(!file.exists())
            	 file.mkdirs();
            
             File file4 = new File(SystemConst.updateFile);
             if (!file4.exists())
            	 file4.mkdirs();
             File file5 = new File(SystemConst.downloadPath);
             if (!file5.exists())
            	 file5.mkdirs();         
 			File filePath=new File(SystemConst.logPath);
 			if(!filePath.exists())
 				filePath.mkdirs();
             File file6 = new File(SystemConst.importPath);
             if(!file6.exists())
            	 file6.mkdirs();
         }
         else{
    		 AlertDialog dialog;
 			AlertDialog.Builder builder = new AlertDialog.Builder(
 					context1);
 			builder.setTitle("提示");
 			builder.setMessage("SD卡不存在!");
 			builder.setPositiveButton("确定",
 					new DialogInterface.OnClickListener() {
 						@Override
 						public void onClick(DialogInterface dialog, int which) {
 							// TODO Auto-generated method stub
 
 						}
 					});
 			builder.setNegativeButton("取消",
 					new DialogInterface.OnClickListener() {
 
 						@Override
 						public void onClick(DialogInterface dialog, int which) {
 							// TODO Auto-generated method stub
 						}
 					});
 			dialog = builder.create();
 			dialog.show();
 			return;
         }
     }
	
	public static Boolean getBoolean(String key){
		return sp.getBoolean(key, true);
	}
}