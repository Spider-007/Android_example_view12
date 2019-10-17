package com.htmitech.dao;

import android.content.ContentValues;
import android.content.Context;

import com.htmitech.app.Constant;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_OrgUser;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.TD_User;
import com.htmitech.myApplication.BaseApplication;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.db.ExtensionField;
import htmitech.com.componentlibrary.db.HtmitechDatabaseHelper;

/**
 * 用户DAO
 *
 * @author htrf-pc
 */
public class SYS_UserDAO {
    private static final String TABLE_NAME = "V_User";

    private static final String TABLE_NAME_ = "org_user";

    private static final String USERID = "user_id"; //用户ID

    private static final String GROUPCORPID = "group_corp_id"; //集团单位ID

    private static final String USERCODE = "user_code"; //用户编码
    private static final String FULLNAME = "user_name"; //用户姓名
    private static final String USERTYPE = "user_type"; //用户类型,0-虚拟用户，1-非平台用户，2-EMM平台用户，3-EMP移动端用户，4-EMI消息用户
    private static final String ADMINTYPE = "admin_type"; //是否管理员,0-非管理员，1-业务管理员，2-单位管理员，3-超级管理员

    private static final String EMITYPE = "emi_type"; //EMI类型,0-未开通，1-开通
    private static final String LOGINTYPE = "login_type"; //用户登录类型，0-无登录账号，1-平台账号密码登录，
    private static final String LOGINNAME = "login_name"; //登录账号
    private static final String PASSWORD = "login_password"; //登录密码
    private static final String USER_NICKNAME = "user_nickname"; //用户昵称
    private static final String USER_PYNAME = "user_pyname"; //用户拼音,同时包含全拼与简拼，用逗号分开。
    private static final String CORPID = "corp_id"; //默认单位ID
    private static final String ORGID = "org_id"; //默认部门Id
    private static final String DISPLAY_ORDER = "display_order"; //显示顺序，用于根据所属部门排序。
    private static final String HEADTYPE = "head_type"; //头像类型,0-自己上传头像图片，1-默认图标，2-显示人名，3-显示姓，4-显示昵称。
    private static final String HEADPICTUREID = "head_picture_id"; //头像图片ID，0-表示没有，不为0则对应图片表的图片ID
    private static final String MOBILE = "mobile_phone"; //手机号码
    private static final String OFFICE = "office_phone"; //办公电话
    private static final String STATUS = "status_flag"; //用户状态，-1 删除 0-停用，1-正常
    /**
     * 新增
     */
    public static final String TREE_NAME = "tree_name";
    public static final String ORGNAME = "org_name";
    private static final String TITLE = "title";//职务
    private static final String GENDER = "gender";//性别,0-未知，1-男，2-女
    private static final String BIRTHDAY = "birthday";//生日
    private static final String EMAIL = "email";//生日
    private static final String HOMEPHONE = "home_phone";//家庭电话
    private static final String OFFICEADDRESS = "office_address";//办公地点
    private static final String POSTALCODE = "postal_code";//邮编
    private static final String REMARK = "remark";//备注
    private static final String FAX = "fax";//传真
    private static final String EFS1 = "efs1";
    private static final String EFS2 = "efs2";
    private static final String EFS3 = "efs3";
    private static final String EFS4 = "efs4";
    private static final String EFS5 = "efs5";
    private static final String EFS6 = "efs6";
    private static final String EFS7 = "efs7";
    private static final String EFS8 = "efs8";
    private static final String EFS9 = "efs9";
    private static final String EFS10 = "efs10";
    private static final String EFI1 = "efi1";
    private static final String EFI2 = "efi2";
    private static final String EFI3 = "efi3";
    private static final String EFI4 = "efi4";
    private static final String EFI5 = "efi5";
    private static final String EFD1 = "efd1";
    private static final String EFD2 = "efd2";
    private static final String EFN1 = "efn1";
    private static final String EFN2 = "efn2";
    private static final String EFN3 = "efn3";
    private static final String ORG_USER_ORG_TITLE = "org_user_org_org_title";
    private static final String ORG_USER_ORG_USER_TITLENAME = "org_user_org_user_titlename";
    private static final String ORG_USER_ORG_FAX = "org_user_org_fax";
    private static final String ORG_USER_ORG_OFFICE_PHONE = "org_user_org_office_phone";


    private static final String PHOTOSURL = "pic_path";


    private SQLiteDatabase db;

    private Context context;
    private HtmitechDatabaseHelper mHtmitechDatabaseHelper;
    private SimpleDateFormat format = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");
    ;
    private BaseApplication myApp;

    public SYS_UserDAO(Context context, BaseApplication myApp) {
//		db = DBManager.getInstance(context);
        db = DBCipherManager.getInstance(context).db;
        this.context = context;
        this.myApp = myApp;
    }

    public SYS_UserDAO(Context context) {
//		db = DBManager.getInstance(context);
        db = DBCipherManager.getInstance(context).db;
        this.context = context;
    }


