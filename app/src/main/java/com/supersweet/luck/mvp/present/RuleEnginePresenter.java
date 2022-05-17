package com.supersweet.luck.mvp.present;


import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.RuleEngineBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.RuleEngineView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2020/7/14
 */
public class RuleEnginePresenter extends BasePresenter<RuleEngineView> {
    /**
     * 加载列表
     *
     * @param resourceRoomId
     */
    public void loadData(long resourceRoomId) {
    }
}
