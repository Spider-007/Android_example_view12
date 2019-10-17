package com.htmitech.htworkflowformpluginnew.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.commonx.base.cache.FileNameGenerator;
import com.htmitech.commonx.base.cache.MD5FileNameGenerator;
import com.htmitech.commonx.base.exception.HttpException;
import com.htmitech.commonx.base.http.ResponseInfo;
import com.htmitech.commonx.base.http.callback.RequestCallBack;
import com.htmitech.commonx.base.net.DownloadManager;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.entity.DocFileInfo;
import com.htmitech.emportal.entity.DocInfoParameters;
import com.htmitech.emportal.entity.DownFilesIsFinishResultInfo;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.detail.model.DocInfoModel;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.emportal.utils.CompressUtil;

import com.htmitech.htworkflowformpluginnew.activity.WorkFlowFormDetalActivity;

import htmitech.com.componentlibrary.base.MyBaseFragment;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

import com.htmitech.htworkflowformpluginnew.listener.JavaScriptObject;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import java.io.File;


/**
 * 网页所有的入口
 * <p/>
 * Created by tony on
 */
public class WorkFlowInitUrlFragment extends MyBaseFragment implements View.OnClickListener, ObserverCallBackType, IBaseCallback {
    private ImageView btn_daiban_person;
    private TextView daibantopTabIndicator_bbslist;
    public WebView webView;
    private String url;
    private ProgressBar bar;

    private Button btn_text_download;
    private Button btn_text_share;

    private boolean mDownload = false;
    private INetWorkManager netWorkManager;
    private DocInfoParameters mDocInfoParameters;
    private DocInfoModel mDocInfoModel;
    private String mDocAttId;
    public String app_id = "";
    private DownloadManager mDownloadManager;

    private ProgressBar mProgressBar;
    private TextView mTextView_progress;
    private LinearLayout mLinearLayoutOperate;
    private LinearLayout mLinearlayout_text_download;

    public DownFilesIsFinishResultInfo downFileResultInfo = null;
    public ImageView iv_down;
    private BaseModel baseModel;
    private DocInfoParameters docInfoParametersTemp;
    private JavaScriptObject javaScriptObject;
    public String getDocInfoPath = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_GETDOCINFO_NODEFINE_METHOD_JAVA;
    private com.htmitech.htworkflowformpluginnew.entity.DocInfoParameters mDocInfoRequestParameters;
    public static final String GET_DOCINFO_NODEFINE = "getDocInfoNoDefine";//不包含表单样式定义的接口
    private String srcJson;

    @Override
    protected int getLayoutId() {
        return com.htmitech.addressbook.R.layout.activity_webview;
    }

    @Override
    protected void initViews() {
        bar = (ProgressBar) findViewById(com.htmitech.addressbook.R.id.myProgressBar);
        webView = (WebView) findViewById(com.htmitech.addressbook.R.id.myWebView);
        javaScriptObject = new JavaScriptObject(getActivity(), this);//创建JS交互引用
        webView.addJavascriptInterface(javaScriptObject, "HtMobileJsInterface");
        daibantopTabIndicator_bbslist = (TextView) findViewById(com.htmitech.addressbook.R.id.daibantopTabIndicator_bbslist);
        btn_daiban_person = (ImageView) findViewById(com.htmitech.addressbook.R.id.btn_daiban_person);
        iv_down = (ImageView) findViewById(com.htmitech.addressbook.R.id.iv_down);
        btn_text_download = (Button) findViewById(com.htmitech.addressbook.R.id.btn_text_download);
        btn_text_share = (Button) findViewById(com.htmitech.addressbook.R.id.btn_text_share);
        btn_text_download.setOnClickListener(this);
        btn_text_share.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(com.htmitech.emportal.R.id.progressbar_text_download);
        mLinearLayoutOperate = (LinearLayout) findViewById(com.htmitech.emportal.R.id.linearlayout_text_operate);
        mLinearlayout_text_download = (LinearLayout) findViewById(com.htmitech.emportal.R.id.linearlayout_text_download);
        mTextView_progress = (TextView) findViewById(com.htmitech.emportal.R.id.textview_text_tip);
        getFunctionLogIdFromServer();
    }

