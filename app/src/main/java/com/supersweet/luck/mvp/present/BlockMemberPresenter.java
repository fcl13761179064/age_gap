package com.supersweet.luck.mvp.present;

import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BasePresenter;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.BlockMemberbean;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.data.net.RxjavaObserver;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.mvp.view.BlockMembersView;
import com.supersweet.luck.mvp.view.VerrifyPhotoView;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.CustomToast;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BlockMemberPresenter extends BasePresenter<BlockMembersView> {

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
        getBlockUserNum(pageNum, maxNum);
    }

    /**
     * 加载第一页
     */
    public void loadFistPage() {
        pageNum = 1;
        getBlockUserNum(pageNum, maxNum);
    }

    public void getBlockUserNum(int currentPage, int maxNum) {
        RequestModel.getInstance().getBlockUserNum(currentPage, maxNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxjavaObserver<BlockMemberbean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addSubscrebe(d);

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void _onNext(BlockMemberbean data) {
                        mView.BlockMemberSuccess(data);
                    }

                    @Override
                    public void _onError(String code, String msg) {
                        mView.errorShake(msg);

                    }
                });
    }

    public void  unBlockUser (int targetUserId) {
        RequestModel.getInstance().unBlockUser(targetUserId)
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
}
