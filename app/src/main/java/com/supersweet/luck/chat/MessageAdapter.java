package com.supersweet.luck.chat;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;
import com.supersweet.luck.emoji.EmojiUtil;
import com.supersweet.luck.utils.DateUtils;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.yuyin.MediaManager;
import com.tencent.imsdk.v2.V2TIMDownloadCallback;
import com.tencent.imsdk.v2.V2TIMElem;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMSoundElem;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created By 樊春雷 on 2018/7/13 11:42
 **/
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<V2TIMMessage> messages;
    private LayoutInflater inflater;
    private Context context;
    // 我发送消息的布局
    public static final int TEXT_TYPE_MY = 1;
    //别人发送消息给我的布局
    public static final int TEXT_TYPE_OTHER = 2;
    public static final int VOICE_TYPE_MY = 3;
    public static final int VOICE_TYPE_OTHER = 4;

    private final int mMinItemWith;
    private final int mMaxItemWith;


    public MessageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        // 获取系统宽度
        WindowManager wManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wManager.getDefaultDisplay().getMetrics(outMetrics);
        mMaxItemWith = (int) (outMetrics.widthPixels * 0.7f);
        mMinItemWith = (int) (outMetrics.widthPixels * 0.15f);
    }

    public void setMessagesList(List<V2TIMMessage> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }


    //参数二为itemView的类型，viewType代表这个类型值
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TEXT_TYPE_MY:
                View my_chat_window = inflater.inflate(R.layout.item_cv_mine, viewGroup, false);
                return new TEXT_MY_HOLDER(my_chat_window);
            case TEXT_TYPE_OTHER:
                View other_send_me = inflater.inflate(R.layout.item_cv_other, viewGroup, false);
                return new TEXT_OTHER_HOLDER(other_send_me);

            case VOICE_TYPE_MY:
                View my_voice = inflater.inflate(R.layout.chat_my_voice, viewGroup, false);
                return new VOICE_MY_HOLDER(my_voice);

            case VOICE_TYPE_OTHER:
                View other_send_voice = inflater.inflate(R.layout.chat_other_voice, viewGroup, false);
                return new VOICE_OTHER_HOLDER(other_send_voice);

        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        V2TIMMessage entity = messages.get(position);
        if (holder instanceof TEXT_MY_HOLDER) {

            try {
                bind_my_send_text_layout((TEXT_MY_HOLDER) holder, position, entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (holder instanceof TEXT_OTHER_HOLDER) {
            try {
                bind_other_send_text_layout((TEXT_OTHER_HOLDER) holder, position, entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (holder instanceof VOICE_MY_HOLDER) {

            bind_my_send_voice_layout((VOICE_MY_HOLDER) holder, position, entity);

        } else if (holder instanceof VOICE_OTHER_HOLDER) {

            bind_other_send_voice_layout((VOICE_OTHER_HOLDER) holder, position, entity);
        }


    }

    private void bind_other_send_voice_layout(final VOICE_OTHER_HOLDER holder, final int position, V2TIMMessage entity) {
        V2TIMSoundElem soundElem = entity.getSoundElem();
        // 语音消息
        V2TIMSoundElem v2TIMSoundElem = entity.getSoundElem();
     /*   // 语音 ID,内部标识，可用于外部缓存 key
        String uuid = v2TIMSoundElem.getUUID();
        // 语音文件大小
        int dataSize = v2TIMSoundElem.getDataSize();
        // 语音时长
        int durations = v2TIMSoundElem.getDuration();
        // 设置语音文件路径 soundPath，这里可以用 uuid 作为标识，避免重复下载
        String soundPath =  uuid;
        File imageFile = new File(soundPath);
        // 判断 soundPath 下有没有已经下载过的语音文件
        if (!imageFile.exists()) {
            v2TIMSoundElem.downloadSound(soundPath, new V2TIMDownloadCallback() {
                @Override
                public void onProgress(V2TIMElem.V2ProgressInfo progressInfo) {
                    // 下载进度回调：已下载大小
                    progressInfo.getCurrentSize();
                    //总文件大小 v2ProgressInfo.getTotalSize()
                }
                @Override
                public void onError(int code, String desc) {
                    ToastUtils.showShortToast(desc);
                    // 下载失败
                }
                @Override
                public void onSuccess() {
                    ToastUtils.showShortToast("3333333333333333");
                    // 下载完成
                }
            });
        } else {
            // 文件已存在
        }*/
        final String uri = soundElem.getPath();
        boolean read = entity.isRead();
        long duration = soundElem.getDuration();
        if (uri != null && !uri.equals("")) {
            holder.item_other_send_time.setText(duration + "s");
            ViewGroup.LayoutParams lParams = holder.other_send_voice_long.getLayoutParams();
            lParams.width = (int) (mMinItemWith + mMaxItemWith / 40f * duration);
            holder.other_send_voice_long.setLayoutParams(lParams);

        }

        if (read == true) {
            holder.yuyin_res_position.setVisibility(View.GONE);
        } else {
            holder.yuyin_res_position.setVisibility(View.VISIBLE);
        }

        holder.other_receivre_voice_play.setOnClickListener(new View.OnClickListener() {
            private AnimationDrawable drawable;
            @Override
            public void onClick(View view) {
                holder.yuyin_res_position.setVisibility(View.GONE);
        /*        // 播放动画
                if (drawable!=null){
                    drawable.stop();
                }
                holder.receive_other_voice_anim.setBackgroundResource(R.drawable.voice_animlist);
                drawable = (AnimationDrawable) holder.receive_other_voice_anim.getBackground();
                drawable.start();*/
                // 播放音频
                MediaManager.playSound(uri, new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                      /*  holder.receive_other_voice_anim.setBackgroundResource(R.drawable.yuyin_animation_three);
                        drawable.stop();
*/
                    }
                });

            }

        });


        long time = entity.getTimestamp() / 1000;

        String beforeData = DateUtils.LongstampToString(time);


        if (position != 0) {
            if (beforeData != null && position % 3 == 0) {
                holder.other_voice_chat_time.setVisibility(View.VISIBLE);
                holder.other_voice_chat_time.setText(beforeData);
            } else {
                holder.other_voice_chat_time.setVisibility(View.GONE);
            }
        } else {
            if (beforeData != null) {
                holder.other_voice_chat_time.setVisibility(View.VISIBLE);
                holder.other_voice_chat_time.setText(beforeData);
            } else {
                holder.other_voice_chat_time.setVisibility(View.GONE);
            }

        }

    }



    private void bind_my_send_voice_layout(final VOICE_MY_HOLDER holder, int position, V2TIMMessage entity) {
        V2TIMSoundElem soundElem = entity.getSoundElem();
        final String uri = soundElem.getPath();
        int status = entity.getStatus();
        if (status == entity.V2TIM_MSG_STATUS_SEND_FAIL) {
            holder.iv_my_voice_error.setVisibility(View.VISIBLE);
        }

        long duration = soundElem.getDuration();
        if (uri != null && !uri.equals("")) {
            holder.item_my_send_time.setText(duration + "s");
            ViewGroup.LayoutParams lParams = holder.my_send_voice_long.getLayoutParams();
            lParams.width = (int) (mMinItemWith + mMaxItemWith / 40f * duration);
            holder.my_send_voice_long.setLayoutParams(lParams);

        }

        holder.rl_my_send_voice_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    holder.iv_send_voice_anim.setBackgroundResource(R.drawable.voice_from_animlist);
                AnimationDrawable drawable = (AnimationDrawable)  holder.iv_send_voice_anim.getBackground();
                if (drawable!=null){
                    drawable.stop();
                }
                drawable.start();*/
                // 播放音频
                MediaManager.playSound(uri, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                     /*   holder.iv_send_voice_anim.setBackgroundResource(R.drawable.yuyin_from_my_three);
                        drawable.start();*/
                    }
                });

            }

        });


        long time = entity.getTimestamp() / 1000;

        String beforeData = DateUtils.LongstampToString(time);
        if (position != 0) {
            if (beforeData != null && position % 4 == 0) {
                holder.my_voice_chat_time.setVisibility(View.VISIBLE);
                holder.my_voice_chat_time.setText(beforeData);
            } else {
                holder.my_voice_chat_time.setVisibility(View.GONE);
            }
        } else {
            if (beforeData != null) {
                holder.my_voice_chat_time.setVisibility(View.VISIBLE);
                holder.my_voice_chat_time.setText(beforeData);
            } else {
                holder.my_voice_chat_time.setVisibility(View.GONE);
            }

        }
    }


    private void bind_other_send_text_layout(TEXT_OTHER_HOLDER holder, int position, V2TIMMessage mData) throws IOException {

        holder.item_other_send_text.setText(mData.getTextElem().getText());
        long time = mData.getTimestamp() / 1000;
        EmojiUtil.handlerEmojiText(holder.item_other_send_text, mData.getTextElem().getText(), context);
        String beforeData = DateUtils.LongstampToString(time);
        if (position != 0) {
            if (beforeData != null && position % 3 == 0) {
                holder.other_chat_time.setVisibility(View.VISIBLE);
                holder.other_chat_time.setText(beforeData);
            } else {
                holder.other_chat_time.setVisibility(View.GONE);
            }
        } else {
            if (beforeData != null) {
                holder.other_chat_time.setVisibility(View.VISIBLE);
                holder.other_chat_time.setText(beforeData);
            } else {
                holder.other_chat_time.setVisibility(View.GONE);
            }

        }


    }

    private void bind_my_send_text_layout(TEXT_MY_HOLDER holder, int position, V2TIMMessage entity) throws IOException {

        holder.item_my_send_text.setText(entity.getTextElem().getText());
        int status = entity.getStatus();
        if (status == entity.V2TIM_MSG_STATUS_SEND_FAIL) {
            holder.iv_text_error.setVisibility(View.VISIBLE);
        }
        long time = entity.getTimestamp() / 1000;
        EmojiUtil.handlerEmojiText(holder.item_my_send_text, entity.getTextElem().getText(), context);
        String beforeData = DateUtils.LongstampToString(time);
        if (position != 0) {
            if (beforeData != null && position % 3 == 0) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(beforeData);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            if (beforeData != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(beforeData);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }

        }


    }


    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getElemType() == 1) {
            if (messages.get(position).isSelf()) {
                return TEXT_TYPE_MY;
            } else {
                return TEXT_TYPE_OTHER;
            }
        } else if (messages.get(position).getElemType() == 4) {
            if (messages.get(position).isSelf()) {
                return VOICE_TYPE_MY;
            } else {
                return VOICE_TYPE_OTHER;
            }
        }
        return 0;
    }


    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }


    public class TEXT_MY_HOLDER extends RecyclerView.ViewHolder {

        TextView item_my_send_text, chat_time;
        private ImageView iv_text_error;

        public TEXT_MY_HOLDER(View itemView) {
            super(itemView);
            item_my_send_text = itemView.findViewById(R.id.item_my_send_text);
            iv_text_error = itemView.findViewById(R.id.iv_text_error);
            chat_time = itemView.findViewById(R.id.chat_time);

        }
    }

    public static class TEXT_OTHER_HOLDER extends RecyclerView.ViewHolder {

        TextView item_other_send_text;
        TextView other_chat_time;

        public TEXT_OTHER_HOLDER(View itemView) {
            super(itemView);
            item_other_send_text = itemView.findViewById(R.id.item_other_send_text);
            other_chat_time = itemView.findViewById(R.id.other_chat_time);

        }

    }

    private class VOICE_MY_HOLDER extends RecyclerView.ViewHolder {

        private ImageView iv_send_voice_anim;
        private LinearLayout rl_my_send_voice_play;
        private TextView item_my_send_time;
        private ImageView iv_my_voice_error;
        private TextView my_voice_chat_time;
        private LinearLayout my_send_voice_long;

        public VOICE_MY_HOLDER(View my_voice) {
            super(my_voice);
            iv_send_voice_anim = my_voice.findViewById(R.id.iv_send_voice_anim);
            item_my_send_time = my_voice.findViewById(R.id.item_my_send_time);
            rl_my_send_voice_play = my_voice.findViewById(R.id.rl_my_send_voice_play);
            iv_my_voice_error = my_voice.findViewById(R.id.iv_my_voice_error);
            my_voice_chat_time = my_voice.findViewById(R.id.my_voice_chat_time);
            my_send_voice_long = my_voice.findViewById(R.id.my_send_voice_long);


        }
    }

    private class VOICE_OTHER_HOLDER extends RecyclerView.ViewHolder {

        private RelativeLayout other_receivre_voice_play;
        private RelativeLayout other_send_voice_long;
        private final TextView item_other_send_time;
        private TextView other_voice_chat_time, yuyin_res_position;
        private final ImageView receive_other_voice_anim;

        public VOICE_OTHER_HOLDER(View other_send_voice) {
            super(other_send_voice);
            other_receivre_voice_play = other_send_voice.findViewById(R.id.other_receivre_voice_play);
            other_send_voice_long = other_send_voice.findViewById(R.id.other_send_voice_long);
            item_other_send_time = other_send_voice.findViewById(R.id.item_other_send_time);
            other_voice_chat_time = other_send_voice.findViewById(R.id.other_voice_chat_time);
            receive_other_voice_anim = other_send_voice.findViewById(R.id.receive_other_voice_anim);
            yuyin_res_position = other_send_voice.findViewById(R.id.yuyin_res_position);

        }
    }


}
