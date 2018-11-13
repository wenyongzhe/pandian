package com.supoin.commoninventory.thread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.supoin.commoninventory.service.androidService;
import com.supoin.commoninventory.util.FileIO;
import com.supoin.commoninventory.util.FileUtility;
import com.supoin.commoninventory.util.Utility;

import android.content.Context;
import android.util.Log;

public class ThreadReadWriterIOSocket implements Runnable
{
	private Socket client;
	private Context context;
	private String TAG="ThreadReadWriterIOSocket";
	public ThreadReadWriterIOSocket(Context context, Socket client)
	{
		this.client = client;
		this.context = context;
	}

	@Override
	public void run()
	{
		Utility.deBug(TAG, "a client has connected to server!");
		BufferedOutputStream out;
		BufferedInputStream in;
		try
		{
			/* PC�˷���������msg */
			String currCMD = "";
			out = new BufferedOutputStream(client.getOutputStream());
			in = new BufferedInputStream(client.getInputStream());
			androidService.ioThreadFlag = true;
			while (androidService.ioThreadFlag)
			{
				try
				{
					if (!client.isConnected())
					{
						break;
					}
					/* ����PC���������� */
					Utility.deBug(androidService.TAG, Thread.currentThread().getName() + "---->" + "will read......");
					/* ���������� */
					currCMD = readCMDFromSocket(in);
					Utility.deBug(androidService.TAG, Thread.currentThread().getName() + "---->" + "**currCMD ==== " + currCMD);

					/* ��������ֱ������� */
					if (currCMD.equals("1"))
					{
						out.write("OK".getBytes());
						out.flush();
					} else if (currCMD.equals("2"))
					{
						out.write("OK".getBytes());
						out.flush();
					} else if (currCMD.equals("3"))
					{
						out.write("OK".getBytes());
						out.flush();
					} else if (currCMD.equals("4"))
					{
						/* ׼�������ļ����� */
						try
						{
							out.write("service receive OK".getBytes());
							out.flush();
						} catch (IOException e)
						{
							e.printStackTrace();
						}

						/* �����ļ����ݣ�4�ֽ��ļ����ȣ�4�ֽ��ļ���ʽ��������ļ����� */
						byte[] filelength = new byte[4];
						byte[] fileformat = new byte[4];
						byte[] filebytes = null;

						/* ��socket���ж�ȡ�����ļ����� */
						filebytes = receiveFileFromSocket(in, out, filelength, fileformat);

						// Utility.deBug(Service139.TAG, "receive data =" + new
						// String(filebytes));
						try
						{
							/* �����ļ� */
							String filename=FileUtility.getSdCardPath()+"/dataupdate/serverLog.txt";
							File file = FileIO.newFile(filename);
							FileIO.writeFile(file, filebytes, 0, filebytes.length);
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					} else if (currCMD.equalsIgnoreCase("exit"))
					{
						out.write("exit ok".getBytes());
						out.flush();
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			out.close();
			in.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (client != null)
				{
					Utility.deBug(androidService.TAG, Thread.currentThread().getName() + "---->" + "client.close()");
					client.close();
				}
			} catch (IOException e)
			{
				Log.e(androidService.TAG, Thread.currentThread().getName() + "---->" + "read write error333333");
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���ܣ���socket���ж�ȡ�����ļ�����
	 * 
	 * InputStream in��socket������
	 * 
	 * byte[] filelength: ����ǰ4���ֽڴ洢Ҫת�͵��ļ����ֽ���
	 * 
	 * byte[] fileformat������ǰ5-8�ֽڴ洢Ҫת�͵��ļ��ĸ�ʽ����.apk��
	 * 
	 * */
	public static byte[] receiveFileFromSocket(InputStream in, OutputStream out, byte[] filelength, byte[] fileformat)
	{
		byte[] filebytes = null;// �ļ�����
		try
		{
			in.read(filelength);// ���ļ�����
			int filelen = FileIO.bytesToInt(filelength);// �ļ����ȴ�4�ֽ�byte[]ת��Int
			String strtmp = "read file length ok:" + filelen;
			out.write(strtmp.getBytes("utf-8"));
			out.flush();

			filebytes = new byte[filelen];
			int pos = 0;
			int rcvLen = 0;
			while ((rcvLen = in.read(filebytes, pos, filelen - pos)) > 0)
			{
				pos += rcvLen;
			}
			Utility.deBug(androidService.TAG, Thread.currentThread().getName() + "---->" + "read file OK:file size="
					+ filebytes.length);
			out.write("read file ok".getBytes("utf-8"));
			out.flush();
		} catch (Exception e)
		{
			Utility.deBug(androidService.TAG, Thread.currentThread().getName() + "---->" + "receiveFileFromSocket error");
			e.printStackTrace();
		}
		return filebytes;
	}

	/* ��ȡ���� */
	public String readCMDFromSocket(InputStream in)
	{
		int MAX_BUFFER_BYTES = 2048;
		String msg = "";
		byte[] tempbuffer = new byte[MAX_BUFFER_BYTES];
		try
		{
			int numReadedBytes = in.read(tempbuffer, 0, tempbuffer.length);
			msg = new String(tempbuffer, 0, numReadedBytes, "utf-8");
			tempbuffer = null;
		} catch (Exception e)
		{
			Utility.deBug(androidService.TAG, Thread.currentThread().getName() + "---->" + "readFromSocket error");
			e.printStackTrace();
		}
		// Utility.deBug(Service139.TAG, "msg=" + msg);
		return msg;
	}
}