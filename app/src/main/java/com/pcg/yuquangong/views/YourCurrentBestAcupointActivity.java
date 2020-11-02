package com.pcg.yuquangong.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.event.SerialReceivedEvent;
import com.pcg.yuquangong.model.serialport.SerialPortManager;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.utils.LunarUtils;
import com.pcg.yuquangong.utils.TimeUtils;
import com.pcg.yuquangong.utils.Utils;
import com.pcg.yuquangong.views.widgets.AcupointCircleView;
import com.pcg.yuquangong.views.widgets.DialogHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 你当前的最佳穴位
 */
public class YourCurrentBestAcupointActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    Button mLayTvBack;
    @BindView(R.id.tvBestAcupointTitle)
    TextView mTvBestAcupointTitle;
    @BindView(R.id.ivAcupoint)
    ImageView mIvAcupoint;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    @BindView(R.id.acupointCircleView)
    AcupointCircleView mAcupointCircleView;
    @BindView(R.id.tvTestInfo)
    TextView mTvTestInfo;
    @BindView(R.id.tvTestSendSerialInfo)
    TextView mTvTestSendSerialInfo;
    @BindView(R.id.tvAcupointDesc)
    TextView mTvAcupointDesc;

    private static final String TAG = "YourCurrentBestAcupointActivity";

    private int mAcupointNum = Constant.AcupointNumber.ERROR_POINT;
    // TEST DATA
