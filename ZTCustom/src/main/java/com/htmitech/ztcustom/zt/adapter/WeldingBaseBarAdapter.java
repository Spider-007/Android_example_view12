package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.chinarailway.WeldingBaseDetailActivity;
import com.htmitech.ztcustom.zt.domain.WeldingBaseResultList;
import com.htmitech.ztcustom.zt.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 焊轨基地 adapter
 *
 * @author htrf-pc
 */
public class WeldingBaseBarAdapter extends BaseAdapter {
    public Context context;
    public LayoutInflater inflater;
    private int width;
    private float maxValue;
    private List<WeldingBaseResultList> list;
    private String type;

    public WeldingBaseBarAdapter(Context context, List<WeldingBaseResultList> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
        inflater = LayoutInflater.from(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth(); //1080
        int widthJa = 0;
        if (width == 720) {
            widthJa = 330;
        } else if (width == 1080) {
            widthJa = 390;
        } else {
            widthJa = DensityUtil.dip2px(context, 122);
        }
        width = width - widthJa;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @SuppressWarnings("null")
    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHodler mViewHodler = null;
        WeldingBaseResultList resultList = list.get(position);
        if (arg1 == null) {
            mViewHodler = new ViewHodler();
            arg1 = inflater.inflate(R.layout.welding_base_bar_adapter, null);
            mViewHodler.tv_num = (TextView) arg1.findViewById(R.id.tv_bar_num);
            mViewHodler.tv_name = (TextView) arg1.findViewById(R.id.tv_bar_name);
            mViewHodler.tv_value_kucun = (TextView) arg1.findViewById(R.id.tv_bar_value_kucun);
            mViewHodler.tv_value_kurong = (TextView) arg1.findViewById(R.id.tv_bar_value_kurong);
            mViewHodler.my_progress_kucun = (ProgressBar) arg1.findViewById(R.id.my_progress_bar_kucun);
            mViewHodler.my_progress_kurong = (ProgressBar) arg1.findViewById(R.id.my_progress_bar_kurong);
            mViewHodler.rl_bar = (RelativeLayout) arg1.findViewById(R.id.rl_welding_base_bar);
            arg1.setTag(mViewHodler);
        } else {
            mViewHodler = (ViewHodler) arg1.getTag();
        }
        float valueKuCun = 0f;
        float valueKuRong = 0f;
        if (!resultList.dhgkucun.equals("")) {
            valueKuCun = Float.parseFloat(resultList.dhgkucun);
        }
        if (!resultList.dhgkurong.equals("")) {
            valueKuRong = Float.parseFloat(resultList.dhgkurong);
        }
        RelativeLayout.LayoutParams linearParamskucun = (RelativeLayout.LayoutParams) mViewHodler.my_progress_kucun.getLayoutParams();
        linearParamskucun.width = proportionWidth(valueKuCun);
        mViewHodler.my_progress_kucun.setLayoutParams(linearParamskucun);
        mViewHodler.tv_value_kucun.setText(resultList.dhgkucun.trim());

        RelativeLayout.LayoutParams linearParamskurong = (RelativeLayout.LayoutParams) mViewHodler.my_progress_kurong.getLayoutParams();
        linearParamskurong.width = proportionWidth(valueKuRong);
        mViewHodler.my_progress_kurong.setLayoutParams(linearParamskurong);
        mViewHodler.tv_value_kurong.setText(resultList.dhgkurong.trim());

        String shortname = resultList.hanguijidi;
        mViewHodler.tv_name.setText(shortname);
        mViewHodler.tv_name.setMovementMethod(ScrollingMovementMethod.getInstance());
        mViewHodler.rl_bar.setOnClickListener(new ViewOnClick(resultList.hanguijidiid, resultList.hanguijidi));
        return arg1;
    }

    public class ViewOnClick implements OnClickListener {
        public String id;
        public String name;

        public ViewOnClick(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(context, WeldingBaseDetailActivity.class);
            intent.putExtra("hanguijidiid", id);
            intent.putExtra("hanguijidi", name);
            intent.putExtra("type", type);
            context.startActivity(intent);
        }
    }

    public class ViewHodler {
        public TextView tv_num;
        public TextView tv_name;
        public TextView tv_value_kucun;
        public TextView tv_value_kurong;
        public ProgressBar my_progress_kucun;
        public ProgressBar my_progress_kurong;
        public RelativeLayout rl_bar;
    }

    /**
     * 算pro宽度
     *
     * @param value
     * @return
     */
    public int proportionWidth(float value) {
        return (int) ((value / Float.parseFloat((maxValue + ""))) * width);
    }

    public void setData(List<WeldingBaseResultList> listTemp, float maxValue) {
        if (list == null) {
            list = new ArrayList<WeldingBaseResultList>();
            list.addAll(listTemp);
        } else {
            this.list.clear();
            this.list.addAll(listTemp);
        }
        this.maxValue = maxValue;
        this.notifyDataSetChanged();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

}
