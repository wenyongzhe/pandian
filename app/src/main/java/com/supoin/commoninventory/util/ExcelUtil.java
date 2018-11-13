package com.supoin.commoninventory.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtil {
	// �ڴ��ַ
	public static String root = Environment.getExternalStorageDirectory()
			.getPath();

	public static void writeExcel(Context context,String[] strExportDatasTitle, List<String[]> exportOrder,
			String fileName,String exportPath) throws Exception {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)
				&& getAvailableStorage() > 1000000) {
			Toast.makeText(context, "SD��������", Toast.LENGTH_LONG).show();
			return;
		}
		String[] title = strExportDatasTitle;
		File file;
		File dir = new File(exportPath);
		file = new File(dir, fileName);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// ����Excel������
		WritableWorkbook wwb;
		OutputStream os = new FileOutputStream(file);
		wwb = Workbook.createWorkbook(os);
		// ��ӵ�һ�����������õ�һ��Sheet������
		WritableSheet sheet = wwb.createSheet("����", 0);
		Label label;
		if(strExportDatasTitle!=null){//i+1 �б�ͷ
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z) ����Ԫ��ĵ�x+1�У���y+1��, ����z
				// ��Label������Ӷ�����ָ����Ԫ���λ�ú�����
				label = new Label(i, 0, title[i], getHeader());
				// ������õĵ�Ԫ����ӵ���������
				sheet.addCell(label);
			}
			for (int i = 0; i < exportOrder.size(); i++) {//i����
				String[] strings = exportOrder.get(i);
				int column = 0;
				while(column<strings.length){
					sheet.addCell(new Label(column, i + 1, strings[column]));//i+1 ��ͷ
					sheet.setColumnView(column, 21); //�п�
					column++;
				}
			}
		}else{//i �ޱ�ͷ
			for (int i = 0; i < exportOrder.size(); i++) {//i����
				String[] strings = exportOrder.get(i);
				int column = 0;
				while(column<strings.length){
					sheet.addCell(new Label(column, i, strings[column]));//i �ޱ�ͷ
					sheet.setColumnView(column, 21); //�п�
					column++;
				}
			}
		}
	
		
		// д������
		wwb.write();
		// �ر��ļ�
		wwb.close();
	}

	public static WritableCellFormat getHeader() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 10,
				WritableFont.BOLD);// ��������
		try {
			font.setColour(Colour.BLACK);// ��ɫ����
		} catch (WriteException e1) {
			e1.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// ���Ҿ���
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// ���¾���
			// format.setBorder(Border.ALL, BorderLineStyle.THIN,
			// Colour.BLACK);// ��ɫ�߿�
			// format.setBackground(Colour.YELLOW);// ��ɫ����
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;
	}

	/** ��ȡSD�������� */
	private static long getAvailableStorage() {

		StatFs statFs = new StatFs(root);
		long blockSize = statFs.getBlockSize();
		long availableBlocks = statFs.getAvailableBlocks();
		long availableSize = blockSize * availableBlocks;
		// Formatter.formatFileSize(context, availableSize);
		return availableSize;
	}
}
