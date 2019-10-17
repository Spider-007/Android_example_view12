package com.htmitech.htcommonformplugin.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.htmitech.emportal.base.IBaseCallback;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.common.CommonFileType;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.entity.AttachmentInfo;
import com.htmitech.emportal.entity.AttachmentInfoParameters;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.DownFilesIsFinishResultInfo;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.emportal.ui.detail.model.DocInfoModel;
import com.htmitech.emportal.ui.widget.BaseDialog;
import com.htmitech.emportal.ui.widget.DialogCancelListener;
import com.htmitech.emportal.ui.widget.DialogConfirmListener;
import com.htmitech.emportal.ui.widget.MyAlertDialogFragment;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.emportal.utils.CompressUtil;
import com.htmitech.htcommonformplugin.entity.AttachmentInfoParameter;
import com.htmitech.htcommonformplugin.entity.Attachments;
import com.htmitech.htcommonformplugin.entity.DownAttachmentFile_isFinishEntity;
import com.htmitech.htcommonformplugin.entity.GetDetailEntity;
import com.htmitech.htcommonformplugin.model.GetCommonFromInfoModel;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 附件
 *
 * @author tenggang
 */
public class GeneralFormAttachmentFragment extends MyBaseFragment {

    private static final String TAG = "GeneralFormAttachmentFr";

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
    private GetDetailEntity mGetDetailEntity;
    private String attachment_tab_formkey;

    private INetWorkManager netWorkManager;
    private String app_id;
    @SuppressLint("ValidFragment")
    public GeneralFormAttachmentFragment(String attachment_tab_formkey,String app_id) {
        this.attachment_tab_formkey = attachment_tab_formkey;
        this.app_id = app_id;
    }

    protected int getLayoutId() {
        return R.layout.fragment_attachment_generalform;
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
//            mGetDetailEntity = ((GeneralFormDetalActivity) getActivity()).mGetDetailEntity;
        }
        mPullToRefreshListView.setEmptyView(mEmptyView);
        setCommon();
        ListView mListview = mPullToRefreshListView.getRefreshableView();

        mAdapter = new AttachmentAdapter();
        // SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new
        // SwingBottomInAnimationAdapter(mAdapter);
        // swingBottomInAnimationAdapter.setListView(mListview);
        // mListview.setAdapter(swingBottomInAnimationAdapter);

