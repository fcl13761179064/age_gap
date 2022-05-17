package com.supersweet.luck.utils;

import android.util.Log;

import com.supersweet.luck.application.MyApplication;

public class Logger {
    private static final String TAG = MyApplication.getContext().getClass().getSimpleName();

    public static void d(String tag, String s) {
        Log.d(TAG, "[" + tag + "] " + s);
    }
}
