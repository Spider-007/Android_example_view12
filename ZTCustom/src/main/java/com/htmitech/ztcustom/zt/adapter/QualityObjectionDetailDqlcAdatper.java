package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.constant.QualityObjectionDetailDqlcListResult;

import java.util.List;

/**
 * Created by heyang on 2018/6/13.
 */

public class QualityObjectionDetailDqlcAdatper extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<QualityObjectionDetailDqlcListResult> listResults;

    public QualityObjectionDetailDqlcAdatper(Context context, List<QualityObjectionDetailDqlcListResult> listResults) {
        this.context = context;
        this.listResults = listResults;
        layoutInflater = LayoutInflater.from(context);
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
            convertView = layoutInflater.inflate(R.layout.quality_objection_dtail_dqlc_adapter_item, null);
            mViewHodler.tvcaozuodanwei = (TextView) convertView.findViewById(R.id.tv_quality_objection_detail_dqlc_adapter_unit);
            mViewHodler.tvcaozuoren = (TextView) convertView.findViewById(R.id.tv_quality_objection_detail_dqlc_adapter_username);
            mViewHodler.tvshijian = (TextView) convertView.findViewById(R.id.tv_quality_objection_detail_dqlc_adapter_time);
            mViewHodler.tvcaozuo = (TextView) convertView.findViewById(R.id.tv_quality_objection_detail_dqlc_adapter_state);
            mViewHodler.tvcaozuoneirong = (TextView) convertView.findViewById(R.id.tv_quality_objection_detail_dqlc_adapter_detail);
            mViewHodler.ivHeader = (ImageView) convertView.findViewById(R.id.iv_quality_objection_detail_dqlc_adapter_head);
            mViewHodler.tvbottomline = (TextView) convertView.findViewById(R.id.iv_quality_objection_detail_dqlc_adapter_bottomline);
            convertView.setTag(mViewHodler);
        } else {
            mViewHodler = (ViewHolder) convertView.getTag();
        }

        mViewHodler.tvcaozuodanwei.setText(listResults.get(position).caozuodanwei);
        mViewHodler.tvcaozuoren.setText("提交人：" + listResults.get(position).caozuoren);
        mViewHodler.tvshijian.setText(listResults.get(position).shijian);
        mViewHodler.tvcaozuo.setText(listResults.get(position).caozuo);
        mViewHodler.tvcaozuoneirong.setText(listResults.get(position).caozuoneirong);
        mViewHodler.ivHeader.setImageResource(R.drawable.icon_dqlc_finished);
        mViewHodler.tvbottomline.setVisibility(View.VISIBLE);
        if (position == listResults.size() - 1) {
            mViewHodler.ivHeader.setImageResource(R.drawable.icon_dqlc_present);
            mViewHodler.tvbottomline.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView ivHeader;
        TextView tvcaozuodanwei;
        TextView tvcaozuoren;
        TextView tvshijian;
        TextView tvcaozuo;
        TextView tvcaozuoneirong;
        TextView tvbottomline;

    }
}
