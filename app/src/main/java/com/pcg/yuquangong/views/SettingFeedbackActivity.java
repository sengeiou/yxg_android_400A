package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.body.SettingFeedbackBody;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SettingFeedbackActivity extends BaseActivity {

    @BindView(R.id.tvSettingFeedbackTitle)
    TextView mTvSettingFeedbackTitle;
    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.edtSettingFeedback)
    EditText mEdtSettingFeedback;
    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SettingFeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_feedback);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        mTvSettingFeedbackTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnConfirm.setTypeface(App.getInstance().getHomeTextTypeface());
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.btnConfirm)
    void confirmFeedback() {
        String feedbackText = mEdtSettingFeedback.getText().toString();
        if (TextUtils.isEmpty(feedbackText)) {
            return;
        }
        sendFeedback(feedbackText);
    }

    private void sendFeedback(String content) {
        ApiClient.getApiService().sendSettingFeedback(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()), new SettingFeedbackBody(content))
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

}
