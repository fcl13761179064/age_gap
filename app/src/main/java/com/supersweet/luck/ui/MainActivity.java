package com.supersweet.luck.ui;

import android.Manifest;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.supersweet.luck.R;
import com.supersweet.luck.addressselector.AddressSelector;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.ItemAddressReq;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.dialog.SearchFilterDialog;
import com.supersweet.luck.fragment.FragmentFour;
import com.supersweet.luck.fragment.FragmentOne;
import com.supersweet.luck.fragment.FragmentThree;
import com.supersweet.luck.fragment.FragmentTwo;
import com.supersweet.luck.location.LocationSelect;
import com.supersweet.luck.mvp.present.MainPresenter;
import com.supersweet.luck.mvp.view.MainView;
import com.supersweet.luck.rxbus.Myinfo;
import com.supersweet.luck.rxbus.RxBus;
import com.supersweet.luck.utils.AppManager;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.pushinfo.Utils;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @描述 首页
 * @作者 fanchunlei
 * @时间 2020/7/20
 */
public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements FragmentOne.fragmentOneToMainActivity, FragmentFour.fragmentFourToMainActivity, MainView, BottomNavigationBar.OnTabSelectedListener {
    private static final int REQUEST_CODE_TO_MORE = 0x10;
    @BindView(R.id.fl_container)
    FrameLayout fl_container;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    @BindView(R.id.tv_head_model)
    TextView tv_head_model;
    @BindView(R.id.tv_verified)
    TextView tv_verified;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.iv_photo)
    ImageView iv_photo;
    @BindView(R.id.ll_main_layout)
    LinearLayout ll_main_layout;
    @BindView(R.id.view_underline)
    View view_underline;


    private int GO_HOME_TYPE = 0;
    private int GO_SECOND_TYPE = 1;
    private int GO_THREE_TYPE = 2;
    private int GO_FOUR_TYPE = 3;
    private int count = 1;
    private FragmentOne mFragmentOne;
    private FragmentTwo mFragmentTwo;
    private FragmentThree mFragmentThree;
    private FragmentFour mFragmentFour;
    //Fragment 列表
    private List<Fragment> mFragments = new ArrayList();
    private Long pressTime = 0l;
    private TextView location;
    private static String filterCityCode;
    private static String filterCountry = "";
    private static String filterCity = "";
    private String TAG = MainActivity.class.toString();
    private String mConsume;
    private static final int PRIVATE_CODE = 1315;//开启GPS权限
    private static final int PRIVATE_OPEN_LOCATION = 1314;//开启GPS权限
    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void refreshUI() {
        super.refreshUI();
    }

    @Override
    protected void initView() {
        initFragment();
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED).setTabSelectedListener(this);
        // TODO 设置背景色样式
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.tab_home_selected, "").setActiveColorResource(R.color.custome_dialog).setInactiveIconResource(R.mipmap.tab_home_normal).setInActiveColorResource(R.color.gray))
                .addItem(new BottomNavigationItem(R.mipmap.tab_message_selected, "").setActiveColorResource(R.color.custome_dialog).setInactiveIconResource(R.mipmap.tab_message_normal).setInActiveColorResource(R.color.gray))
                .addItem(new BottomNavigationItem(R.mipmap.tab_verified_selected, "").setActiveColorResource(R.color.custome_dialog).setInactiveIconResource(R.mipmap.tab_verified_normal).setInActiveColorResource(R.color.gray))
                .setFirstSelectedPosition(0)
                .setBarBackgroundColor(R.color.white)
                .initialise();
        // 启动百度push
        initWithApiKey();
        view_underline.setVisibility(View.VISIBLE);
    }


    // api_key 绑定
    private void initWithApiKey() {
        // Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
        // 这里把apikey存放于manifest文件中，只是一种存放方式，
        // 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,"api_key")
        //请将AndroidManifest.xml api_key 字段值修改为自己的 api_key 方可使用 ！！
        //！！ATTENTION：You need to modify the value of api_key to your own in AndroidManifest.xml to use this Demo !!
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                Utils.getMetaValue(MainActivity.this, "api_key"));
    }

    // 设置标签,以英文逗号隔开
    private void setTags(int userId) {
        // Push: 设置tag调用方式
        List<String> tags = Utils.getTagsList(userId + "");
        PushManager.setTags(getApplicationContext(), tags);
    }


    /*
        初始化Fragment栈管理
     */
    private void initFragment() {
        mFragmentOne = new FragmentOne();
        mFragmentTwo = new FragmentTwo();
        mFragmentThree = new FragmentThree();
        mFragmentFour = new FragmentFour();
        mFragments.add(mFragmentOne);
        mFragments.add(mFragmentTwo);
        mFragments.add(mFragmentThree);
        mFragments.add(mFragmentFour);
        FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.fl_container, 0);
    }


    @Override
    protected void appBarRightIvClicked() {
        super.appBarRightIvClicked();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtras(getIntent());
        startActivityForResult(intent, REQUEST_CODE_TO_MORE);
    }


    @Override
    protected void initListener() {
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
                startActivity(intent);
            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFilterDialog.newInstance(new SearchFilterDialog.Callback() {
                    @Override
                    public void onDone(SearchFilterDialog dialog, String choose_sex, int minAge, int maxAge, String tv_myinfo_Text, String tv_myinfo_twoText, String tv_myinfo_threeText, String tv_myinfo_fourText, String tv_myinfo_fiveText, String tv_myinfo_sixText, String tv_myinfo_sevenText, String tv_myinfo_eightText, String tv_myinfo_nineText,String distance) {
                        AppData.is_come_in = true;
                        dialog.dismissAllowingStateLoss();
                        AppData.Filter_country = filterCountry;
                        AppData.Filter_city = filterCity;
                        AppData.Filter_minAge = minAge;
                        AppData.Filter_maxAge = maxAge;
                        initRxBus(choose_sex, minAge, maxAge,tv_myinfo_Text, tv_myinfo_twoText, tv_myinfo_threeText, tv_myinfo_fourText,tv_myinfo_fiveText, tv_myinfo_sixText, tv_myinfo_sevenText, tv_myinfo_eightText,tv_myinfo_nineText,distance);
                    }

                    @Override
                    public void onCancel(SearchFilterDialog dialog) {
                        dialog.dismissAllowingStateLoss();
                    }

                    @Override
                    public void location(View view, TextView tv_current_location) {
                        location = tv_current_location;

                        new LocationSelect() {


                            @Override
                            protected void onCountryListener(String s, AddressSelector addressSelector) {
                                mPresenter.getCity(s, addressSelector);
                            }


                            @Override
                            public void onCityListener(String country, String city, String cityCode, AddressSelector addressSelector) {
                                filterCityCode = cityCode;
                                filterCountry = country;
                                filterCity = city;
                                location.setText(filterCountry + "," + filterCity);
                            }
                        }.setAddressSelectorPopup(MainActivity.this, view);
                    }

                }).setTitle("Edit Profile")
                        .setContent("").show(getSupportFragmentManager(), null);
            }
        });

        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
                startActivity(intent);
            }
        });

        tv_verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_verified.setTextSize(24);
                tv_head_model.setTextSize(16);
                if (mFragmentFour == null) {
                    mFragmentFour = new FragmentFour();
                }
                changeFragment(GO_FOUR_TYPE);
                mFragmentFour.verifyPhoto();
                AppData.verified = "1";
                view_underline.setVisibility(View.GONE);
            }
        });
        tv_head_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_head_model.getText().equals("MATCH") ||tv_head_model.getText().equals("VERIFIED")) {
                    tv_verified.setTextSize(16);
                    tv_head_model.setTextSize(24);
                    if (mFragmentOne == null) {
                        mFragmentOne = new FragmentOne();
                    }
                    AppData.verified = "-1";
                    ll_main_layout.setBackgroundResource(0);
                    changeFragment(GO_HOME_TYPE);
                    view_underline.setVisibility(View.VISIBLE);
                }
            }
        });

        RxBus.getDefault().subscribe(this, "alter_head", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                String path = SharePreferenceUtils.getString(MainActivity.this, Constance.SP_HEADER, "");
                String sex = SharePreferenceUtils.getString(MainActivity.this, Constance.SP_SEX, "1");
                GlideLocalImageUtils.displaySmallImg(MainActivity.this, iv_photo, sex, path);
                throw new NullPointerException("");
            }
        });
    }

    private void initRxBus(String choose_sex, int minAge, int maxAge, String tv_myinfo_Text, String tv_myinfo_twoText, String tv_myinfo_threeText, String tv_myinfo_fourText, String tv_myinfo_fiveText, String tv_myinfo_sixText, String tv_myinfo_sevenText, String tv_myinfo_eightText, String tv_myinfo_nineText,String Distance) {
        Myinfo myinfo = new Myinfo();
        myinfo.setChooseSex(choose_sex + "");
        myinfo.setMinAge(minAge + "");
        myinfo.setMaxAge(maxAge + "");
        myinfo.setChooseCountryCode(filterCityCode);
        myinfo.setHeight(tv_myinfo_Text);
        myinfo.setBody(tv_myinfo_twoText);
        myinfo.setHair(tv_myinfo_threeText);
        myinfo.setRelationship(tv_myinfo_fourText);
        myinfo.setEducation(tv_myinfo_fiveText);
        myinfo.setEthnicity(tv_myinfo_sixText);
        myinfo.setDrinking(tv_myinfo_sevenText);
        myinfo.setSmoking(tv_myinfo_eightText);
        myinfo.setChildren(tv_myinfo_nineText);
        myinfo.setDisatance(Distance);
        RxBus.getDefault().post(myinfo, "filter_condition");
    }


    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Long time = System.currentTimeMillis();
        if (time - pressTime > 2000) {
            ToastUtils.showShort("Press again to exit the program");
            pressTime = time;
            return false;
        } else {
            AppManager.getAppManager().finishAllActivity();
            return true;
        }
    }



    @Override
    public void onTabSelected(int position) {
        ll_main_layout.setBackgroundResource(0);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                if (mFragmentOne == null) {
                    mFragmentOne = new FragmentOne();
                }
                tv_verified.setTextSize(16);
                tv_head_model.setTextSize(24);
                tv_head_model.setText("MATCH");
                view_underline.setVisibility(View.VISIBLE);
                tv_verified.setVisibility(View.VISIBLE);
                iv_search.setVisibility(View.VISIBLE);
                changeFragment(GO_HOME_TYPE);
                AppData.verified = "-1";
                break;
            case 1:
                if (mFragmentTwo == null) {
                    mFragmentTwo = new FragmentTwo();
                }
                tv_verified.setVisibility(View.GONE);
                iv_search.setVisibility(View.GONE);
                changeFragment(GO_SECOND_TYPE);
                tv_head_model.setTextSize(24);
                tv_head_model.setText("MESSAGE");
                view_underline.setVisibility(View.GONE);
                break;
            case 2:
                if (mFragmentThree == null) {
                    mFragmentThree = new FragmentThree();
                }
                tv_verified.setVisibility(View.GONE);
                iv_search.setVisibility(View.GONE);
                tv_head_model.setTextSize(24);
                tv_head_model.setText("CONNECTION");
                changeFragment(GO_THREE_TYPE);
                view_underline.setVisibility(View.GONE);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /*
  切换Tab，切换对应的Fragment
*/
    private void changeFragment(int position) {
        FragmentUtils.showHide(mFragments.get(position), mFragments);
    }

    @Override
    public void MyInfo_success(MyInfoBean s) {

    }

    @Override
    public void errorShake(String message) {

    }

    @Override
    public void OnCountySucces(CityBean cityBean, AddressSelector addressSelector) {
        ArrayList<ItemAddressReq> itemAddressCity = LocationSelect.getItemAddressCity(cityBean.getCity());
        addressSelector.setCities(itemAddressCity);
    }


    @Override
    public void OnStationSucces(StationBean cityBean, AddressSelector addressSelector) {
        ArrayList<ItemAddressReq> itemAddressCity = LocationSelect.getItemAddressStation(cityBean.getCity());
        addressSelector.setCities(itemAddressCity);
    }

    @Override
    public void updateLocation(IntenetReposeBean o) {

    }

    @Override
    public void updataLocationerror(String message) {

    }


    @Override
    public void fromFragmentOne(String head_avatar) {
        String sex = SharePreferenceUtils.getString(MainActivity.this, Constance.SP_SEX, "1");
        GlideLocalImageUtils.displaySmallImg(MainActivity.this, iv_photo, sex, head_avatar);
    }

    @Override
    public void fromFragmentFour() {
        ll_main_layout.setBackgroundResource(R.mipmap.verified_bg);
    }


}
