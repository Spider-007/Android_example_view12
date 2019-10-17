//package com.htmitech.emportal.ui.commonoptions;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.htmitech.emportal.HtmitechApplication;
//import com.htmitech.emportal.R;
//import com.htmitech.emportal.base.IBaseCallback;
//import com.htmitech.emportal.base.SlidingBackAcitivity;
//import com.htmitech.emportal.entity.OAConText;
//import com.htmitech.emportal.ui.commonoptions.data.AddUserOptionsResult;
//import com.htmitech.emportal.ui.commonoptions.data.DelUserOptionsEntity;
//import com.htmitech.emportal.ui.commonoptions.data.OptionsParameters;
//import com.htmitech.emportal.ui.commonoptions.data.Optionsdel;
//import com.htmitech.emportal.ui.commonoptions.task.AddUserOptionsModel;
//import com.htmitech.emportal.ui.login.data.logindata.UserOptionListEntity;
//import com.htmitech.emportal.ui.login.model.task.LoginModel;
///**常用意见入口*/
//public class OptionSelectActivity extends SlidingBackAcitivity implements
//		OnClickListener, IBaseCallback, OptionSelectAdapter.Callback {
//
//	private ListView options_listview;
//	private TextView tv_newoptions;
//	private List<AddUserOptionsResult> list;
//	private OptionSelectAdapter adapter;
//	private boolean isDel = false;
//	private int viewId;
//	private String datas;
//	private boolean isSuccess = false;
//	private View mEmptyView;
//	private ImageView iv_no_messages;
//	private TextView text_no_messages;
//	ProgressDialog progressDialog ;
//	ProgressDialog progressDialog1 ;
//	public String app_id;//appid 新加参数
//
//	protected int getLayoutById() {
//
//		return R.layout.activity_optionselect;
//
//	}
//
//	protected void initView() {
//		list = new ArrayList<AddUserOptionsResult>();
//		// if (list.size()==0) {
//		// mEmptyView = View.inflate(OptionSelectActivity.this,
//		// R.layout.activity_empty, null);
//		// }
//		findViewById(R.id.imgview_titlebar_back).setOnClickListener(this);
//		((TextView) findViewById(R.id.textview_titlebar_title)).setText("常用意见");
//		findViewById(R.id.imgview_titlebar_back).setOnClickListener(this);
//		options_listview = (ListView) findViewById(R.id.options_listview);
//		tv_newoptions = (TextView) findViewById(R.id.tv_newoptions);
//		iv_no_messages = (ImageView) findViewById(R.id.iv_no_messages);
//		text_no_messages = (TextView) findViewById(R.id.text_no_messages);
//		tv_newoptions.setOnClickListener(this);
//		if(list != null){
//			iv_no_messages.setVisibility(View.GONE);
//			text_no_messages.setVisibility(View.GONE);
//		}
//
//		Intent intent = getIntent();
//		datas = intent.getStringExtra("extra_datas");
//		app_id = getIntent().getStringExtra("app_id");
//
//		adapter = new OptionSelectAdapter(OptionSelectActivity.this, list,
//				OptionSelectActivity.this);
//		options_listview.setAdapter(adapter);
//	}
//
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		// 获取意见列表
//
//		LoginModel loginModel = new LoginModel(OptionSelectActivity.this);
//		loginModel
//				.getDataFromServerByType(
//						LoginModel.TYPE_GetUserOptions,
//						OAConText.getInstance(HtmitechApplication.getInstance()).UserID+"&app_id="+app_id);
//		progressDialog1 = new ProgressDialog
//				(OptionSelectActivity.this);
//				progressDialog1.setTitle("常用意见");
//				progressDialog1.setMessage("加载中...");
//				progressDialog1.setCancelable(true);//通过back键不消失
//				progressDialog1.show();
//
//	}
//
//	@Override
//	public void onSuccess(int requestTypeId, Object result) {
//		if (requestTypeId == LoginModel.TYPE_GetUserOptions) {
//
//			if (result != null && result instanceof UserOptionListEntity) {
//				UserOptionListEntity UserOptionListResult = (UserOptionListEntity) result;
//				if (UserOptionListResult.getResult() != null
//						&& UserOptionListResult.getResult().getItems() != null) {
//					isSuccess = true;
//					progressDialog1.dismiss();
//					List<AddUserOptionsResult> mdata = new ArrayList<AddUserOptionsResult>();
//					for (int i = 0; i < UserOptionListResult.getResult()
//							.getItems().size(); i++) {
//						AddUserOptionsResult addUserOptionsResult = new AddUserOptionsResult();
//						addUserOptionsResult.setId(UserOptionListResult
//								.getResult().getItems().get(i).getId());
//						addUserOptionsResult.setValue(UserOptionListResult
//								.getResult().getItems().get(i).getValue());
//						mdata.add(addUserOptionsResult);
//
//					}
//
//					if (list != null) {
//						list.clear();
//					}
//					list.addAll(mdata);
//
//					if (datas != null && datas.equals("ClientTabActivity")) {
//						adapter.setOptionCheckVisiable(false);
//					}
//
//					adapter.notifyDataSetChanged();
//
//				}
//			}
//
//		}
//		if (requestTypeId == AddUserOptionsModel.TYPE_GET_DEL_OPTIONS) {
//
//			if (result != null && result instanceof DelUserOptionsEntity) {
//				DelUserOptionsEntity mDelUserOptionsEntity = (DelUserOptionsEntity) result;
//				if (mDelUserOptionsEntity.getResult() != null) {
//					isDel = mDelUserOptionsEntity.getResult();
//					progressDialog.dismiss();
//					if (isDel) {
//						adapter.removeItem(viewId);
//
//					}
//
//				}
//			}
//		}
//
//	}
//
//	@Override
//	public void onFail(int requestTypeId, int statusCode, String errorMsg,
//			Object result) {
//		// TODO Auto-generated method stub
//		Toast.makeText(OptionSelectActivity.this, errorMsg + "请您稍后重试", Toast.LENGTH_SHORT)
//		.show();
//		if (requestTypeId == AddUserOptionsModel.TYPE_GET_DEL_OPTIONS) {
//
//			progressDialog.dismiss();
//		}else{
//			progressDialog1.dismiss();
//
//		}
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_newoptions:
//
//			Intent intent = new Intent(OptionSelectActivity.this,
//					OptionNewActivity.class);
//			intent.putExtra("app_id",app_id);
//			startActivity(intent);
//
//			break;
//		case R.id.imgview_titlebar_back:
//			finish();
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//	@Override
//	public void click(final View v) {
//		// TODO Auto-generated method stub
//
//		switch (v.getId()) {
//		case R.id.iv_check_select:
//			String commonOptions = list.get((Integer) v.getTag()).getValue();
//			if (commonOptions != null) {
//				Intent intent = new Intent();
//				intent.putExtra("data1_return", commonOptions);
//				setResult(RESULT_OK, intent);
//			}
//			finish();
//
//			break;
//		case R.id.tv_delselect:
//			Dialog dialog = new AlertDialog.Builder(OptionSelectActivity.this)
//					.setTitle("删除对话框")
//					.setIcon(R.drawable.ic_launcher)
//					.setMessage("确认删除吗？")
//					// 相当于点击确认按钮
//					.setPositiveButton("确认",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int which) {
//									// 删除意见调用
//									OptionsParameters mOptionsParameters = new OptionsParameters();
//									mOptionsParameters.context = OAConText
//											.getInstance(OptionSelectActivity.this);
//									mOptionsParameters.app_id = app_id;
//									mOptionsParameters.option = Optionsdel
//											.getInstance(
//													OptionSelectActivity.this,
//													list.get(
//															(Integer) v
//																	.getTag())
//															.getId());
//
//									AddUserOptionsModel addUserOptionsModel = new AddUserOptionsModel(
//											OptionSelectActivity.this);
//									addUserOptionsModel
//											.getDataFromServerByType(
//													AddUserOptionsModel.TYPE_GET_DEL_OPTIONS,
//													mOptionsParameters);
//									viewId = (Integer) v.getTag();
//									 progressDialog = new ProgressDialog
//												(OptionSelectActivity.this);
//												progressDialog.setTitle("常用意见");
//												progressDialog.setMessage("删除中...");
//												progressDialog.setCancelable(true);//通过back键不消失
//												progressDialog.show();
//
//
//								}
//							})
//					// 相当于点击取消按钮
//					.setNegativeButton("取消",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int which) {
//									// TODO Auto-generated method
//									// stub
//
//								}
//							}).create();
//			dialog.show();
//
//			break;
//		case R.id.tv_editselect:
//			String data = list.get((Integer) v.getTag()).getId() + "|"
//					+ list.get((Integer) v.getTag()).getValue();
//			Intent intent = new Intent(OptionSelectActivity.this,
//					OptionEditActivity.class);
//			intent.putExtra("extra_data", data);
//			intent.putExtra("app_id",app_id);
//			startActivity(intent);
//
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//}
