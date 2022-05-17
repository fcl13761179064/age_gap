package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.MyInfoBean;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface MutualMatchView extends BaseView {

    void errorShake(int type, int CycleTimes, String msg);

    void AbountSuccess(MyInfoBean data);
}
