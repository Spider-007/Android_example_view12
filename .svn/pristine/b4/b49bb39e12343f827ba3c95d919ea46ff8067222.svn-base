package com.htmitech.dao;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.myApplication.BaseApplication;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.db.ExtensionField;
import htmitech.com.componentlibrary.db.HtmitechDatabaseHelper;

/**
 * 部门DAO
 *
 * @author Tony
 */
public class SYS_DepartmentDAO {

    private static final String TABLE_NAME = "org_org_tree";

    private static final String DEPARTMENTCODE = "org_id";//组织ID
    private static final String ORGANISETYPE = "org_type";//'1' COMMENT '组织类型,1-部门，2-集团，3-单位,4-虚拟机构'

    private static final String ORG_CODE = "org_code";//组织机构编码 类似于以前的code

    private static final String SHORTNAME = "org_shortname"; //组织机构简称
    private static final String FULLNAME = "org_name"; //组织机构名称
    private static final String PINYIN = "org_pinyin"; //组织机构拼音
    private static final String DISORDER = "display_order";//显示顺序
    //    private static final String ORGANISETYPE = "OrganiseType";
    private static final String PARENTDEPARTMENT = "parent_org_id"; //上级组织ID
    private static final String ORGSTATUS = "status_flag"; //组织机构状态：-1: 删除，0-禁用，1-正常

    private static final String TREECODE = "tree_code";

    private static final String TELEPHONE = "org_phone";//组织机构的电话
    private static final String FAX = "org_fax";//组织机构的传真
    private static final String ADDRESS = "org_address";//组织机构的地址（部门所在楼层、门牌号等）
    //    private static final String ISDELETE = "IsDelete";
    private static final String CREATEDBY = "create_by";//创建人
    private static final String CREATEDDATE = "create_time";//创建时间
    private static final String MODIFIEDBY = "update_by";//修改人
    private static final String MODIFIEDDATE = "update_time";//修改时间

    private static final String THIRDDEPARTMENTID = "third_dept_id";
    private HtmitechDatabaseHelper mHtmitechDatabaseHelper;
    private SQLiteDatabase db;
    private BaseApplication myApp;
    private Context context;
    private SYS_OrgUserDAO mSYS_OrgUserDAO;

    public SYS_DepartmentDAO(Context context) {
        this.context = context;
        mSYS_OrgUserDAO = new SYS_OrgUserDAO(context);
//        db = DBManager.getInstance(context);
        db = DBCipherManager.getInstance(context).db;
    }

    public SYS_DepartmentDAO(SQLiteDatabase db, BaseApplication myApp) {
        this.db = db;
        this.myApp = myApp;
    }

    public SYS_DepartmentDAO(Context context, BaseApplication myApp) {
        this.context = context;
//        db = DBManager.getInstance(context);
        db = DBCipherManager.getInstance(context).db;
        mSYS_OrgUserDAO = new SYS_OrgUserDAO(context);
        this.myApp = myApp;
    }

    int index = 0;

