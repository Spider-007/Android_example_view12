package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.domain.generalinfomation.InjuryDisposeListData;
import com.htmitech.ztcustom.zt.domain.generalinfomation.InjuryDisposeListRequest;
import com.htmitech.ztcustom.zt.domain.generalinfomation.InjuryDisposeListResult;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.view.ZBWaterDropListView;

import java.util.ArrayList;

/**
 * 综合信息查询
 */
public class InjuryDisposeListActivity extends BaseFragmentActivity implements ZBWaterDropListView.IWaterDropListViewListener {

    private TextView tv_no_value;
    private ZBWaterDropListView listview;
    private mSimpleAdapter adapter;
    private ArrayList<InjuryDisposeListData> list;
    private String undealtype;
    private String sblx;
    private String orgcode;
    private TextView textViewTitle;
    private ImageButton imageButtonBack;
    private String title = "";
    private boolean hasMore = false;
    private int currentMsgid = 1;
    private int currentCountPerPage = 10;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    listview.stopRefresh();
                    break;
                case 2:
                    listview.stopLoadMore();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injury_dispose_list);
        undealtype = getIntent().getStringExtra("undealtype");
        sblx = getIntent().getStringExtra("sblx");
        orgcode = getIntent().getStringExtra("orgcode");
        title = getIntent().getStringExtra("title");
        list = new ArrayList<InjuryDisposeListData>();
        listview = (ZBWaterDropListView) findViewById(R.id.lv_injury_dispose_list_report);
        tv_no_value = (TextView) findViewById(R.id.tv_injury_dispose_list_no_value);
        tv_no_value.setVisibility(View.GONE);
        textViewTitle = (TextView) findViewById(R.id.tv_injury_dispose_list_title_name);
        textViewTitle.setText(title);
        imageButtonBack = (ImageButton) findViewById(R.id.ib_injury_dispose_list_back);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData(currentMsgid + "", 0);
        adapter = new mSimpleAdapter();
        listview.setAdapter(adapter);
        listview.setWaterDropListViewListener(this);
        listview.setPullLoadEnable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                adapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("defect_id", list.get(position - 1).id);
                intent.putExtra("title", list.get(position - 1).desc);
                intent.setClass(InjuryDisposeListActivity.this, InjuryDisposeListDetailActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onRefresh() {
        list.clear();
        currentMsgid = 1;
        getData(currentMsgid + "", 1);
    }

    @Override
    public void onLoadMore() {
        if (!hasMore) {
            Toast.makeText(InjuryDisposeListActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
            listview.stopLoadMore();
            return;
        }
        getData(currentMsgid + "", 2);
    }

    private InjuryDisposeListRequest updateData(String msgid) {

        InjuryDisposeListRequest request = new InjuryDisposeListRequest();
        //    <--------------------Administrator -> 2019-8-16:16:35:ZTCustomInit.get().getmCache()--------------------->
        request.userid = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        request.sblx = sblx;
        request.undealtype = undealtype;
        request.orgcode = orgcode;
        request.rowcount = currentCountPerPage;
        request.msgid = msgid;
        return request;
    }

    public void getData(final String endTime, final int type) {

        AnsynHttpRequest.request(this, updateData(endTime),
                ContantValues.INJURYDISPOSELIST, CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        InjuryDisposeListResult result = FastJsonUtils.getPerson(successMessage, InjuryDisposeListResult.class);
                        ArrayList<InjuryDisposeListData> crrentList = new ArrayList<InjuryDisposeListData>();
                        crrentList.addAll(result.datas);
                        if ((crrentList == null || crrentList.size() == 0) && (list == null || list.size() == 0)) {
                            if (endTime.equals("")) {
                                tv_no_value.setVisibility(View.VISIBLE);
                                listview.setVisibility(View.GONE);
                            }
                            return;
                        }
                        if (crrentList.size() < currentCountPerPage) {
                            hasMore = false;
                        } else if (crrentList.size() == currentCountPerPage) {
                            hasMore = true;
                            currentMsgid++;
                        }
                        list.addAll(crrentList);
                        adapter.notifyDataSetChanged();
                        handler.sendEmptyMessage(type);
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                    }
                });
    }


    class mSimpleAdapter extends BaseAdapter {

        private int[] colors = new int[]{0x30FF0000, 0x300000FF};
        LayoutInflater inflater = null;

        public mSimpleAdapter() {
            inflater = LayoutInflater.from(InjuryDisposeListActivity.this);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodler mViewHodler = null;
            InjuryDisposeListData mZBBroadResult = list.get(position);
            if (convertView == null) {
                mViewHodler = new ViewHodler();
                convertView = inflater.inflate(R.layout.zb_waterdroplistview_item, null);
                mViewHodler.ivRed = (ImageView) convertView.findViewById(R.id.iv_inner_red);
                mViewHodler.iv_title_pic = (ImageView) convertView.findViewById(R.id.iv_title_pic);
                mViewHodler.tv_title_name = (TextView) convertView.findViewById(R.id.tv_title_name);
                mViewHodler.tv_sign = (TextView) convertView.findViewById(R.id.tv_sign);
                mViewHodler.tv_report_time = (TextView) convertView.findViewById(R.id.tv_report_time);
                convertView.setTag(mViewHodler);
            } else {
                mViewHodler = (ViewHodler) convertView.getTag();
            }
//            if (mZBBroadResult.isnew.equals("0") || mZBBroadResult.getStatus().equals("true")) {
//                mViewHodler.ivRed.setVisibility(View.GONE);
//            } else {
//                mViewHodler.ivRed.setVisibility(View.VISIBLE);
//            }
            mViewHodler.ivRed.setVisibility(View.GONE);
            if (mZBBroadResult.getSblx().contains("焊缝")) {
                mViewHodler.iv_title_pic.setImageDrawable(getResources().getDrawable(R.drawable.welding_line));
            } else if (mZBBroadResult.getSblx().contains("道岔")) {
                mViewHodler.iv_title_pic.setImageDrawable(getResources().getDrawable(R.drawable.turnout));
            } else if (mZBBroadResult.getSblx().contains("钢轨")) {
                mViewHodler.iv_title_pic.setImageDrawable(getResources().getDrawable(R.drawable.rail));
            }
            mViewHodler.tv_title_name.setText("" + mZBBroadResult.getDesc());
            mViewHodler.tv_sign.setText("" + mZBBroadResult.getWzlb());
            mViewHodler.tv_report_time.setText("" + mZBBroadResult.getReporttime());
            int colorPos = position % colors.length;
            if (colorPos == 1)
                convertView.setBackgroundColor(Color.rgb(0, 20, 67));
            else
                convertView.setBackgroundColor(Color.rgb(0, 9, 49));
            return convertView;
        }

        public class ViewHodler {
            ImageView ivRed;
            TextView tv_title_name;
            TextView tv_sign;
            TextView tv_report_time;
            ImageView iv_title_pic;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }
    }

}
