package com.supersweet.luck.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.User;
import com.supersweet.luck.dialog.NoTitleDialog;
import com.supersweet.luck.mvp.present.RegisterPresenter;
import com.supersweet.luck.mvp.view.RegisterView;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.CustomToast;

import butterknife.BindView;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.supersweet.luck.application.MyApplication.getContext;

public class AbountActivity extends BaseMvpActivity<RegisterView, RegisterPresenter> implements RegisterView {

    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_des)
    EditText et_des;
    private double latitude;
    private double altitude;
    private static final int PRIVATE_CODE = 1315;//开启GPS权限
    private static final int PRIVATE_OPEN_LOCATION = 1314;//开启GPS权限
    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private String userName;
    private String password;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_abount;
    }

    @Override
    protected void initView() {
        userName = getIntent().getStringExtra("userName");
        password = getIntent().getStringExtra("password");
        altitude = getIntent().getDoubleExtra("altitude", 0.0);
        latitude = getIntent().getDoubleExtra("latitude", 0.0);
        if (latitude == 0 && altitude == 0) {
            showGPSContacts();
        }
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGPSContacts();

            }
        });
    }

    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }


    @Override
    public void errorShake(int type, int CycleTimes, String msg) {
        CustomToast.makeText(AbountActivity.this, msg, R.drawable.ic_toast_warming).show();
    }

    @Override
    public void RegistSuccess(IntenetReposeBean data) {
        if ("0".equals(data.getCode())) {
            mPresenter.login(userName, password);
        }
    }


    @Override
    public void loginSuccess(User user) {
        mPresenter.getLocation(latitude, altitude);
        SharePreferenceUtils.saveString(getContext(), Constance.SP_Login_Token, user.getToken());
        SharePreferenceUtils.saveString(this, Constance.SP_USER_NAME, userName);
        Intent intent = new Intent(AbountActivity.this, MainActivity.class);
        intent.putExtras(getIntent());
        startActivity(intent);
    }

    @Override
    public void updateLocation(IntenetReposeBean o) {

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
                    getLoacation();

                }
            } else {
                getLoacation();
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
     * 检测GPS、位置权限是否开启
     */
    public void openGPSContacts() {
        LocationManager lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {// 没有权限，申请权限。
                    ActivityCompat.requestPermissions(this, LOCATIONGPS, PRIVATE_CODE);
                } else {
                    String trim = et_des.getText().toString().trim();
                    int length = trim.length();
                    if (length < 50) {
                        CustomToast.makeText(AbountActivity.this, "“About” must be at least 50 characters", R.drawable.ic_toast_warming).show();
                    } else {
                        mPresenter.register(getIntent(), trim);
                    }
                }
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
                if (grantResults[0] == PERMISSION_GRANTED && grantResults.length > 0) { //有权限
                    // 获取到权限，作相应处理
                    getLoacation();
                } else {
                    NoTitleDialog
                            .newInstance(new NoTitleDialog.Callback() {
                                @Override
                                public void onDone(NoTitleDialog dialog) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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


    public void getLoacation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);   //请求时间间隔
        locationRequest.setFastestInterval(5000);//最快时间间隔
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                        }
                    }
                });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
