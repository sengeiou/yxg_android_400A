package com.pcg.yuquangong.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.GlideApp;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.body.ModifyProfileInfoBody;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.SettingProfileInfoEntity;
import com.pcg.yuquangong.utils.GsonHelper;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.utils.ToastUtils;
import com.pcg.yuquangong.utils.Utils;
import com.pcg.yuquangong.views.widgets.DialogHelper;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.model.TakePhotoOptions;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SettingMyInfoActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.ivSettingMyInfoAvatar)
    CircularImageView mIvSettingMyInfoAvatar;
    @BindView(R.id.tvAddModifyMemberAvatar)
    TextView mTvAddModifyMemberAvatar;
    @BindView(R.id.edtName)
    EditText mEdtName;
    @BindView(R.id.tvGender)
    TextView mTvGender;
    @BindView(R.id.tvBirthday)
    TextView mTvBirthday;
    @BindView(R.id.edtPhone)
    EditText mEdtPhone;
    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;
    @BindView(R.id.tvSettingMyInfoTitle)
    TextView mTvSettingMyInfoTitle;

    private static final String TAG = SettingMyInfoActivity.class.getSimpleName();

    public static final int REQUEST_BIRTHDAY_CODE = 11;

    private SettingProfileInfoEntity mCacheProfileCacheEntity;

    private String mBirthday;
    private int mCalendar;
    private int mGender;

    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    public static final int GALLERY_LIMIT = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_my_info);
        ButterKnife.bind(this);

        initViews();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SettingMyInfoActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.ivSettingMyInfoAvatar)
    void chooseGalleryImageClick() {
        startSystemGalleryActivity();
    }

    @OnClick(R.id.tvBirthday)
    void birthdayClick() {
        ChooseBirthdayActivity.startActivityForResult(this, REQUEST_BIRTHDAY_CODE);
    }

    @OnClick(R.id.tvGender)
    void genderClick() {
        DialogHelper.getInstance().showGenderDialog(mContext, mGender, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGender = Constant.Member.GENDER_BOY;
                mTvGender.setText(getString(R.string.boy));
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGender = Constant.Member.GENDER_GIRL;
                mTvGender.setText(getString(R.string.girl));
            }
        });
    }

    @OnClick(R.id.btnConfirm)
    void btnConfirmClick() {
        String name = mEdtName.getText().toString();
        String phone = mEdtPhone.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)
                && !TextUtils.isEmpty(mBirthday) && mCalendar != 0 && mGender != 0) {
            modifyProfileInfo(phone, name, mGender, mBirthday);
        } else {
            ToastUtils.showToast(getString(R.string.please_input_info));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "onActivityResult requestCode = " + requestCode + " | resultCode = " + resultCode);
        if (data != null && requestCode == REQUEST_BIRTHDAY_CODE && resultCode == Activity.RESULT_OK) {
            LogUtil.e(TAG, "onActivityResult requestCode REQUEST_BIRTHDAY_CODE");
            String year = data.getStringExtra(Constant.Member.KEY_BIRTHDAY_YEAR);
            String month = data.getStringExtra(Constant.Member.KEY_BIRTHDAY_MONTH);
            String day = data.getStringExtra(Constant.Member.KEY_BIRTHDAY_DAY);
            int calendarType = data.getIntExtra(Constant.Member.KEY_BIRTHDAY_CALENDAR_TYPE, Constant.Member.CALENDAR);

            mBirthday = year + "-" + month + "-" + day;
            mCalendar = calendarType;

            mTvBirthday.setText(mBirthday + getCalendarText(calendarType));
        } else if (data != null && resultCode == Activity.RESULT_OK) {
            LogUtil.e(TAG, "onActivityResult requestCode gallery");
            getTakePhoto().onActivityResult(requestCode, resultCode, data);
        }
    }

    private String getCalendarText(int calendarType) {
        if (calendarType == Constant.Member.CALENDAR) {
            return "(" + getString(R.string.calendar) + ")";
        } else if (calendarType == Constant.Member.LUNAR_CALENDAR) {
            return "(" + getString(R.string.lunar_calendar) + ")";
        } else {
            return "";
        }
    }

    private void initViews() {
        String data = CacheManager.getInstance().getSettingProfileInfo();
        if (!TextUtils.isEmpty(data)) {
            mCacheProfileCacheEntity = GsonHelper.getGson().fromJson(data, SettingProfileInfoEntity.class);
            if (mCacheProfileCacheEntity != null) {
                showSettingProfileInfo(mCacheProfileCacheEntity);
            }
        }
        getProfileInfo();

        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnConfirm.setTypeface(App.getInstance().getHomeTextTypeface());
        mTvSettingMyInfoTitle.setTypeface(App.getInstance().getHomeTextTypeface());
    }

    private void showSettingProfileInfo(SettingProfileInfoEntity entity) {
        mBirthday = entity.getBirthday();
        mGender = entity.getGender();
        mCalendar = entity.getCalendar();

        mEdtName.setText(entity.getName());
        mTvBirthday.setText(mBirthday + getCalendarText(entity.getCalendar()));
        mEdtPhone.setText(entity.getMobile());

        if (mGender == Constant.Member.GENDER_BOY) {
            mTvGender.setText(getString(R.string.boy));
        } else if (mGender == Constant.Member.GENDER_GIRL) {
            mTvGender.setText(getString(R.string.girl));
        }
        if (!TextUtils.isEmpty(entity.getAvatar_link())) {
            mTvAddModifyMemberAvatar.setVisibility(View.GONE);
            mIvSettingMyInfoAvatar.setVisibility(View.VISIBLE);
            GlideApp.with(mContext)
                    .load(Utils.replaceBlank(entity.getAvatar_link()))
                    .placeholder(R.mipmap.ic_detault_avatar)
                    .override(200, 200)
                    .into(mIvSettingMyInfoAvatar);
        } else {
            mTvAddModifyMemberAvatar.setVisibility(View.GONE);
            mIvSettingMyInfoAvatar.setVisibility(View.VISIBLE);
            mIvSettingMyInfoAvatar.setImageResource(R.mipmap.ic_detault_avatar);
        }

    }

    private void modifyProfileInfo(String phone, String name, int gender, String birthday) {
        ModifyProfileInfoBody body = new ModifyProfileInfoBody(phone, name, gender, birthday);
        body.setCalendar(mCalendar);
        ApiClient.getApiService()
                .modifyProfileInfo(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()),
                        body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<SettingProfileInfoEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<SettingProfileInfoEntity> entity) {
                        if (entity.isSuccess() && entity.getData() != null) {
                            mCacheProfileCacheEntity = entity.getData();
//                            showSettingProfileInfo(mCacheProfileCacheEntity);
                            CacheManager.getInstance().setSettingProfileInfo(GsonHelper.getGson().toJson(mCacheProfileCacheEntity));
                            ToastUtils.showToast(getString(R.string.submit_success));
                            finish();
                        } else {
                            ToastUtils.showToast(entity.getMsg());
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

    private void getProfileInfo() {

        ApiClient.getApiService()
                .getProfileInfo(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<SettingProfileInfoEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<SettingProfileInfoEntity> entity) {
                        if (entity.isSuccess() && entity.getData() != null) {
                            mCacheProfileCacheEntity = entity.getData();
                            showSettingProfileInfo(mCacheProfileCacheEntity);
                            CacheManager.getInstance().setSettingProfileInfo(GsonHelper.getGson().toJson(mCacheProfileCacheEntity));
                        } else {
                            ToastUtils.showToast(entity.getMsg());
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        LogUtil.e(TAG, "takeSuccess result");
        if (result.getImages() != null && !result.getImages().isEmpty()) {
            TImage itemImage = result.getImages().get(0);
            String filePath = itemImage.getCompressPath();
            LogUtil.e(TAG, "takeSuccess result filePath = " + filePath);
            mTvAddModifyMemberAvatar.setVisibility(View.GONE);
            mIvSettingMyInfoAvatar.setVisibility(View.VISIBLE);


            File file = new File(filePath);
            Uri imageUri = Uri.fromFile(file);

//            if (!TextUtils.isEmpty(filePath)) {
//                mIvSettingMyInfoAvatar.setImageURI(imageUri);
//            }

            GlideApp.with(mContext)
                    .load(imageUri)
                    .placeholder(R.mipmap.ic_detault_avatar)
                    .override(200, 200)
                    .into(mIvSettingMyInfoAvatar);
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        LogUtil.e(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        LogUtil.e(TAG, "takeCancel");
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 800 * 1024;
        int width = 720;
        int height = 1280;
        boolean showProgressBar = true;
        boolean enableRawFile = true;
        CompressConfig config;
        config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();
        takePhoto.onEnableCompress(config, showProgressBar);
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    private void startSystemGalleryActivity() {
        LogUtil.e(TAG, "startSystemGalleryActivity");
        configCompress(takePhoto);

        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + "current_avatar.jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
    }

    private CropOptions getCropOptions() {
        int height = 200;
        int width = 200;

        CropOptions.Builder builder = new CropOptions.Builder();

//        builder.setAspectX(width).setAspectY(height);
        builder.setOutputX(width).setOutputY(height);
        builder.setWithOwnCrop(true);
        return builder.create();
    }

}
