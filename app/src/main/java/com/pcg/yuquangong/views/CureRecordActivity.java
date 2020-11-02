package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.adapters.CureRecordAdapter;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.CureRecordItemEntity;
import com.pcg.yuquangong.model.network.entity.MemberListItemEntity;
import com.pcg.yuquangong.utils.GsonHelper;
import com.pcg.yuquangong.utils.ToastUtils;
import com.pcg.yuquangong.views.widgets.RcvGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CureRecordActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.tvCureRecordTitle)
    TextView mTvCureRecordTitle;
    @BindView(R.id.edtCureRecordSearch)
    EditText mEdtCureRecordSearch;
    @BindView(R.id.btnCureRecordSearch)
    ImageView mBtnCureRecordSearch;
    @BindView(R.id.layCureRecordSearch)
    LinearLayout mLayCureRecordSearch;
    @BindView(R.id.rcvCureRecordList)
    RecyclerView mRcvCureRecordList;

    private CureRecordAdapter mAdapter;
    private List<CureRecordItemEntity> mCureRecordList = new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, CureRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cure_record);
        ButterKnife.bind(this);

        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btnCureRecordSearch)
    void searchClick() {
        String searchText = mEdtCureRecordSearch.getText().toString();
        if (!TextUtils.isEmpty(searchText)) {
            searchCureRecord(searchText);
        }
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    private void initViews() {
        mTvCureRecordTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());

        mEdtCureRecordSearch.setTypeface(App.getInstance().getEdtTextTypeface());

        mEdtCureRecordSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEdtCureRecordSearch.getText().toString())) {
                    refreshData(mCureRecordList);
                }
            }
        });
        initRcvCureRecordList();
        loadData();
    }

    private void initRcvCureRecordList() {
        List<CureRecordItemEntity> data = new ArrayList<>();

        String cacheData = CacheManager.getInstance().getCureRecordListCache();
        if (!TextUtils.isEmpty(cacheData)) {
            List<CureRecordItemEntity> cacheList = GsonHelper.getGson().fromJson(cacheData, new TypeToken<List<CureRecordItemEntity>>() {
            }.getType());
            if (cacheList != null && !cacheList.isEmpty()) {
                mCureRecordList.clear();
                mCureRecordList.addAll(cacheList);
                data.addAll(cacheList);
            }
        }

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mAdapter = new CureRecordAdapter(R.layout.item_cure_record, data);
        mRcvCureRecordList.setLayoutManager(layoutManager);
        mRcvCureRecordList.setAdapter(mAdapter);

        mAdapter.disableLoadMoreIfNotFullPage(mRcvCureRecordList);
        mAdapter.setEnableLoadMore(false);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final CureRecordItemEntity clickItemData = (CureRecordItemEntity) adapter.getItem(position);
                if (clickItemData == null) {
                    return;
                }
                startCureDetail(clickItemData);
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final CureRecordItemEntity clickItemData = (CureRecordItemEntity) adapter.getItem(position);
                if (clickItemData == null) {
                    return;
                }
                if (view.getId() == R.id.btnRetryCure) {
                    retryCure(clickItemData);
                } else if (view.getId() == R.id.btnModifyCureRecord) {
                    modifyCureRecord(clickItemData);
                }
            }
        });

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mRcvCureRecordList.addItemDecoration(new RcvGridItemDecoration(spacingInPixels));
    }

    private void loadData() {
        ApiClient.getApiService().getAllCureList(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<CureRecordItemEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<List<CureRecordItemEntity>> entity) {
                        if (entity.isSuccess()) {
                            List<CureRecordItemEntity> cureRecordList = entity.getData();
                            if (cureRecordList != null && !cureRecordList.isEmpty()) {
                                mCureRecordList.clear();
                                mCureRecordList.addAll(cureRecordList);
                                refreshData(cureRecordList);
                                CacheManager.getInstance().setCureRecordListCache(GsonHelper.getGson().toJson(cureRecordList));
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

    private void searchCureRecord(String keyWord) {
        List<CureRecordItemEntity> searchResultList = new ArrayList<>();
        if (!mCureRecordList.isEmpty()) {
            for (CureRecordItemEntity itemEntity : mCureRecordList) {
                if ((!TextUtils.isEmpty(itemEntity.getName()) && itemEntity.getName().contains(keyWord))
                        || ((String.valueOf(itemEntity.getMember_no()) != null) && String.valueOf(itemEntity.getMember_no()).contains(keyWord))) {
                    searchResultList.add(itemEntity);
                }
            }
        }
        refreshData(searchResultList);
    }

    private void refreshData(List<CureRecordItemEntity> memberList) {
        List<CureRecordItemEntity> data = new ArrayList<>();
        data.addAll(memberList);
        mAdapter.replaceData(data);
        mAdapter.loadMoreComplete();
    }

    private void startCureDetail(CureRecordItemEntity itemEntity) {
        CureDetailActivity.startActivity(mContext, itemEntity, false);
    }

    private void retryCure(CureRecordItemEntity mEntity) {
//        ToastUtils.showToast("再次治疗" + itemEntity.getCureName());
        int comb_head = -1;
        List<CureRecordItemEntity.CombHeadBean> combHeadList = mEntity.getComb_head();
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
            comb_head = Constant.Sorting.CURE_DOUBLE_DEVICE;
        }
        YourCurrentBestAcupointActivity.startActivity(mContext, mEntity.getBirthday(),
                mEntity.getCalendar(), mEntity.getMember_id(), mEntity.getName(),
                mEntity.getMagnetic(), mEntity.getSymptom(), mEntity.getComb_mpa(), comb_head, false);
        finish();
    }

    private void modifyCureRecord(CureRecordItemEntity mEntity) {
//        ToastUtils.showToast("修改治疗方式" + itemEntity.getCureName());
        ChooseSortingMethodActivity.startActivity(mContext, mEntity.getBirthday(),
                mEntity.getCalendar(), mEntity.getMember_id(), mEntity.getName(), false);
    }

}
