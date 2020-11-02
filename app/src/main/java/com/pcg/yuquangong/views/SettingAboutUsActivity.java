package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.SettingAboutEntity;
import com.pcg.yuquangong.utils.GsonHelper;
import com.pcg.yuquangong.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SettingAboutUsActivity extends BaseActivity {


    @BindView(R.id.tvSettingAboutUsTitle)
    TextView mTvSettingAboutUsTitle;
    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.ivSettingAboutUsLogo)
    ImageView mIvSettingAboutUsLogo;
    @BindView(R.id.tvSettingAboutUsCompanyPhone)
    TextView mTvSettingAboutUsCompanyPhone;
    @BindView(R.id.tvSettingAboutUsCompanyEmail)
    TextView mTvSettingAboutUsCompanyEmail;
    @BindView(R.id.tvSettingAboutUsWebchatPublic)
    TextView mTvSettingAboutUsWebchatPublic;
    @BindView(R.id.tvSettingAboutUsCompanyAddress)
    TextView mTvSettingAboutUsCompanyAddress;
    @BindView(R.id.layAboutUsInfo)
    LinearLayout mLayAboutUsInfo;
    @BindView(R.id.tvAboutComp)
    TextView mTvAboutComp;
    @BindView(R.id.tvBottomAbout)
    TextView mTvBottomAbout;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SettingAboutUsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about_us);
        ButterKnife.bind(this);

        initViews();
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    private void initViews() {
//        mTvSettingAboutUsVersionCode.setText(getString(R.string.about_us_version, "V1.0"));
        String data = CacheManager.getInstance().getSettingAboutInfo();
        if (!TextUtils.isEmpty(data)) {
            SettingAboutEntity cacheAboutEntity = GsonHelper.getGson().fromJson(data, SettingAboutEntity.class);
            if (cacheAboutEntity != null) {
                showSettingAboutInfo(cacheAboutEntity);
            }
        }
        getAboutInfo();

        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mTvSettingAboutUsTitle.setTypeface(App.getInstance().getHomeTextTypeface());
    }

    private void showSettingAboutInfo(SettingAboutEntity entity) {
        mTvSettingAboutUsCompanyPhone.setText(getString(R.string.about_us_company_phone, entity.getTel()));
        mTvSettingAboutUsCompanyEmail.setText(getString(R.string.about_us_company_email, entity.getEmail()));
        mTvSettingAboutUsWebchatPublic.setText(getString(R.string.about_us_company_webchat_public, entity.getWechat()));
        mTvSettingAboutUsCompanyAddress.setText(getString(R.string.about_us_company_address, entity.getAddress()));
    }

    private void getAboutInfo() {
        ApiClient.getApiService()
                .getSettingAboutUs(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<SettingAboutEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<SettingAboutEntity> entity) {
                        if (entity.isSuccess() && entity.getData() != null) {
                            SettingAboutEntity settingEntity = entity.getData();
                            showSettingAboutInfo(settingEntity);
                            CacheManager.getInstance().setSettingAboutInfo(GsonHelper.getGson().toJson(settingEntity));
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

}
