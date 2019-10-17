package com.htmitech.emportal.ui.appcenter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.appcenter.adapter.AppCenterSelectAdapter;
import com.htmitech.emportal.ui.appcenter.data.OcuListEntity;
import com.htmitech.emportal.ui.appcenter.data.OcuSaveEntity;
import com.htmitech.emportal.ui.appcenter.model.task.AppCenterModel;
import com.htmitech.emportal.utils.Base64;
import com.minxing.client.RootActivity;
import com.minxing.client.util.Utils;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXAppInfo;
import com.minxing.kit.api.bean.MXError;
import com.minxing.kit.api.callback.AppInfoCallback;

/** 用于选择应用程序的快捷方式*/
public class AppCenterSelectActivity extends RootActivity implements IBaseCallback {
	private final static String TAG = AppCenterSelectActivity.class.getName();
	private ImageButton leftBackButton = null;
	private ImageButton rightAddButton = null;
	private Button rightTextButton = null;
	
	

	/** 当前用户已经选择的快捷方式列表 */
	private List<MXAppInfo> selectOcuList = null; 
	/**当前用户所有可选的快捷方式列表*/
	private List<MXAppInfo> allOcuList = null; 
	private AppCenterSelectAdapter appCenterSelectAdapter=null;
	private GridView appCenterSelectGridView=null;
//	private AppCenterManager appCenterManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appcenterselect);
		appCenterSelectGridView=(GridView)findViewById(R.id.center_gridview);
		leftBackButton = (ImageButton) findViewById(R.id.title_left_button);
		leftBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finishWithAnimation();
			}
		});

		rightAddButton = (ImageButton) findViewById(R.id.title_right_image_button);
		rightAddButton.setVisibility(View.GONE);
		rightAddButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});

		rightTextButton = (Button) findViewById(R.id.title_right_button);
		rightTextButton.setText("保存");
		rightTextButton.setVisibility(View.VISIBLE);
		rightTextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//发起网络请求，保存当前用户已经自定义的快捷键列表
		      	AppCenterModel getocuListModel = new AppCenterModel(AppCenterSelectActivity.this);
		      	OcuSaveEntity ocuSaveEntity=new OcuSaveEntity();
		      	ocuSaveEntity.Context=OAConText.getInstance(AppCenterSelectActivity.this);
		      	List<MXAppInfo> ocuInfos=appCenterSelectAdapter.getSelectList();
		      	//去除已经无效的app（之前订阅过，但后来取消掉了。或服务器设定权限变更了
		      	if(ocuInfos!=null &&ocuInfos.size()!=0){
		      		if (allOcuList != null && allOcuList.size() > 0){
		      			for(int j=0;j<ocuInfos.size();j++){
		      				boolean bIN = false;
		      				for (int a=0; a < allOcuList.size(); a++){
		      					String appId = ocuInfos.get(j).getAppID() ;
								if(!TextUtils.isEmpty(appId)){
									if (ocuInfos.get(j).getAppID().equalsIgnoreCase(allOcuList.get(a).getAppID())){
										bIN = true;
										ocuInfos.get(j).setName(allOcuList.get(a).getName());
										ocuInfos.get(j).setAppID(allOcuList.get(a).getAppID());
										ocuInfos.get(j).setAvatarUrl(allOcuList.get(a).getAvatarUrl());
									}
								}
		      				}
		      				if (!bIN)
		      					ocuInfos.remove(j);
		      			}
		      		}
		      		else
		      			ocuInfos.clear();
		      	}
		      	
		      	
		      	if(ocuInfos!=null &&ocuInfos.size()!=0){
		      		byte[] data=null;
					try {
						data = JSON.toJSONString(ocuInfos).getBytes("UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		      		ocuSaveEntity.Shortoucs=Base64.encodeBytes(data).toString();
//		      		Utils.toast(AppCenterSelectActivity.this, "保存了"+ocuInfos.size()+"条快捷方式：：："+JSON.toJSONString(ocuInfos), Toast.LENGTH_LONG);
		      	}else{
		      		ocuSaveEntity.Shortoucs="";
//		      		Utils.toast(AppCenterSelectActivity.this, "保存的快捷方式：：：NULL", Toast.LENGTH_LONG);
		      	}
		      	
				
		    	
		      	getocuListModel.getDataFromServerByType(AppCenterModel.TYPE_SAVE_CURRENTOCU_LIST,ocuSaveEntity);
		        
			}
		});

		
		MXAPI.getInstance(this).getAppCenterInfos(new AppInfoCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoading() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFail(MXError arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onResult(List<MXAppInfo> arg0) {
				// TODO Auto-generated method stub
//				Utils.toast(AppCenterSelectActivity.this, "获得的当前用户可查看的应用：" + JSON.toJSONString(ocuList), Toast.LENGTH_LONG);
				allOcuList=arg0;
				
				//使用该数据绘制GridView： http://114.112.89.94:8081/Design/#p=功能快链自定义
				
				//自定义特性
				//native://launchfilter/OA/shouwen?timestamp=1432891924&nonce=401609&login_name=huaizw
				//&mx_sso_token=005d982753ff53615d1507826b64e0b04225280812ee650238312de3edad62d0b562f9e9ed4154fc1660893de3b32e0a&signed=HiQJUbbrZ7v847lV0dfE%2BP9h06E%3D
				//使用以下代码调用默认的应用程序图标行为
				//MXAPI.getInstance(DemoAppCenterViewActivity.this).launchAppCenterOcu(ocuList.get(0));
			}
		});
		/*//获取当前用户能够看到的所有app图标
        MXAPI.getInstance(this).getAppCenterOcus(new OcuInfoCallback() {
			
			@Override
			public void onSuccess() {
			}
			
			@Override
			public void onLoading() {
			}
			
			@Override
			public void onFail(MXError error) {
			}
			
			@Override
			public void onResult(final List<MXAppInfo> ocuList) {
//				Utils.toast(AppCenterSelectActivity.this, "获得的当前用户可查看的应用：" + JSON.toJSONString(ocuList), Toast.LENGTH_LONG);
				allOcuList=ocuList;
				
				//使用该数据绘制GridView： http://114.112.89.94:8081/Design/#p=功能快链自定义
				
				
				//自定义特性
				//native://launchfilter/OA/shouwen?timestamp=1432891924&nonce=401609&login_name=huaizw
				//&mx_sso_token=005d982753ff53615d1507826b64e0b04225280812ee650238312de3edad62d0b562f9e9ed4154fc1660893de3b32e0a&signed=HiQJUbbrZ7v847lV0dfE%2BP9h06E%3D
				//使用以下代码调用默认的应用程序图标行为
				//MXAPI.getInstance(DemoAppCenterViewActivity.this).launchAppCenterOcu(ocuList.get(0));

			}
		});
        */
        
        //发起网络请求，获取所有当前用户已经自定义的快捷键列表
      	AppCenterModel getocuListModel = new AppCenterModel(AppCenterSelectActivity.this);
      	getocuListModel.getDataFromServerByType(AppCenterModel.TYPE_GET_CURRENTOCU_LIST, OAConText.getInstance(AppCenterSelectActivity.this).UserID);
        
      	

	}

	@Override
	public void onSuccess(int requestTypeId, Object result) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onSuccess");
		// TODO Auto-generated method stub
		if (requestTypeId == AppCenterModel.TYPE_GET_CURRENTOCU_LIST){ //获取所有快捷键成功
			if (result != null){
				ArrayList<HashMap<MXAppInfo, Boolean>> resultList=null; 
				OcuListEntity entity = (OcuListEntity)result;
				int size = 0;
				if (allOcuList != null)
					size=allOcuList.size();
				if (entity.getResult() != null && entity.getResult().trim() != "" && entity.getResult().length()>0){
					String Shortoucs="";
					try {
						Shortoucs = new String(Base64.decode(entity.getResult().getBytes("UTF-8")));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					selectOcuList = JSON.parseArray(Shortoucs, MXAppInfo.class);
//					Utils.toast(AppCenterSelectActivity.this, "取得了" +  selectOcuList.size() + "条快捷方式"+"Shortoucs:::::"+Shortoucs, Toast.LENGTH_SHORT);
					//是否被选中：是否是该用户的快捷方式
					resultList = new ArrayList<HashMap<MXAppInfo, Boolean>>();
					
					int selectSize=selectOcuList.size();
					for(int i=0;i<size;i++){
						HashMap<MXAppInfo, Boolean> hashMap=new HashMap<MXAppInfo, Boolean>();
						boolean flag=false;
						//用户已经选中的列表需选中
						for(int j=0;j<selectSize;j++){
							String appId = selectOcuList.get(j).getAppID() ;
							if(!TextUtils.isEmpty(appId)){
								if(selectOcuList.get(j).getAppID().equalsIgnoreCase(allOcuList.get(i).getAppID())){
									hashMap.put(allOcuList.get(i), true);
//									Utils.toast(AppCenterSelectActivity.this, "取得了" +  selectOcuList.size() + "条快捷方式"+"Shortoucs:::::", Toast.LENGTH_SHORT);
									flag=true;
									break;
								}
							}
						}
						if(!flag){
							hashMap.put(allOcuList.get(i), false);
						}
						resultList.add(hashMap);	
					}
				}else{
//					Utils.toast(AppCenterSelectActivity.this, "取得了0条快捷方式", Toast.LENGTH_SHORT);
					//是否被选中：是否是该用户的快捷方式
					resultList = new ArrayList<HashMap<MXAppInfo, Boolean>>();
					for(int i=0;i<size;i++){
						HashMap<MXAppInfo, Boolean> hashMap=new HashMap<MXAppInfo, Boolean>();
						hashMap.put(allOcuList.get(i), false);
						resultList.add(hashMap);	
					}
				}
				
				appCenterSelectAdapter = new AppCenterSelectAdapter(AppCenterSelectActivity.this, resultList);
				if(selectOcuList!=null && selectOcuList.size()!=0){
					appCenterSelectAdapter.setSelectList(selectOcuList);
				}
				appCenterSelectGridView.setAdapter(appCenterSelectAdapter);
					

			}
		}else if (requestTypeId == AppCenterModel.TYPE_SAVE_CURRENTOCU_LIST){ //保存快捷键成功
			Utils.toast(AppCenterSelectActivity.this, "保存成功", Toast.LENGTH_SHORT);
			setResult(ActivityResultConstant.SAVEOCUS_RESULT_OK, getIntent()); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
			finish();//此处一定要调用finish()方法
		}
	}

	@Override
	public void onFail(int requestTypeId, int statusCode, String errorMsg,
			Object result) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onFail");
		// TODO Auto-generated method stub
		if (requestTypeId == AppCenterModel.TYPE_GET_CURRENTOCU_LIST){ //获取所有快捷键失败
			Utils.toast(AppCenterSelectActivity.this, "获取所有快捷键失败：" + errorMsg, Toast.LENGTH_SHORT);
		}else if (requestTypeId == AppCenterModel.TYPE_SAVE_CURRENTOCU_LIST){ //保存快捷键失败
			Utils.toast(AppCenterSelectActivity.this, "保存所有快捷键失败：" + errorMsg, Toast.LENGTH_SHORT);
		}
	}

	
	
	
	
}
