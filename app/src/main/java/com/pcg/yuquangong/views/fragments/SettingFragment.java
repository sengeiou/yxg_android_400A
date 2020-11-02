package com.pcg.yuquangong.views.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseFragment;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.event.ChangeFragmentEvent;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.utils.TimeUtils;
import com.pcg.yuquangong.utils.ToastUtils;
import com.pcg.yuquangong.views.LoginActivity;
import com.pcg.yuquangong.views.SettingAboutUsActivity;
import com.pcg.yuquangong.views.SettingFeedbackActivity;
import com.pcg.yuquangong.views.SettingLanguageActivity;
import com.pcg.yuquangong.views.SettingModifyPswActivity;
import com.pcg.yuquangong.views.SettingMyDevicesActivity;
import com.pcg.yuquangong.views.SettingMyInfoActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SettingFragment extends BaseFragment {

    @BindView(R.id.tvFragmentSettingTitle)
    TextView mTvFragmentSettingTitle;
    @BindView(R.id.tvExitLogin)
    TextView mTvExitLogin;
    @BindView(R.id.laySettingMyInfo)
    LinearLayout mLaySettingMyInfo;
    @BindView(R.id.laySettingModifyPsw)
    LinearLayout mLaySettingModifyPsw;
    @BindView(R.id.laySettingAudioTips)
    LinearLayout mLaySettingAudioTips;
    @BindView(R.id.ivAudioTipState)
    ImageView mIvAudioTipState;
    @BindView(R.id.laySettingLanguage)
    LinearLayout mLaySettingLanguage;
    @BindView(R.id.laySettingMyDevices)
    LinearLayout mLaySettingMyDevices;
    @BindView(R.id.laySettingAbout)
    LinearLayout mLaySettingAbout;
    @BindView(R.id.laySettingFeedback)
    LinearLayout mLaySettingFeedback;
    @BindView(R.id.laySettingWifi)
    LinearLayout mLaySettingWifi;
    @BindView(R.id.tvMainTime)
    TextView mTvMainTime;
    @BindView(R.id.tvSettingMyInfo)
    TextView mTvSettingMyInfo;
    @BindView(R.id.tvSettingModifyPsw)
    TextView mTvSettingModifyPsw;
    @BindView(R.id.tvSettingLanguage)
    TextView mTvSettingLanguage;
    @BindView(R.id.tvSettingFeedback)
    TextView mTvSettingFeedback;
    @BindView(R.id.tvSettingMyDevices)
    TextView mTvSettingMyDevices;
    @BindView(R.id.tvSettingAudioTips)
    TextView mTvSettingAudioTips;
    @BindView(R.id.tvSettingAbout)
    TextView mTvSettingAbout;
    @BindView(R.id.tvSettingWifi)
    TextView mTvSettingWifi;
    Unbinder unbinder;

    @BindView(R.id.tvMainHome)
    TextView mTvMainHome;
    @BindView(R.id.tvMainWatch)
    TextView mTvMainWatch;
    @BindView(R.id.tvMainSetting)
    TextView mTvMainSetting;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        initTextTypeface();
        return view;
    }

    private void initTextTypeface() {
        Typeface mTypeFace = App.getInstance().getHomeTextTypeface();
        mTvMainHome.setTypeface(mTypeFace);
        mTvMainWatch.setTypeface(mTypeFace);
        mTvMainSetting.setTypeface(mTypeFace);
    }

    @OnClick(R.id.tvMainHome)
    void mainHomeClick() {
        EventBus.getDefault().post(new ChangeFragmentEvent(ChangeFragmentEvent.FRAGMENT_HOME));
    }

    @OnClick(R.id.tvMainWatch)
    void mainWatchClick() {
        EventBus.getDefault().post(new ChangeFragmentEvent(ChangeFragmentEvent.FRAGMENT_WATCH));
    }

    @OnClick(R.id.tvMainSetting)
    void mainSettingClick() {
        EventBus.getDefault().post(new ChangeFragmentEvent(ChangeFragmentEvent.FRAGMENT_SETTINGS));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tvExitLogin)
    void exitLoginClick() {
        exitLogin();
    }

    @OnClick(R.id.laySettingWifi)
    void wifiConnectClick() {
        Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
        startActivity(wifiSettingsIntent);
    }

    private void intervalShowCurrentTime() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mTvMainTime.setText(TimeUtils.getNowTimeString("HH:mm:ss"));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
        App.getInstance().setAppDestroyed(false);
        clearLoginCache();
        LoginActivity.startActivity(mContext);
        getActivity().finish();
    }

    private void clearLoginCache() {
        App.getInstance().setLoginEntity(null);
        CacheManager.getInstance().setLoginedData("");
    }

    @OnClick(R.id.laySettingMyInfo)
    void myInfoClick() {
//        ToastUtils.showToast("我的信息");
        SettingMyInfoActivity.startActivity(mContext);
    }

    @OnClick(R.id.laySettingModifyPsw)
    void modifyPswClick() {
//        ToastUtils.showToast("修改密码");
        SettingModifyPswActivity.startActivity(mContext);
    }

    @OnClick(R.id.laySettingAudioTips)
    void audioTipsClick() {
//        ToastUtils.showToast("语音提示");
        if (CacheManager.getInstance().isSettingAudioTipsOpen()) {
            mIvAudioTipState.setImageResource(R.mipmap.ic_setting_audio_close);
            CacheManager.getInstance().setSettingAudioTipsOpen(false);
        } else {
            mIvAudioTipState.setImageResource(R.mipmap.ic_setting_audio_open);
            CacheManager.getInstance().setSettingAudioTipsOpen(true);
        }
    }

    @OnClick(R.id.laySettingLanguage)
    void settingLanguageClick() {
//        ToastUtils.showToast("语言设置");
        SettingLanguageActivity.startActivity(mContext);
    }

    @OnClick(R.id.laySettingMyDevices)
    void myDevicesClick() {
//        ToastUtils.showToast("我的设备");
        SettingMyDevicesActivity.startActivity(mContext);
    }

