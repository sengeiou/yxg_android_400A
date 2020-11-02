package com.pcg.yuquangong.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.crrepa.ble.conn.CRPBleDevice;
import com.deemons.serialportlib.SerialPort;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.base.BaseFragment;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.event.ChangeFragmentEvent;
import com.pcg.yuquangong.model.event.ExitLoginEvent;
import com.pcg.yuquangong.model.event.SerialReceivedEvent;
import com.pcg.yuquangong.model.event.WatchStartEvent;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.body.UploadAlarmBody;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.CheckAppVersionEntity;
import com.pcg.yuquangong.model.serialport.SerialPortManager;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.utils.TimeUtils;
import com.pcg.yuquangong.utils.ToastUtils;
import com.pcg.yuquangong.views.fragments.HomeFragment;
import com.pcg.yuquangong.views.fragments.SettingFragment;
import com.pcg.yuquangong.views.fragments.WatchFragment;
import com.pcg.yuquangong.views.widgets.DialogHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

//    @BindView(R.id.rlMainBg)
//    RelativeLayout mRlMainBg;
//    @BindView(R.id.tvMainHome)
//    TextView mTvMainHome;
//    @BindView(R.id.tvMainWatch)
//    TextView mTvMainWatch;
//    @BindView(R.id.tvMainSetting)
//    TextView mTvMainSetting;
//    @BindView(R.id.layMainBottom)
//    LinearLayout mLayMainBottom;
    @BindView(R.id.layMainContainer)
    RelativeLayout mLayMainContainer;

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int BOTTOM_BTN_HOME = 11;
    public static final int BOTTOM_BTN_WATCH = 12;
    public static final int BOTTOM_BTN_SETTING = 13;

    private BaseFragment mCurrentFragment;

    private HomeFragment mHomeFragment;
    private WatchFragment mWatchFragment;
    private SettingFragment mSettingFragment;

    private boolean isWatching;
    private Toast watchingTip;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        checkAppVersion();
        initViews();
        openSerialPortAndStartReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
        // 如果从main退出，那么关闭Application相关配置
        if (App.getInstance().isAppDestroyed()) {
//            App.getInstance().close();
            if (App.getInstance().mLocationClient != null) {
                App.getInstance().mLocationClient.stopLocation();
                App.getInstance().mLocationClient.onDestroy();
            }
            CRPBleDevice bleDevice = App.getInstance().getBleDevice();
            if (bleDevice != null && bleDevice.isConnected()) {
                bleDevice.disconnect();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWatchStatusChange(WatchStartEvent event){
        isWatching = event.isStarted();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(isWatching && ev.getAction() == MotionEvent.ACTION_DOWN){
//            ToastUtils.showToast(R.string.tip_watching);
            if(watchingTip == null){
                View layout = getLayoutInflater().inflate(R.layout.layout_toast_info,null);
                TextView tv = layout.findViewById(R.id.tv_toast_info);
                tv.setText(R.string.tip_watching);
                watchingTip = new Toast(this);
                watchingTip.setDuration(Toast.LENGTH_SHORT);
                watchingTip.setView(layout);
            }
            watchingTip.show();
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

//    @OnClick(R.id.tvMainHome)
//    void mainHomeClick() {
//        setCurrentContainer(BOTTOM_BTN_HOME);
//    }
//
//    @OnClick(R.id.tvMainWatch)
//    void mainWatchClick() {
//        setCurrentContainer(BOTTOM_BTN_WATCH);
//    }
//
//    @OnClick(R.id.tvMainSetting)
//    void mainSettingClick() {
//        setCurrentContainer(BOTTOM_BTN_SETTING);
//    }

    private void initViews() {
        // 进入main，设置为true
        App.getInstance().setAppDestroyed(true);

        initFragments();
//        initTextTypeface();
        setCurrentContainer(BOTTOM_BTN_HOME);
    }

    private void initFragments() {
        mHomeFragment = HomeFragment.newInstance();
        mWatchFragment = WatchFragment.newInstance();
        mSettingFragment = SettingFragment.newInstance();
    }

//    private void initTextTypeface() {
//        Typeface mTypeFace = App.getInstance().getHomeTextTypeface();
//        mTvMainHome.setTypeface(mTypeFace);
//        mTvMainWatch.setTypeface(mTypeFace);
//        mTvMainSetting.setTypeface(mTypeFace);
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeFragment(ChangeFragmentEvent event){
        switch (event.getWhich()){
            case ChangeFragmentEvent.FRAGMENT_HOME:
                setCurrentContainer(BOTTOM_BTN_HOME);
                break;
            case ChangeFragmentEvent.FRAGMENT_WATCH:
                setCurrentContainer(BOTTOM_BTN_WATCH);
                break;
            case ChangeFragmentEvent.FRAGMENT_SETTINGS:
                setCurrentContainer(BOTTOM_BTN_SETTING);
                break;
        }
    }

    private void setCurrentContainer(int bottomSelectedIndex) {

        if (bottomSelectedIndex == BOTTOM_BTN_HOME) {
//            mTvMainHome.setSelected(true);
//            mTvMainWatch.setSelected(false);
//            mTvMainSetting.setSelected(false);
            setCurrentFragment(mHomeFragment);
//            mRlMainBg.setBackgroundResource(R.mipmap.ic_main_home);
        } else if (bottomSelectedIndex == BOTTOM_BTN_WATCH) {
//            mTvMainHome.setSelected(false);
//            mTvMainWatch.setSelected(true);
//            mTvMainSetting.setSelected(false);
            setCurrentFragment(mWatchFragment);
//            mRlMainBg.setBackgroundResource(R.mipmap.ic_main_watch);
        } else if (bottomSelectedIndex == BOTTOM_BTN_SETTING) {
//            mTvMainHome.setSelected(false);
//            mTvMainWatch.setSelected(false);
//            mTvMainSetting.setSelected(true);
            setCurrentFragment(mSettingFragment);
//            mRlMainBg.setBackgroundResource(R.mipmap.ic_main_watch);
        }

    }

    private void setCurrentFragment(BaseFragment fragment) {
        if (mCurrentFragment != null && mCurrentFragment == fragment) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!(fragment instanceof HomeFragment) && mHomeFragment.isAdded()) {
            transaction.hide(mHomeFragment);
        }
        if (!(fragment instanceof WatchFragment) && mWatchFragment.isAdded()) {
            transaction.hide(mWatchFragment);
        }
        if (!(fragment instanceof SettingFragment) && mSettingFragment.isAdded()) {
            transaction.hide(mSettingFragment);
        }
        if (!fragment.isAdded()) {
            transaction.add(R.id.layMainContainer, fragment);
        } else {
            transaction.show(fragment);
        }
//        transaction.replace(R.id.layMainContainer,fragment);

        transaction.commit();
        mCurrentFragment = fragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LogUtil.e(TAG,"onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        mCurrentFragment.onActivityResult(requestCode, resultCode, data);
    }

    private void checkAppVersion() {
        ApiClient.getApiService()
                .checkAppVersion(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<CheckAppVersionEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<CheckAppVersionEntity> entity) {
                        LogUtil.e(TAG, "check app version onNext");
                        if (entity.isSuccess() && entity.getData() != null) {
                            CheckAppVersionEntity checkAppVersionEntity = entity.getData();
                            if (!TextUtils.isEmpty(checkAppVersionEntity.getVersion())
                                    && !checkAppVersionEntity.getVersion().equalsIgnoreCase("V1.0")) {
                                showAppUpdateDialog(checkAppVersionEntity);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Dialog mAppUpdateDialog;

    private boolean mIsApkDownloading;

    private void showAppUpdateDialog(final CheckAppVersionEntity entity) {
        mAppUpdateDialog = DialogHelper.getInstance().showAppUpdateDialog(mContext, entity.getVersion(),
                entity.getContent(), (entity.getIs_force() == Constant.FORCE_APP_UPDATE),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mIsApkDownloading) {
                            ToastUtils.showToast(getString(R.string.downloading));
                            return;
                        }
                        String downloadApkUrl = entity.getUrl();
                        if ((entity.getIs_force() == Constant.FORCE_APP_UPDATE)) {
                            mIsApkDownloading = true;
                            downloadApkAndInstall(downloadApkUrl, entity.getVersion(), true);
                        } else {
                            downloadApkAndInstall(downloadApkUrl, entity.getVersion(), false);
                            mAppUpdateDialog.dismiss();
                        }
                    }
                });
    }

    private void downloadApkAndInstall(String url, String versionName, boolean isForce) {
//        String apkUrl = "http://app-global.pgyer.com/ddfedb447f64404a6308738b9c42257f.apk?attname=yxg_android_v1.0.2_20190122_debug.apk&sign=f34aef4a949f6bd307b5d72ed26b8c4f&t=5c473ee7";
        String apkUrl = url;
        DownloadBuilder downloadBuilder = AllenVersionChecker
                .getInstance()
                .downloadOnly(
                        UIData.create().setDownloadUrl(apkUrl).setTitle(getString(R.string.app_update)).setContent(versionName)
                );
        downloadBuilder.setDirectDownload(true);
        downloadBuilder.setShowNotification(true);
        downloadBuilder.setShowDownloadingDialog(false);
        downloadBuilder.setShowDownloadFailDialog(false);
        downloadBuilder.executeMission(mContext);
    }

    private void openSerialPortAndStartReceiver() {
        if (App.getInstance().getSerialPort() == null) {
            SerialPort mSerialPort = SerialPortManager.getSerialPortManager().openSerailPort("/dev/ttyS3");
            if (mSerialPort != null) {
                App.getInstance().setSerialPort(mSerialPort);
                App.getInstance().setInterrupted(false);
                App.getInstance().onReceiveSubscribe();
                App.getInstance().onSendSubscribe();
//                ToastUtils.showToast("打开串口成功！");
                LogUtil.e(TAG,"打开串口成功");
                App.getInstance().sendMsg(SerialPortManager.QUERY_DEVICE_STATE);
                delayToQueryDeviceAlarmState();
            } else {
//                ToastUtils.showToast("打开串口失败！");
                LogUtil.e(TAG,"打开串口失败");
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 7878) {
                App.getInstance().sendMsg(SerialPortManager.DEVICE_ALARM_STATE);
            }
        }
    };

    private void delayToQueryDeviceAlarmState() {
        mHandler.sendEmptyMessageDelayed(7878, 6000);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveDeviceAlarmState(SerialReceivedEvent event) {
        String data = event.getReceivedMsg();
        if (!TextUtils.isEmpty(data)) {
            String[] dataArray = data.split(" ");
            if (dataArray != null && dataArray.length == 2) {
                UploadAlarmBody body = new UploadAlarmBody();
                if (TextUtils.equals(dataArray[0], "4F")) {
                    body.setIs_ct(Constant.Alarm.ALARM_OK_STATE);
                } else {
                    body.setIs_ct(Constant.Alarm.ALARM_STATE);
                }

                if (TextUtils.equals(dataArray[1], "4B")) {
                    body.setIs_zb(Constant.Alarm.ALARM_OK_STATE);
                } else {
                    body.setIs_zb(Constant.Alarm.ALARM_STATE);
                }

                if (body.getIs_ct() == 1 || body.getIs_zb() == 1) {
                    uploadAlarm(body);
                }

            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveExitLoginEvent(ExitLoginEvent event) {
        exitLogin();
    }

    private void exitLogin() {
        ApiClient.getApiService().logout(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<String> entity) {
                        if (entity.isSuccess()) {
                            logoutSuccessLogic();
                        } else {
                            ToastUtils.showToast(entity.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        logoutSuccessLogic();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void logoutSuccessLogic() {
        clearLoginCache();
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
        finish();
    }

    private void clearLoginCache() {
        App.getInstance().setLoginEntity(null);
        CacheManager.getInstance().setLoginedData("");
    }

    private void uploadAlarm(UploadAlarmBody body) {

        ApiClient.getApiService().uploadAlarm(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<String> entity) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
