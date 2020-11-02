package com.pcg.yuquangong.adapters;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.model.network.entity.CureRecordItemEntity;

import java.util.List;

public class CureRecordAdapter extends BaseQuickAdapter<CureRecordItemEntity, BaseViewHolder> {

    public CureRecordAdapter(int layoutResId, @Nullable List<CureRecordItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CureRecordItemEntity item) {
        helper.addOnClickListener(R.id.btnRetryCure);
        helper.addOnClickListener(R.id.btnModifyCureRecord);
        helper.setText(R.id.tvItemCureName, item.getName());
        helper.setText(R.id.tvItemCureDate, item.getCreated_at());

        helper.setTypeface(R.id.tvItemCureName, App.getInstance().getRegularTypeface());
        helper.setTypeface(R.id.tvItemCureDate, App.getInstance().getRegularTypeface());
        helper.setTypeface(R.id.btnRetryCure, App.getInstance().getHomeTextTypeface());
        helper.setTypeface(R.id.btnModifyCureRecord, App.getInstance().getHomeTextTypeface());
    }

}
