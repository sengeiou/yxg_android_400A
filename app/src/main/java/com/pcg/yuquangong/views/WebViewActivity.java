package com.pcg.yuquangong.views;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pcg.yuquangong.App;
import com.pcg.yuquangong.R;
import com.pcg.yuquangong.base.BaseActivity;
import com.pcg.yuquangong.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wbview)
    WebView mWbview;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.layTvBack)
    TextView mLayTvBack;

    private static final String TAG = WebViewActivity.class.getSimpleName();
    private static final String WEB_URL = "web_url";
    private String mWebUrl;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, WebViewActivity.class);
        intent.putExtra(WEB_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        mWebUrl = getIntent().getStringExtra(WEB_URL);
        initWebView();

        LogUtil.e(TAG, "mWebUrl = " + mWebUrl);
        mWbview.loadUrl(mWebUrl);

        mLayTvBack.setTypeface(App.getInstance().getHomeTextTypeface());
    }

    @OnClick(R.id.layTvBack)
    void backClick() {
        finish();
    }

    private void initWebView() {
        WebSettings s = mWbview.getSettings();
        s.setBuiltInZoomControls(false);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setAppCacheEnabled(false);

        s.setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // enable Web Storage: localStorage, sessionStorage
        s.setDomStorageEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            s.setMediaPlaybackRequiresUserGesture(false);
        }

        mWbview.setWebViewClient(new MyWebViewClient());

        mWbview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView paramAnonymousWebView,
                                          int paramAnonymousInt) {
                mProgressBar.setProgress(paramAnonymousInt);
                super.onProgressChanged(paramAnonymousWebView,
                        paramAnonymousInt);
            }
        });
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }


//    private void initWebView() {
//        this.mProgressBar.setVisibility(View.VISIBLE);
////        this.mWbview.clearHistory();
//        this.mWbview.clearFormData();
//        String ua = mWbview.getSettings().getUserAgentString();
//        WebSettings webSettings = this.mWbview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webSettings.setSavePassword(false);
//        webSettings.setSaveFormData(false);
//        mWbview.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView paramAnonymousWebView,
//                                          int paramAnonymousInt) {
//                mProgressBar.setProgress(paramAnonymousInt);
//                super.onProgressChanged(paramAnonymousWebView,
//                        paramAnonymousInt);
//            }
//        });
//        this.mWbview.setWebViewClient(new SimpleWebViewClient());
//        this.mWbview.loadUrl(mWebUrl);
//    }

    private void backPressed() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mWbview.canGoBack()) {
            mWbview.goBack();
            return;
        }

        backPressed();
    }

//    class SimpleWebViewClient extends WebViewClient {
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            mProgressBar.setVisibility(View.GONE);
//            mWbview.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            super.onReceivedError(view, errorCode, description, failingUrl);
//            LogUtil.e(TAG, "onReceivedError: errorCode:" + errorCode + " description:" + description + " failingUrl:" + failingUrl);
//        }
//    }
}
