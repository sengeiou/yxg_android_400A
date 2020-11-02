package com.pcg.yuquangong.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.GlideApp;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.event.RefreshMemberListEvent;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.body.AddMemberBody;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.MemberListItemEntity;
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
import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddModifyMemberActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.tvAddModifyMemberTitle)
    TextView mTvAddModifyMemberTitle;
    @BindView(R.id.ivAddModifyMemberAvatar)
    CircularImageView mIvAddModifyMemberAvatar;
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
    @BindView(R.id.btnMemberDelete)
    Button mBtnMemberDelete;

    private static final String TAG = AddModifyMemberActivity.class.getSimpleName();

    public static final int REQUEST_BIRTHDAY_CODE = 11;

    private MemberListItemEntity mItemEntity;

    private String mBirthday;
    private int mCalendar;
    private int mGender;

    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    public static final int GALLERY_LIMIT = 1;

    private boolean mIsDoing;

    public static void startActivity(Context context, MemberListItemEntity itemEntity) {
        Intent intent = new Intent();
        intent.setClass(context, AddModifyMemberActivity.class);
        intent.putExtra("item_entity", itemEntity);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_member);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initData() {
        mItemEntity = getIntent().getParcelableExtra("item_entity");
    }

    private void initView() {
        if (mItemEntity != null) {
            mBirthday = mItemEntity.getBirthday();
            mGender = mItemEntity.getGender();
            mCalendar = mItemEntity.getCalendar();
            showDefaultView();
            mBtnMemberDelete.setVisibility(View.VISIBLE);
        } else {
            mBtnMemberDelete.setVisibility(View.GONE);
        }
        mTvAddModifyMemberTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnConfirm.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnMemberDelete.setTypeface(App.getInstance().getHomeTextTypeface());
    }

    private void showDefaultView() {
        mEdtName.setText(mItemEntity.getName());
        mEdtPhone.setText(mItemEntity.getMobile());
        mTvBirthday.setText(mBirthday + getCalendarText(mItemEntity.getCalendar()));
        if (mGender == Constant.Member.GENDER_BOY) {
            mTvGender.setText(getString(R.string.boy));
        } else if (mGender == Constant.Member.GENDER_GIRL) {
            mTvGender.setText(getString(R.string.girl));
        }
        if (!TextUtils.isEmpty(mItemEntity.getAvatar_link())) {
            mTvAddModifyMemberAvatar.setVisibility(View.GONE);
            mIvAddModifyMemberAvatar.setVisibility(View.VISIBLE);
            GlideApp.with(mContext).load(Utils.replaceBlank(mItemEntity.getAvatar_link()))
                    .placeholder(R.mipmap.ic_detault_avatar)
                    .override(300, 300)
                    .centerCrop()
                    .into(mIvAddModifyMemberAvatar);
        } else {
            mTvAddModifyMemberAvatar.setVisibility(View.GONE);
            mIvAddModifyMemberAvatar.setVisibility(View.VISIBLE);
            mIvAddModifyMemberAvatar.setImageResource(R.mipmap.ic_detault_avatar);
        }
    }

    private String getCalendarText(int calendarType) {
        if (calendarType == Constant.Member.CALENDAR) {
            return "(" + getString(R.string.calendar) + ")";
        } else if(calendarType == Constant.Member.LUNAR_CALENDAR){
            return "(" + getString(R.string.lunar_calendar) + ")";
        } else {
            return "";
        }
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.ivAddModifyMemberAvatar)
    void chooseGalleryImageClick() {
        startSystemGalleryActivity();
    }

    @OnClick(R.id.btnMemberDelete)
    void btnMemberDeleteClick() {
        if (mIsDoing) {
            return;
        }
        mIsDoing = true;
        startDeleteMember();
    }

    private void startDeleteMember() {
        ApiClient.getApiService().deleteMember(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()), mItemEntity.getId())
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
                            ToastUtils.showToast(getString(R.string.submit_success));
                            EventBus.getDefault().post(new RefreshMemberListEvent());
                            finish();
                        } else {
                            ToastUtils.showToast(entity.getMsg());
                            mIsDoing = false;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIsDoing = false;
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
        if (mIsDoing) {
            return;
        }
        mIsDoing = true;
        String name = mEdtName.getText().toString();
        String phone = mEdtPhone.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)
                && !TextUtils.isEmpty(mBirthday) && mCalendar != 0 && mGender != 0) {
            if (mItemEntity != null) {
                modifyMember(name, phone);
            } else {
                addMember(name, phone);
            }
        } else {
            ToastUtils.showToast(getString(R.string.please_input_info));
            mIsDoing = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_BIRTHDAY_CODE && resultCode == Activity.RESULT_OK) {
            String year = data.getStringExtra(Constant.Member.KEY_BIRTHDAY_YEAR);
            String month = data.getStringExtra(Constant.Member.KEY_BIRTHDAY_MONTH);
            String day = data.getStringExtra(Constant.Member.KEY_BIRTHDAY_DAY);
            int calendarType = data.getIntExtra(Constant.Member.KEY_BIRTHDAY_CALENDAR_TYPE, Constant.Member.CALENDAR);

            mBirthday = year + "-" + month + "-" + day;
            mCalendar = calendarType;

            mTvBirthday.setText(mBirthday + getCalendarText(mCalendar));
        }
    }

    private void modifyMember(String name, String phone) {
        AddMemberBody body = new AddMemberBody();
        body.setName(name);
        body.setMobile(phone);
        body.setBirthday(mBirthday);
        body.setCalendar(mCalendar);
        body.setGender(mGender);
        body.setAvatar("afdsfasfsdafdsfasfsdafdsfasfsd");

        ApiClient.getApiService().modifyMember(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()), mItemEntity.getId(), body)
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
                            ToastUtils.showToast(getString(R.string.submit_success));
                            EventBus.getDefault().post(new RefreshMemberListEvent());
                            finish();
                        } else {
                            ToastUtils.showToast(entity.getMsg());
                            mIsDoing = false;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIsDoing = false;
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addMember(String name, String phone) {
        AddMemberBody body = new AddMemberBody();
        body.setName(name);
        body.setMobile(phone);
        body.setBirthday(mBirthday);
        body.setCalendar(mCalendar);
        body.setGender(mGender);
        body.setAvatar("afdsfasfsdafdsfasfsdafdsfasfsd");

        ApiClient.getApiService().addMember(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()), body)
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
                            ToastUtils.showToast(getString(R.string.submit_success));
                            EventBus.getDefault().post(new RefreshMemberListEvent());
                            finish();
                        } else {
                            ToastUtils.showToast(entity.getMsg());
                            mIsDoing = false;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIsDoing = false;
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
        if (result.getImages() != null && !result.getImages().isEmpty()) {
            TImage itemImage = result.getImages().get(0);
            String filePath = itemImage.getCompressPath();
            mTvAddModifyMemberAvatar.setVisibility(View.GONE);
            mIvAddModifyMemberAvatar.setVisibility(View.VISIBLE);

            File file = new File(filePath);
            Uri imageUri = Uri.fromFile(file);

//            if (!TextUtils.isEmpty(filePath)) {
//                mIvSettingMyInfoAvatar.setImageURI(imageUri);
//            }

            GlideApp.with(mContext)
                    .load(imageUri)
                    .override(300, 300)
                    .into(mIvAddModifyMemberAvatar);
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

        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + "member_avatar.jpg");
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