    public String getUserId(String FullName) {
        if (db != null && db.isOpen()) {
            Cursor cursor = null;
            try {
                String sql = "select * from " + TABLE_NAME + " where " + FULLNAME + " like '%" + FullName + "%'";
                cursor = db.rawQuery(sql, null);
                if (cursor.moveToNext()) {
                    return cursor.getString(cursor.getColumnIndex(USERID));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
        return "";
    }


    // 获取用户详细信息
    public SYS_User findUserIdSYS_Users(String userIds) throws ParseException {
        if (db != null && db.isOpen()) {
            Cursor cursor = null;
            try {

                String sql = "select * from V_contact where user_id=" + userIds + " and " + STATUS + "=1";
                cursor = db.rawQuery(sql, null);
//				cursor = db.query(TABLE_NAME, null, USERCODE + "=? and " + STATUS + "=?",
//						new String[]{userIds, "1"}, null, null, null);
                if (cursor.moveToNext()) {
                    SYS_User mSYS_User = new SYS_User();
                    String userId = cursor.getString(cursor.getColumnIndex(USERID));
                    mSYS_User.setUserId(userId);
                    String Password = cursor.getString(cursor
                            .getColumnIndex(PASSWORD));
                    mSYS_User.setPassword(Password);
                    String user_pin = cursor.getString(cursor
                            .getColumnIndex(USER_PYNAME));
                    String FullName = cursor.getString(cursor
                            .getColumnIndex(FULLNAME));
                    mSYS_User.setFullName(FullName);
                    short isEMPUser = cursor.getShort(cursor
                            .getColumnIndex(USERTYPE));
                    mSYS_User.setIsEMPUser(isEMPUser);
                    int display_order = cursor.getInt(cursor.getColumnIndex(DISPLAY_ORDER));
                    mSYS_User.setDisplay_order(display_order);
                    short emi_type = cursor.getShort(cursor.getColumnIndex(EMITYPE));
                    mSYS_User.setEmiType(emi_type);
                    short headPictureId = cursor.getShort(cursor.getColumnIndex(HEADPICTUREID));
                    String fax = cursor.getString(cursor.getColumnIndex(FAX));
                    mSYS_User.setFax(fax);
                    mSYS_User.setHead_picture_id(headPictureId);
                    String officeAddress = cursor.getString(cursor.getColumnIndex(OFFICEADDRESS));
                    mSYS_User.setOffice_address(officeAddress);
                    short head_type = cursor.getShort(cursor.getColumnIndex(HEADTYPE));
                    mSYS_User.setHeadType(head_type);
                    String user_nicName = cursor.getString(cursor.getColumnIndex(USER_NICKNAME));
                    mSYS_User.setUserNickname(user_nicName);
                    short adminType = cursor.getShort(cursor.getColumnIndex(ADMINTYPE));
                    String groupCorpId = cursor.getString(cursor.getColumnIndex(GROUPCORPID));
                    mSYS_User.setGroup_corp_id(groupCorpId);
                    mSYS_User.setAdminType(adminType);
                    try {
                        mSYS_User.setHeader(user_pin.split(",")[1]);
                        mSYS_User.setSuoxie(user_pin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String loginName = cursor.getString(cursor.getColumnIndex(LOGINNAME));
                    mSYS_User.setLogin_name(loginName);
                    String title = cursor.getString(cursor.getColumnIndex(TITLE));
                    mSYS_User.setTitle(title);
                    int gender = cursor.getInt(cursor.getColumnIndex(GENDER));
                    mSYS_User.setGender(gender);
                    String birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY));
                    mSYS_User.setBirthday(birthday);
                    String email = cursor.getString(cursor.getColumnIndex(EMAIL));
                    mSYS_User.setEmail(email);
                    String home_phone = cursor.getString(cursor.getColumnIndex(HOMEPHONE));
                    mSYS_User.setHome_phone(home_phone);
                    String postalCode = cursor.getString(cursor.getColumnIndex(POSTALCODE));
                    mSYS_User.setPostal_code(postalCode);
                    String remark = cursor.getString(cursor.getColumnIndex(REMARK));
                    mSYS_User.setRemark(remark);


                    short Status = cursor.getShort(cursor.getColumnIndex(STATUS));
                    mSYS_User.setStatus(Status);
                    String Office = cursor.getString(cursor.getColumnIndex(OFFICE));
                    mSYS_User.setOffice(Office);
                    String Mobile = cursor.getString(cursor.getColumnIndex(MOBILE));
                    mSYS_User.setMobile(Mobile);
                    String Photosurl = cursor.getString(cursor
                            .getColumnIndex(PHOTOSURL));
                    mSYS_User.setPhotosurl(Photosurl);

                    short isEMIUser = cursor.getShort(cursor
                            .getColumnIndex(USERTYPE));
                    mSYS_User.setIsEMIUser(isEMIUser);

//					short isEMPUser = cursor.getShort(cursor
//							.getColumnIndex(ISEMPUSER));
//					mSYS_User.setIsEMPUser(isEMPUser);

                    String efs1 = cursor.getString(cursor.getColumnIndex(EFS1));
                    String efs2 = cursor.getString(cursor.getColumnIndex(EFS2));
                    String efs3 = cursor.getString(cursor.getColumnIndex(EFS3));
                    String efs4 = cursor.getString(cursor.getColumnIndex(EFS4));
                    String efs5 = cursor.getString(cursor.getColumnIndex(EFS5));
                    String efs6 = cursor.getString(cursor.getColumnIndex(EFS6));
                    String efs7 = cursor.getString(cursor.getColumnIndex(EFS7));
                    String efs8 = cursor.getString(cursor.getColumnIndex(EFS8));
                    String efs9 = cursor.getString(cursor.getColumnIndex(EFS9));
                    String efs10 = cursor.getString(cursor.getColumnIndex(EFS10));
                    String efi1 = cursor.getString(cursor.getColumnIndex(EFI1));
                    String efi2 = cursor.getString(cursor.getColumnIndex(EFI2));
                    String efi3 = cursor.getString(cursor.getColumnIndex(EFI3));
                    String efi4 = cursor.getString(cursor.getColumnIndex(EFI4));
                    String efi5 = cursor.getString(cursor.getColumnIndex(EFI5));
                    String efd1 = cursor.getString(cursor.getColumnIndex(EFD1));
                    String efd2 = cursor.getString(cursor.getColumnIndex(EFD2));
                    String efn1 = cursor.getString(cursor.getColumnIndex(EFN1));
                    String efn2 = cursor.getString(cursor.getColumnIndex(EFN2));
                    String efn3 = cursor.getString(cursor.getColumnIndex(EFN3));
                    mSYS_User.setEfs1(efs1);
                    mSYS_User.setEfs2(efs2);
                    mSYS_User.setEfs3(efs3);
                    mSYS_User.setEfs4(efs4);
                    mSYS_User.setEfs5(efs5);
                    mSYS_User.setEfs6(efs6);
                    mSYS_User.setEfs7(efs7);
                    mSYS_User.setEfs8(efs8);
                    mSYS_User.setEfs9(efs9);
                    mSYS_User.setEfs10(efs10);
                    mSYS_User.setEfi1(efi1);
                    mSYS_User.setEfi2(efi2);
                    mSYS_User.setEfi3(efi3);
                    mSYS_User.setEfi4(efi4);
                    mSYS_User.setEfi5(efi5);
                    mSYS_User.setEfd1(efd1);
                    mSYS_User.setEfd2(efd2);
                    mSYS_User.setEfn1(efn1);
                    mSYS_User.setEfn2(efn2);
                    mSYS_User.setEfn3(efn3);
                    TD_UserDAO mTD_UserDAO = new TD_UserDAO(context);
                    TD_User mTD_User = mTD_UserDAO.getTD_User(FullName, false);
                    mSYS_User.setmTD_User(mTD_User);
                    cursor.close();
                    return mSYS_User;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }


        }
        return null;
    }


    // 获取用户详细信息
    public SYS_User findUserIdSYS_User(String userIds) throws ParseException {
        if (db != null && db.isOpen()) {
            Cursor cursor = null;
            try {
                String sql = "select * from V_contact con JOIN v_org_user_org org on (con.user_id = org.user_id) where con.user_id=" + userIds + " AND user_type > 0";
                cursor = db.rawQuery(sql, null);
//				Cursor cursor = db.query(TABLE_NAME, null, USERID + "=?",
//						new String[]{userIds}, null, null, null);
                if (cursor.moveToNext()) {
                    SYS_User mSYS_User = new SYS_User();
                    String userId = cursor.getString(cursor.getColumnIndex(USERID));
                    mSYS_User.setUserId(userId);
                    String Password = cursor.getString(cursor
                            .getColumnIndex(PASSWORD));
                    mSYS_User.setPassword(Password);
                    String FullName = cursor.getString(cursor
                            .getColumnIndex(FULLNAME));
                    mSYS_User.setFullName(FullName);
                    int display_order = cursor.getInt(cursor.getColumnIndex(DISPLAY_ORDER));
                    mSYS_User.setDisplay_order(display_order);
                    short head_type = cursor.getShort(cursor.getColumnIndex(HEADTYPE));
                    mSYS_User.setHeadType(head_type);
                    short headPictureId = cursor.getShort(cursor.getColumnIndex(HEADPICTUREID));
                    short emi_type = cursor.getShort(cursor.getColumnIndex(EMITYPE));
                    mSYS_User.setEmiType(emi_type);
                    mSYS_User.setHead_picture_id(headPictureId);
                    short Status = cursor.getShort(cursor.getColumnIndex(STATUS));
                    mSYS_User.setStatus(Status);
                    String Office = cursor.getString(cursor.getColumnIndex(OFFICE));
                    mSYS_User.setOffice(Office);
                    short isEMPUser = cursor.getShort(cursor
                            .getColumnIndex(USERTYPE));
                    String fax = cursor.getString(cursor.getColumnIndex(FAX));
                    mSYS_User.setFax(fax);
                    String user_nicName = cursor.getString(cursor.getColumnIndex(USER_NICKNAME));
                    mSYS_User.setUserNickname(user_nicName);
                    mSYS_User.setIsEMPUser(isEMPUser);
                    String loginName = cursor.getString(cursor.getColumnIndex(LOGINNAME));
                    mSYS_User.setLogin_name(loginName);
                    String title = cursor.getString(cursor.getColumnIndex(TITLE));
                    mSYS_User.setTitle(title);
                    int gender = cursor.getInt(cursor.getColumnIndex(GENDER));
                    mSYS_User.setGender(gender);
                    String birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY));
                    mSYS_User.setBirthday(birthday);
                    String email = cursor.getString(cursor.getColumnIndex(EMAIL));
                    mSYS_User.setEmail(email);
                    String home_phone = cursor.getString(cursor.getColumnIndex(HOMEPHONE));
                    mSYS_User.setHome_phone(home_phone);
                    String postalCode = cursor.getString(cursor.getColumnIndex(POSTALCODE));
                    mSYS_User.setPostal_code(postalCode);
                    String remark = cursor.getString(cursor.getColumnIndex(REMARK));
                    mSYS_User.setRemark(remark);
                    String Mobile = cursor.getString(cursor.getColumnIndex(MOBILE));
                    mSYS_User.setMobile(Mobile);
                    String org_name = cursor.getString(cursor.getColumnIndex(ORGNAME));
                    mSYS_User.setOrg_name(org_name);

                    String org_user_org_title = cursor.getString(cursor.getColumnIndex(ORG_USER_ORG_TITLE));
                    mSYS_User.setOrg_user_org_org_title(org_user_org_title);

                    String org_user_org_titlename = cursor.getString(cursor.getColumnIndex(ORG_USER_ORG_USER_TITLENAME));
                    mSYS_User.setOrg_user_org_user_titlename(org_user_org_titlename);

                    String org_user_org_fax = cursor.getString(cursor.getColumnIndex(ORG_USER_ORG_FAX));
                    mSYS_User.setOrg_user_org_fax(org_user_org_fax);

                    String org_user_org_office_phone = cursor.getString(cursor.getColumnIndex(ORG_USER_ORG_OFFICE_PHONE));
                    mSYS_User.setOrg_user_org_office_phone(org_user_org_office_phone);

                    String treeName = cursor.getString(cursor.getColumnIndex(TREE_NAME));
                    mSYS_User.setTree_name(treeName);

                    String Photosurl = cursor.getString(cursor
                            .getColumnIndex(PHOTOSURL));
                    mSYS_User.setPhotosurl(Photosurl);
                    short adminType = cursor.getShort(cursor.getColumnIndex(ADMINTYPE));
                    mSYS_User.setAdminType(adminType);
                    short isEMIUser = cursor.getShort(cursor
                            .getColumnIndex(USERTYPE));
                    mSYS_User.setIsEMIUser(isEMIUser);
                    String user_pin = cursor.getString(cursor
                            .getColumnIndex(USER_PYNAME));
                    String groupCorpId = cursor.getString(cursor.getColumnIndex(GROUPCORPID));
                    mSYS_User.setGroup_corp_id(groupCorpId);
                    try {
                        mSYS_User.setHeader(user_pin.split(",")[1]);
                        mSYS_User.setSuoxie(user_pin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String officeAddress = cursor.getString(cursor.getColumnIndex(OFFICEADDRESS));
                    mSYS_User.setOffice_address(officeAddress);
//				short isEMPUser = cursor.getShort(cursor
//						.getColumnIndex(ISEMPUSER));
//				mSYS_User.setIsEMPUser(isEMPUser);
                    TD_UserDAO mTD_UserDAO = new TD_UserDAO(context);
                    TD_User mTD_User = mTD_UserDAO.getTD_User(FullName, false);
                    mSYS_User.setmTD_User(mTD_User);
                    String efs1 = cursor.getString(cursor.getColumnIndex(EFS1));
                    String efs2 = cursor.getString(cursor.getColumnIndex(EFS2));
                    String efs3 = cursor.getString(cursor.getColumnIndex(EFS3));
                    String efs4 = cursor.getString(cursor.getColumnIndex(EFS4));
                    String efs5 = cursor.getString(cursor.getColumnIndex(EFS5));
                    String efs6 = cursor.getString(cursor.getColumnIndex(EFS6));
                    String efs7 = cursor.getString(cursor.getColumnIndex(EFS7));
                    String efs8 = cursor.getString(cursor.getColumnIndex(EFS8));
                    String efs9 = cursor.getString(cursor.getColumnIndex(EFS9));
                    String efs10 = cursor.getString(cursor.getColumnIndex(EFS10));
                    String efi1 = cursor.getString(cursor.getColumnIndex(EFI1));
                    String efi2 = cursor.getString(cursor.getColumnIndex(EFI2));
                    String efi3 = cursor.getString(cursor.getColumnIndex(EFI3));
                    String efi4 = cursor.getString(cursor.getColumnIndex(EFI4));
                    String efi5 = cursor.getString(cursor.getColumnIndex(EFI5));
                    String efd1 = cursor.getString(cursor.getColumnIndex(EFD1));
                    String efd2 = cursor.getString(cursor.getColumnIndex(EFD2));
                    String efn1 = cursor.getString(cursor.getColumnIndex(EFN1));
                    String efn2 = cursor.getString(cursor.getColumnIndex(EFN2));
                    String efn3 = cursor.getString(cursor.getColumnIndex(EFN3));
                    mSYS_User.setEfs1(efs1);
                    mSYS_User.setEfs2(efs2);
                    mSYS_User.setEfs3(efs3);
                    mSYS_User.setEfs4(efs4);
                    mSYS_User.setEfs5(efs5);
                    mSYS_User.setEfs6(efs6);
                    mSYS_User.setEfs7(efs7);
                    mSYS_User.setEfs8(efs8);
                    mSYS_User.setEfs9(efs9);
                    mSYS_User.setEfs10(efs10);
                    mSYS_User.setEfi1(efi1);
                    mSYS_User.setEfi2(efi2);
                    mSYS_User.setEfi3(efi3);
                    mSYS_User.setEfi4(efi4);
                    mSYS_User.setEfi5(efi5);
                    mSYS_User.setEfd1(efd1);
                    mSYS_User.setEfd2(efd2);
                    mSYS_User.setEfn1(efn1);
                    mSYS_User.setEfn2(efn2);
                    mSYS_User.setEfn3(efn3);
                    SYS_OrgUserDAO mSYS_OrgUserDAO = new SYS_OrgUserDAO(db);
                    SYS_OrgUser mSYS_OrgUser = mSYS_OrgUserDAO
                            .getSYSOrgUser(mSYS_User);
                    SYS_DepartmentDAO mSYS_DepartmentDAO = new SYS_DepartmentDAO(context);
                    SYS_Department mSYS_Department = new SYS_Department();
                    if (mSYS_OrgUser != null)
                        mSYS_DepartmentDAO.selectDepartment(mSYS_OrgUser.getDepartmentCode(), mSYS_Department);
                    mSYS_User.setmSYS_Department(mSYS_Department);
                    cursor.close();
                    return mSYS_User;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return null;
    }

    // 获取用户详细信息
    public SYS_User findLoginNameSYS_User(String loginName) throws ParseException {
        if (db != null && db.isOpen()) {
            Cursor cursor = null;
            try {

                String sql = "select * from V_User con JOIN v_org_user_org org on (con.user_id = org.user_id) where con.user_id=" + loginName;
                cursor = db.rawQuery(sql, null);
//				cursor = db.query(TABLE_NAME, null, USERID + "=?",
//						new String[]{loginName}, null, null, null);
                if (cursor.moveToNext()) {
                    SYS_User mSYS_User = new SYS_User();
                    String userId = cursor.getString(cursor.getColumnIndex(USERID));
                    mSYS_User.setUserId(userId);
                    String Password = cursor.getString(cursor
                            .getColumnIndex(PASSWORD));
                    mSYS_User.setPassword(Password);
                    String FullName = cursor.getString(cursor
                            .getColumnIndex(FULLNAME));
                    mSYS_User.setFullName(FullName);
                    int display_order = cursor.getInt(cursor.getColumnIndex(DISPLAY_ORDER));
                    mSYS_User.setDisplay_order(display_order);
                    short head_type = cursor.getShort(cursor.getColumnIndex(HEADTYPE));
                    mSYS_User.setHeadType(head_type);
                    short headPictureId = cursor.getShort(cursor.getColumnIndex(HEADPICTUREID));
                    short emi_type = cursor.getShort(cursor.getColumnIndex(EMITYPE));
                    mSYS_User.setEmiType(emi_type);
                    mSYS_User.setHead_picture_id(headPictureId);
                    short Status = cursor.getShort(cursor.getColumnIndex(STATUS));
                    mSYS_User.setStatus(Status);
                    String Office = cursor.getString(cursor.getColumnIndex(OFFICE));
                    mSYS_User.setOffice(Office);
                    short isEMPUser = cursor.getShort(cursor
                            .getColumnIndex(USERTYPE));
                    String fax = cursor.getString(cursor.getColumnIndex(FAX));
                    mSYS_User.setFax(fax);
                    String user_nicName = cursor.getString(cursor.getColumnIndex(USER_NICKNAME));
                    mSYS_User.setUserNickname(user_nicName);
                    mSYS_User.setIsEMPUser(isEMPUser);
                    String title = cursor.getString(cursor.getColumnIndex(TITLE));
                    mSYS_User.setTitle(title);
                    int gender = cursor.getInt(cursor.getColumnIndex(GENDER));
                    mSYS_User.setGender(gender);
                    String birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY));
                    mSYS_User.setBirthday(birthday);
                    String email = cursor.getString(cursor.getColumnIndex(EMAIL));
                    mSYS_User.setEmail(email);
                    String loginNames = cursor.getString(cursor.getColumnIndex(LOGINNAME));
                    mSYS_User.setLogin_name(loginNames);
                    String home_phone = cursor.getString(cursor.getColumnIndex(HOMEPHONE));
                    mSYS_User.setHome_phone(home_phone);
                    String postalCode = cursor.getString(cursor.getColumnIndex(POSTALCODE));
                    mSYS_User.setPostal_code(postalCode);
                    String remark = cursor.getString(cursor.getColumnIndex(REMARK));
                    mSYS_User.setRemark(remark);
                    String Mobile = cursor.getString(cursor.getColumnIndex(MOBILE));
                    mSYS_User.setMobile(Mobile);
                    String orgName = cursor.getString(cursor.getColumnIndex(ORGNAME));
                    mSYS_User.setOrg_name(orgName);

                    String treeName = cursor.getString(cursor.getColumnIndex(TREE_NAME));
                    mSYS_User.setTree_name(treeName);

                    String Photosurl = cursor.getString(cursor
                            .getColumnIndex(PHOTOSURL));
                    mSYS_User.setPhotosurl(Photosurl);
                    short adminType = cursor.getShort(cursor.getColumnIndex(ADMINTYPE));
                    mSYS_User.setAdminType(adminType);
                    short isEMIUser = cursor.getShort(cursor
                            .getColumnIndex(USERTYPE));
                    mSYS_User.setIsEMIUser(isEMIUser);
                    String officeAddress = cursor.getString(cursor.getColumnIndex(OFFICEADDRESS));
                    mSYS_User.setOffice_address(officeAddress);
//				short isEMPUser = cursor.getShort(cursor
//						.getColumnIndex(ISEMPUSER));
//				mSYS_User.setIsEMPUser(isEMPUser);
                    String user_pin = cursor.getString(cursor
                            .getColumnIndex(USER_PYNAME));
                    String groupCorpId = cursor.getString(cursor.getColumnIndex(GROUPCORPID));
                    mSYS_User.setGroup_corp_id(groupCorpId);
                    try {
                        mSYS_User.setHeader(user_pin.split(",")[1]);
                        mSYS_User.setSuoxie(user_pin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    TD_UserDAO mTD_UserDAO = new TD_UserDAO(context);
                    TD_User mTD_User = mTD_UserDAO.getTD_User(FullName, false);
                    mSYS_User.setmTD_User(mTD_User);
                    String efs1 = cursor.getString(cursor.getColumnIndex(EFS1));
                    String efs2 = cursor.getString(cursor.getColumnIndex(EFS2));
                    String efs3 = cursor.getString(cursor.getColumnIndex(EFS3));
                    String efs4 = cursor.getString(cursor.getColumnIndex(EFS4));
                    String efs5 = cursor.getString(cursor.getColumnIndex(EFS5));
                    String efs6 = cursor.getString(cursor.getColumnIndex(EFS6));
                    String efs7 = cursor.getString(cursor.getColumnIndex(EFS7));
                    String efs8 = cursor.getString(cursor.getColumnIndex(EFS8));
                    String efs9 = cursor.getString(cursor.getColumnIndex(EFS9));
                    String efs10 = cursor.getString(cursor.getColumnIndex(EFS10));
                    String efi1 = cursor.getString(cursor.getColumnIndex(EFI1));
                    String efi2 = cursor.getString(cursor.getColumnIndex(EFI2));
                    String efi3 = cursor.getString(cursor.getColumnIndex(EFI3));
                    String efi4 = cursor.getString(cursor.getColumnIndex(EFI4));
                    String efi5 = cursor.getString(cursor.getColumnIndex(EFI5));
                    String efd1 = cursor.getString(cursor.getColumnIndex(EFD1));
                    String efd2 = cursor.getString(cursor.getColumnIndex(EFD2));
                    String efn1 = cursor.getString(cursor.getColumnIndex(EFN1));
                    String efn2 = cursor.getString(cursor.getColumnIndex(EFN2));
                    String efn3 = cursor.getString(cursor.getColumnIndex(EFN3));
                    mSYS_User.setEfs1(efs1);
                    mSYS_User.setEfs2(efs2);
                    mSYS_User.setEfs3(efs3);
                    mSYS_User.setEfs4(efs4);
                    mSYS_User.setEfs5(efs5);
                    mSYS_User.setEfs6(efs6);
                    mSYS_User.setEfs7(efs7);
                    mSYS_User.setEfs8(efs8);
                    mSYS_User.setEfs9(efs9);
                    mSYS_User.setEfs10(efs10);
                    mSYS_User.setEfi1(efi1);
                    mSYS_User.setEfi2(efi2);
                    mSYS_User.setEfi3(efi3);
                    mSYS_User.setEfi4(efi4);
                    mSYS_User.setEfi5(efi5);
                    mSYS_User.setEfd1(efd1);
                    mSYS_User.setEfd2(efd2);
                    mSYS_User.setEfn1(efn1);
                    mSYS_User.setEfn2(efn2);
                    mSYS_User.setEfn3(efn3);
                    SYS_OrgUserDAO mSYS_OrgUserDAO = new SYS_OrgUserDAO(db);
                    SYS_OrgUser mSYS_OrgUser = mSYS_OrgUserDAO
                            .getSYSOrgUser(mSYS_User);
                    SYS_DepartmentDAO mSYS_DepartmentDAO = new SYS_DepartmentDAO(context);
                    SYS_Department mSYS_Department = new SYS_Department();
                    if (mSYS_OrgUser != null)
                        mSYS_DepartmentDAO.selectDepartment(mSYS_OrgUser.getDepartmentCode(), mSYS_Department);
                    mSYS_User.setmSYS_Department(mSYS_Department);
                    cursor.close();
                    return mSYS_User;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }
        return null;
    }

    public synchronized ArrayList<SYS_User> findIdByUser(String keyword) {
        ArrayList<SYS_User> userList = new ArrayList<SYS_User>();
        if (db != null && db.isOpen()) {
            Cursor cursor = null;
            try {
                String sql = "select * from " + TABLE_NAME + " where (" + FULLNAME + " like '%" + keyword + "%'" + " or " + MOBILE + " like '%" + keyword + "%'" + " or " + USER_PYNAME + " like '%" + keyword + "%')  and " + STATUS + " = 1";
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    SYS_User mSYS_User = new SYS_User();
                    String userId = cursor.getString(cursor.getColumnIndex(USERID));
                    mSYS_User.setUserId(userId);
                    String Password = cursor.getString(cursor
                            .getColumnIndex(PASSWORD));
                    mSYS_User.setPassword(Password);

                    String FullName = cursor.getString(cursor
                            .getColumnIndex(FULLNAME));
                    short head_type = cursor.getShort(cursor.getColumnIndex(HEADTYPE));
                    mSYS_User.setHeadType(head_type);
                    String user_nicName = cursor.getString(cursor.getColumnIndex(USER_NICKNAME));
                    mSYS_User.setUserNickname(user_nicName);
                    short headPictureId = cursor.getShort(cursor.getColumnIndex(HEADPICTUREID));
                    short emi_type = cursor.getShort(cursor.getColumnIndex(EMITYPE));
                    mSYS_User.setEmiType(emi_type);
                    int display_order = cursor.getInt(cursor.getColumnIndex(DISPLAY_ORDER));
                    mSYS_User.setDisplay_order(display_order);
                    mSYS_User.setHead_picture_id(headPictureId);
                    String user_pin = cursor.getString(cursor
                            .getColumnIndex(USER_PYNAME));
                    try {
                        mSYS_User.setHeader(user_pin.split(",")[1]);
                        mSYS_User.setSuoxie(user_pin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String fax = cursor.getString(cursor.getColumnIndex(FAX));
                    mSYS_User.setFax(fax);
                    String title = cursor.getString(cursor.getColumnIndex(TITLE));
                    mSYS_User.setTitle(title);
                    int gender = cursor.getInt(cursor.getColumnIndex(GENDER));
                    mSYS_User.setGender(gender);
                    String birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY));
                    mSYS_User.setBirthday(birthday);
                    String email = cursor.getString(cursor.getColumnIndex(EMAIL));
                    mSYS_User.setEmail(email);
                    String home_phone = cursor.getString(cursor.getColumnIndex(HOMEPHONE));
                    mSYS_User.setHome_phone(home_phone);
                    String postalCode = cursor.getString(cursor.getColumnIndex(POSTALCODE));
                    mSYS_User.setPostal_code(postalCode);
                    String remark = cursor.getString(cursor.getColumnIndex(REMARK));
                    mSYS_User.setRemark(remark);
                    short isEMPUser = cursor.getShort(cursor
                            .getColumnIndex(USERTYPE));
                    mSYS_User.setIsEMPUser(isEMPUser);
                    mSYS_User.setFullName(FullName);
                    short Status = cursor.getShort(cursor.getColumnIndex(STATUS));
                    mSYS_User.setStatus(Status);
                    String Office = cursor.getString(cursor.getColumnIndex(OFFICE));
                    mSYS_User.setOffice(Office);
                    String Mobile = cursor.getString(cursor.getColumnIndex(MOBILE));
                    mSYS_User.setMobile(Mobile);
                    String Photosurl = cursor.getString(cursor
                            .getColumnIndex(PHOTOSURL));
                    mSYS_User.setPhotosurl(Photosurl);
                    String officeAddress = cursor.getString(cursor.getColumnIndex(OFFICEADDRESS));
                    mSYS_User.setOffice_address(officeAddress);
                    short isEMIUser = cursor.getShort(cursor
                            .getColumnIndex(USERTYPE));
                    mSYS_User.setIsEMIUser(isEMIUser);
                    short adminType = cursor.getShort(cursor.getColumnIndex(ADMINTYPE));
                    mSYS_User.setAdminType(adminType);
                    String groupCorpId = cursor.getString(cursor.getColumnIndex(GROUPCORPID));
                    mSYS_User.setGroup_corp_id(groupCorpId);
//					short isEMPUser = cursor.getShort(cursor
//							.getColumnIndex(ISEMPUSER));
//					mSYS_User.setIsEMPUser(isEMPUser);
                    String efs1 = cursor.getString(cursor.getColumnIndex(EFS1));
                    String efs2 = cursor.getString(cursor.getColumnIndex(EFS2));
                    String efs3 = cursor.getString(cursor.getColumnIndex(EFS3));
                    String efs4 = cursor.getString(cursor.getColumnIndex(EFS4));
                    String efs5 = cursor.getString(cursor.getColumnIndex(EFS5));
                    String efs6 = cursor.getString(cursor.getColumnIndex(EFS6));
                    String efs7 = cursor.getString(cursor.getColumnIndex(EFS7));
                    String efs8 = cursor.getString(cursor.getColumnIndex(EFS8));
                    String efs9 = cursor.getString(cursor.getColumnIndex(EFS9));
                    String efs10 = cursor.getString(cursor.getColumnIndex(EFS10));
                    String efi1 = cursor.getString(cursor.getColumnIndex(EFI1));
                    String efi2 = cursor.getString(cursor.getColumnIndex(EFI2));
                    String efi3 = cursor.getString(cursor.getColumnIndex(EFI3));
                    String efi4 = cursor.getString(cursor.getColumnIndex(EFI4));
                    String efi5 = cursor.getString(cursor.getColumnIndex(EFI5));
                    String efd1 = cursor.getString(cursor.getColumnIndex(EFD1));
                    String efd2 = cursor.getString(cursor.getColumnIndex(EFD2));
                    String efn1 = cursor.getString(cursor.getColumnIndex(EFN1));
                    String efn2 = cursor.getString(cursor.getColumnIndex(EFN2));
                    String efn3 = cursor.getString(cursor.getColumnIndex(EFN3));
                    mSYS_User.setEfs1(efs1);
                    mSYS_User.setEfs2(efs2);
                    mSYS_User.setEfs3(efs3);
                    mSYS_User.setEfs4(efs4);
                    mSYS_User.setEfs5(efs5);
                    mSYS_User.setEfs6(efs6);
                    mSYS_User.setEfs7(efs7);
                    mSYS_User.setEfs8(efs8);
                    mSYS_User.setEfs9(efs9);
                    mSYS_User.setEfs10(efs10);
                    mSYS_User.setEfi1(efi1);
                    mSYS_User.setEfi2(efi2);
                    mSYS_User.setEfi3(efi3);
                    mSYS_User.setEfi4(efi4);
                    mSYS_User.setEfi5(efi5);
                    mSYS_User.setEfd1(efd1);
                    mSYS_User.setEfd2(efd2);
                    mSYS_User.setEfn1(efn1);
                    mSYS_User.setEfn2(efn2);
                    mSYS_User.setEfn3(efn3);
                    TD_UserDAO mTD_UserDAO = new TD_UserDAO(context);
                    TD_User mTD_User = mTD_UserDAO.getTD_User(FullName, false);
                    mSYS_User.setmTD_User(mTD_User);
                    userList.add(mSYS_User);
                }
                myApp.setUserList(userList);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }
        return userList;
    }


    // 获取人员列表
    public ArrayList<SYS_User> getSYSUer(String DepartmentCode, SYS_Department mSYS_Department) throws ParseException {
//		SQLiteDatabase db = DBManager.getInstance(context);
        ArrayList<SYS_User> userList = new ArrayList<SYS_User>();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {


//				String sql = "select * from org_user_org ouo JOIN "+TABLE_NAME+" u on u.user_id = ouo.user_id where ("+ORGID + "=? and "+STATUS+" = 1 order by " + DISPLAY_ORDER + " ASC"+")";
//			;
                String sql = "";
                if (Constant.com_addressbook_mobileconfig_otherorg_show.equals("0")) {
                    sql = "select * from v_org_user_org ouo JOIN V_User u on u.user_id = ouo.user_id where u.user_type > 0 " +
                            " and (ouo.ouo_org_order = 1 or ouo.ouo_org_order = 1.0) and ouo.org_id = '" + DepartmentCode + "'" +
                            " and u.status_flag = 1 and ouo.status_flag > 0 order by ouo.display_order ASC";
                } else if (Constant.com_addressbook_mobileconfig_otherorg_show.equals("1")) {
                    sql = "select * from v_org_user_org ouo JOIN V_User u on u.user_id = ouo.user_id where u.user_type > 0" +
                            " and ouo.org_id = '" + DepartmentCode + "' and u.status_flag = 1 and ouo.status_flag > 0 " +
                            "order by ouo.display_order ASC";
                }

                cursor = db.rawQuery(sql, null);
//				cursor = db.query(TABLE_NAME, null, ORGID + "=? and "+STATUS+" = 1 order by " + DISPLAY_ORDER + " ASC", new String[]{DepartmentCode}, null, null, null);

                while (cursor.moveToNext()) {
                    SYS_User mSYS_User = new SYS_User();
                    String userId = cursor.getString(cursor.getColumnIndex(USERID));
                    mSYS_User.setUserId(userId);
                    String Password = cursor.getString(cursor
                            .getColumnIndex(PASSWORD));
                    mSYS_User.setPassword(Password);
                    String user_pin = cursor.getString(cursor
                            .getColumnIndex(USER_PYNAME));
                    String FullName = cursor.getString(cursor
                            .getColumnIndex(FULLNAME));
                    short head_type = cursor.getShort(cursor.getColumnIndex(HEADTYPE));
                    mSYS_User.setHeadType(head_type);
                    short headPictureId = cursor.getShort(cursor.getColumnIndex(HEADPICTUREID));
                    String title = cursor.getString(cursor.getColumnIndex(TITLE));
                    mSYS_User.setTitle(title);
                    int gender = cursor.getInt(cursor.getColumnIndex(GENDER));
                    mSYS_User.setGender(gender);
                    String birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY));
                    mSYS_User.setBirthday(birthday);
                    String email = cursor.getString(cursor.getColumnIndex(EMAIL));
                    mSYS_User.setEmail(email);
                    String loginName = cursor.getString(cursor.getColumnIndex(LOGINNAME));
                    mSYS_User.setLogin_name(loginName);
                    String user_code = cursor.getString(cursor.getColumnIndex(USERCODE));
                    mSYS_User.setUser_code(user_code);
                    String home_phone = cursor.getString(cursor.getColumnIndex(HOMEPHONE));
                    mSYS_User.setHome_phone(home_phone);
                    String postalCode = cursor.getString(cursor.getColumnIndex(POSTALCODE));
                    mSYS_User.setPostal_code(postalCode);
                    String remark = cursor.getString(cursor.getColumnIndex(REMARK));
                    mSYS_User.setRemark(remark);
                    String org_name = cursor.getString(cursor.getColumnIndex(ORGNAME));
                    mSYS_User.setOrg_name(org_name);

                    String org_user_org_title = cursor.getString(cursor.getColumnIndex(ORG_USER_ORG_TITLE));
                    mSYS_User.setOrg_user_org_org_title(org_user_org_title);

                    String org_user_org_titlename = cursor.getString(cursor.getColumnIndex(ORG_USER_ORG_USER_TITLENAME));
                    mSYS_User.setOrg_user_org_user_titlename(org_user_org_titlename);

                    String org_user_org_fax = cursor.getString(cursor.getColumnIndex(ORG_USER_ORG_FAX));
                    mSYS_User.setOrg_user_org_fax(org_user_org_fax);

                    String org_user_org_office_phone = cursor.getString(cursor.getColumnIndex(ORG_USER_ORG_OFFICE_PHONE));
                    mSYS_User.setOrg_user_org_office_phone(org_user_org_office_phone);

                    String treeName = cursor.getString(cursor.getColumnIndex(TREE_NAME));
                    mSYS_User.setTree_name(treeName);
                    mSYS_User.setHead_picture_id(headPictureId);
                    short emi_type = cursor.getShort(cursor.getColumnIndex(EMITYPE));
                    mSYS_User.setEmiType(emi_type);
                    String user_nicName = cursor.getString(cursor.getColumnIndex(USER_NICKNAME));
                    mSYS_User.setUserNickname(user_nicName);
                    int orgId = cursor.getInt(cursor.getColumnIndex(ORGID));
                    mSYS_User.setOrgId(orgId);
                    int display_order = cursor.getInt(cursor.getColumnIndex(DISPLAY_ORDER));
                    mSYS_User.setDisplay_order(display_order);
                    try {
                        mSYS_User.setHeader(user_pin.split(",")[1]);
                        mSYS_User.setSuoxie(user_pin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String fax = cursor.getString(cursor.getColumnIndex(FAX));
                    mSYS_User.setFax(fax);
                    short isEMPUser = cursor.getShort(cursor
                            .getColumnIndex(USERTYPE));
                    mSYS_User.setIsEMPUser(isEMPUser);
                    mSYS_User.setFullName(FullName);
                    short Status = cursor.getShort(cursor.getColumnIndex(STATUS));
                    mSYS_User.setStatus(Status);
                    String Office = cursor.getString(cursor.getColumnIndex(OFFICE));
                    mSYS_User.setOffice(Office);
                    String Mobile = cursor.getString(cursor.getColumnIndex(MOBILE));
                    mSYS_User.setMobile(Mobile);
                    String Photosurl = cursor.getString(cursor
                            .getColumnIndex(PHOTOSURL));
                    mSYS_User.setPhotosurl(Photosurl);
                    short isEMIUser = cursor.getShort(cursor
                            .getColumnIndex(USERTYPE));
                    mSYS_User.setIsEMIUser(isEMIUser);
                    short adminType = cursor.getShort(cursor.getColumnIndex(ADMINTYPE));
                    mSYS_User.setAdminType(adminType);
                    String officeAddress = cursor.getString(cursor.getColumnIndex(OFFICEADDRESS));
                    mSYS_User.setOffice_address(officeAddress);
                    String groupCorpId = cursor.getString(cursor.getColumnIndex(GROUPCORPID));
                    mSYS_User.setGroup_corp_id(groupCorpId);
//					short isEMPUser = cursor.getShort(cursor
//							.getColumnIndex(ISEMPUSER));
//					mSYS_User.setIsEMPUser(isEMPUser);
//					SYS_OrgUserDAO mSYS_OrgUserDAO = new SYS_OrgUserDAO(db);
//					SYS_OrgUser mSYS_OrgUser = mSYS_OrgUserDAO
//							.getSYSOrgUser(mSYS_User);
                    String efs1 = cursor.getString(cursor.getColumnIndex(EFS1));
                    String efs2 = cursor.getString(cursor.getColumnIndex(EFS2));
                    String efs3 = cursor.getString(cursor.getColumnIndex(EFS3));
                    String efs4 = cursor.getString(cursor.getColumnIndex(EFS4));
                    String efs5 = cursor.getString(cursor.getColumnIndex(EFS5));
                    String efs6 = cursor.getString(cursor.getColumnIndex(EFS6));
                    String efs7 = cursor.getString(cursor.getColumnIndex(EFS7));
                    String efs8 = cursor.getString(cursor.getColumnIndex(EFS8));
                    String efs9 = cursor.getString(cursor.getColumnIndex(EFS9));
                    String efs10 = cursor.getString(cursor.getColumnIndex(EFS10));
                    String efi1 = cursor.getString(cursor.getColumnIndex(EFI1));
                    String efi2 = cursor.getString(cursor.getColumnIndex(EFI2));
                    String efi3 = cursor.getString(cursor.getColumnIndex(EFI3));
                    String efi4 = cursor.getString(cursor.getColumnIndex(EFI4));
                    String efi5 = cursor.getString(cursor.getColumnIndex(EFI5));
                    String efd1 = cursor.getString(cursor.getColumnIndex(EFD1));
                    String efd2 = cursor.getString(cursor.getColumnIndex(EFD2));
                    String efn1 = cursor.getString(cursor.getColumnIndex(EFN1));
                    String efn2 = cursor.getString(cursor.getColumnIndex(EFN2));
                    String efn3 = cursor.getString(cursor.getColumnIndex(EFN3));
                    mSYS_User.setEfs1(efs1);
                    mSYS_User.setEfs2(efs2);
                    mSYS_User.setEfs3(efs3);
                    mSYS_User.setEfs4(efs4);
                    mSYS_User.setEfs5(efs5);
                    mSYS_User.setEfs6(efs6);
                    mSYS_User.setEfs7(efs7);
                    mSYS_User.setEfs8(efs8);
                    mSYS_User.setEfs9(efs9);
                    mSYS_User.setEfs10(efs10);
                    mSYS_User.setEfi1(efi1);
                    mSYS_User.setEfi2(efi2);
                    mSYS_User.setEfi3(efi3);
                    mSYS_User.setEfi4(efi4);
                    mSYS_User.setEfi5(efi5);
                    mSYS_User.setEfd1(efd1);
                    mSYS_User.setEfd2(efd2);
                    mSYS_User.setEfn1(efn1);
                    mSYS_User.setEfn2(efn2);
                    mSYS_User.setEfn3(efn3);
                    SYS_DepartmentDAO mSYS_DepartmentDAO = new SYS_DepartmentDAO(context);
//					SYS_Department mSYS_Department = new SYS_Department();
                    mSYS_DepartmentDAO.selectDepartment(orgId + "", mSYS_Department);
                    mSYS_User.setmSYS_Department(mSYS_Department);
                    TD_UserDAO mTD_UserDAO = new TD_UserDAO(context);
                    TD_User mTD_User = mTD_UserDAO.getTD_User(FullName, false);
                    mSYS_User.setmTD_User(mTD_User);
                    boolean flag = true;
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).UserId.equals(mSYS_User.getUserId()) && userList.get(i).getOrgId() == mSYS_User.getOrgId()) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        userList.add(mSYS_User);
                    }

                }
                if (myApp != null)
                    myApp.setUserList(userList);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return userList;
    }

    public long getCount() {
        long count = 0;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "select count(*) from " + TABLE_NAME, null);
            if (cursor.moveToNext()) {
                count = cursor.getLong(0);
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return count;
    }


    public void insert(String[] culumn, ArrayList<String[]> culumnValueArray) {
        for (String c : culumn) {
            if (!ExtensionField.checkColumnExist(db, c, TABLE_NAME_)) {
                String sql = "ALTER TABLE " + TABLE_NAME_ + "  ADD COLUMN " + c
                        + " VARCHAR(200)";
                db.execSQL(sql);
            }
        }
        if (culumnValueArray.size() > 0) {
            String[] lenth = culumnValueArray.get(0);
            String isDelete = lenth[lenth.length - 1];
            if (isDelete != null && isDelete.equals("1")) {
                db.execSQL("DELETE FROM " + TABLE_NAME_);
            }
        }

        for (String[] culumnValue : culumnValueArray) {
            ContentValues values = new ContentValues();

            for (int i = 0; i < culumn.length; i++) {

                values.put(culumn[i], culumnValue[i]);

            }
            db.replace(TABLE_NAME_, null, values);
        }
    }
}