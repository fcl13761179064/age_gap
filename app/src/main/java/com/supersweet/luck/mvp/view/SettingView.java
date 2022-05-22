package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface SettingView extends BaseView {

    //错误提示
    void errorShake( String msg);

    void logoutsuccess(IntenetReposeBean s);

    void BuyError(String toString);

    void BuyHighCoinSuccess(IntenetReposeBean o);

    void BuyConsumeCoinSuccess(Object o);

    void MyInfo_success(MyInfoBean s);

    void loginFail();
}
