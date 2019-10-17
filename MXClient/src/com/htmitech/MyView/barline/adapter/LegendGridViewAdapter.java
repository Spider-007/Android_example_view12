package com.htmitech.MyView.barline.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.MyView.barline.bean.NameColor;
import com.htmitech.emportal.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by heyang on 2017-6-19.
 */
public class LegendGridViewAdapter extends BaseAdapter {

    private List<NameColor> nameColors;
    private Context context;

    public LegendGridViewAdapter(Context context, List<NameColor> nameColors) {
        this.context = context;
        this.nameColors = nameColors;
    }


    @Override
    public int getCount() {
        return nameColors.size();
    }

    @Override
    public Object getItem(int position) {
        return nameColors.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.bar_line_legend_layout, null, false);
            viewHolder.lineColor = (LinearLayout) convertView.findViewById(R.id.ll_bar_line_legend);
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.tv_bar_line_legend);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewName.setText(nameColors.get(position).name);
        viewHolder.lineColor.setBackgroundColor(Color.parseColor(nameColors.get(position).color));

        return convertView;
    }

    class ViewHolder {
        LinearLayout lineColor;
        TextView textViewName;
    }
}
