package com.htmitech.proxy.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htmitech.base.MyBaseAdapter;
import com.htmitech.emportal.R;
import com.htmitech.proxy.doman.EmpCisInfo;

import java.util.List;

public class ChilDaccountAdapter extends MyBaseAdapter<EmpCisInfo>{

    private LayoutInflater lInflater;
    private List<EmpCisInfo> datas;
    public ChilDaccountAdapter(List<EmpCisInfo> datas, Context context) {
        super(datas, context);
        lInflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;
        EmpCisInfo empCisInfo = (EmpCisInfo) getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.chil_daccount_item, null);
            holder.tv_cis_name = (TextView) convertView
                    .findViewById(R.id.tv_cis_name);
            holder.status_msg = (TextView) convertView
                    .findViewById(R.id.status_msg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_cis_name.setText(empCisInfo.getCis_name());
        holder.status_msg.setText("("+empCisInfo.getStatus_msg()+")");
        return convertView;
    }

    class ViewHolder {
        TextView tv_cis_name;
        TextView status_msg;
    }
}
