package com.supersweet.luck.mvp.present;


import android.net.Uri;

import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.AvaterView;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.utils.ToastUtil;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.AppData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2020/7/14
 */
public class AvaterPresenter extends BasePresenter<AvaterView> {

    public void upHead(String localPath) {
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
                        mView.success(upHeadBean.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShortToast("Head portrait up turn failed");

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
