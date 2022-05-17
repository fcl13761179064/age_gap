package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.PhotoBean;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.bean.UpHeadBean;

import java.util.List;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface VerrifyPhotoView extends BaseView {

    //错误提示
    void errorShake(int type, int CycleTimes, String msg);

    void VerifyPhotoSuccess(UpHeadBean data);
    //默认1次加载20个
    void card_serch_success(List<SeachPeopleBean> data);

    void card_serch_more_success(List<SeachPeopleBean> data);

}
