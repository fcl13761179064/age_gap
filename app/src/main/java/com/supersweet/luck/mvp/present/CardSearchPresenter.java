package com.supersweet.luck.mvp.present;

import android.text.TextUtils;

import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.LocationBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.dialog.ReportUserDialog;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.CardSearchView;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CardSearchPresenter extends BasePresenter<CardSearchView> {

    public void card_search(String city, int currentPage, String gender, String isVerify, String minAge, String maxAge) {
        RequestModel.getInstance().cardsearch(city, currentPage, gender, isVerify, minAge, maxAge, "0")
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
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxjavaObserver<List<SeachPeopleBean>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void _onNext(List<SeachPeopleBean> data) {
                        mView.card_serch_success(data);
                    }

                    @Override
                    public void _onError(String code, String msg) {
                        CustomToast.makeText(MyApplication.getContext(), msg, R.drawable.ic_toast_warming).show();
                        mView.errorShake(msg);
                    }
                });
    }



    public void like(int userId) {
        RequestModel.getInstance().like(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                })
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);

                    }

                    @Override
                    public void onNext(@NonNull IntenetReposeBean s) {
                        mView.likeSuccess(s.getMsg(), userId);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.errorShake(e.getMessage());
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
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
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
                        mView.errorShake(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    public void getCurrentLocation(List<AllCountryBean.Country> countrys) {
        RequestModel.getInstance().getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                }).subscribe(new Observer<LocationBean>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                addSubscrebe(d);
            }

            @Override
            public void onNext(@NonNull LocationBean locationBean) {
                String country = locationBean.getCountry();
                String city = locationBean.getRegionName();
                for (int x = 0; x < countrys.size(); x++) {
                    String configValue = countrys.get(x).getConfigValue();
                    String countrycode = countrys.get(x).getConfigCode();
                    if (configValue != null && TextUtils.equals(country, configValue)) {
                        mView.getCurrentLocationSuccess(countrycode, city);
                        break;
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void PlanSearchInfo(int currentPage) {
        RequestModel.getInstance().getMyinfo().flatMap(new Function<MyInfoBean, Observable<BaseResult<List<SeachPeopleBean>>>>() {
            @Override
            public Observable<BaseResult<List<SeachPeopleBean>>> apply(@NonNull MyInfoBean myInfoBean) throws Exception {
                if ("1".equalsIgnoreCase(myInfoBean.getUser().getSex())) {
                    AppData.search_sex = "2";
                } else {
                    AppData.search_sex = "1";
                }
                AppData.MyInfoBean = myInfoBean;
                AppData.city = myInfoBean.getUser().getCity();
                return RequestModel.getInstance().cardsearch(myInfoBean.getUser().getCity(), currentPage, AppData.search_sex, "-1", "18", "88", "0");
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                mView.showProgress("Loading...");
            }
        }).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                mView.hideProgress();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxjavaObserver<List<SeachPeopleBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void _onNext(List<SeachPeopleBean> data) {
                        mView.hideProgress();
                        mView.card_serch_success(data);
                    }

                    @Override
                    public void _onError(String code, String msg) {
                        mView.hideProgress();
                        CustomToast.makeText(MyApplication.getContext(), msg, R.drawable.ic_toast_warming).show();
                        mView.errorShake(msg);
                    }
                });
    }


    public void uploadHeader(String localPath) {
        RequestModel.getInstance().uploadHeader(localPath)
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
                .subscribe(new Observer<UpHeadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(UpHeadBean upHeadBean) {
                        mView.UPHeadSuccess(upHeadBean,localPath);
                    }

                    @Override
                    public void onError(Throwable e) {
                        CustomToast.makeText(MyApplication.getContext(), "Head portrait up turn failed", R.drawable.ic_toast_warming).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void editHead(String img_path) {
        RequestModel.getInstance().updateHead(img_path)
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
                        mView.updateHeadsuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        CustomToast.makeText(MyApplication.getContext(), "Head portrait up turn failed", R.drawable.ic_toast_warming).show();
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getOtherUserInfo(String userId) {
        RequestModel.getInstance().getOtherUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxjavaObserver<OtherUserInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void _onNext(OtherUserInfoBean data) {
                        mView.getOtherUserInfoSuccess(data);
                    }

                    @Override
                    public void _onError(String code, String msg) {

                    }
                });
    }
    public void BuyHighLightCoin() {
        RequestModel.getInstance().BuyHighLightCoin()
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
                    public void onNext(IntenetReposeBean o) {
                        mView.BuyHighCoinSuccess( o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.BuyError(e.getMessage().toString());
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
                        mView.errorShake(e.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
