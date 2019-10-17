package com.htmitech.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.MyCheckBox;
import com.htmitech.domain.OrgUser;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.CallCheckUserListener;
import com.htmitech.listener.ChildFragmentListener;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.myEnum.PeopleHeadEnum;
import com.htmitech.others.LoadUserAvatar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 层级选择人员
 */
public class UserDetailChooseChildAdapter extends BaseAdapter {
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
    private ArrayList<SYS_User> checkSYSUser; //选择的人员
    private ArrayList<SYS_Department> checkSYSDepartment;//选择的部门
    private ChooseWay mChooseWay;
    private CallCheckUserListener mCallCheckUserListener;
    public ChooseWayEnum mChooseWayEnum;
    public boolean isSoso = false;

    public void setSoso(boolean isSoso) {
        this.isSoso = isSoso;
    }

    private PeopleHeadEnum mPeopleHeadEnum;
    public boolean system = false;

    public void chooseWay(ChooseWayEnum mChooseWayEnum) {
        this.mChooseWayEnum = mChooseWayEnum;
    }

    public void setCheckSYSUser(ArrayList<SYS_User> checkSYSUser) {
        if (checkSYSUser != null) {
            this.checkSYSUser = checkSYSUser;
        }
    }

    public void setCheckSYSDepartment(ArrayList<SYS_Department> checkSYSDepartment) {
        if (checkSYSDepartment != null) {
            this.checkSYSDepartment = checkSYSDepartment;
        }
    }

