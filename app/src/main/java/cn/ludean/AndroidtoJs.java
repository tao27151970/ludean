package cn.ludean;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.ludean.activity.HomeActivity;
import cn.ludean.activity.TaskActivity;
import cn.ludean.activity.WebActivity;
import cn.ludean.beans.MarkerBeans;
import cn.ludean.net.NetCallBack;
import cn.ludean.net.RetrofitUtils;
import io.reactivex.disposables.Disposable;

// 继承自Object类
public class AndroidtoJs extends Object {
    private Activity activity;

    public AndroidtoJs(Activity activity) {
        this.activity = activity;
    }

    public AndroidtoJs() {

    }

    @JavascriptInterface
    public void startHomeActivity(String uuid,String userid) {
        if (activity != null) {
            Intent intent = new Intent(activity, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            SharedPrefrenceUtils.saveString(activity, "uuid", uuid);
            SharedPrefrenceUtils.saveString(activity, "userId", userid);
            Log.e("uuid", uuid);
            Log.e("userId", userid);
            activity.startActivity(intent);
            activity.finish();
        }
    }
    @JavascriptInterface
    public void startTaskActivity() {
        if (activity != null) {
            activity.startActivity(new Intent(activity, TaskActivity.class));
        }
    }


    @JavascriptInterface
    public void startWebActivity() {
        if (activity != null) {
            Intent intent = new Intent(activity, WebActivity.class);
            activity.startActivity(intent);
        }
    }

    @JavascriptInterface
    public void finishActivity() {
        if (activity != null) {
            activity.finish();
        }
    }


}
