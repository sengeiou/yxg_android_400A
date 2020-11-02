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
import com.pcg.yuquangong.adapters.MemberListAdapter;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.event.RefreshMemberListEvent;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.MemberListItemEntity;
import com.pcg.yuquangong.utils.GsonHelper;
import com.pcg.yuquangong.utils.ToastUtils;
import com.pcg.yuquangong.views.widgets.RcvGridItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MemberListActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.tvMemberListTitle)
    TextView mTvMemberListTitle;
    @BindView(R.id.edtMemberListSearch)
    EditText mEdtMemberListSearch;
    @BindView(R.id.ivMemberListSearch)
    ImageView mIvMemberListSearch;
    @BindView(R.id.layMemberListSearch)
    LinearLayout mLayMemberListSearch;
    @BindView(R.id.rcvMemberList)
    RecyclerView mRcvMemberList;
    @BindView(R.id.btnMemberAdd)
    TextView mBtnMemberAdd;

    private MemberListAdapter mAdapter;
    private List<MemberListItemEntity> mMemberList = new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MemberListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberlist);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveRefreshDataEvent(RefreshMemberListEvent event) {
        loadAllData();
    }

    @OnClick(R.id.ivMemberListSearch)
    void searchMemberClick() {
        String searchText = mEdtMemberListSearch.getText().toString();
        if (!TextUtils.isEmpty(searchText)) {
            searchMember(searchText);
        }
    }

    @OnClick(R.id.btnMemberAdd)
    void memberAddClick() {
        AddModifyMemberActivity.startActivity(mContext, null);
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    private void initViews() {
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnMemberAdd.setTypeface(App.getInstance().getHomeTextTypeface());
        mTvMemberListTitle.setTypeface(App.getInstance().getHomeTextTypeface());

        mEdtMemberListSearch.setTypeface(App.getInstance().getEdtTextTypeface());

        mEdtMemberListSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEdtMemberListSearch.getText().toString())) {
                    refreshData(mMemberList);
                }
            }
        });

        initRcvMemberList();
        loadAllData();
    }

    private void initRcvMemberList() {
        List<MemberListItemEntity> data = new ArrayList<>();

        String cacheData = CacheManager.getInstance().getMemberListCache();
        if (!TextUtils.isEmpty(cacheData)) {
            List<MemberListItemEntity> cacheList = GsonHelper.getGson().fromJson(cacheData, new TypeToken<List<MemberListItemEntity>>() {
            }.getType());
            if (cacheList != null && !cacheList.isEmpty()) {
                mMemberList.clear();
                mMemberList.addAll(cacheList);
                data.addAll(cacheList);
            }
        }

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mAdapter = new MemberListAdapter(R.layout.item_member_list, data);
        mRcvMemberList.setLayoutManager(layoutManager);
        mRcvMemberList.setAdapter(mAdapter);

        mAdapter.disableLoadMoreIfNotFullPage(mRcvMemberList);
        mAdapter.setEnableLoadMore(false);
//        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//
//            }
//        }, mRcvMemberList);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final MemberListItemEntity clickItemData = (MemberListItemEntity) adapter.getItem(position);
                if (clickItemData == null) {
                    return;
                }
                startMemberDetail(clickItemData);
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final MemberListItemEntity clickItemData = (MemberListItemEntity) adapter.getItem(position);
                if (clickItemData == null) {
                    return;
                }
                startGoCure(clickItemData);
            }
        });

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mRcvMemberList.addItemDecoration(new RcvGridItemDecoration(spacingInPixels));
    }

    private void loadAllData() {
        ApiClient.getApiService().getAllMembers(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<MemberListItemEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<List<MemberListItemEntity>> entity) {
                        if (entity.isSuccess()) {
                            List<MemberListItemEntity> memberList = entity.getData();
                            if (memberList != null && !memberList.isEmpty()) {
                                mMemberList.clear();
                                mMemberList.addAll(memberList);
                                refreshData(memberList);
                                CacheManager.getInstance().setMemberListCache(GsonHelper.getGson().toJson(memberList));
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

    private void searchMember(String keyWord) {
        List<MemberListItemEntity> searchResultList = new ArrayList<>();
        if (!mMemberList.isEmpty()) {
            for (MemberListItemEntity itemEntity : mMemberList) {
                if ((!TextUtils.isEmpty(itemEntity.getName()) && itemEntity.getName().contains(keyWord))
                        || ((String.valueOf(itemEntity.getMember_no()) != null) && String.valueOf(itemEntity.getMember_no()).contains(keyWord))
                        || (!TextUtils.isEmpty(itemEntity.getMobile()) && itemEntity.getMobile().contains(keyWord))) {
                    searchResultList.add(itemEntity);
                }
            }
        }
        refreshData(searchResultList);

//        ApiClient.getApiService().getAllMembers(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()), keyWord)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseEntity<List<MemberListItemEntity>>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        mDisposable.add(d);
//                    }
//
//                    @Override
//                    public void onNext(BaseEntity<List<MemberListItemEntity>> entity) {
//                        if (entity.isSuccess()) {
//                            List<MemberListItemEntity> memberList = new ArrayList<>();
//                            if (entity.getData() != null && !entity.getData().isEmpty()) {
//                                memberList.addAll(entity.getData());
//                            }
//                            refreshData(entity.getData());
//                        } else {
//                            ToastUtils.showToast(entity.getMsg());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    private void refreshData(List<MemberListItemEntity> memberList) {
        List<MemberListItemEntity> data = new ArrayList<>();
        data.addAll(memberList);
        mAdapter.replaceData(data);
        mAdapter.loadMoreComplete();
    }

    private void startMemberDetail(MemberListItemEntity itemEntity) {
        AddModifyMemberActivity.startActivity(mContext, itemEntity);
    }

    private void startGoCure(MemberListItemEntity itemEntity) {
        ChooseSortingMethodActivity.startActivity(mContext, itemEntity.getBirthday(),
                itemEntity.getCalendar(), itemEntity.getId(), itemEntity.getName(), false);
    }

}
