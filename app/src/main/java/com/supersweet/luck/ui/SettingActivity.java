package com.supersweet.luck.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.kyleduo.switchbutton.SwitchButton;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.dialog.DelectAccountDialog;
import com.supersweet.luck.dialog.HighingConsumeCoinDialog;
import com.supersweet.luck.dialog.NoTitleDialog;
import com.supersweet.luck.mvp.present.SettingPresenter;
import com.supersweet.luck.mvp.view.SettingView;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseMvpActivity<SettingView, SettingPresenter> implements SettingView {


    @BindView(R.id.switch_open)
    SwitchButton switch_open;
    @BindView(R.id.coin_three)
    TextView coin_three;
    @BindView(R.id.highlight_two)
    TextView highlight_two;
    @BindView(R.id.iv_highlight_btn)
    RelativeLayout iv_highlight_btn;
    @BindView(R.id.iv_coin_btn)
    RelativeLayout iv_coin_btn;
    private MyCount mc;
    private long expireTimeSeconds;
    private int userCoins;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_layout;
    }

    @Override
    protected void initView() {
        userCoins = getIntent().getIntExtra("userCoins", 0);
        coin_three.setText(userCoins + "");
        if (AppData.isShow == 1) {
            switch_open.setChecked(false);
        } else {
            switch_open.setChecked(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getMyinfo();
    }

    @Override
    protected void initListener() {
        iv_highlight_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighingConsumeCoinDialog
                        .newInstance(new HighingConsumeCoinDialog.Callback() {
                            @Override
                            public void onDone(HighingConsumeCoinDialog dialog) {
                                dialog.dismissAllowingStateLoss();
                                mPresenter.BuyHighLightCoin();
                            }

                            @Override
                            public void onCancel(HighingConsumeCoinDialog dialog) {
                                dialog.dismissAllowingStateLoss();
                            }
                        })
                        .setContent("10",userCoins+"")
                        .show(getSupportFragmentManager(), "dialog");
            }
        });

        iv_coin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, BuyCoinPageActivity.class);
                startActivity(intent);
            }
        });
    }


    @OnClick({R.id.abount, R.id.account, R.id.feedback, R.id.block_members, R.id.switch_open, R.id.login_out, R.id.delect_account})
    void handleCbClicked(View view) {
        switch (view.getId()) {
            case R.id.abount:
                Intent intent = new Intent(this, SettingCommonActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;

            case R.id.account:
                Intent intents = new Intent(this, SettingCommonActivity.class);
                intents.putExtra("type", "2");
                startActivity(intents);
                break;
            case R.id.feedback:
                Intent feedbook = new Intent(this, FeedBookActivity.class);
                startActivity(feedbook);
                break;
            case R.id.block_members:
                Intent blockMember = new Intent(this, BlockMembersActivity.class);
                startActivity(blockMember);

                break;
            case R.id.switch_open:
                if (AppData.isShow == 1) {
                    mPresenter.HideProfile(0);
                    AppData.isShow = 0;
                } else {
                    mPresenter.HideProfile(1);
                    AppData.isShow = 1;
                }

                break;
            case R.id.delect_account:
                DelectAccountDialog
                        .newInstance(new DelectAccountDialog.Callback() {
                            @Override
                            public void onDone(DelectAccountDialog delectAccountDialog, String password, String Leaving_reason) {
                                if (TextUtils.isEmpty(password)) {
                                    CustomToast.makeText(MyApplication.getContext(), "Password is required...", R.drawable.ic_toast_warming).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(Leaving_reason)) {
                                    CustomToast.makeText(MyApplication.getContext(), "Leaving reason is required...", R.drawable.ic_toast_warming).show();
                                    return;
                                }
                                mPresenter.DelectAccount(password, Leaving_reason, delectAccountDialog);
                            }
                        })
                        .show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.login_out:
                NoTitleDialog
                        .newInstance(new NoTitleDialog.Callback() {
                            @Override
                            public void onDone(NoTitleDialog dialog) {
                                mPresenter.logout();
                                dialog.dismissAllowingStateLoss();

                            }

                            @Override
                            public void onCancel(NoTitleDialog dialog) {
                                dialog.dismissAllowingStateLoss();
                            }
                        })
                        .setContent("Are you sure to log out?")
                        .show(getSupportFragmentManager(), "dialog");
                break;
        }
    }

    @Override
    protected SettingPresenter initPresenter() {
        return new SettingPresenter();
    }

    @Override
    public void errorShake(String msg) {
        CustomToast.makeText(MyApplication.getContext(), msg, R.drawable.ic_toast_warming).show();
    }

    @Override
    public void logoutsuccess(IntenetReposeBean s) {
        SharePreferenceUtils.remove(MyApplication.getContext(), Constance.SP_Login_Token);
        SharePreferenceUtils.remove(MyApplication.getContext(), Constance.SP_Refresh_Token);
        SharePreferenceUtils.remove(MyApplication.getContext(), Constance.SP_HEADER);
        //跳转到首页
        Intent intent = new Intent(MyApplication.getContext(), GuideActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.getContext().startActivity(intent);

    }

    @Override
    public void BuyError(String toString) {
        CustomToast.makeText(MyApplication.getContext(), toString, R.drawable.ic_toast_warming).show();
    }

    @Override
    public void BuyHighCoinSuccess(IntenetReposeBean data) {
        if ("0".equals(data.getCode()) && "success".equalsIgnoreCase(data.getMsg())) {
            CustomToast.makeText(this, "Purchase successful...", R.drawable.ic_toast_warming).show();
            expireTimeSeconds = 86400000;
            mc = new MyCount(expireTimeSeconds * 1000, 1000);
            mc.start();
        } else {
            if ("Your Are Balance is insufficient.".equalsIgnoreCase(data.getMsg())) {//你的余额不足
                Intent intent = new Intent(this, BuyCoinPageActivity.class);
                startActivity(intent);
            } else {
                CustomToast.makeText(this, data.getMsg().toString(), R.drawable.ic_toast_warming).show();
            }
        }
    }

    @Override
    public void BuyConsumeCoinSuccess(Object o) {


    }

    @Override
    public void MyInfo_success(MyInfoBean s) {
        if ( s != null &&  s.getUser() !=null && s.getUser().getExpireTimeSeconds() != 0) {
            expireTimeSeconds = s.getUser().getExpireTimeSeconds();
            mc = new MyCount(s.getUser().getExpireTimeSeconds() * 1000, 60000);
            mc.start();
        } else {
            highlight_two.setText("00:00");
        }

        if ( s != null &&  s.getUser() !=null) {
            userCoins = s.getUser().getUserCoin();
            coin_three.setText(userCoins + "");
        }
    }


    /*定义一个倒计时的内部类*/
    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            highlight_two.setText("00:00");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (!SettingActivity.this.isFinishing()) {
                long day = millisUntilFinished / (1000 * 24 * 60 * 60); //单位天
                long hour = (millisUntilFinished - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60);
                if (hour == 0) {
                    hour = 24;
                }
                //单位时
                long minute = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60);
                //单位分
                long second = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                //单位秒
                if (hour >= 10) {
                    if (minute >= 10) {
                        highlight_two.setText(hour + ":" + minute);
                    } else {
                        highlight_two.setText(hour + ":" + "0" + minute);
                    }

                } else {
                    if (minute >= 10) {
                        highlight_two.setText("0" + hour + ":" + minute);
                    } else {
                        highlight_two.setText("0" + hour + ":" + "0" + minute);
                    }

                }

            }
        }
    }


    /* 记得关闭,负责内存溢出 */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mc != null) {
            mc.cancel();
            mc = null;
        }
    }
}
