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
 * 选择病的程度（轻、中、重）
 */
public class ChooseSortingDiseaseLevelActivity extends BaseActivity {

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

    // data from parent activity
    private int mDiseaseType;
    private String mBirthday;
    private int mCalendar;
    private int mMemberId;
    private String mName;

    private int symptom;
    private int comb_mpa;

    public static void startActivity(Context context, int chooseProcess, String birthday,
                                     int calendar, int memberId, String name) {
        Intent intent = new Intent();
        intent.setClass(context, ChooseSortingDiseaseLevelActivity.class);
        intent.putExtra(Constant.ChooseSorting.KEY_DISEASE_TYPE, chooseProcess);
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
        mDiseaseType = getIntent().getIntExtra(Constant.ChooseSorting.KEY_DISEASE_TYPE, -1);
        mBirthday = getIntent().getStringExtra(Constant.ChooseSorting.KEY_BIRTHDAY);
        mCalendar = getIntent().getIntExtra(Constant.ChooseSorting.KEY_CALENDAR, Constant.DEFAULT_CALENDAR);
        mMemberId = getIntent().getIntExtra(Constant.ChooseSorting.KEY_MEMBER_ID, Constant.DEFAULT_MEMBER_ID);
        mName = getIntent().getStringExtra(Constant.ChooseSorting.KEY_NAME);
    }

    private void initViews() {
        mBtnNext.setVisibility(View.GONE);
        mBtnNext.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mTvChooseSortingMethodProcessTitle.setTypeface(App.getInstance().getHomeTextTypeface());

        if (App.getInstance().getLanguageType()==Constant.Language.LAN_EN) {
            mTvProcessFirFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            mTvProcessSecFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            mTvProcessThirdFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        }

        mTvProcessFirFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvProcessSecFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvProcessThirdFir.setTypeface(App.getInstance().getMainHomeTypeface());

        switch (mDiseaseType) {
            case Constant.Sorting.PAIN:
                mTvChooseSortingMethodProcessTitle.setText(getString(R.string.title_pain));

                mTvProcessFirFir.setText(getString(R.string.pain_little));
                mTvProcessSecFir.setText(getString(R.string.pain_middle));
                mTvProcessThirdFir.setText(getString(R.string.pain_high));

                symptom = Constant.Sorting.SORTING_METHOD_TT;
                break;

            case Constant.Sorting.INFLAMMATION:
                mTvChooseSortingMethodProcessTitle.setText(getString(R.string.title_inflammation));

                mTvProcessFirFir.setText(getString(R.string.inflammation_little));
                mTvProcessSecFir.setText(getString(R.string.inflammation_middle));
                mTvProcessThirdFir.setText(getString(R.string.inflammation_high));

                symptom = Constant.Sorting.SORTING_METHOD_YZ;
                break;

            case Constant.Sorting.THE_LUMP:
                mTvChooseSortingMethodProcessTitle.setText(getString(R.string.title_the_lump));

                mTvProcessFirFir.setText(getString(R.string.the_lump_little));
                mTvProcessSecFir.setText(getString(R.string.the_lump_middle));
                mTvProcessThirdFir.setText(getString(R.string.the_lump_high));

                symptom = Constant.Sorting.SORTING_METHOD_ZK;
                break;

            case Constant.Sorting.THREE_HIGH_GXY:
                // 三高，需要先进入对应的"高血压"再继续选择
                mTvChooseSortingMethodProcessTitle.setText(getString(R.string.title_high_blood_pressure));

                mTvProcessFirFir.setText(getString(R.string.high_blood_pressure_little));
                mTvProcessSecFir.setText(getString(R.string.high_blood_pressure_middle));
                mTvProcessThirdFir.setText(getString(R.string.high_blood_pressure_high));

                symptom = Constant.Sorting.SORTING_METHOD_GXY;
                break;

            case Constant.Sorting.THREE_HIGH_TNB:
                // 三高，需要先进入对应的"糖尿病"再继续选择
                mTvChooseSortingMethodProcessTitle.setText(getString(R.string.title_diabetes));

                mTvProcessFirFir.setText(getString(R.string.diabetes_little));
                mTvProcessSecFir.setText(getString(R.string.diabetes_middle));
                mTvProcessThirdFir.setText(getString(R.string.diabetes_high));

                symptom = Constant.Sorting.SORTING_METHOD_TNB;
                break;

            case Constant.Sorting.THREE_HIGH_GXZ:
                // 三高，需要先进入对应的"高血脂"再继续选择
                mTvChooseSortingMethodProcessTitle.setText(getString(R.string.title_hyperlipidemia));

                mTvProcessFirFir.setText(getString(R.string.hyperlipidemia_little));
                mTvProcessSecFir.setText(getString(R.string.hyperlipidemia_middle));
                mTvProcessThirdFir.setText(getString(R.string.hyperlipidemia_high));

                symptom = Constant.Sorting.SORTING_METHOD_GXZ;
                break;
        }

    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.ivProcessFir)
    void processFirClick() {
        comb_mpa = Constant.Sorting.LITTLE;
        ChooseSortingCombHeadActivity.startActivity(mContext, mDiseaseType, mBirthday, mCalendar, mMemberId, mName, symptom, comb_mpa);
    }

    @OnClick(R.id.ivProcessSec)
    void processSecClick() {
        comb_mpa = Constant.Sorting.MIDDLE;
        ChooseSortingCombHeadActivity.startActivity(mContext, mDiseaseType, mBirthday, mCalendar, mMemberId, mName, symptom, comb_mpa);
    }

    @OnClick(R.id.ivProcessThird)
    void processThirdClick() {
        comb_mpa = Constant.Sorting.HIGN;
        ChooseSortingCombHeadActivity.startActivity(mContext, mDiseaseType, mBirthday, mCalendar, mMemberId, mName, symptom, comb_mpa);
    }

}
