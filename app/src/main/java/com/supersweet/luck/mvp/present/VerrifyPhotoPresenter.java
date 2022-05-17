package com.supersweet.luck.mvp.present;

import android.content.Context;

import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.PhotoBean;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.bean.UpImgBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.VerrifyPhotoView;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.CustomToast;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VerrifyPhotoPresenter extends BasePresenter<VerrifyPhotoView> {

    //页码
    private int pageNum = 1;
    //拉取数量
    private static int maxNum = 20;
    private static int maxNumTwo = 7;

    /**
     * 加载下一页
     */
    public void loadNextPage(String city, String gender, String isVerify, String minAge, String maxAge) {
        pageNum++;
        card_search(city, pageNum, gender, isVerify, minAge, maxAge, true);
    }

    /**
     * 加载第一页
     */
    public void loadFistPage(String city, String gender, String isVerify, String minAge, String maxAge) {
        pageNum = 1;
        card_search(city, pageNum, gender, isVerify, minAge, maxAge, false);
    }

    public void card_search(String city, int currentPage, String gender, String isVerify, String minAge, String maxAge, boolean isMore) {
        RequestModel.getInstance().cardsearch(city, currentPage, gender, isVerify, minAge, maxAge, "0")
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
                })
                .subscribe(new RxjavaObserver<List<SeachPeopleBean>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void _onNext(List<SeachPeopleBean> data) {
                        if (isMore) {
                            mView.card_serch_more_success(data);
                        } else {
                            mView.card_serch_success(data);
                        }

                    }

                    @Override
                    public void _onError(String code, String msg) {
                        CustomToast.makeText(MyApplication.getContext(), msg, R.drawable.ic_toast_warming).show();
                        mView.errorShake(0, 2, msg);

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
                ToastUtils.showShortToast("Head portrait up turn failed");

            }

            @Override
            public void onComplete() {

            }
        });

    }

}
