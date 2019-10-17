package com.htmitech.proxy.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.htmitech.emportal.service.UpdateServiceHanle;

public class UpgradeFromDatabaseServices extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        UpdateServiceHanle mUpdateServiceHanle = new UpdateServiceHanle(UpgradeFromDatabaseServices.this);
        mUpdateServiceHanle.getServerVersionInfomation();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
