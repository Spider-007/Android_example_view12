/*
 * BookShower.java
 * classes : com.kinggrid.iapppdf.demo.BookShower
 * @author 涂博之
 * V 1.0.0
 * Create at 2014年5月20日 下午4:35:00
 */
package com.example.archivermodule;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.example.archivermodule.R;
import com.example.archivermodule.pdfsign.AnnotUtil;
import com.example.archivermodule.pdfsign.AreaSignDialog;
import com.example.archivermodule.pdfsign.BitmapUtil;
import com.example.archivermodule.pdfsign.ConstantValue;
import com.example.archivermodule.pdfsign.LogPrinter;
import com.example.archivermodule.pdfsign.MyButton;
import com.example.archivermodule.pdfsign.NoteDialog;
import com.example.archivermodule.pdfsign.OutlineDialog;
import com.example.archivermodule.pdfsign.SearchControls;
import com.example.archivermodule.pdfsign.SignDialog;
import com.example.archivermodule.pdfsign.TextAnnotDialog;
import com.example.archivermodule.pdfsign.VerifyPDFUtil;
import com.kinggrid.iapppdf.common.settings.AppSettings;
import com.kinggrid.iapppdf.company.signature.DigSignCallBack;
import com.kinggrid.iapppdf.core.codec.OutlineLink;
import com.kinggrid.iapppdf.ui.viewer.IAppPDFActivity;
import com.kinggrid.iapppdf.ui.viewer.PDFHandWriteView;
import com.kinggrid.iapppdf.ui.viewer.ViewerActivityController.LoadPageFinishedListener;
import com.kinggrid.pdfservice.Annotation;
import com.kinggrid.pdfservice.PageViewMode;
import com.kinggrid.pdfservice.SignaturePosition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;

//import com.kinggrid.pdfservice.Field;

/**
 * com.kinggrid.iapppdf.demo.BookShower
 *
 * @author 涂博之 <br/>
 *         create at 2014年5月20日 下午4:35:00
 */
@SuppressLint("NewApi")
public class BookShower extends IAppPDFActivity implements ConstantValue {

    private static final String TAG = "BookShower";

    private FrameLayout frameLayout;
    private LinearLayout toolbar;
    private SparseArray<MyButton> viewMap;
    private Context context;
    private boolean loadField = true;
    //private ArrayList<Field> fieldList;
    private int annotType;

    private Uri systemPhotoUri; // 照片在媒体库中的路径
    private String imagePath; // 照片文件在文件系统中的路径

    private boolean hasLoaded = false;

    private final static String IMAGE_PATH = SDCARD_PATH + "/testSignature.jpg";
    private ArrayList<String> fieldNameList = null;
    private final String SAVESIGNFINISH_ACTION = "com.kinggrid.iapppdf.broadcast.savesignfinish";

    private ArrayList<SignaturePosition> signpos_list = new ArrayList<SignaturePosition>();
    private PDFHandWriteView full_handWriteView;
    /**
     * 全文批注按钮
     */
    public Button btnClose, btnClear, btnUndo, btnRedo, btnSave, btnPen, btnEarse;
    private TextView company_textView;
    private RelativeLayout bottomBar;
    private View handwriteView_layout;
    private NoteDialog note_dlg;
    private AnnotUtil annotUtil;
    private SignDialog signWindow;
    private float y = 0.0f;

    private ProgressDialog connet_progress;  //获取TF Key连接进度框
    private ProgressDialog login_progress;   //获取TF Key登录连接进度框
    private String key_app_name;//TF Key里的应用名称
    private String stamp_filed_name = "Signature2"; //盖章域名称
//	private TFUtil tfUtil;

    private ProgressDialog progressDialog;
    private String url;
    //	private List<SignatureInfo> signatureInfos;
    private int signId = -1;
    private SharedPreferences preferences;

    private String signatureImagePath;
    private int insertPageNo;
    private float signatureX;
    private float signatureY;
    private float signature_width;
    private float signature_height;

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        context = this;
        LogPrinter.getInstance(context).start();
        setContentView(R.layout.book);
        frameLayout = (FrameLayout) findViewById(com.example.archivermodule.R.id.book_frame);
        toolbar = (LinearLayout) findViewById(R.id.toolbar);

//		tfUtil = TFUtil.getInstance(context);
        //isFingerScroll = true;
        this.initPDFParams();

        super.initPDFView(frameLayout);
        this.initViewMap();
        this.initToolBar();
        this.initParentListener();
        super.setLoadingText(R.string.msg_loading_tip);

        //isEbenT9 = true;
        isCyclePage = false;

        annotUtil = new AnnotUtil(this, userName);
        IntentFilter filter = new IntentFilter("com.kinggrid.pages.bmp.save");
        registerReceiver(receiver, filter);

        IntentFilter save_filter = new IntentFilter(SAVESIGNFINISH_ACTION);
        registerReceiver(receiver, save_filter);

        preferences = getSharedPreferences("iAppPDFLibDemo", Context.MODE_PRIVATE);
        if (null != preferences && preferences.contains(KEY_SERVER_URL)) {
            url = preferences.getString(KEY_SERVER_URL, "");
            keySN = preferences.getString(KEY_USER_ID, "");
        }

