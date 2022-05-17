package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.bean.UpHeadBean;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface MyInfoView extends BaseView {

    //错误提示
    void errorShake(int type, int CycleTimes, String msg);

    //登录成功
    void MyInfo_success(MyInfoBean data);

    void VerifyPhotoSuccess(UpHeadBean data);

    void station(StationBean s, String station);
}
