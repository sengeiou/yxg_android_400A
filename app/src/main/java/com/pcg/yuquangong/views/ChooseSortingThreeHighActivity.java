package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 选择哪一种三高类的病情治疗
 */
public class ChooseSortingThreeHighActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.tvChooseSortingMethodProcessTitle)
    TextView mTvChooseSortingMethodProcessTitle;
    @BindView(R.id.ivProcessFir)
    ImageView mIvProcessFir;
    @BindView(R.id.tvProcessFirFir)
    TextView mTvProcessFirFir;
    @BindView(R.id.tvProcessFirSec)
    TextView mTvProcessFirSec;
    @BindView(R.id.ivProcessSec)
    ImageView mIvProcessSec;
    @BindView(R.id.tvProcessSecFir)
    TextView mTvProcessSecFir;
    @BindView(R.id.tvProcessSecSec)
    TextView mTvProcessSecSec;
    @BindView(R.id.ivProcessThird)
    ImageView mIvProcessThird;
    @BindView(R.id.tvProcessThirdFir)
    TextView mTvProcessThirdFir;
    @BindView(R.id.tvProcessThirdSec)
    TextView mTvProcessThirdSec;
    @BindView(R.id.btnNext)
    Button mBtnNext;

    private static final String TAG = ChooseSortingThreeHighActivity.class.getSimpleName();

    // data from parent activity
    private String mBirthday;
    private int mCalendar;
    private int mMemberId;
    private String mName;

    public static void startActivity(Context context, String birthday,
                                     int calendar, int memberId, String name) {
        Intent intent = new Intent();
        intent.setClass(context, ChooseSortingThreeHighActivity.class);
        intent.putExtra(Constant.ChooseSorting.KEY_BIRTHDAY, birthday);
        intent.putExtra(Constant.ChooseSorting.KEY_CALENDAR, calendar);
        intent.putExtra(Constant.ChooseSorting.KEY_MEMBER_ID, memberId);
        intent.putExtra(Constant.ChooseSorting.KEY_NAME, name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sorting_method_process);
        ButterKnife.bind(this);

        initData();
        initViews();
    }

    private void initData() {
        mBirthday = getIntent().getStringExtra(Constant.ChooseSorting.KEY_BIRTHDAY);
        mCalendar = getIntent().getIntExtra(Constant.ChooseSorting.KEY_CALENDAR, Constant.DEFAULT_CALENDAR);
        mMemberId = getIntent().getIntExtra(Constant.ChooseSorting.KEY_MEMBER_ID, Constant.DEFAULT_MEMBER_ID);
        mName = getIntent().getStringExtra(Constant.ChooseSorting.KEY_NAME);
    }

    private void initViews() {
        mBtnNext.setVisibility(View.GONE);

        mTvChooseSortingMethodProcessTitle.setText(getString(R.string.title_three_high));

        mTvChooseSortingMethodProcessTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnNext.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());

        if (App.getInstance().getLanguageType()==Constant.Language.LAN_EN) {
            mTvProcessFirFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            mTvProcessSecFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            mTvProcessThirdFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        }

        mTvProcessFirFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvProcessSecFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvProcessThirdFir.setTypeface(App.getInstance().getMainHomeTypeface());

        mTvProcessFirFir.setText(getString(R.string.high_blood_pressure));
        mTvProcessSecFir.setText(getString(R.string.diabetes));
        mTvProcessThirdFir.setText(getString(R.string.hyperlipidemia));
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.ivProcessFir)
    void processFirClick() {
        ChooseSortingDiseaseLevelActivity.startActivity(mContext, Constant.Sorting.THREE_HIGH_GXY, mBirthday, mCalendar, mMemberId, mName);
    }

    @OnClick(R.id.ivProcessSec)
    void processSecClick() {
        ChooseSortingDiseaseLevelActivity.startActivity(mContext, Constant.Sorting.THREE_HIGH_TNB, mBirthday, mCalendar, mMemberId, mName);
    }

    @OnClick(R.id.ivProcessThird)
    void processThirdClick() {
        ChooseSortingDiseaseLevelActivity.startActivity(mContext, Constant.Sorting.THREE_HIGH_GXZ, mBirthday, mCalendar, mMemberId, mName);
    }

}
