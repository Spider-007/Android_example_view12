package com.htmitech.unit;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.htmitech.app.Constant;
import com.htmitech.domain.OrgUser;
import com.htmitech.domain.SYS_User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 设置颜色样式  以及字体大小配置
 */
public class ConfigStyleUtil {


    public static void changeTextSize(Activity activity){
        Configuration configuration = activity.getResources().getConfiguration();
        configuration.fontScale = Constant.TEXTVIEWSIXE;    //1为标准字体，multiple为放大的倍数
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayMetrics.scaledDensity = configuration.fontScale*displayMetrics.density;
        activity.getBaseContext().getResources().updateConfiguration(configuration, displayMetrics);
    }

    public static String changeConfig(String text,SYS_User mSYS_Users){
        OrgUser mOrgUser = copyOrgUser(mSYS_Users);
        text = text.replace(".","_");
        int fromIndex1 = -1, fromIndex2 = 0;
        try {
            int temp = -1;
            StringBuffer sb = new StringBuffer();
            String tempStr = "";
            while (fromIndex1 != -1 || fromIndex2 != -1) {
                fromIndex1 = text.indexOf("{", fromIndex1 + 1);
                fromIndex2 = text.indexOf("}", fromIndex2 + 1);
                if(fromIndex2 != -1 && fromIndex1 != -1){
                    String str = text.substring(fromIndex1 + 1, fromIndex2);
                    Field f = mOrgUser.getClass().getDeclaredField(str);
                    f.setAccessible(true);
                    String value = "";
                    try{
                        value = f.get(mOrgUser) + "";
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (str.equalsIgnoreCase("gender")) {
                        if (value.equals("1")) {
                            value = "男";
                        } else if (value.equals("2")) {
                            value = "女";
                        } else {
                            value = "未知";
                        }

                    }

                    if(str.equalsIgnoreCase("mobile_phone") || str.equalsIgnoreCase("office_phone") || str.equalsIgnoreCase("home_phone")){
                        if (value.contains(",")){
                            String[] s = value.split(",");
                            value = "";
                            for (String st : s){
                                value +=  DensityUtil.cellPhone(st,str)+",";
                            }
                        }else if (value.contains(":")){
                            String[] s = value.split(":");
                            value = "";
                            for (String st : s){
                                value +=  DensityUtil.cellPhone(st,str) +":";
                            }
                        }else if (value.contains(";")){
                            String[] s = value.split(";");
                            value = "";
                            for (String st : s){
                                value +=  DensityUtil.cellPhone(st,str) + ";";
                            }
                        }else if (value.contains(" ")){
                            String[] s = value.split(" ");
                            value = "";
                            for (String st : s){
                                value +=  DensityUtil.cellPhone(st,str)+" ";
                            }
                        }else if (value.contains("，")){
                            String[] s = value.split("，");
                            value = "";
                            for (String st : s){
                                value +=  DensityUtil.cellPhone(st,str)+"，";
                            }
                        }else{
                            value =  DensityUtil.cellPhone(value,str) +",";
                        }
                        value = value.substring(0,value.length() - 1);
                    }
                    tempStr = text.substring(temp + 1, fromIndex2 + 1).replaceAll("\\{[^}]*\\}", value);
                    temp = fromIndex2;

                    sb.append(tempStr);
                }
            }
            return sb.toString();
        } catch (Exception ep) {
            ep.printStackTrace();
        }
        return "";
    }

    public static OrgUser copyOrgUser(SYS_User mSYS_User){
        OrgUser mOrgUser = new OrgUser();
        mOrgUser.setUser_id(Long.parseLong(mSYS_User.getUserId()));
        mOrgUser.setOrg_name(mSYS_User.getOrg_name());
        mOrgUser.setTree_name(mSYS_User.getTree_name());
        mOrgUser.setUser_name(mSYS_User.getFullName());
        mOrgUser.setUser_type((short) mSYS_User.getUserType());
        mOrgUser.setAdmin_type(mSYS_User.getAdminType());
        mOrgUser.setEmi_type(mSYS_User.getEmiType());
        mOrgUser.setUser_nickname(mSYS_User.getUserNickname());
        mOrgUser.setUser_pyname(mSYS_User.getSuoxie());
        mOrgUser.setDisplay_order(mSYS_User.getDisplay_order());
        mOrgUser.setMobile_phone(mSYS_User.getMobile());
        mOrgUser.setOffice_phone(mSYS_User.getOffice());
        mOrgUser.setPic_path(mSYS_User.getPhotosurl());
        mOrgUser.setRemark(mSYS_User.getRemark());
        mOrgUser.setGender(mSYS_User.getGender());
        mOrgUser.setTitle(mSYS_User.getTitle());
        mOrgUser.setBirthday(mSYS_User.getBirthday());
        mOrgUser.setHome_phone(mSYS_User.getHome_phone());
        mOrgUser.setOffice_address(mSYS_User.getOffice_address());
        mOrgUser.setPostal_code(mSYS_User.getPostal_code());
        mOrgUser.setPic_path(mSYS_User.getPhotosurl());
        mOrgUser.setHead_type(mSYS_User.getHeadType());
        mOrgUser.setLogin_name(mSYS_User.getLogin_name());
        mOrgUser.setOrg_user_org_fax(mSYS_User.getOrg_user_org_fax());
        mOrgUser.setOrg_user_org_office_phone(mSYS_User.getOrg_user_org_office_phone());
        mOrgUser.setOrg_user_org_org_title(mSYS_User.getOrg_user_org_org_title());
        mOrgUser.setOrg_user_org_user_titlename(mSYS_User.getOrg_user_org_user_titlename());
        mOrgUser.setFax(mSYS_User.getFax());
        mOrgUser.setEfn1(mSYS_User.getEfn1());
        mOrgUser.setEfn2(mSYS_User.getEfn2());
        mOrgUser.setEfn3(mSYS_User.getEfn3());
        mOrgUser.setEfi1(mSYS_User.getEfi1());
        mOrgUser.setEfi2(mSYS_User.getEfi2());
        mOrgUser.setEfi3(mSYS_User.getEfi3());
        mOrgUser.setEfi4(mSYS_User.getEfi4());
        mOrgUser.setEfi5(mSYS_User.getEfi5());
        mOrgUser.setEfd1(mSYS_User.getEfd1());
        mOrgUser.setEfd2(mSYS_User.getEfd2());
        mOrgUser.setEfs1(mSYS_User.getEfs1());
        mOrgUser.setEfs2(mSYS_User.getEfs2());
        mOrgUser.setEfs3(mSYS_User.getEfs3());
        mOrgUser.setEfs4(mSYS_User.getEfs4());
        mOrgUser.setEfs5(mSYS_User.getEfs5());
        mOrgUser.setEfs6(mSYS_User.getEfs6());
        mOrgUser.setEfs7(mSYS_User.getEfs7());
        mOrgUser.setEfs8(mSYS_User.getEfs8());
        mOrgUser.setEfs9(mSYS_User.getEfs9());
        mOrgUser.setEfs10(mSYS_User.getEfs10());
        mOrgUser.setEmail(mSYS_User.getEmail());
        return mOrgUser;
    }
}
