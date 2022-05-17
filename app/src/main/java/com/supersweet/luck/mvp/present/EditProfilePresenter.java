package com.supersweet.luck.mvp.present;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.supersweet.luck.R;
import com.supersweet.luck.addressselector.AddressSelector;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.EditProfileView;
import com.supersweet.luck.widget.CustomToast;
import java.io.File;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class EditProfilePresenter extends BasePresenter<EditProfileView> {


    public void editSetting(int position, String myinfo) {
        RequestModel.getInstance().editSetting(position, myinfo)
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
                        mView.editMyInfoProgress(myinfo,position);
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


    public void btncity(String code, AddressSelector addressSelector) {
        RequestModel.getInstance().getCity(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
        }).subscribe(new Observer<CityBean>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                addSubscrebe(d);
            }

            @Override
            public void onNext(@NonNull CityBean cityBean) {
                mView.sheng(cityBean, addressSelector);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.errorShake(1, 2, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void btnStation(String station, AddressSelector addressSelector) {
        RequestModel.getInstance().getStation(station)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mView.showProgress("Loading...");
                    }
                })
                .subscribe(new Observer<StationBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscrebe(d);
                    }

                    @Override
                    public void onNext(StationBean s) {
                        mView.hideProgress();
                        mView.qu(s, addressSelector);
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


    public void uploadHeader(Context editProfileActivity, String localPath) {
        Luban.with(editProfileActivity)
                .load(localPath)
                .ignoreBy(80)   // 忽略不压缩图片的大小//传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        LogUtils.d("load_img", file.length() / 1024 + "kb");
                        RequestModel.getInstance().uploadHeader(file.getPath())
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
                                        mView.UPHeadSuccess(upHeadBean);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        CustomToast.makeText(MyApplication.getContext(), "Head portrait up turn failed", R.drawable.ic_toast_warming).show();
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        //TODO 压缩成功后调用，返回压缩后的图片文件
                    }

                    @Override
                    public void onError(Throwable e) {
                        CustomToast.makeText(editProfileActivity, "Head portrait up turn failed", R.drawable.ic_toast_warming).show();
                    }
                }).launch();    //启动压缩

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
                        mView.updateFuwuqisuccess(s);
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

}
