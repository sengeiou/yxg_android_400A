package com.pcg.yuquangong.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseBirthdayActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    Button mLayTvBack;
    @BindView(R.id.tvChooseBirthdayTitle)
    TextView mTvChooseBirthdayTitle;
    @BindView(R.id.ivCalendarChecked)
    ImageView mIvCalendarChecked;
    @BindView(R.id.layCalendar)
    LinearLayout mLayCalendar;
    @BindView(R.id.ivLunarCalendarChecked)
    ImageView mIvLunarCalendarChecked;
    @BindView(R.id.layLunarCalendar)
    LinearLayout mLayLunarCalendar;
    @BindView(R.id.layCalendarTab)
    LinearLayout mLayCalendarTab;
    @BindView(R.id.tvChooseBirthdayYear)
    TextView mTvChooseBirthdayYear;
    @BindView(R.id.tvChooseBirthdayMonth)
    TextView mTvChooseBirthdayMonth;
    @BindView(R.id.tvChooseBirthdayDay)
    TextView mTvChooseBirthdayDay;
    @BindView(R.id.layYMDContainer)
    LinearLayout mLayYMDContainer;
    @BindView(R.id.ivAddYear)
    ImageView mIvAddYear;
    @BindView(R.id.tvCurrentYear)
    TextView mTvCurrentYear;
    @BindView(R.id.ivMinusYear)
    ImageView mIvMinusYear;
    @BindView(R.id.ivAddMonth)
    ImageView mIvAddMonth;
    @BindView(R.id.tvCurrentMonth)
    TextView mTvCurrentMonth;
    @BindView(R.id.ivMinusMonth)
    ImageView mIvMinusMonth;
    @BindView(R.id.ivAddDay)
    ImageView mIvAddDay;
    @BindView(R.id.tvCurrentDay)
    TextView mTvCurrentDay;
    @BindView(R.id.ivMinusDay)
    ImageView mIvMinusDay;
    @BindView(R.id.layChooseDateContainer)
    LinearLayout mLayChooseDateContainer;
    @BindView(R.id.btnNext)
    Button mBtnNext;

    private int mCurrentCalendar = Constant.Member.CALENDAR;

    private int mCurrentYear = 1988;
    private int mCurrentMonth = 1;
    private int mCurrentDay = 1;

    private boolean mActivityForResult = false;

    private int mMemberId;

    public static void startActivityForResult(Activity context, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, ChooseBirthdayActivity.class);
        intent.putExtra("type", "for_result");
        context.startActivityForResult(intent, requestCode);
    }

    public static void startActivity(Context context, int memberId) {
        Intent intent = new Intent();
        intent.setClass(context, ChooseBirthdayActivity.class);
        intent.putExtra("member_id", memberId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_birthday);
        ButterKnife.bind(this);

        initData();
        initViews();
    }

    private void initData() {
        String intentTypeExtra = getIntent().getStringExtra("type");
        if (TextUtils.equals(intentTypeExtra, "for_result")) {
            mActivityForResult = true;
        }
        mMemberId = getIntent().getIntExtra("member_id", Constant.DEFAULT_MEMBER_ID);
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.btnNext)
    void btnNextClick() {
        if (mActivityForResult) {
            setBirthdayResult();
        } else {
//            ToastUtils.showToast(getCurrentYear() + "-" + getCurrentMonth() + "-" + getCurrentDay());
            String birthday = getCurrentYear() + "-" + getCurrentMonth() + "-" + getCurrentDay();
            ChooseSortingMethodActivity.startActivity(mContext, birthday, mCurrentCalendar,
                    mMemberId, getString(R.string.visitor), false);
        }
    }

    private void setBirthdayResult() {
        Intent intent = new Intent();
        intent.putExtra(Constant.Member.KEY_BIRTHDAY_YEAR, getCurrentYear());
        intent.putExtra(Constant.Member.KEY_BIRTHDAY_MONTH, getCurrentMonth());
        intent.putExtra(Constant.Member.KEY_BIRTHDAY_DAY, getCurrentDay());
        intent.putExtra(Constant.Member.KEY_BIRTHDAY_CALENDAR_TYPE, mCurrentCalendar);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.layCalendar)
    void layCalendarClick() {
        mIvCalendarChecked.setVisibility(View.VISIBLE);
        mIvLunarCalendarChecked.setVisibility(View.INVISIBLE);
        mCurrentCalendar = Constant.Member.CALENDAR;
    }

    @OnClick(R.id.layLunarCalendar)
    void layLunarCalendarClick() {
        mIvCalendarChecked.setVisibility(View.INVISIBLE);
        mIvLunarCalendarChecked.setVisibility(View.VISIBLE);
        mCurrentCalendar = Constant.Member.LUNAR_CALENDAR;
    }

    @OnClick(R.id.ivAddYear)
    void addYearClick() {
        ++mCurrentYear;
        mTvCurrentYear.setText(getCurrentYear());
    }

    @OnClick(R.id.ivMinusYear)
    void minusYearClick() {
        --mCurrentYear;
        if (mCurrentYear < 1900) {
            mCurrentYear = 1900;
        }
        mTvCurrentYear.setText(getCurrentYear());
    }

    @OnClick(R.id.ivAddMonth)
    void addMonthClick() {
        ++mCurrentMonth;
        if (mCurrentMonth > 12) {
            mCurrentMonth = 12;
        }
        mTvCurrentMonth.setText(getCurrentMonth());
    }

    @OnClick(R.id.ivMinusMonth)
    void minusMonthClick() {
        --mCurrentMonth;
        if (mCurrentMonth < 1) {
            mCurrentMonth = 1;
        }
        mTvCurrentMonth.setText(getCurrentMonth());
    }

    @OnClick(R.id.ivAddDay)
    void addDayClick() {
        ++mCurrentDay;
        if (mCurrentCalendar == Constant.Member.CALENDAR) {
            if (mCurrentMonth == 2) {
                if ((mCurrentYear % 4 == 0 && mCurrentYear % 100 != 0) || (mCurrentYear % 400 == 0)) {
                    if (mCurrentDay > 29) {
                        mCurrentDay = 29;
                    }
                } else {
                    if (mCurrentDay > 28) {
                        mCurrentDay = 28;
                    }
                }
            } else if (mCurrentMonth == 4 || mCurrentMonth == 6 || mCurrentMonth == 9 || mCurrentMonth == 11) {
                if (mCurrentDay > 30) {
                    mCurrentDay = 30;
                }
            } else {
                if (mCurrentDay > 31) {
                    mCurrentDay = 31;
                }
            }
        } else if (mCurrentCalendar == Constant.Member.LUNAR_CALENDAR) {
            if (mCurrentDay > 30) {
                mCurrentDay = 30;
            }
        }
        mTvCurrentDay.setText(getCurrentDay());
    }

    @OnClick(R.id.ivMinusDay)
    void minusDayClick() {
        --mCurrentDay;
        if (mCurrentDay < 1) {
            mCurrentDay = 1;
        }
        mTvCurrentDay.setText(getCurrentDay());
    }

    private void initViews() {
        mTvCurrentYear.setText(getCurrentYear());
        mTvCurrentMonth.setText(getCurrentMonth());
        mTvCurrentDay.setText(getCurrentDay());

        mTvChooseBirthdayTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnNext.setTypeface(App.getInstance().getHomeTextTypeface());
    }

    private String getCurrentYear() {
        return String.valueOf(mCurrentYear);
    }

    private String getCurrentMonth() {
        return String.valueOf(mCurrentMonth);
    }

    private String getCurrentDay() {
        return String.valueOf(mCurrentDay);
    }

}
