package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.BlockMemberbean;
import com.supersweet.luck.bean.PayCordBean;
import com.supersweet.luck.mvp.present.PayRecordPresenter;

import java.util.List;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface PayRecordView extends BaseView {

    //错误提示
    void errorShake(String msg);

    void getPayRecordInfoSuccess(PayCordBean data);


}
