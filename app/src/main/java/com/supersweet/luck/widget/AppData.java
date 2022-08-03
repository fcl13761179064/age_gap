package com.supersweet.luck.widget;

import android.graphics.Bitmap;

import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.LocationBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.bean.StationBean;

import java.util.ArrayList;
import java.util.List;

public class AppData extends MyApplication {
    public static String country;
    public static String city;
    public static String station;
    public static String vagueLevel;
    public static String WheelData;
    public static int WheelPositon;
    public static String avatar;

    public static ArrayList<AllCountryBean.Country> allCountry;//1所有国家
    public static List<CityBean.City> allCity;
    public static List<StationBean.City> allStation;
    public static Bitmap BitMap;
    public static MyInfoBean MyInfoBean;//fragmentOne和myInfo
    public static OtherUserInfoBean OhterfoBean;//otherInfo
    public static String location_station;


    public static String Filter_country;
    public static String Filter_city;
    public static int Filter_minAge;
    public static int Filter_maxAge;


    public static String updataAbount;
    public static int isShow;
    public static String search_sex;
    public static boolean is_come_in;
    public static String verified;



}
