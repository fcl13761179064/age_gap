package com.supersweet.luck.mvp.present;

import com.supersweet.luck.addressselector.AddressSelector;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.LocationView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LocationPresenter extends BasePresenter<LocationView> {


    public void btnCity(String code, AddressSelector addressSelector) {
        RequestModel.getInstance().getCity(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                }).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                mView.hideProgress();
            }
        }).subscribe(new Observer<CityBean>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                addSubscrebe(d);
            }

            @Override
            public void onNext(@NonNull CityBean cityBean) {
                mView.sheng(cityBean, addressSelector);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.errorShake(1, 2, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void btnStation(String qu, AddressSelector addressSelector) {
        RequestModel.getInstance().getStation(qu)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                })
                .subscribe(new Observer<StationBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(StationBean s) {
                        mView.hideProgress();
                        mView.qu(s, addressSelector);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.errorShake(0, 2, e.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void city(String code, String province,String city) {
        RequestModel.getInstance().getCity(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                }).subscribe(new Observer<CityBean>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                addSubscrebe(d);
            }

            @Override
            public void onNext(@NonNull CityBean cityBean) {
                mView.city(cityBean,province,city);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.errorShake(1, 2, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void station(String code, String station) {
        RequestModel.getInstance().getStation(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StationBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(StationBean s) {
                        mView.hideProgress();
                        mView.station(s,station);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.errorShake(0, 2, e.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
