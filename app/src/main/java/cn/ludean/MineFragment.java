package cn.ludean;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.zyao89.view.zloading.ZLoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ludean.activity.HomeActivity;
import cn.ludean.activity.WebActivity;

import static com.zyao89.view.zloading.Z_TYPE.ELASTIC_BALL;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    @BindView(R.id.web_view)
    WebView webView;
    private Unbinder unbinder;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_web, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        initWebView("http://117.50.43.6/html/myMessage.html");
        return inflate;
    }

    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
    private Uri imageUri;

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
//        webView.addJavascriptInterface(new AndroidtoJs(this), "pay");//AndroidtoJS类对象映射到js的pay对象
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
                Toast.makeText(Objects.requireNonNull(getActivity()), message, Toast.LENGTH_SHORT).show();
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
        final ZLoadingDialog dialog = new ZLoadingDialog(Objects.requireNonNull(getActivity()));
        dialog.setLoadingBuilder(ELASTIC_BALL)//设置类型
                .setLoadingColor(R.color.colorPrimary)//颜色
                .setHintText("Loading...");
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                if (url!=null&&url.contains("index.html")){
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    SharedPrefrenceUtils.saveString(getActivity(), "uuid", "");
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
                if (url!=null&&!url.contains("myMessage.html")){
                   Intent intent = new Intent(getActivity(), WebActivity.class);
                   intent.putExtra("webUrl",url);
                   startActivity(intent);
                   return true;
               }
                view.loadUrl(url);
                Log.e("WebView", url);
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
        final PackageManager packageManager = getActivity().getPackageManager();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
