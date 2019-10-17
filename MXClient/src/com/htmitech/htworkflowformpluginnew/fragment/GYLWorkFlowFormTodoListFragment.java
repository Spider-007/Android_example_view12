package com.htmitech.htworkflowformpluginnew.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.htmitech.app.Constant;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.applicationcenter.ApplicationDown;
import com.htmitech.emportal.ui.daiban.GridViewAdapter;
import com.htmitech.emportal.ui.daiban.IScrollListener;
import com.htmitech.emportal.ui.homepage.BinnerBitmapMessage;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.emportal.ui.widget.NewTopTabIndicator;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.htworkflowformpluginnew.activity.InitWorkFlowFormActivity;
import com.htmitech.htworkflowformpluginnew.activity.WebSearchActivity;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowAddCenterSelectActivity;
import com.htmitech.htworkflowformpluginnew.entity.DZDocGroupListResult;
import com.htmitech.htworkflowformpluginnew.entity.DoActionParameter;
import com.htmitech.htworkflowformpluginnew.entity.DoActionResultInfo;
import com.htmitech.htworkflowformpluginnew.entity.Doc;
import com.htmitech.htworkflowformpluginnew.entity.DocInfoParameters;
import com.htmitech.htworkflowformpluginnew.entity.DocSearchParameters;
import com.htmitech.htworkflowformpluginnew.entity.FileUpload;
import com.htmitech.htworkflowformpluginnew.entity.GroupListResult;
import com.htmitech.htworkflowformpluginnew.entity.Others;
import com.htmitech.htworkflowformpluginnew.entity.SelectRouteInfo;
import com.htmitech.htworkflowformpluginnew.listener.IWorkFlowCheckCountBack;
import com.htmitech.htworkflowformpluginnew.listener.IWorkFlowScrollBack;
import com.htmitech.htworkflowformpluginnew.util.AngleWorkFlowUntil;
import com.htmitech.htworkflowformpluginnew.util.AnimatorUtil;
import com.htmitech.htworkflowformpluginnew.util.WorkFlowCountHttpUtil;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.pop.ActionSheetDialog;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.activity.WebPluginActivity;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.doman.ReportSosoResult;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.fragment.StatiscalReportFragment;
import com.htmitech.proxy.interfaces.CallBackDamageTypeGridListener;
import com.htmitech.proxy.interfaces.CallBackOpen;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.HTActivityUnit;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.unit.ActivityUnit;
import com.htmitech.unit.DensityUtil;
import com.minxing.client.util.FastJsonUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import htmitech.com.componentlibrary.base.MyBaseFragment;
import htmitech.com.componentlibrary.entity.ActionInfo;
import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/***
 * 代办已办带快捷键展示页面
 *
 * @author joe
 * @date 2017-6-27 11:08:09
 */
