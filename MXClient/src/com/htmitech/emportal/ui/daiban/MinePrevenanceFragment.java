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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.daiban.MineMode.GetGuanZhuModel;
import com.htmitech.emportal.ui.daiban.MineMode.GuanZhuRequestBean;
import com.htmitech.emportal.ui.daiban.MineMode.PrevenanceResult;
import com.htmitech.emportal.ui.daiban.MineMode.PrevenanceResultBean;
import com.htmitech.emportal.ui.daiban.minewidget.ui.ListViewCompat;
import com.htmitech.emportal.ui.daiban.minewidget.ui.SlideView;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class MinePrevenanceFragment extends MyBaseFragment implements IBaseCallback, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener, View.OnClickListener,
        SlideView.OnSlideListener, ListViewCompat.OnRefreshListener, ListViewCompat.OnLoadListener {
    private SlideView mLastSlideViewWithStatusOn;
    private int allCount = 40;
    private RelativeLayout rl_search;  //搜索的相对布局
    private SearchView sv_search;      //搜索控件
    private com.htmitech.emportal.ui.daiban.minewidget.ui.ListViewCompat mListview;        //列表
    private ImageView iv_search;       //搜索图标（放大镜）
    private TextView tv_search;        //图标旁边的字（搜索）
    private PrevenanceResultBean resultBean;
    private List<PrevenanceResult> mMessageItems = new ArrayList<PrevenanceResult>();
    private SlideAdapter adapter;
    private ArrayList<PrevenanceResult> results;
    private String app_id ;

    //定义刷新，加载，以及当前的状态
    private int PULLTOREFRESH_STATE = 1;               //刷新
    private int PULLDOWNMORE_STATE = 2;                //加载更多
    private int START_STATE = 0;  //开始初始化数据状态

    //定义刷新的起始值
    private int more_start = 1;               //接口文档规定
    private int more_end = 30;               //默认加载30条
    private int CurrentIndex;              //当前最后一条记录的行数
    public LinearLayout layout_search_no;
    public int Count = 30;
    public boolean isHasMore = true;
    public String SearchKeyWords = "";
    public int SearchState = 0;
    private PrevenanceResult itemInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_prevenance_fragment;
    }

    @Override
    protected void initViews() {
        app_id = getActivity().getIntent().getStringExtra("app_id");
      int todoFlag = getArguments().getInt("todoFlag");
       String modelName = getArguments().getString("modelName");
        if(todoFlag != 0 && todoFlag != 1) {
            SearchKeyWords = modelName;
        }
        initNet(0, 30, START_STATE, SearchKeyWords);
        rl_search = (RelativeLayout) findViewById(R.id.guanzhu_rv_serach);
        sv_search = (SearchView) findViewById(R.id.guanzhu_sv_search);
        mListview = (com.htmitech.emportal.ui.daiban.minewidget.ui.ListViewCompat) findViewById(R.id.guanzhu_listview);
        iv_search = (ImageView) findViewById(R.id.guanzhu_iv_serach);
        tv_search = (TextView) findViewById(R.id.guanzhu_tv_serach);
        layout_search_no = (LinearLayout) findViewById(R.id.guanzhu_layout_search_no);
        adapter = new SlideAdapter();
        mListview.setOnItemClickListener(this);
        mListview.setOnRefreshListener(this);
        mListview.setOnLoadListener(this);
//        mListview.setResultSize(-1);


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
    }

    public void initNet(int start, int end, final int state, String word) {
        GuanZhuRequestBean docSearchParameters = new GuanZhuRequestBean();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.ModelName = "";
        if (!SearchKeyWords.equals("")) {
            docSearchParameters.Title = SearchKeyWords;
        } else {
            docSearchParameters.Title = "";
        }
        docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
        docSearchParameters.recordStartIndex = 1 + "";
        docSearchParameters.recordEndIndex = 30 + "";
        docSearchParameters.app_id = app_id;
        GetGuanZhuModel getdocListModel = new GetGuanZhuModel(this);
        getdocListModel.getDataFromServerByType(
                GetGuanZhuModel.TYPE_GET_ZERO_LIST, docSearchParameters);

    }

    public void getMoredata() {
        GuanZhuRequestBean docSearchParameters = new GuanZhuRequestBean();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.ModelName = "";
        if (!SearchKeyWords.equals("")) {
            docSearchParameters.Title = SearchKeyWords;
        } else {
            docSearchParameters.Title = "";
        }
        docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
        docSearchParameters.recordStartIndex = more_start + "";
        docSearchParameters.recordEndIndex = more_end + "";
        docSearchParameters.app_id = app_id;
        GetGuanZhuModel getdocListModel = new GetGuanZhuModel(this);
        getdocListModel.getDataFromServerByType(
                GetGuanZhuModel.TYPE_GET_MORE_LISTDATA, docSearchParameters);

    }

    public int isFinished = 0;

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        results = new ArrayList<PrevenanceResult>();
        if (requestTypeId == GetGuanZhuModel.TYPE_GET_ZERO_LIST) {
            //第一次请求
            if (result != null) {
                PrevenanceResultBean entity = (PrevenanceResultBean) result;
                for (int i = 0; i < entity.Result.size(); i++) {
                    Log.e("SSSS", entity.Result.size() + "");
                    results.add(entity.Result.get(i));
                }
                mListview.setAdapter(adapter);
                mListview.setResultSize(20);
                mListview.onRefreshComplete();
//                adapter.notifyDataSetChanged();
                isFinished = 0;
                if (results.size() == 0) {
                    layout_search_no.setVisibility(View.VISIBLE);
                } else {
                    layout_search_no.setVisibility(View.GONE);
                }
            }

        } else if (requestTypeId == GetGuanZhuModel.TYPE_GET_MORE_LISTDATA) {

            if (result != null) {
                PrevenanceResultBean entity = (PrevenanceResultBean) result;
                for (int i = 0; i < entity.Result.size(); i++) {

                    results.add(entity.Result.get(i));
                    mListview.onRefreshComplete();
//                    more_end+=30;
//                    more_start=more_end+1;

                }

                    more_end += entity.Result.size();
                    more_start = more_end + 1;


//                    more_start=more_end+1;
                if (entity.Result.size() >= Count) {
                    more_start = more_end;
                    more_end += Count;
                } else if (entity.Result.size() < Count) {
                    //数据加载完毕
                    isHasMore = false;
//                        Utils.toast(getActivity(), "已经是最后一页了！", Toast.LENGTH_SHORT);
//                        Toast.makeText(getActivity(), "已经是最后一页了", Toast.LENGTH_SHORT).show();
                    mListview.setResultSize(0);
                    mListview.onLoadComplete();
                    isFinished = 1;
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result) {
      Log.e("hh",errorMsg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.holder:

                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != results) {
            try{
                itemInfo = results.get(position - 1);


            if (itemInfo != null) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                intent.putExtra("DocId", itemInfo.DocId);
                intent.putExtra("DocType", itemInfo.DocType);
                intent.putExtra("DocTitle", itemInfo.DocTitle);
                intent.putExtra("Kind", ""); //2015-08-11
                intent.putExtra("TodoFlag", "0");
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
            } }catch (Exception e){
                Log.e("Error","点击暂无数据导致数组脚标越界");
            }
        }

    }

    @Override
    public void onLoad() {
//        Toast.makeText(getActivity(), "加载更多" + CurrentIndex, Toast.LENGTH_SHORT).show();
        if (isFinished != 1) {
            mListview.setResultSize(10);
            getMoredata();
        }
    }

    @Override
    public void onRefresh() {
        results.clear();
        more_start=1;
        more_end=30;
        initNet(0, 30, PULLTOREFRESH_STATE, SearchKeyWords);
    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
            mListview.setOnItemClickListener(null);
        } else {
            mListview.setOnItemClickListener(this);
        }
    }


    private class SlideAdapter extends BaseAdapter {

        //        private LayoutInflater mInflater;
        SlideAdapter() {
            super();
//            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            Log.e("aaa", results.size() + "");
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                LayoutInflater.from(getActivity()).inflate(R.layout.item_listview_delete, null);
                View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_guanzhu_item, null);
                ;

                slideView = new SlideView(getActivity());
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(MinePrevenanceFragment.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            PrevenanceResult item = results.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

//            holder.icon.setImageResource(item.iconRes);
            holder.title.setText(item.DocTitle);
            holder.msg.setText(item.DocType);
            holder.time.setText(item.SendDate);
            holder.tv_sendfrom.setText(item.SendFrom);
//            if (item.iconId != null && !"".equals(item.iconId))
//                BitmapUtils.instance().display(holder.guanzhu_icon, item.iconId);
            Glide.with(getActivity()).load(item.iconId).placeholder(R.drawable.icon_email).error(R.drawable.icon_email).transform(new GlideRoundTransform(getActivity())).into( holder.guanzhu_icon);

//            else
//                holder.guanzhu_icon.setImageResource(R.drawable.icon_email);
            holder.deleteHolder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AttentionFunction(results.get(position));
                    results.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "取消关注成功", Toast.LENGTH_SHORT).show();
                }
            });

            return slideView;
        }

    }

    private static class ViewHolder {

        public TextView title;
        public TextView msg;
        public TextView time;
        public TextView deleteHolder;
        public ImageView guanzhu_icon;
        public TextView tv_sendfrom;
        ViewHolder(View view) {

            title = (TextView) view.findViewById(R.id.tv_guanzhu_title);
            msg = (TextView) view.findViewById(R.id.tv_guanzhu_flowname);
            time = (TextView) view.findViewById(R.id.tv_guanzhu_time);
            deleteHolder = (TextView) view.findViewById(R.id.delete);
            tv_sendfrom = (TextView) view.findViewById(R.id.tv_sendfrom);
            guanzhu_icon = (ImageView) view.findViewById(R.id.guanzhu_icon);

        }
    }

    public void AttentionFunction(PrevenanceResult result) {
        String path = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.SET_ATTENTION_YESORNO;
//        String path = "http://114.112.89.94:8081/testv7/api/GetMobileData/SetAttentionYesOrNo";
        AsyncHttpClient client = new AsyncHttpClient();
        OAConText instance = OAConText.getInstance(getActivity());
        client.addHeader("Content-Type", "application/json");
        try {

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObjectAll = new JSONObject();
            jsonObject.put("UserID", instance.UserID);
            jsonObject.put("UserName", instance.OA_UserName);
            jsonObject.put("OA_UserId", instance.OA_UserId);
            jsonObject.put("OA_DeptId", instance.ThirdDepartmentId);
            jsonObject.put("OA_DeptName", instance.ThirdDepartmentName);
            jsonObject.put("OA_UserName", instance.OA_UserName);

            jsonObject.put("OA_UnitId", instance.OA_UnitId);
            jsonObject.put("MRS_UserId", "");
            jsonObject.put("ThirdDepartmentId", instance.ThirdDepartmentId);
            jsonObject.put("ThirdDepartmentName", instance.ThirdDepartmentName);

//        jsonObject.put("attribute1", null);
//        jsonObject.put("IsEMIUser", null);
            jsonObject.put("NetworkName", instance.NetworkName);

            //jsonObjectAll.put("userloginname", instance.UserID);
            jsonObjectAll.put("DocId", result.DocId);
            jsonObjectAll.put("AttentionFlag", result.AttentionFlag);

            jsonObjectAll.put("AllowPush", result.AllowPush);


            jsonObjectAll.put("DocTitle", result.DocTitle);
            jsonObjectAll.put("DocType", result.DocType);
            jsonObjectAll.put("SendFrom", result.SendFrom);
            jsonObjectAll.put("SendDate", result.SendDate);
            jsonObjectAll.put("iconId", result.iconId);
            jsonObjectAll.put("Kind", result.Kind);
            jsonObjectAll.put("context", jsonObject);

            StringEntity stringEntity = new StringEntity(
                    jsonObjectAll.toString(), "utf-8");
            RequestHandle post = client.post(getActivity(), path,
                    stringEntity, null, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers,
                                              JSONObject response) {

                            super.onSuccess(statusCode, headers, response);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              Throwable throwable, JSONObject errorResponse) {
//                        Toast.makeText(getActivity(),
//                                "1234", Toast.LENGTH_SHORT).show();
                            super.onFailure(statusCode, headers, throwable,
                                    errorResponse);
                            throwable.printStackTrace();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        //开始搜索listener
        results.clear();
        more_start=1;
        more_end=30;
        //开始搜索listener
        SearchKeyWords = query;
        GuanZhuRequestBean docSearchParameters = new GuanZhuRequestBean();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.ModelName = "";
        if (!SearchKeyWords.equals("")) {
            docSearchParameters.Title = SearchKeyWords;
        } else {
            docSearchParameters.Title = "";
        }
        docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
        docSearchParameters.recordStartIndex = more_start + "";
        docSearchParameters.recordEndIndex = more_end + "";
        docSearchParameters.app_id = app_id;
        GetGuanZhuModel getdocListModel = new GetGuanZhuModel(this);
        getdocListModel.getDataFromServerByType(
                GetGuanZhuModel.TYPE_GET_ZERO_LIST, docSearchParameters);

        sv_search.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //输入框内容变化listener
        SearchKeyWords = newText;

        if (newText.toString().equals("") && SearchState != 1) {
            more_start=1;
            more_end=30;
            GuanZhuRequestBean docSearchParameters = new GuanZhuRequestBean();
            docSearchParameters.context = OAConText.getInstance(getActivity());
            docSearchParameters.ModelName = "";
            if (!SearchKeyWords.equals("")) {
                docSearchParameters.Title = SearchKeyWords;
            } else {
                docSearchParameters.Title = "";
            }
            docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
            docSearchParameters.recordStartIndex = more_start + "";
            docSearchParameters.recordEndIndex = more_end + "";
            docSearchParameters.app_id = app_id;
            GetGuanZhuModel getdocListModel = new GetGuanZhuModel(this);
            getdocListModel.getDataFromServerByType(
                    GetGuanZhuModel.TYPE_GET_ZERO_LIST, docSearchParameters);
            sv_search.clearFocus();
        }
        SearchState++;
        return false;
    }

    @Override
    public void onResume() {
//        initNet(0, 29, START_STATE, SearchKeyWords);
        super.onResume();
    }
}
