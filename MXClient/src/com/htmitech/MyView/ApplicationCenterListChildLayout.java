package com.htmitech.MyView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.UserMessageScrollView;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.applicationcenter.ApplicationDown;
import com.htmitech.emportal.ui.applicationcenter.DragAdapter;
import com.htmitech.emportal.ui.applicationcenter.ICallBackAppCenterLong;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.interfaces.CallBackCenterOnScroll;
import com.htmitech.proxy.interfaces.CallBackRemove;
import com.htmitech.proxy.util.FileSizeUtil;
import com.htmitech.unit.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import htmitech.com.componentlibrary.unit.RefreshTotal;

/**
 * tony
 * 应用的子分类的展示
 */
public class ApplicationCenterListChildLayout extends LinearLayout implements CallBackCenterOnScroll {
    private Context context;
    private View appCenterListChildLayout;
    private LinearLayout ll_child_top;
    private ViewHolder mViewHolder;
    private LayoutInflater inflater;
    private Animation mExpandAnimation;
    private Animation mCollapseAnimation;
    private ArrayList<DragAdapter> dragAdapterArr;
    private ArrayList<ViewHolder> mViewHolderArr;
    private ArrayList<TopSth> topList;
    private ICallBackAppCenterLong mICallBackAppCenterLong;
    private UserMessageScrollView mUserMessageScrollView;
    public int ITEM_HIGHT = 75;
    private ProxyDealApplicationPlugin mProxyDealApplication;
    private LinkedHashMap<String, ArrayList<AppInfo>> centerListChildMap;
    private boolean isOnLong = true;
    public int measuredHeight = 0;
    public int height = 0;
    private CallBackRemove mCallBackRemove;
    private float number;
    public void setCallBackRemove(CallBackRemove mCallBackRemove) {
        this.mCallBackRemove = mCallBackRemove;
    }


    public HashMap<String, ArrayList<AppInfo>> getCenterListChildMap() {
        return centerListChildMap;
    }

    public void setUserMessageScrollView(UserMessageScrollView mUserMessageScrollView) {
        this.mUserMessageScrollView = mUserMessageScrollView;
    }

    public ApplicationCenterListChildLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ApplicationCenterListChildLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public ApplicationCenterListChildLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    public void setICallBackAppCenterLong(ICallBackAppCenterLong mICallBackAppCenterLong) {
        this.mICallBackAppCenterLong = mICallBackAppCenterLong;
    }

    public void initView() {
        inflater = LayoutInflater.from(context);
    }

