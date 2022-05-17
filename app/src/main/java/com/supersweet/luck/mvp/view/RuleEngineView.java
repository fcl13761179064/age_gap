package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.RuleEngineBean;

import java.util.List;


public interface RuleEngineView extends BaseView {

    void loadDataSuccess(List<RuleEngineBean> data);

    void loadDataFinish() ;
}
