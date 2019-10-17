package com.htmitech.emportal.ui.applicationcenter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.downmanage.DownTaskHandler;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.ui.widget.ToastInfo;
import com.htmitech.emportal.utils.CompressUtil;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.thread.AnsynHttpRequest;

import net.lingala.zip4j.exception.ZipException;

import java.io.File;


/**
 *  下载应用中心文件
 *  apk
 *  zip文件
 */
public class ApplicationDown implements DownTaskHandler {
        private View view;
        private AppInfo mAppInfo;
        private Context context;
        private TextView layout;
        private CircleProgressView mCircleProgressView;
        private int position;
        private AlphaAnimation   disappearAnimation;
        public ApplicationDown(Context context,View view,AppInfo mAppInfo,int position){
                this.view = view;
                this.mAppInfo = mAppInfo;
                this.context = context;
                this.position = position;
        }

        public void initView(){
                if(view != null) {
                   layout = (TextView) view.findViewById(R.id.tv_back);
                   layout.setVisibility(View.VISIBLE);
                   GradientDrawable myGrad = (GradientDrawable) layout.getBackground();
                   myGrad.setColor(Color.BLACK);
                   layout.setAlpha(0.15f);
                   mCircleProgressView = (CircleProgressView) view.findViewById(R.id.circleProgressbar);
                   mCircleProgressView.setProgress(-1);
                   mCircleProgressView.setVisibility(View.VISIBLE);
                }

                disappearAnimation = new AlphaAnimation(1, 0);
                disappearAnimation.setDuration(500);
                initData();
        }

        /**
         * 获取文件名  直接进行下载并将文件名字传入进去
         */
        public void initData(){
                String filename = mAppInfo.getmAppVersion().getFile_location().substring(mAppInfo.getmAppVersion().getFile_location().lastIndexOf("/") + 1);
                mAppInfo.getmAppVersion().setLocalName(filename);
                File file = new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator + mAppInfo.getApp_id());
                if (!file.exists()) {
                    file.mkdir();
                }
                AnsynHttpRequest.downLoadTask(context, position, mAppInfo.getmAppVersion().getFile_location(), CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER + File.separator + mAppInfo.getApp_id() + File.separator + filename, this);
        }
        @Override
        public void startDown() {

        }

        @Override
        public void onSuccess(int postion) {

                disappearAnimation.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                                mCircleProgressView.setVisibility(View.GONE);
                        }
                });


                layout.setVisibility(View.GONE);
                mCircleProgressView.setVisibility(View.GONE);
                GradientDrawable myGrad = (GradientDrawable) layout.getBackground();
                myGrad.setColor(Color.TRANSPARENT);
                mAppInfo.setIsUpdate(true); //下载完成 表示 可直接进入
                String destination = CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER;
                String h5destination = "";
                h5destination = destination+"/"+mAppInfo.getApp_id();
                if(mAppInfo.getApp_code().equals("SNS_redubang")){
                        h5destination =  CommonSettings.DEFAULT_FOLDER + "/appstore/plugin/apps/HotView/100020";
                        File file = new File(h5destination);
                        if(!file.exists()){
                             file.mkdirs();
                        }
                }
                File srcFile = new File(destination+"/"+mAppInfo.getApp_id()+"/"+mAppInfo.getmAppVersion().getLocalName());
                /**
                 * 如果是zip文件将进行解压 其他格式暂不需要
                 */
                if(mAppInfo.getmAppVersion().getSuffix().endsWith("zip")){
                        try {
                                //下载完成进行解压文件
                           File[] files = CompressUtil.unzip(srcFile, h5destination, "password");
                           srcFile.delete();
                           Log.d("ApplicationDown","应用中心文件解压完成");
                        } catch (ZipException e) {
                                e.printStackTrace();
                        }
                        mAppInfo.setApk_flag(3);

                }

                ProxyDealApplicationPlugin mProxyDealApplication = new ProxyDealApplicationPlugin(context);
                try {
                        mProxyDealApplication.applicationCenterProxy(mAppInfo);
                } catch (NotApplicationException e) {
                        e.printStackTrace();
                }
        }

        @Override
        public void downProgress(int progress, float totalLength) {
                mCircleProgressView.setMaxProgress(totalLength);
                mCircleProgressView.setProgress(progress);
        }

        @Override
        public void onFail(String failMessage) {
                ToastInfo toastInfo = ToastInfo
                        .getInstance(HtmitechApplication.instance());
                toastInfo.setText( failMessage);
                toastInfo.show(Toast.LENGTH_SHORT);
                layout.setVisibility(View.GONE);
                mCircleProgressView.setVisibility(View.GONE);
        }

        @Override
        public void notNetwork() {

        }

}
