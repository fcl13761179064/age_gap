package com.supersweet.luck.pushinfo;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.util.Log;
import android.widget.ScrollView;
import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.supersweet.luck.R;
import java.util.List;

/*
 * 云推送Demo主Activity。
 * 代码中，注释以Push标注开头的，表示接下来的代码块是Push接口调用示例
 */
public class PushDemoActivity extends Activity  {

    private static final String TAG = PushDemoActivity.class.getSimpleName();

    private static final int REQ_CODE_INIT_APIKEY = 0;

    /** 魅族代理需要的魅族appid和appkey，请到魅族推送官网申请 **/
    private static final String mzAppId = "";
    private static final String mzAppKey = "";

    /** 小米代理需要的小米appid和appkey，请到小米推送官网申请 **/
    private static final String xmAppId = "";
    private static final String xmAppKey = "";

    /** OPPO代理需要的OPPO appkey和appsecret，请到OPPO推送官网申请 **/
    private static final String opAppKey = "";
    private static final String opAppSecret = "";
    ScrollView scrollView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.logStringCache = Utils.getLogText(getApplicationContext());
        initWithApiKey();
        setTags();
        /**
         * 以下通知栏设置2选1。使用默认通知时，无需添加以下设置代码。
         */

        // 1.默认通知
        // 若您的应用需要适配Android O（8.x）系统，且将目标版本targetSdkVersion设置为26及以上时：
        // SDK提供设置Android O（8.x）新特性---通知渠道的设置接口。
        // 若不额外设置，SDK将使用渠道名默认值"云推送"；您也可以仿照以下3行代码自定义channelId/channelName。
        // 注：非targetSdkVersion 26的应用无需以下调用且不会生效
  /*      BasicPushNotificationBuilder bBuilder = new BasicPushNotificationBuilder();
        bBuilder.setChannelId("testDefaultChannelId");
        bBuilder.setChannelName("testDefaultChannelName");*/
        // PushManager.setDefaultNotificationBuilder(this, bBuilder); //使自定义channel生效

        // 2.自定义通知
        // 设置自定义的通知样式，具体API介绍见用户手册
        // 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
        // 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应
        CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
                R.layout.notification_custom_builder,
                R.id.notification_icon,
                R.id.notification_title,
                R.id.notification_text);

        cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        cBuilder.setNotificationDefaults(Notification.DEFAULT_VIBRATE);
        cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
        cBuilder.setLayoutDrawable(R.mipmap.app_logo);
        cBuilder.setNotificationSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6").toString());
        // 若您的应用需要适配Android O（8.x）系统，且将目标版本targetSdkVersion设置为26及以上时：
        // 可自定义channelId/channelName, 若不设置则使用默认值"Push"；
        // 注：非targetSdkVersion 26的应用无需以下2行调用且不会生效
        cBuilder.setChannelId("testId");
        cBuilder.setChannelName("testName");
        PushManager.setDefaultNotificationBuilder(this, cBuilder); //使自定义channel生效
        // 推送高级设置，通知栏样式设置为下面的ID，ID应与server下发字段notification_builder_id值保持一致
        PushManager.setNotificationBuilder(this, 1, cBuilder);

    }



    // 设置标签,以英文逗号隔开
    private void setTags() {
        // Push: 设置tag调用方式
        List<String> tags = Utils.getTagsList("age_gap");
        PushManager.setTags(getApplicationContext(),tags);
    }

    // api_key 绑定
    private void initWithApiKey() {
        // Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
        // 这里把apikey存放于manifest文件中，只是一种存放方式，
        // 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
        // "api_key")
       //请将AndroidManifest.xml api_key 字段值修改为自己的 api_key 方可使用 ！！
        //！！ATTENTION：You need to modify the value of api_key to your own in AndroidManifest.xml to use this Demo !!
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                Utils.getMetaValue(PushDemoActivity.this, "api_key"));
    }

    // 解绑
    private void unBindForApp() {
        // Push：解绑
        PushManager.stopWork(getApplicationContext());
    }

    // 列举tag操作
    private void showTags() {
        // Push：标签列表
        PushManager.listTags(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        updateDisplay();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        updateDisplay();
    }

    @Override
    public void onDestroy() {
        Utils.setLogText(getApplicationContext(), Utils.logStringCache);
        super.onDestroy();
    }

    // 更新界面显示内容
    private void updateDisplay() {

    }

}
