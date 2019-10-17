package com.htmitech.addressbook;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.adapter.MyFragmentPagerAdapter;
import com.htmitech.api.BookInit;
import com.htmitech.app.widget.CircleView_Check;
import com.htmitech.base.BaseFragment;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.dao.T_UserRelationshipDAO;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.T_UserRelationship;
import com.htmitech.fragment.AddressFragment;
import com.htmitech.fragment.ChooseAddressFragment;
import com.htmitech.fragment.ChooseQuanyuanFragment;
import com.htmitech.fragment.ChooseShowPeopleMessageFragment;
import com.htmitech.fragment.ChooseStructureyFragment;
import com.htmitech.fragment.ChooseSystemFragment;
import com.htmitech.fragment.UserChooseChildFragment;
import com.htmitech.listener.BackHandledInterface;
import com.htmitech.listener.CallBackChoosePeople;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.unit.DensityUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择页面
 *
 * @author Tony
 */
public class ChooseGeneralActivity extends BaseFragmentActivity implements
        CallBackChoosePeople, BackHandledInterface {
    public BaseFragment mBaseFragment;
    private ViewPager mPager;
    private ArrayList<BaseFragment> fragmentList;
    private ImageView image;
    private TextView tvPeopleMessage, tvWork;
    private int currentIndex;
    private TextView userName;
    private ImageView back;
    private UserChooseChildFragment mUserChooseChildFragment;
    private TextView tv_choose_people_message;
    private ImageView iv_yixuanze_logo;
    private ArrayList<SYS_User> userList = new ArrayList<SYS_User>();
    private ArrayList<SYS_Department> departmentList = new ArrayList<SYS_Department>();
    private RelativeLayout rl_buttom;
    private LinearLayout ll_layout;
    private BaseApplication app;
    // 正在执行的动画数量
    private int number = 0;
    // 是否完成清理
    private boolean isClean = false;
    private FrameLayout animation_viewGroup;
    // 动画时间
    private int AnimationDuration = 1000;
    private TextView tv_choose_people_queding;
    private LinearLayout ll_popupLayout;
    private Animation upAnimation, downAnimation;
    private ChooseShowPeopleMessageFragment mDynamicFragment;
    private LinearLayout content;
    private TextView tv_pop_up;
    private String chooseName = "人员";
    private int itemHight;
    private ChooseWayEnum mChooseWayEnum; //部门选择 还是人员选择 还是自由选择
    private LinearLayout ll_view_title;
    private ChooseTreeHierarchy mChooseTreeHierarchy;
    private ProgressBar progress_;
    private ChooseAddressFragment mChooseCommonlyFragment;
    private ChooseSystemBook mChooseSystemAddressBook;
    private String title = "";
    private int ViewPagerNumber = 1;
    private ArrayList<SYS_User> systemUser;
    private boolean isFreeSelectUser;
    private TextView tv_system_people;
    private ChooseSystemFragment mChooseSystemFragment;
    private ImageButton title_right_new_function;
    private List<T_UserRelationship> contactList;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ht_fragment_choose_user_details);
        title = getIntent().getStringExtra("title");
        isFreeSelectUser = getIntent().getBooleanExtra("isFreeSelectUser", false);
        systemUser = (ArrayList<SYS_User>) getIntent().getSerializableExtra("systemUser");
        animation_viewGroup = createAnimLayout();
        app = BaseApplication.getApplication(this);
        InitTextView();
        InitViewPager();
        initTabLineWidth();
        itemHight = DensityUtil.dip2px(this, 50);
        initData();
    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private FrameLayout createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;

    }

    public void initData() {
        if (title == null || title.equals("")) {
            userName.setText("选择页面");
        } else {
            userName.setText(title);
        }
        T_UserRelationshipDAO mT_UserRelationshipDAO = new T_UserRelationshipDAO(
                this);

        try {
            contactList = mT_UserRelationshipDAO.getContactList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tv_pop_up.getBackground().setAlpha(100);
        upAnimation = AnimationUtils.loadAnimation(this,
                R.anim.popshow_anim);


//		//系统选择人员页面传入进来的
//		if(!isFreeSelectUser){
//			ll_view_title.setVisibility(View.GONE);
//			image.setVisibility(View.GONE);
//		}else{
//			ll_view_title.setVisibility(View.VISIBLE);
//			image.setVisibility(View.VISIBLE);
//		}
        mDynamicFragment = new ChooseShowPeopleMessageFragment();
//		mDynamicFragment.setAlpha(70);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.content, mDynamicFragment);
        transaction.commit();
        upAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                iv_yixuanze_logo.setVisibility(View.GONE);
                tv_pop_up.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
//				layout_iv_down.setVisibility(View.VISIBLE);
                tv_pop_up.setVisibility(View.VISIBLE);

            }
        });

        downAnimation = AnimationUtils.loadAnimation(this,
                R.anim.pophidden_anim);


        downAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
