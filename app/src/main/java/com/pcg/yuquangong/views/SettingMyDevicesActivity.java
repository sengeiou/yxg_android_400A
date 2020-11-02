package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.adapters.SettingMyDevicesAdapter;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.SettingMyDeviceEntity;
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

public class SettingMyDevicesActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.rcvSettingMyDevicesList)
    RecyclerView mRcvSettingMyDevicesList;
    @BindView(R.id.tvSettingMyDeviceTitle)
    TextView mTvSettingMyDeviceTitle;

    private SettingMyDevicesAdapter mAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SettingMyDevicesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_my_devices);
        ButterKnife.bind(this);

        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    private void initViews() {
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mTvSettingMyDeviceTitle.setTypeface(App.getInstance().getHomeTextTypeface());

        initRcvList();
        loadData();
    }

    private void initRcvList() {
        List<SettingMyDeviceEntity> data = new ArrayList<>();

        String cacheData = CacheManager.getInstance().getSettingMyDevicesCache();
        if (!TextUtils.isEmpty(cacheData)) {
            List<SettingMyDeviceEntity> deviceCacheList = GsonHelper.getGson().fromJson(cacheData, new TypeToken<List<SettingMyDeviceEntity>>() {
            }.getType());
            if (deviceCacheList != null && !deviceCacheList.isEmpty()) {
                data.addAll(deviceCacheList);
            }
        }

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mAdapter = new SettingMyDevicesAdapter(R.layout.item_setting_my_devices, data);
        mRcvSettingMyDevicesList.setLayoutManager(layoutManager);
        mRcvSettingMyDevicesList.setAdapter(mAdapter);

        mAdapter.disableLoadMoreIfNotFullPage(mRcvSettingMyDevicesList);
        mAdapter.setEnableLoadMore(false);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mRcvSettingMyDevicesList.addItemDecoration(new RcvGridItemDecoration(spacingInPixels));
    }

    private void loadData() {
        ApiClient.getApiService().getSettingMyDevices(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<SettingMyDeviceEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<List<SettingMyDeviceEntity>> entity) {
                        if (entity.isSuccess() && entity.getData() != null) {
                            List<SettingMyDeviceEntity> devicesList = entity.getData();
                            refreshData(devicesList);
                            CacheManager.getInstance().setSettingMyDevicesCache(GsonHelper.getGson().toJson(devicesList));
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

    private void refreshData(List<SettingMyDeviceEntity> myDeviceEntityList) {
        mAdapter.replaceData(myDeviceEntityList);
        mAdapter.loadMoreComplete();
    }

}
