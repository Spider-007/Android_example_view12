package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.constant.QualityBookSituationBean;

import java.util.List;

/**
 * Created by htmitech on 2018/8/10.
 */

public class QualitybookSituationAdapter extends BaseAdapter {

    private Context context;
    private List<QualityBookSituationBean.ResultsBean> qualityBookSituationList;
    private final LayoutInflater inflater;

    public QualitybookSituationAdapter(Context context, List<QualityBookSituationBean.ResultsBean> qualityBookSituationList) {
        this.context = context;
        this.qualityBookSituationList = qualityBookSituationList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return qualityBookSituationList != null ? qualityBookSituationList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return qualityBookSituationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder mViewHolder;
        if(view == null){
            mViewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_quality_book_situation,null);
            mViewHolder.tvCarNum = (TextView) view.findViewById(R.id.tv_carnum);
            mViewHolder.tvTime = (TextView) view.findViewById(R.id.tv_time);
            mViewHolder.tvGuixing = (TextView) view.findViewById(R.id.tv_guixing);
            mViewHolder.tvCaizhi = (TextView) view.findViewById(R.id.tv_caizhi);
            mViewHolder.tvSudu = (TextView) view.findViewById(R.id.tv_sudu);
            mViewHolder.tvDunshu = (TextView) view.findViewById(R.id.tv_dunshu);
            mViewHolder.tvGenshu = (TextView) view.findViewById(R.id.tv_genshu);
            mViewHolder.tvDzname = (TextView) view.findViewById(R.id.tv_dzname);
            view.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) view.getTag();
        }
        mViewHolder.tvCarNum.setText(qualityBookSituationList.get(i).getZbsid());
        mViewHolder.tvTime.setText(qualityBookSituationList.get(i).getSenddate());
        mViewHolder.tvGuixing.setText(qualityBookSituationList.get(i).getGuixing());
        mViewHolder.tvCaizhi.setText(qualityBookSituationList.get(i).getCaizhi());
        mViewHolder.tvSudu.setText(qualityBookSituationList.get(i).getSudu());
        if(qualityBookSituationList.get(i).getDunshu() != null){
            mViewHolder.tvDunshu.setText(qualityBookSituationList.get(i).getDunshu()+"吨");
        }else{
            mViewHolder.tvDunshu.setText(qualityBookSituationList.get(i).getDunshu());
        }
        if(qualityBookSituationList.get(i).getGenshu() != null){
            mViewHolder.tvGenshu.setText(qualityBookSituationList.get(i).getGenshu()+"根");
        }else{
            mViewHolder.tvGenshu.setText(qualityBookSituationList.get(i).getGenshu());
        }
        mViewHolder.tvDzname.setText(qualityBookSituationList.get(i).getDzname());
        return view;
    }

    class ViewHolder {
         TextView tvCarNum;
         TextView tvTime;
         TextView tvGuixing;
         TextView tvCaizhi;
         TextView tvSudu;
         TextView tvDunshu;
         TextView tvGenshu;
         TextView tvDzname;
    }
}
