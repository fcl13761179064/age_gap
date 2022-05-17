package com.supersweet.luck.utils;

import android.text.TextUtils;

public  class CheckRulerUtils  {
    public static boolean checkAccount(String username) {
        String passRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        return !TextUtils.isEmpty(username) && username.matches(passRegex);
    }
}
