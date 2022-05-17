package com.supersweet.luck.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.supersweet.luck.application.MyApplication;


/**
 * @描述 通用工具类
 * @作者 fanchunlei
 * @时间 2017/7/20
 */
public class CommonUtils {



    private static final String ANDROID_ID = "9774d56d682e549c";//Android_ID的bug，不同设备都拿到该ANDROID_ID

    /**
     * 先获取androidId,获取不到或者为9774d56d682e549c去设备号,设备号为空生成UUID
     */
    public static String getDeviceId() {
        //SERIAL值  没有通话功能的设备会返回一个唯一的device ID
        String serialNumber = android.os.Build.SERIAL;
        if (!TextUtils.isEmpty(serialNumber)) {
            return serialNumber;
        }
        String androidID = Settings.Secure.getString(MyApplication.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidID) && !androidID.equals(ANDROID_ID)) {
            return androidID;
        }
        //设备号 IMEI
        TelephonyManager tm = (TelephonyManager) MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") String deviceId = tm.getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        //序列号 IMSI
        @SuppressLint("MissingPermission") String sn = tm.getSimSerialNumber();
        if (!TextUtils.isEmpty(sn)) {
            return sn;
        }
        // Mac值
//        WifiManager wifi = (WifiManager) MyApplication.getContext().getSystemService(Context.WIFI_SERVICE);
//        String macAddress = wifi.getConnectionInfo().getMacAddress();
//        if(!TextUtils.isEmpty(macAddress)){
//            card_return macAddress;
//        }
        return InstalltionID.id(MyApplication.getContext());
    }


    public static String packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        String code =null;
        try { PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionName;
        } catch (PackageManager.NameNotFoundException e) { e.printStackTrace();
        } return code;
    }

}
