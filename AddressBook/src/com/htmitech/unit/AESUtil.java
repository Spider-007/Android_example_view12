package com.htmitech.unit;

import android.content.Context;
import android.util.Log;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	public final static String AES_KEY = "Q*1_3@c!4kd^j&g%";

	/**
	 * AES解密
	 *
	 * @param str 待解密字符串
	 * @return 解密后字符串
	 */
	public static byte[] aesDecrypt(byte[] str, String Key) {
		try {
			if (Key == null || Key.equals("")) {
				Key = AES_KEY;
			}

			SecretKeySpec skeySpec = new SecretKeySpec(Key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//			String strTmp = new String(cipher.doFinal(str));

//			EncodingUtils.getString(content, );
			return cipher.doFinal(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}


	public static void main(String[] args) {


		byte[] fileByte = getFileByte("C:\\Users\\Jeff\\Desktop\\98bfa42e-ee08-4bcb-a61e-577bccf490fd.txt");

		// 加密原文
//		String plaintext = new String(fileByte);
		// 密钥
//		String key = "12345678";
		// 密文
		String ciphertext;
//		try {
//			String de = aesDecrypt(fileByte, AES_KEY);
//			System.out.println("key:" + key);
//			System.out.println("原:" + plaintext);
//			// System.out.println("加密后:" + ciphertext);
//			System.out.println("解密后:" + de);
//			writeFileByte("C:\\Users\\Jeff\\Desktop\\fb2222.txt", de.getBytes());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////


	public static byte[] getFileByte(String filepath) {

		try {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			fis = new FileInputStream(filepath);
			if (fis != null) {
				bis = new BufferedInputStream(fis);
				bis.available();
			}
			if (bis != null) {

				byte[] bs = new byte[bis.available()];
				if (bis.read(bs) != -1) {
					return bs;
				}
				bis.close();
				fis.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeFileByte(String path, byte[] content) {
		try {
			File f = new File(path);
			if (f.exists()) {
				Log.d("writeFileByte", "文件存在");
			} else {
				Log.d("writeFileByte", "文件不存在，正在创建...");
				if (f.createNewFile()) {
					Log.d("writeFileByte", "文件创建成功！");
				} else {
					Log.d("writeFileByte", "文件创建失败！");
				}
			}
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(content);
			fos.close();

//			writeToTxt(path, new String(content));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void writeToTxt(String fileName, String content) {
		try {
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f));
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}