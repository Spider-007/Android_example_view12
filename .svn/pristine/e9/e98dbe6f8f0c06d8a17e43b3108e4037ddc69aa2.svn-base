package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
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
 * Created by Administrator on 2018/6/14.
 */

public class QualityObjectionDetailJBXXAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context;
    private LayoutInflater inflater;

    public QualityObjectionDetailJBXXAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
            convertView = inflater.inflate(R.layout.zb_broad_detal_quality_objection_sb_item, null);
            mViewHodler.tvstart = (TextView) convertView.findViewById(R.id.zb_broad_detail_red_start_sb);
            mViewHodler.tvkey = (TextView) convertView.findViewById(R.id.tv_key_sb);
            mViewHodler.tvvalue = (TextView) convertView.findViewById(R.id.tv_value_sb);
            convertView.setTag(mViewHodler);
        } else {
            mViewHodler = (ViewHolder) convertView.getTag();
        }
        mViewHodler.tvkey.setText(data.get(position).get("key").toString());
        try {
            mViewHodler.tvvalue.setText(data.get(position).get("value").toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("YJH", "getView: "+e + "--------1716-------" +e.getMessage() );
            mViewHodler.tvvalue.setText("");
        }
        if (mViewHodler.tvkey.getText().equals("填报人") || mViewHodler.tvkey.getText().equals("填报单位") || mViewHodler.tvkey.getText().equals("填报日期")
                || mViewHodler.tvkey.getText().equals("联系人") || mViewHodler.tvkey.getText().equals("联系方式") || mViewHodler.tvkey.getText().equals("项目名称")
                || mViewHodler.tvkey.getText().equals("钢厂") || mViewHodler.tvkey.getText().equals("简要描述")) {
            mViewHodler.tvstart.setVisibility(View.VISIBLE);
        } else {
            mViewHodler.tvstart.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvstart;
        TextView tvkey;
        TextView tvvalue;
    }
}
