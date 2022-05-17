package com.supersweet.luck.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;
import com.supersweet.luck.base.BasicFragment;
import com.supersweet.luck.chat.MessageAdapter;
import com.supersweet.luck.chat.MessageInfo;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.yuyin.AudioRecordButton;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMSoundElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageManager;
import com.tencent.imsdk.v2.V2TIMSendCallback;

import java.io.File;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class FragmentVoice extends BasicFragment {
    private final String sendId;
    private final MessageAdapter mAdapter;
    private final RecyclerView chat_recycleview;
    private final List<V2TIMMessage> messageInfo;
    @BindView(R.id.chat_yuyin_function)
    AudioRecordButton chat_yuyin_function;

    public FragmentVoice(String sendId, MessageAdapter mAdapter, RecyclerView chat_recycleview, List<V2TIMMessage> messageInfo) {
        this.sendId = sendId;
        this.mAdapter = mAdapter;
        this.chat_recycleview = chat_recycleview;
        this.messageInfo = messageInfo;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yuyin;
    }

    @Override
    protected void initView(View view) {
        chat_yuyin_function.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {

            @Override
            public void onFinished(int seconds, String filePath) {

                sendVoice(seconds, filePath);


            }

        }, getActivity());
    }

    /**
     * 创建一条音频消息
     *
     * @param recordPath 音频路径
     * @param duration   音频的时长
     * @return
     */
    public static MessageInfo buildAudioMessage(String recordPath, int duration) {
        MessageInfo info = new MessageInfo();
        V2TIMMessage v2TIMMessage = V2TIMManager.getMessageManager().createSoundMessage(recordPath, duration / 1000);

        info.setDataPath(recordPath);
        info.setSelf(true);
        info.setTimMessage(v2TIMMessage);
        info.setExtra("[语音]");
        info.setMsgTime(System.currentTimeMillis() / 1000);
        info.setFromUser(V2TIMManager.getInstance().getLoginUser());
        info.setMsgType(MessageInfo.MSG_TYPE_AUDIO);
        info.setSelf(true);
        info.setRead(true);
        return info;
    }


    protected void sendVoice(int seconds, String filePath) {
        V2TIMMessage v2TIMMessage = V2TIMManager.getMessageManager().createSoundMessage(filePath, seconds);
        MessageInfo messageInfo = buildAudioMessage(filePath, seconds);
        V2TIMManager.getMessageManager().sendMessage(v2TIMMessage, sendId, null, V2TIMMessage.V2TIM_ELEM_TYPE_SOUND, false, null, new V2TIMSendCallback<V2TIMMessage>() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onError(int code, String desc) {

            }

            @Override
            public void onSuccess(V2TIMMessage v2TIMMessage) {
                FragmentVoice.this.messageInfo.add(v2TIMMessage);
                mAdapter.setMessagesList(FragmentVoice.this.messageInfo);
                smoothMoveToPosition(chat_recycleview, mAdapter.getItemCount());
            }
        });
    }

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) { // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }

        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
