package com.pcg.yuquangong.adapters;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.model.network.entity.MemberListItemEntity;

import java.util.List;

public class ChooseCurePersonAdapter extends BaseQuickAdapter<MemberListItemEntity, BaseViewHolder> {

    public ChooseCurePersonAdapter(int layoutResId, @Nullable List<MemberListItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberListItemEntity item) {
        helper.setText(R.id.tvItemChooseCurePersonName, item.getName());
        helper.setText(R.id.tvItemChooseCurePersonNumber, mContext.getString(R.string.item_member_num, String.valueOf(item.getMember_no())));
        if (item.isChecked()) {
            helper.setGone(R.id.btnPersonChecked, true);
            helper.setBackgroundRes(R.id.layChooseCurePerson, R.mipmap.ic_item_bg_checked);
            helper.setTextColor(R.id.tvItemChooseCurePersonName, mContext.getResources().getColor(R.color.white));
            helper.setTextColor(R.id.tvItemChooseCurePersonNumber, mContext.getResources().getColor(R.color.white));
        } else {
            helper.setGone(R.id.btnPersonChecked, false);
            helper.setBackgroundRes(R.id.layChooseCurePerson, R.mipmap.ic_item_member_list_bg);
            helper.setTextColor(R.id.tvItemChooseCurePersonName, mContext.getResources().getColor(R.color.item_member_text_color));
            helper.setTextColor(R.id.tvItemChooseCurePersonNumber, mContext.getResources().getColor(R.color.item_member_text_color));
        }
        helper.setTypeface(R.id.tvItemChooseCurePersonName, App.getInstance().getRegularTypeface());
        helper.setTypeface(R.id.tvItemChooseCurePersonNumber, App.getInstance().getRegularTypeface());
    }

}