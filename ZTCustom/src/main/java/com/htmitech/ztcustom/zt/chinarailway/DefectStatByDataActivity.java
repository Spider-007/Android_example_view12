package com.htmitech.ztcustom.zt.chinarailway;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.DamageDetailDataAdapter;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.domain.GetDefectStatByOrg;
import com.htmitech.ztcustom.zt.domain.Table;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;

import java.util.ArrayList;

import htmitech.com.componentlibrary.unit.ToastUtil;

/**
 * 重伤监控明细数据
 */
public class DefectStatByDataActivity extends BaseFragmentActivity implements View.OnClickListener {
    public TextView tv_fn5_title_name;
    public RelativeLayout mHead;
    public ListView tv_detail_data;
    public GetDefectStatByOrg mGetDefectStatByOrg;
    public ArrayList<Table> tableList;
    private LinearLayout layout_addview;
    private ArrayList<ArrayList<Table>> twoList;
    private ImageView ibn_fn5_back;
    private TextView unit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zt_defect_data_activity);
        initView();
        initData();
    }

    public void initView(){
        mHead = (RelativeLayout) findViewById(R.id.head);
        tv_fn5_title_name = (TextView) findViewById(R.id.tv_fn5_title_name);
        tv_detail_data = (ListView) findViewById(R.id.tv_detail_data);
        layout_addview = (LinearLayout) findViewById(R.id.layout_addview);
        ibn_fn5_back = (ImageView) findViewById(R.id.ibn_fn5_back);
        unit = (TextView) findViewById(R.id.unit);
    }

    public void initData(){

        mHead.setFocusable(true);
        mHead.setClickable(true);
        ibn_fn5_back.setOnClickListener(this);
        mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        tv_detail_data
                .setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        mHead.setBackgroundColor(getResources().getColor(R.color.shebei));
        mGetDefectStatByOrg = (GetDefectStatByOrg) getIntent().getSerializableExtra("mGetDefectStatByOrg");
        String title = "";
        if(mGetDefectStatByOrg.stat_type.equals("ORG")){
            unit.setText("单位");
            title = "单位明细数据";
        }else if(mGetDefectStatByOrg.stat_type.equals("LINE")){
            unit.setText("线路");
            title = "线路明细数据";
        }
        tv_fn5_title_name.setText(title);
        twoList = new ArrayList<ArrayList<Table>>();
        showProgressDialog(this);
        AnsynHttpRequest.request(this, mGetDefectStatByOrg, CHTTP.GETDEFECTSTATLIST,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        JSONObject mJSONObject = JSON
                                .parseObject(successMessage);
                        JSONArray Table1List = mJSONObject
                                .getJSONArray("defect_stat_list");
                        if (Table1List == null) {
                            ToastUtil.showShort(DefectStatByDataActivity.this,"Result为空，请重新操作！");
//                            ToastInfo.getInstance(
//                                    DefectStatByDataActivity.this).setText(
//                                    "Result为空，请重新操作！");
                            return;
                        }



                        for (int i = 0; i < Table1List.size(); i++) {
                            tableList = new ArrayList<Table>();
                            JSONObject object = Table1List.getJSONObject(i);
                            if (i == 0) {
                                layout_addview.addView(addView1("合计"),
                                        0);
                            }
                            tableList.add(0, new Table("totalvalue", ((int) Float.parseFloat(object.getString("org_total_value")) + "")));
                            JSONArray time_value_list = object
                                    .getJSONArray("time_value_list");

                            for(int j = 0; j < time_value_list.size() ;j++){
                                if (i == 0){
                                    layout_addview.addView(addView1(time_value_list.getJSONObject(j).getString("name")));
                                }
                                tableList.add(new Table(time_value_list.getJSONObject(j).getString("name"), (int) Float
                                        .parseFloat(time_value_list.getJSONObject(j).getString("value")) + ""));
                            }
                            tableList.add(new Table("shortname", object.getString("org_name")));
//
                            twoList.add(tableList);
                        }
                        DamageDetailDataAdapter mDamageDetailDataAdapter = new DamageDetailDataAdapter(
                                DefectStatByDataActivity.this, twoList, mHead);
                        tv_detail_data.setAdapter(mDamageDetailDataAdapter);
                        dimssDialog();
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        ToastUtil.showShort(DefectStatByDataActivity.this,"暂无数据");
                        dimssDialog();
                    }
                });
    }

    private View addView1(String titleName) {
        // TODO 动态添加布局(xml方式)
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // LayoutInflater
        // inflater1=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // LayoutInflater inflater2 = getLayoutInflater();
        LayoutInflater inflater3 = LayoutInflater.from(this);
        View view = inflater3.inflate(
                R.layout.zt_fragment_detail_data_item_text, null);
        TextView tv_detail = (TextView) view.findViewById(R.id.tv_detail);
        tv_detail.setText(titleName);
        view.setLayoutParams(lp);
        return view;

    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.ibn_fn5_back ){
            this.finish();
        }
    }

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            // 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
            HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
                    .findViewById(R.id.horizontalScrollView1);
            headSrcrollView.onTouchEvent(arg1);
            return false;
        }
    }
}
