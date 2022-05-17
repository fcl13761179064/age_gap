package com.supersweet.luck.mvp.present;

import android.text.TextUtils;

import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.LoveMeBean;
import com.supersweet.luck.bean.User;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.LoginView;
import com.supersweet.luck.mvp.view.SplashView;
import com.supersweet.luck.widget.CustomToast;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public class SplashPresenter extends BasePresenter<SplashView> {

    public void location() {
        RequestModel.getInstance().getAllCountry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllCountryBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(AllCountryBean s) {
                        mView.LocationSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.error();
                    }

                    @Override
                    public void onComplete() {


                    }
                });
    }
}
