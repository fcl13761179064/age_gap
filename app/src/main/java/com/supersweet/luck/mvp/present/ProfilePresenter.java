package com.supersweet.luck.mvp.present;

import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.MyInfoView;
import com.supersweet.luck.mvp.view.ProfileView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter<ProfileView> {



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


    public void editMyinfo(int position, String myinfo) {
        RequestModel.getInstance().editProfile(position,myinfo)
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
                        mView.editMyInfoProgress(s);
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
