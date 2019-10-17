package com.htmitech.ztcustom.zt.chinarailway;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.utils.OAConText;
import com.htmitech.ztcustom.zt.app.PlannedCashRateConfig;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.base.FragOperManager;
import com.htmitech.ztcustom.zt.event.PlanRateDataChangeEvent;
import com.htmitech.ztcustom.zt.fragment.plannedcashratefragment.PlannedRateBarFragment;
import com.htmitech.ztcustom.zt.fragment.plannedcashratefragment.PlannedRateHomeFragment;
import com.htmitech.ztcustom.zt.fragment.plannedcashratefragment.PlannedRateTableFragment;
import com.htmitech.ztcustom.zt.interfaces.OnPlannedRateFragmentCallbackListener;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

/**
 * 计划兑现率
 */
public class PlannedCashRateActivity extends BaseFragmentActivity implements View.OnClickListener, OnPlannedRateFragmentCallbackListener {

    private ImageButton imageButtonBack;
    private ImageView iv_date_cut;
    private ImageView iv_date_add;
    private TextView tvDate;
    private TextView tvSearch;
    private Button btClear;
    private FrameLayout FragmentContanier;
    Calendar c = Calendar.getInstance();
    int year1 = c.get(Calendar.YEAR);
    int month1 = c.get(Calendar.MONTH) + 1;
    int monthNow = month1;
    int yearNow = year1;
    private String year;
    private String month;
    private String projectID;
    private String showMontch = null;
    private FragOperManager manager;

