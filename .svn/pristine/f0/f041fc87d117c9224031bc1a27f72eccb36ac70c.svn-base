package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.domain.DefectDetailList;

import java.util.ArrayList;

/**
 * 卡片列表Adapter
 */
public class Shangsxqdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater ;
    private ArrayList<DefectDetailList> mDefectDetailList;
    public Shangsxqdapter(Context context,ArrayList<DefectDetailList> mDefectDetailList){
        this.context = context;
        inflater = LayoutInflater.from(context);
        if(mDefectDetailList == null){
           this. mDefectDetailList = new ArrayList<DefectDetailList>();

        }else{
            this. mDefectDetailList = mDefectDetailList;
        }
    }

    public void setData(ArrayList<DefectDetailList> mDefectDetailList){
        if(mDefectDetailList == null){
            this. mDefectDetailList = new ArrayList<DefectDetailList>();

        }else{
            this. mDefectDetailList = mDefectDetailList;
        }
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mDefectDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDefectDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler mViewHodler = null;
        DefectDetailList detailList = mDefectDetailList.get(position);
        if(convertView == null){
            mViewHodler = new ViewHodler();
            convertView  = inflater.inflate(R.layout.zt_shangsxq_adapter,null);
            mViewHodler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            mViewHodler.tv_value = (TextView) convertView.findViewById(R.id.tv_value);
            convertView.setTag(mViewHodler);
        }else{
            mViewHodler = (ViewHodler) convertView.getTag();
        }
        mViewHodler.tv_name.setText(detailList.label);
        mViewHodler.tv_value.setText(detailList.value);
        return convertView;
    }


    public class ViewHodler{
        public TextView tv_name ;
        public TextView tv_value;
    }
}