        keySN = "001";
    }

    /**
     * 初始化金格控件的参数,根据各自需求重写
     */
    private void initPDFParams() {
        Intent book_intent = getIntent();
        //用户名，默认admin
//        if (book_intent.hasExtra(NAME)) {
//            userName = book_intent.getStringExtra(NAME); //用户名，默认admin
//        }
        userName = PreferenceUtils.getEMPUserID(this);
        //授权码(必传)
//		if (book_intent.hasExtra(LIC)){
//        copyRight = "SxD/phFsuhBWZSmMVtSjKZmm/c/3zSMrkV2Bbj5tznSkEVZmTwJv0wwMmH/+p6wLiUHbjadYueX9v51H9GgnjUhmNW1xPkB++KQqSv/VKLDsR8V6RvNmv0xyTLOrQoGzAT81iKFYb1SZ/Zera1cjGwQSq79AcI/N/6DgBIfpnlwiEiP2am/4w4+38lfUELaNFry8HbpbpTqV4sqXN1WpeJ7CHHwcDBnMVj8djMthFaapMFm/i6swvGEQ2JoygFU3W8onCO1AgMAD2SkxfJXM/ijYgmFZo8sqFMkNKOgywo7x6aD2yiupr6ji7hzsE6/QVFbnJOcPDznqYpoJ6epdnT4Y1YsZxXwh2w5W4lqa1RyVWEbHWAH22+t7LdPt+jENVuE+uBYut77v64UQW7HW3mj7ISWDgc3YLh0bz4sFvgWSgCNRP4FpYjl8hG/IVrYXl2lZdwXeCcBhFGv3J7Er9+W8fXpxdRHfEuWC1PB9ruQ="; //授权码，必传，字符串格式
        copyRight = "SxD/phFsuhBWZSmMVtSjKZmm/c/3zSMrkV2Bbj5tznSkEVZmTwJv0wwMmH/+p6wLiUHbjadYueX9v51H9GgnjUhmNW1xPkB++KQqSv/VKLDsR8V6RvNmv0xyTLOrQoGzAT81iKFYb1SZ/Zera1cjGwQSq79AcI/N/6DgBIfpnlwiEiP2am/4w4+38lfUELaNFry8HbpbpTqV4sqXN1WpeJ7CHHwcDBnMVj8djMthFaapMFm/i6swvGEQ2JoygFU3W8onCO1AgMAD2SkxfJXM/mX1uF23u5oNhx5kpmkBkb3x6aD2yiupr6ji7hzsE6/Qng3l3AbK2vtwyJLdcl2md6r5JJO51PJS2vAlVxcmvGGVWEbHWAH22+t7LdPt+jENOIq5GN/n4KME0L/SFgUD1b8zb/8DFI+sDLA8bVOqHBiSgCNRP4FpYjl8hG/IVrYXOzDNrpoUGsPwMMlLKBA40uW8fXpxdRHfEuWC1PB9ruQ=";
//		}
        //是否支持域编辑功能，在表单PDF文件中可体现此功能，默认为false
        if (book_intent.hasExtra(CANFIELDEIDT)) {
            isFieldEidt = book_intent.getBooleanExtra(CANFIELDEIDT, false); //是否支持域编辑功能，默认为false
        }
        //是否为E人E本模式，默认为false
        if (book_intent.hasExtra(T7MODENAME)) {
            isSupportEbenT7Mode = book_intent.getBooleanExtra(T7MODENAME, false);
        }
        if (book_intent.hasExtra(EBENSDKNAME)) {
            isUseEbenSDK = book_intent.getBooleanExtra(EBENSDKNAME, false);
        }
        //是否保存矢量信息到PDF文档中，默认为true(支持单笔删除，但较慢)，为false时删除一次的手写内容
        if (book_intent.hasExtra(SAVEVECTORNAME)) {
            isSaveVector = book_intent.getBooleanExtra(SAVEVECTORNAME, true);
        }
        //是否选用矢量方式，保存签批时通过此参判断是矢量方式还是图片方式保存
        if (book_intent.hasExtra(VECTORNAME)) {
            isVectorSign = book_intent.getExtras().getBoolean(VECTORNAME);
        }
        //阅读模式，默认PageViewMode.VSCROLL，竖向连续翻页,不需要重设可忽略
        if (book_intent.hasExtra(VIEWMODENAME)) {
            int mode = book_intent.getIntExtra(VIEWMODENAME, VIEWMODE_VSCROLL);
            switch (mode) {
                case VIEWMODE_VSCROLL:
                    pageViewMode = PageViewMode.VSCROLL;
                    break;
                case VIEWMODE_SINGLEV:
                    pageViewMode = PageViewMode.SINGLEV;
                    break;
                case VIEWMODE_SINGLEH:
                    pageViewMode = PageViewMode.SINGLEH;
                    break;
            }
        }
        //是否保留PDF上次阅读位置，默认为true,为false时每次都从第一页开始阅读
        if (book_intent.hasExtra(LOADCACHENAME)) {
            loadPDFReaderCache = book_intent.getBooleanExtra(LOADCACHENAME, true);
        }
        Log.d("tbz", "loadPDFReaderCache = " + loadPDFReaderCache);
        if (book_intent.hasExtra(FILE_NAME)) {
            fileName = book_intent.getStringExtra(FILE_NAME);
        }

        if (book_intent.hasExtra(FILE_DATA)) {
            fileData = book_intent.getByteArrayExtra(FILE_DATA);
        }

        //签名域名称，字符串格式，默认"signatureField1",不需要可忽略
//		signatureField1 = "signatureField1";
        //盖章域名称，字符串格式，默认"signatureField2",不需要可忽略
//		signatureField2 = "signatureField2";
        //照片域名称，字符串格式，默认"photo",不需要可忽略
//		photo = "photo";
        //录音样式，可选0(普通样式)或1(微信样式),默认为普通样式
//		soundAnnotStyle = 1;
        //插入空白页的位置，整数类型，默认-100,如果不需要空白页，忽略此参
//		insertPos = -100;

    }

    /**
     * 监听金格控件
     */
    private void initParentListener() {

        //加载PDF文件结束监听，根据需求选择是否监听
        getController().setLoadPageFinishedListener(
                new LoadPageFinishedListener() {

                    @Override
                    public void onLoadPageFinished() {
                        Log.d("tbz", "onLoadPageFinished");
                        //填充模板，根据需求实现功能
                        if (!hasLoaded) {
                            loadFieldTemplates();
                            //setPageZoom(1.2f);//设置缩放
                            //getController().getZoomModel().setMaxZoom(2.0f);
                            /*Thread thread = new Thread(new LoadAnnotations());
							thread.start();*/

                            //verifySignature(-1);//页码为-1，则是验证全文档的数字签名；

                            hasLoaded = true;
                        }

                    }
                });

        setOnLocalSignatureListener(new OnLocalSignatureListener() {

            @Override
            public void onLocalSingatureFinish(String signPath, float area_width, float area_height,
                                               float insertX, float insertY, int pageNo) {
				/*imagePath = signPath;
				insertPageNo = pageNo;
				signatureX = insertX;
				signatureY = insertY;
				signature_width = area_width;
				signature_height = area_height;*/

                doSaveDigSignByPos(SDCARD_PATH + "/cc_123456.pfx", "123456", signPath, pageNo, insertX, insertY,
                        area_width, area_height, new DigSignCallBack() {

                            @Override
                            public void onFinish(boolean arg0, String arg1) {

                            }

                        });
                //showLoginDialog(SIGNATURE_SERVER_ACTION_GET_CERT);
                //startTFSingature();
            }
        });

        super.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageChange(String currentPage) {
                Log.i(TAG, "onPageChange:" + currentPage);
            }
        });

        super.setOnScrollChangeListener(new OnScrollChangeListener() {

            @Override
            public void onScrollToDocTop() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollToDocBottom() {
                // TODO Auto-generated method stub

            }
        });

        //添加批注
        super.setOnViewTouchAddAnnotListener(new OnViewTouchAddAnnotListener() {

            @Override
            public void onTouch(float x, float y) {
                if (isAnnotation) {
                    isAnnotation = false;
                    //文字批注
                    Log.v("tbz", "textannot x = " + x + ", y = " + y);
                    annotUtil.addTextAnnot(x, y);
                }
                if (isSound) {
                    isSound = false;
                    //语音批注
                    annotUtil.addSoundAnnot(x, y);
                }
                if (isFinger) {
                    isFinger = false;
                    //TODO 录入指纹
                }
            }
        });

        //显示批注
        super.setOnViewTouchShowAnnotListener(new OnViewTouchShowAnnotListener() {

            @Override
            public void onTouchTextAnnot(Annotation annotation) {
                annotUtil.showTextAnnot(annotation);
            }

            @Override
            public void onTouchSoundAnnot(Annotation annotation) {
                annotUtil.showSoundAnnot(annotation);
            }
        });

        //同步接口
        super.setOnSynchronizationOptionListener(new OnSynchronizationOptionListener() {


            //删除手写签批的监听接口
            @Override
            public void onDeleteAnnot(String annotId) {
                Log.d("tbz", "onDeleteHandwriteAnnot annotId = " + annotId);

            }

            //插入文字注释的监听
            @Override
            public void onInsertTextAnnot(int pageNo, String annotId, float x,
                                          float y, String content, String author, String jsonString) {
                Log.d("tbz", "onInsertTextAnnot annotId = " + annotId +
                        ", pageNo = " + pageNo + ", x = " + x + ", y = " + y + ", content = " + content
                        + ", author = " + author + ", jsonString = " + jsonString);

            }

            //更新文字批注内容的监听
            @Override
            public void onUpdataTextAnnotRect(String annotId, int pageNo, float x,
                                              float y) {

                Log.d("tbz", "onUpdataTextAnnotRect annotId = " + annotId + ", pageNo = " + pageNo + ", x = " + x + ", y = " + y);
            }

            //移动文字批注的监听
            @Override
            public void onUpdateTextAnnotContent(String annotId, String content) {

                Log.d("tbz", "onUpdateTextAnnotContent annotId = " + annotId + ", content = " + content);
            }

            //滑动的监听接口
            @Override
            public void onFling(float vX, float vY, Rect limits) {

                Log.d("tbz", "onFling vX = " + vX + ", vY = " + vY + ", limits = " + limits);

            }

            //滚动的监听接口
            @Override
            public void onScroll(int x, int y) {

                Log.d("tbz", "onScroll x = " + x + ", y = " + y);

            }

            //插入手写签批的监听接口
            @Override
            public void onInsertHandwriteAnnot(int pageNo, String annotId, float x,
                                               float y, float w, float h, String imagePath,
                                               String imgName, String author, String annotInfo) {
                Log.d("tbz", "onInsertHandwriteAnnot imagePath = " + imagePath +
                        ", imgName = " + imgName + ", author = " + author + ", annotInfo = " + annotInfo + ", annotId = " + annotId + ", x = " + x
                        + ", y = " + y + ", w = " + w + ", h = " + h);

            }

            //移动手写签批的监听接口
            @Override
            public void onUpdateHandwriteAnnot(String annotId, int pageNo, float x,
                                               float y) {

                Log.d("tbz", "onUpdateHandwriteAnnot annotId = " + annotId +
                        ", pageNo = " + pageNo + ", x = " + x + ", y = " + y);
            }

            //插入矢量签批的监听接口
            @Override
            public void onInsertVectorAnnot(int pageNo, String annotId, float x, float y,
                                            float w, float h, String author, String line,
                                            String annotInfo, boolean saveVector) {

                Log.d("tbz", "onInsertVectorAnnot author = " + author +
                        ", annotInfo = " + annotInfo + ", saveVector = " + saveVector + ", annotId = " + annotId + ", line = " + line);
            }

            @Override
            public void onScrollFinished(int x, int y, boolean isForce) {
                Log.d("tbz", "onScrollFinished x = " + x + ", y = " + y + ", isForce = " + isForce);
            }

        });

    }

    @Override
    protected void loadFieldTemplates() {
        super.loadFieldTemplates();
        if (getIntent().hasExtra(FILLTEMPLATE)) {
            boolean isFillTemplate = getIntent().getBooleanExtra(FILLTEMPLATE, false);
            if (isFillTemplate) {
                Thread thread = new Thread(new LoadAnnots());
                thread.start();
            }
        }
    }

    @Override
    protected void onStartImpl() {
        super.onStartImpl();
        LogPrinter.getInstance(context).stop();
        setScreenPort();
    }

    @Override
    protected void onDestroyImpl(boolean finishing) {
        super.onDestroyImpl(finishing);
        System.exit(0);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("tbz", "onConfigurationChanged");
        resetHandwirteView();
    }


    private void resetHandwirteView() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (isSupportEbenT7Mode) {
            getT7ModePDFHandwriteView().reset(dm.widthPixels, dm.heightPixels);
        } else if (full_handWriteView != null && full_handWriteView.getVisibility() == View.VISIBLE) {
            full_handWriteView.reset(dm.widthPixels, dm.heightPixels);
        }

    }

    @Override
    public void showAnnotInfo(String[] infos, String createTime) {
        StringBuilder builder = new StringBuilder();
        builder.append("用户名：");
        builder.append(infos[1]);
        builder.append("\n");
        builder.append("创建时间:");
        builder.append(createTime);

        Toast.makeText(context, builder.toString(), Toast.LENGTH_SHORT).show();
    }


    public class LoadAnnotations implements Runnable {

        @Override
        public void run() {
            touchEnable = false;
            ArrayList<Annotation> list = getAnnotsFromJson(readFromTxt(Environment
                    .getExternalStorageDirectory().getPath().toString() + "/test_import.txt"));
            Log.d("tbz", "list size = " + list.size());
            for (int i = 0; i < list.size(); i++) {
                Annotation annot = list.get(i);
                String type = annot.getStyleName();
                int pageNo = Integer.parseInt(annot.getPageNo()) - 1;
                float x = Float.parseFloat(annot.getX());
                float y = Float.parseFloat(annot.getY());
                String userName = annot.getAuthorName();
                String creatTime = annot.getCreateTime();
                String annotId = annot.getAnnotId();
                Log.d("tbz", "annotId2 = " + annotId);
                if (type.equals("Stamp") || type.equals("test") || type.equals("unknown")) {
                    if (annot.getAnnoContent().startsWith("q")) {
                        insertVectorAnnotationForNew(pageNo, x, y, Float.parseFloat(annot.getWidth()),
                                Float.parseFloat(annot.getHeight()), annot.getAnnoContent(), userName,
                                creatTime, 1, annotId);
                    } else {
                        insertHandWriteAnnotation(pageNo,
                                x,
                                y,
                                Float.parseFloat(annot.getWidth()),
                                Float.parseFloat(annot.getHeight()), annot
                                        .getAnnoContent(), userName,
                                creatTime, 1, annotId);
                    }
                } else if (type.equals("Text")) {
                    Log.d("tbz", "insert text annotation");
                    insertTextAnnotation(pageNo,
                            x,
                            y,
                            annot.getAnnoContent(),
                            userName,
                            creatTime,
                            "", annotId);
                } else if (type.equals("Sound")) {
                    insertSoundAnnotation(annot.getUnType(), pageNo, x, y, userName, annot.getSoundData(), (int) annot.getSoundRate(),
                            (int) annot.getSoundChannels(), (int) annot.getSoundBitspersample(), creatTime);
                }
            }

            Message msg = new Message();
            msg.what = MSG_WHAT_REFRESHDOCUMENT;
            myHandler.sendMessage(msg);
        }

    }

    /**
     * 填充模板，适用于iAppPDFDemo生成的公安模板.pdf,供此类需求参考
     * com.kinggrid.iapppdf.demo.LoadAnnots
     *
     * @author wmm
     *         create at 2015年8月17日 下午3:00:20
     */
    public class LoadAnnots implements Runnable {

        @Override
        public void run() {
			/*if (loadField) {
				fieldNameList = new ArrayList<String>();
				fieldList = new ArrayList<Field>();
				Field field = new Field();
				field.setFieldName("ajmc");
				field.setFieldContent("抢劫");
				fieldList.add(field);
				fieldNameList.add("ajmc");
				Field field1 = new Field();
				field1.setFieldName("fasj");
				field1.setFieldContent("2014-01-01");
				fieldList.add(field1);
				fieldNameList.add("fasj");
				Field field2 = new Field();
				field2.setFieldName("ajwh");
				field2.setFieldContent("G10001");
				fieldList.add(field2);
				fieldNameList.add("ajwh");
				Field field3 = new Field();
				field3.setFieldName("fzxyrxm");
				field3.setFieldContent("张三");
				fieldList.add(field3);
				fieldNameList.add("fzxyrxm");
				Field field4 = new Field();
				field4.setFieldName("fzxyrxb");
				field4.setFieldContent("男");
				fieldList.add(field4);
				fieldNameList.add("fzxyrxb");
				Field field5 = new Field();
				field5.setFieldName("mz");
				field5.setFieldContent("汉");
				fieldList.add(field5);
				fieldNameList.add("mz");
				Field field6 = new Field();
				field6.setFieldName("fzxyrcsny");
				field6.setFieldContent("1970-01-01");
				fieldList.add(field6);
				fieldNameList.add("fzxyrcsny");
				Field field7 = new Field();
				field7.setFieldName("whcd");
				field7.setFieldContent("初中");
				fieldList.add(field7);
				fieldNameList.add("whcd");
				Field field8 = new Field();
				field8.setFieldName("zjzljhm");
				field8.setFieldContent("320361197001010001");
				fieldList.add(field8);
				fieldNameList.add("zjzljhm");
				Field field9 = new Field();
				field9.setFieldName("zz");
				field9.setFieldContent("北京某街道10#5楼1号");
				fieldList.add(field9);
				fieldNameList.add("zz");
				Field field10 = new Field();
				field10.setFieldName("hjd");
				field10.setFieldContent("北京某街道10#5楼1号");
				fieldList.add(field10);
				fieldNameList.add("hjd");
				Field field11 = new Field();
				field11.setFieldName("gzdw");
				field11.setFieldContent("无业");
				fieldList.add(field11);
				fieldNameList.add("gzdw");
				Field field12 = new Field();
				field12.setFieldName("wfjl");
				field12.setFieldContent("无");
				fieldList.add(field12);
				fieldNameList.add("wfjl");
				Field field13 = new Field();
				field13.setFieldName("dwmc");
				field13.setFieldContent("无");
				fieldList.add(field13);
				fieldNameList.add("dwmc");
				Field field14 = new Field();
				field14.setFieldName("frdtb");
				field14.setFieldContent("李四");
				fieldList.add(field14);
				fieldNameList.add("frdtb");
				Field field15 = new Field();
				field15.setFieldName("dwdz");
				field15.setFieldContent("北京某街道10#5楼1号");
				fieldList.add(field15);
				fieldNameList.add("dwdz");
				Field field16 = new Field();
				field16.setFieldName("taqtr");
				field16.setFieldContent("王五");
				fieldList.add(field16);
				fieldNameList.add("taqtr");
				Field field17 = new Field();
				field17.setFieldName("wfssjzj");
				field17.setFieldContent("省略。。。");
				fieldList.add(field17);
				fieldNameList.add("wfssjzj");

				if (fieldList.size() > 0) {
					for (int i = 0; i < fieldList.size(); i++) {
						setFieldContent(fieldList.get(i).getFieldName(),
								fieldList.get(i).getFieldContent());
					}
				}
				loadField = false;
				refreshDocument();
			}*/

            if (loadField) {
                setFieldContent("topmostSubform[0].Page1[0].department[0]", "卫生局"); //来问单位
                setFieldContent("topmostSubform[0].Page1[0].titleName[0]", "关于加强监督卫生议题"); //文件标题
                setFieldContent("topmostSubform[0].Page1[0].fileNumber[0]", "005"); //文号
                setFieldContent("topmostSubform[0].Page1[0].num[0]", "3"); //份数
                setFieldContent("topmostSubform[0].Page1[0].attachFile[0]", "会议文件.pdf"); //附件
                setFieldContent("topmostSubform[0].Page1[0].incmingDate[0]", "2018-05-31"); //日期
                setFieldContent("topmostSubform[0].Page1[0].incmingNumber[0]", "2406549198744168"); //收文编号
                setFieldContent("topmostSubform[0].Page1[0].proposal[0]", "同意"); //拟办建议
                setFieldContent("topmostSubform[0].Page1[0].showName[0]", "张三"); //拟办人

                loadField = false;
                refreshDocument();
            }
        }

    }


    @Override
    public void windowToFullScreen() {

        Toast.makeText(context, "windowToFullScreen", Toast.LENGTH_SHORT).show();
    }

    private void initToolBar() {
        if (viewMap != null && viewMap.size() > 0) {
            for (int i = 0; i < viewMap.size(); i++) {
                addBtnView(viewMap.get(viewMap.keyAt(i)));
            }
        }
    }

    /**
     * 设置签名位置
     */
    private void setSignaturePosition() {
        for (int i = 0; i < getPageCount(); i++) {
            SignaturePosition sp = new SignaturePosition();
            sp.setSignature_page_num(String.valueOf(i));
            sp.setSignature_x(100.0f);
            sp.setSignature_y(100.0f);
            signpos_list.add(sp);
        }
		/*List<SignPosition> list = getSignPositionInField("topmostSubform[0].Page1[0].fifthDate[0]");
		Log.d("tbz","list size = " + list.size());
		for(int i=0; i<list.size(); i++){
			SignPosition spos = list.get(i);
			SignaturePosition pos = new SignaturePosition();
			pos.setSignature_page_num(String.valueOf(spos.pageno -1));
			pos.setSignature_x(spos.rect[0]);
			pos.setSignature_y(spos.rect[1]);
			pos.setSignature_width(spos.rect[2] - spos.rect[0]);
			pos.setSignature_height(spos.rect[3] - spos.rect[1]);

			signpos_list.add(pos);
		}*/
    }

    @Override
    protected void onPauseImpl(boolean finishing) {

        super.onPauseImpl(finishing);
        if (signWindow != null) {
            signWindow.pause();
        }

    }

    /**
     * 显示签名界面
     */
    private void showSignWindow(final int sign_mode) {
        final String sign_filed_name = "Signature2";//"Signature2";//示例域名称，实际使用中请写成正确的签名域
        if (hasFieldInDocument(sign_filed_name)) {
            lockScreen();//锁定文档
            signWindow = new SignDialog(BookShower.this, copyRight);
            signWindow.show();
            signWindow.setSignatureListener(new SignDialog.OnSignatureListener() {

                @Override
                public void onSignatureSave(final PDFHandWriteView handWriteView) {
                    //签名方式（如：域定位、文本定位、位置定位、数字签名等）可参考技术白皮书
                    final String sign_filed_name = "qm_ssdr"/*"Signature2"*/;//"Signature2";//示例域名称，实际使用中请写成正确的签名域
                    switch (sign_mode) {
                        case SIGN_MODE_FIELDNAME:
                            //域定位
                            if (hasFieldInDocument(sign_filed_name)) {
                                doSaveSignByFieldName(handWriteView, sign_filed_name);
                                //doSaveSignByFieldNameInPage(handWriteView, sign_filed_name, 2);
                            } else {
                                Toast.makeText(context, R.string.no_signature2, Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case SIGN_MODE_TEXT:
                            //文本定位
                            doSaveSignByText(handWriteView, "短信", 1f);
                            break;
                        case SIGN_MODE_POSITION:
                            //位置定位
                            setSignaturePosition();
                            doSaveSignByPosition(handWriteView, signpos_list, 0.1f);
                            break;
                        case SIGN_MODE_SERVER:
                            //配合服务器进行数字签名
                            if (hasFieldInDocument(sign_filed_name)) {
                                doSaveSignByServer(handWriteView, sign_filed_name);
                            } else {
                                Toast.makeText(context, R.string.no_signature2, Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case SIGN_MODE_KEY:
                            if (hasFieldInDocument(sign_filed_name)) {
                                final String imagePath = getFilesDir().getAbsolutePath() + System.currentTimeMillis();
                                final boolean save = doSavePDFHandWriteViewImage(handWriteView, imagePath);
                                if (save) {
                                    //蓝牙Key本地数字签名,要求设备系统Android 4.4或以上
//									new CertInfoDialog(context).setOnItemClickListener(new CertInfoDialog.CertItemClickInterface() {
//
//										@Override
//										public void onItemClick(SOF_K5AppLib app, String containerName) {
//											doSaveDigitallySign(imagePath, sign_filed_name, app, containerName);
//										}
//									});
                                }

                            } else {
                                Toast.makeText(context, R.string.no_signature2, Toast.LENGTH_SHORT).show();
                            }

                            break;
                        case SIGN_MODE_BDE:
                            //行业应用BDE签名
                            doSaveBDESign(handWriteView);
                            break;
                        case SIGN_MODE_TFCARD:

                            break;
                    }

                    signWindow.dismiss();
                    refreshDocument();
                }
            });
            unLockScreen();//解锁文档
        } else {
            Toast.makeText(context, R.string.no_signature2, Toast.LENGTH_SHORT).show();
        }
    }

    private void connectDevice(final String devName) {
        if (!TextUtils.isEmpty(devName)) {
            //登录
            if (connet_progress != null) {
                connet_progress.dismiss();
                connet_progress = null;
            }
            connet_progress = ProgressDialog.show(context, "连接", "连接中，请稍等...", true);

            new Thread() {
                public void run() {
                    myHandler.obtainMessage(HANDLE_TF_CONNET_ALERT, "连接设备...").sendToTarget();
//					int rtn = tfUtil.connect(devName);
//					if(rtn != TFUtil.SUCCESS_CODE){
//						myHandler.obtainMessage(HANDLE_TF_CONNET_ERROR, "连接失败, " + tfUtil.getMessageByCode(rtn)).sendToTarget();
//						return;
//					}
                    myHandler.obtainMessage(HANDLE_TF_CONNET_SUCCESS, "").sendToTarget();
                }
            }.start();
        } else {
            Toast.makeText(context, "设备名称为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginDevice(final String app_name) {
        if (!TextUtils.isEmpty(app_name)) {
            key_app_name = app_name;
            showInputPINDialog();
        } else {
            Toast.makeText(context, "应用名称为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void doTFKeySign(String containerName) {
        if (cert_dialog != null) {
            cert_dialog.dismiss();
            cert_dialog = null;
        }
        Log.d("tbz", "doTFKeySign containerName = " + containerName);
        if (!TextUtils.isEmpty(containerName)) {
			/*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kinggrid_demo);
			doSaveTFDigitallySign(bitmap, stamp_filed_name, containerName, key_app_name);*/
            doSaveTFDigitallySign(imagePath, insertPageNo, signatureX, signatureY, signature_width, signature_height,
                    containerName, key_app_name);
        } else {
            Toast.makeText(context, "容器名称为空", Toast.LENGTH_SHORT).show();
        }
    }

    private AlertDialog choice_dialog;
    private String choice_item_msg;

    private AlertDialog showChoiceDialog(String title, final String[] msgItems) {
        if (choice_dialog != null) {
            choice_dialog.dismiss();
            choice_dialog = null;
        }
        choice_dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setSingleChoiceItems(msgItems, 0,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                choice_item_msg = msgItems[which];
                                choice_dialog.dismiss();
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
						/* User clicked No so do some stuff */
                        choice_dialog.dismiss();
                    }
                }).setCancelable(false).create();
        choice_dialog.show();
        return choice_dialog;
    }

    private AlertDialog pin_dialog;

    private void showInputPINDialog() {
        Log.d("tbz", "showInputPINDialog");
        final View inputView = LayoutInflater.from(context).inflate(
                R.layout.input_pwd, null);
        final EditText pwd_et = (EditText) inputView
                .findViewById(R.id.password_edit);
        final CheckBox is_show_pwd = (CheckBox) inputView
                .findViewById(R.id.is_show_pwd);
        is_show_pwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (is_show_pwd.isChecked()) {
                    pwd_et.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                } else {
                    pwd_et.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                }
            }
        });

        if (pin_dialog != null) {
            pin_dialog.dismiss();
            pin_dialog = null;
        }
        pin_dialog = new AlertDialog.Builder(this)
                .setTitle("请输入密码")
                .setView(inputView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String login_pwd = pwd_et.getText().toString();
                        if (TextUtils.isEmpty(login_pwd)) {
                            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            //隐藏软键盘
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(inputView.getWindowToken(), 0); //强制隐藏键盘

                            pin_dialog.dismiss();

                            login_progress = ProgressDialog.show(context, "登录",
                                    "正在登录", true);

                            new Thread() {
                                public void run() {

                                    myHandler.obtainMessage(
                                            HANDLE_TF_LOGIN_ALERT, "正在登录...")
                                            .sendToTarget();
//									int login_rtn = tfUtil.login(key_app_name,
//											login_pwd);
//									if (login_rtn == TFUtil.SUCCESS_CODE) {
//										myHandler.obtainMessage(
//												HANDLE_TF_LOGIN_SUCCESS, "")
//												.sendToTarget();
//									} else {
//										myHandler
//												.obtainMessage(
//														HANDLE_TF_LOGIN_ERROR,
//														tfUtil.getMessageByCode(login_rtn))
//												.sendToTarget();
//									}

                                }
                            }.start();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (pin_dialog != null) {
                            pin_dialog.dismiss();
                            pin_dialog = null;
                        }
                    }
                }).create();
        pin_dialog.show();
    }

    private AlertDialog containerDialog, cert_dialog;
    private TextView containerView_title, containerView_alert;

    private void showContainerDialog() {
        Log.d("tbz", "showContainerDialog");
        View containerView = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null);
        containerView_title = (TextView) containerView.findViewById(R.id.tv_title);
        containerView_title.setText("选择容器");
        containerView_alert = (TextView) containerView.findViewById(R.id.tv_alert);
        containerView_alert.setText("获取容器列表中...");

        if (containerDialog != null) {
            containerDialog.dismiss();
            containerDialog = null;
        }

        getContainer();

        containerDialog = new AlertDialog.Builder(this).create();
        containerDialog.show();
        containerDialog.getWindow().setContentView(containerView);
        containerDialog.setCancelable(true);
    }

    public List<Map<String, String>> container_list;

    private void getContainer() {
        Log.d("tbz", "getContainer");
        container_list = new ArrayList<Map<String, String>>();
        new Thread() {
            public void run() {

                List<String> containers = new ArrayList<String>();

            }
        }.start();
    }


    private AreaSignDialog areaSignWindow;
    private View areaSignView;

    /**
     * 显示区域签批窗口
     */
    private void showAreaSignWindow() {
        lockScreen();//加锁
        openAreaHandwrite(this);//调用区域签批功能

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (areaSignView == null) {
            areaSignWindow = new AreaSignDialog(BookShower.this, copyRight, false);
            areaSignView = areaSignWindow.get();
            addContentView(areaSignView, params);
        }
        areaSignView.setVisibility(View.VISIBLE);
        isLocked = true;
        areaSignWindow.setSignatureListener(new AreaSignDialog.OnAreaSignatureListener() {

            @Override
            public void onSignatureSave(PDFHandWriteView handWriteView, boolean isShowEdit, EditText editText) {

                areaSignView.setVisibility(View.GONE);
                if (isShowEdit && !TextUtils.isEmpty(editText.getText())) {
                    closeAreaHandwrite(handWriteView, false, editText);//关闭区域签批功能
                } else {
                    closeAreaHandwrite(handWriteView, false);//关闭区域签批功能
                }
                isLocked = false;
                unLockScreen(); //解锁
            }

            @Override
            public void onSignatureClose() {
                areaSignView.setVisibility(View.GONE);
                closeAreaHandwrite();//关闭区域签批功能
                isLocked = false;
                unLockScreen(); //解锁
            }
        });

    }

    Handler scrollHanlder = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    if (view.isScrollFinished()) {
                        Rect r = new Rect();
                        r.left = 0;
                        r.top = 0;
                        r.right = 0;
                        r.bottom = 280408;
                        fling(0, -15141, r);
                    } else {
                        Message msg1 = new Message();
                        msg1.what = 0;
                        scrollHanlder.sendMessageDelayed(msg1, 100);
                    }
            }
        }

    };

    /**
     * 初始化按钮事件
     *
     * @param btn
     */
    private void addBtnView(final MyButton btn) {

        toolbar.addView(btn);

        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!touchEnable) {
                    return;
                }
                if (IAppPDFActivity.progressBarStatus == 1) {
                    Toast.makeText(context, "正在加载页面，请稍后尝试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Integer i1 = (Integer) btn.getTag();
                if (i1.equals(R.string.signature)) {
                    showSignWindow(SIGN_MODE_FIELDNAME);

                } else if (i1.equals(R.string.signature_delete)) {
                    createDialog(TYPE_ANNOT_SIGNATURE);

                } else if (i1.equals(R.string.full_signature)) {
                    changeButtonState((Integer) btn.getTag());
                    if (isSupportEbenT7Mode || isUseEbenSDK) {
                        return;
                    }
                    //当全文签批时，若页面还在滚动，则自动停止
                    if (!getController().getView().isScrollFinished()) {
                        getController().getView().forceFinishScroll();
                    }
                    handwriteView_layout = View.inflate(context, R.layout.signature_kinggrid_full, null);
                    full_handWriteView = (PDFHandWriteView) handwriteView_layout.findViewById(R.id.v_canvas);
                    initBtnView(handwriteView_layout);
                    openHandwriteAnnotation(handwriteView_layout, full_handWriteView);

                } else if (i1.equals(R.string.note_list)) {
                    openAnnotationList(TYPE_ANNOT_STAMP);

                } else if (i1.equals(R.string.bookmark_list)) {
                    openOutlineList();

                } else if (i1.equals(R.string.text_note_list)) {
                    openAnnotationList(TYPE_ANNOT_TEXT);

                } else if (i1.equals(R.string.annot_sound_list)) {
                    openAnnotationList(TYPE_ANNOT_SOUND);

                }
//				else if (i1.equals(R.string.text_note)) {//openTextAnnotation();
//					showTextAnnotWindow();
//
//				}
                else if (i1.equals(R.string.sound_note)) {
                    openSoundAnnotation();

                } else if (i1.equals(R.string.delete_sound_note)) {
                    createDialog(TYPE_ANNOT_SOUND);

                } else if (i1.equals(R.string.full_signature_delete)) {
                    changeButtonState((Integer) btn.getTag());
                    createDialog(TYPE_ANNOT_STAMP);

                } else if (i1.equals(R.string.text_note)) {
                    if (null != signButton && signButton.isSelected()) {
                        changeButtonState((Integer) btn.getTag());
                        full_handWriteView.setPenSettingInfoName("demo_type", "demo_size", "demo_color");
                        full_handWriteView.doSettingHandwriteInfo();
                    } else {
                        Toast.makeText(context, "需要点击全文批注才能进行设置!", Toast.LENGTH_SHORT).show();
                    }

                } else if (i1.equals(R.string.note_delete)) {
                    createDialog(TYPE_ANNOT_TEXT);

                } else if (i1.equals(R.string.camera)) {
                    takePicture();

                } else if (i1.equals(R.string.document_save)) {
                    changeButtonState((Integer) btn.getTag());
                    savePdfSign();
//					if (!loadPDFReaderCache) {
//						clearPDFReadSettings();
//					}
//					final Builder builder = new Builder(context);
//					builder.setMessage(getString(R.string.dialog_message_save));
//					builder.setTitle(getString(R.string.dialog_title));
//					builder.setPositiveButton(getString(R.string.ok),
//							new DialogInterface.OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface dialog,
//													int which) {
//									dialog.dismiss();
//									Log.v(TAG,
//											"" + getIntent().hasExtra(ANNOT_TYPE));
//									//导入导出时，暂时不进行保存文档
//									saveAndExit();
//								}
//							});
//					builder.setNegativeButton(getString(R.string.cancel),
//							new DialogInterface.OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface dialog,
//													int which) {
//									dialog.dismiss();
//								}
//							});
//
//					final Dialog dialog1 = builder.create();
//					dialog1.setCancelable(false);
//					dialog1.show();

                } else if (i1.equals(R.string.digital_signature)) {
                    showSignWindow(SIGN_MODE_SERVER);

                } else if (i1.equals(R.string.local_digital_signature)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        showSignWindow(SIGN_MODE_KEY);
                    } else {
                        Toast.makeText(context, "该功能仅支持Android 4.4及以上版本。", Toast.LENGTH_SHORT).show();
                    }

                } else if (i1.equals(R.string.tf_digital_signature)) {//showLoginDialog(SIGNATURE_SERVER_VERIFY_MODE_COMMON);
                    Bitmap bmp = BitmapFactory.decodeFile(SDCARD_PATH + "/testsignature.jpg");
                    openLocateSignature(bmp, 4, false);
                    //openLocateSignature(bmp);

                } else if (i1.equals(R.string.verify)) {
                    final String verify_signature_url = "http://192.168.0.48:8888/Demo/DefaulServlet";
                    if (TextUtils.isEmpty(verify_signature_url)) {
                        Toast.makeText(getApplicationContext(), "服务器地址为空", Toast.LENGTH_LONG).show();
                    } else {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                VerifyPDFUtil verifyPDFUtil = new VerifyPDFUtil(BookShower.this, getHandler());
                                verifyPDFUtil.upload(verify_signature_url, null, 2, null);
                            }

                        }).start();
                    }

                } else if (i1.equals(R.string.area)) {
                    showAreaSignWindow();

                } else if (i1.equals(R.string.document_saveas)) {
                    final EditText input = new EditText(context);    //定义一个EditText
                    input.setText("xxx.pdf");
                    final Builder saveAsBuilder = new Builder(context);
                    saveAsBuilder.setTitle("设置文件名");
                    saveAsBuilder.setView(input);       //将EditText添加到builder中
                    saveAsBuilder.setPositiveButton(getString(R.string.save),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                    saveAsDocument(Environment.getExternalStorageDirectory() + "/" + input.getText());
                                    Toast.makeText(context, "另存完成", Toast.LENGTH_SHORT).show();
                                }
                            });
                    saveAsBuilder.setNegativeButton(getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            });

                    final Dialog saveAsDlg = saveAsBuilder.create();
                    saveAsDlg.setCancelable(false);
                    saveAsDlg.show();


                } else if (i1.equals(R.string.save_pages)) {
                    isLocked = true;
                    getPagesImage(FILEDIR_PATH + "/pagebmp/");

                } else if (i1.equals(R.string.save_field_content)) {
                    if (fieldNameList != null) {
                        if (fieldNameList.size() > 0) {
                            try {
                                FileOutputStream fos = new FileOutputStream(
                                        FILEDIR_PATH + "/fieldcontents.txt");
                                String values = "";
                                for (int i = 0; i < fieldNameList.size(); i++) {
                                    String key = fieldNameList.get(i);
                                    values = values + key + ": " + getFieldContent(key) + "\r\n";
                                }
                                fos.write(values.getBytes("UTF-8"));
                                fos.close();
                                Toast.makeText(getApplicationContext(), "保存文本域内容完毕", Toast.LENGTH_SHORT).show();
                            } catch (FileNotFoundException e) {
                                Log.e("", e.toString());
                            } catch (UnsupportedEncodingException e) {
                                Log.e("", e.toString());
                            } catch (IOException e) {
                                Log.e("", e.toString());
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "无文本域内容", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "无文本域内容", Toast.LENGTH_SHORT).show();
                    }

                } else if (i1.equals(R.string.search)) {
                    AppSettings.current().setHighLightColor(0x90FFFF00);
                    SearchControls searchControls = new SearchControls(BookShower.this);
                    searchControls.show();

                } else if (i1.equals(R.string.local_signature)) {//showLoginDialog(SIGNATURE_SERVER_VERIFY_MODE_USERCODE);
                    showLoginDialog(SIGNATURE_SERVER_VERIFY_MODE_COMMON);
//					Bitmap bm = BitmapFactory.decodeFile(SDCARD_PATH + /*"/testsignature.jpg"*/"/stamp_src.jpg");
//					openLocateSignature(bm);

                }
            }
        });
    }

