package com.pcg.yuquangong.adapters;

import android.content.Context;
import android.nfc.Tag;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pcg.yuquangong.GlideApp;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.model.network.entity.MainBannerEntity;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.utils.Utils;
import com.pcg.yuquangong.views.interfaces.BannerItemClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gupengcheng on 2018/12/30.
 */
public class BannerPageAdapter extends PagerAdapter {

    private static final String TAG = BannerPageAdapter.class.getSimpleName();

    private LayoutInflater mLayoutInflater;
    private List<MainBannerEntity> mData = new ArrayList<>();
    private Context mContext;
    private BannerItemClick mListener;

    public BannerPageAdapter(Context context, BannerItemClick listener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    public void initItemList(List<MainBannerEntity> mediaList) {
        List<MainBannerEntity> newMediaList = new ArrayList<>();
        newMediaList.addAll(mediaList);
        if (newMediaList.size() > 1) {
            newMediaList.add(0, mediaList.get(mediaList.size() - 1));
            newMediaList.add(mediaList.get(0));
        }
        mediaList.clear();
        mediaList.addAll(newMediaList);
        mData.clear();
        mData.addAll(newMediaList);
    }

    public MainBannerEntity getItem(int position) {
        int realPosition = getPosition(position);
        return mData.get(realPosition);
    }

    public int getPosition(int position) {
        if (!mData.isEmpty()) {
            if (position < 0) {
                position = mData.size() + position;
            }
            return position % mData.size();
        }
        return 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        MainBannerEntity itemData = getItem(position);
        final View rootView = mLayoutInflater.inflate(R.layout.item_main_banner, container, false);
        ImageView ivBannerItem = rootView.findViewById(R.id.ivBannerItem);
        if (!TextUtils.isEmpty(itemData.getImg())) {
            GlideApp.with(mContext)
                    .load(itemData.getImg())
                    .placeholder(R.mipmap.ic_logo)
//                    .override(640, 360)
                    .centerCrop()
                    .into(ivBannerItem);
//            GlideApp.with(mContext).load(Utils.replaceBlank(itemData.getImg())).into(ivBannerItem);
        }
        container.addView(rootView, 0);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(rootView, position);
            }
        });

        return rootView;
    }

    public void setData(List<MainBannerEntity> data) {
        initItemList(data);
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
