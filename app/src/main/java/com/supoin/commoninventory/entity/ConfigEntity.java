package com.supoin.commoninventory.entity;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.util.AlertUtil;

import android.R.bool;

public class ConfigEntity {

	// Զ��ͬ�� AgentURL

	public static String AgentURL = "http://sp:9012/";
	public static String AgentURLKey = "AgentURL";

	// �����¼���û���

	public static String UserId = "";
	public static String UserIdKey = "UserId";

	// ��ʼ��ģʽ

	public static Boolean IsInit = false;
	public static String IsInitKey = "IsInit";

	// HttpDownload��ʽ����ʱ�������*.zip�ļ�

	public static String ZipFile = "";
	public static String ZipFileKey = "ZipFile";

	// ��ʼ������
	public static String PwInit = "123456";
	public static String PwInitKey = "PwInit";
	public static int PwTipInit = 0;
	public static String PwTipsKey = "PwTipsKey";
	public static String pwTipsAnswer = "pwTipsAnswer";

	// ΨһID

	public static String POSID = "45e7f011-5932-49ba-82f4-6e49db8323fb";
	public static String POSIDKey = "POSID";
	// ���봮
//	public static String ImportStr = "�ⲿ��,�ڲ���,����,��ʽ,����,��ɫID,��ɫ,����ID,����,����,С��,���,�۸�";
	public static String ImportStr = AlertUtil.getString(R.string.ImportStr);
	public static String ImportStrKey = "ImportStr";
	// ������
//	public static String ExportStr = "�ŵ���,���,��λ,����,����,��ʽ,����,��ɫID,��ɫ,����ID,����,����,С��,���,�۸�,����,ʱ��";
	public static String ExportStr = AlertUtil.getString(R.string.ExportStr);
	public static String ExportStrKey = "ExportStr";

	// / Ȩ�޵ȼ�,1Ϊ���ع���Ա��0Ϊ��ͨȨ�޲�����ʾ�������ģ�飬2Ϊ��������Ա������ʾ�������ģ��
	public static String RightLevelStr = "0";
	public static String RightLevelKey = "RightLevel";
	// ����������ʽ��0Ϊ�����������1Ϊ���ڲ������

	public static String UnionType = "0";
	public static String UnionTypeKey = "UnionType";

	// ɾ����ʽ��0Ϊ�����ɾ����1Ϊ����ţ���λɾ��

	public static String DeleteType = "1";
	public static String DeleteTypeKey = "DeleteType";

	// ϵͳ�������ɫRGB���м��Զ��ŷָ�

	public static String InterfaceRGB = "100, 80, 156";
	public static String InterfaceRGBKey = "InterfaceRGB";

	// �̵�ָ��������м���(�ָ�

	public static String SeparatorStr = "�Ʊ��(�ո�(,(|(#(;($(~";
	public static String SeparatorStrKey = "SeparatorStr";
	// ����ָ��������м���(�ָ�
	
	public static String SeparatorInStr = "�Ʊ��(�ո�(,(|(#(;($(~";
	public static String SeparatorInStrKey = "SeparatorStr";
	// �����ָ��������м���(�ָ�
	
	public static String SeparatorOutStr = "�Ʊ��(�ո�(,(|(#(;($(~";
	public static String SeparatorOutStrKey = "SeparatorStr";

	// ����ɨ�������ʾѡ���������ֵ
//	�ⲿ��,�ڲ���,����,��ʽ,����,��ɫID,��ɫ,����ID,����,����,С��,���,�۸�
	// 3:����ʾ,�������õ�����ʾ,2:��ʾ�����ҵ���,1:ֻ��ʾ,0:����ʾ
	public static String DisplayItems = "0,0,0,0,1,0,1,0,1,0,0,0,0";
	public static String DisplayItemsKey = "DisplayItems";
	
	//���
	public static String DisplayItemsIn = "0,0,0,0,1,0,1,0,1,0,0,0,0";
	public static String DisplayItemsInKey = "DisplayItemsIn";
	//����
	public static String DisplayItemsOut = "0,0,0,0,1,0,1,0,1,0,0,0,0";
	public static String DisplayItemsOutKey = "DisplayItemsOut";
	

