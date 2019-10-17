package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.ztcustom.R;

import java.util.List;
import java.util.Map;

/**
 * Created by htmitech on 2018/8/22.
 */

public class CarVehicleDetailsAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, Object>> data;

    public CarVehicleDetailsAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_carvehicle_search_detail, null);
            viewHolder.tvCarvehicleNumkey = (TextView) view.findViewById(R.id.tv_carvehicle_numkey);
            viewHolder.tvCarvehicleNumvalue = (TextView) view.findViewById(R.id.tv_carvehicle_numvalue);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (i % 2 != 0) {
            viewHolder.tvCarvehicleNumvalue.setBackgroundColor(context.getResources().getColor(
                    R.color.cardetails));
        } else {
            viewHolder.tvCarvehicleNumvalue.setBackgroundColor(context.getResources().getColor(
                    R.color.color_ffffffff));
        }
        //    <--------------------Administrator -> 2019-8-17:17:35: 车辆追踪 crash -> 处理--------------------->
        String key = "";
        String value = "";
        Map<String, Object> stringObjectMap = data.get(i);
        try {
           key= stringObjectMap.get("key").toString();
           value = stringObjectMap.get("value").toString();
        } catch (Exception e) {
            e.printStackTrace();
            viewHolder.tvCarvehicleNumvalue.setText("");
        }
        viewHolder.tvCarvehicleNumkey.setText(key);
        viewHolder.tvCarvehicleNumvalue.setText(value);
        return view;
    }

    class ViewHolder {
        TextView tvCarvehicleNumkey;
        TextView tvCarvehicleNumvalue;
    }
}
