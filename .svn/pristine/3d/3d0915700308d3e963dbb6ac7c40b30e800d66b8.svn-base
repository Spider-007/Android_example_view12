package dao;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.domain.ApcUserdefinePortal;
import com.htmitech.utils.OAConText;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

import entiy.AppInfo;
import entiy.AppVersion;
import entiy.AppVersionConfig;
import entiy.AppclientVersion;
import entiy.EmpPortal;
import entiy.RequestResort;
import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.db.HtmitechDatabaseHelper;
import utils.DBUnitl;


/**
 * Created by htrf-pc on 2016/12/2.
 */
public class AppliationCenterDao {
    private SQLiteDatabase db;
    private Context context;
    private HtmitechDatabaseHelper mHtmitechDatabaseHelper;
    public AppliationCenterDao(Context context) {
        db = DBCipherManager.getInstance(context).db;
        this.context = context;
    }

    /**
     * 获取所有门户
     *
     * @return
     */
    public ArrayList<EmpPortal> getPortalAll() {
        String sql = "select * from emp_portal JOIN emp_corp_portal p ON(emp_portal.portal_id = p.portal_id)   where emp_portal.status_flag > 0\n";
        ArrayList<EmpPortal> mEmpPortalList = new ArrayList<EmpPortal>();
        String currentPortal = BookInit.getInstance().getPortalId();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    EmpPortal mEmpPortal = new EmpPortal();
                    mEmpPortal.portal_id = cursor.getString(cursor.getColumnIndex("portal_id"));
                    mEmpPortal.home_style = cursor.getInt(cursor.getColumnIndex("home_style"));
                    mEmpPortal.portal_name = cursor.getString(cursor.getColumnIndex("portal_name"));
                    mEmpPortal.color_style = cursor.getInt(cursor.getColumnIndex("color_style"));
                    mEmpPortal.portal_logo = cursor.getString(cursor.getColumnIndex("portal_logo"));
                    mEmpPortal.apc_style = cursor.getInt(cursor.getColumnIndex("apc_style"));
                    mEmpPortal.font_style = cursor.getInt(cursor.getColumnIndex("font_style"));
                    mEmpPortal.mx_appid = cursor.getLong(cursor.getColumnIndex("mx_appid"));
                    mEmpPortal.emi_network_id = cursor.getLong(cursor.getColumnIndex("emi_network_id"));
                    mEmpPortal.network_code = cursor.getString(cursor.getColumnIndex("network_code"));
                    mEmpPortal.corp_id = cursor.getLong(cursor.getColumnIndex("corp_id")) +"";
                    mEmpPortal.group_corp_id = cursor.getLong(cursor.getColumnIndex("group_corp_id"));
                    if (currentPortal.equals("") || currentPortal.equals("-1")) {
//                        mEmpPortal.isCheck = cursor.getInt(cursor.getColumnIndex("is_default")) > 0 ? true : false;
                    } else if (currentPortal.equals("" + mEmpPortal.portal_id)) {
                        mEmpPortal.isCheck = true;
                    } else {
                        mEmpPortal.isCheck = false;
                    }
                    try{
                        mEmpPortal.mApcUserdefinePortal = getUserDefinePortal(mEmpPortal.portal_id);
                    }catch (Exception e){

                    }

//                    mEmpPortal.mApcUserdefinePortal.portal_name = mEmpPortal.portal_name;
                    if(!mEmpPortalList.contains(mEmpPortal))
                        mEmpPortalList.add(mEmpPortal);

                    if ((currentPortal.equals("") || currentPortal.equals("-1")) && mEmpPortalList.size() == 1) {
                        mEmpPortal.isCheck = true;
                    }
                }
                cursor.close();
            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }
        return mEmpPortalList;
    }


    /**
     * 保存用户自定义内容表信息
     *
     * @param mApcUserdefinePortal
     */
    public void saveUserDefinePortal(ApcUserdefinePortal mApcUserdefinePortal) {
        System.out.print(mApcUserdefinePortal.toString());
        Log.e("ApplicationCenterDao", mApcUserdefinePortal.toString());

        ContentValues values = new ContentValues();
        values.put("user_id", mApcUserdefinePortal.getUser_id());
        values.put("portal_id", mApcUserdefinePortal.portal_id);
        values.put("is_using", mApcUserdefinePortal.is_using);
        values.put("using_home_style", mApcUserdefinePortal.using_home_style);
        values.put("using_color_style", mApcUserdefinePortal.using_color_style);
        values.put("using_layout_style", mApcUserdefinePortal.using_layout_style);
        values.put("using_apc_style", mApcUserdefinePortal.using_apc_style);
        values.put("using_font_style", mApcUserdefinePortal.using_font_style);
        values.put("group_corp_id", mApcUserdefinePortal.group_corp_id);
        values.put("status_flag", mApcUserdefinePortal.is_using);
        db.replace("apc_userdefine_portal", null, values);
    }

    public ApcUserdefinePortal getUserDefinePortal(String portal) {
        String sql = "select * from apc_userdefine_portal where user_id = " + BookInit.getInstance().getCrrentUserId() + " and portal_id = " + portal;
        ApcUserdefinePortal mApcUserdefinePortal = null;
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                mApcUserdefinePortal = DBUnitl.getObject(ApcUserdefinePortal.class, cursor);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        }
        if (mApcUserdefinePortal != null) {
            mApcUserdefinePortal.using_apc_style = mApcUserdefinePortal.using_apc_style == 0 ? 3 : mApcUserdefinePortal.using_apc_style;
//            BookInit.getInstance().setmApcUserdefinePortal(mApcUserdefinePortal);
        }
        return mApcUserdefinePortal;

    }

    /**
     * 获取用户自定义表内容
     *
     * @return
     */
    public synchronized ApcUserdefinePortal getUserDefinePortal() {
        String sql = "select * from apc_userdefine_portal where user_id = " + BookInit.getInstance().getCrrentUserId() + " and portal_id = " + BookInit.getInstance().getPortalId() ;
        ApcUserdefinePortal mApcUserdefinePortal = null;
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                mApcUserdefinePortal = DBUnitl.getObject(ApcUserdefinePortal.class, cursor);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        }
        if (mApcUserdefinePortal != null) {
            mApcUserdefinePortal.using_apc_style = mApcUserdefinePortal.using_apc_style == 0 ? 3 : mApcUserdefinePortal.using_apc_style;
            BookInit.getInstance().setmApcUserdefinePortal(mApcUserdefinePortal);
        }

        return mApcUserdefinePortal;
    }

    /**
     * 获取门户ID 获取当前门户
     *
     * @return
     */

    public synchronized EmpPortal getPortalId() {
        String currentPortal = BookInit.getInstance().getPortalId();
        String sql = "";
        if (currentPortal == null || currentPortal.equals("") || currentPortal.equals("-1")) {
            //如果为空或者为-1的时候  我们设置当前门户为默认门户
            sql = "select * from emp_portal JOIN emp_corp_portal p ON(emp_portal.portal_id = p.portal_id) where emp_portal.is_default > 0 and emp_portal.status_flag > 0";
        } else {
            //如果设置了门户的话，那么就要获取设置的门户进行查找
            sql = "select * from emp_portal JOIN emp_corp_portal p ON(emp_portal.portal_id = p.portal_id)  where emp_portal.portal_id ="+currentPortal+" and emp_portal.status_flag > 0\n";
        }
        ApcUserdefinePortal mApcUserdefinePortal = null;
        if(TextUtils.isEmpty(currentPortal)){
            currentPortal = "";
        }
        if(BookInit.getInstance().getmApcUserdefinePortal() == null || TextUtils.isEmpty(BookInit.getInstance().getmApcUserdefinePortal().getPortal_id()) || !currentPortal.contains(BookInit.getInstance().getmApcUserdefinePortal().getPortal_id())){
            mApcUserdefinePortal = getUserDefinePortal();
        }else{
            mApcUserdefinePortal = BookInit.getInstance().getmApcUserdefinePortal();
        }

        String corp_id = "";
        EmpPortal mEmpPortal = new EmpPortal();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    mEmpPortal.portal_id = cursor.getString(cursor.getColumnIndex("portal_id"));
                    mEmpPortal.home_style = cursor.getInt(cursor.getColumnIndex("home_style"));
                    mEmpPortal.color_style = cursor.getInt(cursor.getColumnIndex("color_style"));
                    mEmpPortal.portal_name = cursor.getString(cursor.getColumnIndex("portal_name"));
                    mEmpPortal.portal_logo = cursor.getString(cursor.getColumnIndex("portal_logo"));
                    mEmpPortal.apc_style = cursor.getInt(cursor.getColumnIndex("apc_style"));
                    mEmpPortal.font_style = cursor.getInt(cursor.getColumnIndex("font_style"));
                    mEmpPortal.mx_appid = cursor.getLong(cursor.getColumnIndex("mx_appid"));
                    mEmpPortal.emi_network_id = cursor.getLong(cursor.getColumnIndex("emi_network_id"));
                    mEmpPortal.network_code = cursor.getString(cursor.getColumnIndex("network_code"));
                    mEmpPortal.group_corp_id = cursor.getLong(cursor.getColumnIndex("group_corp_id"));
                    corp_id = cursor.getLong(cursor.getColumnIndex("corp_id")) + "";
                    mEmpPortal.corp_id = corp_id;
                    if (mApcUserdefinePortal == null) {
                        mApcUserdefinePortal = new ApcUserdefinePortal();
                        mApcUserdefinePortal.is_using = 1;
                        mApcUserdefinePortal.portal_id = mEmpPortal.portal_id;
                        mApcUserdefinePortal.using_apc_style = mEmpPortal.apc_style;
                        mApcUserdefinePortal.using_color_style = mEmpPortal.color_style;
                        mApcUserdefinePortal.using_font_style = mEmpPortal.font_style;
                        mApcUserdefinePortal.using_home_style = mEmpPortal.home_style;
                        mApcUserdefinePortal.group_corp_id = mEmpPortal.group_corp_id;
                        mApcUserdefinePortal.corp_id = mEmpPortal.corp_id;
                        mApcUserdefinePortal.portal_name = mEmpPortal.portal_name;
                        mApcUserdefinePortal.user_id = BookInit.getInstance().getCrrentUserId() == null ? OAConText.getInstance(context).UserID : BookInit.getInstance().getCrrentUserId();
                        BookInit.getInstance().setmApcUserdefinePortal(mApcUserdefinePortal);
                    } else {
                        mEmpPortal.apc_style = mApcUserdefinePortal.using_apc_style;
                        mEmpPortal.color_style = mApcUserdefinePortal.using_color_style;
                        mEmpPortal.home_style = mApcUserdefinePortal.using_home_style;
                        mEmpPortal.font_style = mApcUserdefinePortal.using_font_style;
                        BookInit.getInstance().getmApcUserdefinePortal().setGroup_corp_id(mEmpPortal.group_corp_id);
                        mApcUserdefinePortal.portal_name = mEmpPortal.portal_name;
                        mApcUserdefinePortal.setMx_app_id((int) mEmpPortal.mx_appid);
                    }
                    BookInit.getInstance().setApc_style(mEmpPortal.apc_style);
                }
                cursor.close();
            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

        }
        if(!TextUtils.isEmpty(corp_id))
        BookInit.getInstance().setCorp_id(corp_id);

        Constant.color = mEmpPortal.color_style;
