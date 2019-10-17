package com.htmitech.ztcustom.zt.chinarailway;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.ztcustom.zt.adapter.CardListAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.domain.GetDefectInfoListRequest;
import com.htmitech.ztcustom.zt.domain.GetDefectInfoListSuccess;
import com.htmitech.ztcustom.zt.domain.GetDefectStatByOrgList;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.util.ZTActivityUnit;

import java.util.HashMap;
import java.util.Map;

import htmitech.com.componentlibrary.unit.Utils;

/**
 *卡片列表
 */
public class CardListActivity extends BaseFragmentActivity implements View.OnClickListener {
    private ImageButton ibn_fn5_back;

    private PullToRefreshListView lv_card_list;

    private ImageView function;
    public GetDefectInfoListRequest mGetDefectInfoListRequest;
    private CardListAdapter mCardListAdapter;
    private String userId = "123";
    private TextView tv_title_name;
    private GetDefectStatByOrgList mGetDefectStatByOrgList;
    private String begTime,endTime;
    private String upOrgCode;
    private String upOrgId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zt_activity_card_list);
        initView();
        initData();
    }

    public void initView(){
        ibn_fn5_back = (ImageButton) findViewById(R.id.ibn_fn5_back);
        lv_card_list = (PullToRefreshListView) findViewById(R.id.lv_card_list);
        function = (ImageView) findViewById(R.id.function);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
    }

    public void initData(){

        mGetDefectStatByOrgList = (GetDefectStatByOrgList) getIntent().getSerializableExtra("GetDefectStatByOrgList");

        begTime = getIntent().getStringExtra("begTime");
        endTime = getIntent().getStringExtra("endTime");
        upOrgCode = getIntent().getStringExtra("org_type");
        upOrgId = getIntent().getStringExtra("org_id");
        tv_title_name.setText(mGetDefectStatByOrgList.name + "(" + mGetDefectStatByOrgList.value + ")");

        lv_card_list.setMode(PullToRefreshBase.Mode.BOTH);
        ibn_fn5_back.setOnClickListener(this);

        lv_card_list.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载");
        lv_card_list.getLoadingLayoutProxy(true, false).setPullLabel("下拉加载更多");
        lv_card_list.getLoadingLayoutProxy(true, false).setReleaseLabel("释放开始加载");

        lv_card_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        lv_card_list.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载更多");
        lv_card_list.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        mCardListAdapter = new CardListAdapter(this,null);
        lv_card_list.setAdapter(mCardListAdapter);
        lv_card_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                pullDownRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub 上拉加载更多
                pullUpLoadMore();

            }
        });

        mGetDefectInfoListRequest = new GetDefectInfoListRequest();
        mGetDefectInfoListRequest.userid = userId;
        mGetDefectInfoListRequest.org_id = mGetDefectStatByOrgList.org_type.equalsIgnoreCase("xl") ? upOrgId : mGetDefectStatByOrgList.id;

        mGetDefectInfoListRequest.query_type = mGetDefectStatByOrgList.org_type.equalsIgnoreCase("xl") ? "LINE" : "ORG";
        mGetDefectInfoListRequest.line_code = mGetDefectStatByOrgList.org_type.equalsIgnoreCase("xl") ? mGetDefectStatByOrgList.code : "";

        mGetDefectInfoListRequest.org_type = mGetDefectStatByOrgList.org_type.equalsIgnoreCase("xl") ? upOrgCode : mGetDefectStatByOrgList.org_type;


        mGetDefectInfoListRequest.defect_type = "1";
        mGetDefectInfoListRequest.sscd_list.addAll(ZTCustomInit.get().getmCache().getSscdList());
        mGetDefectInfoListRequest.sblx_list.addAll(ZTCustomInit.get().getmCache().getSblxList());
        mGetDefectInfoListRequest.czzt_list.addAll(ZTCustomInit.get().getmCache().getCzztList());

        mGetDefectInfoListRequest.rowcount = "20";
        mGetDefectInfoListRequest.last_defect_id = "0";
        mGetDefectInfoListRequest.begin_date = begTime;
        mGetDefectInfoListRequest.end_date = endTime;

        showProgressDialog(this);
        AnsynHttpRequest.request(this, mGetDefectInfoListRequest, CHTTP.GETDEFECTINFOLIST,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        GetDefectInfoListSuccess mGetDefectStatSuccess = FastJsonUtils
                                .getPerson(successMessage, GetDefectInfoListSuccess.class);
                        mCardListAdapter.setData(mGetDefectStatSuccess.defect_list,0);
//                        mGetDefectStatOrgAdapter.setData(mGetDefectStatSuccess.org_value_list);
                        dimssDialog();
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        dimssDialog();
                    }
                });

        lv_card_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent mIntent = new Intent(CardListActivity.this,InjuriesDetailsActivity.class);
//                mIntent.putExtra("userid",userId);
//                mIntent.putExtra("defect_id",mCardListAdapter.getDefectList().get(position).defect_id);
//                CardListActivity.this.startActivity(mIntent);
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("userid", userId);
                params.put("defect_id", mCardListAdapter.getDefectList().get(position - 1).defect_id+"");
                ZTActivityUnit.switchTo(CardListActivity.this, InjuriesDetailsActivity.class,
                        params);
            }
        });

    }

    /**
     * 上啦
     */
    private void pullDownRefresh() {
        mGetDefectInfoListRequest.last_defect_id = "0";
        AnsynHttpRequest.request(this, mGetDefectInfoListRequest, CHTTP.GETDEFECTINFOLIST,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        lv_card_list.onRefreshComplete();
                        GetDefectInfoListSuccess mGetDefectStatSuccess = FastJsonUtils
                                .getPerson(successMessage, GetDefectInfoListSuccess.class);
                        mCardListAdapter.setData(mGetDefectStatSuccess.defect_list,0);
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        Utils.toast(CardListActivity.this, "暂无数据", Toast.LENGTH_SHORT);
                        lv_card_list.onRefreshComplete();
                    }
                });
    }

    /**
     * 下拉
     */
    public void pullUpLoadMore(){
        if(mCardListAdapter.getDefectList().size() == 0){
            lv_card_list.onRefreshComplete();
            Utils.toast(CardListActivity.this, "已经是最后一页了！", Toast.LENGTH_SHORT);
            return;
        }
        mGetDefectInfoListRequest.last_defect_id = mCardListAdapter.getDefectList().get(mCardListAdapter.getDefectList().size() - 1).defect_id+ "";
        AnsynHttpRequest.request(this, mGetDefectInfoListRequest, CHTTP.GETDEFECTINFOLIST,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        lv_card_list.onRefreshComplete();
                        GetDefectInfoListSuccess mGetDefectStatSuccess = FastJsonUtils
                                .getPerson(successMessage, GetDefectInfoListSuccess.class);
                        if(mGetDefectStatSuccess.defect_list.size() == 0){
                            Utils.toast(CardListActivity.this, "已经是最后一页了！", Toast.LENGTH_SHORT);
                        }else{
                            mCardListAdapter.setData(mGetDefectStatSuccess.defect_list,1);
                        }

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        Utils.toast(CardListActivity.this, "暂无数据", Toast.LENGTH_SHORT);
                        lv_card_list.onRefreshComplete();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.ibn_fn5_back ){
            this.finish();
        }else if(v.getId() ==R.id.function){

        }
    }
}
