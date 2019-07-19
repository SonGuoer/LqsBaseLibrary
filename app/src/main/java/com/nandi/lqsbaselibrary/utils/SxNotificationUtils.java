package com.nandi.lqsbaselibrary.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import static android.support.v4.app.NotificationCompat.VISIBILITY_SECRET;

/**
 * Date:2019-07-13
 * Time:11:37
 * author:qingsong
 * 8.0通知工具
 */
public class SxNotificationUtils extends ContextWrapper {
    private String title;
    private String content;
    private int smallIcon;
    private String channelName;
    private String channelId;
    private Notification.Builder builder;
    private NotificationManager notificationManager;

    private NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            return notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        } else {
            return notificationManager;
        }
    }

    /**
     * @param context
     * @param channelName 通道名
     * @param channelId   通道ID
     */
    public SxNotificationUtils(Context context, String channelName, String channelId) {
        super(context);
        this.channelName = channelName;
        this.channelId = channelId;
        setChannel();
    }

    /**
     * @param context
     * @param channelName 通道名
     * @param channelId   通道ID
     * @param title       通知标题
     * @param content     通知内容
     * @param smallIcon   通知栏显示的图标
     */
    public SxNotificationUtils(Context context, String channelName, String channelId, String title, String content, int smallIcon) {
        super(context);
        this.channelName = channelName;
        this.channelId = channelId;
        this.title = title;
        this.content = content;
        this.smallIcon = smallIcon;
        setBuilder();
        setChannel();
    }

    /**
     * 构造Notification.Builder
     */
    private void setBuilder() {
        builder = new Notification.Builder(this)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }
    }

    /**
     * 8.0 以上给通知设置通道
     */
    private void setChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("通道描述");
            //可否绕过系统的 请勿打扰模式
            channel.canBypassDnd();
            channel.setBypassDnd(true);
            //锁屏是否显示通知（系统应用可用，基本用不到）
            channel.setLockscreenVisibility(VISIBILITY_SECRET);
            //通知时是否有闪光灯（需要手机硬件支持）
            channel.enableLights(true);
            channel.shouldShowLights();
            //设置闪光时的颜色
            channel.setLightColor(Color.BLUE);
            //是否显示桌面图标角标
            channel.canShowBadge();
            channel.setShowBadge(true);
            //是否允许震动
            channel.enableVibration(true);
            //获取系统通知响铃的配置参数（一般用系统默认的即可）
            channel.getAudioAttributes();
            //channel.setSound(); 设置通知铃声
            //设置震动频率（100ms，100ms，100ms 三次震动）
            channel.setVibrationPattern(new long[]{500, 500, 500});

            //给8.0以上的通知设定 渠道
            getNotificationManager().createNotificationChannel(channel);
        }
    }

    /**
     * 显示通知
     *
     * @param notifyId
     */
    public void notify(int notifyId) {
        getNotificationManager().notify(notifyId, builder.build());
    }

}
