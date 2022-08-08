package com.supersweet.luck.mvp.present;

import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MultualMatchBean;
import com.supersweet.luck.bean.ChatInfoBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.MultualMatchView;
import com.supersweet.luck.widget.AppData;
import com.tencent.imsdk.v2.V2TIMConversation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MultualMatchPresenter extends BasePresenter<MultualMatchView> {

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
        MultualMatch();
    }

    /**
     * 加载第一页
     */
    public void loadFistPage() {
        pageNum = 1;
        MultualMatch();
    }


    public void MultualMatch() {
        RequestModel.getInstance().getMultualMatch(pageNum, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxjavaObserver<List<MultualMatchBean>>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void _onNext(List<MultualMatchBean> data) {
                        mView.MultualSuccess(data);

                    }

                    @Override
                    public void _onError(String code, String msg) {

                    }
                });
    }

    public void getEveryoneHead(List<V2TIMConversation> conversion) {
        Observable.fromIterable(conversion)
                .flatMap(new Function<V2TIMConversation, ObservableSource<BaseResult<OtherUserInfoBean>>>() {
                    @Override
                    public ObservableSource<BaseResult<OtherUserInfoBean>> apply(@NonNull V2TIMConversation v2TIMConversation) throws Exception {
                        return RequestModel.getInstance().getOtherUserInfo(v2TIMConversation.getUserID());
                    }

                }).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BaseResult<OtherUserInfoBean>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);

                    }

                    @Override
                    public void onSuccess(@NonNull List<BaseResult<OtherUserInfoBean>> baseResults) {
                        List<OtherUserInfoBean> arrayList = new ArrayList();
                        for (BaseResult<OtherUserInfoBean> s : baseResults) {
                            OtherUserInfoBean chatInfoBean = s.data;
                            arrayList.add(chatInfoBean);
                        }

                        mView.chatHeadSuccess(arrayList, conversion);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    public void deleteConnection(String connectionUserId) {
        RequestModel.getInstance().deleteConnection(connectionUserId)
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
