package com.htmitech.proxy.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/** 
 * AES加解密 
 *  
 * @author  
 */  
public class AESPartUtil {  
    private static final String AES_KEY = "Q*1_3@c!4kd^j&g%";  
    private static final int ENCRYPT_BYTE_LENGTH = 128;
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";
    
    private AESPartUtil() {
        throw new IllegalStateException("Utility class");
      }
    /**
     * @param filepath
     * @throws Exception
     * @Author:奋斗的大侠
     * @Description:文件加密
     */
    public static void encryptFile(String filepath) throws Exception{
    	byte[] fileByte = getFileByte(filepath,0);
        // byte[] fileEnByte = 	
    	Map<String,byte[]> map = getEntyptByteInfo(fileByte);
    	byte[] encryptFileByte = encrypt(map.get("preByte"), null);
    	System.out.println("map.get(\"preByte\").length:"+
    	    	map.get("preByte").length+" encryptFileByte " +  +encryptFileByte.length);
    	writeFileByte(filepath, encryptFileByte,map.get("sufByte"));
    }
    /**
     * @param filepath
     * @throws Exception
     * @Author:奋斗的大侠
     * @Description:文件解密
     */
    public static void decryptFile(String filepath) throws Exception{
    	byte[] fileByte = getFileByte(filepath,0);
    	Map<String,byte[]> map = getDecryptByteInfo(fileByte);
    	byte[] decryptFileByte = decrypt(map.get("preByte"), null);
    	System.out.println("map.get(\"preByte\").length:"+ map.get("preByte").length+" decryptFileByte " +decryptFileByte.length);
    	writeFileByte(filepath, decryptFileByte,map.get("sufByte"));
    }
    public static Map<String,byte[]> getEntyptByteInfo(byte[] fileByte){
    	 Map<String,byte[]> map = new HashMap<String,byte[]>();
    	 byte[] preByte = null;
    	 byte[] sufByte = null;
    	 if(fileByte.length>ENCRYPT_BYTE_LENGTH){
    		 preByte = new byte[ENCRYPT_BYTE_LENGTH];
    		 sufByte = new byte[fileByte.length -ENCRYPT_BYTE_LENGTH];
    		 System.arraycopy(fileByte, 0, preByte, 0, ENCRYPT_BYTE_LENGTH);
    		 System.arraycopy(fileByte, ENCRYPT_BYTE_LENGTH, sufByte, 0, fileByte.length -ENCRYPT_BYTE_LENGTH);
    	 }else{
    		 preByte = fileByte;
    	 }
    	 map.put("preByte", preByte);
    	 map.put("sufByte", sufByte);
    	 return map;
    }
    public static Map<String,byte[]> getDecryptByteInfo(byte[] fileByte){
   	 Map<String,byte[]> map = new HashMap<String,byte[]>();
   	 byte[] preByte = null;
   	 byte[] sufByte = null;
   	 int newEncByteLen = ENCRYPT_BYTE_LENGTH+16;
   	 if(fileByte.length>newEncByteLen){
   		 preByte = new byte[newEncByteLen];
   		 sufByte = new byte[fileByte.length -newEncByteLen];
   		 System.arraycopy(fileByte, 0, preByte, 0, newEncByteLen);
   		 System.arraycopy(fileByte, newEncByteLen, sufByte, 0, fileByte.length -newEncByteLen);
   	 }else{
   		 preByte = fileByte;
   	 }
   	 map.put("preByte", preByte);
   	 map.put("sufByte", sufByte);
   	 return map;
   }
    /**
     * <p>
     * 解密
     * </p>
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
    	if(key==null||("").equals(key)){
    		key = AES_KEY;
    	}
    	byte[] raw = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }
    /**
     * <p>
     * 加密
     * </p>
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String key) throws Exception {
    	if(key==null||("").equals(key)){
    		key = AES_KEY;
    	}
    	byte[] raw = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }
    
	public static byte[] getFileByte(String filepath,int len) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(filepath);
			if (fis != null) {
				bis = new BufferedInputStream(fis);
				bis.available();
			}
			if (bis != null) {
				byte[] bs = null;
				if(len == 0){
					bs = new byte[bis.available()];
				}else{
					bs = new byte[len];
				}
				if (bis.read(bs) != -1) {
					return bs;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(bis!=null){
					bis.close();
				}
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	public static void writeFileByte(String path, byte[] preByte, byte[] sufByte) {
		File f = new File(path);
		FileOutputStream fos = null;
		try {
			
			if (!f.exists()) {
				throw new RuntimeException("文件不存在");
			} 
			fos = new FileOutputStream(f);
			fos.write(preByte);
			if(sufByte!=null) fos.write(sufByte);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(fos!=null){
					fos.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 复制单个文件
	 * @param oldPath String 原文件路径 如：c:/fqf.txt
	 * @param newPath String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //文件存在时
				InputStream inStream = new FileInputStream(oldPath); //读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ( (byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		}
		catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

	}
	public  static void main(String args[]) throws Exception{
		String filepath = "D:\\db\\google软件测试之道1.pdf";
		System.out.println("加密前:"+getFileByte(filepath,0).length);
		encryptFile(filepath);
		System.out.println("加密后:"+getFileByte(filepath,0).length);
		
		System.out.println("解密前:"+getFileByte(filepath,0).length);
		decryptFile(filepath);
		System.out.println("解密后:"+getFileByte(filepath,0).length);
		//System.out.println(getFileByte(filepath, 0).length);
	/*	byte[] data= getFileByte(filepath, 128);
		byte[] encdata = encrypt(data,AES_KEY);
		byte[] decdata = decrypt(encdata, AES_KEY);
		System.out.println(data.length);
		System.out.println(encdata.length);
		System.out.println(decdata.length);*/
	}
}