	// �������η�
	public static String ImportDecaration = "��,˫����";
	public static String ImportDecarationKey = "ImportDecaration";

	// �������η���
	public static String ImportDecaIndex = "0";
	public static String ImportDecaIndexKey = "ImportDecaIndex";

	// �������η�

	public static String ExportDecaration = "��,˫����";
	public static String ExportDecarationKey = "ExportDecaration";

	// �̵㵼�����η������������,�ָ��������Ƿ�Ҫ���һλΪ1��ʾ���ݰ������η������򲻰���
	public static String ExportDecaIndex = "0,0";
	public static String ExportDecaIndexKey = "ExportDecaIndex";
	// ��⵼�����η������������,�ָ��������Ƿ�Ҫ���һλΪ1��ʾ���ݰ������η������򲻰���
	public static String ExportDecaInIndex = "0,0";
	public static String ExportDecaInIndexKey = "ExportDecaInIndex";
	// ���⵼�����η������������,�ָ��������Ƿ�Ҫ���һλΪ1��ʾ���ݰ������η������򲻰���
	public static String ExportDecaOutIndex = "0,0";
	public static String ExportDecaOutIndexKey = "ExportDecaOutIndex";

	// �Ƿ��滻���DB��Ϊ1��ʾ��Ҫ�滻���滻��ɣ�����Ϊ0��

	public static String ReplaceIndirDB = "0";
	public static String ReplaceIndirDBKey = "ReplaceIndirDB";
	public static String ConfigureItem = "141349152831731037232739";
	public static String ConfigureItemKey = "ConfigureItem";
	// ˢ�� ѡ���ѯ����

	public static String RefreshUI = "0";
	public static String RefreshUIKey = "RefreshUI";

	/*
	 * ɨ���������
	 */
	// ��������֤��ʽ 0����֤��1��֤��2��ʾ
	public static String BarCodeAuth = "0";
	public static String BarCodeAuthKey = "BarCodeAuth";
	// ����Ψһ����0�����ã�1����
	public static String UsingBarCode = "0";
	public static String UsingBarCodeKey = "UsingBarCode";
	// �Ƿ��������� 0���룬1������
	public static String IsPutInNum = "1";
	public static String IsPutInNumKey = "IsPutInNum";
	// �޸������Ƿ���Ҫ�������룬0��ʾ����Ҫ��1��ʾ��Ҫ
	public static String ModifyNumPW = "0";
	public static String ModifyNumPWKey = "ModifyNumPW";
	//0�ۼ���ʾ ,1������ʾ
	public static String ScanningShowMode = "0";
	public static String ScanningShowModeKey = "ScanningShowMode";
	// �ڲ�����ⲿ�� 0�ڲ��룬1�ⲿ��
	public static String InOutCode = "0";
	public static String InOutCodeKey = "InOutCode";
	// ɨ����ʾ����
	public static String ScanningShowLineNumb = "30";
	public static String ScanningShowLineNumbKey = "ScanniinngShowLineNumb";

	/*
	 * �������ý���
	 */
	// �������Ʒ�ʽ 0�����Ƴ��ȣ�1�������ƣ�2��Χ����
	public static String LengthLimit = "0";
	public static String LengthLimitKey = "LengthLimit";
	// �������� 6������
	public static String LengthEqualToLimit1 = "0";
	public static String LengthEqualToLimit1Key = "LengthEqualToLimit1";
	public static String LengthEqualToLimit2 = "0";
	public static String LengthEqualToLimit2Key = "LengthEqualToLimit2";
	public static String LengthEqualToLimit3 = "0";
	public static String LengthEqualToLimit3Key = "LengthEqualToLimit3";
	public static String LengthEqualToLimit4 = "0";
	public static String LengthEqualToLimit4Key = "LengthEqualToLimit4";
	public static String LengthEqualToLimit5 = "0";
	public static String LengthEqualToLimit5Key = "LengthEqualToLimit5";
	public static String LengthEqualToLimit6 = "0";
	public static String LengthEqualToLimit6Key = "LengthEqualToLimit6";
	// ��Χ���� Сֵ ��ֵ
	public static String LengthLimitRangeMin = "0";
	public static String LengthLimitRangeMinKey = "LengthLimitRangeMin";
	public static String LengthLimitRangeMax = "0";
	public static String LengthLimitRangeMaxKey = "LengthLimitRangeMax";
	// ��ȥ�������� "0������","1��ȥ�������λ��","2��ȥ����ǰ��λ��","3�����������λ��","4��������ǰ��λ��"
	public static String LengthCutOrKeepBarCode = "0";
	public static String LengthCutOrKeepBarCodeKey = "LengthCutOrKeepBarCode";
	// ��ȥ ����λ��
	public static String LengthCutOrKeepBarCodeNum = "1";
	public static String LengthCutOrKeepBarCodeNumKey = "LengthCutOrKeepBarCodeNum";
	/*
	 * �����ֽ���
	 */
	// ѡ�������ַ�ʽ
	public static String BarCodeCutSetting = "0";
	public static String BarCodeCutSettingKey = "BarCodeCutSetting";