        mAdapter.setData(mGetDetailEntity.getResult().getAttachments(),
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
        ArrayList<Attachments> arrayList = new ArrayList<Attachments>();
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

        public Attachments getDeleteEntity() {
            if (removeEntityIndex < arrayList.size() && removeEntityIndex >= 0) {
                return arrayList.get(removeEntityIndex);
            }
            return null;
        }

        public void setDeleteIndex(int index) {
            removeEntityIndex = index;
        }

        public void setData(java.util.List<Attachments> temp, int pullStyle) {
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
                holder.btn = (Button) convertView
                        .findViewById(R.id.btn_attachment);
                holder.rl_item = (RelativeLayout) convertView
                        .findViewById(R.id.rl_attachment_item);
                holder.progressBar = (ProgressBar) convertView
                        .findViewById(R.id.progressbar_attachment_download);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Attachments entity = arrayList.get(position);

            if (CommonFileType
                    .MIME_icon_MapTable_Instance().containsKey(
                            entity.getAttachment_filetype()))
                holder.imgview_attachmentType.setImageResource(CommonFileType
                        .MIME_icon_MapTable_Instance().get(
                                entity.getAttachment_filetype()));
            else
                holder.imgview_attachmentType.setImageResource(R.drawable.icon_unkonw);

            holder.tv_title.setText(entity.getAttachment_title());
            holder.attacheId = entity.getAttachment_id();
            holder.attacheName = entity.getAttachment_title();
            holder.fileType = entity.getAttachment_filetype();
            holder.btn.setText(holder.isFileExist() ? "查看" : "下载");
            if (holder.down_state == DOWN_FINISH) {
                holder.btn.setText("查看");
            }
            holder.btn.setOnClickListener(holder.listener);
            //监听整个item
            holder.rl_item.setOnClickListener(holder.listener);
            holder.tv_size.setText(holder.isFileExist() ? "已下载，大小:"
                    + (holder.getSize(entity.getAttachment_filesize())) : "大小:"
                    + (holder.getSize(entity.getAttachment_filesize())));

            // BitmapUtils.instance().display(imageView, entity.get,
            // new BitmapLoadCallBack<View>() {
            //
            // @Override
            // public void onLoadStarted(View container, String uri,
            // BitmapDisplayConfig config) {
            // imageView
            // .setImageResource(R.drawable.circle_brife_iv);
            // }
            //
            // @Override
            // public void onLoadCompleted(View container, String uri,
            // Bitmap bitmap, BitmapDisplayConfig config,
            // BitmapLoadFrom from) {
            // imageView.setImageBitmap(bitmap);
            // }
            //
            // @Override
            // public void onLoadFailed(View container, String uri,
            // Drawable drawable) {
            // imageView
            // .setImageResource(R.drawable.circle_brife_iv);
            // }
            // });
            return convertView;
        }

        class ViewHolder implements IBaseCallback, ObserverCallBackType {
            TextView tv_title;
            ImageView imgview_attachmentType;
            TextView tv_size;
            Button btn;
            RelativeLayout rl_item;
            ProgressBar progressBar;
            String attacheId;
            String attacheName;
            String downloadUrl;
            GetCommonFromInfoModel mGetCommonFromInfoModel;
            String fileType;

            DownloadInfo downloadInfo = null;

            int down_state = DOWN_START;

            public ViewHolder() {
                mGetCommonFromInfoModel = new GetCommonFromInfoModel(this);
            }

            @Override
            public void onSuccess(int requestTypeId, Object result) {
                // TODO Auto-generated method stub
                if (result != null
                        && result instanceof DownAttachmentFile_isFinishEntity) {
                    startDownload((DownAttachmentFile_isFinishEntity) result);

                }
            }

            @Override
            public void onFail(int requestTypeId, int statusCode,
                               String errorMsg, Object result) {

            }

            private boolean isFileExist() {
                File file = new File(getFilePath());
                file.exists();
                return file.exists();
            }

            private String getFilePath() {
                return CommonSettings.DEFAULT_ATTACHMENT_CACHE_FOLDER
                        + File.separator + app_id+ File.separator + attacheId + File.separator
                        + attacheName;
            }

            private String getZipFilePath() {
                return CommonSettings.DEFAULT_ATTACHMENT_CACHE_FOLDER
                        + File.separator + app_id+ File.separator + attacheId + File.separator
                        + attacheId + ".zip";
            }

            private String getDecipherPath() {
                return CommonSettings.DEFAULT_ATTACHMENT_CACHE_FOLDER
                        + File.separator + app_id+ File.separator + attacheId + File.separator
                        + attacheName;
            }

            File file;
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    file = new File(getFilePath());
//                    attachMent();
                    netWorkManager.logFunactionStart(GeneralFormAttachmentFragment.this.getActivity(), ViewHolder.this, "commonformAttach", LogManagerEnum.COMMONFORM_DOWN_LOAD_ATTACHMENT.functionCode);


                }
            };

            private String getSize(double fileSize) {
                if (fileSize < 1024) {
                    return String.format("%.2f", fileSize / 1024) + "K";
                    // return df.format(fileSize) + "K";
                } else if (fileSize < 1024 * 1024) {
                    return String.format("%.2f", fileSize / 1024 / 1024) + "M";
                } else {
                    return String.format("%.2f", fileSize / 1024 / 1024) + "M";
                }
            }

            public String getFileType(String type) {
                String aimTypeString = "";

                for (int i = 0; i < CommonFileType.MIME_MapTable.length; i++) {
                    if (type.contains(CommonFileType.MIME_MapTable[i][0])) {
                        aimTypeString = CommonFileType.MIME_MapTable[i][1];
                        break;
                    }

                }
                return aimTypeString;
            }

