package com.supersweet.luck.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.MetaDataUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.gms.common.util.DataUtils;
import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.BlockMemberbean;
import com.supersweet.luck.bean.PayCordBean;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.utils.TimeUtils;
import com.supersweet.luck.view.RoundImageView;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.MyDatas;

import java.util.List;

public abstract class PayRecordAdapter extends BaseQuickAdapter<PayCordBean.PayCord, BaseViewHolder> {


    public PayRecordAdapter() {
        super(R.layout.adapter_pay_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayCordBean.PayCord item) {
        String coinName = item.getCoinName();
        int type = item.getCoinType();
        int changeNum = item.getChangeNum();
        String eventTime = item.getEventTime();
        TextView tv_coin_name = helper.getView(R.id.tv_coin_name);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_coin_num = helper.getView(R.id.tv_coin_num);
        TextView tv_delete = helper.getView(R.id.tv_delete);
        RoundImageView img_head = helper.getView(R.id.img_head);

        tv_coin_name.setText(coinName);
        if (type==1){
            img_head.setBackground(MyApplication.getContext().getDrawable(R.mipmap.record_onlinechat));
        }else if (type ==2){
            img_head.setBackground(MyApplication.getContext().getDrawable(R.mipmap.record_mutualmatch));
        }else if (type ==3){
            img_head.setBackground(MyApplication.getContext().getDrawable(R.mipmap.record_interest));
        }else if (type ==4){
            img_head.setBackground(MyApplication.getContext().getDrawable(R.mipmap.record_onlinechat));
        }else if (type ==5){
            img_head.setBackground(MyApplication.getContext().getDrawable(R.mipmap.record_highlighted));
        }else if (type ==6){
            img_head.setBackground(MyApplication.getContext().getDrawable(R.mipmap.search));
        }else if (type ==98){
            img_head.setBackground(MyApplication.getContext().getDrawable(R.mipmap.record_purchase));
        }else if (type ==99){
            img_head.setBackground(MyApplication.getContext().getDrawable(R.mipmap.record_onlinechat));
        }

        tv_coin_num.setText(changeNum+"");
        try {
            tv_time.setText(TimeUtils.formatTimeEight(eventTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             removePayRecord(item.getId(),item);
            }

        });
    }

    public abstract  void removePayRecord(int Id, PayCordBean.PayCord item) ;
}
