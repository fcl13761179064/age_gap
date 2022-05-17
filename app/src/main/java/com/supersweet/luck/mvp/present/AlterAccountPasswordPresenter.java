package com.supersweet.luck.mvp.present;

import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.AbountView;
import com.supersweet.luck.mvp.view.AlterAccountPasswordView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public  class AlterAccountPasswordPresenter extends BasePresenter<AlterAccountPasswordView> {

    public void alterAccountPassword(String oldPassword,String newPassword) {
        RequestModel.getInstance().UpdataAccountPassword(oldPassword,newPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                })
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(IntenetReposeBean s) {
                        mView.hideProgress();
                        mView.AlterSuccess(s.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.errorShake(e.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void UpdataYouXiang(String email,String Password) {
        RequestModel.getInstance().UpdataYouXiang(email,Password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                })
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(IntenetReposeBean s) {
                        mView.hideProgress();
                        mView.AlterSuccess(s.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.errorShake(e.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
