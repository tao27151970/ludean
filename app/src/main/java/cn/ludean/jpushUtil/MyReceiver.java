package cn.ludean.jpushUtil;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.helper.Logger;
import cn.ludean.HomeFragment;
import cn.ludean.R;
import cn.ludean.SharedPrefrenceUtils;
import cn.ludean.activity.MessageActivity;
import cn.ludean.activity.WebActivity;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MineJpushReceiver";
    private static final int NOTIFICATION_ID = 106;   //推送ID
    private static final int NOTIFICATION_SHOW_SHOW_AT_MOST = 5;   //推送通知最多显示条数

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive");
        try {
            Bundle bundle = intent.getExtras();
            Log.i(TAG, "onReceive*******" + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                HomeFragment.instance.setMessageNew();
                SharedPrefrenceUtils.saveBoolean(context,"message_new",true);
                Log.i(TAG, "ACTION_MESSAGE_RECEIVED 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.i(TAG, "ACTION_MESSAGE_RECEIVED的ID: " + notifactionId);
                Log.i(TAG, "ACTION_MESSAGE_RECEIVED processCustomMessage");
                //在这里自定义通知声音
                processCustomMessage(context.getApplicationContext(), bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                HomeFragment.instance.setMessageNew();
                SharedPrefrenceUtils.saveBoolean(context,"message_new",true);
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Log.i(TAG, "ACTION_NOTIFICATION_RECEIVED 标题:【" + title + "】，内容：【" + content + "】，附加参数:【" + extra + "】");
                Log.i(TAG, "ACTION_NOTIFICATION_RECEIVED 接收到推送下来的通知");
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.i(TAG, "[MyReceiver] 用户点击打开了通知");
                //打开自定义的Activity
                if (SharedPrefrenceUtils.getString(context, "uuid").isEmpty()) {
                    Intent i = new Intent(context, WebActivity.class);
                    i.putExtras(bundle);
                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                } else {
                    SharedPrefrenceUtils.saveBoolean(context,"message_new",false);
                    Intent i = new Intent(context, MessageActivity.class);
                    i.putExtras(bundle);
                    i.putExtra("functionType", bundle.getString("functionType"));
                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    /**
     * 实现自定义推送声音
     *
     * @param context
     * @param bundle
     */
    private void processCustomMessage(Context context, Bundle bundle) {
        WeakReference<Context> modelContext = new WeakReference<Context>(context);
        Context contextSub = modelContext.get();
        if (contextSub != null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(contextSub);

            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Bitmap bitmap = BitmapFactory.decodeResource(contextSub.getResources(), R.drawable.app_logo);
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.i(TAG, "ACTION_MESSAGE_RECEIVED的ID: " + notifactionId);
            Log.i(TAG, "extras*****" + extras);
            Intent mIntent;
            if (SharedPrefrenceUtils.getString(context, "uuid").isEmpty()) {
                mIntent = new Intent(contextSub, WebActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else {
                SharedPrefrenceUtils.saveBoolean(context,"message_new",false);

                mIntent = new Intent(contextSub, MessageActivity.class);
                mIntent.putExtra("functionType", bundle.getString("functionType"));

            }
            Bundle mBundle = new Bundle();
            mBundle.putString("fromToWhichFragment", "FirstPageFragment");
            mIntent.putExtras(mBundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(contextSub, 0, mIntent, 0);
            builder.setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setContentText(msg)
                    .setContentTitle(title.equals("") ? "公告" : title)
                    .setSmallIcon(R.drawable.app_logo)
                    .setLargeIcon(bitmap)
                    .setNumber(NOTIFICATION_SHOW_SHOW_AT_MOST);

            String CHANNEL_ID = "MineJpushReceiverId";
            String CHANNEL_NAME = "MineJpushReceiverName";
            NotificationManager manager =
                    (NotificationManager) contextSub.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            deleteNoNumberNotification(manager, CHANNEL_ID);

                //修改安卓8.1以上系统报错
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
                notificationChannel.setShowBadge(false);//是否显示角标
                notificationChannel.enableVibration(true);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
//                notificationChannel.setSound(
//                        Uri.parse("android.resource://" + contextSub.getPackageName() + "/" + R.raw.sound_1),
//                        Notification.AUDIO_ATTRIBUTES_DEFAULT);

                if (manager != null) {
                    manager.createNotificationChannel(notificationChannel);
                }
                builder.setChannelId(CHANNEL_ID);
            } else {
//                builder.setSound(Uri.parse("android.resource://" + contextSub.getPackageName() + "/" + R.raw.sound_1));
            }
            //id随意，正好使用定义的常量做id，0除外，0为默认的Notification
            manager.notify(NOTIFICATION_ID, builder.build());

//            Log.i(TAG, "extras bodyParams.get(key)*****");
//            if (extras != null && !"".equals(extras)) {
//                Map<String, String> bodyParams = MapManager.jsonStrToMap(extras);
//                if (bodyParams != null && bodyParams.size() > 0) {
//                    for (String key : bodyParams.keySet()) {
//                        if (bodyParams.get(key) != null) {//如果参数不是null，才把参数传给后台
//                            if ("YouWonThePrizes".equals(bodyParams.get(key))) {
//                                Log.i(TAG, "bodyParams.get(key)*****" + bodyParams.get(key));
//                                Intent intent = new Intent(contextSub, HomeActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                contextSub.startActivity(intent);
//                            }
//                        }
//                    }
//                }
//            }
        }

//        if (!TextUtils.isEmpty(extras)) {
//            try {
//                JSONObject extraJson = new JSONObject(extras);
//                if (null != extraJson && extraJson.length() > 0) {
//                    String sound = extraJson.getString("sound");
//                    LogManager.i(TAG, "processCustomMessage sound***" + sound);
//                    if ("sound".equals(sound)) {
//                        notification.setSound(Uri.parse("android.resource://" + contextSub.getPackageName() + "/" + R.raw.sound_1));
//                    } else {
////                        notification.setSound(Uri.parse("android.resource://" + contextSub.getPackageName() + "/" + R.raw.sound_1));
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
    }


    private void deleteNoNumberNotification(NotificationManager manager, String newChannelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            List<NotificationChannel> notificationChannels = null;
            notificationChannels = manager.getNotificationChannels();

//            LogManager.i(TAG, "deleteNoNumberNotification notificationChannels******" + notificationChannels, toString());
//            if (notificationChannels == null || notificationChannels.size() < 5) {
//                return;
//            }
            if (notificationChannels == null) {
                return;
            }
            for (NotificationChannel channel : notificationChannels) {
                if (channel.getId() == null || channel.getId().equals(newChannelId)) {
                    continue;
                }

                int notificationNumbers = getNotificationNumbers(manager, channel.getId());
                Log.i(TAG, "notificationNumbers: " + notificationNumbers + " channelId:" + channel.getId());
                if (notificationNumbers == 0) {
                    Log.i(TAG, "deleteNoNumberNotification: " + channel.getId());
                    manager.deleteNotificationChannel(channel.getId());
                }
            }
        }
    }

    /**
     * 获取某个渠道下状态栏上通知显示个数
     *
     * @param mNotificationManager NotificationManager
     * @param channelId            String
     * @return int
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static int getNotificationNumbers(NotificationManager mNotificationManager, String channelId) {
        if (mNotificationManager == null || TextUtils.isEmpty(channelId)) {
            return -1;
        }
        int numbers = 0;
        StatusBarNotification[] activeNotifications = mNotificationManager.getActiveNotifications();
        for (StatusBarNotification item : activeNotifications) {
            Notification notification = item.getNotification();
            if (notification != null) {
                if (channelId.equals(notification.getChannelId())) {
                    numbers++;
                }
            }
        }
        return numbers;
    }
}