//	private PfxInfo cert_info;

    private void showLoginDialog(final int mode) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.keysn_login, null);
        final EditText keysn = (EditText) layout.findViewById(R.id.keysn_edit);
        final EditText pwd = (EditText) layout.findViewById(R.id.pwd_edit);
        final EditText service_url = (EditText) layout.findViewById(R.id.url_edit);
		/*keySN = "T001";
		url = "http://sign.kinggrid.com/iSignatureServer/OfficeServer.jsp";*/
		/*keySN = "测试2 999999999999999992";
		url = "http://192.168.0.9:8001/iSignatureServer/OfficeServer.jsp";*/
		/*keySN = "990010001";
		url = "http://59.195.65.7:8089/iSignatureServer/OfficeServer.jsp";*/
		/*keySN = "001";
		url = "http://219.139.25.5:808/iSignatureServer/OfficeServer.jsp";*/
        keySN = "0468351210120A11";
        url = "http://192.168.0.69:8080/iSignatureServer/OfficeServer.jsp";


        keysn.setText(keySN);
        service_url.setText(url);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle(R.string.login);
        builder.setView(layout);
        builder.setPositiveButton(R.string.btn_sure, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                keySN = keysn.getEditableText().toString();
                final String login_pwd = pwd.getEditableText().toString();
                url = service_url.getEditableText().toString();
                isDismissDialog(true, dialog);
                if (TextUtils.isEmpty(keySN)) {
                    Toast.makeText(context, R.string.username_cannot_null, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(login_pwd)) {
                    Toast.makeText(context, R.string.password_cannot_null, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(url)) {
                    Toast.makeText(context, R.string.url_cannot_null, Toast.LENGTH_SHORT).show();
                } else {
                    isDismissDialog(false, dialog);
                    dialog.dismiss();
                    showProgressDialog(getResources().getString(R.string.download_signature));
                }
            }
        });
        builder.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                isDismissDialog(false, dialog);
                dialog.dismiss();


            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void commit() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ConstantValue.KEY_SERVER_URL, url);
        editor.putString(ConstantValue.KEY_USER_ID, keySN);
        editor.commit();
    }

    private void isDismissDialog(boolean isForce, DialogInterface dialog) {
        if (isForce) {
            try {
                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                field.setAccessible(true);
                field.set(dialog, false);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            try {
                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                field.setAccessible(true);
                field.set(dialog, true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void showProgressDialog(String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
            progressDialog.setTitle("");
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            //progressDialog.setOnCancelListener(this);
        } else {
            progressDialog.setMessage(message);
        }
    }

    //private String signSN;
    private void showSignaturePictureList() {
//		final GridAdapter adapter = new GridAdapter(context, signatureInfos);
//		GridView gridView = (GridView) View.inflate(context, R.layout.sign_picture_list, null);
//		gridView.setAdapter(adapter);
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//									int position, long id) {
//				signId = position;
//				adapter.setSelectedId(signId);
//				adapter.notifyDataSetChanged();
//			}
//		});

        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle(R.string.signature_list);
//		builder.setView(gridView);
        builder.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                isDismissDialog(false, dialog);
                dialog.dismiss();
                signId = -1;
            }
        });

        builder.setPositiveButton(R.string.btn_sure, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (signId != -1) {
                    isDismissDialog(false, dialog);
                    dialog.dismiss();
//					byte[] b = signatureInfos.get(signId).getSignData();
//					Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
//					//openLocateSignature(bmp, 4, false);
//					openLocateSignature(bmp);
//					/*signSN = signatureInfos.get(signId).getSignSN();
//					final String userName = signatureInfos.get(signId).getUserName();
//					new Thread(new Runnable(){
//
//						@Override
//						public void run() {
//							SignatureServer.saveLog(keySN, url, "kinggrid", signSN, userName);
//						}
//
//					}).start();*/
//
//					IAppPDFActivity.userName = signatureInfos.get(signId).getUserName();
                    signId = -1;
                } else {
                    isDismissDialog(true, dialog);
                    Toast.makeText(context, R.string.no_selected, Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void startTFSingature() {
        try {
            //if(hasFieldInDocument(stamp_filed_name)){
            List<String> devices = new ArrayList<String>();
//			int rtn = tfUtil.getDeviceList(devices);
//			if(rtn == TFUtil.SUCCESS_CODE){
//				if(devices != null && devices.size() > 0){
//
//					if(devices.size() > 1){
//						//showChoiceDialog to get devices name
//						String[] items = devices.toArray(new String[devices.size()]);
//						showChoiceDialog("选择设备", items).setOnDismissListener(new OnDismissListener() {
//
//							@Override
//							public void onDismiss(DialogInterface dialog) {
//								connectDevice(choice_item_msg);
//							}
//						});;
//
//					} else {  //只有一个证书
//						String devName = devices.get(0);
//						connectDevice(devName);
//					}
//
//				} else {
//					Toast.makeText(context, "暂无设备", Toast.LENGTH_SHORT).show();
//				}
//			} else {
//				Toast.makeText(context, tfUtil.getMessageByCode(rtn), Toast.LENGTH_SHORT).show();
//			}

			/*} else {
				Toast.makeText(context, "该文档没有盖章域", Toast.LENGTH_SHORT).show();
			}*/
        } catch (Exception e) {
            Log.d("tbz", "e = " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void changeButtonState(int flag) {
        if (null != signButton) {
            signButton.setTextColor(getResources().getColor(R.color.color_ff555555));
            signButton.setSelected(false);
        }
        if (null != saveButton) {
            saveButton.setTextColor(getResources().getColor(R.color.color_ff555555));
            saveButton.setSelected(false);
        }
        if (null != cancelButton) {
            cancelButton.setTextColor(getResources().getColor(R.color.color_ff555555));
            cancelButton.setSelected(false);
        }
        if (null != deleteButton) {
            deleteButton.setTextColor(getResources().getColor(R.color.color_ff555555));
            deleteButton.setSelected(false);
        }
        if (null != settingButton) {
            settingButton.setTextColor(getResources().getColor(R.color.color_ff555555));
            settingButton.setSelected(false);
        }
        if (flag == R.string.full_signature) {
            if (null != signButton) {
                signButton.setTextColor(getResources().getColor(R.color.ht_hred_title));
                signButton.setSelected(true);
            }

        } else if (flag == R.string.signature_cancel) {
            if (null != cancelButton) {
                cancelButton.setTextColor(getResources().getColor(R.color.ht_hred_title));
                cancelButton.setSelected(true);
            }

        } else if (flag == R.string.document_save) {
            if (null != saveButton) {
                saveButton.setTextColor(getResources().getColor(R.color.ht_hred_title));
                saveButton.setSelected(true);
            }

        } else if (flag == R.string.full_signature_delete) {
            if (null != deleteButton) {
                deleteButton.setTextColor(getResources().getColor(R.color.ht_hred_title));
                deleteButton.setSelected(true);
            }

        } else if (flag == R.string.text_note) {
            if (null != settingButton) {
                settingButton.setTextColor(getResources().getColor(R.color.ht_hred_title));
                settingButton.setSelected(true);
            }

        }
        if (null != signButton && null != settingButton && signButton.isSelected()) {
            settingButton.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.btn_table_set_nomal), null, null);
        } else if (null != settingButton) {
            settingButton.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.btn_table_set_state), null, null);
        }
    }

    MyButton signButton;
    MyButton saveButton;
    MyButton cancelButton;
    MyButton deleteButton;
    MyButton settingButton;


    private void initViewMap() {
        viewMap = new SparseArray<MyButton>();
        saveButton = new MyButton(context,
                R.drawable.btn_table_save_state, R.string.document_save);
        viewMap.put(KEY_DOCUMENT_SAVE, saveButton);
//		viewMap.put(KEY_SINGER, new MyButton(context,
//				R.drawable.kinggrid_signature, R.string.signature));
//		viewMap.put(KEY_SINGER_DEL, new MyButton(context,
//				R.drawable.kinggrid_signature_del, R.string.signature_delete));
        signButton = new MyButton(context,
                R.drawable.btn_table_handwriting_state, R.string.full_signature);
        viewMap.put(KEY_FULL_SINGER, signButton);
        deleteButton = new MyButton(context,
                R.drawable.btn_table_delete_state,
                R.string.full_signature_delete);
        viewMap.put(KEY_DEL_FULL_SINGER, deleteButton);
        settingButton = new MyButton(context,
                R.drawable.btn_table_set_state,
                R.string.text_note);
        viewMap.put(SIGN_SETTING, settingButton);
//		viewMap.put(KEY_TEXT_NOTE, new MyButton(context,
//				R.drawable.kinggrid_annot, R.string.text_note));
//		viewMap.put(KEY_DEL_TEXT_NOTE, new MyButton(context,
//				R.drawable.kinggrid_annot_delete, R.string.note_delete));
//		viewMap.put(KEY_SOUND_NOTE, new MyButton(context,
//				R.drawable.kinggrid_annot_sound, R.string.sound_note));
//		viewMap.put(KEY_DEL_SOUND_NOTE,
//				new MyButton(context, R.drawable.kinggrid_annot_sound_del,
//						R.string.delete_sound_note));
//		viewMap.put(KEY_NOTE_LIST, new MyButton(context,
//				R.drawable.kinggrid_annot_postil_list, R.string.note_list));
//		viewMap.put(KEY_TEXT_LIST, new MyButton(context,
//				R.drawable.kinggrid_annot_text_list, R.string.text_note_list));
//		viewMap.put(KEY_SOUND_LIST, new MyButton(context,
//				R.drawable.kinggrid_annot_sound_list, R.string.annot_sound_list));
//		viewMap.put(KEY_BOOKMARK_LIST, new MyButton(context,
//				R.drawable.kinggrid_outline, R.string.bookmark_list));
//		viewMap.put(KEY_CAMERA, new MyButton(context,
//				R.drawable.kinggrid_camera, R.string.camera));
//		viewMap.put(KEY_LOCAL_DIGITAL_SIGNATURE, new MyButton(context,
//				R.drawable.kinggrid_signature_add, R.string.local_digital_signature));
//		viewMap.put(KEY_DIGITAL_SIGNATURE, new MyButton(context,
//				R.drawable.kinggrid_signature_add, R.string.digital_signature));
		/*viewMap.put(KEY_VERIFY, new MyButton(context,
				R.drawable.kinggrid_signature_verify, R.string.verify));*/
//		viewMap.put(KEY_AREA, new MyButton(context,
//				R.drawable.kinggrid_signer_area, R.string.area));
//		viewMap.put(KEY_SAVE_PAGES, new MyButton(context,
//				R.drawable.kinggrid_save_pages, R.string.save_pages));
//		viewMap.put(KEY_FIELD_CONTENT, new MyButton(context,
//				R.drawable.kinggrid_save_textfield, R.string.save_field_content));
//		viewMap.put(KEY_SAVEAS, new MyButton(context,
//				R.drawable.kinggrid_save_as, R.string.document_saveas));
//		viewMap.put(KEY_TF_SIGNATURE, new MyButton(context,
//				R.drawable.kinggrid_signature_add, R.string.tf_digital_signature));
		/*viewMap.put(KEY_ADD_ATTACHMENT, new MyButton(context,
				R.drawable.kinggrid_signature_add, R.string.add_attachment));*/
//		viewMap.put(KEY_LOCAL_SIGNATURE, new MyButton(context,
//				R.drawable.kinggrid_signature_add, R.string.local_signature));


//		if(isUseEbenSDK){
//			viewMap.get(KEY_FULL_SINGER).setVisibility(View.GONE);
//		}
//		viewMap.put(KEY_SEARCH, new MyButton(context,
//				R.drawable.kinggrid_signer_full, R.string.search));

    }

    private void createStampDialog() {
        final AlertDialog.Builder builder = new Builder(context);

        final TextView tv = new TextView(context);
        tv.setText("请输入页码：");
        LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(tv_params);

        final EditText et = new EditText(context);
        LinearLayout.LayoutParams et_params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(et_params);


        LinearLayout layout = new LinearLayout(context);
        layout.addView(tv);
        layout.addView(et);

        builder.setView(layout);


        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pageNo = Integer.parseInt(et.getEditableText().toString());
                deletaAnnotationInPage(TYPE_ANNOT_STAMP, pageNo);
                refreshPage(pageNo - 1);
            }
        });
        builder.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final Dialog dialog1 = builder.create();
        dialog1.setCancelable(false);
        dialog1.show();
    }

    private void createDialog(final int type) {
        final AlertDialog.Builder builder = new Builder(context);
        switch (type) {
            case TYPE_ANNOT_SIGNATURE:
                builder.setMessage(getString(R.string.dialog_message_annot_signature));
                break;
            case TYPE_ANNOT_STAMP:
                builder.setMessage(getString(R.string.dialog_message_annot_hardwrite));
                break;
            case TYPE_ANNOT_TEXT:
                builder.setMessage(getString(R.string.dialog_message_annot_text));
                break;
            case TYPE_ANNOT_SOUND:
                builder.setMessage(getString(R.string.dialog_message_annot_sound));
        }
        builder.setTitle(getString(R.string.dialog_title));
        builder.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteAllAnnotations(type);
                        refreshDocument();
                    }
                });
        builder.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final Dialog dialog1 = builder.create();
        dialog1.setCancelable(false);
        dialog1.show();
    }

    /**
     * 打开批注或注释的列表
     *
     * @param subType 批注类型
     */
    private void openAnnotationList(final int subType) {
        note_dlg = new NoteDialog(context, BookShower.this, subType);
        note_dlg.show();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                final ArrayList<Annotation> newNoteList = /*getAnnotationNewList*/getAnnotationList("", subType);
                final Message msg = new Message();
                msg.what = MSG_WHAT_LOADANNOTCOMPLETE;
                final Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("annot_list", newNoteList);
                msg.setData(bundle);
                myHandler.sendMessage(msg);
            }

        }).start();
    }

    /**
     * 打开大纲
     */
    private void openOutlineList() {
        final List<OutlineLink> outline = getOutlineList();
        if (outline != null && outline.size() > 0) {
            final OutlineDialog dlg = new OutlineDialog(BookShower.this, outline);
            dlg.show();
        } else {
            Toast.makeText(context, R.string.outline_missed, Toast.LENGTH_SHORT).show();
        }
    }

    // 拍照
    private void takePicture() {
        systemPhotoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        imagePath = FILEDIR_PATH + "/tempphotos/cameraphoto"
                + System.currentTimeMillis() + ".jpg";
        final ContentResolver resolver = getContentResolver();
        final Cursor cursor = resolver.query(systemPhotoUri, null, null, null,
                null);
        String lastPhotoPath;
        if (!cursor.moveToFirst()) {
            cursor.moveToLast();
            lastPhotoPath = cursor.getString(1);
        } else {
            lastPhotoPath = "";
        }
        final SharedPreferences sPreferences = getSharedPreferences(
                "photo_info", MODE_PRIVATE);
        sPreferences.edit().putString("photoPath", lastPhotoPath).commit();
        cursor.close();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File imageFile = new File(imagePath);
        final Uri fileUri = Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQUESTCODE_PHOTOS_TAKE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        systemPhotoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        final ContentResolver resolver = getContentResolver();
        if (requestCode == REQUESTCODE_PHOTOS_TAKE) {
            if (resultCode == RESULT_OK) {
                final Cursor cursor = resolver.query(systemPhotoUri, null,
                        null, null, null);
                String lastPhotoPath;
                String lastPhotoPath_old;
                if (!cursor.moveToFirst()) {
                    cursor.moveToLast();
                    lastPhotoPath = cursor.getString(1);
                    final SharedPreferences sPreferences = getSharedPreferences(
                            "photo_info", MODE_PRIVATE);
                    lastPhotoPath_old = sPreferences.getString("photoPath", "");
                    if (!lastPhotoPath.equals(lastPhotoPath_old)) {
                        final File file = new File(cursor.getString(1));
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
                cursor.close();
                insertImageByPosWithType(0, new File(imagePath), 100, 600, 200, 200, "");
            }
        }

    }


    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WHAT_DISMISSDIALOG:
                    View rm_view = (View) msg.obj;
                    hiddenViewFromPDF(rm_view);
                    hiddenViewFromPDF(handwriteView_layout);
                    if (!loadPDFReaderCache) {
                        clearPDFReadSettings();
                    }
                    final Builder builder = new Builder(context);
                    builder.setMessage(getString(R.string.dialog_message_save));
                    builder.setTitle(getString(R.string.dialog_title));
                    builder.setPositiveButton(getString(R.string.ok),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                    Log.v(TAG,
                                            "" + getIntent().hasExtra(ANNOT_TYPE));
                                    //导入导出时，暂时不进行保存文档
//									saveAndExit();
                                    saveDocument();
                                }
                            });
                    builder.setNegativeButton(getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            });

                    final Dialog dialog1 = builder.create();
                    dialog1.setCancelable(false);
                    dialog1.show();
                    break;
                case MSG_WHAT_LOADANNOTCOMPLETE:
                    //获取批注之后，更新批注清单
                    ArrayList<Annotation> list = msg.getData().getParcelableArrayList("annot_list");
                    for (int i = 0; i < list.size(); i++) {
                        Log.d("tbz", "annotId3 = " + list.get(i).getAnnotId());
                    }
                    note_dlg.updateView(list);
                    break;
                case MSG_WHAT_REFRESHDOCUMENT:
                    if (!touchEnable) {
                        touchEnable = true;
                    }
                    refreshDocument();
                    break;
                case HANDLE_TF_CONNET_ERROR:
                    if (connet_progress != null) {
                        connet_progress.dismiss();
                        connet_progress = null;
                    }
                    Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;

                case HANDLE_TF_CONNET_ALERT:
                    connet_progress.setMessage((String) msg.obj);
                    break;
                case HANDLE_TF_CONNET_SUCCESS:  //设备连接成功
                    if (connet_progress != null) {
                        connet_progress.dismiss();
                        connet_progress = null;
                    }
                    //show login dialog
                    List<String> appNames = new ArrayList<String>();
