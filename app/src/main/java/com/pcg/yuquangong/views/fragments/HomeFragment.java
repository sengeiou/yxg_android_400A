package com.pcg.yuquangong.views.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.adapters.BannerPageAdapter;
import com.pcg.yuquangong.base.BaseFragment;
import com.pcg.yuquangong.model.Constant;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.event.ChangeFragmentEvent;
import com.pcg.yuquangong.model.network.ApiClient;
import com.pcg.yuquangong.model.network.ApiCommon;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.MainBannerEntity;
import com.pcg.yuquangong.utils.GsonHelper;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.views.ChooseCurePersonActivity;
import com.pcg.yuquangong.views.CureRecordActivity;
import com.pcg.yuquangong.views.LoginActivity;
import com.pcg.yuquangong.views.MemberListActivity;
import com.pcg.yuquangong.views.PlayVideoActivity;
import com.pcg.yuquangong.views.WebViewActivity;
import com.pcg.yuquangong.views.interfaces.BannerItemClick;
import com.viewpagerindicator.CirclePageIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.ivStartCure)
    ImageView mIvStartCure;
    @BindView(R.id.tvStartCureFir)
    TextView mTvStartCureFir;
    @BindView(R.id.tvStartCureSec)
    TextView mTvStartCureSec;
    @BindView(R.id.ivMemberList)
    ImageView mIvMemberList;
    @BindView(R.id.tvMemberListFir)
    TextView mTvMemberListFir;
    @BindView(R.id.tvMemberListSec)
    TextView mTvMemberListSec;
    @BindView(R.id.ivCureRecord)
    ImageView mIvCureRecord;
    @BindView(R.id.tvCureRecordFir)
    TextView mTvCureRecordFir;
    @BindView(R.id.tvCureRecordSec)
    TextView mTvCureRecordSec;
    @BindView(R.id.viewPagerBanner)
    ViewPager mViewPagerBanner;
    @BindView(R.id.viewPagerIndicator)
    CirclePageIndicator mViewPagerIndicator;
    Unbinder unbinder;

    @BindView(R.id.tvMainHome)
    TextView mTvMainHome;
    @BindView(R.id.tvMainWatch)
    TextView mTvMainWatch;
    @BindView(R.id.tvMainSetting)
    TextView mTvMainSetting;

    private static final String TAG = HomeFragment.class.getSimpleName();

    private ArrayList<MainBannerEntity> mBannerLists = new ArrayList<>();
    private BannerPageAdapter mBannerAdapter;
    private int mCurrentPlacePage = 0;
    private boolean mIsPlaceAutoScroll = true;
    private static final int MSG_AUTO_PLAY_BANNERS = 23;

    public static final int HOME_CENTER_BTN_START_CURE = 21;
    public static final int HOME_CENTER_BTN_MEMBER_LIST = 22;
    public static final int HOME_CENTER_BTN_CURE_RECORD = 23;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @OnClick(R.id.tvMainHome)
    void mainHomeClick() {
        EventBus.getDefault().post(new ChangeFragmentEvent(ChangeFragmentEvent.FRAGMENT_HOME));
    }

    @OnClick(R.id.tvMainWatch)
    void mainWatchClick() {
        EventBus.getDefault().post(new ChangeFragmentEvent(ChangeFragmentEvent.FRAGMENT_WATCH));
    }

    @OnClick(R.id.tvMainSetting)
    void mainSettingClick() {
        EventBus.getDefault().post(new ChangeFragmentEvent(ChangeFragmentEvent.FRAGMENT_SETTINGS));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacksAndMessages(null);
        unbinder.unbind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeFragment(ChangeFragmentEvent event){
        switch (event.getWhich()){
            case ChangeFragmentEvent.FRAGMENT_HOME:
                loadBanner();
                break;
        }
    }

    @OnClick(R.id.ivStartCure)
    void startCureClick() {
//        selectHomeCenterBtn(HOME_CENTER_BTN_START_CURE);
//        ToastUtils.showToast("点击开始治疗");
        ChooseCurePersonActivity.startActivity(mContext);
    }

    @OnClick(R.id.ivMemberList)
    void memberLisClick() {
//        selectHomeCenterBtn(HOME_CENTER_BTN_MEMBER_LIST);
//        ToastUtils.showToast("点击成员列表");
        MemberListActivity.startActivity(mContext);
    }

    @OnClick(R.id.ivCureRecord)
    void cureRecordClick() {
//        selectHomeCenterBtn(HOME_CENTER_BTN_CURE_RECORD);
//        ToastUtils.showToast("点击治疗记录");
        CureRecordActivity.startActivity(mContext);
    }

    private void selectHomeCenterBtn(int bottomBtnSelected) {

        if (bottomBtnSelected == HOME_CENTER_BTN_START_CURE) {
            mIvStartCure.setSelected(true);
            mTvStartCureFir.setSelected(true);
            mTvStartCureSec.setSelected(true);
            mIvMemberList.setSelected(false);
            mTvMemberListFir.setSelected(false);
            mTvMemberListSec.setSelected(false);
            mIvCureRecord.setSelected(false);
            mTvCureRecordFir.setSelected(false);
            mTvCureRecordSec.setSelected(false);
        } else if (bottomBtnSelected == HOME_CENTER_BTN_MEMBER_LIST) {
            mIvStartCure.setSelected(false);
            mTvStartCureFir.setSelected(false);
            mTvStartCureSec.setSelected(false);
            mIvMemberList.setSelected(true);
            mTvMemberListFir.setSelected(true);
            mTvMemberListSec.setSelected(true);
            mIvCureRecord.setSelected(false);
            mTvCureRecordFir.setSelected(false);
            mTvCureRecordSec.setSelected(false);
        } else if (bottomBtnSelected == HOME_CENTER_BTN_CURE_RECORD) {
            mIvStartCure.setSelected(false);
            mTvStartCureFir.setSelected(false);
            mTvStartCureSec.setSelected(false);
            mIvMemberList.setSelected(false);
            mTvMemberListFir.setSelected(false);
            mTvMemberListSec.setSelected(false);
            mIvCureRecord.setSelected(true);
            mTvCureRecordFir.setSelected(true);
            mTvCureRecordSec.setSelected(true);
        }

    }

    private void loadBannerCache() {
        LogUtil.e(TAG, "loadBannerCache");
        String cacheData = CacheManager.getInstance().getHomeBannerCacheData();
        if (!TextUtils.isEmpty(cacheData)) {
            List<MainBannerEntity> cacheList = GsonHelper.getGson().fromJson(cacheData, new TypeToken<List<MainBannerEntity>>() {
            }.getType());
            if (cacheList != null && !cacheList.isEmpty()) {
                updateBanners(cacheList);
                bannerLoadFinished();
            }
        }
    }

    private void loadBanner() {
        ApiClient.getApiService().getBannerInfo(ApiCommon.getBannerToken(App.getInstance().getLoginEntity().getToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<MainBannerEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseEntity<List<MainBannerEntity>> entity) {
                        LogUtil.e(TAG, "load banner onNext");
                        if (entity.isSuccess()) {
                            if (entity.getData() != null) {
                                CacheManager.getInstance().setHomeBannerCacheData(GsonHelper.getGson().toJson(entity.getData()));
                                updateBanners(entity.getData());
                                bannerLoadFinished();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "load banner error");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void clearLoginCache() {
        App.getInstance().setLoginEntity(null);
        CacheManager.getInstance().setLoginedData("");
    }

    private void initViews() {
        initTextTypeface();
        initViewPagerBanner();

        loadBannerCache();
        loadBanner();

        mHandler.removeMessages(MSG_AUTO_PLAY_BANNERS);
        mHandler.sendEmptyMessageDelayed(MSG_AUTO_PLAY_BANNERS, 3000);
        mViewPagerBanner.setCurrentItem(0);

        mTvMainHome.setSelected(true);

    }

    private void initTextTypeface() {
        Typeface mTypeFace = App.getInstance().getMainHomeTypeface();
        mTvMainHome.setTypeface(App.getInstance().getHomeTextTypeface());
        mTvMainWatch.setTypeface(App.getInstance().getHomeTextTypeface());
        mTvMainSetting.setTypeface(App.getInstance().getHomeTextTypeface());

        mTvStartCureFir.setTypeface(mTypeFace);
        mTvStartCureSec.setTypeface(mTypeFace);
        mTvMemberListFir.setTypeface(mTypeFace);
        mTvMemberListSec.setTypeface(mTypeFace);
        mTvCureRecordFir.setTypeface(mTypeFace);
        mTvCureRecordSec.setTypeface(mTypeFace);

        if (App.getInstance().getLanguageType()==Constant.Language.LAN_EN) {
            mTvStartCureFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvStartCureSec.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvMemberListFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvMemberListSec.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvCureRecordFir.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            mTvCureRecordSec.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
        }

        if (TextUtils.isEmpty(mTvStartCureFir.getText().toString())) {
            mTvStartCureFir.setVisibility(View.GONE);
        } else {
            mTvStartCureFir.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvStartCureSec.getText().toString())) {
            mTvStartCureSec.setVisibility(View.GONE);
        } else {
            mTvStartCureSec.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvMemberListFir.getText().toString())) {
            mTvMemberListFir.setVisibility(View.GONE);
        } else {
            mTvMemberListFir.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvMemberListSec.getText().toString())) {
            mTvMemberListSec.setVisibility(View.GONE);
        } else {
            mTvMemberListSec.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvCureRecordFir.getText().toString())) {
            mTvCureRecordFir.setVisibility(View.GONE);
        } else {
            mTvCureRecordFir.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mTvCureRecordSec.getText().toString())) {
            mTvCureRecordSec.setVisibility(View.GONE);
        } else {
            mTvCureRecordSec.setVisibility(View.VISIBLE);
        }
    }

    private void initViewPagerBanner() {
        mViewPagerBanner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mIsPlaceAutoScroll = false;
                    mHandler.removeMessages(MSG_AUTO_PLAY_BANNERS);
                } else if (event.getAction() == MotionEvent.ACTION_UP
                        || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    mIsPlaceAutoScroll = true;
                    mHandler.removeMessages(MSG_AUTO_PLAY_BANNERS);
                    mHandler.sendEmptyMessageDelayed(MSG_AUTO_PLAY_BANNERS, 3000);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    mIsPlaceAutoScroll = false;
                }
                return false;
            }
        });

        mBannerAdapter = new BannerPageAdapter(mContext,
                new BannerItemClick() {
                    @Override
                    public void onItemClick(View view, int position) {
                        LogUtil.e(TAG, "banner on item click position = " + position);
//                        ToastUtils.showToast("点击了第" + position + "个banner");
                        if (position >= 0 && position < mBannerLists.size()) {
                            MainBannerEntity clickItem = mBannerLists.get(position);
                            if (clickItem != null) {
                                if (TextUtils.equals(clickItem.getGenre(), Constant.Banner.IMAGE_BANNER)) {

                                } else if (TextUtils.equals(clickItem.getGenre(), Constant.Banner.ARTICEL_BANNER)
                                        && !TextUtils.isEmpty(clickItem.getLink())) {
                                    WebViewActivity.startActivity(mContext, clickItem.getLink());
                                } else if (TextUtils.equals(clickItem.getGenre(), Constant.Banner.VIDEO_BANNER)
                                        && !TextUtils.isEmpty(clickItem.getLink())) {
                                    // open video
                                    PlayVideoActivity.startActivity(mContext, clickItem.getLink());
//                                    PlayVideoActivity.startActivity(mContext, "https://raw.githubusercontent.com/danikula/AndroidVideoCache/master/files/orange1.mp4");
                                }
                            }
                        }
                    }
                });

        mViewPagerBanner.setAdapter(mBannerAdapter);
        mViewPagerIndicator.setIsLoop(true);
        mViewPagerIndicator.setViewPager(mViewPagerBanner);
        mViewPagerBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // ViewPager.SCROLL_STATE_IDLE 标识的状态是当前页面完全展现，并且没有动画正在进行中，如果不
                // 是此状态下执行 setCurrentItem 方法回在首位替换的时候会出现跳动！
                if (state != ViewPager.SCROLL_STATE_IDLE)
                    return;

                // 当视图在第一个时，将页面号设置为图片的最后一张。
                updateIndicator(currentPosition, false);
            }
        });

    }

    private void updateIndicator(int position, boolean isScroll) {
        if (mContext == null) {
            return;
        }

        int count = mBannerAdapter.getCount();
        if (count > 1) {
            if (position < 1) {
                mCurrentPlacePage = count - 2;
                mViewPagerBanner.setCurrentItem(mCurrentPlacePage, isScroll);
            } else if (position > count - 2) {
                mCurrentPlacePage = 1;
                mViewPagerBanner.setCurrentItem(mCurrentPlacePage, isScroll);
            } else {
                mCurrentPlacePage = position;
            }
        } else {
            if (position >= mBannerLists.size()) {
                mCurrentPlacePage = 0;
            } else {
                mCurrentPlacePage = position;
            }
            mViewPagerBanner.setCurrentItem(mCurrentPlacePage);
        }
        mViewPagerIndicator.setCurrentItem(mCurrentPlacePage);
    }

    private void updateBanners(List<MainBannerEntity> banners) {
        synchronized (mBannerLists) {
            mBannerLists.clear();
            mBannerLists.addAll(banners);
        }
    }

    private void bannerLoadFinished() {
        mBannerAdapter.setData(mBannerLists);
        if (mBannerLists.size() > 1) {
            updateIndicator(1, false);
        } else {
            updateIndicator(0, false);
        }
        mBannerAdapter.notifyDataSetChanged();
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_AUTO_PLAY_BANNERS: {

                    if (mIsPlaceAutoScroll) {
                        try {
                            mCurrentPlacePage++;
                            updateIndicator(mCurrentPlacePage, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            mViewPagerBanner.setCurrentItem(0);
                            mCurrentPlacePage = 0;
                        }

                        mHandler.removeMessages(MSG_AUTO_PLAY_BANNERS);
                        mHandler.sendEmptyMessageDelayed(MSG_AUTO_PLAY_BANNERS, 3000);
                    }

                }

            }
        }
    };

}
