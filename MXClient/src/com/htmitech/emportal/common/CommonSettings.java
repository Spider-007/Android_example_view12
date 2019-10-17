package com.htmitech.emportal.common;

import android.os.Environment;

import com.htmitech.emportal.HtmitechApplication;

import java.io.File;

public class CommonSettings {


	/**
	 * 用于分享
	 */
	public static final String SHARE_IMAGEURL = "imageUrl"; // 需要分享图片的下载地址
	public static final String SHARE_LINKURL = "linkUrl"; // 分享成功后点击触发的地址
	public static final String SHARE_CONTENT = "content"; // 分享内容
	public static final String SHARE_TITLE = "title"; // 分享标题
	public static final String SHARE_FROM = "from"; // 分享来源
	/** 线上线下总开关 */

	public static boolean DEBUG = false;
	// //////////////////////////应用程序安装目录////////////////////////////////////////////
	private static final String appRootFolder =HtmitechApplication.instance()
			.getCacheDir().getPath();

	public static final String APPFOLDER_FILEDOLDER = appRootFolder
			+ File.separator + "docfile";

	// /////////////sd card存储目录/////////////////////////////////////////////////

	public static final String extRootFolder = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	public static final String DEFAULT_FOLDER_NAME = "htmitech";
	public static final String DEFAULT_FOLDER = extRootFolder + File.separator
			+ DEFAULT_FOLDER_NAME;

	/**
	 * 缓存目录
	 * **/
	public static final String DEFAULT_CACHE_FOLDER = DEFAULT_FOLDER
			+ File.separator + "emportal";

	public static  String DEFAULT_DOCUMENT_CACHE_FOLDER = DEFAULT_CACHE_FOLDER
			+ File.separator + "document";

	public static  String DEFAULT_DOCFILE_CACHE_FOLDER = DEFAULT_CACHE_FOLDER
			+ File.separator + "docFile";//正文

	public static  String DEFAULT_ATTACHMENT_CACHE_FOLDER = DEFAULT_CACHE_FOLDER
			+ File.separator + "attachment";//附件

	public static  String DEFAULT_CACHE_IMAGE_FOLDER = DEFAULT_CACHE_FOLDER
			+ File.separator + "image";


	
	
	/**************************************************************************************************************************/
}
