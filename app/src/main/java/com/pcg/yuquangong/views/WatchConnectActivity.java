package com.pcg.yuquangong.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;
import com.crrepa.ble.conn.listener.CRPBleConnectionStateListener;
import com.crrepa.ble.scan.bean.CRPScanDevice;
import com.crrepa.ble.scan.callback.CRPScanCallback;
import com.google.gson.reflect.TypeToken;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.adapters.WatchConnectHistoryAdapter;
import com.pcg.yuquangong.adapters.WatchScanResultsAdapter;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.model.event.WatchConnectStateEvent;
import com.pcg.yuquangong.utils.GsonHelper;
import com.pcg.yuquangong.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WatchConnectActivity extends BaseActivity {

    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.tvWatchConnectedDevicesTitle)
    TextView mTvWatchConnectedDevicesTitle;
    @BindView(R.id.layConnectedWatch)
    LinearLayout mlayConnectedWatch;
    @BindView(R.id.tvWatchName)
    TextView mTvWatchName;
    @BindView(R.id.tvWatchConnectState)
    TextView mTvWatchConnectState;
    @BindView(R.id.tvWatchNearbyDevicesTitle)
    TextView mTvWatchNearbyDevicesTitle;
    @BindView(R.id.rcvNearbyWatch)
    RecyclerView mRcvNearbyWatch;
    @BindView(R.id.tvWatchMyDevicesTitle)
    TextView mTvWatchMyDevicesTitle;
    @BindView(R.id.rcvMyDevice)
    RecyclerView mRcvMyDevice;
    @BindView(R.id.layNearbyDevices)
    LinearLayout mLayNearbyDevices;
    @BindView(R.id.layMyDevices)
    LinearLayout mLayMyDevices;
    @BindView(R.id.tvWatchConnectTitle)
    TextView mTvWatchConnectTitle;

    public static final String TAG = WatchConnectActivity.class.getSimpleName();

    private WatchScanResultsAdapter mNearbyWatchAdapter;
    private WatchConnectHistoryAdapter mMyDeviceAdapter;

    // 蓝牙手表相关
    private CRPBleClient mBleClient;
    private CRPBleDevice mBleDevice;
    private CRPBleConnection mBleConnection;

    private boolean mScanState = false; // true表示扫描成功，false为失败
    private static final int SCAN_PERIOD = 10 * 1000;// 扫描附近设别需要时间

    // 维护我的设备列表
    private List<CRPScanDevice> mConnectedCacheList = new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, WatchConnectActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_connect);
        ButterKnife.bind(this);

        initViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelScan();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.tvWatchConnectState)
    void disconnectClick() {
        if (mBleDevice != null && mBleDevice.isConnected()) {
            mBleDevice.disconnect();
            setConnectViewEnabled(false);
        }
    }

    private void initViews() {
        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
        mTvWatchConnectedDevicesTitle.setTypeface(App.getInstance().getHomeTextTypeface());

        mProgressDialog = new ProgressDialog(this);
        mBleClient = App.getInstance().getBleClient();
        initConnectedView();
        initScanWatchView();
        initHistoryWatchView();
        loadData();
    }

    private void initConnectedView() {
        mBleDevice = App.getInstance().getBleDevice();
        mBleConnection = App.getInstance().getBleConnection();

        if (mBleDevice != null && mBleDevice.isConnected()) {
            setConnectViewEnabled(true);
            mTvWatchName.setText(mBleDevice.getName());
        }
    }

    private void initScanWatchView() {
        mRcvNearbyWatch.setHasFixedSize(true);
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        mRcvNearbyWatch.setLayoutManager(recyclerLayoutManager);
        mNearbyWatchAdapter = new WatchScanResultsAdapter();
        mRcvNearbyWatch.setAdapter(mNearbyWatchAdapter);
        mNearbyWatchAdapter.setOnAdapterItemClickListener(new WatchScanResultsAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterViewClick(View view) {
                final int childAdapterPosition = mRcvNearbyWatch.getChildAdapterPosition(view);
                final CRPScanDevice itemAtPosition = mNearbyWatchAdapter.getItemAtPosition(childAdapterPosition);
                if (!mConnectedCacheList.contains(itemAtPosition)) {
                    mConnectedCacheList.add(itemAtPosition);
                }
                CacheManager.getInstance().setWatchConnectHistoryListCache(GsonHelper.getGson().toJson(mConnectedCacheList));
                onAdapterItemClick(itemAtPosition);
            }
        });
    }

    private void initHistoryWatchView() {
        List<CRPScanDevice> myDevicesData = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mMyDeviceAdapter = new WatchConnectHistoryAdapter(R.layout.item_watch_unconnect, myDevicesData);
        mRcvMyDevice.setLayoutManager(layoutManager);
        mRcvMyDevice.setAdapter(mMyDeviceAdapter);

        mMyDeviceAdapter.disableLoadMoreIfNotFullPage(mRcvMyDevice);
        mMyDeviceAdapter.setEnableLoadMore(false);

        mMyDeviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final CRPScanDevice clickItemData = (CRPScanDevice) adapter.getItem(position);
                if (clickItemData == null) {
                    return;
                }
                onAdapterItemClick(clickItemData);
            }
        });
    }

    private void onAdapterItemClick(CRPScanDevice scanResults) {
        final String macAddress = scanResults.getDevice().getAddress();
        mBleClient.cancelScan();
        // 连接手表蓝牙
        if (TextUtils.isEmpty(macAddress)) {
            return;
        }
        mBleDevice = mBleClient.getBleDevice(macAddress);
        if (mBleDevice != null && !mBleDevice.isConnected()) {
            App.getInstance().setBleDevice(mBleDevice);
            connect();
        }
    }

    private void connect() {
        mProgressDialog.show();
        mBleConnection = mBleDevice.connect();
        mBleConnection.setConnectionStateListener(new CRPBleConnectionStateListener() {
            @Override
            public void onConnectionStateChange(int newState) {
                LogUtil.e(TAG, "onConnectionStateChange: " + newState);
                int state = -1;
                switch (newState) {
                    case CRPBleConnectionStateListener.STATE_CONNECTED:
                        mProgressDialog.dismiss();
                        App.getInstance().setBleConnection(mBleConnection);
                        updateConnectedView();
                        testSet();
                        break;
                    case CRPBleConnectionStateListener.STATE_CONNECTING:
//                        state = R.string.state_connecting;
                        break;
                    case CRPBleConnectionStateListener.STATE_DISCONNECTED:
//                        state = R.string.state_disconnected;
                        mProgressDialog.dismiss();
                        App.getInstance().setBleConnection(null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new WatchConnectStateEvent(false, ""));
                            }
                        });
//                        updateTextView(btnBleDisconnect, getString(R.string.connect));
                        break;
                }
//                updateConnectState(state);
            }
        });

