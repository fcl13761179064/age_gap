package com.supersweet.luck.mvp.present;

import android.content.Context;

import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.UpImgBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.FeedBookView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FeedBookPresenter extends BasePresenter<FeedBookView> {

    public void submitFeedbook(String reason, String detail, String imgUrl) {
        RequestModel.getInstance().submitFeedbook(reason, detail, imgUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);

                    }

                    @Override
                    public void onNext(@NonNull Object value) {
                        mView.hideProgress();
                        mView.success(value);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.errorShake(e.getMessage().toString());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
