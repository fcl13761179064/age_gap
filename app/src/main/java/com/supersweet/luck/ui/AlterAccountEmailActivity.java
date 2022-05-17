package com.supersweet.luck.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.mvp.present.AlterAccountPasswordPresenter;
import com.supersweet.luck.mvp.view.AlterAccountPasswordView;
import com.supersweet.luck.utils.SoftInputUtil;
import com.supersweet.luck.widget.AppBar;
import com.supersweet.luck.widget.CustomToast;

import butterknife.BindView;

public class AlterAccountEmailActivity extends BaseMvpActivity<AlterAccountPasswordView, AlterAccountPasswordPresenter> implements AlterAccountPasswordView {

    @BindView(R.id.alter_email_password_two)
    EditText alter_email_password_two;
    @BindView(R.id.alter_email_password_one)
    EditText alter_email_password_one;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.register_submitBtn)
    TextView register_submitBtn;
    @BindView(R.id.appBar)
    AppBar appBar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_email;
    }

    @Override
    protected void initView() {
        appBar.setCenterText("Change Email Address");
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
                    CustomToast.makeText(MyApplication.getContext(), "New Email is required...", R.drawable.ic_toast_warming).show();
                    return;
                }
                if (TextUtils.isEmpty(two)) {
                    CustomToast.makeText(MyApplication.getContext(), "Please confire you new email address.", R.drawable.ic_toast_warming).show();
                    return;
                }
                if (!one.equals(two)) {
                    CustomToast.makeText(MyApplication.getContext(), "Your email do not match.", R.drawable.ic_toast_warming).show();
                    return;
                }
                if (TextUtils.isEmpty(three)) {
                    CustomToast.makeText(MyApplication.getContext(), "Password is required.", R.drawable.ic_toast_warming).show();
                    return;
                }
                if (three.length() < 6) {
                    CustomToast.makeText(MyApplication.getContext(), "Password must be a minimum of six charactes.", R.drawable.ic_toast_warming).show();
                    return;
                }
                mPresenter.UpdataYouXiang(one, three);
            }
        });
    }

    @Override
    protected AlterAccountPasswordPresenter initPresenter() {
        return new AlterAccountPasswordPresenter();
    }


    @Override
    public void errorShake(String msg) {
        CustomToast.makeText(this, msg, R.drawable.ic_toast_warming).show();
    }

    @Override
    public void AlterSuccess(String data) {
        CustomToast.makeText(this, data, R.drawable.ic_toast_warming).show();
    }


}
