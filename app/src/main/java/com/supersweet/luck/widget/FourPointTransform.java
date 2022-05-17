package com.supersweet.luck.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Arrays;

import io.reactivex.annotations.NonNull;

/**
 * author : Conan
 * time   : 2019-12-19
 * desc   : 自定义图片四个圆角
 */
public class FourPointTransform extends BitmapTransformation {
        private float radius;

        public FourPointTransform(int radius) {
            this.radius = radius;
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth,
                                   int outHeight)
        {
            return cornersImage(pool, toTransform);
        }

        private Bitmap cornersImage(BitmapPool pool, Bitmap bitmapSource) {
            Bitmap tempBitmap = null;
            try {
                if (bitmapSource == null) {
                    return null;
                }
                tempBitmap = pool.get(bitmapSource.getWidth(), bitmapSource.getHeight(),
                        Bitmap.Config.ARGB_8888);
                if (tempBitmap == null) {
                    tempBitmap = Bitmap.createBitmap(bitmapSource.getWidth(),
                            bitmapSource.getHeight(), Bitmap.Config.ARGB_8888);
                }
                Canvas canvas = new Canvas(tempBitmap);
                Paint paint = new Paint();
                paint.setShader(new BitmapShader(bitmapSource, BitmapShader.TileMode.CLAMP,
                        BitmapShader.TileMode.CLAMP));
                paint.setAntiAlias(true);

                RectF rectF = new RectF(0f, 0f, bitmapSource.getWidth(),
                        bitmapSource.getHeight());
                canvas.drawRoundRect(rectF, radius, radius, paint);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tempBitmap;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
