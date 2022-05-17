package com.supersweet.luck.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.User;
import com.supersweet.luck.mvp.present.EditModifyUserNamePresenter;
import com.supersweet.luck.mvp.present.RegisterPresenter;
import com.supersweet.luck.mvp.view.EditProfileModifyUseNameView;
import com.supersweet.luck.mvp.view.RegisterView;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
import com.supersweet.luck.widget.LastInputEditText;

import butterknife.BindView;

import static com.supersweet.luck.application.MyApplication.getContext;

public class EditModifyUsernameActivity extends BaseMvpActivity<EditProfileModifyUseNameView, EditModifyUserNamePresenter> implements EditProfileModifyUseNameView {

    @BindView(R.id.modify_username)
    LastInputEditText modify_username;
    @BindView(R.id.submitBtn)
    TextView submitBtn;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_editmodify_username;
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void initListener() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = modify_username.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                mPresenter.editSetting(0, s);
            }
        });
    }

    @Override
    protected EditModifyUserNamePresenter initPresenter() {
        return new EditModifyUserNamePresenter();
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void editUserNameSuccess(MyInfoBean s) {
        if (s.getCode() !=0){
            CustomToast.makeText(MyApplication.getContext(), s.getMsg(), R.drawable.ic_toast_warming).show();
            return;
        }
        SharePreferenceUtils.saveString(this, Constance.SP_USER_NAME, modify_username.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("userName",modify_username.getText().toString());
        setResult(1001,intent);
        finish();
    }
}
