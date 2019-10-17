package com.htmitech.emportal.ui.daiban;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.commonx.pulltorefresh.library.ILoadingLayout;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.Mode;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.entity.Doc;
import com.htmitech.emportal.entity.DocSearchParameters;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.appcenter.model.task.AppCenterModel;
import com.htmitech.emportal.ui.applicationcenter.ApplicationDown;
import com.htmitech.emportal.ui.daiban.data.getdoclist.GetDocListEntity;
import com.htmitech.emportal.ui.daiban.model.task.GetDocListModel;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.emportal.ui.homepage.BinnerBitmapMessage;
import com.htmitech.emportal.ui.homepage.DocAdapter3;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.emportal.utils.BitmapLoader;
import com.htmitech.htcommonformplugin.activity.AddCenterSelectActivity;
import htmitech.com.componentlibrary.listener.ObserverCallBack;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.unit.ActivityUnit;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.util.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class DaiBanListFragment extends MyBaseFragment implements
        IBaseCallback, IBottomItemSelectCallBack, OnClickListener,
        IScrollListener, SearchView.OnQueryTextListener {
    private final static String TAG = "DaiBanListFragment";

    private SlidingDrawer mDrawer;
    private GridView mGridView;
    private GridViewAdapter mGridviewHandleAdapter;
    private ImageView mHandle;

    private PullToRefreshListView mPullToRefreshListView;
    private DocAdapter3 docAdapter;
    public ArrayList<AppInfo> mcurrentOcuList;
    /**
     * 是否已读
     */
    private boolean isHaveRead;
    /***
     * 列表实体对象
     */
    public static Vector<Doc> docListEntity = null;
    private Vector<Doc> vectorDoc;

    /***
     * 页码
     **/
    private int pageNum = 0;
    /***
     * 每页要读取的记录数量
     **/
    private int countPerPage = 15;

    private SimpleAdapter adapter;

    private static final int REFRESH_DATA = 1;
    private static final int PULLDOWNTOREGRESH = 2;


    private boolean isHasMore = true;
    private boolean flag = true;

    private boolean golink_flag;
    private SearchView sv_search;
    private RelativeLayout rv_serach;
    private ImageView iv_serach;
    private TextView tv_serach;
    private int i = 0;
    private EmptyLayout layout_search_no, el_layout;
    private ArrayList<BinnerBitmapMessage> currentOcuList = new ArrayList<BinnerBitmapMessage>();
    private boolean isSoso = false;
    public AppliationCenterDao appliationCenterDao;
    public String modelName = "";
    public ProxyDealApplicationPlugin mProxyDealApplication;


    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_daiban_oa_list;
    }

    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    @Override
    protected void initViews() {
        // TODO Auto-generated method stub
        Log.d("initViews", "initViews ------------------------------------>");
        mDrawer = (SlidingDrawer) findViewById(R.id.daiban_slidingDrawer_daiban_list);
        mHandle = (ImageView) findViewById(R.id.daiban_imageviewHandle_daiban_list);
        layout_search_no = (EmptyLayout) findViewById(R.id.layout_search_no);
        el_layout = (EmptyLayout) findViewById(R.id.el_layout);
        if (((DaiBanFragmentActivity) getActivity()).todoFlag == 0) {
            modelName = ((DaiBanFragmentActivity) getActivity()).modelName;
        }
        mHandle.setOnClickListener(this);
        mGridView = (GridView) findViewById(R.id.daiban_gridview_daibanlist);
        sv_search = (SearchView) findViewById(R.id.daiban_sv_search);
        rv_serach = (RelativeLayout) findViewById(R.id.daiban_rv_serach);
        iv_serach = (ImageView) findViewById(R.id.daiban_iv_serach);
        tv_serach = (TextView) findViewById(R.id.daiban_tv_serach);
        if (((DaiBanFragmentActivity) getActivity()).customerShortcuts == 0) {
            mGridView.setVisibility(View.VISIBLE);
//            EmptyLayout.LayoutParams layoutParams = new EmptyLayout.LayoutParams(EmptyLayout.LayoutParams.MATCH_PARENT,EmptyLayout.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(0, DensityUtil.dip2px(getActivity(),40),0,0);
//            mDrawer.setLayoutParams(layoutParams);
        } else if (((DaiBanFragmentActivity) getActivity()).customerShortcuts == 1) {
            mGridView.setVisibility(View.VISIBLE);
        }
        int search_mag_icon_id = sv_search.getContext().getResources()
                .getIdentifier("android:id/search_mag_icon", null, null);
        ImageView search_mag_icon = (ImageView) sv_search
                .findViewById(search_mag_icon_id);// 获取搜索图标
        search_mag_icon.setImageResource(R.drawable.mx_search_bar_icon_normal);// 图标都是用src的

        // 设置提示文字的颜色,这里走了点奇招,用Html类方法
        sv_search.setQueryHint(Html
                .fromHtml("<font color = #999999>" + "请输入标题关键字"
                        + "</font>"));

        int id = sv_search.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv_search.findViewById(id);
        textView.setTextSize(14);

        rv_serach.setVisibility(View.VISIBLE);
        if (!modelName.equals("")) {
            iv_serach.setVisibility(View.GONE);
            tv_serach.setVisibility(View.GONE);
            sv_search.setVisibility(View.VISIBLE);
            sv_search.setQuery(modelName, false);
        }
        rv_serach.setOnClickListener(new OnClickListener() {

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                i++;
                isSoso = true;
                iv_serach.setVisibility(View.GONE);
                tv_serach.setVisibility(View.GONE);
                sv_search.setVisibility(View.VISIBLE);
                sv_search.onActionViewExpanded();
                InputMethodManager inputManager =
                        (InputMethodManager) sv_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(sv_search, 0);
            }
        });
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {
                return true;
            }
        });

        if (sv_search != null) {
            try {        //--拿到字节码
                Class<?> argClass = sv_search.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(sv_search);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 为该SearchView组件设置事件监听器
        sv_search.setOnQueryTextListener(this);
        sv_search.setIconifiedByDefault(false);
//		mDrawer.animateOpen();
        mDrawer.close();
        mDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                // TODO Auto-generated method stub
                open(180, 0);
            }
        });

        mDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                // TODO Auto-generated method stub
                open(0, 180);
            }
        });

        /**
         * 点击按钮刷新
         */
