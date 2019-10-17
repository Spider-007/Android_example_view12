package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.domain.CZList;
import com.htmitech.ztcustom.zt.domain.HistoryList;

import java.util.ArrayList;

/**
 * 卡片列表Adapter
 */
public class HistoryListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater ;
    private ArrayList<HistoryList> mHistoryList;
    private Animation mExpandAnimation;
    private Animation mCollapseAnimation;
    private int flag = 0;
    public HistoryListAdapter(Context context, ArrayList<HistoryList> mHistoryList){
        this.context = context;
        if(mHistoryList == null){
            this.mHistoryList = new ArrayList<HistoryList>();

        }else{
            this.mHistoryList = mHistoryList;
        }
        mExpandAnimation = AnimationUtils.loadAnimation(context, R.anim.expand);
        mCollapseAnimation = AnimationUtils.loadAnimation(context, R.anim.collapse);

        inflater = LayoutInflater.from(context);
    }

    /**
     * 1是上啦 0是下拉
     * @param mHistoryList
     * @param flag
     */
    public void setData( ArrayList<HistoryList> mHistoryList,int flag){
        this.flag = flag;
        if(mHistoryList == null){
            this.mHistoryList = new ArrayList<HistoryList>();
        }else{
            if(flag == 0){
                if(this.mHistoryList == null){
                    this.mHistoryList = new ArrayList<HistoryList>();
                    this.mHistoryList = mHistoryList;
                }else{
                    this.mHistoryList = mHistoryList;
                }
            }else{
                if(this.mHistoryList == null){
                    this.mHistoryList = new ArrayList<HistoryList>();
                    this.mHistoryList = mHistoryList;
                }else{
                    this.mHistoryList.addAll(mHistoryList);
                }
            }


        }
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mHistoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHistoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler mViewHodler = null;
        HistoryChildHodler mHistoryChildHodler = null;
        HistoryList detailList = mHistoryList.get(position);
        if(convertView == null){
            mViewHodler = new ViewHodler();
            convertView  = inflater.inflate(R.layout.zt_activity_history_list_adapter,null);
            mViewHodler.tv_card_head_address = (TextView) convertView.findViewById(R.id.tv_card_head_address);
            mViewHodler.tv_history_time = (TextView) convertView.findViewById(R.id.tv_history_time);
            mViewHodler.tv_sscd = (TextView) convertView.findViewById(R.id.tv_sscd);
            mViewHodler.tv_jcy = (TextView) convertView.findViewById(R.id.tv_jcy);
            mViewHodler.tv_xlgq = (TextView) convertView.findViewById(R.id.tv_xlgq);
            mViewHodler.tv_ssxq = (TextView) convertView.findViewById(R.id.tv_ssxq);
            mViewHodler.ll_chil_layout = (LinearLayout) convertView.findViewById(R.id.ll_chil_layout);
            convertView.setTag(mViewHodler);
        }else{
            mViewHodler = (ViewHodler) convertView.getTag();
        }
        mViewHodler.ll_chil_layout.removeAllViews();
        if(detailList.defect_cz_list != null && detailList.defect_cz_list.size() != 0){
            for(CZList mCZList:detailList.defect_cz_list){
                mViewHodler.ll_chil_layout.addView(addView(mCZList));
            }
        }

        mViewHodler.tv_card_head_address.setText(detailList.xlgq_name);
        mViewHodler.tv_sscd.setText(detailList.sscd_name);
        mViewHodler.tv_jcy.setText(detailList.check_username);
        mViewHodler.tv_ssxq.setText(detailList.defect_desc);
        mViewHodler.tv_history_time.setText(detailList.check_time);
        mViewHodler.tv_xlgq.setText(detailList.xlgq_name);
        for(int i=0;i<mViewHodler.ll_chil_layout.getChildCount();i++){
            mHistoryChildHodler=(HistoryChildHodler) mViewHodler.ll_chil_layout.getChildAt(i).getTag();
            if( i < detailList.defect_cz_list.size()){
                mHistoryChildHodler.tv_history_child_time.setText(detailList.defect_cz_list.get(i).cz_date);
                mHistoryChildHodler.tv_czcs.setText(detailList.defect_cz_list.get(i).czcs_name);
                mHistoryChildHodler.tv_czcj.setText(detailList.defect_cz_list.get(i).czzj_name);
                mHistoryChildHodler.tv_sgfzr.setText(detailList.defect_cz_list.get(i).sgzrr_name);
                mHistoryChildHodler.tv_bz.setText(detailList.defect_cz_list.get(i).bz);
            }
        }

        return convertView;
    }


    public class ViewHodler{
        public TextView tv_card_head_address ;
        public TextView tv_history_time ;
        public TextView tv_sscd ;
        public TextView tv_jcy ;
        public TextView tv_xlgq ;
        public TextView tv_ssxq ;
        public LinearLayout ll_chil_layout ;
    }

    public class HistoryChildHodler{
        public TextView tv_history_child_address ;
        public TextView tv_history_child_time ;
        public TextView tv_czcs ;
        public TextView tv_czcj ;
        public TextView tv_sgfzr ;
        public TextView tv_bz ;
        public RelativeLayout rl_top;
        public LinearLayout ll_center;
        public ImageView iv_button;
    }

    public View addView(CZList mCZList){
        HistoryChildHodler mHistoryChildHodler = new HistoryChildHodler();
        View view =LayoutInflater.from(context).inflate(
                R.layout.zt_activity_history_child_list_adapter, null);
        mHistoryChildHodler.tv_history_child_address = (TextView)view.findViewById(R.id.tv_history_child_address);
        mHistoryChildHodler.tv_history_child_time = (TextView)view.findViewById(R.id.tv_history_child_time);
        mHistoryChildHodler.tv_czcs = (TextView)view.findViewById(R.id.tv_czcs);
        mHistoryChildHodler.tv_czcj = (TextView)view.findViewById(R.id.tv_czcj);
        mHistoryChildHodler.tv_sgfzr = (TextView)view.findViewById(R.id.tv_sgfzr);
        mHistoryChildHodler.tv_bz = (TextView)view.findViewById(R.id.tv_bz);
        mHistoryChildHodler.iv_button= (ImageView)view.findViewById(R.id.iv_button);
        mHistoryChildHodler.rl_top = (RelativeLayout)view.findViewById(R.id.rl_top);
        mHistoryChildHodler.ll_center = (LinearLayout)view.findViewById(R.id.ll_center);
        mHistoryChildHodler.rl_top.setOnClickListener(new OnclickChildListener(mHistoryChildHodler.ll_center,mHistoryChildHodler.iv_button, mCZList));
        mHistoryChildHodler.ll_center.setVisibility(mCZList.isStatus ? View.VISIBLE:View.GONE);
        view.setTag(mHistoryChildHodler);
        return view;
    }
    public class OnclickChildListener implements View.OnClickListener{
        public LinearLayout llcenter;
        public ImageView ivButton;
        private CZList mCZList;
        public OnclickChildListener(LinearLayout llcenter,ImageView ivButton,CZList mCZList){
            this.llcenter = llcenter;
            this.ivButton = ivButton;
            this.mCZList = mCZList;
        }
        @Override
        public void onClick(View v) {
            if(llcenter.isShown()){
//                llcenter.setVisibility(View.GONE);
                mCZList.isStatus = false;
                llcenter.clearAnimation();
                llcenter.startAnimation(mCollapseAnimation);
                mCollapseAnimation.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        llcenter.setVisibility(View.GONE);
                        ivButton.setImageResource(R.drawable.btn_up);
                    }
                });




            }else{
                mCZList.isStatus = true;
                llcenter.clearAnimation();
                llcenter.startAnimation(mExpandAnimation);
                mExpandAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        llcenter.setVisibility(View.VISIBLE);
                        ivButton.setImageResource(R.drawable.btn_down);
                    }
                });

            }
        }
    }

    public ArrayList<HistoryList> getmHistoryList() {
        return mHistoryList;
    }
}
