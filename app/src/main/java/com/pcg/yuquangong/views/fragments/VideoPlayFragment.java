package com.pcg.yuquangong.views.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseFragment;
import com.pcg.yuquangong.utils.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class VideoPlayFragment extends BaseFragment implements CacheListener {

    @BindView(R.id.videoView)
    VideoView mVideoView;
    @BindView(R.id.cacheStatusImageView)
    ImageView mCacheStatusImageView;
    @BindView(R.id.progressBar)
    SeekBar mProgressBar;
    @BindView(R.id.layTvBack)
    TextView mLayTvBack;
    @BindView(R.id.btnPauseStart)
    TextView mBtnPauseStart;
    @BindView(R.id.btnCurrentTime)
    TextView mBtnCurrentTime;
    @BindView(R.id.btnTotalTime)
    TextView mBtnTotalTime;
    Unbinder unbinder;

    private static final String TAG = "VideoFragment";

    private String url;

    private final VideoProgressUpdater updater = new VideoProgressUpdater();

    public static VideoPlayFragment newInstance(String url) {
        VideoPlayFragment fragment = new VideoPlayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("video_url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_play, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        afterViewInjected();
        return view;
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        getActivity().finish();
    }

    @OnClick(R.id.btnPauseStart)
    void btnPauseStartClick() {
        play();
    }

    protected String time(long millionSeconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millionSeconds);
        return simpleDateFormat.format(c.getTime());
    }

    private void initViews() {
        url = getArguments().getString("video_url");
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mVideoView.stopPlayback(); //播放异常，则停止播放，防止弹窗使界面阻塞
                return true;
            }
        });

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mBtnTotalTime.setText(time(mVideoView.getDuration()));
                mBtnPauseStart.setVisibility(View.VISIBLE);
                mBtnPauseStart.setEnabled(true);
            }
        });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //播放结束后的动作
                updater.stop();
                mBtnPauseStart.setVisibility(View.VISIBLE);
                mBtnPauseStart.setEnabled(true);
                mBtnPauseStart.setText(R.string.play);
                mBtnCurrentTime.setText("00:00");
                mProgressBar.setProgress(0);
            }
        });

        mProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LogUtil.e(TAG, "onStopTrackingTouch progress = " + mProgressBar.getProgress());
                int videoPosition = mVideoView.getDuration() * mProgressBar.getProgress() / 100;
                mVideoView.seekTo(videoPosition);
            }
        });
    }

    private void afterViewInjected() {
        checkCachedState();
        startVideo();
    }

    private void checkCachedState() {
        HttpProxyCacheServer proxy = App.getProxy(getActivity());
        boolean fullyCached = proxy.isCached(url);
        setCachedState(fullyCached);
        if (fullyCached) {
            mProgressBar.setSecondaryProgress(100);
        }
    }

    private void startVideo() {
        HttpProxyCacheServer proxy = App.getProxy(getActivity());
        proxy.registerCacheListener(this, url);
        String proxyUrl = proxy.getProxyUrl(url);
        Log.d(TAG, "Use proxy url " + proxyUrl + " instead of original url " + url);
        mVideoView.setVideoPath(proxyUrl);
        mVideoView.start();
    }

    protected void play() {
        if (mBtnPauseStart.getText().equals(getString(R.string.play))) {
            mBtnPauseStart.setText(getString(R.string.pause));
            updater.start();
            mVideoView.start();
        } else {
            mBtnPauseStart.setText(getString(R.string.play));
            if (mVideoView.isPlaying()) {
                updater.stop();
                mVideoView.pause();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updater.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        updater.stop();
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
        App.getProxy(getActivity()).unregisterCacheListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCacheAvailable(File file, String url, int percentsAvailable) {
        mProgressBar.setSecondaryProgress(percentsAvailable);
        setCachedState(percentsAvailable == 100);
        LogUtil.e(TAG, String.format("onCacheAvailable. percents: %d, file: %s, url: %s", percentsAvailable, file, url));
    }

    private void updateVideoProgress() {
        int videoProgress = mVideoView.getCurrentPosition() * 100 / mVideoView.getDuration();
        mProgressBar.setProgress(videoProgress);
        mBtnCurrentTime.setText(time(mVideoView.getCurrentPosition()));
    }

//    @SeekBarTouchStop(R.id.progressBar)
//    void seekVideo() {
//
//    }

    private void setCachedState(boolean cached) {
//        int statusIconId = cached ? R.mipmap.ic_cloud_done : R.mipmap.ic_cloud_download;
//        mCacheStatusImageView.setImageResource(statusIconId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private final class VideoProgressUpdater extends Handler {

        public void start() {
            sendEmptyMessage(0);
        }

        public void stop() {
            removeMessages(0);
        }

        @Override
        public void handleMessage(Message msg) {
            updateVideoProgress();
            sendEmptyMessageDelayed(0, 500);
        }
    }
}
