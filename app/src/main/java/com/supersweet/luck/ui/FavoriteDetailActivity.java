package com.supersweet.luck.ui;


import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.FavotiteDetailAdapter;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.banner.BannerView;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.bean.PhotoBean;
import com.supersweet.luck.dialog.CustomSheet;
import com.supersweet.luck.dialog.HighingConsumeCoinDialog;
import com.supersweet.luck.dialog.NoTitleDialog;
import com.supersweet.luck.dialog.ReportUserDialog;
import com.supersweet.luck.dialog.SingleConfireDialog;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.mvp.present.FavoriteDetailPresenter;
import com.supersweet.luck.mvp.view.FavoriteDetailView;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
import com.supersweet.luck.widget.MyDatas;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FavoriteDetailActivity extends BaseMvpActivity<FavoriteDetailView, FavoriteDetailPresenter> implements FavoriteDetailView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.close)
    RelativeLayout close;
    @BindView(R.id.rl_like_unlike)
    RelativeLayout rl_like_unlike;
    @BindView(R.id.iv_like_unlike_img)
    ImageView iv_like_unlike_img;
    @BindView(R.id.iv_block_user)
    ImageView iv_block_user;
    @BindView(R.id.rl_chat_btn)
    RelativeLayout rl_chat_btn;

    private int UserId;
    private FavotiteDetailAdapter favotiteDetailAdapter;
    private List<String> titles;
    private boolean isLike = false;
    private View inflate;
    private String mResulteData;
    private int videoMinuteUseCoin;
    private OtherUserInfoBean UserInfo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_favirite_detail;
    }


    @Override
    protected void initView() {
        titles = new ArrayList();
        UserId = getIntent().getIntExtra("UserId", 1);
        mPresenter.getOtherUserInfo(UserId + "");
        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        favotiteDetailAdapter = new FavotiteDetailAdapter();
        recyclerview.setAdapter(favotiteDetailAdapter);
        favotiteDetailAdapter.bindToRecyclerView(recyclerview);
        favotiteDetailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

    }


    @OnClick({R.id.rl_like_unlike, R.id.close, R.id.iv_block_user})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.rl_like_unlike:
                if (!isLike) {
                    iv_like_unlike_img.setBackgroundResource(R.mipmap.detail_profile_favorate);
                    mPresenter.like(UserId);
                } else {
                    iv_like_unlike_img.setBackgroundResource(R.mipmap.detail_profile_unfavorate);
                    mPresenter.unlike(UserId);
                }

                isLike = !isLike;
                break;
            case R.id.iv_block_user:
                CustomSheet show = new CustomSheet.Builder(this)
                        .setText("Block User", "Report User")
                        .show(new CustomSheet.CallBack() {
                            @Override
                            public void callback(int index) {
                                if (index == 0) {
                                    NoTitleDialog
                                            .newInstance(new NoTitleDialog.Callback() {
                                                @Override
                                                public void onDone(NoTitleDialog dialog) {
                                                    mPresenter.blockUser(UserId);
                                                    dialog.dismissAllowingStateLoss();

                                                }

                                                @Override
                                                public void onCancel(NoTitleDialog dialog) {
                                                    dialog.dismissAllowingStateLoss();
                                                }
                                            })
                                            .setContent("are you sure to block this member")
                                            .show(getSupportFragmentManager(), "dialog");
                                } else {
                                    ReportUserDialog
                                            .newInstance(new ReportUserDialog.Callback() {
                                                @Override
                                                public void onDone(ReportUserDialog reportUserDialog, String reason, String question, String imgurl) {
                                                    if (TextUtils.isEmpty(reason)) {
                                                        CustomToast.makeText(MyApplication.getContext(), "Please choose a reason.", R.drawable.ic_toast_warming).show();
                                                        return;
                                                    }
                                                    mPresenter.ReportUser(UserId, reason, question, imgurl, reportUserDialog);

                                                }

                                            })
                                            .setContent("are you sure to block this member")
                                            .show(getSupportFragmentManager(), "dialog");
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        });

                break;
            case R.id.close:
                Intent intent = new Intent();
                intent.putExtra("reesulteData", mResulteData);
                setResult(10001, intent);
                finish();
                break;
           /* case R.id.rl_vedio_chat:
                HighingConsumeCoinDialog
                        .newInstance(new HighingConsumeCoinDialog.Callback() {
                            @Override
                            public void onDone(HighingConsumeCoinDialog dialog) {
                                dialog.dismissAllowingStateLoss();
                                mPresenter.VideoConsumeCoin(UserInfo.getUserId() + "",UserInfo.ca);
                            }

                            @Override
                            public void onCancel(HighingConsumeCoinDialog dialog) {
                                dialog.dismissAllowingStateLoss();
                            }
                        })
                        .setContent(videoMinuteUseCoin+"","")
                        .show(getSupportFragmentManager(), "dialog");

                break;*/
        }
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected FavoriteDetailPresenter initPresenter() {
        return new FavoriteDetailPresenter();
    }


    @Override
    public void errorShake(String msg, ReportUserDialog reportUserDialog) {
        reportUserDialog.dismissAllowingStateLoss();
        ToastUtils.showShortToast(msg);
    }

    @Override
    public void errorShakes(String msg) {
        ToastUtils.showShortToast(msg);
    }

    @Override
    public void likeSuccess(String success) {
        this.mResulteData = success;
    }

    @Override
    public void unlikeSuccess(String success) {
        this.mResulteData = success;
    }

    @Override
    public void ReportUserSuccess(Object success, ReportUserDialog reportUserDialog) {
        SingleConfireDialog singleConfireDialog = new SingleConfireDialog(this, "Thank you for your feedback. you should receive a response within 24 hours.");
        singleConfireDialog.setGravity(Gravity.CENTER);
        singleConfireDialog.setOnSureClick("OK ", new SingleConfireDialog.OnSureClick() {
            @Override
            public void click(Dialog dialog) {
                reportUserDialog.dismissAllowingStateLoss();
                dialog.dismiss();
            }
        });
        singleConfireDialog.show();
    }

    @Override
    public void BlockUserSuccess(Object success) {
        Intent intent = new Intent();
        intent.putExtra("reesulteData", "success");
        setResult(10001, intent);
        finish();
    }


    @Override
    public void getOtherUserInfoSuccess(OtherUserInfoBean data, List<PhotoBean> photoBeans) {
        List<String> list = new ArrayList<String>();
        list.add(data.getAvatar());
        if (photoBeans != null && photoBeans.size() > 0) {
            for (int x = 0; x < photoBeans.size(); x++) {
                String photoPath = photoBeans.get(x).getPhotoPath();
                list.add(photoPath);
            }
        }
        titles.add("Height" + "," + data.getHeight());
        titles.add("Body Type" + "," + data.getBody());
        titles.add("Hair Color" + "," + data.getHair());
        titles.add("Relationship" + "," + data.getUserStatus());
        titles.add("Education" + "," + data.getEducation());
        titles.add("Ethnicity" + "," + data.getEthnicity());
        titles.add("Drinking" + "," + data.getDrinking());
        titles.add("Smoking" + "," + data.getSmoking());
        titles.add("Children" + "," + data.getChildren());

        AppData.OhterfoBean = data;
        if (data.getIfConnection() != 1) {
            isLike = false;
            iv_like_unlike_img.setBackgroundResource(R.mipmap.detail_profile_unfavorate);
        } else {
            isLike = true;
            iv_like_unlike_img.setBackgroundResource(R.mipmap.detail_profile_favorate);
        }

        inflate = LayoutInflater.from(this).inflate(R.layout.favotite_detail_head, null, false);
        BannerView head = inflate.findViewById(R.id.my_head);
        TextView qscoreText = (TextView) (inflate.findViewById(R.id.tv_credit_fen));
        qscoreText.setText(data.getQscore());
        head.setData(list, data.getSex());
        //  GlideLocalImageUtils.displayBigOrSmallShadowImage(MyApplication.getContext(), head, data.getSex(), data.getAvatar(), "big");
        TextView tv_user_name = inflate.findViewById(R.id.tv_user_name);
        TextView my_desc = inflate.findViewById(R.id.my_desc);
        TextView abount = inflate.findViewById(R.id.abount);
        ImageView iv_month_pay_vip = inflate.findViewById(R.id.iv_month_pay_vip);
        tv_user_name.setText(data.getUserName());
        if (data.getAbout() != null && data.getAbout().length() > 0) {
            abount.setText(data.getAbout());
        } else {
            abount.setVisibility(View.GONE);
        }

        if (data.getMonthFlag() ==-1){
            iv_month_pay_vip.setVisibility(View.GONE);
        }else {
            iv_month_pay_vip.setVisibility(View.VISIBLE);
        }
         this.UserInfo=data;
        my_desc.setText(MyDatas.sextosting(data.getSex()) + " , " + data.getAge() + " , " + data.getStation());
        favotiteDetailAdapter.addHeaderView(inflate);
        favotiteDetailAdapter.addData(titles);
        rl_chat_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoriteDetailActivity.this, ChatActivity.class);
                intent.putExtra("sendId", data.getUserId() + "");
                intent.putExtra("userName", data.getUserName());
                intent.putExtra("userImg", data.getAvatar());
                intent.putExtra("userSex", data.getSex());
                startActivity(intent);
            }

        });
    }

}