public class GYLWorkFlowFormTodoListFragment extends MyBaseFragment implements Animator.AnimatorListener, IWorkFlowCheckCountBack, ObserverCallBackType, IBottomItemSelectCallBack, OnClickListener,
        IScrollListener, CallBackDamageTypeGridListener, SearchView.OnQueryTextListener, CallBackOpen {
    private final static String TAG = "TodoListFragment";
    private SlidingDrawer mDrawer;
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
    private LinearLayout llSerach;
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
    private String com_workflow_mobileconfig_other_conditions;
    private String com_workflow_mobileconfig_others;
    private String tempOther;
    public int checkIndex = 0;
    private NewTopTabIndicator mMyTopTabIndicator;
    private ImageView ivDirection;
    private LinearLayout ll_content_soso;
    private Animation upAnimation, downAnimation;
    private GYLStatiscalReportFragment mDamageTypeFragment;
    private TextView tv_buttom;
    private ArrayList<ReportSosoResult> mReports;
    private LinearLayout ll_ryb_bom;
    private TextView tvCheck;
    private TextView tv_total;
    private TextView tvApprovalNumber;
    private LinkedHashMap<String, String> hashMapCheck;
    private INetWorkManager netWorkManager;
    public static final String GET_DOCINFO = "getDocInfo";
    public static final String GET_DOCINFO_HANDLE = "getDocInfoHandle";
    public static final String GETDOCGROUP_LIST = "getDocGroupList";
    private DocInfoParameters mDocInfoParameters;
    private LinearLayout llToptab;
    public String getDocInfoPath = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_GETDOCINFO_METHOD_JAVA;

    public String getDocInfoHandles = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_GETDOCINFO_HANDLE_DOC;//批量审批
    private int countPerPage = 499;

    //    public String getDocGroupList = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.DZ_GETDOCGROUP_LIST;//定制开发 获取插件
    public String getDocGroupList = ServerUrlConstant.SERVER_DZ_URL()+"/custom/getDocGroupList";//定制开发 获取插件

    private DoActionParameter mDoActionParameter;

    private BinnerBitmapMessage currentOcuInfo;
    private boolean isGYL;
    private AnimatorUtil mAnimatorUtil;
    private RelativeLayout ll_top_soso;
    private RelativeLayout rl_button_list;
    private boolean isDown = false;
    private boolean isUp = false;

    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;
    private float lastX = 0;
    private float lastY = 0;
    private LinearLayout ll_parent;
    private InitWorkFlowFormActivity.MyOnTouchListener myOnTouchListener;
    private String typeCode;
    private GroupListResult mGroupListResult;
    private DocSearchParameters mDocSearchParameters;
    private String appName;
    private LinearLayout ll_center;
    private boolean IS_GYL;
    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_daiban_gyl_list;
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
        com_workflow_mobileconfig_other_conditions = mBunlde.getString("com_workflow_mobileconfig_other_conditions");
        IS_GYL = mBunlde.getBoolean("IS_GYL",false);
        appName = mBunlde.getString("appShortName");
        typeCode = mBunlde.getString("typeCode");
        tempOther = com_workflow_mobileconfig_others;
        isHome = getActivity().getIntent().getBooleanExtra("isHome", false);
        mDrawer = (SlidingDrawer) findViewById(R.id.daiban_slidingDrawer_daiban_list);
        ll_Handle_daiban_list = (LinearLayout) findViewById(R.id.ll_Handle_daiban_list);
        mMyTopTabIndicator = (NewTopTabIndicator) this.findViewById(R.id.topTabIndicator_detail_gyl);
        mHandle = (ImageView) findViewById(R.id.daiban_imageviewHandle_daiban_list);
        ll_content_soso = (LinearLayout) findViewById(R.id.ll_content_soso);
        ll_parent = (LinearLayout) findViewById(R.id.ll_parent);
        tv_buttom = (TextView) findViewById(R.id.tv_buttom);
        if (((InitWorkFlowFormActivity) getActivity()).todoFlag == 0) {
            modelName = ((InitWorkFlowFormActivity) getActivity()).modelName;
        }
        app_id = ((InitWorkFlowFormActivity) getActivity()).app_id;
        mHandle.setOnClickListener(this);

        sv_search = (SearchView) findViewById(R.id.daiban_sv_search);
        rv_serach = (RelativeLayout) findViewById(R.id.daiban_rv_serach);
        iv_serach = (ImageView) findViewById(R.id.daiban_iv_serach);
        llSerach = (LinearLayout) findViewById(R.id.ll_serach);
        llToptab = (LinearLayout) findViewById(R.id.ll_toptab);
        ll_top_soso = (RelativeLayout) findViewById(R.id.ll_top_soso);
        rl_button_list = (RelativeLayout) findViewById(R.id.rl_button_list);
        tv_serach = (TextView) findViewById(R.id.daiban_tv_serach);
        ivDirection = (ImageView) findViewById(R.id.iv_direction);
        ll_ryb_bom = (LinearLayout) findViewById(R.id.ll_ryb_bom);
        tvCheck = (TextView) findViewById(R.id.tv_check);
        ll_center = (LinearLayout) findViewById(R.id.ll_center);
        tv_total = (TextView) findViewById(R.id.tv_total);
        mDamageTypeFragment = (GYLStatiscalReportFragment) findViewById(R.id.content_soso);
        tvApprovalNumber = (TextView) findViewById(R.id.tv_approval_number);
        if (IS_GYL) {
            ll_ryb_bom.setVisibility(View.VISIBLE);
        } else {
            ll_ryb_bom.setVisibility(View.GONE);
        }
        tvCheck.setOnClickListener(this);
        tvApprovalNumber.setOnClickListener(this);
        ivDirection.setOnClickListener(this);
        tv_buttom.setOnClickListener(this);
        if (todoFlag.trim().equals("1")) {
            if (hasdoneShortcutsview == 1 || hasdoneShortcutsview == 2) {
                mMyTopTabIndicator.setVisibility(View.VISIBLE);
                mHandle.setVisibility(View.VISIBLE);
                ll_Handle_daiban_list.setVisibility(View.GONE);
            } else {
                mMyTopTabIndicator.setVisibility(View.GONE);
                mHandle.setVisibility(View.GONE);
                ll_Handle_daiban_list.setVisibility(View.GONE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, DensityUtil.dip2px(getActivity(), 40), 0, 0);
                mDrawer.setLayoutParams(layoutParams);
            }
        } else {
            if (todoShortcutsview == 1 || todoShortcutsview == 2) {
                mMyTopTabIndicator.setVisibility(View.VISIBLE);
                mHandle.setVisibility(View.VISIBLE);
                ll_Handle_daiban_list.setVisibility(View.GONE);
            } else {
                mMyTopTabIndicator.setVisibility(View.GONE);
                mHandle.setVisibility(View.GONE);
                ll_Handle_daiban_list.setVisibility(View.GONE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
//        sv_search.setQueryHint(Html
//                .fromHtml("<font color = #999999>" + "请输入标题关键字"
//                        + "</font>"));
        int id = sv_search.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv_search.findViewById(id);
//        textView.setTextSize(DensityUtil.sp2px(HtmitechApplication.instance(), 11));
        int idd = sv_search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchTextView = (TextView) sv_search.findViewById(id);
        android.widget.LinearLayout.LayoutParams textLayoutParams = (android.widget.LinearLayout.LayoutParams) searchTextView.getLayoutParams();
        textLayoutParams.height = textLayoutParams.WRAP_CONTENT;
        searchTextView.setLayoutParams(textLayoutParams);
        searchTextView.setHint("请输入标题关键字搜索");
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
                rv_serach.setVisibility(View.GONE);
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
        if (!IS_GYL) {
            llSerach.setVisibility(View.GONE);
        } else {
            llSerach.setVisibility(View.VISIBLE);
        }
        mAnimatorUtil = new AnimatorUtil();
        llSerach.setOnClickListener(this);
        myOnTouchListener = new InitWorkFlowFormActivity.MyOnTouchListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return dispathTouchEvent(ev);
            }
        };
        ((InitWorkFlowFormActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);


        initData(app_id, getActivity());

        if(!Constant.IS_DZKF){
            ll_center.setVisibility(View.INVISIBLE);
        }
        isGYL = IS_GYL;
    }

    public void initWorkFlowHaveDone(String modelName) {
//        if(null != mWorkFlowHaveDoneListFragment){
//            refreshData(modelName);
//            return;
//        }

        mWorkFlowHaveDoneListFragment = new WorkFlowHaveDoneListFragment();
        mWorkFlowHaveDoneListFragment.setCallBackOpen(this);
        if (currentOcuList == null || currentOcuList.size() == 0) {
            mWorkFlowHaveDoneListFragment.initSearch(modelName, tempOther);
        } else {
            if (currentOcuInfo == null) {
                currentOcuInfo = currentOcuList.get(0);
            }
            mWorkFlowHaveDoneListFragment.initSearch(currentOcuInfo.appInfo, tempOther);
        }


        mWorkFlowHaveDoneListFragment.setmIWorkFlowCheckCountBack(this);
        Bundle mBundle = new Bundle();
        mBundle.putString("com_workflow_plugin_selector_paramter_TodoFlag", todoFlag);
        mBundle.putBoolean("hideSearch", true);
        mBundle.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
        mBundle.putInt("com_workflowm_mobileconfig_customer_shortcuts", customerShortcuts);
        mBundle.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
        mBundle.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
        mBundle.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
        mBundle.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
        mBundle.putInt("com_workflow_mobileconfig_include_share", isShare);
        mBundle.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
        mBundle.putString("com_workflow_plugin_selector_paramter_ModelName", modelName);
        mBundle.putString("com_workflow_mobileconfig_others", tempOther);
        mBundle.putString("appShortName", appName);
        mBundle.putBoolean("IS_GYL", IS_GYL);
        mBundle.putInt("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
        mBundle.putString("com_workflow_mobileconfig_importance_workas_toreadflag", com_workflow_plugin_selector_parmater_Importance);
        mBundle.putInt("number",500);
        mWorkFlowHaveDoneListFragment.hideSearch(true);
        mWorkFlowHaveDoneListFragment.setArguments(mBundle);
        FragmentTransaction transaction_task = getChildFragmentManager()
                .beginTransaction();
//        transaction_task.add(R.id.ll_general_from_child, mHaveDoneListFragment);

        transaction_task.replace(R.id.ll_general_from_child, mWorkFlowHaveDoneListFragment);
//        //显示需要显示的fragment
//        transaction_task.show(mHaveDoneListFragment);
        transaction_task.commit();


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


    public void onClickShaixuan(View view) {


        if (ll_content_soso.isShown()) {
            complete();
            return;
        }

        mDamageTypeFragment.refresh(mReports);
        ivDirection.setImageResource(R.drawable.btn_todolist_screen_close);
        ll_content_soso.clearAnimation();
        downAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.zt_score_business_query_enter);


        downAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                ll_content_soso.setBackgroundColor(getResources().getColor(R.color.bantouming));
                ll_content_soso.getBackground().setAlpha(150);
            }
        });
        ll_content_soso.setVisibility(View.VISIBLE);
        ll_content_soso.setAnimation(downAnimation);
    }

    public void initData(String app_id, Context context) {
        this.context = context;
        this.app_id = app_id;
        currentOcuList.clear();
        complete();
        hashMapCheck = new LinkedHashMap<String, String>();
        tvCheck.setTag("false");
        setBack();
        mDocInfoParameters = new DocInfoParameters();
        tvCheck.setSelected(false);
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);

        if (appliationCenterDao == null)
            appliationCenterDao = new AppliationCenterDao(getActivity());
        ArrayList<AppInfo> childApp = appliationCenterDao.getChildApp(app_id, true);//待办appid
        mcurrentOcuList = childApp;

        upAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.zt_score_business_query_exit);

        downAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.zt_score_business_query_enter);


        downAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_content_soso.setBackgroundColor(getResources().getColor(R.color.bantouming));
                ll_content_soso.getBackground().setAlpha(150);
            }
        });

