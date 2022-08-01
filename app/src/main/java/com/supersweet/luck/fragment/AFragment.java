package com.supersweet.luck.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.android.pushservice.PushManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.CardAdapter;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpFragment;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.dialog.CustomSheet;
import com.supersweet.luck.dialog.MatchDialog;
import com.supersweet.luck.dialog.NoTitleDialog;
import com.supersweet.luck.dialog.ReportUserDialog;
import com.supersweet.luck.dialog.SingleConfireDialog;
import com.supersweet.luck.mvp.present.CardSearchPresenter;
import com.supersweet.luck.mvp.view.CardSearchView;
import com.supersweet.luck.pictureselector.PictureBean;
import com.supersweet.luck.pictureselector.PictureSelector;
import com.supersweet.luck.pushinfo.Utils;
import com.supersweet.luck.rxbus.Myinfo;
import com.supersweet.luck.rxbus.RxBus;
import com.supersweet.luck.signature.GenerateTestUserSig;
import com.supersweet.luck.ui.BuyCoinPageActivity;
import com.supersweet.luck.ui.ChatActivity;
import com.supersweet.luck.ui.FavoriteDetailActivity;
import com.supersweet.luck.ui.GooglePayRecordActivity;
import com.supersweet.luck.utils.FastClickUtils;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
import com.supersweet.luck.widget.ItemTouchCallback;
import com.supersweet.luck.widget.OverLayCardLayoutManager;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by fanchunlei on 2020/10/1.
 * Blog:http://blog.csdn.net/student9128
 * Describe：the first fragment
 */

