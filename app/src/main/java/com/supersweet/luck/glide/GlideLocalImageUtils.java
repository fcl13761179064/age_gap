package com.supersweet.luck.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.ui.MainActivity;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CicleImgTransform;

public class GlideLocalImageUtils {

    //展示方形大图和小图，包括占位符
    public static void displayBigOrSmallShadowImage(Context activity, ImageView imageView, String sex, String path,String type) {
        if (!TextUtils.isEmpty(path) && path.contains("http")) {
            path = path;
        } else {
            path = Constance.getBaseUrl() + path;
        }
        if ("small".equalsIgnoreCase(type)) {
            if ("2".equalsIgnoreCase(sex) || "Female".equalsIgnoreCase(sex)) {
                Glide.with(activity)                             //配置上下文
                        .load(path)
                        .fitCenter() //刷新后变形问题//设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .error(R.mipmap.signin_female_notselect)           //设置错误图片
                        .centerCrop()
                        .placeholder(R.mipmap.signin_female_notselect)     //设置占位图片
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//不缓存
                        .into(imageView);
            } else {
                Glide.with(activity)                             //配置上下文
                        .load(path)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .error(R.mipmap.signin_male_notselect)           //设置错误图片
                        .placeholder(R.mipmap.signin_male_notselect)     //设置占位图片
                        .centerCrop()
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//不缓存
                        .into(imageView);
            }
        } else {
            if ("2".equalsIgnoreCase(sex)|| "Female".equalsIgnoreCase(sex)) {
                Glide.with(activity)                             //配置上下文
                        .load(path)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .error(R.mipmap.card_match_women)           //设置错误图片
                        .placeholder(R.mipmap.card_match_women)     //设置占位图片
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//不缓存
                        .into(imageView);
            } else {
                Glide.with(activity)                             //配置上下文
                        .load(path)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .error(R.mipmap.card_match_man)           //设置错误图片
                        .placeholder(R.mipmap.card_match_man)     //设置占位图片
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//不缓存
                        .into(imageView);
            }
        }
    }

    //展示小图片，小圆圈图片
    public static void displaySmallImg(Context activity, ImageView imageView, String sex, String path) {
        if (!TextUtils.isEmpty(path) && path.contains("http")) {
            path = path;
        } else {
            path = Constance.getBaseUrl() + path;
        }

        if ("2".equals(sex) || "Female".equalsIgnoreCase(sex)) {
            Glide.with(activity).load(path).transform(new CicleImgTransform(MyApplication.getContext(), 1, Color.parseColor("#DFDFDF"))).placeholder(R.mipmap.signin_female_notselect).error(R.mipmap.signin_female_notselect).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        } else {
            Glide.with(activity).load(path).transform(new CicleImgTransform(MyApplication.getContext(), 1, Color.parseColor("#DFDFDF"))).placeholder(R.mipmap.signin_male_notselect).error(R.mipmap.signin_male_notselect).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        }
    }

}
