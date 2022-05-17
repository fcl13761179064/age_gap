package com.supersweet.luck.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.supersweet.luck.R;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.mvp.present.CheckAccountPenter;
import com.supersweet.luck.mvp.present.RegisterPresenter;
import com.supersweet.luck.mvp.view.CheckAccountView;
import com.supersweet.luck.mvp.view.RegisterView;
import com.supersweet.luck.utils.AppManager;
import com.supersweet.luck.utils.SoftInputUtil;
import com.supersweet.luck.utils.SoftIntPutUtils;
import com.supersweet.luck.widget.AppData;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpActivity extends BaseMvpActivity<CheckAccountView, CheckAccountPenter> implements CheckAccountView {
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_enail)
    EditText et_enail;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.register_submitBtn)
    TextView register_submitBtn;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    private TranslateAnimation mShakeAnimation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_signup;
    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.register_submitBtn, R.id.tv_back_signin, R.id.tv_agree, R.id.tv_policy})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.register_submitBtn:
                mPresenter.checkAccount();
                SoftIntPutUtils.closeKeyboard(SignUpActivity.this);
                break;
            case R.id.tv_back_signin:
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_agree:
                Intent agree = new Intent(SignUpActivity.this, PolicyActivity.class);
                agree.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                agree.putExtra("url", "2");
                startActivity(agree);
                break;
            case R.id.tv_policy:
                Intent Policy = new Intent(SignUpActivity.this, PolicyActivity.class);
                Policy.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Policy.putExtra("url", "1");
                startActivity(Policy);
                break;
            default:
                break;
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
        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()))) {
                    mPresenter.checkAccount();
                    SoftInputUtil.hideSysSoftInput(SignUpActivity.this);
                    return true;
                }
                return false;
            }
        });

    }


    @Override
    protected CheckAccountPenter initPresenter() {
        return new CheckAccountPenter();
    }


    @Override
    public String getUserName() {
        return et_username.getText().toString();
    }


    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public String getEmal() {
        return et_enail.getText().toString();
    }

    @Override
    public boolean getCheckBox() {
        return checkbox.isChecked();
    }

    @Override
    public void RegistSuccess(Boolean data) {
        String userName = et_username.getText().toString().trim();
        String email = et_enail.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        Intent intent = new Intent(this, ChooseSexActivity.class);
        intent.putExtra("userName", userName);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
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
            et_username.startAnimation(mShakeAnimation);
        } else if (type == 2) {
            et_password.startAnimation(mShakeAnimation);
        } else if (type == 3) {
            et_enail.startAnimation(mShakeAnimation);
        } else {
            et_username.startAnimation(mShakeAnimation);
            et_password.startAnimation(mShakeAnimation);
            et_enail.startAnimation(mShakeAnimation);
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
