package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.event.SerialReceivedEvent;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.body.AddCureRecordBody;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.CureRecordItemEntity;
import com.pcg.yuquangong.model.serialport.SerialPortManager;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.utils.TimeUtils;
import com.pcg.yuquangong.views.widgets.DialogHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.gif.GifImageView;

/**
 * 正在梳理
 */
public class SortingActivity extends BaseActivity {

    @BindView(R.id.tvSortingTitle)
    TextView mTvSortingTitle;
    @BindView(R.id.ivLeftComb)
    ImageView mIvLeftComb;
    @BindView(R.id.tvLeftCombStartPause)
    TextView mTvLeftCombStartPause;
    @BindView(R.id.layLeftCombHead)
    LinearLayout mLayLeftCombHead;
    @BindView(R.id.ivRightComb)
    ImageView mIvRightComb;
    @BindView(R.id.tvRightCombStartPause)
    TextView mTvRightCombStartPause;
    @BindView(R.id.btnStop)
    Button mBtnStop;
    @BindView(R.id.tvLeftCombTime)
    TextView mTvLeftCombTime;
    @BindView(R.id.tvRightCombTime)
    TextView mTvRightCombTime;
    @BindView(R.id.layLeftPauseContainer)
    RelativeLayout mLayLeftPauseContainer;
    @BindView(R.id.layRightPauseContainer)
    RelativeLayout mLayRightPauseContainer;
    @BindView(R.id.givLeftComb)
    GifImageView mGivLeftComb;
    @BindView(R.id.givRightComb)
    GifImageView mGivRightComb;
    @BindView(R.id.tvLeftCombTitle)
    TextView mTvLeftCombTitle;
    @BindView(R.id.tvRightCombTitle)
    TextView mTvRightCombTitle;

    private static final String TAG = SortingActivity.class.getSimpleName();

    private String birthday;
    private int mCalendar;
    private int mMemberNo;
    private String mName;
    private int magnetic;
    private int symptom;
    private int comb_mpa;
    private int comb_head;

    private int mAcupointByDevice;

    private static final int TOTAL_TIME_SECONDS = 20 * 60;

    private long mCurrentLeftHeadTimeSeconds = TOTAL_TIME_SECONDS;
    private long mCurrentRightHeadTimeSeconds = TOTAL_TIME_SECONDS;

    private volatile boolean mLeftCombPaused = false;
    private volatile boolean mRightCombPaused = false;

    private volatile long mCurrentClickTimeMillis;

    private String mSortingCreateAt;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native void ledON();

    public native void ledOFF();

