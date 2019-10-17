package com.htmitech.ztcustom.zt.chinarailway;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.domain.SaveChildAccount;
import com.htmitech.ztcustom.zt.domain.longin.ListDetails;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;

import htmitech.com.componentlibrary.entity.RequestEntity;
import htmitech.com.componentlibrary.unit.PreferenceUtils;

/**
 * 子账号激活
 * @author htrf-pc
 *
 */
public class ChilDaccountYZActivity extends BaseFragmentActivity implements OnClickListener{
	private TextView tv_fn5_title_name;
	private String titleName;
	private ImageView ibn_fn5_back;
	private Button login_btn;
	private ListDetails mListDetails;
	private EditText username,password;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zt_activity_childaccount_wjh);
		Intent intent = getIntent();
		titleName = intent.getStringExtra("titleName");
		mListDetails = (ListDetails) intent.getSerializableExtra("ListDetails");
		initView();
		initData();
//		PreferenceUtils.getRequestEntity(LoginActivity.this)
	}
	public void initView(){
		tv_fn5_title_name = (TextView) findViewById(R.id.tv_fn5_title_name);
		ibn_fn5_back = (ImageView) findViewById(R.id.ibn_fn5_back);
		login_btn = (Button) findViewById(R.id.login_btn);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
	}
	public void initData(){
		tv_fn5_title_name.setText(""+titleName);
		login_btn.setOnClickListener(this);
		ibn_fn5_back.setOnClickListener(this);
		username.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence value, int start, int before, int count) {
				if (value.toString().trim().length() == 0) {
					login_btn.setEnabled(false);
				} else {
					if (username.getText().toString().trim().length() > 0) {
						login_btn.setEnabled(true);
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		password.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence value, int start, int before, int count) {
				if (value.toString().trim().length() == 0) {
					login_btn.setEnabled(false);
				} else {
					if (password.getText().toString().trim().length() > 0) {
						login_btn.setEnabled(true);
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() ==R.id.ibn_fn5_back ){
			this.finish();
		}else if(arg0.getId() ==R.id.login_btn){
			showProgressDialog(this);
			SaveChildAccount mSaveChildAccount = new SaveChildAccount();
			RequestEntity mRequestEntity = PreferenceUtils.getRequestEntity(this);
			mSaveChildAccount.UserId = mRequestEntity.UserId;
			mSaveChildAccount.BsId = mListDetails.BusinessTypeId;
			mSaveChildAccount.BsName = mListDetails.BusinessTypeName;
			mSaveChildAccount.ChildAccount = username.getText().toString();
			mSaveChildAccount.ChildPassword = password.getText().toString();
			mSaveChildAccount.CVersion = mRequestEntity.CVersion;
			mSaveChildAccount.SCode = mRequestEntity.SCode;
			mSaveChildAccount.IsDev = mRequestEntity.IsDev;

			AnsynHttpRequest.request(this, mSaveChildAccount,
					CHTTP.SAVECHILDACCOUNT, CHTTP.POST,
					new ObserverCallBack() {
						@Override
						public void success(String successMessage) {
							JSONObject object = JSON.parseObject(successMessage);
							if(object == null){
								return;
							}
							JSONObject Result = object.getJSONObject("Result");
							int status = object.getIntValue("Status");
							if(status == 1){
								//ZTCustomInit.get().getmCache().getmGetChildAccount().ListDetail
								for(ListDetails mListDetails: ZTCustomInit.get().getmCache().getmGetChildAccount().ListDetail){
									if(Result.getShort("BusinessTypeId").equals(""+mListDetails.BusinessTypeId)){
										mListDetails.AccountStatus = Result.getString("Status");
										ZTCustomInit.get().getmCache().setmListDetails(mListDetails.BusinessTypeId);
										break;
									}
								}
								RequestEntity mRequestEntity = PreferenceUtils.getRequestEntity(ChilDaccountYZActivity.this);
								InitDamage mInitDamage = new InitDamage();
								mInitDamage.init(ChilDaccountYZActivity.this, mHandler, mRequestEntity);

								Toast.makeText(ChilDaccountYZActivity.this, "验证成功,正在初始化信息", Toast.LENGTH_SHORT).show();
							}else if(status == 2){
								Toast.makeText(ChilDaccountYZActivity.this, "子帐号登录失败！", Toast.LENGTH_SHORT).show();
								dimssDialog();
							}

						}

						@Override
						public void notNetwork() {
							// TODO Auto-generated method stub
							dimssDialog();
						}

						@Override
						public void fail(String exceptionMessage) {
							// TODO Auto-generated method stub

						}
					});
		}

	}
	
	@SuppressLint("HandlerLeak")
	public Handler mHandler = new Handler(){

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int num = (Integer) msg.obj;
			switch (msg.what) {
			// 超时异常
			case 0:
				if(num >= CHTTP.NUM){
					dimssDialog();
					ChilDaccountYZActivity.this.finish();
				}
				break;
			}
		}
	
	};
}