public class AFragment extends BaseMvpFragment<CardSearchView, CardSearchPresenter> implements CardSearchView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.to_left)
    ImageView to_left;
    @BindView(R.id.to_right)
    ImageView to_right;
    @BindView(R.id.rl_layout)
    RelativeLayout rl_layout;
    @BindView(R.id.iv_rotation)
    ImageView iv_rotation;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.iv_head_img)
    RelativeLayout iv_head_img;
    @BindView(R.id.notice_no_person)
    TextView notice_no_person;
    @BindView(R.id.ll_updata_head)
    LinearLayout ll_updata_head;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_highlinged_right)
    ImageView iv_highlinged_right;
    @BindView(R.id.iv_highlinged_left)
    ImageView iv_highlinged_left;
    @BindView(R.id.tv_buy_highing_coin)
    TextView tv_buy_highing_coin;
    @BindView(R.id.highlight_btn_layout)
    LinearLayout highlight_btn_layout;

    private List<SeachPeopleBean> mDatas;
    private CardAdapter cardAdapter;
    private ItemTouchCallback callback;
    private int count = 1;
    public static final String TAG = "FragmetOne";
    private String img_path;
    private AFragment.fragmentOneToMainActivity fragmentOneToMainActivity;
    private int userId;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_card;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentOneToMainActivity = (fragmentOneToMainActivity) getActivity();
    }

    public interface fragmentOneToMainActivity {
        void fromFragmentOne(String head_avatar);
    }


    @Override
    protected void initView(View view) {
        AppData.verified = "-1";
        InitLocation();
        cardAdapter = new CardAdapter(R.layout.item_renren_layout, mDatas) {
            @Override
            public void setFource() {
                blockUser();
            }
        };
        recyclerView.setAdapter(cardAdapter);
        OverLayCardLayoutManager overLayCardLayoutManager = new OverLayCardLayoutManager(MyApplication.getContext());
        overLayCardLayoutManager.setTopOffset(1);
        recyclerView.setLayoutManager(overLayCardLayoutManager);
        callback = new ItemTouchCallback();
        callback.setSwipeListener(new ItemTouchCallback.OnSwipeListener() {
            @Override
            public void onSwiped(int adapterPosition, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    userId = mDatas.get(adapterPosition).getUserId();
                    mPresenter.unlike(userId);
                    Log.d("like_or_unlike", "left");
                } else if (direction == ItemTouchHelper.RIGHT) {
                    userId = mDatas.get(adapterPosition).getUserId();
                    mPresenter.like(userId);
                    Log.d("like_or_unlike", "right");
                }
                mDatas.remove(adapterPosition);
                recyclerView.getAdapter().notifyItemRemoved(adapterPosition);
                to_left.setTranslationY(0);
                to_right.setTranslationY(0);
                to_left.setScaleX(1);
                to_right.setScaleX(1);
                to_left.setScaleY(1);
                to_right.setScaleY(1);
                recyclerView.getAdapter().notifyDataSetChanged();
                if (mDatas.size() == 0) {
                    iv_head_img.setVisibility(View.VISIBLE);
                    notice_no_person.setVisibility(View.VISIBLE);
                    String path = SharePreferenceUtils.getString(getContext(), Constance.SP_HEADER, "");
                    String sex = SharePreferenceUtils.getString(getContext(), Constance.SP_SEX, "1");
                    GlideLocalImageUtils.displayBigOrSmallShadowImage(getActivity(), iv_img, sex, path, "small");
                    RotateAnimation rotateAnim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnim.setDuration(2000);//动画持续时间时间
                    rotateAnim.setInterpolator(new LinearInterpolator()); //添加插值器，下面会有说明
                    rotateAnim.setFillAfter(true);
                    rotateAnim.setRepeatCount(1000000);
                    iv_rotation.startAnimation(rotateAnim);
                    rl_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSwipeTo(RecyclerView.ViewHolder viewHolder, float offset) {
                if (offset < -50) {
                    to_left.setTranslationY(offset / 3);
                    to_left.setScaleX(1 + Math.abs(offset) / 1000);
                    to_left.setScaleY(1 + Math.abs(offset) / 1000);
                } else if (offset > 50) {
                    to_right.setTranslationY(-offset / 3);
                    to_right.setScaleX(1 + Math.abs(offset) / 1000);
                    to_right.setScaleY(1 + Math.abs(offset) / 1000);
                } else if (offset == 0) {
                    to_left.setTranslationY(0);
                    to_right.setTranslationY(0);
                    to_left.setScaleX(1);
                    to_right.setScaleX(1);
                    to_left.setScaleY(1);
                    to_right.setScaleY(1);
                }
            }

            @Override
            public void noCardData() {//没有数据了
                count++;
                mPresenter.card_continue_search(AppData.city, count, AppData.search_sex, "-1", "18", "88");
            }
        });
        new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);

    }

    public void blockUser() {
        CustomSheet show = new CustomSheet.Builder(getActivity())
                .setText("Block User", "Report User")
                .show(new CustomSheet.CallBack() {
                    @Override
                    public void callback(int index) {
                        if (index == 0) {
                            NoTitleDialog
                                    .newInstance(new NoTitleDialog.Callback() {
                                        @Override
                                        public void onDone(NoTitleDialog dialog) {
                                            mPresenter.blockUser(userId);
                                            dialog.dismissAllowingStateLoss();

                                        }

                                        @Override
                                        public void onCancel(NoTitleDialog dialog) {
                                            dialog.dismissAllowingStateLoss();
                                        }
                                    })
                                    .setContent("are you sure to block this member")
                                    .show(getFragmentManager(), "dialog");
                        } else {
                            ReportUserDialog
                                    .newInstance(new ReportUserDialog.Callback() {
                                        @Override
                                        public void onDone(ReportUserDialog reportUserDialog, String reason, String question, String imgurl) {
                                            if (TextUtils.isEmpty(reason)) {
                                                CustomToast.makeText(MyApplication.getContext(), "Please choose a reason.", R.drawable.ic_toast_warming).show();
                                                return;
                                            }
                                            mPresenter.ReportUser(userId, reason, question, imgurl, reportUserDialog);

                                        }

                                    })
                                    .setContent("are you sure to block this member")
                                    .show(getFragmentManager(), "dialog");
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    @OnClick({R.id.tv_next, R.id.to_left, R.id.to_right, R.id.iv_highlinged_right, R.id.iv_highlinged_left, R.id.tv_buy_highing_coin})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_next:
                PictureSelector
                        .create(AFragment.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true);
                break;
            case R.id.to_left:
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                callback.toLeft(recyclerView);
                break;
            case R.id.to_right:
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                callback.toRight(recyclerView);
                break;
            case R.id.iv_highlinged_right:
            case R.id.tv_buy_highing_coin:
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                mPresenter.BuyHighLightCoin();
                if (mDatas.size() > 0) {
                    showProgress();
                } else {
                    hiddenPersons();
                }
                highlight_btn_layout.setVisibility(View.GONE);
                break;
            case R.id.iv_highlinged_left:
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                if (mDatas.size() > 0) {
                    showPersons();
                } else {
                    hiddenPersons();
                }
                highlight_btn_layout.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


    @Override
    protected void initListener() {
        RxBus.getDefault().subscribe(this, "filter_condition", new RxBus.Callback<Myinfo>() {
            @Override
            public void onEvent(Myinfo chooseSex) {
                String sex = chooseSex.getChooseSex();
                String maxAge = chooseSex.getMaxAge();
                String minAge = chooseSex.getMinAge();
                String countryCode = chooseSex.getChooseCountryCode();
                mPresenter.card_search(countryCode, 1, sex, "-1", minAge, maxAge);
            }
        });


        RxBus.getDefault().subscribe(this, "alter_head", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                String path = SharePreferenceUtils.getString(getContext(), Constance.SP_HEADER, "");
                String sex = SharePreferenceUtils.getString(getContext(), Constance.SP_SEX, "1");
                GlideLocalImageUtils.displayBigOrSmallShadowImage(getActivity(), iv_img, sex, path, "small");
            }
        });

        cardAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    SeachPeopleBean data = (SeachPeopleBean) adapter.getData().get(position);
                    Intent intent = new Intent(getActivity(), FavoriteDetailActivity.class);
                    intent.putExtra("UserId", data.getUserId());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }


    @Override
    protected void initData() {

    }


    @Override
    public void errorShake(String msg) {
        hideProgress();
    }

    @Override
    public void errorShakes(String msg, ReportUserDialog reportUserDialog) {

    }

    @Override
    public void card_serch_success(List<SeachPeopleBean> data) {
        int userId = AppData.MyInfoBean.getUser().getUserId();
        initTuikit(userId + "");
        setTags(userId);
        String head_avatar = AppData.MyInfoBean.getUser().getAvatar();
        SharePreferenceUtils.saveString(getContext(), Constance.SP_SEX, AppData.MyInfoBean.getUser().getSex());
        SharePreferenceUtils.saveString(MyApplication.getContext(), Constance.SP_HEADER, head_avatar);
        fragmentOneToMainActivity.fromFragmentOne(head_avatar);
        mDatas = data;
        if (TextUtils.isEmpty(head_avatar)) {
            SharePreferenceUtils.saveString(MyApplication.getContext(), Constance.SP_HEADER, "");
            ll_updata_head.setVisibility(View.VISIBLE);
            rl_layout.setVisibility(View.GONE);
            notice_no_person.setVisibility(View.GONE);
            iv_head_img.setVisibility(View.GONE);
        } else {
            if (data.size() > 0) {
                showPersons();
            } else {
                hiddenPersons();
            }
        }
    }

    @Override
    public void card_continue_serch_success(List<SeachPeopleBean> data) {
        mDatas = data;
        String highLightFlag = AppData.MyInfoBean.getUser().getHighLightFlag();
        if ("1".equals(highLightFlag)) {
            if (mDatas.size() > 0) {
                showPersons();
            } else {
                hiddenPersons();
            }
            highlight_btn_layout.setVisibility(View.GONE);
        } else {
            highlight_btn_layout.setVisibility(View.VISIBLE);
            rl_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void errorcontinueShake(String msg) {
        highlight_btn_layout.setVisibility(View.GONE);
        hiddenPersons();
    }

    public void showPersons() {
        rl_layout.setVisibility(View.VISIBLE);
        cardAdapter = new CardAdapter(R.layout.item_renren_layout, mDatas) {
            @Override
            public void setFource() {
                setUserBlock();
            }
        };
        recyclerView.setAdapter(cardAdapter);
        iv_head_img.setVisibility(View.GONE);
        notice_no_person.setVisibility(View.GONE);
        iv_rotation.clearAnimation();
    }


    public void hiddenPersons() {
        iv_head_img.setVisibility(View.VISIBLE);
        notice_no_person.setVisibility(View.VISIBLE);
        String sex = SharePreferenceUtils.getString(getContext(), Constance.SP_SEX, "1");
        String head_avatar = SharePreferenceUtils.getString(getContext(), Constance.SP_HEADER, "1");
        GlideLocalImageUtils.displayBigOrSmallShadowImage(getActivity(), iv_img, sex, head_avatar, "small");
        RotateAnimation rotateAnim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(2000);//动画持续时间时间
        rotateAnim.setInterpolator(new LinearInterpolator()); //添加插值器，下面会有说明
        rotateAnim.setFillAfter(true);
        rotateAnim.setRepeatCount(1000000);
        iv_rotation.startAnimation(rotateAnim);
        rl_layout.setVisibility(View.GONE);
    }

    @Override
    public void likeSuccess(String success, int userId) {
        RxBus.getDefault().post("success", "user_operation_rxbus");
        mPresenter.getOtherUserInfo(userId + "");
    }


    @Override
    public void unlikeSuccess(String success) {
    }

    public void InitLocation() {
        my_location();
    }

    public void my_location() {
        mPresenter.PlanSearchInfo();
    }


    // 设置标签,以英文逗号隔开
    private void setTags(int userId) {
        // Push: 设置tag调用方式
        List<String> tags = Utils.getTagsList(userId + "");
        PushManager.setTags(getActivity(), tags);
    }

    @Override
    public void UPHeadSuccess(UpHeadBean headBean) {
        mPresenter.editHead(headBean.getMsg());
    }

    @Override
    public void getCurrentLocationSuccess(String countryCode, String city) {

    }

    @Override
    public void updateHeadsuccess(MyInfoBean s) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ll_updata_head.setVisibility(View.GONE);
                if (mDatas.size() > 0) {
                    showPersons();
                } else {
                    hiddenPersons();
                }
            }
        });
    }

    @Override
    public void getOtherUserInfoSuccess(OtherUserInfoBean bean) {
        if (1 == bean.getIfEachOther()) {
            MatchDialog matchDialog = new MatchDialog(getActivity(), bean);
            matchDialog.setOnSureClick(new MatchDialog.OnSureClick() {
                @Override
                public void click(Dialog dialog) {
                    AppData.OhterfoBean = bean;
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("sendId", bean.getUserId() + "");
                    intent.putExtra("userName", bean.getUserName());
                    intent.putExtra("userImg", bean.getAvatar());
                    intent.putExtra("userSex", bean.getSex());
                    startActivity(intent);
                }
            });
            matchDialog.show();
        }
    }


    @Override
    public void BuyHighCoinSuccess(IntenetReposeBean data) {
        if ("0".equals(data.getCode()) && "success".equalsIgnoreCase(data.getMsg())) {
            AppData.MyInfoBean.getUser().setHighLightFlag("1");
            CustomToast.makeText(getContext(), "Purchase successful...", R.drawable.ic_toast_warming).show();
        } else {
            if ("Your Are Balance is insufficient.".equalsIgnoreCase(data.getMsg())) {//你的余额不足
                Intent intent = new Intent(getContext(), BuyCoinPageActivity.class);
                startActivity(intent);
            } else {
                CustomToast.makeText(getContext(), data.getMsg().toString(), R.drawable.ic_toast_warming).show();
            }
        }
    }

    @Override
    public void BuyError(String toString) {
        CustomToast.makeText(MyApplication.getContext(), toString, R.drawable.ic_toast_warming).show();
    }

    @Override
    public void ReportUserSuccess(Object value, ReportUserDialog reportUserDialog) {
        SingleConfireDialog singleConfireDialog = new SingleConfireDialog(getActivity(), "Thank you for your feedback. you should receive a response within 24 hours.");
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
    public void BlockUserSuccess(Object value) {
        ToastUtils.showShortToast("success");
    }


    public void initTuikit(String userId) {
        V2TIMManager.getInstance().login(userId, GenerateTestUserSig.genTestUserSig(userId), new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
            }

            @Override
            public void onSuccess() {
            }
        });
    }


    @Override
    public void LocationSuccess(AllCountryBean s) {
    }

    @Override
    protected CardSearchPresenter initPresenter() {
        return new CardSearchPresenter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                Log.i(TAG, "是否裁剪: " + pictureBean.isCut());
                Log.i(TAG, "原图地址: " + pictureBean.getPath());
                Log.i(TAG, "图片 Uri: " + pictureBean.getUri());
                if (pictureBean.isCut()) {
                    AppData.avatar = pictureBean.getPath();
                    this.img_path = pictureBean.getPath();
                    if (!TextUtils.isEmpty(img_path)) {
                        mPresenter.uploadHeader(img_path);
                    }
                } else {
                    this.img_path = pictureBean.getPath();
                    AppData.avatar = pictureBean.getPath();
                    if (!TextUtils.isEmpty(img_path)) {
                        mPresenter.uploadHeader(img_path);
                    }
                }
            }
        }
    }

    public  void setUserBlock(){
        CustomSheet show = new CustomSheet.Builder(getActivity())
                .setText("Block User", "Report User")
                .show(new CustomSheet.CallBack() {
                    @Override
                    public void callback(int index) {
                        if (index == 0) {
                            NoTitleDialog
                                    .newInstance(new NoTitleDialog.Callback() {
                                        @Override
                                        public void onDone(NoTitleDialog dialog) {
                                            mPresenter.blockUser(userId);
                                            dialog.dismissAllowingStateLoss();

                                        }

                                        @Override
                                        public void onCancel(NoTitleDialog dialog) {
                                            dialog.dismissAllowingStateLoss();
                                        }
                                    })
                                    .setContent("are you sure to block this member")
                                    .show(getFragmentManager(), "dialog");
                        } else {
                            ReportUserDialog
                                    .newInstance(new ReportUserDialog.Callback() {
                                        @Override
                                        public void onDone(ReportUserDialog reportUserDialog, String reason, String question, String imgurl) {
                                            if (TextUtils.isEmpty(reason)) {
                                                CustomToast.makeText(MyApplication.getContext(), "Please choose a reason.", R.drawable.ic_toast_warming).show();
                                                return;
                                            }
                                            mPresenter.ReportUser(userId, reason, question, imgurl, reportUserDialog);

                                        }

                                    })
                                    .setContent("are you sure to block this member")
                                    .show(getFragmentManager(), "dialog");
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }
}
