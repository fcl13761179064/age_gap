package com.supersweet.luck.mvp.present;

import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.dialog.ReportUserDialog;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.AbountView;
import com.supersweet.luck.mvp.view.ChatView;
import com.supersweet.luck.widget.AppData;
import com.tencent.imsdk.v2.V2TIMMessage;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public  class ChatPresenter extends BasePresenter<ChatView> {

    public void editMyinfo(String abount) {
        RequestModel.getInstance().editMyinfo(abount)
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
                        mView.Success(s);
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

    public void SendInfoConsumeCoin(String userId, String messageInfo) {
        RequestModel.getInstance().SendInfoConsumeCoin(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideProgress();
                    }
                })
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);

                    }

                    @Override
                    public void onNext(IntenetReposeBean intenetReposeBean) {
                        mView.ConSumeCoinSuccess(intenetReposeBean,messageInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.BuyCoinFail(e.getMessage().toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void ReportUser(int userid, String reason, String detail, String imgUrl, ReportUserDialog reportUserDialog) {
        RequestModel.getInstance().ReportUser(userid, reason, detail, imgUrl)
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
                        mView.ReportUserSuccess(value, reportUserDialog);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.errorShakes(e.getMessage().toString(), reportUserDialog);
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void blockUser(int userid) {
        RequestModel.getInstance().blockUser(userid)
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
                        mView.BlockUserSuccess(value);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.errorShakess(e.getMessage().toString());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void checkMyIsMonth() {
        if (AppData.MyInfoBean!=null &&AppData.MyInfoBean.getUser()!=null){
            int userId = AppData.MyInfoBean.getUser().getUserId();
            RequestModel.getInstance()
                    .getMonthInsertInMe(userId)
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
}
