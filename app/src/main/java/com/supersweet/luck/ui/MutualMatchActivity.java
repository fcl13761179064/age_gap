package com.supersweet.luck.ui;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.MutualMatchDetailAdapter;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MultualMatchBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.dialog.HighingConsumeCoinDialog;
import com.supersweet.luck.dialog.MonthPayDialog;
import com.supersweet.luck.mvp.present.MutualMutchPresenter;
import com.supersweet.luck.mvp.view.MutualMutchView;
import com.supersweet.luck.myphoto.MutailMatchDecoration;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppBar;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;

import java.util.List;

import butterknife.BindView;

public class MutualMatchActivity extends BaseMvpActivity<MutualMutchView, MutualMutchPresenter> implements MutualMutchView {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.appBar)
    AppBar appBar;
    private MutualMatchDetailAdapter multualAdapter;
    private String sex;
    private List<MultualMatchBean> data;
    private MultualMatchBean multualMatchBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mutual_match;
    }

    @Override
    protected void initView() {
        data = (List<MultualMatchBean>) getIntent().getSerializableExtra("detail_data");
        appBar.setCenterText("Mutual Match");
        sex = SharePreferenceUtils.getString(this, Constance.SP_SEX, "1");
        //???????????????
        int spanCount = 2;
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(mLayoutManager);
        multualAdapter = new MutualMatchDetailAdapter();
        recyclerview.setAdapter(multualAdapter);
        multualAdapter.bindToRecyclerView(recyclerview);
        multualAdapter.setEmptyView(R.layout.empty_big_page);
        multualAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    @Override
    protected void initListener() {
        multualAdapter.addData(data);
        multualAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                multualMatchBean = data.get(position);
                 mPresenter.checkMyIsMonth();
            }
        });
    }




    @Override
    protected MutualMutchPresenter initPresenter() {
        return new MutualMutchPresenter();
    }

    @Override
    public void MultualMatchSuccess(IntenetReposeBean data, int userId) {

        if ("0".equals(data.getCode()) && "success".equalsIgnoreCase(data.getMsg())) {
            multualMatchBean.setMatchFreeFlag(1);
            Intent intent = new Intent(this, FavoriteDetailActivity.class);
            intent.putExtra("UserId", userId);
            startActivityForResult(intent,10001);
        } else {
            if ("Your Are Balance is insufficient.".equalsIgnoreCase(data.getMsg())) {//??????????????????
                multualMatchBean.setMatchFreeFlag(-1);
                Intent intent = new Intent(this, BuyCoinPageActivity.class);
                startActivityForResult(intent,10001);//10002????????????????????????
            } else {
                CustomToast.makeText(this, data.getMsg(), R.drawable.ic_toast_warming).show();
            }
        }

    }

    @Override
    public void MultualMatchFail(String message) {

    }

    public void onResumeAdapter() {
        //?????????Fragment???onResume??????true??????Fragment????????????
        if (mPresenter != null) {
            if (multualAdapter != null && multualAdapter.getData().size() > 0) {
                multualAdapter.getData().clear();
            }
            if (multualAdapter != null && multualAdapter.getData().size() > 0) {
                multualAdapter.getData().clear();
            }
            multualAdapter.addData(data);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001) {
            onResumeAdapter();
        }

    }


    @Override
    public void checkIsMonthPay(IntenetReposeBean data) {
        if (data != null) {
            if ("0".equals(data.getCode())) {
                int userId = multualMatchBean.getUserId();
                Intent intent = new Intent(MutualMatchActivity.this, FavoriteDetailActivity.class);
                intent.putExtra("UserId", userId);
                startActivityForResult(intent, 10001);
            } else {
                MonthPayDialog dialog = new MonthPayDialog(this);
                dialog.setOnSureClick(new MonthPayDialog.OnSureClick() {
                    @Override
                    public void click(Dialog dialog) {

                    }
                });
                dialog.show();
                dialog.setGravity(Gravity.CENTER);
            }
        }

    }

}
