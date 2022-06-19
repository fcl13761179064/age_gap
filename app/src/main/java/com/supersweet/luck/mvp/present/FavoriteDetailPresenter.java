package com.supersweet.luck.mvp.present;

import android.util.Log;
import android.view.LayoutInflater;

import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.ChatInfoBean;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.bean.PhotoBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.dialog.ReportUserDialog;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.FavoriteDetailView;
import com.supersweet.luck.widget.CustomToast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FavoriteDetailPresenter extends BasePresenter<FavoriteDetailView> {

    public void like(int userId) {
        RequestModel.getInstance().like(userId)
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
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);

                    }

                    @Override
                    public void onNext(@NonNull IntenetReposeBean s) {
                        mView.likeSuccess(s.getMsg());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.errorShakes(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void unlike(int userId) {
        RequestModel.getInstance().unlike(userId)
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
                    public void onNext(@NonNull IntenetReposeBean data) {
                        mView.unlikeSuccess(data.getMsg());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        CustomToast.makeText(MyApplication.getContext(), e.getMessage(), R.drawable.ic_toast_warming).show();
                        mView.errorShakes(e.getMessage());
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
                        mView.errorShake(e.getMessage().toString(), reportUserDialog);
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
                        mView.errorShakes(e.getMessage().toString());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getOtherUserInfo(String userId) {

        Observable<BaseResult<List<PhotoBean>>> baseResultObservable = RequestModel.getInstance().scanPhotos(userId);
        RequestModel.getInstance().getOtherUserInfo(userId)
                .map(new Function<BaseResult<OtherUserInfoBean>, OtherUserInfoBean>() {
                    @Override
                    public OtherUserInfoBean apply(@NonNull BaseResult<OtherUserInfoBean> otherUserInfoBeanBaseResult) throws Exception {
                        return otherUserInfoBeanBaseResult.data;
                    }
                })
                .zipWith(baseResultObservable, new BiFunction<OtherUserInfoBean, BaseResult<List<PhotoBean>>, Object[]>() {
                    @NonNull
                    @Override
                    public Object[] apply(@NonNull OtherUserInfoBean otherUserInfoBean, @NonNull BaseResult<List<PhotoBean>> listBaseResult) throws Exception {
                        return new Object[]{otherUserInfoBean, listBaseResult.data};
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        addSubscrebe(disposable);
                        mView.showProgress("Loading...");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideProgress();
                    }
                })
               .subscribe(new Consumer<Object[]>() {
                   @Override
                   public void accept(Object[] data) throws Exception {
                       mView.getOtherUserInfoSuccess((OtherUserInfoBean)data[0], (List<PhotoBean>) data[1]);
                   }
               }, new Consumer<Throwable>() {
                   @Override
                   public void accept(Throwable throwable) throws Exception {

                   }
               });

    }


    public void VideoConsumeCoin(String msgUserId,String callId) {
        Observable.interval(0,60, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e("gggg", "apply: "+aLong );
              /*  RequestModel.getInstance().VedioConSume(msgUserId,callId)
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
                                mView.errorShakes(e.getMessage().toString());
                                mView.hideProgress();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });*/

            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {

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
