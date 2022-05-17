package com.supersweet.luck.mvp.view;

import com.supersweet.luck.addressselector.AddressSelector;
import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.StationBean;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface LocationView extends BaseView {


    void errorShake(int type, int CycleTimes, String msg);


    void sheng(CityBean data, AddressSelector addressSelector);

    void city(CityBean data, String city, String station);

    void qu(StationBean data,AddressSelector addressSelector);

    void station(StationBean data, String cofigvalue);

}
