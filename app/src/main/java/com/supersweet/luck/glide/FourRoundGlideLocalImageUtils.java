package com.supersweet.luck.glide;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.FourPointTransform;

public class FourRoundGlideLocalImageUtils {
    public static void displayImage(Context activity, String loadpath, ImageView imageView, String type) {
        String sex = SharePreferenceUtils.getString(activity, Constance.SP_SEX, "1");
        String path = SharePreferenceUtils.getString(activity, Constance.SP_HEADER, "");
        if ("local".equals(type)) {
            RequestOptions options = new RequestOptions().transform(new FourPointTransform(1));
            if ("2".equalsIgnoreCase(sex)) {
                Glide.with(MyApplication.getContext()).load(path).apply(options).placeholder(R.mipmap.signin_female_notselect).error(R.mipmap.signin_female_notselect).into(imageView);
            } else {
                Glide.with(MyApplication.getContext()).load(path).apply(options).placeholder(R.mipmap.signin_male_notselect).error(R.mipmap.signin_male_notselect).into(imageView);
            }
        } else {
            RequestOptions options = new RequestOptions().transform(new FourPointTransform(1));
            if ("2".equalsIgnoreCase(sex)) {
                Glide.with(MyApplication.getContext()).load(loadpath).apply(options).placeholder(R.mipmap.signin_female_notselect).error(R.mipmap.signin_female_notselect).into(imageView);
            } else {
                Glide.with(MyApplication.getContext()).load(loadpath).apply(options).placeholder(R.mipmap.signin_male_notselect).error(R.mipmap.signin_male_notselect).into(imageView);
            }
        }

    }
}
