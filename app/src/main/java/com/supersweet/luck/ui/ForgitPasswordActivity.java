package com.supersweet.luck.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.supersweet.luck.R;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.mvp.present.ForgitPresenter;
import com.supersweet.luck.mvp.view.ForgitView;
import com.supersweet.luck.utils.SoftIntPutUtils;
import com.supersweet.luck.widget.CustomToast;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgitPasswordActivity extends BaseMvpActivity<ForgitView, ForgitPresenter> implements ForgitView {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.userName)
    EditText mUserName;


    @Override
    protected int getLayoutId() {
        return R.layout.forgit_password;
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void initListener() {

    }

    @OnClick({R.id.iv_back, R.id.tv_next, R.id.tv_back_signup})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_back_signup:
                Intent intent = new Intent(ForgitPasswordActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_next:
                String mEmail= email.getText().toString();
                String userName= mUserName.getText().toString();
                mPresenter.forgitpassword(userName,mEmail);
                SoftIntPutUtils.closeKeyboard(ForgitPasswordActivity.this);
                break;
            case R.id.iv_back:
                 finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected ForgitPresenter initPresenter() {
        return new ForgitPresenter();
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {
        CustomToast.makeText(this, msg, R.drawable.ic_toast_warming).show();
    }

    @Override
    public void ForgitPasswordSuccess(IntenetReposeBean data) {
        if (data!=null && "0".equals(data.getCode())){
            CustomToast.makeText(this, "submit success...", R.drawable.ic_toast_warming).show();
            finish();
        }

    }
}
