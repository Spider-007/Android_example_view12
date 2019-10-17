package com.htmitech.emportal.ui.daiban;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.daiban.MineMode.FaQiRequestBean;
import com.htmitech.emportal.ui.daiban.MineMode.GetFaQiListModel;
import com.htmitech.emportal.ui.daiban.MineMode.MineFaQiResultBean;
import com.htmitech.emportal.ui.daiban.MineMode.Result;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.minxing.client.util.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MineFaQiFragment extends MyBaseFragment implements SearchView.OnQueryTextListener, IBaseCallback {
    public RelativeLayout rl_search;      //搜索的相对布局
    private SearchView sv_search;         //搜索控件
    private com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView faqi_listview;       //列表
    private ImageView iv_search;          //搜索图标（放大镜）
    private TextView tv_search;           //图标旁边的字（搜索）
    private MineFaQiResultBean resultBean;
    private ArrayList<Result> results;

    public boolean isHasMore = true;

    //定义刷新，加载，以及当前的状态
    private int PULLTOREFRESH_STATE = 1;               //刷新
    private int PULLDOWNMORE_STATE = 2;                //加载更多
    private int START_STATE = 0;                       //开始初始化数据状态
    //    //判断是搜索还是普通状态
//    private int SEARCHSTATE = 11;                 //搜索状态
//    private int NORMALSTATE = 10;                //普通状态
//    private int CURRENTSTATE = NORMALSTATE;     //当前所处状态，默认为普通
    //定义刷新的起始值
    private int more_start = 0;               //接口文档规定
    private int more_end = 29;               //默认加载30条
    private int CurrentIndex;              //当前最后一条记录的行数

    private int Count = 30;    //分页增量
    private MyAdapter adapter;
    public int SearchState = 0;
    public String SearchKeyWords = "";       //搜索关键字
    public LinearLayout layout_search_no;
    private MineFaQiResultBean entity;
    private String app_id ;
    @Override
    protected int getLayoutId() {
        return R.layout.mine_faqi_fragment;
    }

    @Override
    protected void initViews() {
        app_id = getActivity().getIntent().getStringExtra("app_id");
        String modelName = getArguments().getString("modelName");
        int todoFlag = getArguments().getInt("todoFlag");
        rl_search = (RelativeLayout) findViewById(R.id.faqi_rv_serach);
        sv_search = (SearchView) findViewById(R.id.faqi_sv_search);
        faqi_listview = (com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView) findViewById(R.id.faqi_listview);
        iv_search = (ImageView) findViewById(R.id.faqi_iv_serach);
        tv_search = (TextView) findViewById(R.id.faqi_tv_serach);
        layout_search_no = (LinearLayout) findViewById(R.id.faqi_layout_search_no);
        if(todoFlag != 0 &&todoFlag != 1) {
            SearchKeyWords = modelName;
        }
        initNet(0, 29, START_STATE, SearchKeyWords);
        adapter = new MyAdapter();

        int search_mag_icon_id = sv_search.getContext().getResources()
                .getIdentifier("android:id/search_mag_icon", null, null);
        ImageView search_mag_icon = (ImageView) sv_search
                .findViewById(search_mag_icon_id);// 获取搜索图标
        search_mag_icon.setImageResource(R.drawable.mx_search_bar_icon_normal);// 图标都是用src的

        // 设置提示文字的颜色,这里走了点奇招,用Html类方法
        sv_search.setQueryHint(Html
                .fromHtml("<font color = #999999>" + "请输入标题关键字"
                        + "</font>"));

        int id = sv_search.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv_search.findViewById(id);
        textView.setTextSize(14);

        rl_search.setVisibility(View.VISIBLE);
        if(!SearchKeyWords.equals("")){
            iv_search.setVisibility(View.GONE);
            tv_search.setVisibility(View.GONE);
            sv_search.setVisibility(View.VISIBLE);
            sv_search.setQuery(SearchKeyWords, false);
        }
        rl_search.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //               i++;                                     //不知何用，从DaiBanListFragment中拷贝过来的
                SearchState++;
                iv_search.setVisibility(View.GONE);
                tv_search.setVisibility(View.GONE);
                sv_search.setVisibility(View.VISIBLE);
                sv_search.onActionViewExpanded();
                InputMethodManager inputManager =
                        (InputMethodManager) sv_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(sv_search, 0);
            }
        });
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {
                return true;
            }
        });

        if (sv_search != null) {
            try {        //--拿到字节码
                Class<?> argClass = sv_search.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(sv_search);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 为该SearchView组件设置事件监听器
        sv_search.setOnQueryTextListener(this);
        sv_search.setIconifiedByDefault(false);

        faqi_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != results) {
                    Result itemInfo = results.get(position - 1);
                    if (null != itemInfo) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), DetailActivity.class);
                        intent.putExtra("DocId", itemInfo.DocID);
                        intent.putExtra("DocType", itemInfo.DocType);
                        intent.putExtra("DocTitle", itemInfo.DocTitle);
                        intent.putExtra("Kind", ""); //2015-08-11
                        intent.putExtra("TodoFlag", itemInfo.TodoFlag);
                        intent.putExtra("IconId", "");
                        intent.putExtra("sendFrom", itemInfo.SendFrom);
                        intent.putExtra("sendDate", itemInfo.SendDate);
                        intent.putExtra("app_id", app_id);
                        intent.putExtra("actionButtonStyle",((DaiBanFragmentActivity)getActivity()).actionButtonStyle);
                        intent.putExtra("com_workflow_mobileconfig_IM_enabled",((DaiBanFragmentActivity)getActivity()).com_workflow_mobileconfig_IM_enabled);
                        intent.putExtra("isShare",((DaiBanFragmentActivity)getActivity()).isShare);
                        intent.putExtra("isTextUrl",((DaiBanFragmentActivity)getActivity()).isTextUrl);
                        intent.putExtra("app_version_id",((DaiBanFragmentActivity)getActivity()).app_version_id);
                        intent.putExtra("isWaterSecurity",((DaiBanFragmentActivity)getActivity()).isWaterSecurity);
                        if (itemInfo.iconId == null || "".equals(itemInfo.iconId)
                                || !(
                                itemInfo.iconId.endsWith(".png") || itemInfo.iconId.endsWith(".jpg")
                        )) {
                            intent.putExtra("IconId", "");
                        } else {
                            intent.putExtra("IconId", itemInfo.iconId);
                        }
                        startActivityForResult(intent, 0);
                    }
                }

            }
        });

        faqi_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                if (results != null)
                    results.clear();
                SearchKeyWords = "";
                initNet(0, 29, PULLTOREFRESH_STATE, SearchKeyWords);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        //上拉加载            results未作非空判读
                if (!isHasMore) {
//                    ClientTabActivity.todoTabItem.hideMarker();
                    Utils.toast(getActivity(), "已经是最后一页了！", Toast.LENGTH_SHORT);
                    faqi_listview.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stubbn
                            faqi_listview.onRefreshComplete();
                        }
                    }, 100);
                    return;
                }

                getMoredata();

            }
        });
    }

    public void initNet(int start, int end, final int state, String word) {

        FaQiRequestBean docSearchParameters = new FaQiRequestBean();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        if (!SearchKeyWords.equals("")) {
            docSearchParameters.Title = SearchKeyWords;
        } else {
            docSearchParameters.Title = "";
        }
        docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
        docSearchParameters.recordStartIndex = 0 + "";
        docSearchParameters.recordEndIndex = 29 + "";
        docSearchParameters.app_id = app_id;
        GetFaQiListModel getdocListModel = new GetFaQiListModel(this);
        getdocListModel.getDataFromServerByType(
                GetFaQiListModel.TYPE_GET_ZERO_LIST, docSearchParameters);

    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        faqi_listview.onRefreshComplete();

        if (requestTypeId == GetFaQiListModel.TYPE_GET_ZERO_LIST) {
            results = new ArrayList<Result>();
            //第一次请求
            if (result != null) {
                entity = (MineFaQiResultBean) result;
                if(entity.Result != null ){
                    for (int i = 0; i < entity.Result.size(); i++) {
                        Log.e("SSSS", entity.Result.size() + "");
                        results.add(entity.Result.get(i));
                    }
                    more_start = more_end;
                    more_end += entity.Result.size();
                    faqi_listview.setAdapter(adapter);
                    faqi_listview.onRefreshComplete();
                }

//                more_start=more_end;
//                more_end+=Count;
//                adapter.notifyDataSetChanged();
            }
            if (results.size() == 0) {
                layout_search_no.setVisibility(View.VISIBLE);
            } else {
                layout_search_no.setVisibility(View.GONE);
            }

        } else if (requestTypeId == GetFaQiListModel.TYPE_GET_MORE_LISTDATA) {

            if (result != null) {

                MineFaQiResultBean entity = (MineFaQiResultBean) result;
                for (int i = 0; i < entity.Result.size(); i++) {
                    Log.e("SSSS", entity.Result.size() + "");
                    results.add(entity.Result.get(i));

                }
                if (entity.Result.size() >= Count) {
                    more_start = more_end;
                    more_end += Count;
                } else if (entity.Result.size() < Count && entity.Result.size() > 0) {

                    more_start = more_end;
                    more_end += entity.Result.size();

                } else if (entity.Result.size() == 0) {
                    //数据加载完毕
                    isHasMore = false;
                    faqi_listview.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            faqi_listview.onRefreshComplete();
                        }
                    }, 100);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result) {

    }

    public void parseData(String result, int state, int OpertType) {
        Gson gson = new Gson();
        if (state == 0) {     //我发起的
            resultBean = gson.fromJson(result, MineFaQiResultBean.class);
            initData(OpertType);
        }

    }

    public void getMoredata() {
        FaQiRequestBean docSearchParameters = new FaQiRequestBean();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        if (!SearchKeyWords.equals("")) {
            docSearchParameters.Title = SearchKeyWords;
        } else {
            docSearchParameters.Title = "";
        }

        docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
        docSearchParameters.recordStartIndex = more_start + "";
        docSearchParameters.recordEndIndex = more_end + "";
        docSearchParameters.app_id = app_id;
        GetFaQiListModel getdocListModel = new GetFaQiListModel(this);
        getdocListModel.getDataFromServerByType(
                GetFaQiListModel.TYPE_GET_MORE_LISTDATA, docSearchParameters);

    }

    public void initData(int OpertType) {
        //判断结果数据集不为空，并且当状态为下拉刷新时，清空原数据集里的数据
        if (results == null) {
            results = new ArrayList<Result>();
        } else if (OpertType == PULLTOREFRESH_STATE) {
            results.clear();
        }
        //解析数据，将对应数据填充到数据集中
        if (resultBean != null && resultBean.Result != null) {
            for (int i = 0; i < resultBean.Result.size(); i++) {
                results.add(resultBean.Result.get(i));

            }
            if (OpertType == START_STATE) {
                faqi_listview.setAdapter(adapter);
            }

            //判断对应状态（下拉刷新，上拉加载，初始化），进行对应操作
            if (OpertType == PULLTOREFRESH_STATE || OpertType == PULLDOWNMORE_STATE) {
                //当状态为下拉刷新则取消刷新的动画
                faqi_listview.onRefreshComplete();
            }


            //所有数据加载完毕
            if (CurrentIndex == results.size()) {
                Toast.makeText(getActivity(), "已加载完毕", Toast.LENGTH_SHORT).show();
                faqi_listview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        faqi_listview.onRefreshComplete();
                    }
                }, 100);
            } else {
                CurrentIndex = results.size();
            }
            adapter.notifyDataSetChanged();
        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object getItem(int position) {
            return results.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
            /*convertView =  getLayoutInflater().inflate(
                    R.layout.todolist_item, null);*/
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_faqi_item, null);
                holder.titleTextView = (TextView) convertView.findViewById(R.id.tv_faqi_title);
                holder.infoTextView = (TextView) convertView.findViewById(R.id.tv_faqi_committer);
                holder.timeTextView = (TextView) convertView.findViewById(R.id.tv_faqi_time);
                holder.imgView = (ImageView) convertView.findViewById(R.id.iv_faqi_type);
                holder.faqiType = (TextView) convertView.findViewById(R.id.tv_faqi_type);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.titleTextView.setText(results.get(position).DocTitle);
            holder.infoTextView.setText(results.get(position).SendFrom);
            holder.timeTextView.setText(results.get(position).SendDate);
            holder.faqiType.setText(results.get(position).DocType);
