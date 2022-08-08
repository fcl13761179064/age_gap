package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;

import java.util.List;

public interface InterestMeView extends BaseView {

    void setLoveMeData(FavoritesBean data);

    void setLoveMeDataFail(String msg);

    void IntersetMeFail(String message, int connectionUserId);

    void checkIsMonthPay(IntenetReposeBean intenetReposeBean);
}
