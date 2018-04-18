package lingya.jfdii;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Intent;
import lingya.jfdii.io.MeasureValue;

public class MainActivity extends AppCompatActivity {

    private WebView myWebview;
    private static final String TAG = "MainActivity";
    private KeybroadProxy keybroadProxy = new KeybroadProxy();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebview = (WebView)findViewById(R.id.mainWebView);
        initWebview();

        //启动服务
        Intent intent2 = new Intent(this, SendkeyService.class);
        startService(intent2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myWebview.loadUrl("http://www.baidu.com");

        //myWebview.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_sendkey:
                testSendKey();
                break;
            case R.id.menu_item_sendbroadcast:
                testSendBroadcast();
            break;
        }
        return true;
    }

    /**
     * 测试发送测量数据广播
     */
    private void testSendBroadcast(){
        MeasureValue value = new MeasureValue("test","-123.12","mm");
        Intent intent = new Intent("JFDII.ACTION_DATA_AVAILABLE");
        intent.putExtra("EXTRA_MEASURE_VALUE", value);
        sendBroadcast(intent);
    }

    /**
     * 测试 发送键盘数据
     */
    private void testSendKey(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                for (int i=0;i<10;i++) {
                    Thread.sleep(1000);
                    keybroadProxy.sendString( Integer.toString(i)+ "134.123");
                }
            }catch(InterruptedException ie){
               // Log.d(TAG,ie.getMessage());
            }
            }
        }).start();

        //keybroadProxy.sendString("1234.123");
    }


    private void initWebview(){
        //声明WebSettings子类
        WebSettings webSettings = myWebview.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);

        //缩放操作
        //支持缩放，默认为true。是下面那个的前提。
        webSettings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);

        //其他细节操作
        //关闭webview中缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");

        myWebview.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                view.loadUrl(url);
                return true;
            }
        });

    }
}