//            if (results.get(position).iconId != null && !("").equals(results.get(position).iconId))
//                BitmapUtils.instance().display(holder.imgView, results.get(position).iconId);
            if(holder.imgView!=null){
                Glide.with(getActivity()).load(results.get(position).iconId)
                        .placeholder(R.drawable.icon_email)
                        .error(R.drawable.icon_email)
                        .transform(new GlideRoundTransform(getActivity()))
                        .into( holder.imgView);
            }

//            else
//            holder.imgView.setBackgroundResource(R.drawable. icon_email);

//            Toast.makeText(getActivity(),"没图片",Toast.LENGTH_SHORT).show();


            return convertView;
        }
    }

    class ViewHolder {
        TextView titleTextView;
        TextView infoTextView;
        TextView timeTextView;
        ImageView imgView;
        TextView faqiType;
        //TextView  sendMan;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        results.clear();
        SearchKeyWords = query;
        more_start=1;
        more_end=30;
        //开始搜索listener
        FaQiRequestBean docSearchParameters = new FaQiRequestBean();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.Title = query;
        docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
        docSearchParameters.recordStartIndex = "0";
        docSearchParameters.recordEndIndex = "30";
        docSearchParameters.app_id = app_id;
        GetFaQiListModel getdocListModel = new GetFaQiListModel(this);
        getdocListModel.getDataFromServerByType(
                GetFaQiListModel.TYPE_GET_ZERO_LIST, docSearchParameters);

        sv_search.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //输入框内容变化listener
//        CURRENTSTATE=SEARCHSTATE;
        SearchKeyWords = newText;
        if (newText.toString().equals("") && SearchState != 1) {
            more_start=0;
            more_end=29;
            FaQiRequestBean docSearchParameters = new FaQiRequestBean();
            docSearchParameters.context = OAConText.getInstance(getActivity());
            docSearchParameters.Title = newText;
            docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
            docSearchParameters.recordStartIndex = "0";
            docSearchParameters.recordEndIndex = "30";
            docSearchParameters.app_id = app_id;
            GetFaQiListModel getdocListModel = new GetFaQiListModel(this);
            getdocListModel.getDataFromServerByType(
                    GetFaQiListModel.TYPE_GET_ZERO_LIST, docSearchParameters);
            sv_search.clearFocus();
        }
        SearchState++;
        return false;
    }
}
