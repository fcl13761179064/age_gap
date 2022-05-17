package com.supersweet.luck.myupdate;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.AppUtils;

public class UpgradeUnifiedCode {

    private UpgradeUnifiedCode() {
    }

    public static void uppdataUI(@NonNull AppCompatActivity activity) {

        UpgradeDownloadService.UpgradeDownloadBroadcast broadcast = new UpgradeDownloadService.UpgradeDownloadBroadcast() {
            @Override
            public void noticeUi() {
                activity.unregisterReceiver(this);
            }
        };

        broadcast.register(activity);

        Intent intent = new Intent(activity, UpgradeDownloadService.class);
        activity.startService(intent);
        activity.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_RESUME) {

                }
            }
        });
    }
}
