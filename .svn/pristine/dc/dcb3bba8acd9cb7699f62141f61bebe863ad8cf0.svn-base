package com.htmitech.video.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.proxy.util.GlideUtil;
import com.htmitech.video.bean.VideoClassifiVideoTypeListBean;

import java.util.List;

/**
 * Created by heyang on 2017-7-5.
 */
public class VideoClassifiListAdapter extends BaseAdapter {

    private Context context;
    private List<VideoClassifiVideoTypeListBean> listClassfifLists;

    public VideoClassifiListAdapter(Context context, List<VideoClassifiVideoTypeListBean> listClassfifLists) {
        this.context = context;
        this.listClassfifLists = listClassfifLists;
    }

    @Override
    public int getCount() {
        return listClassfifLists.size();
    }

    @Override
    public Object getItem(int position) {
        return listClassfifLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;
        if (convertView == null) {
            holder = new viewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.video_classifi_adapter_item, null, false);
            holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_video_classifi_adapter_pic);
            holder.iv_arrow = (ImageView) convertView.findViewById(R.id.iv_video_classifi_adapter_in);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_video_classifi_adapter);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        GlideUtil.loadGild(HtmitechApplication.getApplication(), listClassfifLists.get(position).picPath, R.drawable.img_video_default, R.drawable.img_video_default, holder.iv_pic);
        holder.tv_name.setText(listClassfifLists.get(position).typeName);
        return convertView;
    }

    public class viewHolder {
        ImageView iv_pic;
        ImageView iv_arrow;
        TextView tv_name;
    }
}
