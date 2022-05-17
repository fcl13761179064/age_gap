package com.supersweet.luck.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.utils.TempUtils;

public class StaggerPhotoAdapter extends BaseQuickAdapter<SeachPeopleBean, BaseViewHolder> {


    public StaggerPhotoAdapter() {
        super(R.layout.adapte_verify_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SeachPeopleBean item) {
        String avatar = item.getAvatar();
        String userName = item.getUserName();
        String sex = item.getSex();
        int age = item.getAge();
        String city = item.getCity();
        String station = item.getStation();
        int albumNum = item.getAlbumNum();
        int isOnline = item.getIsOnline();
        ImageView view = helper.getView(R.id.iv_image);
        TextView onlie = helper.getView(R.id.onlie);
        LinearLayout ll_online = helper.getView(R.id.ll_online);
        TextView tv_user_name = helper.getView(R.id.tv_user_name);
        TextView tv_sex = helper.getView(R.id.tv_sex);
        TextView tv_age = helper.getView(R.id.age);
        TextView iv_photo_num = helper.getView(R.id.iv_photo_num);
        TextView location_station = helper.getView(R.id.location_station);
        if (sex.equalsIgnoreCase("Female")) {
            tv_sex.setText("Female");
            Glide.with(MyApplication.getContext()).load(Constance.getBaseUrl() + item.getAvatar()).placeholder(R.mipmap.card_match_women).error(R.mipmap.card_match_women).into(view);
        } else {
            Glide.with(MyApplication.getContext()).load(Constance.getBaseUrl() + item.getAvatar()).placeholder(R.mipmap.card_match_man).error(R.mipmap.card_match_man).into(view);
            tv_sex.setText("Male");
        }
        tv_user_name.setText(userName);
        iv_photo_num.setText(albumNum + "");
        tv_age.setText(age + "");
        location_station.setText(station);
        if (isOnline == 1) {
            onlie.setText("OnLine");
            ll_online.setVisibility(View.VISIBLE);
        }
        if (item.getHighLightFlag() == 1) {
            helper.setBackgroundRes(R.id.rl_btn, R.drawable.highing_green);
        }
    }
}
