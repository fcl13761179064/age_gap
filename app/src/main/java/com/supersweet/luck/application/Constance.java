package com.supersweet.luck.application;

import com.supersweet.luck.BuildConfig;

/**
 * @描述 常量类
 * @作者 fanchunlei
 * @时间 2020/7/8
 */
public class Constance {
    //渠道id
    public static final String APP_ID = "f81d2be7b8cb6b2a3d0cacf4e0660873";
    public static final String APP_SECRET = "0bfb2702ea0fd654cc0efb0adcd9997c";

    /**
     * 是否是网络测试环境
     */
    private static boolean networkDebug;

    static {
        switch (BuildConfig.server_domain) {
            case "qa":
            case "dev":
                networkDebug = true;
                break;
            default:
                networkDebug = false;
        }
    }


    public static String sProdUrl = "https://www.agegap.app";//正式环境
    public static String mai = "https://49.51.41.162:18088 ";
    public static String sQaUrl = "http://211.149.235.166:8088";//测试环境
    public static String sQaUrls = "http://49.51.41.162:18088/";//支付的预测试环境

    public static String getBaseUrl() {
        String url;
        switch (BuildConfig.server_domain) {
            case "qa":
                url = sQaUrl;
                break;
            case "prod":
                url = sProdUrl;
                break;
            default:
                url = sProdUrl;
        }

        return url;
    }


    public static boolean isNetworkDebug() {
        return networkDebug;
    }



    //登录保存key
    public static String SP_Login_Token = "login_token";
    public static String SP_USER_NAME = "user_name";
    public static String SP_HEADER = "head_img";
    public static String SP_SEX = "sex";

    //refresh token
    public static String SP_Refresh_Token = "refresh_token";

    //状态
    public static final String V_STATUS_online = "online";    //初始状态
    public static final String V_STATUS_offline = "offline";    //未批准
    public static String Delete_Img = "/api/user/deleteAlbum";

    public static String ALL_COUNTRY = "all_country";
    public static String LATITUDE = "latitude";
    public static String ALTITUDE = "altitude";

    public static String GET_LOCATION() {
        return "https://pro.ip-api.com";
    }
}
