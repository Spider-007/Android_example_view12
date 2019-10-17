package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.PlannedCashRateConfig;
import com.htmitech.ztcustom.zt.constant.PlannedRateBarData;
import com.htmitech.ztcustom.zt.util.DensityUtil;
import com.htmitech.ztcustom.zt.view.TextProgressBar;

import java.util.ArrayList;

public class PlannedRateBarAdapter extends BaseAdapter {

    public ArrayList<PlannedRateBarData> list;
    public Context context;
    private LayoutInflater inflater = null;
    private float maxProgress;
    private int num = 10000;
    private String currentFagment;
    private int width;

    public PlannedRateBarAdapter(ArrayList<PlannedRateBarData> list, Context context, String currentFagment) {
        this.list = list;
        this.context = context;
        this.currentFagment = currentFagment;
        this.inflater = LayoutInflater.from(context);
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
        ViewHolder mViewHodler = null;
        if (convertView == null) {
            mViewHodler = new ViewHolder();
            convertView = inflater.inflate(R.layout.planned_rate_bar_adapter_item_layout, null);
            mViewHodler.progressBar_configweight = (TextProgressBar) convertView.findViewById(R.id.progress_planned_rate_configweight);
            mViewHodler.progressBar_contractweight = (TextProgressBar) convertView.findViewById(R.id.progress_planned_rate_contractweight);
            mViewHodler.progressBar_deliveryweight = (TextProgressBar) convertView.findViewById(R.id.progress_planned_rate_deliveryweight);
            mViewHodler.progressBar_qjcontractweight = (TextProgressBar) convertView.findViewById(R.id.progress_planned_rate_qjcontractweight);
            mViewHodler.textViewGCMC = (TextView) convertView.findViewById(R.id.tv_planned_rate_adapter_gcmc);
            mViewHodler.tv_configweight = (TextView) convertView.findViewById(R.id.tv_planned_rate_configweight);
            mViewHodler.tv_contractweight = (TextView) convertView.findViewById(R.id.tv_planned_rate_contractweight);
            mViewHodler.tv_deliveryweight = (TextView) convertView.findViewById(R.id.tv_planned_rate_deliveryweight);
            mViewHodler.tv_qjcontractweight = (TextView) convertView.findViewById(R.id.tv_planned_rate_qjcontractweight);
            convertView.setTag(mViewHodler);
        } else {
            mViewHodler = (ViewHolder) convertView.getTag();
        }
        mViewHodler.textViewGCMC.setText(list.get(position).gcname);
        if (currentFagment.contains(PlannedCashRateConfig.GC_SJ_FH_TWO_FRAGMENT_TAG)) {

            RelativeLayout.LayoutParams configweight = (RelativeLayout.LayoutParams) mViewHodler.progressBar_configweight.getLayoutParams();
            configweight.width = proportionWidth(Float.parseFloat(list.get(position).monthlydeliveryweight));
            mViewHodler.progressBar_configweight.setLayoutParams(configweight);
            mViewHodler.tv_configweight.setText(list.get(position).monthlydeliveryweight);
            RelativeLayout.LayoutParams contractweight = (RelativeLayout.LayoutParams) mViewHodler.progressBar_contractweight.getLayoutParams();
            contractweight.width = proportionWidth(Float.parseFloat(list.get(position).contractweight));
            mViewHodler.progressBar_contractweight.setLayoutParams(contractweight);
            mViewHodler.tv_contractweight.setText(list.get(position).contractweight);
            RelativeLayout.LayoutParams deliveryweight = (RelativeLayout.LayoutParams) mViewHodler.progressBar_deliveryweight.getLayoutParams();
            deliveryweight.width = proportionWidth(Float.parseFloat(list.get(position).deliveryweight));
            mViewHodler.progressBar_deliveryweight.setLayoutParams(deliveryweight);
            mViewHodler.tv_deliveryweight.setText(list.get(position).deliveryweight);
            RelativeLayout.LayoutParams monthlydeliveryweight = (RelativeLayout.LayoutParams) mViewHodler.progressBar_qjcontractweight.getLayoutParams();
            monthlydeliveryweight.width = proportionWidth(Float.parseFloat(list.get(position).qjcontractweight));
            mViewHodler.progressBar_qjcontractweight.setLayoutParams(monthlydeliveryweight);
            mViewHodler.tv_qjcontractweight.setText(list.get(position).qjcontractweight);
            //monthlydeliveryweight

        } else {
            RelativeLayout.LayoutParams configweight = (RelativeLayout.LayoutParams) mViewHodler.progressBar_configweight.getLayoutParams();
            configweight.width = proportionWidth(Float.parseFloat(list.get(position).configweight));
            mViewHodler.progressBar_configweight.setLayoutParams(configweight);
            mViewHodler.tv_configweight.setText(list.get(position).configweight);

            RelativeLayout.LayoutParams contractweight = (RelativeLayout.LayoutParams) mViewHodler.progressBar_contractweight.getLayoutParams();
            contractweight.width = proportionWidth(Float.parseFloat(list.get(position).contractweight));
            mViewHodler.progressBar_contractweight.setLayoutParams(contractweight);
            mViewHodler.tv_contractweight.setText(list.get(position).contractweight);

            RelativeLayout.LayoutParams deliveryweight = (RelativeLayout.LayoutParams) mViewHodler.progressBar_deliveryweight.getLayoutParams();
            deliveryweight.width = proportionWidth(Float.parseFloat(list.get(position).deliveryweight));
            mViewHodler.progressBar_deliveryweight.setLayoutParams(deliveryweight);
            mViewHodler.tv_deliveryweight.setText(list.get(position).deliveryweight);

            RelativeLayout.LayoutParams qjcontractweight = (RelativeLayout.LayoutParams) mViewHodler.progressBar_qjcontractweight.getLayoutParams();
            qjcontractweight.width = proportionWidth(Float.parseFloat(list.get(position).qjcontractweight));
            mViewHodler.progressBar_qjcontractweight.setLayoutParams(qjcontractweight);
            mViewHodler.tv_qjcontractweight.setText(list.get(position).qjcontractweight);
        }
        return convertView;
    }

    class ViewHolder {
        TextProgressBar progressBar_configweight;
        TextProgressBar progressBar_contractweight;
        TextProgressBar progressBar_deliveryweight;
        TextProgressBar progressBar_qjcontractweight;
        TextView textViewGCMC;
        TextView tv_configweight;
        TextView tv_contractweight;
        TextView tv_deliveryweight;
        TextView tv_qjcontractweight;
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }

    /**
     * 算pro宽度
     *
     * @param value
     * @return
     */
    public int proportionWidth(float value) {
        if (value < 0.0f) {//如果小于0 按照0处理
            value = 0.0f;
        }
        return (int) ((value / Float.parseFloat((maxProgress + ""))) * width);
    }
}
