package com.htmitech.htexceptionmanage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.htmitech.MyView.EmptyLayout;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.applicationcenter.DragAdapter;
import com.htmitech.emportal.ui.applicationcenter.DragGrid;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;

import java.util.ArrayList;

/**
 *x消息提醒快捷键入口
 * @author  joe
 * @date 2017-9-25 09:44:13
 */
public class ManageExceptionAddCenterSelectActivity extends BaseFragmentActivity implements View.OnClickListener {
    private AppInfo appInfo;
    private String app_id;
    private String todoFlag;
    private AppliationCenterDao mAppliationCenterDao;
    private DragAdapter mGeneralFormSelectAdapter;
    private GridView center_gridview;
    private ArrayList<AppInfo> addCenterAppInfos;
    private ImageButton title_left_button;
    private EmptyLayout el_layout;
    private TextView title_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appcenterselect);
        initView();
        initData();
    }

    public void initView(){
        center_gridview = (GridView) findViewById(R.id.center_gridview);
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        el_layout = (EmptyLayout) findViewById(R.id.el_layout);
        title_name = (TextView) findViewById(R.id.title_name);
    }

    public void initData(){
        title_name.setText("编辑");
        mAppliationCenterDao = new AppliationCenterDao(this);
        app_id = getIntent().getStringExtra("app_id");
        todoFlag = getIntent().getStringExtra("todoFlag");
        ArrayList<AppInfo> appInfos = mAppliationCenterDao.getChildApp(app_id,false);
        title_left_button.setOnClickListener(this);
        addCenterAppInfos = new ArrayList<AppInfo>();
        for(AppInfo mAppInfo : appInfos){
            if(mAppInfo.getmAppVersion() == null){
                continue;
            }
           if(mAppInfo.getComp_code().contains("com_alert")){
               addCenterAppInfos.add(mAppInfo);
            }
        }
        mGeneralFormSelectAdapter = new DragAdapter(this,addCenterAppInfos,mAppliationCenterDao);

        center_gridview.setAdapter(mGeneralFormSelectAdapter);

        if(addCenterAppInfos.size() == 0){
            el_layout.showEmpty();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_button:
                setResult(ActivityResultConstant.SAVEOCUS_RESULT_OK, getIntent());
                this.finish();
                break;
        }
    }

}
