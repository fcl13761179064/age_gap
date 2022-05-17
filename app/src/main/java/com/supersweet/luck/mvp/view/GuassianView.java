package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.User;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface GuassianView extends BaseView {

    //错误提示
    void errorShake(int type, int CycleTimes, String msg);

    void LocationSuccess(AllCountryBean data);
}