    private String currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_cash_rate);
        initView();
        initData();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        imageButtonBack = (ImageButton) findViewById(R.id.ib_planned_rate_back);
        iv_date_cut = (ImageView) findViewById(R.id.iv_planned_rate_cut);
        iv_date_add = (ImageView) findViewById(R.id.iv_planned_rate_add);
        tvDate = (TextView) findViewById(R.id.tv_planned_rate_date);
        tvSearch = (TextView) findViewById(R.id.tv_planned_rate_search);
        btClear = (Button) findViewById(R.id.bt_planned_rate_clear);
        FragmentContanier = (FrameLayout) findViewById(R.id.fl_planned_rate_fragment_container);
        imageButtonBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        btClear.setOnClickListener(this);
        iv_date_add.setOnClickListener(this);
        iv_date_cut.setOnClickListener(this);
        year = String.valueOf(year1);
        if (month1 < 10) {
            month = "0" + month1;
        } else {
            month = String.valueOf(month1);
        }
        if (month1 != 10 && month1 < 10) {
            if (month.contains("0")) {
                showMontch = month.replace("0", "");
            }
        } else {
            showMontch = month;
        }
        tvDate.setText(year + "年" + showMontch + "月");
    }

    private void initData() {
        manager = new FragOperManager(this, R.id.fl_planned_rate_fragment_container);
        PlannedRateHomeFragment fragmentHome = new PlannedRateHomeFragment();
        Bundle bundleHomeFragment = new Bundle();
        bundleHomeFragment.putString(PlannedCashRateConfig.YEAR, year);
        bundleHomeFragment.putString(PlannedCashRateConfig.MONTH, month);
        bundleHomeFragment.putString(PlannedCashRateConfig.PROJECTID, projectID);
        fragmentHome.setArguments(bundleHomeFragment);
        manager.addFragment(fragmentHome, PlannedCashRateConfig.HOME_FRAGMENT_TAG, false);
        currentFragment = PlannedCashRateConfig.HOME_FRAGMENT_TAG;
        if (currentFragment.equals(PlannedCashRateConfig.HOME_FRAGMENT_TAG)) {
            tvDate.setText(year + "年");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_planned_rate_cut) {
            if (currentFragment.equals(PlannedCashRateConfig.HOME_FRAGMENT_TAG)) {
                c.add(Calendar.YEAR, -1);
                year1 = c.get(Calendar.YEAR);
                year = String.valueOf(year1);
                tvDate.setText(year + "年");
            } else {
                c.add(Calendar.MONTH, -1);
                year1 = c.get(Calendar.YEAR);
                month1 = c.get(Calendar.MONTH) + 1;
                if (month1 < 10) {
                    month = "0" + month1;
                } else {
                    month = String.valueOf(month1);
                }
                year = String.valueOf(year1);

                if (month1 != 10 && month1 < 10) {
                    if (month.contains("0")) {
                        showMontch = month.replace("0", "");
                    }
                } else {
                    showMontch = month;
                }
                tvDate.setText(year + "年" + showMontch + "月");
            }
            getData();

        } else if (v.getId() == R.id.iv_planned_rate_add) {
            if (currentFragment.equals(PlannedCashRateConfig.HOME_FRAGMENT_TAG)) {
                if (year1 >= yearNow) {
                    return;
                }
            }
            if (year1 < yearNow || month1 < monthNow) {
                if (currentFragment.equals(PlannedCashRateConfig.HOME_FRAGMENT_TAG)) {
                    c.add(Calendar.YEAR, 1);
                    year1 = c.get(Calendar.YEAR);
                    year = String.valueOf(year1);
                    if (year1 == yearNow && month1 > monthNow) {
                        month1 = monthNow;
                        if (month1 < 10) {
                            month = "0" + month1;
                        } else {
                            month = String.valueOf(month1);
                        }
                        c.set(yearNow, monthNow, 1);
                    }
                    tvDate.setText(year + "年");
                } else {
                    c.add(Calendar.MONTH, 1);
                    year1 = c.get(Calendar.YEAR);
                    month1 = c.get(Calendar.MONTH) + 1;
                    year = String.valueOf(year1);
                    if (month1 < 10) {
                        month = "0" + month1;
                    } else {
                        month = String.valueOf(month1);
                    }

                    if (month1 != 10 && month1 < 10) {

                        if (month.contains("0")) {
                            showMontch = month.replace("0", "");
                        }
                    } else {
                        showMontch = month;
                    }
                    tvDate.setText(year + "年" + showMontch + "月");
                }
                getData();
            }
        } else if (v.getId() == R.id.tv_planned_rate_search) {
            Intent intent1 = new Intent(this, QualityBookKeyWordsSearchActivity.class);
            intent1.putExtra("builder", 1);
            intent1.putExtra("from", "plannedCashRate");
            intent1.putExtra("title", "计划兑现");
            intent1.putExtra("userid", OAConText.getInstance(this).UserID);
            startActivityForResult(intent1, PlannedCashRateConfig.LISTREQUESTCODE);
        } else if (v.getId() == R.id.ib_planned_rate_back) {
            if (currentFragment.equals(PlannedCashRateConfig.HOME_FRAGMENT_TAG)) {
                finish();
            } else {
                manager.goBack(null, 0);
            }
            if (manager.getFragments() != null
                    && manager.getFragments().size() > 0
                    && manager.getFragments().get(manager.getFragments().size() - 1) != null) {
                currentFragment = manager.getFragments().get(manager.getFragments().size() - 1).getTag();
            }
            if (currentFragment.equals(PlannedCashRateConfig.HOME_FRAGMENT_TAG)) {
                tvDate.setText(year + "年");
            } else {
                if (month1 != 10 && month1 < 10) {

                    if (month.contains("0")) {
                        showMontch = month.replace("0", "");
                    }
                } else {
                    showMontch = month;
                }
                tvDate.setText(year + "年" + showMontch + "月");
            }
        } else if (v.getId() == R.id.bt_planned_rate_clear) {
            projectID = "";
            tvSearch.setText("请输入项目名称");
            getData();
        }

    }

    private void getData() {
        PlanRateDataChangeEvent event = new PlanRateDataChangeEvent();
        event.month = month;
        event.year = year;
        event.projectID = projectID;
        EventBus.getDefault().post(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PlannedCashRateConfig.LISTREQUESTCODE && resultCode == PlannedCashRateConfig.LISTRESULTCODE) {
            if (data != null) {
                String projectName = data.getStringExtra("pntprojectname");
                projectID = data.getStringExtra("id");
                tvSearch.setText(projectName);
                getData();
            }
        }
    }

    @Override
    public void onRequestDataStart() {
        showProgressDialog(this);
    }

    @Override
    public void onRequestDataFinish() {
        dimssDialog();
    }

    @Override
    public void onItemClickListener(String currentFragment, String goToFragemnt, String typeCode, String gccode, String usedircode, String gcName) {
        this.currentFragment = goToFragemnt;
        if (!this.currentFragment.equals(PlannedCashRateConfig.HOME_FRAGMENT_TAG)) {
            if (month1 != 10 && month1 < 10) {
                if (month.contains("0")) {
                    showMontch = month.replace("0", "");
                }
            } else {
                showMontch = month;
            }
            tvDate.setText(year + "年" + showMontch + "月");
        }
        if (goToFragemnt.equals(PlannedCashRateConfig.PZ_DX_TWO_FRAGMENT_TAG)) {
            PlannedRateTableFragment tableFragment = new PlannedRateTableFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            tableFragment.setArguments(bundle);
            manager.addFragment(tableFragment, goToFragemnt, true);
        } else if (goToFragemnt.equals(PlannedCashRateConfig.PZ_DX_THREE_FRAGMENT_TAG)) {
            PlannedRateTableFragment tableFragment = new PlannedRateTableFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            bundle.putString(PlannedCashRateConfig.GCCODE, gccode);
            bundle.putString(PlannedCashRateConfig.GCNAME, gcName);
            tableFragment.setArguments(bundle);
            manager.addFragment(tableFragment, goToFragemnt, true);
        } else if (goToFragemnt.equals(PlannedCashRateConfig.PZ_DX_FOUR_FRAGMENT_TAG)) {
            PlannedRateTableFragment tableFragment = new PlannedRateTableFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            bundle.putString(PlannedCashRateConfig.GCCODE, gccode);
            bundle.putString(PlannedCashRateConfig.GCNAME, gcName);
            bundle.putString(PlannedCashRateConfig.USEDIRCODE, usedircode);
            tableFragment.setArguments(bundle);
            manager.addFragment(tableFragment, goToFragemnt, true);
        } else if (goToFragemnt.equals(PlannedCashRateConfig.DY_PZ_FH_TWO_FRAGMENT_TAG)) {
            PlannedRateBarFragment barFragment = new PlannedRateBarFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            bundle.putString(PlannedCashRateConfig.TITLE, PlannedCashRateConfig.DY_FH_TITLE);
            barFragment.setArguments(bundle);
            manager.addFragment(barFragment, goToFragemnt, true);
        } else if (goToFragemnt.equals(PlannedCashRateConfig.DY_PZ_FH_THREE_FRAGMENT_TAG)) {
            PlannedRateTableFragment tableFragment = new PlannedRateTableFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            bundle.putString(PlannedCashRateConfig.GCCODE, gccode);
            bundle.putString(PlannedCashRateConfig.GCNAME, gcName);
            tableFragment.setArguments(bundle);
            manager.addFragment(tableFragment, goToFragemnt, true);
        } else if (goToFragemnt.equals(PlannedCashRateConfig.DY_PZ_FH_FOUR_FRAGMENT_TAG)) {
            PlannedRateTableFragment tableFragment = new PlannedRateTableFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            bundle.putString(PlannedCashRateConfig.GCCODE, gccode);
            bundle.putString(PlannedCashRateConfig.GCNAME, gcName);
            bundle.putString(PlannedCashRateConfig.USEDIRCODE, usedircode);
            tableFragment.setArguments(bundle);
            manager.addFragment(tableFragment, goToFragemnt, true);
        } else if (goToFragemnt.equals(PlannedCashRateConfig.LJ_PZ_FH_TWO_FRAGMENT_TAG)) {
            PlannedRateBarFragment barFragment = new PlannedRateBarFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            bundle.putString(PlannedCashRateConfig.TITLE, PlannedCashRateConfig.LJ_FH_TITLE);
            barFragment.setArguments(bundle);
            manager.addFragment(barFragment, goToFragemnt, true);
        } else if (goToFragemnt.equals(PlannedCashRateConfig.LJ_PZ_FH_THREE_FRAGMENT_TAG)) {
            PlannedRateTableFragment tableFragment = new PlannedRateTableFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            bundle.putString(PlannedCashRateConfig.GCCODE, gccode);
            bundle.putString(PlannedCashRateConfig.GCNAME, gcName);
            tableFragment.setArguments(bundle);
            manager.addFragment(tableFragment, goToFragemnt, true);
        } else if (goToFragemnt.equals(PlannedCashRateConfig.LJ_PZ_FH_FOUR_FRAGMENT_TAG)) {
            PlannedRateTableFragment tableFragment = new PlannedRateTableFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            bundle.putString(PlannedCashRateConfig.GCCODE, gccode);
            bundle.putString(PlannedCashRateConfig.GCNAME, gcName);
            bundle.putString(PlannedCashRateConfig.USEDIRCODE, usedircode);
            tableFragment.setArguments(bundle);
            manager.addFragment(tableFragment, goToFragemnt, true);
        } else if (goToFragemnt.equals(PlannedCashRateConfig.GC_SJ_FH_TWO_FRAGMENT_TAG)) {
            PlannedRateBarFragment barFragment = new PlannedRateBarFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            bundle.putString(PlannedCashRateConfig.TITLE, "钢厂实际发货情况");
            barFragment.setArguments(bundle);
            manager.addFragment(barFragment, goToFragemnt, true);
        } else if (goToFragemnt.equals(PlannedCashRateConfig.GC_SJ_FH_THREE_FRAGMENT_TAG)) {
            PlannedRateTableFragment tableFragment = new PlannedRateTableFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            bundle.putString(PlannedCashRateConfig.GCCODE, gccode);
            bundle.putString(PlannedCashRateConfig.GCNAME, gcName);
            tableFragment.setArguments(bundle);
            manager.addFragment(tableFragment, goToFragemnt, true);
        } else if (goToFragemnt.equals(PlannedCashRateConfig.GC_SJ_FH_FOUR_FRAGMENT_TAG)) {
            PlannedRateTableFragment tableFragment = new PlannedRateTableFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PlannedCashRateConfig.YEAR, year);
            bundle.putString(PlannedCashRateConfig.MONTH, month);
            bundle.putString(PlannedCashRateConfig.PROJECTID, projectID);
            bundle.putString(PlannedCashRateConfig.TYPECODE, typeCode);
            bundle.putString(PlannedCashRateConfig.GCCODE, gccode);
            bundle.putString(PlannedCashRateConfig.GCNAME, gcName);
            bundle.putString(PlannedCashRateConfig.USEDIRCODE, usedircode);
            tableFragment.setArguments(bundle);
            manager.addFragment(tableFragment, goToFragemnt, true);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
