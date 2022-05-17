package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.MyInfoBean;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface AvaterView extends BaseView {

    //上传图片
    void success(String msg);

    //错误提示
    void errorShake(int type, int CycleTimes, String msg);

}
