package com.htmitech.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.R;
import com.htmitech.proxy.doman.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by htrf-pc on 2017/8/22.
 */
public class ClassifyView extends LinearLayout {
    private Context context;
    private int width = 15;
    private int hight = 15;
    public ClassifyView(Context context) {
        super(context);
        this.context = context;
    }

    public ClassifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ClassifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
    public void initAppInfo(List<AppInfo> list,int width,int hight){
        this.width = width;
        this.hight = hight;
        initAppInfo(list);
    }
    public void initAppInfo(List<AppInfo> list){
        ViewHodler mViewHodler = null;
        View classify_view = null;
        if(list == null){
            list = new ArrayList<AppInfo>();
        }
        this.removeAllViews();
        for(int i = 0; i < list.size() ; i++){
            if(i % 3 == 0){
                classify_view = LayoutInflater.from(context).inflate(R.layout.classify_view,null);
                mViewHodler = new ViewHodler();
                mViewHodler.image1 = (ImageView) classify_view.findViewById(R.id.image1);
                mViewHodler.image2 = (ImageView) classify_view.findViewById(R.id.image2);
                mViewHodler.image3 = (ImageView) classify_view.findViewById(R.id.image3);

                LinearLayout.LayoutParams layoutParms1 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(context, width), DeviceUtils.dip2px(context, hight));
                LinearLayout.LayoutParams layoutParms2 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(context, width), DeviceUtils.dip2px(context, hight));
                LinearLayout.LayoutParams layoutParms3 = new LinearLayout.LayoutParams(DeviceUtils.dip2px(context, width), DeviceUtils.dip2px(context, hight));
                layoutParms1.setMargins(0,DeviceUtils.dip2px(context, 5),0,0);
                layoutParms2.setMargins(DeviceUtils.dip2px(context, 5),DeviceUtils.dip2px(context, 5),0,0);
                layoutParms3.setMargins(DeviceUtils.dip2px(context, 5),DeviceUtils.dip2px(context, 5),0,0);
                mViewHodler.image1.setLayoutParams(layoutParms1);
                mViewHodler.image2.setLayoutParams(layoutParms2);
                mViewHodler.image3.setLayoutParams(layoutParms3);

                this.addView(classify_view);
            }



            if(i % 3 == 0){

                Glide.with(context).load(list.get(i).getPicture_normal()).placeholder(R.drawable.icon_app_centre_normal).error(R.drawable.icon_app_centre_normal).transform(new GlideRoundTransform(context)).into(mViewHodler.image1);

            }else if(i % 3 == 1){

                Glide.with(context).load(list.get(i).getPicture_normal()).placeholder(R.drawable.icon_app_centre_normal).error(R.drawable.icon_app_centre_normal).transform(new GlideRoundTransform(context)).into(mViewHodler.image2);

            }else  if(i % 3 == 2){

                Glide.with(context).load(list.get(i).getPicture_normal()).placeholder(R.drawable.icon_app_centre_normal).error(R.drawable.icon_app_centre_normal).transform(new GlideRoundTransform(context)).into(mViewHodler.image3);

            }
            if(i >= 8){
                break;
            }

        }
    }

    public  void init(ArrayList<Integer> list){
        ViewHodler mViewHodler = null;
        View classify_view = null;
        for(int i = 0; i < list.size() ; i++){
            if(i % 3 == 0){
                classify_view = LayoutInflater.from(context).inflate(R.layout.classify_view,null);
                mViewHodler = new ViewHodler();
                mViewHodler.image1 = (ImageView) classify_view.findViewById(R.id.image1);
                mViewHodler.image2 = (ImageView) classify_view.findViewById(R.id.image2);
                mViewHodler.image3 = (ImageView) classify_view.findViewById(R.id.image3);
                this.addView(classify_view);
            }
            if(i % 3 == 0){
                mViewHodler.image1.setImageResource(list.get(i));
            }else if(i % 3 == 1){
                mViewHodler.image2.setImageResource(list.get(i));
            }else  if(i % 3 == 2){
                mViewHodler.image3.setImageResource(list.get(i));
            }
            if(i >= 8){
                break;
            }

        }
    }

    public class ViewHodler{
        public ImageView image1;
        public ImageView image2;
        public ImageView image3;
    }
}
