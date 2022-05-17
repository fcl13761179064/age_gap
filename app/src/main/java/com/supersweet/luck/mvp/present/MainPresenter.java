package com.supersweet.luck.mvp.present;


import com.supersweet.luck.R;
import com.supersweet.luck.addressselector.AddressSelector;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.ItemAddressReq;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.bean.User;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.MainView;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.CustomToast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2020/7/14
 */
public class MainPresenter extends BasePresenter<MainView> {

    public void getCity(String code, AddressSelector addressSelector) {
        RequestModel.getInstance().getCity(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                }).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                mView.hideProgress();
            }
        }).subscribe(new Observer<CityBean>() {

            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                addSubscrebe(d);
            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull CityBean cityBean) {
                mView.OnCountySucces(cityBean, addressSelector);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.errorShake(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
