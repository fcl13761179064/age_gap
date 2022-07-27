package com.supersweet.luck.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.FavoriteAdapter;
import com.supersweet.luck.adapter.LoveMeAdapter;
import com.supersweet.luck.base.BaseMvpFragment;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.mvp.present.MyLovePresenter;
import com.supersweet.luck.mvp.view.MyLoveView;
import com.supersweet.luck.rxbus.RxBus;
import com.supersweet.luck.ui.BuyCoinPageActivity;
import com.supersweet.luck.ui.FavoriteDetailActivity;
import com.supersweet.luck.ui.InterestMeActivity;
import com.supersweet.luck.utils.FastClickUtils;
import com.supersweet.luck.widget.CustomToast;

import java.util.List;

import butterknife.BindView;


/**
 * Created by fanchunlei on 2020/10/1.
 * Blog:http://blog.csdn.net/student9128
 * Describe：the first fragment
 */

public class FragmentThree extends BaseMvpFragment<MyLoveView, MyLovePresenter> implements MyLoveView {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loveme_recycleview)
    RecyclerView loveme_recycleview;
    @BindView(R.id.more)
    TextView more;

    private LoveMeAdapter loveMeAdapter;
    private FavoriteAdapter favoritesAdapter;
    private FavoritesBean meloveData;
    private FavoritesBean lovemeData;
    private FavoritesBean.Love love;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_love;
    }

    /**
     * 是否创建
     */
    protected boolean isCreate = false;
    private String is_user_opertate;

    @Override
    protected void initView(View view) {
        isCreate = true;
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        favoritesAdapter = new FavoriteAdapter();
        recyclerview.setAdapter(favoritesAdapter);
        favoritesAdapter.bindToRecyclerView(recyclerview);
        favoritesAdapter.setEmptyView(R.layout.empty_big_page);
        favoritesAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        loveme_recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), loveme_recycleview.HORIZONTAL, false));
        loveMeAdapter = new LoveMeAdapter();
        loveme_recycleview.setAdapter(loveMeAdapter);
        loveMeAdapter.bindToRecyclerView(loveme_recycleview);
        loveMeAdapter.setEmptyView(R.layout.empty_min_page);
        loveMeAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }


    @Override
    protected void initListener() {

        favoritesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    if (FastClickUtils.isDoubleClick()) {
                        return;
                    }
                    FavoritesBean.Love data = (FavoritesBean.Love) adapter.getData().get(position);
                    Intent intent = new Intent(getActivity(), FavoriteDetailActivity.class);
                    intent.putExtra("UserId", data.getUserId());
                    startActivityForResult(intent, 1001);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    favoritesAdapter.getData().clear();
                    loveMeAdapter.getData().clear();
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

        loveMeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                List<FavoritesBean.Love> list = adapter.getData();
                love = list.get(position);
            /*    new EveryMonthPayDialog(getActivity()).setOnSureClick(new EveryMonthPayDialog.OnSureClick(){

                    @Override
                    public void click(@Nullable Dialog dialog) {

                    }
                });*/
              /*  if (-1 == love.getInterestMeFreeFlag()) {
                    HighingConsumeCoinDialog
                            .newInstance(new HighingConsumeCoinDialog.Callback() {
                                @Override
                                public void onDone(HighingConsumeCoinDialog dialog) {
                                    dialog.dismissAllowingStateLoss();
                                    mPresenter.lookOtherInterest(love.getUserId());
                                }

                                @Override
                                public void onCancel(HighingConsumeCoinDialog dialog) {
                                    dialog.dismissAllowingStateLoss();
                                }
                            })
                             .setContent(love.getInterestMeUseCoin()+"","")
                            .show(getFragmentManager(), "dialog");

                } else {
                    Intent intent = new Intent(getActivity(), FavoriteDetailActivity.class);
                    intent.putExtra("UserId", love.getUserId());
                    startActivityForResult(intent, 1001);
                }*/

            }
        });

        RxBus.getDefault().subscribe(this, "user_operation_rxbus", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                is_user_opertate = s;
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), InterestMeActivity.class);
                startActivityForResult(intent,1001);
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1001 == requestCode) {
            if (data != null && data.getStringExtra("reesulteData") != null) {
                is_user_opertate = "success";
                onResumeAdapter();
            } else {
                is_user_opertate = "fail";
            }
        }
    }

    @Override
    protected void initData() {
        RxBus.getDefault().subscribe(this, "lovemedata", new RxBus.Callback<List<FavoritesBean.Love>>() {
            @Override
            public void onEvent(List<FavoritesBean.Love> s) {
                if (loveMeAdapter != null && loveMeAdapter.getData() != null && loveMeAdapter.getData().size() > 0) {
                    loveMeAdapter.getData().clear();
                    loveMeAdapter.addData(s);
                    loveMeAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if ("success".equals(is_user_opertate)) {
            onResumeAdapter();
        }
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        if (isFirstLoad == true) {
            is_user_opertate = "success";
        } else {
            is_user_opertate = "fail";
        }

    }


    public void onResumeAdapter() {
        //相当于Fragment的onResume，为true时，Fragment已经可见
        if (mPresenter != null) {
            if (favoritesAdapter != null && favoritesAdapter.getData().size() > 0) {
                favoritesAdapter.getData().clear();
            }
            if (loveMeAdapter != null && loveMeAdapter.getData().size() > 0) {
                loveMeAdapter.getData().clear();
            }
            mPresenter.loadFistPage();
        }
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void FavoritesSuccess(FavoritesBean meloveData, FavoritesBean lovemeData) {
        this.meloveData = meloveData;
        this.lovemeData = lovemeData;
        loveMeAdapter.addData(lovemeData.getList());
        favoritesAdapter.addData(meloveData.getList());
        loadDataFinish();
        if (meloveData.getList().size() == 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
        }

    }

    @Override
    public void IntersetMeSuccess(IntenetReposeBean data, int userId) {
        if ("0".equals(data.getCode()) && "success".equalsIgnoreCase(data.getMsg())) {
            love.setInterestMeFreeFlag(1);
            Intent intent = new Intent(getActivity(), FavoriteDetailActivity.class);
            intent.putExtra("UserId", userId);
            startActivityForResult(intent,1001);
        } else {
            if ("Your Are Balance is insufficient.".equalsIgnoreCase(data.getMsg())) {//你的余额不足
                love.setInterestMeFreeFlag(-1);
                Intent intent = new Intent(getActivity(), BuyCoinPageActivity.class);
                startActivityForResult(intent,1001);
            } else {
                CustomToast.makeText(getActivity(), data.getMsg(), R.drawable.ic_toast_warming).show();
            }
        }
    }

    @Override
    public void IntersetMeFail(String message) {
        CustomToast.makeText(getActivity(), message, R.drawable.ic_toast_warming).show();
    }


    public void loadDataFinish() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    protected MyLovePresenter initPresenter() {
        return new MyLovePresenter();
    }


}