    public UserDetailChooseChildAdapter(SYS_Department mSYS_Department,
                                        ChildFragmentListener mChildFragmentListener, boolean search,
                                        Context context, CallCheckUserListener mCallCheckUserListener) {
        mChooseWayEnum = BookInit.getInstance().getChooseWayEnum();
        this.mCallCheckUserListener = mCallCheckUserListener;
        this.search = search;

        arrDepartment = new ArrayList<SYS_Department>();
        checkSYSUser = BaseApplication.getApplication(context).getCheckALlUser();
        checkSYSDepartment = BaseApplication.getApplication(context).getCheckSYSDepartment();
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
        } else {
            arraySYS_User = new ArrayList<SYS_User>();
        }
        if (mSYS_Department != null && mSYS_Department.getSYS_DepartmentList() != null) {
            arrDepartment = (ArrayList<SYS_Department>) mSYS_Department
                    .getSYS_DepartmentList();
        }
        if (arrDepartment.size() == 0 && arraySYS_User.size() == 0) {
            return;
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
        mPeopleHeadEnum = BookInit.getInstance().getPeopleHeadEnum();
        mChooseWay = BookInit.getInstance().getmChooseWay();
        Collections.sort(arrDepartment, comp);
        avatarLoader = new LoadUserAvatar(context, Constant.SDCARD_PATH);
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
            return arraySYS_User.get(Math.abs(arrDepartment.size() - position));
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
            SYS_Department crrentDepartment = arrDepartment.get(position);
            if (convertView == null) {
                mViewHolderChild = new ViewHolderChild();
                viewDepartment = inflater.inflate(
                        R.layout.ht_fragment_choose_contactlist_adapter, null);
                mViewHolderChild.ivAvatar = (ImageView) viewDepartment
                        .findViewById(R.id.iv_avatar);
                mViewHolderChild.tv_department_name = (TextView) viewDepartment
                        .findViewById(R.id.tv_department_name);
                mViewHolderChild.fragment_child = (RelativeLayout) viewDepartment
                        .findViewById(R.id.fragment_child);
                mViewHolderChild.iv_label = (ImageView) viewDepartment
                        .findViewById(R.id.iv_label);
                mViewHolderChild.iv_right = (ImageView) viewDepartment.findViewById(R.id.iv_right);
                mViewHolderChild.cbChild = (CheckBox) viewDepartment.findViewById(R.id.cb_select_child);
                viewDepartment.setTag(R.id.tag_department, mViewHolderChild);
                convertView = viewDepartment;
            } else {
                mViewHolderChild = (ViewHolderChild) convertView
                        .getTag(R.id.tag_department);
            }
            switch (mChooseWayEnum) {
                case DEPARTMENTCHOOSE: //部门选择
                    mViewHolderChild.cbChild.setVisibility(View.VISIBLE);
                    mViewHolderChild.iv_right.setVisibility(View.VISIBLE);
                    break;
                case PEOPLECHOOSE:   //人员选择
                    mViewHolderChild.cbChild.setVisibility(View.GONE);
                    break;
                case FREECHOOSE:    //自由选择
                    mViewHolderChild.cbChild.setVisibility(View.VISIBLE);
                    break;
            }
            /**
             * 部门展示添加如果由部门的话那么就有右箭头提醒 如果没有的话 就不给提醒
             */
            if (crrentDepartment.getSYS_DepartmentList() == null || crrentDepartment.getSYS_DepartmentList().size() == 0) {
                mViewHolderChild.iv_right.setVisibility(View.GONE);
            } else {
                mViewHolderChild.iv_right.setVisibility(View.VISIBLE);
            }

            mViewHolderChild.iv_label.setVisibility(View.GONE);
            mViewHolderChild.cbChild.setChecked(crrentDepartment.getIsCheck());
            crrentDepartment.mCheckBox = mViewHolderChild.cbChild;
            mViewHolderChild.tv_department_name.setText(crrentDepartment.getFullName());
            mViewHolderChild.fragment_child
                    .setOnClickListener(new DepartmentOnClickListener(
                            mSYS_Department, crrentDepartment, true, mViewHolderChild.cbChild, mViewHolderChild.ivAvatar));
            mViewHolderChild.cbChild.setOnClickListener(new DepartmentOnClickListener(
                    mSYS_Department, crrentDepartment, false, mViewHolderChild.cbChild, mViewHolderChild.ivAvatar));
        } else {
            if (itemType == TYPE_USER_TAG) {
                View viewUser = null;
                SYS_User mSYS_User = (SYS_User) getItem(position);
                if (convertView == null) {
                    mViewHolderChildPeople = new ViewHolderChildPeople();
                    viewUser = inflater.inflate(R.layout.ht_item_contact_choose_list, null);
                    mViewHolderChildPeople.ivAvatar = (ImageView) viewUser
                            .findViewById(R.id.iv_avatar);
                    mViewHolderChildPeople.tvName = (TextView) viewUser
                            .findViewById(R.id.tv_name);
                    mViewHolderChildPeople.tv_phone = (TextView) viewUser
                            .findViewById(R.id.tv_phone);
                    mViewHolderChildPeople.user_relative = (RelativeLayout) viewUser
                            .findViewById(R.id.user_relative);
                    mViewHolderChildPeople.default_tv = (TextView) viewUser.findViewById(R.id.default_tv);
                    mViewHolderChildPeople.cbPeople = (com.htmitech.app.widget.MyCheckBox) viewUser.findViewById(R.id.cb_people);
                    viewUser.setTag(R.id.tag_people, mViewHolderChildPeople);
                    convertView = viewUser;
                } else {
                    mViewHolderChildPeople = (ViewHolderChildPeople) convertView
                            .getTag(R.id.tag_people);
                }
                switch (mChooseWayEnum) {
                    case DEPARTMENTCHOOSE: //部门选择
                        mViewHolderChildPeople.cbPeople.setVisibility(View.GONE);
                        break;
                    case PEOPLECHOOSE:   //人员选择
                        mViewHolderChildPeople.cbPeople.setVisibility(View.VISIBLE);
                        break;

                    case FREECHOOSE:   //自由选择
                        mViewHolderChildPeople.cbPeople.setVisibility(View.VISIBLE);
                        break;
                }
                int index = position % Constant.colorList.length;

                if(BookInit.getInstance().getCheckAllUser().contains(mSYS_User)){
                    mSYS_User.setIsCheck(true);
                    for(SYS_User sys_user : BookInit.getInstance().getCheckAllUser()){
                        if(sys_user.mCheckBox == null && sys_user.getUserId().equals(mSYS_User.getUserId()) ){
                            sys_user.setmCheckBox(mViewHolderChildPeople.cbPeople);
                            break;
                        }
                    }
                    mViewHolderChildPeople.cbPeople.setChecked(mSYS_User.getIsCheck());
                }else{
                    mViewHolderChildPeople.cbPeople.setChecked(mSYS_User.getIsCheck());
                }
                mViewHolderChildPeople.cbPeople.setTag(mSYS_User.getUserId());
                mSYS_User.setmCheckBox(mViewHolderChildPeople.cbPeople);

                mViewHolderChildPeople.tvName.setText(mSYS_User.getFullName());
                String phoneSrl = mSYS_User.getPhotosurl();
                final String phoneSrls = phoneSrl == null ? "" : phoneSrl;
                if (phoneSrls.equals("")) {
                    String name = mSYS_User.getFullName();
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
                    if (mPeopleHeadEnum == null) {
                        mPeopleHeadEnum = PeopleHeadEnum.DEFAULT;
                    }
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

                    if (m.matches()) {
                        mViewHolderChildPeople.default_tv.setText(name.substring(start, end).toUpperCase() + "");
                    } else {
                        if (name.length() < 2) {
                            mViewHolderChildPeople.default_tv.setText(name);
                        } else {
                            mViewHolderChildPeople.default_tv.setText(name.substring(start, end) + "");
                        }
                    }
                    mViewHolderChildPeople.default_tv.setVisibility(View.VISIBLE);
                    mViewHolderChildPeople.cbPeople.setOnClickListener(new DepartmentPeopleOnClickListener(
                            mSYS_User, mViewHolderChildPeople.cbPeople, mViewHolderChildPeople.ivAvatar, false, mViewHolderChildPeople.default_tv, false));
                    mSYS_User.nameJan = mViewHolderChildPeople.default_tv.getText().toString();
                } else {
                    mViewHolderChildPeople.default_tv.setVisibility(View.GONE);
                    mViewHolderChildPeople.cbPeople.setOnClickListener(new DepartmentPeopleOnClickListener(
                            mSYS_User, mViewHolderChildPeople.cbPeople, mViewHolderChildPeople.ivAvatar, false, mViewHolderChildPeople.default_tv, true));
                }
                mViewHolderChildPeople.ivAvatar.setImageResource(R.drawable.default_useravatar);
                showUserAvatar(mViewHolderChildPeople.ivAvatar, phoneSrls);
                mViewHolderChildPeople.user_relative.setOnClickListener(new DepartmentPeopleOnClickListener(
                        mSYS_User, mViewHolderChildPeople.cbPeople, mViewHolderChildPeople.ivAvatar, true, mViewHolderChildPeople.default_tv, true));
                if (search) {
                    mViewHolderChildPeople.tv_phone.setText(mSYS_User
                            .getmSYS_Department().getFullName());
                } else {
                    mViewHolderChildPeople.tv_phone.setText(mSYS_User.getMobile());

                }
            }
        }

