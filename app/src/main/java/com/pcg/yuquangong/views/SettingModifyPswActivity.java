package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.body.ModifyPswBody;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.ZCodeItemEntity;
import com.pcg.yuquangong.utils.GsonHelper;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.utils.ToastUtils;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SettingModifyPswActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.tvSettingModifyPswTitle)
    TextView mTvSettingModifyPswTitle;
    @BindView(R.id.edtPhoneNum)
    EditText mEdtPhoneNum;
    @BindView(R.id.edtVerifyCode)
    EditText mEdtVerifyCode;
    @BindView(R.id.edtPsw)
    EditText mEdtPsw;
    @BindView(R.id.btnConfirm)
    Button mBtnConfirm;
    @BindView(R.id.btnGetCaptcha)
    TextView mBtnGetCaptcha;
    @BindView(R.id.spinnerInternational)
    NiceSpinner mSpinnerInternational;

    public static final String TAG = SettingModifyPswActivity.class.getSimpleName();

//    private String mServerReturnGaptchaCode;

    private String mCurrentCountryPhoneCode = "86";

    private boolean mHasGetCaptchaCode;

    private static final String[] m_Codes = {
            "86",
            "93",
            "355",
            "213",
            "376",
            "244",
            "672",
            "54",
            "374",
            "297",
            "61",
            "43",
            "994",
            "973",
            "880",
            "375",
            "32",
            "501",
            "229",
            "975",
            "591",
            "387",
            "267",
            "55",
            "673",
            "359",
            "226",
            "257",
            "855",
            "237",
            "1",
            "238",
            "236",
            "235",
            "56",
            "61",
            "61",
            "57",
            "269",
            "682",
            "506",
            "385",
            "53",
            "357",
            "420",
            "243",
            "45",
            "253",
            "670",
            "593",
            "20",
            "503",
            "240",
            "291",
            "372",
            "251",
            "500",
            "298",
            "679",
            "358",
            "33",
            "689",
            "241",
            "220",
            "995",
            "49",
            "233",
            "350",
            "30",
            "299",
            "502",
            "224",
            "245",
            "592",
            "509",
            "504",
            "852",
            "36",
            "91",
            "62",
            "98",
            "964",
            "353",
            "44",
            "972",
            "39",
            "225",
            "81",
            "962",
            "7",
            "254",
            "686",
            "965",
            "996",
            "856",
            "371",
            "961",
            "266",
            "231",
            "218",
            "423",
            "370",
            "352",
            "853",
            "389",
            "261",
            "265",
            "60",
            "960",
            "223",
            "356",
            "692",
            "222",
            "230",
            "262",
            "52",
            "691",
            "373",
            "377",
            "976",
            "382",
            "212",
            "258",
            "95",
            "264",
            "674",
            "977",
            "31",
            "599",
            "687",
            "64",
            "505",
            "227",
            "234",
            "683",
            "850",
            "47",
            "968",
            "92",
            "680",
            "507",
            "675",
            "595",
            "51",
            "63",
            "870",
            "48",
            "351",
            "1",
            "974",
            "242",
            "40",
            "7",
            "250",
            "590",
            "290",
            "508",
            "685",
            "378",
            "239",
            "966",
            "221",
            "381",
            "248",
            "232",
            "65",
            "421",
            "386",
            "677",
            "252",
            "27",
            "82",
            "34",
            "94",
            "249",
            "597",
            "268",
            "46",
            "41",
            "963",
            "886",
            "992",
            "255",
            "66",
            "228",
            "690",
            "676",
            "216",
            "90",
            "993",
            "688",
            "256",
            "380",
            "971",
            "44",
            "1",
            "598",
            "998",
            "678",
            "39",
            "58",
            "84",
            "681",
            "967",
            "260",
            "263"};

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SettingModifyPswActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_modify_psw);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
//        final List<String> dataset = new LinkedList<>();
//        for (int i = 0; i < m_Codes.length; i++) {
//            dataset.add(m_Codes[i]);
//        }
        if (App.getInstance().getZCodeList().isEmpty()) {
            getZCode();
        } else {
            initSpinner();
        }

        mTvSettingModifyPswTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnConfirm.setTypeface(App.getInstance().getHomeTextTypeface());
    }

    private void initSpinner() {
        mCurrentCountryPhoneCode = "" + App.getInstance().getZCodeList().get(0).getZcode();
        mSpinnerInternational.attachDataSource(App.getInstance().getZCodeList());
        mSpinnerInternational.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentCountryPhoneCode = "" + App.getInstance().getZCodeList().get(position).getZcode();
                LogUtil.e(TAG, "spinner selected = " + mCurrentCountryPhoneCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btnGetCaptcha)
    void getCatchaClick() {
        String phoneNumber = mEdtPhoneNum.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtils.showToast(getString(R.string.phone_not_correct));
            return;
        }
//        phoneNumber = mCurrentCountryPhoneCode + phoneNumber;
        mBtnGetCaptcha.setText(getString(R.string.has_send));
        getCatchaCode(phoneNumber, mCurrentCountryPhoneCode);
        // test data
//        getCatchaCode("13012341234");
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.btnConfirm)
    void btnConfirmClick() {
        String edtCatpchaCode = mEdtVerifyCode.getText().toString();

        if (!mHasGetCaptchaCode) {
            ToastUtils.showToast(getString(R.string.captcha_get_error));
            return;
        }
//
//        if (!TextUtils.equals(mServerReturnGaptchaCode, edtCatpchaCode)) {
//            ToastUtils.showToast(getString(R.string.captcha_not_correct));
//            return;
//        }

        String phoneStr = mEdtPhoneNum.getText().toString();
        if (TextUtils.isEmpty(phoneStr)) {
            ToastUtils.showToast(getString(R.string.phone_not_correct));
            return;
        }

//        phoneStr = mCurrentCountryPhoneCode + phoneStr;

        String pswStr = mEdtPsw.getText().toString();
        if (TextUtils.isEmpty(pswStr)) {
            ToastUtils.showToast(getString(R.string.psw_not_correct));
            return;
        }

        modifyPsw(phoneStr, pswStr, edtCatpchaCode);
    }

    private void getZCode() {
        ApiClient.getApiService().getZCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<ZCodeItemEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<List<ZCodeItemEntity>> entity) {
                        if (entity.isSuccess() && entity.getData() != null
                                && !entity.getData().isEmpty()) {
                            App.getInstance().setZCodeList(entity.getData());
                            CacheManager.getInstance().setPhoneCodeListCache(GsonHelper.getGson().toJson(entity.getData()));
                            initSpinner();
                        } else {
                            ToastUtils.showToast(entity.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "get zCode onError e = " + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void modifyPsw(String phone, String psw, String captchaCode) {
        ModifyPswBody body = new ModifyPswBody(phone, captchaCode, psw);
        body.setZcode(mCurrentCountryPhoneCode);
        ApiClient.getApiService().modifyPsw(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()), body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<String> entity) {
                        if (entity.isSuccess()) {
                            ToastUtils.showToast(getString(R.string.submit_success));
                            finish();
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

    private void getCatchaCode(String phoneNumber, String zcode) {
        ApiClient.getApiService().getCaptchaCode(phoneNumber, zcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<String> entity) {
                        LogUtil.e(TAG, "getCatchaCode onNext");
                        if (entity.isSuccess()) {
//                            mServerReturnGaptchaCode = entity.getData();
                            mHasGetCaptchaCode = true;
                            mBtnGetCaptcha.setText(getString(R.string.has_send));
                        } else {
                            ToastUtils.showToast(entity.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "getCatchaCode onError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
