package com.pcg.yuquangong.model.cache;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.model.Constant;

/**
 * Created by gupengcheng on 2018/12/30.
 */

public class CacheManager {

    private static CacheManager sCacheManager;

    private CacheManager() {

    }

    public static CacheManager getInstance() {
        if (sCacheManager == null) {
            synchronized (CacheManager.class) {
                if (sCacheManager == null) {
                    sCacheManager = new CacheManager();
                }
            }
        }
        return sCacheManager;
    }

    /**
     * 第一次GPS需要时间，保存上次最后更新的GPS AdCode，用户就不必下次进来重新登录
     * @param adCode
     */
    public void setMapAdCode(String adCode) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_AD_CODE, adCode).commit();
    }

    public void setDeviceId(String deviceId) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_DEVICE_ID, deviceId).commit();
    }

    public String getMapAdCode() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String adCode = sharedPreferences.getString(Constant.Cache.KEY_AD_CODE, "");
        return adCode;
    }

    public String getDeviceId() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String adCode = sharedPreferences.getString(Constant.Cache.KEY_DEVICE_ID, "");
        return adCode;
    }

    // account cache

    public void setLoginedData(String loginedData) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_LOGINED_DATA, loginedData).commit();
    }

    public String getLoginedData() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String loginedData = sharedPreferences.getString(Constant.Cache.KEY_LOGINED_DATA, "");
        return loginedData;
    }

    public void setSuperLoginedDeviceNo(String deviceNo) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_SUPER_LOGINED_DEVICE_NO, deviceNo).commit();
    }

    public String getSuperLoginedDeviceNo() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String data = sharedPreferences.getString(Constant.Cache.KEY_SUPER_LOGINED_DEVICE_NO, "");
        return data;
    }

    // home cache

    public void setHomeBannerCacheData(String data) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_HOME_BANNER_DATA, data).commit();
    }

    public String getHomeBannerCacheData() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String data = sharedPreferences.getString(Constant.Cache.KEY_HOME_BANNER_DATA, "");
        return data;
    }

    // Setting cache

    public void setSettingLanguageType(int languageType) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putInt(Constant.Cache.KEY_SETTING_LANGUAGE_TYPE, languageType).commit();
    }

    public int getSettingLanguageType() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        int data = sharedPreferences.getInt(Constant.Cache.KEY_SETTING_LANGUAGE_TYPE, Constant.Language.LAN_CN);
        return data;
    }

    public void setSettingAboutInfo(String data) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_SETTING_ABOUT_DATA, data).commit();
    }

    public String getSettingAboutInfo() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String data = sharedPreferences.getString(Constant.Cache.KEY_SETTING_ABOUT_DATA, "");
        return data;
    }

    public void setSettingMyDevicesCache(String data) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_SETTING_DEVICES_DATA, data).commit();
    }

    public String getSettingMyDevicesCache() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String data = sharedPreferences.getString(Constant.Cache.KEY_SETTING_DEVICES_DATA, "");
        return data;
    }

    public void setSettingProfileInfo(String data) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_SETTING_PROFILE_DATA, data).commit();
    }

    public String getSettingProfileInfo() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String data = sharedPreferences.getString(Constant.Cache.KEY_SETTING_PROFILE_DATA, "");
        return data;
    }

    public void setSettingAudioTipsOpen(boolean audioOpen) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putBoolean(Constant.Cache.KEY_IS_AUDIO_TIPS_OPEN, audioOpen).commit();
    }

    public boolean isSettingAudioTipsOpen() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        boolean isOpen = sharedPreferences.getBoolean(Constant.Cache.KEY_IS_AUDIO_TIPS_OPEN, true);
        return isOpen;
    }

    // member cache

    public void setMemberListCache(String data) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_MEMBER_LIST_DATA, data).commit();
    }

    public String getMemberListCache() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String data = sharedPreferences.getString(Constant.Cache.KEY_MEMBER_LIST_DATA, "");
        return data;
    }

    // cure cache

    public void setCureRecordListCache(String data) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_CURE_RECORD_LIST_DATA, data).commit();
    }

    public String getCureRecordListCache() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String data = sharedPreferences.getString(Constant.Cache.KEY_CURE_RECORD_LIST_DATA, "");
        return data;
    }

    // watch record cache

    public void setWatchRecordListCache(String data) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_WATCH_RECORD_LIST_DATA, data).commit();
    }

    public String getWatchRecordListCache() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String data = sharedPreferences.getString(Constant.Cache.KEY_WATCH_RECORD_LIST_DATA, "");
        return data;
    }

    public void setWatchConnectHistoryListCache(String data) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_WATCH_HISTOR_LIST_DATA, data).commit();
    }

    public String getWatchConnectHistoryListCache() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String data = sharedPreferences.getString(Constant.Cache.KEY_WATCH_HISTOR_LIST_DATA, "");
        return data;
    }

    // phone national code cache
    public void setPhoneCodeListCache(String data) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        sharedPreferences.edit().putString(Constant.Cache.KEY_PHONE_NATIONAL_CODE_DATA, data).commit();
    }

    public String getPhoneCodeListCache() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(App.getInstance());
        String data = sharedPreferences.getString(Constant.Cache.KEY_PHONE_NATIONAL_CODE_DATA, "");
        return data;
    }

}
