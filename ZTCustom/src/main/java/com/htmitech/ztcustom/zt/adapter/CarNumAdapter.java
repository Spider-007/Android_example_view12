package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.KeyWordUtil;
import com.htmitech.ztcustom.zt.constant.CarNumFuzzyQueryBean;

import java.util.List;

/**
 * Created by htmitech on 2018/8/10.
 */

public class CarNumAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;
    private String flag;
    private List<CarNumFuzzyQueryBean.ResultsBean> carNumFuzzyQueryBeanAllList;


    public CarNumAdapter(Context context, List<CarNumFuzzyQueryBean.ResultsBean> carNumFuzzyQueryBeanAllList, String flag) {
        this.flag = flag;
        this.context = context;
        this.carNumFuzzyQueryBeanAllList = carNumFuzzyQueryBeanAllList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return carNumFuzzyQueryBeanAllList != null ? carNumFuzzyQueryBeanAllList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return carNumFuzzyQueryBeanAllList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
           ViewHolder mViewHolder;
        if(view == null){
            mViewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_carvehicle_search_lv, null);
            mViewHolder.tvCarNum = (TextView) view.findViewById(R.id.tv_carnum);
            mViewHolder.all_m = (TextView) view.findViewById(R.id.all_m);
            mViewHolder.time = (TextView) view.findViewById(R.id.time);
            mViewHolder.address = (TextView) view.findViewById(R.id.address);
            mViewHolder.imgCarNext = (ImageView) view.findViewById(R.id.img_car_next);
            view.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) view.getTag();
        }
        if(i%2 == 0){
            view.setBackgroundColor(context.getResources().getColor(R.color.allbackground));
        }else{
            view.setBackgroundColor(context.getResources().getColor(R.color.listview_item_jiaoti_color));
        }

        if (!TextUtils.isEmpty(flag)) {
            SpannableString spannableTitle = KeyWordUtil.matcherSearchTitle(Color.parseColor("#ffffff"), carNumFuzzyQueryBeanAllList.get(i).getVehicleno(), flag);
            mViewHolder.tvCarNum.setText(spannableTitle);
        }else {
            mViewHolder.tvCarNum.setText(carNumFuzzyQueryBeanAllList.get(i).getVehicleno());
        }

        mViewHolder.all_m.setText(carNumFuzzyQueryBeanAllList.get(i).getTotalWeight());
        mViewHolder.time.setText(carNumFuzzyQueryBeanAllList.get(i).getSendDate());
        if (carNumFuzzyQueryBeanAllList.get(i).getReportStation() != null && !carNumFuzzyQueryBeanAllList.get(i).getReportStation().equals("")){
            mViewHolder.address.setText(carNumFuzzyQueryBeanAllList.get(i).getReportStation());
        }else {
            mViewHolder.address.setText("");
        }
        return view;
    }


     class ViewHolder {
         TextView tvCarNum,address;
         TextView all_m;
         TextView time;
         ImageView imgCarNext;
     }
}
