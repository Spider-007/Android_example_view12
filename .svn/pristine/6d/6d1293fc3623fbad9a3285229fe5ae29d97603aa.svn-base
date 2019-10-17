package com.htmitech.htworkflowformpluginnew.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archivermodule.archiver.ArchiverManager;
import com.example.archivermodule.archiver.IArchiverListener;
import com.htmitech.MyView.RectProgressView;
import com.htmitech.commonx.base.cache.FileNameGenerator;
import com.htmitech.commonx.base.cache.MD5FileNameGenerator;
import com.htmitech.commonx.base.exception.DbException;
import com.htmitech.commonx.base.exception.HttpException;
import com.htmitech.commonx.base.http.ResponseInfo;
import com.htmitech.commonx.base.http.callback.RequestCallBack;
import com.htmitech.commonx.base.net.DownloadInfo;
import com.htmitech.commonx.base.net.DownloadManager;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.Mode;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.common.CommonFileType;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.widget.BaseDialog;
import com.htmitech.emportal.ui.widget.DialogCancelListener;
import com.htmitech.emportal.ui.widget.DialogConfirmListener;
import com.htmitech.emportal.ui.widget.MyAlertDialogFragment;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.emportal.utils.CompressUtil;
import com.htmitech.htworkflowformpluginnew.activity.UnrarListView;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowFormDetalActivity;
import com.htmitech.htworkflowformpluginnew.entity.AttachmentInfoParameters;
import com.htmitech.htworkflowformpluginnew.entity.DownFilesIsFinishResultInfo;

import htmitech.com.componentlibrary.base.MyBaseFragment;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.activity.GeneralFileViewActivity;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.HTActivityUnit;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;

import com.htmitech.unit.ActivityUnit;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.util.FastJsonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import htmitech.com.componentlibrary.entity.AttachmentInfo;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import net.lingala.zip4j.exception.ZipException;

/**
 * 工作流 附件详情
 *
 * @author joe
 * @date 2017-06-30 12:06:40
 */
public class WorkFlowAttachmentFragment extends MyBaseFragment {
    private PullToRefreshListView mPullToRefreshListView;
    /* 网络异常显示 */
    private AttachmentAdapter mAdapter;
    private DialogFragment mNewFragment;
    private static final int PULLDOWN_TOREFRESH = 0;
    private static final int PULLUP_TOLOADMORE = 1;

    private static final int DOWN_START = 0;
    private static final int DOWN_LOAD = 1;
    private static final int DOWN_STOP = 2;
    private static final int DOWN_FINISH = 3;


    private int mPageNum = 1;
    private boolean has_more = false;

    private LayoutInflater mInflater;
    private View mEmptyView;
    private DocResultInfo mDocResultInfo;

    private INetWorkManager netWorkManager;
    private String app_id;
    AttachmentInfoParameters mDocInfoParameters;
    public String DOWNLOAD_ATTISFINISH_PATH = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_DOWNFILE_ISFinish_ATTFile_METHOD_JAVA;
    public static final String DOWNLOAD_ATTIS = "downLoadAttis";
    @SuppressLint("ValidFragment")
    public WorkFlowAttachmentFragment(String app_id) {
        this.app_id = app_id;
    }

    protected int getLayoutId() {
        return R.layout.fragment_attachment;
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
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_attachment);
        if (getActivity() != null) {
            mEmptyView = View.inflate(getActivity(),
                    R.layout.layout_empty_view, null);
            mDocResultInfo = ((WorkFlowFormDetalActivity) getActivity()).mDocResultInfo;
        }
        mPullToRefreshListView.setEmptyView(mEmptyView);
        setCommon();
        ListView mListview = mPullToRefreshListView.getRefreshableView();

        mAdapter = new AttachmentAdapter();

