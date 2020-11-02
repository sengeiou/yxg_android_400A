package com.pcg.yuquangong.views;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.adapters.ChooseCurePersonAdapter;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
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

/**
 * 选择治疗人
 */
public class ChooseCurePersonActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.tvChooseCurePersonTitle)
    TextView mTvChooseCurePersonTitle;
    @BindView(R.id.edtChooseCurePersonSearch)
    EditText mEdtChooseCurePersonSearch;
    @BindView(R.id.btnChooseCurePersonSearch)
    ImageView mBtnChooseCurePersonSearch;
    @BindView(R.id.layChooseCurePersonSearch)
    LinearLayout mLayChooseCurePersonSearch;
    @BindView(R.id.btnSkipNext)
    Button mBtnSkipNext;
    @BindView(R.id.rcvChooseCurePersonList)
    RecyclerView mRcvChooseCurePersonList;

    private ChooseCurePersonAdapter mAdapter;
    private List<MemberListItemEntity> mMemberList = new ArrayList<>();

    private int mPreviousClickIndex = -1;
    private int mCurrentSelectedIndex = -1;

    private String mActivityType;
    public static final String ACTIVITY_TYPE_FOR_RESULT = "for_result";

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ChooseCurePersonActivity.class);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity context, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, ChooseCurePersonActivity.class);
        intent.putExtra("type", ACTIVITY_TYPE_FOR_RESULT);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cure_person);
        ButterKnife.bind(this);

        initData();
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btnSkipNext)
    void skipNextClick() {
        // 测量返回数据
        if (TextUtils.equals(mActivityType, ACTIVITY_TYPE_FOR_RESULT)) {
            MemberListItemEntity clickItem;
            if (mCurrentSelectedIndex == -1) {
                clickItem = queryCurrentPhoneMember();
                if (clickItem == null) {
                    ToastUtils.showToast(getString(R.string.choose_member_please));
                    return;
                }
            } else {
                clickItem = mMemberList.get(mCurrentSelectedIndex);
                if (clickItem == null) {
                    ToastUtils.showToast(getString(R.string.choose_member_please));
                    return;
                }
            }

            Intent intent = new Intent();
            intent.putExtra(Constant.Member.KEY_MEASURE_MEMBER, clickItem);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            if (mCurrentSelectedIndex == -1) {
                MemberListItemEntity clickItem = queryCurrentPhoneMember();
                if (clickItem == null) {
                    ToastUtils.showToast(getString(R.string.choose_member_please));
                    return;
                }
                ChooseBirthdayActivity.startActivity(mContext, clickItem.getId());
            } else {
                MemberListItemEntity clickItem = mMemberList.get(mCurrentSelectedIndex);
                if (clickItem == null) {
                    ToastUtils.showToast(getString(R.string.choose_member_please));
                    return;
                }
                ChooseSortingMethodActivity.startActivity(mContext, clickItem.getBirthday(),
                        clickItem.getCalendar(), clickItem.getId(), clickItem.getName(),false);
            }
        }
    }

    private MemberListItemEntity queryCurrentPhoneMember() {
        MemberListItemEntity queryItem = null;
        for (MemberListItemEntity itemEntity : mMemberList) {
            if (itemEntity != null && TextUtils.equals(itemEntity.getMobile(), App.getInstance().getLoginEntity().getMobile())) {
                queryItem = itemEntity;
                break;
            }
        }
        return queryItem;
    }

    @OnClick(R.id.btnChooseCurePersonSearch)
    void searchClick() {
        String searchText = mEdtChooseCurePersonSearch.getText().toString();
        if (!TextUtils.isEmpty(searchText)) {
            searchCurePerson(searchText);
        }
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    private void initData() {
        mActivityType = getIntent().getStringExtra("type");
    }

    private void initViews() {
        mTvChooseCurePersonTitle.setTypeface(App.getInstance().getHomeTextTypeface());
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mBtnSkipNext.setTypeface(App.getInstance().getHomeTextTypeface());

        mEdtChooseCurePersonSearch.setTypeface(App.getInstance().getEdtTextTypeface());

        mEdtChooseCurePersonSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEdtChooseCurePersonSearch.getText().toString())) {
                    refreshData(mMemberList);
                }
            }
        });
//        mBtnSkipNext.setText(getString(R.string.next));
        initRcvList();
        loadData();
    }

    private void initRcvList() {
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
        mAdapter = new ChooseCurePersonAdapter(R.layout.item_choose_cure_person, data);
        mRcvChooseCurePersonList.setLayoutManager(layoutManager);
        mRcvChooseCurePersonList.setAdapter(mAdapter);

        mAdapter.disableLoadMoreIfNotFullPage(mRcvChooseCurePersonList);
        mAdapter.setEnableLoadMore(false);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final MemberListItemEntity clickItemData = (MemberListItemEntity) adapter.getItem(position);
                if (clickItemData == null) {
                    return;
                }

                if (clickItemData.isChecked()) {
                    clickItemData.setChecked(false);
                } else {
                    clickItemData.setChecked(true);
                }

                if (mPreviousClickIndex >= 0 && mPreviousClickIndex != position) {
                    final MemberListItemEntity previousClickItemData = (MemberListItemEntity) adapter.getData().get(mPreviousClickIndex);
                    if (previousClickItemData != null) {
                        if (previousClickItemData.isChecked()) {
                            previousClickItemData.setChecked(false);
                        }
                    }
                }

                mPreviousClickIndex = position;

                // 从首页测量进来，一直显示下一步
                if (TextUtils.equals(mActivityType, ACTIVITY_TYPE_FOR_RESULT)) {
                    if (clickItemData.isChecked()) {
                        mCurrentSelectedIndex = position;
                        mBtnSkipNext.setText(getString(R.string.next));
                    } else {
                        mCurrentSelectedIndex = -1;
                        mBtnSkipNext.setText(getString(R.string.skip_next));
                    }
                } else {
                    if (clickItemData.isChecked()) {
                        mCurrentSelectedIndex = position;
                        mBtnSkipNext.setText(getString(R.string.next));
                    } else {
                        mCurrentSelectedIndex = -1;
                        mBtnSkipNext.setText(getString(R.string.skip_next));
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mRcvChooseCurePersonList.addItemDecoration(new RcvGridItemDecoration(spacingInPixels));
    }

    private void loadData() {

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

    private void refreshData(List<MemberListItemEntity> cureRecordList) {
        List<MemberListItemEntity> data = new ArrayList<>();
        data.addAll(cureRecordList);
        mAdapter.replaceData(data);
        mAdapter.loadMoreComplete();
    }

    private void searchCurePerson(String keyWord) {
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
    }

}
