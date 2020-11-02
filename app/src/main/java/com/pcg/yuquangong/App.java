package com.pcg.yuquangong;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;
import com.danikula.videocache.HttpProxyCacheServer;
import com.deemons.serialportlib.ByteUtils;
import com.deemons.serialportlib.SerialPort;
import com.google.gson.reflect.TypeToken;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.event.SerialReceivedEvent;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.bean.ZCodeItemBean;
import com.pcg.yuquangong.model.network.entity.LoginEntity;
import com.pcg.yuquangong.model.network.entity.ZCodeItemEntity;
import com.pcg.yuquangong.utils.GsonHelper;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.utils.PhoneUtils;
import com.pcg.yuquangong.utils.ToastUtils;
import com.pcg.yuquangong.utils.Utils;
import com.pcg.yuquangong.utils.VideoUtil;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    private static App sInstance;
    // 字体
    private Typeface mHomeTextTypeface;
    private Typeface mMainHomeTypeface;
    private Typeface mRegularTypeface;
    private Typeface mEdtTextTypeface;
    // 登陆信息
    private LoginEntity mLoginEntity;
    // 蓝牙手表
    private CRPBleClient mBleClient;
    private CRPBleDevice mBleDevice;
    private CRPBleConnection mBleConnection;
    // language
    private int mLanguageType = Constant.Language.LAN_CN;
    // device id
    private String mDeviceId;

    // national code list
    private List<ZCodeItemEntity> mZCodeList = new ArrayList<>();

    // for spinner show
    private List<ZCodeItemBean> mZCodeBeanList = new ArrayList<>();

    // App是否销毁
    private volatile boolean mIsAppDestroyed = false;

    public static App getInstance() {
        return sInstance;
    }

    public Typeface getHomeTextTypeface() {
        return mHomeTextTypeface;
    }

    public Typeface getMainHomeTypeface() {
        return mMainHomeTypeface;
    }

    public Typeface getRegularTypeface() {
        return mRegularTypeface;
    }

    public Typeface getEdtTextTypeface() {
        return mEdtTextTypeface;
    }

    public LoginEntity getLoginEntity() {
        return mLoginEntity;
    }

    public void setLoginEntity(LoginEntity loginEntity) {
        mLoginEntity = loginEntity;
    }

    public CRPBleClient getBleClient() {
        return mBleClient;
    }

    public CRPBleDevice getBleDevice() {
        return mBleDevice;
    }

    public CRPBleConnection getBleConnection() {
        return mBleConnection;
    }

    public void setBleDevice(CRPBleDevice bleDevice) {
        mBleDevice = bleDevice;
    }

    public void setBleConnection(CRPBleConnection bleConnection) {
        mBleConnection = bleConnection;
    }

    public boolean isAppDestroyed() {
        return mIsAppDestroyed;
    }

    public void setAppDestroyed(boolean appDestroyed) {
        mIsAppDestroyed = appDestroyed;
    }

    public List<ZCodeItemBean> getZCodeList() {
        return mZCodeBeanList;
    }

    public void setZCodeList(List<ZCodeItemEntity> zCodeList) {
        mZCodeList.clear();
        mZCodeList.addAll(zCodeList);

        mZCodeBeanList.clear();
        for (ZCodeItemEntity itemEntity : mZCodeList) {
            ZCodeItemBean itemBean = new ZCodeItemBean(itemEntity.getZname(), itemEntity.getZcode());
            mZCodeBeanList.add(itemBean);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        CrashReport.initCrashReport(getApplicationContext(), "b99ba20f1a", false);
        initCustomSetLanguage();
        mDeviceId = CacheManager.getInstance().getDeviceId();
//        mDeviceId = PhoneUtils.getIMEI();
        LogUtil.e(TAG, "mDeviceId imei = " + mDeviceId);
        mMapAdcode = CacheManager.getInstance().getMapAdCode();
        ApiClient.init();
        initTypeface();
        initLoginedData();
        initPhoneNationalCodeData();
//        initMapLocation();
        initWatch();
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
        CacheManager.getInstance().setDeviceId(deviceId);
    }

    private void initTypeface() {
        if (getSetLocale() == Locale.CHINA) {
            mHomeTextTypeface = Typeface.createFromAsset(getAssets(), "classic_lishu_ti.ttf");
            mMainHomeTypeface = Typeface.createFromAsset(getAssets(), "main_home.ttf");
            mRegularTypeface = Typeface.createFromAsset(getAssets(), "heiti_regular.ttf");
            mEdtTextTypeface = Typeface.createFromAsset(getAssets(), "msyh.ttf");
        } else {
            mHomeTextTypeface = Typeface.DEFAULT;
            mMainHomeTypeface = Typeface.DEFAULT;
            mRegularTypeface = Typeface.DEFAULT;
            mEdtTextTypeface = Typeface.DEFAULT;
        }
    }

    private void initLoginedData() {
        String loginedData = CacheManager.getInstance().getLoginedData();
        if (!TextUtils.isEmpty(loginedData)) {
            mLoginEntity = GsonHelper.getGson().fromJson(loginedData, LoginEntity.class);
        }
    }

    private void initPhoneNationalCodeData() {
//        String zCodeCache = CacheManager.getInstance().getPhoneCodeListCache();
        String zCodeCache = Constant.ZCODE_SERVER_TEXT;
        if (getSetLocale() == Locale.UK) {
            zCodeCache = Utils.replaceBlank(Constant.ZCODE_SERVER_ENGLISH_TEXT);
        }
        if (!TextUtils.isEmpty(zCodeCache)) {
            List<ZCodeItemEntity> cacheList = GsonHelper.getGson().fromJson(zCodeCache, new TypeToken<List<ZCodeItemEntity>>() {
            }.getType());
            if (cacheList != null && !cacheList.isEmpty()) {
                setZCodeList(cacheList);
            }
        }
    }

    // AMap begin

    // 声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    // 声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    // DeviceArea
    private String mMapAdcode;

    private static final String CHINA_COUNTRY = "中国";

    public String getMapAdcode() {
        return mMapAdcode;
    }

    //异步获取定位结果
    AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            refreshLocation(amapLocation);
        }
    };

    private void refreshLocation(AMapLocation amapLocation){
        if (amapLocation != null) {
            LogUtil.e(TAG, "onLocationChanged " + amapLocation.getLocationType());
            if (amapLocation.getErrorCode() == 0) {
                //解析定位结果
                String country = amapLocation.getCountry();
                country = Utils.replaceBlank(country);

                String adCode = amapLocation.getAdCode();
                adCode = Utils.replaceBlank(adCode);

                if (!TextUtils.isEmpty(adCode) && !TextUtils.equals(adCode, mMapAdcode)) {
                    LogUtil.e(TAG, adCode + " refresh api client");
                    mMapAdcode = adCode;
                    ApiClient.refreshApiClient();
                    CacheManager.getInstance().setMapAdCode(mMapAdcode);
                } else if (!TextUtils.isEmpty(country)
                        && !TextUtils.equals(country, CHINA_COUNTRY)) {
                    LogUtil.e(TAG, "out china");
                    mMapAdcode = "90000";
                    ApiClient.refreshApiClient();
                    CacheManager.getInstance().setMapAdCode(mMapAdcode);
                }
                EventBus.getDefault().post(Constant.Event.EVENT_ON_LOCATION_CHANGE);
//                    LogUtil.e(TAG, "adCode = " + amapLocation.getAdCode() + " | code = " + adCode + " | cityCode = " + amapLocation.getCityCode() + " | " + amapLocation.getCountry() + " | country length = " + country.length() + " | country length = " + amapLocation.getCountry().length() + " | equal china = " + TextUtils.equals(country, CHINA_COUNTRY) + " | " + amapLocation.getProvince() + " | city = " + amapLocation.getCity() + " | getLongitude = " + amapLocation.getLongitude() + " | getLatitude = " + amapLocation.getLatitude());
            } else {
//                    LogUtil.e(TAG, "error code = " + amapLocation.getErrorCode() + " | " + amapLocation.getErrorInfo());
            }
        }
    }

    public void initMapLocation() {
        try{
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(mAMapLocationListener);
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setHttpTimeOut(5000);
            mLocationOption.setWifiScan(true);
            mLocationOption.setMockEnable(true);
            mLocationOption.setLocationCacheEnable(true);
            //设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(5000);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            //启动定位
            mLocationClient.startLocation();
            refreshLocation(mLocationClient.getLastKnownLocation());
        }catch (Exception e){
            ToastUtils.showToast(e.getMessage());
        }

    }

    // AMap end

    // Watch begin
    private void initWatch() {
        mBleClient = CRPBleClient.create(this);
    }
    // Watch end

    // video cache
    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        App app = (App) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .cacheDirectory(VideoUtil.getVideoCacheDir(this))
                .build();
    }
    // video cache

    // language
    private void initCustomSetLanguage() {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = getSetLocale();
        resources.updateConfiguration(config, dm);
    }

    public int getLanguageType() {
        return mLanguageType;
    }

    private Locale getSetLocale() {
        mLanguageType = CacheManager.getInstance().getSettingLanguageType();
        LogUtil.e(TAG, "mLanguageType = " + mLanguageType);
        if (mLanguageType == Constant.Language.LAN_CN) {
            return Locale.CHINA;
        } else if (mLanguageType == Constant.Language.LAN_TW) {
            return Locale.TAIWAN;
        } else if (mLanguageType == Constant.Language.LAN_EN) {
            return Locale.UK;
        } else {
            return Locale.CHINA;
        }
    }

    // serial port

    private SerialPort mSerialPort;
    private boolean isInterrupted;
    private Disposable mReceiveDisposable;
    private ObservableEmitter<String> mEmitter;
    private Disposable mSendDisposable;

    public void setSerialPort(SerialPort serialPort) {
        mSerialPort = serialPort;
    }

    public SerialPort getSerialPort() {
        return mSerialPort;
    }

    public void setInterrupted(boolean interrupted) {
        isInterrupted = interrupted;
    }

    public void onReceiveSubscribe() {
        mReceiveDisposable = Flowable.create((new FlowableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(FlowableEmitter<byte[]> emitter) throws Exception {
                InputStream is = mSerialPort.getInputStream();
                int available;
                int first;
                while (!isInterrupted
                        && mSerialPort != null
                        && is != null
                        && (first = is.read()) != -1) {
                    do {
                        available = is.available();
                        SystemClock.sleep(1);
                    } while (available != is.available());

                    available = is.available();
                    byte[] bytes = new byte[available + 1];
                    is.read(bytes, 1, available);
                    bytes[0] = (byte) (first & 0xFF);
                    emitter.onNext(bytes);
                }
                close();
            }
        }), BackpressureStrategy.MISSING)
                .retry()
                .map(new Function<byte[], String>() {
                    @Override
                    public String apply(byte[] bytes) {
                        return addSpace(ByteUtils.bytesToHexString(bytes));
                    }
                })
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        try {
                            LogUtil.e(TAG, "received serial msg = " + s);
                            EventBus.getDefault().post(new SerialReceivedEvent(s));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void onSendSubscribe() {
        mSendDisposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                mEmitter = emitter;
            }
        }).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return !TextUtils.isEmpty(s);
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtil.e(TAG, "send msg = " + s);
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mSerialPort.getOutputStream()
                        .write(ByteUtils.hexStringToBytes(s));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.e(TAG, "发送成功消息 = " + s);
                    }
                });
    }

    private String addSpace(String s) {
        if (s.length() % 2 == 0) {
            StringBuilder builder = new StringBuilder();
            char[] array = s.toCharArray();
            int length = array.length;
            for (int i = 0; i < length; i += 2) {
                if (i != 0 && i <= length - 2) {
                    builder.append(" ");
                }

                builder.append(array[i]);
                builder.append(array[i + 1]);
            }

            return builder.toString();
        }
        return s;
    }

    public void sendMsg(String contain) {
        if (mEmitter != null) {
            mEmitter.onNext(contain.replace(" ", ""));
        } else {
            ToastUtils.showToast("请先打开串口！");
        }
    }

    public void close() {
        isInterrupted = true;
        disposable(mReceiveDisposable);

        disposable(mSendDisposable);
        mEmitter = null;

        if (mSerialPort != null) {
            mSerialPort.close();
        }
        mSerialPort = null;
    }

    private void disposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
