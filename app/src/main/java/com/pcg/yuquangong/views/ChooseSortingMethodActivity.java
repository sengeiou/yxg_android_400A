package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseSortingMethodActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.tvChooseSortingMethodTitle)
    TextView mTvChooseSortingMethodTitle;
    @BindView(R.id.ivPain)
    ImageView mIvPain;
    @BindView(R.id.tvPainFir)
    TextView mTvPainFir;
    @BindView(R.id.tvPainSec)
    TextView mTvPainSec;
    @BindView(R.id.ivInflammation)
    ImageView mIvInflammation;
    @BindView(R.id.tvInflammationFir)
    TextView mTvInflammationFir;
    @BindView(R.id.tvInflammationSec)
    TextView mTvInflammationSec;
    @BindView(R.id.layChooseSortingMethodFir)
    LinearLayout mLayChooseSortingMethodFir;
    @BindView(R.id.ivTheLump)
    ImageView mIvTheLump;
    @BindView(R.id.tvTheLumpFir)
    TextView mTvTheLumpFir;
    @BindView(R.id.tvTheLumpSec)
    TextView mTvTheLumpSec;
    @BindView(R.id.ivThreeHigh)
    ImageView mIvThreeHigh;
    @BindView(R.id.tvThreeHighFir)
    TextView mTvThreeHighFir;
    @BindView(R.id.tvThreeHighSec)
    TextView mTvThreeHighSec;
    @BindView(R.id.layChooseSortingMethodSec)
    LinearLayout mLayChooseSortingMethodSec;

    private String mBirthday;
    private int mCalendar;
    private int mMemberNo;
    private String mName;
    private boolean mBackToMain;

    public static void startActivity(Context context, String birthday, int calendar,
                                     int memberNo, String name, boolean backToMain) {
        Intent intent = new Intent();
        intent.setClass(context, ChooseSortingMethodActivity.class);
        intent.putExtra("birthday", birthday);
        intent.putExtra("calendar", calendar);
        intent.putExtra("member_no", memberNo);
        intent.putExtra("name", name);
        intent.putExtra("back_to_main", backToMain);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sorting_method);
        ButterKnife.bind(this);

        initData();
        initViews();
    }

    private void initData() {
        mBirthday = getIntent().getStringExtra("birthday");
        mCalendar = getIntent().getIntExtra("calendar", Constant.DEFAULT_CALENDAR);
        mMemberNo = getIntent().getIntExtra("member_no", Constant.DEFAULT_MEMBER_ID);
        mName = getIntent().getStringExtra("name");
        mBackToMain = getIntent().getBooleanExtra("back_to_main", false);
    }

    private void initViews() {
        mTvChooseSortingMethodTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());

        mTvPainFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvPainSec.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvInflammationFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvInflammationSec.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvTheLumpFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvTheLumpSec.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvThreeHighFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvThreeHighSec.setTypeface(App.getInstance().getMainHomeTypeface());

        if (App.getInstance().getLanguageType()==Constant.Language.LAN_EN) {
            mTvPainFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvPainSec.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvInflammationFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvInflammationSec.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvTheLumpFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvTheLumpSec.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvThreeHighFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvThreeHighSec.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
        }

        if (TextUtils.isEmpty(mTvPainFir.getText().toString())) {
            mTvPainFir.setVisibility(View.GONE);
        } else {
            mTvPainFir.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvPainSec.getText().toString())) {
            mTvPainSec.setVisibility(View.GONE);
        } else {
            mTvPainSec.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvInflammationFir.getText().toString())) {
            mTvInflammationFir.setVisibility(View.GONE);
        } else {
            mTvInflammationFir.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvInflammationSec.getText().toString())) {
            mTvInflammationSec.setVisibility(View.GONE);
        } else {
            mTvInflammationSec.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvTheLumpFir.getText().toString())) {
            mTvTheLumpFir.setVisibility(View.GONE);
        } else {
            mTvTheLumpFir.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvTheLumpSec.getText().toString())) {
            mTvTheLumpSec.setVisibility(View.GONE);
        } else {
            mTvTheLumpSec.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvThreeHighFir.getText().toString())) {
            mTvThreeHighFir.setVisibility(View.GONE);
        } else {
            mTvThreeHighFir.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvThreeHighSec.getText().toString())) {
            mTvThreeHighSec.setVisibility(View.GONE);
        } else {
            mTvThreeHighSec.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        if (mBackToMain) {
            MainActivity.startActivity(mContext);
        }
        finish();
    }

    @OnClick(R.id.ivPain)
    void painClick() {
        ChooseSortingDiseaseLevelActivity.startActivity(mContext, Constant.Sorting.PAIN, mBirthday, mCalendar, mMemberNo, mName);
    }

    @OnClick(R.id.ivInflammation)
    void inflammationClick() {
        ChooseSortingDiseaseLevelActivity.startActivity(mContext, Constant.Sorting.INFLAMMATION, mBirthday, mCalendar, mMemberNo, mName);
    }

    @OnClick(R.id.ivTheLump)
    void theLumpClick() {
        ChooseSortingDiseaseLevelActivity.startActivity(mContext, Constant.Sorting.THE_LUMP, mBirthday, mCalendar, mMemberNo, mName);
    }

    @OnClick(R.id.ivThreeHigh)
    void threeHighClick() {
        ChooseSortingThreeHighActivity.startActivity(mContext, mBirthday, mCalendar, mMemberNo, mName);
    }

}
