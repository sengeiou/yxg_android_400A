package com.pcg.yuquangong.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.pcg.yuquangong.R;

public class SoundPoolUtil {

    private static SoundPoolUtil soundPoolUtil;
    private SoundPool soundPool;
    private int mSoundId;

    //单例模式
    public static SoundPoolUtil getInstance(Context context) {
        if (soundPoolUtil == null) {
            soundPoolUtil = new SoundPoolUtil(context);
        }
        return soundPoolUtil;
    }

    private SoundPoolUtil(Context context) {
        soundPool = new SoundPool(3, AudioManager.STREAM_SYSTEM, 0);
        //加载音频文件
        mSoundId = soundPool.load(context, R.raw.clicksound, 1);
    }

    public void play() {
        //播放音频
        soundPool.play(mSoundId, 1, 1, 0, 0, 1);
    }

}