//        el_layout.setErrorButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pullDownRefresh();
//                iv_serach.setVisibility(View.GONE);
//                tv_serach.setVisibility(View.GONE);
//                //获取所有当前用户已经自定义的快捷键列表
////                AppCenterModel getocuListModel = new AppCenterModel(DaiBanListFragment.this);
////                getocuListModel.getDataFromServerByType(AppCenterModel.TYPE_GET_CURRENTOCU_LIST,
////                        OAConText.getInstance(HtmitechApplication.getInstance()).UserID);
//
//            }
//        });

        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    final int position, long id) {
                // TODO Auto-generated method stub
                // 点击最后一个item，打开快捷键定义界面
                if (position == 0) {
//                    Intent intentAppSelect = new Intent(getActivity(),
//                            AppCenterSelectActivity.class);
//                    startActivityForResult(intentAppSelect, 0);
                    modelName = "";
                    DocSearchParameters docSearchParameters = new DocSearchParameters();
                    docSearchParameters.context = OAConText.getInstance(getActivity());
                    docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
                    docSearchParameters.RecordStartIndex = 0;
                    docSearchParameters.RecordEndIndex = countPerPage - 1;
                    docSearchParameters.ModelName = "";
                    docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
                    GetDocListModel getdocListModel = new GetDocListModel(DaiBanListFragment.this);
                    getdocListModel.getDataFromServerByType(
                            GetDocListModel.TYPE_GET_ZERO_LIST, docSearchParameters);
                    mDrawer.close();
                } else if (position == currentOcuList.size() - 1 && ((DaiBanFragmentActivity) getActivity()).customerShortcuts == 1) {//如果等于最后一个的话 那么将进入
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("app_id", ((DaiBanFragmentActivity) getActivity()).app_id);
                    map.put("todoFlag", "0");
                    ActivityUnit.switchTo(getActivity(), AddCenterSelectActivity.class, ActivityResultConstant.SAVEOCUS_RESULT_OK, map);
                } else {
                    BinnerBitmapMessage currentOcuInfo = ((GridViewAdapter) mGridView.getAdapter()).getCurrentOcuList().get(position);
                    if (currentOcuInfo.appInfo != null && currentOcuInfo.appInfo.getPlugin_code().equals("com_workflow_plugin_startor") || currentOcuInfo.appInfo.getPlugin_code().equals("com_workflow_plugin_H5_startor") || currentOcuInfo.appInfo.getPlugin_code().equals("com_workflow_plugin_opinions")) {     //h5插件
                        mProxyDealApplication = new ProxyDealApplicationPlugin(getActivity());
                        try {
                            int success = mProxyDealApplication.applicationCenterProxy(mcurrentOcuList.get(position - 1));//默认加了查询所有的插件
                            final AppInfo mAppInfo = mcurrentOcuList.get(position - 1);
                            switch (success) {
                                case 1: //强制升级
                                    new AlertDialog(getActivity()).builder().setTitle("下载").setMsg("应用名称：" + mAppInfo.getApp_name() + "\n" + "大小：" + mAppInfo.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ApplicationDown mApplicationDown = new ApplicationDown(getActivity(), view, mAppInfo, position);
                                            mApplicationDown.initView();
                                        }
                                    }).show();
                                    break;
                                case 2://可暂时不升级
                                    new AlertDialog(getActivity()).builder().setTitle("下载").setMsg("应用名称：" + mAppInfo.getApp_name() + "\n" + "大小：" + mAppInfo.getmAppVersion().getApp_filesize() ).setPositiveButton("下载", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ApplicationDown mApplicationDown = new ApplicationDown(getActivity(), view, mAppInfo, position);
                                        mApplicationDown.initView();
                                    }
                                }).setNegativeButton("暂不升级", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //暂不升级可直接进入
                                            try {
                                                mAppInfo.setIsUpdate(true);
                                                mProxyDealApplication.applicationCenterProxy(mAppInfo);
                                            } catch (NotApplicationException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).show();
                                    break;
                            }

                        } catch (NotApplicationException e) {
                            e.printStackTrace();
                        }


                    } else if (currentOcuInfo.appid != null && currentOcuInfo.Caption != null) {
                        modelName = currentOcuInfo.modleName;
                        //刷新列表
                        // 发起网络请求，获取所有待办列表
                        DocSearchParameters docSearchParameters = new DocSearchParameters();
                        docSearchParameters.context = OAConText.getInstance(getActivity());
                        docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
                        docSearchParameters.RecordStartIndex = 0;
                        docSearchParameters.RecordEndIndex = countPerPage - 1;
                        docSearchParameters.ModelName = currentOcuInfo.modleName;
                        docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
                        GetDocListModel getdocListModel = new GetDocListModel(DaiBanListFragment.this);
                        getdocListModel.getDataFromServerByType(
                                GetDocListModel.TYPE_GET_ZERO_LIST, docSearchParameters);
                        Log.d(TAG, "显示所有待办");
                        mDrawer.close();
//						if (!mDrawer.isOpened())
//							mDrawer.animateOpen();
                    } else {

                    }

                }

            }
        });

        // 待办列表
        mPullToRefreshListView = (PullToRefreshListView) mDrawer.getContent()
                .findViewById(R.id.listview_todo);
