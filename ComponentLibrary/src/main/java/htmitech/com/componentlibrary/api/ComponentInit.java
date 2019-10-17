package htmitech.com.componentlibrary.api;

import android.content.Context;

import htmitech.com.componentlibrary.listener.CallBackImageSelectImpJava;
import htmitech.com.componentlibrary.listener.CallBackSuccess;
import htmitech.com.componentlibrary.listener.CallbackFormListener;
import htmitech.com.componentlibrary.listener.HTCallbackEMIUserListener;
import htmitech.com.componentlibrary.listener.ICallCheckUserListener;
import htmitech.com.componentlibrary.listener.IFindKeyByValueListener;
import htmitech.com.componentlibrary.listener.ILogUpdateCallListener;
import htmitech.com.componentlibrary.listener.ISearch413;
import htmitech.com.componentlibrary.listener.ISelectPoisitionListener;
import htmitech.com.componentlibrary.log.StatisLog;
import htmitech.com.componentlibrary.unit.PreferenceUtils;

/**
 * Created by htrf-pc on 2018-3-15.
 */
public class ComponentInit {
    private static ComponentInit mComponentInit;
    private int isWaterSecurity;
    private String OAUserName = "";
    private String EMPUserID = "";
    private String Attribute1 = "";
    private String EMPUserName = "";
    private String ThirdDepartmentName = "";
    private CallbackFormListener mCallbackFormListener = null;
    private boolean isIMI;
    private String loginName;
    private int usingColorStyle;
    private String appId;
    private ICallCheckUserListener mICallCheckUserListener; //回调关于选人
    private CallBackSuccess success;
    private HTCallbackEMIUserListener mHTCallbackEMIUserListener;
    private ISelectPoisitionListener mISelectPoisitionListener;
    private IFindKeyByValueListener mIFindKeyByValueListener;
    private ISearch413 mISearch413;
    private Context context;
    private ILogUpdateCallListener mILogUpdateCallListener;



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ILogUpdateCallListener getmILogUpdateCallListener() {
        if(mILogUpdateCallListener == null){
            mILogUpdateCallListener = new StatisLog();
        }
        return mILogUpdateCallListener;
    }

    public void setmILogUpdateCallListener(ILogUpdateCallListener mILogUpdateCallListener) {
        this.mILogUpdateCallListener = mILogUpdateCallListener;
    }

    public CallBackSuccess getSuccess() {
        if (success == null) {
            success = new CallBackSuccess.DefalutCallBackSuccess();
        }
        return success;
    }

    public void setSuccess(CallBackSuccess success) {
        this.success = success;
    }

    public ISearch413 getmISearch413() {
        if (mISearch413 == null) {
            mISearch413 = new ISearch413.DefaultISearch();
        }
        return mISearch413;
    }

    public void setmISearch413(ISearch413 mISearch413) {
        this.mISearch413 = mISearch413;
    }

    public HTCallbackEMIUserListener getmHTCallbackEMIUserListener() {
        if (mHTCallbackEMIUserListener == null) {
            mHTCallbackEMIUserListener = new HTCallbackEMIUserListener.DefaultHTCallbackEMIUserListener();
        }
        return mHTCallbackEMIUserListener;
    }

    public ISelectPoisitionListener getmISelectPoisitionListener() {
        if (mISelectPoisitionListener == null) {
            mISelectPoisitionListener = new ISelectPoisitionListener.DefaultISelectPoisitionListener();
        }
        return mISelectPoisitionListener;
    }

    public void setmISelectPoisitionListener(ISelectPoisitionListener mISelectPoisitionListener) {
        this.mISelectPoisitionListener = mISelectPoisitionListener;
    }

    public void setmHTCallbackEMIUserListener(HTCallbackEMIUserListener mHTCallbackEMIUserListener) {
        this.mHTCallbackEMIUserListener = mHTCallbackEMIUserListener;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public ICallCheckUserListener getmICallCheckUserListener() {
        if (mICallCheckUserListener == null) {
            mICallCheckUserListener = new ICallCheckUserListener.DefaultICallCheckUserListener();
        }
        return mICallCheckUserListener;
    }

    public void setmICallCheckUserListener(ICallCheckUserListener mICallCheckUserListener) {
        this.mICallCheckUserListener = mICallCheckUserListener;
    }

    public IFindKeyByValueListener getmIFindKeyByValueListener() {
        if (mIFindKeyByValueListener == null) {
            mIFindKeyByValueListener = new IFindKeyByValueListener.DefaultFindKeyByValueListener();
        }
        return mIFindKeyByValueListener;
    }

    public void setmIFindKeyByValueListener(IFindKeyByValueListener mIFindKeyByValueListener) {
        this.mIFindKeyByValueListener = mIFindKeyByValueListener;
    }

    public int getUsingColorStyle() {
        return usingColorStyle;
    }

    public void setUsingColorStyle(int usingColorStyle) {
        this.usingColorStyle = usingColorStyle;
    }

    public String getLoginName(Context context) {
        if (loginName == null) {
            loginName = PreferenceUtils.getLoginName(context);
        }
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public boolean isIMI() {
        return isIMI;
    }

    public void setIsIMI(boolean isIMI) {
        this.isIMI = isIMI;
    }

    public String getThirdDepartmentName() {
        return ThirdDepartmentName;
    }

    public void setThirdDepartmentName(String thirdDepartmentName) {
        ThirdDepartmentName = thirdDepartmentName;
    }

    public String getEMPUserName() {
        return EMPUserName;
    }

    public void setEMPUserName(String EMPUserName) {
        this.EMPUserName = EMPUserName;
    }

    public String getAttribute1() {
        return Attribute1;
    }

    public void setAttribute1(String attribute1) {
        Attribute1 = attribute1;
    }

    public String getEMPUserID() {
        return EMPUserID;
    }

    public void setEMPUserID(String EMPUserID) {
        this.EMPUserID = EMPUserID;
    }

    public String getOAUserName() {
        return OAUserName;
    }

    public void setOAUserName(String OAUserName) {
        this.OAUserName = OAUserName;
    }

    public int getIsWaterSecurity() {
        return isWaterSecurity;
    }

    public CallbackFormListener getmCallbackFormListener() {
        if (mCallbackFormListener == null) {
            mCallbackFormListener = new CallbackFormListener.DefaultCallbackFormListener();
        }
        return mCallbackFormListener;
    }

    public void setmCallbackFormListener(CallbackFormListener mCallbackFormListener) {
        this.mCallbackFormListener = mCallbackFormListener;
    }

    private ComponentInit() {

    }

    public static ComponentInit getInstance() {
        if (mComponentInit == null) {
            mComponentInit = new ComponentInit();
        }
        return mComponentInit;
    }

    public CallBackImageSelectImpJava mCallBackImageSelectImpJava;

    public CallBackImageSelectImpJava getmCallBackImageSelectImpJava() {
        return mCallBackImageSelectImpJava;
    }

    public void setmCallBackImageSelectImpJava(CallBackImageSelectImpJava mCallBackImageSelectImp) {
        this.mCallBackImageSelectImpJava = mCallBackImageSelectImp;
    }

}
