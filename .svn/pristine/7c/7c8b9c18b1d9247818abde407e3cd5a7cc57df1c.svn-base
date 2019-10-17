package com.htmitech.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.addressbook.R;
import com.htmitech.addressbook.UserDetailsActivity;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.CircleView;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.T_UserRelationship;
import com.htmitech.listener.CallBackUserCount;
import com.htmitech.myEnum.PeopleHeadEnum;
import com.htmitech.others.LoadUserAvatar;
import com.htmitech.pop.CallPhonePopupWindow;
import com.htmitech.pop.DeleteUserPopupWindow;
import com.htmitech.unit.ConfigStyleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 好友Adapter实现
 */
public class ContactAdapter extends BaseAdapter implements SectionIndexer {
    private LayoutInflater inflater;
    private Context context;
    private int res;
    private List<T_UserRelationship> list;
    private LoadUserAvatar avatarLoader;
    private CallBackUserCount mCallBackUserCount;
    private PeopleHeadEnum mPeopleHeadEnum;

    public ContactAdapter(Context context, int resource,
                          List<T_UserRelationship> objects, CallBackUserCount mCallBackUserCount) {
        this.context = context;
        this.mCallBackUserCount = mCallBackUserCount;
        if (list == null) {
            list = new ArrayList<T_UserRelationship>();
        } else {
            this.list = objects;
        }
        this.res = resource;
        res = resource;
        avatarLoader = new LoadUserAvatar(context, Constant.SDCARD_PATH);
        mPeopleHeadEnum = BookInit.getInstance().getPeopleHeadEnum();
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
            holder.tv_phone = (TextView) convertView
                    .findViewById(R.id.tv_phone);
            holder.iv_SMS = (ImageView) convertView.findViewById(R.id.iv_SMS);
            holder.iv_phone = (ImageView) convertView
                    .findViewById(R.id.iv_phone);
            holder.user_relative = (RelativeLayout) convertView
                    .findViewById(R.id.user_relative);
            holder.default_tv = (TextView) convertView.findViewById(R.id.default_tv);
            TextPaint tp = holder.tvHeader.getPaint();
            tp.setFakeBoldText(true);
            holder.viewTemp = (View) convertView.findViewById(R.id.view_temp);


            convertView.setTag(holder);
        } else if (((ViewHolder) convertView.getTag()).needInflate) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, null);
            holder = new ViewHolder();

            holder.ivAvatar = (ImageView) convertView
                    .findViewById(R.id.iv_avatar);

            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvHeader = (TextView) convertView.findViewById(R.id.header);
            holder.tv_phone = (TextView) convertView
                    .findViewById(R.id.tv_phone);
            holder.iv_SMS = (ImageView) convertView.findViewById(R.id.iv_SMS);
            holder.iv_phone = (ImageView) convertView
                    .findViewById(R.id.iv_phone);
            holder.user_relative = (RelativeLayout) convertView
                    .findViewById(R.id.user_relative);
            TextPaint tp = holder.tvHeader.getPaint();
            tp.setFakeBoldText(true);
            holder.viewTemp = (View) convertView.findViewById(R.id.view_temp);
            holder.default_tv = (CircleView) convertView.findViewById(R.id.default_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        java.util.Random random = new java.util.Random();
        int index = position % Constant.colorList.length;
//        if (mT_UserRelationship.getmSYS_User().getMobile() != null && mT_UserRelationship.getmSYS_User().getMobile().equals("")) {
//            if (mT_UserRelationship.getmSYS_User().getTelephone().equals("")) {
//                holder.iv_phone.setImageResource(R.drawable.btn_phone_disabled);
//                holder.iv_phone.setEnabled(false);
//            } else {
//                holder.iv_phone.setImageResource(R.drawable.btn_phone_selected);
//                holder.iv_phone.setEnabled(true);
//            }
//
//        } else {
//            holder.iv_phone.setImageResource(R.drawable.btn_phone_selected);
//            holder.iv_phone.setEnabled(true);
//        }
        if(TextUtils.isEmpty(mT_UserRelationship.getmSYS_User().getMobile()) && TextUtils.isEmpty(mT_UserRelationship.getmSYS_User().getTelephone()) && TextUtils.isEmpty(mT_UserRelationship.getmSYS_User().getHome_phone())){
            holder.iv_phone.setImageResource(R.drawable.btn_phone_disabled);
            holder.iv_phone.setEnabled(false);
        }else{

            boolean mobile = mT_UserRelationship.getmSYS_User().getMobile().equals("") || mT_UserRelationship.getmSYS_User().getMobile().contains("*");
            boolean titlePhone = mT_UserRelationship.getmSYS_User().getTelephone().equals("") || mT_UserRelationship.getmSYS_User().getTelephone().contains("*");
            boolean homePhone = mT_UserRelationship.getmSYS_User().getHome_phone().equals("") || mT_UserRelationship.getmSYS_User().getHome_phone().contains("*");
            if(mobile && titlePhone && homePhone){
                holder.iv_phone.setImageResource(R.drawable.btn_phone_disabled);
                holder.iv_phone.setEnabled(false);
            }else{
                holder.iv_phone.setImageResource(R.drawable.btn_phone_selected);
                holder.iv_phone.setEnabled(true);
            }
        }
        if (mT_UserRelationship.getmSYS_User().getEmiType() != null) {
            if (mT_UserRelationship.getmSYS_User().getEmiType() == 1) {//如果是EMI用户 就可以进行短信
                holder.iv_SMS.setVisibility(View.VISIBLE);
            } else {
                holder.iv_SMS.setVisibility(View.GONE);
            }
        }
        if (mT_UserRelationship.getmSYS_User().getmTD_User() != null) {
            switch (mT_UserRelationship.getmSYS_User().getmTD_User().getAction()) {
                case 0:
                    break;
                case 1:
                    holder.iv_phone.setImageResource(R.drawable.btn_phone_selected);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    holder.iv_phone.setImageResource(R.drawable.btn_phone_disabled);
                    break;
            }
        }
        UserListListener mUserListListener = new UserListListener(mT_UserRelationship);
        holder.iv_SMS.setOnClickListener(mUserListListener);
        holder.iv_phone.setOnClickListener(mUserListListener);
        holder.user_relative.setOnClickListener(mUserListListener);
        if (mT_UserRelationship != null) {
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
            holder.tvName.setText(mT_UserRelationship.getmSYS_User()
                    .getFullName());

            if (!Constant.ADDRESS_LIST_MESSAGE.equals("")) {
                try {
                    String temp = Constant.ADDRESS_LIST_MESSAGE;
                    temp = ConfigStyleUtil.changeConfig(temp, mT_UserRelationship.getmSYS_User());
                    if(!"".equals(temp.trim())){
                        holder.tv_phone.setVisibility(View.VISIBLE);
                        holder.tv_phone.setText(temp);
                    }else{
                        holder.tv_phone.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            DensityUtil.cellPhone(mT_UserRelationship.getmSYS_User()
//                    .getMobile(), holder.tv_phone);

//			holder.tv_phone.setText(mT_UserRelationship.getmSYS_User()
//					.getMobile());
            String phoneSrl = mT_UserRelationship.getmSYS_User().getPhotosurl();
            String phoneSrls = phoneSrl == null ? "" : phoneSrl;
            short headType = Short.parseShort(Constant.ADDRESS_HEADER_TYPE);
            if (phoneSrls.equals("")) {
                String name = mT_UserRelationship.getmSYS_User().getFullName();
                if (headType == 4) {
                    name = mT_UserRelationship.getmSYS_User().getUserNickname();
                }
                int color = mT_UserRelationship.getmSYS_User().getColor();
                if (color == 0) {
                    mT_UserRelationship.getmSYS_User().setColor(Constant.colorList[index]);
                    color = Constant.colorList[index];
                }
                GradientDrawable myGrad = (GradientDrawable) holder.default_tv.getBackground();
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

                switch (headType) {
                    case 0:
                        holder.default_tv.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        holder.default_tv.setVisibility(View.GONE);
                        break;
                    case 2:
                        holder.default_tv.setVisibility(View.VISIBLE);
                        start = name.length() - 2;
                        end = name.length();
                        break;
                    case 3:
                        holder.default_tv.setVisibility(View.VISIBLE);
                        start = 0;
                        end = 1;
                        break;
                    case 4:
                        holder.default_tv.setVisibility(View.VISIBLE);
                        start = 0;
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
//				mViewHolderChildPeople.default_tv.setVisibility(View.VISIBLE);
                mT_UserRelationship.getmSYS_User().nameJan = holder.default_tv.getText().toString();
            } else {
                holder.default_tv.setVisibility(View.GONE);
            }
            holder.ivAvatar.setImageResource(R.drawable.default_useravatar);
            showUserAvatar(holder.ivAvatar, phoneSrls);

        }
        holder.user_relative.setOnLongClickListener(new MyOnLong(position, convertView, mT_UserRelationship));
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

    public class MyOnLong implements View.OnLongClickListener {
        public int index;
        private View convertView;
        private T_UserRelationship mT_UserRelationship;

        public MyOnLong(int index, View convertView, T_UserRelationship mT_UserRelationship) {
            this.index = index;
            this.convertView = convertView;
            this.mT_UserRelationship = mT_UserRelationship;
        }

        @Override
        public boolean onLongClick(final View views) {
            menuWindow = new DeleteUserPopupWindow(context, new MyClick(views, index, convertView, mT_UserRelationship));
            //显示窗口
            menuWindow.showAtLocation(views, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置

            return false;
        }
    }

    DeleteUserPopupWindow menuWindow;

    public class MyClick implements OnClickListener {
        private View convertViews;
        private View linearViews;
        public int indexs;
        private T_UserRelationship mT_UserRelationship;

        public MyClick(View linearView, int index, View convertView, T_UserRelationship mT_UserRelationship) {
            indexs = index;
            convertViews = convertView;
            this.mT_UserRelationship = mT_UserRelationship;
            linearViews = linearView;
        }

        @Override
        public void onClick(View view) {
//			deleteCell(linearViews,indexs,convertViews); //暂时去除删除动画
            BookInit.getInstance().getmCallbackMX().callRemoveCUser(mT_UserRelationship,BookInit.getInstance().getTxlAppId());
            menuWindow.dismiss();
        }
    }

    public void delete() {
//		if(convertViews == null){
//			this.notifyDataSetChanged();
//		}else{
//			deleteCell(BookInit.getInstance().getLinearViews(),BookInit.getInstance().getIndexs(),BookInit.getInstance().getConvertViews());
//		}

    }

    class ViewHolder {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvHeader;
        View viewTemp;
        TextView tv_phone;
        ImageView iv_SMS;
        ImageView iv_phone;
        TextView default_tv;
        RelativeLayout user_relative;
        boolean needInflate = false;
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
            if (arg0.getId() == R.id.iv_SMS) {
                BookInit.getInstance().getmCallbackMX().callbackSendMessage(mT_UserRelationship.getmSYS_User().getLogin_name());
            } else if (arg0.getId() == R.id.iv_phone) {
                SYS_User mSYS_User = mT_UserRelationship.getmSYS_User();

                if(!TextUtils.isEmpty(mSYS_User.getTelephone()) && TextUtils.isEmpty(mSYS_User.getMobile()) && TextUtils.isEmpty(mSYS_User.getHome_phone())){
                    if(!mSYS_User.getTelephone().contains("*") && !Constant.com_addressbook_mobileconfig_office_phone_secrecy.equals("2")){
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mSYS_User.getTelephone()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(context,"对不起，该号码不支持拨打，请您查询后重试",Toast.LENGTH_SHORT).show();
                    }
                }else if(TextUtils.isEmpty(mSYS_User.getTelephone()) && !TextUtils.isEmpty(mSYS_User.getMobile()) && TextUtils.isEmpty(mSYS_User.getHome_phone())){
                    if(!mSYS_User.getMobile().contains("*") && !Constant.com_addressbook_mobileconfig_mobile_phone_secrecy.equals("2")){
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mSYS_User.getMobile()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(context,"对不起，该号码不支持拨打，请您查询后重试",Toast.LENGTH_SHORT).show();
                    }
                }else if(TextUtils.isEmpty(mSYS_User.getTelephone()) && TextUtils.isEmpty(mSYS_User.getMobile()) && !TextUtils.isEmpty(mSYS_User.getHome_phone())){
                    if(!mSYS_User.getHome_phone().contains("*") && !Constant.com_addressbook_mobileconfig_home_phone_secrecy.equals("2")){
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mSYS_User.getHome_phone()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(context,"对不起，该号码不支持拨打，请您查询后重试",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    CallPhonePopupWindow menuWindow = new CallPhonePopupWindow(context, UserListListener.this, mSYS_User.getMobile(), mSYS_User.getTelephone(),mSYS_User.home_phone);
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
//                } else if (mSYS_User.getMobile().equals("") && !mSYS_User.getTelephone().equals("")) {
//                    if(!mSYS_User.getTelephone().contains("*")){
//                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mSYS_User.getTelephone()));
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//                    }else{
//                        Toast.makeText(context,"对不起，该号码不支持拨打，请您查询后重试",Toast.LENGTH_SHORT).show();
//                    }
//                } else if (!mSYS_User.getMobile().equals("") && !mSYS_User.getTelephone().equals("")) {
//
//                }
//				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mT_UserRelationship.getmSYS_User().getMobile()));
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
            } else if (arg0.getId() == R.id.user_relative) {
                BookInit.getInstance().setCurrentUser(mT_UserRelationship.getmSYS_User());
                Intent intent = new Intent(context, UserDetailsActivity.class);
                context.startActivity(intent);
            } else {
                SYS_User mSYS_User = mT_UserRelationship.getmSYS_User();
                if(!arg0.getTag().toString().contains("*") ){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + arg0.getTag().toString()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context,"对不起，该号码不支持拨打，请您查询后重试",Toast.LENGTH_SHORT).show();
                }

            }
//			switch (arg0.getId()) {
//			case R.id.iv_SMS:
//				Toast.makeText(context, "发送短信", Toast.LENGTH_SHORT).show();
//				break;
//			case R.id.iv_phone:
//				Toast.makeText(context, "拨打电话", Toast.LENGTH_SHORT).show();
//				break;
//			case R.id.user_relative:
//				Intent intent = new Intent(context, UserDetailsActivity.class);
//				intent.putExtra("currentSYS_Department",
//						mT_UserRelationship.getmSYS_User());
//				context.startActivity(intent);
//				break;
//			}
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

    //长安点击删除  此处添加回调函数
    private void deleteCell(final View v, final int index, final View convertView) {
        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                list.remove(index);
                mCallBackUserCount.callUserCount(list.size());
                ViewHolder vh = (ViewHolder) convertView.getTag();
                vh.needInflate = true;
                ContactAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        };
        collapse(v, al);
    }

    private void collapse(final View v, Animation.AnimationListener al) {
        final int initialHeight = v.getMeasuredHeight();

        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        if (al != null) {
            anim.setAnimationListener(al);
        }
        anim.setDuration(200);
        v.startAnimation(anim);
    }

    public void callDelete() {

    }
}
