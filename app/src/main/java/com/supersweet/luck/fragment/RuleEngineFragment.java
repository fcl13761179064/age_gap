package com.supersweet.luck.fragment;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.RuleEnginePagerAdapter;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpFragment;
import com.supersweet.luck.bean.DeviceListBean;
import com.supersweet.luck.bean.RuleEngineBean;
import com.supersweet.luck.dialog.CustomSheet;
import com.supersweet.luck.mvp.present.RuleEnginePresenter;
import com.supersweet.luck.mvp.view.RuleEngineView;
import com.supersweet.luck.ui.MainActivity;
import com.supersweet.luck.utils.TempUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.supersweet.luck.widget.CustomToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * RuleEngine页面
 */
public class RuleEngineFragment extends BaseMvpFragment<RuleEngineView, RuleEnginePresenter> implements RuleEngineView {
    private final long mRoom_ID;
    @BindView(R.id.tl_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.float_btn)
    FloatingActionButton float_btn;
    @BindView(R.id.device_refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    private RuleEnginePagerAdapter mAdapter;

    public RuleEngineFragment(long mRoom_ID) {
        this.mRoom_ID = mRoom_ID;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ruleengine;
    }

    @Override
    protected void initView(View view) {
/*        mAdapter = new RuleEnginePagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(mAdapter);
        mRefreshLayout.setEnableLoadMore(false);
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.setTabTextColors(Color.parseColor("#282828"), ContextCompat.getColor(getContext(), R.color.colorAccent));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);*/
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    //mPresenter.loadData(mRoom_ID);
                }
            }
        });

        mRefreshLayout.autoRefresh();//自动刷新

        float_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomSheet show = new CustomSheet.Builder(getActivity())
                        .setText("本地联动（本地存储）", "云端联动（云端存储）")
                        .show(new CustomSheet.CallBack() {
                            @Override
                            public void callback(int index) {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("scopeId", mRoom_ID);

                                if (index == 0) {//选择了本地联动
                                    DeviceListBean.DevicesBean gateway = null;
                                    List<DeviceListBean.DevicesBean> devicesBean = MyApplication.getInstance().getDevicesBean();
                                    for (DeviceListBean.DevicesBean bean : devicesBean) {
                                        if (TempUtils.isDeviceGateway(bean)) {
                                            gateway = bean;
                                            break;
                                        }
                                    }
                                    if (gateway != null) {//存在网关
                                        if (TempUtils.isDeviceOnline(gateway)) {//网关在线
                                            intent.putExtra("targetGateway", gateway.getDeviceId());
                                        } else {
                                            CustomToast.makeText(getContext(), "请确保网关在线", R.drawable.ic_toast_warming).show();
                                            return;
                                        }
                                    } else {
                                        CustomToast.makeText(getContext(), "请先添加网关", R.drawable.ic_toast_warming).show();
                                        return;
                                    }
                                }

                                intent.putExtra("siteType", index == 0 ? 1 : 2);
                                startActivityForResult(intent, 0);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected RuleEnginePresenter initPresenter() {
        return new RuleEnginePresenter();
    }

    @Override
    public void loadDataSuccess(List<RuleEngineBean> data) {
        List<RuleEngineBean> oneKeys = new ArrayList<>();
        List<RuleEngineBean> autoRuns = new ArrayList<>();

        for (RuleEngineBean ruleEngineBean : data) {
            if (ruleEngineBean.getRuleType() == 2) {//一键执行
                oneKeys.add(ruleEngineBean);
            }
            if (ruleEngineBean.getRuleType() == 1) {//自动化
                autoRuns.add(ruleEngineBean);
            }
        }
        {
        }
        {
        }
        loadDataFinish();
    }

    @Override
    public void loadDataFinish() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            mRefreshLayout.autoRefresh();
        }
    }

    /**
     * subFragment 通知刷新的入口
     */
    public final void notifyRefresh() {
        mRefreshLayout.autoRefresh();//自动刷新
    }
}
