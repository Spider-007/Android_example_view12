package com.htmitech.htworkflowformpluginnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.htmitech.MyView.EmptyLayout;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.applicationcenter.CallBackApplicationCenter;
import com.htmitech.emportal.ui.applicationcenter.DragAdapter;
import com.htmitech.emportal.ui.applicationcenter.DragGrid;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;

import java.util.ArrayList;

/**
 *工作流表单快捷键入口
 * @author  joe
 * @date 2017-06-30 12:08:05
 */
public class WorkFlowAddCenterSelectActivity extends BaseFragmentActivity implements View.OnClickListener,CallBackApplicationCenter {
    private AppInfo appInfo;
    private String app_id;
    private String todoFlag;
    private AppliationCenterDao mAppliationCenterDao;
    private DragAdapter mGeneralFormSelectAdapter;
    private DragGrid center_gridview;
    private ArrayList<AppInfo> addCenterAppInfos;
    private ImageButton title_left_button;
    private EmptyLayout el_layout;
    private TextView title_name;
    private TextView title_right_text_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appcenterselect);
        initView();
        initData();
    }

    public void initView(){
        center_gridview = (DragGrid) findViewById(R.id.center_gridview);
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        el_layout = (EmptyLayout) findViewById(R.id.el_layout);
        title_name = (TextView) findViewById(R.id.title_name);
        title_right_text_button = (TextView) findViewById(R.id.title_right_text_button);
    }

    public void initData(){
        title_right_text_button.setOnClickListener(this);
        center_gridview.setCallBackApplicationCenter(this);
        title_name.setText("编辑");
        mAppliationCenterDao = new AppliationCenterDao(this);
        app_id = getIntent().getStringExtra("app_id");
        todoFlag = getIntent().getStringExtra("todoFlag");
        title_left_button.setOnClickListener(this);
        addCenterAppInfos = new ArrayList<AppInfo>();
        showDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<AppInfo> appInfos = mAppliationCenterDao.getChildApp(app_id,false);
                for(AppInfo mAppInfo : appInfos){
                    if(mAppInfo.getmAppVersion() == null){
                        continue;
                    }
                    if(mAppInfo.getPlugin_code().equals("com_workflow_plugin_opinions")){
                        addCenterAppInfos.add(mAppInfo);
                    }
                    if(mAppInfo.getComp_code().contains("com_workflow")){
                        if (mAppInfo.getmAppVersion() != null) {
                            for (AppVersionConfig mAppVersionConfig : mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                                String cofingName = mAppVersionConfig.getConfig_code();
                                if (cofingName.equals("com_workflow_plugin_selector_paramter_TodoFlag")) {
                                    if (mAppVersionConfig.getConfig_value().equals(todoFlag)||mAppVersionConfig.getConfig_value().equals("")) {
                                        addCenterAppInfos.add(mAppInfo);
                                    }
                                }
                            }
                        }
                    }

                }
                WorkFlowAddCenterSelectActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mGeneralFormSelectAdapter = new DragAdapter(WorkFlowAddCenterSelectActivity.this,addCenterAppInfos,mAppliationCenterDao);

                        center_gridview.setAdapter(mGeneralFormSelectAdapter);

                        if(addCenterAppInfos.size() == 0){
                            el_layout.showEmpty();
                        }
                        dismissDialog();
                    }
                });
            }
        }).start();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_button:
                setResult(ActivityResultConstant.SAVEOCUS_RESULT_OK, getIntent());





                this.finish();
                break;
            case R.id.title_right_text_button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mAppliationCenterDao.updateChildAppOrder(addCenterAppInfos);
                    }
                }).start();
                center_gridview.refresh();
                title_right_text_button.setVisibility(View.GONE);



                break;
        }
    }

    @Override
    public void onClickItem() {
        title_right_text_button.setVisibility(View.VISIBLE);
    }
}
