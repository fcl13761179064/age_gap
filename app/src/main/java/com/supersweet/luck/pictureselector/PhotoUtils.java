package com.supersweet.luck.pictureselector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PhotoUtils {

    /**
     * 调用系统裁剪<br>
     *
     * @param uri        需要裁剪的图片路径
     * @param mImagePath 图片输出路径
     * @param sizeX      裁剪x
     * @param sizeY      裁剪y
     */
    public static Intent startPhotoZoom(Uri uri, Uri mImagePath, int sizeX, int sizeY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        //裁剪后输出路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImagePath);
        //输入图片路径
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("circleCrop", "true");
        //华为的手机原型裁剪适应大小
        if (Build.MANUFACTURER.equals("HUAWEI")) {
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        } else {
            intent.putExtra("aspectX", sizeX);
            intent.putExtra("aspectY", sizeY);
        }
        //输出的图片size越大越清晰，但是过大可能会导致程序崩溃
        intent.putExtra("outputX", sizeX);
        intent.putExtra("outputY", sizeY);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        //设置不返回数据，而是返回JPEG格式，在函数中调用编码函数再显示，这样图片更清晰
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);

        return intent;
    }


    /**
     * 保存图片到文件File。
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return true 成功 false 失败
     */
    public static boolean save(Bitmap src, File file, Bitmap.CompressFormat format, boolean recycle) {
        if (src == null)
            return false;

        OutputStream os;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, 100, os);
            if (recycle && !src.isRecycled())
                src.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
