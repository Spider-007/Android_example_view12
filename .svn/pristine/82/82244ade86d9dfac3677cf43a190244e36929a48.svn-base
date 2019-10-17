package com.htmitech.emportal.ui.plugin.oamodel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.htmitech.MyView.EmptyLayout;
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
import com.htmitech.emportal.ui.daiban.DocAdapter;
import com.htmitech.emportal.ui.daiban.data.getdoclist.GetDocListEntity;
import com.htmitech.emportal.ui.daiban.model.task.GetDocListModel;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.minxing.client.util.Utils;

import java.util.Vector;

public class YiBanListFragment extends MyBaseFragment implements IBaseCallback,
        IBottomItemSelectCallBack {
    private final static String TAG = "YiBanListFragment";

    private PullToRefreshListView mPullToRefreshListView;

    DocAdapter docAdapter;

    private String modelName;

    private boolean isSoso = false;

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
    public EmptyLayout layout_search_no;

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.fragment_yiban_list;
    }

    @SuppressLint("HandlerLeak")
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void initViews() {
        // TODO Auto-generated method stub
        // 已经办理列表
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_hasdo);
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
        docSearchParameters.TodoFlag = "1"; // 0，待办；1，已办
        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.RecordEndIndex = countPerPage - 1;
        docSearchParameters.ModelName = modelName;
        GetDocListModel getdocListModel = new GetDocListModel(this);
        getdocListModel.getDataFromServerByType(
                GetDocListModel.TYPE_GET_ZERO_LIST, docSearchParameters);
        Log.d(TAG, "发起获取已办列表请求");


    }

	/*public List<Map<String, Object>> getData(Vector<Doc> docListEntity) {
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
	}*/

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
        // context = ConText.getInstance();
        // String orderString = "";
        // AuthWS.getInstance().goLink(MenuItem_mobileReceiverListData.this,
        // false, MenuItem_mobileReceiverListData.this, interfaceId,
        // context, orderString, 0, countPerPage-1, method);
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.context = OAConText.getInstance(getActivity());
        docSearchParameters.app_id = ((OAModelFragmentActivity) getActivity()).app_id;
        docSearchParameters.TodoFlag = "1";
        docSearchParameters.RecordStartIndex = 0;
        docSearchParameters.RecordEndIndex = countPerPage - 1;
        docSearchParameters.ModelName = modelName;
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
        docSearchParameters.TodoFlag = "1";
        docSearchParameters.RecordStartIndex = countPerPage * pageNum;
        docSearchParameters.RecordEndIndex = countPerPage * (pageNum + 1) - 1;
        docSearchParameters.ModelName = modelName;
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
//				Utils.toast(getActivity(), "取得了" + entity.getResult().length
//						+ "条待办信息", Toast.LENGTH_SHORT);
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

        // listView.setOnScrollListener(onscrollListener);


        docAdapter.setData(true, docListEntity);

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


    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
        // TODO Auto-generated method stub
        Log.d(TAG, "返回获取已办列表请求：错误");
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

}
