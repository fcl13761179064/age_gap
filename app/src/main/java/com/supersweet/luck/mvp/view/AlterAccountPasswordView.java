package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.MyInfoBean;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface AlterAccountPasswordView extends BaseView {

    void errorShake( String msg);

    void AlterSuccess(String data);
}
