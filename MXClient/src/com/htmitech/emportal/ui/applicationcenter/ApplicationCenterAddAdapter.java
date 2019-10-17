package com.htmitech.emportal.ui.applicationcenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.app.widget.XCRoundRectImageView;
import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.commonx.base.bitmap.BitmapDisplayConfig;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadCallBack;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadFrom;
import com.htmitech.emportal.R;
import com.htmitech.proxy.doman.RemoveAppss;

import java.util.ArrayList;

/**
 * tony
 * 应用中心添加Adapter
 */
public class ApplicationCenterAddAdapter extends BaseAdapter  {
    private ArrayList<RemoveAppss> appInfoList;
    private Context cotnext;
    private LayoutInflater inflater;

    public ApplicationCenterAddAdapter(Context context,ArrayList<RemoveAppss> appInfoList){
        this.cotnext = context;
        this.appInfoList = appInfoList;
        if(this.appInfoList == null){
            this.appInfoList = new ArrayList<RemoveAppss>();
        }
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<RemoveAppss> appInfoList){
        this.appInfoList = appInfoList;
        if(this.appInfoList == null){
            this.appInfoList = new ArrayList<RemoveAppss>();
        }
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return appInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return appInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        final RemoveAppss mAppInfo = appInfoList.get(position);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_appcenter,null);
            mViewHolder = new ViewHolder();
            mViewHolder.iv_app_center_add = (XCRoundRectImageView) convertView.findViewById(R.id.iv_app_center_add);
            mViewHolder.tv_app_center_name = (TextView) convertView.findViewById(R.id.tv_app_center_name);
            mViewHolder.cb_app_center_select = (CheckBox) convertView.findViewById(R.id.cb_app_center_select);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_app_center_name.setText(mAppInfo.appShortname);
        final ViewHolder finalMViewHolder = mViewHolder;
        mViewHolder.cb_app_center_select.setTag(position);
        mViewHolder.cb_app_center_select.setChecked(mAppInfo.isCheck());
        mViewHolder.cb_app_center_select.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {

                CheckBox cb = (CheckBox)view;
                if(cb.isChecked()){
                    cb.setChecked(true);
                }else {
                    cb.setChecked(false);
                }
                mAppInfo.setCheck(cb.isChecked());
                Log.i("ApplicationCenterAddAdapter", "setCheck: "+cb.isChecked());
                boolean hascheck = false;
                for (int i = 0; i < appInfoList.size(); i++) {
                    if (appInfoList.get(i).isCheck()) {
                        hascheck = true;
                    }
                    if(hascheck){
                       ((ApplicationCenterAddActivity)cotnext).complete.setVisibility(View.VISIBLE);
                    }else {
                        ((ApplicationCenterAddActivity)cotnext).complete.setVisibility(View.GONE);
                    }
                }

            }
        });
        if(mAppInfo.getPicPath() != null && !mAppInfo.getPicPath().endsWith("null")){
//            mViewHolder.iv_app_center_add.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            BitmapUtils.instance().display(mViewHolder.iv_app_center_add,
                    mAppInfo.getPicPath(),
                    new BitmapLoadCallBack<ImageView>() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onLoadCompleted(
                                ImageView container, String uri,
                                Bitmap bitmap,
                                BitmapDisplayConfig config,
                                BitmapLoadFrom from) {
                            container.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onLoadFailed(ImageView container,
                                                 String uri, Drawable drawable) {
                            // TODO Auto-generated method stub
                        }
                    });
        }else{
            mViewHolder.iv_app_center_add.setImageResource(R.drawable.icon_app_centre_normal);
        }

        return convertView;
    }



    public class ViewHolder{
        public XCRoundRectImageView iv_app_center_add;
        public TextView tv_app_center_name;
        public CheckBox cb_app_center_select;

    }
}
