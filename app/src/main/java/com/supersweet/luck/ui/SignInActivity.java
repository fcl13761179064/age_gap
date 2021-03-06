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
    private static final int PRIVATE_CODE = 1315;//??????GPS??????
    private static final int PRIVATE_OPEN_LOCATION = 1314;//??????GPS??????

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        showGPSContacts();
        //???????????????????????????
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
     * ??????GPS???????????????????????????
     */
    public void showGPSContacts() {
        LocationManager lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//??????????????????
            if (Build.VERSION.SDK_INT >= 23) { //???????????????android6.0???????????????????????????????????????????????????
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {// ??????????????????????????????
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
                if (Build.VERSION.SDK_INT >= 23) { //???????????????android6.0???????????????????????????????????????????????????
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {// ??????????????????????????????
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
     * Android6.0???????????????????????????
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
//             requestCode????????????????????????????????????checkSelfPermission?????????
            case PRIVATE_CODE:
                //?????????????????????permissions?????????null.
                if (grantResults != null && grantResults[0] == PERMISSION_GRANTED && grantResults.length > 0) { //?????????
                    // ?????????????????????????????????
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
            // CycleTimes?????????????????????
            if (null == mShakeAnimation) {
                mShakeAnimation = new TranslateAnimation(0, 10, 0, 0);
                mShakeAnimation.setInterpolator(new CycleInterpolator(2));
                mShakeAnimation.setDuration(1000);
                mShakeAnimation.setRepeatMode(Animation.REVERSE);//?????????????????????
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
        //??????locationservice????????????????????????????????????1???location???????????????????????????????????????????????????activity?????????????????????????????????locationservice?????????
        locationService.registerListener(mListener);
        //????????????
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        }
        locationService.start();// ??????SDK
    }

    /*****
     *
     * ???????????????????????????onReceiveLocation???????????????????????????????????????????????????????????????
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        /**
         * ????????????????????????
         * @param location ????????????
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
         * ?????????????????????????????????????????????????????????????????????????????????????????????
         * @param locType ??????????????????
         * @param diagnosticType ???????????????1~9???
         * @param diagnosticMessage ???????????????????????????
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
        // CycleTimes?????????????????????
        if (null == mShakeAnimation) {
            mShakeAnimation = new TranslateAnimation(0, 10, 0, 0);
            mShakeAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
            mShakeAnimation.setDuration(1000);
            mShakeAnimation.setRepeatMode(Animation.REVERSE);//?????????????????????
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
     * ???????????????????????????????????????
     *
     * @param root
     * @param subView
     */
    private void keepLoginBtnNotOver(final View root, final View subView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                // ??????root????????????????????????
                root.getWindowVisibleDisplayFrame(rect);
                // ??????root?????????????????????????????????(?????????View?????????????????????)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                // ??????????????????????????????200??????????????????,??????????????????????????????
                if (rootInvisibleHeight > 200) {
                    // ???????????????
                    int srollHeight = rootInvisibleHeight - (root.getHeight() - subView.getHeight()) - SoftIntPutUtils.getNavigationBarHeight(root.getContext());
                    if (srollHeight > 0) {//??????????????????????????????
                        root.scrollTo(0, srollHeight + 10);
                    }
                } else {
                    // ???????????????
                    root.scrollTo(0, 0);
                }
            }
        });
    }
}
