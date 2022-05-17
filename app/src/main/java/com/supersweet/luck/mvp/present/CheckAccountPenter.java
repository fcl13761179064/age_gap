package com.supersweet.luck.mvp.present;

import android.text.TextUtils;

import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.CheckAccountView;
import com.supersweet.luck.utils.CheckRulerUtils;
import com.supersweet.luck.utils.PregnancyUtil;
import com.supersweet.luck.widget.CustomToast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public class CheckAccountPenter extends BasePresenter<CheckAccountView> {

    public void checkAccount() {
        String userName = mView.getUserName();
        String emal = mView.getEmal();
        String password = mView.getPassword();
        Boolean is_choose = mView.getCheckBox();

        if (TextUtils.isEmpty(userName)) {
            CustomToast.makeText(MyApplication.getContext(), "Username is required", R.drawable.ic_toast_warming).show();
            mView.errorShake(1, 2, "");
            return;
        } else if (userName.length() < 6) {
            CustomToast.makeText(MyApplication.getContext(), "Userame must be at least 6 characters", R.drawable.ic_toast_warming).show();
            mView.errorShake(1, 2, "");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            CustomToast.makeText(MyApplication.getContext(), "The Password is required", R.drawable.ic_toast_warming).show();
            mView.errorShake(2, 2, "");
            return;
        }

        if (TextUtils.isEmpty(emal)) {
            CustomToast.makeText(MyApplication.getContext(), "Email is required", R.drawable.ic_toast_warming).show();
            mView.errorShake(2, 2, "");
            return;
        } else if (!PregnancyUtil.checkEmail(emal)) {
            CustomToast.makeText(MyApplication.getContext(), "The format of email address is incorrect", R.drawable.ic_toast_warming).show();
            mView.errorShake(2, 2, "");
            return;
        }

        if (!is_choose) {
            CustomToast.makeText(MyApplication.getContext(), "Service must be agreement", R.drawable.ic_toast_warming).show();
            mView.errorShake(4, 2, "");
            return;
        }
        checkAccount(userName,emal);

    }


    private void checkAccount(String user_name, String email) {
        RequestModel.getInstance().checkAccount(user_name,email)
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
                .subscribe(new RxjavaObserver<Boolean>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void _onNext(Boolean data) {
                        mView.RegistSuccess(data);
                    }

                    @Override
                    public void _onError(String code, String msg) {
                        CustomToast.makeText(MyApplication.getContext(), msg, R.drawable.ic_toast_warming).show();
                        mView.errorShake(0, 2, code);

                    }
                });
    }
}
