package com.pcg.yuquangong.adapters;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.model.network.entity.WatchRecordItemEntity;

import java.util.List;

public class WatchRecordAdapter extends BaseQuickAdapter<WatchRecordItemEntity, BaseViewHolder> {

    public WatchRecordAdapter(int layoutResId, @Nullable List<WatchRecordItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WatchRecordItemEntity item) {
        helper.setText(R.id.tvWatchRecordName, item.getName());
        helper.setText(R.id.tvWatchRecordBloodPressure, mContext.getString(R.string.blood_pressure, item.getDbp(), item.getSbp()));
        helper.setText(R.id.tvWatchRecordHeartRate, mContext.getString(R.string.heart_rate, item.getHr()));
        helper.setText(R.id.tvWatchRecordBloodOxygen, mContext.getString(R.string.blood_oxygen, item.getBo()));
        helper.setText(R.id.tvWatchRecordTime, item.getCreated_at().substring(0, item.getCreated_at().length() - 3));
    }

}
