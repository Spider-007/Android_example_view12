package com.htmitech.proxy.activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.htmitech.MyView.EmptyLayout;
import com.htmitech.api.BookInit;
import com.htmitech.app.widget.CustomEditText;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.adapter.SearchAdapter;
import com.htmitech.proxy.dao.MobileSearchDao;
import com.htmitech.proxy.doman.SearchDoman;
import com.htmitech.proxy.doman.SearchResult;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.minxing.client.util.FastJsonUtils;

import java.util.ArrayList;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.entity.Dics;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by Administrator on 2018/5/24.
 */

public class SearchActivity extends BaseFragmentActivity implements ObserverCallBackType,SearchView.OnQueryTextListener {
    private CustomEditText mSearchView;
    private TextView tvSoso;
    private ImageButton title_left_button;
    private ListView lv_serach;

    private String appId;
    private String userId;
    private String keyWord;
    private String fieldName;
    private SearchAdapter mSearchAdapter;
    private SearchDoman mSearchDoman;
    private EmptyLayout mEmptyLayout;
    private String queryText;
    private final static String OA_GETDOCINFO_LIST_CONDITION = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.OA_GETDOCINFO_LIST_CONDITION;;

    private final static String CONDITION = "condition";

    private INetWorkManager netWorkManager;

    private ArrayList<Dics> dics;

    private MobileSearchDao mMobileSearchDao;

    private TextView tv_check;

    private TextView tv_remove;

    private int maxLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_search);
        initView();
        initData();
    }

    public void initView(){
        mSearchView = (CustomEditText) findViewById(R.id.sv_search);
        tvSoso = (TextView) findViewById(R.id.tv_soso);
        tv_check = (TextView) findViewById(R.id.tv_check);
        tv_remove = (TextView) findViewById(R.id.tv_remove);
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        lv_serach = (ListView) findViewById(R.id.lv_serach);
        mEmptyLayout = (EmptyLayout) findViewById(R.id.layout_search_no);
    }

    public void initData(){
        mMobileSearchDao = new MobileSearchDao(this);
        fieldName = getIntent().getStringExtra("fieldName");
        maxLength = getIntent().getIntExtra("maxLength",10);
        appId = ComponentInit.getInstance().getAppId();
        userId = OAConText.getInstance(this).UserID;
        mSearchDoman = new SearchDoman();
        mSearchDoman.setAppId(appId);
        mSearchDoman.setUserId(userId);
        mSearchDoman.setFieldName(fieldName);
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
//        int id = mSearchView.getContext().getResources()
//                .getIdentifier("android:id/search_src_text", null, null);
//        TextView searchTextView = (TextView) mSearchView.findViewById(id);
//        android.widget.LinearLayout.LayoutParams textLayoutParams = (android.widget.LinearLayout.LayoutParams) searchTextView.getLayoutParams();
//        textLayoutParams.height = textLayoutParams.WRAP_CONTENT;
//        searchTextView.setLayoutParams(textLayoutParams);
//        searchTextView.setHint("请输入关键字进行搜索");
//        mSearchView.setEnabled(true);
//        mSearchView.setMaxWidth(20);
//        if (mSearchView != null) {
//            try {        //--拿到字节码
//                Class<?> argClass = mSearchView.getClass();
//                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
//                Field ownField = argClass.getDeclaredField("mSearchPlate");
//                //--暴力反射,只有暴力反射才能拿到私有属性
//                ownField.setAccessible(true);
//                View mView = (View) ownField.get(mSearchView);
//                //--设置背景
//                mView.setBackgroundColor(Color.TRANSPARENT);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        mSearchView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
                maxLength) });
//        mSearchView.setOnQueryTextListener(this);
        title_left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.this.finish();
            }
        });

        tvSoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                mSearchDoman.setKeyWord(mSearchView.getText().toString());
                netWorkManager.logFunactionStart(SearchActivity.this, SearchActivity.this, "getListContion", LogManagerEnum.GGENERAL.functionCode);
            }
        });
        dics = (ArrayList<Dics>) mMobileSearchDao.getListSearch(appId,fieldName);
        notifys();
        lv_serach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                BookInit.getInstance().getmISearchResult().searchResult(dics.get(i));
                mMobileSearchDao.saveSearch(dics.get(i),appId,fieldName);
                SearchActivity.this.finish();
            }
        });
        tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMobileSearchDao.deleteAllSearch();
                dics.clear();
                mSearchAdapter.setData(dics);
                tv_check.setVisibility(View.GONE);
                mEmptyLayout.setEmptyMessage("当前数据为空，请搜索！");
                mEmptyLayout.showEmpty();
            }
        });

        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    onQueryTextSubmit(mSearchView.getText().toString());
                }
                return false;
            }
        });

    }

    public void notifys(){
        if(dics != null && dics.size() > 0){
            tv_check.setVisibility(View.VISIBLE);
            mSearchAdapter = new SearchAdapter(dics,mSearchView.getText().toString(),this,1, new CSearchDelete());
            lv_serach.setAdapter(mSearchAdapter);
        }else{
            tv_check.setVisibility(View.GONE);
            tv_remove.setVisibility(View.GONE);
            mEmptyLayout.setEmptyMessage("当前数据为空，请搜索！");
            mEmptyLayout.showEmpty();
        }
    }

    @Override
    public boolean onQueryTextChange(String arg0) {
        //输入框内容变化listener
        // TODO Auto-generated method stub
        this.queryText = arg0;
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        showDialog();
        //开始搜索listener
        // TODO Auto-generated method stub
        // 发起网络请求，获取所有待办列表
        mSearchDoman.setKeyWord(arg0);
        netWorkManager.logFunactionStart(this, this, "getListContion", LogManagerEnum.GGENERAL.functionCode);
        return false;
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if ("getListContion".equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, mSearchDoman, OA_GETDOCINFO_LIST_CONDITION, CHTTP.POSTWITHTOKEN, this, CONDITION, LogManagerEnum.GGENERAL.functionCode);
        }else if(CONDITION.equals(requestName)){
            tv_check.setVisibility(View.GONE);
            tv_remove.setVisibility(View.GONE);
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this,requestValue, type, OA_GETDOCINFO_LIST_CONDITION, mSearchDoman, this, requestName, LogManagerEnum.GGENERAL.functionCode);
            SearchResult mSearchResult = FastJsonUtils.getPerson(requestValue, SearchResult.class);
            if(mSearchResult != null && mSearchResult.code == 200){
                if(mSearchResult.getResult().size() > 0 ){
                    mEmptyLayout.hide();
                    dics = mSearchResult.getResult();
                    mSearchAdapter = new SearchAdapter(dics,mSearchView.getText().toString(),this);
                    lv_serach.setAdapter(mSearchAdapter);
                }else{
                    mEmptyLayout.setEmptyMessage("抱歉，没有找到相关搜索结果\n" +
                            "换个词试一试吧");
                    mEmptyLayout.showEmpty();
                }

            }

            dismissDialog();
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    public class CSearchDelete implements ISearchDelete{

        @Override
        public void searchDelete(Dics dic) {

            mMobileSearchDao.deleteSearch(appId,fieldName,dic.id);

            dics = (ArrayList<Dics>) mMobileSearchDao.getListSearch(appId,fieldName);
            notifys();
            mSearchAdapter = new SearchAdapter(dics,mSearchView.getText().toString(),SearchActivity.this,1, new CSearchDelete());
            lv_serach.setAdapter(mSearchAdapter);

        }
    }



    public interface ISearchDelete{
        public void searchDelete(Dics dics);
    }
}
