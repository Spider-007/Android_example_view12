package com.htmitech.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.MyCheckBox;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.T_UserRelationship;
import com.htmitech.listener.CallCheckUserListener;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.PeopleHeadEnum;
import com.htmitech.others.LoadUserAvatar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 选择常用联系人
 */
public class ChooseContactAdapter extends BaseAdapter implements SectionIndexer {
    private LayoutInflater inflater;
    private Context context;
    private int res;
    private List<T_UserRelationship> list;
    private LoadUserAvatar avatarLoader;
    private PeopleHeadEnum mPeopleHeadEnum;
    private ArrayList<SYS_User> checkSYSUser; //选择的人员
    private ChooseWay mChooseWay;
    private CallCheckUserListener mCallCheckUserListener;

    public ChooseContactAdapter(Context context, int resource,
                                List<T_UserRelationship> objects, CallCheckUserListener mCallCheckUserListener) {
        this.context = context;
        this.mCallCheckUserListener = mCallCheckUserListener;
        if (list == null) {
            list = new ArrayList<T_UserRelationship>();
        } else {
            this.list = objects;
        }
        this.res = resource;
        checkSYSUser = BaseApplication.getApplication(context).getCheckALlUser();
        res = resource;
        mPeopleHeadEnum = BookInit.getInstance().getPeopleHeadEnum();
        mChooseWay = BookInit.getInstance().getmChooseWay();
        avatarLoader = new LoadUserAvatar(context, Constant.SDCARD_PATH);
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<T_UserRelationship> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final T_UserRelationship mT_UserRelationship = list.get(position);
        if (convertView == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, null);
            holder = new ViewHolder();

            holder.ivAvatar = (ImageView) convertView
                    .findViewById(R.id.iv_avatar);

            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvHeader = (TextView) convertView.findViewById(R.id.header);
            holder.user_relative = (RelativeLayout) convertView
                    .findViewById(R.id.user_relative);
            holder.default_tv = (TextView) convertView.findViewById(R.id.default_tv);
            TextPaint tp = holder.tvHeader.getPaint();
            tp.setFakeBoldText(true);
            holder.viewTemp = (View) convertView.findViewById(R.id.view_temp);
            holder.cb_people = (com.htmitech.app.widget.MyCheckBox) convertView.findViewById(R.id.cb_people);
//			if(mT_UserRelationship != null)
//				mT_UserRelationship.getmSYS_User().mCheckBox = holder.cb_people;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        java.util.Random random = new java.util.Random();
        int index = position % Constant.colorList.length;
        String phoneSrls = "";
		UserListListener mUserListListener = new UserListListener(mT_UserRelationship);
		holder.user_relative.setOnClickListener(mUserListListener);
        if (mT_UserRelationship != null) {
            SYS_User mSYS_User = mT_UserRelationship.getmSYS_User();
            holder.cb_people.setTag(mSYS_User.getUserId());


            if(BookInit.getInstance().getCheckAllUser().contains(mSYS_User)){
                mSYS_User.setIsCheck(true);
                holder.cb_people.setChecked(mSYS_User.getIsCheck());
            }else{
                holder.cb_people.setChecked(mSYS_User.getIsCheck());
            }

            mSYS_User.setmCheckBox(holder.cb_people);
            // 根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(position);
            // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (position == getPositionForSection(section)) {
                holder.tvHeader.setVisibility(View.VISIBLE);
                holder.tvHeader.setText("☆".equals(mT_UserRelationship
                        .getHeader()) ? mT_UserRelationship.getHeader()
                        + "(管理员)" : mT_UserRelationship.getHeader());
                holder.viewTemp.setVisibility(View.VISIBLE);
            } else {
                holder.tvHeader.setVisibility(View.GONE);
                holder.viewTemp.setVisibility(View.GONE);
            }
            holder.tvName.setText(mSYS_User
                    .getFullName());
            String phoneSrl = mT_UserRelationship.getmSYS_User().getPhotosurl() ;
            phoneSrls = phoneSrl == null ? "":phoneSrl;

            if (phoneSrls.equals("")) {
                String name = mSYS_User.getFullName();
                int color = mSYS_User.getColor();
                if (color == 0) {
                    mSYS_User.setColor(Constant.colorList[index]);
                    color = Constant.colorList[index];
                }
                holder.ivAvatar.setVisibility(View.INVISIBLE);
                GradientDrawable myGrad = (GradientDrawable) holder.default_tv.getBackground();
                myGrad.setColor(color);
                if (myGrad != null)
                    myGrad.setColor(color);
                Pattern p = Pattern.compile("[a-zA-Z]");
                Matcher m = p.matcher(name);
                int start = 0, end = 0;
                switch (mPeopleHeadEnum) {
                    case DEFAULT:
                        holder.default_tv.setVisibility(View.GONE);
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
                    holder.default_tv.setText(name.substring(start, end).toUpperCase() + "");
                } else {
                    if (name.length() < 2) {
                        holder.default_tv.setText(name);
                    } else {
                        holder.default_tv.setText(name.substring(start, end) + "");
                    }
                }
                holder.default_tv.setVisibility(View.VISIBLE);
                mSYS_User.nameJan = holder.default_tv.getText().toString();
                holder.cb_people.setOnClickListener(new DepartmentPeopleOnClickListener(mSYS_User, holder.cb_people, holder.ivAvatar, holder.default_tv, false));
            } else {
                holder.default_tv.setVisibility(View.GONE);
                holder.ivAvatar.setVisibility(View.VISIBLE);
                holder.cb_people.setOnClickListener(new DepartmentPeopleOnClickListener(mSYS_User, holder.cb_people, holder.ivAvatar, holder.default_tv, true));
            }
            holder.ivAvatar.setImageResource(R.drawable.default_useravatar);
            showUserAvatar(holder.ivAvatar, phoneSrls);
//            holder.user_relative.setOnClickListener(new UserListListener(mT_UserRelationship));
        }

