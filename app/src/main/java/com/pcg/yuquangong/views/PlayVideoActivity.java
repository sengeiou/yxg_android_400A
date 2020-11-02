package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pcg.yuquangong.R;
import com.pcg.yuquangong.views.fragments.VideoPlayFragment;

import butterknife.ButterKnife;

public class PlayVideoActivity extends FragmentActivity {

    public static void startActivity(Context context, String videoUrl) {
        Intent intent = new Intent();
        intent.setClass(context, PlayVideoActivity.class);
        intent.putExtra("video_url", videoUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("video_url");

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerView, VideoPlayFragment.newInstance(url))
                    .commit();
        }
    }

}
