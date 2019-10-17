package com.htmitech.ztcustom.zt.chinarailway;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class BitmapLoader {

	// 内存缓存,SoftReference实现自动回收
	private static HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

	/**
	 * 自动判断从内存还是从网络获取图片
	 * 
	 * @param imageURL
	 * @return
	 */
	public static Bitmap loadBitmap(String imageURL) {
		Bitmap bm = null;
		if (imageCache.containsKey(imageURL)) {// 从内存中获取
			System.out.println("imageCache");
			SoftReference<Bitmap> reference = imageCache.get(imageURL);
			bm = reference.get();
		}
		if (null == bm) {// 到网络下载
			System.out.println("loadNetBitmap");
			bm = loadNetBitmap(imageURL);
			if (null != bm) {
				imageCache.put(imageURL, new SoftReference<Bitmap>(bm)); // 保存到内存
			}
		}
		return bm;
	}

	// 从网络下载图片
	private static Bitmap loadNetBitmap(String imageURL) {
		try {
			URL url = new URL(imageURL);
			URLConnection connection;
			connection = url.openConnection();
			Bitmap bitmap = BitmapFactory.decodeStream((InputStream) connection
					.getContent());
			return bitmap;
		} catch (Exception e) {

		}
		return null;
	}

}
