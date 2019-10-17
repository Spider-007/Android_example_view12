package com.htmitech.zhiwen;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * Created by htrf-pc on 2018/1/15.
 */
public class ZWUtils {
    com.htmitech.pop.AlertDialog mAlertDialog;
    FingerprintManager manager;
    KeyguardManager mKeyManager;
    private MyAuthCallback myAuthCallback;
    private Handler handler;
    private boolean result = false;
    public static final int MSG_AUTH_SUCCESS = 100;
    public static final int MSG_AUTH_FAILED = 101;
    public static final int MSG_AUTH_ERROR = 102;
    public static final int MSG_AUTH_HELP = 103;
    private CancellationSignal cancellationSignal;
    private Context context;
    private boolean isLogin;
    private boolean isFloag;
    private IBackMainOnClick mIBackMainOnClick;
    public ZWUtils(Context context,boolean isLogin,IBackMainOnClick mIBackMainOnClick){
        this.context = context;
        this.isLogin = isLogin;
        this.mIBackMainOnClick = mIBackMainOnClick;
        mAlertDialog =  new com.htmitech.pop.AlertDialog(context);
    }

    public ZWUtils(Context context,boolean isLogin,IBackMainOnClick mIBackMainOnClick,boolean isFloag){
        mAlertDialog =  new com.htmitech.pop.AlertDialog(context);
        this.context = context;
        this.isLogin = isLogin;
        this.isFloag = isFloag;
        this.mIBackMainOnClick = mIBackMainOnClick;
    }
    @TargetApi(Build.VERSION_CODES.M)
    public  void initFingure() {

        if(mAlertDialog.isShow()){
            mAlertDialog.dismiss();
        }

        manager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        int targetSdkVersion = 0;

        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = context.checkSelfPermission( Manifest.permission.USE_FINGERPRINT)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
//                result = PermissionChecker.checkSelfPermission(context,  Manifest.permission.USE_FINGERPRINT)
//                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }


        if (!manager.isHardwareDetected()) {
            // no fingerprint sensor is detected, show dialog to tell user.
            mAlertDialog.builder().setImage().setCancelable(false).setTitle("没有指纹传感器").setMsg("您的设备上没有指纹传感器，点击取消退出").setPositiveButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            }).show();

        } else if (!manager.hasEnrolledFingerprints()) {
            // no fingerprint image has been enrolled.
            mAlertDialog.builder().setImage().setCancelable(false).setTitle("没有指纹登记").setMsg("没有指纹登记，请到设置->安全中设置").setPositiveButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            }).show();
        } else {
            try {
                setFingurePws();
                initFingureDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @TargetApi(Build.VERSION_CODES.M)
    private void setFingurePws() {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

//                Log.e(TAG, "msg: " + msg.what + " ,arg1: " + msg.arg1);
                switch (msg.what) {
                    case MSG_AUTH_SUCCESS:
                        setResultInfo("指纹识别");
                        cancellationSignal = null;
                        break;
                    case MSG_AUTH_FAILED:
                        setResultInfo("指纹识别失败");
                        cancellationSignal = null;
                        break;
                    case MSG_AUTH_ERROR:
                        handleErrorCode(msg.arg1);
                        break;
                    case MSG_AUTH_HELP:
                        handleHelpCode(msg.arg1);
                        break;
                }
            }
        };

        myAuthCallback = new MyAuthCallback(handler);

        try {

            CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
            if(cancellationSignal == null)
                cancellationSignal = new CancellationSignal();

            manager.authenticate(cryptoObjectHelper.buildCryptoObject(), cancellationSignal, 0, myAuthCallback, null);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            Toast.makeText(contexts, "指纹识别初始化失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }
    private void handleErrorCode(int code) {

        switch (code) {
            case FingerprintManager.FINGERPRINT_ERROR_CANCELED:
                setResultInfo("切换手势解锁");
                break;
            case FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE:
                setResultInfo("硬件不可用，请稍后再试");
                break;
            case FingerprintManager.FINGERPRINT_ERROR_LOCKOUT:
                setResultInfo("locked");
                break;
            case FingerprintManager.FINGERPRINT_ERROR_NO_SPACE:
                setResultInfo("无法完成操作，因为还没有足够的存储空间来完成操作");
                break;
            case FingerprintManager.FINGERPRINT_ERROR_TIMEOUT:
                setResultInfo("操作超时，请重试");
                break;
            case FingerprintManager.FINGERPRINT_ERROR_UNABLE_TO_PROCESS:
                setResultInfo("指纹图像无法识别，请重试");
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setResultInfo(String stringId) {
        if ("指纹识别".equals(stringId)) {
            mIBackMainOnClick.onMainOnClick();
            mAlertDialog.dismiss();
            if(!isFloag)
                ((Activity) context).finish();
        }else if("locked".equals(stringId)){
//        	Log.i("TAG--->", "指纹传感器被锁");
            if(isFloag){
                mAlertDialog.setTitle("指纹已被锁定").isHD().setCancelable(false).setMsg("指纹验证失败已超限").setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cancellationSignal!=null){
                            cancellationSignal.cancel();
                            cancellationSignal = null;
                        }
                    }
                }).setLayout();
            }else{
                mAlertDialog.setTitle("指纹已被锁定").isHD().setCancelable(false).setMsg("指纹验证失败已超限").setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cancellationSignal!=null){
                            cancellationSignal.cancel();
                            cancellationSignal = null;
                        }
                    }
                }).setNegativeButton("验证登录密码", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mIBackMainOnClick) {
                            mIBackMainOnClick.onLoginOnClick();
                        }
                    }
                }).setLayout();
            }


        }else{
            if("切换手势解锁".contains(stringId)){
                return;
            }
//            Toast.makeText(contexts, stringId,  Toast.LENGTH_SHORT).show();
//        	ShowToast.showToast(FingureAriseActivity.this, stringId);
            if(isFloag){
                mAlertDialog.setTitle("“再试一次”").isHD().setCancelable(false).setMsg("验证已有指纹").setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cancellationSignal!=null){
                            cancellationSignal.cancel();
                            cancellationSignal = null;
                        }
                    }
                }).setLayout();
            }else{
                mAlertDialog.setTitle("“再试一次”").isHD().setCancelable(false).setMsg("验证已有指纹").setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cancellationSignal!=null){
                            cancellationSignal.cancel();
                            cancellationSignal = null;
                        }
                    }
                }).setNegativeButton("验证登录密码", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mIBackMainOnClick.onLoginOnClick();
                    }
                }).setLayout();
            }



        }


    }


    private void handleHelpCode(int code) {
        switch (code) {
            case FingerprintManager.FINGERPRINT_ACQUIRED_GOOD:
                setResultInfo("获得的图像是好的");
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_IMAGER_DIRTY:
                setResultInfo("由于传感器上的可疑或检测到的灰尘，指纹图像太嘈杂了。");
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_INSUFFICIENT:
                setResultInfo("指纹图像太嘈杂了");
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_PARTIAL:
                setResultInfo("请紧按指纹传感器");
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_FAST:
                setResultInfo("手指移动过快");
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_SLOW:
                setResultInfo("请移动手指");
                break;
        }
    }



    /**
     * 弹出指纹识别的弹框
     */
    private void initFingureDialog(){

        mAlertDialog.builder().setImage().setCancelable(false).setTitle("指纹验证").setMsg("验证已有指纹").setPositiveButton("取消", new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(cancellationSignal!=null){
                    cancellationSignal.cancel();
                    cancellationSignal = null;
                }
            }
        }).show();

    }
}
