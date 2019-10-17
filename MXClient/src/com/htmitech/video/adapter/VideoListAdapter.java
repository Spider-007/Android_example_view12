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
import com.htmitech.video.bean.VideoListPageList;

import java.util.List;

/**
 * Created by heyang on 2017-7-5.
 */
public class VideoListAdapter extends BaseAdapter {

    private Context context;
    private List<VideoListPageList> listPageLists;

    public VideoListAdapter(Context context, List<VideoListPageList> listPageLists) {
        this.context = context;
        this.listPageLists = listPageLists;
    }

    @Override
    public int getCount() {
        return listPageLists.size();
    }

    @Override
    public Object getItem(int position) {
        return listPageLists.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.vdeolist_adapter_item, null, false);
            holder.ivItem = (ImageView) convertView.findViewById(R.id.iv_video_item);
            holder.ivIsPlay = (ImageView) convertView.findViewById(R.id.iv_video_item_isplay);
            holder.tvisPrivate = (TextView) convertView.findViewById(R.id.tv_video_item_isprivate);
            holder.tvtitle = (TextView) convertView.findViewById(R.id.tv_video_item_title);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_video_item_time);
            holder.tvTitleCen = (TextView) convertView.findViewById(R.id.tv_video_item_title_cen);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        GlideUtil.loadGild(HtmitechApplication.getApplication(), listPageLists.get(position).thumbPic, R.drawable.img_video_default, R.drawable.img_video_default, holder.ivItem);


//        Glide.with(context).load(listPageLists.get(position).thumbPic).placeholder(R.drawable.img_video_default).error(R.drawable.img_video_default).into(holder.ivItem);
        if (listPageLists.get(position).state == 1) {
            holder.tvisPrivate.setVisibility(View.VISIBLE);
            holder.tvTitleCen.setVisibility(View.GONE);
            holder.tvtitle.setVisibility(View.VISIBLE);
        } else if (listPageLists.get(position).state == 0) {
            holder.tvisPrivate.setVisibility(View.GONE);
            holder.tvTitleCen.setVisibility(View.VISIBLE);
            holder.tvtitle.setVisibility(View.GONE);
        }
        holder.tvtitle.setText(listPageLists.get(position).title);
        holder.tvTitleCen.setText(listPageLists.get(position).title);
        holder.tvTime.setText(listPageLists.get(position).getUpdateTime());
        return convertView;
    }

    public class viewHolder {
        ImageView ivItem;
        ImageView ivIsPlay;
        TextView tvisPrivate;
        TextView tvtitle;
        TextView tvTime;
        TextView tvTitleCen;
    }
}
