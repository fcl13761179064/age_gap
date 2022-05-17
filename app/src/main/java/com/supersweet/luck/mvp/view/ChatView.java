package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.dialog.ReportUserDialog;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface ChatView extends BaseView {

    void errorShake(int type, int CycleTimes, String msg);

    void Success(MyInfoBean data);

    void  ConSumeCoinSuccess(IntenetReposeBean intenetReposeBean, String messageInfo);

    void BuyCoinFail(String msg);
    void ReportUserSuccess(Object success, ReportUserDialog reportUserDialog);
    void BlockUserSuccess(Object success);

    //错误提示
    void errorShakes(String msg, ReportUserDialog reportUserDialog);
    void errorShakess(String msg);
}
