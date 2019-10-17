package com.htmitech.htexceptionmanage.fragment;

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
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.daiban.IScrollListener;
import com.htmitech.emportal.ui.homepage.BinnerBitmapMessage;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.htexceptionmanage.adapter.ExceptionGridViewAdapter;
import com.htmitech.htexceptionmanage.entity.AlertCountInfo;
import com.htmitech.htexceptionmanage.entity.ManageExceptionparameter;
import com.htmitech.htexceptionmanage.utils.ExceptionAngleUtils;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.interfaces.CallBackOpen;

import java.lang.reflect.Field;
import java.util.ArrayList;

import htmitech.com.componentlibrary.base.MyBaseFragment;

/***
 * 消息提醒快捷键展示页面
 *
 * @author joe
 * @date 2017-9-25 10:07:01
 */
public class ManageExceptionTodoListFragment extends MyBaseFragment implements IBottomItemSelectCallBack,
        IScrollListener, SearchView.OnQueryTextListener, CallBackOpen {
    private final static String TAG = ManageExceptionTodoListFragment.class.getSimpleName();
    private SlidingDrawer mDrawer;
    private GridView mGridView;
    private ExceptionGridViewAdapter mGridviewHandleAdapter;
    private ImageView mHandle;
    public ArrayList<AppInfo> mcurrentOcuList;
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
    private ManageExceptionHaveDoneListFragment manageExceptionHaveDoneListFragment;
    private String app_id;
    private int isWaterSecurity;
    private Context context;
    private LinearLayout ll_Handle_exception_list;
    private int numberIndex = 0;

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_exception_list;
    }

    @SuppressWarnings({"deprecation", "rawtypes", "uncheckedget"})
    @Override
    protected void initViews() {
        // TODO Auto-generated method stub
        Log.d("initViews", "initViews ------------------------------------>");
        Bundle mBunlde = getArguments();
        app_id = mBunlde.getString("app_id");
        isWaterSecurity = mBunlde.getInt("com_alert_mobileconfig_include_security");

        mDrawer = (SlidingDrawer) findViewById(R.id.exception_slidingDrawer_exception_list);
        ll_Handle_exception_list = (LinearLayout) findViewById(R.id.ll_Handle_exception_list);
        mHandle = (ImageView) findViewById(R.id.exception_imageviewHandle_exception_list);
        mGridView = (GridView) findViewById(R.id.exception_gridview_exceptionlist);
        sv_search = (SearchView) findViewById(R.id.exception_sv_search);
        rv_serach = (RelativeLayout) findViewById(R.id.exception_rv_serach);
        iv_serach = (ImageView) findViewById(R.id.exception_iv_serach);
        tv_serach = (TextView) findViewById(R.id.exception_tv_serach);

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

        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    final int position, long id) {
                // TODO Auto-generated method stub
                // 点击最后一个item，打开快捷键定义界面
                if (position == 0) {
                    manageExceptionHaveDoneListFragment.searchQueryAll();
                    mDrawer.close();
                }
//                else if (position == currentOcuList.size() - 1) {//如果等于最后一个的话 那么将进入
//                    Map<String, Object> map = new HashMap<String, Object>();
//                    map.put("app_id", app_id);
//                    ActivityUnit.switchTo(getActivity(), ManageExceptionAddCenterSelectActivity.class, ActivityResultConstant.SAVEOCUS_RESULT_OK, map);
//                }
                else {
                    BinnerBitmapMessage currentOcuInfo = ((ExceptionGridViewAdapter) mGridView.getAdapter()).getCurrentOcuList().get(position);
                    if (currentOcuInfo.appid != null && currentOcuInfo.Caption != null) {
//                        //刷新列表
                        manageExceptionHaveDoneListFragment.searchQuery(currentOcuInfo.appInfo);
                        mDrawer.close();
                    }
                }
            }
        });
        manageExceptionHaveDoneListFragment = new ManageExceptionHaveDoneListFragment();
        manageExceptionHaveDoneListFragment.setCallBackOpen(this);
        Bundle mBundle = new Bundle();
        mBundle.putString("app_id",app_id);
        mBundle.putInt("com_alert_mobileconfig_include_security",isWaterSecurity);
        manageExceptionHaveDoneListFragment.hideSearch(true);
        manageExceptionHaveDoneListFragment.setArguments(mBundle);
        FragmentTransaction transaction_task = getChildFragmentManager()
                .beginTransaction();
        transaction_task.replace(R.id.ll_general_from_child, manageExceptionHaveDoneListFragment);
        transaction_task.commit();
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
                    try {
                        currentOcuList.add(new BinnerBitmapMessage(null,
                                mAppInfo.getApp_id() + "", mAppInfo.getApp_shortname(), "", mAppInfo.getApp_id(), mAppInfo, mAppInfo.getComp_code()));
                    } catch (Exception e) {
                        e.printStackTrace();
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
                case 1:
                    if (currentOcuList == null) {
                        currentOcuList = new ArrayList<BinnerBitmapMessage>();
                    }
                    mGridviewHandleAdapter = new ExceptionGridViewAdapter(currentOcuList, "0", app_id, 0, ManageExceptionTodoListFragment.this.getActivity() == null ? context : ManageExceptionTodoListFragment.this.getActivity());
                    mGridView.setAdapter(mGridviewHandleAdapter);
                    mGridviewHandleAdapter.notifyDataSetChanged();
                    if (currentOcuList != null)
                        for (BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
                            if (mBinnerBitmapMessage.Caption.equals("全部") || (mBinnerBitmapMessage.appInfo != null)) {
                                setAngleNumber(mBinnerBitmapMessage);
                            }
                        }
                    break;

            }
            super.handleMessage(msg);
        }

    };


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
            manageExceptionHaveDoneListFragment.searchQuery(arg0);
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
        manageExceptionHaveDoneListFragment.searchQuery(arg0);
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
        if (getActivity() != null)
            initData(app_id, getActivity());
        if (sv_search != null)
            sv_search.clearFocus();
    }


    public synchronized void setAngleNumber(final BinnerBitmapMessage mBinnerBitmapMessage) {
        ManageExceptionparameter manageExceptionparameter = new ManageExceptionparameter();
        manageExceptionparameter.setUserId(OAConText.getInstance(HtmitechApplication.instance()).UserID);

        if(mBinnerBitmapMessage.Caption.equals("全部")){
            manageExceptionparameter.setFilterDays("0");
            manageExceptionparameter.setSourceType("");
            manageExceptionparameter.setKeyWord("");

        }else {
            if( mBinnerBitmapMessage.appInfo!=null&& mBinnerBitmapMessage.appInfo.getmAppVersion()!=null&& mBinnerBitmapMessage.appInfo.getmAppVersion().getAppVersionConfigArrayList()!=null){
                for(AppVersionConfig appVersionConfig : mBinnerBitmapMessage.appInfo.getmAppVersion().getAppVersionConfigArrayList()){
                    if(appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_days")){
                        manageExceptionparameter.setFilterDays(appVersionConfig.getConfig_value()==null?"":appVersionConfig.getConfig_value());
                    }
                    if(appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_sourcetype")){
                        manageExceptionparameter.setSourceType(appVersionConfig.getConfig_value()==null?"":appVersionConfig.getConfig_value());
                    }
                    if(appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_title_keyword")){
                        manageExceptionparameter.setKeyWord(appVersionConfig.getConfig_value()==null?"":appVersionConfig.getConfig_value());
                    }
                }
            }
        }
        ExceptionAngleUtils exceptionAngleUtils =new ExceptionAngleUtils(getActivity(),manageExceptionparameter, new ExceptionAngleUtils.IexceptionAlertItem() {
            @Override
            public void AlertItemClick(AlertCountInfo alertCountInfo) {
                if (alertCountInfo != null) {
                    try {
                       int resultInteger = Integer.parseInt(alertCountInfo.getNoDealCount());
                        if (resultInteger!= 0)
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
        });

    }

    public ManageExceptionHaveDoneListFragment getmHaveDoneListFragment() {
        return manageExceptionHaveDoneListFragment;
    }

    @Override
    public void callBackOpen() {
        if (!mDrawer.isOpened())
            mDrawer.animateOpen();
    }

}
