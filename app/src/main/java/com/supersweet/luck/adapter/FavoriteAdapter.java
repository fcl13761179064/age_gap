package com.supersweet.luck.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.imageloader.GlideImageLoader;
import com.supersweet.luck.widget.MyDatas;

public class FavoriteAdapter extends BaseQuickAdapter<FavoritesBean.Love, BaseViewHolder> {


    public FavoriteAdapter() {
        super(R.layout.item_my_favorites);
    }

    @Override
    protected void convert(BaseViewHolder helper,FavoritesBean.Love item) {
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
        TextView desc = helper.getView(R.id.desc);
        LinearLayout ll_layout = helper.getView(R.id.ll_layout);
        RelativeLayout rl_btn = helper.getView(R.id.rl_btn);
        GlideLocalImageUtils.displayBigOrSmallShadowImage(MyApplication.getContext(), view,sex, avatar,"big");
        tv_user_name.setText(userName);
        desc.setText(MyDatas.sextosting(sex) + ", " + age + ", " + station);
        if (isOnline==1){
            ll_layout.setVisibility(View.VISIBLE);

        }
        if (item.getHighLightFlag()==1){
            rl_btn.setBackgroundResource(R.drawable.highing_green);
        }else {
            rl_btn.setBackgroundResource(R.color.white);
        }
    }
}
