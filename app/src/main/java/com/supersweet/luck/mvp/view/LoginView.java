package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.User;

import org.json.JSONException;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface LoginView extends BaseView {

    //获取账号
    String getAccount();

    //密码
    String getPassword();

    //错误提示
    void errorShake(int type, int CycleTimes, String msg);

    //登录成功
    void loginSuccess(User data);

    void updateLocation(IntenetReposeBean o);
}
