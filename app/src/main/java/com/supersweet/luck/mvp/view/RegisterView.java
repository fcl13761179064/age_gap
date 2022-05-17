package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.User;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface RegisterView extends BaseView {

    //错误提示
    void errorShake(int type, int CycleTimes, String code);

    //注册成功
    void RegistSuccess(IntenetReposeBean data);

    void loginSuccess(User user);

    void updateLocation(IntenetReposeBean o);
}
