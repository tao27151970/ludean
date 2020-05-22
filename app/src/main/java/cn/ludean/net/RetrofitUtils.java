package cn.ludean.net;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cn.ludean.App;
import cn.ludean.SharedPrefrenceUtils;
import cn.ludean.activity.HomeActivity;
import cn.ludean.activity.WebActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author JWT
 */

public class RetrofitUtils {
    private static RetrofitUtils retorfitUtils;
    // 设置变量
    // 可重试次数
    private int maxConnectCount = 10;
    // 当前已重试次数
    private int currentRetryCount = 0;
    // 重试等待时间
    private int waitRetryTime = 0;

    private Retrofit retrofit;
    private Retrofit ugc_retrofit;
    private Retrofit shop_retrofit;

    private RetrofitUtils() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        retrofit = new Retrofit.Builder()
                .baseUrl(NetConstant.BaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(OkHtppUtils.getOkHttpClient())
                .build();

        ugc_retrofit = new Retrofit.Builder()
                .baseUrl(NetConstant.BaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHtppUtils.getOkHttpClient())
                .build();

        shop_retrofit = new Retrofit.Builder()
                .baseUrl(NetConstant.BaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHtppUtils.getOkHttpClient())
                .build();
    }


    public static RetrofitUtils getInstance() {
        if (retorfitUtils == null) {
            synchronized (RetrofitUtils.class) {
                if (retorfitUtils == null) {
                    retorfitUtils = new RetrofitUtils();
                }
            }
        }
        return retorfitUtils;
    }


    public <T> void postHttp(String url, String uuid, Map<String, String> map, final NetCallBack<T> netCallBack) {
        ApiService apiService = retrofit.create(ApiService.class);
        postHttp(apiService, url, uuid, map, netCallBack);
    }

    public <T> void saveClockInfo(String url, String uuid, Map<String, String> map, final NetCallBack<T> netCallBack) {
        ApiService apiService = retrofit.create(ApiService.class);
        saveClockInfo(apiService, url, uuid, map, netCallBack);
    }

