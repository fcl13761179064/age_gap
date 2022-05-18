package com.supersweet.luck.ui;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.User;
import com.supersweet.luck.dialog.NoTitleDialog;
import com.supersweet.luck.mvp.present.LoginPresenter;
import com.supersweet.luck.mvp.view.LoginView;
import com.supersweet.luck.serive.service.LocationService;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.utils.SoftInputUtil;
import com.supersweet.luck.utils.SoftIntPutUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.supersweet.luck.application.MyApplication.getContext;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SignInActivity extends BaseMvpActivity<LoginView, LoginPresenter> implements LoginView {
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.login_submitBtn)
    TextView login_submitBtn;
    @BindView(R.id.tv_forgitpassword)
    TextView tv_forgitpassword;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_back_signup)
    TextView tv_back_signup;
    private double altitude;
    private double latitude;

    private TranslateAnimation mShakeAnimation;
    private LocationService locationService;
    private static final int PRIVATE_CODE = 1315;//开启GPS权限
    private static final int PRIVATE_OPEN_LOCATION = 1314;//开启GPS权限

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        showGPSContacts();
        //触摸外部，键盘消失
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String username = SharePreferenceUtils.getString(this, Constance.SP_USER_NAME, "");
        et_username.setText(username);
    }

    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

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
                if (grantResults != null && grantResults[0] == PERMISSION_GRANTED && grantResults.length > 0) { //有权限
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

    @OnClick({R.id.tv_back_signup, R.id.login_submitBtn, R.id.tv_forgitpassword})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_back_signup:
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_submitBtn:
                mPresenter.login();
                SoftIntPutUtils.closeKeyboard(SignInActivity.this);
                break;
            case R.id.tv_forgitpassword:
                Intent intents = new Intent(SignInActivity.this, ForgitPasswordActivity.class);
                startActivity(intents);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initListener() {
        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()))) {
                    mPresenter.login();
                    SoftInputUtil.hideSysSoftInput(SignInActivity.this);
                    return true;
                }
                return false;
            }
        });

    }


    private TextWatcher edtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(getAccount()) && !TextUtils.isEmpty(getPassword())) {
                login_submitBtn.setEnabled(true);
            } else {
                login_submitBtn.setEnabled(false);
            }
        }
    };

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }


    @Override
    public String getAccount() {
        return et_username.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void loginSuccess(User data) {
        if (data != null && "0".equals(data.getCode())) {
            mPresenter.getLocation(latitude, altitude);
            SharePreferenceUtils.saveString(getContext(), Constance.SP_Login_Token, data.getToken());
            SharePreferenceUtils.saveString(this, Constance.SP_USER_NAME, et_username.getText().toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // CycleTimes动画重复的次数
            if (null == mShakeAnimation) {
                mShakeAnimation = new TranslateAnimation(0, 10, 0, 0);
                mShakeAnimation.setInterpolator(new CycleInterpolator(2));
                mShakeAnimation.setDuration(1000);
                mShakeAnimation.setRepeatMode(Animation.REVERSE);//设置反方向执行
            }

            login_submitBtn.startAnimation(mShakeAnimation);
            et_password.startAnimation(mShakeAnimation);
        }
    }

    @Override
    public void updateLocation(IntenetReposeBean o) {

    }


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
                if (locationService != null) {
                    locationService.unregisterListener(mListener);
                    locationService.stop();
                }
                latitude = location.getLatitude();
                altitude = location.getLongitude();
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
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationService != null) {
            locationService.unregisterListener(mListener);
            locationService.stop();
        }
    }


    @Override
    public void errorShake(int type, int CycleTimes, String code) {
        // CycleTimes动画重复的次数
        if (null == mShakeAnimation) {
            mShakeAnimation = new TranslateAnimation(0, 10, 0, 0);
            mShakeAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
            mShakeAnimation.setDuration(1000);
            mShakeAnimation.setRepeatMode(Animation.REVERSE);//设置反方向执行
        }
        if (type == 1) {
            login_submitBtn.startAnimation(mShakeAnimation);
        } else if (type == 2) {
            et_password.startAnimation(mShakeAnimation);
        } else {
            login_submitBtn.startAnimation(mShakeAnimation);
            et_password.startAnimation(mShakeAnimation);
        }
    }

    /**
     * 保持登录按钮始终不会被覆盖
     *
     * @param root
     * @param subView
     */
    private void keepLoginBtnNotOver(final View root, final View subView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                // 若不可视区域高度大于200，则键盘显示,其实相当于键盘的高度
                if (rootInvisibleHeight > 200) {
                    // 显示键盘时
                    int srollHeight = rootInvisibleHeight - (root.getHeight() - subView.getHeight()) - SoftIntPutUtils.getNavigationBarHeight(root.getContext());
                    if (srollHeight > 0) {//当键盘高度覆盖按钮时
                        root.scrollTo(0, srollHeight + 10);
                    }
                } else {
                    // 隐藏键盘时
                    root.scrollTo(0, 0);
                }
            }
        });
    }
}
