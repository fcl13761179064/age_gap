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
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
import com.supersweet.luck.widget.ItemTouchCallback;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import java.util.List;
import butterknife.BindView;


/**
 * Created by fanchunlei on 2020/10/1.
 * Blog:http://blog.csdn.net/student9128
 * Describe：the first fragment
 */

public class FragmentOne extends BaseMvpFragment<CardSearchView, CardSearchPresenter> implements CardSearchView {

    @BindView(R.id.csp_)
    CardSlidePanel mCardSlidePanel;


    private List<SeachPeopleBean> mDatas;
    private CardAdapter cardAdapter;
    private ItemTouchCallback callback;
    private int count = 1;
    public static final String TAG = "FragmetOne";
    private String img_path;
    private fragmentOneToMainActivity fragmentOneToMainActivity;
    private int userId;


    private CardAdapter mAdapter;

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
        mPresenter.PlanSearchInfo();
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
    }

    class ViewHolder {
        ImageView card_image_view;
        ImageView iv_block_user;
        TextView text_view_one;
        TextView text_view_two;
        TextView tv_credit_fen;
        TextView text_view_three;
        LinearLayout ll_online;

        public ViewHolder(View view) {

         /*   text_view_one = ((TextView) view.findViewById(R.id.text_view_one));
            text_view_two = ((TextView) view.findViewById(R.id.text_view_two));
            tv_credit_fen = ((TextView) view.findViewById(R.id.tv_credit_fen));
            text_view_three = ((TextView) view.findViewById(R.id.text_view_three));
            card_image_view = ((ImageView) view.findViewById(R.id.card_image_view));
            ll_online = ((LinearLayout) view.findViewById(R.id.ll_online));*/
            iv_block_user = ((ImageView) view.findViewById(R.id.iv_block_user));
            card_image_view = ((ImageView) view.findViewById(R.id.card_image_view));
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
                    card_image_view.setBackgroundResource(R.drawable.highing_green);
                }
                if (isOnline == 1) {
                    ll_online.setVisibility(View.VISIBLE);

                } else {
                    ll_online.setVisibility(View.GONE);
                }
                iv_block_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                mPresenter.card_search(countryCode, 1, sex, "-1", minAge, maxAge);
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
        mDatas = data;
        setAdapter();

        if (TextUtils.isEmpty(head_avatar)) {
            SharePreferenceUtils.saveString(MyApplication.getContext(), Constance.SP_HEADER, "");
          /*  ll_updata_head.setVisibility(View.VISIBLE);
            rl_layout.setVisibility(View.GONE);
            notice_no_person.setVisibility(View.GONE);
            iv_head_img.setVisibility(View.GONE);*/
        }
    }

    @Override
    public void card_continue_serch_success(List<SeachPeopleBean> data) {
        mDatas = data;
        String highLightFlag = AppData.MyInfoBean.getUser().getHighLightFlag();
        if ("1".equals(highLightFlag)) {
            if (mDatas.size() > 0) {
               // showPersons();
            } else {
                hiddenPersons();
            }
           // highlight_btn_layout.setVisibility(View.GONE);
        } else {
          //  highlight_btn_layout.setVisibility(View.VISIBLE);
          //  rl_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void errorcontinueShake(String msg) {
        //highlight_btn_layout.setVisibility(View.GONE);
        hiddenPersons();
    }



    public void hiddenPersons() {
       /* iv_head_img.setVisibility(View.VISIBLE);
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
        rl_layout.setVisibility(View.GONE);*/
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
//                ll_updata_head.setVisibility(View.GONE);
                if (mDatas.size() > 0) {
                    //showPersons();
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

    public void setUserBlock() {
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
