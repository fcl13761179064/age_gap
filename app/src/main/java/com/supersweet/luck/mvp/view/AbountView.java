package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.User;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface AbountView extends BaseView {

    void errorShake(int type, int CycleTimes, String msg);

    void AbountSuccess(MyInfoBean data);

}
