package com.supersweet.luck.mvp.present;

import android.content.Context;

import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.SelfAlbumBean;
import com.supersweet.luck.bean.UpImgBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.fragment.MyPictureFragment;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.MyPictureView;
import com.supersweet.luck.widget.AppData;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MyPicturePresenter extends BasePresenter<MyPictureView> {

    public void uploadImg(Context context, String localPath) {
        RequestModel.getInstance().uploadAlbum(context, localPath)
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
                .subscribe(new RxjavaObserver<UpImgBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void _onNext(UpImgBean data) {
                        mView.success(data);
                    }

                    @Override
                    public void _onError(String code, String msg) {
                        mView.errorShake(1, 3, msg);
                    }

                });
    }


    public void getSelfAlbum() {
        RequestModel.getInstance().getSelfAlbum()
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
                .subscribe(new RxjavaObserver<List<SelfAlbumBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void _onNext(List<SelfAlbumBean> data) {
                        mView.SelfAbulmsuccess(data);
                    }

                    @Override
                    public void _onError(String code, String msg) {
                        mView.errorShake(1, 3, msg);
                    }

                });
    }
}
