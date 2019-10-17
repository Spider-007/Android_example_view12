package com.htmitech.ztcustom.zt.videoview;//package com.htmitech.zt.videoview;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.multidex.MultiDex;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.android.zhumu.InstantMeetOptions;
//import com.android.zhumu.JoinMeetOptions;
//import com.android.zhumu.MeetInviteOptions;
//import com.android.zhumu.MeetServiceListener;
//import com.android.zhumu.MeetViewsOptions;
//import com.android.zhumu.StartMeetOptions;
//import com.android.zhumu.ZhuMuSDK;
//import com.android.zhumu.ZhuMuSDKInitializeListener;
//import com.android.zhumu.ZhuMuStatus;
//import com.google.gson.Gson;
//import com.htmitech.emportalzt.R;
//
//import org.xutils.common.Callback;
//import org.xutils.http.RequestParams;
//import org.xutils.x;
//
//public class VideoViewActivity extends Activity implements  Constants, MeetServiceListener,ZhuMuSDKInitializeListener {
//	static Logs log = new Logs("us.zm.sdkexample.MainActivity",
//			Logs.DebugType.D);
//
//	private EditText mEdtMeetingNo;
//	private EditText mEdtMeetingPwd;
//	private final static int STYPE = ZhuMuStatus.MeetingService.USER_TYPE_ZOOM;
//
//	public static String user_id = "";
//
//	public static String token = "";
//
//	public static String user_name = "";
//
//	public static String meet_num = "";
//	private Meeting mMeeting;
//	private static final int SHOW_MEET_NUM = 100;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.video_main);
//		ZhuMuSDK sdk = ZhuMuSDK.getInstance();
//		sdk.initSDK(this,this);
//		x.Ext.init(this.getApplication());
//
//		String url = "https://api.zhumu.me/v1/user/get";
//		RequestParams params = new RequestParams(url);
//		params.addBodyParameter("api_key", API_KEY);
//		params.addBodyParameter("api_secret", API_SECRET);
//		params.addBodyParameter("logintype", "3");
//		params.addBodyParameter("loginname", "SDKTest@zhumu.me");
//		x.http().post(params, new Callback.CommonCallback<String>() {
//			@Override
//			public void onSuccess(String s) {
//				Log.e("MainActivity", "瞩目服务器返回结果：" + s);
//				mMeeting = JSON.parseObject(s, Meeting.class);
//				if(mMeeting != null){
//					user_id = mMeeting.getId();
//					token = mMeeting.getToken();
//					user_name = mMeeting.getUsername();
//					meet_num = mMeeting.getPmi();
//					mHandler.sendEmptyMessage(SHOW_MEET_NUM);
//				}else{
//					log.W("onCreate():--mMeeting is null !!!");
//				}
//			}
//
//			@Override
//			public void onError(Throwable throwable, boolean b) {
//
//			}
//
//			@Override
//			public void onCancelled(CancelledException e) {
//
//			}
//
//			@Override
//			public void onFinished() {
//
//			}
//		});
//
//		mEdtMeetingNo = (EditText) findViewById(R.id.edtMeetingNo);
//		mEdtMeetingPwd = (EditText) findViewById(R.id.edtMeetingPassword);
//		ZhuMuSDK zhuMuSDK = ZhuMuSDK.getInstance();
//		if (zhuMuSDK.isInitialized()) {
//			zhuMuSDK.addListener(this);
//		}
//	}
//
////	@Override
////	protected void attachBaseContext(Context base) {
////		super.attachBaseContext(base);
////		MultiDex.install(this);
////	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		mEdtMeetingNo.setText(meet_num);
//	}
//
//	@Override
//	protected void onDestroy() {
//		ZhuMuSDK zhuMuSDK = ZhuMuSDK.getInstance();
//
//		if (zhuMuSDK.isInitialized()) {
//			zhuMuSDK.removeListener(this);
//		}
//		super.onDestroy();
//	}
//
//	public void onClickBtnJoinMeeting(View view) {
//		mEdtMeetingNo.setText(meet_num);
//		meet_num = mEdtMeetingNo.getText().toString().trim();
//		String pwd = mEdtMeetingPwd.getText().toString().trim();
//		if (meet_num.length() == 0) {
//			Toast.makeText(
//					this,
//					"You need to enter a meeting number which you want to join.",
//					Toast.LENGTH_LONG).show();
//			return;
//		}
//
//		ZhuMuSDK zhuMuSDK = ZhuMuSDK.getInstance();
//
//		if (!zhuMuSDK.isInitialized()) {
//			Toast.makeText(this, "SDK has not been initialized successfully",
//					Toast.LENGTH_LONG).show();
//			return;
//		}
//
//
//		JoinMeetOptions opts = new JoinMeetOptions();
////		opts.no_driving_mode = true;
////		opts.no_invite = true;
////		opts.no_meeting_end_message = true;
////		opts.no_titlebar = true;
////		opts.no_bottom_toolbar = true;
////		opts.no_dial_in_via_phone = true;
////		opts.no_dial_out_to_phone = true;
////		opts.no_disconnect_audio = true;
//		opts.invite_options = MeetInviteOptions.INVITE_VIA_EMAIL + MeetInviteOptions.INVITE_VIA_SMS;
//		opts.meeting_views_options = MeetViewsOptions.NO_BUTTON_SHARE;
////		opts.no_audio = true;
////		opts.no_video = true;
//
////		int ret = zhuMuSDK.joinMeeting(this, meet_num, user_name, opts);
//		int ret = zhuMuSDK.joinMeeting(this, meet_num, user_name, pwd,opts);
//
//		log.D("onClickBtnJoinMeeting, ret=" + ret + " meet_num==" + meet_num);
//	}
//
//	public void onClickBtnStartMeeting(View view) {
////		mEdtMeetingNo.setText(meet_num);
//		meet_num = mEdtMeetingNo.getText().toString().trim();
//
//		if (meet_num.length() == 0) {
//			Toast.makeText(this,
//					"You need to enter a scheduled meeting number.",
//					Toast.LENGTH_LONG).show();
//			return;
//		}
//
//		ZhuMuSDK zhuMuSDK = ZhuMuSDK.getInstance();
//
//		if (!zhuMuSDK.isInitialized()) {
//			Toast.makeText(this, "SDK has not been initialized successfully",
//					Toast.LENGTH_LONG).show();
//			return;
//		}
//
//		StartMeetOptions opts = new StartMeetOptions();
////		opts.no_driving_mode = true;
////		opts.no_invite = true;
////		opts.no_meeting_end_message = true;
////		opts.no_titlebar = true;
////		opts.no_bottom_toolbar = true;
////		opts.no_dial_in_via_phone = true;
////		opts.no_dial_out_to_phone = true;
////		opts.no_disconnect_audio = true;
//		opts.invite_options = MeetInviteOptions.INVITE_ENABLE_ALL;
//		opts.meeting_views_options = MeetViewsOptions.NO_BUTTON_SHARE + MeetViewsOptions.NO_BUTTON_VIDEO;
//		opts.no_audio = true;
////		opts.no_video = true;
//
//		int ret = zhuMuSDK.startMeeting(this, user_id, token, STYPE,
//				meet_num, user_name, opts);
//
//		log.D("onClickBtnStartMeeting, ret=" + ret + " meet_num==" + meet_num);
//	}
//
//	public void onClickBtnStartInstantMeeting(View view) {
//
//		ZhuMuSDK zhuMuSDK = ZhuMuSDK.getInstance();
//
//		if (!zhuMuSDK.isInitialized()) {
//			Toast.makeText(this, "SDK has not been initialized successfully",
//					Toast.LENGTH_LONG).show();
//			return;
//		}
//
//		InstantMeetOptions opts = new InstantMeetOptions();
////		opts.no_driving_mode = true;
////		opts.no_invite = true;
////		opts.no_meeting_end_message = true;
////		opts.no_titlebar = true;
////		opts.no_bottom_toolbar = true;
////		opts.no_dial_in_via_phone = true;
////		opts.no_dial_out_to_phone = true;
////		opts.no_disconnect_audio = true;
//		opts.invite_options = MeetInviteOptions.INVITE_ENABLE_ALL;
//		opts.meeting_views_options = MeetViewsOptions.NO_BUTTON_SHARE + MeetViewsOptions.NO_BUTTON_VIDEO;
////		opts.no_video = true;
//
//		int ret = zhuMuSDK.startInstantMeeting(this, user_id, token,
//				STYPE, user_name, opts);
//
//		log.D("onClickBtnStartInstantMeeting, ret=" + ret);
//	}
//
//	@Override
//	public void onMeetingEvent(int meetingEvent, int errorCode,
//			int internalErrorCode) {
//
//		if (meetingEvent == ZhuMuStatus.MeetingEvent.MEETING_CONNECT_FAILED
//				&& errorCode == ZhuMuStatus.MeetingError.MEETING_ERROR_CLIENT_INCOMPATIBLE) {
//			Toast.makeText(this, "Version of SDK is too low!",
//					Toast.LENGTH_LONG).show();
//		}
//	}
//
//	@Override
//	public void onZhuMuSDKInitializeResult(int errorCode, int internalErrorCode) {
//		log.I("onZhuMuSDKInitializeResult, errorCode=" + errorCode + ", internalErrorCode=" + internalErrorCode);
//
//		if(errorCode != ZhuMuStatus.ZMError.ZM_ERROR_SUCCESS) {
//			Toast.makeText(this, "Failed to initialize SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode, Toast.LENGTH_LONG).show();
//		}
//	}
//
//	private Handler mHandler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case SHOW_MEET_NUM:
//				mEdtMeetingNo.setText(meet_num);
//				break;
//			case 2:
//				break;
//
//			default:
//				break;
//			}
//		}
//
//	};
//}