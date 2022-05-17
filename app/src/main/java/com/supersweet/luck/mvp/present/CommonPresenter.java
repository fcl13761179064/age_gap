package com.supersweet.luck.mvp.present;

import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.User;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.CommonView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CommonPresenter extends BasePresenter<CommonView> {
/*    public void height() {
        RequestModel.getInstance().height()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("loading...");
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(User s) {
                        mView.hideProgress();
                        mView.heightSuccess(s);
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
    }*/
  /*  public void body_type(final String account, String password) {
        RequestModel.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("loading...");
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(User s) {
                        mView.hideProgress();
                        mView.loginSuccess(s);
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
    public void hair(final String account, String password) {
        RequestModel.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("loading...");
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(User s) {
                        mView.hideProgress();
                        mView.loginSuccess(s);
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
    public void relationship(final String account, String password) {
        RequestModel.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("loading...");
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(User s) {
                        mView.hideProgress();
                        mView.loginSuccess(s);
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
    public void edutaion(final String account, String password) {
        RequestModel.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("loading...");
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(User s) {
                        mView.hideProgress();
                        mView.loginSuccess(s);
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
    public void ethnicity(final String account, String password) {
        RequestModel.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("loading...");
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(User s) {
                        mView.hideProgress();
                        mView.loginSuccess(s);
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
    public void drinking(final String account, String password) {
        RequestModel.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("loading...");
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(User s) {
                        mView.hideProgress();
                        mView.loginSuccess(s);
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

    public void smoking(final String account, String password) {
        RequestModel.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("loading...");
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(User s) {
                        mView.hideProgress();
                        mView.loginSuccess(s);
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

    public void children(final String account, String password) {
        RequestModel.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("loading...");
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(User s) {
                        mView.hideProgress();
                        mView.loginSuccess(s);
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
    }*/

}
