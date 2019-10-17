package com.htmitech.emportal.ui.daiban;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.MyView.EmptyLayout;
import com.htmitech.commonx.pulltorefresh.library.ILoadingLayout;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.Mode;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.entity.Doc;
import com.htmitech.emportal.entity.DocSearchParameters;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.daiban.data.getdoclist.GetDocListEntity;
import com.htmitech.emportal.ui.daiban.model.task.GetDocListModel;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.emportal.ui.homepage.DocAdapter3;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.minxing.client.util.Utils;

import java.util.ArrayList;
import java.util.Vector;

public class YiBanListFragment extends MyBaseFragment implements IBaseCallback,
        IBottomItemSelectCallBack, SearchView.OnQueryTextListener {
    private final static String TAG = "YiBanListFragment";

    private PullToRefreshListView mPullToRefreshListView;
    private DocAdapter3 docAdapter;
    /**
     * 是否已办理
     */
    private boolean isHaveRead = true;

    /***
     * 列表实体对象
     */
    private Vector<Doc> docListEntity;
    private Vector<Doc> vectorDoc;

    /***
     * 页码
     **/
    private int pageNum = 0;
    /***
     * 每页要读取的记录数量
     **/
    private int countPerPage = 15;

    private SimpleAdapter adapter;

    private static final int REFRESH_DATA = 1;
    private static final int PULLDOWNTOREGRESH = 2;

    private boolean isHasMore = true;
    private boolean flag = true;
    private SearchView sv_search;
    private RelativeLayout rv_serach;
    private ImageView iv_serach;
    private TextView tv_serach;

    private EmptyLayout layout_search_no;
    private boolean isSoso = false;
    private String modelName = "";

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_yiban_oa_list;
    }

    @SuppressLint("HandlerLeak")
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void initViews() {
        // TODO Auto-generated method stub
        // 已经办理列表
        if (((DaiBanFragmentActivity) getActivity()).todoFlag == 1) {
            modelName = ((DaiBanFragmentActivity) getActivity()).modelName;
        }

        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_hasdo);
        sv_search = (SearchView) findViewById(R.id.sv_search);
        rv_serach = (RelativeLayout) findViewById(R.id.rv_serach);
        iv_serach = (ImageView) findViewById(R.id.iv_serach);
        tv_serach = (TextView) findViewById(R.id.tv_serach);
        layout_search_no = (EmptyLayout) findViewById(R.id.layout_search_no);
        int search_mag_icon_id = sv_search.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView search_mag_icon = (ImageView) sv_search.findViewById(search_mag_icon_id);//获取搜索图标
        search_mag_icon.setImageResource(R.drawable.mx_search_bar_icon_normal);//图标都是用src的
        int id = sv_search.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv_search.findViewById(id);
        textView.setTextSize(14);
        rv_serach.clearFocus();//清除焦
        //设置提示文字的颜色,这里走了点奇招,用Html类方法
        sv_search.setQueryHint(Html.fromHtml("<font color = #999999>" + "请输入标题关键字" + "</font>"));
        if (!modelName.equals("")) {
            iv_serach.setVisibility(View.GONE);
            tv_serach.setVisibility(View.GONE);
            sv_search.setVisibility(View.VISIBLE);
            sv_search.setQuery(modelName, false);
        }
        rv_serach.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                isSoso = true;
                iv_serach.setVisibility(View.GONE);
                tv_serach.setVisibility(View.GONE);
                sv_search.setVisibility(View.VISIBLE);
                sv_search.onActionViewExpanded();
            }
        });
        sv_search.setOnQueryTextListener(this);
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {
                return true;
            }
        });
        sv_search.setIconifiedByDefault(false);
        mPullToRefreshListView.setMode(Mode.BOTH);
        ILoadingLayout startLabels = mPullToRefreshListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("释放开始刷新");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = mPullToRefreshListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载");// 刷新时
        endLabels.setReleaseLabel("释放开始加载");// 下来达到一定距离时，显示的提示
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub
                // 下拉刷新
                pullDownRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub 上拉加载更多
                pullUpLoadMore();
            }
        });
        /**
         * 点击按钮刷新
         */
        ((EmptyLayout) getRootView()).setErrorButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullDownRefresh();
            }
        });
        // listView.setOnScrollListener(onscrollListener);
        mPullToRefreshListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long id) {
//						iv_serach.setVisibility(View.GONE);
//						tv_serach.setVisibility(View.GONE);
                        Doc doc = null;
                        System.out.println("position:" + position);
                        if (flag) {// item按键响应
                            if (null != docListEntity) {
                                doc = docListEntity.get(position - 1);
                                Log.i(TAG, "doc.getDocID():" + doc.getDocID());
                            }

                            Intent intent = new Intent();
                            intent.setClass(getActivity(),
                                    isHaveRead == false ? DetailActivity.class
                                            : DetailActivity.class);
                            intent.putExtra("DocId", doc.getDocID());
                            intent.putExtra("DocType", doc.getDocType());
                            intent.putExtra("DocTitle", doc.getDocTitle());
                            intent.putExtra("Kind", doc.getKind()); //2015-08-11
                            intent.putExtra("TodoFlag", doc.getTodoFlag());
                            intent.putExtra("sendFrom", doc.getSendFrom());
                            intent.putExtra("sendDate", doc.getSendDate());
                            intent.putExtra("app_id", ((DaiBanFragmentActivity) getActivity()).app_id);
                            intent.putExtra("actionButtonStyle", ((DaiBanFragmentActivity) getActivity()).actionButtonStyle);
                            intent.putExtra("com_workflow_mobileconfig_IM_enabled", ((DaiBanFragmentActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
                            intent.putExtra("isShare", ((DaiBanFragmentActivity) getActivity()).isShare);
                            intent.putExtra("isTextUrl", ((DaiBanFragmentActivity) getActivity()).isTextUrl);
                            intent.putExtra("app_version_id", ((DaiBanFragmentActivity) getActivity()).app_version_id);
                            intent.putExtra("isWaterSecurity", ((DaiBanFragmentActivity) getActivity()).isWaterSecurity);
                            if (doc.getIconId() == null || "".equals(doc.getIconId())
                                    || !(
                                    doc.getIconId().endsWith(".png") || doc.getIconId().endsWith(".jpg")
                            )) {
                                intent.putExtra("IconId", "");
                            } else {
                                intent.putExtra("IconId", doc.getIconId());
                            }
                            startActivityForResult(intent, 0);
                        }
                    }
                });

        docAdapter = new DocAdapter3(getActivity(), isHaveRead);

        mPullToRefreshListView.setAdapter(docAdapter);


        // 发起网络请求，获取所有待办列表
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.TodoFlag = "1"; // 0，待办；1，已办
        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.Title = "";
        docSearchParameters.ModelName = modelName;
        docSearchParameters.RecordEndIndex = countPerPage - 1;
        docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(
                GetDocListModel.TYPE_GET_ZERO_LIST, docSearchParameters);
        Log.d(TAG, "发起获取已办列表请求");
        layout_search_no.hide();
    }

    public void addElement(Doc[] docArr) {
        if (vectorDoc == null) {
            vectorDoc = new Vector<Doc>();
        }
        int length = docArr.length;
        for (int i = 0; i < length; i++) {
            if (!vectorDoc.contains(docArr[i]) && docArr[i] != null) {
                vectorDoc.addElement(docArr[i]);
            }
        }
        docListEntity = vectorDoc;
    }

    public void pullDownRefresh() {
        // 刷新
        pageNum = 0;
        getRefreshData(GetDocListModel.TYPE_GET_ZERO_LIST);
    }

    private void pullUpLoadMore() {
        if (!isHasMore) {
            Utils.toast(getActivity(), "已经是最后一页了！", Toast.LENGTH_SHORT);
            mPullToRefreshListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    mPullToRefreshListView.onRefreshComplete();
                }
            }, 100);
            return;
        }
        pageNum = pageNum + 1;
        getMoreData(pageNum, GetDocListModel.TYPE_GET_MORE_LISTDATA);

    }

    /**
     * 刷新列表
     **/
    public void getRefreshData(int interfaceId) {
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.TodoFlag = "1";
        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.RecordEndIndex = countPerPage - 1;
        docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(interfaceId,
                docSearchParameters);
        Log.d(TAG, "发起获取待办列表请求:getRefreshData");
    }

    /****
     * 获取更多
     */
    public void getMoreData(int pageNum, int interfaceId) {
        String orderString = "";
        /*
         * AuthWS.getInstance().goLink(MenuItem_mobileReceiverListData.this,
		 * false, MenuItem_mobileReceiverListData.this, interfaceId,
		 * getActivity(), orderString, countPerPage * pageNum, countPerPage *
		 * (pageNum + 1) - 1, method);
		 */
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.TodoFlag = "1";
        docSearchParameters.RecordStartIndex = countPerPage * pageNum;
        docSearchParameters.RecordEndIndex = countPerPage * (pageNum + 1) - 1;
        docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(interfaceId,
                docSearchParameters);
        Log.d(TAG, "发起获取待办列表请求:getMoreData");
    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        // TODO Auto-generated method stub
        Log.d(TAG, "返回获取待办列表请求");
        mPullToRefreshListView.onRefreshComplete();
        if (requestTypeId == GetDocListModel.TYPE_GET_ZERO_LIST) { // 初次获取所有已办
            if (result != null) {
                GetDocListEntity entity = (GetDocListEntity) result;
                Log.d(TAG, "取得了" + entity.getResult().length + "条已办信息");

                if (docListEntity == null) {
                    docListEntity = new Vector<Doc>();
                }
                docListEntity.removeAllElements();
                for (int i = 0; i < entity.getResult().length; i++) {
                    docListEntity.add(entity.getResult()[i]);
                }
                if (entity.getResult().length == 0) {
                    if (isSoso) {
                        layout_search_no.setShowEmptyButton(false);
                        layout_search_no.showEmpty();
                    } else {
                        layout_search_no.setEmptyButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pullDownRefresh();
                            }
                        });
                        layout_search_no.setShowEmptyButton(true);
                        layout_search_no.showEmpty();
                        layout_search_no.setShowEmptyButton(true);
                    }
                } else {
                    layout_search_no.hide();
                }
            } else {
                docListEntity = new Vector<Doc>();
            }
            if (docListEntity.size() < countPerPage) {
                isHasMore = false;
            }
            setData();

        } else if (requestTypeId == GetDocListModel.TYPE_GET_MORE_LISTDATA) {
            if (result != null) {
                GetDocListEntity entity = (GetDocListEntity) result;
                Log.d(TAG, "取得了更多：" + entity.getResult().length + "条待办信息");

                for (int i = 0; i < entity.getResult().length; i++) {
                    docListEntity.add(entity.getResult()[i]);
                }
                if (entity.getResult().length < countPerPage) {
                    isHasMore = false;
                }
                setData();
            }

        }
        ((EmptyLayout) getRootView()).hide();
    }

    // 修改
    public ArrayList<Doc> getData(Vector<Doc> docListEntity) {
        if (docListEntity == null) {
            return new ArrayList<Doc>();
        } else {
            return new ArrayList<Doc>(docListEntity);
        }
    }

    public void setData() {
        docAdapter.setData(true, docListEntity);
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
        // TODO Auto-generated method stub
        Log.d(TAG, "返回获取已办列表请求：错误");
//        ((EmptyLayout) getRootView()).showError();
        if (!Utils.isNetworkAvailable()) {
            layout_search_no.setNoWifiButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pullDownRefresh();
                }
            });
            layout_search_no.showNowifi();
        } else {
            layout_search_no.setErrorButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YiBanListFragment.this.getActivity().finish();
                }
            });
            layout_search_no.showError();
        }
    }


    @Override
    public void onFragmentTabClick(int position) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onQueryTextChange(String arg0) {
        // TODO Auto-generated method stub
        if (arg0.equals("")) {
            DocSearchParameters docSearchParameters = new DocSearchParameters();
            docSearchParameters.context = OAConText.getInstance(getActivity());
            docSearchParameters.TodoFlag = "1"; // 0，待办；1，已办
            docSearchParameters.RecordStartIndex = 0;
            docSearchParameters.RecordEndIndex = countPerPage - 1;
            docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
            GetDocListModel getdocListModel = new GetDocListModel(this);
            getdocListModel.getDataFromServerByType(
                    GetDocListModel.TYPE_GET_ZERO_LIST, docSearchParameters);
            sv_search.clearFocus();
        }
        isSoso = false;
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        // TODO Auto-generated method stub
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.TodoFlag = "1"; // 0，待办；1，已办
        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.Title = "" + arg0;
        docSearchParameters.RecordEndIndex = countPerPage - 1;
        docSearchParameters.app_id = ((DaiBanFragmentActivity) getActivity()).app_id;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(
                GetDocListModel.TYPE_GET_ZERO_LIST, docSearchParameters);
        sv_search.clearFocus();
        if (arg0.toString().equals("")) {
            isSoso = false;
        } else {
            isSoso = true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sv_search != null)
            sv_search.clearFocus();
    }
}