	// ��ɫλ�� ˳��
	public static String StyleBitLength = "0";
	public static String StyleBitLengthKey = "StyleBitLength";
	public static String StyleBitLengthOrder = "1";
	public static String StyleBitLengthOrderKey = "StyleBitLengthOrder";
	// ��ɫλ�� ˳��
	public static String ColorBitLength = "0";
	public static String ColorBitLengthKey = "ColorBitLength";
	public static String ColorBitLengthOrder = "0";
	public static String ColorBitLengthOrderKey = "ColorBitLengthOrder";
	// ����λ�� ˳��
	public static String SizeBitLength = "0";
	public static String SizeBitLengthKey = "SizeBitLength";
	public static String SizeBitLengthOrder = "0";
	public static String SizeBitLengthOrderKey = "SizeBitLengthOrder";

	/*
	 * ���ţ���λ������
	 */
	// ���ţ���λ�� ˳��
	public static String GoodIdBitLength = "0";
	public static String GoodIdBitLengthKey = "GoodIdBitLength";
	public static String GoodIdBitLengthOrder = "1";
	public static String GoodIdBitLengthOrderKey = "GoodIdBitLengthOrder";
	// ����λ�� ˳��
	public static String SizeBitLength0 = "0";
	public static String SizeBitLength0Key = "SizeBitLength0";
	public static String SizeBitLength0Order = "2";
	public static String SizeBitLength0OrderKey = "SizeBitLength0Order";
	/*
	 * ��ɫ��ָ�������
	 */
	// ��ʽ�ָ��� ˳��
	public static String StyleSeparator = "-";
	public static String StyleSeparatorKey = "StyleSeparator";
	public static String StyleSeparatorOrder = "1";
	public static String StyleSeparatorOrderKey = "StyleSeparatorOrder";
	// ��ɫ�ָ��� ˳��
	public static String ColorSeparator = "-";
	public static String ColorSeparatorKey = "ColorSeparator";
	public static String ColorSeparatorOrder = "0";
	public static String ColorSeparatorOrderKey = "ColorSeparatorOrder";
	// ����ָ��� ˳��
	public static String SizeSeparator = "-";
	public static String SizeSeparatorKey = "SizeSeparator";
	public static String SizeSeparatorOrder = "0";
	public static String SizeSeparatorOrderKey = "SizeSeparatorOrder";

	/*
	 * ���ţ���ָ�������
	 */
	// ����ָ��� ˳��
	public static String GoodsIdSeparator2 = "-";
	public static String GoodsIdSeparator2Key = "GoodsIdSeparator2";
	public static String GoodsIdSeparatorOrder2 = "1";
	public static String GoodsIdSeparatorOrder2Key = "GoodsIdSeparatorOrde";
	public static String SizeSeparator2 = "-";
	public static String SizeSeparator2Key = "SizeSeparator2";
	public static String SizeSeparatorOrder2 = "2";
	public static String SizeSeparatorOrder2Key = "SizeSeparatorOrder2";

