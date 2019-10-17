package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.constant.QualityObjectionListSearchResultList;
import com.htmitech.ztcustom.zt.util.GlideUtil;

import java.util.List;

/**
 * Created by heyangv on 2018/5/22.
 */

public class QualityObjectionDealOverAdapter extends BaseAdapter {


    private int[] colors = new int[]{0x30FF0000, 0x300000FF};
    private LayoutInflater inflater = null;
    private Context context;
    private List<QualityObjectionListSearchResultList> list;

    public QualityObjectionDealOverAdapter(Context context, List<QualityObjectionListSearchResultList> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
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
        ViewHodler mViewHodler = null;
        if (convertView == null) {
            mViewHodler = new ViewHodler();
            convertView = inflater.inflate(R.layout.qualtiy_objection_deal_over_adapter_item, null);
            mViewHodler.iv_pic = (ImageView) convertView.findViewById(R.id.iv_quality_objection_deal_over_pic);
            mViewHodler.tv_title_name = (TextView) convertView.findViewById(R.id.tv_quality_objection_deal_over_title_name);
            mViewHodler.tv_code = (TextView) convertView.findViewById(R.id.tv_quality_objection_deal_over_code);
            mViewHodler.tv_detail = (TextView) convertView.findViewById(R.id.tv_quality_objection_deal_over_detail);
            mViewHodler.tv_time = (TextView) convertView.findViewById(R.id.tv_quality_objection_deal_over_time);
            mViewHodler.checkBox = (CheckBox) convertView.findViewById(R.id.cb_quality_objection_deal_over);
            convertView.setTag(mViewHodler);
        } else {
            mViewHodler = (ViewHodler) convertView.getTag();
        }
        //-----------
        mViewHodler.tv_title_name.setText(list.get(position).danweimingcheng);
        mViewHodler.tv_code.setText(list.get(position).id);
        mViewHodler.tv_time.setText(list.get(position).riqi);
        String detail = "";
        detail = list.get(position).guixing + " " + list.get(position).guige + " " + list.get(position).gangchang + " " + list.get(position).changdu;
        mViewHodler.tv_detail.setText(detail);
        if (list.get(position).photo != null && list.get(position).photo.size() != 0) {
            GlideUtil.loadGild(context, list.get(position).photo.get(0), R.drawable.img_zlyy_list_thumbnail, R.drawable.img_zlyy_list_thumbnail, mViewHodler.iv_pic);
        }
        mViewHodler.checkBox.setChecked(list.get(position).isCheckd);
        //-----------
        int colorPos = position % colors.length;
        if (colorPos == 1)
            convertView.setBackgroundColor(Color.rgb(0, 20, 67));
        else
            convertView.setBackgroundColor(Color.rgb(0, 9, 49));
        return convertView;
    }


    class ViewHodler {
        CheckBox checkBox;
        TextView tv_title_name;
        TextView tv_time;
        TextView tv_code;
        TextView tv_detail;
        ImageView iv_pic;
    }
}
