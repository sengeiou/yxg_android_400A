package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.cache.CacheManager;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingLanguageActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.tvSettingLgTitle)
    TextView mTvSettingLgTitle;
    @BindView(R.id.ivSettingLgCNChecked)
    ImageView mIvSettingLgCNChecked;
    @BindView(R.id.laySettingLgCN)
    LinearLayout mLaySettingLgCN;
    @BindView(R.id.ivSettingLgTwChecked)
    ImageView mIvSettingLgTwChecked;
    @BindView(R.id.laySettingLgTw)
    LinearLayout mLaySettingLgTw;
    @BindView(R.id.ivSettingLgEnglishChecked)
    ImageView mIvSettingLgEnglishChecked;
    @BindView(R.id.laySettingLgEnglish)
    LinearLayout mLaySettingLgEnglish;

    private int mCurrentLanType;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SettingLanguageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_language);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        mTvSettingLgTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());

        mCurrentLanType = CacheManager.getInstance().getSettingLanguageType();

        switch (mCurrentLanType) {
            case Constant.Language.LAN_CN:

                mIvSettingLgCNChecked.setVisibility(View.VISIBLE);
                mIvSettingLgTwChecked.setVisibility(View.GONE);
                mIvSettingLgEnglishChecked.setVisibility(View.GONE);

                break;

            case Constant.Language.LAN_TW:

                mIvSettingLgCNChecked.setVisibility(View.GONE);
                mIvSettingLgTwChecked.setVisibility(View.VISIBLE);
                mIvSettingLgEnglishChecked.setVisibility(View.GONE);

                break;

            case Constant.Language.LAN_EN:

                mIvSettingLgCNChecked.setVisibility(View.GONE);
                mIvSettingLgTwChecked.setVisibility(View.GONE);
                mIvSettingLgEnglishChecked.setVisibility(View.VISIBLE);

                break;
        }
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.laySettingLgCN)
    void languageCnClick() {
        mIvSettingLgCNChecked.setVisibility(View.VISIBLE);
        mIvSettingLgTwChecked.setVisibility(View.GONE);
        mIvSettingLgEnglishChecked.setVisibility(View.GONE);

        changeLanguage(Constant.Language.LAN_CN);
    }

    @OnClick(R.id.laySettingLgTw)
    void languageTwClick() {
        mIvSettingLgCNChecked.setVisibility(View.GONE);
        mIvSettingLgTwChecked.setVisibility(View.VISIBLE);
        mIvSettingLgEnglishChecked.setVisibility(View.GONE);

        changeLanguage(Constant.Language.LAN_TW);
    }

    @OnClick(R.id.laySettingLgEnglish)
    void languageEnglishClick() {
        mIvSettingLgCNChecked.setVisibility(View.GONE);
        mIvSettingLgTwChecked.setVisibility(View.GONE);
        mIvSettingLgEnglishChecked.setVisibility(View.VISIBLE);

        changeLanguage(Constant.Language.LAN_EN);
    }

    private void changeLanguage(int languageType) {
        if (languageType == mCurrentLanType) {
            return;
        }

        CacheManager.getInstance().setSettingLanguageType(languageType);

        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();

        switch (languageType) {
            case Constant
                    .Language.LAN_CN:
                // 应用用户选择语言
                config.locale = Locale.CHINA;
                break;
            case Constant
                    .Language.LAN_TW:
                // 应用用户选择语言
                config.locale = Locale.TAIWAN;
                break;
            case Constant
                    .Language.LAN_EN:
                // 应用用户选择语言
                config.locale = Locale.UK;
                break;
        }

        resources.updateConfiguration(config, dm);

        restartApp();
    }

    private void restartApp() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        // 杀掉进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
