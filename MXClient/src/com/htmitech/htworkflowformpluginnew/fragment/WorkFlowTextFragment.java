package com.htmitech.htworkflowformpluginnew.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
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

import com.htmitech.commonx.base.cache.FileNameGenerator;
import com.htmitech.commonx.base.cache.MD5FileNameGenerator;
import com.htmitech.commonx.base.exception.HttpException;
import com.htmitech.commonx.base.http.ResponseInfo;
import com.htmitech.commonx.base.http.callback.RequestCallBack;
import com.htmitech.commonx.base.net.DownloadManager;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.detail.DetailActivity2;
import com.htmitech.emportal.ui.detail.ObservableScrollView;
import com.htmitech.emportal.ui.detail.ScrollViewListener;
import com.htmitech.emportal.ui.widget.BaseDialog;
import com.htmitech.emportal.ui.widget.DialogCancelListener;
import com.htmitech.emportal.ui.widget.DialogConfirmListener;
import com.htmitech.emportal.ui.widget.MyAlertDialogFragment;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.emportal.utils.CompressUtil;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowFormDetalActivity;
import com.htmitech.htworkflowformpluginnew.entity.DocFileInfo;
import com.htmitech.htworkflowformpluginnew.entity.DocInfoParameters;
import com.htmitech.htworkflowformpluginnew.entity.DownFilesIsFinishResultInfo;
import com.htmitech.htworkflowformpluginnew.entity.WordTextInfo;

import htmitech.com.componentlibrary.base.MyBaseFragment;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.activity.GeneralFileViewActivity;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.HTActivityUnit;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.minxing.client.util.FastJsonUtils;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ShareLink;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 工作流 正文详情
 *
 * @author joe
 * @date 2017-06-30 12:03:41
 */
