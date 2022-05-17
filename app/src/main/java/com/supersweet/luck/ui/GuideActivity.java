package com.supersweet.luck.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.base.BasicActivity;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.mvp.present.SplashPresenter;
import com.supersweet.luck.mvp.view.SplashView;
import com.supersweet.luck.utils.AppManager;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class GuideActivity extends BaseMvpActivity<SplashView, SplashPresenter> implements SplashView {

    @BindView(R.id.sign_up)
    TextView sign_up;
    @BindView(R.id.sign_in)
    TextView sign_in;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        ArrayList<AllCountryBean.Country> allCountry = (ArrayList<AllCountryBean.Country>) SharePreferenceUtils.readObject(GuideActivity.this, Constance.ALL_COUNTRY);
        if (allCountry == null) {
            mPresenter.location();
        }
    }


    @Override
    protected void initListener() {
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().AppExit(this);
    }

    @Override
    protected SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void LocationSuccess(AllCountryBean data)
    {
        SharePreferenceUtils.saveObject(this,Constance.ALL_COUNTRY, data.getCountry());
    }

    @Override
    public void error() {
        SharePreferenceUtils.saveObject(this,Constance.ALL_COUNTRY, null);
    }
}
