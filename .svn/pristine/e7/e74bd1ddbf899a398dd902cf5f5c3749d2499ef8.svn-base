package com.minxing.client.util;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class FileUtils {

	
	public static void deleteFileOrDirectory(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory())
			for (File child : fileOrDirectory.listFiles()){
				deleteFileOrDirectory(child);
			}
		fileOrDirectory.delete();
	}
	
	public static String bytesToHuman(long size) {
		long Kb = 1 * 1024;
		long Mb = Kb * 1024;
		long Gb = Mb * 1024;
		long Tb = Gb * 1024;
		long Pb = Tb * 1024;
		long Eb = Pb * 1024;

		if (size < Kb)
			return floatForm(size) + " byte";
		if (size >= Kb && size < Mb)
			return floatForm((double) size / Kb) + " Kb";
		if (size >= Mb && size < Gb)
			return floatForm((double) size / Mb) + " Mb";
		if (size >= Gb && size < Tb)
			return floatForm((double) size / Gb) + " Gb";
		if (size >= Tb && size < Pb)
			return floatForm((double) size / Tb) + " Tb";
		if (size >= Pb && size < Eb)
			return floatForm((double) size / Pb) + " Pb";
		if (size >= Eb)
			return floatForm((double) size / Eb) + " Eb";

		return "???";
	}

	public static String floatForm(double d) {
		return new DecimalFormat("#.##").format(d);
	}
	/*
	* 获取sd卡根目录
	* */
	public static String getSDPath(){
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
		if (sdCardExist)
		{
			sdDir = Environment.getExternalStorageDirectory();//获取跟目录
		}
		return sdDir.toString() == null ? "" : sdDir.toString();
	}
	public static void saveMessageLog(String msg) throws Exception {
		 FileWriter fos = null;
		 BufferedWriter bw = null;
		if(!("").equals(FileUtils.getSDPath())){
			File MessageLogFile = new File(FileUtils.getSDPath()+ File.separator+"htmessagelog"+File.separator+"meslog.txt");
			if(!MessageLogFile.getParentFile().exists() || MessageLogFile.length()/1024/1024>=1){
				MessageLogFile.getParentFile().mkdirs();
				MessageLogFile.delete();
				MessageLogFile.createNewFile();
			}
			try {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date curDate = new Date(System.currentTimeMillis());//获取当前时间
					String date  = formatter.format(curDate);
					fos = new FileWriter(new File(FileUtils.getSDPath()+ File.separator+"htmessagelog"+File.separator+"meslog.txt"),true);
					bw = new BufferedWriter(fos);
					bw.write(date+" : "+msg);
					bw.newLine();
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						bw.flush();
						bw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		}
	}

	/**
	 * 没有签批pdf需要给个空模板
	 */
	public static void copyFiles(Context context) {
		try {
			copyAssetsFileToSDCard("blank.pdf", Environment
					.getExternalStorageDirectory().getPath().toString() + "/blank.pdf",context);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 拷贝PDF到设备本地，方便演示功能
	 *
	 * @param fileName
	 * @param toFilePath
	 * @throws IOException
	 */
	private static void copyAssetsFileToSDCard(String fileName, String toFilePath,Context content)
			throws IOException {
		File file = new File(toFilePath);
		InputStream myInput;
		OutputStream myOutput = new FileOutputStream(toFilePath);
		myInput = content.getAssets().open(fileName);
		byte[] buffer = new byte[1024];
		int length = myInput.read(buffer);
		while (length > 0) {
			myOutput.write(buffer, 0, length);
			length = myInput.read(buffer);
		}
		myOutput.flush();
		myInput.close();
		myOutput.close();
//        initPdf();
	}
}
