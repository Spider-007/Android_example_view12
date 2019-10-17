package com.htmitech.ztcustom.zt.chinarailway;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.AdvAdapter;
import com.htmitech.ztcustom.zt.adapter.HomePageGridAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.dialog.AlertDialog;
import com.htmitech.ztcustom.zt.domain.longin.ListDetails;
import com.htmitech.ztcustom.zt.domain.menu.GetAppMenu;
import com.htmitech.ztcustom.zt.domain.menu.ListNodes;
import com.htmitech.ztcustom.zt.enums.IntentEnum;
import com.htmitech.ztcustom.zt.util.ZTActivityUnit;
import com.htmitech.ztcustom.zt.view.CustomViewpager;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXCurrentUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 主页面
 *
 * @author Tony
 */
public class HomeActivity extends BaseFragmentActivity implements
        OnItemClickListener {
    private CustomViewpager mCustomViewpager;
    private ArrayList<BinnerBitmapMessage> arrBitmap;
    private ArrayList<BinnerBitmapMessage> magnetBinnerBitmapMessage;
    private ImageView[] imageViews = null;
    private ImageView imageView;
    private boolean isContinue = true;
    private AtomicInteger what = new AtomicInteger(0);
    private GridView fragmentHomeGv;
    private TextView binnername;
    private ImageView functionButton;
    private HomePageGridAdapter mHomePageGridAdapter;
//    // 下拉图
//    private SystemMainTopRightPopMenu functionPopMenu;

    public class BinnerBitmapMessage {
        public Bitmap mBitmap;
        public String appid;
        public String Caption;
        public int FavDisOrder;
        public ListNodes mListNodes;
        /**
         * 栏目是否选中
         */
        public Integer selected;

        public BinnerBitmapMessage(Bitmap mBitmap, String appid,
                                   String Caption, String FavDisOrder, ListNodes mListNodes) {
            this.mBitmap = mBitmap;
            this.appid = appid;
            this.Caption = Caption;
            this.FavDisOrder = Integer.parseInt(FavDisOrder);
            this.mListNodes = mListNodes;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zt_activity_home_);
        initView();
        initData();
    }

    public void initView() {
//        findViewById(R.id.btn_home_person).setOnClickListener(
//                new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((ClientTabActivity) getParent()).getResideMenu()
//                                .openMenu(ResideMenu.DIRECTION_LEFT);
//                    }
//                });
        functionButton = (ImageView) findViewById(R.id.imageview_home_more);
//        functionPopMenu = new SystemMainTopRightPopMenu(this);
//        functionPopMenu.setForTodo(); // 设计下拉菜单项
//        functionButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!functionPopMenu.isShowing()) {
//                    functionPopMenu.showAsDropDown(v);
//                }
//            }
//        });
        MXCurrentUser currentUser = MXAPI.getInstance(this).currentUser();
        if (currentUser != null) {
            /*
             * ((TextView) this.findViewById(R.id.text_home_title))
			 * .setText(currentUser.getNetworkName() +
			 * this.getString(R.string.app_name));
			 */
            ((TextView) this.findViewById(R.id.text_home_title))
                    .setText("钢轨全寿命平台");
        }
        mCustomViewpager = (CustomViewpager) findViewById(R.id.vp);
        fragmentHomeGv = (GridView) findViewById(R.id.fragment_home_gv);

        fragmentHomeGv.setOnItemClickListener(this);
        binnername = (TextView) findViewById(R.id.binnername);
    }

    private String upName;
    private ListNodes mListNodes;
    private ListDetails mListDetails;

    /**
     * GRIDVIEW对应的ITEM点击监听接口
     */
    @Override
    public void onItemClick(AdapterView<?> parent, final View view,
                            final int position, long id) {
        // 如果点击的时候，之前动画还没结束，那么就让点击事件无效
//        switch (parent.getId()) {
//            case R.id.fragment_home_gv:
//                BinnerBitmapMessage channel = ((HomePageGridAdapter) parent
//                        .getAdapter()).getItem(position);// 获取点击的频道内容
//                mListNodes = channel.mListNodes;
//                app.getmCache().setmListDetails(mListNodes.BusinessTypeId);
//                mListDetails = app.getmCache().getmListDetails();
//                if (mListDetails == null) {
//                    return;
//                }
//                String status = mListDetails.AccountStatus;
//                String BusinessTypeName = mListDetails.BusinessTypeName;
//                String title = "";
//                if (status.equals("2")) {
//                    title = "激活";
//                } else if (status.equals("3")) {
//                    title = "验证";
//                } else {
//                    Class<? extends Activity> c = IntentEnum.getActivity(channel.appid);
//                    if (channel.appid != null && c != null) {
//                        ZTActivityUnit.switchTo(HomeActivity.this, c, null);
//                    }
//                    return;
//                }
//                upName = mListDetails.BusinessTypeName + "（未激活）";
//                new AlertDialog(HomeActivity.this).builder().setMsg("请" + title + BusinessTypeName)
//                        .setTitle(title).setCancelable(false)
//                        .setNegativeButton("", new OnClickListener() {
//
//                            @Override
//                            public void onClick(View arg0) {
//                                // TODO Auto-generated method stub
//
//                            }
//                        }).setPositiveButton("", new OnClickListener() {
//
//                    @Override
//                    public void onClick(View arg0) {
//                        // TODO Auto-generated method stub
//                        Map<String, Object> params = new HashMap<String, Object>();
//                        params.put("titleName", upName);
//                        params.put("ListDetails", mListDetails);
//                        ZTActivityUnit.switchTo(HomeActivity.this, ChilDaccountYZActivity.class, params);
//                    }
//                }).show();
//                break;
//            default:
//                break;
        }
//    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 0:
                    initViewPager();
                    break;
                case 1:
//                    mHomePageGridAdapter = new HomePageGridAdapter(
//                            HomeActivity.this, magnetBinnerBitmapMessage, app);
                    fragmentHomeGv.setAdapter(mHomePageGridAdapter);
                    break;
            }
            super.handleMessage(msg);
        }

    };

    public void initData() {
        arrBitmap = new ArrayList<BinnerBitmapMessage>();
        magnetBinnerBitmapMessage = new ArrayList<BinnerBitmapMessage>();
        // 初始化广告栏
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetAppMenu mGetAppMenu = ZTCustomInit.get().getmCache().getmGetAppMenu();
                for (ListNodes mListNodes : mGetAppMenu.ListNode) {
                    if (mListNodes.mySystem != null
                            && mListNodes.mySystem.IsFav.equals("")
                            && mListNodes.mySystem.Type == 1) {
                        arrBitmap.add(new BinnerBitmapMessage(BitmapLoader
                                .loadBitmap(mListNodes.ItemImg1),
                                mListNodes.mySystem.appID, mListNodes.Caption,
                                mListNodes.mySystem.FavDisOrder, mListNodes));
                    }
                }
                Message msg = handler.obtainMessage();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        }).start();
        // 初始化下面GridView
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetAppMenu mGetAppMenu = ZTCustomInit.get().getmCache().getmGetAppMenu();
                for (ListNodes mListNodes : mGetAppMenu.ListNode) {
                    if (mListNodes.mySystem != null
                            && mListNodes.mySystem.IsFav.equals("")
                            && mListNodes.mySystem.Type == 2) {
                        magnetBinnerBitmapMessage.add(new BinnerBitmapMessage(
                                BitmapLoader.loadBitmap(mListNodes.ItemImg1),
                                mListNodes.mySystem.appID, mListNodes.Caption,
                                mListNodes.mySystem.FavDisOrder, mListNodes));
                    }
                }
                sort();
                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }).start();
    }

    // 根据FavDisOrder 进行排序
    public void sort() {
        Comparator comp = new Comparator() {
            public int compare(Object o1, Object o2) {
                BinnerBitmapMessage p1 = (BinnerBitmapMessage) o1;
                BinnerBitmapMessage p2 = (BinnerBitmapMessage) o2;
                if (p1.FavDisOrder < p2.FavDisOrder)
                    return -1;
                else if (p1.FavDisOrder == p2.FavDisOrder)
                    return 0;
                else if (p1.FavDisOrder > p2.FavDisOrder)
                    return 1;
                return 0;
            }
        };
        Collections.sort(magnetBinnerBitmapMessage, comp);
    }

    public class BinnerBitmapImageView {
        public ImageView mImageView;
        public String appid;
        public String Caption;
        public int FavDisOrder;
        public ListNodes mListNodes;

        public BinnerBitmapImageView(ImageView mBitmap, String appid,
                                     String Caption, String FavDisOrder, ListNodes mListNodes) {
            this.mImageView = mBitmap;
            this.appid = appid;
            this.Caption = Caption;
            this.FavDisOrder = Integer.parseInt(FavDisOrder);
            this.mListNodes = mListNodes;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViewPager() {
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        ProgressBar viewProgress = (ProgressBar) findViewById(R.id.viewProgress);
        viewProgress.setVisibility(View.GONE);
        List<BinnerBitmapImageView> advPics = new ArrayList<BinnerBitmapImageView>();
        for (BinnerBitmapMessage bit : arrBitmap) {
            ImageView img1 = new ImageView(this);
            img1.setScaleType(ScaleType.FIT_XY);
            img1.setImageBitmap(bit.mBitmap);
            img1.setTag(bit.appid);// 通过tag来判断点击哪个
            advPics.add(new BinnerBitmapImageView(img1, bit.appid, bit.Caption,
                    bit.FavDisOrder + "", bit.mListNodes));
        }
        // 对imageviews进行填充
        imageViews = new ImageView[advPics.size()];
        // 小图标
        for (int i = 0; i < advPics.size(); i++) {
            TextView text = new TextView(this);
            text.setWidth(20);
            imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(20, 20));
            imageView.setPadding(5, 5, 5, 5);
            imageViews[i] = imageView;
            if (i == 0) {
//                imageViews[i].setBackgroundResource(R.drawable.white_dot);
            } else {

//                imageViews[i].setBackgroundResource(R.drawable.gray_dot);
            }
            group.addView(text);
            group.addView(imageViews[i]);
        }
        if (arrBitmap != null && arrBitmap.size() > 0) {
            binnername.setText(arrBitmap.get(0).Caption);
        }
//        mCustomViewpager.setAdapter(new AdvAdapter(this, advPics, app));
        mCustomViewpager.setOnPageChangeListener(new GuidePageChangeListener());
        mCustomViewpager.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isContinue = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        isContinue = true;
                        break;
                    default:
                        isContinue = true;
                        break;
                }
                return false;
            }

        });
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (isContinue) {
                        viewHandler.sendEmptyMessage(what.get());
                        whatOption();
                    }
                }
            }
        }).start();
    }

    private final class GuidePageChangeListener implements OnPageChangeListener {
        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int arg0) {
            what.getAndSet(arg0);
            binnername.setText(arrBitmap.get(arg0).Caption);
            for (int i = 0; i < imageViews.length; i++) {
//                imageViews[arg0].setBackgroundResource(R.drawable.white_dot);
                if (arg0 != i) {
//                    imageViews[i].setBackgroundResource(R.drawable.gray_dot);
                }
            }
        }
    }

    private final Handler viewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mCustomViewpager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }
    };

    private void whatOption() {
        what.incrementAndGet();
        if (what.get() > imageViews.length - 1) {
            what.getAndAdd(-(imageViews.length));
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
