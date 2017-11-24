package sign.qk365.com.noneone;

import android.content.Context;
import android.graphics.PixelFormat;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;

/**
 * 功能:集成腾讯x5插件  播放视频
 */

public class X5WebActivity extends AppCompatActivity {
    private Context mContext;
    private WebView x5Web;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        mContext = this;
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        x5Web = findViewById(R.id.webview);
        WebSettings webSetings = x5Web.getSettings();
        webSetings.setJavaScriptEnabled(true);
        webSetings.setSaveFormData(true);
        webSetings.setSavePassword(true);
        webSetings.setSupportZoom(true);
        webSetings.setUseWideViewPort(true); // 关键点
        webSetings.setAllowFileAccess(true); // 允许访问文件
        webSetings.setLoadWithOverviewMode(true);
        webSetings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        webSetings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetings.setBuiltInZoomControls(true);
        webSetings.setDefaultTextEncodingName("UTF-8");
        webSetings.setDomStorageEnabled(true);
        x5Web.requestFocus(View.FOCUS_DOWN);
        x5Web.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(android.webkit.WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                handler.proceed();//接受证书
                //handleMessage(Message msg); 其他处理
            }

            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String urlStr) {
                return true;
            }
        });
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
                int size = outView.size();
                if (outView != null && outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        x5Web.loadUrl("https://www.noneone.cn");
        x5Web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && x5Web.canGoBack()) {
            x5Web.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
