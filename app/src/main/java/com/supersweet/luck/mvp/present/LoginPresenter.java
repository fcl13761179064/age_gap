package com.supersweet.luck.mvp.present;

import android.text.TextUtils;

import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.User;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.LoginView;
import com.supersweet.luck.utils.PregnancyUtil;
import com.supersweet.luck.widget.CustomToast;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    public void login() {
        String account = mView.getAccount();
        String password = mView.getPassword();

        if (TextUtils.isEmpty(account)) {
            CustomToast.makeText(MyApplication.getContext(), "Username is required.", R.drawable.ic_toast_warming).show();
            mView.errorShake(1, 2, "");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            CustomToast.makeText(MyApplication.getContext(), "Password  is required.", R.drawable.ic_toast_warming).show();
            mView.errorShake(2, 2, "");
            return;
        }

        login(account, password);
    }

    public void getLocation(double latitude, double altitude) {
        RequestModel.getInstance().updateLocation(latitude, altitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(@NonNull IntenetReposeBean o) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void login(final String account, String password) {
        RequestModel.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
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
}
