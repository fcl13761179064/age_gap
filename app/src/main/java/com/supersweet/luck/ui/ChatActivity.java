package com.supersweet.luck.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.FragmentUtils;
import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.chat.ConversationInfo;
import com.supersweet.luck.chat.MessageAdapter;
import com.supersweet.luck.chat.MessageInfo;
import com.supersweet.luck.chat.MessageInfoUtil;
import com.supersweet.luck.chat.MessageRevokedManager;
import com.supersweet.luck.dialog.CustomSheet;
import com.supersweet.luck.dialog.HighingConsumeCoinDialog;
import com.supersweet.luck.dialog.MonthPayDialog;
import com.supersweet.luck.dialog.NoTitleDialog;
import com.supersweet.luck.dialog.ReportUserDialog;
import com.supersweet.luck.dialog.SingleConfireDialog;
import com.supersweet.luck.emoji.Emoji;
import com.supersweet.luck.emoji.EmojiUtil;
import com.supersweet.luck.emoji.FragmentEnjoy;
import com.supersweet.luck.fragment.FragmentTwo;
import com.supersweet.luck.mvp.present.ChatPresenter;
import com.supersweet.luck.mvp.view.ChatView;
import com.supersweet.luck.signature.GenerateTestUserSig;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.utils.FastClickUtils;
import com.supersweet.luck.utils.SoftInputUtil;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
import com.supersweet.luck.yuyin.MediaManager;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BaseMvpActivity<ChatView, ChatPresenter> implements ChatView, MessageRevokedManager.MessageRevokeHandler, FragmentEnjoy.OnEmojiClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.et_username)
    TextView et_username;
    @BindView(R.id.chat_recycleview)
    RecyclerView chat_recycleview;
    @BindView(R.id.input_switch)
    LinearLayout input_switch;
    @BindView(R.id.chat_message_input)
    EditText chat_message_input;
    @BindView(R.id.send_btn)
    ImageView send_btn;
    @BindView(R.id.ll_layout)
    RelativeLayout ll_layout;
    @BindView(R.id.ll_function_layout)
    LinearLayout ll_function_layout;
    @BindView(R.id.layout_edit)
    LinearLayout layout_edit;
    @BindView(R.id.iv_block_user)
    ImageView iv_block_user;

    private FragmentEnjoy enjoy_fragment;
    private MessageAdapter mAdapter;
    private int userId;
    private List<V2TIMMessage> messageInfo;
    private String sendId;
    private String userImg;
    private String userName;
    private String userSex;
    //Fragment 列表
    private List<Fragment> mFragments = new ArrayList();
    private TIMConversation con;
    private boolean is_show;
    private Serializable otherInfoBean;
    private OtherUserInfoBean otherInfo;
    private String send_message="";


    @Override
    protected ChatPresenter initPresenter() {
        return new ChatPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.chat_layout;
    }

    @Override
    protected void initView() {
        messageInfo = new ArrayList<>();
        userId = AppData.MyInfoBean.getUser().getUserId();
        sendId = getIntent().getStringExtra("sendId");
        userImg = getIntent().getStringExtra("userImg");
        userName = getIntent().getStringExtra("userName");
        userSex = getIntent().getStringExtra("userSex");
        et_username.setText(userName);
        otherInfoBean = AppData.OhterfoBean;
        GlideLocalImageUtils.displaySmallImg(this, user_img, userSex, userImg);
        mAdapter = new MessageAdapter(ChatActivity.this);
        initTuikit();
        getmessage();
        initFragment();
        changeFragment(0);
    }


    private void upReadMessage() {
        con = TIMManager.getInstance().getConversation(TIMConversationType.C2C, sendId);
        //获取会话未读数
        TIMMessage timMessage = new TIMMessage();
        timMessage.addElement(null);
        con.setReadMessage(timMessage, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {

            }

            @Override
            public void onSuccess() {

            }
        });
    }

    private void initFragment() {
        enjoy_fragment = new FragmentEnjoy();
        mFragments.add(enjoy_fragment);
        FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.fl_container, 0);
    }


    /**
     * TIMConversation转换为ConversationInfo
     *
     * @param conversation
     * @return
     */
    private ConversationInfo TIMConversation2ConversationInfo(final V2TIMConversation conversation) {
        if (conversation == null) {
            return null;
        }

        V2TIMMessage message = conversation.getLastMessage();
        if (message == null) {
            return null;
        }
        final ConversationInfo info = new ConversationInfo();
        int type = conversation.getType();
        if (type != V2TIMConversation.V2TIM_C2C && type != V2TIMConversation.V2TIM_GROUP) {
            return null;
        }
        info.setLastMessageTime(message.getTimestamp());
        List<MessageInfo> list = MessageInfoUtil.TIMMessage2MessageInfo(message);
        if (list != null && list.size() > 0) {
            info.setLastMessage(list.get(list.size() - 1));
        }

        info.setTitle(conversation.getShowName());

        List<Object> faceList = new ArrayList<>();
        if (TextUtils.isEmpty(conversation.getFaceUrl())) {
            faceList.add(R.mipmap.signin_female_notselect);
        } else {
            faceList.add(conversation.getFaceUrl());
        }
        info.setIconUrlList(faceList);

        info.setId(conversation.getUserID());
        info.setConversationId(conversation.getConversationID());
        info.setUnRead(conversation.getUnreadCount());
        return info;
    }

    public void getmessage() {
        //获取会话扩展实例
        V2TIMManager.getMessageManager().getC2CHistoryMessageList(sendId, 30, null, new V2TIMValueCallback<List<V2TIMMessage>>() {

            @Override
            public void onError(int code, String desc) {

            }

            @Override
            public void onSuccess(List<V2TIMMessage> v2TIMMessages) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageInfo.addAll(v2TIMMessages);
                        Collections.reverse(messageInfo);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        // 设置布局管理器
                        chat_recycleview.setLayoutManager(mLayoutManager);
                        // 设置adapter
                        chat_recycleview.setAdapter(mAdapter);
                        mAdapter.setMessagesList(messageInfo);
                        smoothMoveToPosition(chat_recycleview, mAdapter.getItemCount());
                    }
                });

            }

        });
    }

    public void initTuikit() {
        int loginStatus = V2TIMManager.getInstance().getLoginStatus();
        if (loginStatus == 3) {
            if (userId != 0) {
                V2TIMManager.getInstance().login(userId + "", GenerateTestUserSig.genTestUserSig(userId + ""), new V2TIMCallback() {
                    @Override
                    public void onError(int code, String desc) {
                    }

                    @Override
                    public void onSuccess() {
                    }
                });
            }
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    smoothMoveToPosition(chat_recycleview, mAdapter.getItemCount());
                    break;
            }
        }

    };

    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upReadMessage();
                MediaManager.pause();//就回复
                SoftInputUtil.hideShow(view);
                setResult(10001);
                finish();
            }
        });
        input_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_show = !is_show;
                if (is_show) {
                    ll_function_layout.setVisibility(View.VISIBLE);
                    SoftInputUtil.hideShow(v);
                } else {
                    ll_function_layout.setVisibility(View.GONE);
                }
            }
        });

        chat_message_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    send_btn.setBackgroundResource(R.mipmap.chat_send);
                } else {
                    send_btn.setBackgroundResource(R.mipmap.chat_cantsend);
                }

            }
        });
        // GroupView的监听
        layout_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //让View 也就是EditText获得焦点
                chat_message_input.requestFocus();
                ll_function_layout.setVisibility(View.GONE);
                SoftInputUtil.showSoftInput(chat_message_input);
                //通过handler保证在主线程中进行滑动操作
                handler.sendEmptyMessageDelayed(0, 250);
            }
        });


        chat_recycleview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoftInputUtil.hideSysSoftInput(ChatActivity.this);
            }
        });
        setListener();
    }

    private void setListener() {
        V2TIMManager.getMessageManager().addAdvancedMsgListener(new V2TIMAdvancedMsgListener() {
            @Override
            public void onRecvNewMessage(V2TIMMessage msg) {
                super.onRecvNewMessage(msg);
                messageInfo.add(msg);
                mAdapter.setMessagesList(messageInfo);
                smoothMoveToPosition(chat_recycleview, mAdapter.getItemCount());
            }
        });
    }


    @OnClick({R.id.ll_layout, R.id.send_btn, R.id.iv_block_user})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.ll_layout:
                SoftInputUtil.hideShow(v);
                break;
            case R.id.send_btn:
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                send_message = chat_message_input.getText().toString();
                if (TextUtils.isEmpty(send_message)) {
                    ToastUtils.showLongToast("Send data cannot be empty");
                    return;
                }
                if (otherInfoBean instanceof OtherUserInfoBean) {
                    otherInfo = (OtherUserInfoBean) otherInfoBean;
                    mPresenter.checkMyIsMonth(otherInfo.getUserId());
                }

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
                                                    mPresenter.blockUser(Integer.parseInt(sendId));
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
                                                    mPresenter.ReportUser(Integer.parseInt(sendId), reason, question, imgurl, reportUserDialog);

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
        }
    }


    public void chat_method(String send_message) {
        V2TIMManager.getInstance().sendC2CTextMessage(send_message, sendId, new V2TIMSendCallback<V2TIMMessage>() {
            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onError(int code, String desc) {

            }

            @Override
            public void onSuccess(V2TIMMessage v2TIMMessage) {
                messageInfo.add(v2TIMMessage);
                mAdapter.setMessagesList(messageInfo);
                smoothMoveToPosition(chat_recycleview, mAdapter.getItemCount());
            }
        });
        chat_message_input.setText("");
    }

    /*
   切换Tab，切换对应的Fragment
    */
    private void changeFragment(int position) {
        FragmentUtils.showHide(mFragments.get(position), mFragments);
    }


    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) { // 第一个可见位置
        if (mRecyclerView == null) {
            return;
        }
        mRecyclerView.smoothScrollToPosition(position);
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void Success(MyInfoBean data) {

    }

    @Override
    public void ConSumeCoinSuccess(IntenetReposeBean data, String messageInfo) {
        if ("0".equals(data.getCode()) && "success".equalsIgnoreCase(data.getMsg())) {
            if (otherInfoBean instanceof OtherUserInfoBean) {
                chat_method(messageInfo);
                OtherUserInfoBean otherInfo = (OtherUserInfoBean) otherInfoBean;
                otherInfo.setMessageFreeFlag(1);
            }
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
    public void BuyCoinFail(String msg) {

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
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("reesulteData", "success");
        setResult(10001, intent);
        startActivity(intent);
    }


    @Override
    public void errorShakes(String msg, ReportUserDialog reportUserDialog) {
        reportUserDialog.dismissAllowingStateLoss();
        ToastUtils.showShortToast(msg);
    }

    @Override
    public void errorShakess(String msg) {
        ToastUtils.showShortToast(msg);
    }


    @Override
    public void checkIsMonthPay(IntenetReposeBean data) {
        if (data != null) {
            if ("0".equals(data.getCode())) {
                chat_method(send_message);
            } else {
                MonthPayDialog dialog = new MonthPayDialog(this);
                dialog.setOnSureClick(new MonthPayDialog.OnSureClick() {

                    @Override
                    public void click(Dialog dialog) {

                    }
                });
                dialog.show();
                dialog.setGravity(Gravity.CENTER);
            }
        }
    }

    @Override
    public void handleInvoke(String msgID) {

    }

    @Override
    public void onEmojiDelete() {
        String text = chat_message_input.getText().toString();
        if (text.isEmpty()) {
            return;
        }
        if ("]".equals(text.substring(text.length() - 1, text.length()))) {
            int index = text.lastIndexOf("[");
            if (index == -1) {
                int action = KeyEvent.ACTION_DOWN;
                int code = KeyEvent.KEYCODE_DEL;
                KeyEvent event = new KeyEvent(action, code);
                chat_message_input.onKeyDown(KeyEvent.KEYCODE_DEL, event);
                displayTextView();
                return;
            }
            chat_message_input.getText().delete(index, text.length());
            displayTextView();
            return;
        }
        int action = KeyEvent.ACTION_DOWN;
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        chat_message_input.onKeyDown(KeyEvent.KEYCODE_DEL, event);
        displayTextView();
    }

    private void displayTextView() {
        try {
            EmojiUtil.handlerEmojiText(chat_message_input, chat_message_input.getText().toString(), this);
            chat_message_input.setSelection(chat_message_input.getText().length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEmojiClick(Emoji emoji) {
        if (emoji != null) {
            int index = chat_message_input.getSelectionStart();
            Editable editable = chat_message_input.getEditableText();

            if (index == 0) {
                editable.append(emoji.getContent());
            } else {
                editable.insert(index, emoji.getContent());
            }
        }
        displayTextView();
    }

}
