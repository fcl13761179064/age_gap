package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface CheckAccountView extends BaseView {

    //获取用户名
    String getUserName();

    //密码
    String getPassword();
    //邮箱
    String getEmal();

    //checkbox
    boolean getCheckBox();

    //错误提示
    void errorShake(int type, int CycleTimes, String code);

    //注册成功
    void RegistSuccess(Boolean data);
}