//    @OnClick(R.id.laySettingHelper)
//    void settingHelperClick() {
////        ToastUtils.showToast("使用帮助");
//        WebViewActivity.startActivity(mContext, "https://www.baidu.com/");
//    }

    @OnClick(R.id.laySettingAbout)
    void settingAboutClick() {
//        ToastUtils.showToast("关于我们");
        SettingAboutUsActivity.startActivity(mContext);
    }

    @OnClick(R.id.laySettingFeedback)
    void feedbackClick() {
//        ToastUtils.showToast("用户反馈");
        SettingFeedbackActivity.startActivity(mContext);
    }

    private void initViews() {
        mTvFragmentSettingTitle.setTypeface(App.getInstance().getHomeTextTypeface());

        mTvSettingAbout.setTypeface(App.getInstance().getRegularTypeface());
        mTvSettingAudioTips.setTypeface(App.getInstance().getRegularTypeface());
        mTvSettingFeedback.setTypeface(App.getInstance().getRegularTypeface());
        mTvSettingLanguage.setTypeface(App.getInstance().getRegularTypeface());
        mTvSettingModifyPsw.setTypeface(App.getInstance().getRegularTypeface());
        mTvSettingMyDevices.setTypeface(App.getInstance().getRegularTypeface());
        mTvSettingMyInfo.setTypeface(App.getInstance().getRegularTypeface());
        mTvSettingWifi.setTypeface(App.getInstance().getRegularTypeface());

        mTvExitLogin.setTypeface(App.getInstance().getRegularTypeface());
        mTvMainTime.setTypeface(App.getInstance().getRegularTypeface());

        if (CacheManager.getInstance().isSettingAudioTipsOpen()) {
            mIvAudioTipState.setImageResource(R.mipmap.ic_setting_audio_open);
        } else {
            mIvAudioTipState.setImageResource(R.mipmap.ic_setting_audio_close);
        }
        intervalShowCurrentTime();

        mTvMainSetting.setSelected(true);
    }

}
