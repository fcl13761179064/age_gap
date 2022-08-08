package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MultualMatchBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.tencent.imsdk.v2.V2TIMConversation;

import java.util.List;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface MultualMatchView extends BaseView {

    //错误提示
    void errorShake(int type, int CycleTimes, String msg);

    void MultualSuccess(List<MultualMatchBean> data);

    void chatHeadSuccess(List<OtherUserInfoBean> chatInfoBeanslist, List<V2TIMConversation> text);

    void MultualMatchSuccess(IntenetReposeBean o, int multualMatchBean);

    void MultualMatchFail(String o);

    void checkIsMonthPay(IntenetReposeBean message);
}
