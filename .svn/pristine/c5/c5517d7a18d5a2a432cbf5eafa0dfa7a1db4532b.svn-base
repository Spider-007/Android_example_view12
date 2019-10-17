package com.htmitech.htworkflowformpluginnew.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.MyView.SearchEditText;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragment;
import com.htmitech.emportal.R;
import com.htmitech.proxy.doman.ReportSosoResult;
import com.htmitech.proxy.doman.SosoResult;
import com.htmitech.proxy.interfaces.CallBackDamageTypeGridListener;
import com.htmitech.unit.DensityUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by htrf-pc on 2017/4/17.
 */
public class GYLStatiscalReportFragment extends LinearLayout  implements View.OnClickListener {
    private CallBackDamageTypeGridListener mCallBackDamageTypeGridListener;
    private LinearLayout layout_type;
    private String input_type = "";
    private ArrayList<ReportSosoResult> sosoResults;
    private TextView checkView;
    private Context context;
    private Map<Integer, ArrayList<DamageCaeck>> cacheMap;
    private LayoutInflater inflater;
    private View view;
    public GYLStatiscalReportFragment(Context context) {
        super(context);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public GYLStatiscalReportFragment(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.fragment_statistical_report,this);
    }

    public GYLStatiscalReportFragment(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.fragment_statistical_report,this);
    }

    public CallBackDamageTypeGridListener getmCallBackDamageTypeGridListener() {
        return mCallBackDamageTypeGridListener;
    }

    public void setmCallBackDamageTypeGridListener(CallBackDamageTypeGridListener mCallBackDamageTypeGridListener) {
        this.mCallBackDamageTypeGridListener = mCallBackDamageTypeGridListener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistical_report, container,
                false);
    }

    protected void initData() {
        // TODO Auto-generated method stub

        if (sosoResults == null) {
            return;
        }

        cacheMap = new HashMap<Integer, ArrayList<DamageCaeck>>();
        try {


            layout_type = (LinearLayout) view.findViewById(R.id.layout_type);
            layout_type.removeAllViews();
            LinearLayout layout_child = new LinearLayout(context);
            layout_child.setOrientation(LinearLayout.VERTICAL);
            layout_child.setGravity(Gravity.CENTER_VERTICAL);

            LinearLayout.LayoutParams layoutParams_child = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layout_child.setLayoutParams(layoutParams_child);
            layout_child.setId(R.id.gyl_id);
            layout_type.addView(layout_child);

            WindowManager wm = ((Activity)context).getWindowManager();
            int width = wm.getDefaultDisplay().getWidth();
            int num = 0;
            int textHight = 70;
            if (width == 1080) {
                textHight = DensityUtil.dip2px(context, 40);;
            } else {
                textHight = DensityUtil.dip2px(context, 40);
            }

            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, textHight);
            layoutParams.setMargins(0, 10, 20, 10);
            layout.setLayoutParams(layoutParams);
            layout.setOrientation(LinearLayout.HORIZONTAL);
                    /*
                     * 排序
                     */
//            Comparator comp = new Comparator() {
//                public int compare(Object o1, Object o2) {
//                    ReportSosoResult p1 = (ReportSosoResult) o1;
//                    ReportSosoResult p2 = (ReportSosoResult) o2;
//                    if (p1.name.length() < p2.name.length())
//                        return -1;
//                    else if (p1.name.length() == p2.name.length())
//                        return 0;
//                    else if (p1.name.length() > p2.name.length())
//                        return 1;
//                    return 0;
//                }
//            };
//            Collections.sort(sosoResults, comp);
        /*
         * 四个字 每个TextView的宽度
		 */
            int colum4 = (width - 160) / 4;
        /*
         *两个字 每个TextView的宽度
		 */
            int colum2 = (width - 80) / 2;
        /*
         *一个字 每个TextView的宽度
		 */
            int colum1 = (width - 40) / 1;

            int index = 0;
            int indexColum4 = 0, indexColum2 = 0, indexColum1 = 0;
            int colums = (width) / 3 - 20* 3;

            for (ReportSosoResult dic : sosoResults) {


//                if (indexColum4 == 0) {
//                    layout = new LinearLayout(context);
//                    layout.setLayoutParams(layoutParams);
//                }
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                        colums , textHight);
                textparams.setMargins(20, 0, 0, 0);
                textView.setText(dic.name);
                if(dic.getIsSelectd() == 0){

                    if(Constant.color == 3){
                        textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_red);
                    }else{
                        textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                    }

                }else{
                    if(Constant.color == 3){
                        textView.setBackgroundResource(R.drawable.fragment_gridview_adapter_down_red);
                    }else{
                        textView.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);
                    }

                }
                if(index == sosoResults.size() - 1 && dic.name.equals("编辑")){
                    textView.setTag("编辑");
                    textView.setText("");
                    textView.setBackgroundResource(R.drawable.btn_todolist_screen_add);
                }
                textView.setLayoutParams(textparams);
                textView.setTextSize(14);
                textView.setSingleLine(false);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(10, 10, 10, 10);
                textView.setOnClickListener(new NameOnClickListener(layout_child, dic, textView, index));
                layout.addView(textView);
                index ++;
                if (index % 3 == 0) {
                    layout_child.addView(layout);
                    layout = new LinearLayout(context);
                    layout.setLayoutParams(layoutParams);
                }



