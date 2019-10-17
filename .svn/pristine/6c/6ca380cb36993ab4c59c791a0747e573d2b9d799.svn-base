package com.htmitech_updown.updownloadmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.updownloadmanagement.R;
import com.htmitech_updown.updownloadmanagement.UploadFinishDeleteActivity;
import com.htmitech_updown.updownloadmanagement.uploadbean.UploadFileInfoBean;
import com.htmitech_updown.updownloadmanagement.view.MyOnSlipeStatusListener;
import com.htmitech_updown.updownloadmanagement.view.SwipeListLayout;

import java.util.List;
import java.util.Set;

import htmitech.com.componentlibrary.unit.CommonFileType;

/**
 * Created by Administrator on 2018/7/10.
 */
public class SellSipeListAdapter extends BaseAdapter {

    private Context context;
    private List<UploadFileInfoBean> list;
    private Set<SwipeListLayout> sets;

    public SellSipeListAdapter(Context context, List<UploadFileInfoBean> list, Set<SwipeListLayout> sets) {
        this.context = context;
        this.list = list;
        this.sets = sets;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    ViewHolder viewHolder = null;

    @Override
    public View getView(final int position, View view, ViewGroup arg2) {

        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.upload_file_finish_adapter_item, null);
            viewHolder.sll_main = (SwipeListLayout) view.findViewById(R.id.sll_main);
            viewHolder.tv_top = (TextView) view.findViewById(R.id.tv_top);
            viewHolder.tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            viewHolder.textViewTitle = (TextView) view.findViewById(R.id.tv_upload_file_finish_up_adapter_title);
            viewHolder.textViewInfomation = (TextView) view.findViewById(R.id.tv_upload_file_finish_up_adapter_information);
            viewHolder.imageViewCheckBox = (ImageView) view.findViewById(R.id.iv_upload_file_finish_up_right_cricle);
            viewHolder.imageViewType = (ImageView) view.findViewById(R.id.iv_upload_file_finish_up_adapter);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String fileName = list.get(position).fileName;
        if (CommonFileType.MIME_icon_MapTable_Instance().containsKey(fileName.substring(fileName.lastIndexOf(".") + 1))) {
            viewHolder.imageViewType.setImageResource(CommonFileType.MIME_icon_MapTable_Instance().get(fileName.substring(fileName.lastIndexOf(".") + 1)));
        } else {
            viewHolder.imageViewType.setImageResource(R.drawable.icon_unkonw);
        }
        viewHolder.textViewTitle.setText(list.get(position).fileName);
        viewHolder.textViewInfomation.setText(list.get(position).taskName + " " + list.get(position).createTime);
        viewHolder.sll_main.setOnSwipeStatusListener(new MyOnSlipeStatusListener(viewHolder.sll_main, sets));
        viewHolder.imageViewCheckBox.setOnClickListener(new OnCheckBoxClickListener());
        viewHolder.tv_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.sll_main.setStatus(SwipeListLayout.Status.Close, true);
//                String str = list.get(arg0);
//                list.remove(arg0);
//                list.add(0, str);
                notifyDataSetChanged();
            }
        });
        viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.sll_main.setStatus(SwipeListLayout.Status.Close, true);
//                list.remove(arg0);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    public static class ViewHolder {
        public SwipeListLayout sll_main;
        public TextView textViewTitle;
        public TextView textViewInfomation;
        public TextView tv_top;
        public TextView tv_delete;
        public ImageView imageViewCheckBox;
        public ImageView imageViewType;
    }

    class OnCheckBoxClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, UploadFinishDeleteActivity.class);
            context.startActivity(intent);
        }
    }

}
