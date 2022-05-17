package com.supersweet.luck.mvp.present;

import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.CardBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.CardSearchView;
import com.supersweet.luck.mvp.view.MyInfoView;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.CustomToast;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MyInfoPresenter extends BasePresenter<MyInfoView> {

    public void getMyinfo() {
        RequestModel.getInstance().getMyinfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                })
                .subscribe(new Observer<MyInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(MyInfoBean s) {
                        mView.hideProgress();
                        mView.MyInfo_success(s);
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


    public void station(String cityCode, String station) {
        RequestModel.getInstance().getStation(cityCode)
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

    public void verfyUpdataPhoto(String localPath) {
        RequestModel.getInstance().verfyUpdataPhoto(localPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideProgress();
                    }
                }).subscribe(new Observer<UpHeadBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscrebe(d);
            }

            @Override
            public void onNext(UpHeadBean upHeadBean) {
                mView.VerifyPhotoSuccess(upHeadBean);
            }

            @Override
            public void onError(Throwable e) {
                CustomToast.makeText(MyApplication.getContext(), "Head portrait up turn failed", R.drawable.ic_toast_warming).show();
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
