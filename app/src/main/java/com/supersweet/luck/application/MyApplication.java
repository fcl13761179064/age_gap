package com.supersweet.luck.application;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.billingclient.api.BillingClient;
import com.baidu.android.pushservice.BasicPushNotificationBuilder;
import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushManager;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.supersweet.luck.R;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.DeviceListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.supersweet.luck.google.BillingManager;
import com.supersweet.luck.serive.service.LocationService;
import com.supersweet.luck.signature.GenerateTestUserSig;
import com.supersweet.luck.ui.BuyCoinPageActivity;
import com.supersweet.luck.ui.MainActivity;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by fcl13761179064 on 2020/6/3.
 */
public class MyApplication extends Application {
    private final String TAG = this.getClass().getSimpleName();
    private List<DeviceListBean.DevicesBean> mDevicesBean;
    private static MyApplication mInstance = null;
    private static ArrayList<AllCountryBean.Country> allcountry;

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Context getContext() {
        return mInstance.getApplicationContext();
    }

    public static Resources getResource() {
        return mInstance.getResources();
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        Locale.setDefault(Locale.ENGLISH);
        mInstance = this;
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        initAutoSize();
        initTuikit();
        try {

            notication();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void notication() {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            BasicPushNotificationBuilder bBuilder = new BasicPushNotificationBuilder();
            bBuilder.setChannelId("AgeGapChannelId");
            bBuilder.setChannelName("AgeGapChannelName");
            bBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
            bBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
            bBuilder.setNotificationDefaults(Notification.DEFAULT_VIBRATE);
            bBuilder.setNotificationSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6").toString());
            PushManager.setDefaultNotificationBuilder(this, bBuilder); //使自定义channel生效
        } else {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setContentTitle("AgeGap")//设置通知栏标题
                    //通知首次出现在通知栏，带上升动画效果的
                    .setTicker("通知来啦")
                    //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                    .setWhen(System.currentTimeMillis())
                    //设置该通知优先级
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    //设置通知栏内容
                    .setContentText("")
                    //设置通知大图标，在通知栏显示
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo))
                    //设置这个标志当用户单击面板就可以让通知将自动取消
                    .setAutoCancel(true)
                    //ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                    .setOngoing(false)
                    //设置通知小图标在状态栏显示（必须设置）
                    .setSmallIcon(R.mipmap.app_logo);//设置通知小ICON
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            notification = mBuilder.build();
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }


    public void initTuikit() {
        // 1. 从 IM 控制台获取应用 SDKAppID，详情请参考 SDKAppID。
        // 2. 初始化 config 对象
        V2TIMSDKConfig config = new V2TIMSDKConfig();
        // 3. 指定 log 输出级别，详情请参考 SDKConfig。
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_INFO);
        // 4. 初始化 SDK 并设置 V2TIMSDKListener 的监听对象。
        // initSDK 后 SDK 会自动连接网络，网络连接状态可以在 V2TIMSDKListener 回调里面监听。
        //初始化 IM SDK 基本配置
        //判断是否是在主线程
        if (SessionWrapper.isMainProcess(getApplicationContext())) {
            V2TIMManager.getInstance().initSDK(getContext(), GenerateTestUserSig.SDKAPPID, config, new V2TIMSDKListener() {
                // 5. 监听 V2TIMSDKListener 回调
                @Override
                public void onConnecting() {
                    // 正在连接到腾讯云服务器
                }

                @Override
                public void onConnectSuccess() {
                    // 已经成功连接到腾讯云服务器
                }

                @Override
                public void onConnectFailed(int code, String error) {
                    // 连接腾讯云服务器失败
                }
            });
        }
    }


    public List<DeviceListBean.DevicesBean> getDevicesBean() {
        return mDevicesBean;
    }

    public void setDevicesBean(List<DeviceListBean.DevicesBean> devicesBean) {
        mDevicesBean = devicesBean;
    }

    private void initAutoSize() {
//        AutoSizeConfig.getInstance()
//                .setBaseOnWidth(true)
//                .getUnitsManager()
//                .setSupportSubunits(Subunits.MM);
    }
}
