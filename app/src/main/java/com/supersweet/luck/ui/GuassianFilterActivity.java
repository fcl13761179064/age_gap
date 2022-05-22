package com.supersweet.luck.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.User;
import com.supersweet.luck.dialog.DelectAccountDialog;
import com.supersweet.luck.dialog.NoTitleDialog;
import com.supersweet.luck.mvp.present.GuassianPresenter;
import com.supersweet.luck.mvp.present.RegisterPresenter;
import com.supersweet.luck.mvp.view.GuassianView;
import com.supersweet.luck.mvp.view.RegisterView;
import com.supersweet.luck.pictureselector.PictureSelector;
import com.supersweet.luck.utils.AppManager;
import com.supersweet.luck.utils.BitmapUtil;
import com.supersweet.luck.utils.BitmapUtils;
import com.supersweet.luck.widget.AppBar;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;

public class GuassianFilterActivity extends BaseMvpActivity<GuassianView, GuassianPresenter> implements GuassianView {

    @BindView(R.id.appBar)
    AppBar appBar;
    @BindView(R.id.iv_show_img)
    ImageView iv_show_img;
    @BindView(R.id.guassian_one)
    ImageView guassian_one;
    @BindView(R.id.guassian_two)
    ImageView guassian_two;
    @BindView(R.id.guassian_three)
    ImageView guassian_three;
    @BindView(R.id.login_submitBtn)
    TextView login_submitBtn;
    @BindView(R.id.choose_right_one)
    ImageView choose_right_one;
    @BindView(R.id.choose_right_two)
    ImageView choose_right_two;
    @BindView(R.id.choose_right_three)
    ImageView choose_right_three;
    private String guassianImg;
    private static Bitmap outBitmapOne;
    private static Bitmap outBitmapTwo;
    private static Bitmap outBitmapThree;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap mBitmap1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_guassian_filter;
    }

    @Override
    protected void initView() {
        AppData.vagueLevel = "1";
        guassianImg = getIntent().getStringExtra("guassianImg");
        appBar.getCenterText().setTextColor(getResources().getColor(R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Uri parse = Uri.parse(guassianImg);
            mBitmap1 = BitmapUtils.decodeUriAsBitmap(this,parse);
        } else {
             mBitmap1 = BitmapUtils.getimage(guassianImg);
        }
        bitmap1 = BlurBitmapOne(this, mBitmap1, 1);
        guassian_one.setImageBitmap(bitmap1);
        bitmap2 = BlurBitmapTwo(this, mBitmap1, 13);
        guassian_two.setImageBitmap(bitmap2);
        bitmap3 = BlurBitmapThree(this, mBitmap1, 25);
        guassian_three.setImageBitmap(bitmap3);
        iv_show_img.setImageBitmap(bitmap1);
        guassian_one.setColorFilter(Color.parseColor("#6B26CD"), PorterDuff.Mode.LIGHTEN);
        choose_right_one.setVisibility(View.VISIBLE);
    }


    @Override
    protected void initListener() {

    }


    @Override
    protected GuassianPresenter initPresenter() {
        return new GuassianPresenter();
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void LocationSuccess(AllCountryBean data) {

    }

    @OnClick({R.id.guassian_one, R.id.guassian_two, R.id.guassian_three, R.id.login_submitBtn})
    void handleCbClicked(View view) {
        switch (view.getId()) {
            case R.id.guassian_one:
                //Glide实现图片高斯模糊
                AppData.vagueLevel = "1";
                iv_show_img.setImageBitmap(bitmap1);
                // 颜色滤镜 30%透明白  4D
                guassian_one.setColorFilter(Color.parseColor("#6B26CD"), PorterDuff.Mode.LIGHTEN);
                guassian_two.clearColorFilter();
                guassian_three.clearColorFilter();
                choose_right_one.setVisibility(View.VISIBLE);
                choose_right_two.setVisibility(View.GONE);
                choose_right_three.setVisibility(View.GONE);
                break;
            case R.id.guassian_two:
                AppData.vagueLevel = "2";
                iv_show_img.setImageBitmap(bitmap2);
                // 颜色滤镜 30%透明白  4D
                guassian_two.setColorFilter(Color.parseColor("#6B26CD"), PorterDuff.Mode.LIGHTEN);
                guassian_one.clearColorFilter();
                guassian_three.clearColorFilter();
                choose_right_two.setVisibility(View.VISIBLE);
                choose_right_one.setVisibility(View.GONE);
                choose_right_three.setVisibility(View.GONE);
                break;
            case R.id.guassian_three:
                AppData.vagueLevel = "3";
                iv_show_img.setImageBitmap(bitmap3);
                // 颜色滤镜 30%透明白  4D
                guassian_three.setColorFilter(Color.parseColor("#6B26CD"), PorterDuff.Mode.LIGHTEN);
                guassian_two.clearColorFilter();
                guassian_one.clearColorFilter();
                choose_right_three.setVisibility(View.VISIBLE);
                choose_right_one.setVisibility(View.GONE);
                choose_right_two.setVisibility(View.GONE);
                break;
            case R.id.login_submitBtn:
                if ("1".equals(AppData.vagueLevel)) {
                    AppData.BitMap = bitmap1;
                } else if ("2".equals(AppData.vagueLevel)) {
                    AppData.BitMap = bitmap2;
                } else {
                    AppData.BitMap = bitmap3;
                }
                Intent intent = new Intent();
                intent.putExtra("guassian_url",guassianImg);
                setResult(Activity.RESULT_OK,intent);
                finish();
                break;
        }
    }


    /**
     * 获取模糊的图片
     *
     * @param context 上下文对象
     * @param bitmap  传入的bitmap图片
     * @param radius  模糊度（Radius最大只能设置25.f）
     * @return
     */
    public static Bitmap BlurBitmapOne(Context context, Bitmap bitmap, int radius) {
        try {
            //用需要创建高斯模糊bitmap创建一个空的bitmap
            outBitmapOne = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            // 初始化Renderscript，该类提供了RenderScript context，创建其他RS类之前必须先创建这个类，其控制RenderScript的初始化，资源管理及释放
            RenderScript rs = RenderScript.create(context);
            // 创建高斯模糊对象
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            // 创建Allocations，此类是将数据传递给RenderScript内核的主要方 法，并制定一个后备类型存储给定类型
            Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
            Allocation allOut = Allocation.createFromBitmap(rs, outBitmapOne);
            //设定模糊度(注：Radius最大只能设置25.f)
            blurScript.setRadius(radius);
            // Perform the Renderscript
            blurScript.setInput(allIn);
            blurScript.forEach(allOut);
            // Copy the final bitmap created by the out Allocation to the outBitmap
            allOut.copyTo(outBitmapOne);
            // recycle the original bitmap
            // bitmap.recycle();
            // After finishing everything, we destroy the Renderscript.
            rs.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outBitmapOne;
    }

    public static Bitmap BlurBitmapTwo(Context context, Bitmap bitmap, int radius) {
        try {
            //用需要创建高斯模糊bitmap创建一个空的bitmap
            outBitmapTwo = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            // 初始化Renderscript，该类提供了RenderScript context，创建其他RS类之前必须先创建这个类，其控制RenderScript的初始化，资源管理及释放
            RenderScript rs = RenderScript.create(context);
            // 创建高斯模糊对象
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            // 创建Allocations，此类是将数据传递给RenderScript内核的主要方 法，并制定一个后备类型存储给定类型
            Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
            Allocation allOut = Allocation.createFromBitmap(rs, outBitmapTwo);
            //设定模糊度(注：Radius最大只能设置25.f)
            blurScript.setRadius(radius);
            // Perform the Renderscript
            blurScript.setInput(allIn);
            blurScript.forEach(allOut);
            // Copy the final bitmap created by the out Allocation to the outBitmap
            allOut.copyTo(outBitmapTwo);
            // recycle the original bitmap
            // bitmap.recycle();
            // After finishing everything, we destroy the Renderscript.
            rs.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outBitmapTwo;
    }


    public static Bitmap BlurBitmapThree(Context context, Bitmap bitmap, int radius) {
        try {
            //用需要创建高斯模糊bitmap创建一个空的bitmap
            outBitmapThree = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            // 初始化Renderscript，该类提供了RenderScript context，创建其他RS类之前必须先创建这个类，其控制RenderScript的初始化，资源管理及释放
            RenderScript rs = RenderScript.create(context);
            // 创建高斯模糊对象
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            // 创建Allocations，此类是将数据传递给RenderScript内核的主要方 法，并制定一个后备类型存储给定类型
            Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
            Allocation allOut = Allocation.createFromBitmap(rs, outBitmapThree);
            //设定模糊度(注：Radius最大只能设置25.f)
            blurScript.setRadius(radius);
            // Perform the Renderscript
            blurScript.setInput(allIn);
            blurScript.forEach(allOut);
            // Copy the final bitmap created by the out Allocation to the outBitmap
            allOut.copyTo(outBitmapThree);
            // recycle the original bitmap
            // bitmap.recycle();
            // After finishing everything, we destroy the Renderscript.
            rs.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outBitmapThree;
    }

}
