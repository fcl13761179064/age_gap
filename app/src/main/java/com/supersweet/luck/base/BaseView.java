package com.supersweet.luck.base;

import com.supersweet.luck.bean.MyInfoBean;

/**
 * MVP模式
 * View层
 */
public interface BaseView {

    void showProgress(String msg);

    void showProgress();

    void hideProgress();

}