    public static void startActivity(Context context, int acupointByDevice, String birthday,
                                     int calendar, int memberNo, String name,
                                     int magnetic, int symptom, int comb_mpa, int comb_head) {
        Intent intent = new Intent();
        intent.setClass(context, SortingActivity.class);
        intent.putExtra("acupoint", acupointByDevice);
        intent.putExtra("birthday", birthday);
        intent.putExtra("calendar", calendar);
        intent.putExtra("member_no", memberNo);
        intent.putExtra("name", name);
        intent.putExtra("magnetic", magnetic);
        intent.putExtra("symptom", symptom);
        intent.putExtra("comb_mpa", comb_mpa);
        intent.putExtra("comb_head", comb_head);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initData();
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveCalculateAcupointReceiver(SerialReceivedEvent event) {
        String data = event.getReceivedMsg();
        LogUtil.e(TAG, "receiveCalculateAcupointReceiver data = " + data);
        if (!TextUtils.isEmpty(data)) {
            String[] dataArray = data.split(" ");
            if (dataArray.length == 5 && TextUtils.equals(dataArray[0], "41")
                    && TextUtils.equals(dataArray[1], "42")) {
            }
        }
    }

    private Disposable mTimeIntervalDisposable;

    private void timeInterval() {
        if (mTimeIntervalDisposable != null && !mTimeIntervalDisposable.isDisposed()) {
            mTimeIntervalDisposable.dispose();
        }

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mTimeIntervalDisposable = d;
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {

                        if (!mLeftCombPaused && mCurrentLeftHeadTimeSeconds > 0) {
                            mCurrentLeftHeadTimeSeconds = mCurrentLeftHeadTimeSeconds - 1;
                        }

                        if (!mRightCombPaused && mCurrentRightHeadTimeSeconds > 0) {
                            mCurrentRightHeadTimeSeconds = mCurrentRightHeadTimeSeconds - 1;
                        }

                        LogUtil.e(TAG, "timeInterval onNext long = " + aLong + " | mCurrentLeftHeadTimeSeconds = " + mCurrentLeftHeadTimeSeconds);
                        if (mCurrentLeftHeadTimeSeconds == 0 && mCurrentRightHeadTimeSeconds == 0) {
                            if (mTimeIntervalDisposable != null && !mTimeIntervalDisposable.isDisposed()) {
                                mTimeIntervalDisposable.dispose();
                            }
                            stopDeviceSortingAndUploadData();
                            mTvLeftCombTime.setText("00:00");
                            mTvRightCombTime.setText("00:00");
                        } else {
                            if (comb_head == Constant.Sorting.CURE_LEFT_DEVICE || comb_head == Constant.Sorting.CURE_DOUBLE_DEVICE) {
                                String currentTimeStr = getCurrentMinutesSecond(mCurrentLeftHeadTimeSeconds);
                                mTvLeftCombTime.setText(currentTimeStr);
                            }

                            if (comb_head == Constant.Sorting.CURE_RIGHT_DEVICE || comb_head == Constant.Sorting.CURE_DOUBLE_DEVICE) {
                                String currentTimeStr = getCurrentMinutesSecond(mCurrentRightHeadTimeSeconds);
                                mTvRightCombTime.setText(currentTimeStr);
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

    private String getCurrentMinutesSecond(long seconds) {
        long minutes = seconds / 60;
        long second = seconds % 60;
        String minutesStr = String.valueOf(minutes);
        String secondStr = String.valueOf(second);

        if (!TextUtils.isEmpty(minutesStr) && minutesStr.length() == 1) {
            minutesStr = "0" + minutesStr;
        }

        if (!TextUtils.isEmpty(secondStr) && secondStr.length() == 1) {
            secondStr = "0" + secondStr;
        }

        return minutesStr + ":" + secondStr;
    }

    // 停止治疗并上传记录
    private void stopDeviceSortingAndUploadData() {
        stopDoubleDeviceSorting();
    }

    private void uploadSortingRecord() {
        AddCureRecordBody body = new AddCureRecordBody();
        body.setDevice_id(App.getInstance().getDeviceId());
//        body.setDuration(TOTAL_TIME_SECONDS / 60);
        body.setDuration(getCureDuration());
        body.setComb_mpa(comb_mpa);
        body.setMagnetic(magnetic);
        body.setSymptom(symptom);
        body.setAcupoint(mAcupointByDevice);
        List<AddCureRecordBody.CombHeadBean> combHeadBeanList = new ArrayList<>();
        if (comb_head == Constant.Sorting.CURE_LEFT_DEVICE) {
            combHeadBeanList.add(new AddCureRecordBody.CombHeadBean(1, "左", 1));
            combHeadBeanList.add(new AddCureRecordBody.CombHeadBean(2, "右", 0));
        } else if (comb_head == Constant.Sorting.CURE_RIGHT_DEVICE) {
            combHeadBeanList.add(new AddCureRecordBody.CombHeadBean(1, "左", 0));
            combHeadBeanList.add(new AddCureRecordBody.CombHeadBean(2, "右", 1));
        } else if (comb_head == Constant.Sorting.CURE_DOUBLE_DEVICE) {
            combHeadBeanList.add(new AddCureRecordBody.CombHeadBean(1, "左", 1));
            combHeadBeanList.add(new AddCureRecordBody.CombHeadBean(2, "右", 1));
        } else {
            combHeadBeanList.add(new AddCureRecordBody.CombHeadBean(1, "左", 1));
            combHeadBeanList.add(new AddCureRecordBody.CombHeadBean(2, "右", 0));
        }
        body.setComb_head(combHeadBeanList);
        ApiClient.getApiService().addCureRecord(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()),
                mMemberNo, body)
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<String> entity) {
//                        if (entity.isSuccess()) {
//                            LogUtil.e(TAG, "upload Sorting Record success");
//                        } else {
//                            ToastUtils.showToast(entity.getMsg());
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initData() {
        initParentData();
    }

    private void initViews() {
        mTvSortingTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnStop.setTypeface(App.getInstance().getHomeTextTypeface());

        mTvLeftCombTitle.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvRightCombTitle.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvLeftCombStartPause.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvRightCombStartPause.setTypeface(App.getInstance().getMainHomeTypeface());

        mTvLeftCombTime.setTypeface(App.getInstance().getRegularTypeface());
        mTvRightCombTime.setTypeface(App.getInstance().getRegularTypeface());

        mSortingCreateAt = TimeUtils.getNowTimeString();

        if (comb_head == Constant.Sorting.CURE_LEFT_DEVICE) {
            mLayLeftPauseContainer.setVisibility(View.VISIBLE);
            mLayRightPauseContainer.setVisibility(View.INVISIBLE);

            mTvLeftCombTime.setVisibility(View.VISIBLE);
            mTvRightCombTime.setVisibility(View.INVISIBLE);

            mGivLeftComb.setVisibility(View.VISIBLE);
            mGivRightComb.setVisibility(View.INVISIBLE);
        } else if (comb_head == Constant.Sorting.CURE_RIGHT_DEVICE) {
            mLayLeftPauseContainer.setVisibility(View.INVISIBLE);
            mLayRightPauseContainer.setVisibility(View.VISIBLE);

            mTvLeftCombTime.setVisibility(View.INVISIBLE);
            mTvRightCombTime.setVisibility(View.VISIBLE);

            mGivLeftComb.setVisibility(View.INVISIBLE);
            mGivRightComb.setVisibility(View.VISIBLE);
        } else {
            mLayLeftPauseContainer.setVisibility(View.VISIBLE);
            mLayRightPauseContainer.setVisibility(View.VISIBLE);

            mTvLeftCombTime.setVisibility(View.VISIBLE);
            mTvRightCombTime.setVisibility(View.VISIBLE);

            mGivLeftComb.setVisibility(View.VISIBLE);
            mGivRightComb.setVisibility(View.VISIBLE);
        }

        startDeviceSorting();
        timeInterval();
    }

    private void initParentData() {
        birthday = getIntent().getStringExtra("birthday");
        mCalendar = getIntent().getIntExtra("calendar", Constant.DEFAULT_CALENDAR);
        mMemberNo = getIntent().getIntExtra("member_no", Constant.DEFAULT_MEMBER_ID);
        mName = getIntent().getStringExtra("name");
        magnetic = getIntent().getIntExtra("magnetic", -1);
        symptom = getIntent().getIntExtra("symptom", -1);
        comb_mpa = getIntent().getIntExtra("comb_mpa", -1);
        comb_head = getIntent().getIntExtra("comb_head", -1);
        mAcupointByDevice = getIntent().getIntExtra("acupoint", -1);
    }

    private boolean isCanClick() {
        long currentClickMillis = System.currentTimeMillis();
        if (currentClickMillis - mCurrentClickTimeMillis < 1000) {
            mCurrentClickTimeMillis = currentClickMillis;
            return false;
        }
        mCurrentClickTimeMillis = currentClickMillis;
        return true;
    }

    private void setLeftCombHeadPauseText(boolean paused) {
        if (paused) {
            mTvLeftCombStartPause.setText(getString(R.string.pause));
        } else {
            mTvLeftCombStartPause.setText(getString(R.string.start));
        }
    }

    private void setRightCombHeadPauseText(boolean paused) {
        if (paused) {
            mTvRightCombStartPause.setText(getString(R.string.pause));
        } else {
            mTvRightCombStartPause.setText(getString(R.string.start));
        }
    }

    @OnClick(R.id.tvLeftCombStartPause)
    void leftCombStartPauseBtnClick() {
        if (comb_head == Constant.Sorting.CURE_LEFT_DEVICE || comb_head == Constant.Sorting.CURE_DOUBLE_DEVICE) {
            if (!isCanClick()) {
                return;
            }
//        ToastUtils.showToast("左梳理头暂停");
            if (mLeftCombPaused) {
                setLeftCombHeadPauseText(true);
//                startDeviceSorting();
                startDeviceSorting(LEFT_COMB_PAUSE_TO_START);
                mLeftCombPaused = !mLeftCombPaused;
                mGivLeftComb.setVisibility(View.VISIBLE);
            } else {
                setLeftCombHeadPauseText(false);
                mLeftCombPaused = !mLeftCombPaused;
                pauseDeviceSorting(Constant.Sorting.CURE_LEFT_DEVICE);
                mGivLeftComb.setVisibility(View.INVISIBLE);
            }
        }
    }

    @OnClick(R.id.tvRightCombStartPause)
    void rightCombStartPauseBtnClick() {
        if (comb_head == Constant.Sorting.CURE_RIGHT_DEVICE || comb_head == Constant.Sorting.CURE_DOUBLE_DEVICE) {
            if (!isCanClick()) {
                return;
            }
//        ToastUtils.showToast("右梳理头暂停");
            if (mRightCombPaused) {
                setRightCombHeadPauseText(true);
//                startDeviceSorting();
                startDeviceSorting(RIGHT_COMB_PAUSE_TO_START);
                mRightCombPaused = !mRightCombPaused;
                mGivRightComb.setVisibility(View.VISIBLE);
            } else {
                setRightCombHeadPauseText(false);
                mRightCombPaused = !mRightCombPaused;
                pauseDeviceSorting(Constant.Sorting.CURE_RIGHT_DEVICE);
                mGivRightComb.setVisibility(View.INVISIBLE);
            }
        }
    }

    @OnClick(R.id.btnStop)
    void btnStopClick() {
        if (!isCanClick()) {
            return;
        }
        showConfirmDialog();
    }

    private void showConfirmDialog() {
        DialogHelper.getInstance().showConfirmDialog(mContext, getString(R.string.confirm_title),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stopDoubleDeviceSorting();
                    }
                });
    }

    private void pauseDeviceSorting(int pauseCombSide) {
        if (pauseCombSide == Constant.Sorting.CURE_LEFT_DEVICE) {
            App.getInstance().sendMsg(SerialPortManager.STOP_LEFT_CURE);
        } else if (pauseCombSide == Constant.Sorting.CURE_RIGHT_DEVICE) {
            App.getInstance().sendMsg(SerialPortManager.STOP_RIGHT_CURE);
        }

        if (comb_head == Constant.Sorting.CURE_LEFT_DEVICE) {

            if (mLeftCombPaused) {
                ledOFF();
            }

        } else if (comb_head == Constant.Sorting.CURE_DOUBLE_DEVICE) {

            if (mLeftCombPaused && mRightCombPaused) {
                ledOFF();
            }

        } else if (comb_head == Constant.Sorting.CURE_RIGHT_DEVICE) {

            if (mRightCombPaused) {
                ledOFF();
            }

        }

    }

    private void stopDoubleDeviceSorting() {
//        ToastUtils.showToast("梳理头停止");
        if (mTimeIntervalDisposable != null && !mTimeIntervalDisposable.isDisposed()) {
            mTimeIntervalDisposable.dispose();
        }
        ledOFF();
        App.getInstance().sendMsg(SerialPortManager.STOP_DOUBLE_CURE);
        uploadSortingRecord();
//            EventBus.getDefault().post(new CloseSortingStopPastActivityEvent());
        CureDetailActivity.startActivity(mContext, initCureRecordItemEntity(), true);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.startActivity(mContext);
        finish();
    }

    private CureRecordItemEntity initCureRecordItemEntity() {
        CureRecordItemEntity entity = new CureRecordItemEntity();
        entity.setBirthday(birthday);
        entity.setAcupoint(mAcupointByDevice);
        entity.setComb_mpa(comb_mpa);
        entity.setSymptom(symptom);
        entity.setMagnetic(magnetic);
        entity.setCreated_at(mSortingCreateAt);
        entity.setCalendar(mCalendar);
        entity.setName(mName);
        entity.setMember_id(mMemberNo);
        List<CureRecordItemEntity.CombHeadBean> combHeadBeanList = new ArrayList<>();
        if (comb_head == Constant.Sorting.CURE_LEFT_DEVICE) {
            combHeadBeanList.add(new CureRecordItemEntity.CombHeadBean(1, "左", 1));
            combHeadBeanList.add(new CureRecordItemEntity.CombHeadBean(2, "右", 0));
        } else if (comb_head == Constant.Sorting.CURE_RIGHT_DEVICE) {
            combHeadBeanList.add(new CureRecordItemEntity.CombHeadBean(1, "左", 0));
            combHeadBeanList.add(new CureRecordItemEntity.CombHeadBean(2, "右", 1));
        } else if (comb_head == Constant.Sorting.CURE_DOUBLE_DEVICE) {
            combHeadBeanList.add(new CureRecordItemEntity.CombHeadBean(1, "左", 1));
            combHeadBeanList.add(new CureRecordItemEntity.CombHeadBean(2, "右", 1));
        } else {
            combHeadBeanList.add(new CureRecordItemEntity.CombHeadBean(1, "左", 1));
            combHeadBeanList.add(new CureRecordItemEntity.CombHeadBean(2, "右", 0));
        }

        entity.setComb_head(combHeadBeanList);

//        int duration = (int) ((TOTAL_TIME_SECONDS - mCurrentLeftHeadTimeSeconds) / 60);
        int duration = getCureDuration();

        LogUtil.e(TAG, "结束时 mCurrentLeftHeadTimeSeconds = " + mCurrentLeftHeadTimeSeconds + " | duration = " + duration);
        entity.setDuration(duration);
        return entity;
    }

    private int getCureDuration() {
        int duration;
        if (comb_head == Constant.Sorting.CURE_LEFT_DEVICE) {
            duration = (int) ((TOTAL_TIME_SECONDS - mCurrentLeftHeadTimeSeconds) / 60);
        } else if (comb_head == Constant.Sorting.CURE_RIGHT_DEVICE) {
            duration = (int) ((TOTAL_TIME_SECONDS - mCurrentRightHeadTimeSeconds) / 60);
        } else {
            int leftDuration = (int) ((TOTAL_TIME_SECONDS - mCurrentLeftHeadTimeSeconds) / 60);
            int rightDuration = (int) ((TOTAL_TIME_SECONDS - mCurrentRightHeadTimeSeconds) / 60);

            if (leftDuration < rightDuration) {
                duration = rightDuration;
            } else if (leftDuration > rightDuration) {
                duration = leftDuration;
            } else {
                duration = leftDuration;
            }
        }
        if (duration == 0) {
            duration = 1;
        }
        return duration;
    }

    private void startDeviceSorting() {
//        int minutes = seconds / 60;
//
//        int remainderSeconds = seconds % 60;
//        if (remainderSeconds >= 30) {
//            minutes = minutes + 1;
//            mCurrentLeftHeadTimeSeconds = minutes * 60;
//        } else {
//            mCurrentLeftHeadTimeSeconds = minutes * 60;
//        }

        //0x59 0x58 0x03 0x06 0x06 1C 00 00
        String cureCode = "";

        switch (comb_head) {
            case Constant.Sorting.CURE_LEFT_DEVICE:
                cureCode = SerialPortManager.START_LEFT_CURE;
                break;
            case Constant.Sorting.CURE_RIGHT_DEVICE:
                cureCode = SerialPortManager.START_RIGHT_CURE;
                break;
            case Constant.Sorting.CURE_DOUBLE_DEVICE:
                cureCode = SerialPortManager.START_DOUBLE_CURE;
                break;
        }

        LogUtil.e(TAG, "梳理头为 = " + comb_head + " | 治疗强度的值 magnetic = " + magnetic);

        switch (magnetic) {
            case Constant.Sorting.LITTLE:
                cureCode = cureCode + SerialPortManager.SMALL_CURE;
                break;
            case Constant.Sorting.MIDDLE:
                cureCode = cureCode + SerialPortManager.MIDDLE_CURE;
                break;
            case Constant.Sorting.HIGN:
                cureCode = cureCode + SerialPortManager.HIGH_CURE;
                break;
        }

        String hexMinutes = Integer.toHexString(20);

        if (!TextUtils.isEmpty(hexMinutes) && hexMinutes.length() == 1) {
            hexMinutes = "0" + hexMinutes;
        }

        cureCode = cureCode + hexMinutes + "0000";

        LogUtil.e(TAG, "cureCode = " + cureCode);

        App.getInstance().sendMsg(cureCode);

        ledON();
    }

    public static final int LEFT_COMB_PAUSE_TO_START = 1234;
    public static final int RIGHT_COMB_PAUSE_TO_START = 1235;

    private void startDeviceSorting(int combDirection) {
//        int minutes = seconds / 60;
//
//        int remainderSeconds = seconds % 60;
//        if (remainderSeconds >= 30) {
//            minutes = minutes + 1;
//            mCurrentLeftHeadTimeSeconds = minutes * 60;
//        } else {
//            mCurrentLeftHeadTimeSeconds = minutes * 60;
//        }

        //0x59 0x58 0x03 0x06 0x06 1C 00 00
        String cureCode = "";

        if (combDirection == LEFT_COMB_PAUSE_TO_START) {
            cureCode = SerialPortManager.START_LEFT_CURE;
        } else {
            cureCode = SerialPortManager.START_RIGHT_CURE;
        }

        LogUtil.e(TAG, "梳理头为 = " + comb_head + " | 治疗强度的值 magnetic = " + magnetic);

        switch (magnetic) {
            case Constant.Sorting.LITTLE:
                cureCode = cureCode + SerialPortManager.SMALL_CURE;
                break;
            case Constant.Sorting.MIDDLE:
                cureCode = cureCode + SerialPortManager.MIDDLE_CURE;
                break;
            case Constant.Sorting.HIGN:
                cureCode = cureCode + SerialPortManager.HIGH_CURE;
                break;
        }

        String hexMinutes = Integer.toHexString(20);

        if (!TextUtils.isEmpty(hexMinutes) && hexMinutes.length() == 1) {
            hexMinutes = "0" + hexMinutes;
        }

        cureCode = cureCode + hexMinutes + "0000";

        LogUtil.e(TAG, "cureCode = " + cureCode);

        App.getInstance().sendMsg(cureCode);

        ledON();
    }

}
