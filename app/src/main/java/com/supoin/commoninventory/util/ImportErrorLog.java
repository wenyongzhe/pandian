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
    /// 创建错误文件路径
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
        	String fileName=String.format("%s%s", err_file_path, "导入失败记录");
        	File file=new File(fileName);
            if (!file.exists())
            {
            	file.mkdir();  
            }
        }
        catch (Exception ex)
        {
            Utility.deBug("创建错误文件出错", ex.toString());
        }
    }
    /// <summary>
    /// 仅写错误记录，不记录标题、时间
    /// </summary>
    /// <param name="strError"></param>
    public void WriteErrorWithoutTime(String strError)
    {
        try
        {
            InitErrorFile();
            String filePath = err_file_path+String.format("导入失败记录/Error_%s.txt", Utility.getCurDate());
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
            Utility.deBug("写入错误数据出错", ex.toString());
        }
        finally
        {
           
        }
    }
    /// <summary>
    /// 写错误记录，不含标题，含时间
    /// </summary>
    /// <param name="strError">错误记录内容</param>
    public void WriteError(String strError)
    {
        try
        {
            InitErrorFile();
            String filePath = err_file_path+String.format("导入失败记录/Error_%s.txt", Utility.getCurDate());
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
            Utility.deBug("写入错误数据出错", ex.toString());
        }
        finally
        {
           
        }
    }
    /// <summary>
    /// 写错误记录，含标题、时间
    /// </summary>
    /// <param name="strTitle">标题</param>
    /// <param name="strError">错误记录内容</param>
    public void WriteError(String strTitle, String strError)
    {
        try
        {
            InitErrorFile();
            String filePath = err_file_path+String.format("导入失败记录/Error_%s.txt", Utility.getCurDate());
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
        	 Utility.deBug("写入错误数据出错", ex.toString());
        }
        finally
        {
           
        }
    }
}