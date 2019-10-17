package com.htmitech.emportal.ui.appcenter.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.ClassifyView;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.homepage.HomeGridSysle;

import java.util.ArrayList;

import com.htmitech.htworkflowformpluginnew.util.BadgeAllUnit;


public class HomePageGridAdapter extends BaseAdapter {
    public Activity context;
    private ArrayList<HomeGridSysle.BinnerBitmapMessage> mBinnerBitmapMessageList;
    public LayoutInflater inflater;
    public HtmitechApplication app;
    /**
     * 控制的postion
     */
    private int holdPosition;
    /**
     * 是否显示底部的ITEM
     */
    private boolean isItemShow = false;
    public boolean isChanged = false;
    /**
     * 是否可见
     */
    boolean isVisible = true;

    /**
     * 控制的postion
     */
    public HomePageGridAdapter(Activity context, ArrayList<HomeGridSysle.BinnerBitmapMessage> mBinnerBitmapMessageList, HtmitechApplication app) {
        this.context = context;
        this.mBinnerBitmapMessageList = mBinnerBitmapMessageList;
        inflater = LayoutInflater.from(context);
        this.app = app;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mBinnerBitmapMessageList.size();
    }

    @Override
    public HomeGridSysle.BinnerBitmapMessage getItem(int arg0) {
        // TODO Auto-generated method stub
        return mBinnerBitmapMessageList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        HomeGridSysle.BinnerBitmapMessage mBinnerBitmapMessage = mBinnerBitmapMessageList.get(arg0);
        ViewHolder mViewHolder = null;
        if (arg1 == null) {
            mViewHolder = new ViewHolder();
            arg1 = inflater.inflate(R.layout.activity_application_center_adapter, null);

            mViewHolder.image = (ImageView) arg1.findViewById(R.id.icon_iv);
            mViewHolder.tv_name = (TextView) arg1.findViewById(R.id.name_tv);
            mViewHolder.angle_nulber = (TextView) arg1.findViewById(R.id.angle_nulber);
            mViewHolder.class_angle_nulber = (TextView) arg1.findViewById(R.id.class_angle_nulber);
            mViewHolder.img_no_installed = (ImageView) arg1.findViewById(R.id.img_no_installed);
            mViewHolder.classify_view = (ClassifyView) arg1.findViewById(R.id.classify_view);
            mViewHolder.iv_image_layout = (RelativeLayout) arg1.findViewById(R.id.iv_image_layout);
            arg1.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) arg1.getTag();
        }
        if (null != mBinnerBitmapMessage) {
            mViewHolder.angle_nulber.setTag(mBinnerBitmapMessage.appid);
            mBinnerBitmapMessage.angle_nulber = mViewHolder.angle_nulber;
            mViewHolder.tv_name.setText(mBinnerBitmapMessage.mAppInfo.getApp_shortname());

            mViewHolder.angle_nulber.setText(mBinnerBitmapMessage.number);
            if (!mBinnerBitmapMessage.number.equals("") && mBinnerBitmapMessage.number.contains("+")) {
                RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 15));
                layoutParms.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
            }

            mViewHolder.angle_nulber.setVisibility(mBinnerBitmapMessage.numberFlag);

