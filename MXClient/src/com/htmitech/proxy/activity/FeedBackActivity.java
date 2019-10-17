package com.htmitech.proxy.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.htmitech.api.BookInit;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.photoselector.model.PhotoModel;
import com.htmitech.photoselector.myinterface.CallBackImageSelectImpOne;
import com.htmitech.photoselector.ui.PhotoPreviewActivity;
import com.htmitech.photoselector.ui.PhotoSelectorActivity;
import com.htmitech.pop.SelectPicPopupWindow;
import com.htmitech.proxy.doman.GetDicInfoResult;
import com.htmitech.proxy.doman.GetDicparameter;
import com.htmitech.proxy.doman.GetPictureResult;
import com.htmitech.proxy.doman.OutputInfo;
import com.htmitech.proxy.doman.OutputInfoResult;
import com.htmitech.proxy.doman.PictureInfo;
import com.htmitech.proxy.doman.SubmitDicResult;
import com.htmitech.proxy.doman.SubmitDicparameter;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.unit.ActivityUnit;
import com.htmitech.unit.DensityUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by joe on 2017/5/5.
 * 反馈意见入口
 */

public class FeedBackActivity extends BaseFragmentActivity implements ObserverCallBackType, View.OnClickListener, CallBackImageSelectImpOne {

    private static final String TAG = "FeedBackActivity";
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int IMAGE_REQUEST_DRAW = 102;

