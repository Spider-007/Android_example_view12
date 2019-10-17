package com.htmitech_updown.updownloadmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.updownloadmanagement.R;
import com.htmitech_updown.updownloadmanagement.listener.UploadFileNetConnectionListener;
import com.htmitech_updown.updownloadmanagement.uploadbean.ChunkInfo;
import com.htmitech_updown.updownloadmanagement.uploadbean.UploadFileInfoBean;
import com.htmitech_updown.updownloadmanagement.uploadfile.Md5Utils;
import com.htmitech_updown.updownloadmanagement.uploadfile.UploadManager;
import com.htmitech_updown.updownloadmanagement.uploadfile.UploadStatus;
import com.htmitech_updown.updownloadmanagement.view.CustomCircleProgressBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.unit.CommonFileType;
import htmitech.com.componentlibrary.unit.NetConnectionUtils;
import htmitech.com.componentlibrary.unit.PreferenceUtils;

/**
 * Created by Administrator on 2018/7/10.
 */
public class UploadFileNeedUpAdapter extends BaseAdapter {

    private Context context;
    private List<UploadFileInfoBean> list;
    private LayoutInflater inflater;
    private UploadFileNetConnectionListener uploadFileNetConnectionListener;

    public UploadFileNeedUpAdapter(Context context, List<UploadFileInfoBean> list, UploadFileNetConnectionListener uploadFileNetConnectionListener) {
        this.context = context;
        this.list = list;
        this.uploadFileNetConnectionListener = uploadFileNetConnectionListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.upload_file_need_up_item, null);
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.tv_upload_file_need_up_adapter_title);
            viewHolder.textViewInfomation = (TextView) convertView.findViewById(R.id.tv_upload_file_need_up_adapter_information);
            viewHolder.imageViewType = (ImageView) convertView.findViewById(R.id.iv_upload_file_need_up_adapter);
            viewHolder.imageViewStartOrPause = (ImageView) convertView.findViewById(R.id.iv_upload_file_need_up_adapter_start_pause);
            viewHolder.progressBar = (CustomCircleProgressBar) convertView.findViewById(R.id.am__upload_file_need_up_adapter_progressbar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String fileName = list.get(position).fileName;
        if (CommonFileType.MIME_icon_MapTable_Instance().containsKey(fileName.substring(fileName.lastIndexOf(".") + 1))) {
            viewHolder.imageViewType.setImageResource(CommonFileType.MIME_icon_MapTable_Instance().get(fileName.substring(fileName.lastIndexOf(".") + 1)));
        } else {
            viewHolder.imageViewType.setImageResource(R.drawable.icon_unkonw);
        }
        viewHolder.textViewTitle.setText(list.get(position).fileName);
        viewHolder.textViewInfomation.setText(list.get(position).taskName + " " + list.get(position).createTime);
        if (UploadManager.getInstance().getUploadTask(list.get(position).TASK_ID).getUploadStatus() == UploadStatus.UPLOAD_STATUS_UPLOADING) {
            viewHolder.imageViewStartOrPause.setVisibility(View.GONE);
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setMaxProgress(list.get(position).chunks);
            viewHolder.progressBar.setProgress(list.get(position).chunk);
            viewHolder.progressBar.setOnClickListener(new OnStartOrPauseClick(list.get(position), viewHolder.imageViewStartOrPause, viewHolder.progressBar));
            viewHolder.imageViewStartOrPause.setOnClickListener(new OnStartOrPauseClick(list.get(position), viewHolder.imageViewStartOrPause, viewHolder.progressBar));
        } else {
            viewHolder.imageViewStartOrPause.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setVisibility(View.GONE);
            viewHolder.progressBar.setOnClickListener(new OnStartOrPauseClick(list.get(position), viewHolder.imageViewStartOrPause, viewHolder.progressBar));
            viewHolder.imageViewStartOrPause.setOnClickListener(new OnStartOrPauseClick(list.get(position), viewHolder.imageViewStartOrPause, viewHolder.progressBar));
        }
        return convertView;
    }

    public static class ViewHolder {
        public TextView textViewTitle;
        public TextView textViewInfomation;
        public ImageView imageViewType;
        public ImageView imageViewStartOrPause;
        public CustomCircleProgressBar progressBar;
    }

    class OnStartOrPauseClick implements View.OnClickListener {

        UploadFileInfoBean bean;
        ImageView imageView;
        CustomCircleProgressBar progressBar;

        public OnStartOrPauseClick(UploadFileInfoBean bean, ImageView imageView, CustomCircleProgressBar progressBar) {
            this.bean = bean;
            this.imageView = imageView;
            this.progressBar = progressBar;
        }

        @Override
        public void onClick(View v) {
            //先判断是上传还是暂停
            if (UploadManager.getInstance().getUploadTask(bean.TASK_ID).getUploadStatus() == UploadStatus.UPLOAD_STATUS_UPLOADING) {
                UploadManager.getInstance().pause(bean.TASK_ID);
                imageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {
//                if (bean.chunk == 0) {//新的需要从开始上传的
//                    ChunkInfo fileInfo = new ChunkInfo();
//                    fileInfo.setFilePath(bean.filePath);
//                    fileInfo.setFileLength(Long.parseLong(bean.len));
//                    fileInfo.setIsChunk(true);
//                    fileInfo.setBatchNumber(bean.batchNumber);
//                    fileInfo.setFileName(bean.fileName);
//                    long time = System.currentTimeMillis();
//                    File file = new File(bean.filePath);
//                    String md5 = Md5Utils.getFileMd5(file) + time;//文件的MD5加上一个当前时间戳
//                    fileInfo.setMd5(md5);
//                    UploadManager.getInstance().addUploadTask(fileInfo);
//                } else {//暂停或者失败了，继续上传

                if (NetConnectionUtils.hasNetWorkConnection(context)) {
                    //判断是不是有wifi，当前网络环境
                    if (NetConnectionUtils.hasWifiConnection(context)) {
                        UploadManager.getInstance().resume(bean.TASK_ID);
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        int code = PreferenceUtils.getUploadBigFileNet(context);
                        if (code == 3 || code == 2) {//仅wifi上传和仅一次 弹出popwindow
                            uploadFileNetConnectionListener.noWifi(bean.TASK_ID, imageView, progressBar);
                        } else {
                            UploadManager.getInstance().resume(bean.TASK_ID);
                            imageView.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                } else {//没网

                }
//                }
            }
        }
    }

    public void setData(List<UploadFileInfoBean> list) {
        if (list != null) {
            this.list.clear();
            this.list.addAll(list);
        } else {
            this.list = new ArrayList<>();
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

}