//    private int mAcupointNum = Constant.AcupointNumber.SHAO_SHANG;

    private String birthday;
    private int mCalendar;
    private int mMemberNo;
    private String mName;
    private int magnetic;
    private int symptom;
    private int comb_mpa;
    private int comb_head;

    private int mAcupointByDevice;
    private boolean mBackToMain;

    private boolean mGloableLayouted = false;

    private boolean mCanStartSortingActivity = false;

    public static void startActivity(Context context, String birthday, int calendar, int memberNo,
                                     String name, int magnetic, int symptom, int comb_mpa,
                                     int comb_head, boolean backToMain) {
        Intent intent = new Intent();
        intent.setClass(context, YourCurrentBestAcupointActivity.class);
        intent.putExtra("birthday", birthday);
        intent.putExtra("calendar", calendar);
        intent.putExtra("member_no", memberNo);
        intent.putExtra("name", name);
        intent.putExtra("magnetic", magnetic);
        intent.putExtra("symptom", symptom);
        intent.putExtra("comb_mpa", comb_mpa);
        intent.putExtra("comb_head", comb_head);
        intent.putExtra("back_tomain", backToMain);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_acupoint);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initData();
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mShiningAnimator != null && mShiningAnimator.isStarted()) {
            mShiningAnimator.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveCalculateAcupointReceiver(SerialReceivedEvent event) {
        String data = event.getReceivedMsg();
        mTvTestInfo.setText("治疗仪返回的数据为: " + data);
        LogUtil.e(TAG, "receiveCalculateAcupointReceiver data = " + data);
        if (!TextUtils.isEmpty(data)) {
            String[] dataArray = data.split(" ");
            if (dataArray.length == 5 && TextUtils.equals(dataArray[0], "41")
                    && TextUtils.equals(dataArray[1], "42")) {
                String acupointData = dataArray[2];
                int serverReturnAcupoint = Integer.parseInt(acupointData, 10);
                mAcupointByDevice = serverReturnAcupoint;
                showAcupintViewByServerAcupoint(serverReturnAcupoint);

                if (mGloableLayouted) {
                    drawCircleAcupoint();
                }
            } else if (dataArray.length == 4 && (TextUtils.equals(dataArray[0], "41")
                    || TextUtils.equals(dataArray[0], "42"))) {
                String acupointData = dataArray[1];
                int serverReturnAcupoint = Integer.parseInt(acupointData, 10);
                mAcupointByDevice = serverReturnAcupoint;
                showAcupintViewByServerAcupoint(serverReturnAcupoint);

                if (mGloableLayouted) {
                    drawCircleAcupoint();
                }
            } else if (dataArray.length >= 6 && TextUtils.equals(dataArray[0], "43")
                    && TextUtils.equals(dataArray[1], "44")) {
                mProgressDialog.hide();
//                List<String> dataList = Arrays.asList(dataArray);

                boolean allDeviceError = true;
//                for (int i = 2; i < dataList.size() - 2; i++) {
//                    if (dataList.contains("00") || dataList.contains("01")) {
//                        allDeviceError = false;
//                    }
//                }

                // 串口返回只有第1和第2个手柄，第三和第四个手柄返回00 00
                if (TextUtils.equals(dataArray[2], "00") || TextUtils.equals(dataArray[2], "01")
                        || TextUtils.equals(dataArray[3], "00") || TextUtils.equals(dataArray[3], "01")) {
                    allDeviceError = false;
                }

                if (allDeviceError) {
                    mCanStartSortingActivity = false;
                    showErrorConnectDialog();
                } else {
                    mCanStartSortingActivity = true;
                    SortingActivity.startActivity(mContext, mAcupointByDevice, birthday, mCalendar, mMemberNo, mName,
                            magnetic, symptom, comb_mpa, comb_head);
                }
            }
        }
    }

    private synchronized void showErrorConnectDialog() {
        DialogHelper.getInstance().showErrorConnectDialog(mContext);
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        if (mBackToMain) {
            MainActivity.startActivity(mContext);
        }
        finish();
    }

    @OnClick(R.id.btnNext)
    void nextBtnClick() {
        mProgressDialog.show();
        App.getInstance().sendMsg(SerialPortManager.QUERY_DEVICE_STATE);
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
        mBackToMain = getIntent().getBooleanExtra("back_tomain", false);
    }

    private void initData() {
        mTvTestInfo.setVisibility(View.GONE);
        mTvTestSendSerialInfo.setVisibility(View.GONE);

        initParentData();

        String middleSerialCode = "";
        String currentTime = TimeUtils.getYYYYMMDDHHMMByMillis(System.currentTimeMillis());

        LogUtil.e(TAG, "currentTime = " + currentTime);
        if (!TextUtils.isEmpty(currentTime)) {
            String[] currentTimeArray = currentTime.split(" ");
            if (currentTimeArray != null && currentTimeArray.length == 2) {
                String[] currentTimeYYMMDD = Utils.getBirthdayArray(currentTimeArray[0]);

                if (currentTimeYYMMDD != null && currentTimeYYMMDD.length == 3) {
                    for (int index = 0; index < currentTimeYYMMDD.length; index++) {
                        String item = currentTimeYYMMDD[index];
                        if (index == 0) {
                            for (int i = 0; i < item.length(); i++) {
                                char itemChar = item.charAt(i);
                                middleSerialCode = middleSerialCode + getSingleCureCodeStringByNumber(Character.toString(itemChar));
                            }
                        } else {
                            middleSerialCode = middleSerialCode + getSingleCureCodeStringByNumber(Integer.toHexString(Integer.parseInt(item)));
                        }
                    }
                }

                String[] hourMinutesArray = currentTimeArray[1].split(":");
                if (hourMinutesArray != null && hourMinutesArray.length > 0) {
                    String hour = hourMinutesArray[0];
                    middleSerialCode = middleSerialCode + getSingleCureCodeStringByNumber(Integer.toHexString(Integer.parseInt(hour)));
                }
            }
        }

        String[] birthdayArray = Utils.getBirthdayArray(birthday);

        if (birthdayArray != null && birthdayArray.length == 3) {
            if (mCalendar == Constant.Member.LUNAR_CALENDAR) {

                String year = birthdayArray[0];
                String month = birthdayArray[1];
                String day = birthdayArray[2];

                int yearLunar = Integer.parseInt(year);
                int monthLunar = Integer.parseInt(month);
                int dayLunar = Integer.parseInt(day);

                int[] solarDateArray = LunarUtils.lunarToSolar(yearLunar, monthLunar, dayLunar);

                if (solarDateArray != null && solarDateArray.length == 3) {
                    LogUtil.e(TAG, birthday + "农历转公历时间为: " + solarDateArray[0] + "-" + solarDateArray[1] + "-" + solarDateArray[2]);
                    for (int index = 0; index < solarDateArray.length; index++) {
                        String item = String.valueOf(solarDateArray[index]);
                        if (index == 0) {
                            for (int i = 0; i < item.length(); i++) {
                                char itemChar = item.charAt(i);
                                middleSerialCode = middleSerialCode + getSingleCureCodeStringByNumber(Character.toString(itemChar));
                            }
                        } else {
                            middleSerialCode = middleSerialCode + getSingleCureCodeStringByNumber(Integer.toHexString(Integer.parseInt(item)));
                        }
                    }
                    middleSerialCode = middleSerialCode + "00";
                }
            } else {
                for (int index = 0; index < birthdayArray.length; index++) {
                    String item = birthdayArray[index];
                    if (index == 0) {
                        for (int i = 0; i < item.length(); i++) {
                            char itemChar = item.charAt(i);
                            middleSerialCode = middleSerialCode + getSingleCureCodeStringByNumber(Character.toString(itemChar));
                        }
                    } else {
                        middleSerialCode = middleSerialCode + getSingleCureCodeStringByNumber(Integer.toHexString(Integer.parseInt(item)));
                    }
                }
                middleSerialCode = middleSerialCode + "00";
            }
        }

        middleSerialCode = SerialPortManager.CALCULATE_ACUPOINT_HEADER + middleSerialCode + SerialPortManager.FOOTER_CODE;

//        LogUtil.e(TAG, "middleSerialCode = " + middleSerialCode + " length = " + middleSerialCode.length());

        middleSerialCode = middleSerialCode.toUpperCase();

        LogUtil.e(TAG, "upperCase middleSerialCode = " + middleSerialCode + " length = " + middleSerialCode.length());

        mTvTestSendSerialInfo.setText("发送给串口的信息: " + middleSerialCode);

        App.getInstance().sendMsg(middleSerialCode);
    }

    private String getSingleCureCodeStringByNumber(String cureCode) {
        if (cureCode.length() == 1) {
            return "0" + cureCode;
        }
        return cureCode;
    }

    private void showAcupintViewByServerAcupoint(int deviceReturnAcupoint) {
        mAcupointNum = Constant.AcupointUtils.getAcupointNumberByDeviceReturnPoint(deviceReturnAcupoint);
        if (mAcupointNum == Constant.AcupointNumber.ERROR_POINT) {
            return;
        }
        int drawableRes = Constant.AcupointUtils.getAcupointDrawableResouces(mAcupointNum);
        mTvAcupointDesc.setText(Constant.AcupointUtils.getAcupointDescByDeviceReturnPoint(deviceReturnAcupoint) + "。");
        mIvAcupoint.setBackgroundResource(drawableRes);
    }

    private void initView() {
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnNext.setTypeface(App.getInstance().getHomeTextTypeface());
        mTvBestAcupointTitle.setTypeface(App.getInstance().getHomeTextTypeface());

        mProgressDialog = new ProgressDialog(mContext);
        mAcupointCircleView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Optionally remove the listener so future layouts don't change the value
                        mAcupointCircleView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        if (mAcupointNum != Constant.AcupointNumber.ERROR_POINT) {
                            mGloableLayouted = false;
                            drawCircleAcupoint();
                        } else {
                            mGloableLayouted = true;
                        }
                    }
                });
    }

    private void drawCircleAcupoint() {
        if (mAcupointNum == Constant.AcupointNumber.ERROR_POINT) {
            return;
        }

        // Now you may get the left/top/etc.
        int[] acupointCordinator = Constant.AcupointUtils.getAcupintCoordinate(mAcupointNum);

        if (acupointCordinator == null) {
            return;
        }

        float scaleX = mIvAcupoint.getWidth() / 800f;
        float scaleY = mIvAcupoint.getHeight() / 340f;

        LogUtil.e(TAG, "scaleX = " + scaleX + " | scaleY = " + scaleY);

        int cordinatorX = acupointCordinator[0];
        int cordinatorY = acupointCordinator[1];

        cordinatorX = (int) (cordinatorX * scaleX);
        cordinatorY = (int) (cordinatorY * scaleY);

        LogUtil.e(TAG, "cordinatorX = " + cordinatorX + " | cordinatorY = " + cordinatorY);

        mAcupointCircleView.setCordinator(cordinatorX, cordinatorY, 13);

        startCirclePointShiningAnimation();
    }

    private ObjectAnimator mShiningAnimator;

    private void startCirclePointShiningAnimation() {
        if (mShiningAnimator != null && mShiningAnimator.isStarted()) {
            mShiningAnimator.cancel();
        }
        mShiningAnimator = ObjectAnimator.ofFloat(mAcupointCircleView, "alpha", 1, 0);
        mShiningAnimator.setDuration(500);
        mShiningAnimator.setInterpolator(new LinearInterpolator());
        mShiningAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mShiningAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mShiningAnimator.start();
    }

}
