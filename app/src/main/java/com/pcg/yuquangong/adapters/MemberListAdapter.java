package com.pcg.yuquangong.adapters;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.model.network.entity.MemberListItemEntity;

import java.util.List;

public class MemberListAdapter extends BaseQuickAdapter<MemberListItemEntity, BaseViewHolder> {

    public MemberListAdapter(int layoutResId, @Nullable List<MemberListItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberListItemEntity item) {
        helper.addOnClickListener(R.id.btnGoCure);
        helper.setText(R.id.tvItemMemberName, item.getName());
        helper.setText(R.id.tvItemMemberNumber, mContext.getString(R.string.item_member_num, String.valueOf(item.getMember_no())));

        helper.setTypeface(R.id.btnGoCure, App.getInstance().getHomeTextTypeface());
        helper.setTypeface(R.id.tvItemMemberName, App.getInstance().getRegularTypeface());
        helper.setTypeface(R.id.tvItemMemberNumber, App.getInstance().getRegularTypeface());
    }

}
