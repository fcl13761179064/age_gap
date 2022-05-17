package com.supersweet.luck.base;


import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.supersweet.luck.R;
import com.supersweet.luck.rxbus.RxBus;
import com.supersweet.luck.utils.AppManager;
import com.supersweet.luck.utils.ClickUtils;
import com.supersweet.luck.utils.StatusBarToolUlti;
import com.supersweet.luck.widget.LoadingDialog;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author fancunlei
 * BaseActivity
 * 基础Activity
 */
public abstract class BasicActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 锁定竖屏
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initSaveInstace(savedInstanceState);
        refreshUI();
        initView();
        initListener();
        AppManager.getAppManager().addActivity(this);
        if (!ScreenUtils.isFullScreen(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
                BarUtils.setStatusBarLightMode(this, true);
            } else {
                BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
            }
        }

        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.loading);
        ClassicsHeader.REFRESH_HEADER_LOADING = getString(R.string.loading);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.pulldown);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.pulldowntorelease);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_ail);
        ClassicsHeader.REFRESH_HEADER_PULLING = getString(R.string.pulldowntorelease);
        ClassicsHeader.REFRESH_HEADER_UPDATE = "'Last update' M-d HH:mm";

        ClassicsFooter.REFRESH_FOOTER_NOTHING = getString(R.string.no_more_data);

    }


    //设置字体为默认大小，不随系统字体大小改而改变
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    protected void setStatusBar() {
        //沉浸式代码配置
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarToolUlti.setRootViewFitsSystemWindows(this, true);
        //设置状态栏透明
        StatusBarToolUlti.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarToolUlti.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarToolUlti.setStatusBarColor(this, 0xffffff00);
        }
    }

    /**
     * 需要从savedInstanceState拿数据的调用改方法
     */
    public void initSaveInstace(Bundle savedInstanceState) {
    }

    // 初始化UI，setContentView等
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initListener();


    private LoadingDialog progressDialog;

    public void showProgress() {
        showProgress("Loading...");
    }

    @TargetApi(17)
    public boolean isFinished() {
        if (Build.VERSION.SDK_INT >= 16) {
            return isDestroyed() || isFinishing();
        } else {
            return isFinishing();
        }
    }


    public void showProgress(String msg) {
        if (isFinished() || isDestroyed()) {
            return;
        }
        if (null != progressDialog) {
            return;
        }
        progressDialog = LoadingDialog.newInstance(msg);
        progressDialog.show(getSupportFragmentManager(), "Loading...");
    }

    public void hideProgress() {
        if (null != progressDialog) {
            progressDialog.dismissAllowingStateLoss();
        }
        progressDialog = null;
    }

    public void refreshUI() {
        final View appbarRoot = findViewById(R.id.appbar_root_rl_ff91090);
        if (appbarRoot != null) {
            View leftIV = appbarRoot.findViewById(R.id.iv_left);
            if (leftIV != null && !leftIV.hasOnClickListeners()) {
                ClickUtils.applySingleDebouncing(leftIV, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appBarLeftIvClicked();
                    }
                });
            }
            View leftTV = appbarRoot.findViewById(R.id.tv_left);
            if (leftTV != null && !leftTV.hasOnClickListeners()) {
                ClickUtils.applySingleDebouncing(leftTV, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appBarLeftTvClicked();
                    }
                });
            }
            View rightIV = appbarRoot.findViewById(R.id.tv_right);
            if (rightIV != null && !rightIV.hasOnClickListeners()) {
                ClickUtils.applySingleDebouncing(rightIV, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appBarRightIvClicked();
                    }
                });
            }
            View rightTv = appbarRoot.findViewById(R.id.tv_right);
            if (rightTv != null && !rightTv.hasOnClickListeners()) {
                ClickUtils.applySingleDebouncing(rightTv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appBarRightTvClicked();
                    }
                });
            }
        }
    }


    /**
     * appbar左侧图标点击事件
     */
    protected void appBarLeftIvClicked() {
       onBackPressed();
    }

    protected void appBarRightIvClicked() {

    }

    protected void appBarLeftTvClicked() {
        mExitApp();
    }

    protected void appBarRightTvClicked() {

    }

    protected void mExitApp() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        RxBus.getDefault().unregister(this);
    }

    /**
     * 统计提供方法监听Framgnt function
     *
     * @param tag
     */
    public void setFunctionsForFragment(String tag) {
    }
}

