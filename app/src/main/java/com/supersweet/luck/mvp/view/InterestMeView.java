package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;

import java.util.List;

public interface InterestMeView extends BaseView {

    void setLoveMeData(FavoritesBean data);

    void setLoveMeDataFail(String msg);

    void IntersetMeSuccess(IntenetReposeBean o, int connectionUserId, List<FavoritesBean.Love> data);

    void IntersetMeFail(String message, int connectionUserId);
}
