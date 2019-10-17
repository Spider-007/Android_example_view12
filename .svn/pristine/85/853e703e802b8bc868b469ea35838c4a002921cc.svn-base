package com.htmitech.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.htmitech.addressbook.R;
import com.htmitech.addressbook.UserDetailsActivity;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.ChildFragmentListener;
import com.htmitech.myEnum.PeopleHeadEnum;
import com.htmitech.others.LoadUserAvatar;
import com.htmitech.pop.CallPhonePopupWindow;
import com.htmitech.unit.ConfigStyleUtil;
import com.htmitech.unit.DensityUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//二级菜单的Adapter
public class UserDetailChildAdapter extends BaseAdapter {
    private SYS_Department mSYS_Department;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<SYS_User> arraySYS_User;
    private ArrayList<SYS_Department> arrDepartment;
    private ChildFragmentListener mChildFragmentListener;
    private static final int TYPE_USER_TAG = 0;
    private static final int TYPE_DEPARTMENT_TAG = 1;
    private boolean search;
    private LoadUserAvatar avatarLoader;
    private PeopleHeadEnum mPeopleHeadEnum;


    public UserDetailChildAdapter(SYS_Department mSYS_Department,
                                  ChildFragmentListener mChildFragmentListener, boolean search,
                                  Context context) {
        this.search = search;
        arraySYS_User = new ArrayList<SYS_User>();
        arrDepartment = new ArrayList<SYS_Department>();
        this.mSYS_Department = mSYS_Department;
        this.context = context;
        this.mChildFragmentListener = mChildFragmentListener;
        inflater = LayoutInflater.from(context);
        if (mSYS_Department != null && mSYS_Department.getSYS_User() != null) {
            arraySYS_User = (ArrayList<SYS_User>) mSYS_Department.getSYS_User();
            for (int i = 0; i < arraySYS_User.size(); i++) {
                if (arraySYS_User.get(i).getUserId().equals("")) {
                    arraySYS_User.remove(i);
                }
            }
        }
        if (mSYS_Department != null && mSYS_Department.getSYS_DepartmentList() != null) {
            arrDepartment = (ArrayList<SYS_Department>) mSYS_Department
                    .getSYS_DepartmentList();
        }
        // 部门之间的排序
        Comparator comp = new Comparator() {
            public int compare(Object o1, Object o2) {
                SYS_Department p1 = (SYS_Department) o1;
                SYS_Department p2 = (SYS_Department) o2;
                if (p1.DisOrder < p2.DisOrder)
                    return -1;
                else if (p1.DisOrder == p2.DisOrder)
                    return 0;
                else if (p1.DisOrder > p2.DisOrder)
                    return 1;
                return 0;
            }
        };
        Collections.sort(arrDepartment, comp);
        avatarLoader = new LoadUserAvatar(context, Constant.SDCARD_PATH);
        mPeopleHeadEnum = BookInit.getInstance().getPeopleHeadEnum();
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        if (position < arrDepartment.size()) {
            return TYPE_DEPARTMENT_TAG;
        } else {
            return TYPE_USER_TAG;
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arraySYS_User.size() + arrDepartment.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub

        if (position < arrDepartment.size()) {
            return arrDepartment.get(position);
        } else {
            return arraySYS_User.get(arrDepartment.size() - position);
        }
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolderChild mViewHolderChild = null;
        ViewHolderChildPeople mViewHolderChildPeople = null;
        int itemType = this.getItemViewType(position);
        if (itemType == TYPE_DEPARTMENT_TAG) {
            View viewDepartment = null;
            if (convertView == null) {
                mViewHolderChild = new ViewHolderChild();
                viewDepartment = inflater.inflate(
                        R.layout.ht_fragment_child_contactlist_adapter, null);
                mViewHolderChild.ivAvatar = (ImageView) viewDepartment
                        .findViewById(R.id.iv_avatar);
                mViewHolderChild.tv_department_name = (TextView) viewDepartment
                        .findViewById(R.id.tv_department_name);
                mViewHolderChild.fragment_child = (RelativeLayout) viewDepartment
                        .findViewById(R.id.fragment_child);
                viewDepartment.setTag(R.id.tag_department, mViewHolderChild);
                convertView = viewDepartment;
            } else {
                mViewHolderChild = (ViewHolderChild) convertView
                        .getTag(R.id.tag_department);
            }
            mViewHolderChild.tv_department_name.setText(arrDepartment.get(
                    position).getShortName());
            mViewHolderChild.fragment_child
                    .setOnClickListener(new DepartmentOnClickListener(
                            mSYS_Department, arrDepartment.get(position)));

        } else if (itemType == TYPE_USER_TAG) {
            View viewUser = null;
            SYS_User mSYS_User = arraySYS_User.get(position
                    - arrDepartment.size());
            if (convertView == null) {
                mViewHolderChildPeople = new ViewHolderChildPeople();
                viewUser = inflater.inflate(R.layout.ht_item_contact_list, null);
                mViewHolderChildPeople.ivAvatar = (ImageView) viewUser
                        .findViewById(R.id.iv_avatar);
                mViewHolderChildPeople.tvName = (TextView) viewUser
                        .findViewById(R.id.tv_name);
                mViewHolderChildPeople.tv_phone = (TextView) viewUser
                        .findViewById(R.id.tv_phone);
                mViewHolderChildPeople.iv_SMS = (ImageView) viewUser
                        .findViewById(R.id.iv_SMS);
                mViewHolderChildPeople.iv_phone = (ImageView) viewUser
                        .findViewById(R.id.iv_phone);
                mViewHolderChildPeople.default_tv = (TextView) viewUser.findViewById(R.id.default_tv);
                mViewHolderChildPeople.user_relative = (RelativeLayout) viewUser
                        .findViewById(R.id.user_relative);
                viewUser.setTag(R.id.tag_people, mViewHolderChildPeople);
                convertView = viewUser;
            } else {
                mViewHolderChildPeople = (ViewHolderChildPeople) convertView
                        .getTag(R.id.tag_people);
            }
//            if (mSYS_User.getMobile().equals("")) {
//                if (mSYS_User.getTelephone().equals("")) {
//                    mViewHolderChildPeople.iv_phone.setImageResource(R.drawable.btn_phone_disabled);
//                    mViewHolderChildPeople.iv_phone.setEnabled(false);
//                } else {
//                    mViewHolderChildPeople.iv_phone.setImageResource(R.drawable.btn_phone_selected);
//                    mViewHolderChildPeople.iv_phone.setEnabled(true);
//                }
//            } else {
//                mViewHolderChildPeople.iv_phone.setImageResource(R.drawable.btn_phone_selected);
//                mViewHolderChildPeople.iv_phone.setEnabled(true);
//            }

            if (TextUtils.isEmpty(mSYS_User.getMobile()) && TextUtils.isEmpty(mSYS_User.getTelephone()) && TextUtils.isEmpty(mSYS_User.getHome_phone())) {
                mViewHolderChildPeople.iv_phone.setImageResource(R.drawable.btn_phone_disabled);
                mViewHolderChildPeople.iv_phone.setEnabled(false);
            } else {

                boolean mobile = mSYS_User.getMobile().equals("") || mSYS_User.getMobile().contains("*");
                boolean titlePhone = mSYS_User.getTelephone().equals("") || mSYS_User.getTelephone().contains("*");
                boolean homePhone = mSYS_User.getHome_phone().equals("") || mSYS_User.getHome_phone().contains("*");
                if (mobile && titlePhone && homePhone) {
                    mViewHolderChildPeople.iv_phone.setImageResource(R.drawable.btn_phone_disabled);
                    mViewHolderChildPeople.iv_phone.setEnabled(false);
                } else {
                    mViewHolderChildPeople.iv_phone.setImageResource(R.drawable.btn_phone_selected);
                    mViewHolderChildPeople.iv_phone.setEnabled(true);
                }

            }
            if (mSYS_User.getEmiType() != null) {
                if (mSYS_User.getEmiType() == 1) {//如果是EMI用户 就可以进行短信
                    mViewHolderChildPeople.iv_SMS.setVisibility(View.VISIBLE);
                    mViewHolderChildPeople.iv_SMS.setEnabled(true);
                    mViewHolderChildPeople.iv_SMS.setFocusable(true);
                    mViewHolderChildPeople.iv_SMS.setImageResource(R.drawable.btn_email_selected);
                } else {
                    mViewHolderChildPeople.iv_SMS.setVisibility(View.VISIBLE);
                    mViewHolderChildPeople.iv_SMS.setImageResource(R.drawable.btn_email_disabled);
                    mViewHolderChildPeople.iv_SMS.setEnabled(false);
                    mViewHolderChildPeople.iv_SMS.setFocusable(false);
                }
            }
            if (mSYS_User.getmTD_User() != null) {
                switch (mSYS_User.getmTD_User().getAction()) {
                    case 0:
                        break;
                    case 1:
                        mViewHolderChildPeople.iv_phone.setImageResource(R.drawable.btn_phone_selected);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        mViewHolderChildPeople.iv_phone.setImageResource(R.drawable.btn_phone_disabled);
                        break;
                }
            }

            java.util.Random random = new java.util.Random();
            int index = position % Constant.colorList.length;

            convertView.setOnClickListener(new DepartmentPeopleOnClickListener(
                    mSYS_User));
//            mViewHolderChildPeople.tvName.setText(mSYS_User.getFullName());
            String fullName;
            if (!TextUtils.isEmpty(mSYS_User.getOrg_name())) {
                fullName = mSYS_User.isSoso() ? mSYS_User.getFullName() + "-(" + mSYS_User.getOrg_name() + ")" : mSYS_User.getFullName();
            } else {
                fullName = mSYS_User.getFullName();
            }

            mViewHolderChildPeople.tvName.setText(fullName);
            mViewHolderChildPeople.iv_phone
                    .setOnClickListener(new UserListListener(mSYS_User));
            mViewHolderChildPeople.iv_SMS
                    .setOnClickListener(new UserListListener(mSYS_User));
            String phoneSrl = mSYS_User.getPhotosurl();
            short headType = Short.parseShort(Constant.ADDRESS_HEADER_TYPE);
            final String phoneSrls = phoneSrl == null ? "" : phoneSrl;
            if (phoneSrls.equals("")) {
                String name = mSYS_User.getFullName();
                if (headType == 4) {
                    name = mSYS_User.getUserNickname();
                }
                int color = mSYS_User.getColor();
                if (color == 0) {
                    mSYS_User.setColor(Constant.colorList[index]);
                    color = Constant.colorList[index];
                }
                GradientDrawable myGrad = (GradientDrawable) mViewHolderChildPeople.default_tv.getBackground();
                myGrad.setColor(color);
                Pattern p = Pattern.compile("[a-zA-Z]");
                Matcher m = p.matcher(name);
                int start = 0, end = 0;
                switch (mPeopleHeadEnum) {
                    case DEFAULT:
                        mViewHolderChildPeople.default_tv.setVisibility(View.GONE);
                        break;
                    case SURNAME:
                        start = 0;
                        end = 1;
                        break;
                    case THENAME:
                        start = name.length() - 2;
                        end = name.length();
                        break;
                }

                switch (headType) {
                    case 0:
                        mViewHolderChildPeople.default_tv.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mViewHolderChildPeople.default_tv.setVisibility(View.GONE);
                        break;
                    case 2:
                        mViewHolderChildPeople.default_tv.setVisibility(View.VISIBLE);
                        start = name.length() - 2;
                        end = name.length();
                        break;
                    case 3:
                        mViewHolderChildPeople.default_tv.setVisibility(View.VISIBLE);
                        start = 0;
                        end = 1;
                        break;
                    case 4:
                        mViewHolderChildPeople.default_tv.setVisibility(View.VISIBLE);
                        start = 0;
                        end = name.length();
                        break;
                }

                if (m.matches()) {
                    mViewHolderChildPeople.default_tv.setText(name.substring(start, end).toUpperCase() + "");
                } else {
                    if (name.length() < 2) {
                        mViewHolderChildPeople.default_tv.setText(name);
                    } else {
                        mViewHolderChildPeople.default_tv.setText(name.substring(start, end) + "");
                    }
                }
//				mViewHolderChildPeople.default_tv.setVisibility(View.VISIBLE);
                mSYS_User.nameJan = mViewHolderChildPeople.default_tv.getText().toString();
            } else {
                mViewHolderChildPeople.default_tv.setVisibility(View.GONE);
                mViewHolderChildPeople.ivAvatar.setVisibility(View.VISIBLE);
            }
            if (mSYS_User != null && !mSYS_User.getUserId().equals(BookInit.getInstance().getCrrentUserId())) {
                mViewHolderChildPeople.ivAvatar.setImageResource(R.drawable.default_useravatar);
                showUserAvatar(mViewHolderChildPeople.ivAvatar, phoneSrls, mSYS_User);
            }

//				mViewHolderChildPeople.default_tv.setVisibility(View.VISIBLE);

//			if (search) {
//				mViewHolderChildPeople.tv_phone.setText(mSYS_User
//						.getmSYS_Department().getFullName());
//			} else {
//				mViewHolderChildPeople.tv_phone.setText(mSYS_User.getMobile());
//            DensityUtil.cellPhone(mSYS_User
//                    .getMobile(), mViewHolderChildPeople.tv_phone);
            if (!Constant.ADDRESS_LIST_MESSAGE.equals("")) {

                try {
                    String temp = Constant.ADDRESS_LIST_MESSAGE;

                    temp = ConfigStyleUtil.changeConfig(temp, mSYS_User);
                    if (!"".equals(temp.trim())) {
                        mViewHolderChildPeople.tv_phone.setVisibility(View.VISIBLE);
                        mViewHolderChildPeople.tv_phone.setText(temp);
                    } else {
                        mViewHolderChildPeople.tv_phone.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //如果是当前本人
            if (mSYS_User != null && mSYS_User.getUserId().equals(BookInit.getInstance().getCrrentUserId())) {
                mViewHolderChildPeople.tvName.setText(mViewHolderChildPeople.tvName.getText());
                mViewHolderChildPeople.iv_SMS.setVisibility(View.GONE);
                if (TextUtils.isEmpty(mSYS_User.getMobile()) && TextUtils.isEmpty(mSYS_User.getTelephone()) && TextUtils.isEmpty(mSYS_User.getHome_phone())) {
                    mViewHolderChildPeople.iv_phone.setImageResource(R.drawable.btn_phone_disabled);
                    mViewHolderChildPeople.iv_phone.setEnabled(false);
                } else {
                    boolean mobile = mSYS_User.getMobile().equals("") || mSYS_User.getMobile().contains("*");
                    boolean titlePhone = mSYS_User.getTelephone().equals("") || mSYS_User.getTelephone().contains("*");
                    boolean homePhone = mSYS_User.getHome_phone().equals("") || mSYS_User.getHome_phone().contains("*");
                    if (mobile && titlePhone && homePhone) {
                        mViewHolderChildPeople.iv_phone.setImageResource(R.drawable.btn_phone_disabled);
                        mViewHolderChildPeople.iv_phone.setEnabled(false);
                    } else {
                        mViewHolderChildPeople.iv_phone.setImageResource(R.drawable.btn_phone_selected);
                        mViewHolderChildPeople.iv_phone.setEnabled(true);
                    }
                }
                mViewHolderChildPeople.default_tv.setVisibility(View.GONE);
                mViewHolderChildPeople.ivAvatar.setImageResource(R.drawable.img_headportrait_self);
//                Glide.with(context).load(phoneSrl).placeholder(R.drawable.img_headportrait_self).error(R.drawable.img_headportrait_self).into(mViewHolderChildPeople.ivAvatar);

                showUserAvatar(mViewHolderChildPeople.ivAvatar, phoneSrls, mSYS_User);
            }
        }

        return convertView;
    }

    private void showUserAvatar(ImageView iamgeView, final String avatar, SYS_User mSYS_User) {
        if (mSYS_User != null && BookInit.getInstance().getBitmap() != null && BookInit.getInstance().getCrrentUserId().equals(mSYS_User.getUserId())) {
            iamgeView.setImageBitmap(BookInit.getInstance().getBitmap());
            return;
        }

        if (avatar == null || avatar.equals("")) return;
        iamgeView.setTag(avatar);
        Bitmap bitmap = avatarLoader.loadImage(iamgeView,
                avatar, new LoadUserAvatar.ImageDownloadedCallBack() {

                    @Override
                    public void onImageDownloaded(
                            ImageView imageView, Bitmap bitmap) {
                        if (imageView.getTag() == avatar) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }

                });

        if (bitmap != null) {
            iamgeView.setImageBitmap(bitmap);
        }

    }

    Map<String, View> mMap = new HashMap<String, View>();

    class ViewHolderChild {
        ImageView ivAvatar;
        TextView tv_department_name;
        RelativeLayout fragment_child;
    }

    class ViewHolderChildPeople {
        ImageView ivAvatar;
        TextView tvName;
        TextView tv_phone;
        ImageView iv_SMS;
        ImageView iv_phone;
        RelativeLayout user_relative;
        TextView default_tv;
    }

    // 部门进入下一级菜单
    class DepartmentOnClickListener implements OnClickListener {
        private SYS_Department currentSYS_Department;
        private SYS_Department upSYS_Department;

        public DepartmentOnClickListener(SYS_Department upSYS_Department,
                                         SYS_Department currentSYS_Department) {
            this.currentSYS_Department = currentSYS_Department;
            this.upSYS_Department = upSYS_Department;
        }

        @Override
        public void onClick(View position) {
            mChildFragmentListener.notifyDataSetChanged(upSYS_Department,
                    currentSYS_Department);
        }
    }

    // 进入人员详细信息
    class DepartmentPeopleOnClickListener implements OnClickListener {
        private SYS_User currentSYS_User;

        public DepartmentPeopleOnClickListener(SYS_User currentSYS_User) {
            this.currentSYS_User = currentSYS_User;
        }

        @Override
        public void onClick(View position) {
            Intent intent = new Intent(context, UserDetailsActivity.class);
            BookInit.getInstance().setCurrentUser(currentSYS_User);
            context.startActivity(intent);
        }
    }

    class UserListListener implements OnClickListener {
        private SYS_User mSYS_User;

        public UserListListener(SYS_User mSYS_User) {
            this.mSYS_User = mSYS_User;
        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            if (arg0.getId() == R.id.iv_SMS) {
                BookInit.getInstance().getmCallbackMX().callbackSendMessage(mSYS_User.getLogin_name());
            } else if (arg0.getId() == R.id.iv_phone) {


                if (!TextUtils.isEmpty(mSYS_User.getTelephone()) && TextUtils.isEmpty(mSYS_User.getMobile()) && TextUtils.isEmpty(mSYS_User.getHome_phone())) {
                    if (!mSYS_User.getTelephone().contains("*") && !Constant.com_addressbook_mobileconfig_office_phone_secrecy.equals("2")) {
                        if (mSYS_User.getTelephone().contains(",") || mSYS_User.getTelephone().contains("，") || mSYS_User.getTelephone().contains(";") || mSYS_User.getTelephone().contains("；") || mSYS_User.getTelephone().contains(" ")) {
                            CallPhonePopupWindow menuWindow = new CallPhonePopupWindow(context, UserListListener.this, mSYS_User.getMobile(), mSYS_User.getTelephone(), mSYS_User.home_phone);
                            //显示窗口
                            menuWindow.showAtLocation(arg0, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                        } else {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mSYS_User.getTelephone()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }

                    } else {
                        Toast.makeText(context, "对不起，该号码不支持拨打，请您查询后重试", Toast.LENGTH_SHORT).show();
                    }
                } else if (TextUtils.isEmpty(mSYS_User.getTelephone()) && !TextUtils.isEmpty(mSYS_User.getMobile()) && TextUtils.isEmpty(mSYS_User.getHome_phone())) {
                    if (!mSYS_User.getMobile().contains("*") && !Constant.com_addressbook_mobileconfig_mobile_phone_secrecy.equals("2")) {
                        if (mSYS_User.getMobile().contains(",") || mSYS_User.getMobile().contains("，") || mSYS_User.getMobile().contains(";") || mSYS_User.getMobile().contains("；") || mSYS_User.getMobile().contains(" ")) {
                            CallPhonePopupWindow menuWindow = new CallPhonePopupWindow(context, UserListListener.this, mSYS_User.getMobile(), mSYS_User.getTelephone(), mSYS_User.home_phone);
                            //显示窗口
                            menuWindow.showAtLocation(arg0, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                        } else {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mSYS_User.getMobile()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }

                    } else {
                        Toast.makeText(context, "对不起，该号码不支持拨打，请您查询后重试", Toast.LENGTH_SHORT).show();
                    }
                } else if (TextUtils.isEmpty(mSYS_User.getTelephone()) && TextUtils.isEmpty(mSYS_User.getMobile()) && !TextUtils.isEmpty(mSYS_User.getHome_phone())) {
                    if (!mSYS_User.getHome_phone().contains("*") && !Constant.com_addressbook_mobileconfig_home_phone_secrecy.equals("2")) {
                        if (mSYS_User.getHome_phone().contains(",") || mSYS_User.getHome_phone().contains("，") || mSYS_User.getHome_phone().contains(";") || mSYS_User.getHome_phone().contains("；") || mSYS_User.getHome_phone().contains(" ")) {
                            CallPhonePopupWindow menuWindow = new CallPhonePopupWindow(context, UserListListener.this, mSYS_User.getMobile(), mSYS_User.getTelephone(), mSYS_User.home_phone);
                            //显示窗口
                            menuWindow.showAtLocation(arg0, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                        } else {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mSYS_User.getHome_phone()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }

                    } else {
                        Toast.makeText(context, "对不起，该号码不支持拨打，请您查询后重试", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    CallPhonePopupWindow menuWindow = new CallPhonePopupWindow(context, UserListListener.this, mSYS_User.getMobile(), mSYS_User.getTelephone(), mSYS_User.home_phone);
                    //显示窗口
                    menuWindow.showAtLocation(arg0, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                }


//                if (mSYS_User.getTelephone().equals("")) {
//                    if(!mSYS_User.getMobile().contains("*")){
//                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mSYS_User.getMobile()));
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//                    }else{
//                        Toast.makeText(context,"对不起，该号码不支持拨打，请您查询后重试",Toast.LENGTH_SHORT).show();
//                    }
//
//                } else if (mSYS_User.getMobile().equals("") && !mSYS_User.getTelephone().equals("")) {
//                    if(!mSYS_User.getTelephone().contains("*")){
//                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mSYS_User.getTelephone()));
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//                    }else{
//                        Toast.makeText(context,"对不起，该号码不支持拨打，请您查询后重试",Toast.LENGTH_SHORT).show();
//                    }
//
//                } else if (!mSYS_User.getMobile().equals("") && !mSYS_User.getTelephone().equals("")) {
//                    CallPhonePopupWindow menuWindow = new CallPhonePopupWindow(context, UserListListener.this, mSYS_User.getMobile(), mSYS_User.getTelephone(),mSYS_User.home_phone);
//                    //显示窗口
//                    menuWindow.showAtLocation(arg0, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
//                }
            } else {

                if (!arg0.getTag().toString().contains("*")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + arg0.getTag().toString()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "对不起，该号码不支持拨打，请您查询后重试", Toast.LENGTH_SHORT).show();
                }


            }

//			switch (arg0.getId()) {
//			case R.id.iv_SMS:
//				Toast.makeText(context, "发送短信", Toast.LENGTH_SHORT).show();
//				break;
//			case R.id.iv_phone:
//				if(!mSYS_User.getMobile().equals("")){
//					Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mSYS_User.getMobile()));
//					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					context.startActivity(intent);
//				}
//				break;
//			}
        }
    }
}
