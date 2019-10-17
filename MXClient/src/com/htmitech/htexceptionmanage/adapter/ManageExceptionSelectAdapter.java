package com.htmitech.htexceptionmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.base.MyBaseAdapter;
import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.emportal.R;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;

import java.util.List;

/**
 * Created by htrf-pc on 2017/4/25.
 */
public class ManageExceptionSelectAdapter extends MyBaseAdapter<AppInfo> {
    private LayoutInflater lInflater;
    private AppliationCenterDao mAppliationCenterDao;
    public ManageExceptionSelectAdapter(List<AppInfo> datas, Context context, AppliationCenterDao mAppliationCenterDao) {
        super(datas, context);
        lInflater = LayoutInflater.from(context);
        this.mAppliationCenterDao = mAppliationCenterDao;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;
        AppInfo appInfo = (AppInfo) getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.center_gridview_item, null);
            holder.textView = (TextView) convertView
                    .findViewById(R.id.textview_griditem);
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.imageview_griditem);
            holder.checkBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox_griditem);
            holder.imageView.setTag(holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String bitmapUrl = appInfo.getPicture_normal();

        if (bitmapUrl == null || "".equals(bitmapUrl))
            holder.imageView.setImageResource(R.drawable.pictures_no);
        else
            BitmapUtils.instance().display(holder.imageView, bitmapUrl);
        holder.checkBox.setChecked(appInfo.getType_flag() == 1);
        holder.textView.setText(appInfo.getApp_name());
        holder.checkBox.setOnClickListener(new CheckChildOnClick(appInfo,holder.checkBox));

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        CheckBox checkBox;
        TextView textView;

    }


    public class CheckChildOnClick implements View.OnClickListener{
        public AppInfo appInfo;
        public CheckBox checkBox;
        public CheckChildOnClick(AppInfo appInfo,CheckBox checkBox){
            this.appInfo = appInfo;
            this.checkBox = checkBox;
        }
        @Override
        public void onClick(View v) {
            checkBox.setChecked(checkBox.isChecked());
            if(checkBox.isChecked()){
                mAppliationCenterDao.updateChildTypeFlag(appInfo.getApp_id() + "","1");
                appInfo.setType_flag(1);
            }else{
                mAppliationCenterDao.updateChildTypeFlag(appInfo.getApp_id() + "","0");
                appInfo.setApk_flag(-1);
            }
        }
    }

}
