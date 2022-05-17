package com.supersweet.luck.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.PayRecordAdapter;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.BlockMemberbean;
import com.supersweet.luck.bean.PayCordBean;
import com.supersweet.luck.mvp.present.PayRecordPresenter;
import com.supersweet.luck.mvp.view.PayRecordView;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppBar;
import com.supersweet.luck.widget.CustomToast;

import java.util.List;

import butterknife.BindView;


/**
 * Created by fanchunlei on 2020/10/1.
 * Blog:http://blog.csdn.net/student9128
 * Describe：the first fragment
 */

public class GooglePayRecordActivity extends BaseMvpActivity<PayRecordView, PayRecordPresenter> implements PayRecordView {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.appBar)
    AppBar appBar;
    private PayRecordAdapter blockMember;
    private String sex;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_record;
    }

    @Override
    protected void initView() {
        appBar.setCenterText("Record");
        sex = SharePreferenceUtils.getString(this, Constance.SP_SEX, "1");
        //列数为两列
        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        blockMember = new PayRecordAdapter() {
            @Override
            public void removePayRecord(int Id, PayCordBean.PayCord item) {
                mPresenter.deletePayReCord(Id);
                blockMember.getData().remove(item);
                notifyDataSetChanged();
            }

        };
        recyclerview.setAdapter(blockMember);
        blockMember.bindToRecyclerView(recyclerview);
        blockMember.setEmptyView(R.layout.block_member_empty_page);
        blockMember.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    @Override
    protected void initListener() {

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    blockMember.getData().clear();
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
        refreshLayout.autoRefresh();
    }


    @Override
    public void errorShake(String msg) {
        refreshLayout.finishLoadMore(true);
        refreshLayout.finishRefresh(true);
        CustomToast.makeText(MyApplication.getContext(), msg, R.drawable.ic_toast_warming).show();
    }

    @Override
    public void getPayRecordInfoSuccess(PayCordBean data) {
        if (data!=null && data.getList().size() == 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
        }
        blockMember.addData(data.getList());
        loadDataFinish();
    }



    public void loadDataFinish() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    protected PayRecordPresenter initPresenter() {
        return new PayRecordPresenter();
    }

}