    @SuppressLint("CheckResult")
    public <T> void saveClockInfo(ApiService apiService, final String url, String uuid, Map<String, String> map, final NetCallBack netCallBack) {
        Observable<ResponseBody> observable = apiService
                .saveClockInfo(url, uuid,  map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable
//                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
//                        // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
//                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
//                            @Override
//                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
//                                // 输出异常信息
//                                Log.d("Error", "发生异常 = " + throwable.toString());
//                                /**
//                                 * 需求1：根据异常类型选择是否重试
//                                 * 即，当发生的异常 = 网络异常 = IO异常 才选择重试
//                                 */
//                                if (throwable instanceof IOException) {
//                                    Log.d("Error", "属于IO异常，需重试");
//                                    /**
//                                     * 需求2：限制重试次数
//                                     * 即，当已重试次数 < 设置的重试次数，才选择重试
//                                     */
//                                    AppManageHelper helper = new AppManageHelper();
//                                    Log.e("Error", BaseActivity.getActivity().toString());
//                                    Log.e("Error", helper.getTopActivity().toString());
//                                    if (currentRetryCount < maxConnectCount && BaseActivity.getActivity() == helper.getTopActivity()) {
//                                        // 记录重试次数
//                                        currentRetryCount++;
//                                        Log.d("Error", "重试次数 = " + currentRetryCount);
//                                        /**
//                                         * 需求2：实现重试
//                                         * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
//                                         *
//                                         * 需求3：延迟1段时间再重试
//                                         * 采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
//                                         *
//                                         * 需求4：遇到的异常越多，时间越长
//                                         * 在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
//                                         */
//                                        // 设置等待时间
//                                        waitRetryTime = 1000 + currentRetryCount * 1000;
//                                        Log.d("Error", "等待时间 =" + waitRetryTime);
//                                        return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);
//                                    } else {
//                                        // 若重试次数已 > 设置重试次数，则不重试
//                                        // 通过发送error来停止重试（可在观察者的onError（）中获取信息）
//                                        return Observable.error(new Throwable("重试次数已超过设置次数 = " + currentRetryCount + "，即 不再重试"));
//                                    }
//                                }
//
//                                // 若发生的异常不属于I/O异常，则不重试
//                                // 通过返回的Observable发送的事件 = Error事件 实现（可在观察者的onError（）中获取信息）
//                                else {
//                                    Log.e("taotaotao", throwable.getMessage());
//                                    return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
//                                }
//                            }
//                        });
//                    }
//                })
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        netCallBack.star(d);
                    }

                    @Override
                    public void onNext(ResponseBody requestBody) {
                        Gson gson = new Gson();
                        Type[] typeInterface = netCallBack.getClass().getGenericInterfaces();
                        Type[] types = ((ParameterizedType) typeInterface[0]).getActualTypeArguments();
                        try {
                            String string = requestBody.string();
//                            Log.e("123456789", string);
//                            if (string.contains("\"error\":500000")) {
//                                Toast.makeText(App.getContext(), "操作频繁,稍后再试", Toast.LENGTH_SHORT).show();
//                            }
                            if (string.contains("\"error\":100006") || string.contains("\"error\":100009")) {
//                                Toast.makeText(App.getContext(), "登录已经过期,请重新登录", Toast.LENGTH_SHORT).show();
//                                String token = SharedPrefrenceUtils.getString(App.getContext(), "token", "token");
//                                if ("token".equals(token)) {
//                                    AppManageHelper.finishAllActivity();
////                                    BaseActivity.getActivity().finish();
//                                    BaseActivity.getActivity().startActivity(new Intent(BaseActivity.getActivity(), ProxyLoginActivity.class));
//                                } else {
//                                if (!banner_Url.equals(url)){
//                                    BaseActivity.getActivity().finish();
//                                    Intent intent = new Intent(BaseActivity.getActivity(), LoginActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    BaseActivity.getActivity().startActivity(intent);
//                                }
//                                }
                            } else {
                                if (getJSONType(string)) {
                                    T retu_t = gson.fromJson(string, types[0]);
                                    netCallBack.onSuccess(retu_t);
//                                    dialog.dismiss();
                                } else {
                                    Log.e("Retrofit_Json", "数据异常不属于Json");
                                }
                            }
                        } catch (SocketException e) {
                            Log.e("taotaotao", e.getMessage().toString());
                            try {
                                Log.e("JsonSyntaxException", requestBody.string());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JsonSyntaxException e) {
                            Log.e("taotaotao", e.getMessage().toString());
                            try {
                                Log.e("JsonSyntaxException", requestBody.string());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        netCallBack.onError(e);
//                        dialog.dismiss();
//                        Toast.makeText(BaseActivity.getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                        Log.e("RetrofitUtils", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    public <T> void postHttp(ApiService apiService, final String url, String uuid, Map<String, String> map, final NetCallBack netCallBack) {
        Observable<ResponseBody> observable = apiService
                .send_Post(url, uuid, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        netCallBack.star(d);
                    }

                    @Override
                    public void onNext(ResponseBody requestBody) {
                        Gson gson = new Gson();
                        Type[] typeInterface = netCallBack.getClass().getGenericInterfaces();
                        Type[] types = ((ParameterizedType) typeInterface[0]).getActualTypeArguments();
                        try {
                            String string = requestBody.string();
                            if (string.contains("请先登录！")) {
                                Toast.makeText(App.getmContext(), "登录已经过期,请重新登录", Toast.LENGTH_SHORT).show();
                                SharedPrefrenceUtils.saveString(App.getmContext(), "uuid", "");
                                Intent intent = new Intent(App.getmContext(), WebActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                if (HomeActivity.getHomeActivity()!=null){
                                    HomeActivity.getHomeActivity().startActivity(intent);
                                }
                            } else{
                                    if (getJSONType(string)) {
                                        T retu_t = gson.fromJson(string, types[0]);
                                        netCallBack.onSuccess(retu_t);
//                                    dialog.dismiss();
                                    } else {
                                        Log.e("Retrofit_Json", "数据异常不属于Json");
                                    }
                                }
                            } catch(SocketException e){
                                Log.e("taotaotao", e.getMessage().toString());
                                try {
                                    Log.e("JsonSyntaxException", requestBody.string());
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            } catch(IOException e){
                                e.printStackTrace();
                            } catch(JsonSyntaxException e){
                                Log.e("taotaotao", e.getMessage().toString());
                                try {
                                    Log.e("JsonSyntaxException", requestBody.string());
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onError (Throwable e){
                            netCallBack.onError(e);
//                        dialog.dismiss();
//                        Toast.makeText(BaseActivity.getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                            Log.e("RetrofitUtils", e.toString());
                        }

                        @Override
                        public void onComplete () {

                        }
                    });
                }

        public static boolean getJSONType (String str){
            boolean result = false;
            if (!str.isEmpty()) {
                str = str.trim();
                if (str.startsWith("{") && str.endsWith("}")) {
                    result = true;
                } else if (str.startsWith("[") && str.endsWith("]")) {
                    result = true;
                }
            } else {
                Log.e("RetrofitUtils", "返回数据为空");
            }
            return result;
        }


        public <T > void getMarker (String url,final NetCallBack netCallBack, String uuid){
            retrofit.create(ApiService.class)
                    .getMarker(url, uuid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            netCallBack.star(d);
                        }

                        @Override
                        public void onNext(ResponseBody requestBody) {
                            Gson gson = new Gson();
                            Type[] typeInterface = netCallBack.getClass().getGenericInterfaces();
                            Type[] types = ((ParameterizedType) typeInterface[0]).getActualTypeArguments();
                            try {
                                String string = requestBody.string();
                                if (string.contains("请先登录！")) {
                                    Toast.makeText(App.getmContext(), "登录已经过期,请重新登录", Toast.LENGTH_SHORT).show();
                                    SharedPrefrenceUtils.saveString(App.getmContext(), "uuid", "");
                                    Intent intent = new Intent(App.getmContext(), WebActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                   if (HomeActivity.getHomeActivity()!=null){
                                       HomeActivity.getHomeActivity().startActivity(intent);
                                   }
                                } else{
                                    if (getJSONType(string)) {
                                        T retu_t = gson.fromJson(string, types[0]);
                                        netCallBack.onSuccess(retu_t);
//                                    dialog.dismiss();
                                    } else {
                                        Log.e("Retrofit_Json", "数据异常不属于Json");
                                    }
                                }
                            } catch(SocketException e){
                                Log.e("taotaotao", e.getMessage().toString());
                                try {
                                    Log.e("JsonSyntaxException", requestBody.string());
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            } catch(IOException e){
                                e.printStackTrace();
                            } catch(JsonSyntaxException e){
                                Log.e("taotaotao", e.getMessage().toString());
                                try {
                                    Log.e("JsonSyntaxException", requestBody.string());
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            netCallBack.onError(e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

    }
