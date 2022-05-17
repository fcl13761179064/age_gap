package com.supersweet.luck.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.base.BasicActivity;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.LocationBean;
import com.supersweet.luck.mvp.present.SplashPresenter;
import com.supersweet.luck.mvp.view.SplashView;
import com.supersweet.luck.utils.AppManager;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppData;

import java.util.ArrayList;
import java.util.Locale;

public class SPlashActivity extends BaseMvpActivity<SplashView, SplashPresenter> implements SplashView {

    private String sava_token;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(Locale.ENGLISH);
        resources.updateConfiguration(config, dm);
        sava_token = SharePreferenceUtils.getString(SPlashActivity.this, Constance.SP_Login_Token, null);
        ArrayList<AllCountryBean.Country> allCountry = (ArrayList<AllCountryBean.Country>) SharePreferenceUtils.readObject(SPlashActivity.this, Constance.ALL_COUNTRY);
        if (allCountry == null) {
            mPresenter.location();
        } else {
            if (TextUtils.isEmpty(sava_token)) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SPlashActivity.this, GuideActivity.class);
                        startActivity(intent);
                    }
                }, 600);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SPlashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, 600L);
            }
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void LocationSuccess(AllCountryBean data) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharePreferenceUtils.saveObject(SPlashActivity.this, Constance.ALL_COUNTRY, data.getCountry());

                if (TextUtils.isEmpty(sava_token)) {
                    Intent intent = new Intent(SPlashActivity.this, GuideActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SPlashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 600L);
    }


    @Override
    public void error() {
        SharePreferenceUtils.saveObject(SPlashActivity.this, Constance.ALL_COUNTRY, null);
        SharePreferenceUtils.saveString(SPlashActivity.this, Constance.SP_Login_Token, null);
        Intent intent = new Intent(SPlashActivity.this, GuideActivity.class);
        startActivity(intent);
        finish();
    }
}
