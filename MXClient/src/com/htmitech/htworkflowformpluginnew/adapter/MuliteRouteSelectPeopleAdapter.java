package com.htmitech.htworkflowformpluginnew.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.htworkflowformpluginnew.entity.RouteInfo;

import java.util.ArrayList;


/**
 * Created by heyang on 2018-1-15.
 */
public class MuliteRouteSelectPeopleAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RouteInfo> list;

    public MuliteRouteSelectPeopleAdapter(Context context, ArrayList<RouteInfo> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.mulite_route_select_people_adapter_item, null, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_mulite_route_select_people_adapter);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_mulite_route_select_people_adapter);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setVisibility(View.VISIBLE);
        if (list.get(position).getRouteID() != null && !list.get(position).getRouteID().equals("")) {
            //这种属于部门
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {//这种属于只显示人名
            viewHolder.imageView.setVisibility(View.GONE);
            convertView.setBackgroundColor(Color.parseColor("#F8F8F8"));
        }
        if (!list.get(position).isAllowSelectUser()) {
            viewHolder.imageView.setVisibility(View.GONE);
        }
        viewHolder.textView.setTextColor(Color.parseColor("#555555"));
        if (list.get(position).getRouteName() != null) {
            viewHolder.textView.setText(list.get(position).getRouteName());
            //设置哪个路由部门显示为蓝色
            if (!list.get(position).isAllowSelectUser()) {
                viewHolder.textView.setTextColor(Color.parseColor("#297BFB"));
            } else {
                if (position < list.size() - 1 && list.get(position + 1).getRouteID() == null) {
                    viewHolder.textView.setTextColor(Color.parseColor("#297BFB"));
                }
            }
        }
        return convertView;
    }

    class ViewHolder {
        private TextView textView;
        private ImageView imageView;
    }
}
