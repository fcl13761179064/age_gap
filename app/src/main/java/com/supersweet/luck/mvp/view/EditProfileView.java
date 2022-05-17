package com.supersweet.luck.mvp.view;

import com.supersweet.luck.addressselector.AddressSelector;
import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.bean.UpHeadBean;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface EditProfileView extends BaseView {

    //错误提示
    void errorShake(int type, int CycleTimes, String msg);

    void editMyInfoProgress(String s, int position);

    void UPHeadSuccess(UpHeadBean s);

    void sheng(CityBean data, AddressSelector addressSelector);


    void qu(StationBean data, AddressSelector addressSelector);


    void updateFuwuqisuccess(MyInfoBean s);

}
