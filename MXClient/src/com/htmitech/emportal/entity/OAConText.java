package com.htmitech.emportal.entity;

import android.content.Context;

import com.htmitech.emportal.ui.login.data.logindata.PNList;

import java.io.Serializable;
import java.util.ArrayList;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

public class OAConText implements Serializable{

	private static final long serialVersionUID = 1L;
	private static OAConText context;

	public String UserID;

	public String UserName;

     private String oA_UserName;

     private String oA_DeptName;

	public String OA_UserId;

	public String OA_UserName;

	public String OA_UnitId;
	public String ThirdDepartmentName;
	public String ThirdDepartmentId;
	public String MRS_UserId;
	public String NetworkName;

	public String LogFunctionId;

	/** 新增 20170523**/
	public int IsEMIUser;
	public String login_name;
	public String group_corp_id;
	/**2017年6月5日15:16:01*/

	public ArrayList<PNList> PN_List;

//     private String currentDocId;
//
//     private String currentFileId;//当前的文件ID
//
//     private String currentAttId;//当前的附件ID
//
//     private String currentActionDesc;
//
//     private String currentreportID;
//
//     private String currentreportName;



	public OAConText(){
	}

    public static OAConText getInstance(Context androidcontext){
    	if(null == context){
    		context = new OAConText();
    	}
    	if(androidcontext != null){
			context.OA_UserId = (PreferenceUtils.getOAUserID(androidcontext));
			context.OA_UserName = (PreferenceUtils.getOAUserName(androidcontext));
			context.UserID = (PreferenceUtils.getEMPUserID(androidcontext));
			context.UserName = (PreferenceUtils.getEMPUserName(androidcontext));  //getLoginName
			context.OA_UnitId = (PreferenceUtils.getOAUnitId(androidcontext));
			context.NetworkName = PreferenceUtils.getNetworkName(androidcontext);

			context.IsEMIUser = PreferenceUtils.getIsEMIUser(androidcontext);
			context.login_name = PreferenceUtils.getLogin_name(androidcontext);
			context.group_corp_id = PreferenceUtils.getGroup_corp_id(androidcontext);
        }



    	return context ;
    }
}
