package com.supersweet.luck.mvp.present;

import com.supersweet.luck.addressselector.AddressSelector;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.CompanyBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.dialog.DelectAccountDialog;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.ProfileView;
import com.supersweet.luck.mvp.view.SettingView;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.AppData;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class SettingPresenter extends BasePresenter<SettingView> {


    public void getMyinfo() {
        RequestModel.getInstance().getMyinfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(MyInfoBean s) {
                        mView.MyInfo_success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void logout() {
        RequestModel.getInstance().logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                })
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(IntenetReposeBean s) {
                        mView.logoutsuccess(s);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.errorShake( e.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void DelectAccount(String password, String leaveReason, DelectAccountDialog delectAccountDialog) {
        RequestModel.getInstance().delectAccount(password, leaveReason)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                })
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(IntenetReposeBean s) {
                        if ("0".equals(s.getCode())) {
                            mView.logoutsuccess(s);
                            delectAccountDialog.dismiss();
                        } else {
                            mView.errorShake(s.getMsg());
                        }
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.errorShake(e.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void HideProfile(int isshow) {
        RequestModel.getInstance().HideProfile(isshow)
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.errorShake(e.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

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
}