//        mBleConnection.setStepChangeListener(mStepChangeListener);
//        mBleConnection.setSleepChangeListener(mSleepChangeListener);
    }

    private void testSet() {
        mBleConnection.syncTime();
        mBleConnection.queryPastHeartRate();
    }

    private void updateConnectedView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setConnectViewEnabled(true);
                mTvWatchName.setText(mBleDevice.getName());
                EventBus.getDefault().post(new WatchConnectStateEvent(true, mBleDevice.getName()));
            }
        });
    }

    private void loadData() {
        startScan();
        loadConnectHistoryCacheData();
    }

    // 手表蓝牙扫描
    private void startScan() {
        boolean success = mBleClient.scanDevice(new CRPScanCallback() {
            @Override
            public void onScanning(final CRPScanDevice device) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mNearbyWatchAdapter.addScanResult(device);
                    }
                });
            }

            @Override
            public void onScanComplete(List<CRPScanDevice> results) {
                if (mScanState) {
                    mScanState = false;
//                    updateButtonUIState();
                }
            }
        }, SCAN_PERIOD);
        if (success) {
            mScanState = true;
//            updateButtonUIState();
            mNearbyWatchAdapter.clearScanResults();
        }
    }

    private void cancelScan() {
        mBleClient.cancelScan();
    }

    private void loadConnectHistoryCacheData() {
        List<CRPScanDevice> data = new ArrayList<>();
        String cacheData = CacheManager.getInstance().getWatchConnectHistoryListCache();
        if (!TextUtils.isEmpty(cacheData)) {
            List<CRPScanDevice> cacheList = GsonHelper.getGson().fromJson(cacheData, new TypeToken<List<CRPScanDevice>>() {
            }.getType());
            if (cacheList != null && !cacheList.isEmpty()) {
                setConnectHistoryViewEnabled(true);
                data.addAll(cacheList);
                mMyDeviceAdapter.replaceData(data);
                mMyDeviceAdapter.loadMoreComplete();
            }
        }
    }

    private void setConnectHistoryViewEnabled(boolean enabled) {
        mTvWatchMyDevicesTitle.setVisibility(enabled ? View.VISIBLE : View.GONE);
        mLayMyDevices.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    private void setConnectViewEnabled(boolean enabled) {
        mTvWatchConnectedDevicesTitle.setVisibility(enabled ? View.VISIBLE : View.GONE);
        mlayConnectedWatch.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

}
