package com.supersweet.luck.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.fragment.AbountFragment;
import com.supersweet.luck.fragment.MyPictureFragment;
import com.supersweet.luck.fragment.ProfileFragment;
import com.supersweet.luck.mvp.present.MyInfoPresenter;
import com.supersweet.luck.mvp.view.MyInfoView;
import com.supersweet.luck.pictureselector.PictureBean;
import com.supersweet.luck.pictureselector.PictureSelector;
import com.supersweet.luck.rxbus.RxBus;
import com.supersweet.luck.utils.AppManager;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
import com.supersweet.luck.widget.MyDatas;
import com.supersweet.luck.widget.NoAnimationViewPager;
import com.supersweet.luck.widget.VerifyPhotoDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyInfoActivity extends BaseMvpActivity<MyInfoView, MyInfoPresenter> implements MyInfoView {

    @BindView(R.id.iv_back)
    RelativeLayout iv_back;
    @BindView(R.id.appbar_iv_target)
    ImageView appbar_iv_target;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    NoAnimationViewPager viewPager;
    @BindView(R.id.my_setting)
    RelativeLayout my_setting;
    @BindView(R.id.user_sex)
    TextView user_sex;
    @BindView(R.id.user_age)
    TextView user_age;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.edit_profile)
    ImageView edit_profile;
    @BindView(R.id.ll_verify)
    LinearLayout ll_verify;
    @BindView(R.id.iv_verify)
    ImageView iv_verify;
    @BindView(R.id.ll_verify_success)
    LinearLayout ll_verify_success;
    @BindView(R.id.coins)
    TextView coins;
    @BindView(R.id.tv_station)
    TextView tv_station;
    @BindView(R.id.tv_credit_fen)
    TextView tv_credit_fen;
    @BindView(R.id.iv_month_pay_vip)
    ImageView iv_month_pay_vip;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titleArr = {"Profile", "Photo", "About Me"};
    private int[] selectedArr = {R.mipmap.me_profile_selected, R.mipmap.me_image_selected, R.mipmap.me_about_select};
    private int[] unSelectedArr = {R.mipmap.me_profile_normal, R.mipmap.me_image_normal, R.mipmap.me_about_normal};
    private final int DEFAULT_POSITION = 0;
    private ProfileFragment profileFragment;
    private MyInfoBean myinfobean;
    private int userCoin;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_myinfo;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initTabVp();
        }
        if (AppData.MyInfoBean != null && AppData.MyInfoBean.getUser() != null) {
            if (2 == AppData.MyInfoBean.getUser().getVerifyStatus()) {
                ll_verify_success.setVisibility(View.VISIBLE);
                ll_verify.setVisibility(View.GONE);
            }
        }

        // 导航栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //触摸外部，键盘消失
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setTitle("  ");
        my_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoActivity.this, SettingActivity.class);
                intent.putExtra("userCoins", userCoin);
                startActivity(intent);
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoActivity.this, EditProfileActivity.class);
                intent.putExtra("MyinfoBean", (Serializable) myinfobean);
                intent.putExtra("location_station", tv_station.getText().toString());
                startActivity(intent);
            }
        });
        String path = SharePreferenceUtils.getString(this, Constance.SP_HEADER, "");
        String sex = SharePreferenceUtils.getString(this, Constance.SP_SEX, "1");
        GlideLocalImageUtils.displayBigOrSmallShadowImage(this, appbar_iv_target, sex,path,"big");
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getMyinfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initTabVp() {
        viewPager.setOffscreenPageLimit(2);
        fragments = new ArrayList<>();
        profileFragment = new ProfileFragment();
        fragments.add(profileFragment);
        fragments.add(new MyPictureFragment());
        fragments.add(new AbountFragment());
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < fragments.size(); i++) {
            TabLayout.Tab tabView = tabLayout.getTabAt(i);
            tabView.setCustomView(getTabView(i));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setTabBackground(tabLayout.getTabAt(i), false);
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //设置选中图标样式
                View tabView = tab.getCustomView();
                tabView.findViewById(R.id.tabicon).setBackgroundResource(selectedArr[tab.getPosition()]);

                //设置选中字体颜色
                TextView textView = tabView.findViewById(R.id.tabtext);
                textView.setTextColor(getResources().getColor(R.color.cl_666));
                setTabBackground(tab, true);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //设置未选中图标样式
                View tabView = tab.getCustomView();
                tabView.findViewById(R.id.tabicon).setBackgroundResource(unSelectedArr[tab.getPosition()]);

                //设置未选中字体颜色
                TextView textView = tabView.findViewById(R.id.tabtext);
                textView.setTextColor(getResources().getColor(R.color.theme_color));
                setTabBackground(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        setTabBackground(tabLayout.getTabAt(DEFAULT_POSITION), true);

    }

    /**
     * 使用自定义的View布局
     *
     * @param position
     * @return
     */
    private View getTabView(int position) {
        View v = LayoutInflater.from(this).inflate(R.layout.tab_view, null);
        ImageView iv = v.findViewById(R.id.tabicon);
        TextView tv = v.findViewById(R.id.tabtext);

        tv.setText(titleArr[position]);
        //设置默认页面
        if (position == DEFAULT_POSITION) {
            iv.setBackgroundResource(selectedArr[position]);
            tv.setTextColor(v.getResources().getColor(R.color.cl_666));
        } else {
            iv.setBackgroundResource(unSelectedArr[position]);
            tv.setTextColor(v.getResources().getColor(R.color.theme_color));
        }
        return v;
    }

    /**
     * TabLayout每个Tab选中背景不一样。
     * https://blog.csdn.net/qq_32606467/article/details/103068611
     *
     * @param tab
     * @param selected
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setTabBackground(TabLayout.Tab tab, boolean selected) {
        Drawable drawable = null;
        switch (tab.getPosition()) {
            case 0:
                if (selected) {
                    drawable = getDrawable(R.drawable.tab_group);
                } else {
                    drawable = getDrawable(R.color.color_f1f1f1);
                }
                break;
            case 1:
                if (selected) {
                    drawable = getDrawable(R.drawable.tab_group);
                } else {
                    drawable = getDrawable(R.color.color_f1f1f1);
                }
                break;
            case 2:
                if (selected) {
                    drawable = getDrawable(R.drawable.tab_group);
                } else {
                    drawable = getDrawable(R.color.color_f1f1f1);
                }
                break;
        }

        ViewCompat.setBackground(tab.view, drawable);
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleArr[position];
        }
    }


    @Override
    protected void initListener() {
        ll_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (-1 == AppData.MyInfoBean.getUser().getVerifyStatus()|| 3 == AppData.MyInfoBean.getUser().getVerifyStatus()) {
                    VerifyPhotoDialog.newInstance(new VerifyPhotoDialog.Callback() {
                        @Override
                        public void onDone(VerifyPhotoDialog dialog) {
                            PictureSelector
                                    .create(MyInfoActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                                    .selectPicture(false);
                            dialog.dismissAllowingStateLoss();
                        }

                        @Override
                        public void onCancel(VerifyPhotoDialog dialog) {
                            dialog.dismissAllowingStateLoss();
                        }


                    }).setTitle("")
                            .setContent("").show(getSupportFragmentManager(), null);

                } else if (1 == AppData.MyInfoBean.getUser().getVerifyStatus()) {
                    CustomToast.makeTopText(MyInfoActivity.this, "Your verification is processing,normally takes 1 working day").show();
                }
            }
        });


        RxBus.getDefault().subscribe(this, "alter_head", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                String path = SharePreferenceUtils.getString(getApplicationContext(), Constance.SP_HEADER, "");
                String sex = SharePreferenceUtils.getString(getApplicationContext(), Constance.SP_SEX, "1");
                GlideLocalImageUtils.displayBigOrSmallShadowImage(getApplicationContext(), appbar_iv_target, sex,path,"big");
            }
        });

        RxBus.getDefault().subscribe(this, "edit_sex", new RxBus.Callback<String>() {
            private String s;

            @Override
            public void onEvent(String s) {
                String sex = SharePreferenceUtils.getString(MyInfoActivity.this, Constance.SP_SEX, "1");
                user_sex.setText(MyDatas.sextosting(sex));

            }
        });

        RxBus.getDefault().subscribe(this, "edit_age", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                user_age.setText(s);
            }
        });

        RxBus.getDefault().subscribe(this, "edit_location", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String myinfo) {
                if (!TextUtils.isEmpty(myinfo)){
                    String[] split = myinfo.split(",");
                    tv_station.setText(split[3]);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().AppExit(this);
    }

    @Override
    protected MyInfoPresenter initPresenter() {
        return new MyInfoPresenter();
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void MyInfo_success(MyInfoBean data) {
        try {
            this.myinfobean = data;
            AppData.MyInfoBean = data;
            AppData.verified = data.getUser().getVerifyStatus() + "";
            String sex = data.getUser().getSex();
            String qscore = data.getUser().getQscore();
            int age = data.getUser().getAge();
            userCoin = data.getUser().getUserCoin();
            String station = data.getUser().getStation();
            user_sex.setText(MyDatas.sextosting(sex));
            user_age.setText(age+"");
            coins.setText(userCoin+"");
            tv_credit_fen.setText(qscore);
            tv_user_name.setText(data.getUser().getAccount());
            mPresenter.station(data.getUser().getCity(), station);
            if (data.getUser().getMonthFlag() ==-1){
                iv_month_pay_vip.setVisibility(View.GONE);
            }else {
                iv_month_pay_vip.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void VerifyPhotoSuccess(UpHeadBean data) {
        CustomToast.makeText(MyApplication.getContext(), "Upload successfully", R.drawable.ic_toast_warming).show();
        AppData.MyInfoBean.getUser().setVerifyStatus(1);
        RxBus.getDefault().post("", "refresh_data");
        /*ll_verify_success.setVisibility(View.VISIBLE);
        ll_verify.setVisibility(View.GONE);*/
    }

    @Override
    public void station(StationBean s, String station) {
        for (StationBean.City stationList : s.getCity()) {
            if (stationList.getConfigCode().equals(station)) {
               String configName = stationList.getConfigName();
                tv_station.setText(configName);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                if (pictureBean.isCut()) {
                    mPresenter.verfyUpdataPhoto(pictureBean.getPath());
                } else {
                    mPresenter.verfyUpdataPhoto(pictureBean.getPath());
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
