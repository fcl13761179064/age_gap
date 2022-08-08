package com.supersweet.luck.mvp.present;

import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.AbountView;
import com.supersweet.luck.mvp.view.InterestMeView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class InterestMePresenter extends BasePresenter<InterestMeView> {

    //页码
    private int pageNum = 1;
    //拉取数量
    private static int maxNum = 20;
    /**
     * 加载下一页
     */
    public void loadNextPage() {
        pageNum++;
        loveMeData();
    }

    /**
     * 加载第一页
     */
    public void loadFistPage() {
        pageNum = 1;
        loveMeData();
    }


    public void loveMeData() {
        RequestModel.getInstance()
                .getLoveMeList(pageNum, maxNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxjavaObserver<FavoritesBean>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);

                    }

                    @Override
                    public void _onNext(FavoritesBean data) {
                        mView.setLoveMeData(data);
                    }

                    @Override
                    public void _onError(String code, String msg) {
                        mView.setLoveMeDataFail(msg);

                    }
                });
    }
/*

    public void lookOtherInterest(int connectionUserId, List< FavoritesBean.Love> data) {
        RequestModel.getInstance().lookOtherInterest(connectionUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(IntenetReposeBean o) {
                        mView.IntersetMeSuccess(o, connectionUserId,data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.IntersetMeFail(e.getMessage(),connectionUserId);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

*/

    public void getMonthInsertInMe(String otherUserid) {
        RequestModel.getInstance()
                .getMonthInsertInMe(otherUserid)
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
