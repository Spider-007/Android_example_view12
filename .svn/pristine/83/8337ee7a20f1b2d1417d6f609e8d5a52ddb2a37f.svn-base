package com.minxing.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.minxing.client.AppConstants;
import com.minxing.client.receiver.PushReceiverBean.ReceiverBean;
import com.minxing.client.util.FileUtils;
import com.minxing.kit.MXConstants;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ResourceUtil;

public class MXKitPushReceiver extends BroadcastReceiver {

	private ReceiverBean receiverBean;
	private PushReceiverFunctionService mPushReceiverFunctionService;
	public Context mContext;

    @Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		if (intent.getAction().equals(MXConstants.BroadcastAction.MXKIT_OUTSIDE_PUSH_MESSAGE)) {
			final String pushData = intent.getStringExtra(MXConstants.IntentKey.MXKIT_OUTSIDE_PUSH_MESSAGE_KEY);
//			Toast.makeText(context, pushData+"", Toast.LENGTH_SHORT).show();
			try {
				FileUtils.saveMessageLog(pushData);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (pushData != null && !"".equals(pushData)) {
				JSONObject data = null;
				//判断是否为JSON数据格式
				try {
					data = JSONObject.parseObject(pushData);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// JSON数据格式处理，此处处理的是敏行自己的升级推送，第三方用户如果自行实现升级，可忽略删除此部分代码。
				if(data != null){
					String event = data.getString("event");
//					String push_info = (String)data.getString("push_info");    //正确
					if (event != null && !"".equals(event) && "upgrade".equals(event)) {
						long versonCode = data.getIntValue("version_code");
						long currentVersionCode = ResourceUtil.getVerCode(context);
						if (versonCode > currentVersionCode) {
							PreferenceUtils.saveUpgradeMark(context);
						} else {
							PreferenceUtils.clearUpgradeMark(context);
						}
						Intent refresh = new Intent();
						refresh.setAction(AppConstants.MXCLIENT_REFRESH_NEW_VERSION);
						context.sendBroadcast(refresh);
					}else {
						//若不是升级所需要的而是有关业务的处理部分
						Gson gson = new Gson();
						try{
							receiverBean = gson.fromJson(pushData, ReceiverBean.class);
							receiverBean.mPush =  JSONObject.parseObject(receiverBean.push_info);

						}catch (Exception e){
							e.printStackTrace();
						}

						if(receiverBean != null){
							if(mPushReceiverFunctionService == null)
							mPushReceiverFunctionService = new PushReceiverFunctionService(mContext,receiverBean);
							if(TextUtils.isEmpty(receiverBean.push_code)){
								receiverBean.push_code = "";
							}

							if (receiverBean.is_need_receipt == 1)
								mPushReceiverFunctionService.ReceiptMessage(receiverBean.push_id);
							if(receiverBean.push_code.equals("0")){
								//--------------需要发起同步请求----------------------------------
								mPushReceiverFunctionService.SynchData(receiverBean.push_id, receiverBean.is_need_receipt);

							}else if(receiverBean.push_code.equals("1")){
								//--------------版本更新提醒--------------------------------------
                                if(receiverBean.comp_code.equalsIgnoreCase("com_clientbase")) {
									mPushReceiverFunctionService.NeedUpdateAppVersion(receiverBean.push_id, receiverBean.is_need_receipt);
								}else {
									ArrayList arrayList = IsHasApplicationAuthority(receiverBean.comp_code);
									if(arrayList != null && arrayList.size()>0){
										mPushReceiverFunctionService.NeedUpdateAppVersion(receiverBean.push_id, receiverBean.is_need_receipt);
									}
								}

							}else if(receiverBean.push_code.equals("2")){
								//---------------用户停用提醒------------------------------------
								if(receiverBean.comp_code.equalsIgnoreCase("com_clientbase")) {
									mPushReceiverFunctionService.SystemUserStop(receiverBean.push_id, receiverBean.is_need_receipt);
								}else {
									ArrayList arrayList = IsHasApplicationAuthority(receiverBean.comp_code);
									if(arrayList != null && arrayList.size()>0){
										mPushReceiverFunctionService.SystemUserStop(receiverBean.push_id, receiverBean.is_need_receipt);
									}
								}


							}else if(receiverBean.push_code.equals("3")){
								//---------------设备停用提醒------------------------------------
								if(receiverBean.comp_code.equalsIgnoreCase("com_clientbase")) {
									mPushReceiverFunctionService.UserDeceiveStop(receiverBean.push_id, receiverBean.is_need_receipt);
								}else {
									ArrayList arrayList = IsHasApplicationAuthority(receiverBean.comp_code);
									if(arrayList != null && arrayList.size()>0){
										mPushReceiverFunctionService.UserDeceiveStop(receiverBean.push_id, receiverBean.is_need_receipt);
									}
								}

							}else if(receiverBean.push_code.equals("4")){
								//--------------------------------清除缓存提醒-------------------
								if(receiverBean.comp_code.equalsIgnoreCase("com_clientbase")) {
									mPushReceiverFunctionService.ClearCacheData(receiverBean.push_id,receiverBean.is_need_receipt);
								}else {
									ArrayList arrayList = IsHasApplicationAuthority(receiverBean.comp_code);
									if(arrayList != null && arrayList.size()>0){
										mPushReceiverFunctionService.ClearCacheData(receiverBean.push_id,receiverBean.is_need_receipt);
									}
								}

							}else if(receiverBean.push_code.equals("5")){
								//-------------------------------------磁贴变更-------------------------------
								mPushReceiverFunctionService.ChangeTiles(receiverBean.push_id, receiverBean.is_need_receipt);

							}else if(receiverBean.push_code.equals("6")){
								//-------------------------------------挂失退出-------------------------------
								mPushReceiverFunctionService.DeviceLossPush(receiverBean.push_id,receiverBean.is_need_receipt);

							}else if (receiverBean.push_code.equals("200")) {
								//---------------日程待确认提醒----------------
								if (receiverBean.comp_code.equalsIgnoreCase("com_clientbase")) {
									mPushReceiverFunctionService.ToConfirmScheduleAlert(receiverBean.push_id, receiverBean.is_need_receipt);
								} else {
									ArrayList arrayList = IsHasApplicationAuthority(receiverBean.comp_code);
									if (arrayList != null && arrayList.size() > 0) {
										mPushReceiverFunctionService.ToConfirmScheduleAlert(receiverBean.push_id, receiverBean.is_need_receipt);
									}
								}

							} else if (receiverBean.push_code.equals("201")) {
								//---------------日程成立提醒------------------------------------
								if (receiverBean.comp_code.equalsIgnoreCase("com_clientbase")) {
									mPushReceiverFunctionService.ScheduleSetupAlert(receiverBean.push_id, receiverBean.is_need_receipt);
								} else {
									ArrayList arrayList = IsHasApplicationAuthority(receiverBean.comp_code);
									if (arrayList != null && arrayList.size() > 0) {
										mPushReceiverFunctionService.ScheduleSetupAlert(receiverBean.push_id, receiverBean.is_need_receipt);
									}
								}

							} else if (receiverBean.push_code.equals("202")) {
								//---------------日程修改提醒------------------------------------
								if (receiverBean.comp_code.equalsIgnoreCase("com_clientbase")) {
									mPushReceiverFunctionService.ScheduleUpdateAlert(receiverBean.push_id, receiverBean.is_need_receipt);
								} else {
									ArrayList arrayList = IsHasApplicationAuthority(receiverBean.comp_code);
									if (arrayList != null && arrayList.size() > 0) {
										mPushReceiverFunctionService.ScheduleUpdateAlert(receiverBean.push_id, receiverBean.is_need_receipt);
									}
								}

							}else if(receiverBean.push_code.equals("301")){
								//---------------项目定制消息------------------------------------

							}else if(receiverBean.push_code.equals("101")||receiverBean.push_code.equals("102")||receiverBean.push_code.equals("204")){
								//---------------工作流推送------------------------------------
								ArrayList arrayList = IsHasApplicationAuthority(receiverBean.comp_code);
								if(arrayList != null && arrayList.size()>0) {
									mPushReceiverFunctionService.OAflowFormPushInfo(receiverBean);
								}
							}else if(receiverBean.push_code.equals("103")||receiverBean.push_code.equals("205")){
								//---------------通用表单推送------------------------------------
								ArrayList arrayList = IsHasApplicationAuthority(receiverBean.comp_code);
								if(arrayList != null && arrayList.size()>0) {
									mPushReceiverFunctionService.commFormPushInfo(receiverBean);
								}
							}else if(receiverBean.push_code.equals("207")){
								//---------------提醒中心推送------------------------------------
								ArrayList arrayList = IsHasApplicationAuthority(receiverBean.comp_code);
								if(arrayList != null && arrayList.size()>0) {
									mPushReceiverFunctionService.comAlertPushInfo(receiverBean);
								}
							}else if(receiverBean.push_code.equals("1010")){
								ArrayList arrayList = IsHasApplicationAuthority(receiverBean.comp_code);
								if(arrayList != null && arrayList.size()>0) {
									mPushReceiverFunctionService.completePushInfoWorkFlowList(receiverBean);
								}

							}
						}

					}

				} else {
					//非JSON数据格式处理
				}

			}
		}
	}
	//------------------------------------判断是否有该应用权限--------------------------------------
	private SQLiteDatabase db;
	public ArrayList IsHasApplicationAuthority(String compcode){
		ArrayList SelectResultList = new ArrayList();
//		db = DBManager.getInstance(mContext);
		db = DBCipherManager.getInstance(mContext).db;
		if (db.isOpen()) {
			Cursor cursor = null;
			try
			{
				cursor = db.rawQuery("SELECT * from app_info  WHERE comp_code= '"+compcode+
						"' and status_flag> 0 ", null);
				while (cursor.moveToNext()) {
					String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
					if(comp_code != null && !"".equals(comp_code))
						SelectResultList.add(comp_code);
				}
				cursor.close();
			}catch (Exception e){
                e.printStackTrace();
			}finally {
				if(cursor != null){
					cursor.close();
				}
			}

		}
		return SelectResultList;
	}


}
