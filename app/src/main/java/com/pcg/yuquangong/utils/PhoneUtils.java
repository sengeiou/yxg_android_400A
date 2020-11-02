package com.pcg.yuquangong.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.pcg.yuquangong.App;

public class PhoneUtils {

    private static TelephonyManager sTelephonyManager;

    private static TelephonyManager getTelephonyManager() {
        if (sTelephonyManager == null) {
            sTelephonyManager = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        }
        return sTelephonyManager;
    }

    public static String getIMEI() {
        return getTelephonyManager().getDeviceId();
    }

    public static String getBaseband() {
        try {
            @SuppressLint("PrivateApi")
            Class localClass = Class.forName("android.os.SystemProperties");
            Object localObject1 = localClass.newInstance();
            Object localObject2 = localClass.getMethod("get", new Class[]{String.class, String.class}).invoke(localObject1, new Object[]{"gsm.version.baseband", "no message"});
//            Object localObject3 = localClass.getMethod("get", new Class[]{String.class, String.class}).invoke(localObject1, new Object[]{"ro.build.display.id", ""});
            return (String) localObject2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSystemOsId() {
        try {
            @SuppressLint("PrivateApi")
            Class localClass = Class.forName("android.os.SystemProperties");
            Object localObject1 = localClass.newInstance();
//            Object localObject2 = localClass.getMethod("get", new Class[]{String.class, String.class}).invoke(localObject1, new Object[]{"gsm.version.baseband", "no message"});
            Object localObject3 = localClass.getMethod("get", new Class[]{String.class, String.class}).invoke(localObject1, new Object[]{"ro.build.display.id", ""});
            return (String) localObject3;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
