package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.domain.DefectList;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.DicttypeResult;

import java.util.ArrayList;

/**
 * 卡片列表Adapter
 */
public class CardListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater ;
    private ArrayList<DefectList> mDefectList;
    public CardListAdapter(Context context,ArrayList<DefectList> mDefectList){
        this.context = context;
        if(mDefectList == null){
            this.mDefectList = new ArrayList<DefectList>();

        }else{
            this.mDefectList = mDefectList;
        }
        inflater = LayoutInflater.from(context);
    }
    public void setData(ArrayList<DefectList> mDefectList,int flag){
        if(mDefectList == null){
            this.mDefectList = new ArrayList<DefectList>();
        }else{
            if(flag == 0){
                if(this.mDefectList == null){
                    this.mDefectList = new ArrayList<DefectList>();
                    this.mDefectList = mDefectList;
                }else{
                    this.mDefectList = mDefectList;
                }
            }else{
                if(this.mDefectList == null){
                    this.mDefectList = new ArrayList<DefectList>();
                    this.mDefectList = mDefectList;
                }else{
                    this.mDefectList.addAll(mDefectList);
                }
            }


        }
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mDefectList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDefectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHodler mViewHodler = null;
        DefectList detailList = mDefectList.get(position);
        if(convertView == null){
            mViewHodler = new ViewHodler();
            convertView  = inflater.inflate(R.layout.zt_activity_card_list_adapter,null);
            mViewHodler.tv_card_head_address = (TextView) convertView.findViewById(R.id.tv_card_head_address);
            mViewHodler.tv_card_head_meter = (ImageView) convertView.findViewById(R.id.tv_card_head_meter);
            mViewHodler.tv_jiance_data = (TextView) convertView.findViewById(R.id.tv_jiance_data);
            mViewHodler.tv_danwei = (TextView) convertView.findViewById(R.id.tv_danwei);
            mViewHodler.tv_shangsun_x = (TextView) convertView.findViewById(R.id.tv_shangsun_xiangq);
            mViewHodler.tv_xianbie = (TextView) convertView.findViewById(R.id.tv_xianbie);
            mViewHodler.tv_shangsun_weizhi = (TextView) convertView.findViewById(R.id.tv_shangsun_weizhi);
            mViewHodler.tv_line_name = (TextView) convertView.findViewById(R.id.tv_line_name);
            mViewHodler.tv_hangbie = (TextView) convertView.findViewById(R.id.tv_hangbie);
            mViewHodler.tv_shangsun_type = (TextView) convertView.findViewById(R.id.tv_shangsun_type);
            convertView.setTag(mViewHodler);
        }else{
            mViewHodler = (ViewHodler) convertView.getTag();
        }
        if(detailList.sblx.equals("DXHF") || detailList.sblx.equals("ZXHF") || detailList.sblx.equals("ZXHF")|| detailList.sblx.equals("HF")){
            mViewHodler.tv_card_head_meter.setImageDrawable(context.getResources().getDrawable(R.drawable.welding_line));
        }else if(detailList.sblx.equals("DC")){
            mViewHodler.tv_card_head_meter.setImageDrawable(context.getResources().getDrawable(R.drawable.turnout));
        }else if(detailList.sblx.equals("ZXGG")||detailList.sblx.equals("GG")){
            mViewHodler.tv_card_head_meter.setImageDrawable(context.getResources().getDrawable(R.drawable.rail));
        }
        DicttypeResult mDicttypeResult = ZTCustomInit.get().getmCache().getmDicttypeResult().get("SBLX");
        for(Dictitemlist mDictitemlist : mDicttypeResult.dictitemlist){
            if(mDictitemlist.getName().equalsIgnoreCase(detailList.sblx)){
                mViewHodler.tv_card_head_address.setText(mDictitemlist.getName()+"  "+detailList.check_time);
                break;
            }
        }

        mViewHodler.tv_jiance_data.setText(detailList.defect_id + "");
        mViewHodler.tv_danwei.setText(detailList.tlj_name+"/"+detailList.gwd_name+"/"+detailList.gq_name);
        mViewHodler.tv_xianbie.setText(detailList.linetype_name);
        mViewHodler.tv_shangsun_weizhi.setText(detailList.sswz_name);
        mViewHodler.tv_line_name.setText(detailList.line_name);
        mViewHodler.tv_hangbie.setText(detailList.xb_name);
        mViewHodler.tv_shangsun_x.setText(detailList.defect_desc);
        mViewHodler.tv_shangsun_type.setText(detailList.sslx);
        return convertView;
    }


    public class ViewHodler{
        public TextView tv_card_head_address ;
        public ImageView tv_card_head_meter;
        public TextView tv_jiance_data;
        public TextView tv_danwei;
        public TextView tv_xianbie;
        public TextView tv_shangsun_weizhi;
        public TextView tv_line_name;
        public TextView tv_hangbie;
        public TextView tv_shangsun_x;
        public TextView tv_shangsun_type;
    }
    public ArrayList<DefectList> getDefectList(){
        return mDefectList;
    }
}
