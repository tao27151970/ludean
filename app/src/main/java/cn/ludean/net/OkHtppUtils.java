package cn.ludean.net;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author JWT
 */

public class OkHtppUtils {

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.SECONDS);
        //OkHttp进行添加拦截器loggingInterceptorgv
        httpClientBuilder.addInterceptor(OkHtppUtils.getLogInterceptor());
        return httpClientBuilder.build();
    }

    public static OkHttpClient getOkHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(new Cache(new File(context.getCacheDir(), "HttpCache"), 10 * 1024 * 1024));
        builder.connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.SECONDS);
        builder.addInterceptor(OkHtppUtils.getLogInterceptor());
        builder.addInterceptor(OkHtppUtils.cacheInterceptor(context));
        builder.addNetworkInterceptor(OkHtppUtils.cacheInterceptor(context));
        return builder.build();
    }

    /**
     * 网络请求log拦截器
     *
     * @return log拦截器对象
     */

    public static HttpLoggingInterceptor getLogInterceptor() {
        //设置log拦截器拦截内容
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.e("------retrofit-------", message));
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

    /**
     * 网络优先数据缓存拦截器
     *
     * @return 拦截器对象
     */
    public static Interceptor cacheInterceptor(final Context context) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //获取请求
                //没有网络的时候强制使用缓存
                if (!isNetworkAvailable(context)) {
                    request = request.newBuilder()
                            .header("User-Agent", "reading")
                            .header("Content-Type", "application/json")
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    Log.e("JWT", "没网读取缓存");
                }
                Response originalResponse = chain.proceed(request);
                if (isNetworkAvailable(context)) {
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public,max-age" + 0)
                            .header("Content-Type", "application/json")
                            .build();
                } else {
                    int maxTime = 4 * 24 * 60 * 30;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public,only-if-cached,max-state=" + maxTime)
                            .header("Content-Type", "application/json")
                            .build();
                }
            }
        };
        return interceptor;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            //ACCESS_NETWORK_STATE
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