//                num = (dic.name.length() * 57); //34是根据 字的大小和padding的左右宽度而来的 14 +
//                if (index % 3 == 0) {
//
//                    if (indexColum4 == 0) {
//                        layout = new LinearLayout(context);
//                        layout.setLayoutParams(layoutParams);
//                    }
//                    TextView textView = new TextView(context);
//                    LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
//                            colums , textHight, 1);
//                    textparams.setMargins(20, 0, 0, 0);
//                    textView.setText(dic.name);
//                    if(dic.getIsSelectd() == 0){
//
//                        if(Constant.color == 3){
//                            textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_red);
//                        }else{
//                            textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
//                        }
//
//                    }else{
//                        if(Constant.color == 3){
//                            textView.setBackgroundResource(R.drawable.fragment_gridview_adapter_down_red);
//                        }else{
//                            textView.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);
//                        }
//
//                    }
//                    if(index == sosoResults.size() - 1 && dic.name.equals("编辑")){
//                        textView.setTag("编辑");
//                        textView.setText("");
//                        textView.setBackgroundResource(R.drawable.btn_todolist_screen_add);
//                    }
//                    textView.setLayoutParams(textparams);
//                    textView.setTextSize(14);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setPadding(10, 10, 10, 10);
//                    textView.setOnClickListener(new NameOnClickListener(layout_child, dic, textView, index));
//                    layout.addView(textView);
//                    indexColum4++;
//                    if (indexColum4 == 3) {
//                        indexColum4 = 0;
//                        layout_child.addView(layout);
//                    }
//
//                } else if (num < colum2) {
//                /*
//				 * 把上一个最后一个没有Add进去的给add进去
//				 */
//                    if (indexColum4 != 0) {
//                        indexColum4 = 0;
//                        layout_child.addView(layout);
//                    }
//                    if (indexColum2 == 0) {
//                        layout = new LinearLayout(context);
//                        layout.setLayoutParams(layoutParams);
//                        layout.removeAllViews();
//                    }
//                    TextView textView = new TextView(context);
//                    LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
//                            colum2, textHight, 1);
//                    textparams.setMargins(20, 0, 0, 0);
//                    textView.setText(dic.name);
//                    if(dic.getIsSelectd() == 0){
//                        if(Constant.color == 3){
//                            textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_red);
//                        }else{
//                            textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
//                        }
//
//                    }else{
//                        if(Constant.color == 3){
//                            textView.setBackgroundResource(R.drawable.fragment_gridview_adapter_down_red);
//                        }else{
//                            textView.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);
//                        }
//
//                    }
//                    if(index == sosoResults.size() - 1 && dic.name.equals("编辑")){
//                        textView.setText("");
//                        textView.setTag("编辑");
//                        textView.setBackgroundResource(R.drawable.btn_todolist_screen_add);
//                    }
//                    textView.setLayoutParams(textparams);
//                    textView.setTextSize(14);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setPadding(10, 10, 10, 10);
//                    textView.setOnClickListener(new NameOnClickListener(layout_child, dic, textView, index));
//                    layout.addView(textView);
//                    indexColum2++;
//                    if (indexColum2 == 2) {
//                        indexColum2 = 0;
//                        layout_child.addView(layout);
//                    }
//                } else {
//				/*
//				 * 把上一个最后一个没有Add进去的给add进去
//				 */
//                    if (indexColum2 != 0) {
//                        indexColum2 = 0;
//                        layout_child.addView(layout);
//                    }
//                    layout = new LinearLayout(context);
//                    layout.setLayoutParams(layoutParams);
//                    layout.removeAllViews();
//                    TextView textView = new TextView(context);
//                    LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
//                            colum1, textHight, 1);
//                    textparams.setMargins(20, 0, 0, 0);
//                    textView.setText(dic.name);
//                    if(dic.getIsSelectd() == 0){
//                        if(Constant.color == 3){
//                            textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_red);
//                        }else{
//                            textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
//                        }
//
//                    }else{
//                        if(Constant.color == 3){
//                            textView.setBackgroundResource(R.drawable.fragment_gridview_adapter_down_red);
//                        }else{
//                            textView.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);
//                        }
//
//
//                    }
//                    if(index == sosoResults.size() - 1 && dic.name.equals("编辑")){
//                        textView.setText("");
//                        textView.setTag("编辑");
//                        textView.setBackgroundResource(R.drawable.btn_todolist_screen_add);
//                    }
//                    textView.setLayoutParams(textparams);
//                    textView.setTextSize(14);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setPadding(10, 10, 10, 10);
//                    textView.setOnClickListener(new NameOnClickListener(layout_child, dic, textView, index));
//                    layout.addView(textView);
//                    layout_child.addView(layout);
//                }

            }
            if (index % 3 != 0) {
                layout_child.addView(layout);
            }
            layout = new LinearLayout(context);
            layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 20);
            layout.setLayoutParams(layoutParams);
            layout_child.addView(layout);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 每个TextView的点击事件
     */
    public class NameOnClickListener implements View.OnClickListener, SearchEditText.SearchTextWatcher {

        private ReportSosoResult mDictitemlist;

        private TextView tvName;

        private LinearLayout layout_child;
        private int postion;

        public NameOnClickListener(LinearLayout layout_child, ReportSosoResult mDictitemlist, TextView tvName, int postion) {
            this.mDictitemlist = mDictitemlist;
            this.tvName = tvName;
            this.layout_child = layout_child;
            this.postion = postion;
        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            boolean isFlag = true;
            if (input_type == null || input_type.equals("")) {
                input_type = "503";
            }
            if (input_type.equals("503")) {
                if (cacheMap.get(postion) == null || cacheMap.get(postion).size() == 0) {
                    cacheMap.put(postion, new ArrayList<DamageCaeck>());
                }

                cacheMap.get(postion).clear();
//                for (int i = 0; i < layout_child.getChildCount(); i++) {
//                    LinearLayout mView = (LinearLayout) layout_child.getChildAt(i);
//                    for (int j = 0; j < mView.getChildCount(); j++) {
//                        View view = mView.getChildAt(j);
//                        view.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
//                    }
//
//                }
//                tvName.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);

//                GradientDrawable myGradPrevenans = (GradientDrawable) tvName.getBackground();
//
//                myGradPrevenans.setColor((getResources().getColor(
//                        R.color.ht_hred_title)));
                /**
                 * 缓存当前集合
                 */
                cacheMap.get(postion).add(new DamageCaeck(mDictitemlist, isFlag, postion));
//                mDamageCaeckList
//                        .add(new DamageCaeck(mDictitemlist, isFlag, postion));

//                cacheMap.put(postion, mDamageCaeckList);

                checkView = tvName;
            } else {
                for (DamageCaeck mDamageCaeck : mDamageCaeckList) {
                    if (mDamageCaeck.mDictitemlist.getName().equals(
                            mDictitemlist.getName())) {
                        mDamageCaeckList.remove(mDamageCaeck);
                        tvName.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                        isFlag = false;
                        break;
                    }
                }
                if (isFlag) {
                    tvName.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);

                    GradientDrawable myGradPrevenans = (GradientDrawable) tvName.getBackground();

                    myGradPrevenans.setColor((getResources().getColor(
                            R.color.ht_hred_title)));
                    mDamageCaeckList
                            .add(new DamageCaeck(mDictitemlist, isFlag, postion));
                }
            }
            ArrayList<ReportSosoResult> mArrayList = new ArrayList<ReportSosoResult>();

//            for (int key : cacheMap.keySet()) {
//                for (DamageCaeck mDamageCaeck : cacheMap.get(key)) {
//                    mDamageCaeck.mDictitemlist.setPostion(mDamageCaeck.postion);
//                    mArrayList.add(mDamageCaeck.mDictitemlist);
//                }
//            }
            mArrayList.add(mDictitemlist);
            mCallBackDamageTypeGridListener.callBack(mArrayList);

        }

        @Override
        public void afterTextChanged(String arg0) {
            if (cacheMap.get(postion) == null || cacheMap.get(postion).size() == 0) {
                cacheMap.put(postion, new ArrayList<DamageCaeck>());
            }
            cacheMap.get(postion).clear();
            /**
             * 缓存当前集合
             */
            mDictitemlist.setValue(arg0);
            cacheMap.get(postion).add(new DamageCaeck(mDictitemlist, true, postion));

        }
    }

    private ArrayList<DamageCaeck> mDamageCaeckList = new ArrayList<DamageCaeck>();

    private class DamageCaeck {
        ReportSosoResult mDictitemlist;
        boolean isFlag;
        int postion;

        public DamageCaeck(ReportSosoResult mDictitemlist, boolean isFlag, int postion) {
            this.mDictitemlist = mDictitemlist;
            this.isFlag = isFlag;
            this.postion = postion;
        }
    }


    public class ImageDownOnClick implements View.OnClickListener {
        public int position;
        private LinearLayout layout_child;

        public ImageDownOnClick(int position, LinearLayout layout_child) {
            this.position = position;
            this.layout_child = layout_child;
        }

        @Override
        public void onClick(View v) {
            ((ImageView) v).setImageResource(layout_child.isShown() ? R.drawable.btn_angle_down_circle : R.drawable.btn_angle_up_circle);
            layout_child.setVisibility(layout_child.isShown() ? View.GONE : View.VISIBLE);
        }
    }


    public class CompleteOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getTag().equals("qkxz")) {
                if (checkView != null) {
                    //清空所有缓存
                    for (int i = 0; i < cacheMap.size(); i++) {
                        if (cacheMap.get(i) != null)
                            cacheMap.get(i).clear();
                    }
//                    checkView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                    for (int w = 0; w < layout_type.getChildCount(); w++) {
                        try {
                            LinearLayout childLayout = ((LinearLayout) layout_type.getChildAt(w));
                            for (int i = 0; i < childLayout.getChildCount(); i++) {
                                try {
                                    LinearLayout mView = (LinearLayout) (childLayout).getChildAt(i);
                                    for (int j = 0; j < mView.getChildCount(); j++) {
                                        View view = mView.getChildAt(j);
                                        view.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                                    }
                                } catch (Exception e) {

                                }


                            }
                        } catch (Exception e) {

                        }

                    }

                    ArrayList<ReportSosoResult> mArrayList = new ArrayList<ReportSosoResult>();
//                    mCallBackDamageTypeGridListener.callBack(mArrayList);
//                    mCallBackDamageTypeGridListener.complete();
                }


            } else {

                ArrayList<ReportSosoResult> mArrayList = new ArrayList<ReportSosoResult>();

                for (int key : cacheMap.keySet()) {
                    for (DamageCaeck mDamageCaeck : cacheMap.get(key)) {
                        mDamageCaeck.mDictitemlist.setPostion(mDamageCaeck.postion);
                        mArrayList.add(mDamageCaeck.mDictitemlist);
                    }
                }

//                for (DamageCaeck mDamageCaeck : mDamageCaeckList) {
//                    mArrayList.add(mDamageCaeck.mDictitemlist);
//                }
                if(mCallBackDamageTypeGridListener != null){
                    mCallBackDamageTypeGridListener.callBack(mArrayList);
                    mCallBackDamageTypeGridListener.complete();
                }

            }

        }
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.complete:
//                mCallBackDamageTypeGridListener.complete();
                break;
        }
    }

    public void refresh(String model){
        if(layout_type == null){
            return ;
        }
        int count = 0;
        for (int w = 0; w < layout_type.getChildCount(); w++) {
            try {
                LinearLayout childLayout = ((LinearLayout) layout_type.getChildAt(w));
                for (int i = 0; i < childLayout.getChildCount(); i++) {
                    try {
                        LinearLayout mView = (LinearLayout) (childLayout).getChildAt(i);
                        for (int j = 0; j < mView.getChildCount(); j++) {
                            TextView view = (TextView) mView.getChildAt(j);
                            if(TextUtils.equals(model,view.getText())){
                                if(Constant.color == 3){
                                    view.setBackgroundResource(R.drawable.fragment_gridview_adapter_down_red);
                                }else{
                                    view.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);
                                }

                            }else{
                                if(Constant.color == 3){
                                    view.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_red);
                                }else{
                                    view.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                                }

                            }
                            if(view.getTag() != null && !TextUtils.isEmpty(view.getTag().toString()) && view.getTag().toString().equals("编辑")){
                                view.setBackgroundResource(R.drawable.btn_todolist_screen_add);
                            }
                            count ++;
                        }
                    } catch (Exception e) {

                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void refresh(ArrayList<ReportSosoResult> sosoResults) {
        this.sosoResults = sosoResults;
        initData();
    }

}
