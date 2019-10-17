package com.htmitech.proxy.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.api.BookInit;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.EmpPortal;
import com.minxing.client.util.ConfigStyleUtil;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXCurrentUser;

import java.util.ArrayList;

/**
 * 切换当前门户
 * <p/>
 * 切换门户需要做的那些操作
 * <p/>
 * 1、将当前选中门存入数据库中，
 * 2、将当前页面发送EvnetBus去主页面 进行刷新页面（相当于重启页面，只要报错当前门户状态）is_default 这个状态 设置为1位启用 0位禁用
 * <p/>
 * 颜色风格，0-自定义，1-银白色，2-蓝色，3-红色
 */
public class PortalSwitchActivity extends BaseFragmentActivity implements View.OnClickListener {
    private ListView lv_portal;
    private LayoutInflater infalter;
    private PortalSwitchAdapter mPortalSwitchAdapter;
    private ArrayList<EmpPortal> mEmpPortalList;
    private ImageView btn_back;
    private AppliationCenterDao appCenterDao;
    private RelativeLayout layout_daiban_titlebar;
    private LinearLayout ll_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_portal);
        initView();
        initData();
    }

    public void initView() {
        lv_portal = (ListView) findViewById(R.id.lv_portal);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        layout_daiban_titlebar = (RelativeLayout) findViewById(R.id.layout_daiban_titlebar);
        ll_pro = (LinearLayout) findViewById(R.id.ll_pro);

    }

    public void initData() {
        infalter = LayoutInflater.from(this);
        btn_back.setOnClickListener(this);
        ll_pro.getBackground().setAlpha(100);
        setPortalLayout();
    }

    public void setPortalLayout() {
        //先模拟数据进行测试
        appCenterDao = new AppliationCenterDao(this);

        mEmpPortalList = new ArrayList<EmpPortal>();

        mEmpPortalList = appCenterDao.getPortalAll();
        mPortalSwitchAdapter = new PortalSwitchAdapter(mEmpPortalList);
        lv_portal.setAdapter(mPortalSwitchAdapter);
        lv_portal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                /**
                 * 将所有的门户都进行初始化
                 */
                for (EmpPortal mEmpPortal : mEmpPortalList) {
                    mEmpPortal.isCheck = false;
                    appCenterDao.switchPortal(mEmpPortal);
                }
                /**
                 * 点击门户进行选中状态
                 */
                ll_pro.setVisibility(View.VISIBLE);
                EmpPortal mEmpPortals = (EmpPortal) parent.getItemAtPosition(position);

                mEmpPortals.isCheck = true;
                appCenterDao.switchPortal(mEmpPortals);

                if (mEmpPortals.mApcUserdefinePortal == null) {
                    BookInit.getInstance().getmApcUserdefinePortal().portal_id = mEmpPortals.portal_id;
                    BookInit.getInstance().getmApcUserdefinePortal().using_apc_style = mEmpPortals.apc_style;
                    BookInit.getInstance().getmApcUserdefinePortal().using_home_style = mEmpPortals.home_style;
                    BookInit.getInstance().getmApcUserdefinePortal().using_font_style = mEmpPortals.font_style;
                    BookInit.getInstance().getmApcUserdefinePortal().using_color_style = mEmpPortals.color_style;
                    BookInit.getInstance().getmApcUserdefinePortal().portal_name = mEmpPortals.portal_name;
//                    BookInit.getInstance().getmApcUserdefinePortal().using_home_style = mEmpPortals.home_style;
                    BookInit.getInstance().getmApcUserdefinePortal().setPortal_id(mEmpPortals.portal_id);
                } else {
                    BookInit.getInstance().setmApcUserdefinePortal(mEmpPortals.mApcUserdefinePortal);
                }
                BookInit.getInstance().setMx_appid(mEmpPortals.getMx_appid() + "");
                MXCurrentUser currentUser = null;
                boolean isHaveCommunity = false;
                try {
                    /**
                     * 切换社区
                     */
                    currentUser = MXAPI.getInstance(PortalSwitchActivity.this).currentUser();

                    if (currentUser != null && currentUser.getNetworks() != null) {
                        for (int i = 0; i < currentUser.getNetworks().size(); i++) {
                            if ((currentUser.getNetworks().get(i).getId() + "").equals(BookInit.getInstance().getMx_appid())) {
                                isHaveCommunity = true;
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //TODO 如果不存在改社区那么将不进行切换社区
              /*  if(!isHaveCommunity){
                    Toast.makeText(PortalSwitchActivity.this, "对应默认社区未配置,门户切换失败！", Toast.LENGTH_LONG).show();
                    return ;
                }
*/
                ConfigStyleUtil.changeTextSize(PortalSwitchActivity.this, new ConfigStyleUtil.FinishPortalSwitch() {
                    @Override
                    public void finishPortalSwitchActivity() {
                        PortalSwitchActivity.this.finish();
                    }
                });

                appCenterDao.saveUserDefinePortal(BookInit.getInstance().getmApcUserdefinePortal());
                BookInit.getInstance().setPortalId(mEmpPortals.portal_id);
                BookInit.getInstance().setPortalName(mEmpPortals.portal_name);
                BookInit.getInstance().setApc_style(mEmpPortals.apc_style);
                BookInit.getInstance().setCorp_id(mEmpPortals.corp_id);
                BookInit.getInstance().getmCallbackMX().updatePortalSizeStyle(BookInit.getInstance().getmApcUserdefinePortal().using_home_style + "", BookInit.getInstance().getmApcUserdefinePortal().using_color_style + "", BookInit.getInstance().getmApcUserdefinePortal().using_font_style + "");
//                BookInit.getInstance().setmApcUserdefinePortal(appCenterDao.getUserDefinePortal());
                BookInit.getInstance().getmApcUserdefinePortal().setGroup_corp_id(mEmpPortals.group_corp_id);


                dismissDialog();
                mPortalSwitchAdapter.notifyDataSetChanged();
//                layout_daiban_titlebar.setBackgroundColor(getResources().getColor(R.color.ht_hred_title));
//                PortalSwitchActivity.this.finish();
//                mLoadingDialog.setValue("正在切换门户");
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
////                        ClassEvent mClassEvent = new ClassEvent();
////                        mClassEvent.msg = "portalSwitch";
////                        EventBus.getDefault().post(mClassEvent);  //发送桌面重启
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ConfigStyleUtil.changeTextSize(PortalSwitchActivity.this);
//                                dismissDialog();
//                                mPortalSwitchAdapter.notifyDataSetChanged();
//                                layout_daiban_titlebar.setBackgroundColor(getResources().getColor(R.color.ht_hred_title));
//                                PortalSwitchActivity.this.finish();
//                            }
//                        });
//                    }
//                }).start();


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                this.finish();
                break;
        }
    }

    public class PortalSwitchAdapter extends BaseAdapter {
        private ArrayList<EmpPortal> mEmpPortalList;

        public PortalSwitchAdapter(ArrayList<EmpPortal> mEmpPortalList) {
            this.mEmpPortalList = mEmpPortalList;
        }

        @Override
        public int getCount() {
            return mEmpPortalList.size();
        }

        @Override
        public Object getItem(int position) {
            return mEmpPortalList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodler mViewHodler = null;
            EmpPortal mEmpPortal = mEmpPortalList.get(position);
            if (convertView == null) {
                mViewHodler = new ViewHodler();
                convertView = infalter.inflate(R.layout.activity_switch_portal_adapter, null);
                mViewHodler.tv_portal_title = (TextView) convertView.findViewById(R.id.tv_portal_title);
                mViewHodler.cb_portal_switch = (TextView) convertView.findViewById(R.id.cb_portal_switch);
                mViewHodler.tv_portal_color = (ImageView) convertView.findViewById(R.id.tv_portal_color);
                convertView.setTag(mViewHodler);
            } else {
                mViewHodler = (ViewHodler) convertView.getTag();
            }
//            mViewHodler.tv_portal_color.setImageResource(com.htmitech.addressbook.R.drawable.pictures_no);


            Glide.with(PortalSwitchActivity.this).load(mEmpPortal.portal_logo).placeholder(R.drawable.pictures_no).error(R.drawable.pictures_no).transform(new GlideRoundTransform(PortalSwitchActivity.this)).into(mViewHodler.tv_portal_color);

            mViewHodler.tv_portal_title.setText("" + mEmpPortal.portal_name);
            mViewHodler.cb_portal_switch.setSelected(mEmpPortal.isCheck);
            mViewHodler.tv_portal_title.setSelected(mEmpPortal.isCheck);
            return convertView;
        }

    }

    public class ViewHodler {
        public TextView tv_portal_title;
        public TextView cb_portal_switch;
        public ImageView tv_portal_color;
    }

}