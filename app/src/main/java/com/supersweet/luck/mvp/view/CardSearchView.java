package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.dialog.ReportUserDialog;

import java.util.List;


/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface CardSearchView extends BaseView {

    //错误提示
    void errorShake(String msg);

    void errorShakes(String msg, ReportUserDialog reportUserDialog);

    //默认1次加载20个
    void card_serch_success(List<SeachPeopleBean> data);

    void likeSuccess(String success, int userId);

    void unlikeSuccess(String success, int userId);

    void LocationSuccess(AllCountryBean s);


    void UPHeadSuccess(UpHeadBean upHeadBean, String localPath);

    void getCurrentLocationSuccess(String countrycode, String locationBean);

    void updateHeadsuccess(MyInfoBean s);

    void getOtherUserInfoSuccess(OtherUserInfoBean chatInfo);

    void BuyHighCoinSuccess(IntenetReposeBean o);

    void BuyError(String toString);

    void ReportUserSuccess(Object value, ReportUserDialog reportUserDialog);

    void BlockUserSuccess(Object value);

    void checkIsMonthPay(IntenetReposeBean message, int otherId);
}
