package com.pcg.yuquangong.adapters;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.model.network.entity.SettingMyDeviceEntity;

import java.util.List;

public class SettingMyDevicesAdapter extends BaseQuickAdapter<SettingMyDeviceEntity, BaseViewHolder> {

    public SettingMyDevicesAdapter(int layoutResId, @Nullable List<SettingMyDeviceEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SettingMyDeviceEntity item) {
        helper.setText(R.id.tvSettingItemDeviceName, mContext.getString(R.string.setting_my_device_name, item.getDevice_model()));
        helper.setText(R.id.tvSettingItemDeviceNum, mContext.getString(R.string.setting_my_device_num, item.getDevice_no()));
    }

}
