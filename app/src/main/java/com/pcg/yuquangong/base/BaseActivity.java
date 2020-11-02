package com.pcg.yuquangong.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pcg.yuquangong.model.cache.CacheManager;
import com.pcg.yuquangong.utils.LogUtil;
import com.pcg.yuquangong.utils.SoundPoolUtil;

import io.reactivex.disposables.CompositeDisposable;

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected CompositeDisposable mDisposable = new CompositeDisposable();
    protected ProgressDialog mProgressDialog;

    private static final int DEFAULT_CLICK_ACTION_THRESHOLD = 200;
    private int mClickActionThreshold;
    private float startX;
    private float startY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        hideNavigationBar();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        }

        mContext = this;

        mClickActionThreshold = ViewConfiguration.get(mContext).getScaledTouchSlop();
        LogUtil.e("baseActivity", "mClickActionThreshold = " + mClickActionThreshold);

        if (mClickActionThreshold <= 0) {
            mClickActionThreshold = DEFAULT_CLICK_ACTION_THRESHOLD;
        }

        //给View添加全局的布局监听器
        getWindow().getDecorView().getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        LogUtil.e("OnGlobalLayoutListener");
                        hideNavigationBar();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBaseHandler.postDelayed(mHideNavigationRunnable, 500);
    }

    private Runnable mHideNavigationRunnable = new Runnable() {
        @Override
        public void run() {
            hideNavigationBar();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaseHandler.removeCallbacksAndMessages(null);
        mDisposable.clear();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                }
                break;
            case MotionEvent.ACTION_UP:
                float endX = ev.getX();
                float endY = ev.getY();
                if (isAClick(startX, endX, startY, endY) && CacheManager.getInstance().isSettingAudioTipsOpen()) {
                    SoundPoolUtil.getInstance(mContext).play();
                }
                break;
        }

//        if (ev.getAction() == MotionEvent.ACTION_DOWN){
//            // 判断连续点击事件时间差
//            if (Utils.isFastClick()){
//                return true;
//            }
//            SoundPoolUtil.getInstance(mContext).play();
//        }

        return super.dispatchTouchEvent(ev);
    }

    private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        return !(differenceX > mClickActionThreshold/* =5 */ || differenceY > mClickActionThreshold);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void hideNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private Handler mBaseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

}
