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

public class ChooseSortingMagneticActivity extends BaseActivity {

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
    private int mCombHead;

    private int magnetic;

    public static void startActivity(Context context, int chooseProcess, String birthday,
                                     int calendar, int memberId, String name, int symptom,
                                     int comb_mpa, int comb_head) {
        Intent intent = new Intent();
        intent.setClass(context, ChooseSortingMagneticActivity.class);
        intent.putExtra(Constant.ChooseSorting.KEY_DISEASE_TYPE, chooseProcess);
        intent.putExtra(Constant.ChooseSorting.KEY_BIRTHDAY, birthday);
        intent.putExtra(Constant.ChooseSorting.KEY_CALENDAR, calendar);
        intent.putExtra(Constant.ChooseSorting.KEY_MEMBER_ID, memberId);
        intent.putExtra(Constant.ChooseSorting.KEY_NAME, name);
        intent.putExtra(Constant.ChooseSorting.KEY_SYMPTOM, symptom);
        intent.putExtra(Constant.ChooseSorting.KEY_COMB_MPA, comb_mpa);
        intent.putExtra(Constant.ChooseSorting.KEY_COMB_HEAD, comb_head);
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
        mSymptom = getIntent().getIntExtra(Constant.ChooseSorting.KEY_SYMPTOM, Constant.Sorting.SORTING_METHOD_TT);
        mCombMpa = getIntent().getIntExtra(Constant.ChooseSorting.KEY_COMB_MPA, Constant.Sorting.LITTLE);
        mCombHead = getIntent().getIntExtra(Constant.ChooseSorting.KEY_COMB_HEAD, Constant.Sorting.CURE_LEFT_DEVICE);
    }

    private void initViews() {
        mBtnNext.setVisibility(View.VISIBLE);

        mTvChooseSortingMethodProcessTitle.setText(getString(R.string.title_magnetic_field_intensity));

        mTvChooseSortingMethodProcessTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnNext.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());

        mTvProcessFirFir.setText(getString(R.string.magnetic_field_intensity_high));
        mTvProcessSecFir.setText(getString(R.string.magnetic_field_intensity_middle));
        mTvProcessThirdFir.setText(getString(R.string.magnetic_field_intensity_little));

        mTvProcessFirFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        mTvProcessSecFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        mTvProcessThirdFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);

        mTvProcessFirFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvProcessSecFir.setTypeface(App.getInstance().getMainHomeTypeface());
        mTvProcessThirdFir.setTypeface(App.getInstance().getMainHomeTypeface());
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.btnNext)
    void nextBtnClick() {
        if (magnetic != 0) {
            YourCurrentBestAcupointActivity.startActivity(mContext, mBirthday, mCalendar, mMemberId, mName, magnetic, mSymptom, mCombMpa, mCombHead, false);
        }
    }

    @OnClick(R.id.ivProcessFir)
    void processFirClick() {
        magnetic = Constant.Sorting.HIGN;
        setCurrentItemSelected(magnetic);
    }

    @OnClick(R.id.ivProcessSec)
    void processSecClick() {
        magnetic = Constant.Sorting.MIDDLE;
        setCurrentItemSelected(magnetic);
    }

    @OnClick(R.id.ivProcessThird)
    void processThirdClick() {
        magnetic = Constant.Sorting.LITTLE;
        setCurrentItemSelected(magnetic);
    }

    private void setCurrentItemSelected(int magnetic) {
        if (magnetic == Constant.Sorting.HIGN) {
            mIvProcessFir.setSelected(true);
            mTvProcessFirFir.setSelected(true);
            mTvProcessFirSec.setSelected(true);

            mIvProcessSec.setSelected(false);
            mTvProcessSecFir.setSelected(false);
            mTvProcessSecSec.setSelected(false);

            mIvProcessThird.setSelected(false);
            mTvProcessThirdFir.setSelected(false);
            mTvProcessThirdSec.setSelected(false);
        } else if (magnetic == Constant.Sorting.MIDDLE) {
            mIvProcessFir.setSelected(false);
            mTvProcessFirFir.setSelected(false);
            mTvProcessFirSec.setSelected(false);

            mIvProcessSec.setSelected(true);
            mTvProcessSecFir.setSelected(true);
            mTvProcessSecSec.setSelected(true);

            mIvProcessThird.setSelected(false);
            mTvProcessThirdFir.setSelected(false);
            mTvProcessThirdSec.setSelected(false);
        } else if (magnetic == Constant.Sorting.LITTLE) {
            mIvProcessFir.setSelected(false);
            mTvProcessFirFir.setSelected(false);
            mTvProcessFirSec.setSelected(false);

            mIvProcessSec.setSelected(false);
            mTvProcessSecFir.setSelected(false);
            mTvProcessSecSec.setSelected(false);

            mIvProcessThird.setSelected(true);
            mTvProcessThirdFir.setSelected(true);
            mTvProcessThirdSec.setSelected(true);
        }
    }
}