//		mPullToRefreshListView =  (PullToRefreshListView) findViewById(R.id.listview_todo);
        mPullToRefreshListView.setMode(Mode.BOTH);
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2() {

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

        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long id) {
                Doc doc = null;
                System.out.println("position:" + position);
                if (flag) {// item按键响应
                    if (null != docListEntity) {
                        doc = docListEntity.get(position - 1);
                        Log.i(TAG, "doc.getDocID():" + doc.getDocID());
                    }

                    Intent intent = new Intent();
                    intent.setClass(getActivity(),
                            isHaveRead == false ? DetailActivity.class
                                    : DetailActivity.class);
                    intent.putExtra("DocId", doc.getDocID());
                    intent.putExtra("DocType", doc.getDocType());
                    intent.putExtra("DocTitle", doc.getDocTitle());
                    intent.putExtra("Kind", doc.getKind()); //2015-08-11
                    intent.putExtra("TodoFlag", doc.getTodoFlag());
                    intent.putExtra("sendFrom", doc.getSendFrom());
                    intent.putExtra("sendDate", doc.getSendDate());
                    intent.putExtra("app_id", ((DaiBanFragmentActivity) getActivity()).app_id);
                    intent.putExtra("actionButtonStyle", ((DaiBanFragmentActivity) getActivity()).actionButtonStyle);
                    intent.putExtra("com_workflow_mobileconfig_IM_enabled", ((DaiBanFragmentActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
                    intent.putExtra("isShare", ((DaiBanFragmentActivity) getActivity()).isShare);
                    intent.putExtra("isTextUrl", ((DaiBanFragmentActivity) getActivity()).isTextUrl);
                    intent.putExtra("app_version_id", ((DaiBanFragmentActivity) getActivity()).app_version_id);
                    intent.putExtra("isWaterSecurity", ((DaiBanFragmentActivity) getActivity()).isWaterSecurity);
                    if (doc.getIconId() == null || "".equals(doc.getIconId())
                            || !(
                            doc.getIconId().endsWith(".png") || doc.getIconId().endsWith(".jpg")
                    )) {
                        intent.putExtra("IconId", "");
                    } else {
                        intent.putExtra("IconId", doc.getIconId());
                    }
                    startActivityForResult(intent, 0);
                }
            }
        });


        docAdapter = new DocAdapter3(getActivity(), isHaveRead);
        mPullToRefreshListView.setAdapter(docAdapter);

//		ListView mListView = mPullToRefreshListView.getRefreshableView();
//		mListView.setAdapter(docAdapter);


        ILoadingLayout startLabels = mPullToRefreshListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("释放开始刷新");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载");// 刷新时
        endLabels.setReleaseLabel("释放开始加载");// 下来达到一定距离时，显示的提示

        // 发起网络请求，获取所有待办列表
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.Title = modelName;
        docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
        docSearchParameters.RecordEndIndex = countPerPage - 1;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
        getdocListModel.getDataFromServerByType(
                GetDocListModel.TYPE_GET_ZERO_LIST, docSearchParameters);
        Log.d(TAG, "发起获取待办列表请求");
        initData();

    }

    public static int getItemtCount() {
        if (docListEntity != null)
            return docListEntity.size();
        return 0;
    }

    public void initData() {
        if (appliationCenterDao == null)
            appliationCenterDao = new AppliationCenterDao(getActivity());
        ArrayList<AppInfo> childApp = appliationCenterDao.getChildApp(((DaiBanFragmentActivity) getActivity()).app_id, true);//待办appid
        mcurrentOcuList = childApp;
        if (currentOcuList != null) {
            currentOcuList.clear();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mcurrentOcuList.size(); i++) {
                    AppInfo mAppInfo = mcurrentOcuList.get(i);
                    boolean flag = false;
                    if (mAppInfo.getPlugin_code().equals("com_workflow_plugin_H5_startor")) {
                        flag = true;
                    } else if (mAppInfo.getPlugin_code().equals("com_workflow_plugin_startor")) {
                        flag = true;
                    } else if (mAppInfo.getPlugin_code().equals("com_workflow_plugin_selector")) {
                        if (mAppInfo.getmAppVersion() != null) {
                            for (AppVersionConfig mAppVersionConfig : mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                                String cofingName = mAppVersionConfig.getConfig_code();
                                if (cofingName.equals("com_workflow_plugin_selector_paramter_TodoFlag")) {
                                    if (mAppVersionConfig.getConfig_value().equals("0")) {
                                        flag = true;
                                    }
                                }
                            }
                        }

                    } else if (mAppInfo.getPlugin_code().equals("com_workflow_plugin_opinions")) {
                        flag = true;
                    }
                    String modleName = "";
                    String todoFlag = "";
                    for (AppVersionConfig mAppVersionConfig : mcurrentOcuList.get(i).getmAppVersion().getAppVersionConfigArrayList()) {
                        String configValue = mAppVersionConfig.getConfig_value() == null ? "" : mAppVersionConfig.getConfig_value();
                        if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_ModelName")) {
                            modleName = configValue;
                        }
                        if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_TodoFlag")) {
                            todoFlag = configValue;
                        }
                    }

                    if (flag) {
                        try {
                            currentOcuList.add(new BinnerBitmapMessage(
                                    BitmapLoader.loadBitmap(mAppInfo.getPicture_normal()),
                                    mAppInfo.getApp_id() + "", mAppInfo.getApp_name(), "", mAppInfo.getApp_id(), mAppInfo, mAppInfo.getComp_code(), todoFlag, modleName));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }).start();

    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        Log.d(TAG, "返回获取待办列表请求");
        mPullToRefreshListView.onRefreshComplete();
        // TODO Auto-generated method stub
        if (requestTypeId == GetDocListModel.TYPE_GET_ZERO_LIST) { // 初次获取所有待办
            if (result != null) {
                GetDocListEntity entity = (GetDocListEntity) result;
//				Utils.toast(getActivity(), "取得了" + entity.getResult().length
//						+ "条待办信息", Toast.LENGTH_SHORT);
                Log.d(TAG, "取得了" + entity.getResult().length + "条待办信息");

                if (docListEntity == null) {
                    docListEntity = new Vector<Doc>();
                }
                docListEntity.removeAllElements();
                for (int i = 0; i < entity.getResult().length; i++) {
                    docListEntity.add(entity.getResult()[i]);
                }

                if (entity.getResult().length == 0) {
                    if (isSoso) {
                        el_layout.setShowEmptyButton(false);
                        el_layout.showEmpty();
                    } else {
                        el_layout.setEmptyButtonClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pullDownRefresh();
                            }
                        });
                        el_layout.setShowEmptyButton(true);
                        el_layout.showEmpty();
                        el_layout.setShowEmptyButton(true);
                    }

                } else {
                    el_layout.hide();
                }
            } else {
                docListEntity = new Vector<Doc>();
            }
            if (docListEntity.size() < countPerPage) {
                isHasMore = false;
            }
            setData();
            if (!mDrawer.isOpened())
                mDrawer.animateOpen();
        } else if (requestTypeId == GetDocListModel.TYPE_GET_MORE_LISTDATA) {
            if (result != null) {
                GetDocListEntity entity = (GetDocListEntity) result;
//				Utils.toast(getActivity(), "取得了更多" + entity.getResult().length
//						+ "条待办信息", Toast.LENGTH_SHORT);
                Log.d(TAG, "取得了更多：" + entity.getResult().length + "条待办信息");

                for (int i = 0; i < entity.getResult().length; i++) {
                    docListEntity.add(entity.getResult()[i]);
                }
                if (entity.getResult().length < countPerPage) {
                    isHasMore = false;
                }
                setData();
                if (!mDrawer.isOpened())
                    mDrawer.animateOpen();
            }
        } else if (requestTypeId == AppCenterModel.TYPE_GET_CURRENTOCU_LIST) { // 获取所有用户已经定义的快捷方式
//            addPort(requestTypeId,result);
        }
        //加载之后判断数据长度，确定是否有红点
        if (DaiBanListFragment.getItemtCount() > 0) {
            if (ClientTabActivity.todoTabItem != null) {
                ClientTabActivity.todoTabItem.showMarker();
            }
        } else {
            if (ClientTabActivity.todoTabItem != null) {
                ClientTabActivity.todoTabItem.hideMarker();
            }
        }
        el_layout.hide();
    }


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 0:
                    if (currentOcuList == null) {
                        currentOcuList = new ArrayList<BinnerBitmapMessage>();
                    }
                    mGridviewHandleAdapter = new GridViewAdapter(currentOcuList, "0", ((DaiBanFragmentActivity) getActivity()).app_id + "", ((DaiBanFragmentActivity) getActivity()).customerShortcuts, DaiBanListFragment.this.getActivity());
                    mGridView.setAdapter(mGridviewHandleAdapter);
                    mGridviewHandleAdapter.notifyDataSetChanged();
                    if (currentOcuList != null)
                        for (BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
                            if (mBinnerBitmapMessage.appid.contains("OA_Select")) {
//                                setAngleNumber(mBinnerBitmapMessage);
                            } else if (mBinnerBitmapMessage.appid.contains("OA_todo_")) {
//                                setAngleNumber(mBinnerBitmapMessage);
                            }
                        }
                    break;
                case 1:
                    mGridviewHandleAdapter = new GridViewAdapter(currentOcuList, "0", ((DaiBanFragmentActivity) getActivity()).app_id + "", ((DaiBanFragmentActivity) getActivity()).customerShortcuts, DaiBanListFragment.this.getActivity());

                    mGridView.setAdapter(mGridviewHandleAdapter);
                    mGridviewHandleAdapter.notifyDataSetChanged();
                    if (currentOcuList != null)
                        for (BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
                            if (mBinnerBitmapMessage.Caption.equals("全部") || (mBinnerBitmapMessage.appInfo != null && mBinnerBitmapMessage.appInfo.getPlugin_code().equals("com_workflow_plugin_selector"))) {
                                setAngleNumber(mBinnerBitmapMessage);
                            }

                        }
                    break;

            }
            super.handleMessage(msg);
        }

    };

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
        // TODO Auto-generated method stub
        Log.d(TAG, "返回获取待办列表请求：错误");
