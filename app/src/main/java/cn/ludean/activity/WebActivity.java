package cn.ludean.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ludean.AndroidtoJs;
import cn.ludean.PermissionManager;
import cn.ludean.R;
import cn.ludean.SharedPrefrenceUtils;

import static com.zyao89.view.zloading.Z_TYPE.ELASTIC_BALL;

public class WebActivity extends AppCompatActivity {

    @BindView(R.id.web_view)
    WebView webView;

    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
    private Uri imageUri;
    private String webUrl;
    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.
                fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.white)
                .flymeOSStatusBarFontColor(R.color.black)
                .init();
        ButterKnife.bind(this);
        boolean isLocation = PermissionManager.Query(this, PermissionManager.LOCATION);
        if (!isLocation) {
            PermissionManager.Granted(this, PermissionManager.LOCATION, 3);
        }
         webUrl = getIntent().getStringExtra("webUrl");
        if (webUrl == null || webUrl.isEmpty()) {
            if (SharedPrefrenceUtils.getString(this, "uuid") != null && !SharedPrefrenceUtils.getString(this, "uuid").isEmpty()) {
                startActivity(new Intent(WebActivity.this, HomeActivity.class));
                finish();
                return;
            }
            webUrl = "http://117.50.43.6/html/index.html";
        }
        initWebView(webUrl);
    }

    private void initWebView(String webUrl) {

        Log.e("webUrl", webUrl);
//        titleContent.setText(title);
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setDomStorageEnabled(true);
        //其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setAllowFileAccess(true);//资源加载超时操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.addJavascriptInterface(new AndroidtoJs(this), "Android");//AndroidtoJS类对象映射到js的
        webView.loadUrl(webUrl);
        CookieManager cookieManager = CookieManager.getInstance();
        String cookieStr = cookieManager.getCookie(webUrl);
        if (cookieStr != null) {
            Log.e("WebView", cookieStr);
        } else {
            Log.e("WebView", "cookieStr为空");
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            @Override
            public boolean onJsAlert(WebView arg0, String url, String message, JsResult result) {
                /**
                 * 这里写你自己的处理方式
                 */
                result.confirm();

                Log.e("WebView", "url:" + url + "message:" + message);
//                showToast(MyWebActivity.this, message);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                return true;
                // return super.onJsAlert(null, arg1, arg2, arg3);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                take();
                return true;
            }

            //<3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                take();
            }

            //>3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                take();
            }

            //>4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                take();
            }
        });
        final ZLoadingDialog dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(ELASTIC_BALL)//设置类型
                .setLoadingColor(R.color.colorPrimary)//颜色
                .setHintText("Loading...");
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                Log.e("WebView", url);
//                if (webUrl.contains("index.html")&&url.contains("mapHome.html")){
//                    startActivity(new Intent(WebActivity.this,HomeActivity.class));
//                    finish();
//                    return true;
//                }else
                if (url.contains("mapHome.html")||url.contains("myMessage.html")||url.contains("task.html")){
                    finish();
                    return true;
                }
                view.loadUrl(url);
                WebActivity.this.webUrl = url;
                return true;
            }

            //WebView开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.show();
            }

            //WebView加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!title.contains("html") && !title.contains("http") && !title.contains("https") && !title.isEmpty()) {
//                    titleContent.setText(title);
                }
                webView.setVisibility(View.VISIBLE);

                dialog.cancel();

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                // 这个方法在 android 6.0才出现
                int statusCode = errorResponse.getStatusCode();
                if (404 == statusCode || 500 == statusCode) {
//                    view.loadUrl("about:blank");// 避免出现默认的错误界面
//                    Toast.makeText(getApplicationContext(), "网络不佳", Toast.LENGTH_SHORT).show();
//                    activity.showToast(activity,"加载错误！");
//                    view.loadUrl(mErrorUrl);// 加载自定义错误页面
                }
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                if (webResourceRequest.isForMainFrame()) {//是否是为 main frame创建
//                    webView.loadUrl("about:blank");// 避免出现默认的错误界面
//                    Toast.makeText(getApplicationContext(), "网络不佳", Toast.LENGTH_SHORT).show();

//                    activity.showToast(activity,"加载错误！");
                }
            }
        });

    }

    private void take() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = this.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);
        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }

    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }
        return;
    }

    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (webView.canGoBack()) {
                if (webUrl == null) {
                    webView.goBack();
                    return true;
                }
                if (!webUrl.contains("mapHome.html") && !webUrl.contains("task.html") && !webUrl.contains("myMessage.html") && !webUrl.contains("index.html")) {
                    webView.goBack(); //调用goBack()返回WebView的上一页面
                    return true;
                } else {
                    //与上次点击返回键时刻作差
                    if ((System.currentTimeMillis() - mExitTime) > 2000) {
                        //大于2000ms则认为是误操作，使用Toast进行提示
                        Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                        //并记录下本次点击“返回键”的时刻，以便下次进行判断
                        mExitTime = System.currentTimeMillis();
                    } else {
                        Intent home = new Intent(Intent.ACTION_MAIN);
                        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        home.addCategory(Intent.CATEGORY_HOME);
                        startActivity(home);
                    }
                    return true;
                }
            } else {
                if (webUrl != null && webUrl.contains("index.html")) {
                    //与上次点击返回键时刻作差
                    if ((System.currentTimeMillis() - mExitTime) > 2000) {
                        //大于2000ms则认为是误操作，使用Toast进行提示
                        Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                        //并记录下本次点击“返回键”的时刻，以便下次进行判断
                        mExitTime = System.currentTimeMillis();
                    } else {
                        Intent home = new Intent(Intent.ACTION_MAIN);
                        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        home.addCategory(Intent.CATEGORY_HOME);
                        startActivity(home);
                    }
                } else {
                    finish();
                }
                return true;
            }
        } else {
            finish();
            return true;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading();
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }

    }
}
