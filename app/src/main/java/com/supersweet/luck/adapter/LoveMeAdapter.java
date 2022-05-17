package com.supersweet.luck.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.glide.GlideLocalImageUtils;

public class LoveMeAdapter extends BaseQuickAdapter<FavoritesBean.Love, BaseViewHolder> {


    public LoveMeAdapter() {
        super(R.layout.item_me_love);
    }

    @Override
    protected void convert(BaseViewHolder helper, FavoritesBean.Love item) {
        String avatar = item.getAvatar();
        String userName = item.getUserName();
        String sex = item.getSex();
        int age = item.getAge();
        String city = item.getCity();
        String station = item.getStation();
        ImageView view = helper.getView(R.id.iv_image);
        if (item.getHighLightFlag() == 1) {
           helper.setBackgroundRes(R.id.rl_head_color,R.drawable.highing_circle_corners);
        }else {
            helper.setBackgroundRes(R.id.rl_head_color,R.drawable.gray_circle_corners);
        }
        GlideLocalImageUtils.displaySmallImg(MyApplication.getContext(), view, item.getSex(), item.getAvatar());
    }
}
