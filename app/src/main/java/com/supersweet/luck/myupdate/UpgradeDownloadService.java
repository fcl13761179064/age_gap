package com.supersweet.luck.myupdate;

import android.app.Activity;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.blankj.utilcode.util.PathUtils;

public class UpgradeDownloadService extends IntentService {

    private static final String TAG = "UpgradeDownloadService";

    private final int notifyId = 123423;

    public static boolean isWorking;

    public UpgradeDownloadService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {


    }

    public static abstract class UpgradeDownloadBroadcast extends BroadcastReceiver {
        public static final String sAction_notice = "notice";

        @Override
        public final void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case sAction_notice:
                    noticeUi();
                    break;
            }
        }

        public abstract void noticeUi();

        public void register(Activity activity) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(sAction_notice);
            activity.registerReceiver(this, intentFilter);
        }
    }
}