            private RequestCallBack<File> mRequestCallBack = new RequestCallBack<File>() {

                public void onStart() {
                    progressBar.setVisibility(View.VISIBLE);
                }

                public void onCancelled() {
//					progressBar.setVisibility(View.GONE);
                }

                public void onLoading(long total, long current,
                                      boolean isUploading) {
                    progressBar.setMax((int) total);
                    progressBar.setProgress((int) current);
                    tv_size.setText("附件下载中:"
                            + String.format("%.2f", current * 1.0f / total
                            * 100) + "%");
                    if (total == current) {
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {

                    progressBar.setVisibility(View.GONE);
//					btn.setVisibility(View.GONE);
                    down_state = DOWN_FINISH;
                    btn.setText("查看");
                    unZipFile();
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    progressBar.setVisibility(View.GONE);
                }
            };

            private void startDownload(DownAttachmentFile_isFinishEntity entity) {
                if (mDownloadManager == null) {
                    mDownloadManager = new DownloadManager(
                            HtmitechApplication.instance());
                }
                try {
                    downloadInfo = mDownloadManager.addNewDownload_return(entity.getResult()
                                    .getFileinfo().getFile_url(), entity
                                    .getResult().getFileinfo().getFile_name(),
                            getZipFilePath(), true, false, mRequestCallBack);
                    down_state = DOWN_LOAD;
                    btn.setText("暂停");
                    //mDownloadManager.stopDownload(index);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    ToastInfo toastInfo = ToastInfo
                            .getInstance(HtmitechApplication.instance());
                    toastInfo.setText("下载信息出错，下载失败");
                    toastInfo.show(Toast.LENGTH_SHORT);
                }
            }

            private void unZipFile() {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            /** 解压 **/
                            File srcFile = new File(getZipFilePath());
                            String destination = CommonSettings.DEFAULT_ATTACHMENT_CACHE_FOLDER
                                    + File.separator + app_id + File.separator + attacheId;
                            try {
                                CompressUtil.unzip(srcFile, destination,
                                        "password");
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

            @Override
            public void success(String requestValue, int type, String requestName) {
                if (requestName.equals("commonformAttach")) {
                    attachMent();
                }
            }

            @Override
            public void fail(String exceptionMessage, int type, String requestName) {
                if (requestName.equals("commonformAttach")) {
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
                if (file.exists()) { //文件存在逻辑

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file),
                            getFileType(fileType));
                    try {
                        startActivity(intent);
                        netWorkManager.logFunactionFinsh(getActivity(), this, "commonformAttachFail", LogManagerEnum.COMMONFORM_DOWN_LOAD_ATTACHMENT.functionCode, "", INetWorkManager.State.SUCCESS);
                    } catch (Exception e) {
                        Log.e(TAG, "attachMent: "+e.toString());
                        netWorkManager.logFunactionFinsh(getActivity(), this, "commonformAttachFail", LogManagerEnum.COMMONFORM_DOWN_LOAD_ATTACHMENT.functionCode, e.getMessage(), INetWorkManager.State.FAIL);
                        ToastInfo toastInfo = ToastInfo
                                .getInstance(HtmitechApplication.instance());
                        toastInfo.setText("系统无法打开文件！请下载相关辅助软件！");
                        toastInfo.show(Toast.LENGTH_SHORT);
                    }
                } else if (down_state == DOWN_START) {
                    // startDownload(downloadUrl);
                    List<Attachments> mAttachmentsList = new ArrayList<Attachments>();
                    mAttachmentsList = mGetDetailEntity.getResult().getAttachments();
                    AttachmentInfoParameter mAttachmentInfoParameter = new AttachmentInfoParameter();
                    for (Attachments mAttachments : mAttachmentsList) {
                        if (attachment_tab_formkey != null && attachment_tab_formkey.equals(mAttachments.attachment_tab_formkey)) {

//                            mAttachmentInfoParameter.app_id = ((GeneralFormDetalActivity) getActivity()).app_id;
                            mAttachmentInfoParameter.user_id = OAConText.getInstance(getActivity()).UserID;
                            mAttachmentInfoParameter.attachment_id = mAttachments.attachment_id;
                            mAttachmentInfoParameter.file_name = mAttachments.getAttachment_title();
                            mAttachmentInfoParameter.file_type = mAttachments.getAttachment_filetype();

                            mGetCommonFromInfoModel.getDataFromServerByType(
                                    GetCommonFromInfoModel.TYPE_GET_DOWNLOAD_ATTISFINISH,
                                    mAttachmentInfoParameter);
                        }
                    }


                } else if (down_state == DOWN_LOAD) {
                    //暂停逻辑
                    try {
                        mDownloadManager.stopDownload(downloadInfo);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    down_state = DOWN_STOP;
                    if (!file.exists()) {
                        btn.setText("开始");
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
                        btn.setText("暂停");
                    }
                }
            }
        }

    }

}
