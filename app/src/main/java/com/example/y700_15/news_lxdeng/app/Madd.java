package com.example.y700_15.news_lxdeng.app;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class Madd extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        UMConfigure.init(this, Contstans.UMENG_APP_KEY, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);

        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMConfigure.setLogEnabled(true);
    }
}
