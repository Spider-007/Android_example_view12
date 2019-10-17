package com.htmitech.htexceptionmanage.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.base.MyBaseAdapter;
import com.htmitech.emportal.R;
import com.htmitech.htexceptionmanage.entity.ManageExcInfo;

import java.util.List;

public class ManageExceptionAdapter extends MyBaseAdapter<ManageExcInfo>{

    private LayoutInflater lInflater;
    private List<ManageExcInfo> datas;
    private Context context;
    public ManageExceptionAdapter(List<ManageExcInfo> datas, Context context) {
        super(datas, context);
        this.context = context;
        this.datas = datas;
        if (context==null){return;}
        lInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;
        ManageExcInfo manageExcInfo = (ManageExcInfo) getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.exception_item, null);
            holder.tv_come = (TextView) convertView.findViewById(R.id.tv_come);
            holder.iv_type = (ImageView) convertView
                    .findViewById(R.id.iv_type);
            holder.tv_title = (TextView) convertView
                    .findViewById(R.id.tv_title);
            holder.tv_sourse_from = (TextView) convertView
                    .findViewById(R.id.tv_sourse_from);
            holder.tv_time = (TextView) convertView
                    .findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        Glide.with(context).load(ExceptionList.getIconId()).placeholder(R.drawable.icon_email).error(R.drawable.icon_email).transform(new GlideRoundTransform(mContext)).into( holder.iv_type);
        holder.iv_type.setImageResource(R.drawable.list_menu_shenpi);
        holder.tv_title.setText(manageExcInfo.getAlertTitle());
        if(manageExcInfo.getSourceUserName()!=null){
            holder.tv_sourse_from.setText(manageExcInfo.getSourceUserName());
        }else {
            holder.tv_come.setVisibility(View.INVISIBLE);
            holder.tv_sourse_from.setVisibility(View.INVISIBLE);
        }
        holder.tv_time.setText(manageExcInfo.getCreateTime());
        return convertView;
    }
    class ViewHolder {
        ImageView iv_type;
        TextView tv_title;
        TextView tv_sourse_from;
        TextView tv_come;
        TextView tv_time;
    }
}
