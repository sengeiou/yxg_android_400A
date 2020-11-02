package com.pcg.yuquangong.adapters;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crrepa.ble.scan.bean.CRPScanDevice;
import com.pcg.yuquangong.R;

import java.util.List;

public class WatchConnectHistoryAdapter extends BaseQuickAdapter<CRPScanDevice, BaseViewHolder> {

    public WatchConnectHistoryAdapter(int layoutResId, @Nullable List<CRPScanDevice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CRPScanDevice item) {
        final BluetoothDevice bleDevice = item.getDevice();
//        helper.setText(R.id.tvItemWatchName, String.format("%s (%s) RSSI: %d", bleDevice.getAddress(), bleDevice.getName(), item.getRssi()));
        helper.setText(R.id.tvItemWatchName, bleDevice.getName());
    }

}
