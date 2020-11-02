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

public class ChooseSortingCombHeadActivity extends BaseActivity {

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
    private int mSymptom;
    private int mCombMpa;

    private int comb_head;

    public static void startActivity(Context context, int chooseProcess, String birthday,
                                     int calendar, int memberId, String name, int symptom, int comb_mpa) {
        Intent intent = new Intent();
        intent.setClass(context, ChooseSortingCombHeadActivity.class);
        intent.putExtra(Constant.ChooseSorting.KEY_DISEASE_TYPE, chooseProcess);
        intent.putExtra(Constant.ChooseSorting.KEY_BIRTHDAY, birthday);
        intent.putExtra(Constant.ChooseSorting.KEY_CALENDAR, calendar);
        intent.putExtra(Constant.ChooseSorting.KEY_MEMBER_ID, memberId);
        intent.putExtra(Constant.ChooseSorting.KEY_NAME, name);
        intent.putExtra(Constant.ChooseSorting.KEY_SYMPTOM, symptom);
        intent.putExtra(Constant.ChooseSorting.KEY_COMB_MPA, comb_mpa);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_comb_head);
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
        mSymptom = getIntent().getIntExtra(Constant.ChooseSorting.KEY_SYMPTOM, Constant.Sorting.SORTING_METHOD_TT);
        mCombMpa = getIntent().getIntExtra(Constant.ChooseSorting.KEY_COMB_MPA, Constant.Sorting.LITTLE);
    }

    private void initViews() {
        mBtnNext.setVisibility(View.GONE);
        mBtnNext.setTypeface(App.getInstance().getHomeTextTypeface());

        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());

        mTvChooseSortingMethodProcessTitle.setText(getString(R.string.title_comb_head));
        mTvChooseSortingMethodProcessTitle.setTypeface(App.getInstance().getHomeTextTypeface());

        if (App.getInstance().getLanguageType()==Constant.Language.LAN_EN) {
            mTvProcessFirFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
            mTvProcessSecFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
            mTvProcessThirdFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        }

        mTvProcessFirFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvProcessSecFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvProcessThirdFir.setTypeface(App.getInstance().getMainHomeTypeface());

        mTvProcessFirFir.setText(getString(R.string.comb_head_left));
        mTvProcessSecFir.setText(getString(R.string.comb_head_double));
        mTvProcessThirdFir.setText(getString(R.string.comb_head_right));
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.ivProcessFir)
    void processFirClick() {
        comb_head = Constant.Sorting.CURE_LEFT_DEVICE;
        ChooseSortingMagneticActivity.startActivity(mContext, mDiseaseType, mBirthday, mCalendar, mMemberId, mName, mSymptom, mCombMpa, comb_head);
    }

    @OnClick(R.id.ivProcessSec)
    void processSecClick() {
        comb_head = Constant.Sorting.CURE_DOUBLE_DEVICE;
        ChooseSortingMagneticActivity.startActivity(mContext, mDiseaseType, mBirthday, mCalendar, mMemberId, mName, mSymptom, mCombMpa, comb_head);
    }

    @OnClick(R.id.ivProcessThird)
    void processThirdClick() {
        comb_head = Constant.Sorting.CURE_RIGHT_DEVICE;
        ChooseSortingMagneticActivity.startActivity(mContext, mDiseaseType, mBirthday, mCalendar, mMemberId, mName, mSymptom, mCombMpa, comb_head);
    }

}