public class WorkFlowTextFragment extends MyBaseFragment implements
        View.OnClickListener, IBaseCallback, ObserverCallBackType, ScrollViewListener {
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

    private String mDocAttId;

    String apiUrl = "";
    String apiUrlTemp = "";
    public int curItem = 0;
    public ShareLink shareLink = null;
    private int isTextUrl;
    private String downloadType = "1";
    public boolean downFileForShare = false;

    public DownFilesIsFinishResultInfo downFileResultInfo = null;
    private INetWorkManager netWorkManager;
    private DocInfoParameters mDocInfoParameters;
    private String app_id;
    private ObservableScrollView zoom;//手势放大
    private BaseModel baseModel;
    private DocInfoParameters mDocInfoParametersTemp;
    private boolean isAutoDownload = false;
    private Drawable drawable;
    private String GET_DOWNLOAD_DOCISFINISH_PATH = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_DOWNFILE_ISFinish_DOCFile_METHOD_JAVA;
    private String GET_DOC_TEXT_PATH = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.OA_GETDOC_TEXT_METHOD;//?????? 不确定还要不要
    public static final String DOWNLOAD_DOCISFINISH = "downloadFinish";
    public static final String GET_DOC_TEXT = "getDocText";

    /**
     * 获取布局id，用于setContentView。
     *
     * @return id
     */
    protected int getLayoutId() {
        return R.layout.fragment_text;
    }

    @SuppressLint("ValidFragment")
    public WorkFlowTextFragment(String app_id) {
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
            mEmptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        }
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
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        //mDocId = ((DetailActivity) getActivity()).getDocId();
        if (DetailActivity2.currentActivity) {
            mDocAttId = ((DetailActivity2) getActivity()).mDocAttachmentID;
        } else {
            mDocAttId = ((WorkFlowFormDetalActivity) getActivity()).getMDocAttachmentID();
            if(TextUtils.isEmpty(mDocAttId)){
                mDocAttId =  ((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo.getResult().getDocID();
            }
        }
        if (getActivity() instanceof WorkFlowFormDetalActivity) {
            isTextUrl = ((WorkFlowFormDetalActivity) getActivity()).isTextUrl;
            downloadType = ((WorkFlowFormDetalActivity) getActivity()).com_workflow_mobileconfig_downloadType;
        }
        if (mDocAttId != null && mDocAttId.length() > 0) {
            mDownload = isFileExists(mDocAttId);
        } else {
            return;
        }

        mDocInfoParameters = new DocInfoParameters();
        if (DetailActivity2.currentActivity) {
            String item[] = ((DetailActivity2) getActivity()).apiUrl.split("[|]");
            mDocInfoParameters.userId = item[0 + 3];
            mDocInfoParameters.docId = item[3 + 3];
            mDocInfoParameters.systemCode = item[4 + 3];
        } else {
            mDocInfoParameters.userId = OAConText.getInstance(getActivity()).UserID;
            mDocInfoParameters.docId = ((WorkFlowFormDetalActivity) getActivity()).getDocId();
            mDocInfoParameters.systemCode = ((WorkFlowFormDetalActivity) getActivity()).getDocKind(); //2015-08-11
//            mDocInfoParameters.docAttachmentID = ((WorkFlowFormDetalActivity) getActivity()).getMDocAttachmentID();
//            mDocInfoParameters.docAttachmentType = ((WorkFlowFormDetalActivity) getActivity()).getDocAttachmentType();
        }
        mDocInfoParameters.appId = ((WorkFlowFormDetalActivity) getActivity()).app_id;
        mDocInfoParameters.downloadType = downloadType;//暂时传递一个2
//        netWorkManager.logFunactionStart(getActivity(), this, "functionTextStart", LogManagerEnum.APP_DOC_TEXT.functionCode);

        //页面加载 请求文件下载结构

    }

    @Override
    public void onMyResume() {
        super.onMyResume();
        if (mDownload) {
            Button button = (Button) findViewById(R.id.btn_text_download);
            button.setText("打开正文");
            Drawable two = getResources().getDrawable(R.drawable.icon_text_details);
            two.setBounds(0, 0, two.getMinimumWidth(), two.getMinimumHeight());
            button.setCompoundDrawables(two, null, null, null);
        } else {
            drawable = ((Button) findViewById(R.id.btn_text_download)).getCompoundDrawables()[0];
            try {
                if (!((Animatable) drawable).isRunning()) {
                    ((Animatable) drawable).start();
                }
            } catch (Exception e) {

            }

            downText();
        }
    }

    private DocInfoParameters getdocInfoParameters(DocInfoParameters mDocInfoParameters) {
        String item[] = ((DetailActivity2) getActivity()).apiUrl.split("[|]");
        mDocInfoParameters.userId = item[0 + 3].substring(2);
        mDocInfoParameters.docId = item[3 + 3];
        mDocInfoParameters.systemCode = item[4 + 3];
        mDocInfoParameters.downloadType = downloadType;
        return mDocInfoParameters;
    }


    public void toDeleteMsg(final int entityIndex) {
        mAdapter.setDeleteIndex(entityIndex);
        mNewFragment = MyAlertDialogFragment.newInstance("确实要删除此帖子吗?",
                R.drawable.prompt_warn, cancelListener, confirmListener, true);
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

        if (requestName.equals("functionTextDoc")) {
            DocInfoParameters mDocInfoParameters = new DocInfoParameters();
            if (DetailActivity2.currentActivity) {
                getdocInfoParameters(mDocInfoParameters);
            } else {
                mDocInfoParameters.userId = OAConText.getInstance(getActivity()).UserID;
                mDocInfoParameters.docId = ((WorkFlowFormDetalActivity) getActivity()).getDocId();
                mDocInfoParameters.systemCode = ((WorkFlowFormDetalActivity) getActivity()).getDocKind(); //2015-08-11
//                mDocInfoParameters.docAttachmentID = ((WorkFlowFormDetalActivity) getActivity()).getMDocAttachmentID();
//                mDocInfoParameters.docAttachmentType = ((WorkFlowFormDetalActivity) getActivity()).getDocAttachmentType();
            }
            mDocInfoParameters.downloadType = downloadType;
            mDocInfoParameters.appId = ((WorkFlowFormDetalActivity) getActivity()).app_id;
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocInfoParameters, GET_DOWNLOAD_DOCISFINISH_PATH, CHTTP.POSTWITHTOKEN, this, DOWNLOAD_DOCISFINISH, LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode);
            mDocInfoParametersTemp = mDocInfoParameters;
        } else if (requestName.equals("functionTextStart")) {
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocInfoParameters, GET_DOC_TEXT_PATH, CHTTP.POSTWITHTOKEN, this, GET_DOC_TEXT, LogManagerEnum.APP_DOC_TEXT.functionCode);
            mDocInfoParametersTemp = mDocInfoParameters;
        } else if (DOWNLOAD_DOCISFINISH.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(getActivity(),requestValue, type, GET_DOWNLOAD_DOCISFINISH_PATH, mDocInfoParametersTemp, this, requestName, LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode);
            if (!TextUtils.isEmpty(requestValue)) {

                DownFilesIsFinishResultInfo result = FastJsonUtils.getPerson(requestValue, DownFilesIsFinishResultInfo.class);
                if (null != result) {
                    if (result.getResult() != null) {
                        startDownload(result);
                        if (downFileForShare = false) {

                        } else {
                            downFileResultInfo = result;//目测是用来分享用的 改造时候在森工项目 没有emi 所以这个暂时没用到 只是猜测
                            downFileForShare = false;
                        }
                    } else {
                        Toast.makeText(getActivity(), "服务器文件还未下载完成，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    netWorkManager.logFunactionFinsh(getActivity(), WorkFlowTextFragment.this, "functionTextDocFail", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode, "失败", INetWorkManager.State.FAIL);
                }
            } else {
                netWorkManager.logFunactionFinsh(getActivity(), WorkFlowTextFragment.this, "functionTextDocFail", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode, "失败", INetWorkManager.State.FAIL);
            }
        } else if (GET_DOC_TEXT.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(getActivity(),requestValue, type, GET_DOC_TEXT_PATH, mDocInfoParametersTemp, this, requestName, LogManagerEnum.APP_DOC_TEXT.functionCode);
            if (!TextUtils.isEmpty(requestValue)) {
                WordTextInfo result = FastJsonUtils.getPerson(requestValue, WordTextInfo.class);
                if (result.getResult() == null || result.getResult().length() == 0) {
                    mTextView_content.setText("正文无内容");
                    findViewById(R.id.btn_text_download).setVisibility(View.INVISIBLE);
                    findViewById(R.id.btn_text_share).setVisibility(View.INVISIBLE);
                } else {
                    String temp = StringEscapeUtils.unescapeJava(result.getResult());
                    mTextView_content.setText(temp);
//                    if (DetailActivity2.currentActivity == true) {
//
//                    } else {
//                        mDocInfoParameters = new DocInfoParameters();
//                        if (getActivity() != null) {
//                            mDocInfoParameters.userId = OAConText.getInstance(getActivity()).UserID;
//                            mDocInfoParameters.docId = ((WorkFlowFormDetalActivity) getActivity()).getMDocAttachmentID();
//                            mDocInfoParameters.systemCode = ((WorkFlowFormDetalActivity) getActivity()).getDocKind(); // 2015-08-11
//                            mDocInfoParameters.appId = ((WorkFlowFormDetalActivity) getActivity()).app_id;
//                            netWorkManager.logFunactionStart(getActivity(), this, "functionTextDoc", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode);
//                        }
//                        downFileForShare = true;
//                    }
                }
                netWorkManager.logFunactionFinsh(getActivity(), this, "functionTextStartFail", LogManagerEnum.APP_DOC_TEXT.functionCode, result.getMessage().getStatusMessage(), INetWorkManager.State.SUCCESS);
            } else {
                netWorkManager.logFunactionFinsh(getActivity(), this, "functionTextStartFail", LogManagerEnum.APP_DOC_TEXT.functionCode, "获取正文概要信息失败", INetWorkManager.State.FAIL);
            }
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("functionTextDoc")) {
            DocInfoParameters mDocInfoParameters = new DocInfoParameters();
            if (DetailActivity2.currentActivity) {
                getdocInfoParameters(mDocInfoParameters);
            } else {
                mDocInfoParameters.userId = OAConText.getInstance(getActivity()).UserID;
                mDocInfoParameters.docId = ((WorkFlowFormDetalActivity) getActivity()).getMDocAttachmentID();
                mDocInfoParameters.systemCode = ((WorkFlowFormDetalActivity) getActivity()).getDocKind(); //2015-08-11
            }
            mDocInfoParameters.appId = ((WorkFlowFormDetalActivity) getActivity()).app_id;
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocInfoParameters, GET_DOWNLOAD_DOCISFINISH_PATH, CHTTP.POSTWITHTOKEN, this, DOWNLOAD_DOCISFINISH, LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode);
            mDocInfoParametersTemp = mDocInfoParameters;
        } else if (requestName.equals("functionTextStart")) {
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocInfoParameters, GET_DOC_TEXT_PATH, CHTTP.POSTWITHTOKEN, this, GET_DOC_TEXT, LogManagerEnum.APP_DOC_TEXT.functionCode);
            mDocInfoParametersTemp = mDocInfoParameters;
        } else if (DOWNLOAD_DOCISFINISH.equals(requestName)) {
            netWorkManager.logFunactionFinsh(getActivity(), WorkFlowTextFragment.this, "functionTextDocFail", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
        } else if (GET_DOC_TEXT.equals(requestName)) {
            netWorkManager.logFunactionFinsh(getActivity(), this, "functionTextStartFail", LogManagerEnum.APP_DOC_TEXT.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
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

        public void setData(List<List> temp, int pullStyle) {
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
                isAutoDownload = false;
                downText();
                break;

            case R.id.btn_text_share:
                ShareListener();
                break;
            default:
                break;
        }
    }

    private String getFileName(String fileName){
        int i = fileName.lastIndexOf('.');
        return fileName.substring(0,i);
    }
    public void downText() {
        if (mDownload && !isAutoDownload) {
            DocFileInfo docfileinfo = downFileResultInfo.getResult().getDocFileInfoResult();
            //查看
            File file = new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator + app_id + File.separator + mDocAttId +"_"+docfileinfo.fileCrc32+ "."+docfileinfo.getFileType());
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), getFileType("doc"));
            try {
//                startActivity(intent);
                HashMap map = new HashMap<String, String>();
                map.put("path", file.getAbsolutePath());
                HTActivityUnit.switchTo(getActivity(), GeneralFileViewActivity.class, map);
            } catch (Exception e) {
                ToastInfo toastInfo = ToastInfo.getInstance(HtmitechApplication.instance());
                toastInfo.setText("系统无法打开文件！请下载相关辅助软件！");
                toastInfo.show(Toast.LENGTH_SHORT);
            }
        } else if (!isAutoDownload) {
            isAutoDownload = true;
            if (downFileResultInfo == null) {
                netWorkManager.logFunactionStart(getActivity(), this, "functionTextDoc", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode);
            } else {
                startDownload(downFileResultInfo);
            }
        }
    }


    private void ShareListener() {
        if (downFileResultInfo == null || "".equals(downFileResultInfo)) {
            Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_SHORT).show();
            return;
        } else {
            DocFileInfo docfileinfo = downFileResultInfo.getResult().getDocFileInfoResult();
            apiUrlTemp = docfileinfo.getUrl(isTextUrl) + "|" + docfileinfo.getByteLength() + "|" + docfileinfo.getFielName() + "|";
        }
        shareLink = new ShareLink();
        shareLink.setTitle("分享正文");
        shareLink.setDesc(((WorkFlowFormDetalActivity) getActivity()).docTitle);
        shareLink.setThumbnail(((WorkFlowFormDetalActivity) getActivity()).iconId);
        shareLink.setUrl(ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.OA_GETDOCINFO_METHOD);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请选择分享位置");
        final String[] pos = {"联系人", "工作组"};
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (curItem == 0) {
                    apiUrl = "cc" + apiUrlTemp + ((WorkFlowFormDetalActivity) getActivity()).apiUrlTmp;
                    shareLink.setAppUrl(apiUrl);
                    MXAPI.getInstance(getActivity()).shareToChat(getActivity(), shareLink);
                } else {
                    apiUrl = "dd" + apiUrlTemp + ((WorkFlowFormDetalActivity) getActivity()).apiUrlTmp;
                    shareLink.setAppUrl(apiUrl);
                    shareLink.setTitle("分享正文:" + ((WorkFlowFormDetalActivity) getActivity()).docTitle);
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
                + File.separator + app_id + File.separator + docAttId + ".doc");
        return file.exists();
    }

    private void startDownload(DownFilesIsFinishResultInfo entity) {
        if (mDownloadManager == null) {
            mDownloadManager = new DownloadManager(HtmitechApplication.instance());
        }

        DocFileInfo fileInfo = entity.getResult().getDocFileInfoResult();
        /*String docId = ((DetailActivity) getActivity()).getDocId();
        mDocAttId = */
        try {
            mDownloadManager.addNewDownload(fileInfo.getUrl(isTextUrl), fileInfo.getFielName(),
                    CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator + app_id + File.separator + mDocAttId + ".zip", false, false,
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
            mLinearlayout_text_download.setVisibility(View.GONE);
            mLinearLayoutOperate.setVisibility(View.VISIBLE);
        }

        public void onCancelled() {
            mLinearlayout_text_download.setVisibility(View.GONE);
            mLinearLayoutOperate.setVisibility(View.VISIBLE);
        }

        public void onLoading(long total, long current, boolean isUploading) {
            mProgressBar.setMax((int) total);
            mProgressBar.setProgress((int) current);
            mTextView_progress.setText("正文下载中:" + String.format("%.2f", current * 1.0f / total * 100) + "%");
        }

        @Override
        public void onSuccess(ResponseInfo<File> responseInfo) {
            mLinearlayout_text_download.setVisibility(View.GONE);
            mLinearLayoutOperate.setVisibility(View.VISIBLE);
            mDownload = true;
            Button button = (Button) findViewById(R.id.btn_text_download);
            button.setText("打开正文");
            if (drawable instanceof Animatable)
                ((Animatable) drawable).stop();
            Drawable two = getResources().getDrawable(R.drawable.icon_text_details);
            two.setBounds(0, 0, two.getMinimumWidth(), two.getMinimumHeight());
            button.setCompoundDrawables(two, null, null, null);
            unZipFile();
            downText();
            netWorkManager.logFunactionFinsh(getActivity(), WorkFlowTextFragment.this, "functionTextDocFail", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode, "成功", INetWorkManager.State.SUCCESS);
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            mLinearlayout_text_download.setVisibility(View.GONE);
            mLinearLayoutOperate.setVisibility(View.VISIBLE);

            netWorkManager.logFunactionFinsh(getActivity(), WorkFlowTextFragment.this, "functionTextDocFail", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode, "失败", INetWorkManager.State.FAIL);

        }
    };


    @Override
    public void onSuccess(int requestTypeId, Object result) {
        // TODO Auto-generated method stub
//        if (result != null && result instanceof DownFilesIsFinishResultInfo) {
//            startDownload((DownFilesIsFinishResultInfo) result);
//            if (downFileForShare = false) {
//
//            } else {
//                downFileResultInfo = (DownFilesIsFinishResultInfo) result;
//                downFileForShare = false;
//            }
//
//        } else if (result != null && result instanceof WordTextInfo) {
//            if (((WordTextInfo) result).getResult() == null || ((WordTextInfo) result).getResult().length() == 0) {
//                mTextView_content.setText("正文无内容");
//                findViewById(R.id.btn_text_download).setVisibility(View.INVISIBLE);
////取消正文分享内容
//                findViewById(R.id.btn_text_share).setVisibility(View.INVISIBLE);
//            } else {
//                //String temp =  (((WordTextInfo) result).getResult()).replaceAll("\\", "");
//                String temp = StringEscapeUtils.unescapeJava(((WordTextInfo) result).getResult());
//                mTextView_content.setText(temp);
//                if (DetailActivity2.currentActivity == true) {
//                } else {
//                    mDocInfoParameters = new DocInfoParameters();
//                    mDocInfoParameters.context = OAConText
//                            .getInstance(HtmitechApplication.instance());
//
//                    if (getActivity() != null) {
//
//                        mDocInfoParameters.DocId = ((WorkFlowFormDetalActivity) getActivity())
//                                .getMDocAttachmentID();
//                        mDocInfoParameters.DocType = ((WorkFlowFormDetalActivity) getActivity())
//                                .getDocType();
//                        mDocInfoParameters.Kind = ((WorkFlowFormDetalActivity) getActivity())
//                                .getDocKind(); // 2015-08-11
//                        mDocInfoParameters.app_id = ((WorkFlowFormDetalActivity) getActivity()).app_id;
//                        netWorkManager.logFunactionStart(getActivity(), this, "functionTextDoc", LogManagerEnum.APP_DOWN_LOAD_DOC.functionCode);
//
//                    }
//
//                    downFileForShare = true;
//                }
//            }
//
//            netWorkManager.logFunactionFinsh(getActivity(), this, "functionTextStartFail", LogManagerEnum.APP_DOC_TEXT.functionCode, ((WordTextInfo) result).getMessage().getStatusMessage(), INetWorkManager.State.SUCCESS);
//        } else {
//            netWorkManager.logFunactionFinsh(getActivity(), this, "functionTextStartFail", LogManagerEnum.APP_DOC_TEXT.functionCode, result.toString(), INetWorkManager.State.FAIL);
//        }
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result) {
        if (result != null) {
//            String finalString = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValueCloudApi(result, baseModel, requestTypeId, mDocInfoParametersTemp);
//            if (finalString.equals("timeout")) {
//                return;
//            }
        }
        try {
            netWorkManager.logFunactionFinsh(getActivity(), this, "functionTextStartFail", LogManagerEnum.APP_DOC_TEXT.functionCode, errorMsg, INetWorkManager.State.FAIL);
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
