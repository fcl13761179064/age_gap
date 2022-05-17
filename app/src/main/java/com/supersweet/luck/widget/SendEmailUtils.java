package com.supersweet.luck.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.supersweet.luck.dialog.SingleConfireDialog;
import com.supersweet.luck.ui.SettingActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SendEmailUtils {

    private SendEmailUtils() {
    }

    private static SendEmailUtils instance;

    public static SendEmailUtils getInstance() {
        synchronized (SendEmailUtils.class) {
            if (instance == null) {
                instance = new SendEmailUtils();
            }
        }
        return instance;
    }

    /**
     * 这是一个简单的测试，不支持带附件，多人，抄送发送等。
     *
     * @param context
     */
    public void sendEmail(Context context) {
        try {
            Uri uri = Uri.parse("mailto:support@luckyapp.co");
            String[] email = {""};
            String clientid = CacheUtils.getString(context, "clientid", "");
            String deviceId = CommonUtils.getDeviceId();
            String model = Build.MODEL;
            String release = Build.VERSION.RELEASE;
            String code = CommonUtils.packageCode(context);
            String s = "User: " + clientid + ", " + "Version: " + code + ", " + "Device: " + deviceId + ", " + "Model: " + model + ", " + "Android: " + release;
            String my_desc = s.replaceAll("\r\n", "");

            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
            intent.putExtra(Intent.EXTRA_SUBJECT, ""); // 主题
            intent.putExtra(Intent.EXTRA_TEXT, "\n\n\n\n\n\n" + my_desc); // 正文
            Intent choose_email_client = Intent.createChooser(intent, "Choose Email Client");
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

            if (intent.resolveActivity(context.getPackageManager()) != null){
                context.startActivity(choose_email_client);
            }else {
                showBottomListDialog(context);
            }



        } catch (Exception e) {

            showBottomListDialog(context);
        }

    }


    /**
     * 底部弹出对话框
     */
    public void showBottomListDialog(Context context) {
        SingleConfireDialog singleConfireDialog = new SingleConfireDialog(context, "Please email us at support@luckyapp.co.");
        singleConfireDialog.setOnSureClick("OK ", new SingleConfireDialog.OnSureClick() {
            @Override
            public void click(Dialog dialog) {


            }
        });
        singleConfireDialog.show();
    }

    /**
     * 发邮件，带抄送，和密送，并带上个附件
     *
     * @param context
     */
    public void sendEmailDuo(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
      //  intent.setData(Uri.parse("mailto:"));
        String[] tos = {"support@luckyapp.co"};
        String[] ccs = {""};

        String clientid = CacheUtils.getString(context, "clientid", "");
        String deviceId = CommonUtils.getDeviceId();
        String model = Build.MODEL;
        String release = Build.VERSION.RELEASE;
        String code = CommonUtils.packageCode(context);
        String s = "User: " + clientid + ", " + "Version: " + code + ", " + "Device: " + deviceId + ", " + "Model: " + model + ", " + "Android: " + release;
        String my_desc = s.replaceAll("\r\n", "");
        intent.putExtra(Intent.EXTRA_EMAIL, tos); //收件者
        intent.putExtra(Intent.EXTRA_CC, ccs); //抄送这
        intent.putExtra(Intent.EXTRA_TEXT, my_desc);
        intent.putExtra(Intent.EXTRA_SUBJECT, "邮件标题");

        intent.setType("message/rfc882");
        Intent.createChooser(intent, "Choose Email Client");
        context.startActivity(intent);

    }

    /**
     * 多附件发送
     *
     * @param conext
     */
    public void sendFujian(Context conext) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        String[] tos = {"way.ping.li@gmail.com"};
        String[] ccs = {"way.ping.li@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, tos);
        intent.putExtra(Intent.EXTRA_CC, ccs);
        intent.putExtra(Intent.EXTRA_TEXT, "body");
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");

        List<Uri> imageUris = new ArrayList<Uri>();
        imageUris.add(Uri.parse("file:///mnt/sdcard/a.jpg"));
        imageUris.add(Uri.parse("file:///mnt/sdcard/b.jpg"));
        intent.putExtra(Intent.EXTRA_STREAM, (Serializable) imageUris);
        intent.setType("image/*");
        intent.setType("message/rfc882");
        Intent.createChooser(intent, "Choose Email Client");
        conext.startActivity(intent);
    }

}