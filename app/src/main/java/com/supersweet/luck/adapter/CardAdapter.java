package com.supersweet.luck.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.dialog.CustomSheet;
import com.supersweet.luck.dialog.NoTitleDialog;
import com.supersweet.luck.dialog.ReportUserDialog;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.widget.CustomToast;

import java.util.List;

public abstract class CardAdapter extends BaseQuickAdapter<SeachPeopleBean, BaseViewHolder> {


    public CardAdapter(int layoutResId, List<SeachPeopleBean> datas) {
        super(layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, SeachPeopleBean item) {
        String avatar = item.getAvatar();
        String userName = item.getUserName();
        String sex = item.getSex();
        int age = item.getAge();
        String city = item.getCity();
        String station = item.getStation();
        int isOnline = item.getIsOnline();
        String qscore = item.getQscore();
        String distance = item.getDistance();
        GlideLocalImageUtils.displayBigOrSmallShadowImage(mContext, (ImageView) helper.getView(R.id.image_view),sex,avatar, "big");
        String my_desc = sex + ", " + age + ", " + station;
        ((TextView) helper.itemView.findViewById(R.id.text_view_one)).setText(userName);
        ((TextView) helper.itemView.findViewById(R.id.text_view_two)).setText(my_desc);
        ((TextView) helper.itemView.findViewById(R.id.tv_credit_fen)).setText(qscore);
        ((TextView) helper.itemView.findViewById(R.id.text_view_three)).setText(distance+" km away");
        if (item.getHighLightFlag() == 1) {
            helper.setBackgroundRes(R.id.image_view, R.drawable.highing_green);
        }
        LinearLayout ll_online =((LinearLayout) helper.itemView.findViewById(R.id.ll_online));
        if (isOnline==1){
            ll_online.setVisibility(View.VISIBLE);

        }else {
            ll_online.setVisibility(View.GONE);
        }
        ((ImageView) helper.itemView.findViewById(R.id.iv_block_user)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFource();
            }
        });
    }


    public abstract void  setFource();
}
