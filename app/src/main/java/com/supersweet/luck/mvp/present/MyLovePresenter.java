package com.supersweet.luck.mvp.present;

import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.MyLoveView;
import com.supersweet.luck.widget.AppData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MyLovePresenter extends BasePresenter<MyLoveView> {

    //页码
    private int pageNum = 1;
    //拉取数量
    private static int maxNum = 20;
    private static int maxNumTwo = 7;

    /**
     * 加载下一页
     */
    public void loadNextPage() {
        pageNum++;
        favorites(false);
    }

    /**
     * 加载第一页
     */
    public void loadFistPage() {
        pageNum = 1;
        favorites(true);
    }


    public void favorites(boolean isRefresh) {
        Observable<BaseResult<FavoritesBean>> loveMeList = RequestModel.getInstance().getLoveMeList(pageNum, maxNum);
        RequestModel.getInstance().getFavoritesList(pageNum, maxNum)
                .map(new Function<BaseResult<FavoritesBean>, FavoritesBean>() {
                    @Override
                    public FavoritesBean apply(@NonNull BaseResult<FavoritesBean> favoritesBeanBaseResult) throws Exception {
                        return favoritesBeanBaseResult.data;
                    }
                }).zipWith(loveMeList, new BiFunction<FavoritesBean, BaseResult<FavoritesBean>, Object[]>() {
                    @NonNull
                    @Override
                    public Object[] apply(@NonNull FavoritesBean favoritesBean, @NonNull BaseResult<FavoritesBean> favoritesBeanBaseResult) throws Exception {
                        return new Object[]{favoritesBean, favoritesBeanBaseResult.data};
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object[]>() {
                    @Override
                    public void accept(Object[] data) throws Exception {
                        mView.FavoritesSuccess((FavoritesBean) data[0], (FavoritesBean) data[1],isRefresh);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.errorShake(1, 2, throwable.getMessage());
                    }
                });
    }

/*
    public void lookOtherInterest(int connectionUserId) {
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
                        mView.IntersetMeSuccess(o, connectionUserId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.IntersetMeFail(e.getMessage());
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