    public List<OutputInfo> mOutputInfoList;
    public LinearLayout layout_feed_back_type;
    private String getDicListUrl = "";
    private String getPictureUrl = "";
    private String getSubmitUrl = "";
    private GetDicparameter mGetDicparameter;
    private Gson mGson = new Gson();
    private String jsonToString;
    private static final String HTTPTYPE = "getDicList";
    private static final String HTTPTYPESUBMIT = "getDicSubmit";
    private static final String HTTPTYPEPICTUTR = "getDicPicture";
    private GetDicInfoResult mGetDicInfoResult;
    private OutputInfoResult mOutputInfoResult;
    private ArrayList<OutputInfoResult> mOutputInfoResultList;
    private TextView checkView;
    private String input_type = "";
    private EditText et_feed_back;
    private LinearLayout ll_feed_back_img;
    private ImageView iv_feed_back_add;
    private List<String> listImg = new ArrayList<String>();
    private SelectPicPopupWindow menuWindow;
    private ImageButton title_left_button;
    private TextView title_name;
    private ImageButton title_right_image_button;
    private File mFileTemp;
    private GetPictureResult mGetPictureResult;
    private PictureInfo mPictureInfo;
    private List<Integer> listImgId = new ArrayList<Integer>();
    private ArrayList<DamageCaeck> mDamageCaeckList = new ArrayList<DamageCaeck>();
    private SubmitDicResult mSubmitDicResult;
    private OutputInfo mOutputInfoTemp;
    private String app_id;
    private String app_version_id;
    private String userId;
    private String portal_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        initView();
        initData();
    }

    private void initView() {
        BookInit.getInstance().setmCallBackImageSelectImpOne(this);
        layout_feed_back_type = (LinearLayout) findViewById(R.id.layout_feed_back_type);
        et_feed_back = (EditText) findViewById(R.id.et_feed_back);
        ll_feed_back_img = (LinearLayout) findViewById(R.id.ll_feed_back_img);
        iv_feed_back_add = (ImageView) findViewById(R.id.iv_feed_back_add);
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        title_left_button.setOnClickListener(this);
        title_name = (TextView) findViewById(R.id.title_name);
        title_right_image_button = (ImageButton) findViewById(R.id.title_right_image_button_send);
        title_right_image_button.setOnClickListener(this);
        iv_feed_back_add.setOnClickListener(new AddImageViewOnClick());

    }

    private void initData() {
        title_name.setText("反馈意见");
        title_name.setVisibility(View.VISIBLE);
        title_right_image_button.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        app_id = intent.getStringExtra("app_id");
        app_version_id = intent.getStringExtra("app_version_id");
        portal_id = BookInit.getInstance().getPortalId();
        userId = OAConText.getInstance(this).UserID;
        getDicListUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.DiccontrollerGetDicList;
        mGetDicparameter = new GetDicparameter();
        mGetDicparameter.setTableName("cs_feedback");
        mGetDicparameter.setFieldName("cs_feedback_type");
        jsonToString = mGson.toJson(mGetDicparameter);
        AnsynHttpRequest.requestByPostWithToken(this, jsonToString, getDicListUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPE, LogManagerEnum.GGENERAL.getFunctionCode());


    }

    private void initDicForm() {
        // TODO Auto-generated method stub
        if (mOutputInfoResultList == null) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(FeedBackActivity.this);
        for (int i = 0; i < mOutputInfoResultList.size(); i++) {
            View view = inflater.inflate(R.layout.fragment_statiscal_report_titletext, null);
            view.setVisibility(View.GONE);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_title.setText(mOutputInfoResultList.get(i).getTitle());
            ImageView iv_down = (ImageView) view.findViewById(R.id.iv_down);
            layout_feed_back_type.addView(view);

            LinearLayout layout_child = new LinearLayout(FeedBackActivity.this);
            layout_child.setOrientation(LinearLayout.VERTICAL);
            layout_child.setGravity(Gravity.CENTER_VERTICAL);

            LinearLayout.LayoutParams layoutParams_child = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layout_child.setLayoutParams(layoutParams_child);
            layout_feed_back_type.addView(layout_child);

            iv_down.setOnClickListener(new ImageDownOnClick(i, layout_child));
            List<OutputInfo> mDictitemlistList = mOutputInfoResultList.get(i).getOutputInfo();
            WindowManager wm = FeedBackActivity.this.getWindowManager();
            int width = wm.getDefaultDisplay().getWidth();
            int num = 0;
            int textHight = 70;
            if (width == 1080) {
                textHight = 120;
            } else {
                textHight = DensityUtil.dip2px(FeedBackActivity.this, 32);
            }

            LinearLayout layout = new LinearLayout(FeedBackActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, textHight);
            layoutParams.setMargins(0, 10, 20, 10);
            layout.setLayoutParams(layoutParams);
            layout.setOrientation(LinearLayout.HORIZONTAL);
        /*
         * 四个字 每个TextView的宽度
		 */
            int colum4 = (width - 120) / 4;
        /*
		 *两个字 每个TextView的宽度
		 */
            int colum2 = (width - 40) / 2;
		/*
		 *一个字 每个TextView的宽度
		 */
            int colum1 = (width - 20) / 1;
            int indexColum4 = 0, indexColum2 = 0, indexColum1 = 0;
            for (OutputInfo dic : mDictitemlistList) {
                num = (dic.field_text.length() * 34); //34是根据 字的大小和padding的左右宽度而来的 14 +
                if (num < colum4) {
                    if (indexColum4 == 0) {
                        layout = new LinearLayout(FeedBackActivity.this);
                        layout.setLayoutParams(layoutParams);
                    }
                    TextView textView = new TextView(FeedBackActivity.this);
                    LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                            colum4, textHight, 1);
                    textparams.setMargins(20, 0, 0, 0);
                    textView.setText(dic.field_text);
                    textView.setLayoutParams(textparams);
                    textView.setTextSize(14);
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(10, 10, 10, 10);
                    textView.setOnClickListener(new NameOnClickListener(layout_child, dic, textView));
                    if (dic.field_text.equals(mDictitemlistList.get(0).getField_text())) {
                        mOutputInfoTemp = dic;
                        textView.setTextColor(getResources().getColor(R.color.ht_hred_title));
                        textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                    } else {
                        textView.setTextColor(getResources().getColor(R.color.buttom_color));
                        textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_noselect);
                    }
                    layout.addView(textView);
                    indexColum4++;
                    if (indexColum4 == 4) {
                        indexColum4 = 0;
                        layout_child.addView(layout);
                    }

                } else if (num < colum2) {
				/*
				 * 把上一个最后一个没有Add进去的给add进去
				 */
                    if (indexColum4 != 0) {
                        indexColum4 = 0;
                        layout_child.addView(layout);
                    }
                    if (indexColum2 == 0) {
                        layout = new LinearLayout(FeedBackActivity.this);
                        layout.setLayoutParams(layoutParams);
                        layout.removeAllViews();
                    }
                    TextView textView = new TextView(FeedBackActivity.this);
                    LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                            colum2, textHight, 1);
                    textparams.setMargins(20, 0, 0, 0);
                    textView.setText(dic.field_text);
                    textView.setLayoutParams(textparams);
                    textView.setTextSize(14);
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(10, 10, 10, 10);
                    if (dic.field_text.equals(mDictitemlistList.get(0).getField_text())) {
                        mOutputInfoTemp = dic;
                        textView.setTextColor(getResources().getColor(R.color.ht_hred_title));
                        textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                    } else {
                        textView.setTextColor(getResources().getColor(R.color.buttom_color));
                        textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_noselect);
                    }
                    textView.setOnClickListener(new NameOnClickListener(layout_child, dic, textView));
                    layout.addView(textView);
                    indexColum2++;
                    if (indexColum2 == 2) {
                        indexColum2 = 0;
                        layout_child.addView(layout);
                    }
                } else {
				/*
				 * 把上一个最后一个没有Add进去的给add进去
				 */
                    if (indexColum2 != 0) {
                        indexColum2 = 0;
                        layout_child.addView(layout);
                    }
                    layout = new LinearLayout(FeedBackActivity.this);
                    layout.setLayoutParams(layoutParams);
                    layout.removeAllViews();
                    TextView textView = new TextView(FeedBackActivity.this);
                    LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                            colum1, textHight, 1);
                    textparams.setMargins(20, 0, 0, 0);
                    textView.setText(dic.field_text);
                    textView.setLayoutParams(textparams);
                    textView.setTextSize(14);
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(10, 10, 10, 10);
                    if (dic.field_text.equals(mDictitemlistList.get(0).getField_text())) {
                        mOutputInfoTemp = dic;
                        textView.setTextColor(getResources().getColor(R.color.ht_hred_title));
                        textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                    } else {
                        textView.setTextColor(getResources().getColor(R.color.buttom_color));
                        textView.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_noselect);
                    }
                    textView.setOnClickListener(new NameOnClickListener(layout_child, dic, textView));
                    layout.addView(textView);
                    layout_child.addView(layout);
                }
            }
            if (indexColum4 != 0 || indexColum2 != 0) {
                layout_child.addView(layout);
            }
            layout = new LinearLayout(FeedBackActivity.this);
            layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 20);
            layout.setLayoutParams(layoutParams);
            layout_child.addView(layout);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_feed_back_add:
                break;
            case R.id.title_name:
                break;
            case R.id.title_left_button:
                FeedBackActivity.this.finish();
                break;
            case R.id.title_right_image_button_send:
                doSubmit();
                break;
        }


    }

    private void doSubmit() {
        showDialog();
        getPictureUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.FilecontrollerPicture;
        if (listImg != null && listImg.size() > 0) {
            setDialogValue("图片上传...");
            for (int i = 0; i < listImg.size(); i++) {
                mFileTemp = new File(listImg.get(i));
                AnsynHttpRequest.requestByPostWithToken(this, mFileTemp, getPictureUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPEPICTUTR, LogManagerEnum.GGENERAL.getFunctionCode());
            }
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append("");
            subOptions(buffer);
        }


    }


    public class AddImageViewOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(Intent.ACTION_PICK, null);
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            startActivityForResult(intent, IMAGE_REQUEST_CODE);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("come_where", "FeedBackActivity");
            ActivityUnit.switchTo(FeedBackActivity.this, PhotoSelectorActivity.class, map);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_REQUEST_CODE: // 相册数据
