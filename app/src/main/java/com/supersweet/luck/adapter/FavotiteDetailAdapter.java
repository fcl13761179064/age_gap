package com.supersweet.luck.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.FavoritesBean;

import java.util.List;
import java.util.Map;

import static com.blankj.utilcode.util.ColorUtils.getColor;

public class FavotiteDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public FavotiteDetailAdapter() {

        super(R.layout.adapter_favorites_detail);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        try{
            String[] s = item.split(",");
            String s1 = s[0];
            String s2 = s[1];
            ((TextView) helper.itemView.findViewById(R.id.tv_des)).setText(s1);
            ((TextView) helper.itemView.findViewById(R.id.tv_myfinfo)).setText(s2);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
