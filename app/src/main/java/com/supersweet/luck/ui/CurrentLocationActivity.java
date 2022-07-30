package com.supersweet.luck.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.supersweet.luck.R;
import com.supersweet.luck.addressselector.AddressSelector;
import com.supersweet.luck.addressselector.CityInterface;
import com.supersweet.luck.addressselector.OnItemClickListener;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.ItemAddressReq;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.dialog.NoTitleDialog;
import com.supersweet.luck.mvp.present.LocationPresenter;
import com.supersweet.luck.mvp.view.LocationView;
import com.supersweet.luck.serive.service.LocationService;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CommonPopWindow;
import com.supersweet.luck.widget.CustomToast;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.supersweet.luck.application.MyApplication.getContext;

public class CurrentLocationActivity extends BaseMvpActivity<LocationView, LocationPresenter> implements CommonPopWindow.ViewClickListener, LocationView {

    @BindView(R.id.tv_current_location)
    TextView tv_current_location;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.rl_current_location)
    RelativeLayout rl_current_location;
    private ArrayList<CityBean.City> mCityData;
    private List<StationBean.City> mStation;
    private List<AllCountryBean.Country> allCountry;
    private String local_country;
    private String local_province;
    private String local_city;
    private static final int PRIVATE_CODE = 1315;//开启GPS权限
    private static final int PRIVATE_OPEN_LOCATION = 1314;//开启GPS权限
    private LocationService locationService;
    private double latitude;
    private double altitude;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_current_location;
    }

    @Override
    protected void initView() {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(Locale.ENGLISH);
        resources.updateConfiguration(config, dm);
        allCountry = (ArrayList<AllCountryBean.Country>) SharePreferenceUtils.readObject(CurrentLocationActivity.this, Constance.ALL_COUNTRY);
        showGPSContacts();
    }


    /**
     * 检测GPS、位置权限是否开启
     */
    public void showGPSContacts() {
        LocationManager lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {// 没有权限，申请权限。
                    ActivityCompat.requestPermissions(this, LOCATIONGPS, PRIVATE_CODE);
                } else {
                    showProgress();
                    getLoacation();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideProgress();
                        }
                    }, 3000L);

                }
            } else {
                showProgress();
                getLoacation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgress();
                    }
                }, 3000L);
            }
        } else {
            NoTitleDialog
                    .newInstance(new NoTitleDialog.Callback() {
                        @Override
                        public void onDone(NoTitleDialog dialog) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, PRIVATE_OPEN_LOCATION);
                            dialog.dismiss();

                        }

                        @Override
                        public void onCancel(NoTitleDialog dialog) {
                            dialog.dismissAllowingStateLoss();
                        }
                    })
                    .setContent("Please open location information permission.")
                    .show(getSupportFragmentManager(), "dialog");
        }
    }

    /**
     * Android6.0申请权限的回调方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
//             requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case PRIVATE_CODE:
                //如果用户取消，permissions可能为null.
                if (grantResults !=null && grantResults[0] == PERMISSION_GRANTED && grantResults.length > 0) { //有权限
                    // 获取到权限，作相应处理
                    getLoacation();
                } else {
                    NoTitleDialog
                            .newInstance(new NoTitleDialog.Callback() {
                                @Override
                                public void onDone(NoTitleDialog dialog) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_LOCALE_SETTINGS);
                                    startActivityForResult(intent, PRIVATE_CODE);
                                    dialog.dismiss();

                                }

                                @Override
                                public void onCancel(NoTitleDialog dialog) {
                                    dialog.dismissAllowingStateLoss();
                                }
                            })
                            .setContent("Please open location information permission.")
                            .show(getSupportFragmentManager(), "dialog");

                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PRIVATE_OPEN_LOCATION: {
                if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {// 没有权限，申请权限。
                        ActivityCompat.requestPermissions(this, LOCATIONGPS, PRIVATE_CODE);
                    } else {
                        getLoacation();
                    }
                } else {
                    getLoacation();
                }
                break;
            }
        }
    }

    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public void getLoacation() {
        // -----------location config ------------
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        }
        locationService.start();// 定位SDK
    }


    /**
     * 设置弹出PopWindow
     *
     * @param v
     */
    private void setAddressSelectorPopup(View v) {
        int screenHeigh = getResources().getDisplayMetrics().heightPixels;

        CommonPopWindow.newBuilder()
                .setView(R.layout.pop_address_selector_bottom)
                .setAnimationStyle(R.style.AnimUp)
                .setBackgroundDrawable(new BitmapDrawable())
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(screenHeigh * 0.6f))
                .setViewOnClickListener(this)
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(this)
                .showAsBottom(v);
    }


    //性能最高的方法
    public String getStringBuilder(String country, String city, String district) {
        StringBuilder sb = new StringBuilder();
        sb.append(country).append(" ");
        sb.append(city).append(" ");
        sb.append(district);
        return sb.toString();
    }

    @Override
    protected void initListener() {
        rl_current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddressSelectorPopup(view);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tv_current_location.getText())) {
                    CustomToast.makeText(MyApplication.getContext(), "Please select your location.", R.drawable.ic_toast_warming).show();
                    return;
                }
                if (altitude !=0 && latitude!=0 ){
                    Intent intent2 = new Intent(CurrentLocationActivity.this, ChooseHightActivity.class);
                    intent2.putExtras(getIntent());
                    intent2.putExtra("altitude",altitude);
                    intent2.putExtra("latitude",latitude);
                    startActivity(intent2);
                }
            }
        });
    }

    @Override
    public void getChildView(PopupWindow mPopupWindow, View view, int mLayoutResId) {
        switch (mLayoutResId) {
            case R.layout.pop_address_selector_bottom:
                ImageView imageBtn = view.findViewById(R.id.img_guanbi);
                AddressSelector addressSelector = view.findViewById(R.id.address);

                //设置默认选择数据
                dealWithAddressSelector(addressSelector, mPopupWindow);

                imageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });

                break;
        }
    }

    @Override
    protected LocationPresenter initPresenter() {
        return new LocationPresenter();
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void sheng(CityBean data, AddressSelector addressSelector) {
        this.mCityData = data.getCity();
        addressSelector.setCities(getItemAddressCity(data.getCity()));
    }

    @Override
    public void city(CityBean data, String province, String station) {
        ArrayList<CityBean.City> city = data.getCity();
        for (CityBean.City every_city : city) {
            if (every_city.getConfigValue().equalsIgnoreCase(province)) {
                AppData.city = every_city.getConfigCode();
                local_province = every_city.getConfigValue();
                mPresenter.station(every_city.getConfigCode(), station);
            }

        }

    }


    @Override
    public void station(StationBean data, String station) {
        List<StationBean.City> citys = data.getCity();
        for (StationBean.City every_city : citys) {
            if (every_city.getConfigValue().equalsIgnoreCase(station)) {
                AppData.station = every_city.getConfigCode();
                local_city = every_city.getConfigValue();
                String stringBuilder = getStringBuilder(local_country, local_province, local_city);
                if (!TextUtils.isEmpty(stringBuilder)) {
                    tv_current_location.setText(stringBuilder);
                }
                break;
            }
        }

    }

    @Override
    public void qu(StationBean data, AddressSelector addressSelector) {
        this.mStation = data.getCity();
        addressSelector.setCities(getItemAddressStation(mStation));
    }


    private void dealWithAddressSelector(AddressSelector addressSelector, final PopupWindow mPopupWindow) {
        final String[] sheng = new String[3];
        final String[] code = new String[3];
        ArrayList<ItemAddressReq> itemCountry = getItemAddressCountry(allCountry);
        addressSelector.setTabAmount(3);
        //设置数据
        addressSelector.setCities(itemCountry);
        //设置Tab横线的颜色
        addressSelector.setLineColor(Color.parseColor("#7857EF"));
        //设置Tab文字默认颜色
        addressSelector.setTextEmptyColor(Color.parseColor("#7857EF"));
        //设置列表选中文字颜色
        addressSelector.setListTextSelectedColor(Color.parseColor("#7857EF"));
        //设置Tab文字选中的颜色
        addressSelector.setTextSelectedColor(Color.parseColor("#7857EF"));

        //设置列表的点击事件回调接口
        addressSelector.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(AddressSelector addressSelector, CityInterface city, int tabPosition, int selecePos) {
                switch (tabPosition) {
                    case 0:
                        //设置省列表数据
                        sheng[0] = city.getCityName();
                        code[0] = city.getCityCode();
                        mPresenter.btnCity(code[0], addressSelector);
                        AppData.country = code[0];
                        break;
                    case 1:
                        //设置市列表数据
                        sheng[1] = city.getCityName();
                        code[1] = city.getCityCode();
                        mPresenter.btnStation(code[1], addressSelector);
                        AppData.city = code[1];
                        break;
                    case 2:
                        //设置区列表数据
                        sheng[2] = city.getCityName();
                        code[2] = city.getCityCode();
                        AppData.station = code[2];
                        tv_current_location.setText(sheng[0] + " " + sheng[1] + " " + sheng[2]);
                        mPopupWindow.dismiss();
                        break;
                }
            }
        });


        //设置顶部tab的点击事件回调
        addressSelector.setOnTabSelectedListener(new AddressSelector.OnTabSelectedListener() {
            @Override
            public void onTabSelected(AddressSelector addressSelector, AddressSelector.Tab tab) {
                switch (tab.getIndex()) {
                    case 0:
                        addressSelector.setCities(itemCountry);
                        break;
                    case 1:
                        addressSelector.setCities(getItemAddressCity(mCityData));
                        break;
                    case 2:
                        addressSelector.setCities(getItemAddressStation(mStation));
                        break;
                }
            }

            @Override
            public void onTabReselected(AddressSelector addressSelector, AddressSelector.Tab tab) {

            }
        });
    }



    /*
     * 获取省的数据
     *
     * @param addressSelectorList
     */

    @NonNull
    private ArrayList<ItemAddressReq> getItemAddressCountry(List<AllCountryBean.Country> addressSelectorList) {
        final ArrayList<ItemAddressReq> itemAddressReqs = new ArrayList<>();
        for (int i = 0; i < addressSelectorList.size(); i++) {
            ItemAddressReq itemAddressReq = new ItemAddressReq();
            itemAddressReq.setConfigCode(addressSelectorList.get(i).getConfigCode());
            itemAddressReq.setConfigLevel(addressSelectorList.get(i).getConfigLevel());
            itemAddressReq.setConfigName(addressSelectorList.get(i).getConfigName());
            itemAddressReq.setConfigType(addressSelectorList.get(i).getConfigType());
            itemAddressReq.setConfigValue(addressSelectorList.get(i).getConfigValue());
            itemAddressReq.setId(addressSelectorList.get(i).getId());
            itemAddressReq.setParentId(addressSelectorList.get(i).getParentId());
            itemAddressReq.setYn(addressSelectorList.get(i).getYn());
            itemAddressReq.setOrderBy(addressSelectorList.get(i).getOrderBy());
            itemAddressReqs.add(itemAddressReq);
        }
        return itemAddressReqs;
    }


    /*
     * 获取市的数据
     *
     * @param addressSelectorList
     */

    @NonNull
    private ArrayList<ItemAddressReq> getItemAddressCity(ArrayList<CityBean.City> addressSelectorList) {
        final ArrayList<ItemAddressReq> itemAddressReqs = new ArrayList<>();
        for (int i = 0; i < addressSelectorList.size(); i++) {
            ItemAddressReq itemAddressReq = new ItemAddressReq();
            itemAddressReq.setConfigCode(addressSelectorList.get(i).getConfigCode());
            itemAddressReq.setConfigLevel(addressSelectorList.get(i).getConfigLevel());
            itemAddressReq.setConfigName(addressSelectorList.get(i).getConfigName());
            itemAddressReq.setConfigType(addressSelectorList.get(i).getConfigType());
            itemAddressReq.setConfigValue(addressSelectorList.get(i).getConfigValue());
            itemAddressReq.setId(addressSelectorList.get(i).getId());
            itemAddressReq.setParentId(addressSelectorList.get(i).getParentId());
            itemAddressReq.setYn(addressSelectorList.get(i).getYn());
            itemAddressReq.setOrderBy(addressSelectorList.get(i).getOrderBy());
            itemAddressReqs.add(itemAddressReq);
        }
        return itemAddressReqs;
    }


    /*
     * 获取市的数据
     *
     * @param addressSelectorList
     */

    @NonNull
    private ArrayList<ItemAddressReq> getItemAddressStation(List<StationBean.City> addressSelectorList) {
        final ArrayList<ItemAddressReq> itemAddressReqs = new ArrayList<>();
        for (int i = 0; i < addressSelectorList.size(); i++) {
            ItemAddressReq itemAddressReq = new ItemAddressReq();
            itemAddressReq.setConfigCode(addressSelectorList.get(i).getConfigCode());
            itemAddressReq.setConfigLevel(addressSelectorList.get(i).getConfigLevel());
            itemAddressReq.setConfigName(addressSelectorList.get(i).getConfigName());
            itemAddressReq.setConfigType(addressSelectorList.get(i).getConfigType());
            itemAddressReq.setConfigValue(addressSelectorList.get(i).getConfigValue());
            itemAddressReq.setId(addressSelectorList.get(i).getId());
            itemAddressReq.setParentId(addressSelectorList.get(i).getParentId());
            itemAddressReq.setYn(addressSelectorList.get(i).getYn());
            itemAddressReq.setOrderBy(addressSelectorList.get(i).getOrderBy());
            itemAddressReqs.add(itemAddressReq);
        }
        return itemAddressReqs;
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        /**
         * 定位请求回调函数
         * @param location 定位结果
         */
        @Override
        public void onReceiveLocation(BDLocation location) {

            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                altitude = location.getLongitude();
                latitude = location.getLatitude();
                local_province = location.getProvince();
                local_country = location.getCountry();
                local_city = location.getCity();
                if (!TextUtils.isEmpty(local_province) && !TextUtils.isEmpty(local_country) && !TextUtils.isEmpty(local_city)) {
                    if (locationService != null) {
                        locationService.unregisterListener(mListener);
                        locationService.stop();
                    }
                    for (int x = 0; x < allCountry.size(); x++) {
                        String every_country = allCountry.get(x).getConfigValue();
                        String every_country_code = allCountry.get(x).getConfigCode();
                        if (local_country.equalsIgnoreCase(every_country)) {
                            AppData.country = every_country_code;
                            local_country = every_country;
                            mPresenter.city(every_country_code, local_province, local_city);
                            break;
                        }
                    }
                }
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            super.onConnectHotSpotMessage(s, i);
        }

        /**
         * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
         * @param locType 当前定位类型
         * @param diagnosticType 诊断类型（1~9）
         * @param diagnosticMessage 具体的诊断信息释义
         */
        @Override
        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage);
            CustomToast.makeText(getContext(), "seek failed...", R.drawable.ic_toast_warming).show();
        }
    };

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationService != null) {
            locationService.unregisterListener(mListener);
            locationService.stop();
        }
    }
}

