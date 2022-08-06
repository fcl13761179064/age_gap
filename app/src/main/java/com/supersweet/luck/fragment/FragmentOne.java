package com.supersweet.luck.fragment;

import static com.stone.card.library.CardSlidePanel.VANISH_TYPE_LEFT;
import static com.stone.card.library.CardSlidePanel.VANISH_TYPE_RIGHT;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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

import com.baidu.android.pushservice.PushManager;
import com.stone.card.library.CardAdapter;
import com.stone.card.library.CardSlidePanel;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpFragment;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.dialog.CustomSheet;
import com.supersweet.luck.dialog.HighingConsumeCoinDialog;
import com.supersweet.luck.dialog.MatchDialog;
import com.supersweet.luck.dialog.MonthPayDialog;
import com.supersweet.luck.dialog.NoTitleDialog;
import com.supersweet.luck.dialog.ReportUserDialog;
import com.supersweet.luck.dialog.SingleConfireDialog;
import com.supersweet.luck.mvp.present.CardSearchPresenter;
import com.supersweet.luck.mvp.view.CardSearchView;
import com.supersweet.luck.pictureselector.PictureBean;
import com.supersweet.luck.pictureselector.PictureSelector;
import com.supersweet.luck.pictureselector.UriUtils;
import com.supersweet.luck.pushinfo.Utils;
import com.supersweet.luck.rxbus.Myinfo;
import com.supersweet.luck.rxbus.RxBus;
import com.supersweet.luck.signature.GenerateTestUserSig;
import com.supersweet.luck.ui.BuyCoinPageActivity;
import com.supersweet.luck.ui.ChatActivity;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.ui.FavoriteDetailActivity;
import com.supersweet.luck.utils.FastClickUtils;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
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

public class FragmentOne extends BaseMvpFragment<CardSearchView, CardSearchPresenter> implements CardSearchView {

