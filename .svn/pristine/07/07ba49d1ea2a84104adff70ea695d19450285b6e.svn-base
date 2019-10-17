package com.htmitech.emportal.ui.document;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.commonx.base.app.DialogFactory;
import com.htmitech.commonx.base.exception.DbException;
import com.htmitech.commonx.base.exception.HttpException;
import com.htmitech.commonx.base.http.ResponseInfo;
import com.htmitech.commonx.base.http.callback.RequestCallBack;
import com.htmitech.commonx.base.net.DownloadInfo;
import com.htmitech.commonx.base.net.DownloadManager;
import com.htmitech.commonx.base.net.DownloadService;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.Mode;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.CommonFileType;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.document.adapter.DocumentSubNodeAndListAdapter;
import com.htmitech.emportal.ui.document.data.DocumentSerachParmarsEntity;
import com.htmitech.emportal.ui.document.data.DocumentSubNodeAndList;
import com.htmitech.emportal.ui.document.data.FsDocResultList;
import com.htmitech.emportal.ui.document.data.RequestSubAndListEntity;
import com.htmitech.emportal.utils.CacheDeleteUtils;
import com.htmitech.emportal.utils.OpenFiles;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.proxy.interfaces.INetWorkManager;

import com.htmitech.proxy.util.AESPartUtil;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.CHTTP;
import com.minxing.client.RootActivity;
import com.minxing.client.util.DocumentNodeUtil;
import com.minxing.client.util.MD5Util;
import com.minxing.client.util.Utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.thread.Network;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 资料库
 */
public class DocumentAndNodeListActivity extends RootActivity implements OnClickListener, ObserverCallBackType, DocumentStartOrStopListener {
    private final static String TAG = DocumentAndNodeListActivity.class.getName();
    private ImageButton leftBackButton = null;
    private DocumentSubNodeAndListAdapter documentAdapter = null;
    private PullToRefreshListView documentnodeListView = null;
    private EditText editText_keyword;
    private ImageView remove_icon; //去除所有关键字按钮
    private Handler handler;
    /***
     * 页码
     **/
    private int pageNum = 1;

    /***
     * 每页要读取的记录数量
     **/
    private int countPerPage = 15;
    private static final int MSGID_GET_FILELIST = 8; //搜索文件
    private boolean isHasMore = true;
    /**
     * 当前节点ID
     */
    private String mCurrentDocNodeID;
    private Vector<FsDocResultList> documentSubNodeAndListEntity = new Vector<FsDocResultList>();

    /**
     * 需要下载的文件
     */
    private FsDocResultList docToDownload;
    private File file_tmp; // 临时文件 作为操作删除使用
    private List<File> delectFileTemp;//存放要删除的临时文件
    private LinearLayout linearDeleteItem;
    private Button buttonDeleteItem;
    private RelativeLayout relativeDeleteItemTop;
    private Button buttonDeleteCanle;
    private Button buttonDeleteSelectAll;
    private RelativeLayout relativeSystemTitle;
    private boolean isClickLong = false;
    private com.htmitech.MyView.EmptyLayout linearDoucmentEmpty;
    private String app_id;
    private INetWorkManager netWorkManager;
    RequestSubAndListEntity mRequestSubAndListEntity = new RequestSubAndListEntity();
    DocumentSerachParmarsEntity mSearchentity = new DocumentSerachParmarsEntity();
    private String getSubNodeAndListurl = ServerUrlConstant.SERVER_EMPAPI_URL() +
            ServerUrlConstant.DOCUMENT_GETSUBNODEANDLIST_METHOD;
    private String getSerachurl = ServerUrlConstant.SERVER_EMPAPI_URL() +
            ServerUrlConstant.DOCUMENT_GETDOCUMENT_SERCH_METHOD;
    private Button buttonDelete;
    public boolean isFirst;
    public String nodeIds = "";
    public boolean isSerach = false;
    public String appShortName;
    public Map<String, DownloadInfo> downloadInfoMap;

