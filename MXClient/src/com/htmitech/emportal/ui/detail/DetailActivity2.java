package com.htmitech.emportal.ui.detail;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.SlidingBackAcitivity;
import com.htmitech.emportal.entity.DoActionResultInfo;
import com.htmitech.emportal.entity.DocInfoParameters;
import com.htmitech.emportal.entity.DocResultInfo;
import com.htmitech.emportal.entity.EditFieldList;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.detail.AdapterFragmentForCollection.ChildType;
import com.htmitech.emportal.ui.detail.model.DocInfoModel;
import com.htmitech.emportal.ui.widget.LoadingView;
import com.htmitech.emportal.ui.widget.MainViewPager;
import com.htmitech.emportal.ui.widget.NewTopTabIndicator;
import com.htmitech.emportal.ui.widget.ToastInfo;

/***
 * @author tenggang 待办 已办 详情页面
 */
public class DetailActivity2 extends SlidingBackAcitivity implements
        OnClickListener, IBaseCallback {

    public static final String TEXT_PAGE = "text_page";
    public static final String FORM_PAGE = "form_page";
    private MainViewPager mViewPager_mycollection;
    private AdapterFragmentForCollection mAdapter;
    private NewTopTabIndicator mMyTopTabIndicator;
    private DocInfoModel mDocInfoModel;
    public DocResultInfo mDocResultInfo;
    private LoadingView mLoadingView = null;

    public int curItem = 0;
    public String currentPage = "";
    public String apiUrl = "";

    public static boolean currentActivity = false;
    public String mDocAttachmentID = "";

    //private String mDocAttachmentID = null;

    String docType = null;
    String itemTemp[] = null;

    private DoActionResultInfo mDoActionResultInfo;

    protected int getLayoutById() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onDestroy() {
        currentActivity = false;
        super.onDestroy();
    }

    /**
     * 初始化UI
     */
    //该方法在 onCreate 的时候调用
    protected void initView() {
        currentActivity = true;

        EditFieldList mustFieldList = EditFieldList.getInstance();
        mustFieldList.Clear();
        View titleBar = findViewById(R.id.layout_detail_titlebar);                //构件titlebar
        titleBar.findViewById(R.id.imgview_titlebar_back).setOnClickListener(
                this);

        mLoadingView = (LoadingView) findViewById(R.id.loadingview_detail);    //构建 加载视图


        Intent intent = getIntent();
        apiUrl = intent.getStringExtra("api_url");

        String item[] = apiUrl.split("[|]");
    /*	for (int i = 0; i < item.length ; i++) {
			Log.d("Detail2", item[i]);
		}*/

        String docTitle = intent.getStringExtra("doc_title");
        ((TextView) titleBar.findViewById(R.id.textview_titlebar_title))
                .setText(docTitle);

        showLoadingView();
        mDocInfoModel = new DocInfoModel(this);
        DocInfoParameters mDocInfoParameters = new DocInfoParameters();

        int i = 0;

        if (item[0].substring(0, 2).equals("aa") || item[0].substring(0, 2).equals("bb")) {
            currentPage = FORM_PAGE;
        } else {
            i += 3;
            currentPage = TEXT_PAGE;
        }

        // 发起网络请求，获取详细
        mDocInfoParameters.context = OAConText.getInstance(HtmitechApplication.instance());
        if (i == 3) {
            mDocInfoParameters.context.UserID = item[0 + i];
        } else {
            mDocInfoParameters.context.UserID = item[0].substring(2);
        }

        mDocInfoParameters.context.OA_UserId = item[1 + i];
        mDocInfoParameters.context.OA_UserName = item[2 + i];
        //mDocInfoParameters.context.OA_UnitId = item.length > 6 ?  item[9 + i] : "";
        mDocInfoParameters.context.OA_UnitId = "";

        mDocInfoParameters.DocId = item[3 + i];
        mDocInfoParameters.DocType = item[5 + i];
        docType = item[5 + i];
        mDocInfoParameters.Kind = item[4 + i];
        mDocInfoParameters.app_id = item[6 + i];
        mDocInfoModel.getDataFromServerByType(DocInfoModel.TYPE_GET_DETAILTASK,
                mDocInfoParameters);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgview_titlebar_back:
                exit();
                break;
        }
    }

    /**
     * 退出
     **/
    public void exit() {
        finish();
    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {

        if (result == null) {
            Toast.makeText(this, "流程已到下一个节点", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestTypeId == DocInfoModel.TYPE_GET_DETAILTASK) {
            if (result != null && result instanceof DocResultInfo) {
                mDocResultInfo = (DocResultInfo) result;
                mDocResultInfo.getResult().setDocType(docType);
                mViewPager_mycollection = (MainViewPager) findViewById(R.id.viewPager_qarank);
                FragmentManager fm = getSupportFragmentManager();
                mViewPager_mycollection.setOffscreenPageLimit(4);
                mMyTopTabIndicator = (NewTopTabIndicator) this
                        .findViewById(R.id.topTabIndicator_detail);

                ArrayList<ChildType> list = new ArrayList<AdapterFragmentForCollection.ChildType>();
                List<String> listStr = new ArrayList<String>();
                if (currentPage == FORM_PAGE) {
                    listStr.add("表单");
                    list.add(ChildType.TAB_FORM);
                } else if ((mDocAttachmentID = mDocResultInfo.getResult().getDocAttachmentID()) != null
                        && mDocResultInfo.getResult().getDocAttachmentID()
                        .length() > 0) {

                    listStr.add("正文");
                    list.add(ChildType.TAB_TEXT);
                }

                String[] arrayTopTabIndicator = new String[listStr.size()];
                listStr.toArray(arrayTopTabIndicator);
                mMyTopTabIndicator.setCommonData2(mViewPager_mycollection,
                        arrayTopTabIndicator, R.color.color_title,
                        R.color.color_ff888888);
                mAdapter = new AdapterFragmentForCollection(HtmitechApplication
                        .instance().getApplicationContext(), fm, list, mDocResultInfo.getResult().getDocID(), mDocResultInfo.getResult().TabItems, null);
                mViewPager_mycollection.setAdapter(mAdapter);
            }

        }

        hideLoadingView();
    }

    private void hideLoadingView() {
        mLoadingView.setVisibility(View.GONE);
        mLoadingView.stopLoading();
    }

    private void showLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.startLoading();
    }

    public void onFail(int taskType, int statusCode, String errorMsg,
                       Object result) {
        if (result != null) {

            if (taskType == DocInfoModel.TYPE_GET_DETAILTASK) {
                mDocResultInfo = new DocResultInfo();
                try {
                    mDocResultInfo.parseJson(result.toString());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (mDocResultInfo.getResult() != null) {
                    Toast.makeText(DetailActivity2.this, mDoActionResultInfo.getResult().getResultInfo()
                                    + "("
                                    + mDoActionResultInfo.getResult().getResultCode()
                                    + ")",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(DetailActivity2.this, "获取详情失败！",
                            Toast.LENGTH_SHORT).show();
            } else if (taskType == DocInfoModel.TYPE_DOACTION_TASK) {
                mDoActionResultInfo = new DoActionResultInfo();
                try {
                    mDoActionResultInfo.parseJson(result.toString());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (mDoActionResultInfo.getResult() != null) {
                    Toast.makeText(DetailActivity2.this, mDoActionResultInfo.getResult().getResultInfo()
                                    + "("
                                    + mDoActionResultInfo.getResult().getResultCode()
                                    + ")",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(DetailActivity2.this, "操作失败！",
                            Toast.LENGTH_SHORT).show();
            } else {
                ToastInfo toast = ToastInfo.getInstance(this);
                toast.setView(getLayoutInflater(), R.drawable.prompt_error,
                        result.toString());
                toast.show(Toast.LENGTH_SHORT);
            }

        }
        hideLoadingView();
    }

}
