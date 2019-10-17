package com.htmitech.ztcustom.zt.fragment.qualityobjectiondetailfragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.QualityObjectionClqkAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.chinarailway.DynamicDetailDataActivity;
import com.htmitech.ztcustom.zt.chinarailway.QualityObjectionDetailCLQKSubmitActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.GetUserDetailInformationRequest;
import com.htmitech.ztcustom.zt.constant.GetUserDetailInformationResult;
import com.htmitech.ztcustom.zt.constant.QualityObjectionDetailCLQKListRequest;
import com.htmitech.ztcustom.zt.constant.QualityObjectionDetailClqkListResult;
import com.htmitech.ztcustom.zt.constant.QualityObjectionDetailClqkResult;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.interfaces.OnClickBigImageListener;
import com.htmitech.ztcustom.zt.interfaces.OnQualityObjectionDetailClqkImageViewClickCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.view.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.unit.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class QualityObjectionDetailCLQKFragment extends BaseFragment implements View.OnClickListener, OnQualityObjectionDetailClqkImageViewClickCallBack {

    private ListView listView;
    private ImageView iv_i;
    private QualityObjectionDetailCLQKListRequest request;
    private String id;
    private boolean isFristShow = true;
    private final static int REQUESTCODE = 1; // 返回的结果码
    private List<QualityObjectionDetailClqkListResult> listResults;
    private QualityObjectionClqkAdapter adapter;
    private PhotoView bigImageView;
    private OnClickBigImageListener imageListener;
    private GetUserDetailInformationRequest getUserDetailInformationRequest;
    private GetUserDetailInformationResult getUserDetailInformationResult;


    public QualityObjectionDetailCLQKFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quality_objection_detail_clqk, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClickBigImageListener) {
            imageListener = (OnClickBigImageListener) context;
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected void initView() {
        listView = (ListView) getView().findViewById(R.id.lv_quality_objection_clqk);
        iv_i = (ImageView) getView().findViewById(R.id.iv_quality_objection_detail_clqk_i);
        iv_i.setOnClickListener(this);
        bigImageView = (PhotoView) getView().findViewById(R.id.iv_quality_objection_detail_clqk_bigimage);
        bigImageView.setOnClickListener(this);
        bigImageView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        request = new QualityObjectionDetailCLQKListRequest();
//        request.userid =  ZTCustomInit.get().getmCache().getmListDetails().AccountId;
        request.userid = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        Log.e("YJH", "initData->QualityObjectionDetailCLQKFragment: "+request.userid );
        request.id = id;
        listResults = new ArrayList<QualityObjectionDetailClqkListResult>();
        adapter = new QualityObjectionClqkAdapter(getActivity(), listResults, this);
        listView.setAdapter(adapter);
        getUserDeatil();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.iv_quality_objection_detail_clqk_i ){
            Intent intent = new Intent(getActivity(), QualityObjectionDetailCLQKSubmitActivity.class);
            intent.putExtra("id", id);
            startActivityForResult(intent, REQUESTCODE);
        }
    }

    @Override
    public void lazyLoad() {
        Log.e("CLQK_lazyLoad", "执行了");
        if (isFristShow) {
            showProgressDialog(getActivity());
            getData();
        }
        if (isRefresh) {
            if (isFristShow) {
                return;
            }
            getData();
        }
    }

    public void getData() {

        AnsynHttpRequest.request(getActivity(), request, ContantValues.GETZLYYCLQKLIST, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                if (isFristShow) {
                    isFristShow = false;
                    dimssDialog();
                }
                if (isRefresh) {
                    isRefresh = false;
                    dimssDialog();
                }
                if (successMessage != null && !successMessage.equals("")) {
                    QualityObjectionDetailClqkResult result = new QualityObjectionDetailClqkResult();
                    result = FastJsonUtils.getPerson(successMessage, QualityObjectionDetailClqkResult.class);
                    if (result != null) {
                        if (result.success) {
                            if (listResults != null) {
                                listResults.clear();
                            }
                            listResults.addAll(result.results);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                if (isFristShow) {
                    isFristShow = false;
                    dimssDialog();
                }
                if (isRefresh) {
                    isRefresh = false;
                    dimssDialog();
                }
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                if (isFristShow) {
                    isFristShow = false;
                    dimssDialog();
                }
                if (isRefresh) {
                    isRefresh = false;
                    dimssDialog();
                }
                ToastUtil.showShort(getActivity(),"暂无数据");

            }
        });
    }


    /**
     * 获取用户信息
     */
    private void getUserDeatil() {
        getUserDetailInformationRequest = new GetUserDetailInformationRequest();
//        getUserDetailInformationRequest.userid =  ZTCustomInit.get().getmCache().getmListDetails().AccountId;
        getUserDetailInformationRequest.userid =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        Log.e("YJH", "getUserDeatil->QualityObjectionDetailCLQKFragment"+  getUserDetailInformationRequest.userid);
        AnsynHttpRequest.request(getActivity(), getUserDetailInformationRequest, ContantValues.GETUSERDETAILINFORMATION, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                if (successMessage != null && !successMessage.equals("")) {
                    getUserDetailInformationResult = FastJsonUtils.getPerson(successMessage, GetUserDetailInformationResult.class);
                    if (getUserDetailInformationResult.success) {
                        String type = getUserDetailInformationResult.type.trim();
                        //还需要判断权限，是否显示 提报按钮
                        if (type.equals("2")) {
                            iv_i.setVisibility(View.VISIBLE);
                        } else {
                            iv_i.setVisibility(View.GONE);
                        }
                    }
                }
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


    /**
     * 提报或者处理后的回掉，为了刷新数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("CLQK_onActivityResult", "执行了");
        //刷新当前页签的列表内容
        isRefresh = true;
        lazyLoad();
    }


    @Override
    public void onImageViewCallBack(String id, String fileId) {
        imageListener.clickImageView(id, fileId);
    }
}
