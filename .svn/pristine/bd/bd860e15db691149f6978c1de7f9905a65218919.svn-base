package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.constant.QualityObjectionDetailClqkListResult;
import com.htmitech.ztcustom.zt.interfaces.OnQualityObjectionDetailClqkImageViewClickCallBack;
import com.htmitech.ztcustom.zt.util.GlideUtil;

import java.util.List;


/**
 * Created by Administrator on 2018/6/11.
 */

public class QualityObjectionClqkAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private Context context;
    private List<QualityObjectionDetailClqkListResult> listResults;
    private OnQualityObjectionDetailClqkImageViewClickCallBack callBack;

    public QualityObjectionClqkAdapter(Context context, List<QualityObjectionDetailClqkListResult> listResults, OnQualityObjectionDetailClqkImageViewClickCallBack callBack) {
        this.context = context;
        this.listResults = listResults;
        this.callBack = callBack;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listResults.size();
    }

    @Override
    public Object getItem(int position) {
        return listResults.get(position);
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
            convertView = inflater.inflate(R.layout.qulality_objection_clqk_adapter_item, null);
            mViewHodler.tvchulidanwei = (TextView) convertView.findViewById(R.id.tv_quality_objection_clqk_adapter_unit);
            mViewHodler.tvchuliren = (TextView) convertView.findViewById(R.id.tv_quality_objection_clqk_adapter_company);
            mViewHodler.tvshijian = (TextView) convertView.findViewById(R.id.tv_quality_objection_clqk_adapter_data);
            mViewHodler.tvchuliqingkuang = (TextView) convertView.findViewById(R.id.tv_quality_objection_clqk_adapter_detail);
            mViewHodler.imageView1 = (ImageView) convertView.findViewById(R.id.iv_quality_objection_clqk_adapter_image1);
            mViewHodler.imageView2 = (ImageView) convertView.findViewById(R.id.iv_quality_objection_clqk_adapter_image2);
            mViewHodler.imageView3 = (ImageView) convertView.findViewById(R.id.iv_quality_objection_clqk_adapter_image3);
            convertView.setTag(mViewHodler);
        } else {
            mViewHodler = (ViewHolder) convertView.getTag();
        }

        mViewHodler.tvchulidanwei.setText(listResults.get(position).chulidanwei);
        mViewHodler.tvchuliren.setText(listResults.get(position).chuliren);
        mViewHodler.tvshijian.setText(listResults.get(position).shijian);
        mViewHodler.tvchuliqingkuang.setText(listResults.get(position).chuliqingkuang);
        if (listResults.get(position).smallphoto.size() > 0) {
            try {
                if (listResults.get(position).smallphoto.get(0) != null) {
                    GlideUtil.loadGild(context, listResults.get(position).smallphoto.get(0), R.drawable.img_zlyy_list_thumbnail, R.drawable.img_zlyy_list_thumbnail, mViewHodler.imageView1);
                    mViewHodler.imageView1.setOnClickListener(new OnImageViewClickListener(listResults.get(position).id, listResults.get(position).id + "-01"));
                }
                if (listResults.get(position).smallphoto.get(1) != null) {
                    GlideUtil.loadGild(context, listResults.get(position).smallphoto.get(1), R.drawable.img_zlyy_list_thumbnail, R.drawable.img_zlyy_list_thumbnail, mViewHodler.imageView2);
                    mViewHodler.imageView2.setOnClickListener(new OnImageViewClickListener(listResults.get(position).id, listResults.get(position).id + "-02"));
                }
                if (listResults.get(position).smallphoto.get(2) != null) {
                    GlideUtil.loadGild(context, listResults.get(position).smallphoto.get(2), R.drawable.img_zlyy_list_thumbnail, R.drawable.img_zlyy_list_thumbnail, mViewHodler.imageView3);
                    mViewHodler.imageView3.setOnClickListener(new OnImageViewClickListener(listResults.get(position).id, listResults.get(position).id + "-03"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            mViewHodler.imageView1.setImageResource(R.drawable.img_zlyy_list_thumbnail);
            mViewHodler.imageView2.setImageResource(R.drawable.img_zlyy_list_thumbnail);
            mViewHodler.imageView3.setImageResource(R.drawable.img_zlyy_list_thumbnail);
        }

        return convertView;
    }

    class OnImageViewClickListener implements View.OnClickListener {

        public String id;
        public String fileId;

        public OnImageViewClickListener(String id, String fileId) {
            this.id = id;
            this.fileId = fileId;
        }

        @Override
        public void onClick(View v) {
            callBack.onImageViewCallBack(id, fileId);
        }
    }

    class ViewHolder {
        TextView tvchulidanwei;
        TextView tvchuliren;
        TextView tvshijian;
        TextView tvchuliqingkuang;
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
    }
}
