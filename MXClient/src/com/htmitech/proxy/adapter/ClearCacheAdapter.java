package com.htmitech.proxy.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.app.widget.XCRoundRectImageView;
import com.htmitech.base.MyBaseAdapter;
import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.commonx.base.bitmap.BitmapDisplayConfig;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadCallBack;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadFrom;
import com.htmitech.emportal.R;
import com.htmitech.proxy.doman.ClearCacheDoman;
import com.htmitech.proxy.doman.EmpCisInfo;

import java.util.List;

public class ClearCacheAdapter extends MyBaseAdapter<ClearCacheDoman>{

    private LayoutInflater lInflater;
    private List<ClearCacheDoman> datas;
    public ClearCacheAdapter(List<ClearCacheDoman> datas, Context context) {
        super(datas, context);
        lInflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;
        ClearCacheDoman clearCacheDoman = (ClearCacheDoman) getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.activity_clear_adapter, null);
            holder.tv_clear_text_name = (TextView) convertView
                    .findViewById(R.id.tv_clear_text_name);
            holder.tv_clear_text_size = (TextView) convertView
                    .findViewById(R.id.tv_clear_text_size);
            holder.tv_danwei = (TextView) convertView
                    .findViewById(R.id.tv_danwei);
            holder.cb_clear_docfile = (CheckBox) convertView
                    .findViewById(R.id.cb_clear_docfile);
            holder.icon_iv = (ImageView) convertView
                    .findViewById(R.id.icon_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(getContext()).load(clearCacheDoman.getAppInfo().getPicture_normal()).placeholder(R.drawable.icon_app_centre_normal).error(R.drawable.icon_app_centre_normal).transform(new GlideRoundTransform(getContext())).into( holder.icon_iv);

        holder.tv_clear_text_name.setText(clearCacheDoman.getAppInfo().getApp_shortname());
        holder.tv_clear_text_size.setText(clearCacheDoman.getShowTextSize() + "");
        holder.tv_danwei.setText(clearCacheDoman.getDanwei());
        holder.cb_clear_docfile.setChecked(clearCacheDoman.isDelete());
        return convertView;
    }


    public void setData(List<ClearCacheDoman> datas){
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public void remove(int postion){
        if(datas.size() > 0){
            datas.remove(postion);
        }
        notifyDataSetChanged();
    }

    class ViewHolder {
        ImageView icon_iv;
        TextView tv_clear_text_name;
        TextView tv_clear_text_size;
        TextView tv_danwei;
        CheckBox cb_clear_docfile;
    }
}
