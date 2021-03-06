package com.supersweet.luck.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class SharePreferenceUtils {
    public final static String SP_NAME = "config";
    private static SharedPreferences sp;

    public static void saveBoolean(Context context, String key, boolean value) {

        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        sp.edit().putBoolean(key, value).commit();
    }

    public static void saveInt(Context context, String key, int value) {

        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putInt(key, value).commit();
    }

    public static void saveLong(Context context, String key, long value) {

        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String key, int defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        return sp.getLong(key, defValue);
    }


    public static void saveString(Context context, String key, String value) {

        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        sp.edit().putString(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        return sp.getInt(key, defValue);
    }

    public static void saveFloat(Context context, String key, double value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putFloat(key, (float) value).commit();
    }

    public static float getFloat(Context context, String key) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getFloat(key, 0);
    }


    public static String getString(Context context, String key, String defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        return sp.getString(key, defValue);

    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        return sp.getBoolean(key, defValue);
    }


    public static void remove(Context context, String key) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().remove(key).commit();
    }

    /**
     * desc:????????????
     *
     * @param context
     * @param key
     * @param obj     ??????????????????????????????????????????serializable?????????
     *                modified:
     */
    public static void saveObject(Context context, String key, Object obj) {
        try {
            // ????????????
            SharedPreferences.Editor sharedata = context.getSharedPreferences(SP_NAME, 0).edit();
            //???????????????????????????byte?????????????????????????????????????????????
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //????????????????????????byte??????
            os.writeObject(obj);
            //???????????????????????????16????????????
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //?????????16????????????
            sharedata.putString(key, bytesToHexString);
            sharedata.commit();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", "??????obj??????");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * desc:???????????????16??????
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:???????????????Object??????
     *
     * @param context
     * @param key
     * @return modified:
     */
    public static Object readObject(Context context, String key) {
        try {
            SharedPreferences sharedata = context.getSharedPreferences(SP_NAME, 0);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    //???16????????????????????????????????????????????????
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //?????????????????????????????????
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //??????????????????null
        return null;

    }

    /**
     * desc:???16???????????????????????????
     *
     * @param data
     * @return modified:
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // ??????16?????????????????????10?????????
            char hex_char1 = hexString.charAt(i);
            //??????16????????????????????????(??????*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;
                //0 ???Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16;
                //A ???Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///??????16????????????????????????(??????)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48);
                //0 ???Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55;
                //A ???Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//????????????????????????Byte???
        }
        return retData;
    }
}
