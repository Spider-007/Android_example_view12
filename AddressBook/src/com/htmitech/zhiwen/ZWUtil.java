package com.htmitech.zhiwen;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Toast;

import com.htmitech.pop.AlertDialog;

/**
 * Created by htrf-pc on 2018/1/12.
 */
@TargetApi(Build.VERSION_CODES.M)
public class ZWUtil {
    private Context context;
    FingerprintManager manager;
    KeyguardManager mKeyManager;
    boolean result = false;
    private AlertDialog mAlertDialog;
    private final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0;
    @TargetApi(Build.VERSION_CODES.M)
    public boolean isFinger(Context context) {
        this.context = context;
        //android studio 上，没有这个会报错
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

        if (!result) {
            Toast.makeText(context, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            return false;
        }
        //判断硬件是否支持指纹识别
        if (!manager.isHardwareDetected()) {
            Toast.makeText(context, "没有指纹识别模块", Toast.LENGTH_SHORT).show();
            return false;
        }
        //判断 是否开启锁屏密码

        if (!mKeyManager.isKeyguardSecure()) {
            Toast.makeText(context, "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        //判断是否有指纹录入
        if (!manager.hasEnrolledFingerprints()) {
            Toast.makeText(context, "没有录入指纹", Toast.LENGTH_SHORT).show();
            mAlertDialog.setTitle("“没有指纹登记”").isHD().setMsg("没有指纹登记，请到设置->安全中设置").setPositiveButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return false;
        }
        mAlertDialog =  new AlertDialog(context);
        mAlertDialog.builder().setImage().setTitle("‘慧通公司’的TouchID").setMsg("通过Home建验证已有的手机指纹").setPositiveButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
        return true;
    }

    CancellationSignal mCancellationSignal = new CancellationSignal();
    //回调方法
    FingerprintManager.AuthenticationCallback mSelfCancelled = new FingerprintManager.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            //但多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
            Toast.makeText(context, errString, Toast.LENGTH_SHORT).show();
            showAuthenticationScreen();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

            Toast.makeText(context, helpString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

            Toast.makeText(context, "指纹识别成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationFailed() {
            mAlertDialog.setTitle("“再试一次”").isHD().setMsg("通过Home建验证已有的手机指纹").setPositiveButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).setNegativeButton("验证登录密码", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    };


    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        //android studio 上，没有这个会报错
        if (!result) {
            Toast.makeText(context, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            return;
        }
        manager.authenticate(cryptoObject, mCancellationSignal, 0, mSelfCancelled, null);


    }

    private void showAuthenticationScreen() {
        mAlertDialog.setDismiss();
//        Intent intent = mKeyManager.createConfirmDeviceCredentialIntent("finger", "测试指纹识别");
//        if (intent != null) {
//            ((Activity)context).startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
//        }
    }
}
