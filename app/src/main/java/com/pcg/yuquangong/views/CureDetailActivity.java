package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.network.body.AddCureRecordBody;
import com.pcg.yuquangong.model.network.entity.CureRecordItemEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 治疗详情
 */
public class CureDetailActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.tvCureDetailTitle)
    TextView mTvCureDetailTitle;
    @BindView(R.id.btnReCure)
    Button mBtnReCure;
    @BindView(R.id.btnChooseCureMethod)
    Button mBtnChooseCureMethod;
    @BindView(R.id.layCureDetailBottom)
    LinearLayout mLayCureDetailBottom;
    @BindView(R.id.tvCurePerson)
    TextView mTvCurePerson;
    @BindView(R.id.tvCureBirthday)
    TextView mTvCureBirthday;
    @BindView(R.id.tvSortingMethod)
    TextView mTvSortingMethod;
    @BindView(R.id.tvSortingHead)
    TextView mTvSortingHead;
    @BindView(R.id.tvMagneticFieldIntensity)
    TextView mTvMagneticFieldIntensity;
    @BindView(R.id.tvBestAcupoint)
    TextView mTvBestAcupoint;
    @BindView(R.id.tvTreatTimeDuration)
    TextView mTvTreatTimeDuration;
    @BindView(R.id.tvTreatTime)
    TextView mTvTreatTime;

    private CureRecordItemEntity mEntity;

    private boolean mBackToMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cure_detail);
        ButterKnife.bind(this);

        initData();
        initViews();
    }

    public static void startActivity(Context context, CureRecordItemEntity itemEntity, boolean backToMain) {
        Intent intent = new Intent();
        intent.setClass(context, CureDetailActivity.class);
        intent.putExtra("item_entity", itemEntity);
        intent.putExtra("back_to_main", backToMain);
        context.startActivity(intent);
    }

    private void initData() {
        mEntity = getIntent().getParcelableExtra("item_entity");
        mBackToMain = getIntent().getBooleanExtra("back_to_main", false);
    }

    private void initViews() {
        if (mEntity != null) {
            mTvCurePerson.setText(getString(R.string.cure_person, mEntity.getName()));
            mTvCureBirthday.setText(getString(R.string.cure_person_birthday, mEntity.getBirthday()) + " ( " + getCalendarString(mEntity.getCalendar()) + " ) ");


            mTvSortingMethod.setText(getString(R.string.sorting_method, getSortingMethod(mEntity.getSymptom()) + "-" + getDiseaseLevelText(mEntity.getComb_mpa())));
            mTvSortingHead.setText(getString(R.string.sorting_head, getSortHead(mEntity.getComb_head())));
            mTvMagneticFieldIntensity.setText(getString(R.string.magnetic_field_intensity, getMagnetic(mEntity.getMagnetic())));
            mTvBestAcupoint.setText(getString(R.string.best_acupoint, getAcupoint(mEntity.getAcupoint())));


            mTvTreatTime.setText(getString(R.string.treat_time, mEntity.getCreated_at()));
            mTvTreatTimeDuration.setText(getString(R.string.treat_time_duration, mEntity.getDuration()));
        }

        mTvCureDetailTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnReCure.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnChooseCureMethod.setTypeface(App.getInstance().getHomeTextTypeface());
    }

    private String getCalendarString(int calendar) {
        if (calendar == Constant.Member.CALENDAR) {
            return getString(R.string.calendar);
        } else {
            return getString(R.string.lunar_calendar);
        }
    }

    private String getSortingMethod(int symptom) {
        switch (symptom) {
            case Constant
                    .Sorting.SORTING_METHOD_TT:
                return getString(R.string.pain);
            case Constant
                    .Sorting.SORTING_METHOD_YZ:
                return getString(R.string.inflammation);
            case Constant
                    .Sorting.SORTING_METHOD_ZK:
                return getString(R.string.the_lump);
            case Constant
                    .Sorting.SORTING_METHOD_TNB:
                return getString(R.string.three_high) + "-" + getString(R.string.diabetes);
            case Constant
                    .Sorting.SORTING_METHOD_GXY:
                return getString(R.string.three_high) + "-" + getString(R.string.high_blood_pressure_normal);
            case Constant
                    .Sorting.SORTING_METHOD_GXZ:
                return getString(R.string.three_high) + "-" + getString(R.string.hyperlipidemia_normal);
        }
        return "";
    }

    private String getSortHead(List<CureRecordItemEntity.CombHeadBean> combHeadList) {
        if (combHeadList != null) {
            if (combHeadList.size() == 1) {
                CureRecordItemEntity.CombHeadBean headBean = combHeadList.get(0);
                if (headBean != null) {
                    if (headBean.getId() == 1) {
                        return getString(R.string.comb_head_left3);
                    } else {
                        return getString(R.string.comb_head_right3);
                    }
                }
            } else if (combHeadList.size() == 2) {
                int combHeadNum = getCombHeadNumber(combHeadList);
                if (combHeadNum == Constant.Sorting.CURE_DOUBLE_DEVICE) {
                    return getString(R.string.comb_head_double3);
                } else if (combHeadNum == Constant.Sorting.CURE_LEFT_DEVICE) {
                    return getString(R.string.comb_head_left3);
                } else if (combHeadNum == Constant.Sorting.CURE_RIGHT_DEVICE) {
                    return getString(R.string.comb_head_right3);
                }
            }
        }
        return getString(R.string.comb_head_double3);
    }

    private String getAcupoint(int acupoint) {
        int acupintNumber = Constant.AcupointUtils.getAcupointNumberByDeviceReturnPoint(acupoint);
        if (acupintNumber == Constant.AcupointNumber.ERROR_POINT) {
            return "";
        }
        String acupointStr = Constant.AcupointUtils.getAcupointStringByNumber(acupintNumber);
        return acupointStr;
    }

    private String getMagnetic(int magnetic) {
        switch (magnetic) {
            case Constant.Sorting.LITTLE:
                return getString(R.string.little);
            case Constant.Sorting.MIDDLE:
                return getString(R.string.middle);
            case Constant.Sorting.HIGN:
                return getString(R.string.high);
        }
        return "";
    }

    public String getDiseaseLevelText(int comb_mpa) {
        switch (comb_mpa) {
            case Constant.Sorting.LITTLE:
                return "轻度";
            case Constant.Sorting.MIDDLE:
                return "中度";
            case Constant.Sorting.HIGN:
                return "重度";
        }
        return "轻度";
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        if (mBackToMain) {
            MainActivity.startActivity(mContext);
        }
        finish();
    }

    @OnClick(R.id.btnReCure)
    void btnRecureClick() {
        int comb_head = getCombHeadNumber(mEntity.getComb_head());

        YourCurrentBestAcupointActivity.startActivity(mContext, mEntity.getBirthday(),
                mEntity.getCalendar(), mEntity.getMember_id(), mEntity.getName(),
                mEntity.getMagnetic(), mEntity.getSymptom(), mEntity.getComb_mpa(), comb_head, true);
        finish();
    }

    private int getCombHeadNumber(List<CureRecordItemEntity.CombHeadBean> combHeadList) {

        int comb_head = Constant.Sorting.CURE_DOUBLE_DEVICE;

        if (null != combHeadList) {
            if (combHeadList.size() == 1) {
                CureRecordItemEntity.CombHeadBean headBean = combHeadList.get(0);
                if (headBean != null) {
                    if (headBean.getId() == 1) {
                        comb_head = Constant.Sorting.CURE_LEFT_DEVICE;
                    } else {
                        comb_head = Constant.Sorting.CURE_RIGHT_DEVICE;
                    }
                }
            } else if (combHeadList.size() == 2) {

                int count = 0;
                CureRecordItemEntity.CombHeadBean usedCombHeadBean = null;

                for (CureRecordItemEntity.CombHeadBean itemCombHeadBean : combHeadList) {
                    if (itemCombHeadBean.getIs_used() == 1) {
                        count = count + 1;
                        usedCombHeadBean = itemCombHeadBean;
                    }
                }

                if (count == 2) {
                    comb_head = Constant.Sorting.CURE_DOUBLE_DEVICE;
                } else {
                    if (usedCombHeadBean != null) {
                        if (usedCombHeadBean.getId() == 1) {
                            comb_head = Constant.Sorting.CURE_LEFT_DEVICE;
                        } else {
                            comb_head = Constant.Sorting.CURE_RIGHT_DEVICE;
                        }
                    }
                }

            }
        }

        return comb_head;

    }

    @OnClick(R.id.btnChooseCureMethod)
    void btnChooseCureMethodClick() {
        ChooseSortingMethodActivity.startActivity(mContext, mEntity.getBirthday(),
                mEntity.getCalendar(), mEntity.getMember_id(), mEntity.getName(), true);
    }

}
