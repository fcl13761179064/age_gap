package com.supersweet.luck.mvp.present;

import android.content.Intent;
import android.text.TextUtils;

import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.User;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.CheckAccountView;
import com.supersweet.luck.mvp.view.RegisterView;
import com.supersweet.luck.utils.PregnancyUtil;
import com.supersweet.luck.widget.CustomToast;

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
 * @时间 2017/8/2
 */
public class RegisterPresenter extends BasePresenter<RegisterView> {


    public void register(Intent intent, String about) {
        RequestModel.getInstance().register(intent,about)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideProgress();
                    }
                })
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(@NonNull IntenetReposeBean data) {
                        mView.RegistSuccess(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        CustomToast.makeText(MyApplication.getContext(), e.getMessage(), R.drawable.ic_toast_warming).show();
                        mView.errorShake(0, 2, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void login(final String account, String password) {
        RequestModel.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        addSubscrebe(disposable);
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(@NonNull User user) {
                        mView.hideProgress();
                        mView.loginSuccess(user);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.errorShake(0, 2, e.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void getLocation(double latitude, double altitude) {
        RequestModel.getInstance().updateLocation(latitude, altitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        addSubscrebe(disposable);
                    }
                })
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(@NonNull IntenetReposeBean o) {
                        mView.updateLocation(o);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
