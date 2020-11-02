package com.pcg.yuquangong.views.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.bean.CRPHeartRateInfo;
import com.crrepa.ble.conn.bean.CRPMovementHeartRateInfo;
import com.crrepa.ble.conn.callback.CRPDeviceBatteryCallback;
import com.crrepa.ble.conn.listener.CRPBloodOxygenChangeListener;
import com.crrepa.ble.conn.listener.CRPBloodPressureChangeListener;
import com.crrepa.ble.conn.listener.CRPHeartRateChangeListener;
import com.google.gson.reflect.TypeToken;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.adapters.WatchRecordAdapter;
import com.pcg.yuquangong.base.BaseFragment;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.event.AddSurveryRecordEvent;
import com.pcg.yuquangong.model.event.ChangeFragmentEvent;
import com.pcg.yuquangong.model.event.WatchConnectStateEvent;
import com.pcg.yuquangong.model.event.WatchStartEvent;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.MemberListItemEntity;
import com.pcg.yuquangong.model.network.entity.WatchRecordItemEntity;
import com.pcg.yuquangong.utils.BluetoothManager;
import com.pcg.yuquangong.utils.GsonHelper;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.utils.ToastUtils;
import com.pcg.yuquangong.views.ChooseCurePersonActivity;
import com.pcg.yuquangong.views.MeasureDetailActivity;
import com.pcg.yuquangong.views.WatchConnectActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WatchFragment extends BaseFragment {

    @BindView(R.id.tvFragmentWatchTitle)
    TextView mTvFragmentWatchTitle;
    @BindView(R.id.tvConnectWatch)
    TextView mTvConnectWatch;
    @BindView(R.id.tvWatchBattery)
    TextView mTvWatchBattery;
    @BindView(R.id.ivStartMeasure)
    ImageView mIvStartMeasure;
    @BindView(R.id.tvStartMeasure)
    TextView mTvStartMeasure;
    @BindView(R.id.layStartMeasure)
    RelativeLayout mLayStartMeasure;
    @BindView(R.id.tvCurrentProgress)
    TextView mTvCurrentProgress;
    @BindView(R.id.ivCurrentProgress)
    ImageView mIvCurrentProgress;
    @BindView(R.id.layCurrentProgress)
    LinearLayout mLayCurrentProgress;
    @BindView(R.id.viewMeasureProgressBg)
    View mViewMeasureProgressBg;
    @BindView(R.id.viewMeasureInnerProgress)
    View mViewMeasureInnerProgress;
    @BindView(R.id.layWatchMeasureProgress)
    RelativeLayout mLayWatchMeasureProgress;
    @BindView(R.id.rcvMeasureRecord)
    RecyclerView mRcvMeasureRecord;
    Unbinder unbinder;

    @BindView(R.id.tvMainHome)
    TextView mTvMainHome;
    @BindView(R.id.tvMainWatch)
    TextView mTvMainWatch;
    @BindView(R.id.tvMainSetting)
    TextView mTvMainSetting;

    private static final String TAG = WatchFragment.class.getSimpleName();

    private static final int TOTAL_MEASURE_SECONDS = 80;
    private float mLayCurrentProgressOriginalX;
    private WatchRecordAdapter mAdapter;
    private List<WatchRecordItemEntity> mWatchRecordList = new ArrayList<>();
    private ObjectAnimator mStartMeasureAnimator;

    // measure watch

    private static final int START_MEASURE = 11;
    private static final int STOP_MEASURE = 12;
    private static final int SHOW_BATTERY = 20;
    private static final int MEASURE_COMPLETE = 1918;

    private CRPBleConnection mBleConnection;

    private int mBloodOxygen;
    private int mHeartRate;
    private int mBloodPressureSbp;
    private int mBloodPressureDbp;

    private boolean mMeasureAnimationStarted;

    private String mWatchConnectedDeviceName = "";

    private MemberListItemEntity mChooseMeasureMember;

    private long mCurrentClickMillis;

    public static WatchFragment newInstance() {
        WatchFragment fragment = new WatchFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watch, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
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
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (mStartMeasureAnimator != null && mStartMeasureAnimator.isStarted()) {
                mStartMeasureAnimator.cancel();
            }
        } else {
            LogUtil.e(TAG, "onHiddenChanged show fragment");
            resetUnMeasureUI();
        }
    }

    private void resetUnMeasureUI() {
        mTvCurrentProgress.setText("");
        mMeasureAnimationStarted = false;
        mLayCurrentProgress.post(new Runnable() {
            @Override
            public void run() {
                mLayCurrentProgress.setX(mLayCurrentProgressOriginalX);
            }
        });
        mViewMeasureInnerProgress.getLayoutParams().width = 1;
        mViewMeasureInnerProgress.requestLayout();
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        LogUtil.e(TAG, "isVisibleToUser = " + isVisibleToUser);
//        if (!isVisibleToUser) {
//            LogUtil.e(TAG, "mStartMeasureAnimator != null is " + (mStartMeasureAnimator != null) + " |  mStartMeasureAnimator.isStarted() = " + mStartMeasureAnimator.isStarted());
//            if (mStartMeasureAnimator != null && mStartMeasureAnimator.isStarted()) {
//                mStartMeasureAnimator.cancel();
//            }
//        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveAddSurveryRecordEvent(AddSurveryRecordEvent event) {
//        loadData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveWatchConnectStateEvent(WatchConnectStateEvent event) {
        LogUtil.e(TAG, "receiveWatchConnectStateEvent connected = " + event.isWatchConnected());
        if (event.isWatchConnected()) {
            mWatchConnectedDeviceName = event.getWatchName();
        } else {
            if (mStartMeasureAnimator != null && mStartMeasureAnimator.isStarted()) {
                mStartMeasureAnimator.cancel();
            }
            resetUnMeasureUI();
            resetUnConnectWatchUI();
        }
    }

    @OnClick(R.id.layStartMeasure)
    void startMeasureClick() {
        long current = System.currentTimeMillis();
        if (current - mCurrentClickMillis < 1000) {
            mCurrentClickMillis = current;
            return;
        }

        mCurrentClickMillis = current;

        if (mMeasureAnimationStarted) {
            return;
        }

        if (BluetoothManager.isBluetoothSupported()) {
            if (!BluetoothManager.isBluetoothEnabled()) {
                BluetoothManager.turnOnBluetooth();
                return;
            }
            if (App.getInstance().getBleConnection() == null) {
                WatchConnectActivity.startActivity(mContext);
                return;
            }

//            Intent intent = new Intent();
//            intent.setClass(mContext, ChooseCurePersonActivity.class);
//            intent.putExtra("type", ChooseCurePersonActivity.ACTIVITY_TYPE_FOR_RESULT);
//            startActivityForResult(intent, 1111);
            ChooseCurePersonActivity.startActivityForResult(getActivity(), 1111);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == Activity.RESULT_OK) {
            mChooseMeasureMember = data.getParcelableExtra(Constant.Member.KEY_MEASURE_MEMBER);
            startCountDownAnimation();
        }
    }

    @OnClick(R.id.tvConnectWatch)
    void connectWatchClick() {
        if (BluetoothManager.isBluetoothSupported()) {
            if (!BluetoothManager.isBluetoothEnabled()) {
                BluetoothManager.turnOnBluetooth();
                return;
            }
            WatchConnectActivity.startActivity(mContext);
        }
    }

    private void initViews() {
        mTvFragmentWatchTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mTvStartMeasure.setTypeface(App.getInstance().getMainHomeTypeface());

        mTvMainWatch.setSelected(true);

        mLayCurrentProgressOriginalX = mLayCurrentProgress.getX();
        initRcvList();
        loadData();
    }

    private void startCountDownAnimation() {
        if (mMeasureAnimationStarted) {
            return;
        }

        final float destinationX = mViewMeasureProgressBg.getWidth();
        final int triangleViewWidth = mLayCurrentProgress.getWidth();
        mStartMeasureAnimator = ObjectAnimator.ofFloat(mLayCurrentProgress, "x", destinationX - triangleViewWidth);
        mStartMeasureAnimator.setDuration(TOTAL_MEASURE_SECONDS * 1000);
        mStartMeasureAnimator.setInterpolator(new LinearInterpolator());
        mStartMeasureAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                int currentSeconds = (int) (currentValue / (destinationX - triangleViewWidth) * TOTAL_MEASURE_SECONDS);
                if(currentValue >= destinationX - triangleViewWidth || currentSeconds >= TOTAL_MEASURE_SECONDS){
                    resetUnMeasureUI();
                    endMeasureWatch();
                    return;
                }
//                int currentSeconds = (int) (currentValue / destinationX * TOTAL_MEASURE_SECONDS + 0.5f);
//                LogUtil.e(TAG, "onAnimationUpdate currentValue = " + currentValue);
                mTvCurrentProgress.setText(currentSeconds + "S");

                mViewMeasureInnerProgress.getLayoutParams().width = (int) (currentValue + triangleViewWidth);
                mViewMeasureInnerProgress.requestLayout();
            }
        });
        mStartMeasureAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mMeasureAnimationStarted = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 防止和measure complete重复
                if (mMeasureAnimationStarted) {

//                    mMeasureAnimationStarted = false;
//                    mLayCurrentProgress.setX(mLayCurrentProgressOriginalX);
//                    mViewMeasureInnerProgress.getLayoutParams().width = 1;
//                    mViewMeasureInnerProgress.requestLayout();
//
//                    mTvCurrentProgress.setText("");
                    resetUnMeasureUI();
                    endMeasureWatch();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
//                mMeasureAnimationStarted = false;
                resetUnMeasureUI();
                endMeasureWatch();
//                EventBus.getDefault().post(new WatchStartEvent(false));
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        if(startMeasureWatch()) mStartMeasureAnimator.start();
    }

    private void initRcvList() {
        List<WatchRecordItemEntity> data = new ArrayList<>();

        String cacheData = CacheManager.getInstance().getWatchRecordListCache();
        if (!TextUtils.isEmpty(cacheData)) {
            List<WatchRecordItemEntity> cacheList = GsonHelper.getGson().fromJson(cacheData, new TypeToken<List<WatchRecordItemEntity>>() {
            }.getType());
            if (cacheList != null && !cacheList.isEmpty()) {
                mWatchRecordList.clear();
                mWatchRecordList.addAll(cacheList);
                data.addAll(cacheList);
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mAdapter = new WatchRecordAdapter(R.layout.item_watch_record, data);
        mRcvMeasureRecord.setLayoutManager(layoutManager);
        mRcvMeasureRecord.setAdapter(mAdapter);

        mAdapter.disableLoadMoreIfNotFullPage(mRcvMeasureRecord);
        mAdapter.setEnableLoadMore(false);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final WatchRecordItemEntity clickItemData = (WatchRecordItemEntity) adapter.getItem(position);
                if (clickItemData == null) {
                    return;
                }
                MeasureDetailActivity.startActivity(mContext, Constant.JUST_SHOW_WATCH_RECORD_DETAIL,
                        clickItemData.getBo(), clickItemData.getHr(), clickItemData.getSbp(),
                        clickItemData.getDbp());
            }
        });

    }

    private void loadData() {

        ApiClient.getApiService().getAllSurveryList(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<WatchRecordItemEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<List<WatchRecordItemEntity>> entity) {
                        if (entity.isSuccess()) {
                            List<WatchRecordItemEntity> watchRecordList = entity.getData();
                            if (watchRecordList != null && !watchRecordList.isEmpty()) {
                                mWatchRecordList.clear();
                                mWatchRecordList.addAll(watchRecordList);
                                refreshData(watchRecordList);
                                CacheManager.getInstance().setWatchRecordListCache(GsonHelper.getGson().toJson(watchRecordList));
                            }
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

    private void refreshData(List<WatchRecordItemEntity> watchRecordList) {
        List<WatchRecordItemEntity> data = new ArrayList<>();
        data.addAll(watchRecordList);
        mAdapter.replaceData(data);
        mAdapter.loadMoreComplete();
    }

    private void startMeasureDetailActivity() {
        if (mChooseMeasureMember == null) {
            return;
        }
        MeasureDetailActivity.startActivity(mContext, mChooseMeasureMember.getId(),
                mBloodOxygen, mHeartRate, mBloodPressureSbp, mBloodPressureDbp);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == START_MEASURE) {
                LogUtil.e(TAG, "开始检测");
                if (mBleConnection == null) {
                    return;
                }
                mTvStartMeasure.setText(getString(R.string.measureing));
                LogUtil.e(TAG, "开始检测222");
                mBleConnection.startMeasureBloodPressure();
            } else if (msg.what == STOP_MEASURE) {
                LogUtil.e(TAG, "停止检测");
                mTvStartMeasure.setText(getString(R.string.measure));
                EventBus.getDefault().post(new WatchStartEvent(false));
                if (mBleConnection == null) {
                    return;
                }
                LogUtil.e(TAG, "停止检测222");
//                stopMeasureGoDetail();
            } else if (msg.what == SHOW_BATTERY) {
                int i = msg.arg1;
                mTvWatchBattery.setText(getString(R.string.battery) + i + "%");
            } else if (msg.what == MEASURE_COMPLETE) {
                mTvStartMeasure.setText(getString(R.string.measure));
                EventBus.getDefault().post(new WatchStartEvent(false));
                if (mStartMeasureAnimator != null && mStartMeasureAnimator.isStarted()) {
                    mStartMeasureAnimator.cancel();
                }
                stopMeasureGoDetail();
            }
        }
    };

    private void stopMeasureGoDetail() {
        mBleConnection.stopMeasureBloodPressure();
        mBleConnection.stopMeasureBloodOxygen();
        mBleConnection.stopMeasureOnceHeartRate();

        startMeasureDetailActivity();
    }

    CRPHeartRateChangeListener mHeartRateChangListener = new CRPHeartRateChangeListener() {
        @Override
        public void onMeasuring(int rate) {
            LogUtil.e(TAG, "onMeasuring: " + rate);
//            updateTextView(tvHeartRate, String.format(getString(R.string.heart_rate), rate));
            mHeartRate = rate;
        }

        @Override
        public void onOnceMeasureComplete(int rate) {
            LogUtil.e(TAG, "onOnceMeasureComplete: " + rate);
            mHeartRate = rate;

            mHandler.sendEmptyMessage(MEASURE_COMPLETE);
        }

        @Override
        public void onMeasureComplete(CRPHeartRateInfo info) {
            if (info != null && info.getMeasureData() != null) {
                for (Integer integer : info.getMeasureData()) {
                    LogUtil.e(TAG, "onMeasureComplete: " + integer);
                }
            }
        }

        @Override
        public void on24HourMeasureResult(CRPHeartRateInfo info) {
            List<Integer> data = info.getMeasureData();
            LogUtil.e(TAG, "on24HourMeasureResult: " + data.size());
        }

        @Override
        public void onMovementMeasureResult(List<CRPMovementHeartRateInfo> list) {
            for (CRPMovementHeartRateInfo info : list) {
                if (info != null) {
                    LogUtil.e(TAG, "onMovementMeasureResult: " + info.getStartTime());
                }
            }
        }

    };

    CRPBloodPressureChangeListener mBloodPressureChangeListener = new CRPBloodPressureChangeListener() {
        @Override
        public void onBloodPressureChange(int sbp, int dbp) {
            LogUtil.e(TAG, "BloodPressure sbp: " + sbp + ",dbp: " + dbp);
//            updateTextView(tvBloodPressure,
//                    String.format(getString(R.string.blood_pressure), sbp, dbp));
            mBloodPressureDbp = dbp;
            mBloodPressureSbp = sbp;

            mBleConnection.startMeasureBloodOxygen();
        }
    };

    CRPBloodOxygenChangeListener mBloodOxygenChangeListener = new CRPBloodOxygenChangeListener() {
        @Override
        public void onBloodOxygenChange(int bloodOxygen) {
            LogUtil.e(TAG, "bloodOxygen : " + bloodOxygen);
//            updateTextView(tvBloodOxygen,
//                    String.format(getString(R.string.blood_oxygen), bloodOxygen));
            mBloodOxygen = bloodOxygen;

            mBleConnection.startMeasureOnceHeartRate();
        }
    };

    private boolean startMeasureWatch() {
        if (App.getInstance().getBleConnection() == null) {
            return false;
        }
        EventBus.getDefault().post(new WatchStartEvent(true));
        LogUtil.e(TAG, "startMeasureWatch");

        mBleConnection = App.getInstance().getBleConnection();

        mBleConnection.setHeartRateChangeListener(mHeartRateChangListener);
        mBleConnection.setBloodPressureChangeListener(mBloodPressureChangeListener);
        mBleConnection.setBloodOxygenChangeListener(mBloodOxygenChangeListener);

        mHandler.sendEmptyMessage(START_MEASURE);
        return true;
    }

    private void endMeasureWatch() {
        LogUtil.e(TAG, "endMeasureWatch");
        mHandler.sendEmptyMessage(STOP_MEASURE);
    }

    private void resetUnConnectWatchUI() {
        mTvConnectWatch.setText(getString(R.string.watch_un_connect));
        mTvStartMeasure.setText(getString(R.string.un_connect));
        mTvWatchBattery.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e(TAG, "onResume");

        loadData();

        if (App.getInstance().getBleConnection() == null) {
            resetUnConnectWatchUI();
            return;
        }

        resetUnMeasureUI();
        mTvConnectWatch.setText(mWatchConnectedDeviceName);
        mTvStartMeasure.setText(getString(R.string.measure));

        LogUtil.e(TAG, "onResume222");

        mBleConnection = App.getInstance().getBleConnection();

        mBleConnection.queryDeviceBattery(new CRPDeviceBatteryCallback() {
            @Override
            public void onDeviceBattery(int i) {
                LogUtil.e(TAG, "onDeviceBattery = " + i);
                Message msg = new Message();
                msg.what = SHOW_BATTERY;
                msg.arg1 = i;
                mHandler.sendMessage(msg);
            }
        });
    }
}
