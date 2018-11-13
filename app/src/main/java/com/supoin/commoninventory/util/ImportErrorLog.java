package com.supoin.commoninventory.util;

import java.io.BufferedWriter;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.util.Xml.Encoding;

public class ImportErrorLog
{
    private String err_file_path ="";
    private OutputStreamWriter sw = null;

    /// <summary>
    /// ���������ļ�·��
    /// </summary>
    public String getErr_file_path() {
		return err_file_path;
	}
	public void setErr_file_path(String err_file_path) {
		this.err_file_path = err_file_path;
	}
	private void InitErrorFile()
    {
        try
        {
        	String fileName=String.format("%s%s", err_file_path, "����ʧ�ܼ�¼");
        	File file=new File(fileName);
            if (!file.exists())
            {
            	file.mkdir();  
            }
        }
        catch (Exception ex)
        {
            Utility.deBug("���������ļ�����", ex.toString());
        }
    }
    /// <summary>
    /// ��д�����¼������¼���⡢ʱ��
    /// </summary>
    /// <param name="strError"></param>
    public void WriteErrorWithoutTime(String strError)
    {
        try
        {
            InitErrorFile();
            String filePath = err_file_path+String.format("����ʧ�ܼ�¼/Error_%s.txt", Utility.getCurDate());
            File file=new File(filePath);
            if (!file.exists())
            {
            	file.createNewFile();
            }
            if (sw == null)
            {
            	OutputStream out=new FileOutputStream(filePath);
            	sw =new OutputStreamWriter(out);

            }
            sw.write(String.format("{0}", strError)+"\n");
            if (sw != null)
            {
            	sw.flush();  
                sw.close();
                sw = null;
            }
        }
        catch (Exception ex)
        {
            Utility.deBug("д��������ݳ���", ex.toString());
        }
        finally
        {
           
        }
    }
    /// <summary>
    /// д�����¼���������⣬��ʱ��
    /// </summary>
    /// <param name="strError">�����¼����</param>
    public void WriteError(String strError)
    {
        try
        {
            InitErrorFile();
            String filePath = err_file_path+String.format("����ʧ�ܼ�¼/Error_%s.txt", Utility.getCurDate());
            if (sw == null)
            {
            	OutputStream out=new FileOutputStream(filePath);
            	sw =new OutputStreamWriter(out);
            }
            sw.write(String.format("{0} {1}=>{2}", Utility.getCurTime(), "", strError)+"\n");
            if (sw != null)
            {
            	sw.flush();
                sw.close();
                sw = null;
            }
        }
        catch (Exception ex)
        {
            Utility.deBug("д��������ݳ���", ex.toString());
        }
        finally
        {
           
        }
    }
    /// <summary>
    /// д�����¼�������⡢ʱ��
    /// </summary>
    /// <param name="strTitle">����</param>
    /// <param name="strError">�����¼����</param>
    public void WriteError(String strTitle, String strError)
    {
        try
        {
            InitErrorFile();
            String filePath = err_file_path+String.format("����ʧ�ܼ�¼/Error_%s.txt", Utility.getCurDate());
            if (sw == null)
            {
            	OutputStream out=new FileOutputStream(filePath);
            	sw =new OutputStreamWriter(out);
            }
            sw.write(String.format("{0} {1}=>{2}", Utility.getCurTime(), strTitle, strError)+"\n");
            if (sw != null)
            {
            	sw.flush();
                sw.close();
                sw = null;
            }
        }
        catch (Exception ex)
        {
        	 Utility.deBug("д��������ݳ���", ex.toString());
        }
        finally
        {
           
        }
    }
}