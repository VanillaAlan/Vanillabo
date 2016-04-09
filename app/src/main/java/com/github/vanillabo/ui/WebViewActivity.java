package com.github.vanillabo.ui;

import com.github.vanillabo.Config;
import com.github.vanillabo.R;
import com.github.vanillabo.api.AuthorizeCodeListener;
import com.github.vanillabo.api.WeiboAuthService;
import com.github.vanillabo.model.AccessToken;
import com.github.vanillabo.ui.widget.ContentLoadingProgressBar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebViewActivity extends BaseActivity implements AuthorizeCodeListener {

    @Bind(R.id.webview)
    WebView mWebView;

    @Bind(R.id.progressbar)
    ContentLoadingProgressBar mProgressBar;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new MyWebChromeClient(mProgressBar));
        mWebView.setWebViewClient(new MyWebViewClient(this));
        mWebView.loadUrl(Config.AUTHORIZE_URL);
    }

    private static class MyWebChromeClient extends WebChromeClient {

        private ContentLoadingProgressBar progressBar;

        MyWebChromeClient(ContentLoadingProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            }
        }

    }

    private static class MyWebViewClient extends WebViewClient {

        AuthorizeCodeListener authorizeCodeListener;

        MyWebViewClient(AuthorizeCodeListener listener) {
            authorizeCodeListener = listener;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(Config.REDIRECT_URI)) {
                int index = url.indexOf("?");
                String[] codePath = url.substring(++index).split("=");
                String code = codePath[1];
                authorizeCodeListener.authorizeCode(code);
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

    }

    @Override
    public void authorizeCode(String code) {
        final WeiboAuthService weiboAuthApi = new Retrofit.Builder()
                .baseUrl(Config.WEIBO_API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeiboAuthService.class);
        final Call<AccessToken> accessTokenCall = weiboAuthApi.getAccessToken(
                Config.APP_KEY,
                Config.APP_SECRET,
                "authorization_code",
                Config.REDIRECT_URI,
                code);
        accessTokenCall.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });
    }
}
