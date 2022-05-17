package com.supersweet.luck.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.MultualMatchBean;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.utils.SharePreferenceUtils;

public class MultualMatchAdapter extends BaseQuickAdapter<MultualMatchBean, BaseViewHolder> {


    public MultualMatchAdapter() {
        super(R.layout.item_mutual_match);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultualMatchBean item) {
        String avatar = item.getAvatar();
        String userName = item.getUserName();
        String sex = item.getSex();
        int age = item.getAge();
        String city = item.getCity();
        String station = item.getStation();
        ImageView view = helper.getView(R.id.iv_image);
        GlideLocalImageUtils.displaySmallImg(MyApplication.getContext(), view,sex, item.getAvatar());
        if (item.getHighLightFlag() == 1) {
            helper.setBackgroundRes(R.id.rl_head_color,R.drawable.highing_circle_corners);
        }else {
            helper.setBackgroundRes(R.id.rl_head_color,R.drawable.gray_circle_corners);
        }
    }
}