//                if (data != null) {
//                    Uri originalUri = data.getData();
//                    String[] proj = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = getContentResolver().query(originalUri, proj, null, null, null);
//                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    cursor.moveToFirst();
//                    String path = cursor.getString(column_index);
//                    Map<String, Object> params = new HashMap<String, Object>();
//                    params.put("filePath", path);
//                    ActivityUnit.switchTo(FeedBackActivity.this, DrawPaneActivity.class,IMAGE_REQUEST_DRAW, params);
//                }
                break;
            case IMAGE_REQUEST_DRAW:
                if (data != null) {
                    String result_path = data.getStringExtra("result");
                    listImg.add(result_path);
                    updateImg();
                }
        }
    }

    @Override
    public void checkImageBitUrl(String bitUrl) {
        if (bitUrl != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("filePath", bitUrl);
            params.put("app_id", app_id);
            params.put("portal_id", portal_id);
            params.put("userId", userId);
            ActivityUnit.switchTo(FeedBackActivity.this, DrawPaneActivity.class, IMAGE_REQUEST_DRAW, params);
        }
    }

    /**
     * 更新显示图片
     */
    private void updateImg() {
        ll_feed_back_img.removeAllViews();
        final LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < listImg.size(); i++) {
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.layout_pic, null);
            ImageView imageView = (ImageView) layout.findViewById(R.id.img);
            ImageView imageClose = (ImageView) layout.findViewById(R.id.img_delete);
            imageClose.setTag(i);
            Glide.with(FeedBackActivity.this).load(listImg.get(i)).
                    placeholder(com.htmitech.addressbook.R.drawable.pictures_no).
                    error(com.htmitech.addressbook.R.drawable.pictures_no).into(imageView);
