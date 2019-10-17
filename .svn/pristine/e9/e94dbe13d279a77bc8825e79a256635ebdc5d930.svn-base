package com.htmitech.emportal.ui.detail;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.commonx.base.exception.HttpException;
import com.htmitech.commonx.base.http.ResponseInfo;
import com.htmitech.commonx.base.http.callback.RequestCallBack;
import com.htmitech.commonx.base.net.DownloadManager;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.entity.DocFileInfo;
import com.htmitech.emportal.entity.DocInfoParameters;
import com.htmitech.emportal.entity.DownFilesIsFinishResultInfo;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.entity.WordTextInfo;
import com.htmitech.emportal.ui.detail.model.DocInfoModel;
import com.htmitech.emportal.ui.widget.BaseDialog;
import com.htmitech.emportal.ui.widget.DialogCancelListener;
import com.htmitech.emportal.ui.widget.DialogConfirmListener;
import com.htmitech.emportal.ui.widget.MyAlertDialogFragment;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.emportal.utils.CompressUtil;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ShareLink;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 正文
 *
 * @author tenggang
 *
 */
public class TextFragment extends MyBaseFragment implements
		View.OnClickListener, IBaseCallback,ObserverCallBackType,ScrollViewListener{
	private SendAdapter mAdapter = new SendAdapter();
	private int pullStyle = 0;
	private static final int PULLDOWN_TOREFRESH = 0;
	private static final int PULLUP_TOLOADMORE = 1;
	private DialogFragment mNewFragment;
	private int mPageNum = 1;
	private boolean has_more = false;
	private LayoutInflater mInflater;
	private View mEmptyView;
	private TextView mTextView_content;
	private ProgressBar mProgressBar;
	private TextView mTextView_progress;
	private LinearLayout mLinearLayoutOperate;
	private LinearLayout mLinearlayout_text_download;

	private boolean mDownload;
	private DownloadManager mDownloadManager;
	private DocInfoModel mDocInfoModel;

	private String mDocAttId;

	String apiUrl = "";
	String apiUrlTemp = "";
	public int curItem = 0;
	public ShareLink shareLink = null;

	public boolean downFileForShare = false;

	public static DownFilesIsFinishResultInfo downFileResultInfo = null;
	private INetWorkManager netWorkManager;
	private DocInfoParameters mDocInfoParameters;
	private String app_id;
	private ObservableScrollView zoom;//手势放大

	/**
	 * 获取布局id，用于setContentView。
	 *
	 * @return id
	 */
	protected int getLayoutId() {
		return R.layout.fragment_text;
	}

	@SuppressLint("ValidFragment")
	public TextFragment( String app_id) {
		this.app_id = app_id;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		this.mInflater = inflater;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * 初始化View。
	 */
	protected void initViews() {
		initValues();
	}

	private void initValues() {
		if (getActivity() != null) {
			mEmptyView = View.inflate(getActivity(),
					R.layout.layout_empty_view, null);
		}
		mDocInfoModel = new DocInfoModel(this);
		mTextView_content = (TextView) findViewById(R.id.textview_text_content);
		zoom = (ObservableScrollView) findViewById(R.id.zoom);
		zoom.setScrollViewListener(this);
		findViewById(R.id.btn_text_download).setOnClickListener(this);

		findViewById(R.id.btn_text_share).setOnClickListener(this);
		if (DetailActivity2.currentActivity) {
			findViewById(R.id.btn_text_share).setEnabled(false);
			findViewById(R.id.btn_text_share).setVisibility(View.GONE);
		}
		mProgressBar = (ProgressBar) findViewById(R.id.progressbar_text_download);
		mLinearLayoutOperate = (LinearLayout) findViewById(R.id.linearlayout_text_operate);
		mLinearlayout_text_download = (LinearLayout) findViewById(R.id.linearlayout_text_download);
		mTextView_progress = (TextView) findViewById(R.id.textview_text_tip);

		//mDocId = ((DetailActivity) getActivity()).getDocId();
		if (DetailActivity2.currentActivity) {
			mDocAttId = ((DetailActivity2) getActivity()).mDocAttachmentID;
		} else {
			mDocAttId = ((DetailActivity) getActivity()).getMDocAttachmentID();
		}

		if (mDocAttId != null && mDocAttId.length() > 0) {
			mDownload = isFileExists(mDocAttId);
		} else {
			return;
		}

		if (mDownload) {
			((Button) findViewById(R.id.btn_text_download)).setText("查看");
		}

		mDocInfoParameters = new DocInfoParameters();
		mDocInfoParameters.context = OAConText.getInstance(HtmitechApplication.instance());

		if (DetailActivity2.currentActivity) {
			String item[] = ((DetailActivity2) getActivity()).apiUrl.split("[|]");
			mDocInfoParameters.context.UserID = item[0 + 3];
			mDocInfoParameters.context.OA_UserId = item[1 + 3];
			mDocInfoParameters.context.OA_UserName = item[2 + 3];
			//mDocInfoParameters.context.OA_UnitId = item.length > 6 ?  item[9 + 3] : "";
			mDocInfoParameters.context.OA_UnitId = "";

			mDocInfoParameters.DocId = item[3 + 3];
			mDocInfoParameters.DocType = item[5 + 3];
			// docType = item[5 + i];
			mDocInfoParameters.Kind = item[4 + 3];
			//getdocInfoParameters(mDocInfoParameters);
		} else {
			mDocInfoParameters.DocId = ((DetailActivity) getActivity()).getMDocAttachmentID();
			mDocInfoParameters.DocType = ((DetailActivity) getActivity()).getDocType();
			mDocInfoParameters.Kind = ((DetailActivity) getActivity()).getDocKind(); //2015-08-11
		}
		mDocInfoParameters.app_id=((DetailActivity) getActivity()).app_id;

		netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
		netWorkManager.logFunactionStart(getActivity(), this, "functionTextStart", LogManagerEnum.APP_DOC_TEXT.functionCode);

		//页面加载 请求文件下载结构

	}

	private DocInfoParameters getdocInfoParameters(DocInfoParameters mDocInfoParameters) {
		String item[] = ((DetailActivity2) getActivity()).apiUrl.split("[|]");
		mDocInfoParameters.context.UserID = item[0 + 3].substring(2);
		mDocInfoParameters.context.OA_UserId = item[1 + 3];
		mDocInfoParameters.context.OA_UserName = item[2 + 3];
		//mDocInfoParameters.context.OA_UnitId = item.length > 6 ?  item[9 + 3] : "";
		mDocInfoParameters.context.OA_UnitId = "";

		mDocInfoParameters.DocId = item[3 + 3];
		mDocInfoParameters.DocType = item[5 + 3];
		// docType = item[5 + i];
		mDocInfoParameters.Kind = item[4 + 3];
		return mDocInfoParameters;
	}


	public void toDeleteMsg(final int entityIndex) {
		mAdapter.setDeleteIndex(entityIndex);
		mNewFragment = MyAlertDialogFragment.newInstance("确实要删除此帖子吗?",
				R.drawable.prompt_warn, cancelListener, confirmListener,true);
		mNewFragment.show(getFragmentManager(), "dialog");
	}

	private DialogCancelListener cancelListener = new DialogCancelListener() {
		@Override
		public void onCancel(BaseDialog dialog) {
			// TODO Auto-generated method stub
			mNewFragment.dismiss();
		}
	};

	private DialogConfirmListener confirmListener = new DialogConfirmListener() {
		public void onConfirm(BaseDialog dialog) {
			mNewFragment.dismiss();
		}
	};

	public void onResume() {

		super.onResume();
	}

	@Override
	public void success(String requestValue, int type, String requestName) {

		if(requestName.equals("functionTextDoc")){
			DocInfoParameters mDocInfoParameters = new DocInfoParameters();
			mDocInfoParameters.context = OAConText
					.getInstance(HtmitechApplication.instance());
			if (DetailActivity2.currentActivity) {
				getdocInfoParameters(mDocInfoParameters);
			} else {
				mDocInfoParameters.DocId = ((DetailActivity) getActivity()).getMDocAttachmentID();//
				mDocInfoParameters.DocType = ((DetailActivity) getActivity()).getDocType();//
				mDocInfoParameters.Kind = ((DetailActivity) getActivity()).getDocKind(); //2015-08-11
			}
			mDocInfoParameters.app_id=((DetailActivity) getActivity()).app_id;
			mDocInfoModel.getDataFromServerByType(
					DocInfoModel.TYPE_GET_DOWNLOAD_DOCISFINISH,
					mDocInfoParameters);
		}else if(requestName.equals("functionTextStart")){
			mDocInfoModel.getDataFromServerByType(
					DocInfoModel.TYPE_GET_WORD_TEXTTASK, mDocInfoParameters);
		}
	}

	@Override
	public void fail(String exceptionMessage, int type, String requestName) {
		if(requestName.equals("functionTextDoc")){
			DocInfoParameters mDocInfoParameters = new DocInfoParameters();
			mDocInfoParameters.context = OAConText
					.getInstance(HtmitechApplication.instance());
			if (DetailActivity2.currentActivity) {
				getdocInfoParameters(mDocInfoParameters);
			} else {
				try {
					mDocInfoParameters.DocId = ((DetailActivity) getActivity()).getMDocAttachmentID();//
					mDocInfoParameters.DocType = ((DetailActivity) getActivity()).getDocType();//
					mDocInfoParameters.Kind = ((DetailActivity) getActivity()).getDocKind(); //2015-08-11
				}catch (Exception e){
					e.printStackTrace();
				}

			}
			mDocInfoParameters.app_id=((DetailActivity) getActivity()).app_id;
			mDocInfoModel.getDataFromServerByType(
					DocInfoModel.TYPE_GET_DOWNLOAD_DOCISFINISH,
					mDocInfoParameters);
		}else if(requestName.equals("functionTextStart")){
			mDocInfoModel.getDataFromServerByType(
					DocInfoModel.TYPE_GET_WORD_TEXTTASK, mDocInfoParameters);
		}
	}

	@Override
	public void notNetwork() {

	}

	@Override
	public void callbackMainUI(String successMessage) {

	}

	class SendAdapter extends BaseAdapter {
		ArrayList<List> arrayList = new ArrayList<List>();
		private int removeEntityIndex = -1;

		public void removeEntity() {
			if (removeEntityIndex < arrayList.size() && removeEntityIndex >= 0) {
				arrayList.remove(removeEntityIndex);
				notifyDataSetChanged();
				removeEntityIndex = -1;
			}
		}

		public List getDeleteEntity() {
			if (removeEntityIndex < arrayList.size() && removeEntityIndex >= 0) {
				return arrayList.get(removeEntityIndex);
			}
			return null;
		}

		public void setDeleteIndex(int index) {
			removeEntityIndex = index;
		}

		public void setData(java.util.List<List> temp, int pullStyle) {
			if (pullStyle == PULLDOWN_TOREFRESH) {
				arrayList.clear();
				arrayList.addAll(temp);
			} else if (pullStyle == PULLUP_TOLOADMORE) {
				arrayList.addAll(temp);
			}
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return arrayList.size();
		}

		@Override
		public Object getItem(int position) {
			if (position < arrayList.size()) {
				return arrayList.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}

		class ViewHolder {
			TextView tv_title;
			TextView tv_content;
			ImageView imgview_headPhoto;
			TextView tv_nickname;
			TextView tv_time;
			TextView tv_cmtNum;
		}
	}

	/***/
	public String getFileType(String type) {
		String aimTypeString = "";
		if (type.equals("html") || type.equals("htm")) { // 打开HTML文件
			aimTypeString = "text/html";
		} else if (type.equals("png") || type.equals("jpg")) {// 打开图片文件
			aimTypeString = "image/*";
		} else if (type.equals("pdf") || type.equals("pdfx")) {// 打开PDF文件的intent
			aimTypeString = "application/pdf";
		} else if (type.equals("txt")) {// 打开文本文件的intent
			aimTypeString = "text/plain";
		} else if (type.equals("doc") || type.equals("docx")) {// 打开文打开Word文件
			aimTypeString = "application/msword";
		} else if (type.equals("xls") || type.equals("xlsx")) {// 打开Excel文件
			aimTypeString = "application/vnd.ms-excel";
		} else if (type.equals("ppt")) {// 打开PPT文件
			aimTypeString = "application/vnd.ms-powerpoint";
		} else {
		}
		return aimTypeString;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_text_download:
				if (mDownload) {
					//查看
					File file = new File(
							CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
							+File.separator + app_id+ File.separator + mDocAttId + ".doc");
					Intent intent = new Intent();
					intent.setAction(android.content.Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), getFileType("doc"));
					try {
						startActivity(intent);
					} catch (Exception e) {
						ToastInfo toastInfo = ToastInfo
								.getInstance(HtmitechApplication.instance());
						toastInfo.setText("系统无法打开文件！请下载相关辅助软件！");
						toastInfo.show(Toast.LENGTH_SHORT);
					}
				} else {

					if (downFileResultInfo == null) {

						netWorkManager.logFunactionStart(getActivity(), this, "functionTextDoc", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode);

					} else {
						startDownload(downFileResultInfo);
					}
				}
				break;

			case R.id.btn_text_share:
				ShareListener();
				break;
			default:
				break;
		}
	}




	private void ShareListener() {
		if (downFileResultInfo == null || "".equals(downFileResultInfo)) {
			Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_SHORT).show();
			return ;
		} else {
			DocFileInfo docfileinfo = downFileResultInfo.getResult().getDocFileInfoResult();
			apiUrlTemp = docfileinfo.getDownloadURL() + "|" + docfileinfo.getByteLength() + "|" + docfileinfo.getFielName() + "|";
		}
		shareLink = new ShareLink();
		shareLink.setTitle("分享正文");
		shareLink.setDesc(((DetailActivity)getActivity()).docTitle);
		shareLink.setThumbnail(((DetailActivity)getActivity()).iconId);
		shareLink.setUrl(ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.OA_GETDOCINFO_METHOD);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("请选择分享位置");
		final String[] pos = { "联系人", "工作组" };
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (curItem == 0) {
					apiUrl = "cc" + apiUrlTemp + ((DetailActivity)getActivity()).apiUrlTmp;
					shareLink.setAppUrl(apiUrl);
					MXAPI.getInstance(getActivity()).shareToChat(getActivity(), shareLink);
				} else {
					apiUrl = "dd" + apiUrlTemp + ((DetailActivity)getActivity()).apiUrlTmp;
					shareLink.setAppUrl(apiUrl);
					shareLink.setTitle("分享正文:" + ((DetailActivity)getActivity()).docTitle);
					MXAPI.getInstance(getActivity()).shareToCircle(getActivity(), shareLink);
				}
				curItem = 0;
			}
		});
		builder.setSingleChoiceItems(pos, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						curItem = which;
					}
				});
		builder.show();
	}



	private boolean isFileExists(String docAttId) {
		File file = new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
				+File.separator + app_id+ File.separator + docAttId + ".doc");
		return file.exists();
	}

	private void startDownload(DownFilesIsFinishResultInfo entity) {
		if (mDownloadManager == null) {
			mDownloadManager = new DownloadManager(
					HtmitechApplication.instance());
		}
		DocFileInfo fileInfo = entity.getResult().getDocFileInfoResult();
		/*String docId = ((DetailActivity) getActivity()).getDocId();
		mDocAttId = */
		try {
			mDownloadManager.addNewDownload(fileInfo.getDownloadURL(),
					fileInfo.getFielName(),
					CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
							+File.separator + app_id+ File.separator + mDocAttId + ".zip", false, false,
					mRequestCallBack);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void unZipFile() {
		new Thread(new Runnable() {
			public void run() {
				try {
					/** 解压 **/
					File srcFile = new File(
							CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
									+File.separator + app_id + File.separator + mDocAttId + ".zip");
					String destination = CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
							+File.separator + app_id + File.separator;
					try {
						CompressUtil.unzip(srcFile, destination, "password");
						System.out.println("文件解压成功！！");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("文件解压失败！！");
					}

				} catch (Exception e) {
					System.out.println("文件解压失败！！");
				}
			}
		}).start();
	}

	private RequestCallBack<File> mRequestCallBack = new RequestCallBack<File>() {

		public void onStart() {
			mLinearlayout_text_download.setVisibility(View.VISIBLE);
			mLinearLayoutOperate.setVisibility(View.GONE);
		}

		public void onCancelled() {
			mLinearlayout_text_download.setVisibility(View.GONE);
			mLinearLayoutOperate.setVisibility(View.VISIBLE);
		}

		public void onLoading(long total, long current, boolean isUploading) {
			mProgressBar.setMax((int) total);
			mProgressBar.setProgress((int) current);
			mTextView_progress
					.setText("正文下载中:"
							+ String.format("%.2f", current * 1.0f / total
							* 100) + "%");
		}

		@Override
		public void onSuccess(ResponseInfo<File> responseInfo) {
			mLinearlayout_text_download.setVisibility(View.GONE);
			mLinearLayoutOperate.setVisibility(View.VISIBLE);
			mDownload = true;
			((Button) findViewById(R.id.btn_text_download)).setText("查看");
			unZipFile();
			netWorkManager.logFunactionFinsh(getActivity(), TextFragment.this, "functionTextDocFail", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode,"成功", INetWorkManager.State.SUCCESS);
		}

		@Override
		public void onFailure(HttpException error, String msg) {
			mLinearlayout_text_download.setVisibility(View.GONE);
			mLinearLayoutOperate.setVisibility(View.VISIBLE);

			netWorkManager.logFunactionFinsh(getActivity(), TextFragment.this, "functionTextDocFail", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode,"失败", INetWorkManager.State.FAIL);

		}
	};



	@Override
	public void onSuccess(int requestTypeId, Object result) {
		// TODO Auto-generated method stub
		if (result != null && result instanceof DownFilesIsFinishResultInfo) {
			if (downFileForShare = false) {
				startDownload((DownFilesIsFinishResultInfo) result);
			} else {
				downFileResultInfo = (DownFilesIsFinishResultInfo) result;
				downFileForShare = false;
			}

		} else if (result != null && result instanceof WordTextInfo) {
			if (((WordTextInfo) result).getResult() == null || ((WordTextInfo) result).getResult().length() == 0 ){
				mTextView_content.setText("正文无内容");
				findViewById(R.id.btn_text_download).setVisibility(View.INVISIBLE);
//取消正文分享内容
				findViewById(R.id.btn_text_share).setVisibility(View.INVISIBLE);
			}
			else {
				//String temp =  (((WordTextInfo) result).getResult()).replaceAll("\\", "");
				String temp = StringEscapeUtils.unescapeJava(((WordTextInfo) result).getResult());
				mTextView_content.setText(temp);
				if (DetailActivity2.currentActivity == true) {
				} else {
					mDocInfoParameters = new DocInfoParameters();
					mDocInfoParameters.context = OAConText
							.getInstance(HtmitechApplication.instance());

					if (getActivity() != null){

						mDocInfoParameters.DocId = ((DetailActivity) getActivity())
								.getMDocAttachmentID();
						mDocInfoParameters.DocType = ((DetailActivity) getActivity())
								.getDocType();
						mDocInfoParameters.Kind = ((DetailActivity) getActivity())
								.getDocKind(); // 2015-08-11
						mDocInfoParameters.app_id=((DetailActivity) getActivity()).app_id;
						netWorkManager.logFunactionStart(getActivity(), this, "functionTextDoc", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode);

					}

					downFileForShare = true;
				}
			}

			netWorkManager.logFunactionFinsh(getActivity(), this, "functionTextStartFail", LogManagerEnum.APP_DOC_TEXT.functionCode,((WordTextInfo) result).getMessage().getStatusMessage(), INetWorkManager.State.SUCCESS);
		}else{
			netWorkManager.logFunactionFinsh(getActivity(), this, "functionTextStartFail", LogManagerEnum.APP_DOC_TEXT.functionCode,result.toString(), INetWorkManager.State.FAIL);
		}
	}

	@Override
	public void onFail(int requestTypeId, int statusCode, String errorMsg,
					   Object result) {
		try {
			netWorkManager.logFunactionFinsh(getActivity(), this, "functionTextStartFail", LogManagerEnum.APP_DOC_TEXT.functionCode,errorMsg, INetWorkManager.State.FAIL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onScrollChanged() {

	}

	@Override
	public void onZoomText(float zoom) {
		mTextView_content.setTextSize(zoom);
	}

	@Override
	public void onRequfouch() {

	}

}