    public void refreshData(final List<AppInfo> appInfoArrayList){
        new Thread(new Runnable() {
            @Override
            public void run() {
                centerListChildMap = ancapAppInfo((ArrayList<AppInfo>) appInfoArrayList);
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int index = 0;
                        for (String key : centerListChildMap.keySet()) {
                            dragAdapterArr.get(index).setData(centerListChildMap.get(key));
                            index++;
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 入口
     *
     * @param appInfoArrayList
     */

    public void initData(List<AppInfo> appInfoArrayList) {
        if(dragAdapterArr != null){
            refreshData(appInfoArrayList);
            return;
        }
        this.removeAllViews();
        measuredHeight = 0;
        height = 0;
        mProxyDealApplication = new ProxyDealApplicationPlugin(context);
        centerListChildMap = ancapAppInfo((ArrayList<AppInfo>) appInfoArrayList);
        dragAdapterArr = new ArrayList<DragAdapter>();
        mViewHolderArr = new ArrayList<ViewHolder>();
        topList = new ArrayList<TopSth>();
        mExpandAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.expand);


        mCollapseAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.collapse);
        int index = 0;
        for (String key : centerListChildMap.keySet()) {
            initChildView(index);
            initChildData(key, centerListChildMap.get(key), index);
            index++;
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    for (String key : centerListChildMap.keySet()) {
//                        setAngleNumber(centerListChildMap.get(key));
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        mExpandAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.expand);

        mCollapseAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.collapse);


//        mUserMessageScrollView.setOnScrollListener(new UserMessageScrollView.OnScrollListener() {
//            @Override
//            public void onScroll(int l, int t, int oldl, int oldt) {
//                ApplicationCenterListChildLayout.this.t = t;
//            }
//        });


    }

    /**
     * 初始化自View的值
     *
     * @param key
     * @param childValue
     */

    public void initChildData(String key, ArrayList<AppInfo> childValue, int index) {
        mViewHolder.tv_app_center_classify_name.setText(key);
        mViewHolder.iv_app_center_classify_jt.setImageResource(R.drawable.btn_angle_up_circle);
        DragAdapter adapter = new DragAdapter(context, childValue);
        adapter.setRemoveIndwx(index);
        adapter.setCallBackRemove(new CallBackRemoveC());
        mViewHolder.gradview.setAdapter(adapter);
        mViewHolder.gradview.setNumColumns(BookInit.getInstance().getApc_style());
        mViewHolder.gradview.setStruesHeight(measuredHeight + height);

        topList.add(new TopSth(height, index, ll_child_top));
        mViewHolder.gradview.measure(0, 0);
        height = height + mViewHolder.gradview.getMeasuredHeight() + measuredHeight;
        mViewHolder.gradview.setICallBackAppCenterLong(new CallBackAppCenterLong());
        number = (childValue.size() / (float) BookInit.getInstance().getApc_style());
        mViewHolder.gradview.setOnItemClickListener(new COnItemClickListener());
//        adapter.setData(childValue);
        //暂时给注释掉了
//        if ("更多应用".equals(key)) {
//            mViewHolder.iv_app_center_classify_jt.setVisibility(GONE);
//        }
        mViewHolder.iv_app_center_classify_jt.setTag("down");
        mViewHolder.iv_app_center_classify_jt.setOnClickListener(new ClassifyJtLister((index + 1),mViewHolder.iv_app_center_classify_jt, mViewHolder.rl_child, mViewHolder.rl_child.getHeight(), Math.ceil(number)));

        ll_child_top.setTag(key);
        dragAdapterArr.add(adapter);
    }
    //吸顶暂时屏蔽
    @Override
    public void onScroll(ScrollView scrollView,View view, int t) {

        if(topList == null){
            return;
        }
        int i = 0;
        for(TopSth mTopSth : topList){
            if(mTopSth.top <= t){
                view.setVisibility(VISIBLE);
                TextView name = (TextView) view.findViewById(R.id.tv_app_center_classify_name);
                name.setText(mViewHolderArr.get(mTopSth.index).tv_app_center_classify_name.getText().toString());
                ImageView iv_app_center_classify_jt = (ImageView) view.findViewById(R.id.iv_app_center_classify_jt);
                iv_app_center_classify_jt.setImageResource(!mViewHolderArr.get(mTopSth.index).rl_child.isShown() ? R.drawable.btn_angle_down_circle : R.drawable.btn_angle_up_circle);
                iv_app_center_classify_jt.setOnClickListener(new ClassifyJtLister(scrollView,i + 1,iv_app_center_classify_jt,mViewHolderArr.get(mTopSth.index).iv_app_center_classify_jt, mViewHolderArr.get(mTopSth.index).rl_child, mViewHolderArr.get(mTopSth.index).rl_child.getHeight(), Math.ceil(number)));
            }
            i++;
        }

        if(t == 0){
            view.setVisibility(GONE);
        }

    }

    public class ClassifyJtLister implements OnClickListener {
        public ImageView iv_app_center_classify_jt;
        public ImageView iv_app_center_classify_jt0;
        public RelativeLayout rl_child;
        public int height;
        public int number;
        public int index;
        public ScrollView scrollView;
        public ClassifyJtLister(int index,ImageView iv_app_center_classify_jt, RelativeLayout rl_child, int height, double number) {
            this.iv_app_center_classify_jt = iv_app_center_classify_jt;
            this.rl_child = rl_child;
            this.height = height;
            this.number = (int) number;
            this.index = index;
        }

        public ClassifyJtLister(ScrollView scrollView,int index,ImageView iv_app_center_classify_jt0,ImageView iv_app_center_classify_jt, RelativeLayout rl_child, int height, double number) {
            this.iv_app_center_classify_jt = iv_app_center_classify_jt;
            this.iv_app_center_classify_jt0 = iv_app_center_classify_jt0;
            this.scrollView = scrollView;
            this.index = index;
            this.rl_child = rl_child;
            this.height = height;
            this.number = (int) number;
        }

        @Override
        public void onClick(View view) {

            iv_app_center_classify_jt.setImageResource(rl_child.isShown() ? R.drawable.btn_angle_down_circle : R.drawable.btn_angle_up_circle);
            if(iv_app_center_classify_jt0 != null){
                iv_app_center_classify_jt0.setImageResource(rl_child.isShown() ? R.drawable.btn_angle_down_circle : R.drawable.btn_angle_up_circle);
            }
            rl_child.setVisibility(rl_child.isShown() ? View.GONE : View.VISIBLE);

            if(rl_child.isShown()){
                for(int i = index ; i < topList.size() ; i++){
                    topList.get(i).top +=rl_child.getMeasuredHeight();
                }

            }else{
                for(int i = index ; i < topList.size() ; i++){
                    topList.get(i).top -=rl_child.getMeasuredHeight();
                }
            }

            if(scrollView != null){
                scrollView.scrollTo(0,topList.get(index - 1).top);
            }
        }
    }

    /**
     * 初始化子View
     */
    public void initChildView(int index) {
        appCenterListChildLayout = inflater.inflate(R.layout.activity_application_center_list_child, null);
        mViewHolder = new ViewHolder();
        mViewHolder.iv_app_center_classify_jt = (ImageView) appCenterListChildLayout.findViewById(R.id.iv_app_center_classify_jt);
        ll_child_top = (LinearLayout) appCenterListChildLayout.findViewById(R.id.ll_child_top);
        mViewHolder.tv_app_center_classify_name = (TextView) appCenterListChildLayout.findViewById(R.id.tv_app_center_classify_name);
        mViewHolder.gradview = (com.htmitech.emportal.ui.applicationcenter.DragGrid) appCenterListChildLayout.findViewById(R.id.gradview);
        mViewHolder.rl_child = (RelativeLayout) appCenterListChildLayout.findViewById(R.id.rl_child);
        measuredHeight = DensityUtil.dip2px(context, 50);
        mViewHolder.gradview.setIsOnLong(isOnLong);
        mViewHolderArr.add(mViewHolder);
        this.addView(appCenterListChildLayout);
    }

    /**
     * 重新封装APPInfo
     *
     * @param appInfoArrayList
     */
    public LinkedHashMap<String, ArrayList<AppInfo>> ancapAppInfo(ArrayList<AppInfo> appInfoArrayList) {
        ArrayList<AppInfo> centerListChildAppInfo = new ArrayList<AppInfo>();
        LinkedHashMap<String, ArrayList<AppInfo>> centerListChildMap = new LinkedHashMap<String, ArrayList<AppInfo>>();
        for (AppInfo appInfo : appInfoArrayList) {
            if (appInfo.getApp_type() == 7) {
                if (appInfo.getClassifyAppInfo() != null && appInfo.getClassifyAppInfo().size() > 0) {
                    centerListChildMap.put(appInfo.getApp_shortname(), appInfo.getClassifyAppInfo());
                }
            } else {
                centerListChildAppInfo.add(appInfo);
            }
        }
        if(centerListChildAppInfo != null && centerListChildAppInfo.size() != 0){
            centerListChildMap.put("更多应用", centerListChildAppInfo);
        }

        return centerListChildMap;
    }

    /**
     *
     */
    public class ViewHolder {
        public TextView tv_app_center_classify_name;
        public ImageView iv_app_center_classify_jt;
        public com.htmitech.emportal.ui.applicationcenter.DragGrid gradview;
        public RelativeLayout rl_child;
    }

    public class COnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long l) {
            final AppInfo mAppInfo = (AppInfo) adapterView.getItemAtPosition(position);
            mAppInfo.setView(mUserMessageScrollView);
            if (mAppInfo.getApk_flag() != 3) {
                RefreshTotal.addReshActivity();
            }
            try {
                int success = mProxyDealApplication.applicationCenterProxy(mAppInfo);
                switch (success) {
                    case 1: //强制升级以及下载
                        new AlertDialog(context).builder().setTitle("下载").setMsg("应用名称：" + mAppInfo.getApp_name() + "\n" + "大小：" + mAppInfo.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ApplicationDown mApplicationDown = new ApplicationDown(context, view, mAppInfo, position);
                                mApplicationDown.initView();
                            }
                        }).show();
                        break;
                    case 2://可暂时不升级
                        new AlertDialog(context).builder().setTitle("升级").setMsg("应用名称：" + mAppInfo.getApp_name() + "\n" + "大小：" + mAppInfo.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ApplicationDown mApplicationDown = new ApplicationDown(context, view, mAppInfo, position);
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
        }
    }

    public class CallBackRemoveC implements CallBackRemove {

        @Override
        public void callBackRemoveApp(AppInfo mAppInfo, int ponstion, int classifyIndex) {
            if (mCallBackRemove != null) {
                mCallBackRemove.callBackRemoveApp(mAppInfo, ponstion, classifyIndex);
            }

        }
    }

    public class CallBackAppCenterLong implements ICallBackAppCenterLong {

        @Override
        public void callCenterLong(boolean isLong) {
            if (isLong) {
                isDelete(true);
            }
            if (mICallBackAppCenterLong != null)
                mICallBackAppCenterLong.callCenterLong(isLong);
        }
    }

    public void delete(AppInfo appinfo, int index, int removeIndex) {
        if (dragAdapterArr == null) {
            return;
        }
        if (dragAdapterArr.get(removeIndex) != null) {
            dragAdapterArr.get(removeIndex).deletInfo(appinfo, index);
            if (dragAdapterArr.get(removeIndex).getCount() == 0) {
                this.removeViewAt(removeIndex);
            }
        }
    }

    public void isDelete(boolean isD) {
        if (mViewHolderArr == null || dragAdapterArr == null) {
            return;
        }
        for (int i = 0; i < mViewHolderArr.size(); i++) {
            dragAdapterArr.get(i).setisDelete(isD);
            mViewHolderArr.get(i).gradview.setIsDrag(isD);
            if (!isD) {
                mViewHolderArr.get(i).gradview.refresh();
            }
        }
    }

    /**
     * 是否允许拖动
     *
     * @param isOnLong
     */
    public void isOnLong(boolean isOnLong) {
        this.isOnLong = isOnLong;
    }


    public class TopSth {
        public int top;
        public int index;
        public LinearLayout ll_child_top;
        public TopSth(int top,int index,LinearLayout ll_child_top){
            this.top = top;
            this.index = index;
            this.ll_child_top = ll_child_top;
        }
        @Override
        public boolean equals(Object o) {
            if (o instanceof TopSth) {
                TopSth topSth = (TopSth) o;
                return topSth.top == top;
            }
            return super.equals(o);
        }
    }

}
