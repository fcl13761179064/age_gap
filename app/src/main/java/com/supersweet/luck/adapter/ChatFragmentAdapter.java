package com.supersweet.luck.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.ChatInfoBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.ui.MainActivity;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;

import static com.blankj.utilcode.util.ViewUtils.runOnUiThread;


/**
 * Author: fanchunlei
 * Date:2017/4/14.
 */

public abstract class ChatFragmentAdapter extends BaseQuickAdapter<OtherUserInfoBean, BaseViewHolder> {


    public ChatFragmentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OtherUserInfoBean item) {
        TextView tv_user_name = helper.getView(R.id.tv_user_name);
        ImageView img_head = helper.getView(R.id.img_head);
        TextView tv_des = helper.getView(R.id.tv_desc);
        TextView yuyin_res_position = helper.getView(R.id.yuyin_res_position);
        TextView tv_delete = helper.getView(R.id.tv_delete);
        tv_des.setText(item.getMessage());
        tv_user_name.setText(item.getUserName());
        GlideLocalImageUtils.displaySmallImg(MyApplication.getContext(), img_head,item.getSex(), item.getAvatar());
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteConnection(item.getUserId(), item);
            }

        });
        new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(200L);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TIMConversation con = TIMManager.getInstance().getConversation(TIMConversationType.C2C, item.getUserId());
                            long unreadMessageNum = con.getUnreadMessageNum();
                            if (unreadMessageNum > 0) {
                                yuyin_res_position.setVisibility(View.VISIBLE);
                            } else {
                                yuyin_res_position.setVisibility(View.GONE);
                            }
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public abstract void deleteConnection(String connectionUserId, OtherUserInfoBean item);

}