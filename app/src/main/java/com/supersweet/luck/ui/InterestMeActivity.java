package com.supersweet.luck.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.InterestInMeAdapter;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.dialog.HighingConsumeCoinDialog;
import com.supersweet.luck.mvp.present.InterestMePresenter;
import com.supersweet.luck.mvp.view.InterestMeView;
import com.supersweet.luck.myphoto.MutailMatchDecoration;
import com.supersweet.luck.rxbus.RxBus;
import com.supersweet.luck.utils.FastClickUtils;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.CustomToast;

import java.util.List;

import butterknife.BindView;

public class InterestMeActivity extends BaseMvpActivity<InterestMeView, InterestMePresenter> implements InterestMeView {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.back)
    ImageView back;
    private InterestInMeAdapter interestInMeAdapter;
    private String sex;
    private String mResulteData;
    private FavoritesBean.Love love;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_interestme;
    }


    @Override
    protected void initView() {
        mPresenter.loveMeData();
        sex = SharePreferenceUtils.getString(this, Constance.SP_SEX, "1");
        //列数为两列
        //列数为两列
        int spanCount = 2;
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerview.addItemDecoration(new MutailMatchDecoration(this));
        recyclerview.setLayoutManager(mLayoutManager);
        interestInMeAdapter = new InterestInMeAdapter();
        recyclerview.setAdapter(interestInMeAdapter);
        interestInMeAdapter.bindToRecyclerView(recyclerview);
        interestInMeAdapter.setEmptyView(R.layout.empty_big_page);
        interestInMeAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("reesulteData", mResulteData);
                setResult(1001, intent);
                finish();
            }
        });
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    interestInMeAdapter.getData().clear();
                    mPresenter.loadFistPage();
                }
                refreshLayout.setEnableLoadMore(true);

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    mPresenter.loadNextPage();
                }
            }
        });

        interestInMeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                love = (FavoritesBean.Love) adapter.getData().get(position);
                if (-1 == love.getInterestMeFreeFlag()) {
                    HighingConsumeCoinDialog
                            .newInstance(new HighingConsumeCoinDialog.Callback() {
                                @Override
                                public void onDone(HighingConsumeCoinDialog dialog) {
                                    mPresenter.lookOtherInterest(love.getUserId(), adapter.getData());
                                    dialog.dismissAllowingStateLoss();
                                }

                                @Override
                                public void onCancel(HighingConsumeCoinDialog dialog) {
                                    dialog.dismissAllowingStateLoss();
                                }
                            })
                            .setContent(love.getInterestMeUseCoin() + "",  "")
                            .show(getSupportFragmentManager(), "dialog");

                } else {
                    int user_Id = love.getUserId();
                    Intent intent = new Intent(InterestMeActivity.this, FavoriteDetailActivity.class);
                    intent.putExtra("UserId", user_Id);
                    startActivityForResult(intent,1001);
                }
            }
        });

        refreshLayout.autoRefresh();
    }

    @Override
    protected InterestMePresenter initPresenter() {
        return new InterestMePresenter();
    }


    @Override
    public void setLoveMeData(FavoritesBean data) {
        if (data != null) {
            interestInMeAdapter.addData(data.getList());
            loadDataFinish();
            if (data.getList().size() == 0) {
                refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
            }
        }
    }

    @Override
    public void setLoveMeDataFail(String msg) {
        CustomToast.makeText(this, msg, R.drawable.ic_toast_warming).show();
    }

    @Override
    public void IntersetMeSuccess(IntenetReposeBean o, int connectionUserId, List<FavoritesBean.Love> data) {
        if ("0".equals(o.getCode()) && "success".equalsIgnoreCase(o.getMsg())) {
            love.setInterestMeFreeFlag(1);
            this.mResulteData = "success";
            RxBus.getDefault().post(data, "lovemedata");
            Intent intent = new Intent(InterestMeActivity.this, FavoriteDetailActivity.class);
            intent.putExtra("UserId", connectionUserId);
            startActivityForResult(intent,1001);
        } else {
            if ("Your Are Balance is insufficient.".equalsIgnoreCase(o.getMsg())) {//你的余额不足
                love.setInterestMeFreeFlag(-1);
                Intent intent = new Intent(this, BuyCoinPageActivity.class);
                startActivity(intent);
            } else {
                CustomToast.makeText(this, o.getMsg(), R.drawable.ic_toast_warming).show();
            }
        }

    }

    @Override
    public void IntersetMeFail(String message, int connectionUserId) {
        Intent intent = new Intent(InterestMeActivity.this, FavoriteDetailActivity.class);
        intent.putExtra("UserId", connectionUserId);
        startActivity(intent);
    }


    public void loadDataFinish() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1001){
            this.mResulteData = "success";
        }
    }

}
