package com.supersweet.luck.mvp.present;

import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.BlockMemberbean;
import com.supersweet.luck.bean.PayCordBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.BlockMembersView;
import com.supersweet.luck.mvp.view.PayRecordView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PayRecordPresenter extends BasePresenter<PayRecordView> {

    //页码
    private int pageNum = 1;
    //拉取数量
    private static int maxNum = 20;

    /**
     * 加载下一页
     */
    public void loadNextPage() {
        pageNum++;
        getPayRecordInfo(pageNum, maxNum);
    }

    /**
     * 加载第一页
     */
    public void loadFistPage() {
        pageNum = 1;
        getPayRecordInfo(pageNum, maxNum);
    }

    public void getPayRecordInfo(int currentPage, int maxNum) {
        RequestModel.getInstance().getPayRecord(currentPage, maxNum)
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
        }).subscribe(new RxjavaObserver<PayCordBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                addSubscrebe(d);
            }

            @Override
            public void _onNext(PayCordBean data) {
                mView.getPayRecordInfoSuccess(data);
            }

            @Override
            public void _onError(String code, String msg) {
                mView.errorShake(msg);

            }
        });
    }

    public void deletePayReCord(int Id) {
        RequestModel.getInstance().deletePayReCord(Id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
