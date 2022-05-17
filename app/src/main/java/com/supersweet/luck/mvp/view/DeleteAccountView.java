package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.User;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface DeleteAccountView extends BaseView {

    void errorShake(int type, int CycleTimes, String code);

    void DeleteSuccess(Boolean data);

}