    public SYS_Department getUpdateDepartments(String DepartmentCode, SYS_Department departmentList) throws ParseException {
        departmentList.getSYS_DepartmentList().clear();

        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.query(TABLE_NAME, null, PARENTDEPARTMENT + "=? and status_flag = 1 and parent_org_id != org_id",
                        new String[]{DepartmentCode}, null, null, null);
                if (cursor == null || cursor.getCount() <= 0) {
                    return null;
                }
                while (cursor.moveToNext()) {
                    SYS_Department mSYS_Department = new SYS_Department();
                    String departmentCode = cursor.getString(cursor
                            .getColumnIndex(DEPARTMENTCODE));
                    mSYS_Department.setDepartmentCode(departmentCode);
                    String ShortName = cursor.getString(cursor
                            .getColumnIndex(SHORTNAME));
                    mSYS_Department.setShortName(ShortName);
                    String fullName = cursor.getString(cursor
                            .getColumnIndex(FULLNAME));
                    mSYS_Department.setFullName(fullName);
                    String OrganiseType = cursor.getString(cursor
                            .getColumnIndex(ORGANISETYPE));
                    mSYS_Department.setOrganiseType(OrganiseType);
                    String ParentDepartment = cursor.getString(cursor
                            .getColumnIndex(PARENTDEPARTMENT));
                    Short orgStatus = cursor.getShort(cursor
                            .getColumnIndex(ORGSTATUS));
                    mSYS_Department.setOrgStatus(orgStatus);
                    mSYS_Department.setParentDepartment(ParentDepartment);
                    String Telephone = cursor.getString(cursor
                            .getColumnIndex(TELEPHONE));
                    mSYS_Department.setTelephone(Telephone);
                    String orgCode = cursor.getString(cursor.getColumnIndex(ORG_CODE));
                    mSYS_Department.setOrgCode(orgCode);
                    String treeCode = cursor.getString(cursor.getColumnIndex(TREECODE));
                    mSYS_Department.setTreeCode(treeCode);
                    String Fax = cursor.getString(cursor.getColumnIndex(FAX));
                    mSYS_Department.setFax(Fax);
                    String Address = cursor.getString(cursor
                            .getColumnIndex(ADDRESS));
                    mSYS_Department.setAddress(Address);
                    String CreatedBy = cursor.getString(cursor
                            .getColumnIndex(CREATEDBY));
                    mSYS_Department.setCreatedBy(CreatedBy);
                    String tree_level = cursor.getString(cursor
                            .getColumnIndex("tree_level"));
                    mSYS_Department.setTree_level(tree_level);
                    String CreatedDate = cursor.getString(cursor
                            .getColumnIndex(CREATEDDATE));
                    mSYS_Department.setCreatedDate(CreatedDate);
                    String ModifiedBy = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDBY));
                    mSYS_Department.setModifiedBy(ModifiedBy);
                    String ModifiedDate = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDDATE));
                    mSYS_Department.setModifiedDate(ModifiedDate);
                    String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                    try {
                        mSYS_Department.setPinyin(Pinyin);
                        mSYS_Department.setSuoxie(Pinyin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String ThirdDepartmentId = cursor.getString(cursor
                            .getColumnIndex(THIRDDEPARTMENTID));
                    mSYS_Department.setThirdDepartmentId(ThirdDepartmentId);
                    int DisOrder = cursor.getInt(cursor.getColumnIndex(DISORDER));
                    mSYS_Department.setDisOrder(DisOrder);

                    departmentList.getSYS_DepartmentList().add(mSYS_Department);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }
        return departmentList;
    }

    /*
    * 晋中定制获取部门根节点
    * */
    public SYS_Department getUpdateDepartmentsRoot(String userId, SYS_Department departmentList) throws ParseException {
        departmentList.getSYS_DepartmentList().clear();

        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                String sql = "select *,uo.* FROM org_user_org uo\n" +
                        "JOIN org_org_tree o ON uo.org_id=o.org_id\n" +
                        "WHERE user_id="+userId+"";
                cursor = db.rawQuery(sql,null);
                if (cursor == null || cursor.getCount() <= 0) {
                    return null;
                }
                while (cursor.moveToNext()) {
                    SYS_Department mSYS_Department = new SYS_Department();
                    String departmentCode = cursor.getString(cursor
                            .getColumnIndex(DEPARTMENTCODE));
                    mSYS_Department.setDepartmentCode(departmentCode);
                    String ShortName = cursor.getString(cursor
                            .getColumnIndex(SHORTNAME));
                    mSYS_Department.setShortName(ShortName);
                    String fullName = cursor.getString(cursor
                            .getColumnIndex(FULLNAME));
                    mSYS_Department.setFullName(fullName);
                    String OrganiseType = cursor.getString(cursor
                            .getColumnIndex(ORGANISETYPE));
                    mSYS_Department.setOrganiseType(OrganiseType);
                    String ParentDepartment = cursor.getString(cursor
                            .getColumnIndex(PARENTDEPARTMENT));
                    Short orgStatus = cursor.getShort(cursor
                            .getColumnIndex(ORGSTATUS));
                    mSYS_Department.setOrgStatus(orgStatus);
                    mSYS_Department.setParentDepartment(ParentDepartment);
                    String Telephone = cursor.getString(cursor
                            .getColumnIndex(TELEPHONE));
                    mSYS_Department.setTelephone(Telephone);
                    String orgCode = cursor.getString(cursor.getColumnIndex(ORG_CODE));
                    mSYS_Department.setOrgCode(orgCode);
                    String treeCode = cursor.getString(cursor.getColumnIndex(TREECODE));
                    mSYS_Department.setTreeCode(treeCode);
                    String Fax = cursor.getString(cursor.getColumnIndex(FAX));
                    mSYS_Department.setFax(Fax);
                    String Address = cursor.getString(cursor
                            .getColumnIndex(ADDRESS));
                    mSYS_Department.setAddress(Address);
                    String CreatedBy = cursor.getString(cursor
                            .getColumnIndex(CREATEDBY));
                    mSYS_Department.setCreatedBy(CreatedBy);
                    String tree_level = cursor.getString(cursor
                            .getColumnIndex("tree_level"));
                    mSYS_Department.setTree_level(tree_level);
                    String CreatedDate = cursor.getString(cursor
                            .getColumnIndex(CREATEDDATE));
                    mSYS_Department.setCreatedDate(CreatedDate);
                    String ModifiedBy = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDBY));
                    mSYS_Department.setModifiedBy(ModifiedBy);
                    String ModifiedDate = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDDATE));
                    mSYS_Department.setModifiedDate(ModifiedDate);
                    String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                    try {
                        mSYS_Department.setPinyin(Pinyin);
                        mSYS_Department.setSuoxie(Pinyin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String ThirdDepartmentId = cursor.getString(cursor
                            .getColumnIndex(THIRDDEPARTMENTID));
                    mSYS_Department.setThirdDepartmentId(ThirdDepartmentId);
                    int DisOrder = cursor.getInt(cursor.getColumnIndex(DISORDER));
                    mSYS_Department.setDisOrder(DisOrder);

                    departmentList.getSYS_DepartmentList().add(mSYS_Department);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }
        return departmentList;
    }

    /*
    * 晋中定制通讯录查询单位根节点
    * */
    public SYS_Department getUpdateUnitsRoot(String userId, SYS_Department departmentList) throws ParseException {
        departmentList.getSYS_DepartmentList().clear();

        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                String sql = "SELECT * FROM org_org_tree \n" +
                        "WHERE org_type=3 AND status_flag=1 \n" +
                        "AND corp_id IN (SELECT corp_id FROM org_user_org WHERE user_id=" + userId + ")";
                cursor = db.rawQuery(sql, null);
                if (cursor == null || cursor.getCount() <= 0) {
                    return null;
                }
                while (cursor.moveToNext()) {
                    SYS_Department mSYS_Department = new SYS_Department();
                    String departmentCode = cursor.getString(cursor
                            .getColumnIndex(DEPARTMENTCODE));
                    mSYS_Department.setDepartmentCode(departmentCode);
                    String ShortName = cursor.getString(cursor
                            .getColumnIndex(SHORTNAME));
                    mSYS_Department.setShortName(ShortName);
                    String fullName = cursor.getString(cursor
                            .getColumnIndex(FULLNAME));
                    mSYS_Department.setFullName(fullName);
                    String OrganiseType = cursor.getString(cursor
                            .getColumnIndex(ORGANISETYPE));
                    mSYS_Department.setOrganiseType(OrganiseType);
                    String ParentDepartment = cursor.getString(cursor
                            .getColumnIndex(PARENTDEPARTMENT));
                    Short orgStatus = cursor.getShort(cursor
                            .getColumnIndex(ORGSTATUS));
                    mSYS_Department.setOrgStatus(orgStatus);
                    mSYS_Department.setParentDepartment(ParentDepartment);
                    String Telephone = cursor.getString(cursor
                            .getColumnIndex(TELEPHONE));
                    mSYS_Department.setTelephone(Telephone);
                    String orgCode = cursor.getString(cursor.getColumnIndex(ORG_CODE));
                    mSYS_Department.setOrgCode(orgCode);
                    String treeCode = cursor.getString(cursor.getColumnIndex(TREECODE));
                    mSYS_Department.setTreeCode(treeCode);
                    String Fax = cursor.getString(cursor.getColumnIndex(FAX));
                    mSYS_Department.setFax(Fax);
                    String Address = cursor.getString(cursor
                            .getColumnIndex(ADDRESS));
                    mSYS_Department.setAddress(Address);
                    String CreatedBy = cursor.getString(cursor
                            .getColumnIndex(CREATEDBY));
                    mSYS_Department.setCreatedBy(CreatedBy);
                    String tree_level = cursor.getString(cursor
                            .getColumnIndex("tree_level"));
                    mSYS_Department.setTree_level(tree_level);
                    String CreatedDate = cursor.getString(cursor
                            .getColumnIndex(CREATEDDATE));
                    mSYS_Department.setCreatedDate(CreatedDate);
                    String ModifiedBy = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDBY));
                    mSYS_Department.setModifiedBy(ModifiedBy);
                    String ModifiedDate = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDDATE));
                    mSYS_Department.setModifiedDate(ModifiedDate);
                    String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                    try {
                        mSYS_Department.setPinyin(Pinyin);
                        mSYS_Department.setSuoxie(Pinyin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String ThirdDepartmentId = cursor.getString(cursor
                            .getColumnIndex(THIRDDEPARTMENTID));
                    mSYS_Department.setThirdDepartmentId(ThirdDepartmentId);
                    int DisOrder = cursor.getInt(cursor.getColumnIndex(DISORDER));
                    mSYS_Department.setDisOrder(DisOrder);

                    departmentList.getSYS_DepartmentList().add(mSYS_Department);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }
        return departmentList;
    }

    public SYS_Department getUpdateUnitsRoot2(String DepartmentCode, SYS_Department departmentList) throws ParseException {
        departmentList.getSYS_DepartmentList().clear();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                String sql = "SELECT * FROM org_org_tree WHERE corp_id=" + DepartmentCode + " AND parent_org_id=" + DepartmentCode + "";
                cursor = db.rawQuery(sql, null);
                if (cursor == null || cursor.getCount() <= 0) {
                    return null;
                }
                while (cursor.moveToNext()) {
                    SYS_Department mSYS_Department = new SYS_Department();
                    String departmentCode = cursor.getString(cursor
                            .getColumnIndex(DEPARTMENTCODE));
                    mSYS_Department.setDepartmentCode(departmentCode);
                    String ShortName = cursor.getString(cursor
                            .getColumnIndex(SHORTNAME));
                    mSYS_Department.setShortName(ShortName);
                    String fullName = cursor.getString(cursor
                            .getColumnIndex(FULLNAME));
                    mSYS_Department.setFullName(fullName);
                    Short orgStatus = cursor.getShort(cursor
                            .getColumnIndex(ORGSTATUS));
                    String orgCode = cursor.getString(cursor.getColumnIndex(ORG_CODE));
                    mSYS_Department.setOrgCode(orgCode);
                    String treeCode = cursor.getString(cursor.getColumnIndex(TREECODE));
                    mSYS_Department.setTreeCode(treeCode);
                    mSYS_Department.setOrgStatus(orgStatus);
                    String OrganiseType = cursor.getString(cursor
                            .getColumnIndex(ORGANISETYPE));
                    mSYS_Department.setOrganiseType(OrganiseType);
                    String ParentDepartment = cursor.getString(cursor
                            .getColumnIndex(PARENTDEPARTMENT));
                    mSYS_Department.setParentDepartment(ParentDepartment);
                    String Telephone = cursor.getString(cursor
                            .getColumnIndex(TELEPHONE));
                    mSYS_Department.setTelephone(Telephone);
                    String Fax = cursor.getString(cursor.getColumnIndex(FAX));
                    mSYS_Department.setFax(Fax);
                    String Address = cursor.getString(cursor
                            .getColumnIndex(ADDRESS));
                    mSYS_Department.setAddress(Address);
                    String CreatedBy = cursor.getString(cursor
                            .getColumnIndex(CREATEDBY));
                    mSYS_Department.setCreatedBy(CreatedBy);
                    String CreatedDate = cursor.getString(cursor
                            .getColumnIndex(CREATEDDATE));
                    mSYS_Department.setCreatedDate(CreatedDate);
                    String ModifiedBy = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDBY));
                    mSYS_Department.setModifiedBy(ModifiedBy);
                    String ModifiedDate = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDDATE));
                    mSYS_Department.setModifiedDate(ModifiedDate);
                    String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                    try {
                        mSYS_Department.setPinyin(Pinyin);
                        mSYS_Department.setSuoxie(Pinyin);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String ThirdDepartmentId = cursor.getString(cursor
                            .getColumnIndex(THIRDDEPARTMENTID));
                    mSYS_Department.setThirdDepartmentId(ThirdDepartmentId);
                    int DisOrder = cursor.getInt(cursor.getColumnIndex(DISORDER));
                    mSYS_Department.setDisOrder(DisOrder);
//                    SYS_OrgUserDAO mSYS_OrgUserDAO = new SYS_OrgUserDAO(context);
//                    ArrayList<SYS_User> arraylistUser = (ArrayList<SYS_User>) mSYS_OrgUserDAO.findPartmentIdOrgUser(departmentCode, mSYS_Department);
                    SYS_UserDAO mSYS_UserDAO = new SYS_UserDAO(context);
                    ArrayList<SYS_User> arraylistUser = (ArrayList<SYS_User>) mSYS_UserDAO.getSYSUer(departmentCode, mSYS_Department);
                    index += arraylistUser.size();
                    mSYS_Department.setSYS_User(arraylistUser);
                    getDepartments(departmentCode, mSYS_Department);
                    departmentList.getSYS_DepartmentList().add(mSYS_Department);
                }
                if (myApp != null) {
                    myApp.setSYS_Department_(departmentList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }
        return departmentList;
    }


    public SYS_Department getDepartments(String DepartmentCode, SYS_Department departmentList) throws ParseException {
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.query(TABLE_NAME, null, PARENTDEPARTMENT + "=? and status_flag = 1 and parent_org_id != org_id",
                        new String[]{DepartmentCode}, null, null, null);
                if (cursor == null || cursor.getCount() <= 0) {
                    return null;
                }
                while (cursor.moveToNext()) {
                    SYS_Department mSYS_Department = new SYS_Department();
                    String departmentCode = cursor.getString(cursor
                            .getColumnIndex(DEPARTMENTCODE));
                    mSYS_Department.setDepartmentCode(departmentCode);
                    String ShortName = cursor.getString(cursor
                            .getColumnIndex(SHORTNAME));
                    mSYS_Department.setShortName(ShortName);
                    String fullName = cursor.getString(cursor
                            .getColumnIndex(FULLNAME));
                    mSYS_Department.setFullName(fullName);
                    Short orgStatus = cursor.getShort(cursor
                            .getColumnIndex(ORGSTATUS));
                    String orgCode = cursor.getString(cursor.getColumnIndex(ORG_CODE));
                    mSYS_Department.setOrgCode(orgCode);
                    String treeCode = cursor.getString(cursor.getColumnIndex(TREECODE));
                    mSYS_Department.setTreeCode(treeCode);
                    mSYS_Department.setOrgStatus(orgStatus);
                    String OrganiseType = cursor.getString(cursor
                            .getColumnIndex(ORGANISETYPE));
                    mSYS_Department.setOrganiseType(OrganiseType);
                    String ParentDepartment = cursor.getString(cursor
                            .getColumnIndex(PARENTDEPARTMENT));
                    mSYS_Department.setParentDepartment(ParentDepartment);
                    String Telephone = cursor.getString(cursor
                            .getColumnIndex(TELEPHONE));
                    mSYS_Department.setTelephone(Telephone);
                    String Fax = cursor.getString(cursor.getColumnIndex(FAX));
                    mSYS_Department.setFax(Fax);
                    String Address = cursor.getString(cursor
                            .getColumnIndex(ADDRESS));
                    mSYS_Department.setAddress(Address);
                    String CreatedBy = cursor.getString(cursor
                            .getColumnIndex(CREATEDBY));
                    mSYS_Department.setCreatedBy(CreatedBy);
                    String CreatedDate = cursor.getString(cursor
                            .getColumnIndex(CREATEDDATE));
                    mSYS_Department.setCreatedDate(CreatedDate);
                    String ModifiedBy = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDBY));
                    mSYS_Department.setModifiedBy(ModifiedBy);
                    String ModifiedDate = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDDATE));
                    mSYS_Department.setModifiedDate(ModifiedDate);
                    String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                    try {
                        mSYS_Department.setPinyin(Pinyin);
                        mSYS_Department.setSuoxie(Pinyin);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String ThirdDepartmentId = cursor.getString(cursor
                            .getColumnIndex(THIRDDEPARTMENTID));
                    mSYS_Department.setThirdDepartmentId(ThirdDepartmentId);
                    int DisOrder = cursor.getInt(cursor.getColumnIndex(DISORDER));
                    mSYS_Department.setDisOrder(DisOrder);
//                    SYS_OrgUserDAO mSYS_OrgUserDAO = new SYS_OrgUserDAO(context);
//                    ArrayList<SYS_User> arraylistUser = (ArrayList<SYS_User>) mSYS_OrgUserDAO.findPartmentIdOrgUser(departmentCode, mSYS_Department);
                    SYS_UserDAO mSYS_UserDAO = new SYS_UserDAO(context);
                    ArrayList<SYS_User> arraylistUser = (ArrayList<SYS_User>) mSYS_UserDAO.getSYSUer(departmentCode, mSYS_Department);
                    index += arraylistUser.size();
                    mSYS_Department.setSYS_User(arraylistUser);
                    getDepartments(departmentCode, mSYS_Department);
                    departmentList.getSYS_DepartmentList().add(mSYS_Department);
                }
                if (myApp != null) {
                    myApp.setSYS_Department_(departmentList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }
        return departmentList;
    }


    public SYS_Department searchDepartmentIsContains(SYS_Department mSYS_DepartmentupAll,
                                                     SYS_Department mSYS_Departmentup) {
        for (SYS_Department sSYS_Department : mSYS_DepartmentupAll
                .getSYS_DepartmentList()) {
            if (mSYS_DepartmentupAll.getDepartmentCode().equals(
                    mSYS_Departmentup.getParentDepartment())) {
                mSYS_DepartmentupAll.getSYS_DepartmentList().add(mSYS_Departmentup);
                return mSYS_Departmentup;
            } else {
                return searchDepartmentIsContains(sSYS_Department, mSYS_Departmentup);
            }
        }


        return null;
    }


    public void selectDepartment(String DepartmentCode,
                                 SYS_Department mSYS_Department) {
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.query(TABLE_NAME, null, DEPARTMENTCODE + "=? and status_flag = 1 and parent_org_id != org_id",
                        new String[]{DepartmentCode}, null, null, null);
                if (cursor.moveToLast()) {
                    String departmentCode = cursor.getString(cursor
                            .getColumnIndex(DEPARTMENTCODE));
                    mSYS_Department.setDepartmentCode(departmentCode);
                    String ShortName = cursor.getString(cursor
                            .getColumnIndex(SHORTNAME));
                    mSYS_Department.setShortName(ShortName);
                    String fullName = cursor.getString(cursor
                            .getColumnIndex(FULLNAME));
                    mSYS_Department.setFullName(fullName);
                    String OrganiseType = cursor.getString(cursor
                            .getColumnIndex(ORGANISETYPE));
                    String orgCode = cursor.getString(cursor.getColumnIndex(ORG_CODE));
                    mSYS_Department.setOrgCode(orgCode);
                    String treeCode = cursor.getString(cursor.getColumnIndex(TREECODE));
                    mSYS_Department.setTreeCode(treeCode);
                    Short orgStatus = cursor.getShort(cursor
                            .getColumnIndex(ORGSTATUS));
                    mSYS_Department.setOrgStatus(orgStatus);
                    mSYS_Department.setOrganiseType(OrganiseType);
                    String ParentDepartment = cursor.getString(cursor
                            .getColumnIndex(PARENTDEPARTMENT));
                    mSYS_Department.setParentDepartment(ParentDepartment);
                    String Telephone = cursor.getString(cursor
                            .getColumnIndex(TELEPHONE));
                    mSYS_Department.setTelephone(Telephone);
                    String Fax = cursor.getString(cursor.getColumnIndex(FAX));
                    mSYS_Department.setFax(Fax);
                    String Address = cursor.getString(cursor
                            .getColumnIndex(ADDRESS));
                    mSYS_Department.setAddress(Address);
                    String CreatedBy = cursor.getString(cursor
                            .getColumnIndex(CREATEDBY));
                    mSYS_Department.setCreatedBy(CreatedBy);
                    String CreatedDate = cursor.getString(cursor
                            .getColumnIndex(CREATEDDATE));
                    mSYS_Department.setCreatedDate(CreatedDate);
                    String ModifiedBy = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDBY));
                    mSYS_Department.setModifiedBy(ModifiedBy);
                    String ModifiedDate = cursor.getString(cursor
                            .getColumnIndex(MODIFIEDDATE));
                    mSYS_Department.setModifiedDate(ModifiedDate);
                    String Pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
                    try {

                        mSYS_Department.setPinyin(Pinyin);
                        mSYS_Department.setSuoxie(Pinyin);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String ThirdDepartmentId = cursor.getString(cursor
                            .getColumnIndex(THIRDDEPARTMENTID));
                    mSYS_Department.setThirdDepartmentId(ThirdDepartmentId);
                    int DisOrder = cursor.getInt(cursor.getColumnIndex(DISORDER));
                    mSYS_Department.setDisOrder(DisOrder);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }
    }

    /**
     * 获取根节点信息
     */
    public String departmentCode(String portalId) {
//        String sql = "SELECT * from org_org_tree WHERE tree_level = (\n" +
//                "SELECT min(tree_level) from org_org_tree where \n" +
//                "corp_id in (select corp_id from emp_corp_portal WHERE portal_id="+portalId+") LIMIT 0,1);";
        String sql = "SELECT t.org_id FROM emp_corp_portal cp JOIN org_org_tree t ON t.org_type = 3 " +
                "AND t.corp_id = cp.corp_id AND t.status_flag = 1 WHERE cp.portal_id = " + portalId + " LIMIT 1;";
        Cursor cursor = null;
        cursor = db.rawQuery(sql, null);
        String departmentCode = "";
        if (cursor.moveToFirst()) {
            departmentCode = cursor.getString(cursor
                    .getColumnIndex(DEPARTMENTCODE));
        }
        if (null != cursor && !cursor.isClosed())
            cursor.close();
        return departmentCode;
    }

    /**
     * 获取根节点code信息
     *
     * @param parent_org_id
     * @return
     */
    public String departmentTreeCode(String parent_org_id) {
        if (TextUtils.isEmpty(parent_org_id)) {
            return "";
        }
        String sql = "SELECT * FROM org_org_tree WHERE org_id = " + parent_org_id;
        Cursor cursor = null;
        cursor = db.rawQuery(sql, null);
        String departmentCode = "";
        if (cursor.moveToFirst()) {
            departmentCode = cursor.getString(cursor
                    .getColumnIndex(TREECODE));
        }
        if (null != cursor && !cursor.isClosed())
            cursor.close();
        return departmentCode;
    }

    /**
     * 复制 以防索引指向相同
     *
     * @param parentSYS_Department
     */
    public void copyDepartment(SYS_Department parentSYS_Department) {
        SYS_Department mNewSYS_Department = new SYS_Department();
        List<SYS_Department> SYS_DepartmentList = new ArrayList<SYS_Department>();
        SYS_DepartmentList.addAll(parentSYS_Department.getSYS_DepartmentList());
        List<SYS_User> SYS_User = new ArrayList<com.htmitech.domain.SYS_User>();
        SYS_User.addAll(parentSYS_Department.getSYS_User());
        String code = new String(parentSYS_Department.getDepartmentCode());
        String ParentDepartment = new String(
                parentSYS_Department.getParentDepartment());
        String ShortName = new String(parentSYS_Department.getShortName());
        String FullName = new String(parentSYS_Department.getFullName());
        mNewSYS_Department.setDepartmentCode(code);
        mNewSYS_Department.setParentDepartment(ParentDepartment);
        mNewSYS_Department.setShortName(ShortName);
        mNewSYS_Department.setFullName(FullName);
        mNewSYS_Department.setSYS_User(SYS_User);
        mNewSYS_Department.setSYS_DepartmentList(SYS_DepartmentList);
        myApp.setSYS_Department_(mNewSYS_Department);
    }

    public void insert(String[] culumn, ArrayList<String[]> culumnValueArray, String isDelete) {
        for (String c : culumn) {
            if (!ExtensionField.checkColumnExist(db, c, TABLE_NAME)) {
                String sql = "ALTER TABLE " + TABLE_NAME + "  ADD COLUMN " + c
                        + " VARCHAR(200)";
                db.execSQL(sql);
            }
        }
        if (culumnValueArray.size() > 0) {
            if (isDelete.equals("1")) {
                db.execSQL("DELETE FROM " + TABLE_NAME);
            }
        }

        for (String[] culumnValue : culumnValueArray) {
            ContentValues values = new ContentValues();
            for (int i = 0; i < culumn.length; i++) {
                values.put(culumn[i], culumnValue[i]);
            }
            db.replace(TABLE_NAME, null, values);
        }
    }
}
