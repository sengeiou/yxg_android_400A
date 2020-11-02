package com.pcg.yuquangong.views;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crrepa.ble.conn.CRPBleDevice;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.body.LoginBody;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.LoginEntity;
import com.pcg.yuquangong.model.network.entity.ZCodeItemEntity;
import com.pcg.yuquangong.utils.GPSUtil;
import com.pcg.yuquangong.utils.GsonHelper;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.utils.PhoneUtils;
import com.pcg.yuquangong.utils.SystemUtil;
import com.pcg.yuquangong.utils.ToastUtils;

import org.angmarch.views.NiceSpinner;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.spinnerInternational)
    NiceSpinner mSpinnerInternational;
    @BindView(R.id.edtLoginName)
    EditText mEdtLoginName;
    @BindView(R.id.layLoginName)
    LinearLayout mLayLoginName;
    @BindView(R.id.edtLoginPsw)
    EditText mEdtLoginPsw;
    @BindView(R.id.tvForgetPsw)
    TextView mTvForgetPsw;
    @BindView(R.id.tvSettingWifi)
    TextView mTvSettingWifi;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    @BindView(R.id.tvDevicesResult)
    TextView mTvDevicesResult;
    @BindView(R.id.tvLoginTitle)
    TextView mTvLoginTitle;
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int GPS_REQUEST_CODE = 323;

    private String mCurrentCountryPhoneCode = "86";

    private boolean isLoginDelayed = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        App.getInstance().close();
        if (App.getInstance().mLocationClient != null) {
            App.getInstance().mLocationClient.stopLocation();
            App.getInstance().mLocationClient.onDestroy();
        }
        CRPBleDevice bleDevice = App.getInstance().getBleDevice();
        if (bleDevice != null && bleDevice.isConnected()) {
            bleDevice.disconnect();
        }
    }

    private String getUniqueId(){
        String uniqueId = null;
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        try{
            String deviceId = tm.getDeviceId();
            if(deviceId == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                deviceId = tm.getImei();
                if(deviceId == null) deviceId = tm.getImei(0);
            }
            if(!TextUtils.isEmpty(deviceId)) return deviceId;
            String sn = android.os.Build.SERIAL;
            String macAddress = SystemUtil.getMacAddressFromIp(this);
            String btAddr = BluetoothAdapter.getDefaultAdapter().getAddress();
            int deviceIdHashCode = TextUtils.isEmpty(deviceId) ? 0 : deviceId.hashCode();
//            if(deviceIdHashCode == 0) ToastUtils.showToast("无法获取设备Id");
            int macAddrHashCode = TextUtils.isEmpty(macAddress) ? 0 : macAddress.hashCode();
//            if(macAddrHashCode == 0) ToastUtils.showToast("无法获取Mac地址");
            int btAddrHashCode = TextUtils.isEmpty(btAddr) ? 0 : btAddr.hashCode();
//            if(btAddrHashCode == 0) ToastUtils.showToast("无法获取蓝牙地址");
//            UUID deviceUuid = new UUID(sn.hashCode(), ((long)deviceId.hashCode() << 32) | macAddress.hashCode());
            UUID deviceUuid = new UUID(deviceIdHashCode == 0 ? btAddrHashCode : deviceIdHashCode, macAddrHashCode);
            uniqueId = deviceUuid.toString().replaceAll("-","");
        }catch (SecurityException e){

        }
        if(uniqueId != null && uniqueId.length() > 32){
            uniqueId = uniqueId.substring(0,32);
        }
        return  uniqueId;
    }

    @OnClick(R.id.tvSettingWifi)
    void settingWifiClick() {
        Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
        startActivity(wifiSettingsIntent);
    }

    private void startNextActivity() {
        MainActivity.startActivity(mContext);
        finish();
    }

    private void pleaseOpenGpsDialog() {
        //没有打开则弹出对话框
        new AlertDialog.Builder(this)
                .setTitle(R.string.open_gps_title)
                .setMessage(R.string.open_gps_message)
                // 拒绝, 退出应用
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                .setPositiveButton(R.string.confirm,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //跳转GPS设置界面
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(intent, GPS_REQUEST_CODE);
                            }
                        })

                .setCancelable(false)
                .show();
    }

    /**
     * 检查权限
     *
     * @param
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        //获取权限列表
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            //list.toarray将集合转化为数组
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }else{
            App.getInstance().setDeviceId(getUniqueId());
            App.getInstance().initMapLocation();
        }
    }


    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        //for (循环变量类型 循环变量名称 : 要被遍历的对象)
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {      //没有授权
                showMissingPermissionDialog();              //显示提示信息
                isNeedCheck = false;
            }else{
                App.getInstance().setDeviceId(getUniqueId());
                App.getInstance().initMapLocation();
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.prompt);
        builder.setMessage(R.string.allow_permissions);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton(R.string.go_settings,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }


    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(Integer what){
        if(what == Constant.Event.EVENT_ON_LOCATION_CHANGE && isLoginDelayed){
            doLogin();
        }
    }

    private void doLogin(){
        isLoginDelayed = false;
        String userName = mEdtLoginName.getText().toString();
        String password = mEdtLoginPsw.getText().toString();

//        ToastUtils.showToast("Device-Code:"+App.getInstance().getDeviceId()+"\nDevice-Area:"+App.getInstance().getMapAdcode());
        LoginBody body = new LoginBody(userName, password);
        body.setZcode(mCurrentCountryPhoneCode);

        ApiClient.getApiService().login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<LoginEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<LoginEntity> entity) {
                        mProgressDialog.hide();
                        LogUtil.e(TAG, "login onNext");
                        if (entity.isSuccess() && entity.getData() != null) {
                            LoginEntity loginEntity = entity.getData();
                            App.getInstance().setLoginEntity(loginEntity);
                            CacheManager.getInstance().setLoginedData(GsonHelper.getGson().toJson(loginEntity));
                            if (!TextUtils.isEmpty(loginEntity.getDevice_no())) {
                                CacheManager.getInstance().setSuperLoginedDeviceNo(loginEntity.getDevice_no());
                            }
                            startNextActivity();
                        } else {
                            ToastUtils.showToast(entity.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressDialog.hide();
                        LogUtil.e(TAG, "login onError e = " + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e(TAG, "login onComplete");
                    }
                });
    }

    @OnClick(R.id.btnLogin)
    void btnLoginClick() {
        //new LoginBody("18800001122", "123456")

        if (!GPSUtil.isOPen(mContext)) {
            pleaseOpenGpsDialog();
            return;
        }

        String userName = mEdtLoginName.getText().toString();
        String password = mEdtLoginPsw.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showToast(getString(R.string.input_user_name));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(getString(R.string.psw_not_correct));
            return;
        }

        mProgressDialog.show();
        if(TextUtils.isEmpty(App.getInstance().getMapAdcode())){
            isLoginDelayed = true;
        }else{
            doLogin();
        }
    }

    private void getZCode() {
        ApiClient.getApiService().getZCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<ZCodeItemEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<List<ZCodeItemEntity>> entity) {
                        if (entity.isSuccess() && entity.getData() != null
                                && !entity.getData().isEmpty()) {
                            App.getInstance().setZCodeList(entity.getData());
                            CacheManager.getInstance().setPhoneCodeListCache(GsonHelper.getGson().toJson(entity.getData()));
                            initSpinner();
                        } else {
                            ToastUtils.showToast(entity.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "get zCode onError e = " + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.tvForgetPsw)
    void forgetPswClick() {
        SettingModifyPswActivity.startActivity(mContext);
    }

    private void initViews() {
        mProgressDialog = new ProgressDialog(this);
//        PlayVideoActivity.startActivity(mContext, "https://raw.githubusercontent.com/danikula/AndroidVideoCache/master/files/orange1.mp4");
        mTvDevicesResult.setText(CacheManager.getInstance().getSuperLoginedDeviceNo());

        mTvLoginTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnLogin.setTypeface(App.getInstance().getHomeTextTypeface());

        checkLogined();
    }

    private void checkInternationalCodeSpinner() {
        if (App.getInstance().getZCodeList().isEmpty()) {
            getZCode();
        } else {
            initSpinner();
        }
    }

    private void initSpinner() {
        mCurrentCountryPhoneCode = "" + App.getInstance().getZCodeList().get(0).getZcode();
        mSpinnerInternational.attachDataSource(App.getInstance().getZCodeList());
        mSpinnerInternational.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentCountryPhoneCode = "" + App.getInstance().getZCodeList().get(position).getZcode();
                LogUtil.e(TAG, "spinner selected = " + mCurrentCountryPhoneCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void checkLogined() {
        LoginEntity loginEntity = App.getInstance().getLoginEntity();
        if (loginEntity != null && !TextUtils.isEmpty(loginEntity.getToken())) {
//            ToastUtils.showToast("已登录，准备进入主页");
            startNextActivity();
        } else {
//            ToastUtils.showToast("未登录");
            if (!GPSUtil.isOPen(mContext)) {
                LogUtil.e(TAG, "没有开启GPS");
                pleaseOpenGpsDialog();
            } else {
                LogUtil.e(TAG, "开启GPS");
            }
            checkInternationalCodeSpinner();
        }
    }

}
