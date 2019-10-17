package com.htmitech.htworkflowformpluginnew.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.Doc;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.applicationcenter.ApplicationDown;
import com.htmitech.emportal.ui.daiban.GridViewAdapter;
import com.htmitech.emportal.ui.daiban.IScrollListener;
import com.htmitech.emportal.ui.homepage.BinnerBitmapMessage;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.htworkflowformpluginnew.activity.InitWorkFlowFormActivity;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowAddCenterSelectActivity;
import com.htmitech.htworkflowformpluginnew.entity.DocSearchParameters;
import com.htmitech.htworkflowformpluginnew.util.AngleWorkFlowUntil;
import com.htmitech.htworkflowformpluginnew.util.WorkFlowCountHttpUtil;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.interfaces.CallBackOpen;
import com.htmitech.unit.ActivityUnit;
import com.htmitech.unit.DensityUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import htmitech.com.componentlibrary.base.MyBaseFragment;

/***
 * 代办已办带快捷键展示页面
 *
 * @author joe
 * @date 2017-6-27 11:08:09
 */
public class WorkFlowFormTodoListFragment extends MyBaseFragment implements IBottomItemSelectCallBack, OnClickListener,
        IScrollListener, SearchView.OnQueryTextListener, CallBackOpen {
    private final static String TAG = "TodoListFragment";
    private SlidingDrawer mDrawer;
    private GridView mGridView;
    private GridViewAdapter mGridviewHandleAdapter;
    private ImageView mHandle;
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
    private int countPerPage = 10;
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
    private ArrayList<BinnerBitmapMessage> currentOcuList = new ArrayList<BinnerBitmapMessage>();
    private boolean isSoso = false;
    public AppliationCenterDao appliationCenterDao;
    public String modelName = "";
    public ProxyDealApplicationPlugin mProxyDealApplication;
    private WorkFlowHaveDoneListFragment mWorkFlowHaveDoneListFragment;
    private String todoFlag;
    private int actionButtonStyle; //表单详情中的操作按钮样式
    private int customerShortcuts;  //是否支持快捷键自定义
    private int com_workflow_mobileconfig_IM_enabled;//表单详情和历史操作中，是否支持点击人员跳转聊天的功能。
    private int isFavValue;//是否支持我的关注
    private int isStartValue;//是否支持我的发起
    private int isWaterSecurity;//是不是支持水印
    private int isShare;//是否支持表单分享功能。
    private String app_id;
    private Context context;
    private boolean isHome = false;
    private int com_workflow_mobileconfig_include_options;//是否显示意见审批里面的常用意见

    //新增参数
    private int hasdoneShortcuts; //已办面板中是否支持快捷键自定义
    private int hasdoneShortcutsview; //是否需要在已办中显示“筛选面板”
    private int todoShortcuts; //待办面板中是否支持快捷键自定义
    private int todoShortcutsview; //是否需要在待办中显示“筛选面板”
    public int isTextUrl;//是否正文展示HTML
    private int com_workflow_mobileconfig_tabbutton_style;//表示所有代办 还是所有已办 还是待阅已阅
    private LinearLayout ll_Handle_daiban_list;
    private int numberIndex = 0;
    private String com_workflow_plugin_selector_parmater_Importance;//待阅已阅
    private String com_workflow_mobileconfig_others;
    public int checkIndex = 0;
    private int com_workflow_mobileconfig_opinion_style;

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_daiban_list;
    }

    @SuppressWarnings({"deprecation", "rawtypes", "uncheckedget"})
    @Override
    protected void initViews() {
        // TODO Auto-generated method stub
        Log.d("initViews", "initViews ------------------------------------>");

        Bundle mBunlde = getArguments();
        todoFlag = mBunlde.getString("com_workflow_plugin_selector_paramter_TodoFlag");
        actionButtonStyle = mBunlde.getInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
        customerShortcuts = mBunlde.getInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
        com_workflow_mobileconfig_IM_enabled = mBunlde.getInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
        isFavValue = mBunlde.getInt("com_workflow_mobileconfig_include_myfav", isFavValue);
        com_workflow_mobileconfig_opinion_style = mBunlde.getInt("com_workflow_mobileconfig_opinion_style", 0);
        isStartValue = mBunlde.getInt("com_workflow_mobileconfig_include_mystart", isStartValue);
        isWaterSecurity = mBunlde.getInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
        isShare = mBunlde.getInt("com_workflow_mobileconfig_include_share", isShare);
        isTextUrl = mBunlde.getInt("com_workflow_mobileconfig_docview_style", isTextUrl);
        hasdoneShortcuts = mBunlde.getInt("com_workflow_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
        hasdoneShortcutsview = mBunlde.getInt("com_workflow_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
        todoShortcuts = mBunlde.getInt("com_workflow_mobileconfig_customer_todo_shortcuts", todoShortcuts);
        todoShortcutsview = mBunlde.getInt("com_workflow_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
        com_workflow_mobileconfig_include_options = mBunlde.getInt("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
        com_workflow_mobileconfig_tabbutton_style = mBunlde.getInt("com_workflow_mobileconfig_tabbutton_style", com_workflow_mobileconfig_tabbutton_style);
        com_workflow_plugin_selector_parmater_Importance = mBunlde.getString("com_workflow_mobileconfig_importance_workas_toreadflag");
        com_workflow_mobileconfig_others = mBunlde.getString("com_workflow_mobileconfig_others");

        isHome = getActivity().getIntent().getBooleanExtra("isHome", false);
        mDrawer = (SlidingDrawer) findViewById(R.id.daiban_slidingDrawer_daiban_list);
        ll_Handle_daiban_list = (LinearLayout) findViewById(R.id.ll_Handle_daiban_list);
        mHandle = (ImageView) findViewById(R.id.daiban_imageviewHandle_daiban_list);
        if (((InitWorkFlowFormActivity) getActivity()).todoFlag == 0) {
            modelName = ((InitWorkFlowFormActivity) getActivity()).modelName;
        }
        app_id = ((InitWorkFlowFormActivity) getActivity()).app_id;
        mHandle.setOnClickListener(this);
        mGridView = (GridView) findViewById(R.id.daiban_gridview_daibanlist);
        sv_search = (SearchView) findViewById(R.id.daiban_sv_search);
        rv_serach = (RelativeLayout) findViewById(R.id.daiban_rv_serach);
        iv_serach = (ImageView) findViewById(R.id.daiban_iv_serach);
        tv_serach = (TextView) findViewById(R.id.daiban_tv_serach);
        if (todoFlag.trim().equals("1")) {
            if (hasdoneShortcutsview == 1 || hasdoneShortcutsview == 2) {
                mGridView.setVisibility(View.VISIBLE);
                mHandle.setVisibility(View.VISIBLE);
                ll_Handle_daiban_list.setVisibility(View.VISIBLE);
            } else {
                mGridView.setVisibility(View.GONE);
                mHandle.setVisibility(View.GONE);
                ll_Handle_daiban_list.setVisibility(View.GONE);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, DensityUtil.dip2px(getActivity(), 40), 0, 0);
                mDrawer.setLayoutParams(layoutParams);
            }
        } else {
            if (todoShortcutsview == 1 || todoShortcutsview == 2) {
                mGridView.setVisibility(View.VISIBLE);
                mHandle.setVisibility(View.VISIBLE);
                ll_Handle_daiban_list.setVisibility(View.VISIBLE);
            } else {
                mGridView.setVisibility(View.GONE);
                mHandle.setVisibility(View.GONE);
                ll_Handle_daiban_list.setVisibility(View.GONE);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, DensityUtil.dip2px(getActivity(), 40), 0, 0);
                mDrawer.setLayoutParams(layoutParams);
            }
        }
        int search_mag_icon_id = sv_search.getContext().getResources()
                .getIdentifier("android:id/search_mag_icon", null, null);
        ImageView search_mag_icon = (ImageView) sv_search
                .findViewById(search_mag_icon_id);// 获取搜索图标
        search_mag_icon.setImageResource(R.drawable.mx_search_bar_icon_normal);// 图标都是用src的
        // 设置提示文字的颜色,这里走了点奇招,用Html类方法
        sv_search.setQueryHint(Html
                .fromHtml("<font color = #999999>" + "请输入标题关键字搜索"
                        + "</font>"));
        int id = sv_search.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv_search.findViewById(id);
//        textView.setTextSize(DensityUtil.sp2px(HtmitechApplication.instance(), 11));
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
        mDrawer.animateOpen();
//        mDrawer.close();
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

        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    final int position, long id) {
                // TODO Auto-generated method stub
                // 点击最后一个item，打开快捷键定义界面
                if (position == 0) {
                    checkIndex = position;
                    for (int i = 0; i < parent.getCount(); i++) {
                        View item = mGridView.getChildAt(i).findViewById(R.id.edit_ll);
                        if (position == i) {//当前选中的Item改变背景颜色
                            item.setBackgroundResource(R.color.huise);
                        } else {
                            item.setBackgroundResource(R.color.white);
                        }
                    }
                    mWorkFlowHaveDoneListFragment.searchQueryAll(com_workflow_mobileconfig_others);
                    mDrawer.close();
                } else if (position == currentOcuList.size() - 1 && (hasdoneShortcuts == 1 || todoShortcuts == 1)) {//如果等于最后一个的话 那么将进入
                    if (todoFlag.trim().equals("1")) {
                        if (hasdoneShortcuts == 1) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("app_id", app_id);
                            map.put("todoFlag", todoFlag);
                            ActivityUnit.switchTo(getActivity(), WorkFlowAddCenterSelectActivity.class, ActivityResultConstant.SAVEOCUS_RESULT_OK, map);
                        }
                    } else {
                        if (todoShortcuts == 1) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("app_id", app_id);
                            map.put("todoFlag", todoFlag);
                            ActivityUnit.switchTo(getActivity(), WorkFlowAddCenterSelectActivity.class, ActivityResultConstant.SAVEOCUS_RESULT_OK, map);
                        }
                    }
                } else {
                    BinnerBitmapMessage currentOcuInfo = ((GridViewAdapter) mGridView.getAdapter()).getCurrentOcuList().get(position);
                    if (currentOcuInfo.appInfo.getPlugin_code().equals("com_workflow_plugin_startor") || currentOcuInfo.appInfo.getPlugin_code().equals("com_workflow_plugin_H5_startor") || currentOcuInfo.appInfo.getPlugin_code().equals("com_workflow_plugin_opinions")) {     //h5插件
                        mProxyDealApplication = new ProxyDealApplicationPlugin(getActivity());
                        try {
                            int success = mProxyDealApplication.applicationCenterProxy(currentOcuInfo.appInfo);//默认加了查询所有的插件
                            final AppInfo mAppInfo = currentOcuInfo.appInfo;
                            switch (success) {
                                case 1: //强制升级
                                    new AlertDialog(getActivity()).builder().setTitle("下载").setMsg("应用名称：" + mAppInfo.getApp_name() + "\n" + "大小：" + mAppInfo.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ApplicationDown mApplicationDown = new ApplicationDown(getActivity(), view, mAppInfo, position);
                                            mApplicationDown.initView();
                                        }
                                    }).show();
                                    break;
                                case 2://可暂时不升级
                                    new AlertDialog(getActivity()).builder().setTitle("下载").setMsg("应用名称：" + mAppInfo.getApp_name() + "\n" + "大小：" + mAppInfo.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ApplicationDown mApplicationDown = new ApplicationDown(getActivity(), view, mAppInfo, position);
                                            mApplicationDown.initView();
                                        }
                                    }).setNegativeButton("暂不升级", new OnClickListener() {
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
//                        //刷新列表
                        mWorkFlowHaveDoneListFragment.searchQuery(currentOcuInfo.appInfo, com_workflow_mobileconfig_others);
                        mDrawer.close();
                        checkIndex = position;

                        for (int i = 0; i < parent.getCount(); i++) {
                            View item = mGridView.getChildAt(i).findViewById(R.id.edit_ll);
                            if (position == i) {//当前选中的Item改变背景颜色
                                item.setBackgroundResource(R.color.huise);
                            } else {
                                item.setBackgroundResource(R.color.white);
                            }
                        }
                    } else {

                    }



                }

            }
        });

        initData(app_id, getActivity());
    }

    public void initWorkFlowHaveDone(DocSearchParameters mDocSearchParameters) {
        mWorkFlowHaveDoneListFragment = new WorkFlowHaveDoneListFragment();
        mWorkFlowHaveDoneListFragment.setCallBackOpen(this);
        Bundle mBundle = new Bundle();
        mBundle.putString("com_workflow_plugin_selector_paramter_TodoFlag", todoFlag);
        mBundle.putBoolean("hideSearch", true);
        mBundle.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
        mBundle.putInt("com_workflowm_mobileconfig_customer_shortcuts", customerShortcuts);
        mBundle.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
        mBundle.putInt("com_workflow_mobileconfig_opinion_style", com_workflow_mobileconfig_opinion_style);
        mBundle.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
        mBundle.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
        mBundle.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
        mBundle.putInt("com_workflow_mobileconfig_include_share", isShare);
        mBundle.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
        mBundle.putString("com_workflow_plugin_selector_paramter_ModelName", modelName);
        mBundle.putInt("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
        mBundle.putString("com_workflow_mobileconfig_importance_workas_toreadflag", com_workflow_plugin_selector_parmater_Importance);
        mBundle.putSerializable("mDocSearchParameters", mDocSearchParameters);
        mWorkFlowHaveDoneListFragment.hideSearch(true);
        mWorkFlowHaveDoneListFragment.setArguments(mBundle);
        FragmentTransaction transaction_task = getChildFragmentManager()
                .beginTransaction();
//        transaction_task.add(R.id.ll_general_from_child, mHaveDoneListFragment);

        transaction_task.replace(R.id.ll_general_from_child, mWorkFlowHaveDoneListFragment);
//        //显示需要显示的fragment
//        transaction_task.show(mHaveDoneListFragment);
        transaction_task.commit();
//        initData(app_id, getActivity());
    }


    public static int getItemtCount() {
        if (docListEntity != null)
            return docListEntity.size();
        return 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void initData(String app_id, Context context) {
        this.context = context;
        this.app_id = app_id;
        currentOcuList.clear();
        if (appliationCenterDao == null)
            appliationCenterDao = new AppliationCenterDao(getActivity());
        ArrayList<AppInfo> childApp = appliationCenterDao.getChildApp(app_id, true);//待办appid
        mcurrentOcuList = childApp;
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
                                    if (mAppVersionConfig.getConfig_value().equals("") || mAppVersionConfig.getConfig_value().equals(todoFlag)) {
                                        flag = true;
                                    }
                                }
                            }
                        }

                    } else if (mAppInfo.getPlugin_code().equals("com_workflow_plugin_opinions")) {
                        flag = true;
                    }
                    if (flag) {
                        try {
                            currentOcuList.add(new BinnerBitmapMessage(null,
                                    mAppInfo.getApp_id() + "", mAppInfo.getApp_shortnames(), "", mAppInfo.getApp_id(), mAppInfo, mAppInfo.getComp_code()));
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


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 0:
//                    if (currentOcuList == null) {
//                        currentOcuList = new ArrayList<BinnerBitmapMessage>();
//                    }
//                    mGridviewHandleAdapter = new GridViewAdapter(currentOcuList, todoFlag, app_id, customerShortcuts, WorkFlowFormTodoListFragment.this.getActivity() == null ? context : WorkFlowFormTodoListFragment.this.getActivity(), hasdoneShortcuts, todoShortcuts);
//                    mGridView.setAdapter(mGridviewHandleAdapter);
//                    mGridviewHandleAdapter.notifyDataSetChanged();
////                    if (currentOcuList != null)
////                        for (BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
////                            if (mBinnerBitmapMessage.compCode.contains("com_commonform")) {
////                                setAngleNumber(mBinnerBitmapMessage);
////                            } else if (mBinnerBitmapMessage.compCode.contains("com_workflow") || mBinnerBitmapMessage.compCode.contains("todo_flag")) {
//////                                AngleUntil.setAngleNumberGeneralForm(mBinnerBitmapMessage, getActivity(), mGridviewHandleAdapter, true);
////                                setAngleNumber(mBinnerBitmapMessage);
////                            }
////                        }
//                    if (currentOcuList != null)
//                        for (final BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
//                            if (mBinnerBitmapMessage.Caption.equals("全部") || (mBinnerBitmapMessage.appInfo != null && mBinnerBitmapMessage.appInfo.getPlugin_code().equals("com_workflow_plugin_selector"))) {
//                                new Handler().postDelayed(new Runnable()
//                                {
//                                    public void run()
//                                    {
//                                        setAngleNumber(mBinnerBitmapMessage);
//                                    }
//                                }, 500);
//
//                            }
//
//                        }
//                    break;
                case 1:
                    if (currentOcuList == null) {
                        currentOcuList = new ArrayList<BinnerBitmapMessage>();
                    }
                    if (com_workflow_mobileconfig_tabbutton_style == 2 || com_workflow_mobileconfig_tabbutton_style == 5 || com_workflow_mobileconfig_tabbutton_style == 6) {
                        todoFlag = com_workflow_plugin_selector_parmater_Importance;
                    }

                    mGridviewHandleAdapter = new GridViewAdapter(currentOcuList, todoFlag, app_id, customerShortcuts, WorkFlowFormTodoListFragment.this.getActivity() == null ? context : WorkFlowFormTodoListFragment.this.getActivity(), hasdoneShortcuts, todoShortcuts, hasdoneShortcutsview, todoShortcutsview);
                    mGridviewHandleAdapter.setCheckIndex(checkIndex);
                    mGridviewHandleAdapter.setCom_workflow_mobileconfig_tabbutton_style(com_workflow_mobileconfig_tabbutton_style);
                    mGridView.setAdapter(mGridviewHandleAdapter);
                    mGridviewHandleAdapter.notifyDataSetChanged();
//                    if (currentOcuList != null)
//                        for (BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
//                            if ((mBinnerBitmapMessage.appInfo != null && mBinnerBitmapMessage.appInfo.getPlugin_code().equals("com_commonform_plugin_selector"))) {
//                                setAngleNumber(mBinnerBitmapMessage);
//                            } else if (mBinnerBitmapMessage.compCode.contains("com_workflow") || mBinnerBitmapMessage.compCode.contains("todo_flag")) {
////                                AngleUntil.setAngleNumberGeneralForm(mBinnerBitmapMessage, WorkFlowFormTodoListFragment.this.getActivity() == null ? context : WorkFlowFormTodoListFragment.this.getActivity(), mGridviewHandleAdapter, true);
//                                setAngleNumber(mBinnerBitmapMessage);
//                            }
//
                    if (currentOcuList != null)
                        for (BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
                            if (mBinnerBitmapMessage.Caption.equals("全部") || (mBinnerBitmapMessage.appInfo != null && mBinnerBitmapMessage.appInfo.getPlugin_code().equals("com_workflow_plugin_selector"))) {
                                mBinnerBitmapMessage.setmBaseAdapter(mGridviewHandleAdapter);
                                setAngleNumber(mBinnerBitmapMessage);
                            }

                        }

                    DocSearchParameters mDocSearchParameters = null;
                    if (currentOcuList != null && currentOcuList.size() > 0) {
                        if (!currentOcuList.get(0).getAppInfo().equals("全部")) {
                            mDocSearchParameters = AngleWorkFlowUntil.getSearchcondition(getActivity(), currentOcuList.get(0).getAppInfo());
                            if (com_workflow_mobileconfig_tabbutton_style == 2 || com_workflow_mobileconfig_tabbutton_style == 5 || com_workflow_mobileconfig_tabbutton_style == 6) {
                                mDocSearchParameters.todoFlag = "";
                                mDocSearchParameters.importance = com_workflow_plugin_selector_parmater_Importance;
                            }
                        }

                    }
                    initWorkFlowHaveDone(mDocSearchParameters);
                    break;

            }
            super.handleMessage(msg);
        }

    };


    public void open(int startAngle, int endAngle) {
        Interpolator interpolator = new OvershootInterpolator(1.2f);
        RotateAnimation rotateAnimation = new RotateAnimation(startAngle, endAngle, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
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
        if (mWorkFlowHaveDoneListFragment != null && arg0.toString().equals("") && i != 1) {
            mWorkFlowHaveDoneListFragment.searchQuery(com_workflow_mobileconfig_others,arg0);
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
        mWorkFlowHaveDoneListFragment.searchQuery(com_workflow_mobileconfig_others,arg0);
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
        if (getActivity() != null) {
            if (currentOcuList != null && mGridviewHandleAdapter != null && mGridView != null) {
                mGridviewHandleAdapter.setCheckIndex(checkIndex);
                mGridviewHandleAdapter.notifyDataSetChanged();
                for (BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
                    if (mBinnerBitmapMessage.Caption.equals("全部") || (mBinnerBitmapMessage.appInfo != null && mBinnerBitmapMessage.appInfo.getPlugin_code().equals("com_workflow_plugin_selector"))) {
                        mBinnerBitmapMessage.setmBaseAdapter(mGridviewHandleAdapter);
                        setAngleNumber(mBinnerBitmapMessage);
                    }

                }
            }
        }
        if (sv_search != null)
            sv_search.clearFocus();
    }


    public void setAngleNumber(final BinnerBitmapMessage mBinnerBitmapMessage) {
        final DocSearchParameters docSearchParameters = new DocSearchParameters();
//        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.userId = OAConText.getInstance(getActivity()).UserID;
        docSearchParameters.appId = ((InitWorkFlowFormActivity) getActivity()).app_id;
//        docSearchParameters.title = "";
        if (com_workflow_mobileconfig_tabbutton_style == 2 || 6 == com_workflow_mobileconfig_tabbutton_style || 5 == com_workflow_mobileconfig_tabbutton_style) {
            docSearchParameters.importance = com_workflow_plugin_selector_parmater_Importance;
        } else {
            docSearchParameters.todoFlag = todoFlag; // 0，待办；1，已办
        }
        docSearchParameters.modelName = mBinnerBitmapMessage.Caption.equals("全部") ? "" : mBinnerBitmapMessage.Caption + "";
        WorkFlowCountHttpUtil mWorkFlowCountHttpUtil = new WorkFlowCountHttpUtil();
        mWorkFlowCountHttpUtil.isTypeNatvie = true;
        mWorkFlowCountHttpUtil.ShowNumber(mBinnerBitmapMessage, context, docSearchParameters);

    }

    public WorkFlowHaveDoneListFragment getmHaveDoneListFragment() {
        return mWorkFlowHaveDoneListFragment;
    }


    @Override
    public void callBackOpen() {
        if (!mDrawer.isOpened())
            mDrawer.animateOpen();
    }

}