    @BindView(R.id.csp_)
    CardSlidePanel mCardSlidePanel;
    @BindView(R.id.no_data_layout)
    RelativeLayout no_data_layout;
    @BindView(R.id.highlight_btn_layout)
    LinearLayout highlight_btn_layout;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.iv_rotation)
    ImageView iv_rotation;
    @BindView(R.id.ll_updata_head)
    LinearLayout ll_updata_head;

    private List<SeachPeopleBean> mDatas;
    private int count = 1;
    private int userId;
    public static final String TAG = "FragmetOne";
    private fragmentOneToMainActivity fragmentOneToMainActivity;

    private CardAdapter mAdapter;
    private Boolean isFirst = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_card_select;
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
        mDatas = new ArrayList();
        setAdapter();
        mPresenter.PlanSearchInfo(1);

    }

    @OnClick({R.id.tv_next, R.id.tv_buy_highing_coin, R.id.iv_highlinged_right, R.id.iv_highlinged_left})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_next:
                PictureSelector
                        .create(FragmentOne.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true);
                break;
            case R.id.tv_buy_highing_coin:
            case R.id.iv_highlinged_right:
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                mPresenter.BuyHighLightCoin();
                mCardSlidePanel.setVisibility(View.VISIBLE);
                highlight_btn_layout.setVisibility(View.GONE);
                break;
            case R.id.iv_highlinged_left:
                mCardSlidePanel.setVisibility(View.VISIBLE);
                highlight_btn_layout.setVisibility(View.GONE);
            default:
                break;
        }
    }

    private void setAdapter() {
        mAdapter = new CardAdapter() {
            @Override
            public int getLayoutId() {
                return R.layout.card_item;
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }

            @Override
            public void bindView(View view, int index) {
                Object tag = view.getTag();
                ViewHolder viewHolder;
                if (null != tag) {
                    viewHolder = (ViewHolder) tag;
                } else {
                    viewHolder = new ViewHolder(view);
                    view.setTag(viewHolder);
                }
                viewHolder.bindData(mDatas.get(index));
            }

            @Override
            public Object getItem(int index) {
                return mDatas.get(index);
            }
        };
        mCardSlidePanel.setAdapter(mAdapter);
        mCardSlidePanel.setCardSwitchListener(new CardSlidePanel.CardSwitchListener() {
            /**
             * 新卡片显示回调
             * @param index 最顶层显示的卡片的index
             */
            @Override
            public void onShow(int index) {
                try {
                    if (index == mDatas.size() - 1 && isFirst) {
                        isFirst = true;
                        count++;
                        mPresenter.PlanSearchInfo(count);
                    }
                    isFirst = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             * 卡片飞向两侧回调
             * @param index 飞向两侧的卡片数据index
             * @param type  飞向哪一侧{@link #:VANISH_TYPE_LEFT}或{@link #:VANISH_TYPE_RIGHT}
             */
            @Override
            public void onCardVanish(int index, int type) {
                Log.e("Card", "正在消失-" + mDatas.get(index).getUserName() + " 消失type=" + type);
                if (type == VANISH_TYPE_LEFT) {
                    userId = mDatas.get(index).getUserId();
                    mPresenter.unlike(userId);
                    Log.d("like_or_unlike", "left");
                } else if (type == VANISH_TYPE_RIGHT) {
                    userId = mDatas.get(index).getUserId();
                    mPresenter.like(userId);
                    Log.d("like_or_unlike", "right");
                }
            }


            /**
             * 卡片撤回的回调
             * @param status :1：撤回成功 2：已经没有可以撤回的数据
             * @param type :之前飞向哪一侧{@link #""VANISH_TYPE_LEFT"}或{@link #"VANISH_TYPE_RIGHT"}
             */
            @Override
            public void onCardRetract(int status, int type) {
                Log.e("Card", "正在撤回-" + (status == 1 ? "撤回成功 " : "已经没有可以撤回的数据") + " 撤回type=" + type);
                if (status == 1) {
                    HighingConsumeCoinDialog
                            .newInstance(new HighingConsumeCoinDialog.Callback() {
                                @Override
                                public void onDone(HighingConsumeCoinDialog dialog) {
                                    dialog.dismissAllowingStateLoss();
                                    //mPresenter.getOtherUserInfo();
                                }

                                @Override
                                public void onCancel(HighingConsumeCoinDialog dialog) {
                                    dialog.dismissAllowingStateLoss();
                                }
                            })
                            .setContent("1", "")
                            .show(getFragmentManager(), "dialog");
                }
            }

            /**
             * 卡片功能按钮的监听
             * @return :
             *      0: off  1: on
             *          撤 左 右
             *      0b  0  0  0
             *
             *      列：只能撤回--->0b100
             */
            @Override
            public int onFunctionEnabled() {
                return 0b111;
            }

            /**
             * 卡片移动距离的回调
             *
             * @param "percentage":移动距离的百分比：
             *                  -1:为松开手指 回到中心
             *                  中心--->删除  0---->1
             *                  +-表示方向
             *                  +：右移   -：左移
             * @param oldCard：移动的卡片下面一层的CardView
             * @param moveCard：移动的卡片
             */
            @Override
            public void onCardMove(float offset, View oldCard, View moveCard) {
                Log.e("Card", "移动距离百分比---=" + offset);
                if (oldCard != null) {
                }

                View view_left = moveCard.findViewById(R.id.touch_left);
                View touch_right = moveCard.findViewById(R.id.touch_right);

                if (offset < 0) {
                    view_left.setTranslationY(offset / 3);
                    view_left.setScaleX(1 + Math.abs(offset) / 1000);
                    view_left.setScaleY(1 + Math.abs(offset) / 1000);
                } else {
                    touch_right.setTranslationY(-offset / 3);
                    touch_right.setScaleX(1 + Math.abs(offset) / 1000);
                    touch_right.setScaleY(1 + Math.abs(offset) / 1000);
                }
            }

        });

    }

    class ViewHolder {
        ImageView card_image_view;
        View view_high_light;
        ImageView iv_block_user;
        TextView text_view_one;
        TextView text_view_two;
        TextView tv_credit_fen;
        TextView text_view_three;
        LinearLayout ll_online;
        LinearLayout user_score;
        View maskView;

        public ViewHolder(View view) {
            text_view_one = ((TextView) view.findViewById(R.id.text_view_one));
            text_view_two = ((TextView) view.findViewById(R.id.text_view_two));
            text_view_three = ((TextView) view.findViewById(R.id.text_view_three));
            tv_credit_fen = ((TextView) view.findViewById(R.id.tv_credit_fen));
            iv_block_user = ((ImageView) view.findViewById(R.id.iv_block_user));
            card_image_view = ((ImageView) view.findViewById(R.id.card_image_view));
            view_high_light = ((View) view.findViewById(R.id.view_high_light));
            ll_online = ((LinearLayout) view.findViewById(R.id.ll_online));
            user_score = ((LinearLayout) view.findViewById(R.id.user_score));
            maskView = ((View) view.findViewById(R.id.maskView));
        }

        public void bindData(SeachPeopleBean item) {
            try {
                String avatar = item.getAvatar();
                String userName = item.getUserName();
                String sex = item.getSex();
                int age = item.getAge();
                String city = item.getCity();
                String station = item.getStation();
                int isOnline = item.getIsOnline();
                String qscore = item.getQscore();
                String distance = item.getDistance();
                GlideLocalImageUtils.displayBigOrSmallShadowImage(getActivity(), card_image_view, sex, avatar, "big");
                String my_desc = sex + ", " + age + ", " + station;
                text_view_one.setText(userName);
                text_view_two.setText(my_desc);
                tv_credit_fen.setText(qscore);
                text_view_three.setText(distance + " km away");
                if (item.getHighLightFlag() == 1) {
                    view_high_light.setBackgroundResource(R.drawable.highing_card_corners);
                } else {
                    view_high_light.setBackgroundResource(0);
                }

                if (isOnline == 1) {
                    ll_online.setVisibility(View.VISIBLE);
                } else {
                    ll_online.setVisibility(View.GONE);
                }
                ll_online.setVisibility(View.VISIBLE);
                iv_block_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        blockUser();
                    }
                });
                maskView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), FavoriteDetailActivity.class);
                            intent.putExtra("UserId", item.getUserId());
                            startActivity(intent);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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


    @Override
    protected void initListener() {
        RxBus.getDefault().subscribe(this, "filter_condition", new RxBus.Callback<Myinfo>() {
            @Override
            public void onEvent(Myinfo chooseSex) {
                String sex = chooseSex.getChooseSex();
                String maxAge = chooseSex.getMaxAge();
                String minAge = chooseSex.getMinAge();
                String countryCode = chooseSex.getChooseCountryCode();
                String height = chooseSex.getHeight();
                String body = chooseSex.getBody();
                String hair = chooseSex.getHair();
                String relationship = chooseSex.getRelationship();
                String education = chooseSex.getEducation();
                String ethnicity = chooseSex.getEthnicity();
                String drinking = chooseSex.getDrinking();
                String smoking = chooseSex.getSmoking();
                String children = chooseSex.getChildren();
                mPresenter.card_search(countryCode, 1, sex, "-1", minAge, maxAge
                        , height, body, hair, relationship, education, ethnicity, drinking, smoking, children);
            }
        });


        RxBus.getDefault().subscribe(this, "alter_head", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                String path = SharePreferenceUtils.getString(getContext(), Constance.SP_HEADER, "");
                String sex = SharePreferenceUtils.getString(getContext(), Constance.SP_SEX, "1");
                // GlideLocalImageUtils.displayBigOrSmallShadowImage(getActivity(), iv_img, sex, path, "small");
            }
        });

      /*  cardAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
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
        });*/
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

        String highLightFlag = AppData.MyInfoBean.getUser().getHighLightFlag();
        if ("1".equals(highLightFlag)) {
            highlight_btn_layout.setVisibility(View.GONE);
        } else {
            if (count > 1) {
                mCardSlidePanel.setVisibility(View.GONE);
                highlight_btn_layout.setVisibility(View.VISIBLE);
            }

        }
        if (data.size() > 0) {
            no_data_layout.setVisibility(View.GONE);
            showPersons();
        } else {
            no_data_layout.setVisibility(View.VISIBLE);
            highlight_btn_layout.setVisibility(View.GONE);
            noPersionAmation();
        }

        if (TextUtils.isEmpty(head_avatar)) {
            ll_updata_head.setVisibility(View.VISIBLE);
            highlight_btn_layout.setVisibility(View.GONE);
            no_data_layout.setVisibility(View.GONE);
        } else {
            ll_updata_head.setVisibility(View.GONE);
        }
        mDatas = data;
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void showPersons() {
        iv_rotation.clearAnimation();
    }

    public void noPersionAmation() {
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
    }

    @Override
    public void likeSuccess(String success, int userId) {
        RxBus.getDefault().post("success", "user_operation_rxbus");
        mPresenter.getOtherUserInfo(userId + "");
    }


    @Override
    public void unlikeSuccess(String success, int userId) {

    }


    // 设置标签,以英文逗号隔开
    private void setTags(int userId) {
        // Push: 设置tag调用方式
        List<String> tags = Utils.getTagsList(userId + "");
        PushManager.setTags(getActivity(), tags);
    }

    @Override
    public void UPHeadSuccess(UpHeadBean headBean, String localPath) {
        SharePreferenceUtils.saveString(MyApplication.getContext(), Constance.SP_HEADER, localPath);
        CustomToast.makeText(MyApplication.getContext(), "Avatar uploaded successfully", R.drawable.ic_toast_success).show();
    }

    @Override
    public void getCurrentLocationSuccess(String countryCode, String city) {

    }

    @Override
    public void updateHeadsuccess(MyInfoBean s) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDatas.size() > 0) {

                } else {
                    //hiddenPersons();
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
                AppData.avatar = pictureBean.getPath();
                String img_path = pictureBean.getPath();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Uri parse = Uri.parse(pictureBean.getPath());
                    String outputPath = UriUtils.getFileAbsolutePath(getContext(), parse);
                    if (!TextUtils.isEmpty(outputPath)) {
                        mPresenter.uploadHeader(outputPath);
                    }
                } else {
                    if (!TextUtils.isEmpty(img_path)) {
                        mPresenter.uploadHeader(img_path);
                    }
                }
            }
        }
    }
}
