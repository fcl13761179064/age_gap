package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.bean.PhotoBean;
import com.supersweet.luck.dialog.ReportUserDialog;

import java.util.List;


/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface FavoriteDetailView extends BaseView {

    //错误提示
    void errorShake(String msg, ReportUserDialog reportUserDialog);
    void errorShakes(String msg);
    void likeSuccess(String success);
    void unlikeSuccess(String success);
    void ReportUserSuccess(Object success, ReportUserDialog reportUserDialog);
    void BlockUserSuccess(Object success);
    void getOtherUserInfoSuccess(OtherUserInfoBean data,List<PhotoBean> success);
}
