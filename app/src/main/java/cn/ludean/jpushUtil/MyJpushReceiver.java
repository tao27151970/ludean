package cn.ludean.jpushUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.logging.LogManager;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import cn.ludean.App;
import cn.ludean.SharedPrefrenceUtils;
import cn.ludean.activity.HomeActivity;
import cn.ludean.activity.MessageActivity;
import cn.ludean.activity.WebActivity;

/**
 * Created by jiash on 2019/9/25.
 */

public class MyJpushReceiver extends JPushMessageReceiver {
    private static final String TAG = "JPushReceiver";

    /**
     * 收到自定义消息回调
     *
     * @param context
     * @param customMessage
     */
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);

        Log.i(TAG, "onMessage customMessage*****" + customMessage.toString());
    }

    /**
     * 收到通知回调
     *
     * @param context
     * @param notificationMessage
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);

        Log.i(TAG, "onNotifyMessageArrived notificationMessage*****" + notificationMessage.toString());
    }

    /**
     * 点击通知回调
     *
     * @param context
     * @param notificationMessage
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        Log.i(TAG, "onNotifyMessageOpened notificationMessage*****" + notificationMessage.toString());
        App mineApplication = (App) context.getApplicationContext();
        Intent intent;
        if (SharedPrefrenceUtils.getString(mineApplication,"uuid").isEmpty()) {
            intent = new Intent(context, WebActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putString("fromToWhichFragment", "FirstPageFragment");
            intent.putExtras(bundle);
        } else {
            SharedPrefrenceUtils.saveBoolean(context,"message_new",false);
            intent = new Intent(context, MessageActivity.class);
        }
        context.startActivity(intent);
    }

}
