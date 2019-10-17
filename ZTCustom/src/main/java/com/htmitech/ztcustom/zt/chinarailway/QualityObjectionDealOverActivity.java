package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.QualityObjectionDealOverAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.GetQualityObjectionListSearchRequest;
import com.htmitech.ztcustom.zt.constant.QualityObjectionListSearchResultList;
import com.htmitech.ztcustom.zt.constant.QualityObjectionListSearchRuslt;
import com.htmitech.ztcustom.zt.dialog.QualityObjectionDealAlertDialog;
import com.htmitech.ztcustom.zt.dialog.QualityObjectionOverAlertDialog;
import com.htmitech.ztcustom.zt.domain.QualityObjectionDealOverActionRequest;
import com.htmitech.ztcustom.zt.domain.QualityObjectionDealOverActionResult;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.interfaces.QualityObjectionOverDialogCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.unit.Utils;

/**
 * 质量异议
 */
public class QualityObjectionDealOverActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ImageButton ibBack;
    private TextView tvUnit;
    private ListView listView;
    private Button btSelectAll;
    private Button btDealOrOver;
    private QualityObjectionDealOverAdapter adapter;
    private GetQualityObjectionListSearchRequest getQualityObjectionListSearchRequest;
    private String type = "";
    private String currentYqbm = "";
    private String currentYqbmName = "";
    private String currentState = "";
    private List<QualityObjectionListSearchResultList> list;
    private boolean isSelectedAll = true;
    private QualityObjectionDealOverActionRequest dealOverActionRequest;
    private String dealString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_objection_deal_over);
        initView();
        initData();
        initControl();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.ib_quality_objection_deal_over_back ){
            this.finish();
        }else if(v.getId() ==R.id.bt_quality_objection_deal_over_select_all){
            if (isSelectedAll) {
                isSelectedAll = false;
                btSelectAll.setText("全取消");
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).isCheckd = true;
                }
                if (type.equals("WJ")) {
                    btDealOrOver.setText("完结（" + list.size() + "）");
                } else if (type.equals("CL")) {
                    btDealOrOver.setText("处理（" + list.size() + "）");
                }
            } else {
                isSelectedAll = true;
                btSelectAll.setText("全选");
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).isCheckd = false;
                }
                if (type.equals("WJ")) {
                    btDealOrOver.setText("完结（0）");
                } else if (type.equals("CL")) {
                    btDealOrOver.setText("处理（0）");
                }
            }
            adapter.notifyDataSetChanged();
        }else if(v.getId() ==R.id.bt_quality_objection_deal_over_deal_or_over){
            int num = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isCheckd) {
                    num++;
                }
            }
            if (num == 0) {
                Toast.makeText(this, "还未选择要处理或完结的质量异议", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = "";
            if ("WJ".equals(type)) {//完结确定按钮
                title = "请确认所选质量异议问题是否已完结";
            } else if ("CL".equals(type)) {//处理确定按钮
                title = "请确认所选质量异议问题是否已处理";
            }
            if (currentYqbm.equals("cljg") || currentYqbm.equals("fxyy")) {
                String titleDeal;
                titleDeal = "cljg".equals(currentYqbm) ? "输入处理情况" : "请输入分析原因";
                new QualityObjectionOverAlertDialog(this).builder().setTitle(titleDeal).
                        setNegativeButton("", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//取消

                            }
                        }).
                        setPositiveButton("", new QualityObjectionOverDialogCallBack() {
                            @Override
                            public void getEditText(String editText) {//确定
                                if (editText != null && !editText.equals("")) {
                                    dealString = editText;
                                }
                                dealOverAction();
                            }
                        }).
                        setCancelable(true).show();
            } else {
                new QualityObjectionDealAlertDialog(this).builder().setTitle(title).
                        setNegativeButton("否", new View.OnClickListener() {//取消
                            @Override
                            public void onClick(View v) {

                            }
                        }).
                        setPositiveButton("是", new View.OnClickListener() {//确定
                            @Override
                            public void onClick(View v) {
                                dealOverAction();
                            }
                        }).
                        setCancelable(true).show();
            }
        }


    }

    private void initView() {
        ibBack = (ImageButton) findViewById(R.id.ib_quality_objection_deal_over_back);
        ibBack.setOnClickListener(this);
        tvUnit = (TextView) findViewById(R.id.tv_quality_objection_deal_over_unit);
        listView = (ListView) findViewById(R.id.lv_quality_objection_deal_over);
        btSelectAll = (Button) findViewById(R.id.bt_quality_objection_deal_over_select_all);
        btSelectAll.setOnClickListener(this);
        btDealOrOver = (Button) findViewById(R.id.bt_quality_objection_deal_over_deal_or_over);
        btDealOrOver.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        currentYqbm = intent.getStringExtra("yqbm");
        currentYqbmName = intent.getStringExtra("currentYqbmName");
        currentState = intent.getStringExtra("currentState");
        tvUnit.setText(currentYqbmName);
        if (type.equals("WJ")) {
            btDealOrOver.setText("完结（0）");
        } else if (type.equals("CL")) {
            btDealOrOver.setText("处理（0）");
        }
        list = new ArrayList<QualityObjectionListSearchResultList>();
        getQualityObjectionListSearchRequest = new GetQualityObjectionListSearchRequest();
        //    <--------------------Administrator -> 2019-8-16:16:38:ZTCustomInit.get().getmCache().getmListDetails().AccountId--------------------->
        getQualityObjectionListSearchRequest.userid =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        getQualityObjectionListSearchRequest.yqbm = currentYqbm;
        getQualityObjectionListSearchRequest.pagesize = 100000 + "";
        getQualityObjectionListSearchRequest.page = 1 + "";
        getData();
        adapter = new QualityObjectionDealOverAdapter(this, list);
        listView.setAdapter(adapter);
    }

    private void initControl() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //这里面要做局部更新 listview
                list.get(position).isCheckd = !list.get(position).isCheckd;
                updateItem(position);
                int num = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isCheckd) {
                        num++;
                    }
                }
                if (type.equals("WJ")) {
                    btDealOrOver.setText("完结（" + num + "）");
                } else if (type.equals("CL")) {
                    btDealOrOver.setText("处理（" + num + "）");
                }
            }
        });
    }

    /**
     * 调用一次getView()方法
     *
     * @param position 要更新的位置
     */
    private void updateItem(int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = listView.getChildAt(position - firstVisiblePosition);
            adapter.getView(position, view, listView);
        }

    }


    private void getData() {
        showProgressDialog(this);
        AnsynHttpRequest.request(this, getQualityObjectionListSearchRequest, ContantValues.GETZLYYLBCX, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
                if (successMessage != null && !successMessage.equals("")) {
                    QualityObjectionListSearchRuslt result = new QualityObjectionListSearchRuslt();
                    result = FastJsonUtils.getPerson(successMessage, QualityObjectionListSearchRuslt.class);
                    if (result.success) {
                        if (list != null) {
                            list.clear();
                        }
//                        if (type.equals("WJ")) {
//                            for (int i = 0; i < result.results.size(); i++) {
//                                if (result.results.get(i).zhuangtai.equals("待完结")) {
//                                    list.add(result.results.get(i));
//                                }
//                            }
//                        } else if (type.equals("CL")) {
//                            for (int i = 0; i < result.results.size(); i++) {
//                                if (result.results.get(i).zhuangtai.equals("未受理")) {
//                                    list.add(result.results.get(i));
//                                }
//                            }
//                        }
                        list.addAll(result.results);
                        adapter.notifyDataSetChanged();
                    } else {
                        Utils.toast(QualityObjectionDealOverActivity.this, "服务器异常" + result.msg, Toast.LENGTH_SHORT);
                    }
                } else {
                    Utils.toast(QualityObjectionDealOverActivity.this, "服务器异常,返回为空", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
                Utils.toast(QualityObjectionDealOverActivity.this, "暂无数据", Toast.LENGTH_SHORT);
            }
        });
    }

    public void dealOverAction() {
        showProgressDialog(this);
        dealOverActionRequest = new QualityObjectionDealOverActionRequest();
        //    <--------------------Administrator -> 2019-8-16:16:38:ZTCustomInit.get().getmCache().getmListDetails().AccountId--------------------->
        dealOverActionRequest.userid =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        dealOverActionRequest.state = currentState;
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isCheckd) {
                if (str.equals("")) {
                    str = str + list.get(i).id;
                } else {
                    str = str + "','" + list.get(i).id;
                }
            }
        }
        dealOverActionRequest.id = str;
        if (currentState.equals("8") && dealString != null && !dealString.equals("")) {
            dealOverActionRequest.chulijieguo = dealString;
        }
        if (currentState.equals("5") && dealString != null && !dealString.equals("")) {
            dealOverActionRequest.fenxiyuanyin = dealString;
        }
        AnsynHttpRequest.request(this, dealOverActionRequest, ContantValues.DEALOVERACTION, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
                if (successMessage != null && !successMessage.equals("")) {
                    QualityObjectionDealOverActionResult result = new QualityObjectionDealOverActionResult();
                    result = FastJsonUtils.getPerson(successMessage, QualityObjectionDealOverActionResult.class);
                    if (result.success) {
                        Utils.toast(QualityObjectionDealOverActivity.this, type.equals("WJ") ? "完结质量异议成功" : "处理质量异议成功", Toast.LENGTH_SHORT);
                        setResult(2, new Intent());//为了回掉能够刷新页签数量
                        QualityObjectionDealOverActivity.this.finish();
                    } else {
                        Utils.toast(QualityObjectionDealOverActivity.this, "服务器异常" + result.msg, Toast.LENGTH_SHORT);
                    }
                } else {
                    Utils.toast(QualityObjectionDealOverActivity.this, "服务器异常,返回为空", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
                Utils.toast(QualityObjectionDealOverActivity.this, "暂无数据", Toast.LENGTH_SHORT);
            }
        });
    }
}