//        mDamageTypeFragment = new GYLStatiscalReportFragment();
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
//                .beginTransaction();
//        Bundle mBundle = new Bundle();
        mDamageTypeFragment.setmCallBackDamageTypeGridListener(this);
//        mDamageTypeFragment.setArguments(mBundle);
//        transaction
//                .hide(mDamageTypeFragment)
//                .add(R.id.content_soso,mDamageTypeFragment,""+todoFlag);
//        transaction.commit();


//

        mDoActionParameter = new DoActionParameter();
        mDoActionParameter.userId = OAConText.getInstance(getActivity()).UserID;
        mDoActionParameter.docId = ""; // "HZ286f024cf9144b014d22ae386f495e";
        mDoActionParameter.systemCode = "";//2015-08-11
        mDoActionParameter.appId = app_id;
        mDoActionParameter.editFields = new EditField[0];
        mDoActionParameter.selectRoutes = new ArrayList<SelectRouteInfo>();
        mDoActionParameter.fileList = new ArrayList<FileUpload>();


        //此处为定制开发，如果遇到这个则进行走接口
        if (TextUtils.equals("zt_ryb_fkd", typeCode)) {
//            String dzResult = "{\"code\":200,\"debugMsg\":null,\"message\":\"操作成功\",\"result\":[{\"flowId\":\"流程ID：如，Y01A0000-OilBankPayBillApprovalFlowDBHEBSC \",\"flowName\":\"流程名称：中铁油料东北分公司市场银行付款审批流(哈尔滨）\",\"flowShortName\":\"哈尔滨局\",\"count\":2},{\"flowId\":\"Y06A0000-OilBankPayBillApprovalFlowHDSC \",\"flowName\":\"中铁油料华东分公司市场银行付款审批流\",\"flowShortName\":\"东北分公司\",\"count\":2}]}";
//            DZDocGroupListResult mDZDocGroupListResult = FastJsonUtils.getPerson(dzResult, DZDocGroupListResult.class);
//            if (mDZDocGroupListResult != null && mDZDocGroupListResult.getCode() == 200) {
//                Message msg = handler.obtainMessage();
//                msg.what = 0;
//                msg.obj = mDZDocGroupListResult;
//                handler.sendMessage(msg);
//            }
            mDocSearchParameters = new DocSearchParameters();
            mDocSearchParameters.modelName = "银行付款单审批";
            mDocSearchParameters.recordStartIndex = 0;
            mDocSearchParameters.recordEndIndex = countPerPage;
            mDocSearchParameters.appId = app_id;
            mDocSearchParameters.todoFlag = todoFlag;
            mDocSearchParameters.userId = OAConText.getInstance(getActivity()).UserID;;
            mDocSearchParameters.setOthers(tempOther);
            ll_center.setVisibility(View.INVISIBLE);
            netWorkManager.logFunactionStart(getActivity(), GYLWorkFlowFormTodoListFragment.this, "dzDocGroupList", LogManagerEnum.GGENERAL.functionCode);
            return;
        }else{
            initLocalSearch();
        }

    }

    public void initLocalSearch(){
        currentOcuList.clear();
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

    @Override
    public void callBack(ArrayList<ReportSosoResult> list) {
        try {
            for (ReportSosoResult mReportSosoResult : list) {
                mMyTopTabIndicator.onPageSelected(Integer.parseInt(mReportSosoResult.getId()));
            }
        } catch (Exception e) {

        }

    }

    @Override
    public void complete() {
        upAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.zt_score_business_query_exit);
        ll_content_soso.setBackgroundColor(getResources().getColor(R.color.transparent));
        ll_content_soso.setVisibility(View.GONE);
        ll_content_soso.setAnimation(upAnimation);

        ivDirection.setImageResource(R.drawable.btn_todolist_screen_open);


    }

    public Handler handler = new Handler() {
        public DZDocGroupListResult mDZDocGroupListResult;

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 0:
                    mDZDocGroupListResult = (DZDocGroupListResult) msg.obj;

                    mReports = new ArrayList<ReportSosoResult>();
                    if (com_workflow_mobileconfig_tabbutton_style == 2 || com_workflow_mobileconfig_tabbutton_style == 5 || com_workflow_mobileconfig_tabbutton_style == 6) {
                        todoFlag = com_workflow_plugin_selector_parmater_Importance;
                    }
                    Bitmap daiban_bit;
                    Bitmap add = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_add);
                    if (!TextUtils.isEmpty(todoFlag) && todoFlag.equals("0")) {
                        daiban_bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_all_backlog);
                    } else {
                        daiban_bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_all_finished);
                    }
                    String tempName = "";
                    String edit = "";
                    if (!todoFlag.equals("0")) {
                        if (hasdoneShortcutsview == 2) {

                        } else {
                            tempName = ((com_workflow_mobileconfig_tabbutton_style == 1) || (com_workflow_mobileconfig_tabbutton_style == 4)) ? "所有已办" : "所有已阅";
                            mDZDocGroupListResult.getResult().add(0, new GroupListResult("", tempName));
                        }

                    } else {
                        if (todoShortcutsview == 2) {

                        } else {
                            tempName = ((com_workflow_mobileconfig_tabbutton_style == 1) || (com_workflow_mobileconfig_tabbutton_style == 3)) ? "所有待办" : "所有待阅";
                            mDZDocGroupListResult.getResult().add(0, new GroupListResult("", tempName));
                        }

                    }

                    if (todoFlag.equals("1")) {
                        if (hasdoneShortcuts == 1) {
//                            currentOcuList.add(new BinnerBitmapMessage(add, "", context.getResources().getString(R.string.edit), "", 0, null, "add", todoFlag));
                            edit = context.getResources().getString(R.string.edit);
                            mDZDocGroupListResult.getResult().add(new GroupListResult(edit, edit));
                        }
                    } else {
                        if (todoShortcuts == 1) {
//                            currentOcuList.add(new BinnerBitmapMessage(add, "", context.getResources().getString(R.string.edit), "", 0, null, "add", todoFlag));
                            edit = context.getResources().getString(R.string.edit);
                            mDZDocGroupListResult.getResult().add(new GroupListResult(edit, edit));
                        }
                    }
                    int index = 0;
                    ArrayList<String> arrayList = new ArrayList<String>();

                    for (GroupListResult mGroupListResult : mDZDocGroupListResult.getResult()) {
                        ReportSosoResult mReportSosoResult = new ReportSosoResult();
                        mReportSosoResult.setId(index + "");
                        mReportSosoResult.setKey(index + "");
                        mReportSosoResult.setName(mGroupListResult.getFlowShortName() + "");
                        mReportSosoResult.setValue(mGroupListResult.getFlowName() + "");
                        if (index == 0) {
                            mReportSosoResult.setIsSelectd(1);
                        }
                        index++;
                        mReports.add(mReportSosoResult);
                        if (mGroupListResult.getFlowName().equals(context.getResources().getString(R.string.edit))) {
                            continue;
                        }
                        arrayList.add("" + mGroupListResult.getFlowShortName());
                    }

                    String[] arrayTopTabIndicator = new String[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayTopTabIndicator[i] = arrayList.get(i);
                    }
                    if (arrayTopTabIndicator.length > 0) {
                        mMyTopTabIndicator.removeAllViews();
                        llToptab.setVisibility(View.VISIBLE);
                        mMyTopTabIndicator.setCommonData2(null, arrayTopTabIndicator, R.color.color_title, R.color.color_ff888888);
                    } else {
                        llToptab.setVisibility(View.GONE);
                    }

                    mMyTopTabIndicator.setmOnPageSelect(new NewTopTabIndicator.OnPageSelect() {
                        @Override
                        public void onPageSelect(final View view, final int position) {

                            if (position >= mDZDocGroupListResult.getResult().size()) {
                                return;
                            }
                            int index = 0;
                            mReports.clear();
                            for (GroupListResult mGroupListResult : mDZDocGroupListResult.getResult()) {
                                ReportSosoResult mReportSosoResult = new ReportSosoResult();
                                mReportSosoResult.setId(index + "");
                                mReportSosoResult.setKey(index + "");
                                mReportSosoResult.setName(mGroupListResult.getFlowShortName() + "");
                                mReportSosoResult.setValue(mGroupListResult.getFlowName() + "");
                                if (position == index) {
                                    mReportSosoResult.setIsSelectd(1);
                                } else {
                                    mReportSosoResult.setIsSelectd(0);
                                }
                                mReports.add(mReportSosoResult);
                                index++;
                            }
                            mGroupListResult = mDZDocGroupListResult.getResult().get(position);
//                        //刷新列表
                            if (mGroupListResult.getFlowShortName().equals("编辑")) {
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("app_id", app_id);
                                map.put("todoFlag", todoFlag);
                                ActivityUnit.switchTo(getActivity(), WorkFlowAddCenterSelectActivity.class, ActivityResultConstant.SAVEOCUS_RESULT_OK, map);
                                return;
                            }
                            refreshData(mReports.get(position).getValue());
                            mDrawer.close();
                            mWorkFlowHaveDoneListFragment.getCheckDoc().clear();
                            workFlowCheckCount(null, false);
                            mDamageTypeFragment.refresh(mGroupListResult.flowShortName);
                            complete();
                        }
                    });

                    if (mReports.size() > 0) {
                        modelName = mReports.get(0).getValue();

                        mGroupListResult = mDZDocGroupListResult.getResult().get(0);

                    }

                    initWorkFlowHaveDone(modelName);
                    break;
                case 1:
                    if (currentOcuList == null) {
                        currentOcuList = new ArrayList<BinnerBitmapMessage>();
                    }
                    if (com_workflow_mobileconfig_tabbutton_style == 2 || com_workflow_mobileconfig_tabbutton_style == 5 || com_workflow_mobileconfig_tabbutton_style == 6) {
                        todoFlag = com_workflow_plugin_selector_parmater_Importance;
                    }
                    daiban_bit = null;
                    add = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_add);
                    if (!TextUtils.isEmpty(todoFlag) && todoFlag.equals("0")) {
                        daiban_bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_all_backlog);
                    } else {
                        daiban_bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_all_finished);
                    }

                    String tempFlag = currentOcuList.size() > 0 ? currentOcuList.get(0).Caption : "";

                    if (!tempFlag.equals("全部")) {
                        if (!todoFlag.equals("0")) {
                            if (hasdoneShortcutsview == 2) {

                            } else {
                                tempName = ((com_workflow_mobileconfig_tabbutton_style == 1) || (com_workflow_mobileconfig_tabbutton_style == 4)) ? "所有已办" : "所有已阅";
                                currentOcuList.add(0, new BinnerBitmapMessage(daiban_bit, app_id, tempName, "", 0, null, "todo_flag", todoFlag));
                            }

                        } else {
                            if (todoShortcutsview == 2) {

                            } else {
                                tempName = ((com_workflow_mobileconfig_tabbutton_style == 1) || (com_workflow_mobileconfig_tabbutton_style == 3)) ? "所有待办" : "所有待阅";
                                currentOcuList.add(0, new BinnerBitmapMessage(daiban_bit, app_id, tempName, "", 0, null, "todo_flag", todoFlag));
                            }

                        }

                        if (todoFlag.equals("1")) {
                            if (hasdoneShortcuts == 1) {
                                currentOcuList.add(new BinnerBitmapMessage(add, "", context.getResources().getString(R.string.edit), "", 0, null, "add", todoFlag));
                            }
                        } else {
                            if (todoShortcuts == 1) {
                                currentOcuList.add(new BinnerBitmapMessage(add, "", context.getResources().getString(R.string.edit), "", 0, null, "add", todoFlag));
                            }
                        }
                    }
                    arrayList = new ArrayList<String>();
                    index = 0;
                    mReports = new ArrayList<ReportSosoResult>();
                    for (BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
                        ReportSosoResult mReportSosoResult = new ReportSosoResult();
                        mReportSosoResult.setId(index + "");
                        mReportSosoResult.setKey(index + "");
                        mReportSosoResult.setName(mBinnerBitmapMessage.getCaption() + "");
                        mReportSosoResult.setValue(mBinnerBitmapMessage.getCaption() + "");
                        if (index == 0) {
                            mReportSosoResult.setIsSelectd(1);
                        }

                        mReports.add(mReportSosoResult);
                        if (mBinnerBitmapMessage.getCaption().equals(context.getResources().getString(R.string.edit))) {
                            continue;
                        }
                        arrayList.add(mBinnerBitmapMessage.getCaption());

                        index++;
                    }
                    arrayTopTabIndicator = new String[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayTopTabIndicator[i] = arrayList.get(i);
                    }
                    if (arrayTopTabIndicator.length > 0) {
                        mMyTopTabIndicator.removeAllViews();
                        mMyTopTabIndicator.setCommonData2(null, arrayTopTabIndicator, R.color.color_title, R.color.color_ff888888);
                    } else {
                        llToptab.setVisibility(View.GONE);
                    }
                    mMyTopTabIndicator.setmOnPageSelect(new NewTopTabIndicator.OnPageSelect() {
                        @Override
                        public void onPageSelect(final View view, final int position) {

                            for (BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
                                mBinnerBitmapMessage.setSelected(0);
                            }
                            if (position >= currentOcuList.size()) {
                                return;
                            }
                            int index = 0;
                            mReports.clear();
                            for (BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
                                ReportSosoResult mReportSosoResult = new ReportSosoResult();
                                mReportSosoResult.setId(index + "");
                                mReportSosoResult.setKey(index + "");
                                mReportSosoResult.setName(mBinnerBitmapMessage.getCaption() + "");
                                mReportSosoResult.setValue(mBinnerBitmapMessage.getCaption() + "");
                                if (position == index) {
                                    mReportSosoResult.setIsSelectd(1);
                                }
                                mReports.add(mReportSosoResult);
                                index++;
                            }
                            currentOcuInfo = currentOcuList.get(position);
                            currentOcuInfo.setSelected(1);
                            if (currentOcuInfo.appInfo != null && (currentOcuInfo.appInfo.getPlugin_code().equals("com_workflow_plugin_startor") || currentOcuInfo.appInfo.getPlugin_code().equals("com_workflow_plugin_H5_startor") || currentOcuInfo.appInfo.getPlugin_code().equals("com_workflow_plugin_opinions"))) {     //h5插件
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
                                if (currentOcuInfo.Caption.equals("编辑")) {
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("app_id", app_id);
                                    map.put("todoFlag", todoFlag);
                                    ActivityUnit.switchTo(getActivity(), WorkFlowAddCenterSelectActivity.class, ActivityResultConstant.SAVEOCUS_RESULT_OK, map);
                                    return;
                                }
                                refreshData("");
                                mDrawer.close();
                                mWorkFlowHaveDoneListFragment.getCheckDoc().clear();
                                workFlowCheckCount(null, false);
                            } else {

                            }
                            mDamageTypeFragment.refresh(currentOcuInfo.getCaption());
                            complete();
                        }
                    });

                    if(currentOcuList != null && currentOcuList.size() > 0){
                        if(!currentOcuList.get(0).getAppInfo().equals("全部")){
                            mDocSearchParameters= AngleWorkFlowUntil.getSearchcondition(getActivity(),currentOcuList.get(0).getAppInfo());
                        }

                    }
                    if(mDocSearchParameters != null){
                        modelName = mDocSearchParameters.modelName;
                    }
                    initWorkFlowHaveDone(modelName);
                    break;

            }
            super.handleMessage(msg);
        }

    };

    public void refreshData(String modelName) {
        if (currentOcuInfo == null) {
            if(currentOcuList.size() > 0)
                currentOcuInfo = currentOcuList.get(0);
        }
        if(mWorkFlowHaveDoneListFragment == null){
            return;
        }
        mWorkFlowHaveDoneListFragment.setTodoFlag(todoFlag);
        if (currentOcuInfo != null && currentOcuInfo.getCaption().contains("所有")) {
            mWorkFlowHaveDoneListFragment.searchsearchQueryAllShow(tempOther);
        } else {
            if (currentOcuInfo != null && currentOcuInfo.getAppInfo() != null) {
                mWorkFlowHaveDoneListFragment.searchQueryShow(currentOcuInfo.appInfo, tempOther);
            } else {
                mWorkFlowHaveDoneListFragment.searchQueryShow(modelName, tempOther);
            }

        }
    }

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
            case R.id.iv_direction:
                onClickShaixuan(view);
                break;
            case R.id.tv_buttom:
                complete();
                break;
            case R.id.ll_serach:
                if(TextUtils.isEmpty(com_workflow_mobileconfig_other_conditions)){
                    ToastInfo mToastInfo = ToastInfo.getInstance(getActivity());
                    mToastInfo.setText("筛选模版尚未配置");
                    mToastInfo.show(Toast.LENGTH_LONG);
                    return;
                }
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("app_id", app_id);
                params.put("app_version_id", ((InitWorkFlowFormActivity) getActivity()).app_version_id);
                params.put("mobile_condition", com_workflow_mobileconfig_other_conditions);
                HTActivityUnit.switchTo(getActivity(), WebSearchActivity.class, params);
                break;
            case R.id.tv_check:

                if (tvCheck.getTag() != null && !TextUtils.isEmpty(tvCheck.getTag().toString()) && tvCheck.getTag().toString().equals("false")) {
                    tvCheck.setTag("true");
                    tvCheck.setSelected(true);
                    mWorkFlowHaveDoneListFragment.setRefreshData(true);

                } else if (tvCheck.getTag() != null && !TextUtils.isEmpty(tvCheck.getTag().toString()) && tvCheck.getTag().toString().equals("true")) {
                    tvCheck.setTag("false");
                    tvCheck.setSelected(false);
                    mWorkFlowHaveDoneListFragment.setRefreshData(false);
                    mWorkFlowHaveDoneListFragment.getCheckDoc().clear();
                }
                workFlowCheckCount(mWorkFlowHaveDoneListFragment.getCheckDoc(), mWorkFlowHaveDoneListFragment.getCheckDoc().size() == mWorkFlowHaveDoneListFragment.getDocListEntity().size());

                break;
            case R.id.tv_approval_number:
                try {
                    setCancelable(false);
                    showDialog();
                    netWorkManager.logFunactionStart(getActivity(), this, "functionStart", LogManagerEnum.APP_DOC_INFO.functionCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
            mWorkFlowHaveDoneListFragment.searchQuery(tempOther, arg0);
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
        mWorkFlowHaveDoneListFragment.searchQuery(tempOther, arg0);
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
//            checkIndex = 0;
//            initData(app_id, getActivity());
            if (com.htmitech.htworkflowformpluginnew.constant.ActivityResultConstant.otherses.size() > 0 && !com.htmitech.htworkflowformpluginnew.constant.ActivityResultConstant.isBack) {
//                com.htmitech.htworkflowformpluginnew.constant.ActivityResultConstant.otherses.
                List<Others> otherses = FastJsonUtils.getPersonList(com_workflow_mobileconfig_others, Others.class);


                otherses.addAll(com.htmitech.htworkflowformpluginnew.constant.ActivityResultConstant.otherses);

                for (Others mOhter : otherses) {
                    if (mOhter.fieldName.equalsIgnoreCase("todoFlag")) {
                        todoFlag = mOhter.compareValue;
                        break;
                    }
                }

                tempOther = JSONArray.toJSONString(otherses);

                if (TextUtils.equals("1", todoFlag)) {
                    ll_ryb_bom.setVisibility(View.GONE);
                } else {
                    ll_ryb_bom.setVisibility(View.VISIBLE);
                }

                IS_GYL = TextUtils.equals("1", todoFlag) ? false : isGYL;

//                if(mGroupListResult != null){
//                    refreshData(mGroupListResult.getFlowName());
//                }else{
//                    refreshData(modelName);
//                }

                if(mWorkFlowHaveDoneListFragment != null)
                    mWorkFlowHaveDoneListFragment.isRefresh = false;

                initData(app_id, getActivity());
//                if (TextUtils.equals("zt_ryb_fkd", typeCode)) {
//                    showDialog();
//                    if(mDocSearchParameters == null){
//                        mDocSearchParameters = new DocSearchParameters();
//                        mDocSearchParameters.modelName = "银行付款单审批";
//                        mDocSearchParameters.recordStartIndex = 0;
//                        mDocSearchParameters.recordEndIndex = countPerPage;
//                        mDocSearchParameters.appId = app_id;
//                        mDocSearchParameters.userId = OAConText.getInstance(getActivity()).UserID;;
//                        mDocSearchParameters.setOthers(com_workflow_mobileconfig_others);
//                    }
//
//                    netWorkManager.logFunactionStart(getActivity(), GYLWorkFlowFormTodoListFragment.this, "dzDocGroupList", LogManagerEnum.GGENERAL.functionCode);
//                }else{
//                    initLocalSearch();
//                }

                if(!Constant.IS_DZKF){
                    ll_center.setVisibility(View.INVISIBLE);
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
        docSearchParameters.title = "";
        if (com_workflow_mobileconfig_tabbutton_style == 2 || 6 == com_workflow_mobileconfig_tabbutton_style || 5 == com_workflow_mobileconfig_tabbutton_style) {
            docSearchParameters.importance = com_workflow_plugin_selector_parmater_Importance;
        } else {
            docSearchParameters.todoFlag = todoFlag; // 0，待办；1，已办
        }
        docSearchParameters.modelName = mBinnerBitmapMessage.Caption.equals("全部") ? "" : mBinnerBitmapMessage.Caption + "";
        WorkFlowCountHttpUtil mWorkFlowCountHttpUtil = new WorkFlowCountHttpUtil();
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBack() {
        int colorValue = getResources().getColor(R.color.ht_hred_title);
        int colorBulue = getResources().getColor(R.color.ht_hred_title_duibi);
        int colorRed = getResources().getColor(R.color.ht_red_dangzheng);
        try{
            Drawable drawable = tvApprovalNumber.getBackground();
            ColorDrawable dra = (ColorDrawable) drawable;
            int colors[] = new int[2];
            colorValue = dra.getColor();
            if (colorValue == colorBulue) {
                colors[0] = colorBulue;
                colors[1] = 0xff53B2FD;
                Constant.color = 2;
            } else if (colorValue == colorRed) {
                colors[0] = colorRed;
                colors[1] = 0xffF08F8F;
                Constant.color = 3;
            } else {
                drawable = tvApprovalNumber.getBackground();
                dra = (ColorDrawable) drawable;
                colorValue = dra.getColor();
                if (colorValue == colorBulue) {
                    colors[0] = colorBulue;
                    colors[1] = 0xff53B2FD;
                    Constant.color = 2;
                } else if (colorValue == colorRed) {
                    colors[0] = colorRed;
                    colors[1] = 0xffF08F8F;
                    Constant.color = 3;
                }
            }
            tvApprovalNumber.setTextColor(getResources().getColor(R.color.white));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void workFlowCheckCount(ArrayList<com.htmitech.htworkflowformpluginnew.entity.Doc> docs, boolean isAll) {
        if (docs == null) {
            docs = new ArrayList<Doc>();
        }
        double total = 0;
        for (Doc doc : docs) {
            total += doc.getEfn1();
        }
        if (isAll && docs.size() != 0) {
            tvCheck.setTag("true");
            tvCheck.setSelected(true);
            tvApprovalNumber.setText("审批（" + docs.size() + "）");
            tvApprovalNumber.setEnabled(true);
        } else {
            tvCheck.setTag("false");
            tvCheck.setSelected(false);
            if (docs.size() == 0) {
                tvApprovalNumber.setText("审批");
                tvApprovalNumber.setEnabled(false);
            } else {
                tvApprovalNumber.setText("审批（" + docs.size() + "）");
                tvApprovalNumber.setEnabled(true);
            }
        }
        tv_total.setText(total + "");
        if (docs.size() > 0) {
            OAConText instance = OAConText.getInstance(getActivity());
            mDocInfoParameters.docId = docs.get(0).getDocId();
            mDocInfoParameters.systemCode = docs.get(0).getSystemCode();
            mDocInfoParameters.appId = app_id;
            mDocInfoParameters.appVersionId = ((InitWorkFlowFormActivity) getActivity()).app_version_id;
            mDocInfoParameters.userId = instance.UserID;
            mDoActionParameter.flowId = docs.get(0).getFlowId();
            if (!TextUtils.isEmpty(docs.get(0).getFlowId())) {
                mDocInfoParameters.flowId = docs.get(0).getFlowId();
            }
        }

    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("functionStart")) {
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocInfoParameters, getDocInfoPath, CHTTP.POSTWITHTOKEN, this, GET_DOCINFO, LogManagerEnum.APP_DOC_INFO.functionCode);
        } else if (GET_DOCINFO.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(getActivity(),requestValue, type, getDocInfoPath, mDocInfoParameters, this, requestName, LogManagerEnum.APP_DOC_INFO.functionCode);
            if (!TextUtils.isEmpty(requestValue)) {
                DocResultInfo mDocResultInfo = FastJsonUtils.getPerson(requestValue, DocResultInfo.class);
                if (mDocResultInfo != null && mDocResultInfo.getResult() != null && mDocResultInfo.getResult().getListActionInfo() != null) {

                    List<ActionInfo> infos = mDocResultInfo.getResult().getListActionInfo();
                    hashMapCheck.clear();
                    for (ActionInfo mActionInfo : infos) {
                        hashMapCheck.put(mActionInfo.getActionID(), mActionInfo.getActionName());
                    }
                    new ActionSheetDialog(getActivity())
                            .builder()
                            .setTitle("请选择")
                            .setCancelable(false)
                            .setCanceledOnTouchOutside(false)
                            .setChooseWay(ChooseWay.NOT_CHECK)
                            .addSheetItemMap(hashMapCheck)
                            .setOnSheetItemClickListener(
                                    new ActionSheetDialog.OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(HashMap<String, String> map) {
                                            if (map == null || map.size() == 0) {
                                                return;
                                            }
                                            for (String key : map.keySet()) {
                                                mDoActionParameter.actionName = map.get(key);
                                                mDoActionParameter.actionId = key;
                                                break;
                                            }
                                            String[] docIds = new String[mWorkFlowHaveDoneListFragment.getCheckDoc().size()];
                                            for (int i = 0; i < mWorkFlowHaveDoneListFragment.getCheckDoc().size(); i++) {
                                                docIds[i] = mWorkFlowHaveDoneListFragment.getCheckDoc().get(i).getDocId();
                                            }
                                            mDoActionParameter.docIds = docIds;
                                            showDialog();
                                            netWorkManager.logFunactionStart(getActivity(), GYLWorkFlowFormTodoListFragment.this, "handlefunctionStart", LogManagerEnum.APP_DO_ACTION.functionCode);
                                        }
                                    }).show();

                }
            }
            dismissDialog();
        } else if ("handlefunctionStart".equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDoActionParameter, getDocInfoHandles, CHTTP.POSTWITHTOKEN, this, GET_DOCINFO_HANDLE, LogManagerEnum.APP_DO_ACTION.functionCode);
        } else if ("dzDocGroupList".equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(getActivity(), mDocSearchParameters, getDocGroupList, CHTTP.POSTWITHTOKEN, this, GETDOCGROUP_LIST, LogManagerEnum.GGENERAL.functionCode);
        } else if (GETDOCGROUP_LIST.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(getActivity(),requestValue, type, getDocGroupList, mDocSearchParameters, this, requestName, LogManagerEnum.GGENERAL.functionCode);
            DZDocGroupListResult mDZDocGroupListResult = FastJsonUtils.getPerson(requestValue, DZDocGroupListResult.class);
            if (mDZDocGroupListResult != null && mDZDocGroupListResult.getCode() == 200) {
                Message msg = handler.obtainMessage();
                msg.what = 0;

                msg.obj = mDZDocGroupListResult;
                handler.sendMessage(msg);
            }
            dismissDialog();
        } else if (GET_DOCINFO_HANDLE.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(getActivity(),requestValue, type, getDocInfoHandles, mDoActionParameter, this, requestName, LogManagerEnum.APP_DO_ACTION.functionCode);
            if (!TextUtils.isEmpty(requestValue)) {
                DoActionResultInfo mDoActionResultInfo = FastJsonUtils.getPerson(requestValue, DoActionResultInfo.class);
                if (null != mDoActionResultInfo && null != mDoActionResultInfo.getResult()) {
                    ToastInfo toastInfo = ToastInfo.getInstance(getActivity());
                    toastInfo.setText(mDoActionResultInfo.getMessage());
                    toastInfo.show(Toast.LENGTH_SHORT);
                }
                workFlowCheckCount(null, false);
                if(mGroupListResult == null){
                    refreshData(modelName);
                }else{
                    refreshData(mGroupListResult.getFlowName());
                }

            }
            dismissDialog();
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        dismissDialog();
        ToastInfo toastInfo = ToastInfo.getInstance(getActivity());
        toastInfo.setText(exceptionMessage);
        toastInfo.show(Toast.LENGTH_SHORT);
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    private boolean dispathTouchEvent(MotionEvent event) {
        if (mIsAnim) {
            return false;
        }
        final int action = event.getAction();

        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
                isUp = dX < 8 && dY > 8 && !mIsTitleHide && !down;
                isDown = dX < 8 && dY > 8 && mIsTitleHide && down;
                if (isUp) {
                    View view = this.ll_top_soso;
                    float[] f = new float[2];
                    f[0] = 0.0F;
                    f[1] = -ll_top_soso.getHeight();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.setDuration(200);
                    animator1.start();
                    animator1.addListener(this);
                    setMarginTop(llToptab.getHeight());
                } else if (isDown) {
                    View view = this.ll_top_soso;
                    float[] f = new float[2];
                    f[0] = -ll_top_soso.getHeight();
                    f[1] = 0F;
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setDuration(200);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.start();
                    animator1.addListener(this);

                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                break;
            default:
                return false;
        }
        return false;

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        if (isDown) {
//            setMarginTop(ll_top_soso.getHeight()+ llToptab.getHeight());

            ll_top_soso.setVisibility(View.VISIBLE);
        } else if (isUp) {
            ll_top_soso.setVisibility(View.GONE);
        }
//        ll_parent.invalidate();
        mIsAnim = false;
    }

    public void setMarginTop(int page) {
//        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
//        layoutParam.setMargins(0, page, 0, 0);
//        rl_button_list.setLayoutParams(layoutParam);
//
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }



}
