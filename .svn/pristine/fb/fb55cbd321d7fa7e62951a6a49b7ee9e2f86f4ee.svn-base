package com.htmitech.emportal.ui.document;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.api.BookInit;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.document.adapter.DocumentGridAdapter;
import com.htmitech.emportal.ui.document.data.DocumentNodeEntity;
import com.htmitech.emportal.ui.document.data.DocumentNodeListEntity;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.CHTTP;
import com.minxing.client.RootActivity;
import com.minxing.client.util.DocumentNodeUtil;
import com.minxing.client.util.Utils;

import java.util.Vector;

import com.htmitech.thread.AnsynHttpRequest;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 资料库
 */
public class DocumentMainActivity extends RootActivity implements ObserverCallBackType {
    private final static String TAG = DocumentMainActivity.class.getName();
    private ImageButton leftBackButton = null;
    private ImageButton rightAddButton = null;
    private Button rightTextButton = null;
    private DocumentGridAdapter documentAdapter = null;
    private GridView documentGridView = null;
    private String appId;
    private LinearLayout linearDocumentEmpty;
    private INetWorkManager netWorkManager;
    /***
     * 列表实体对象
     */
    private Vector<DocumentNodeEntity> documentNodeListEntity;
    private boolean isHome;
    private ImageButton title_left_button_home;
    private ImageView ivMore;
    private ToRightPopMenum functionPopMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        DocumentNodeUtil.removeAll();
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        documentGridView = (GridView) findViewById(R.id.document_gridview);
        ((TextView) findViewById(R.id.title_name)).setText("资料库");
        ivMore = (ImageView)findViewById(R.id.iv_more);
        appId = getIntent().getStringExtra("app_id");
        isHome = getIntent().getBooleanExtra("isHome", false);

        leftBackButton = (ImageButton) findViewById(R.id.title_left_button);
        title_left_button_home = (ImageButton) findViewById(R.id.title_left_button_home);
        if (isHome) {
            title_left_button_home.setVisibility(View.VISIBLE);
            leftBackButton.setVisibility(View.GONE);
        }
        title_left_button_home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHome) {
                    BookInit.getInstance().getmCallbackMX().callUserMeesageMain();
                } else {
                    finishWithAnimation();
                }
            }
        });
        leftBackButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isHome) {
                    BookInit.getInstance().getmCallbackMX().callUserMeesageMain();
                } else {
                    finishWithAnimation();
                }
            }
        });
        linearDocumentEmpty = (LinearLayout) findViewById(R.id.ll_doucment_gride_emptey);

//		rightAddButton = (ImageButton) findViewById(R.id.title_right_image_button);


        rightTextButton = (Button) findViewById(R.id.title_right_button);
        rightTextButton.setVisibility(View.GONE);
        rightTextButton.setText("搜索");
        rightTextButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.toast(DocumentMainActivity.this, "搜索", Toast.LENGTH_SHORT);
            }
        });

        documentGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intentSub = new Intent(DocumentMainActivity.this, DocumentAndNodeListActivity.class);
                DocumentNodeEntity documentNode = (DocumentNodeEntity) ((DocumentGridAdapter) documentGridView.getAdapter()).getItem(position);
                intentSub.putExtra("ParentDocNodeID", documentNode.id);
                intentSub.putExtra("ParentDocNodeName", documentNode.name);
                intentSub.putExtra("functionCodeFinish", LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
                intentSub.putExtra("app_id", appId);
                startActivity(intentSub);
            }
        });

        functionPopMenu = new ToRightPopMenum(
                this);
        functionPopMenu.setView(ApplicationAllEnum.ZLK.tab_item_id, ivMore);
        ivMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!functionPopMenu.isShowing()) {
                    functionPopMenu.showAsDropDown(v);
                }
            }
        });

        //获取文档库目录 开启日志
        netWorkManager.logFunactionStart(DocumentMainActivity.this, DocumentMainActivity.this, LogManagerEnum.DOCUMENT_MAIN);

    }



    public void setData() {
        documentAdapter = new DocumentGridAdapter(DocumentMainActivity.this, documentNodeListEntity);
        documentGridView.setAdapter(documentAdapter);
        documentAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishWithAnimation();
        }
        return false;
    }


    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals(LogManagerEnum.DOCUMENT_MAIN.getFunctionCode())) {
            //发起网络请求，获取所有当前用户能够看到的顶层资料库目录
            String url = ServerUrlConstant.SERVER_EMPAPI_URL() +
                    ServerUrlConstant.DOCUMENT_GETTOPLEVELNODE_METHOD + "/" + OAConText.getInstance(DocumentMainActivity.this).UserID;
            AnsynHttpRequest.requestByPostWithToken(DocumentMainActivity.this, "", url, CHTTP.POSTWITHTOKEN, DocumentMainActivity.this, "getDocumentNodeTopLevel", LogManagerEnum.DOCUMENT_MAIN.getFunctionCode());
        } else if (requestName.equals("getDocumentNodeTopLevel")) {
            if (requestValue != null) {
                DocumentNodeListEntity entity = JSONObject.parseObject(requestValue, DocumentNodeListEntity.class);
                if (this.documentNodeListEntity == null) {
                    this.documentNodeListEntity = new Vector<DocumentNodeEntity>();
                }
                this.documentNodeListEntity.removeAllElements();
                if(null != entity && null !=  entity.getResult()){
                    for (int i = 0; i < entity.getResult().size(); i++) {
                        this.documentNodeListEntity.add(entity.getResult().get(i));
                    }
                }
                netWorkManager.logFunactionFinsh(DocumentMainActivity.this, DocumentMainActivity.this, "documentmainSuccess", LogManagerEnum.DOCUMENT_MAIN.getFunctionCode(), entity.getMessage().toString(), INetWorkManager.State.SUCCESS);
            } else {
                documentNodeListEntity = new Vector<DocumentNodeEntity>();
            }
            if (documentNodeListEntity.size() == 0) {
                linearDocumentEmpty.setVisibility(View.VISIBLE);
            } else {
                linearDocumentEmpty.setVisibility(View.GONE);
            }
            setData();
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals(LogManagerEnum.DOCUMENT_MAIN.getFunctionCode())) {
            //发起网络请求，获取所有当前用户能够看到的顶层资料库目录
            String url = ServerUrlConstant.SERVER_EMPAPI_URL() +
                    ServerUrlConstant.DOCUMENT_GETTOPLEVELNODE_METHOD + "/" + OAConText.getInstance(DocumentMainActivity.this).UserID;
            AnsynHttpRequest.requestByPostWithToken(DocumentMainActivity.this, "", url, CHTTP.POSTWITHTOKEN, DocumentMainActivity.this, "getDocumentNodeTopLevel", LogManagerEnum.DOCUMENT_MAIN.getFunctionCode());
        }
        if (requestName.equals("getDocumentNodeTopLevel")) { //获取顶层目录失败
            Utils.toast(DocumentMainActivity.this, "获取顶层目录失败：" + exceptionMessage, Toast.LENGTH_SHORT);
            netWorkManager.logFunactionFinsh(DocumentMainActivity.this, DocumentMainActivity.this, "documentmainSuccess", LogManagerEnum.DOCUMENT_MAIN.getFunctionCode(), exceptionMessage, INetWorkManager.State.FAIL);
        }
        if (documentNodeListEntity != null && documentNodeListEntity.size() == 0) {
            linearDocumentEmpty.setVisibility(View.VISIBLE);
        } else {
            linearDocumentEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
