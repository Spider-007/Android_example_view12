package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.domain.FileManagerEntity;
import com.htmitech.ztcustom.zt.interfaces.FileManagerUploadCallBack;
import com.htmitech.ztcustom.zt.interfaces.UploadFileCallBackListener;
import com.htmitech.ztcustom.zt.util.FileSizeUtil;
import com.htmitech.ztcustom.zt.util.GlideUtil;
import com.htmitech.ztcustom.zt.util.UpLoadFileUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/5/26.
 */

public class FileManagerAdapter extends BaseAdapter {

    private Context context;
    private List<FileManagerEntity> list;
    private int[] colors = new int[]{0x30FF0000, 0x300000FF};
    private LayoutInflater inflater = null;
    private FileManagerUploadCallBack callBack;

    public FileManagerAdapter(Context context, List<FileManagerEntity> list, FileManagerUploadCallBack callBack) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.callBack = callBack;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHodler = null;
        if (convertView == null) {
            mViewHodler = new ViewHolder();
            convertView = inflater.inflate(R.layout.file_manager_adapter_item, null);
            mViewHodler.ivPic = (ImageView) convertView.findViewById(R.id.iv_file_manager_pic);
            mViewHodler.ivState = (ImageView) convertView.findViewById(R.id.iv_file_manager_state);
            mViewHodler.tvName = (TextView) convertView.findViewById(R.id.tv_file_manager_name);
            mViewHodler.tvSize = (TextView) convertView.findViewById(R.id.tv_file_manager_size);
            convertView.setTag(mViewHodler);
        } else {
            mViewHodler = (ViewHolder) convertView.getTag();
        }
        GlideUtil.loadGild(context, list.get(position).FILE_PATH, R.drawable.img_zlyy_list_thumbnail, R.drawable.img_zlyy_list_thumbnail, mViewHodler.ivPic);
        if (list.get(position).UPLOAD_STATUS.equals("2")) {
            mViewHodler.ivState.setImageResource(R.drawable.btn_list_radio_normal);
            mViewHodler.tvSize.setText(list.get(position).UPLOAD_TIME);
        } else {
            mViewHodler.ivState.setImageResource(R.drawable.icon_list_uploading);
            mViewHodler.ivState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final Animation rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.zlyy_uploadfile_roate);
                    v.startAnimation(rotateAnimation);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            new UpLoadFileUtils().uploadFile(list.get(position).FILE_PATH, list.get(position).EXTRA, list.get(position).FILE_SUBMIT_ID.substring(0, list.get(position).FILE_SUBMIT_ID.length() - 3), list.get(position).FILE_SUBMIT_ID, context, true, list.get(position).FILE_ID, new UploadFileCallBackListener() {
                                @Override
                                public void fileCallBack(String result) {
                                    Log.e("UPLOADFILE", result);
                                    v.clearAnimation();
                                    if (callBack != null && result.equals("success"))
                                        callBack.uploadFileSuccess();
                                }
                            });
                        }
                    }).start();
                }
            });
            mViewHodler.tvSize.setText(FileSizeUtil.getFileOrFilesSize(list.get(position).FILE_PATH, FileSizeUtil.SIZETYPE_KB) + "KB");
        }
        if (list.get(position).TASK_ID.equals("zlyy")) {
            mViewHodler.tvName.setText("【质量异议】" + list.get(position).FILE_SUBMIT_ID);
        }

        int colorPos = position % colors.length;
        if (colorPos == 1)
            convertView.setBackgroundColor(Color.rgb(0, 20, 67));
        else
            convertView.setBackgroundColor(Color.rgb(0, 9, 49));
        return convertView;
    }

    class ViewHolder {
        public ImageView ivPic;
        public ImageView ivState;
        public TextView tvName;
        public TextView tvSize;
    }
}
