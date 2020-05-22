package cn.ludean;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import cn.jpush.android.api.JPushInterface;

public class App extends Application {
    private static Context mContext;
    public static String CACHE_PATH;// 缓存文件夹
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //极光初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static  Context getmContext() {
        return mContext;
    }



}
