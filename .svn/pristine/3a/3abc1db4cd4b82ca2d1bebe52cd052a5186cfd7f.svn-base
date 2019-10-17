package com.htmitech.emportal.ui.document;

import com.minxing.client.RootActivity;

/**
 * Created by yanxin on 2017-8-31.
 */
public class DocumentSubActivity extends RootActivity {
//    private LinearLayout linearDeleteItem;
//    private Button buttonDeleteItem;
//    private ImageButton leftBackButton = null;
//    private RelativeLayout relativeDeleteItemTop;
//    private Button buttonDeleteCanle;
//    private Button buttonDeleteSelectAll;
//    private RelativeLayout relativeSystemTitle;
//    private EditText editText_keyword;
//    private ImageView remove_icon; //去除所有关键字按钮
//    private File file_tmp; // 临时文件 作为操作删除使用
//    private INetWorkManager netWorkManager;
//    private LinearLayout linearDoucmentEmpty;
//    /**
//     * 当前节点ID
//     */
//    private String mCurrentDocNodeID;
//    private String app_id;
//    public String functionCodeFinish = LogManagerEnum.DOCUMENT_MAIN.functionCode;
//    private PullToRefreshListView documentnodeListView = null;
//    private Button allBack;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_documentandnodelist);
//        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
//        mCurrentDocNodeID = getIntent().getStringExtra("ParentDocNodeID");
//        app_id = getIntent().getStringExtra("app_id");
//        functionCodeFinish = getIntent().getStringExtra("functionCodeFinish");
//        String mParentDocNodeName = getIntent().getStringExtra("ParentDocNodeName");
//        ((TextView) findViewById(R.id.title_name)).setText(mParentDocNodeName);
//        linearDoucmentEmpty = (LinearLayout) findViewById(R.id.ll_doucment_gride_emptey);
//        allBack = (Button) findViewById(R.id.title_right_button);
//        allBack.setVisibility(View.VISIBLE);
//        editText_keyword = (EditText) findViewById(R.id.editText_keyword);
//        editText_keyword.addTextChangedListener(watcher);
//        remove_icon = (ImageView) findViewById(R.id.remove_icon);
//        remove_icon.setVisibility(View.GONE);
//        leftBackButton = (ImageButton) findViewById(R.id.title_left_button);
//        linearDeleteItem = (LinearLayout) findViewById(R.id.ll_delete_item);
//        buttonDeleteItem = (Button) findViewById(R.id.bt_delete_item);
//        buttonDeleteItem.setOnClickListener(this);
//        buttonDeleteCanle = (Button) findViewById(R.id.bt_delete_item_canle);
//        buttonDeleteCanle.setOnClickListener(this);
//        buttonDeleteSelectAll = (Button) findViewById(R.id.bt_delete_item_selectall);
//        buttonDeleteSelectAll.setOnClickListener(this);
//        relativeDeleteItemTop = (RelativeLayout) findViewById(R.id.ll_delete_item_top);
//        relativeSystemTitle = (RelativeLayout) findViewById(R.id.system_title);
//        documentnodeListView = (PullToRefreshListView) findViewById(R.id.listview_documentnode);
//        documentnodeListView.setMode(PullToRefreshBase.Mode.BOTH);
//        initClick();
//    }
//    /*
//    * 点击事件初始化
//    * */
//    private void initClick() {
//        allBack.setOnClickListener(this);
//        remove_icon.setOnClickListener(this);
//        leftBackButton.setOnClickListener(this);
//        editText_keyword.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
//                    // 先隐藏键盘
//                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//                            .hideSoftInputFromWindow(DocumentSubActivity.this
//                                            .getCurrentFocus().getWindowToken(),
//                                    InputMethodManager.HIDE_NOT_ALWAYS);
////                    getMoreData(0, 1, "");
//                    if (null != documentAdapter)
//                        documentAdapter.Clear();
//                    pullDownRefresh();
//                    /*// 调用搜索接口
//                    handler.sendEmptyMessage(MSGID_GET_FILELIST);*/
//                    return true;
//                }
//                return false;
//            }
//        });
//        documentnodeListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
//
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
//                // TODO Auto-generated method stub
//                // 下拉刷新
//                pullDownRefresh();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
//                // TODO Auto-generated method stub 上拉加载更多
//                pullUpLoadMore();
//            }
//        });
//    }
//
//    @Override
//    public void success(String requestValue, int type, String requestName) {
//
//    }
//
//    @Override
//    public void fail(String exceptionMessage, int type, String requestName) {
//
//    }
//
//    @Override
//    public void notNetwork() {
//
//    }
//
//    @Override
//    public void callbackMainUI(String successMessage) {
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.title_right_button:
//                DocumentNodeUtil.removeAll();
//                break;
//            case R.id.remove_icon:
//                editText_keyword.setText("");
//                break;
//            case R.id.title_left_button:
//                if (file_tmp != null && file_tmp.exists()) {
//                    file_tmp.delete();
//                }
//                finishWithAnimation();
//                break;
//        }
//
//    }
//
//    private TextWatcher watcher = new TextWatcher() {
//
//        public void afterTextChanged(Editable s) {
//            if (editText_keyword.getText().toString().equals("")) {
//                remove_icon.setVisibility(View.GONE);
//                handler.sendEmptyMessage(MSGID_GET_FILELIST);
//            } else {
//                remove_icon.setVisibility(View.VISIBLE);
//            }
//        }
//
//        public void beforeTextChanged(CharSequence s, int start, int count,
//                                      int after) {
//
//        }
//
//        public void onTextChanged(CharSequence s, int start, int before,
//                                  int count) {
//
//        }
//    };
}