        mAdapter.setData(mDocResultInfo.getResult().getListAttInfo(),
                PULLDOWN_TOREFRESH);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                toDeleteMsg(position);
                return true;
            }
        });
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
    }

    public void toDeleteMsg(final int entityIndex) {
        mAdapter.setDeleteIndex(entityIndex);
        mNewFragment = MyAlertDialogFragment.newInstance("确实要删除此收藏记录吗?",
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

    private void setCommon() {
        mPullToRefreshListView.setMode(Mode.DISABLED);
        // 下拉刷新时的提示文本设置
        mPullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setLastUpdatedLabel("");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel(
                "下拉刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setRefreshingLabel("正在刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false)
                .setReleaseLabel("放开刷新");
        // 上拉加载更多时的提示文本设置
        mPullToRefreshListView.getLoadingLayoutProxy(false, true)
                .setLastUpdatedLabel("");
        mPullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(
                "上拉加载");
        mPullToRefreshListView.getLoadingLayoutProxy(false, true)
                .setRefreshingLabel("正在加载");
        mPullToRefreshListView.getLoadingLayoutProxy(false, true)
                .setReleaseLabel("放开加载");
    }

    public void onResume() {
        mAdapter.notifyDataSetChanged();
        super.onResume();
    }


    class AttachmentAdapter extends BaseAdapter {
        ArrayList<AttachmentInfo> arrayList = new ArrayList<AttachmentInfo>();
        private int removeEntityIndex = -1;
        private DownloadManager mDownloadManager;

        // private DecimalFormat df = new DecimalFormat("##.00");

        public AttachmentAdapter() {

        }

        public void removeEntity() {
            if (removeEntityIndex < arrayList.size() && removeEntityIndex >= 0) {
                arrayList.remove(removeEntityIndex);
                notifyDataSetChanged();
                removeEntityIndex = -1;
            }
        }

        public AttachmentInfo getDeleteEntity() {
            if (removeEntityIndex < arrayList.size() && removeEntityIndex >= 0) {
                return arrayList.get(removeEntityIndex);
            }
            return null;
        }

        public void setDeleteIndex(int index) {
            removeEntityIndex = index;
        }

        public void setData(java.util.List<AttachmentInfo> temp, int pullStyle) {
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
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.listitem_attachment,
                        null);
                holder.imgview_attachmentType = (ImageView) convertView
                        .findViewById(R.id.imageview_attachment);
                holder.tv_title = (TextView) convertView
                        .findViewById(R.id.textview_attachment_title);
                holder.tv_size = (TextView) convertView
                        .findViewById(R.id.textview_attachment_size);
                holder.btn = (ImageView) convertView
                        .findViewById(R.id.btn_attachment);
                holder.rl_item = (RelativeLayout) convertView
                        .findViewById(R.id.rl_attachment_item);
                holder.mRectProgressView = (RectProgressView) convertView
                        .findViewById(R.id.circleProgressbar);
                holder.progressBar = (ProgressBar) convertView
                        .findViewById(R.id.progressbar_attachment_download);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AttachmentInfo entity = arrayList.get(position);

            if (CommonFileType.MIME_icon_MapTable_Instance().containsKey(entity.getAttachmentType()))
                holder.imgview_attachmentType.setImageResource(CommonFileType.MIME_icon_MapTable_Instance().get(entity.getAttachmentType()));
            else
                holder.imgview_attachmentType.setImageResource(R.drawable.icon_unkonw);
            holder.imgview_attachmentType.getBackground().setAlpha(0);

            holder.tv_title.setText(entity.getAttachmentTitle());
            holder.attacheId = entity.getAttachmentID();
            holder.attacheName = entity.getAttachmentTitle();
            holder.fileType = entity.getAttachmentType();
            holder.btn.setImageResource(holder.isFileExist() ? R.drawable.btn_accessory_look: R.drawable.btn_accessory_download);
            if (holder.down_state == DOWN_FINISH) {
                holder.btn.setImageResource(R.drawable.btn_accessory_look);
            }
            holder.btn.setOnClickListener(holder.listener);
            //监听整个item
            holder.rl_item.setOnClickListener(holder.listener);
            holder.tv_size.setText(holder.isFileExist() ? (holder.getSize(entity.getAttachmentSize())) +"-已下载" :(holder.getSize(entity.getAttachmentSize())));

            return convertView;
        }

        class ViewHolder implements IBaseCallback, ObserverCallBackType {
            TextView tv_title;
            ImageView imgview_attachmentType;
            TextView tv_size;
            ImageView btn;
            RelativeLayout rl_item;
            ProgressBar progressBar;
            String attacheId;
            String attacheName;
            String downloadUrl;
            String fileType;
            BaseModel baseModel;
            RectProgressView mRectProgressView;
            DownloadInfo downloadInfo = null;

            int down_state = DOWN_START;

            public ViewHolder() {
            }

            @Override
            public void onSuccess(int requestTypeId, Object result) {
                // TODO Auto-generated method stub
                if (result != null
                        && result instanceof DownFilesIsFinishResultInfo) {
                    startDownload((DownFilesIsFinishResultInfo) result);

                }
            }

            @Override
            public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result) {
//                DocInfoModel docInfoModel = new DocInfoModel(this);
                //判断是否是超时
//                String finalString = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValueCloudApi(result, baseModel, requestTypeId, mDocInfoParameters);
//                if (finalString.equals("timeout")) {
//                    return;
//                }
            }

            private boolean isFileExist() {
                if(fileType.endsWith("rar")){
                    fileType = "zip";
                }
                File file = new File(getFilePath() + "." + fileType);
                return file.exists();
            }

            private File getIsFileExist() {
                File file = new File(getFilePath() + "." + fileType);
                return file;
            }

            private String getFilePath() {
                return CommonSettings.DEFAULT_ATTACHMENT_CACHE_FOLDER
                        + File.separator + app_id + File.separator + attacheId + File.separator
                        + attacheId;
            }

            private String getZipFilePath() {
                return CommonSettings.DEFAULT_ATTACHMENT_CACHE_FOLDER
                        + File.separator + app_id + File.separator + attacheId + File.separator
                        + attacheId + ".zip";

            }

            private String getDecipherPath() {
                return CommonSettings.DEFAULT_ATTACHMENT_CACHE_FOLDER
                        + File.separator + app_id + File.separator + attacheId + File.separator
                        + attacheName;
            }

            File file;
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    file = new File(getFilePath() + "." + fileType);
                    if (fileType.contains("zip") || fileType.contains("rar")) {
                        showDialog();
                        setCancelable(true);
                        file = new File(getDecipherNotPath() + fileType);
                    } else {
                        file = new File(getFilePath());
                    }
                    attachMent();
                }
            };

            private String getSize(double fileSize) {
              return Formatter.formatFileSize(getActivity(), Math.round(fileSize));
            }
            private String getDecipherNotPath() {
                return CommonSettings.DEFAULT_ATTACHMENT_CACHE_FOLDER
                        + File.separator + app_id + File.separator + attacheId + File.separator;
            }

            /***/
            public String getFileType(String type) {
                String aimTypeString = "";

                for (int i = 0; i < CommonFileType.MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
                    if (type.equals(CommonFileType.MIME_MapTable[i][0])) {
                        aimTypeString = CommonFileType.MIME_MapTable[i][1];
                        break;
                    }

                }
                return aimTypeString;
            }

            private RequestCallBack<File> mRequestCallBack = new RequestCallBack<File>() {

                public void onStart() {
                    mRectProgressView.setVisibility(View.VISIBLE);
                }

                public void onCancelled() {
                    mRectProgressView.setVisibility(View.GONE);
					progressBar.setVisibility(View.GONE);
                }

                public void onLoading(long total, long current,
                                      boolean isUploading) {
                    mRectProgressView.setVisibility(View.GONE);
                    mRectProgressView.setMaxProgress(total);
                    mRectProgressView.setProgress((int) current);
                    tv_size.setText("附件下载中:"
                            + String.format("%.2f", current * 1.0f / total
                            * 100) + "%");
                    if (total == current) {
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {

                    mRectProgressView.setVisibility(View.GONE);
//					btn.setVisibility(View.GONE);
                    down_state = DOWN_FINISH;
                    btn.setImageResource(R.drawable.btn_accessory_look);
                    String path = responseInfo.result.getAbsolutePath();
                    path = path.substring(path.lastIndexOf(".") + 1);
                    unZipFile(path);

                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    mRectProgressView.setVisibility(View.GONE);
                    dismissDialog();
                }
            };

            private void startDownload(DownFilesIsFinishResultInfo entity) {
                if (mDownloadManager == null) {
                    mDownloadManager = new DownloadManager(
                            HtmitechApplication.instance());
                }
                try {
                    downloadInfo = mDownloadManager.addNewDownload_return(entity.getResult().getAttachmentInfoResult().getDownloadURL(), entity
                                    .getResult().getAttachmentInfoResult().getFileName(),
                            getZipFilePath(), true, false, mRequestCallBack);
                    down_state = DOWN_LOAD;
                    btn.setImageResource(R.drawable.btn_accessory_time_out);
                    //mDownloadManager.stopDownload(index);
                } catch (Exception e) {
                    dismissDialog();
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    ToastInfo toastInfo = ToastInfo
                            .getInstance(HtmitechApplication.instance());
                    toastInfo.setText("下载信息出错，下载失败");
                    toastInfo.show(Toast.LENGTH_SHORT);
                }
            }

            private void unZipFile(final String type) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String typetmp = type;
                        if (TextUtils.isEmpty(typetmp)) {
                            typetmp = "";
                        }

                        if (typetmp.contains("7z")) {
                            new AlertDialog(getActivity()).builder().setTitle("打开").setMsg("移动端暂不支持.7z格式的压缩文件\n查看").setPositiveButton("确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).show();
                            return;
                        }
                        String localPath = "";
                        if (fileType.contains("zip") || fileType.contains("rar")) {
                            localPath = getDecipherNotPath() + fileType;
                        } else {
                            localPath = getDecipherNotPath();
                            typetmp = fileType;
                        }
                        FileNameGenerator fileNameGenerator = new MD5FileNameGenerator();
                        String password = fileNameGenerator.generate(attacheId + fileNameGenerator.generate(attacheId));
                        if (typetmp.contains("rar")) {

                            ArchiverManager.getInstance().doUnArchiver(getZipFilePath(), localPath, password, new IArchiverListener() {
                                @Override
                                public void onStartArchiver() {
                                    showDialog();
                                }

                                @Override
                                public void onProgressArchiver(int current, int total) {
//                                    setValue(current + "/" + total);
                                }

                                @Override
                                public void onEndArchiver() {
                                    dismissDialog();
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("path", getDecipherNotPath() + "/" + fileType);
                                    map.put("name", attacheName);
                                    HTActivityUnit.switchTo(((Activity) getActivity()), UnrarListView.class, map);
                                }
                            });
                        } else {
                            File srcFile = new File(getZipFilePath());
                            String destination = CommonSettings.DEFAULT_ATTACHMENT_CACHE_FOLDER
                                    + File.separator + app_id + File.separator + attacheId;
                            try {
                                CompressUtil.unzip2(srcFile, localPath,
                                        password);
                                dismissDialog();
                                if (typetmp.contains("zip")) {
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("path", localPath);
                                    map.put("name", attacheName);
                                    HTActivityUnit.switchTo(((Activity) getActivity()), UnrarListView.class, map);
                                }

                            } catch (ZipException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }

            @Override
            public void success(String requestValue, int type, String requestName) {
                if (requestName.equals("functionAttach")) {
                    AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocInfoParameters, DOWNLOAD_ATTISFINISH_PATH, CHTTP.POSTWITHTOKEN, this, DOWNLOAD_ATTIS, LogManagerEnum.WORKFLOWATTACHMENTDOWNLOAD.functionCode);
                } else if (DOWNLOAD_ATTIS.equals(requestName)) {
                    requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(getActivity(),requestValue, type, DOWNLOAD_ATTISFINISH_PATH, mDocInfoParameters, this, requestName, LogManagerEnum.WORKFLOWATTACHMENTDOWNLOAD.functionCode);
                    if (!TextUtils.isEmpty(requestValue)) {
                        DownFilesIsFinishResultInfo mDownFilesIsFinishResultInfo = FastJsonUtils.getPerson(requestValue, DownFilesIsFinishResultInfo.class);
                        if (mDownFilesIsFinishResultInfo != null) {
                            if (mDownFilesIsFinishResultInfo.getResult().isFinished()) {
                                startDownload(mDownFilesIsFinishResultInfo);
                            } else {
                                Toast.makeText(getActivity(), "服务器文件还未下载完成，请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            netWorkManager.logFunactionFinsh(getActivity(), this, "functionAttachFail", LogManagerEnum.WORKFLOWATTACHMENTDOWNLOAD.functionCode, "附件下载失败", INetWorkManager.State.FAIL);
                        }

                    } else {
                        netWorkManager.logFunactionFinsh(getActivity(), this, "functionAttachFail", LogManagerEnum.WORKFLOWATTACHMENTDOWNLOAD.functionCode, "附件下载失败", INetWorkManager.State.FAIL);
                    }
                }
            }

            @Override
            public void fail(String exceptionMessage, int type, String requestName) {
                if (requestName.equals("functionAttach")) {
                    attachMent();
                }
            }

            @Override
            public void notNetwork() {

            }

            @Override
            public void callbackMainUI(String successMessage) {

            }


            public void attachMent() {
                if (isFileExist()) { //文件存在逻辑
                    dismissDialog();
                    if (fileType.contains("zip") || fileType.contains("rar")) {
                        File srcFileDir = new File(this.file.getAbsolutePath());
                        if (!srcFileDir.exists()) {
                            getIsFileExist().delete();
                            return;
                        }
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("path", this.file.getAbsolutePath());
                        map.put("name", attacheName);
                        HTActivityUnit.switchTo(((Activity) getActivity()), UnrarListView.class, map);
                        return;
                    }
                    try {
//                        startActivity(intent);
                        HashMap map = new HashMap<String, String>();
                        map.put("path", file.getAbsolutePath() + "." + fileType);
                        HTActivityUnit.switchTo(getActivity(), GeneralFileViewActivity.class, map);
                        netWorkManager.logFunactionFinsh(getActivity(), this, "functionAttachFail", LogManagerEnum.WORKFLOWATTACHMENTDOWNLOAD.functionCode, "", INetWorkManager.State.SUCCESS);
                    } catch (Exception e) {
                        dismissDialog();
                        netWorkManager.logFunactionFinsh(getActivity(), this, "functionAttachFail", LogManagerEnum.WORKFLOWATTACHMENTDOWNLOAD.functionCode, e.getMessage(), INetWorkManager.State.FAIL);
                        ToastInfo toastInfo = ToastInfo
                                .getInstance(HtmitechApplication.instance());
                        toastInfo.setText("系统无法打开文件！请下载相关辅助软件！");
                        toastInfo.show(Toast.LENGTH_SHORT);
                    }

                } else if (down_state == DOWN_START) {
                    // startDownload(downloadUrl);

                    mDocInfoParameters = new AttachmentInfoParameters();
                    mDocInfoParameters.userId = OAConText.getInstance(HtmitechApplication.instance()).UserID;
                    mDocInfoParameters.attachmentId = attacheId;
                    if( getActivity() != null &&  getActivity() instanceof WorkFlowFormDetalActivity){
                        mDocInfoParameters.systemCode = ((WorkFlowFormDetalActivity) getActivity()).getDocKind(); //2015-08-11
                        mDocInfoParameters.appId = ((WorkFlowFormDetalActivity) getActivity()).app_id;
                    }

                    mDocInfoParameters.attachmentType = fileType;
                    netWorkManager.logFunactionStart(WorkFlowAttachmentFragment.this.getActivity(), ViewHolder.this, "functionAttach", LogManagerEnum.WORKFLOWATTACHMENTDOWNLOAD.functionCode);

                } else if (down_state == DOWN_LOAD) {
                    //暂停逻辑
                    try {
                        mDownloadManager.stopDownload(downloadInfo);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    down_state = DOWN_STOP;
                    if (!file.exists()) {
                        btn.setImageResource(R.drawable.btn_accessory_time_start);
                    }
                    //mDownloadManager.resumeDownload(index, callback);

                } else if (down_state == DOWN_STOP) {
                    try {
                        mDownloadManager.resumeDownload(downloadInfo, mRequestCallBack);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    down_state = DOWN_LOAD;
                    if (!file.exists()) {
                        btn.setImageResource(R.drawable.btn_accessory_time_out);
                    }
                }
            }
        }

    }

}