    public void initData() {

        if (getActivity() instanceof WorkFlowFormDetalActivity) {
            url = ((WorkFlowFormDetalActivity) getActivity()).DocviewUrl;
//            url = "file:///android_asset/vueTemplate/templates/template1/index.html";
            app_id = ((WorkFlowFormDetalActivity) getActivity()).app_id;
        }
        mDocInfoModel = new DocInfoModel(this);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                daibantopTabIndicator_bbslist.setText(title);
            }


        });
        btn_daiban_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        // 创建WebViewClient对象
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                webView.loadUrl(url);
                // 消耗掉这个事件。Android中返回True的即到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl("javascript:initFormData(" + srcJson + ")");   //初始化数据
            }
        };
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        //设置 缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(wvc);
        webView.loadUrl(url);
        mLinearlayout_text_download.setVisibility(View.GONE);
        mLinearLayoutOperate.setVisibility(View.GONE);
        if (((WorkFlowFormDetalActivity) getActivity()).isTextUrl == 2) {
//            mLinearlayout_text_download.setVisibility(View.VISIBLE);
            mLinearLayoutOperate.setVisibility(View.VISIBLE);
            initDowm();
        }
        //保利集团暂时这么处理 等于1的时候都有下载按钮
//        mLinearLayoutOperate.setVisibility(View.VISIBLE);
        initDowm();

    }
    public void getFunctionLogIdFromServer(){
        try {
            netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
            netWorkManager.logFunactionStart(getActivity(), this, "functionStart", LogManagerEnum.APP_DOC_INFO.functionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initDowm() {

        mDocAttId = ((WorkFlowFormDetalActivity) getActivity()).getMDocAttachmentID();

        if (mDocAttId != null && mDocAttId.length() > 0) {
            mDownload = isFileExists(mDocAttId);
        } else {
            return;
        }
        if (mDownload) {
            btn_text_download.setText("查看");
            iv_down.setVisibility(View.INVISIBLE);
        }
        mDocInfoParameters = new DocInfoParameters();
        mDocInfoParameters.context = OAConText
                .getInstance(HtmitechApplication.instance());
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        if (getActivity() != null && getActivity() instanceof WorkFlowFormDetalActivity) {
            mDocInfoParameters.DocId = ((WorkFlowFormDetalActivity) getActivity())
                    .getMDocAttachmentID();
            mDocInfoParameters.DocType = ((WorkFlowFormDetalActivity) getActivity())
                    .getDocType();
            mDocInfoParameters.Kind = ((WorkFlowFormDetalActivity) getActivity())
                    .getDocKind(); // 2015-08-11
            mDocInfoParameters.app_id = ((WorkFlowFormDetalActivity) getActivity()).app_id;
            netWorkManager.logFunactionStart(getActivity(), this, "functionTextDoc", LogManagerEnum.APP_DOWN_LOAD_DOC.getFunctionCode());
        }
    }

    private void getHtmlFormDetailData() {
        mDocInfoRequestParameters = new com.htmitech.htworkflowformpluginnew.entity.DocInfoParameters();
        if (((WorkFlowFormDetalActivity) getActivity()).comeShare.equals("1")) {
            mDocInfoRequestParameters.docId = ((WorkFlowFormDetalActivity) getActivity())
                    .docId;
            mDocInfoRequestParameters.appId = ((WorkFlowFormDetalActivity) getActivity()).app_id;
            mDocInfoRequestParameters.appVersionId = ((WorkFlowFormDetalActivity) getActivity())
                    .app_version_id;
            mDocInfoRequestParameters.userId = ((WorkFlowFormDetalActivity) getActivity()).UserID;
            if (!TextUtils.isEmpty(((WorkFlowFormDetalActivity) getActivity()).flowId)) {
                mDocInfoRequestParameters.flowId = ((WorkFlowFormDetalActivity) getActivity()).flowId;
            } else {
                mDocInfoRequestParameters.flowId = "";
            }
        } else {
            mDocInfoRequestParameters.docId = ((WorkFlowFormDetalActivity) getActivity())
                    .docId;
            mDocInfoRequestParameters.systemCode = ((WorkFlowFormDetalActivity) getActivity())
                    .getDocKind();
            mDocInfoRequestParameters.appId = ((WorkFlowFormDetalActivity) getActivity()).app_id;
            mDocInfoRequestParameters.appVersionId = ((WorkFlowFormDetalActivity) getActivity())
                    .app_version_id;
            mDocInfoRequestParameters.userId = ((WorkFlowFormDetalActivity) getActivity()).UserID;
            if (!TextUtils.isEmpty(((WorkFlowFormDetalActivity) getActivity()).flowId)) {
                mDocInfoRequestParameters.flowId = ((WorkFlowFormDetalActivity) getActivity()).flowId;
            } else {
                mDocInfoRequestParameters.flowId = "";
            }
        }


//        mDocInfoParameters = new DocInfoParameters();
//        mDocInfoParameters.context = OAConText
//                .getInstance(HtmitechApplication.instance());
//        if (getActivity() != null && getActivity() instanceof WorkFlowFormDetalActivity) {
//            mDocInfoParameters.DocId = ((WorkFlowFormDetalActivity) getActivity())
//                    .getMDocAttachmentID();
//            mDocInfoParameters.DocType = ((WorkFlowFormDetalActivity) getActivity())
//                    .getDocType();
//            mDocInfoParameters.Kind = ((WorkFlowFormDetalActivity) getActivity())
//                    .getDocKind(); // 2015-08-11
//            mDocInfoParameters.app_id = ((WorkFlowFormDetalActivity) getActivity()).app_id;
////            netWorkManager.logFunactionStart(getActivity(), this, "functionTextDoc", LogManagerEnum.APP_DOWN_LOAD_DOC.getFunctionCode());
//        }
        AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocInfoRequestParameters, getDocInfoPath, CHTTP.POSTWITHTOKEN, this, GET_DOCINFO_NODEFINE, LogManagerEnum.APP_DOC_INFO.functionCode);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
//        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case com.htmitech.addressbook.R.id.btn_text_download:
                if (mDownload) {
                    //查看
                    File file = new File(
                            CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
                                    + File.separator + app_id + File.separator + mDocAttId + ".doc");
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
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
                        netWorkManager.logFunactionStart(getActivity(), this, "functionTextDoc", LogManagerEnum.APP_DOWN_LOAD_DOC.getFunctionCode());
                    } else {
                        startDownload(downFileResultInfo);
                    }
                }
                break;

            case com.htmitech.addressbook.R.id.btn_text_share:
//                ShareListener();
                break;
            default:
                break;
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("functionTextDoc")) {
            DocInfoParameters mDocInfoParameters = new DocInfoParameters();
            mDocInfoParameters.context = OAConText
                    .getInstance(HtmitechApplication.instance());
            mDocInfoParameters.DocId = ((WorkFlowFormDetalActivity) getActivity()).getMDocAttachmentID();//
            mDocInfoParameters.DocType = ((WorkFlowFormDetalActivity) getActivity()).getDocType();//
            mDocInfoParameters.Kind = ((WorkFlowFormDetalActivity) getActivity()).getDocKind(); //2015-08-11
            mDocInfoParameters.app_id = ((WorkFlowFormDetalActivity) getActivity()).app_id;
            mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_GET_DOWNLOAD_DOCISFINISH, mDocInfoParameters);
            baseModel = mDocInfoModel;
            docInfoParametersTemp = mDocInfoParameters;
        } else if (requestName.equals("functionStart")) {
            getHtmlFormDetailData();
        }else if(requestName.equals(GET_DOCINFO_NODEFINE)){
            initData();
            srcJson = requestValue;
//            initData();
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("functionStart")) {
            getHtmlFormDetailData();
        }else if(requestName.equals(GET_DOCINFO_NODEFINE)){
            initData();
//            initData();
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    private boolean isFileExists(String docAttId) {
        File file = new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
                + File.separator + app_id + File.separator + docAttId + ".doc");
        return file.exists();
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

    private void startDownload(DownFilesIsFinishResultInfo entity) {
        if (mDownloadManager == null) {
            mDownloadManager = new DownloadManager(
                    HtmitechApplication.instance());
        }
        DocFileInfo fileInfo = entity.getResult().getDocFileInfoResult();
        /*String docId = ((WorkFlowFormDetalActivity) getActivity()).getDocId();
        mDocAttId = */
        try {
            mDownloadManager.addNewDownload(fileInfo.getDownloadURL(),
                    fileInfo.getFielName(),
                    CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
                            + File.separator + app_id + File.separator + mDocAttId + ".zip", false, false,
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
                                    + File.separator + app_id + File.separator + mDocAttId + ".zip");
                    String destination = CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER
                            + File.separator + app_id + File.separator;
                    try {
                        FileNameGenerator fileNameGenerator = new MD5FileNameGenerator();
                        String password = fileNameGenerator.generate(mDocAttId + fileNameGenerator.generate(mDocAttId));
                        CompressUtil.unzip(srcFile, destination, password);
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
            btn_text_download.setText("查看");
            iv_down.setVisibility(View.INVISIBLE);
            unZipFile();
            netWorkManager.logFunactionFinsh(getActivity(), WorkFlowInitUrlFragment.this, "functionTextDocFail", LogManagerEnum.APP_DOWN_LOAD_DOC.getFunctionCode(), "成功", INetWorkManager.State.SUCCESS);
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            mLinearlayout_text_download.setVisibility(View.GONE);
            mLinearLayoutOperate.setVisibility(View.VISIBLE);

            netWorkManager.logFunactionFinsh(getActivity(), WorkFlowInitUrlFragment.this, "functionTextDocFail", LogManagerEnum.APP_DOWN_LOAD_DOC.getFunctionCode(), "失败", INetWorkManager.State.FAIL);

        }
    };


    @Override
    public void onSuccess(int requestTypeId, Object result) {
        if (result != null && result instanceof DownFilesIsFinishResultInfo) {
//            startDownload((DownFilesIsFinishResultInfo) result);
            downFileResultInfo = (DownFilesIsFinishResultInfo) result;
        }
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result) {
        if (result != null) {
//            String finalString = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValueCloudApi(result, baseModel, requestTypeId, docInfoParametersTemp);
//            if (finalString.equals("timeout")) {
//                return;
//            }
        }
    }

    public String optionString = "";//意见内容
    public String key = "";//意见内容

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != data) {
            optionString = data.getStringExtra("option");
        }
        JSONObject root = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        if (!TextUtils.isEmpty(optionString)) {
            jsonObject.put("opinionText", optionString + "");
        } else {
            jsonObject.put("opinionText", "");
            root.put("dataList", jsonObject);
            root.put("key", key);
            return;
        }
        jsonObject.put("userName", OAConText.getInstance(getActivity()).OA_UserName);
        root.put("dataList", jsonObject);
        root.put("key", key);
        webView.loadUrl("javascript:getSignInfo(" + root.toJSONString() + ")");   //移动端调用js方法,获取签章图片数据
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
    * 数据提交
    * */
    public void submit() {
        if (null != webView) {
            webView.loadUrl("javascript:submitData()");
        }
    }
}