        return convertView;
    }

    private void showUserAvatar(ImageView iamgeView, final String avatar) {
        if(avatar==null||avatar.equals("")) return;
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

    class ViewHolder {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvHeader;
        View viewTemp;
        TextView default_tv;
        MyCheckBox cb_people;
        RelativeLayout user_relative;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getHeader().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getHeader();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    class UserListListener implements OnClickListener {
        private T_UserRelationship mT_UserRelationship;

        public UserListListener(T_UserRelationship mT_UserRelationship) {
            this.mT_UserRelationship = mT_UserRelationship;
        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            if (arg0.getId() == R.id.user_relative) {
//				BookInit.getInstance().setCurrentUser(mT_UserRelationship.getmSYS_User());
//				Intent intent = new Intent(context, UserDetailsActivity.class);
////				intent.putExtra("currentSYS_Department",
////						mT_UserRelationship.getmSYS_User());
//				context.startActivity(intent);
            }
        }
    }

    public void setData(List<T_UserRelationship> list) {
        if (list == null) {
            this.list = new ArrayList<T_UserRelationship>();
        } else {
            this.list = new ArrayList<T_UserRelationship>(list);
        }
        this.notifyDataSetChanged();
    }

    public void callDelete() {

    }

    // CheckBox 选择页面
    class DepartmentPeopleOnClickListener implements View.OnClickListener {
        private SYS_User currentSYS_User;
        private CheckBox cbPeople;
        private ImageView ivAvatar;
        private TextView default_tv;
        private boolean isImage;

        public DepartmentPeopleOnClickListener(SYS_User currentSYS_User, CheckBox cbPeople, ImageView ivAvatar, TextView default_tv, boolean isImage) {
            this.currentSYS_User = currentSYS_User;
            this.cbPeople = cbPeople;
            this.ivAvatar = ivAvatar;
            this.isImage = isImage;
            this.default_tv = default_tv;
        }

        @Override
        public void onClick(View position) {
            switch (mChooseWay) {
                case SINGLE_CHOOSE:    //人员单选
                    for (SYS_User mSYS_User : checkSYSUser) {
                        mSYS_User.setIsCheck(false);
                        mSYS_User.mCheckBox.setChecked(false);
                    }
                    checkSYSUser.clear();
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
            BaseApplication.getApplication(context).setCheck(1, currentSYS_User);
            BaseApplication.getApplication(context).setCheckUserChangyong(checkSYSUser);
        }
    }
}