//        if (isSoso) {
//            el_layout.showError();
//        } else {

        if (!Utils.isNetworkAvailable()) {
            el_layout.setNoWifiButtonClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    pullDownRefresh();
                }
            });
            el_layout.showNowifi();
        } else {
            el_layout.setErrorButtonClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DaiBanListFragment.this.getActivity().finish();
                }
            });
            el_layout.showError();
        }
//        }

    }

    public void setData() {
        docAdapter.setData(true, docListEntity);
    }


    //修改
    public ArrayList<Doc> getData(Vector<Doc> docListEntity) {
        if (docListEntity == null) {
            return new ArrayList<Doc>();
        } else {
            return new ArrayList<Doc>(docListEntity);
        }
    }


    public void pullDownRefresh() {
        // 刷新
        pageNum = 0;
        getRefreshData(GetDocListModel.TYPE_GET_ZERO_LIST);
    }

    private void pullUpLoadMore() {
        if (!isHasMore) {
//            ClientTabActivity.todoTabItem.hideMarker();
            Utils.toast(getActivity(), "已经是最后一页了！", Toast.LENGTH_SHORT);
            mPullToRefreshListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    mPullToRefreshListView.onRefreshComplete();
                }
            }, 100);
            return;
        }
        pageNum = pageNum + 1;
        getMoreData(pageNum, GetDocListModel.TYPE_GET_MORE_LISTDATA);
        /*if (golink_flag == false) {
            // 数据全部显示出来时运行此处代码，如果要实现分页功能，在这里加载下一页的数据
			golink_flag = true;

		}*/
    }

    public void addElement(Doc[] docArr) {
        if (vectorDoc == null) {
            vectorDoc = new Vector<Doc>();
        }
        int length = docArr.length;
        for (int i = 0; i < length; i++) {
            if (!vectorDoc.contains(docArr[i]) && docArr[i] != null) {
                vectorDoc.addElement(docArr[i]);
            }
        }
        docListEntity = vectorDoc;
    }

    /**
     * 刷新列表
     **/
    public void getRefreshData(int interfaceId) {
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.TodoFlag = "0";
        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.RecordEndIndex = countPerPage - 1;
        docSearchParameters.ModelName = modelName;
        docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(interfaceId,
                docSearchParameters);
        Log.d(TAG, "发起获取待办列表请求:getRefreshData");
    }

    /****
     * 获取更多
     */
    public void getMoreData(int pageNum, int interfaceId) {
        String orderString = "";
        /*
         * AuthWS.getInstance().goLink(MenuItem_mobileReceiverListData.this,
		 * false, MenuItem_mobileReceiverListData.this, interfaceId,
		 * getActivity(), orderString, countPerPage * pageNum, countPerPage *
		 * (pageNum + 1) - 1, method);
		 */
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.TodoFlag = "0";
        docSearchParameters.RecordStartIndex = countPerPage * pageNum;
        docSearchParameters.RecordEndIndex = countPerPage * (pageNum + 1) - 1;
        docSearchParameters.ModelName = modelName;
        docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(interfaceId,
                docSearchParameters);
        Log.d(TAG, "发起获取待办列表请求:getMoreData");
    }

    public void open(int startAngle, int endAngle) {
        Interpolator interpolator = new OvershootInterpolator(1.2f);
        RotateAnimation rotateAnimation = new RotateAnimation(startAngle,
                endAngle, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setFillBefore(true);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setInterpolator(interpolator);
        /*mHandle.clearAnimation();
        mHandle.startAnimation(rotateAnimation);*/
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageviewHandle_daiban_list:
            /*if (!mDrawer.isOpened()) {
                mDrawer.animateOpen();
			} else {
				mDrawer.animateClose();
			}*/
                break;
            default:
                break;
        }
    }

    @Override
    public void onFragmentTabClick(int position) {
        // TODO Auto-generated method stub

    }

    public void onScroll(int scrollY) {

    }


    @Override
    public boolean onQueryTextChange(String arg0) {
        //输入框内容变化listener
        // TODO Auto-generated method stub
        if (arg0.toString().equals("") && i != 1) {
            DocSearchParameters docSearchParameters = new DocSearchParameters();
            docSearchParameters.context = OAConText.getInstance(getActivity());
            docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
            docSearchParameters.RecordStartIndex = 0;
            docSearchParameters.RecordEndIndex = countPerPage - 1;
            docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
            GetDocListModel getdocListModel = new GetDocListModel(this);
            getdocListModel.getDataFromServerByType(
                    GetDocListModel.TYPE_GET_ZERO_LIST, docSearchParameters);
        }
        i++;
        isSoso = false;
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        //开始搜索listener
        // TODO Auto-generated method stub
        // 发起网络请求，获取所有待办列表
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.Title = arg0.toString() + "";
        docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.RecordEndIndex = countPerPage - 1;
        docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(
                GetDocListModel.TYPE_GET_ZERO_LIST, docSearchParameters);
        sv_search.clearFocus();
        if (arg0.toString().equals("")) {
            isSoso = false;
        } else {
            isSoso = true;
        }

        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sv_search != null)
            sv_search.clearFocus();

    }


    public void setAngleNumber(final BinnerBitmapMessage mBinnerBitmapMessage) {
        String daiban_yiban_url = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.USER_DAIBAN_YIBAN_COUNT;
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
        docSearchParameters.Title = "";
        docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
        docSearchParameters.ModelName = mBinnerBitmapMessage.Caption.equals("全部") ? "" : mBinnerBitmapMessage.modleName + "";

        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.RecordEndIndex = 14;

        AnsynHttpRequest.requestByPost(getActivity(), docSearchParameters, daiban_yiban_url, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                Log.d("AnsynHttpRequest", successMessage);
                JSONObject mJSONObject = JSON.parseObject(successMessage);
                String Result = mJSONObject.getString("Result");
//                && !Result.equals("0")
                if (Result != null && !Result.equals("")) {
                    try {
                        int resultInteger = Integer.parseInt(Result);
                        if (resultInteger != 0)
                            mBinnerBitmapMessage.numberFlag = View.VISIBLE;
                        else
                            mBinnerBitmapMessage.numberFlag = View.GONE;
                        mBinnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
                        mBinnerBitmapMessage.angle_nulber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
                        mBinnerBitmapMessage.number = resultInteger > 99 ? "99+" : resultInteger + "";
                        if (resultInteger > 99) {
                            RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(getActivity(), 30), DeviceUtils.dip2px(getActivity(), 15));
                            mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
                        }
                        mGridviewHandleAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (mBinnerBitmapMessage != null && mBinnerBitmapMessage.numberText != null) {
                        mBinnerBitmapMessage.numberText.setVisibility(View.GONE);
                        mBinnerBitmapMessage.numberText.setText("");
                    }

                }

            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stubl
                el_layout.setNoWifiButtonClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pullDownRefresh();
                    }
                });
                el_layout.showNowifi();
            }

            @Override
            public void callbackMainUI(String successMessage) {
                Log.d("AnsynHttpRequest", "callbackMainUI");
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub

            }
        });


    }
}