//        if(!TextUtils.isEmpty(mEmpPortal.portal_id))
//            BookInit.getInstance().setPortalId(mEmpPortal.portal_id);
//        BookInit.getInstance().setPortalName(mEmpPortal.portal_name);
        return mEmpPortal;
    }

    /**
     * 保存当前门户的所有信息
     */
    public void saveCurrentPortalMessage(String font_size, String home, String portal) {
        String sql = "update emp_portal set font_style = " + font_size + ",home_style = " + home + ",mx_appid = " + BookInit.getInstance().getMx_appid() + " where portal_id = " + portal;
        if (db.isOpen()) {
            try {

                db.execSQL(sql);

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
            }
        }

    }

    /**
     * 切换门户
     *
     * @return
     */
    public void switchPortal(EmpPortal mEmpPortal) {
        String sql = "update emp_portal set is_default = " + (mEmpPortal.isCheck == true ? 1 : -1) + " where portal_id = " + mEmpPortal.portal_id;

        if (db.isOpen()) {
            try {

                db.execSQL(sql);

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
            }
        }

    }

    /**
     * 获取所有portal_app下所有应用 仅供清除缓存用
     * @return
     */
    public ArrayList<AppInfo> getPortalAppInfoAll(){
        ArrayList<AppInfo> mAppInfoList = new ArrayList<AppInfo>();
        String portal_id = BookInit.getInstance().getPortalId();
        String sql = "select * from portal_app JOIN app_info ON(portal_app.app_id = app_info.app_id) where portal_app.portal_id = "+portal_id;
        if (db.isOpen()) {
            Cursor cursor = null;
            try {

                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    AppInfo mAppInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String app_code = cursor.getString(cursor.getColumnIndex("app_code"));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String app_shortname = cursor.getString(cursor.getColumnIndex("app_shortname"));
                    int app_type = cursor.getInt(cursor.getColumnIndex("app_type"));
                    String app_logo = cursor.getString(cursor.getColumnIndex("app_logo"));
                    String app_desc = cursor.getString(cursor.getColumnIndex("app_desc"));
                    String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
                    String plugin_code = cursor.getString(cursor.getColumnIndex("plugin_code"));
                    long parent_app_id = cursor.getLong(cursor.getColumnIndex("parent_app_id"));
                    long current_version = getCurrentVersion(app_id);
                    String picture_disabled = cursor.getString(cursor.getColumnIndex("picture_disabled"));
                    String picture_selected = cursor.getString(cursor.getColumnIndex("picture_selected"));
                    String picture_normal = cursor.getString(cursor.getColumnIndex("picture_normal"));

                    mAppInfo.setApp_id(app_id);
                    mAppInfo.setApp_code(app_code);
                    mAppInfo.setApp_name(app_name);
                    mAppInfo.setApp_shortname(app_shortname);
                    mAppInfo.setApp_type(app_type);
                    mAppInfo.setApp_logo(app_logo);
                    mAppInfo.setApp_desc(app_desc);
                    mAppInfo.setPlugin_code(plugin_code);
                    mAppInfo.setComp_code(comp_code);
                    mAppInfo.setParent_app_id(parent_app_id);
                    mAppInfo.setCurrent_version(current_version);
                    mAppInfo.setPicture_disabled(picture_disabled);
                    mAppInfo.setPicture_normal(picture_normal);
                    mAppInfo.setPicture_selected(picture_selected);
                    max_version_number = 0;
                    ArrayList<AppVersion> mAppVersionList = getVersion(app_id + "");

                    for (AppVersion mAppVersion : mAppVersionList) {
                        if (current_version > 0) {
                            if (max_version_number == current_version) {
                                mAppInfo.setApk_flag(3);
                                mAppInfo.setmAppVersion(mAppVersion);
                            } else {
                                if (max_version_number > current_version) {
                                    mAppInfo.setApk_flag(1);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                } else {
                                    mAppInfo.setApk_flag(3);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                }

                            }
                        } else {
                            if(mAppInfo.getApp_type() == 3){
                                mAppInfo.setApk_flag(3);
//                                mAppVersion.setMustupdated(1);
                            }
                            mAppInfo.setmAppVersion(mAppVersion);
                        }

//                        else if (max_version_number == mAppVersion.getVersion_number()){
//                            mAppInfo.setApk_flag(1);
//                            mAppInfo.setmAppVersion(mAppVersion);
//                        }
                    }

                    mAppInfo.setMax_version_number(max_version_number);
                    mAppInfo.setmAppVersionList(mAppVersionList);
                    mAppInfoList.add(mAppInfo);
                }
                cursor.close();
            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return mAppInfoList;

    }


    /**
     * 查询分类插件中的应用
     */

    public ArrayList<AppInfo> selectClassify(String parentAppId) {
        String sql = "";
//                "SELECT distinct  app.display_title,app.app_id,app.appcenter_remove,info.app_code,info.app_name,info.app_shortname,info.app_type,info.app_logo,info.app_desc,info.comp_code,info.plugin_code,info.parent_app_id,app.picture_disabled,app.picture_selected,app.picture_normal,info.parent_appgroup_app_id " +
//                " FROM portal_app  app LEFT JOIN user_apc_portal_app  ON(app.app_id = user_apc_portal_app.app_id) JOIN app_info info on(app.app_id = info.app_id) WHERE info.parent_appgroup_app_id = " + parentAppId + " and (user_apc_portal_app.user_using = 1 or user_apc_portal_app.user_using is null ) and info.status_flag = 1 and app.status_flag= 1 and app.appcenter_include = 1 and app.portal_id = "+BookInit.getInstance().getPortalId()+" and (user_apc_portal_app.portal_id = " + BookInit.getInstance().getPortalId() + " or user_apc_portal_app.portal_id is null ) ORDER BY user_apc_portal_app.display_order";
        ArrayList<AppInfo> mAppInfoList = new ArrayList<AppInfo>();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                sql = "SELECT * FROM user_apc_portal_app app JOIN app_info info on(app.app_id = info.app_id) LEFT JOIN apc_app_appgroup ON( app.app_id = apc_app_appgroup.app_id AND apc_app_appgroup.status_flag > 0) WHERE apc_app_appgroup.appgroup_app_id = " +parentAppId+" AND info.status_flag > 0 AND app.portal_id="+ BookInit.getInstance().getPortalId() + "";
                cursor = db.rawQuery(sql, null);
                int number = cursor.getCount();
                cursor.close();
//                //如果自定义中为0的话 那么从portal_app中获取
                if (number == 0) {
                    cursor = null;
                    sql = "SELECT distinct  apc_app_appgroup.appgroup_app_id AS app_group_id,app.display_title,app.app_id,app.appcenter_remove,info.app_code,info.app_name,info.app_shortname,info.app_type,info.app_logo,info.app_desc,info.comp_code,info.plugin_code,info.parent_app_id,app.picture_disabled,app.picture_selected,app.picture_normal,info.parent_appgroup_app_id " +
                            " FROM portal_app  app JOIN app_info info on(app.app_id = info.app_id) JOIN apc_app_appgroup ON( app.app_id = apc_app_appgroup.app_id AND apc_app_appgroup.status_flag > 0) WHERE apc_app_appgroup.appgroup_app_id = " + parentAppId + " and info.status_flag = 1 and app.status_flag= 1 and app.appcenter_include = 1 and app.portal_id = "+BookInit.getInstance().getPortalId()+"  ORDER BY apc_app_appgroup.display_order";
                } else {
                    sql = "SELECT distinct  apc_app_appgroup.appgroup_app_id AS app_group_id,app.display_title,app.app_id,app.appcenter_remove,info.app_code,info.app_name,info.app_shortname,info.app_type,info.app_logo,info.app_desc,info.comp_code,info.plugin_code,info.parent_app_id,app.picture_disabled,app.picture_selected,app.picture_normal,info.parent_appgroup_app_id " +
                            " FROM portal_app  app LEFT JOIN user_apc_portal_app  ON(app.app_id = user_apc_portal_app.app_id) JOIN app_info info on(app.app_id = info.app_id) JOIN apc_app_appgroup ON( app.app_id = apc_app_appgroup.app_id AND apc_app_appgroup.status_flag > 0) WHERE apc_app_appgroup.appgroup_app_id = " + parentAppId + " and (user_apc_portal_app.user_using = 1 or user_apc_portal_app.user_using is null ) and info.status_flag = 1 and app.status_flag= 1 and app.appcenter_include = 1 and app.portal_id = "+BookInit.getInstance().getPortalId()+" and (user_apc_portal_app.portal_id = " + BookInit.getInstance().getPortalId() + " or user_apc_portal_app.portal_id is null ) ORDER BY user_apc_portal_app.display_order";
                    cursor = null;
                }
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    AppInfo mAppInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String app_code = cursor.getString(cursor.getColumnIndex("app_code"));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String app_shortname = cursor.getString(cursor.getColumnIndex("app_shortname"));
                    int app_type = cursor.getInt(cursor.getColumnIndex("app_type"));
                    String app_logo = cursor.getString(cursor.getColumnIndex("app_logo"));
                    String app_desc = cursor.getString(cursor.getColumnIndex("app_desc"));
                    String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
                    String plugin_code = cursor.getString(cursor.getColumnIndex("plugin_code"));
                    long parent_app_id = cursor.getLong(cursor.getColumnIndex("parent_app_id"));
                    long current_version = getCurrentVersion(app_id);
                    String picture_disabled = cursor.getString(cursor.getColumnIndex("picture_disabled"));
                    String picture_selected = cursor.getString(cursor.getColumnIndex("picture_selected"));
                    String picture_normal = cursor.getString(cursor.getColumnIndex("picture_normal"));
                    long parent_appgroup_app_id = cursor.getLong(cursor.getColumnIndex("app_group_id"));
                    int appcenter_remove = cursor.getInt(cursor.getColumnIndex("appcenter_remove"));
                    try{
                        String display_title = cursor.getString(cursor.getColumnIndex("display_title"));
                        mAppInfo.setDisplay_title(display_title);
                    }catch (Exception e){
//                        e.printStackTrace();
                    }
                    mAppInfo.setAppcenter_remove(appcenter_remove);
                    mAppInfo.setApp_id(app_id);
                    mAppInfo.setApp_code(app_code);
                    mAppInfo.setApp_name(app_name);
                    mAppInfo.setApp_shortname(app_shortname);
                    mAppInfo.setApp_type(app_type);
                    mAppInfo.setApp_logo(app_logo);
                    mAppInfo.setApp_desc(app_desc);
                    mAppInfo.setPlugin_code(plugin_code);
                    mAppInfo.setComp_code(comp_code);
                    mAppInfo.setParent_app_id(parent_app_id);
                    mAppInfo.setCurrent_version(current_version);
                    mAppInfo.setPicture_disabled(picture_disabled);
                    mAppInfo.setPicture_normal(picture_normal);
                    mAppInfo.setParent_appgroup_app_id(parent_appgroup_app_id);
                    mAppInfo.setPicture_selected(picture_selected);
                    max_version_number = 0;
                    ArrayList<AppVersion> mAppVersionList = getVersion(app_id + "");
                    if (app_type == 7) {
                        mAppInfo.setClassifyAppInfo(selectClassify("" + app_id));
                    }
                    for (AppVersion mAppVersion : mAppVersionList) {
                        if (current_version > 0) {
                            if (max_version_number == current_version && mAppVersion.getVersion_number() == current_version) {
                                mAppInfo.setApk_flag(3);
                                mAppInfo.setmAppVersion(mAppVersion);
                            } else {
                                if (max_version_number > current_version && mAppVersion.getVersion_number() == max_version_number) {
                                    mAppInfo.setApk_flag(1);
//                                    if(mAppInfo.getmAppVersion() == null){
                                    mAppInfo.setmAppVersion(mAppVersion);
//                                    }
                                } else if (mAppVersion.getVersion_number() == current_version) {
                                    mAppInfo.setApk_flag(3);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                }
                            }
                        } else {
                            if (mAppInfo.getApp_type() == 3) {
                                mAppInfo.setApk_flag(1);
////                                mAppVersion.setMustupdated(1);
                            }
                            mAppInfo.setmAppVersion(mAppVersion);
                        }

//                        else if (max_version_number == mAppVersion.getVersion_number()){
//                            mAppInfo.setApk_flag(1);
//                            mAppInfo.setmAppVersion(mAppVersion);
//                        }
                    }

                    mAppInfo.setMax_version_number(max_version_number);
                    mAppInfo.setmAppVersionList(mAppVersionList);
                    mAppInfoList.add(mAppInfo);
                }
                cursor.close();
            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        mAppInfoList = refresh(mAppInfoList, true, null);
        return mAppInfoList;
    }


    /**
     * 设置当前所有门户为不可用
     */

    /**
     * 切换门户
     *
     * @return
     */
    public void switchPortalAll() {
        String sql = "update emp_portal set is_default = -1,status_flag = -1";
        if (db.isOpen()) {
            try {

                db.execSQL(sql);

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
            }
        }

    }

    /**
     * 获取左侧信息
     *
     * @return
     */
    public ArrayList<AppInfo> getLeftAppInfo() {
        ArrayList<AppInfo> mAppInfoList = new ArrayList<AppInfo>();
        String portal_id = BookInit.getInstance().getPortalId();
        String sql = "SELECT * FROM portal_left_menu pLeft LEFT JOIN app_info info on (pLeft.app_id = info.app_id AND pLeft.status_flag > 0) AND pLeft.portal_id = " + portal_id + " where pLeft.status_flag > 0 AND info.status_flag > 0 ORDER BY pLeft.display_order";
        if (db.isOpen()) {
            Cursor cursor = null;
            try {

                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    AppInfo mAppInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String app_code = cursor.getString(cursor.getColumnIndex("app_code"));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String app_shortname = cursor.getString(cursor.getColumnIndex("app_shortname"));
                    int app_type = cursor.getInt(cursor.getColumnIndex("app_type"));
                    String app_logo = cursor.getString(cursor.getColumnIndex("app_logo"));
                    String app_desc = cursor.getString(cursor.getColumnIndex("app_desc"));
                    String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
                    String plugin_code = cursor.getString(cursor.getColumnIndex("plugin_code"));
                    long parent_app_id = cursor.getLong(cursor.getColumnIndex("parent_app_id"));
                    long current_version = getCurrentVersion(app_id);
                    String picture_disabled = cursor.getString(cursor.getColumnIndex("picture_disabled"));
                    String picture_selected = cursor.getString(cursor.getColumnIndex("picture_selected"));
                    String picture_normal = cursor.getString(cursor.getColumnIndex("picture_normal"));
                    try{
                        String display_title = cursor.getString(cursor.getColumnIndex("display_title"));
                        mAppInfo.setDisplay_title(display_title);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    mAppInfo.setApp_id(app_id);
                    mAppInfo.setApp_code(app_code);
                    mAppInfo.setApp_name(app_name);
                    mAppInfo.setApp_shortname(app_shortname);
                    mAppInfo.setApp_type(app_type);
                    mAppInfo.setApp_logo(app_logo);
                    mAppInfo.setApp_desc(app_desc);
                    mAppInfo.setPlugin_code(plugin_code);
                    mAppInfo.setComp_code(comp_code);
                    mAppInfo.setParent_app_id(parent_app_id);
                    mAppInfo.setCurrent_version(current_version);
                    mAppInfo.setPicture_disabled(picture_disabled);
                    mAppInfo.setPicture_normal(picture_normal);
                    mAppInfo.setPicture_selected(picture_selected);
                    max_version_number = 0;
                    ArrayList<AppVersion> mAppVersionList = getVersion(app_id + "");

                    for (AppVersion mAppVersion : mAppVersionList) {
                        if (current_version > 0) {
                            if (max_version_number == current_version) {
                                mAppInfo.setApk_flag(3);
                                mAppInfo.setmAppVersion(mAppVersion);
                            } else {
                                if (max_version_number > current_version) {
                                    mAppInfo.setApk_flag(1);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                } else {
                                    mAppInfo.setApk_flag(3);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                }

                            }
                        } else {
                            if (mAppInfo.getApp_type() == 3) {
                                mAppInfo.setApk_flag(1);
//                                mAppVersion.setMustupdated(1);
                            }
                            mAppInfo.setmAppVersion(mAppVersion);
                        }

//                        else if (max_version_number == mAppVersion.getVersion_number()){
//                            mAppInfo.setApk_flag(1);
//                            mAppInfo.setmAppVersion(mAppVersion);
//                        }
                    }

                    mAppInfo.setMax_version_number(max_version_number);
                    mAppInfo.setmAppVersionList(mAppVersionList);
                    mAppInfoList.add(mAppInfo);
                }
                cursor.close();
            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return mAppInfoList;
    }


    /**
     * 获取底部信息
     */
    public ArrayList<AppInfo> getButtomInfo() {
        ArrayList<AppInfo> mAppInfoList = new ArrayList<AppInfo>();
        String portal_id = BookInit.getInstance().getPortalId();
        String sql = "SELECT distinct  * FROM portal_tab tab LEFT JOIN app_info info on (tab.app_id = info.app_id AND tab.status_flag > 0) AND tab.portal_id = " + portal_id + " Where tab.status_flag > 0 AND info.status_flag > 0 ORDER BY tab.display_order";
        if (db.isOpen()) {
            Cursor cursor = null;
            try {

                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    AppInfo mAppInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String app_code = cursor.getString(cursor.getColumnIndex("app_code"));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String app_shortname = cursor.getString(cursor.getColumnIndex("app_shortname"));
                    int app_type = cursor.getInt(cursor.getColumnIndex("app_type"));
                    String app_logo = cursor.getString(cursor.getColumnIndex("app_logo"));
                    String app_desc = cursor.getString(cursor.getColumnIndex("app_desc"));
                    String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
                    String plugin_code = cursor.getString(cursor.getColumnIndex("plugin_code"));
                    long parent_app_id = cursor.getLong(cursor.getColumnIndex("parent_app_id"));
                    long current_version = getCurrentVersion(app_id);
                    String picture_disabled = cursor.getString(cursor.getColumnIndex("picture_disabled"));
                    String picture_selected = cursor.getString(cursor.getColumnIndex("picture_selected"));
                    String picture_normal = cursor.getString(cursor.getColumnIndex("picture_normal"));
                    long tab_item_id = cursor.getLong(cursor.getColumnIndex("tab_item_id"));
                    try{
                        String display_title = cursor.getString(cursor.getColumnIndex("display_title"));
                        mAppInfo.setDisplay_title(display_title);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mAppInfo.setTab_item_id(tab_item_id);
                    mAppInfo.setApp_id(app_id);
                    mAppInfo.setApp_code(app_code);
                    mAppInfo.setApp_name(app_name);
                    mAppInfo.setApp_shortname(app_shortname);
                    mAppInfo.setApp_type(app_type);
                    mAppInfo.setApp_logo(app_logo);
                    mAppInfo.setApp_desc(app_desc);
                    mAppInfo.setPlugin_code(plugin_code);
                    mAppInfo.setComp_code(comp_code);
                    mAppInfo.setParent_app_id(parent_app_id);
                    mAppInfo.setCurrent_version(current_version);
                    mAppInfo.setPicture_disabled(picture_disabled);
                    mAppInfo.setPicture_normal(picture_normal);
                    mAppInfo.setPicture_selected(picture_selected);
                    max_version_number = 0;
                    ArrayList<AppVersion> mAppVersionList = getVersion(app_id + "");

                    for (AppVersion mAppVersion : mAppVersionList) {
                        if (current_version > 0) {
                            if (max_version_number == current_version) {
                                mAppInfo.setApk_flag(3);
                                mAppInfo.setmAppVersion(mAppVersion);
                            } else {
                                if (max_version_number > current_version) {
                                    mAppInfo.setApk_flag(1);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                } else {
                                    mAppInfo.setApk_flag(3);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                }
                            }
                        } else {
                            if (mAppInfo.getApp_type() == 3) {
                                mAppInfo.setApk_flag(3);
//                                mAppVersion.setMustupdated(1);
                            }
                            mAppInfo.setmAppVersion(mAppVersion);
                        }

//                        else if (max_version_number == mAppVersion.getVersion_number()){
//                            mAppInfo.setApk_flag(1);
//                            mAppInfo.setmAppVersion(mAppVersion);
//                        }
                    }

                    mAppInfo.setMax_version_number(max_version_number);
                    mAppInfo.setmAppVersionList(mAppVersionList);
                    mAppInfoList.add(mAppInfo);
                }
                cursor.close();
            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return mAppInfoList;
    }

    /**
     * 获取右上角的
     */

    public ArrayList<AppInfo> getToRightInfo(String tab_item_id) {


        ArrayList<AppInfo> mAppInfoList = new ArrayList<AppInfo>();
        String portal_id = BookInit.getInstance().getPortalId();
        String sql = "SELECT * FROM portal_righttop_menu LEFT JOIN app_info on (portal_righttop_menu.app_id = app_info.app_id)LEFT JOIN portal_tab_menu on (portal_righttop_menu.righttop_item_id = portal_tab_menu.righttop_item_id) WHERE (portal_righttop_menu.status_flag > 0 AND portal_tab_menu.tab_item_id = " + tab_item_id + "  AND portal_righttop_menu.portal_id = " + portal_id + " AND portal_tab_menu.display > 0) ORDER BY portal_righttop_menu.display_order";
        if (db.isOpen()) {
            Cursor cursor = null;
            try {

                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    AppInfo mAppInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String app_code = cursor.getString(cursor.getColumnIndex("app_code"));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String app_shortname = cursor.getString(cursor.getColumnIndex("app_shortname"));
                    int app_type = cursor.getInt(cursor.getColumnIndex("app_type"));
                    String app_logo = cursor.getString(cursor.getColumnIndex("app_logo"));
                    String app_desc = cursor.getString(cursor.getColumnIndex("app_desc"));
                    String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
                    String plugin_code = cursor.getString(cursor.getColumnIndex("plugin_code"));
                    long parent_app_id = cursor.getLong(cursor.getColumnIndex("parent_app_id"));
                    long current_version = getCurrentVersion(app_id);
                    String picture_disabled = cursor.getString(cursor.getColumnIndex("picture_disabled"));
                    String picture_selected = cursor.getString(cursor.getColumnIndex("picture_selected"));
                    String picture_normal = cursor.getString(cursor.getColumnIndex("picture_normal"));
                    try{
                        String display_title = cursor.getString(cursor.getColumnIndex("display_title"));
                        mAppInfo.setDisplay_title(display_title);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mAppInfo.setApp_id(app_id);
                    mAppInfo.setApp_code(app_code);
                    mAppInfo.setApp_name(app_name);
                    mAppInfo.setApp_shortname(app_shortname);
                    mAppInfo.setApp_type(app_type);
                    mAppInfo.setApp_logo(app_logo);
                    mAppInfo.setApp_desc(app_desc);
                    mAppInfo.setPlugin_code(plugin_code);
                    mAppInfo.setComp_code(comp_code);
                    mAppInfo.setParent_app_id(parent_app_id);
                    mAppInfo.setCurrent_version(current_version);
                    mAppInfo.setPicture_disabled(picture_disabled);
                    mAppInfo.setPicture_normal(picture_normal);
                    mAppInfo.setPicture_selected(picture_selected);
                    max_version_number = 0;
                    ArrayList<AppVersion> mAppVersionList = getVersion(app_id + "");

                    for (AppVersion mAppVersion : mAppVersionList) {
                        if (current_version > 0) {
                            if (max_version_number == current_version) {
                                mAppInfo.setApk_flag(3);
                                mAppInfo.setmAppVersion(mAppVersion);
                            } else {
                                if (max_version_number > current_version) {
                                    mAppInfo.setApk_flag(1);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                } else {
                                    mAppInfo.setApk_flag(3);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                }
                            }
                        } else {
                            if (mAppInfo.getApp_type() == 3) {
                                mAppInfo.setApk_flag(3);
//                                mAppVersion.setMustupdated(1);
                            }
                            mAppInfo.setmAppVersion(mAppVersion);
                        }

//                        else if (max_version_number == mAppVersion.getVersion_number()){
//                            mAppInfo.setApk_flag(1);
//                            mAppInfo.setmAppVersion(mAppVersion);
//                        }
                    }

                    mAppInfo.setMax_version_number(max_version_number);
                    mAppInfo.setmAppVersionList(mAppVersionList);
                    mAppInfoList.add(mAppInfo);
                }
                cursor.close();
            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return mAppInfoList;
    }


    /**
     * 根据appId 获取AppInfo
     *
     * @param appId
     * @return
     */
    public AppInfo getAppInfo(String appId) {
        String sql = "SELECT * FROM portal_app app JOIN app_info on(app.app_id = app_info.app_id) where app.app_id = '" + appId + "' AND app.status_flag > 0 AND app.portal_id = "+BookInit.getInstance().getPortalId()+" AND app_info.status_flag > 0 AND app.status_flag > 0";
        AppInfo mAppInfo = null;
        if (db.isOpen()) {
            Cursor cursor = null;
            try {

                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    mAppInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String app_code = cursor.getString(cursor.getColumnIndex("app_code"));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String app_shortname = cursor.getString(cursor.getColumnIndex("app_shortname"));
                    int app_type = cursor.getInt(cursor.getColumnIndex("app_type"));
                    String app_logo = cursor.getString(cursor.getColumnIndex("app_logo"));
                    String app_desc = cursor.getString(cursor.getColumnIndex("app_desc"));
                    String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
                    String plugin_code = cursor.getString(cursor.getColumnIndex("plugin_code"));
                    long parent_app_id = cursor.getLong(cursor.getColumnIndex("parent_app_id"));
                    long current_version = getCurrentVersion(app_id);

                    mAppInfo.setApp_id(app_id);
                    mAppInfo.setApp_code(app_code);
                    mAppInfo.setApp_name(app_name);
                    mAppInfo.setApp_shortname(app_shortname);
                    mAppInfo.setApp_type(app_type);
                    mAppInfo.setApp_logo(app_logo);
                    mAppInfo.setApp_desc(app_desc);
                    mAppInfo.setPlugin_code(plugin_code);
                    mAppInfo.setComp_code(comp_code);
                    mAppInfo.setParent_app_id(parent_app_id);
                    mAppInfo.setCurrent_version(current_version);
                    max_version_number = 0;
                    ArrayList<AppVersion> mAppVersionList = getVersion(app_id + "");

                    for (AppVersion mAppVersion : mAppVersionList) {
                        if (current_version > 0) {
                            if (max_version_number == current_version && mAppVersion.getVersion_number() == current_version) {
                                mAppInfo.setApk_flag(3);
                                mAppInfo.setmAppVersion(mAppVersion);
                            } else {
                                if (max_version_number > current_version && mAppVersion.getVersion_number() == max_version_number) {
                                    mAppInfo.setApk_flag(1);
//                                    if(mAppInfo.getmAppVersion() == null){
                                    mAppInfo.setmAppVersion(mAppVersion);
//                                    }
                                } else if (mAppVersion.getVersion_number() == current_version) {
                                    mAppInfo.setApk_flag(3);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                }
                            }
                        } else {
                            if (mAppInfo.getApp_type() == 3) {
                                mAppInfo.setApk_flag(3);
//                                mAppVersion.setMustupdated(1);
                            }
                            mAppInfo.setmAppVersion(mAppVersion);
                        }

//                        else if (max_version_number == mAppVersion.getVersion_number()){
//                            mAppInfo.setApk_flag(1);
//                            mAppInfo.setmAppVersion(mAppVersion);
//                        }
                    }

                    mAppInfo.setMax_version_number(max_version_number);
                    mAppInfo.setmAppVersionList(mAppVersionList);
                }
                cursor.close();
            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return mAppInfo;
    }


    /**
     * 根据appCode 获取AppInfo
     *
     * @param appCode
     * @return
     */
    public AppInfo getAppInfoByAppCode(String appCode) {
        String sql = "SELECT * FROM portal_app app JOIN app_info on(app.app_id = app_info.app_id) where app_info.app_code = '" + appCode + "' AND app.status_flag > 0 AND app.portal_id = "+BookInit.getInstance().getPortalId()+" AND app_info.status_flag > 0 AND app.status_flag > 0";
        AppInfo mAppInfo = null;
        if (db.isOpen()) {
            Cursor cursor = null;
            try {

                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    mAppInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String app_code = cursor.getString(cursor.getColumnIndex("app_code"));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String app_shortname = cursor.getString(cursor.getColumnIndex("app_shortname"));
                    int app_type = cursor.getInt(cursor.getColumnIndex("app_type"));
                    String app_logo = cursor.getString(cursor.getColumnIndex("app_logo"));
                    String app_desc = cursor.getString(cursor.getColumnIndex("app_desc"));
                    String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
                    String plugin_code = cursor.getString(cursor.getColumnIndex("plugin_code"));
                    long parent_app_id = cursor.getLong(cursor.getColumnIndex("parent_app_id"));
                    long current_version = getCurrentVersion(app_id);

                    mAppInfo.setApp_id(app_id);
                    mAppInfo.setApp_code(app_code);
                    mAppInfo.setApp_name(app_name);
                    mAppInfo.setApp_shortname(app_shortname);
                    mAppInfo.setApp_type(app_type);
                    mAppInfo.setApp_logo(app_logo);
                    mAppInfo.setApp_desc(app_desc);
                    mAppInfo.setPlugin_code(plugin_code);
                    mAppInfo.setComp_code(comp_code);
                    mAppInfo.setParent_app_id(parent_app_id);
                    mAppInfo.setCurrent_version(current_version);
                    max_version_number = 0;
                    ArrayList<AppVersion> mAppVersionList = getVersion(app_id + "");

                    for (AppVersion mAppVersion : mAppVersionList) {
                        if (current_version > 0) {
                            if (max_version_number == current_version && mAppVersion.getVersion_number() == current_version) {
                                mAppInfo.setApk_flag(3);
                                mAppInfo.setmAppVersion(mAppVersion);
                            } else {
                                if (max_version_number > current_version && mAppVersion.getVersion_number() == max_version_number) {
                                    mAppInfo.setApk_flag(1);
//                                    if(mAppInfo.getmAppVersion() == null){
                                    mAppInfo.setmAppVersion(mAppVersion);
//                                    }
                                } else if (mAppVersion.getVersion_number() == current_version) {
                                    mAppInfo.setApk_flag(3);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                }
                            }
                        } else {
                            if (mAppInfo.getApp_type() == 3) {
                                mAppInfo.setApk_flag(3);
//                                mAppVersion.setMustupdated(1);
                            }
                            mAppInfo.setmAppVersion(mAppVersion);
                        }

//                        else if (max_version_number == mAppVersion.getVersion_number()){
//                            mAppInfo.setApk_flag(1);
//                            mAppInfo.setmAppVersion(mAppVersion);
//                        }
                    }

                    mAppInfo.setMax_version_number(max_version_number);
                    mAppInfo.setmAppVersionList(mAppVersionList);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return mAppInfo;
    }


    /**
     * 获取应用中心的
     *
     * @return
     */
    public ArrayList<AppInfo> getApplicationCenterInfo() {
        String portal_id = BookInit.getInstance().getPortalId();
//        ArrayList<AppInfo> classifAppInfo = getAPCNotInAppId();
//        for(AppInfo mApp : classifAppInfo){
//            repliceAPC_Portal(Long.parseLong(portal_id),mApp.getApp_id(),mApp.getAppcenter_diplay_order());
//        }
        //判断自定义表中是否存在 只需要查询自定义表 不需要关联user_using这个属性即可
        String sql = "SELECT apc_app_appgroup.appgroup_app_id AS app_group_id , apc_app_appgroup.display_order AS app_group_display_order,user_apc_portal_app.*, \n" +
                "app_info.*, mobile_app_info.current_version, portal_app.* FROM portal_app LEFT JOIN user_apc_portal_app\n" +
                " ON( user_apc_portal_app.app_id = portal_app.app_id AND user_apc_portal_app.portal_id = portal_app.portal_id) \n" +
                " LEFT JOIN mobile_app_info ON( user_apc_portal_app.app_id = mobile_app_info.app_id) LEFT JOIN app_info \n" +
                " ON( user_apc_portal_app.app_id = app_info.app_id) LEFT JOIN apc_app_appgroup ON( \n" +
                " portal_app.app_id = apc_app_appgroup.app_id AND apc_app_appgroup.status_flag > 0) LEFT JOIN portal_app gpa ON gpa.portal_id = "+portal_id+" AND gpa.app_id = apc_app_appgroup.appgroup_app_id\n" +
                " WHERE( app_info.status_flag > 0 \n" +
                " AND user_apc_portal_app.status_flag > 0 AND portal_app.appcenter_include = 1 \n" +
                "AND portal_app.status_flag > 0 AND user_apc_portal_app.portal_id = "+portal_id+") \n" +
                " GROUP BY user_apc_portal_app.app_id ORDER BY user_apc_portal_app.display_order;";
        //注释这个sql 需要进行
//        String sql = "SELECT portal_app.status_flag, aag.appgroup_app_id AS app_group_id , user_apc_portal_app.display_order AS app_group_display_order , portal_app.appcenter_diplay_order, aag.display_order,\n" +
//                " portal_app.*, app_info.* FROM portal_app  \n" +
//                " LEFT JOIN app_info ON( portal_app.app_id = app_info.app_id AND app_info.status_flag > 0) \n" +
//                " LEFT JOIN (select a.appgroup_app_id ,a.app_id ,display_order  \n" +
//                "from  apc_app_appgroup a, portal_app p \n" +
//                "where p.app_id = a.appgroup_app_id AND p.status_flag > 0 and a.status_flag > 0)  aag \n" +
//                "ON (app_info.app_id = aag.app_id ) \n" +
//                "LEFT JOIN user_apc_portal_app ON(user_apc_portal_app.user_using = 1 AND user_apc_portal_app.status_flag > 0 AND user_apc_portal_app.portal_id = 4 AND user_apc_portal_app.app_id = portal_app.app_id)\n" +
//                " \n" +
//                " WHERE(portal_app.appcenter_include = 1  AND portal_app.status_flag > 0 AND portal_app.portal_id = "+portal_id+"  ) \n" +
//                "  ORDER BY  user_apc_portal_app.display_order , portal_app.appcenter_diplay_order, aag.display_order\n";
        int number = 0;
        ArrayList<AppInfo> appinfosList = new ArrayList<AppInfo>();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
//                sql = "SELECT aag.appgroup_app_id AS app_group_id , aag.display_order AS app_group_display_order ,\n" +
//                        " portal_app.*, app_info.*, mobile_app_info.current_version FROM portal_app \n" +
//                        " LEFT JOIN mobile_app_info ON( portal_app.app_id = mobile_app_info.app_id) \n" +
//                        " LEFT JOIN app_info ON( portal_app.app_id = app_info.app_id) \n" +
//                        " LEFT JOIN apc_app_appgroup aag ON (app_info.app_id = aag.app_id AND aag.status_flag > 0) \n" +
//                        " LEFT JOIN portal_app gpa ON gpa.portal_id = "+portal_id+" AND gpa.app_id = aag.appgroup_app_id \n" +
//                        " WHERE( app_info.status_flag > 0 AND portal_app.status_flag > 0 \n" +
//                        "AND portal_app.appcenter_include = 1 AND portal_app.portal_id = "+portal_id+") \n" +
//                        "GROUP BY portal_app.app_id ORDER BY portal_app.appcenter_diplay_order";
                sql = "SELECT portal_app.status_flag, aag.appgroup_app_id AS app_group_id , aag.display_order AS app_group_display_order ,\n" +
                        " portal_app.*, app_info.* FROM portal_app  \n" +
                        " LEFT JOIN app_info ON( portal_app.app_id = app_info.app_id AND app_info.status_flag > 0) \n" +
                        " LEFT JOIN (select a.appgroup_app_id ,a.app_id ,display_order  \n" +
                        "from  apc_app_appgroup a, portal_app p \n" +
                        "where p.app_id = a.appgroup_app_id AND p.status_flag > 0 and a.status_flag > 0)  aag \n" +
                        "ON (app_info.app_id = aag.app_id ) \n" +
                        " \n" +
                        " WHERE(portal_app.appcenter_include = 1  AND portal_app.status_flag > 0 AND portal_app.portal_id = "+portal_id+"  )  ORDER BY portal_app.appcenter_diplay_order\n";
                number = cursor.getCount();
                cursor.close();
                //如果自定义中为0的话 那么从portal_app中获取
                if (number == 0) {
                    cursor = null;
                    cursor = db.rawQuery(sql, null);
                } else {
                    sql = "SELECT apc_app_appgroup.appgroup_app_id AS app_group_id , apc_app_appgroup.display_order AS app_group_display_order,user_apc_portal_app.*, \n" +
                            "app_info.*, mobile_app_info.current_version, portal_app.* FROM portal_app LEFT JOIN user_apc_portal_app\n" +
                            " ON( user_apc_portal_app.app_id = portal_app.app_id AND user_apc_portal_app.portal_id = portal_app.portal_id) \n" +
                            " LEFT JOIN mobile_app_info ON( user_apc_portal_app.app_id = mobile_app_info.app_id) LEFT JOIN app_info \n" +
                            " ON( user_apc_portal_app.app_id = app_info.app_id) LEFT JOIN apc_app_appgroup ON( \n" +
                            " portal_app.app_id = apc_app_appgroup.app_id AND apc_app_appgroup.status_flag > 0) LEFT JOIN portal_app gpa ON gpa.portal_id = "+portal_id+" AND gpa.app_id = apc_app_appgroup.appgroup_app_id\n" +
                            " WHERE( app_info.status_flag > 0 \n" +
                            " AND user_apc_portal_app.status_flag > 0 AND portal_app.appcenter_include = 1 \n" +
                            " AND user_apc_portal_app.user_using = 1 AND portal_app.status_flag > 0 AND user_apc_portal_app.portal_id = "+portal_id+") \n" +
                            " GROUP BY user_apc_portal_app.app_id ORDER BY user_apc_portal_app.display_order;";
                    cursor = null;
                    cursor = db.rawQuery(sql, null);
                }
                while (cursor.moveToNext()) {
                    AppInfo mAppInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String app_code = cursor.getString(cursor.getColumnIndex("app_code"));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String app_shortname = cursor.getString(cursor.getColumnIndex("app_shortname"));
                    int app_type = cursor.getInt(cursor.getColumnIndex("app_type"));
                    String app_logo = cursor.getString(cursor.getColumnIndex("app_logo"));
                    String app_desc = cursor.getString(cursor.getColumnIndex("app_desc"));
                    String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
                    String plugin_code = cursor.getString(cursor.getColumnIndex("plugin_code"));
                    long parent_app_id = cursor.getLong(cursor.getColumnIndex("parent_app_id"));
                    long current_version = getCurrentVersion(app_id);
                    String picture_normal = cursor.getString(cursor.getColumnIndex("picture_normal"));
                    int appcenter_remove = cursor.getInt(cursor.getColumnIndex("appcenter_remove"));
                    long parent_appgroup_app_id = cursor.getLong(cursor.getColumnIndex("app_group_id"));
                    int appcenter_diplay_order = cursor.getInt(cursor.getColumnIndex("appcenter_diplay_order"));
                    int appcenter_include_atfirst = cursor.getInt(cursor.getColumnIndex("appcenter_include_atfirst"));
                    mAppInfo.setAppcenter_include_atfirst(appcenter_include_atfirst);
                    try{
                        String display_title = cursor.getString(cursor.getColumnIndex("display_title"));
                        mAppInfo.setDisplay_title(display_title);
                    }catch (Exception e){
//                        e.printStackTrace();
                    }
                    if(app_id == 0){
                        continue;
                    }
                    mAppInfo.setAppcenter_diplay_order(appcenter_diplay_order);
                    mAppInfo.setAppcenter_remove(appcenter_remove);
                    mAppInfo.setPicture_normal(picture_normal);
                    mAppInfo.setApp_id(app_id);
                    mAppInfo.setApp_code(app_code);
                    mAppInfo.setApp_name(app_name);
                    mAppInfo.setApp_shortname(app_shortname);
                    mAppInfo.setApp_type(app_type);
                    mAppInfo.setApp_logo(app_logo);
                    mAppInfo.setApp_desc(app_desc);
                    mAppInfo.setPlugin_code(plugin_code);
                    mAppInfo.setComp_code(comp_code);
                    mAppInfo.setParent_app_id(parent_app_id);
                    mAppInfo.setParent_appgroup_app_id(parent_appgroup_app_id);
                    mAppInfo.setCurrent_version(current_version);
                    Log.d("applicationcenterdao", "当前版本是：" + current_version + "");
                    max_version_number = 0;
                    ArrayList<AppVersion> mAppVersionList = getVersion(app_id + "");
                    if (app_type == 7) {
                        mAppInfo.setClassifyAppInfo(selectClassify("" + app_id));
                    }
                    for (AppVersion mAppVersion : mAppVersionList) {
                        if (current_version > 0) {
                            if (max_version_number == current_version && mAppVersion.getVersion_number() == current_version) {
                                mAppInfo.setApk_flag(3);
                                mAppInfo.setmAppVersion(mAppVersion);
                            } else {
                                if (max_version_number > current_version && mAppVersion.getVersion_number() == max_version_number) {
                                    mAppInfo.setApk_flag(1);
//                                    if(mAppInfo.getmAppVersion() == null){
                                    mAppInfo.setmAppVersion(mAppVersion);
//                                    }
                                } else if (mAppVersion.getVersion_number() == current_version) {
                                    mAppInfo.setApk_flag(3);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                }
                            }
                        } else {
                            if (mAppInfo.getApp_type() == 3) {
                                mAppInfo.setApk_flag(1);
////                                mAppVersion.setMustupdated(1);
                            }
                            mAppInfo.setmAppVersion(mAppVersion);
                        }

//                        else if (max_version_number == mAppVersion.getVersion_number()){
//                            mAppInfo.setApk_flag(1);
//                            mAppInfo.setmAppVersion(mAppVersion);
//                        }
                    }

                    mAppInfo.setMax_version_number(max_version_number);
                    mAppInfo.setmAppVersionList(mAppVersionList);
                    if(mAppInfo.getApp_type() == 7 && mAppInfo.getClassifyAppInfo().size() == 0){

                    }else{
                        appinfosList.add(mAppInfo);
                    }

//                    if(mAppInfo.getmAppVersion() != null){ //设置 当前版本必须有值的前提下才可进行放入应用中心中
//                        appinfosList.add(mAppInfo);
//                    }

                }


            } catch (Exception e) {

            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        if (number == 0)
            appinfosList = refresh(appinfosList, true, getAllClassifyAppInfo());
        else
            appinfosList = refresh(appinfosList, false, getAllClassifyAppInfo());
//        refreshPortal(appinfosList);
//        refresh(appinfosList);
        return appinfosList;
    }

    public ArrayList<AppInfo> refresh(ArrayList<AppInfo> appCenterAppInfo, boolean flag, ArrayList<AppInfo> allClassify) {
        boolean isClassify = false;
        ArrayList<AppInfo> tempClassify = new ArrayList<AppInfo>();
        for (int i = 0; i < appCenterAppInfo.size(); i++) {
            if (appCenterAppInfo.get(i).getApp_type() == 7) {
                tempClassify.add(appCenterAppInfo.get(i));
                if(appCenterAppInfo.get(i).getAppcenter_include_atfirst() == 1){

                    isClassify = true;
                }
            }
        }
//        if(flag){
//            for(int i = 0; i < appCenterAppInfo.size() ; i++){
//                if(appCenterAppInfo.get(i).getApp_type() == 7){
//                    tempClassify.add(appCenterAppInfo.get(i));
//                }
//            }
//        }else{
//            tempClassify = getAllClassifyAppInfo();
//        }
        if (allClassify != null)
            tempClassify.addAll(allClassify);

        if (allClassify != null && allClassify.size() != 0 && !isClassify)
            for (AppInfo temp : tempClassify) {
                appCenterAppInfo.remove(temp);
            }

        int fristIndex = -1;
        for (AppInfo mAppInfo : tempClassify) {
            for (int i = 0; i < appCenterAppInfo.size(); i++) {
                if (mAppInfo.getApp_id() == appCenterAppInfo.get(i).getParent_appgroup_app_id()) {
                    fristIndex = i;
                    break;
                }
            }
            remove(mAppInfo.getApp_id(), appCenterAppInfo, mAppInfo, fristIndex);
        }

        ArrayList<AppInfo> tempAppInfo = new ArrayList<AppInfo>(appCenterAppInfo);
        for (int i = 0; i < tempAppInfo.size(); i++) {
            for (AppInfo twoAppInfo : appCenterAppInfo) {

                if (tempAppInfo.get(i).getApp_type() == 7 && tempAppInfo.get(i).getParent_appgroup_app_id() == twoAppInfo.getApp_id()) {
                    tempAppInfo.remove(i);
                    i--;
                    break;
                }
            }
        }

        return tempAppInfo;
    }

    public void remove(long classifyAppId, ArrayList<AppInfo> appCenterAppInfo, AppInfo classifyAppInfo, int index) {
        for (int i = 0; i < appCenterAppInfo.size(); i++) {
            if (classifyAppId == appCenterAppInfo.get(i).getParent_appgroup_app_id()) {
                appCenterAppInfo.remove(i);
                i--;
            }
        }
//        if(classifyAppInfo.getClassifyAppInfo() == null || classifyAppInfo.getClassifyAppInfo().size() == 0){
//            classifyAppInfo.setClassifyAppInfo(selectClassify("" + classifyAppInfo.getApp_id()));
//        }
        if (!appCenterAppInfo.contains(classifyAppInfo) && classifyAppInfo.getClassifyAppInfo() != null && classifyAppInfo.getClassifyAppInfo().size() > 0) {
            if (index != -1 && appCenterAppInfo.size() > index) {
                appCenterAppInfo.add(index, classifyAppInfo);
            } else {
                appCenterAppInfo.add(classifyAppInfo);
            }

        }


//        if(isRemove)
//            remove(classifyAppId,appCenterAppInfo,classifyAppInfo,index);
//        else if(classifyAppInfo.getClassifyAppInfo() != null && classifyAppInfo.getClassifyAppInfo().size() > 0){
//            appCenterAppInfo.add(index,classifyAppInfo);
//        }

    }

    /**
     * 获取所有分类的
     *
     * @return
     */
    public ArrayList<AppInfo> getAllClassifyAppInfo() {
        ArrayList<AppInfo> mAppInfoList = new ArrayList<AppInfo>();
        String portal_id = BookInit.getInstance().getPortalId();
        String sql = "";

        sql = "SELECT * FROM portal_app JOIN app_info ON( portal_app.app_id = app_info.app_id) WHERE app_info.app_id NOT IN( SELECT app_info.app_id FROM user_apc_portal_app LEFT JOIN app_info ON( user_apc_portal_app.app_id = app_info.app_id) WHERE app_info.app_type = 7 AND app_info.status_flag > 0 AND user_apc_portal_app.portal_id = " + BookInit.getInstance().getPortalId() + ") AND app_info.app_type = 7 AND app_info.status_flag > 0 AND portal_app.status_flag > 0 AND portal_app.portal_id = " + BookInit.getInstance().getPortalId();

        if (db.isOpen()) {
            Cursor cursor = null;
            try {

                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    AppInfo mAppInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String app_code = cursor.getString(cursor.getColumnIndex("app_code"));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String app_shortname = cursor.getString(cursor.getColumnIndex("app_shortname"));
                    int app_type = cursor.getInt(cursor.getColumnIndex("app_type"));
                    String app_logo = cursor.getString(cursor.getColumnIndex("app_logo"));
                    String app_desc = cursor.getString(cursor.getColumnIndex("app_desc"));
                    String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
                    String plugin_code = cursor.getString(cursor.getColumnIndex("plugin_code"));
                    long parent_app_id = cursor.getLong(cursor.getColumnIndex("parent_app_id"));
                    long parent_appgroup_app_id = cursor.getLong(cursor.getColumnIndex("parent_appgroup_app_id"));
                    long current_version = getCurrentVersion(app_id);
                    String picture_normal = cursor.getString(cursor.getColumnIndex("picture_normal"));
                    int type_flag = cursor.getInt(cursor.getColumnIndex("type_flag"));
                    if (type_flag == -1) {
                        type_flag = cursor.getInt(cursor.getColumnIndex("default_showinparent_flag"));
                    }
                    int appcenter_remove = cursor.getInt(cursor.getColumnIndex("appcenter_remove"));
                    mAppInfo.setAppcenter_remove(appcenter_remove);
                    int appcenter_include_atfirst = cursor.getInt(cursor.getColumnIndex("appcenter_include_atfirst"));
                    mAppInfo.setAppcenter_include_atfirst(appcenter_include_atfirst);
                    mAppInfo.setType_flag(type_flag);
                    mAppInfo.setPicture_normal(picture_normal);
                    mAppInfo.setApp_id(app_id);
                    mAppInfo.setApp_code(app_code);
                    mAppInfo.setApp_name(app_name);
                    mAppInfo.setApp_shortname(app_shortname);
                    mAppInfo.setApp_type(app_type);
                    mAppInfo.setApp_logo(app_logo);
                    mAppInfo.setApp_desc(app_desc);
                    mAppInfo.setParent_appgroup_app_id(parent_appgroup_app_id);
                    mAppInfo.setPlugin_code(plugin_code);
                    mAppInfo.setComp_code(comp_code);
                    mAppInfo.setParent_app_id(parent_app_id);
                    mAppInfo.setCurrent_version(current_version);
                    max_version_number = 0;
                    if (app_type == 7) {
                        mAppInfo.setClassifyAppInfo(selectClassify("" + app_id));
                    }
                    ArrayList<AppVersion> mAppVersionList = getVersion(app_id + "");

                    for (AppVersion mAppVersion : mAppVersionList) {
                        if (current_version > 0) {
                            if (max_version_number == current_version) {
                                mAppInfo.setApk_flag(3);
                                mAppInfo.setmAppVersion(mAppVersion);
                            } else {
                                if (max_version_number > current_version) {
                                    mAppInfo.setApk_flag(1);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                } else {
                                    mAppInfo.setApk_flag(3);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                }
                            }
                        } else {
                            if (mAppInfo.getApp_type() == 3) {
                                mAppInfo.setApk_flag(3);
//                                mAppVersion.setMustupdated(1);
                            }
                            mAppInfo.setmAppVersion(mAppVersion);
                        }

//                        else if (max_version_number == mAppVersion.getVersion_number()){
//                            mAppInfo.setApk_flag(1);
//                            mAppInfo.setmAppVersion(mAppVersion);
//                        }
                    }
                    mAppInfoList.add(mAppInfo);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return mAppInfoList;
    }

    public void updateChildAppOrder(ArrayList<AppInfo> appInfos){
        for(int i = 0;i < appInfos.size() ; i++){
            String sql = "update app_info set default_showinparent_flag="+(i+1)+" where app_id="+appInfos.get(i).getApp_id();
            db.execSQL(sql);
        }
    }

    /**
     * 获取代办列表中的子应用  获取子应用
     *
     * @param appId
     * @return
     */
    public ArrayList<AppInfo> getChildApp(String appId, boolean type_flags) {
        ArrayList<AppInfo> mAppInfoList = new ArrayList<AppInfo>();
        String portal_id = BookInit.getInstance().getPortalId();
        String sql = "";
//        if (type_flags) {
//            sql = "SELECT * FROM portal_app LEFT JOIN app_info on(app_info.app_id = portal_app.app_id) where portal_app.portal_id = '" + portal_id + "' AND app_info.parent_app_id = '" + appId + "' AND app_info.[status_flag] > 0 AND portal_app.[status_flag] > 0 AND app_info.type_flag = 1\n";
//        } else {
//           }

        sql = "SELECT * FROM portal_app LEFT JOIN app_info on(app_info.app_id = portal_app.app_id)  where portal_app.portal_id = '" + portal_id + "' AND app_info.parent_app_id = '" + appId + "' AND app_info.[status_flag] > 0 AND portal_app.[status_flag] > 0 ORDER BY app_info.default_showinparent_flag \n";

        if (db.isOpen()) {
            Cursor cursor = null;
            try {

                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    AppInfo mAppInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String app_code = cursor.getString(cursor.getColumnIndex("app_code"));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String app_shortname = cursor.getString(cursor.getColumnIndex("app_shortname"));
                    int app_type = cursor.getInt(cursor.getColumnIndex("app_type"));
                    String app_logo = cursor.getString(cursor.getColumnIndex("app_logo"));
                    String app_desc = cursor.getString(cursor.getColumnIndex("app_desc"));
                    String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
                    String plugin_code = cursor.getString(cursor.getColumnIndex("plugin_code"));
                    long parent_app_id = cursor.getLong(cursor.getColumnIndex("parent_app_id"));
                    long current_version = getCurrentVersion(app_id);
                    String picture_normal = cursor.getString(cursor.getColumnIndex("picture_normal"));
                    int type_flag = cursor.getInt(cursor.getColumnIndex("type_flag"));
                    if (type_flag == -1) {
                        type_flag = cursor.getInt(cursor.getColumnIndex("default_showinparent_flag"));
                    }
                    mAppInfo.setType_flag(type_flag);
                    mAppInfo.setPicture_normal(picture_normal);
                    mAppInfo.setApp_id(app_id);
                    mAppInfo.setApp_code(app_code);
                    mAppInfo.setApp_name(app_name);
                    mAppInfo.setApp_shortname(app_shortname);
                    mAppInfo.setApp_type(app_type);
                    mAppInfo.setApp_logo(app_logo);
                    mAppInfo.setApp_desc(app_desc);
                    mAppInfo.setPlugin_code(plugin_code);
                    mAppInfo.setComp_code(comp_code);
                    mAppInfo.setParent_app_id(parent_app_id);
                    mAppInfo.setCurrent_version(current_version);
                    max_version_number = 0;
                    ArrayList<AppVersion> mAppVersionList = getVersion(app_id + "");

                    for (AppVersion mAppVersion : mAppVersionList) {
                        if (current_version > 0) {
                            if (max_version_number == current_version) {
                                mAppInfo.setApk_flag(3);
                                mAppInfo.setmAppVersion(mAppVersion);
                            } else {
                                if (max_version_number > current_version) {
                                    mAppInfo.setApk_flag(1);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                } else {
                                    mAppInfo.setApk_flag(3);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                }
                            }
                        } else {
                            if (mAppInfo.getApp_type() == 3) {
                                mAppInfo.setApk_flag(3);
//                                mAppVersion.setMustupdated(1);
                            }
                            mAppInfo.setmAppVersion(mAppVersion);
                        }

//                        else if (max_version_number == mAppVersion.getVersion_number()){
//                            mAppInfo.setApk_flag(1);
//                            mAppInfo.setmAppVersion(mAppVersion);
//                        }
                    }
                    if (type_flags && type_flag <= 0) {
                        continue;
                    }
                    mAppInfoList.add(mAppInfo);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return mAppInfoList;
    }


    //将当前点击版本存入数据库中
    public void updateCurrentVersion(long current_version, long appId) {
//        String sql = "update app_info set current_version = "+current_version+" where  app_id = " + appId +" AND status_flag > 0" ;
//        if (db.isOpen()) {
//            try {
//                db.execSQL(sql);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
        ContentValues values = new ContentValues();
        values.put("app_id", appId);
        values.put("current_version", current_version);
        db.replace("mobile_app_info", null, values);
    }

    //    public void updateCurrentVersion(String appId){
//        String sql
//    }
    public void sortAppList(ArrayList<RequestResort> mRequestResortList) {


        /**
         * 如果没有数据 那么将插入数据
         */
        for (RequestResort mRequestResort : mRequestResortList) {
//                        ContentValues values = new ContentValues();
//                        values.put("portal_id", mRequestResort.portalId);
//                        values.put("app_id", mRequestResort.appId);
//                        values.put("user_using", "1");
//                        values.put("display_order", mRequestResort.displayOrder);
//                        values.put("status_flag", 1);
//                        db.replace("user_apc_portal_app", null, values);
            repliceAPC_Portal(mRequestResort.getPortalId(), mRequestResort.appId, mRequestResort.displayOrder);

//            if (mRequestResort.getAppInfo() != null && mRequestResort.getAppInfo().getClassifyAppInfo() != null && mRequestResort.getAppInfo().getClassifyAppInfo().size() != 0) {
//                for (AppInfo tempAppInfo : mRequestResort.getAppInfo().getClassifyAppInfo()) {
//                    repliceAPC_Portal(mRequestResort.getPortalId(), tempAppInfo.getApp_id(), tempAppInfo.getAppcenter_diplay_order());
//                }
//            }
        }
//        String portal_id = BookInit.getInstance().getPortalId();
//        String sql = "SELECT * FROM user_apc_portal_app LEFT JOIN app_info on (user_apc_portal_app.app_id = app_info.app_id) WHERE (app_info.status_flag > 0 AND user_apc_portal_app.status_flag > 0  AND user_apc_portal_app.user_using = 1 AND user_apc_portal_app.portal_id = " + portal_id + ")";
//        if (db.isOpen()) {
//            Cursor cursor = null;
//            try {
//                cursor = db.rawQuery(sql, null);
//                int number = cursor.getCount();
//                if (number != 0) {
//                    for (RequestResort mRequestResort : mRequestResortList) {
//                        sql = "update user_apc_portal_app set display_order = " + mRequestResort.displayOrder + " where portal_id = " + mRequestResort.portalId + " AND app_id = " + mRequestResort.appId + " AND user_using = 1";
//                        db.execSQL(sql);
//                    }
//                } else {
//                    /**
//                     * 如果没有数据 那么将插入数据
//                     */
//                    for (RequestResort mRequestResort : mRequestResortList) {
////                        ContentValues values = new ContentValues();
////                        values.put("portal_id", mRequestResort.portalId);
////                        values.put("app_id", mRequestResort.appId);
////                        values.put("user_using", "1");
////                        values.put("display_order", mRequestResort.displayOrder);
////                        values.put("status_flag", 1);
////                        db.replace("user_apc_portal_app", null, values);
//                        repliceAPC_Portal(mRequestResort.getPortalId(), mRequestResort.appId, mRequestResort.displayOrder);
//
//                        if (mRequestResort.getAppInfo() != null && mRequestResort.getAppInfo().getClassifyAppInfo() != null && mRequestResort.getAppInfo().getClassifyAppInfo().size() != 0) {
//                            for (AppInfo tempAppInfo : mRequestResort.getAppInfo().getClassifyAppInfo()) {
//                                repliceAPC_Portal(mRequestResort.getPortalId(), tempAppInfo.getApp_id(), tempAppInfo.getAppcenter_diplay_order());
//                            }
//                        }
//                    }
//                }
//                cursor.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void sortApp(ArrayList<RequestResort> mRequestResortList) {


        String portal_id = BookInit.getInstance().getPortalId();
        String sql = "SELECT * FROM user_apc_portal_app LEFT JOIN app_info on (user_apc_portal_app.app_id = app_info.app_id) WHERE (app_info.status_flag > 0 AND user_apc_portal_app.status_flag > 0  AND user_apc_portal_app.user_using = 1 AND user_apc_portal_app.portal_id = " + portal_id + ")";
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                int number = cursor.getCount();
                if (number != 0) {
                    for (RequestResort mRequestResort : mRequestResortList) {
                        sql = "update user_apc_portal_app set display_order = " + mRequestResort.displayOrder + " where portal_id = " + mRequestResort.portalId + " AND app_id = " + mRequestResort.appId + " AND user_using = 1";
                        db.execSQL(sql);
                    }
                } else {
                    /**
                     * 如果没有数据 那么将插入数据
                     */
                    for (RequestResort mRequestResort : mRequestResortList) {
//                        ContentValues values = new ContentValues();
//                        values.put("portal_id", mRequestResort.portalId);
//                        values.put("app_id", mRequestResort.appId);
//                        values.put("user_using", "1");
//                        values.put("display_order", mRequestResort.displayOrder);
//                        values.put("status_flag", 1);
//                        db.replace("user_apc_portal_app", null, values);
                        repliceAPC_Portal(mRequestResort.getPortalId(), mRequestResort.appId, mRequestResort.displayOrder);

                        if (mRequestResort.getAppInfo() != null && mRequestResort.getAppInfo().getClassifyAppInfo() != null && mRequestResort.getAppInfo().getClassifyAppInfo().size() != 0) {
                            for (AppInfo tempAppInfo : mRequestResort.getAppInfo().getClassifyAppInfo()) {
                                repliceAPC_Portal(mRequestResort.getPortalId(), tempAppInfo.getApp_id(), tempAppInfo.getAppcenter_diplay_order());
                            }
                        }
                    }
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void repliceAPC_Portal(long portalId, long appId, long displayOrder) {
        ContentValues values = new ContentValues();
        values.put("portal_id", portalId);
        values.put("app_id", appId);
        values.put("user_using", "1");
        values.put("display_order", displayOrder);
        values.put("status_flag", 1);
        db.replace("user_apc_portal_app", null, values);
    }

    /**
     * 删除应用中心应用
     * 将自定义表中 或者从 portal_id中改变状态
     * 再将app_info修改为0 还原初始状态
     */
    public void deleteAppInfos(String appId) {
        String portal_id = BookInit.getInstance().getPortalId();
        String sql = "SELECT * FROM user_apc_portal_app LEFT JOIN app_info on (user_apc_portal_app.app_id = app_info.app_id) WHERE (app_info.status_flag > 0 AND user_apc_portal_app.status_flag > 0  AND user_apc_portal_app.portal_id = " + portal_id + ")";
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                int number = cursor.getCount();
                //如果自定义中为0的话 那么从portal_app中获取
                if(!cursor.isClosed()){
                    cursor.close();
                }

                if (number != 0) {
                    sql = "update user_apc_portal_app set user_using = -1 where portal_id = " + portal_id + " AND app_id = " + appId;
                } else {
                    sql = "update portal_app set status_flag = -1 where portal_id = " + portal_id + " AND app_id = " + appId;
                }
                db.execSQL(sql);
                sql = "update app_info set current_version = 0 where app_id = " + appId + " AND status_flag > 0";
                db.execSQL(sql);
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
            }
        }
    }

    /**
     * 添加我已经删除的应用
     */
    public void addRemoveApp(String appId) {
        String portal_id = BookInit.getInstance().getPortalId();
        String sql = "SELECT * FROM user_apc_portal_app LEFT JOIN app_info on (user_apc_portal_app.app_id = app_info.app_id) WHERE (app_info.status_flag > 0 AND user_apc_portal_app.status_flag > 0 AND user_apc_portal_app.portal_id = " + portal_id + ")";
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                int number = cursor.getCount();
                //如果自定义中为0的话 那么从portal_app中获取
                if (number != 0) {
                    sql = "update user_apc_portal_app set user_using = 1 where portal_id = " + portal_id + " AND app_id = " + appId;
                } else {
                    sql = "update portal_app set status_flag = 1 where portal_id = " + portal_id + " AND app_id = " + appId;
                }
                db.execSQL(sql);
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(!cursor.isClosed()){
                    cursor.close();
                }
            }
        }
    }

    private long max_version_number;

    /**
     * 获取版本
     *
     * @param appId
     * @return
     */
    public ArrayList<AppVersion> getVersion(String appId) {
        String sql = "SELECT v.app_version_id,v.app_id,v.version_number,v.version_name,v.version_type,v.version_desc,v.mustupdated,v.app_filesize,v.hash_code,v.file_location,v.package_name,v.package_main FROM app_version v WHERE v.app_id = " + appId + " AND v.status_flag > 0 AND v.version_type != 2 AND v.version_type != 3 ORDER BY v.version_number";
        ArrayList<AppVersion> appVersionList = new ArrayList<AppVersion>();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    AppVersion mAppVersion = new AppVersion();
                    long app_version_id = cursor.getLong(cursor.getColumnIndex("app_version_id"));
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    long version_number = cursor.getLong(cursor.getColumnIndex("version_number"));
                    String version_name = cursor.getString(cursor.getColumnIndex("version_name"));
                    int version_type = cursor.getInt(cursor.getColumnIndex("version_type"));
                    String version_desc = cursor.getString(cursor.getColumnIndex("version_desc"));
                    int mustupdated = cursor.getInt(cursor.getColumnIndex("mustupdated"));
                    long app_filesize = cursor.getLong(cursor.getColumnIndex("app_filesize"));
                    String hash_code = cursor.getString(cursor.getColumnIndex("hash_code"));
                    String file_location = cursor.getString(cursor.getColumnIndex("file_location"));
                    String package_name = cursor.getString(cursor.getColumnIndex("package_name"));
                    String package_main = cursor.getString(cursor.getColumnIndex("package_main"));
                    if (max_version_number < version_number) {
                        max_version_number = version_number;
                    }
                    mAppVersion.setApp_version_id(app_version_id);
                    mAppVersion.setApp_id(app_id);
                    mAppVersion.setVersion_number(version_number);
                    mAppVersion.setVersion_name(version_name);
                    mAppVersion.setVersion_type(version_type);
                    mAppVersion.setVersion_desc(version_desc);
                    mAppVersion.setMustupdated(mustupdated);
                    mAppVersion.setApp_filesize(app_filesize);
                    mAppVersion.setHash_code(hash_code);
                    mAppVersion.setFile_location(file_location);
                    mAppVersion.setPackage_name(package_name);
                    mAppVersion.setPackage_main(package_main);

                    ArrayList<AppVersionConfig> appVersionConfigList = getVersionConfig(app_version_id + "");
                    mAppVersion.setAppVersionConfigArrayList(appVersionConfigList);
                    appVersionList.add(mAppVersion);
                }
            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }


        return appVersionList;
    }

    /**
     * 获取各个版本信息
     *
     * @param appVersionId
     * @return
     */
    public ArrayList<AppVersionConfig> getVersionConfig(String appVersionId) {
        String sql = "SELECT * FROM app_version_config config WHERE config.app_version_id = " + appVersionId + " AND config.status_flag > 0";
        ArrayList<AppVersionConfig> appVersionConfigList = new ArrayList<AppVersionConfig>();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {

                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    AppVersionConfig mAppVersionConfig = new AppVersionConfig();
                    long app_version_id = cursor.getLong(cursor.getColumnIndex("app_version_id"));
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String config_name = cursor.getString(cursor.getColumnIndex("config_name"));
                    String config_value = cursor.getString(cursor.getColumnIndex("config_value"));
                    String config_remark = cursor.getString(cursor.getColumnIndex("config_remark"));
                    int status_flag = cursor.getInt(cursor.getColumnIndex("status_flag"));
                    String config_code = cursor.getString(cursor.getColumnIndex("config_code"));
                    mAppVersionConfig.setApp_version_id(app_version_id);
                    mAppVersionConfig.setApp_id(app_id);
                    mAppVersionConfig.setConfig_name(config_name);
                    mAppVersionConfig.setConfig_value(config_value);
                    mAppVersionConfig.setConfig_remark(config_remark);
                    mAppVersionConfig.setStatus_flag(status_flag);
                    mAppVersionConfig.setConfig_code(config_code);
                    appVersionConfigList.add(mAppVersionConfig);
                }
            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return appVersionConfigList;
    }

    /**
     * 刷新用户自定义表
     *
     * @param portal_id
     */
    public void replace(String portal_id) {
        String sql = "SELECT * FROM user_apc_portal_app LEFT JOIN app_info on (user_apc_portal_app.app_id = app_info.app_id) WHERE (app_info.status_flag > 0 AND user_apc_portal_app.status_flag > 0  AND user_apc_portal_app.portal_id = " + portal_id + ")";
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                int number = cursor.getCount();
                //如果自定义中为0的话 那么从portal_app中获取
                cursor.close();
                if (number != 0) {
                    return;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
            }
        }

        ArrayList<AppInfo> appInfos = getApplicationCenterInfo();
        ArrayList<String[]> culumnValueArray = new ArrayList<String[]>();
        String[] culumnString = null;
        for (int i = 0; i < appInfos.size(); i++) {
            culumnString = new String[5];
            culumnString[0] = portal_id;
            culumnString[1] = appInfos.get(i).getApp_id() + "";
            culumnString[2] = "1";
            culumnString[3] = appInfos.get(i).getStatus_flag() + "";
            culumnValueArray.add(culumnString);
        }
        String[] culumn = new String[]{"portal_id", "app_id", "user_using", "display_order", "status_flag"};

        for (String[] culumnValue : culumnValueArray) {
            ContentValues values = new ContentValues();
            for (int i = 0; i < culumn.length; i++) {
                values.put(culumn[i], culumnValue[i]);
            }
            db.replace("user_apc_portal_app", null, values);
        }
    }

    public AppInfo accordCodeCheckAppId(String app_code) {
        String sql = "select * FROM app_info where app_code = '" + app_code + "'";
        AppInfo appInfo = null;
        if (db.isOpen()) {
            Cursor cursor = null;
            try {

                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    appInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    appInfo.setApp_id(app_id);
                }
            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return appInfo;
    }

    /**
     * 清空用户自定义表
     */
    public void deleteUserProtal() {
        if (db.isOpen()) {
            db.execSQL("DELETE FROM user_apc_portal_app");
            db.execSQL("DELETE FROM app_info");
            db.execSQL("DELETE FROM app_version");
            db.execSQL("DELETE FROM app_version_config");
            db.execSQL("DELETE FROM emp_portal");
            db.execSQL("DELETE FROM emp_corp_portal");
            db.execSQL("DELETE FROM fs_picture");
            db.execSQL("DELETE FROM org_org_tree");
            db.execSQL("DELETE FROM org_user");
            db.execSQL("DELETE FROM org_user_contact");
            db.execSQL("DELETE FROM org_user_org");
            db.execSQL("DELETE FROM org_userfield");
            db.execSQL("DELETE FROM fs_picture");
            db.execSQL("DELETE FROM portal_app");
            db.execSQL("DELETE FROM portal_left_menu");
            db.execSQL("DELETE FROM portal_righttop_menu");
            db.execSQL("DELETE FROM portal_tab");
            db.execSQL("DELETE FROM portal_tab_menu");
            db.execSQL("DELETE FROM user_apc_portal_app");
            db.execSQL("DELETE FROM appclient_version");
            db.execSQL("DELETE FROM form_extension_files");
            db.execSQL("DELETE FROM mobile_app_info");
            db.execSQL("DELETE FROM shortcutkeys");
            db.execSQL("DELETE FROM apc_app_appgroup");

        }

    }


    //----------------------------下面是操作自定义 是否支持快捷键自定义---------------------------
    //---------------------------通用表单的 type_flag 代办为0 已办为1 oa的type代办为2 已办为3

    public void replaceShortcutkeys(String app_id, String type) {
//        ArrayList<AppInfo> appInfos = getChildApp(app_id);
//        ArrayList<String[]> culumnValueArray = new ArrayList<String[]>();
//        String[] culumnString = null;
//        for(int i = 0; i < appInfos.size() ;i++){
//            for(AppVersionConfig mAppVersionConfig : appInfos.get(i).getmAppVersion().getAppVersionConfigArrayList()){
//                if(mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_todoflag")){
//                    String config_value = mAppVersionConfig.getConfig_value();
//                    if(TextUtils.isEmpty(config_value)){
//                        config_value = "0";
//                    }
//                    culumnString = new String[4];
//                    culumnString[0] = appInfos.get(i).getParent_app_id() + "";
//                    culumnString[1] = appInfos.get(i).getApp_id() + "";
//                    if(type.equals("OA")){
//                        config_value = (Integer.parseInt(config_value) + 1 )+ "";
//                    }
//                    culumnString[2] = config_value;
//                    culumnString[3] = appInfos.get(i).getStatus_flag() + "";
//                    culumnValueArray.add(culumnString);
//                }
//            }
//
//        }
//        String[] culumn = new String[]{"parent_app_id","app_id","type_flag","app_status_flag"};
//
//        for (String[] culumnValue : culumnValueArray) {
//            ContentValues values = new ContentValues();
//            for (int i = 0; i < culumn.length; i++) {
//                values.put(culumn[i], culumnValue[i]);
//            }
//            db.replace("shortcutkeys", null, values);
//        }

    }

    /**
     * 更新状态
     *
     * @param app_id
     * @param type_flag
     */
    public void updateChildTypeFlag(String app_id, String type_flag) {
        String sql = "update app_info set type_flag = " + type_flag + " where app_id = " + app_id;
        db.execSQL(sql);
    }


    public AppclientVersion getAppClientVersion(int currentCode) {
        String sql = "SELECT * FROM appclient_version WHERE version_no = " + currentCode + " AND TYPE = 1 AND status_flag > 0\n";
        AppclientVersion mAppclientVersion = null;
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                int env_type = 0;
                int version_no = 0;
                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    env_type = cursor.getInt(cursor.getColumnIndex("env_type"));
                    version_no = cursor.getInt(cursor.getColumnIndex("version_no"));
                }
                long[] intVsersionMax = getVersion(env_type, version_no);
                mAppclientVersion = getAppClientVersion(intVsersionMax[0], intVsersionMax[1]);
            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return mAppclientVersion;
    }

    public AppclientVersion getAppClientVersion(long currentCode, long env_type) {
        String sql = "SELECT * FROM appclient_version WHERE version_no = " + currentCode + " AND env_type = " + env_type + "  AND TYPE = 1 AND status_flag > 0\n";
        AppclientVersion mAppclientVersion = new AppclientVersion();

        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    mAppclientVersion.envType = cursor.getInt(cursor.getColumnIndex("env_type"));
                    mAppclientVersion.versionNo = cursor.getInt(cursor.getColumnIndex("version_no"));
                    mAppclientVersion.mustupdated = cursor.getInt(cursor.getColumnIndex("mustupdated"));
                    mAppclientVersion.resetClient = cursor.getInt(cursor.getColumnIndex("reset_client"));
                    mAppclientVersion.downloadUrl = cursor.getString(cursor.getColumnIndex("download_url"));
                    mAppclientVersion.mustupdated = Integer.parseInt(cursor.getString(cursor.getColumnIndex("mustupdated")));
                    mAppclientVersion.setVersion_name(cursor.getString(cursor.getColumnIndex("version_name")));
                    mAppclientVersion.setUpdate_desc(cursor.getString(cursor.getColumnIndex("update_desc")));
                }
                ;

            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return mAppclientVersion;
    }


    //第一个参数表示的是版本的最大号 第二个参数表示的是是否是测试版本还是正式版本
    public long[] getVersion(int env_type, int version_no) {
        String sql = "";
        if (env_type == 1) {
            sql = "SELECT max(version_no) version_no_max, env_type env_type_max FROM appclient_version WHERE TYPE = 1 AND status_flag > 0 ";

        } else if (env_type == 2) {
            sql = "SELECT version_no as version_no_max,env_type as env_type_max FROM appclient_version WHERE version_no = (\n" +
                    "SELECT max(version_no) FROM appclient_version WHERE TYPE = 1 AND status_flag > 0 \n" +
                    ") AND TYPE = 1 AND status_flag > 0 AND env_type = 2\n";
        }
        long version_no_max = 0;
        long env_type_max = -1;
        if (db.isOpen() && !"".equals(sql)) {
            Cursor cursor = null;
            try {

                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    version_no_max = cursor.getLong(cursor.getColumnIndex("version_no_max"));
                    env_type_max = cursor.getInt(cursor.getColumnIndex("env_type_max"));
                }
            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return new long[]{version_no_max, env_type_max};

    }

    /*
    * 获取当前环境
    * */
    public int getAppClientEnvtype(long currentCode) {
        String sql = "SELECT env_type FROM appclient_version WHERE version_no = " + currentCode + "  AND TYPE = 1 AND status_flag > 0\n";
        int envType = 2;
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    envType = cursor.getInt(cursor.getColumnIndex("env_type"));

                }
                ;

            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return envType;
    }

    /**
     * 查询用户登陆名，用于跳转敏行聊天
     */
    public String getLoginName(String user_id) {
        String sql = "SELECT login_name FROM org_user WHERE user_id ='" + user_id + "'";
        String envType = "";
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    envType = cursor.getString(cursor.getColumnIndex("login_name"));

                }
                ;

            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return envType;
    }

    /**
     * 查询用户是否开通敏行，用于跳转敏行聊天
     */
    public boolean isEmiUser(String user_id) {
        String sql = "SELECT emi_type FROM org_user WHERE user_id ='" + user_id + "'";
        int envType = 0;
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    envType = cursor.getInt(cursor.getColumnIndex("emi_type"));
                    if (envType == 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
                ;

            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return false;
    }

    public ArrayList<String> getParamGroupCorpValue(String...keys){
        ArrayList<String> arrayList = new ArrayList<String>();
        for(String key : keys) {
            String sql = "SELECT param_value FROM cm_param_groupcorp WHERE group_corp_id =" + BookInit.getInstance().getGroup_corp_id()+" and param_name='"+key+"'";
            if (db.isOpen()) {
                Cursor cursor = null;
                try {
                    cursor = db.rawQuery(sql, null);
                    String paramValue = "";
                    if (cursor.moveToFirst()) {
                        paramValue = cursor.getString(cursor.getColumnIndex("param_value"));
                    }
                    arrayList.add(paramValue);

                } catch (Exception e) {
                    if (cursor != null) {
                        cursor.close();
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }

                }
            }
        }



        return arrayList;
    }


    public long getCurrentVersion(long app_id) {
        String sql = "SELECT current_version FROM mobile_app_info WHERE app_id =" + app_id;
        long current = -1;
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    current = cursor.getLong(cursor.getColumnIndex("current_version"));
                }


            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }
        return current;
    }


    public ArrayList<AppInfo> getSettingAppInfo(String appId) {
        String sql = "SELECT * FROM portal_app LEFT JOIN app_info ON( portal_app.app_id = app_info.app_id) LEFT JOIN apc_app_appgroup aag ON (app_info.app_id = aag.app_id AND aag.status_flag > 0) WHERE( app_info.status_flag > 0 AND portal_app.portal_id  = "+BookInit.getInstance().getPortalId()+" AND portal_app.status_flag > 0 AND aag.appgroup_app_id = "+appId+") GROUP BY portal_app.app_id ORDER BY aag.display_order;";
        ArrayList<AppInfo> appInfos = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    AppInfo mAppInfo = new AppInfo();
                    long app_id = cursor.getLong(cursor.getColumnIndex("app_id"));
                    String app_code = cursor.getString(cursor.getColumnIndex("app_code"));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String app_shortname = cursor.getString(cursor.getColumnIndex("app_shortname"));
                    int app_type = cursor.getInt(cursor.getColumnIndex("app_type"));
                    String app_logo = cursor.getString(cursor.getColumnIndex("app_logo"));
                    String app_desc = cursor.getString(cursor.getColumnIndex("app_desc"));
                    String comp_code = cursor.getString(cursor.getColumnIndex("comp_code"));
                    String plugin_code = cursor.getString(cursor.getColumnIndex("plugin_code"));
                    long parent_app_id = cursor.getLong(cursor.getColumnIndex("parent_app_id"));
                    long current_version = getCurrentVersion(app_id);
                    try{
                        String picture_normal = cursor.getString(cursor.getColumnIndex("picture_normal"));
                        mAppInfo.setPicture_normal(picture_normal);
                    }catch (Exception e){

                    }

                    try {
                        String display_title = cursor.getString(cursor.getColumnIndex("display_title"));
                        mAppInfo.setDisplay_title(display_title);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mAppInfo.setApp_id(app_id);
                    mAppInfo.setApp_code(app_code);
                    mAppInfo.setApp_name(app_name);
                    mAppInfo.setApp_shortname(app_shortname);
                    mAppInfo.setApp_type(app_type);
                    mAppInfo.setApp_logo(app_logo);
                    mAppInfo.setApp_desc(app_desc);
                    mAppInfo.setPlugin_code(plugin_code);
                    mAppInfo.setComp_code(comp_code);
                    mAppInfo.setParent_app_id(parent_app_id);
                    mAppInfo.setCurrent_version(current_version);

                    max_version_number = 0;
                    ArrayList<AppVersion> mAppVersionList = getVersion(app_id + "");

                    for (AppVersion mAppVersion : mAppVersionList) {
                        if (current_version > 0) {
                            if (max_version_number == current_version) {
                                mAppInfo.setApk_flag(3);
                                mAppInfo.setmAppVersion(mAppVersion);
                            } else {
                                if (max_version_number > current_version) {
                                    mAppInfo.setApk_flag(1);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                } else {
                                    mAppInfo.setApk_flag(3);
                                    mAppInfo.setmAppVersion(mAppVersion);
                                }
                            }
                        } else {
                            if (mAppInfo.getApp_type() == 3) {
                                mAppInfo.setApk_flag(3);
//                                mAppVersion.setMustupdated(1);
                            }
                            mAppInfo.setmAppVersion(mAppVersion);
                        }

//                        else if (max_version_number == mAppVersion.getVersion_number()){
//                            mAppInfo.setApk_flag(1);
//                            mAppInfo.setmAppVersion(mAppVersion);
//                        }
                    }

                    mAppInfo.setMax_version_number(max_version_number);
                    mAppInfo.setmAppVersionList(mAppVersionList);
                    appInfos.add(mAppInfo);
                }
                cursor.close();

            } catch (Exception e) {
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }

            }
        }

        return appInfos;

    }

}
