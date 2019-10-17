package com.htmitech.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.addressbook.PhotoActivity;
import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.app.PhoneClickText;
import com.htmitech.app.widget.UserMessageScrollView;
import com.htmitech.base.BaseFragment;
import com.htmitech.dao.TD_UserDAO;
import com.htmitech.dao.T_UserRelationshipDAO;
import com.htmitech.domain.OrgUser;
import com.htmitech.domain.PeopleMessage;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.TD_User;
import com.htmitech.domain.T_UserRelationship;
import com.htmitech.listener.AddressListener;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.myEnum.PeopleHeadEnum;
import com.htmitech.others.LoadUserAvatar;
import com.htmitech.pop.SelectPicPopupWindow;
import com.htmitech.unit.ActivityUnit;
import com.htmitech.unit.ConfigStyleUtil;
import com.htmitech.unit.DensityUtil;
import com.htmitech.unit.ImageUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户详细信息fragment 个人信息
 * 根据条件进行展示数据信息
 *
 * @author Tony
 */
public class UpdateUserDetailsMessageFragment extends BaseFragment implements View.OnClickListener {
    private AddressListener mAddressListener;
    private SYS_User mSYS_User;
    private TextView tv_add_friends;//加为常用联系人
    private TextView tv_send;//发送消息
    private TextView tv_change_vip;//特别关注
    private LinearLayout layout_message;
    private TextView default_tv;
    private LinearLayout layout_grid_view;
    private ArrayList<PeopleMessage> modelName;
    private LayoutInflater inflater;
    private UserMessageScrollView mUserMessageScrollView;
    private View footerView;
    private SelectPicPopupWindow menuWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.ht_fragment_user_details_people_update, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {/* 判断宿主activity是否实现了接口MyListener */
        super.onAttach(activity);
        try {
            mAddressListener = (AddressListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getClass().getName()
                    + " must implements interface MyListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean onBackPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        tv_send = (TextView) getView().findViewById(R.id.tv_send);
        tv_add_friends = (TextView) getView().findViewById(R.id.tv_add_friends);
        layout_message = (LinearLayout) getView().findViewById(R.id.layout_message);
        default_tv = (TextView) getView().findViewById(R.id.default_tv);
        layout_grid_view = (LinearLayout) getView().findViewById(R.id.layout_grid_view);
        mUserMessageScrollView = (UserMessageScrollView) getView().findViewById(R.id.set_scrollview);
        inflater = LayoutInflater.from(getActivity());
        LinearLayout main = (LinearLayout) getView().findViewById(R.id.main);
//        main.setBackground(ImageUtil.drawTextToBitmap(getActivity(),BookInit.getInstance().getCurrentUserName()));
        if (Constant.IS_WATER_BACKGROUND.equals("1")) {
            mUserMessageScrollView.setBackground(ImageUtil.drawTextToBitmap(getActivity(), BookInit.getInstance().getCurrentUserName()));
            mUserMessageScrollView.getBackground().setAlpha((int) (0.5 * 255));
        }

    }

//    public OrgUser copyOrgUser(SYS_User mSYS_User) {
//        OrgUser mOrgUser = new OrgUser();
//        mOrgUser.setUser_id(Long.parseLong(mSYS_User.getUserId()));
//        mOrgUser.setUser_name(mSYS_User.getFullName());
//        mOrgUser.setTree_name(mSYS_User.getTree_name());
//        mOrgUser.setGroup_corp_id(mSYS_User.getGroup_corp_id());
//        mOrgUser.setOrg_name(mSYS_User.getOrg_name());
//        mOrgUser.setUser_type((short) mSYS_User.getUserType());
//        mOrgUser.setAdmin_type(mSYS_User.getAdminType());
//        mOrgUser.setEmi_type(mSYS_User.getEmiType());
//        mOrgUser.setUser_nickname(mSYS_User.getUserNickname());
//        mOrgUser.setUser_pyname(mSYS_User.getSuoxie());
//        mOrgUser.setDisplay_order(mSYS_User.getDisplay_order());
//        mOrgUser.setMobile_phone(mSYS_User.getMobile());
//        mOrgUser.setOffice_phone(mSYS_User.getOffice());
//        mOrgUser.setPic_path(mSYS_User.getPhotosurl());
//        mOrgUser.setRemark(mSYS_User.getRemark());
//        mOrgUser.setGender(mSYS_User.getGender());
//        mOrgUser.setTitle(mSYS_User.getTitle());
//        mOrgUser.setBirthday(mSYS_User.getBirthday());
//        mOrgUser.setHome_phone(mSYS_User.getHome_phone());
//        mOrgUser.setOffice_address(mSYS_User.getOffice_address());
//        mOrgUser.setPostal_code(mSYS_User.getPostal_code());
//        mOrgUser.setPic_path(mSYS_User.getPhotosurl());
//        mOrgUser.setHead_type(mSYS_User.getHeadType());
//        mOrgUser.setLogin_name(mSYS_User.getLogin_name());
//        mOrgUser.setFax(mSYS_User.getFax());
//        mOrgUser.setEfn1(mSYS_User.getEfn1());
//        mOrgUser.setEfn2(mSYS_User.getEfn2());
//        mOrgUser.setEfn3(mSYS_User.getEfn3());
//        mOrgUser.setEfi1(mSYS_User.getEfi1());
//        mOrgUser.setEfi2(mSYS_User.getEfi2());
//        mOrgUser.setEfi3(mSYS_User.getEfi3());
//        mOrgUser.setEfi4(mSYS_User.getEfi4());
//        mOrgUser.setEfi5(mSYS_User.getEfi5());
//        mOrgUser.setEfd1(mSYS_User.getEfd1());
//        mOrgUser.setEfd2(mSYS_User.getEfd2());
//        mOrgUser.setEfs1(mSYS_User.getEfs1());
//        mOrgUser.setEfs2(mSYS_User.getEfs2());
//        mOrgUser.setEfs3(mSYS_User.getEfs3());
//        mOrgUser.setEfs4(mSYS_User.getEfs4());
//        mOrgUser.setEfs5(mSYS_User.getEfs5());
//        mOrgUser.setEfs6(mSYS_User.getEfs6());
//        mOrgUser.setEfs7(mSYS_User.getEfs7());
//        mOrgUser.setEfs8(mSYS_User.getEfs8());
//        mOrgUser.setEfs9(mSYS_User.getEfs9());
//        mOrgUser.setEfs10(mSYS_User.getEfs10());
//        mOrgUser.setEmail(mSYS_User.getEmail());
//        return mOrgUser;
//    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        PeopleHeadEnum mPeopleHeadEnum = BookInit.getInstance().getPeopleHeadEnum();
        mSYS_User = BookInit.getInstance().getCurrentUser();
        LoadUserAvatar avatarLoader = new LoadUserAvatar(getActivity(), Constant.SDCARD_PATH);
        OrgUser mOrgUser = ConfigStyleUtil.copyOrgUser(mSYS_User);
        try {
            Field[] field = mOrgUser.getClass().getDeclaredFields();
            modelName = new ArrayList();
            for (int i = 0; i < field.length; i++) {
                String name = field[i].getName();
                field[i].setAccessible(true);
                String value = "";
                TD_UserDAO mTD_UserDAO = new TD_UserDAO(getActivity());
                TD_User mTD_User = mTD_UserDAO.getTD_User(name,true);
//                if(mTD_User == null){
//                    continue;
//                }
                if (mTD_User == null || mTD_User.getIsActive() == 0 || mTD_User.getIsActive() == 1) {  //
                    continue;
                }



//                SYS_User currentUser = BookInit.getInstance().getIndexCurrentUser();
//                if(mTD_User.getSecretFlag() == 2){
//
//                    if (currentUser.getDepartmentCode().contains(mSYS_User.getDepartmentCode()) && mSYS_User.getDepartmentCode().length() > currentUser.getDepartmentCode().length()){
//
//                    }else{
//                        continue;
//                    }
//
//                }else if(mTD_User.getSecretFlag() == 3){
//                    if (currentUser.getDepartmentCode().contains(mSYS_User.getDepartmentCode()) ){
//
//                    }else{
//                        continue;
//                    }
//                }else if(mTD_User.getSecretFlag() == 4){
//                    if (currentUser.getDepartmentCode().equals(mSYS_User.getDepartmentCode()) ){
//
//                    }else{
//                        continue;
//                    }
//                }else if(mTD_User.getSecretFlag() == 5){
//                    if (mSYS_User.getDepartmentCode().contains(currentUser.getDepartmentCode()) ){
//
//                    }else{
//                        continue;
//                    }
//                }
//                switch (SecretFlag){
//                    case 0:
//                        break;
//                    case 1:
//                        SYS_User currentUser = BookInit.getInstance().getIndexCurrentUser();
//                        //同级敏感没有规定 默认不可查看
//                        if(currentUser != null) {
//                            if (mSYS_User.getDepartmentCode().contains(currentUser.getDepartmentCode()) && mSYS_User.getDepartmentCode().length() > currentUser.getDepartmentCode().length())
//                                tv_name_value.setText("*******");
//                        }
//                        break;
//                    case 2:
//                        tv_name_value.setText("*******");
//                        break;
//                }
                try {
                    Field f = mOrgUser.getClass().getDeclaredField(name);
                    f.setAccessible(true);
                    value = f.get(mOrgUser) + "";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PeopleMessage mPeopleMessage = new PeopleMessage();
                mPeopleMessage.setName(name);
                if (name.equals("gender")) {
                    if (value.equals("1")) {
                        mPeopleMessage.setValue("男");
                    } else if (value.equals("2")) {
                        mPeopleMessage.setValue("女");
                    } else {
                        mPeopleMessage.setValue("未知");
                    }

                } else if (name.equals("birthday")) {
                    if (value != null && !value.equals("")) {
                        mPeopleMessage.setValue(value.split(" ")[0]);
                    }
                } else {
                    mPeopleMessage.setValue(value);
                }
                mPeopleMessage.setDisOrder(mTD_User.getDisOrder());
                mPeopleMessage.setmTD_User(mTD_User);
                modelName.add(mPeopleMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Comparator comp = new Comparator() {
            public int compare(Object o1, Object o2) {
                PeopleMessage p1 = (PeopleMessage) o1;
                PeopleMessage p2 = (PeopleMessage) o2;
                if (p1.DisOrder < p2.DisOrder)
                    return -1;
                else if (p1.DisOrder == p2.DisOrder)
                    return 0;
                else if (p1.DisOrder > p2.DisOrder)
                    return 1;
                return 0;
            }
        };
        Collections.sort(modelName, comp);

        for (PeopleMessage mPeopleMessage : modelName) {
            View peopleMessage = inflater.inflate(R.layout.ht_activity_people_add_update, null);
            TextView tv_name = (TextView) peopleMessage.findViewById(R.id.tv_name);
            TextView default_tv = (TextView) peopleMessage.findViewById(R.id.default_tv);
            TextView tv_name_value = (TextView) peopleMessage.findViewById(R.id.tv_name_value);
            ImageView tv_message_head = (ImageView) peopleMessage.findViewById(R.id.tv_message_head);
            RelativeLayout rl_head_view = (RelativeLayout) peopleMessage.findViewById(R.id.rl_head_view);
            tv_name.setText(mPeopleMessage.getmTD_User().getDisLabel() + " : ");
            String mProtect_mode = mPeopleMessage.getmTD_User().getProtect_mode();
            //头像单独处理
            if (mPeopleMessage.getName().equals("head_type")) {
                tv_name_value.setVisibility(View.INVISIBLE);
                rl_head_view.setVisibility(View.VISIBLE);

                String phoneSrl = mSYS_User.getPhotosurl();
                final String phoneSrls = phoneSrl == null ? "" : phoneSrl;
                if (phoneSrls == null || phoneSrls.equals("")) {
                    int headType = Short.parseShort(Constant.ADDRESS_HEADER_TYPE);

                    switch (headType) {
                        case 0:
                            tv_message_head.setVisibility(View.VISIBLE);
                            tv_message_head.setTag(mPeopleMessage.getValue());
                            if(mSYS_User != null && BookInit.getInstance().getBitmap() != null && BookInit.getInstance().getCrrentUserId().equals(mSYS_User.getUserId())){
                                tv_message_head.setImageBitmap(BookInit.getInstance().getBitmap());
                            }else{
                                Bitmap bitmap = avatarLoader.loadImage(tv_message_head,
                                        mPeopleMessage.getValue(), new LoadUserAvatar.ImageDownloadedCallBack() {

                                            @Override
                                            public void onImageDownloaded(
                                                    ImageView imageView, Bitmap bitmap) {
                                                imageView.setImageBitmap(bitmap);
                                            }

                                        });

                                if (bitmap != null) {
                                    tv_message_head.setImageBitmap(bitmap);
                                }
                            }
                            tv_message_head.setTag(mPeopleMessage.getName());
                            tv_message_head.setOnClickListener(this);
                            default_tv.setVisibility(View.GONE);
                            if(phoneSrls.equals("")){
                                tv_message_head.setEnabled(false);
                            }
                            break;
                        case 1:
                            tv_message_head.setVisibility(View.VISIBLE);
                            default_tv.setVisibility(View.GONE);
                            break;
                        case 2:
                            default_tv.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            default_tv.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            default_tv.setVisibility(View.VISIBLE);
                            break;
                    }
                    GradientDrawable myGrad = (GradientDrawable) default_tv.getBackground();
                    myGrad.setColor(mSYS_User.getColor());
                    default_tv.setText(mSYS_User.nameJan);
                    if(BookInit.getInstance().getCrrentUserId().equals(mSYS_User.getUserId())){
                        default_tv.setText("");
                        default_tv.setBackgroundResource(R.drawable.img_headportrait_self);
                    }
                } else {
                    tv_message_head.setVisibility(View.VISIBLE);
                    default_tv.setVisibility(View.GONE);
                    tv_message_head.setOnClickListener(this);
                    if(phoneSrls.equals("")){
                        tv_message_head.setEnabled(false);
                    }
                    if(mSYS_User != null && BookInit.getInstance().getBitmap() != null && BookInit.getInstance().getCrrentUserId().equals(mSYS_User.getUserId())){
                        tv_message_head.setImageBitmap(BookInit.getInstance().getBitmap());
                    }else{
                        Bitmap bitmap = avatarLoader.loadImage(tv_message_head,
                                phoneSrls, new LoadUserAvatar.ImageDownloadedCallBack() {

                                    @Override
                                    public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {
                                        if (imageView.getTag() == phoneSrls) {
                                            if (BookInit.getInstance().getBitmap() != null) {
                                                imageView.setImageBitmap(BookInit.getInstance().getBitmap());
                                            } else {
                                                imageView.setImageBitmap(bitmap);
                                            }
                                            BookInit.getInstance().setBitmap(bitmap);
                                        }
                                    }

                                });

                        if (bitmap != null) {
                            tv_message_head.setImageBitmap(bitmap);
                        }
                    }

                }


            } else {
                rl_head_view.setVisibility(View.GONE);
                default_tv.setVisibility(View.GONE);
                tv_message_head.setVisibility(View.GONE);
                tv_name_value.setVisibility(View.VISIBLE);
                tv_name_value.setTag(mPeopleMessage.getName());
                /**
                 * 处理出现两个或者多个电话号码
                 */
                if (mPeopleMessage.getName().equals("office_phone") || mPeopleMessage.getName().equals("mobile_phone") || mPeopleMessage.getName().equals("home_phone")) {
                    if (!mPeopleMessage.getValue().equals("")) {
                        tv_name_value.setText("");
                        String telephone = mPeopleMessage.getValue();
                        String[] s;
                        if (telephone.contains(",")) {
                            s = telephone.split(",");
                            for (String st : s) {
                                SpannableString spannableString = new SpannableString("  " + DensityUtil.cellPhone(st,mPeopleMessage.getName()));
                                ClickableSpan clickttt = new PhoneClickText(getActivity(), DensityUtil.getTagType(st, mPeopleMessage.getName()), mPeopleMessage.getName(), this);
                                spannableString.setSpan(clickttt, 0, st.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                tv_name_value.append(spannableString);
                                tv_name_value.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                        } else if (telephone.contains(";")) {
                            s = telephone.split(";");
                            for (String st : s) {
                                SpannableString spannableString = new SpannableString("  " + DensityUtil.cellPhone(st,mPeopleMessage.getName()));
                                ClickableSpan clickttt = new PhoneClickText(getActivity(), DensityUtil.getTagType(st, mPeopleMessage.getName()), mPeopleMessage.getName(), this);
                                spannableString.setSpan(clickttt, 0, st.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                tv_name_value.append(spannableString);
                                tv_name_value.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                        } else if (telephone.contains(" ")) {
                            s = telephone.split(" ");
                            for (String st : s) {
                                SpannableString spannableString = new SpannableString("  " + DensityUtil.cellPhone(st,mPeopleMessage.getName()));


                                ClickableSpan clickttt = new PhoneClickText(getActivity(), DensityUtil.getTagType(st, mPeopleMessage.getName()), mPeopleMessage.getName(), this);
                                spannableString.setSpan(clickttt, 0, st.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                tv_name_value.append(spannableString);
                                tv_name_value.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                        } else {
                            s = new String[1];
                            s[0] = telephone;
                            tv_name_value.setText(DensityUtil.cellPhone(telephone,mPeopleMessage.getName()));
                            tv_name_value.setTag(mPeopleMessage.getName() + ":" + DensityUtil.getTagType(telephone, mPeopleMessage.getName()));
                            if (!mPeopleMessage.getValue().equals("")) {
                                tv_name_value.setOnClickListener(this);
                            }
                        }
                    } else {
                        tv_name_value.setText("未填写");
                        tv_name_value.setTextColor(getActivity().getResources().getColor(R.color.grayaddress));
                    }
                } else {
                    tv_name_value.setText(mPeopleMessage.getValue().equals("")||mPeopleMessage.getValue().equals("null") ? "未填写" : mPeopleMessage.getValue());
                    tv_name_value.setTextColor(getActivity().getResources().getColor(R.color.grayaddress));
                }

            }
            if (mPeopleMessage.getName().equals("home_phone") || mPeopleMessage.getName().equals("office_phone") || mPeopleMessage.getName().equals("mobile_phone") || mPeopleMessage.getName().equals("email")) {
                if ((mPeopleMessage.getValue().trim().equals(""))) {
                    tv_name_value.setTextColor(getActivity().getResources().getColor(R.color.grayaddress));
                } else {
                    tv_name_value.setTextColor(getActivity().getResources().getColor(R.color.blueaddress));
                }

                if (mPeopleMessage.getName().equals("email") && !mPeopleMessage.getValue().equals("")) {
                    tv_name_value.setOnClickListener(this);
                    tv_name_value.setTextColor(getActivity().getResources().getColor(R.color.blueaddress));
                } else if (mPeopleMessage.getName().equals("email") && mPeopleMessage.getValue().equals("")) {
                    tv_name_value.setTextColor(getActivity().getResources().getColor(R.color.grayaddress));
                }
            } else if(mPeopleMessage.getValue().equals("")||mPeopleMessage.getValue().equals("未填写")||mPeopleMessage.getValue().equals("null")){
                tv_name_value.setTextColor(getActivity().getResources().getColor(R.color.grayaddress));
            }else{
                tv_name_value.setTextColor(getActivity().getResources().getColor(R.color.black));
            }
            int SecretFlag = mPeopleMessage.getmTD_User().getSecretFlag();
            if (!mProtect_mode.equals("0")) {  //暂时不给敏感信息
                tv_name_value.setText("*******");
            }

            layout_grid_view.addView(peopleMessage);

        }
        /**
         * 添加上滑更多
         * 当加载完成的时候 将footerView设置成gone 下次进入的时候
         */
        footerView = inflater.inflate(R.layout.ht_list_pull_footerview, null);
        layout_grid_view.addView(footerView);
        footerView.setVisibility(View.GONE);
        mUserMessageScrollView.setOverScrollListener(new UserMessageScrollView.OverScrollListener() {
            @Override
            public void headerScroll() {

            }

            @Override
            public void footerScroll() {
                footerView.findViewById(R.id.progress_).setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        footerView.setVisibility(View.GONE);
                    }
                }, 2000);
            }
        });
//		tv_message_location.setText(""+mSYS_User.get());
        tv_add_friends.setOnClickListener(this);
        tv_send.setOnClickListener(this);

        if (BookInit.getInstance().getCrrentUserId().equals(mSYS_User.getUserId())) {
            layout_message.setVisibility(View.GONE);
        } else {
//            layout_message.setVisibility(View.VISIBLE);
        }
        List<T_UserRelationship> mList = BaseApplication.getApplication(getActivity()).getContactList();
        T_UserRelationship mT_UserRelationship = new T_UserRelationship();
        mT_UserRelationship.setCUserId(mSYS_User.getUserId());
        //是否可以发送信息
        if (mSYS_User.getEmiType() != null) {
            if (mSYS_User.getEmiType() == 1) {//如果是EMI用户 就可以进行短信
                tv_send.setVisibility(View.VISIBLE);
            } else {
                tv_send.setVisibility(View.GONE);
            }
        }
        if (mList.contains(mT_UserRelationship)) {
            tv_add_friends.setVisibility(View.GONE);
        } else {
            if (Constant.IS_CONTACT) {
                tv_add_friends.setVisibility(View.VISIBLE);
            } else {
                tv_add_friends.setVisibility(View.GONE);
            }

        }
    }

    public String mobile_phone;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

        if (arg0.getId() == R.id.tv_message_head) {
//				BookInit.getInstance().setRequestUser(orgUser);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("photoUrl", mSYS_User.getPhotosurl());
            if(mSYS_User != null && BookInit.getInstance().getBitmap() != null && BookInit.getInstance().getCrrentUserId().equals(mSYS_User.getUserId())){
                map.put("isCurrent",true);
                ActivityUnit.switchTo(getActivity(), PhotoActivity.class, map);
            }else {
                map.put("isCurrent",false);
                ActivityUnit.switchTo(getActivity(), PhotoActivity.class, map);
            }



//				Intent intent = new Intent(context, PhotoActivity.class);
//
//				context.startActivity(intent);


        } else if (arg0.getTag() != null && arg0.getTag().toString().contains("mobile_phone")) {
            mobile_phone = arg0.getTag().toString().split(":")[1];
            if (!mobile_phone.contains("*") && !Constant.com_addressbook_mobileconfig_mobile_phone_secrecy.equals("2")) {        //电话号中带*号不予拨打功能
                menuWindow = new SelectPicPopupWindow(getActivity(), this);
                //显示窗口
                menuWindow.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            } else {
                Toast.makeText(getActivity(), "对不起，该号码不支持拨打，请您查询后重试", Toast.LENGTH_SHORT).show();
            }

        } else if(arg0.getTag() != null && !TextUtils.isEmpty(arg0.getTag().toString()) && arg0.getTag().toString().equals("new_people")){
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setType("vnd.android.cursor.dir/person");
            intent.setType("vnd.android.cursor.dir/contact");
            intent.setType("vnd.android.cursor.dir/raw_contact");
            // 添加姓名
            intent.putExtra(ContactsContract.Intents.Insert.NAME, mSYS_User.getFullName());
            // 添加手机
            intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, mobile_phone);
            startActivity(intent);
        }else if(arg0.getTag() != null && !TextUtils.isEmpty(arg0.getTag().toString()) && arg0.getTag().toString().equals("modify_people")){
            Intent oldConstantIntent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
            oldConstantIntent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
            oldConstantIntent.putExtra(ContactsContract.Intents.Insert.PHONE,mobile_phone);
            oldConstantIntent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, 3);
            oldConstantIntent.putExtra(ContactsContract.Intents.Insert.NAME, mSYS_User.getFullName());
            startActivity(oldConstantIntent);
            if(oldConstantIntent.resolveActivity(getActivity().getPackageManager()) != null){
                startActivity(oldConstantIntent);
            }
        } else if (arg0.getId() == R.id.bt_call) {

            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile_phone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);

        } else if (arg0.getId() == R.id.bt_save) {//保存至手机
//            if(menuWindow != null && menuWindow.isShowing())
//                menuWindow.dismiss();
//            menuWindow = new SelectPicPopupWindow(getActivity(), this);
//            menuWindow.setSave();
//            //显示窗口
//            menuWindow.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
//
            Intent oldConstantIntent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
            oldConstantIntent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
            oldConstantIntent.putExtra(ContactsContract.Intents.Insert.PHONE,mobile_phone);
            oldConstantIntent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, 3);
            oldConstantIntent.putExtra(ContactsContract.Intents.Insert.NAME, mSYS_User.getFullName());
            startActivity(oldConstantIntent);
            if(oldConstantIntent.resolveActivity(getActivity().getPackageManager()) != null){
                startActivity(oldConstantIntent);
            }

        }else if (arg0.getId() == R.id.bt_copy) {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", mobile_phone);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            Toast.makeText(getActivity(), "复制完成！", Toast.LENGTH_SHORT).show();
        } else if (arg0.getId() == R.id.bt_send) {//发送消息
            Uri smsToUri = Uri.parse("smsto:" + mobile_phone);
            Intent mIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
            startActivity(mIntent);
        } else if (arg0.getId() == R.id.tv_add_friends) { // 加为联系人
            T_UserRelationship mT_UserRelationship = new T_UserRelationship();
            mT_UserRelationship.setCUserId(mSYS_User.getUserId());
            mT_UserRelationship.setUserId(BookInit.getInstance().getCrrentUserId());
            mT_UserRelationship.setmSYS_User(mSYS_User);
            BookInit.getInstance().getmCallbackMX().callAddCUser(mT_UserRelationship, BookInit.getInstance().getTxlAppId()); // 添加主页的回调

        } else if (arg0.getId() == R.id.tv_send) {//发消息
            BookInit.getInstance().getmCallbackMX().callbackSendMessage(mSYS_User.getLogin_name());
        } else if (arg0.getTag() != null && arg0.getTag().toString().contains("office_phone")) {
            mobile_phone = arg0.getTag().toString().split(":")[1];
            if(mobile_phone.contains("*") || Constant.com_addressbook_mobileconfig_office_phone_secrecy.equals("2")){
                Toast.makeText(getActivity(), "对不起，该号码不支持拨打，请您查询后重试", Toast.LENGTH_SHORT).show();
                return;
            }
            menuWindow = new SelectPicPopupWindow(getActivity(), this);
            //显示窗口
            menuWindow.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        } else if (arg0.getTag() != null && arg0.getTag().toString().contains("home_phone")) {
            mobile_phone = arg0.getTag().toString().split(":")[1];
            if(mobile_phone.contains("*") || Constant.com_addressbook_mobileconfig_home_phone_secrecy.equals("2")){
                Toast.makeText(getActivity(), "对不起，该号码不支持拨打，请您查询后重试", Toast.LENGTH_SHORT).show();
                return;
            }

            menuWindow = new SelectPicPopupWindow(getActivity(), this);
            //显示窗口
            menuWindow.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        } else if (arg0.getTag() != null && arg0.getTag().equals("email")) {
            if(mSYS_User.getEmail().contains("*")){
                Toast.makeText(getActivity(), "对不起，该加密文件无法发送，请您查询后重试", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("plain/text");
            String[] reciver = new String[]{mSYS_User.getEmail()};
            //设置邮件默认地址
            email.putExtra(Intent.EXTRA_EMAIL, reciver);
            //设置邮件默认标题
            email.putExtra(Intent.EXTRA_SUBJECT, "发送给" + mSYS_User.getFullName() + "的邮件");
            //设置要默认发送的内容
            email.putExtra(Intent.EXTRA_TEXT, "");
            //调用系统的邮件系统
            startActivity(Intent.createChooser(email, "请选择邮件发送软件"));
        } else if (arg0.getTag() != null && arg0.getTag().equals("head_type")) {
            if (mSYS_User.getPhotosurl() != null && !mSYS_User.getPhotosurl().equals("")) {
//                Intent intent = new Intent(getActivity(), PhotoActivity.class);
//                intent.putExtra("photoUrl", mSYS_User.getPhotosurl());
//                startActivity(intent);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("photoUrl", mSYS_User.getPhotosurl());
                ActivityUnit.switchTo(getActivity(), PhotoActivity.class, map);
            }
        }
    }

    public SYS_User getmSYS_User() {
        return mSYS_User;
    }

    public void setmSYS_User(SYS_User mSYS_User) {
        this.mSYS_User = mSYS_User;
    }

    //添加广播回调
    public void addUser() {
        T_UserRelationship mT_UserRelationship = new T_UserRelationship();
        mT_UserRelationship.setCUserId(mSYS_User.getUserId());
        mT_UserRelationship.setUserId(BookInit.getInstance().getCrrentUserId());
        mT_UserRelationship.setmSYS_User(mSYS_User);
        String CUserId = BookInit.getInstance().getCrrentUserId();
        T_UserRelationshipDAO mT_UserRelationshipDAO = new T_UserRelationshipDAO(getActivity());
        String[] s = new String[]{CUserId, mSYS_User.getUserId(), mSYS_User.getGroup_corp_id()};
        ArrayList<String[]> arr = new ArrayList<String[]>();
        arr.add(s);
        mT_UserRelationshipDAO.insert(new String[]{"user_id", "contact_id", "group_corp_id"}, arr);
        Toast.makeText(getActivity(), "加入联系人成功！", Toast.LENGTH_SHORT).show();
        getActivity().finish();
        BaseApplication.getApplication(getActivity()).getContactList().add(mT_UserRelationship);
    }
}