	/*
	 * �������ý���
	 */
	// �Ƿ񵼳� ��ͷ 0������ͷ 1��������ͷ
	public static String IsExportHeader = "0";
	public static String IsExportHeaderKey = "IsExportHeader";
	// �Ƿ񵼳�ɨ��˳��0Ϊ��������1Ϊ����
	public static String IsExportSO = "0";
	public static String IsExportSOKey = "IsExportSO";
	// ��������λ��0Ϊ����ŵ�����1Ϊ����ţ���λ���� <summary>
	public static String ExportType = "0";
	public static String ExportTypeKey = "ExportType";
	// �����ļ����ͣ�0txt,1csv���ͣ���ע��0
	public static String ExportFT = "0";
	public static String ExportFTKey = "ExportFT";
	// ȫ�������Ƿ�ϲ������0Ϊ�ϲ�������1Ϊȫ������ʱ�ֱ𵼳�0
	public static String MergeAllExport = "0";
	public static String MergeAllExportKey = "MergeAllExport";
	// ������ֵ�����0Ϊ����֣�1Ϊ���
	public static String ExportNumSplit = "0";
	public static String ExportNumSplitKey = "ExportNumSplit";
	// ���ϵ��뷽ʽ��0Ϊȫ�����루���ǵ��룩��1Ϊ��������
	public static String ExportWay = "0";
	public static String ExportWayKey = "ExportWay";
	// ������ʽ��0Ϊ����+������1Ϊ����+1
	public static String ExportMode= "0";
	public static String ExportModeKey = "ExportModeKey";
	/*
	 * ��������
	 */

	// ϵͳ�ŵ��� ����
	public static String ShopID = "000000";
	public static String ShopIDKey = "ShopID";
	// ϵͳ�ͻ���������
	public static String ShopName = "����Ƽ�";
	public static String ShopNameKey = "ShopName";
	// �Զ�����ϵͳ ʱ������0������1����
	public static String ShowSettingTime = "1";
	public static String ShowSettingTimeKey = "ShowSettingTime";
	// �̵��Ƿ�ر�WIFI "0��", "1��"
	public static String IsCloseWIFI = "0";
	public static String IsCloseWIFIKey = "IsCloseWIFI";

	// ɾ�����ݹ���λ0�����ɾ�� 1����ţ���λɾ��
	public static String DeleteDataWay = "1";
	public static String DeleteDataWayKey = "DeleteDataWay";
	// ɾ������������� 0����Ҫ 1��Ҫ
	public static String DeleteDataPSW = "0";
	public static String DeleteDataPSWKey = "DeleteDataPSW";
	// �����Ƿ�����С�� 0������  1����1λ 2����2λ
	public static String IsDataDecimalPoint = "0";
	public static String IsDataDecimalPointKey = "IsDataDecimalPoint";
	// �˳�ϵͳ������� 0������ 1����
	public static String ExitSystemPSW = "0";
	public static String ExitSystemPSWKey = "ExitSystemPSW";

	/*
	 * ������ʽѡ�����
	 */
	// ����ǰ׺/summary>
	public static String ExportPrefix = AlertUtil.getString(R.string.bill_inventory);
	public static String ExportPrefixKey = "ExportPrefix";
	// ������������ʽ0("Ĭ�ϵ�������ʽ");1("Ĭ�ϵ�������ʽ+_(����������)");2("Ĭ�ϵ�������ʽ+_������");3("Ĭ�ϵ�������ʽ+_������+_(����������)");
	//	4("Ĭ�ϵ�������ʽ+_������ʱ����");5("Ĭ�ϵ�������ʽ+_������ʱ����+_(����������)");6("ǰ׺++_������");7("ǰ׺++_������+_(����������)");
	//	8("ǰ׺++_������ʱ����");9("ǰ׺++_������ʱ����+_(����������)");
	// ��⵼��ǰ׺/summary>
	public static String ExportInPrefix = AlertUtil.getString(R.string.bill_instock);
	public static String ExportInPrefixKey = "ExportInPrefix";
	// ���⵼��ǰ׺/summary>
	public static String ExportOutPrefix = AlertUtil.getString(R.string.bill_outstock);
	public static String ExportOutPrefixKey = "ExportOutPrefix";
	public static String ExportNameWay = "0";
	public static String ExportNameWayKey = "ExPrefixAndIndex";

	// ɾ�������Ƿ���Ҫ�������룬0��ʾ����Ҫ��1��ʾ��Ҫ