//					int rtn = tfUtil.enumApplication(appNames);
//					if (rtn != TFUtil.SUCCESS_CODE) {
//						Toast.makeText(context, "枚举应用失败，" + tfUtil.getMessageByCode(rtn), Toast.LENGTH_SHORT).show();
//					} else {
//						if (appNames != null && appNames.size() > 0) {
//
//							if (appNames.size() > 1) {
//								// showChoiceDialog to get devices name
//								String[] items = appNames.toArray(new String[appNames
//										.size()]);
//								showChoiceDialog("选择应用", items)
//										.setOnDismissListener(
//												new OnDismissListener() {
//
//													@Override
//													public void onDismiss(
//															DialogInterface dialog) {
//														loginDevice(choice_item_msg);
//													}
//												});
//								;
//
//							} else {
//								loginDevice(appNames.get(0));
//							}
//						}
//					}
                    break;
                case HANDLE_TF_LOGIN_SUCCESS:
                    if (login_progress != null) {
                        login_progress.dismiss();
                        login_progress = null;
                    }
                    //选择容器
                    showContainerDialog();
                    break;
                case HANDLE_TF_LOGIN_ERROR:
                    if (login_progress != null) {
                        login_progress.dismiss();
                        login_progress = null;
                    }
                    Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;

                case HANDLE_TF_GETCERT_SUCCESS:
                    if (container_list != null && container_list.size() > 0) {
                        Log.d("tbz", "HANDLE_TF_GETCERT_SUCCESS include");
                        //取消进度条
                        if (containerDialog != null) {
                            containerDialog.dismiss();
                        }
                        if (container_list.size() > 1) {
                            View view = LayoutInflater.from(context).inflate(R.layout.container_list, null);
                            ListView container_listView = (ListView) view.findViewById(R.id.list_container);
                            SimpleAdapter adapter = new SimpleAdapter(context, container_list, R.layout.container_list_item,
                                    new String[]{"container_name",
                                            "container_type",
                                            "is_sign",
                                            "sign_length",
                                            "is_exch",
                                            "exch_length"},
                                    new int[]{R.id.tv_container_name,
                                            R.id.tv_container_type,
                                            R.id.tv_is_sign,
                                            R.id.tv_sign_length,
                                            R.id.tv_is_exch,
                                            R.id.tv_exch_length});


                            container_listView.setAdapter(adapter);
                            container_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View view, int positon, long arg3) {
                                    if (container_list.get(positon).get("is_sign").contains("无")) {
                                        Toast.makeText(context, "没有签名证书，无法进行签名操作", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String containerName = container_list.get(positon).get("container_name");
                                        doTFKeySign(containerName);
                                    }
                                }
                            });
                            if (cert_dialog != null) {
                                cert_dialog.dismiss();
                                cert_dialog = null;
                            }
                            cert_dialog = new AlertDialog.Builder(context).create();
                            cert_dialog.setView(view);
                            cert_dialog.show();
                            cert_dialog.setCancelable(true);
                        } else {
                            Log.d("tbz", "doTFKeySign start");
                            doTFKeySign(container_list.get(0).get("container_name"));
                        }
                    } else {
                        //取消进度条
                        containerView_alert.setText("应用内无可用容器");
                    }
                    break;
                case HANDLE_TF_GETCERT_ERROR:
                    //取消进度条
                    containerView_alert.setText((String) msg.obj);
                    break;
                case HANDLE_TF_GETCERT_ALERT:
                    containerView_alert.setText((String) msg.obj);
                    break;
                case MSG_NET_ERROR:
                    progressDialog.dismiss();
                    Toast.makeText(context, R.string.network_connection_error, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_VERIFYPASSWORD_FAIL:
                    progressDialog.dismiss();
                    String error = null;
//					int errorType = SignatureServer.getErrorType();
//					Log.d("tbz","error type = " + errorType);
//					if(errorType == SignatureServer.ERROR_SERVER_CONNECT_FAILED){
//						error = "服务器连接失败!";
//					}else if(errorType == SignatureServer.ERROR_SERVER_RUN_EXCEPTIONALLY){
//						error = SignatureServer.getErrorMsg();
//					}else if(errorType == SignatureServer.ERROR_SERVER_CLIENT_EXCEPTION_OCCURRED){
//						error = "程序发生异常！";
//					}

                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_GET_SIGNATURE_FAIL:
                    progressDialog.dismiss();
                    Toast.makeText(context, R.string.get_signature_fail, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_GET_SIGNATURE_SUCCESS:
                    progressDialog.dismiss();
                    showSignaturePictureList();
                    break;
//				case MSG_GET_CERT_SUCCESS:
//					progressDialog.dismiss();
//
//					try {
//						File file = new File(SDCARD_PATH + "/1.pfx");
//						FileOutputStream out = new FileOutputStream(file);
//						out.write(cert_info.getCert());
//						out.flush();
//						out.close();
//
//						doSaveDigSignByPos(SDCARD_PATH + "/1.pfx", cert_info.getPassword(),imagePath,insertPageNo, signatureX, signatureY,
//								signature_width, signature_height,new DigSignCallBack(){
//
//									@Override
//									public void onFinish(boolean arg0, String arg1) {
//									}
//
//								});
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//
//					break;
//				case MSG_GET_CERT_FAIL:
//					progressDialog.dismiss();
//					Toast.makeText(context, R.string.get_cert_fail, Toast.LENGTH_SHORT).show();
//					break;
                case MSG_SHOW_ANNOT_INFO:
                    Log.d("tbz", "send msg annot info");
                    Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /*
     * 显示保存提示框
     */
    private void showSaveDialog(Context context) {
        View v = View.inflate(context, R.layout.save_dialog_layout, null);
        final Button cancel = (Button) v.findViewById(R.id.btn_cancel);
        final Button no_save = (Button) v.findViewById(R.id.btn_no_save);
        final Button save = (Button) v.findViewById(R.id.btn_save);

        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }

        });

        no_save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                if (loadPDFReaderCache) {
                    savePDFReadSettings();
                }
                closeDocument();
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }

        });

        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                saveAndExit();
            }

        });

        dialog.setContentView(v);
        dialog.setCancelable(false);
        dialog.show();
        getController().setDialogWindowSize(dialog);
    }

    /**
     * 保存并退出金格控件
     */
    private void saveAndExit() {
        if (!isUseEbenSDK) {
            if (getIntent().hasExtra(ANNOTPROTECTNAME)) {
                boolean isAnnotProtect = getIntent().getBooleanExtra(ANNOTPROTECTNAME, false);
                setAllAnnotationsOnlyRead(isAnnotProtect);//设置所以批注是否为只读
            }
            if (isSupportEbenT7Mode) {
                doSaveHandwriteInfo(true, false, getT7ModePDFHandwriteView());
            }

            saveDocument();
            if (loadPDFReaderCache) {
                Log.v("tbz", "save reader setting on exit");
                savePDFReadSettings();
            }
            closeDocument();
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());//必需要杀掉进程
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if((keyCode == KeyEvent.KEYCODE_BACK)){
//			if(!isDocumentModified()){
//				final AlertDialog.Builder builder = new Builder(context);
//				builder.setTitle(getString(R.string.dialog_title));
//				builder.setMessage(getString(R.string.close_doc_title));
//				builder.setPositiveButton(getString(R.string.ok),
//						new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								dialog.dismiss();
//								Log.d("tbz","offsetY = " + getOffsetY());
//								if(loadPDFReaderCache){
//									savePDFReadSettings();
//								}
//								closeDocument();
//								finish();
//								android.os.Process.killProcess(android.os.Process
//										.myPid());
//							}
//						});
//				builder.setNegativeButton(getString(R.string.cancel),
//						new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								dialog.dismiss();
//							}
//						});
//
//				final Dialog dialog1 = builder.create();
//				dialog1.setCancelable(false);
//				dialog1.show();
//			}else{
//				showSaveDialog(context);
//			}
//		}
        return super.onKeyDown(keyCode, event);
    }

    //显示证书信息
    public void showCertificateInfo(String[] infos) {
        LinearLayout ll = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.certificateinfo_layout, null);

        TextView issuer = (TextView) ll.findViewById(R.id.issuer);
        TextView before_date = (TextView) ll.findViewById(R.id.beforedate);
        TextView after_date = (TextView) ll.findViewById(R.id.afterdate);
        TextView subject = (TextView) ll.findViewById(R.id.subject);

        issuer.setText(infos[0]);
        before_date.setText(infos[1]);
        after_date.setText(infos[2]);
        subject.setText(infos[3]);

        PopupWindow popup = new PopupWindow(ll, (int) screenWidth / 2, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setOutsideTouchable(true);
        popup.showAtLocation(frameLayout, Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    ;


    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.kinggrid.pages.bmp.save")) {
                isLocked = false;
                Toast.makeText(context, "保存页面图片完毕", Toast.LENGTH_SHORT).show();
            }
            if (action.equals(SAVESIGNFINISH_ACTION)) {
                if (loadPDFReaderCache) {
                    savePDFReadSettings();
                }
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    };

    /*
     * 初始化全文批注界面中的按钮，并设置按钮点击事件的监听接口
     */
    private void initBtnView(final View layout) {
        btnClose = (Button) layout.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (full_handWriteView.getEarseState()) {
                    full_handWriteView.cancelEarseHandwriteInfo();
                    btnUndo.setVisibility(View.VISIBLE);
                    btnRedo.setVisibility(View.VISIBLE);
                    btnClear.setVisibility(View.VISIBLE);
                    btnPen.setVisibility(View.VISIBLE);
                    btnSave.setVisibility(View.VISIBLE);
                    btnEarse.setVisibility(View.VISIBLE);
                } else {
                    doCloseHandwriteInfo(layout, full_handWriteView);
                }

            }
        });

        btnSave = (Button) layout.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                savePdfSign();
            }
        });

        btnEarse = (Button) layout.findViewById(R.id.btn_earse);
        btnEarse.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                full_handWriteView.doEarseHandwriteInfo();
                btnUndo.setVisibility(View.GONE);
                btnRedo.setVisibility(View.GONE);
                btnClear.setVisibility(View.GONE);
                btnPen.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
                btnEarse.setVisibility(View.GONE);
            }

        });

        btnUndo = (Button) layout.findViewById(R.id.btn_undo);
        btnUndo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                full_handWriteView.doUndoHandwriteInfo();
            }
        });

        btnRedo = (Button) layout.findViewById(R.id.btn_redo);
        btnRedo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                full_handWriteView.doRedoHandwriteInfo();
            }
        });

        btnClear = (Button) layout.findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                full_handWriteView.doClearHandwriteInfo();
            }
        });

        btnPen = (Button) layout.findViewById(R.id.btn_pen);
        btnPen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                full_handWriteView.setPenSettingInfoName("demo_type", "demo_size", "demo_color");
                full_handWriteView.doSettingHandwriteInfo();
            }
        });


        company_textView = (TextView) layout.findViewById(R.id.company_name);
        company_textView.setText(companyName);
        bottomBar = (RelativeLayout) layout.findViewById(R.id.bottom);

    }

    public void savePdfSign() {
        if (null != full_handWriteView && full_handWriteView.canSave()) {
            //添加等待交互
            if (insertVertorFlag != 0) {
                return;
            }
            final View dialog_view = getLayoutInflater().inflate(R.layout.insert_progress, null);
            showViewToPDF(dialog_view);
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    Log.v("tbz", "doSaveHandwriteInfo run");
                    doSaveHandwriteInfo(true, false, full_handWriteView);
                    SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PDFSIGNSAVE, Context.MODE_PRIVATE);
                    sp.edit().putBoolean("pdfsignsave", true).commit();

                    //取消等待交互
                    Message msg = new Message();
                    msg.what = MSG_WHAT_DISMISSDIALOG;
                    msg.obj = dialog_view;
                    myHandler.sendMessage(msg);

                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        } else {
            Toast.makeText(context, "没有保存内容", Toast.LENGTH_SHORT).show();
        }
    }

    private void showTextAnnotWindow() {
        TextAnnotDialog textAnnot = new TextAnnotDialog(BookShower.this, null);
        textAnnot.setDeleteBtnGone();
        textAnnot.setSaveAnnotListener(new TextAnnotDialog.OnSaveAnnotListener() {

            @Override
            public void onAnnotSave(Bitmap bitmap, String time) {
                Bitmap username_bmp;
                try {
                    Rect padding = new Rect(0, 0, 0, 0);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    username_bmp = BitmapFactory.decodeStream(getResources().getAssets().open("signature.gif"), padding, options);
                    Bitmap word_bmp = BitmapUtil.getInstance().getStampBitmap(bitmap, username_bmp, time);
                    openLocateSignature(word_bmp, 0);
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }

            @Override
            public void onAnnotSave(Annotation annotTextNew) {
                // TODO Auto-generated method stub

            }
        });
    }


/**********************************************批注导入导出功能实现**************************************************************************************************/

    /**
     * @param fileString
     */
    @SuppressWarnings("deprecation")
    private void writeStringToTxt(String fileString) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(Environment
                    .getExternalStorageDirectory() + "/test_export.txt");
            outputStream.write(fileString.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void writeToFile(String path, byte[] data) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path);
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数组转换为JSON格式的数据。
     *
     * @param stoneList 数据源
     * @return JSON格式的数据
     */
    public String annotationToJson(List<Annotation> annotList) {
        try {
            JSONArray array = new JSONArray();
            //JSONObject object = new JSONObject();
            int length = annotList.size();
            for (int i = 0; i < length; i++) {
                Annotation annot = annotList.get(i);
                JSONObject jsonObject = new JSONObject();
                String authorId = annot.getAuthorId();
                String authorName = annot.getAuthorName();
                String pageNo = annot.getPageNo();
                String X = annot.getX();
                String Y = annot.getY();
                String width = annot.getWidth();
                String height = annot.getHeight();
                String styleName = annot.getStyleName();
                String createTime = annot.getCreateTime();
                String annotContent = annot.getAnnoContent();
                String unType = annot.getUnType();
                String annotId = annot.getAnnotId();
                jsonObject.put("annotId", annotId);
                Log.d("tbz", "annotId = " + annotId);
                jsonObject.put("authorId", authorId);
                jsonObject.put("authorName", authorName);
                jsonObject.put("unType", unType);
                jsonObject.put("styleName", styleName);
                jsonObject.put("pageNo", pageNo);
                jsonObject.put("X", X);
                jsonObject.put("Y", Y);
                jsonObject.put("width", width);
                jsonObject.put("height", height);
                jsonObject.put("createTime", createTime);
                jsonObject.put("annotContent", annotContent);
                if (annot.getStyleName().equals("Stamp")) {
                    jsonObject.put("styleId", "12");
                    if (annotContent != null) {
                        if (annotContent.startsWith("q")) {
                            jsonObject.put("annotSignature", "");
                        } else {
                            jsonObject.put("annotSignature", bytesToHexString(getBytesFromFile(new File(annotContent))));
                        }
                    }
                } else if (annot.getStyleName().equals("Text")) {
                    jsonObject.put("styleId", "0");
                    jsonObject.put("annotSignature", "");
                } else if (annot.getStyleName().equals("Sound")) {
                    jsonObject.put("styleId", "17");
                    jsonObject.put("annotSignature", "");
                    jsonObject.put("annotRate", String.valueOf(annot.getSoundRate()));
                    jsonObject.put("annotChannels", String.valueOf(annot.getSoundChannels()));
                    jsonObject.put("annotBitspersample", String.valueOf(annot.getSoundBitspersample()));
                    jsonObject.put("soundData", bytesToHexString(annot.getSoundData()));
                }
                array.put(jsonObject);
            }

            Log.d("Kevin", "array.toString() : " + array.toString());
            return array.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] hexStr2Bytes(String hex) {
        if (hex == null || hex.equals("")) {
            return null;
        }
        hex = hex.toUpperCase();
        int len = hex.length() / 2;
        char[] hexChars = hex.toCharArray();
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            b[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }

        return b;
    }

    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Annotation> getAnnotsFromJson(String json) {
        Log.d("Kevin", " getAnnotsFromJson ");
        ArrayList<Annotation> annotList = new ArrayList<Annotation>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            Log.d("Kevin", " jsonArray.length() : " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                Annotation annotation = new Annotation();
                if (jsonObject2.has("annotId")) {
                    annotation.setAnnotId(jsonObject2.getString("annotId"));
                }
                annotation.setPageNo(jsonObject2.getString("pageNo"));
                annotation.setX(jsonObject2.getString("X"));
                annotation.setY(jsonObject2.getString("Y"));
                annotation.setWidth(jsonObject2.getString("width"));
                annotation.setHeight(jsonObject2.getString("height"));
                annotation.setCreateTime(jsonObject2.getString("createTime"));
                annotation.setAuthorName(jsonObject2.getString("authorName"));
                annotation.setAuthorId(jsonObject2.getString("authorId"));
                String styleName = "";
                if (jsonObject2.has("styleName")) {
                    styleName = jsonObject2.getString("styleName");
                }
                annotation.setStyleName(styleName);

                String unType = "";
                if (jsonObject2.has("unType")) {
                    unType = jsonObject2.getString("unType");
                }
                annotation.setUnType(unType);

                if (jsonObject2.getString("styleName").equals("Stamp")
                        || jsonObject2.getString("styleName").equals("test") || jsonObject2.getString("styleName").equals("unknown")) {
                    String annotContent = jsonObject2.getString("annotContent");
                    if (annotContent.startsWith("q")) {
                        annotation.setAnnoContent(annotContent);
                    } else {
                        byte[] annotSignature = hexStr2Bytes(jsonObject2.getString("annotSignature"));
                        annotContent = IAppPDFActivity.signaturePath + jsonObject2.getString("unType");
                        File file = new File(annotContent);
                        if (!file.exists()) {
                            file.mkdirs();
                            try {
                                file.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        annotation.setAnnoContent(annotContent);
                        writeToFile(annotContent, annotSignature);
                    }
                }
                if (jsonObject2.getString("styleName").equals("Text")) {
                    String textContent = jsonObject2.getString("annotContent");
                    annotation.setAnnoContent(textContent);
                }

                if (jsonObject2.getString("styleName").equals("Sound")) {
                    annotation.setSoundRate(Integer.valueOf(jsonObject2.getString("annotRate")));
                    annotation.setSoundChannels(Integer.valueOf(jsonObject2.getString("annotChannels")));
                    annotation.setSoundBitspersample(Integer.valueOf(jsonObject2.getString("annotBitspersample")));
                    annotation.setSoundData(hexStr2Bytes(jsonObject2.getString("soundData")));
                }

                annotList.add(annotation);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return annotList;
    }

    /**
     * @param string
     */
    private String readFromTxt(String fileName) {
        String content = "";
        Log.d("Kevin", fileName);
        try {
            FileInputStream inputStream = new FileInputStream(fileName);

            byte[] bytes = new byte[1024 * 4];
            Log.d("Kevin", "bytes");
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            Log.d("Kevin", "inputStream");
            inputStream.close();
            arrayOutputStream.close();
            content = new String(arrayOutputStream.toByteArray());
            Log.d("Kevin", content);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }


}
