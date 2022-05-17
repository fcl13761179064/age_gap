package com.supersweet.luck.mvp.view;


import com.supersweet.luck.addressselector.AddressSelector;
import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.bean.User;


/**
 * @描述
 * @作者 fanchunlei
 * @时间 2020/7/14
 */
public interface MainView extends BaseView {

    void MyInfo_success(MyInfoBean s);

    void errorShake(String message);

    void OnCountySucces(CityBean cityBean, AddressSelector addressSelector);
    void OnStationSucces(StationBean cityBean, AddressSelector addressSelector);

    void updateLocation(IntenetReposeBean o);

    void updataLocationerror(String message);
}
