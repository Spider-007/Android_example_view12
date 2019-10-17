
package com.example.archivermodule.pdfsign;

import android.os.Environment;

/**
 * 常量
 * com.kinggrid.iapppdf.demo.ConstantValue
 * @author wmm
 * create at 2015年8月13日 下午5:13:18
 */
public interface ConstantValue {
	final static int KEY_DOCUMENT_SAVE = 3;//退出保存
	final static int KEY_SINGER = 2;//签名
	final static int KEY_SINGER_DEL = 2;//删除签名
	final static int KEY_FULL_SINGER = 1;//全文批注
	final static int KEY_DEL_FULL_SINGER = 4;// 删除全文批注
	final static int KEY_TEXT_NOTE = 5;//文字注释
	final static int KEY_DEL_TEXT_NOTE = 6;// 删除文字注释
	final static int KEY_SOUND_NOTE = 7;// 语音批注
	final static int KEY_DEL_SOUND_NOTE = 8;// 删除语音批注
	final static int KEY_NOTE_LIST = 9; //批注列表
	final static int KEY_TEXT_LIST = 10;// 文字注释列表
	final static int KEY_SOUND_LIST = 11; //语音批注列表
	final static int KEY_BOOKMARK_LIST = 12;// 大纲
	final static int KEY_CAMERA = 13;// 证件拍照
	final static int KEY_DIGITAL_SIGNATURE = 14;//数字签名
	final static int KEY_VERIFY = 15;//验证
	final static int KEY_SAVEAS = 16; // 另存
	final static int KEY_SAVE_PAGES = 17; // 保存页面图片
	final static int KEY_FIELD_CONTENT = 18; // 获取全部域内容
	final static int KEY_AREA = 19; //区域签批
	final static int KEY_LOCAL_DIGITAL_SIGNATURE = 20;//数字签名
	final static int KEY_TF_SIGNATURE = 21;//TF卡数字签名
	//final static int KEY_ADD_ATTACHMENT = 22; //添加附件
	//final static int KEY_DUPLEX = 21;//同步签批
	//final static int KEY_ABOUT = 22; // 关于界面
	final static int KEY_SEARCH = 23;//搜索
	final static int KEY_LOCAL_SIGNATURE = 24;//定位盖章
	final static int KEY_UPLOAD = 25;//签批件上传
	final static int SIGN_SETTING = 26;//签批件上传

	
	

/*	final static int TYPE_ANNOT_HANDWRITE = 1;*/
	final static int TYPE_ANNOT_STAMP = 1;//全文批注
	final static int TYPE_ANNOT_TEXT = 2;//文字注释
	final static int TYPE_ANNOT_SIGNATURE = 3;//签名
	final static int TYPE_ANNOT_SOUND = 4;//语音注释
	
	final static String SDCARD_PATH = Environment
			.getExternalStorageDirectory().getPath().toString();
	
	//intent传递名称,实际使用中根据需要自定义名称
	final String NAME = "demo_name";
	final String LIC = "demo_lic";
	final String CANFIELDEIDT = "demo_fieldEdit";
	final String T7MODENAME = "demo_T7Mode";
	final String EBENSDKNAME = "demo_ebenSDK";
	final String SAVEVECTORNAME = "demo_savevectortopdf";
	final String VECTORNAME = "demo_vectorsign";
	final String VIEWMODENAME = "demo_viewMode";
	final String LOADCACHENAME = "demo_loadCache";
	final String ANNOTPROTECTNAME = "demo_annotprotect";
	final String FILLTEMPLATE = "demo_filltemplate";
	final String ANNOT_TYPE = "demo_annottype";
	
	final String FILE_DATA = "demo_filedata";
	final String FILE_NAME= "demo_filename";
	
	
	//阅读模式
	final int VIEWMODE_VSCROLL = 101;
	final int VIEWMODE_SINGLEH = 102;
	final int VIEWMODE_SINGLEV = 103;
	
	//Handler 
	final int MSG_WHAT_DISMISSDIALOG = 201;
	final int MSG_WHAT_LOADANNOTCOMPLETE = 202;
	final int MSG_WHAT_REFRESHDOCUMENT = 203;
	final int HANDLE_TF_CONNET_ALERT = 212;
	final int HANDLE_TF_CONNET_ERROR = 204;
	final int HANDLE_TF_CONNET_SUCCESS = 205;
	final int HANDLE_TF_LOGIN_ALERT = 206;
	final int HANDLE_TF_LOGIN_ERROR = 207;
	final int HANDLE_TF_LOGIN_SUCCESS = 208;
	final int HANDLE_TF_GETCERT_SUCCESS = 209;
	final int HANDLE_TF_GETCERT_ERROR = 210;
	final int HANDLE_TF_GETCERT_ALERT = 211;

	final int MSG_WHAT_CANCEL = 302;
	
	public static final String KEY_SERVER_URL = "server_url";
	public static final String KEY_USER_ID = "user_id";
	
	public static final int MSG_NET_ERROR = 1001;
	public static final int MSG_VERIFYPASSWORD_FAIL = 1002;
	public static final int MSG_GET_SIGNATURE_FAIL = 1003;
	public static final int MSG_GET_SIGNATURE_SUCCESS = 1004;
	public static final int MSG_SHOW_ANNOT_INFO = 1005;
	public static final int MSG_URL_IS_NULL = 1006;
	public static final int MSG_PASSWORD_IS_NULL = 1007;
	public static final int MSG_USERNAME_IS_NULL = 1008;
	
	//拍照需要的参数
	final int REQUESTCODE_PHOTOS_TAKE = 100;
	final int REQUESTCODE_PHOTOS_CROP = 200;
	
	//签名方式：域定位、位置定位、文字定位、数字签名等
	final int SIGN_MODE_FIELDNAME = 301;
	final int SIGN_MODE_TEXT = 302;
	final int SIGN_MODE_POSITION = 303;
	final int SIGN_MODE_SERVER = 304;
	final int SIGN_MODE_KEY = 305;
	final int SIGN_MODE_BDE = 306;
	final int SIGN_MODE_TFCARD = 307;
	
	
	final int SIGNATURE_SERVER_VERIFY_MODE_COMMON = 5001;
	final int SIGNATURE_SERVER_VERIFY_MODE_DEVICESN = 5002;
	final int SIGNATURE_SERVER_VERIFY_MODE_USERCODE = 5003;


	final int INIT_PDF_PARAM = 1001;  //新加初始化pdf签批属性
}

