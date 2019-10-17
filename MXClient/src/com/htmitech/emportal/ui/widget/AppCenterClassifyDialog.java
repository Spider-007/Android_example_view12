package com.htmitech.emportal.ui.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.applicationcenter.ApplicationCenterActivity;
import com.htmitech.emportal.ui.applicationcenter.ApplicationDown;
import com.htmitech.emportal.ui.applicationcenter.DragAdapter;
import com.htmitech.photoselector.util.AnimationUtil;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.util.FileSizeUtil;

import java.util.ArrayList;
import java.util.List;


public class AppCenterClassifyDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private LinearLayout layout_search_no;
    private Display display;
    private GridView gridView;
    private AppInfo mAppInfo;
    private TextView title;
    private ProxyDealApplicationPlugin mProxyDealApplication;
    private DragAdapter adapter;
    private View views;
    private ArrayList<AppInfo> classifyAppInfo;
    private String prentAppId;
    public AppCenterClassifyDialog(Context context, View views, ArrayList<AppInfo> classifyAppInfo,String prentAppId) {
        this.context = context;
        this.prentAppId = prentAppId;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        this.views = views;
        this.classifyAppInfo = classifyAppInfo;
        display = windowManager.getDefaultDisplay();
    }


    public AppCenterClassifyDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.ht_app_center_classify_dialog, null);
        gridView = (GridView) view.findViewById(R.id.gradview);
        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        layout_search_no = (LinearLayout) view.findViewById(R.id.layout_search_no);
        title = (TextView) view.findViewById(R.id.title);

//		if (views != null) {
//
//			AnimatorSet animatorSet = new AnimatorSet();//组合动画
//
//			ObjectAnimator animY = ObjectAnimator.ofFloat(views, "scaleY", 1f, 1.4f, 1f);
//			ObjectAnimator animX = ObjectAnimator.ofFloat(views, "scaleX", 1f, 1.4f, 1f);
//			animatorSet.setDuration(500);
//			animatorSet.setInterpolator(new DecelerateInterpolator());
//			animatorSet.play(animY).with(animX);//两个动画同时开始
//			animatorSet.start();
//		}
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(view);
//		ColorDrawable dw = new ColorDrawable(0x000000);
//		dw.setAlpha(100);
//		dialog.getWindow().setBackgroundDrawable(dw);
        adapter = new DragAdapter(context, new ArrayList<AppInfo>());
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                stopAnimator();
                dialog.cancel();
            }
        });
//		adapter.setCallBackRemove(context);
//		dialog.setCanceledOnTouchOutside(true);
        //设置SelectPicPopupWindow弹出窗体的背景
        gridView.setAdapter(adapter);
        mProxyDealApplication = new ProxyDealApplicationPlugin(context);
        ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
                // TODO Auto-generated method stub
                mAppInfo = (AppInfo) parent.getItemAtPosition(position);

                try {
                    int success = mProxyDealApplication.applicationCenterProxy(mAppInfo);

                    switch (success) {
                        case 1: //强制升级以及下载

                            new com.htmitech.pop.AlertDialog(context).builder().setTitle("下载").setMsg("应用名称：" + mAppInfo.getApp_name() + "\n" + "大小：" + mAppInfo.getmAppVersion().getApp_filesize() ).setPositiveButton("下载", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApplicationDown mApplicationDown = new ApplicationDown(context, view, mAppInfo, position);
                                    mApplicationDown.initView();
                                }
                            }).show();
                            break;
                        case 2://可暂时不升级
                            new com.htmitech.pop.AlertDialog(context).builder().setTitle("升级").setMsg("应用名称：" + mAppInfo.getApp_name() + "\n" + "大小：" + mAppInfo.getmAppVersion().getApp_filesize() ).setPositiveButton("下载", new View.OnClickListener() {
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
                        default:
                            stopAnimator();
                            dialog.dismiss();
                            break;
                    }

                } catch (NotApplicationException e) {
                    e.printStackTrace();
                }
            }
        });


        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), (int) (display
                .getHeight() * 0.6)));
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//
//				final ArrayList<AppInfo> appInfos = (ArrayList<AppInfo>) getList();
//				((Activity) context).runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//
//					}
//				});
//			}
//		}).start();
        if (classifyAppInfo == null || classifyAppInfo.size() == 0) {
            layout_search_no.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        } else {
            layout_search_no.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            adapter.setData(classifyAppInfo);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppliationCenterDao m = new AppliationCenterDao(context);
                classifyAppInfo = m.selectClassify(prentAppId);

                if (mProxyDealApplication != null) {
                    for (AppInfo AppInfos : classifyAppInfo) {
                        mProxyDealApplication.interceptAPK(AppInfos);
                    }
                }
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (classifyAppInfo == null || classifyAppInfo.size() == 0) {
                            layout_search_no.setVisibility(View.VISIBLE);
                            gridView.setVisibility(View.GONE);
                        } else {
                            layout_search_no.setVisibility(View.GONE);
                            gridView.setVisibility(View.VISIBLE);
                            adapter.setData(classifyAppInfo);
                        }

                        adimator();
                    }
                });
            }
        }).start();


        return this;
    }

    public void stopAnimator(){
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator animY = ObjectAnimator.ofFloat(views, "scaleY", 0.9f, 1f, 1f);
        ObjectAnimator animX = ObjectAnimator.ofFloat(views, "scaleX", 0.9f, 1f, 1f);
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(animY).with(animX);//两个动画同时开始
        animatorSet.start();
    }


    public void adimator(){
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator animY = ObjectAnimator.ofFloat(views, "scaleY", 1f, 0.9f, 0.9f);
        ObjectAnimator animX = ObjectAnimator.ofFloat(views, "scaleX", 1f, 0.9f, 0.9f);
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(animY).with(animX);//两个动画同时开始
        animatorSet.start();
    }

    public AppCenterClassifyDialog setTitle(String name) {
        title.setText(name);
        return this;
    }

    private List<AppInfo> getList() {
        // TODO Auto-generated method stub
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(context);
        ArrayList<AppInfo> appInfos = mAppliationCenterDao.getApplicationCenterInfo();
        ArrayList<AppInfo> temp = new ArrayList<AppInfo>();
        temp.add(appInfos.get(appInfos.size() - 2));
        temp.add(appInfos.get(appInfos.size() - 3));
        temp.add(appInfos.get(appInfos.size() - 4));
        return temp;
    }

    public void show() {

//		gradview.setCallBackApplicationCenter(this);
        try {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
