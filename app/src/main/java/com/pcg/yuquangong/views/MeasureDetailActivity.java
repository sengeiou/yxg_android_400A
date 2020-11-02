package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.event.AddSurveryRecordEvent;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.body.AddSurveryBody;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MeasureDetailActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.tvMeasureDetailTitle)
    TextView mTvMeasureDetailTitle;
    @BindView(R.id.tvMeasureHeartRateResult)
    TextView mTvMeasureHeartRateResult;
    @BindView(R.id.ivMeasureHeartRate)
    ImageView mIvMeasureHeartRate;
    @BindView(R.id.ivMeasureHeartRatePoint)
    ImageView mIvMeasureHeartRatePoint;
    @BindView(R.id.tvMeasureHeartRateTips)
    TextView mTvMeasureHeartRateTips;
    @BindView(R.id.tvMeasureBloodPressureResult)
    TextView mTvMeasureBloodPressureResult;
    @BindView(R.id.ivMeasureBloodPressure)
    ImageView mIvMeasureBloodPressure;
    @BindView(R.id.ivMeasureBloodPressurePoint)
    ImageView mIvMeasureBloodPressurePoint;
    @BindView(R.id.tvMeasureBloodPressureTips)
    TextView mTvMeasureBloodPressureTips;
    @BindView(R.id.ivMeasureBloodOxygen)
    ImageView mIvMeasureBloodOxygen;
    @BindView(R.id.ivMeasureBloodOxygenPoint)
    ImageView mIvMeasureBloodOxygenPoint;
    @BindView(R.id.tvMeasureBloodOxygenResult)
    TextView mTvMeasureBloodOxygenResult;
    @BindView(R.id.tvMeasureBloodOxygenTips)
    TextView mTvMeasureBloodOxygenTips;

    public static final String TAG = MeasureDetailActivity.class.getSimpleName();

    private int mMemberId;
    private int mBloodOxygen;
    private int mHeartRate;
    private int mBloodPresureSbp;
    private int mBloodPresureDbp;

    public static void startActivity(Context context, int memberId, int bloodOxygen, int heartRate,
                                     int bloodPresureSbp, int bloodPresureDbp) {
        Intent intent = new Intent();
        intent.setClass(context, MeasureDetailActivity.class);
        intent.putExtra("member_id", memberId);
        intent.putExtra("blood_oxygen", bloodOxygen);
        intent.putExtra("heart_rate", heartRate);
        intent.putExtra("blood_presure_sbp", bloodPresureSbp);
        intent.putExtra("blood_presure_dbp", bloodPresureDbp);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_detail);
        ButterKnife.bind(this);

        initData();
        initViews();

        LogUtil.e(TAG,"mMemberId = " + mMemberId);

        if (mMemberId != Constant.JUST_SHOW_WATCH_RECORD_DETAIL) {
            uploadMeasureRecord();
        }
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    private void initData() {
        mMemberId = getIntent().getIntExtra("member_id", 0);
        mBloodOxygen = getIntent().getIntExtra("blood_oxygen", 0);
        mHeartRate = getIntent().getIntExtra("heart_rate", 0);
        mBloodPresureSbp = getIntent().getIntExtra("blood_presure_sbp", 0);
        mBloodPresureDbp = getIntent().getIntExtra("blood_presure_dbp", 0);
    }

    private void initViews() {
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mTvMeasureDetailTitle.setTypeface(App.getInstance().getHomeTextTypeface());

        mTvMeasureHeartRateResult.setText(String.valueOf(mHeartRate));
        mTvMeasureBloodPressureResult.setText(String.valueOf(mBloodPresureDbp) + "/" + String.valueOf(mBloodPresureSbp));
        mTvMeasureBloodOxygenResult.setText(String.valueOf(mBloodOxygen));

        if (mHeartRate > 100) {
            mTvMeasureHeartRateTips.setText(getString(R.string.heart_rate_more));
            mTvMeasureHeartRateTips.setTextColor(ContextCompat.getColor(mContext,R.color.measure_exception_color));
        } else if (mHeartRate < 60) {
            mTvMeasureHeartRateTips.setText(getString(R.string.heart_rate_less));
            mTvMeasureHeartRateTips.setTextColor(ContextCompat.getColor(mContext,R.color.measure_exception_color));
        } else {
            mTvMeasureHeartRateTips.setText(getString(R.string.heart_rate_ok));
            mTvMeasureHeartRateTips.setTextColor(ContextCompat.getColor(mContext,R.color.measure_ok_color));
        }

        if (mBloodOxygen >= 94) {
            mTvMeasureBloodOxygenTips.setText(getString(R.string.blood_oxygen_ok));
            mTvMeasureBloodOxygenTips.setTextColor(ContextCompat.getColor(mContext,R.color.measure_ok_color));
        } else {
            mTvMeasureBloodOxygenTips.setText(getString(R.string.blood_oxygen_less));
            mTvMeasureBloodOxygenTips.setTextColor(ContextCompat.getColor(mContext,R.color.measure_exception_color));
        }

        if (mBloodPresureSbp >= 140) {
            mTvMeasureBloodPressureTips.setText(getString(R.string.blood_pressure_high));
            mTvMeasureBloodPressureTips.setTextColor(ContextCompat.getColor(mContext,R.color.measure_exception_color));
        } else if (mBloodPresureSbp <= 90) {
            mTvMeasureBloodPressureTips.setText(getString(R.string.blood_pressure_less));
            mTvMeasureBloodPressureTips.setTextColor(ContextCompat.getColor(mContext,R.color.measure_exception_color));
        } else {
            mTvMeasureBloodPressureTips.setText(getString(R.string.blood_pressure_ok));
            mTvMeasureBloodPressureTips.setTextColor(ContextCompat.getColor(mContext,R.color.measure_ok_color));
        }

        mIvMeasureHeartRate.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIvMeasureHeartRate.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                float width = mIvMeasureHeartRate.getWidth();
                float percent = mHeartRate / 210.0f;
                mIvMeasureHeartRatePoint.setX(percent * width);
            }
        });

        mIvMeasureBloodPressure.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIvMeasureBloodPressure.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                float width = mIvMeasureBloodPressure.getWidth();
                float percent = mBloodPresureSbp / 210.0f;
                mIvMeasureBloodPressurePoint.setX(percent * width);
            }
        });

        mIvMeasureBloodOxygen.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIvMeasureBloodOxygen.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                float width = mIvMeasureBloodOxygen.getWidth();
                if (mBloodOxygen < 90) {
                    float percent = mBloodOxygen / 90.0f;
                    mIvMeasureBloodOxygenPoint.setX(width * 0.1f + percent * width * 0.3f);
                } else {
                    float percent = (mBloodOxygen - 90) / 10.0f;
                    mIvMeasureBloodOxygenPoint.setX(width * 0.4f + percent * width * 0.6f);
                }
            }
        });

    }

    private void uploadMeasureRecord() {
        AddSurveryBody body = new AddSurveryBody();
        body.setBo(mBloodOxygen);
        body.setHr(mHeartRate);
        body.setDbp(mBloodPresureDbp);
        body.setSbp(mBloodPresureSbp);
//        body.setDevice_id("1");
        ApiClient.getApiService().addSurveryRecord(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()),
                mMemberId, body)
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
                            EventBus.getDefault().post(new AddSurveryRecordEvent());
                            LogUtil.e(TAG, "add survery Record success");
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