	public static String DeletePW = "1";
	public static String DeletePWKey = "DeletePW";

	// �Ƿ��ǲ��԰棬1Ϊ�ǣ��ǵĻ����ڵ�¼��ʱ����ʾϵͳΪ���԰棬����Ļ�����ʾ

	public static String IsForTest = "0";
	public static String IsForTestKey = "IsForTest";

	// ����һ��Indir���ݿ���õ�λ�ã�F����Flash���棬S����Storage Card���档

	public static String FlashOrST = "F";
	public static String FlashOrSTKey = "FlashOrST";

	// �Ƿ���������ϣ�1Ϊ�ǣ�0Ϊ������

	public static String IsImportBasedata = "1";
	public static String IsImportBasedataKey = "IsImportBasedata";

	// �����ļ��Ƿ���̧ͷ��1Ϊ�У�0Ϊ��

	public static String ImportTopTitle = "1";
	public static String ImportTopTitleKey = "ImportTopTitle";

	// ��ǰ�ͻ�ʹ�õ�ERP

	public static String ERP = "000000";
	public static String ERPKey = "ERP";

	// �����Ե�Ѹ��ERP���õ���Ϣ

	//
	public static String XunerInfo = "5,30,5,8";
	public static String XunerInfoKey = "XunerInfo";

	// �����ԵĹܼ���ERP���õ���Ϣ
	public static String GraspInfo = "000000,����Ƽ�";
	public static String GraspInfoKey = "GraspInfo";

	// �������ϵ��룬�Ƿ���Ҫ����������뷽ʽ,0Ϊ����ӣ�1Ϊ��ӡ��ù����ڳ����ϱ���Ϊ���ѡ������ӣ����ڵ���ʱ���ᵯ������ȫ���ķ�ʽ��ѡ��

	public static String AddImportIncreWay = "0";
	public static String AddImportIncreWayKey = "AddImportIncreWay";

	// ��¼�ϴ��û�ѡ��ĵ���������Ϸ�ʽ��0Ϊ���ǵ��룬1Ϊ��������

	public static String LastImportWay = "0";
	public static String LastImportWayKey = "LastImportWay";

	// �˳��Ƿ���Ҫ���룬0����Ҫ��1��Ҫ

	public static String ExitPwd = "0";
	public static String ExitPwdKey = "ExitPwd";

	// ���������Ƿ��֣�0����֣�1���

	public static String SplitQty = "0";
	public static String SplitQtyKey = "SplitQty";

	// ��ǰϵͳɨ�������ļ�������Ϊ��׼ϵͳ������������Ϊ����׼ϵͳ������������Ϊָ���������ļ��µ��ļ�����

	public static String ScanSound = "��׼ϵͳ����";
	public static String ScanSoundKey = "ScanSound";

	//
	// ɨ��Ϊ���Ƿ���Ҫ����0Ϊ����Ҫ��1Ϊ��Ҫ,��ѡ���Ѿ�ȡ����

	// �Ƿ񵯳�����ʱ�䴰��.1Ϊ���ã����������������������.

	public static String SetTime = "0";
	public static String SetTimeKey = "SetTime";

	// ǹ�汾��Ϣ

	public static String PDAVersion = "1";
	public static String PDAVersionKey = "PDAVersion";

	// ɨ��ʱ���������ͷ���������
	// 0��������
	// 1�������
	// 2����������
	// 3����+���
	// 4������+���
	// 5�ر�
	// -1Ϊ��ȡ������ֵ

	public static String ScanSoundType = "3";
	public static String ScanSoundTypeKey = "ScanSoundType";

	// ���,����С����λ��,0��ʾ���������,1��2�ֱ��ʾ����С�����1λ��2λ��20131017 Added

	public static String DotCounter = "0";
	public static String DotCounterKey = "DotCounterKey";

	// RFID��أ���|�ָ��������ֱ��ʾ��ɨ�跽ʽ��0��ͨɨ�裬1RFIDɨ��
	// �����������ã�0����1�У�2Զ
	// ɨ��ģʽ���ã�0����ɨ�裬1���������2Ϊ���ȫ��
	// ɨ���������ã�0ΪEPC��1ΪTID
	// ʵ��ɨ�跽ʽ���ã�0Ϊ�ֶ������ɨ�裬1Ϊ����ɨ�跽ʽ

