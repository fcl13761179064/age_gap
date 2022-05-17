package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;

import java.util.List;

public interface MutualMutchView extends BaseView {


    void MultualMatchSuccess(IntenetReposeBean o, int connectionUserId);

    void MultualMatchFail(String message);
}
