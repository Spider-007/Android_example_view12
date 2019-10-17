package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.domain.QualittObjectionDicVauleList;

import java.util.List;

/**
 * Created by heyang on 2018/5/23.
 */

public class QualityObjectionItemClickDialogAdapter extends BaseAdapter {

    private Context context;
    private List<QualittObjectionDicVauleList> list;
    private int clickNumber = -1;

    public QualityObjectionItemClickDialogAdapter(Context context, List<QualittObjectionDicVauleList> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.quality_objection_item_click_dialog_adapter_item, null, false);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_quality_objection_item_click_dialog_adapter);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).value);

        if (position == clickNumber) {
            viewHolder.textView.setBackgroundColor(Color.parseColor("#E1E1E1"));
        } else {
            viewHolder.textView.setBackgroundColor(Color.parseColor("#F7F7F7"));
        }
        return convertView;
    }

    public void setClickNumber(int clickNumber) {
        this.clickNumber = clickNumber;
    }

    class ViewHolder {
        TextView textView;
    }
}