	public static String RFIDCorresponding = "1|2|0|1|0";
	public static String RFIDCorrespondingKey = "RFIDCorresponding";

	// ���뵼�����ý���
	public static String inputOutputSetting;


	// Ѹ������
	public static String XunerSetting1 = "0";
	public static String XunerSetting1Key = "XunerSetting1";
	public static String XunerSetting2 = "0";
	public static String XunerSetting2Key = "XunerSetting2";
	public static String XunerSetting3 = "0";
	public static String XunerSetting3Key = "XunerSetting3";
	public static String XunerSetting4 = "0";
	public static String XunerSetting4Key = "XunerSetting4";
	
	// �ܼ�������
	public static String GuanJiaPoshopNo = "";
	public static String GuanJiaPoshopNoKey = "GuanJiaPoshopNo";
	public static String GuanJiaPoshopName = "";
	public static String GuanJiaPoshopNameKey = "GuanJiaPoshopName";

	// erp����,0Ϊ������,1ΪѸ��,2Ϊ�ܼ���,0ΪĬ������
	public static String ERPSetting = "0";
	public static String ERPSettingKey = "ERPSetting";

	// ɨ����ʾ
	public static String scanDisplay = "scanDisplay";
	public static boolean scanDisplayKey;
	
	// ��Ʒ���
	public static String goods = "goods";
	public static boolean goodsKey;
	public static String goodsDisPlayOnline = "goodsDisPlayOnline";
	public static boolean goodsDisPlayOnlineKey;

	public static String style = "style";
	public static boolean styleKey;
	public static String styleDisPlayOnline = "styleDisPlayOnline";
	public static boolean styleDisPlayOnlineKey;

	public static String name = "name";
	public static boolean nameKey;
	public static String nameDisPlayOnline = "nameDisPlayOnline";
	public static boolean nameDisPlayOnlineKey;

	public static String color = "color";
	public static boolean colorKey;
	public static String colorDisPlayOnline = "colorDisPlayOnline";
	public static boolean colorDisPlayOnlineKey;

	public static String colorId = "colorId";
	public static boolean colorIdKey;
	public static String colorIdDisPlayOnline = "colorIdDisPlayOnline";
	public static boolean colorIdDisPlayOnlineKey;

	public static String size = "size";
	public static boolean sizeKey;
	public static String sizeDisPlayOnline = "sizeDisPlayOnline";
	public static boolean sizeDisPlayOnlineKey;

	public static String sizeId = "sizeId";
	public static boolean sizeIdKey;
	public static String sizeIdDisPlayOnline = "sizeIdDisPlayOnline";
	public static boolean sizeIdDisPlayOnlineKey;

	public static String bigSort = "bigSort";
	public static boolean bigSortKey;
	public static String bigSortDisPlayOnline = "bigSort";
	public static boolean bigSortDisPlayOnlineKey;

	public static String smallSort = "smallSort";
	public static boolean smallSortKey;
	public static String smallSortDisPlayOnline = "smallSortDisPlayOnline";
	public static boolean smallSortDisPlayOnlineKey;

	public static String quantity = "quantity";
	public static boolean quantityKey;
	public static String quantityDisPlayOnline = "quantityDisPlayOnline";
	public static boolean quantityDisPlayOnlineKey;

	public static String price = "price";
	public static boolean priceKey;
	public static String priceDisPlayOnline = "priceDisPlayOnline";
	public static boolean priceDisPlayOnlineKey;

	// ϵͳ��������
	public static final String SYSTEM_UPDATE_URL = "SYSTEM_UPDATE_URL";
	public static final String SYSTEM_UPDATE_ENTERPRISE_ID = "SYSTEM_UPDATE_ENTERPRISE_ID";
	public static final String SYSTEM_UPDATE_PROMPT = "SYSTEM_UPDATE_PROMPT";// true/false
	
	/*
	 * ɨ��ģʽ����
	 */
	// ɨ��ģʽ 0���룬1RFID
	public static String ScanPattern = "0";
	public static String ScanPatternKey = "ScanPattern";
	
	

}