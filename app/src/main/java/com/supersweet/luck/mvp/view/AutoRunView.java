package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.RuleEngineBean;

public interface AutoRunView extends BaseView {

    void changeSuccess();

    void changeFailed(RuleEngineBean ruleEngineBean);
}