//            BitmapUtils.instance().display(imageView, listImg.get(i));
            imageClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Object obj = view.getTag();
                    int tag = Integer.parseInt(String.valueOf(obj));
                    listImg.remove(tag);
                    updateImg();
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    List<PhotoModel> photoModelList = new ArrayList<PhotoModel>();
                    for (String str : listImg) {
                        PhotoModel photoModel = new PhotoModel();
                        photoModel.setOriginalPath(str);
                        photoModelList.add(photoModel);
                    }
                    map.put("photos", photoModelList);
                    map.put("position", photoModelList.size());
                    ActivityUnit.switchTo(FeedBackActivity.this, PhotoPreviewActivity.class, map);
                }
            });
            ll_feed_back_img.addView(layout);
        }
        if (listImg.size() == 3) {
            iv_feed_back_add.setVisibility(View.GONE);
        } else {
            iv_feed_back_add.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void success(String requestValue, int type, String requestfield_text) {
        if (requestfield_text.equals(HTTPTYPE)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getDicListUrl, jsonToString, this, requestfield_text, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                mGetDicInfoResult = mGson.fromJson(requestValue.toString(), GetDicInfoResult.class);
                mOutputInfoResult = mGetDicInfoResult.getResult();
                mOutputInfoResultList = new ArrayList<OutputInfoResult>();
                mOutputInfoResultList.add(mOutputInfoResult);
                if (mOutputInfoResultList != null) {
                    refresh(mOutputInfoResultList);
                }
            } else {
                Log.d(TAG, "success: " + requestValue);
                return;
            }
        } else if (requestfield_text.equals(HTTPTYPEPICTUTR)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getPictureUrl, mFileTemp, this, requestfield_text, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                mGetPictureResult = mGson.fromJson(requestValue.toString(), GetPictureResult.class);
                mPictureInfo = mGetPictureResult.getResult();
                listImgId.add(mPictureInfo.getPictureId());
                Log.d(TAG, "success: " + requestValue);

            } else {
                Log.d(TAG, "success: " + requestValue);
                return;
            }
            if (listImg != null && listImgId != null && listImg.size() == listImgId.size()) {
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < listImgId.size(); i++) {
                    buffer.append(listImgId.get(i));
                    if (i != (listImgId.size() - 1))
                        buffer.append(";");
                }
                subOptions(buffer);
            }
        } else if (requestfield_text.equals(HTTPTYPESUBMIT)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getSubmitUrl, jsonToString, this, requestfield_text, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                mSubmitDicResult = mGson.fromJson(requestValue.toString(), SubmitDicResult.class);
                Toast.makeText(FeedBackActivity.this, mSubmitDicResult.getMessage().toString(), Toast.LENGTH_SHORT).show();
                finish();
                dismissDialog();
                Log.d(TAG, "success: " + requestValue);

            } else {
                Log.d(TAG, "success: " + requestValue);
                dismissDialog();
                return;
            }


        }

    }

    public void subOptions(StringBuffer buffer) {
        setDialogValue("提交意见...");
        getSubmitUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.CsfeedbackControllerSubmit;

        SubmitDicparameter mSubmitDicparameter = new SubmitDicparameter();
        mSubmitDicparameter.setDeviceNo(android.os.Build.MODEL + "|" + android.os.Build.VERSION.RELEASE);
        mSubmitDicparameter.setDeviceType("Android");
        if (et_feed_back.getText() != null)
            mSubmitDicparameter.setRemark(et_feed_back.getText().toString());
        mSubmitDicparameter.setPicCont(listImgId.size() + "");
        mSubmitDicparameter.setPicId(buffer.toString());
        if (mOutputInfoTemp != null) {
            mSubmitDicparameter.setType(mOutputInfoTemp.getDisplay_order() + "");
        } else {
            mSubmitDicparameter.setType(0 + "");
        }
        jsonToString = mGson.toJson(mSubmitDicparameter);
        AnsynHttpRequest.requestByPostWithToken(this, jsonToString, getSubmitUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPESUBMIT, LogManagerEnum.GGENERAL.getFunctionCode());

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestfield_text) {

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    /**
     * 每个TextView的点击事件
     */
    public class NameOnClickListener implements View.OnClickListener {

        private OutputInfo mDictitemlist;

        private TextView tvName;

        private LinearLayout layout_child;

        public NameOnClickListener(LinearLayout layout_child, OutputInfo mDictitemlist, TextView tvName) {
            this.mDictitemlist = mDictitemlist;
            this.tvName = tvName;
            this.layout_child = layout_child;
        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            boolean isFlag = true;
            if (input_type == null || input_type.equals("")) {
                input_type = "503";
            }
            if (input_type.equals("503")) {
                mDamageCaeckList.clear();
                for (int i = 0; i < layout_child.getChildCount(); i++) {
                    LinearLayout mView = (LinearLayout) layout_child.getChildAt(i);
                    for (int j = 0; j < mView.getChildCount(); j++) {
                        View view = mView.getChildAt(j);
                        if (view instanceof TextView) {
                            ((TextView) view).setTextColor(getResources().getColor(R.color.buttom_color));
                            ((TextView) view).setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_noselect);
                        }
//                        view.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                    }

                }
//                tvName.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);
                tvName.setTextColor(getResources().getColor(R.color.ht_hred_title));
                tvName.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
//                GradientDrawable myGradPrevenans = (GradientDrawable) tvName.getBackground();
//                myGradPrevenans.setColor((getResources().getColor(
//                        R.color.ht_hred_title)));
                mDamageCaeckList.add(new DamageCaeck(mDictitemlist, isFlag));
                mOutputInfoTemp = mDamageCaeckList.get(0).mDictitemlist;
                checkView = tvName;
            } else {
                for (DamageCaeck mDamageCaeck : mDamageCaeckList) {
                    if (mDamageCaeck.mDictitemlist.getField_text().equals(mDictitemlist.getField_text())) {
                        mDamageCaeckList.remove(mDamageCaeck);
                        tvName.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
                        isFlag = false;
                        break;
                    }
                }
                if (isFlag) {
                    tvName.setBackgroundResource(R.drawable.fragment_gridview_adapter_down);

                    GradientDrawable myGradPrevenans = (GradientDrawable) tvName.getBackground();

                    myGradPrevenans.setColor((getResources().getColor(R.color.ht_hred_title)));
                    mDamageCaeckList.add(new DamageCaeck(mDictitemlist, isFlag));
                }
            }
        }
    }


    private class DamageCaeck {
        OutputInfo mDictitemlist;
        boolean isFlag;

        public DamageCaeck(OutputInfo mDictitemlist, boolean isFlag) {
            this.mDictitemlist = mDictitemlist;
            this.isFlag = isFlag;
        }
    }

    public class ImageDownOnClick implements View.OnClickListener {
        public int position;
        private LinearLayout layout_child;

        public ImageDownOnClick(int position, LinearLayout layout_child) {
            this.position = position;
            this.layout_child = layout_child;
        }

        @Override
        public void onClick(View v) {
            ((ImageView) v).setImageResource(layout_child.isShown() ? R.drawable.btn_angle_down_circle : R.drawable.btn_angle_up_circle);
            layout_child.setVisibility(layout_child.isShown() ? View.GONE : View.VISIBLE);
        }
    }

    public void refresh(ArrayList<OutputInfoResult> sosoResults) {
        layout_feed_back_type.removeAllViews();
        this.mOutputInfoResultList = sosoResults;
        initDicForm();
    }


}
