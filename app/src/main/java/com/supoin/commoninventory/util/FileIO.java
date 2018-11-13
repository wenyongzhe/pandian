package com.supoin.commoninventory.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

public class FileIO {
	//��ȡ�ı��ļ��е�����
    public static String ReadTxtFile(String strFilePath,String encoding)
    {
        String path = strFilePath;
        String content = ""; //�ļ������ַ���
            //���ļ�
            File file = new File(path);
            //���path�Ǵ��ݹ����Ĳ�����������һ����Ŀ¼���ж�
            if (file.isDirectory())
            {
                LogUtil.d("TestFile", "The File doesn't not exist.");
            }
            else
            {
                try {
                    InputStream instream = new FileInputStream(file);
                    if (instream != null)
                    {
                        InputStreamReader inputreader = new InputStreamReader(instream,encoding);
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line;
                        //���ж�ȡ
                        while (( line = buffreader.readLine()) != null) {
                            content += line + "n";
                        }               
                        instream.close();
                    }
                }
                catch (java.io.FileNotFoundException e)
                {
                    LogUtil.d("TestFile", "The File doesn't not exist.");
                }
                catch (IOException e)
                {
                     LogUtil.d("TestFile", e.getMessage());
                }
            }
            return content;
    }
	
    public static List<String> ReadFile(String fileName)
	{
		String res = "";
		 List<String> fileContent = new ArrayList<String>();
	    try
	    {
	    	File file = new File(fileName);
	        if (!file.exists())
	            throw new Exception("�ļ�������!");
	        FileInputStream fin = new FileInputStream(fileName);

			int length = fin.available();

			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			 fileContent.add(res);
			fin.close();
			
//	        using (StreamReader reader = new StreamReader(fileName , Encoding.Default))
//	        {
//	            String temp = ""; 
//	            while (!reader.EndOfStream)
//	            {
//	                temp = reader.ReadLine();
//	                fileContent.add(temp);
//	            }
//	        }
	       
	    }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	    return fileContent;
	}
	public static File newFile(String filename)
	{
//		String FILEPATH="";
//		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
//		{
//			File sdCardDir = Environment.getExternalStorageDirectory();
//			// SDCardĿ¼��/mnt/sdcard
//			FILEPATH = sdCardDir.getAbsolutePath();
//			System.out.println("here is  sdcardir.......    " + FILEPATH);
//		}

		File file = null;

		try
		{
//			file = new File(FILEPATH, filename);
			file = new File(filename);
			file.delete();
			file.createNewFile();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return file;
	}
	public static void WriteFile(String path, String fileName,
			String fileContent) {
		try {
			FileOutputStream fout = new FileOutputStream(path + fileName);
			byte[] bytes = fileContent.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeFile(File file, byte[] data, int offset, int count) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(file, true);
		fos.write(data, offset, count);
		fos.flush();
		fos.close();
	}

	 /** 
     * ��֤�Ƿ���ڸ��ļ� 
     * @return 
     * @throws Exception 
     */  
    public boolean isExist(String fileName)throws Exception{  
        boolean flag=false;  
        FileInputStream fs= new FileInputStream(fileName);
        if(fs!=null){  
            flag=true;  
        }  
          
        return flag;  
    }  
	public static void writeFileSdcardFile(String fileName, String write_str)
			throws IOException {
		try {

			FileOutputStream fout = new FileOutputStream(fileName);
			byte[] bytes = write_str.getBytes();

			fout.write(bytes);
			fout.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��SD�е��ļ�
	public String readFileSdcardFile(String fileName) throws IOException {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(fileName);

			int length = fin.available();

			byte[] buffer = new byte[length];
			fin.read(buffer);

			res = EncodingUtils.getString(buffer, "UTF-8");

			fin.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	/**  
     * ���Ƶ����ļ�  
     * @param oldPath String ԭ�ļ�·�� �磺c:/fqf.txt  
     * @param newPath String ���ƺ�·�� �磺f:/fqf.txt  
     * @return boolean  
     */   
   public static void copyFile(String oldPath, String newPath) {   
       try {   
           int bytesum = 0;   
           int byteread = 0;   
           File oldfile = new File(oldPath);   
           if (oldfile.exists()) { //�ļ�����ʱ   
               InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ�   
               FileOutputStream fs = new FileOutputStream(newPath);   
               byte[] buffer = new byte[1024];   
               int length;   
               while ( (byteread = inStream.read(buffer)) != -1) {   
                   bytesum += byteread; //�ֽ��� �ļ���С   
//                   System.out.println(bytesum);   
                   fs.write(buffer, 0, byteread);   
               }   
               fs.flush();// �������е�д��file
               fs.close();
               inStream.close();   
           }   
       }   
       catch (Exception e) {   
           System.out.println("���Ƶ����ļ���������");   
           e.printStackTrace();   
  
       }   
  
   }   
  
   /**  
     * ���������ļ�������  
     * @param oldPath String ԭ�ļ�·�� �磺c:/fqf  
     * @param newPath String ���ƺ�·�� �磺f:/fqf/ff  
     * @return boolean  
     */   
   public void copyFolder(String oldPath, String newPath) {   
  
       try {   
           (new File(newPath)).mkdirs(); //����ļ��в����� �������ļ���   
           File a=new File(oldPath);   
           String[] file=a.list();   
           File temp=null;   
           for (int i = 0; i < file.length; i++) {   
               if(oldPath.endsWith(File.separator)){   
                   temp=new File(oldPath+file[i]);   
               }   
               else{   
                   temp=new File(oldPath+File.separator+file[i]);   
               }   
  
               if(temp.isFile()){   
                   FileInputStream input = new FileInputStream(temp);   
                   FileOutputStream output = new FileOutputStream(newPath + "/" +   
                           (temp.getName()).toString());   
                   byte[] b = new byte[1024 * 5];   
                   int len;   
                   while ( (len = input.read(b)) != -1) {   
                       output.write(b, 0, len);   
                   }   
                   output.flush();   
                   output.close();   
                   input.close();   
               }   
               if(temp.isDirectory()){//��������ļ���   
                   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);   
               }   
           }   
       }   
       catch (Exception e) {   
           System.out.println("���������ļ������ݲ�������");   
           e.printStackTrace();   
  
       }   
  
   }  
   /**  
   * �ƶ��ļ�  
   * @param srcFileName    Դ�ļ�����·�� 
   * @param destDirName    Ŀ��Ŀ¼����·�� 
   * @return �ļ��ƶ��ɹ�����true�����򷵻�false  
   */    
   public static boolean moveFile(String srcFileName, String destDirName) {  
         
       File srcFile = new File(srcFileName);  
       if(!srcFile.exists()|| !srcFile.isFile())   
           return false;  
         
       File destDir = new File(destDirName);  
       if (!destDir.exists())  
           destDir.mkdirs();  
      File file = new File(destDir.getParent() + File.separator + srcFile.getName());
//      if(file.exists())
//    	  file.delete();
     return srcFile.renameTo(new File(destDir.getParent() + File.separator + srcFile.getName()));  
   }  
   /**  
   * �ƶ�Ŀ¼  
   * @param srcDirName     ԴĿ¼����·�� 
   * @param destDirName    Ŀ��Ŀ¼����·�� 
   * @return Ŀ¼�ƶ��ɹ�����true�����򷵻�false  
   */    
   public  boolean moveDirectory(String srcDirName, String destDirName) {  
         
       File srcDir = new File(srcDirName);  
       if(!srcDir.exists() || !srcDir.isDirectory())    
           return false;    
        
      File destDir = new File(destDirName);  
      if(!destDir.exists())  
          destDir.mkdirs();  
        
      /** 
       * ������ļ����ƶ�������ݹ��ƶ��ļ��С�ɾ�����յĿ�Դ�ļ��� 
       * ע���ƶ��ļ���ʱ�����ļ��е���״�ṹ 
       */  
      File[] sourceFiles = srcDir.listFiles();  
      for (File sourceFile : sourceFiles) {  
          if (sourceFile.isFile())  
              moveFile(sourceFile.getAbsolutePath(), destDir.getAbsolutePath());  
          else if (sourceFile.isDirectory())  
              moveDirectory(sourceFile.getAbsolutePath(),   
                      destDir.getAbsolutePath() + File.separator + sourceFile.getName());  
          else  
              ;  
      }  
      return srcDir.delete();  
   }  
   /** 
    * �ƶ�ָ���ļ����ļ���(���������ļ������ļ���) 
    *  
    * @param fromDir 
    *            Ҫ�ƶ����ļ����ļ��� 
    * @param toDir 
    *            Ŀ���ļ��� 
    * @throws Exception 
    */  
   public static void MoveFolderAndFileWithSelf(String from, String to) throws Exception {  
       try {  
           File dir = new File(from);  
           // Ŀ��  
           to +=  File.separator+dir.getName();  
           File moveDir = new File(to);  
           if(dir.isDirectory()){  
               if (!moveDir.exists()) {  
                   moveDir.mkdirs();  
               }  
           }else{  
               File tofile = new File(to);  
               dir.renameTo(tofile);  
               return;  
           }  
             
           //System.out.println("dir.isDirectory()"+dir.isDirectory());  
           //System.out.println("dir.isFile():"+dir.isFile());  
             
           // �ļ�һ��  
           File[] files = dir.listFiles();  
           if (files == null)  
               return;  
 
           // �ļ��ƶ�  
           for (int i = 0; i < files.length; i++) {  
               System.out.println("�ļ�����"+files[i].getName());  
               if (files[i].isDirectory()) {  
                   MoveFolderAndFileWithSelf(files[i].getPath(), to);  
                   // �ɹ���ɾ��ԭ�ļ�  
                   files[i].delete();  
               }  
               File moveFile = new File(moveDir.getPath() + File.separator + files[i].getName());  
               // Ŀ���ļ����´��ڵĻ���ɾ��  
               if (moveFile.exists()) {  
                   moveFile.delete();  
               }  
               files[i].renameTo(moveFile);  
           }  
           dir.delete();  
       } catch (Exception e) {  
           throw e;  
       }  
   }  
     
   /** 
    * ���Ƶ����ļ�(�ɸ�������) 
    * @param oldPathFile ׼�����Ƶ��ļ�Դ 
    * @param newPathFile �������¾���·�����ļ���(ע��Ŀ¼·������ļ���) 
    * @return 
    */  
   public static void CopySingleFile(String oldPathFile, String newPathFile) {  
       try {  
           int bytesum = 0;  
           int byteread = 0;  
           File oldfile = new File(oldPathFile);  
           if (oldfile.exists()) { //�ļ�����ʱ  
               InputStream inStream = new FileInputStream(oldPathFile); //����ԭ�ļ�  
               FileOutputStream fs = new FileOutputStream(newPathFile);  
               byte[] buffer = new byte[1444];  
               while ((byteread = inStream.read(buffer)) != -1) {  
                   bytesum += byteread; //�ֽ��� �ļ���С  
                   //System.out.println(bytesum);  
                   fs.write(buffer, 0, byteread);  
               }  
               inStream.close();  
           }  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
   }  
 
   /** 
    * ���Ƶ����ļ�(ԭ������) 
    * @param oldPathFile ׼�����Ƶ��ļ�Դ 
    * @param newPathFile �������¾���·�����ļ���(ע��Ŀ¼·������ļ���) 
    * @return 
    */  
   public static void CopySingleFileTo(String oldPathFile, String targetPath) {  
       try {  
           int bytesum = 0;  
           int byteread = 0;  
           File oldfile = new File(oldPathFile);  
           String targetfile = targetPath + File.separator +  oldfile.getName();  
           if (oldfile.exists()) { //�ļ�����ʱ  
               InputStream inStream = new FileInputStream(oldPathFile); //����ԭ�ļ�  
               FileOutputStream fs = new FileOutputStream(targetfile);  
               byte[] buffer = new byte[1444];  
               while ((byteread = inStream.read(buffer)) != -1) {  
                   bytesum += byteread; //�ֽ��� �ļ���С  
                   //System.out.println(bytesum);  
                   fs.write(buffer, 0, byteread);  
               }  
               inStream.close();  
           }  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
   }  
 
   /** 
    * ���������ļ��е�����(������) 
    * @param oldPath ׼��������Ŀ¼ 
    * @param newPath ָ������·������Ŀ¼ 
    * @return 
    */  
   public static void copyFolderWithSelf(String oldPath, String newPath) {  
       try {  
           new File(newPath).mkdirs(); //����ļ��в����� �������ļ���  
           File dir = new File(oldPath);  
           // Ŀ��  
           newPath +=  File.separator + dir.getName();  
           File moveDir = new File(newPath);  
           if(dir.isDirectory()){  
               if (!moveDir.exists()) {  
                   moveDir.mkdirs();  
               }  
           }  
           String[] file = dir.list();  
           File temp = null;  
           for (int i = 0; i < file.length; i++) {  
               if (oldPath.endsWith(File.separator)) {  
                   temp = new File(oldPath + file[i]);  
               } else {  
                   temp = new File(oldPath + File.separator + file[i]);  
               }  
               if (temp.isFile()) {  
                   FileInputStream input = new FileInputStream(temp);  
                   FileOutputStream output = new FileOutputStream(newPath +  
                           "/" +  
                           (temp.getName()).toString());  
                   byte[] b = new byte[1024 * 5];  
                   int len;  
                   while ((len = input.read(b)) != -1) {  
                       output.write(b, 0, len);  
                   }  
                   output.flush();  
                   output.close();  
                   input.close();  
               }  
               if (temp.isDirectory()) { //��������ļ���  
                   copyFolderWithSelf(oldPath + "/" + file[i], newPath);  
               }  
           }  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
   }
   public static void Delete(String pathName){
	   File file= new File(pathName);
	   if(file.exists())
		   file.delete();
   }
   public static boolean Exists(String fileName){
	   File file = new File(fileName);
	   if(file.exists())
		   return true;
	   return false;
   }
   
   
   /* byte[]תInt */
	public static int bytesToInt(byte[] bytes) {
		int addr = bytes[0] & 0xFF;
		addr |= ((bytes[1] << 8) & 0xFF00);
		addr |= ((bytes[2] << 16) & 0xFF0000);
		addr |= ((bytes[3] << 25) & 0xFF000000);
		return addr;

	}

	/* Intתbyte[] */
	public static byte[] intToByte(int i) {
		byte[] abyte0 = new byte[4];
		abyte0[0] = (byte) (0xff & i);
		abyte0[1] = (byte) ((0xff00 & i) >> 8);
		abyte0[2] = (byte) ((0xff0000 & i) >> 16);
		abyte0[3] = (byte) ((0xff000000 & i) >> 24);
		return abyte0;
	}
	
	public void writeFileData(String fileName,String message){ 

//	       try{ 
//
//	        FileOutputStream fout =getApplication().openFileOutput(fileName, getApplication().MODE_PRIVATE);
//	        
//	        byte [] bytes = message.getBytes(); 
//
//	        fout.write(bytes); 
//
//	         fout.close(); 
//
//	        } 
//
//	       catch(Exception e){ 
//
//	        e.printStackTrace(); 
//
//	       } 

	   }    
	 public String readFileData(String fileName){ 

	        String res=""; 

//	        try{ 
//
//	         FileInputStream fin = getApplication().openFileInput(fileName); 
//
//	         int length = fin.available(); 
//
//	         byte [] buffer = new byte[length]; 
//
//	         fin.read(buffer);     
//
//	         res = EncodingUtils.getString(buffer, "UTF-8"); 
//
//	         fin.close();     
//
//	        } 
//
//	        catch(Exception e){ 
//
//	         e.printStackTrace(); 
//
//	        } 

	        return res; 

	    }   
	public File creatDataFile(String fileName) throws IOException {
		  File file = new File(fileName);
		  file.createNewFile();
		  return file;
		 
		 }
	public static byte[] readFile(String fileName) throws IOException {
		File file = new File(fileName);
		file.createNewFile();
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		int leng = bis.available();
		byte[] b = new byte[leng];
		bis.read(b, 0, leng);
		// Input in = new Input(fis);
		// byte[] ret = in.readAll();
		// in.close();
		bis.close();
		return b;

	}
	public static List<File> FindFile(File file, String key_search)
    {
        List<File> list = new ArrayList<File>();
        if (file.isDirectory()) {
            File[] all_file = file.listFiles();
            if (all_file != null) {
                for (File tempf : all_file) {
                    if (tempf.isDirectory()) {
                        if (tempf.getName().toLowerCase().lastIndexOf(key_search) > -1) {
                            list.add(tempf);
                        }
                        list.addAll(FindFile(tempf, key_search));
                    }
                    else 
                    {
                        if (tempf.getName().toLowerCase().lastIndexOf(key_search) > -1) {
                            list.add(tempf);
                        }
                    }
                }
            }
        } 
        return list;
    }
}