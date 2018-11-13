package com.supoin.commoninventory.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipFile;
import android.graphics.Path;
import android.provider.MediaStore.Files;

/// <summary>
/// ѹ��������
/// </summary>
public class ZipCompress
{
	private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte
    private  int BLOCK = 2048;
	/* 
	 * Java�ļ����� ��ȡ�ļ���չ�� 
	 *  
	 */   
	    public static String getExtensionName(String filename) {    
	        if ((filename != null) && (filename.length() > 0)) {    
	            int dot = filename.lastIndexOf('.');    
	            if ((dot >-1) && (dot < (filename.length() - 1))) {    
	                return filename.substring(dot + 1);    
	            }    
	        }    
	        return filename;    
	    }    
	/* 
	 * Java�ļ����� ��ȡ������չ�����ļ��� 
	 */   
	    public static String getFileNameNoEx(String filename) {    
	        if ((filename != null) && (filename.length() > 0)) {    
	            int dot = filename.lastIndexOf('.');    
	            if ((dot >-1) && (dot < (filename.length()))) {    
	                return filename.substring(0, dot);    
	            }    
	        }    
	        return filename;    
	    }   
    /// <summary>
    /// ѹ���ļ�, ѹ���ļ�������Դ�ļ���+.zip
    /// </summary>
    /// <param name="sourceFile">Դ�ļ�����·��</param>
    /// <param name="desPath">ѹ����ŵ�Ŀ¼</param>
    /// <example>
    ///     <remarks>
    ///         ��C:\123.txtѹ������뵽C:\zipĿ¼�£��ļ���Ϊ123.zip
    ///     </remarks>
    ///     <code>        ///         
    ///         SharpZip.ZipFile("C:\\123.txt", "C:\\zip");     
    ///     </code>
    /// </example>
    /// <returns>��</returns>
    public static void ZipFile(String sourceFile, String desPath) throws IOException
    {	
    	File file = new File(sourceFile);
        if (!file.exists())
			try {
				throw new Exception("ָ����Դ�ļ�������!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        String sourceFileWithout = getFileNameNoEx(sourceFile);//Դ�ļ���������չ��
//        		Path.GetFileNameWithoutExtension(sourceFile);       //Դ�ļ���������չ��
        String zipFile = String.format("{0}/{1}.zip", desPath, sourceFileWithout);   //Ŀ���ļ�
        //ѹ���ļ���Ϊ��ʱʹ��Դ�ļ���+.zip
        File file1= new File(zipFile);
        if(!file1.exists())
        	file1.createNewFile();
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream s = new ZipOutputStream(new BufferedOutputStream(fos) );
        ZipEntry entry = null ;
//        while ((entry = s.getNextEntry()) != null){
        try {
            s.setLevel(9);      //����ѹ������0-9
            byte[] buffer = new byte[4096];
            String strEntry; //����ÿ��zip����Ŀ����
            strEntry = entry.getName();
            File entryFile = new File(desPath + strEntry);
	    	File entryDir = new File(entryFile.getParent());
	    	if (!entryDir.exists()) {
	    		entryDir.mkdirs();
	    	}
           entry = new ZipEntry(entryFile.getName());
            s.putNextEntry(entry);
//            using (FileStream fs = File.OpenRead(sourceFile))
            FileInputStream fs = new FileInputStream(sourceFile);
            {
                int soureBytes = 0;
                do
                {
                    soureBytes = fs.read(buffer, 0, buffer.length);
                    s.write(buffer, 0, soureBytes);
                } while (soureBytes > 0);
            }
        }
        catch (Exception ex)
        {
           ex.printStackTrace();
        }
        finally
        {
            s.finish();
            s.close();
        } 
    }

    /// <summary>
    /// ��ѹ���ļ�
    /// </summary>
    /// <param name="sourceZip">ѹ���ļ�ȫ����·��</param>
    /// <param name="unZipPath">��ѹ��Ŀ¼</param>
    /// <example>
    ///     <remarks>
    ///         ��C:\zip\123.zipѹ���ļ���ѹ����뵽C:\Ŀ¼��
    ///     </remarks>
    ///     <code>
    ///         SharpZip.UnZipFile("C:\\zip\\123.txt", "C:\\");     
    ///     </code>
    /// </example>
    /// <returns>��</returns>
    public static void UnZipFile(String sourceZip, String unZipPath) throws Exception
    {
		 File desDir = new File(unZipPath);
		  if(!desDir.exists())
		 {
			  desDir.mkdirs();
		 }
		  if(!desDir.getAbsoluteFile().exists())
		 {
			  desDir.getAbsoluteFile().mkdirs();
		 }
		 ZipFile zf = new ZipFile(sourceZip);  
		 for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) 
		 { 
		 ZipEntry entry = ((ZipEntry)entries.nextElement());  
		 InputStream in = zf.getInputStream(entry);  
		 String str = unZipPath + File.separator + entry.getName(); 
		 str = new String(str.getBytes("8859_1"), "GB2312");  
		 File desFile = new File(str); 
		 if(desFile.isDirectory()){
			 desFile.delete();
			 desFile.createNewFile();
		 }
		 if (!desFile.exists()) 
		 { 
			 File fileParentDir = desFile.getParentFile();  
			 if (!fileParentDir.exists()) 
			 { 
				 fileParentDir.mkdirs();  
			 }  
			 	desFile.createNewFile();  
			  }  
	          OutputStream out = new FileOutputStream(desFile);  
	         byte buffer[] = new byte[1024];  
	          int realLength;  
	          while ((realLength = in.read(buffer)) > 0) 
	          {
	            out.write(buffer, 0, realLength);  
	         }
	         in.close();  
	         out.close(); 
		 }  
//    	File file = new File(sourceZip);
//        if (!file.exists())
//            throw new Exception("ָ����zip�ļ�������!");
//        File fileDir = new File(unZipPath);
//        if(!fileDir.exists())
//        	fileDir.mkdirs();
//        FileInputStream fis = new FileInputStream(sourceZip);
//    	ZipInputStream s = new ZipInputStream(new BufferedInputStream(fis));
////        ZipInputStream s = new ZipInputStream(File.OpenRead(sourceZip));
//        try
//        {
//        	BufferedOutputStream dest = null; //���������
//            ZipEntry theEntry = s.getNextEntry();
//            int count; 
//	    	byte data[] = new byte[4096];
//            if (theEntry != null)
//            {
//                String strFile = String.format("{0}/{1}", unZipPath, theEntry.getName());
//                File file1 = new File(strFile);
//                if(!file1.exists())
//                	file1.delete();
//                FileOutputStream fos = new FileOutputStream(strFile);
//    	    	dest = new BufferedOutputStream(fos, 4096);
//    	    	while ((count = s.read(data, 0, 4096)) != -1) {
//    	    		dest.write(data, 0, count);
//    	    	}
//
//            }
//        }
//        catch (Exception ex)
//        {
//            throw ex;
//        }
//        finally
//        {
//            s.closeEntry();
//            s.close();
//        }
    }



    /// <summary>
    /// ѹ������ļ�
    /// </summary>
    /// <param name="fileList">��ѹ�����ļ��б�</param>
    /// <param name="zipFileName">����·����ѹ���ļ���</param>
    /// <param name="descPath">��ѹ��Ŀ¼</param>
    /// <param name="deleteSourceFile">�Ƿ�ɾ��ԭ�ļ�</param>
    /// <example>
    ///     <remarks>
    ///         ��1.txt,2.txt�����ļ�ѹ����t.zip������c:\zipĿ¼�¡�
    ///     </remarks>
    ///     <code>
    ///         List&lt;String&gt; fileList = new List&lt;String&gt;;
    ///         fileList.Add("C:\\1.txt");
    ///         fileList.Add("C:\\2.txt");
    /// 
    ///         SharpZip.ZipMutiFiles(fileList, "t.zip", "C:\\zip", true);  
    ///     </code>
    /// </example>
    /// <returns>�����ļ�����</returns>
    public static long ZipMutiFiles(List<String> fileList, String zipFileName, String descPath, Boolean deleteSourceFile) throws IOException
    {
//    	ZipOutputStream s = new ZipOutputStream(File.Create(String.format("{0}/{1}", descPath, zipFileName)));
    	String strFile=descPath+zipFileName;//String.format("{0}/{1}", descPath, zipFileName);
//    	File zipFile= new File(strFile);
//    	if(!zipFile.exists())
//    		zipFile.createNewFile();
    	FileOutputStream fos=new FileOutputStream(strFile);
        ZipOutputStream s = new ZipOutputStream(new BufferedOutputStream(fos));
        try
        {
            s.setLevel(9);      //����ѹ������0-9
            byte[] buffer = new byte[4096];
            BufferedOutputStream dest =null;
            for (String file : fileList)
            {
            	File strDir = new File(descPath);
            	
                if (!descPath.equals("") && !strDir.exists()) 
                	strDir.mkdirs();
                String filename = "";
                File file1;
                if(descPath.equals("")){
                	file1 = new File(file);
                	filename =file1.getName(); 
            	}else{
            		file1 = new File(file);
                	filename=descPath+file1.getName();//String.format("{0}/{1}", descPath, file1.getName());
                }
                file1= new File(filename);
//                zipFile(file1, zipout, rootpath)
                ZipEntry entry = new ZipEntry(file1.getName());
//                entry.DateTime = DateTime.Now;
                Date date=new Date();
//                entry.setTime(Long.parseLong(Helper.getdateandtimeby24(date)));
                s.putNextEntry(entry);
                FileInputStream fs = new FileInputStream(filename);
                int soureBytes = 0;
                while ((soureBytes = fs.read(buffer)) != -1) {
                    s.write(buffer, 0, soureBytes);
                }
                fs.close();
//                {
//                    do
//                    {
//                        soureBytes = fs.read(buffer, 0, buffer.length);
//                        s.write(buffer, 0, soureBytes);
//                    } while (soureBytes > 0);
//                    fs.close();
//                }
                if (deleteSourceFile) 
                	file1.delete();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            s.finish();
            s.close();
        }
        File f = new File(descPath+zipFileName);
//        FileInfo f = new FileInfo(String.Format(@"{0}\{1}", descPath, zipFileName));
        return f.length();
    }
    /// <summary>
    /// ��ѹ����ļ�
    /// </summary>
    /// <param name="zipFileName">ѹ���ļ�</param>
    /// <param name="descPath">��ѹĿ��·��</param>
    /// <param name="deleteZipFile">��ѹ���Ƿ�ɾ��ԭѹ���ļ�</param>
    /// <example>
    ///     <remarks>
    ///         ��t.zip�ļ���ѹ������c:\zipĿ¼�£���ɾ����ԭ����ѹ���ļ���
    ///     </remarks>
    ///     <code>
    ///         List&lt;String&gt; fileList = null;
    /// 
    ///         SharpZip.UnZipMutiFile("t.zip", "C:\\zip", true); 
    ///     </code>
    /// </example>
    /// <returns>���ؽ�ѹ�ļ��б�</returns>
    public static List<String> UnZipMutiFile(String zipFileName, String descPath, boolean deleteZipFile) throws Exception
    {
        List<String> fileList = new ArrayList<String>();
        File file = new File(zipFileName);
        if (!file.exists())
            throw new Exception("ָ����zip�ļ�������!");
        File strDir= new File(descPath);
        if (!strDir.exists())
        	strDir.mkdirs();
        FileInputStream fs= new FileInputStream(zipFileName);
        ZipInputStream s = new ZipInputStream(new BufferedInputStream(fs));
        try
        {
            ZipEntry theEntry = null;
            while ((theEntry = s.getNextEntry()) != null)
            {
                String strfile = String.format("{0}/{1}", descPath, theEntry.getName());
                File file1=new File(strfile);
                if (file1.exists())
                	file1.delete();
                FileOutputStream fos = new FileOutputStream(strfile);
                {
                    int length = 0;
                    byte[] data = new byte[2048];
                    while (true)
                    {
                        length = s.read(data, 0, data.length);
                        if (length > 0)
                        {
                            fos.write(data, 0, length);
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                fileList.add(strfile);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            s.close();
        }
        if (deleteZipFile)
        	file.delete();
        return fileList;
    }



  
    /**
     * ����ѹ���ļ����У�
     *
     * @param resFileList Ҫѹ�����ļ����У��б�
     * @param zipFile ���ɵ�ѹ���ļ�
     * @throws IOException ��ѹ�����̳���ʱ�׳�
     */
    public static void zipFiles(Collection<File> resFileList, File zipFile) throws IOException {
        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(
                zipFile), BUFF_SIZE));
        for (File resFile : resFileList) {
            zipFile(resFile, zipout, "");
        }
        zipout.close();
    }
 
    /**
     * ����ѹ���ļ����У�
     *
     * @param resFileList Ҫѹ�����ļ����У��б�
     * @param zipFile ���ɵ�ѹ���ļ�
     * @param comment ѹ���ļ���ע��
     * @throws IOException ��ѹ�����̳���ʱ�׳�
     */
    public static void zipFiles(Collection<File> resFileList, File zipFile, String comment)
            throws IOException {
        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(
                zipFile), BUFF_SIZE));
        for (File resFile : resFileList) {
            zipFile(resFile, zipout, "");
        }
        zipout.setComment(comment);
        zipout.close();
    }
 
  
 
    /**
     * ���ѹ���ļ����ļ��б�
     *
     * @param zipFile ѹ���ļ�
     * @return ѹ���ļ����ļ�����
     * @throws ZipException ѹ���ļ���ʽ����ʱ�׳�
     * @throws IOException ����ѹ�����̳���ʱ�׳�
     */
//    public static ArrayList<String> getEntriesNames(File zipFile) throws ZipException, IOException {
//        ArrayList<String> entryNames = new ArrayList<String>();
//        Enumeration<?> entries = getEntriesEnumeration(zipFile);
//        while (entries.hasMoreElements()) {
//            ZipEntry entry = ((ZipEntry)entries.nextElement());
//            entryNames.add(new String(getEntryName(entry).getBytes("GB2312"), "8859_1"));
//        }
//        return entryNames;
//    }
 
 
 
    /**
     * ȡ��ѹ���ļ������ע��
     *
     * @param entry ѹ���ļ�����
     * @return ѹ���ļ������ע��
     * @throws UnsupportedEncodingException
     */
    public static String getEntryComment(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getComment().getBytes("GB2312"), "8859_1");
    }
 
    /**
     * ȡ��ѹ���ļ����������
     *
     * @param entry ѹ���ļ�����
     * @return ѹ���ļ����������
     * @throws UnsupportedEncodingException
     */
    public static String getEntryName(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getName().getBytes("GB2312"), "8859_1");
    }
 
    /**
     * ѹ���ļ�
     *
     * @param resFile ��Ҫѹ�����ļ����У�
     * @param zipout ѹ����Ŀ���ļ�
     * @param rootpath ѹ�����ļ�·��
     * @throws FileNotFoundException �Ҳ����ļ�ʱ�׳�
     * @throws IOException ��ѹ�����̳���ʱ�׳�
     */
    private static void zipFile(File resFile, ZipOutputStream zipout, String rootpath)
            throws FileNotFoundException, IOException {
        rootpath = rootpath + (rootpath.trim().length() == 0 ? "" : File.separator)
                + resFile.getName();
        rootpath = new String(rootpath.getBytes("8859_1"), "GB2312");
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            for (File file : fileList) {
                zipFile(file, zipout, rootpath);
            }
        } else {
            byte buffer[] = new byte[BUFF_SIZE];
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile),
                    BUFF_SIZE);
            zipout.putNextEntry(new ZipEntry(rootpath));
            int realLength;
            while ((realLength = in.read(buffer)) != -1) {
                zipout.write(buffer, 0, realLength);
            }
            in.close();
            zipout.flush();
            zipout.closeEntry();
        }
    }
}