        return convertView;
    }

    private void showUserAvatar(ImageView iamgeView, final String avatar) {
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
        CheckBox cbChild;
        ImageView iv_label;
        ImageView iv_right;
        RelativeLayout fragment_child;
    }

    class ViewHolderChildPeople {
        ImageView ivAvatar;
        TextView tvName;
        TextView tv_phone;
        MyCheckBox cbPeople;
        TextView default_tv;
        RelativeLayout user_relative;
    }

    // 部门进入下一级菜单 和点击部门选择
    class DepartmentOnClickListener implements OnClickListener {
        private SYS_Department currentSYS_Department;
        private SYS_Department upSYS_Department;
        private boolean flag;
        private CheckBox cbPeople;
        private ImageView ivAvatar;

        public DepartmentOnClickListener(SYS_Department upSYS_Department,
                                         SYS_Department currentSYS_Department, boolean flag, CheckBox cbPeople, ImageView ivAvatar) {
            this.currentSYS_Department = currentSYS_Department;
            this.upSYS_Department = upSYS_Department;
            this.flag = flag;
            this.cbPeople = cbPeople;
            this.ivAvatar = ivAvatar;
        }

        @Override
        public void onClick(View position) {
            if (flag) {
                if (!isSoso) {
                    mChildFragmentListener.notifyDataSetChanged(upSYS_Department,
                            currentSYS_Department);
                }
            } else {
                boolean isCheck = cbPeople.isChecked();
                /**
                 * 自由选择的时候是单选
                 */
                switch (mChooseWayEnum) {
                    case DEPARTMENTCHOOSE: //部门选择
                        break;
                    case PEOPLECHOOSE:   //人员选择
                        break;
                    case FREECHOOSE: // 自由选择
                        for (SYS_User mSYS_User : checkSYSUser) {
                            mSYS_User.setIsCheck(false);
                            mSYS_User.mCheckBox.setChecked(false);
                        }
                        checkSYSUser.clear();
                        for (SYS_Department mSYS_Department : checkSYSDepartment) {
                            mSYS_Department.setIsCheck(false);
                            mSYS_Department.mCheckBox.setChecked(false);
//							mSYS_Department.setIsCheck(isCheck);
                        }
                        checkSYSDepartment.clear();
                        break;
                }

                switch (mChooseWay) {
                    case SINGLE_CHOOSE:    //人员单选
                        for (SYS_Department mSYS_Department : checkSYSDepartment) {
                            mSYS_Department.setIsCheck(false);
                            mSYS_Department.mCheckBox.setChecked(false);
//							mSYS_Department.setIsCheck(isCheck);
                        }
                        checkSYSDepartment.clear();
                        break;
                    case MORE_CHOOSE://人员多选 什么都不做
                        break;
                }

                currentSYS_Department.setIsCheck(isCheck);
                if (!isCheck) {
                    checkSYSDepartment.remove(currentSYS_Department);
                }
                mCallCheckUserListener.checkDepartment(currentSYS_Department, checkSYSDepartment, cbPeople.isChecked(), ivAvatar, cbPeople);
            }

        }
    }

