package com.htmitech.emportal.ui.plugin.oamodel;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.MyView.EmptyLayout;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.Mode;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.entity.Doc;
import com.htmitech.emportal.entity.DocSearchParameters;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.appcenter.model.task.AppCenterModel;
import com.htmitech.emportal.ui.daiban.DocAdapter;
import com.htmitech.emportal.ui.daiban.data.getdoclist.GetDocListEntity;
import com.htmitech.emportal.ui.daiban.model.task.GetDocListModel;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.minxing.client.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class DaiBanListFragment extends MyBaseFragment implements
        IBaseCallback, IBottomItemSelectCallBack, OnClickListener, SearchView.OnQueryTextListener {
    private final static String TAG = "DaiBanListFragment";


    private PullToRefreshListView mPullToRefreshListView;

    private String modelName;

    DocAdapter docAdapter;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        if (modelName == null)
            this.modelName = "";
        else
            this.modelName = modelName;
    }


    /**
     * 是否已读
     */
    private boolean isHaveRead;
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

    private boolean golink_flag;
    private EmptyLayout layout_search_no;
    private SearchView sv_search;
    private RelativeLayout rv_serach;
    private ImageView iv_serach;
    private TextView tv_serach;
    private boolean isSoso = false;

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_oamodel_daiban_list;
    }

    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    @Override
    protected void initViews() {
        // TODO Auto-generated method stub


        // 待办列表
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_todo);
        layout_search_no = (EmptyLayout) findViewById(R.id.layout_search_no);
        mPullToRefreshListView.setMode(Mode.BOTH);
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

        docAdapter = new DocAdapter(getActivity(), isHaveRead);
        mPullToRefreshListView.setAdapter(docAdapter);

        // 发起网络请求，获取所有待办列表
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.app_id = ((OAModelFragmentActivity) getActivity()).app_id;
        docSearchParameters.TodoFlag = ((OAModelFragmentActivity) getActivity()).com_workflow_plugin_selector_paramter_TodoFlag; // 0，待办；1，已办
        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.RecordEndIndex = countPerPage - 1;
        docSearchParameters.ModelName = modelName;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(
                GetDocListModel.TYPE_GET_ZERO_LIST, docSearchParameters);
        Log.d(TAG, "发起获取待办列表请求");

        layout_search_no.hide();

        sv_search = (SearchView) findViewById(R.id.sv_search);
        rv_serach = (RelativeLayout) findViewById(R.id.rv_serach);
        iv_serach = (ImageView) findViewById(R.id.iv_serach);
        tv_serach = (TextView) findViewById(R.id.tv_serach);

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
        if (!((OAModelFragmentActivity) getActivity()).com_workflow_plugin_selector_paramter_Title.equals("")) {
            iv_serach.setVisibility(View.GONE);
            tv_serach.setVisibility(View.GONE);
            sv_search.setVisibility(View.VISIBLE);
            sv_search.setQuery(((OAModelFragmentActivity) getActivity()).com_workflow_plugin_selector_paramter_Title, false);
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
    }

    /**
     * 刷新快捷键数据
     */
    public void refreshOcuData() {
        //获取所有当前用户已经自定义的快捷键列表
        AppCenterModel getocuListModel = new AppCenterModel(DaiBanListFragment.this);
        getocuListModel.getDataFromServerByType(AppCenterModel.TYPE_GET_CURRENTOCU_LIST,
                OAConText.getInstance(HtmitechApplication.getInstance()).UserID);
    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        Log.d(TAG, "返回获取待办列表请求");
        mPullToRefreshListView.onRefreshComplete();
        // TODO Auto-generated method stub
        if (requestTypeId == GetDocListModel.TYPE_GET_ZERO_LIST) { // 初次获取所有待办
            if (result != null) {
                GetDocListEntity entity = (GetDocListEntity) result;
//				Utils.toast(getActivity(), "取得了" + entity.getResult().length
//						+ "条待办信息", Toast.LENGTH_SHORT);
                Log.d(TAG, "取得了" + entity.getResult().length + "条待办信息");

                if (docListEntity == null) {
                    docListEntity = new Vector<Doc>();
                }
                docListEntity.removeAllElements();
                for (int i = 0; i < entity.getResult().length; i++) {
                    docListEntity.add(entity.getResult()[i]);
                }

                if (entity.getResult().length == 0) {
                    if (isSoso) {
                        if (isSoso) {
                            layout_search_no.setShowEmptyButton(false);
                            layout_search_no.showEmpty();
                        } else {
                            layout_search_no.setEmptyButtonClickListener(new OnClickListener() {
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
//				Utils.toast(getActivity(), "取得了更多" + entity.getResult().length
//						+ "条待办信息", Toast.LENGTH_SHORT);
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


        }
    }


    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
        // TODO Auto-generated method stub
        Log.d(TAG, "返回获取待办列表请求：错误");
        if (!Utils.isNetworkAvailable()) {
            layout_search_no.setNoWifiButtonClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    pullDownRefresh();
                }
            });
            layout_search_no.showNowifi();
        } else {
            layout_search_no.setErrorButtonClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DaiBanListFragment.this.getActivity().finish();
                }
            });
            layout_search_no.showError();
        }
    }

    public void setData() {
        /*
         * IntentFilter filter = new
		 * IntentFilter("com.comsoft.listviewrefresh");
		 * registerReceiver(receiver, filter);
		 */

		/*
         * docListEntity = (GetDoclistNew_Entity) getIntent()
		 * .getSerializableExtra("doclist");
		 * this.addElement(docListEntity.getDocs());
		 */


		/*List<Map<String, Object>> list = getData(docListEntity);

		adapter = new SimpleAdapter(getActivity(), list,
				R.layout.todolist_item,
				new String[] { "title", "info", "time", "img" }, new int[] {
						R.id.title, R.id.info, R.id.time, R.id.itemIcon });

		mPullToRefreshListView.setAdapter(adapter);*/

        docAdapter.setData(true, docListEntity);


        // listView.setOnScrollListener(onscrollListener);
        mPullToRefreshListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long id) {
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
                            intent.putExtra("app_id", ((OAModelFragmentActivity) getActivity()).app_id);
                            intent.putExtra("DocId", doc.getDocID());
                            intent.putExtra("DocType", doc.getDocType());
                            intent.putExtra("DocTitle", doc.getDocTitle());
                            intent.putExtra("IconId", doc.getIconId());
                            intent.putExtra("TodoFlag", doc.getTodoFlag());


                            intent.putExtra("actionButtonStyle", ((OAModelFragmentActivity) getActivity()).actionButtonStyle);
                            intent.putExtra("com_workflow_mobileconfig_IM_enabled", ((OAModelFragmentActivity) getActivity()).com_workflow_mobileconfig_IM_enabled);
                            intent.putExtra("isShare", ((OAModelFragmentActivity) getActivity()).isShare);
                            intent.putExtra("isWaterSecurity", ((OAModelFragmentActivity) getActivity()).isWaterSecurity);

                            startActivityForResult(intent, 0);
                        }
                    }
                });

    }


    public List<Map<String, Object>> getData(Vector<Doc> docListEntity) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (docListEntity == null) {
            return list;
        }

        int length = docListEntity.size();
        for (int i = 0; i < length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", docListEntity.get(i).getDocTitle());
            map.put("info", docListEntity.get(i).getDocType());
            map.put("time", docListEntity.get(i).getSendDate());
            if (isHaveRead) {
                map.put("img", R.drawable.icon_email_taken);   ///icon_email_taken
            } else {
                map.put("img", R.drawable.icon_email);  //icon_email ic_msg_p
            }
            list.add(map);
        }

        return list;
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
        /*if (golink_flag == false) {
            // 数据全部显示出来时运行此处代码，如果要实现分页功能，在这里加载下一页的数据
			golink_flag = true;
			
		}*/
    }

	/*public void addElement(Doc[] docArr) {
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
	}*/

    /**
     * 刷新列表
     **/
    public void getRefreshData(int interfaceId) {
        // context = ConText.getInstance();
        // String orderString = "";
        // AuthWS.getInstance().goLink(MenuItem_mobileReceiverListData.this,
        // false, MenuItem_mobileReceiverListData.this, interfaceId,
        // context, orderString, 0, countPerPage-1, method);
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.app_id = ((OAModelFragmentActivity) getActivity()).app_id;
        docSearchParameters.TodoFlag = ((OAModelFragmentActivity) getActivity()).com_workflow_plugin_selector_paramter_TodoFlag;
        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.RecordEndIndex = countPerPage - 1;
        docSearchParameters.ModelName = modelName;
        docSearchParameters.Title = query;
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
        docSearchParameters.app_id = ((OAModelFragmentActivity) getActivity()).app_id;
        docSearchParameters.TodoFlag = ((OAModelFragmentActivity) getActivity()).com_workflow_plugin_selector_paramter_TodoFlag;
        docSearchParameters.RecordStartIndex = countPerPage * pageNum;
        docSearchParameters.RecordEndIndex = countPerPage * (pageNum + 1) - 1;
        docSearchParameters.ModelName = modelName;
        docSearchParameters.Title = query;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(interfaceId,
                docSearchParameters);
        Log.d(TAG, "发起获取待办列表请求:getMoreData");
    }

    public void open(int startAngle, int endAngle) {
        Interpolator interpolator = new OvershootInterpolator(1.2f);
        RotateAnimation rotateAnimation = new RotateAnimation(startAngle,
                endAngle, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setFillBefore(true);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setInterpolator(interpolator);
		/*mHandle.clearAnimation();
		mHandle.startAnimation(rotateAnimation);*/
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageviewHandle_daiban_list:
			/*if (!mDrawer.isOpened()) {
				mDrawer.animateOpen();
			} else {
				mDrawer.animateClose();
			}*/
                break;
            default:
                break;
        }
    }

    @Override
    public void onFragmentTabClick(int position) {
        // TODO Auto-generated method stub

    }

    public void onScroll(int scrollY) {
        // LogUtil.e("mGridView_handle top=:" + mHandle.getTop() + "   y="
        // + mHandle.getY());
        // int mBuyLayout2ParentTop = Math.max(0, scrollY);
        // findViewById(R.id.layout_daiban_handle).layout(0,
        // mBuyLayout2ParentTop,
        // mGridView_handle.getWidth(),
        // mBuyLayout2ParentTop + mGridView_handle.getHeight());
    }

    public void onSearchClick(String search) {

        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.app_id = ((OAModelFragmentActivity) getActivity()).app_id;
        docSearchParameters.TodoFlag = ((OAModelFragmentActivity) getActivity()).com_workflow_plugin_selector_paramter_TodoFlag; // 0，待办；1，已办
        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.RecordEndIndex = countPerPage - 1;
        docSearchParameters.ModelName = modelName;
        docSearchParameters.Title = search;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(
                GetDocListModel.TYPE_GET_ZERO_LIST, docSearchParameters);

        sv_search.clearFocus();
        this.query = search;
    }

    public String query = "";

    @Override
    public boolean onQueryTextSubmit(String query) {
        onSearchClick(query);
        if (query.toString().equals("")) {
            isSoso = false;
        } else {
            isSoso = true;
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.equals("")) {
            onSearchClick(newText);
        }
        isSoso = false;
        return false;
    }
}
