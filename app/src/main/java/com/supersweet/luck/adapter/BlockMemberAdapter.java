package com.supersweet.luck.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.BlockMemberbean;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.MyDatas;

public abstract class BlockMemberAdapter extends BaseQuickAdapter<BlockMemberbean.BlickMemberInfo, BaseViewHolder> {


    public BlockMemberAdapter() {
        super(R.layout.adapter_block_member);
    }

    @Override
    protected void convert(BaseViewHolder helper, BlockMemberbean.BlickMemberInfo item) {
        String avatar = item.getAvatar();
        String userName = item.getUserName();
        String sex = item.getSex();
        int age = item.getAge();
        String city = item.getCity();
        String station = item.getStation();
        ImageView view = helper.getView(R.id.img_head);
        TextView tv_user_name = helper.getView(R.id.tv_user_name);
        TextView tv_sex = helper.getView(R.id.tv_sex);
        TextView user_age = helper.getView(R.id.age);
        TextView location_station = helper.getView(R.id.location_station);
        TextView tv_delete = helper.getView(R.id.tv_delete);
        GlideLocalImageUtils.displaySmallImg(MyApplication.getContext(), view,sex,avatar);
        tv_user_name.setText(userName);
        user_age.setText(age+"");
        tv_sex.setText(MyDatas.sextosting(sex));
        location_station.setText(AppData.location_station);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             removeBlockuser(item.getUserId(),item);
            }

        });
    }

    public abstract  void removeBlockuser(int deviceId, BlockMemberbean.BlickMemberInfo item) ;
}
