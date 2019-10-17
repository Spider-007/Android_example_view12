package com.example.archivermodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.archivermodule.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import htmitech.com.componentlibrary.unit.CommonFileType;

/**
 * Created by Administrator on 2018-6-6.
 */

public class UnrarAdapter extends BaseAdapter {
    private File[] files;
    private LayoutInflater inflater;
    public UnrarAdapter(Context contexts, File[] files){
        inflater = LayoutInflater.from(contexts);
        this.files = files;
    }

    @Override
    public int getCount() {
        if (null != files)
            return files.length;
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return files[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoudler mViewHoudler = null;
        File file = files[position];
        if (convertView == null) {
            mViewHoudler = new ViewHoudler();
            convertView = inflater.inflate(R.layout.archiver_prefix_listitem_attachment,
                    null);
            mViewHoudler.imgview_attachmentType = (ImageView) convertView
                    .findViewById(R.id.imageview_attachment);
            mViewHoudler.tv_title = (TextView) convertView
                    .findViewById(R.id.textview_attachment_title);
            mViewHoudler.tv_size = (TextView) convertView
                    .findViewById(R.id.textview_attachment_size);
            mViewHoudler.btn = (ImageView) convertView
                    .findViewById(R.id.btn_attachment);
            mViewHoudler.rl_item = (RelativeLayout) convertView
                    .findViewById(R.id.rl_attachment_item);
            mViewHoudler.progressBar = (ProgressBar) convertView
                    .findViewById(R.id.progressbar_attachment_download);
            convertView.setTag(mViewHoudler);
        } else {
            mViewHoudler = (ViewHoudler) convertView.getTag();
        }

        if(file.isFile()){
            mViewHoudler.tv_title.setText(file.getName());
            String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName	().length()).toLowerCase();
            if (CommonFileType.MIME_icon_MapTable_Instance().containsKey(end))
                mViewHoudler.imgview_attachmentType.setImageResource(CommonFileType.MIME_icon_MapTable_Instance().get(end));
            else
                mViewHoudler.imgview_attachmentType.setImageResource(R.drawable.icon_unkonw);

            mViewHoudler.btn.setImageResource(R.drawable.btn_accessory_look);
        }else{
            mViewHoudler.btn.setImageResource(R.drawable.ic_arrow_right_normal);
            mViewHoudler.tv_title.setText(file.getName());
//            convertView.setOnClickListener(new Onclick(file.getPath()));
            mViewHoudler.imgview_attachmentType.setImageResource(R.drawable.folder);
        }
//        SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
        Date date = new Date(file.lastModified());
//        mViewHoudler.tv_size.setVisibility(View.GONE);
        Date currentDate = new Date(System.currentTimeMillis());
        mViewHoudler.tv_size.setText(twoDateDistance(date,currentDate));
        return convertView;
    }
    public void setData(File[] files){
        this.files = files;
        this.notifyDataSetChanged();
    }
    public String twoDateDistance(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        } else {
            long timeLong = endDate.getTime() - startDate.getTime();
            if (timeLong < 60 * 1000) {
                return "刚刚";

            } else if (timeLong < 60 * 60 * 1000) {
                timeLong =timeLong/1000/60;
                return timeLong+"分钟前";

            }else if(timeLong<60*60*24*1000){
                timeLong = timeLong/60/60/1000;
                return timeLong+"小时前";

            }else if(timeLong<60*60*24*1000*7){
                timeLong = timeLong/1000/ 60 / 60 / 24;
                return timeLong+"天前";
            }else if(timeLong<60*60*24*1000*7*4){
                timeLong = timeLong/1000/ 60 / 60 / 24/7;
                return timeLong+"周前";

            }
            else {
                SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
                return  sf.format(startDate);
            }
        }

    }
    public File getFile(int index){
        return files[index];
    }

    public class Onclick implements View.OnClickListener{
        private String path;
        public Onclick(String path){
            this.path = path;
        }
        @Override
        public void onClick(View v) {
            setData(getFiles(path));
        }
    }

    public File[] getFiles(String path){
        File file=new File(path);
        File[] files=file.listFiles();
        return files;
    }

    public class ViewHoudler{
        TextView tv_title;
        ImageView imgview_attachmentType;
        TextView tv_size;
        ImageView btn;
        RelativeLayout rl_item;
        ProgressBar progressBar;
    }
}