//		if(mBinnerBitmapMessage.mAppInfo.getPicture_normal() != null && !mBinnerBitmapMessage.mAppInfo.getPicture_normal().equals("")){
////			mViewHolder.image.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//			BitmapUtils.instance().display(mViewHolder.image,
//					mBinnerBitmapMessage.mAppInfo.getPicture_normal(),
//					new BitmapLoadCallBack<ImageView>() {
//						@SuppressLint("NewApi")
//						@Override
//						public void onLoadCompleted(
//								ImageView container, String uri,
//								Bitmap bitmap,
//								BitmapDisplayConfig config,
//								BitmapLoadFrom from) {
//							container.setImageBitmap(bitmap);
//						}
//
//						@Override
//						public void onLoadFailed(ImageView container,
//												 String uri, Drawable drawable) {
//							// TODO Auto-generated method stub
//						}
//					});
//		}else{
//			mViewHolder.image.setImageResource(R.drawable.icon_app_centre_normal);
//		}

            Glide.with(context).load(mBinnerBitmapMessage.mAppInfo.getPicture_normal()).placeholder(R.drawable.icon_app_centre_normal).error(R.drawable.icon_app_centre_normal).transform(new GlideRoundTransform(context)).into(mViewHolder.image);

            if (mBinnerBitmapMessage.mAppInfo.getApk_flag() == 2) {
                mViewHolder.img_no_installed.setVisibility(View.VISIBLE);
                mViewHolder.img_no_installed.setImageResource(R.drawable.img_no_installed);
            } else if (mBinnerBitmapMessage.mAppInfo.getApk_flag() == 1) {
                mViewHolder.img_no_installed.setVisibility(View.VISIBLE);
                mViewHolder.img_no_installed.setImageResource(R.drawable.img_new);
            } else {
                mViewHolder.img_no_installed.setVisibility(View.GONE);
            }
        }


        if (null != mBinnerBitmapMessage && null != mBinnerBitmapMessage.mAppInfo && mBinnerBitmapMessage.mAppInfo.getApp_type() == 7) {
//            holderView.classify_view.getBackground().setAlpha(120);
            mViewHolder.classify_view.setVisibility(View.VISIBLE);

            mViewHolder.classify_view.initAppInfo(mBinnerBitmapMessage.mAppInfo.getClassifyAppInfo());
//			mViewHolder.class_angle_nulber.setVisibility(View.GONE);
//            holderView.class_angle_nulber.setText("20");
            mViewHolder.iv_image_layout.setVisibility(View.GONE);

            if (TextUtils.isEmpty(BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo )) || BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo ).equals("0")) {

                mViewHolder.class_angle_nulber.setVisibility(View.GONE);

            } else {

                mViewHolder.class_angle_nulber.setVisibility(View.VISIBLE);
                if (Integer.parseInt(BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo)) > 99) {
                    RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 15));
                    layoutParms.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    mViewHolder.class_angle_nulber.setLayoutParams(layoutParms);
                    mViewHolder.class_angle_nulber.setText("99+");
                }else{
                    if (!TextUtils.isEmpty(BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo)) && !BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo).equals("0")) {
                        mViewHolder.class_angle_nulber.setText(BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo) + "");
                        mViewHolder.angle_nulber.setVisibility(View.VISIBLE);
                    }else{
                        mViewHolder.angle_nulber.setVisibility(View.GONE);
                    }

                }


            }
        } else {
            mViewHolder.classify_view.setVisibility(View.GONE);
            mViewHolder.iv_image_layout.setVisibility(View.VISIBLE);

            if (mBinnerBitmapMessage.mAppInfo != null && TextUtils.isEmpty(BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo.getApp_id() + "")) || BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo.getApp_id() + "").equals("0")) {

                mViewHolder.angle_nulber.setVisibility(View.GONE);

            } else {
                if(mBinnerBitmapMessage.mAppInfo != null){
                    mViewHolder.angle_nulber.setVisibility(View.VISIBLE);
                    if (Integer.parseInt(BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo.getApp_id() + "")) > 99) {
                        RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 15));
                        layoutParms.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        mViewHolder.angle_nulber.setGravity(Gravity.RIGHT);
                        mViewHolder.angle_nulber.setLayoutParams(layoutParms);
                        mViewHolder.angle_nulber.setText("99+");
                    }else{
                        if (!TextUtils.isEmpty(BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo)) && !BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo).equals("0")) {
                            mViewHolder.angle_nulber.setText(BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.mAppInfo) + "");
                            mViewHolder.angle_nulber.setVisibility(View.VISIBLE);
                        }else{
                            mViewHolder.angle_nulber.setVisibility(View.GONE);
                        }
                    }
                }



            }
        }
//		image.setAnimation(AnimationUtils.loadAnimation(context, R.anim.zt_gridview_item_anim));
//		image.setOnClickListener(new ImageOnclickListener(mBinnerBitmapMessage.appid));
        return arg1;
    }

    class ViewHolder {
        private ImageView image;
        private TextView tv_name;
        private TextView angle_nulber;
        private TextView class_angle_nulber;
        private ImageView img_no_installed;
        private ClassifyView classify_view;
        private RelativeLayout iv_image_layout;
    }

    /**
     * 拖动变更排序
     */
    public void exchange(int dragPostion, int dropPostion) {
        HomeGridSysle.BinnerBitmapMessage dragItem = getItem(dragPostion);
        holdPosition = dropPostion;
        if (dragPostion < dropPostion) {
            mBinnerBitmapMessageList.add(dropPostion + 1, dragItem);
            mBinnerBitmapMessageList.remove(dragPostion);
        } else {
            mBinnerBitmapMessageList.add(dropPostion, dragItem);
            mBinnerBitmapMessageList.remove(dragPostion + 1);
        }
        isChanged = true;
        notifyDataSetChanged();
//		mObtainCurrentCount.obtainCount(0);
    }

    /**
     * 显示放下的ITEM
     */
    public void setShowDropItem(boolean show) {
        isItemShow = show;
    }
}
