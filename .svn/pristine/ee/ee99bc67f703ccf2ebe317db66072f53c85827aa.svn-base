package com.htmitech.addressbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.htmitech.adapter.FragmentPeopleMessageAdapter;
import com.htmitech.api.BookInit;
import com.htmitech.dao.SYS_UserDAO;
import com.htmitech.dao.TD_UserDAO;
import com.htmitech.domain.OrgUser;
import com.htmitech.domain.PeopleMessage;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.TD_User;
import com.htmitech.photo.TailoringActivity;
import com.htmitech.unit.ActivityUnit;
import com.htmitech.unit.ConfigStyleUtil;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import cn.feng.skin.manager.base.BaseFragmentActivity;

/**
 * Created by htrf-pc on 2016/6/3.
 */
public class PeopleMessageActivity extends BaseFragmentActivity {
    private ListView lv_people_message;
    private ArrayList<PeopleMessage> modelName;
    private ImageView btn_daiban_person;
    private  FragmentPeopleMessageAdapter mFragmentPeopleMessageAdapter;
    private boolean isHome = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ht_activity_people_message);
        initView();
        initDate();
    }

    public void initView(){
        lv_people_message = (ListView) findViewById(R.id.lv_people_message);
        btn_daiban_person = (ImageView)findViewById(R.id.btn_daiban_person);
    }

//    public OrgUser copyOrgUser(SYS_User mSYS_User){
//        OrgUser mOrgUser = new OrgUser();
//        mOrgUser.setUser_id(Long.parseLong(mSYS_User.getUserId()));
//        mOrgUser.setTree_name(mSYS_User.getTree_name());
//        mOrgUser.setGroup_corp_id(mSYS_User.getGroup_corp_id());
//        mOrgUser.setOrg_name(mSYS_User.getOrg_name());
//        mOrgUser.setUser_name(mSYS_User.getFullName());
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
//        mOrgUser.setBirthday(mSYS_User.getBirthday());
//        mOrgUser.setTitle(mSYS_User.getTitle());
//        mOrgUser.setHome_phone(mSYS_User.getHome_phone());
//        mOrgUser.setOffice_address(mSYS_User.getOffice_address());
//        mOrgUser.setPostal_code(mSYS_User.getPostal_code());
//        mOrgUser.setPic_path(mSYS_User.getPhotosurl());
//        mOrgUser.setHead_type(mSYS_User.getHeadType());
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
//        mOrgUser.setFax(mSYS_User.getFax());
//        return mOrgUser;
//    }

    public void initDate(){
        isHome = getIntent().getBooleanExtra("isHome",false);
        if(isHome){
            btn_daiban_person.setBackgroundResource(R.drawable.mx_btn_community_list_phone);
        }
        SYS_UserDAO mSYS_UserDAO = new SYS_UserDAO(PeopleMessageActivity.this);
        SYS_User mSYS_User = null ;
        OrgUser mOrgUser =  BookInit.getInstance().getOrgUser();

        try {
            if(mOrgUser == null){
                mSYS_User =  mSYS_UserDAO.findLoginNameSYS_User(BookInit.getInstance().getCrrentUserId());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(mSYS_User == null && mOrgUser == null){
            this.finish();
            return;
        }
        if(mSYS_User != null){
            mOrgUser = ConfigStyleUtil.copyOrgUser(mSYS_User);
        }

        Field[] field = mOrgUser.getClass().getDeclaredFields();
        modelName = new ArrayList<PeopleMessage>();
        // 获取属性的名字
        modelName = new ArrayList();
        for (int i = 0; i < field.length; i++) {
            String name = field[i].getName();
            field[i].setAccessible(true);
            String value = "";
            TD_UserDAO mTD_UserDAO = new TD_UserDAO(this);
            TD_User mTD_User =  mTD_UserDAO.getTD_User(name,true);

            if(mTD_User == null || mTD_User.getIsActive() == 0 || mTD_User.getIsActive() == 1 ){
                continue;
            }
            try {
                Field f = mOrgUser.getClass().getDeclaredField(name);
                f.setAccessible(true);
                value = f.get(mOrgUser) + "";
            } catch (Exception e) {
                e.printStackTrace();
            }
            PeopleMessage mPeopleMessage = new PeopleMessage();
            mPeopleMessage.setName(name);
            if(name.equals("gender")){
                if(value.equals("1")){
                    mPeopleMessage.setValue("男");
                }else if(value.equals("2")){
                    mPeopleMessage.setValue("女");
                }else{
                    mPeopleMessage.setValue("未知");
                }
            }else if(name.equals("birthday")){
                if(value != null && !value.equals("")){
                    mPeopleMessage.setValue(value.split(" ")[0]);
                }
            }else{
                mPeopleMessage.setValue(value);
            }

            mPeopleMessage.setDisOrder(mTD_User.getDisOrder());
            mPeopleMessage.setmTD_User(mTD_User);
            modelName.add(mPeopleMessage);
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
        mFragmentPeopleMessageAdapter = new FragmentPeopleMessageAdapter(this,modelName,mSYS_User,mOrgUser);
        lv_people_message.setAdapter(mFragmentPeopleMessageAdapter);
        btn_daiban_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BookInit.getInstance().setRequestUser(null);
                if(isHome){
                    BookInit.getInstance().getmCallbackMX().callUserMeesageMain();
                }else{
                    PeopleMessageActivity.this.finish();
                }

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 102:
                if (resultCode == Activity.RESULT_OK) {
                    String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/filename.jpg";
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("imagePath", imageFilePath);
                    ActivityUnit.switchTo(this,TailoringActivity.class,map);
//                    Intent intent = new Intent(this,TailoringActivity.class);
//                    intent.putExtra("imagePath", imageFilePath);
//                    startActivity(intent);
                }
                break;

        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        OrgUser mOrgUser =  BookInit.getInstance().getOrgUser();
        if(mOrgUser != null) {
            Field[] field = mOrgUser.getClass().getDeclaredFields();
            modelName = new ArrayList<PeopleMessage>();
            // 获取属性的名字
            modelName = new ArrayList();
            for (int i = 0; i < field.length; i++) {
                String name = field[i].getName();
                field[i].setAccessible(true);
                String value = "";
                TD_UserDAO mTD_UserDAO = new TD_UserDAO(this);
                TD_User mTD_User = mTD_UserDAO.getTD_User(name,true);
                if (mTD_User == null || mTD_User.getIsActive() == 0 || mTD_User.getIsActive() == 1 ) {
                    continue;
                }
                try {
                    Field f = mOrgUser.getClass().getDeclaredField(name);
                    f.setAccessible(true);
                    value = f.get(mOrgUser) + "";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PeopleMessage mPeopleMessage = new PeopleMessage();
                mPeopleMessage.setName(name);
                if(name.equals("gender")){
                    if(value.equals("1")){
                        mPeopleMessage.setValue("男");
                    }else if(value.equals("2")){
                        mPeopleMessage.setValue("女");
                    }else{
                        mPeopleMessage.setValue("未知");
                    }
                }else if(name.equals("birthday")){
                    if(value != null && !value.equals("")){
                        mPeopleMessage.setValue(value.split(" ")[0]);
                    }
                }else{
                    mPeopleMessage.setValue(value);
                }
                mPeopleMessage.setDisOrder(mTD_User.getDisOrder());
                mPeopleMessage.setmTD_User(mTD_User);
                modelName.add(mPeopleMessage);
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
            mFragmentPeopleMessageAdapter.setData(modelName, mOrgUser);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