    @SuppressLint("HandlerLeak")
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DocumentNodeUtil.add(this);
        setContentView(R.layout.activity_documentandnodelist);
        downloadInfoMap = new HashMap<>();
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        delectFileTemp = new ArrayList<File>();
        mCurrentDocNodeID = getIntent().getStringExtra("ParentDocNodeID");
        app_id = getIntent().getStringExtra("app_id");
        String mParentDocNodeName = getIntent().getStringExtra("ParentDocNodeName");
        nodeIds = getIntent().getStringExtra("nodeIds");
        isSerach = getIntent().getBooleanExtra("isSerach", false);
        appShortName = getIntent().getStringExtra("appShortName");
        ((TextView) findViewById(R.id.title_name)).setText(mParentDocNodeName != null ? mParentDocNodeName : appShortName);
        linearDoucmentEmpty = (com.htmitech.MyView.EmptyLayout) findViewById(R.id.ll_doucment_gride_emptey);
        Button allBack = (Button) findViewById(R.id.title_right_button);
        allBack.setVisibility(View.VISIBLE);
        allBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (int i = 0; i < delectFileTemp.size(); i++) {
//                    File file_tmp_delect = delectFileTemp.get(i);
//                    if (file_tmp_delect != null && file_tmp_delect.exists()) {
//                        file_tmp_delect.delete();
//                    }
//                }
                try {
                    CacheDeleteUtils.clearFiles(CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DocumentNodeUtil.removeAll();
            }
        });
        editText_keyword = (EditText) findViewById(R.id.editText_keyword);
        editText_keyword.addTextChangedListener(watcher);
        editText_keyword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(DocumentAndNodeListActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (null != documentAdapter)
                        documentAdapter.Clear();
                    pullDownRefresh();
                    return true;
                }
                return false;
            }
        });

        remove_icon = (ImageView) findViewById(R.id.remove_icon);
        remove_icon.setVisibility(View.GONE);
        remove_icon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                editText_keyword.setText("");
            }
        });

        leftBackButton = (ImageButton) findViewById(R.id.title_left_button);
        leftBackButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                CacheDeleteUtils.clearFiles(CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id);
                finishWithAnimation();
            }
        });

        linearDeleteItem = (LinearLayout) findViewById(R.id.ll_delete_item);
        buttonDeleteItem = (Button) findViewById(R.id.bt_delete_item);
        buttonDeleteItem.setOnClickListener(this);
        buttonDeleteCanle = (Button) findViewById(R.id.bt_delete_item_canle);
        buttonDelete = (Button) findViewById(R.id.bt_delete_item);
        buttonDeleteCanle.setOnClickListener(this);
        buttonDeleteSelectAll = (Button) findViewById(R.id.bt_delete_item_selectall);
        buttonDeleteSelectAll.setOnClickListener(this);
        relativeDeleteItemTop = (RelativeLayout) findViewById(R.id.ll_delete_item_top);
        relativeSystemTitle = (RelativeLayout) findViewById(R.id.system_title);
        documentnodeListView = (PullToRefreshListView) findViewById(R.id.listview_documentnode);
        documentnodeListView.setMode(Mode.BOTH);
        documentnodeListView.setOnRefreshListener(new OnRefreshListener2() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub
                // 下拉刷新
                pullDownRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub 上拉加载更多
                pullUpLoadMore();
            }
        });
        documentnodeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                if (!isClickLong) {
                    FsDocResultList entity = documentAdapter.getList().get(position - 1);
                    if (null != entity && entity.type == 1) {//目录
                        FsDocResultList docNode = (FsDocResultList) entity;
                        Intent intent = new Intent();
                        intent.setClass(DocumentAndNodeListActivity.this, DocumentAndNodeListActivity.class);
                        intent.putExtra("ParentDocNodeID", docNode.id);
                        intent.putExtra("functionCodeFinish", LogManagerEnum.DOCUMENT_SUB.functionCode);
                        intent.putExtra("ParentDocNodeName", docNode.name);
                        intent.putExtra("app_id", app_id);
                        startActivity(intent);
                    } else if (null != entity && entity.type == 2) {  //非目录,是文件
                        //文件下载统一到右侧按钮的点击
                    }
                } else if (isClickLong) {
                    if (documentAdapter.getList().get(position - 1) != null && documentAdapter.getList().get(position - 1) instanceof FsDocResultList) {
                        FsDocResultList doc = (FsDocResultList) documentAdapter.getList().get(position - 1);
                        doc.isCheck = !doc.isCheck;
                        documentAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
        //长点击删除
//        documentnodeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                if (documentAdapter.getList().get(position - 1) != null && documentAdapter.getList().get(position - 1) instanceof FsDocResultList) {
//                    linearDeleteItem.setVisibility(View.VISIBLE);
//                    relativeDeleteItemTop.setVisibility(View.VISIBLE);
////                    relativeSystemTitle.setVisibility(View.GONE);
//                    setViewEnable(false);
//                    documentAdapter.needDelete = true;
//                    isClickLong = true;
//                    FsDocResultList doc = (FsDocResultList) documentAdapter.getList().get(position - 1);
//                    doc.isCheck = true;
//                    documentAdapter.notifyDataSetChanged();
//                }
//                return true;
//            }
//        });

        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == MSGID_GET_FILELIST) {
                    if (null != documentAdapter)
                        documentAdapter.Clear();
                    pullDownRefresh();
                }

            }
        };
        //获取文档库子目录开启日志
        netWorkManager.logFunactionStart(DocumentAndNodeListActivity.this, DocumentAndNodeListActivity.this, LogManagerEnum.DOCUMENT_SUB);
    }

    /*
    * 资料库文档下载
    * */
    private void downLoadFile(final FsDocResultList doc, final String filePath, final String filePath_temp) {
        netWorkManager.logFunactionStart(DocumentAndNodeListActivity.this, DocumentAndNodeListActivity.this, LogManagerEnum.DOCUMENT_DOWNLOAD);
        DownloadManager downloadManager = DownloadService.getDownloadManager(HtmitechApplication.getInstance());
        DownloadInfo downloadInfo = null;
        try {
            String md5FileName = MD5Util.getMD5String(docToDownload.filename) + "." + StringUtils.substringAfterLast(docToDownload.filename, ".");
            downloadInfo = downloadManager.addNewDownload_return(docToDownload.filePath, docToDownload.filename,
                    CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + docToDownload.fileId + File.separator + md5FileName, false, false, new RequestCallBack<File>() {

                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {
                            // TODO Auto-generated method stub
                            final File file = responseInfo.result;
                            if (file.exists()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Utils.toast(DocumentAndNodeListActivity.this, "文件" + docToDownload.filename + "下载成功！", Toast.LENGTH_SHORT);
                                    }
                                });

                                netWorkManager.logFunactionFinsh(DocumentAndNodeListActivity.this, DocumentAndNodeListActivity.this, "documentDownLoadSuccess", LogManagerEnum.DOCUMENT_DOWNLOAD.getFunctionCode(), "下载完成", INetWorkManager.State.SUCCESS);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            AESPartUtil.copyFile(filePath, filePath_temp);//复制一个临时文件
                                            AESPartUtil.decryptFile(filePath_temp);//将临时文件进行解密
                                            file_tmp = new File(filePath_temp);
                                            delectFileTemp.add(file_tmp);
                                            openFile(file_tmp);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }).start();
                            }
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            // TODO Auto-generated method stub
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utils.toast(DocumentAndNodeListActivity.this, "文件下载失败！", Toast.LENGTH_SHORT);
                                    doc.isDownLoading = false;
                                    doc.isDownLoadFinish = false;
                                    documentAdapter.notifyDataSetChanged();
                                }
                            });
                            netWorkManager.logFunactionFinsh(DocumentAndNodeListActivity.this, DocumentAndNodeListActivity.this, "documentDownLoadFail", LogManagerEnum.DOCUMENT_DOWNLOAD.getFunctionCode(), "文件下载失败", INetWorkManager.State.FAIL);
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                            doc.total = (int) total;
                            doc.current = (int) current;
                            if (total == current) {
                                doc.isDownLoadFinish = true;
                                doc.isDownLoading = false;
                            }
                            documentAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onCancelled() {
                        }
                    });
            downloadInfoMap.put(docToDownload.fileId + md5FileName, downloadInfo);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void pullDownRefresh() {
        // 下拉刷新
        pageNum = 1;
        isHasMore = true;
        getRefreshData();
    }

    private void pullUpLoadMore() {
        if (!isHasMore) {
            Utils.toast(DocumentAndNodeListActivity.this, "没有了！", Toast.LENGTH_SHORT);
            documentnodeListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    documentnodeListView.onRefreshComplete();
                }
            }, 100);
            return;
        }
        getMoreDocumentData("getMoreData");

    }

    private TextWatcher watcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            if (editText_keyword.getText().toString().equals("")) {
                remove_icon.setVisibility(View.GONE);
                handler.sendEmptyMessage(MSGID_GET_FILELIST);
            } else {
                remove_icon.setVisibility(View.VISIBLE);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }
    };

    /**
     * 刷新列表
     **/
    public void getRefreshData() {
        clearData();
        if (isSerach) {
            mSearchentity = new DocumentSerachParmarsEntity();
            if (null != editText_keyword)
                mSearchentity.name = (editText_keyword.getText().toString());
            mSearchentity.nodeIds = nodeIds;
            mSearchentity.pageNum = String.valueOf(1);
            mSearchentity.pageSize = String.valueOf(countPerPage);
            mSearchentity.userId = OAConText.getInstance(DocumentAndNodeListActivity.this).UserID;
            AnsynHttpRequest.requestByPostWithToken(DocumentAndNodeListActivity.this, mSearchentity, getSerachurl, CHTTP.POSTWITHTOKEN, DocumentAndNodeListActivity.this, "getSubNodeAndList", LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
        } else {
            mRequestSubAndListEntity = new RequestSubAndListEntity();
            mRequestSubAndListEntity.parentId = (mCurrentDocNodeID);
            mRequestSubAndListEntity.groupCorpId = (OAConText.getInstance(DocumentAndNodeListActivity.this).group_corp_id);
            mRequestSubAndListEntity.pageNum = (String.valueOf(1));
            mRequestSubAndListEntity.pageSize = (String.valueOf(countPerPage));
            mRequestSubAndListEntity.name = (editText_keyword.getText().toString());
            AnsynHttpRequest.requestByPostWithToken(DocumentAndNodeListActivity.this, mRequestSubAndListEntity, getSubNodeAndListurl, CHTTP.POSTWITHTOKEN, DocumentAndNodeListActivity.this, "getSubNodeAndList", LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
            Log.d(TAG, "发起获取待办列表请求:getRefreshData");
        }
    }

    /****
     * 获取更多文档
     */
    public void getMoreDocumentData(String type) {
        this.pageNum++;
        if (isSerach) {
            mSearchentity = new DocumentSerachParmarsEntity();
            if (null != editText_keyword)
                mSearchentity.name = (editText_keyword.getText().toString());
            mSearchentity.nodeIds = nodeIds;
            mSearchentity.pageNum = String.valueOf(String.valueOf(pageNum));
            mSearchentity.pageSize = String.valueOf(countPerPage);
            mSearchentity.userId = OAConText.getInstance(DocumentAndNodeListActivity.this).UserID;
            AnsynHttpRequest.requestByPostWithToken(DocumentAndNodeListActivity.this, mSearchentity, getSerachurl, CHTTP.POSTWITHTOKEN, DocumentAndNodeListActivity.this, "getSubNodeAndList", LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
        } else {
            mRequestSubAndListEntity = new RequestSubAndListEntity();
            mRequestSubAndListEntity.parentId = (mCurrentDocNodeID);
            mRequestSubAndListEntity.groupCorpId = OAConText.getInstance(DocumentAndNodeListActivity.this).group_corp_id;
            mRequestSubAndListEntity.pageNum = (String.valueOf(pageNum));
            if (null != editText_keyword) {
                mRequestSubAndListEntity.name = (editText_keyword.getText().toString());
            }
            mRequestSubAndListEntity.pageSize = (String.valueOf(countPerPage));
            AnsynHttpRequest.requestByPostWithToken(DocumentAndNodeListActivity.this, mRequestSubAndListEntity, getSubNodeAndListurl, CHTTP.POSTWITHTOKEN, DocumentAndNodeListActivity.this, type, LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
        }
    }

    public void clearData() {
        if (documentAdapter == null) {
            return;
        }
        if (documentSubNodeAndListEntity != null) {
            documentSubNodeAndListEntity.clear();

        }
        documentAdapter.Clear();
        documentAdapter.notifyDataSetChanged();
    }

    /*
    * adapter数据更新时刷数据列表
    * */
    public void setDocumentNodeAndListData(Vector<FsDocResultList> nodeList, boolean isFrist) {
        if (documentAdapter == null) {
            documentAdapter = new DocumentSubNodeAndListAdapter(DocumentAndNodeListActivity.this, app_id, this);
            documentnodeListView.setAdapter(documentAdapter);
        }
        documentAdapter.AddDocumentNodeAndList(nodeList, isFrist);
        documentAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishWithAnimation();
//            for (int i = 0; i < delectFileTemp.size(); i++) {
//                File file_tmp_delect = delectFileTemp.get(i);
//                if (file_tmp_delect != null && file_tmp_delect.exists()) {
//                    file_tmp_delect.delete();
//                }
//            }
            try {
                CacheDeleteUtils.clearFiles(CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /***/
    public String getFileType(String type) {
        String aimTypeString = "";

        for (int i = 0; i < CommonFileType.MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (type.equals(CommonFileType.MIME_MapTable[i][0])) {
                aimTypeString = CommonFileType.MIME_MapTable[i][1];
                break;
            }

        }
        return aimTypeString;


    }

    public String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1).toLowerCase();
            }
        }
        return filename;
    }

    //	// 4、通过调用OpenFiles类返回的Intent，打开相应的文件
    private void openFile(File currentPath) {
        try {
            if (currentPath != null && currentPath.isFile()) {
                String fileName = currentPath.toString();
                Intent intent = null;
                if (checkEndsWithInStringArray(fileName, getResources()
                        .getStringArray(R.array.fileEndingImage))) {
                    intent = OpenFiles.getImageFileIntent(currentPath);
                    startActivity(intent);
                } else if (checkEndsWithInStringArray(fileName, getResources()
                        .getStringArray(R.array.fileEndingWebText))) {
                    intent = OpenFiles.getHtmlFileIntent(currentPath);
                    startActivity(intent);
                } else if (checkEndsWithInStringArray(fileName, getResources()
                        .getStringArray(R.array.fileEndingPackage))) {
                    intent = OpenFiles.getApkFileIntent(currentPath);
                    startActivity(intent);

                } else if (checkEndsWithInStringArray(fileName, getResources()
                        .getStringArray(R.array.fileEndingAudio))) {
                    intent = OpenFiles.getAudioFileIntent(currentPath);
                    startActivity(intent);
                } else if (checkEndsWithInStringArray(fileName, getResources()
                        .getStringArray(R.array.fileEndingVideo))) {
                    intent = OpenFiles.getVideoFileIntent(currentPath);
                    startActivity(intent);
                } else if (checkEndsWithInStringArray(fileName, getResources()
                        .getStringArray(R.array.fileEndingText))) {
                    intent = OpenFiles.getTextFileIntent(currentPath);
                    startActivity(intent);
                } else if (checkEndsWithInStringArray(fileName, getResources()
                        .getStringArray(R.array.fileEndingPdf))) {
                    intent = OpenFiles.getPdfFileIntent(currentPath);
                    startActivity(intent);
                } else if (checkEndsWithInStringArray(fileName, getResources()
                        .getStringArray(R.array.fileEndingWord))) {
                    intent = OpenFiles.getWordFileIntent(currentPath);
                    startActivity(intent);
                } else if (checkEndsWithInStringArray(fileName, getResources()
                        .getStringArray(R.array.fileEndingExcel))) {
                    intent = OpenFiles.getExcelFileIntent(currentPath);
                    startActivity(intent);
                } else if (checkEndsWithInStringArray(fileName, getResources()
                        .getStringArray(R.array.fileEndingPPT))) {
                    intent = OpenFiles.getPPTFileIntent(currentPath);
                    startActivity(intent);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("OpenMode", "ReadMode");
                    intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(currentPath),
                            getFileType(getExtensionName(currentPath.getName())));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.toast(DocumentAndNodeListActivity.this, "系统无法打开文件！请下载相关辅助软件！", Toast.LENGTH_SHORT);
                }
            });

        }
    }

    private boolean checkEndsWithInStringArray(String checkItsEnd,
                                               String[] fileEndings) {
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    private List<FsDocResultList> documentBaseEntityList;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_delete_item_canle://取消
                linearDeleteItem.setVisibility(View.GONE);
                relativeDeleteItemTop.setVisibility(View.GONE);
                relativeSystemTitle.setVisibility(View.VISIBLE);
                setViewEnable(true);
                documentAdapter.canleDelete();
                documentAdapter.needDelete = false;
                isClickLong = false;
                documentAdapter.notifyDataSetChanged();
                break;
            case R.id.bt_delete_item_selectall://全选 全不选
                if (buttonDeleteSelectAll.getText().toString().equals("全选")) {
                    buttonDeleteSelectAll.setText("全不选");
                    documentAdapter.selectAllItem();
                } else {
                    buttonDeleteSelectAll.setText("全选");
                    documentAdapter.canleAllItem();
                }

                documentAdapter.notifyDataSetChanged();
                break;
            case R.id.bt_delete_item://删除
                documentBaseEntityList = documentAdapter.getList();
                setViewEnable(true);
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean isDelectSuccess = false;
                        for (int i = 0; i < documentBaseEntityList.size(); i++) {
                            if (!(documentBaseEntityList.get(i) instanceof FsDocResultList)) {
                                continue;
                            }
                            FsDocResultList entity = (FsDocResultList) documentBaseEntityList.get(i);
                            if (null == entity.filename) {
                                continue;
                            }
                            String md5FileName = MD5Util.getMD5String(entity.filename) + "." + StringUtils.substringAfterLast(entity.filename, ".");
                            if (entity.isCheck) {
                                final File file = new File(CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + entity.fileId + File.separator + md5FileName);
                                final String filePath = (CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + entity.fileId + File.separator + md5FileName);
                                final String filePath_temp = CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + ("tmp_" + md5FileName);
                                if (file.exists()) {
                                    if (!deleteFile(filePath) || !deleteFile(filePath_temp)) {
                                        isDelectSuccess = false;
                                        break;
                                    }
                                    entity.isDownLoadFinish = false;
                                    entity.isDownLoading = false;
                                    downloadInfoMap.remove(entity.fileId + md5FileName);
                                }
                            }
                        }
                        isDelectSuccess = true;
                        isdelectFinish(isDelectSuccess);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public void isdelectFinish(boolean isDelectSuccess) {
        if (isDelectSuccess) {
            linearDeleteItem.setVisibility(View.GONE);
            relativeDeleteItemTop.setVisibility(View.GONE);
            relativeSystemTitle.setVisibility(View.VISIBLE);
            documentAdapter.canleDelete();
            documentAdapter.needDelete = false;
            isClickLong = false;
            documentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        try {
            if (requestName.equals(LogManagerEnum.DOCUMENT_MAIN.getFunctionCode()) || requestName.equals(LogManagerEnum.DOCUMENT_SUB.getFunctionCode())) {
                //如果是从筛选插件进来的 则走isSerach=true
                if (isSerach) {
                    //发起网络请求，获取所有当前用户能够看到的顶层资料库目录
//                    String url = ServerUrlConstant.SERVER_EMPAPI_URL() +
//                            ServerUrlConstant.DOCUMENT_GETDOCUMENT_SERCH_METHOD;
                    if (null != editText_keyword)
                        mSearchentity.name = editText_keyword.getText().toString();
                    mSearchentity.nodeIds = nodeIds;
                    mSearchentity.pageNum = String.valueOf(1);
                    mSearchentity.pageSize = String.valueOf(countPerPage);
                    mSearchentity.userId = OAConText.getInstance(DocumentAndNodeListActivity.this).UserID;
                    AnsynHttpRequest.requestByPostWithToken(DocumentAndNodeListActivity.this, mSearchentity, getSerachurl, CHTTP.POSTWITHTOKEN, DocumentAndNodeListActivity.this, "getSubNodeAndList", LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
                } else {
                    mRequestSubAndListEntity.groupCorpId = OAConText.getInstance(DocumentAndNodeListActivity.this).group_corp_id;
                    mRequestSubAndListEntity.parentId = mCurrentDocNodeID;
                    mRequestSubAndListEntity.pageNum = String.valueOf(1);
                    mRequestSubAndListEntity.pageSize = String.valueOf(countPerPage);
                    if (null != editText_keyword)
                        mRequestSubAndListEntity.name = editText_keyword.getText().toString();
                    AnsynHttpRequest.requestByPostWithToken(DocumentAndNodeListActivity.this, mRequestSubAndListEntity, getSubNodeAndListurl, CHTTP.POSTWITHTOKEN, DocumentAndNodeListActivity.this, "getSubNodeAndList", LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
                }
            } else if (requestName.equals("getSubNodeAndList")) {
                if (null != documentSubNodeAndListEntity) {
                    documentSubNodeAndListEntity.removeAllElements();
                    documentSubNodeAndListEntity.clear();
                }
                isFirst = true;
                if (isSerach) {
                    requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getSerachurl, mSearchentity, this, requestName, LogManagerEnum.DOCUMENT_SUB.getFunctionCode());

                } else {
                    requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getSubNodeAndListurl, mRequestSubAndListEntity, this, requestName, LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
                }
                if (requestValue != null && !requestValue.equals("")) {
                    DocumentSubNodeAndList entity = JSONObject.parseObject(requestValue, DocumentSubNodeAndList.class);

                    if (null != entity && entity.code == 200 && null != entity.result) {
                        if (null != entity.result.fsDocList && entity.result.fsDocList.size() > 0) {
                            for (FsDocResultList data : entity.result.fsDocList)
                                documentSubNodeAndListEntity.add(data);
                            if (entity.result.fsDocList.size() < countPerPage)
                                isHasMore = false;
                            else
                                isHasMore = true;
                        } else {
                            isHasMore = false;
                        }
                    }
                }
                if (null != documentSubNodeAndListEntity && documentSubNodeAndListEntity.size() == 0) {
//                linearDoucmentEmpty.setVisibility(View.VISIBLE);
                    linearDoucmentEmpty.setEmptyButtonClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getRefreshData();
                        }
                    });
                    linearDoucmentEmpty.showEmpty();
                } else {
                    linearDoucmentEmpty.hide();
                    linearDeleteItem.setVisibility(View.GONE);
                }
                for (int i = 0; i < documentSubNodeAndListEntity.size(); i++) {
                    FsDocResultList doc = documentSubNodeAndListEntity.get(i);
                    if (doc.filename == null) {
                        continue;
                    }
                    String md5FileName = MD5Util.getMD5String(doc.filename) + "." + StringUtils.substringAfterLast(doc.filename, ".");
                    final File file = new File(CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + doc.fileId + File.separator + md5FileName);
                    if (file.exists()) {
                        documentSubNodeAndListEntity.get(i).isDownLoadFinish = true;
                    }
                }
                setDocumentNodeAndListData(documentSubNodeAndListEntity, isFirst);
            } else if (requestName.equals("getMoreData")) {
                if (null != documentSubNodeAndListEntity) {
                    documentSubNodeAndListEntity.removeAllElements();
                    documentSubNodeAndListEntity.clear();
                }
                isFirst = false;
                if (isSerach) {
                    requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getSerachurl, mSearchentity, this, requestName, LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
                } else {
                    requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getSubNodeAndListurl, mRequestSubAndListEntity, this, requestName, LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
                }
                if (requestValue != null && !requestValue.equals("")) {
                    DocumentSubNodeAndList entity = JSONObject.parseObject(requestValue, DocumentSubNodeAndList.class);
                    if (null != entity && entity.code == 200 && null != entity.result) {
                        if (null != entity.result.fsDocList && entity.result.fsDocList.size() > 0) {
                            for (FsDocResultList data : entity.result.fsDocList)
                                documentSubNodeAndListEntity.add(data);
                            if (entity.result.fsDocList.size() >= countPerPage)
                                isHasMore = true;
                            else
                                isHasMore = false;
                        } else {
                            isHasMore = false;
                        }
                    }
                }
                for (int i = 0; i < documentSubNodeAndListEntity.size(); i++) {
                    FsDocResultList doc = documentSubNodeAndListEntity.get(i);
                    if (doc.filename == null) {
                        continue;
                    }
                    String md5FileName = MD5Util.getMD5String(doc.filename) + "." + StringUtils.substringAfterLast(doc.filename, ".");
                    final File file = new File(CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + doc.fileId + File.separator + md5FileName);
                    if (file.exists()) {
                        documentSubNodeAndListEntity.get(i).isDownLoadFinish = true;
                    }
                }
                setDocumentNodeAndListData(documentSubNodeAndListEntity, isFirst);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            setDocumentNodeAndListData(documentSubNodeAndListEntity, isFirst);
            if (null != documentnodeListView)
                documentnodeListView.onRefreshComplete();
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals(LogManagerEnum.DOCUMENT_MAIN.getFunctionCode()) || requestName.equals(LogManagerEnum.DOCUMENT_SUB.getFunctionCode())) {
            if (isSerach) {
                //发起网络请求，获取所有当前用户能够看到的顶层资料库目录
//                String url = ServerUrlConstant.SERVER_EMPAPI_URL() +
//                        ServerUrlConstant.DOCUMENT_GETDOCUMENT_SERCH_METHOD;
                if (null != editText_keyword)
                    mSearchentity.name = editText_keyword.getText().toString();
                mSearchentity.nodeIds = nodeIds;
                mSearchentity.pageNum = String.valueOf(1);
                mSearchentity.pageSize = String.valueOf(countPerPage);
                mSearchentity.userId = OAConText.getInstance(DocumentAndNodeListActivity.this).UserID;
                AnsynHttpRequest.requestByPostWithToken(DocumentAndNodeListActivity.this, mSearchentity, getSerachurl, CHTTP.POSTWITHTOKEN, DocumentAndNodeListActivity.this, "getSubNodeAndList", LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
            } else {
                mRequestSubAndListEntity.groupCorpId = OAConText.getInstance(DocumentAndNodeListActivity.this).group_corp_id;
                mRequestSubAndListEntity.parentId = mCurrentDocNodeID;
                mRequestSubAndListEntity.pageNum = String.valueOf(1);
                mRequestSubAndListEntity.pageSize = String.valueOf(countPerPage);
                if (null != editText_keyword)
                    mRequestSubAndListEntity.name = editText_keyword.getText().toString();
                AnsynHttpRequest.requestByPostWithToken(DocumentAndNodeListActivity.this, mRequestSubAndListEntity, getSubNodeAndListurl, CHTTP.POSTWITHTOKEN, DocumentAndNodeListActivity.this, "getSubNodeAndList", LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
            }
        } else if (requestName.equals("getSubNodeAndList")) {
            Utils.toast(DocumentAndNodeListActivity.this, "获取资料数据失败：" + exceptionMessage, Toast.LENGTH_SHORT);
            netWorkManager.logFunactionFinsh(DocumentAndNodeListActivity.this, this, "documentListSuccess", LogManagerEnum.DOCUMENT_SUB.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
        } else if (requestName.equals("getMoreData")) {
            Utils.toast(DocumentAndNodeListActivity.this, "获取更多资料库数据失败：" + exceptionMessage, Toast.LENGTH_SHORT);
            netWorkManager.logFunactionFinsh(DocumentAndNodeListActivity.this, this, "documentNodeSuccess", LogManagerEnum.DOCUMENT_SUB.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
        }
        if (null != documentSubNodeAndListEntity && documentSubNodeAndListEntity.size() == 0) {
            linearDoucmentEmpty.setVisibility(View.VISIBLE);
        } else {
            linearDoucmentEmpty.setVisibility(View.GONE);
        }
        if (null != documentnodeListView)
            documentnodeListView.onRefreshComplete();
    }

    @Override
    public void notNetwork() {
        if (null != documentnodeListView)
            documentnodeListView.onRefreshComplete();
        if (!Network.checkNetWork(DocumentAndNodeListActivity.this)) {
            linearDoucmentEmpty.setNoWifiButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Network.checkNetWork(DocumentAndNodeListActivity.this)) {
                        linearDoucmentEmpty.hide();
                        linearDeleteItem.setVisibility(View.GONE);
                        //获取文档库子目录开启日志
                        netWorkManager.logFunactionStart(DocumentAndNodeListActivity.this, DocumentAndNodeListActivity.this, LogManagerEnum.DOCUMENT_SUB);
                    }
                }
            });
            linearDoucmentEmpty.showNowifi();
        }
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    private void setViewEnable(boolean enable) {
        editText_keyword.setEnabled(enable);
        editText_keyword.setFocusable(enable);

        if (enable) {
            editText_keyword.setFocusableInTouchMode(true);
            editText_keyword.requestFocus();
            editText_keyword.findFocus();
        }
    }

    @Override
    public void startDownload(final FsDocResultList doc) {
        final String md5FileName = MD5Util.getMD5String(doc.filename) + "." + StringUtils.substringAfterLast(doc.filename, ".");
        DownloadInfo downloadInfo = downloadInfoMap.get(doc.fileId + md5FileName);
        if (downloadInfo != null) {//暂停后重新开始
            final String filePath = (CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + doc.fileId + File.separator + md5FileName);
            final String filePath_temp = CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + ("tmp_" + md5FileName);
            DownloadManager downloadManager = DownloadService.getDownloadManager(HtmitechApplication.getInstance());
            try {
                downloadManager.resumeDownload(downloadInfo, new RequestCallBack<File>() {

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        // TODO Auto-generated method stub
                        final File file = responseInfo.result;
                        if (file.exists()) {
                            downloadInfoMap.remove(doc.fileId + md5FileName);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utils.toast(DocumentAndNodeListActivity.this, "文件" + file.getName() + "下载成功！", Toast.LENGTH_SHORT);
                                }
                            });
                            netWorkManager.logFunactionFinsh(DocumentAndNodeListActivity.this, DocumentAndNodeListActivity.this, "documentDownLoadSuccess", LogManagerEnum.DOCUMENT_DOWNLOAD.getFunctionCode(), "下载完成", INetWorkManager.State.SUCCESS);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        AESPartUtil.copyFile(filePath, filePath_temp);//复制一个临时文件
                                        AESPartUtil.decryptFile(filePath_temp);//将临时文件进行解密
                                        file_tmp = new File(filePath_temp);
                                        delectFileTemp.add(file_tmp);
                                        openFile(file_tmp);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        // TODO Auto-generated method stub
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.toast(DocumentAndNodeListActivity.this, "文件下载失败！", Toast.LENGTH_SHORT);
                                doc.isDownLoading = false;
                                doc.isDownLoadFinish = false;
                                documentAdapter.notifyDataSetChanged();
                            }
                        });
                        netWorkManager.logFunactionFinsh(DocumentAndNodeListActivity.this, DocumentAndNodeListActivity.this, "documentDownLoadFail", LogManagerEnum.DOCUMENT_DOWNLOAD.getFunctionCode(), "文件下载失败", INetWorkManager.State.FAIL);
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        doc.total = (int) total;
                        doc.current = (int) current;
                        if (total == current) {
                            doc.isDownLoadFinish = true;
                            doc.isDownLoading = false;
                        }
                        documentAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onCancelled() {
                    }
                });
            } catch (DbException e) {
                e.printStackTrace();
            }
        } else {//还没有下载 开始下载
            final File file = new File(CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + doc.fileId + File.separator + md5FileName);
            final String filePath = (CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + doc.fileId + File.separator + md5FileName);
            final String filePath_temp = CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER + File.separator + app_id + File.separator + ("tmp_" + md5FileName);
            if (file.exists()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!new File(filePath_temp).exists()) {//如果临时文件不存在的话，那么复制一个临时文件
                                AESPartUtil.copyFile(filePath, filePath_temp);//复制一个临时文件
                                AESPartUtil.decryptFile(filePath_temp);//将临时文件进行解密
                            }
                            file_tmp = new File(filePath_temp);
                            delectFileTemp.add(file_tmp);
                            openFile(file_tmp);
                            doc.isDownLoadFinish = true;
                            doc.isDownLoading = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            } else {
                //开始下载
                docToDownload = doc;
//                Dialog dialog = DialogFactory.creatDialog(DocumentAndNodeListActivity.this, R.string.prompt,
//                        R.string.download_warn);
//                DialogFactory.addDialogButtons(dialog, new int[]{
//                                Dialog.BUTTON_POSITIVE, Dialog.BUTTON_NEUTRAL}, new int[]{
//                                R.string.bname_sure, R.string.bname_cancel},
//
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int which) {
//                                switch (which) {
//                                    case Dialog.BUTTON_POSITIVE:// 确定下载
//                                        downLoadFile(docToDownload, filePath, filePath_temp);
//                                        break;
//                                    case Dialog.BUTTON_NEUTRAL:
//                                        dialog.dismiss();
//                                        break;
//                                    default:
//                                        break;
//                                }
//                            }
//                        });
//                dialog.show();
                downLoadFile(docToDownload, filePath, filePath_temp);
            }
        }
    }

    @Override
    public void stopDownload(FsDocResultList doc) {
        String md5FileName = MD5Util.getMD5String(doc.filename) + "." + StringUtils.substringAfterLast(doc.filename, ".");
        DownloadInfo downloadInfo = downloadInfoMap.get(doc.fileId + md5FileName);
        DownloadManager downloadManager = DownloadService.getDownloadManager(HtmitechApplication.getInstance());
        try {
            downloadManager.stopDownload(downloadInfo);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}