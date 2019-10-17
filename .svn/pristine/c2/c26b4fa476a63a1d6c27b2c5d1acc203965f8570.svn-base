package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.chinarailway.CarVehicleDetailsActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by htmitech on 2018/8/15.
 */

public class DeliveryAdapter extends BaseAdapter {


    private static final int TYPE1 = 1;
    private static final int TYPE2 = 2;
    private Context context;
    private List<Map<String, Object>> data;
    private TextView tvKey;
    private TextView tvValue;
    private Map<String, Object> stringObjectMap;
    private RecyclerView rvcarNum;
    private String[] split;
    private ViewHolder viewHolder;

    public DeliveryAdapter(Context context, List<Map<String, Object>> data) {
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

        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.zb_broad_detal_item, null);
            viewHolder.tvKey = (TextView) view.findViewById(R.id.tv_key);
            viewHolder.tvValue = (TextView) view.findViewById(R.id.tv_value);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Map<String, Object> stringObjectMap = data.get(i);
        String value = null;
        String key = stringObjectMap.get("key").toString();
        value = stringObjectMap.get("value").toString();
        if ("车号".equals(key)) {
            viewHolder.tvKey.setText(key);
//            String[] split = value.split(",");
//            String string = "";
//            for (int j = 0; j < split.length; j++) {
//                if (j == split.length - 1) {
//                    string += split[j];
//                } else {
//                    string += split[i] + " ";
//                }
//            }

            if( value != null){
                value = value.replace(',', ' ');
            }
            String[] splitArray = value.split(" ");
            SpannableString spanableInfo = new SpannableString(value);

            for (int j = 0; j < splitArray.length; j++) {
                String numbers = splitArray[j];
                spanableInfo.setSpan(new Clickable(click, numbers),
                        value.indexOf(numbers), value.indexOf(numbers) + numbers.length(), //设置需要监听的字符串位置
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            viewHolder.tvValue.setText(spanableInfo);  //将处理过的数据set到View里
            viewHolder.tvValue.setMovementMethod(LinkMovementMethod.getInstance());


        } else {
            viewHolder.tvKey.setText(key);
            viewHolder.tvValue.setText(value);

        }
        return view;
    }

    class ViewHolder {

        public TextView tvKey;
        public TextView tvValue;
    }

    private View.OnClickListener click = new View.OnClickListener() {

        String number = "";


        @Override
        public void onClick(View v) {
//            getClick(phone);
        }
    };


    class Clickable extends ClickableSpan {

        private final View.OnClickListener mListener;
        private String nubmer = "";

        public Clickable(View.OnClickListener l, String nubmer) {
            this.mListener = l;
            this.nubmer = nubmer;
        }

        /**
         * 重写父类点击事件
         */
        @Override
        public void onClick(View v) {
            mListener.onClick(v);
            Intent intent = new Intent(context, CarVehicleDetailsActivity.class);
            intent.putExtra("carnum", nubmer);
            context.startActivity(intent);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(true);// 设置文字下划线显示
            ds.setColor(Color.parseColor("#2962FF"));// 设置字体颜色
        }
    }

}