//	public OrgUser copyOrgUser(SYS_User mSYS_User){
//		OrgUser mOrgUser = new OrgUser();
//		mOrgUser.setUser_id(Long.parseLong(mSYS_User.getUserId()));
//		mOrgUser.setUser_name(mSYS_User.getFullName());
//		mOrgUser.setUser_type((short) mSYS_User.getUserType());
//		mOrgUser.setAdmin_type(mSYS_User.getAdminType());
//		mOrgUser.setEmi_type(mSYS_User.getEmiType());
//		mOrgUser.setUser_nickname(mSYS_User.getUserNickname());
//		mOrgUser.setUser_pyname(mSYS_User.getSuoxie());
//		mOrgUser.setDisplay_order(mSYS_User.getDisplay_order());
//		mOrgUser.setMobile_phone(mSYS_User.getMobile());
//		mOrgUser.setOffice_phone(mSYS_User.getOffice());
//		mOrgUser.setPic_path(mSYS_User.getPhotosurl());
//		mOrgUser.setRemark(mSYS_User.getRemark());
//		mOrgUser.setGender(mSYS_User.getGender());
//		mOrgUser.setTitle(mSYS_User.getTitle());
//		mOrgUser.setBirthday(mSYS_User.getBirthday());
//		mOrgUser.setHome_phone(mSYS_User.getHome_phone());
//		mOrgUser.setOffice_address(mSYS_User.getOffice_address());
//		mOrgUser.setPostal_code(mSYS_User.getPostal_code());
//		mOrgUser.setPic_path(mSYS_User.getPhotosurl());
//		mOrgUser.setHead_type(mSYS_User.getHeadType());
//		mOrgUser.setFax(mSYS_User.getFax());
//		mOrgUser.setEfn1(mSYS_User.getEfn1());
//		mOrgUser.setEfn2(mSYS_User.getEfn2());
//		mOrgUser.setEfn3(mSYS_User.getEfn3());
//		mOrgUser.setEfi1(mSYS_User.getEfi1());
//		mOrgUser.setEfi2(mSYS_User.getEfi2());
//		mOrgUser.setEfi3(mSYS_User.getEfi3());
//		mOrgUser.setEfi4(mSYS_User.getEfi4());
//		mOrgUser.setEfi5(mSYS_User.getEfi5());
//		mOrgUser.setEfd1(mSYS_User.getEfd1());
//		mOrgUser.setEfd2(mSYS_User.getEfd2());
//		mOrgUser.setEfs1(mSYS_User.getEfs1());
//		mOrgUser.setEfs2(mSYS_User.getEfs2());
//		mOrgUser.setEfs3(mSYS_User.getEfs3());
//		mOrgUser.setEfs4(mSYS_User.getEfs4());
//		mOrgUser.setEfs5(mSYS_User.getEfs5());
//		mOrgUser.setEfs6(mSYS_User.getEfs6());
//		mOrgUser.setEfs7(mSYS_User.getEfs7());
//		mOrgUser.setEfs8(mSYS_User.getEfs8());
//		mOrgUser.setEfs9(mSYS_User.getEfs9());
//		mOrgUser.setEfs10(mSYS_User.getEfs10());
//		mOrgUser.setEmail(mSYS_User.getEmail());
//		return mOrgUser;
//	}

    // CheckBox 选择页面
    class DepartmentPeopleOnClickListener implements OnClickListener {
        private SYS_User currentSYS_User;
        private CheckBox cbPeople;
        private ImageView ivAvatar;
        private TextView default_tv;
        private boolean flag; //表示是进入人员详细信息 还是选择
        private boolean isImage;

        public DepartmentPeopleOnClickListener(SYS_User currentSYS_User, CheckBox cbPeople, ImageView ivAvatar, boolean flag, TextView default_tv, boolean isImage) {
            this.currentSYS_User = currentSYS_User;
            this.cbPeople = cbPeople;
            this.ivAvatar = ivAvatar;
            this.flag = flag;
            this.isImage = isImage;
            this.default_tv = default_tv;
        }

        @Override
        public void onClick(View position) {
            if (flag) {
                if (!system) {
//					BookInit.getInstance().setCurrentUser(currentSYS_User);
//					Intent intent = new Intent(context, UserDetailsActivity.class);
////				intent.putExtra("currentSYS_Department", copyUser(currentSYS_User));
//					context.startActivity(intent);

                }

            } else {
                /**
                 * 自由选择的时候是单选
                 */
                switch (mChooseWayEnum) {
                    case DEPARTMENTCHOOSE: //部门选择
                        break;
                    case PEOPLECHOOSE:   //人员选择
                        break;
                    case FREECHOOSE: // 自由选择
                        if (checkSYSUser != null) {
                            for (SYS_User mSYS_User : checkSYSUser) {
                                mSYS_User.setIsCheck(false);
                                mSYS_User.mCheckBox.setChecked(false);
                            }
                            checkSYSUser.clear();
                        }
                        if (null != checkSYSDepartment) {
                            for (SYS_Department mSYS_Department : checkSYSDepartment) {
                                mSYS_Department.setIsCheck(false);
                                mSYS_Department.mCheckBox.setChecked(false);
//							mSYS_Department.setIsCheck(isCheck);
                            }
                            checkSYSDepartment.clear();
                        }
                        break;
                }
                if (mChooseWay == null) {
                    mChooseWay = ChooseWay.SINGLE_CHOOSE;
                }
                switch (mChooseWay) {
                    case SINGLE_CHOOSE:    //人员单选
                        if (checkSYSUser != null) {
                            for (SYS_User mSYS_User : checkSYSUser) {
                                mSYS_User.setIsCheck(false);
                                mSYS_User.mCheckBox.setChecked(false);
                            }
                            checkSYSUser.clear();
                        }
                        break;
                    case MORE_CHOOSE://人员多选 什么都不做
                        break;
                }
                currentSYS_User.setIsCheck(cbPeople.isChecked());

                if (cbPeople.isChecked()) {
                    mCallCheckUserListener.checkUser(currentSYS_User, checkSYSUser, true, ivAvatar, cbPeople, default_tv, isImage);
                } else {
                    checkSYSUser.remove(currentSYS_User);
                    mCallCheckUserListener.checkUser(currentSYS_User, checkSYSUser, false, ivAvatar, cbPeople, default_tv, isImage);
                }
                switch (mChooseWayEnum) {
                    case DEPARTMENTCHOOSE: //部门选择
                        break;
                    case PEOPLECHOOSE:   //人员选择
                        BaseApplication.getApplication(context).setCheck(2, currentSYS_User);
                        BaseApplication.getApplication(context).setCheckUserCengji(checkSYSUser);
                        break;
                    case FREECHOOSE: // 自由选择
                        break;
                }
            }
        }
    }
}
