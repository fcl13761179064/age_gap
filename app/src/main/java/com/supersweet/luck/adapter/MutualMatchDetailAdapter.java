package com.supersweet.luck.adapter;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.MultualMatchBean;
import com.supersweet.luck.glide.GlideLocalImageUtils;

import java.util.List;

public class MutualMatchDetailAdapter extends BaseQuickAdapter<MultualMatchBean, BaseViewHolder> {


    public MutualMatchDetailAdapter() {
        super(R.layout.adapte_mutual_match_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper,MultualMatchBean item) {
        String avatar = item.getAvatar();
        String userName = item.getUserName();
        String sex = item.getSex();
        int age = item.getAge();
        String city = item.getCity();
        String station = item.getStation();
        int isOnline = item.getIsOnline();
        ImageView view = helper.getView(R.id.iv_image);
        TextView onlie = helper.getView(R.id.onlie);
        TextView tv_user_name = helper.getView(R.id.tv_user_name);
        TextView tv_sex = helper.getView(R.id.tv_sex);
        TextView tv_age = helper.getView(R.id.age);
        TextView location_station = helper.getView(R.id.location_station);
        RelativeLayout rl_head_color = helper.getView(R.id.rl_head_color);
        GlideLocalImageUtils.displayBigOrSmallShadowImage(mContext, view,avatar, avatar,"big");

        if ("Female".equalsIgnoreCase(sex) || "2".equals(sex)) {
            tv_sex.setText("Female");
            Glide.with(MyApplication.getContext()).load(Constance.getBaseUrl() + item.getAvatar()).placeholder(R.mipmap.card_match_women).error(R.mipmap.card_match_women).into(view);
        } else {
            Glide.with(MyApplication.getContext()).load(Constance.getBaseUrl() + item.getAvatar()).placeholder(R.mipmap.card_match_man).error(R.mipmap.card_match_man).into(view);
            tv_sex.setText("Male");
        }
        tv_user_name.setText(userName);
        tv_age.setText(age+"");
        location_station.setText(station);
        if (isOnline==1){
            onlie.setText("OnLine");
        }

        if (item.getHighLightFlag() == 1) {
            rl_head_color.setBackgroundResource(R.drawable.highing_green);
        }else {
            rl_head_color.setBackgroundResource(R.color.white);
        }
    }
}
