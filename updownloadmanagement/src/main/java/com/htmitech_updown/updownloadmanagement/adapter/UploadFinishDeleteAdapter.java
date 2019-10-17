package com.htmitech_updown.updownloadmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.updownloadmanagement.R;
import com.htmitech_updown.updownloadmanagement.listener.UploadFinishDeleteCheckBoxClickListener;
import com.htmitech_updown.updownloadmanagement.uploadbean.UploadFileInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/16.
 */
public class UploadFinishDeleteAdapter extends BaseAdapter {

    private List<UploadFileInfoBean> list;
    private Context context;
    private LayoutInflater inflater;
    private UploadFinishDeleteCheckBoxClickListener checkBoxClickListener;

    public UploadFinishDeleteAdapter(Context context, List<UploadFileInfoBean> list, UploadFinishDeleteCheckBoxClickListener checkBoxClickListener) {
        this.context = context;
        this.list = list;
        this.checkBoxClickListener = checkBoxClickListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.upload_finish_delete_adapter_item, null);
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.tv_upload_file_finish_delete_up_adapter_title);
            viewHolder.textViewInformation = (TextView) convertView.findViewById(R.id.tv_upload_file_finish_delete_up_adapter_information);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_upload_file_finish_delete_up_right);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list.get(position).delectChecked) {
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    list.get(position).delectChecked = true;
                } else {
                    list.get(position).delectChecked = false;
                }
                checkBoxClickListener.onCheckBoxClickListener();
            }
        });
        viewHolder.textViewTitle.setText(list.get(position).fileName);
        viewHolder.textViewInformation.setText(list.get(position).taskName + " " + list.get(position).createTime);
        return convertView;
    }

    public static class ViewHolder {
        public TextView textViewTitle;
        public CheckBox checkBox;
        public TextView textViewInformation;
    }

    public void selectAll() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).delectChecked = true;
        }
        checkBoxClickListener.onCheckBoxClickListener();
        notifyDataSetChanged();
    }

    public void cancleAll() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).delectChecked = false;
        }
        checkBoxClickListener.onCheckBoxClickListener();
        notifyDataSetChanged();
    }
}