//				layout_iv_down.setVisibility(View.GONE);
                tv_pop_up.setVisibility(View.INVISIBLE);
                mDynamicFragment.setAlpha(getResources().getColor(R.color.transparent));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                mDynamicFragment.setAlpha(getResources().getColor(R.color.bantouming));
                iv_yixuanze_logo.setVisibility(View.VISIBLE);
                Animation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
                AnimationSet set = new AnimationSet(true);
                // 设置尺寸变化动画
                Animation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);

                scaleAnimation.setDuration(500);
                alphaAnimation.setDuration(500);
                set.addAnimation(scaleAnimation);
                set.addAnimation(alphaAnimation);
                set.setDuration(500);
                iv_yixuanze_logo.startAnimation(set);
            }
        });

        tv_choose_people_queding.setOnClickListener(new txListener(0));

        if(BookInit.getInstance().getCheckAllUser().size() > 0){
            tv_choose_people_queding.setText("确定");
            tv_choose_people_queding.setEnabled(true);
            tv_choose_people_queding.setTextColor(getResources().getColor(R.color.white));
            tv_choose_people_queding.setBackgroundResource(R.drawable.choose_queding);
            iv_yixuanze_logo.setImageResource(R.drawable.icon_personnel_selected);
            tv_choose_people_message.setText("已选择" + BookInit.getInstance().getCheckAllUser().size() + chooseName);
            userList = BookInit.getInstance().getCheckAllUser();
        }
    }

    /*
     * 初始化标签名
     */
    public void InitTextView() {
        tvPeopleMessage = (TextView) findViewById(R.id.tv_people_message);
        userName = (TextView) findViewById(R.id.tv_user_name);
        tvWork = (TextView) findViewById(R.id.tv_work);
        image = (ImageView) findViewById(R.id.cursor);
        tv_system_people = (TextView) findViewById(R.id.tv_system_people);
        back = (ImageView) findViewById(R.id.back);
        progress_ = (ProgressBar) findViewById(R.id.progress_);
        ll_view_title = (LinearLayout) findViewById(R.id.ll_view_title);
        tv_choose_people_message = (TextView) findViewById(R.id.tv_choose_people_message);
        iv_yixuanze_logo = (ImageView) findViewById(R.id.iv_yixuanze_logo);
        rl_buttom = (RelativeLayout) findViewById(R.id.rl_buttom);
        ll_popupLayout = (LinearLayout) findViewById(R.id.ll_popupLayout);
        ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
        title_right_new_function = (ImageButton) findViewById(R.id.title_right_new_function);
        content = (LinearLayout) findViewById(R.id.content);
        tv_choose_people_queding = (TextView) findViewById(R.id.tv_choose_people_queding);
        tv_pop_up = (TextView) findViewById(R.id.tv_pop_up);
        tv_pop_up.setOnClickListener(new txListener(0));
        iv_yixuanze_logo.setOnClickListener(new txListener(0));
        back.setOnClickListener(new txListener(0));
        title_right_new_function.setOnClickListener(new txListener(0));

    }


    @Override
    public void callBackChoosePeopleMessage(ArrayList<SYS_User> userList, int chooseNumber, Bitmap pothoBitmap, ImageView ivAvatar, TextView default_tv, boolean isImage, int color, int itemHight, boolean isCheck, CheckBox mCheckBox) {
        if (isCheck) {
            if (!isImage) {
                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                mCheckBox.getLocationInWindow(startLocation);
                CircleView_Check mCircleView = (CircleView_Check) LayoutInflater.from(this).inflate(R.layout.ht_fragment_circle, null);
                mCircleView.setText(default_tv.getText().toString());
                mCircleView.setBackgroundColor(color);
                doAnim(null, mCircleView, startLocation);
            } else {
                ImageView mImageView = new ImageView(this);
                mImageView.setImageBitmap(pothoBitmap);
                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                mCheckBox.getLocationInWindow(startLocation);
                doAnim(mImageView, null, startLocation);
            }
        }
        chooseName = "人";
        if (chooseNumber == 0) {
            tv_choose_people_queding.setEnabled(true);
            tv_choose_people_queding.setText("未选择");
            tv_choose_people_queding.setTextColor(getResources().getColor(R.color.black));
            tv_choose_people_queding.setBackgroundResource(R.color.huise);
            iv_yixuanze_logo.setImageResource(R.drawable.icon_personnel_normal);
        } else {
            tv_choose_people_queding.setEnabled(true);
            tv_choose_people_queding.setText("确定");
            tv_choose_people_queding.setTextColor(getResources().getColor(R.color.white));
            tv_choose_people_queding.setBackgroundResource(R.drawable.choose_queding);
            iv_yixuanze_logo.setImageResource(R.drawable.icon_personnel_selected);
        }
        tv_choose_people_message.setText("已选择" + chooseNumber + chooseName);
//		this.itemHight = itemHight;
        this.userList = userList;
    }

    @Override
    public void callBackChooseDepartment(ArrayList<SYS_Department> departmentList, int chooseNumber, Bitmap pothoBitmap, ImageView ivAvatar, int itemHight, boolean isCheck, CheckBox mCheckBox) {
        chooseName = "部门";
        tv_choose_people_message.setText("已选择" + chooseNumber + chooseName);
        if (isCheck) {
            int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
            mCheckBox.getLocationInWindow(startLocation);
            ImageView mImageView = new ImageView(this);
            mImageView.setImageBitmap(pothoBitmap);
            doAnim(mImageView, null, startLocation);
        }
        this.departmentList = departmentList;
//		this.itemHight = itemHight;
        if (chooseNumber == 0) {
            tv_choose_people_queding.setEnabled(true);
            tv_choose_people_queding.setText("未选择");
            tv_choose_people_queding.setTextColor(getResources().getColor(R.color.black));
            tv_choose_people_queding.setBackgroundResource(R.color.huise);
            iv_yixuanze_logo.setImageResource(R.drawable.icon_personnel_normal);
        } else {
            tv_choose_people_queding.setText("确定");
            tv_choose_people_queding.setEnabled(true);
            tv_choose_people_queding.setTextColor(getResources().getColor(R.color.white));
            tv_choose_people_queding.setBackgroundResource(R.drawable.choose_queding);
            iv_yixuanze_logo.setImageResource(R.drawable.icon_personnel_selected);
        }
    }

    @Override
    public void setShutDownAnimation() {
        ll_popupLayout.setVisibility(View.GONE); //隐藏布局
        ll_popupLayout.startAnimation(downAnimation);
    }

    @Override
    public void callBackDeletePeople(int number) {
        tv_choose_people_message.setText("已选择" + number + chooseName);
        tv_choose_people_queding.setEnabled(true);
        if (number == 0) {
            ll_popupLayout.setVisibility(View.GONE); //隐藏布局
            iv_yixuanze_logo.setVisibility(View.VISIBLE);
            iv_yixuanze_logo.setImageResource(R.drawable.icon_personnel_normal);
            tv_choose_people_queding.setText("未选择");
            tv_choose_people_queding.setEnabled(true);
            tv_choose_people_queding.setTextColor(getResources().getColor(R.color.black));
            tv_choose_people_queding.setBackgroundResource(R.color.huise);
        }
        if (number <= 6) {
            LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            content.setLayoutParams(sp_params);
        }
    }

    /**
     * 当常用联系人和层级以及树形一同存在的情况下 进度条要统一
     */
    @Override
    public void callBackProGone() {
        progress_.setVisibility(View.GONE);
//		mChooseCommonlyFragment.setProGone();
//		mChooseSystemFragment.setProGone();
    }

    public class txListener implements OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v.getId() == R.id.back) {
                if (null != mUserChooseChildFragment) {
                    if (index != ViewPagerNumber) {
                        if (!mUserChooseChildFragment.onBackPressed()) {
                            ChooseGeneralActivity.this.finish();
                            if (null != app.getCheckALlUser())
                                app.getCheckALlUser().clear();
                            if (null != app.getCheckSYSDepartment())
                                app.getCheckSYSDepartment().clear();
                        }
                    } else {
                        ChooseGeneralActivity.this.finish();
                        if (null != app.getCheckALlUser())
                            app.getCheckALlUser().clear();
                        if (null != app.getCheckSYSDepartment())
                            app.getCheckSYSDepartment().clear();
                    }

                } else {
                    ChooseGeneralActivity.this.finish();
                    if (null != app.getCheckALlUser())
                        app.getCheckALlUser().clear();
                    if (null != app.getCheckSYSDepartment())
                        app.getCheckSYSDepartment().clear();

                }
            } else if (v.getId() == R.id.iv_yixuanze_logo) {
                if (!ll_popupLayout.isShown()) {
                    if (userList.size() != 0 || departmentList.size() != 0) {
                        if (userList.size() >= 6 || departmentList.size() >= 6) {
                            LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT, itemHight * 7);
                            content.setLayoutParams(sp_params);
                        } else {
                            LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            content.setLayoutParams(sp_params);
                        }
                        mDynamicFragment.setAdapter(departmentList, userList);
                        ll_popupLayout.setVisibility(View.VISIBLE);
                        ll_popupLayout.startAnimation(upAnimation);
                    }
                }
            } else if (v.getId() == R.id.tv_pop_up) {
                setShutDownAnimation();
            } else if (v.getId() == R.id.tv_choose_people_queding) {
                StringBuffer sb = new StringBuffer();
                for (SYS_Department mdepartment : departmentList) {
                    sb.append(mdepartment.getFullName() + ",");
                }
                for (SYS_User user : userList) {
                    sb.append(user.getFullName() + ",");
                }

                if (BookInit.getInstance().getCallCheckAllUserListener() != null) {
                    BookInit.getInstance().getCallCheckAllUserListener().checkAll(new ArrayList<SYS_User>(userList), new ArrayList<SYS_Department>(departmentList));
                    userList.clear();
                    departmentList.clear();
                    ChooseGeneralActivity.this.finish();
                }
            } else if (R.id.title_right_new_function == v.getId()) {
                title_right_new_function.setVisibility(View.GONE);
                mUserChooseChildFragment.onAKeyBackPressed();
            } else {
                mPager.setCurrentItem(index);
            }
        }
    }

    /*
     * 初始化ViewPager
     */
    public void InitViewPager() {
        BaseApplication.getApplication(this).setCheckAllUser(BookInit.getInstance().getCheckAllUser());
        mChooseTreeHierarchy = BookInit.getInstance().getmChooseTreeHierarchy();
        mChooseWayEnum = BookInit.getInstance().getChooseWayEnum();
        mChooseSystemAddressBook = BookInit.getInstance().getmChooseSystemAddressBook();
        mPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentList = new ArrayList<BaseFragment>();
        /**
         * 系统选择
         */
        mChooseSystemFragment = new ChooseSystemFragment();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("systemUser", systemUser);
        mChooseSystemFragment.setArguments(mBundle);

        /**
         * 选择人员列表
         */
        mChooseCommonlyFragment = new ChooseAddressFragment();
        /**
         * 树形
         */
        ChooseStructureyFragment mChooseStructureyFragment = new ChooseStructureyFragment();
        /**
         * 全部人员
         */
        ChooseQuanyuanFragment mUserDetailsChildFragment = new ChooseQuanyuanFragment();

        /**
         * 层级
         */
        mUserChooseChildFragment = new UserChooseChildFragment();
        mBundle = new Bundle();
        mBundle.putSerializable("ChooseWayEnum", ChooseWayEnum.FREECHOOSE);
        mUserChooseChildFragment.setArguments(mBundle);


        switch (mChooseWayEnum) {
            case DEPARTMENTCHOOSE:
                ll_view_title.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                break;
            case PEOPLECHOOSE:
                ll_view_title.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                break;
            case FREECHOOSE:
                ll_view_title.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        /**
         * 只有是本地和选择人员的情况下才会出现常用联系人这个页面 其他都不进行展示
         */
        switch (mChooseSystemAddressBook){
            case ADDRESSBOOK:
                tvWork.setTextColor(getResources().getColor(R.color.ht_hred_title));
                ViewPagerNumber = 2;


                switch (mChooseTreeHierarchy){
                    case TREE:
                        fragmentList.add(mChooseStructureyFragment);
                        break;
                    case HIERARCHY:
                        fragmentList.add(mUserChooseChildFragment);
                        break;
                }


                switch (mChooseWayEnum){
                    case DEPARTMENTCHOOSE:
                        break;
                    case PEOPLECHOOSE:
                        fragmentList.add(mChooseCommonlyFragment);
                        break;
                    case FREECHOOSE:
                        break;
                    default:
                        break;
                }
                break;
            case SYSTEM:
                /**
                 * 从系统过来 也就是第三方网络数据 进行组装拼接
                 */
                /**
                 * 从系统过来 也就是第三方网络数据 进行组装拼接
                 */
                if(systemUser != null && systemUser.size() != 0){
                    fragmentList.add(mChooseSystemFragment);
                    tv_system_people.setTextColor(getResources().getColor(R.color.ht_hred_title));
                    tv_system_people.setVisibility(View.VISIBLE);
                    ViewPagerNumber = 3;
                }else{
                    tv_system_people.setVisibility(View.GONE);
                    tvWork.setTextColor(getResources().getColor(R.color.ht_hred_title));
                    ViewPagerNumber = 2;
                    mChooseSystemAddressBook = ChooseSystemBook.ADDRESSBOOK;
                }
                if(isFreeSelectUser){
//                    tv_system_people.setTextColor(getResources().getColor(R.color.ht_hred_title));
//                    tv_system_people.setVisibility(View.VISIBLE);


                    switch (mChooseTreeHierarchy){
                        case TREE:
                            fragmentList.add(mChooseStructureyFragment);
                            break;
                        case HIERARCHY:
                            fragmentList.add(mUserChooseChildFragment);
                            break;
                    }

                    switch (mChooseWayEnum){
                        case DEPARTMENTCHOOSE:
                            break;
                        case PEOPLECHOOSE:
                            fragmentList.add(mChooseCommonlyFragment);
                            break;
                        case FREECHOOSE:
                            break;
                        default:
                            break;
                    }
                }else{
                    progress_.setVisibility(View.GONE);
                    ll_view_title.setVisibility(View.GONE);
                    image.setVisibility(View.GONE);
                }
                break;
        }


        ViewPagerNumber = fragmentList.size();
        // 给ViewPager设置适配器
        mPager.setAdapter(new MyFragmentPagerAdapter(
                getSupportFragmentManager(), fragmentList));
        mPager.setCurrentItem(0);// 设置当前显示标签页为第一页
        mPager.setOffscreenPageLimit(ViewPagerNumber);
        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset,
                                       int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) image
                        .getLayoutParams();
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1;
                 * 1->0
                 */

                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / ViewPagerNumber) + currentIndex
                            * (screenWidth / ViewPagerNumber));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / ViewPagerNumber) + currentIndex
                            * (screenWidth / ViewPagerNumber));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / ViewPagerNumber) + currentIndex
                            * (screenWidth / ViewPagerNumber));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / ViewPagerNumber) + currentIndex
                            * (screenWidth / ViewPagerNumber));
                }
                image.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();

                switch (mChooseSystemAddressBook) {
                    case ADDRESSBOOK:
                        switch (position) {

                            case 0:
                                tvWork.setTextColor(getResources().getColor(R.color.ht_hred_title));
                                break;
                            case 1:
                                tvPeopleMessage.setTextColor(getResources().getColor(R.color.ht_hred_title));
                                break;
                        }
                        break;
                    case SYSTEM:

                        switch (position) {

                            case 0:
                                tv_system_people.setTextColor(getResources().getColor(R.color.ht_hred_title));
                                break;
                            case 2:
                                tvPeopleMessage.setTextColor(getResources().getColor(R.color.ht_hred_title));
                                break;
                            case 1:
                                tvWork.setTextColor(getResources().getColor(R.color.ht_hred_title));
                                break;
                        }

                        break;
                }

                currentIndex = position;
            }
        });// 页面变化时的监听器

        tv_system_people.setOnClickListener(new txListener(0));
        tvWork.setOnClickListener(new txListener(ViewPagerNumber == 2 ? 0 : 1));
        tvPeopleMessage.setOnClickListener(new txListener(ViewPagerNumber == 2 ? 1 : 2));
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        tvPeopleMessage.setTextColor(Color.BLACK);
        tvWork.setTextColor(Color.BLACK);
        tv_system_people.setTextColor(Color.BLACK);
    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) image
                .getLayoutParams();
        lp.width = screenWidth / ViewPagerNumber;
        image.setLayoutParams(lp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    AddressFragment mAddressFragment;

    /**
     * 初始化显示内容
     **/
    private void initContent() {
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        // TODO Auto-generated method stub
        mBaseFragment = selectedFragment;
    }

    private void doAnim(ImageView imageView, CircleView_Check mCircleView, int[] start_location) {
        if (!isClean) {
            setAnim(imageView, mCircleView, start_location);
        } else {
            try {
                animation_viewGroup.removeAllViews();
                isClean = false;
                setAnim(imageView, mCircleView, start_location);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isClean = true;
            }
        }
    }

    /**
     * 动画效果设置
     *
     * @param start_location 起始位置
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setAnim(ImageView iview, CircleView_Check mCircleView, int[] start_location) {

        Animation mScaleAnimation = new ScaleAnimation(1.5f, 0.0f, 1.5f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF,
                0.1f);
        mScaleAnimation.setDuration(AnimationDuration);
        mScaleAnimation.setFillAfter(true);
        View view = null;
        if (iview != null) {
            view = addViewToAnimLayout(animation_viewGroup, iview,
                    start_location);
        } else {
            view = addViewToAnimLayout(animation_viewGroup, mCircleView,
                    start_location);
        }
        view.setAlpha(0.6f);

        int[] end_location = new int[2];
        iv_yixuanze_logo.getLocationInWindow(end_location);// shopCart是那个购物车
        // if (daohangGridView.getCount() % 4 == 2) {
        // daohangGridView.getChildAt(2)
        // .getLocationInWindow(end_location);
        // }
        int endX = 0 - start_location[0] + 40;// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标

        Animation mTranslateAnimation = new TranslateAnimation(0, endX, 0, endY);
        Animation mRotateAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setDuration(AnimationDuration);
        mTranslateAnimation.setDuration(AnimationDuration);
        AnimationSet mAnimationSet = new AnimationSet(true);

        mAnimationSet.setFillAfter(true);
        mAnimationSet.addAnimation(mRotateAnimation);
        mAnimationSet.addAnimation(mScaleAnimation);
        mAnimationSet.addAnimation(mTranslateAnimation);

        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                number++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub

                number--;
                if (number == 0) {
                    isClean = true;
                    myHandler.sendEmptyMessage(0);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

        });
        view.startAnimation(mAnimationSet);

    }

    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    // 用来清除动画后留下的垃圾
                    try {
                        animation_viewGroup.removeAllViews();
                    } catch (Exception e) {

                    }

                    isClean = false;

                    break;
                default:
                    break;
            }
        }
    };

    /**
     * @param vg       动画运行的层 这里是frameLayout
     * @param view     要运行动画的View
     * @param location 动画的起始位置
     * @return
     * @deprecated 将要执行动画的view 添加到动画层
     */
    private View addViewToAnimLayout(ViewGroup vg, View view, int[] location) {
        int x = location[0];
        int y = location[1];
        vg.addView(view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(dip2px(this,
                90), dip2px(this, 90));
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);

        return view;
    }

    /**
     * dip，dp转化成px 用来处理不同分辨路的屏幕
     *
     * @param context
     * @param dpValue
     * @return
     */
    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 内存过低时及时处理动画产生的未处理冗余
     */
    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        isClean = true;
        try {
            animation_viewGroup.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isClean = false;
        super.onLowMemory();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (null != app.getCheckALlUser())
            app.getCheckALlUser().clear();
        if (null != app.getCheckSYSDepartment())
            app.getCheckSYSDepartment().clear();
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BookInit.getInstance().setFlag(true);
    }

    public void setVisible() {
        title_right_new_function.setVisibility(View.VISIBLE);
    }

    public void setGone() {
        title_right_new_function.setVisibility(View.GONE);
    }

}
