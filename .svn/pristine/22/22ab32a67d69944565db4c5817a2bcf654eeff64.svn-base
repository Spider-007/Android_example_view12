package com.htmitech.proxy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.emportal.R;

import java.util.ArrayList;

/**
 * Created by yanxin on 2017-7-4.
 */
public class ListPopupWindowAdapter extends BaseAdapter {
    public Context context;
    public ArrayList<Integer> dataList;
    private LayoutInflater Inflater;
    private ViewHolder holder;
    public int curPosition;

    public ListPopupWindowAdapter(Context context, ArrayList list) {
        this.context = context;
        dataList = list;
        Inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Integer getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = Inflater.inflate(R.layout.popupwindow_list_item, null);
            holder.tv_conten = (TextView) convertView
                    .findViewById(R.id.tv_content);
            holder.ll_root = (LinearLayout) convertView.findViewById(R.id.ll_root);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (dataList.get(position) >= 999) {
            holder.tv_conten.setText("无限制");
        } else {
            holder.tv_conten.setText(dataList.get(position) + "");
        }

        if (position == curPosition) {
            holder.tv_conten.setSelected(true);
            convertView.setBackgroundColor(Color.parseColor("#fcf8e2"));
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_conten;
        LinearLayout ll_root;
    }

    public void selectIndex(int postion) {
        this.curPosition = postion;
    }
}
