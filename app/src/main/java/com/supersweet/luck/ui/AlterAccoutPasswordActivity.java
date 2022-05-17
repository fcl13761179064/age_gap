package com.supersweet.luck.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.mvp.present.AlterAccountPasswordPresenter;
import com.supersweet.luck.mvp.present.RegisterPresenter;
import com.supersweet.luck.mvp.view.AlterAccountPasswordView;
import com.supersweet.luck.mvp.view.RegisterView;
import com.supersweet.luck.utils.AppManager;
import com.supersweet.luck.utils.SoftInputUtil;
import com.supersweet.luck.widget.AppBar;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;

import butterknife.BindView;

public class AlterAccoutPasswordActivity extends BaseMvpActivity<AlterAccountPasswordView, AlterAccountPasswordPresenter> implements AlterAccountPasswordView {

    @BindView(R.id.alter_email_password_two)
    EditText alter_email_password_two;
    @BindView(R.id.alter_email_password_one)
    EditText alter_email_password_one;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.register_submitBtn)
    TextView register_submitBtn;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_account_password;
    }

    @Override
    protected void initView() {
    }


    @Override
    protected void initListener() {
        register_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftInputUtil.hideShow(v);
                String one = alter_email_password_one.getText().toString().trim();
                String two = alter_email_password_two.getText().toString().trim();
                String three = password.getText().toString().trim();

                if (TextUtils.isEmpty(one)) {
                    CustomToast.makeText(MyApplication.getContext(), "Password must be a minimum of six charactes.", R.drawable.ic_toast_warming).show();
                    return;
                }

                if (TextUtils.isEmpty(two) || two.length() < 6) {
                    CustomToast.makeText(MyApplication.getContext(), "New Password must be a minimum of six charactes.", R.drawable.ic_toast_warming).show();
                    return;
                }

                if (two.length() > 16) {
                    CustomToast.makeText(MyApplication.getContext(), "New Password must be a maximum of six charactes.", R.drawable.ic_toast_warming).show();
                    return;
                }
                if (one.equals(two)) {
                    CustomToast.makeText(MyApplication.getContext(), "Try again with a password that you haven't used before.", R.drawable.ic_toast_warming).show();
                    return;
                }
                if (!three.equals(two)) {
                    CustomToast.makeText(MyApplication.getContext(), "Your passwords do not match.", R.drawable.ic_toast_warming).show();
                    return;
                }
                if (TextUtils.isEmpty(three)) {
                    CustomToast.makeText(MyApplication.getContext(), "Confire Password must be a minimum of six charactes.", R.drawable.ic_toast_warming).show();
                    return;
                }
                mPresenter.alterAccountPassword(one, three);
            }
        });
    }

    @Override
    protected AlterAccountPasswordPresenter initPresenter() {
        return new AlterAccountPasswordPresenter();
    }


    @Override
    public void errorShake(String msg) {
        CustomToast.makeText(AlterAccoutPasswordActivity.this, msg, R.drawable.ic_toast_warming).show();
    }

    @Override
    public void AlterSuccess(String data) {
        CustomToast.makeText(AlterAccoutPasswordActivity.this, data, R.drawable.ic_toast_warming).show();
    }


}
