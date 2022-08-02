package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface MyLoveView extends BaseView {

    //错误提示
    void errorShake(int type, int CycleTimes, String msg);
    void checkIsMonthPay(IntenetReposeBean message);
    void FavoritesSuccess(FavoritesBean datum, FavoritesBean data, boolean isRefresh);
}
