package com.supersweet.luck.mvp.present;

import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.InterestMeView;
import com.supersweet.luck.mvp.view.MutualMutchView;
import com.supersweet.luck.widget.AppData;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MutualMutchPresenter extends BasePresenter<MutualMutchView> {

    public void lookMutualMatch(int connectionUserId/*,  List<MultualMatchBean> multualMatchBean*/) {
        RequestModel.getInstance().lookMutualMatch(connectionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IntenetReposeBean o) {
                        mView.MultualMatchSuccess(o, connectionUserId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.MultualMatchFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void getMonthPayMatch(String userId) {
        RequestModel.getInstance()
                .getMonthPayMatch(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<IntenetReposeBean>() {
                    @Override
                    public void accept(IntenetReposeBean intenetReposeBean) throws Exception {
                        mView.checkIsMonthPay(intenetReposeBean);
                    }
                });
    }

}
