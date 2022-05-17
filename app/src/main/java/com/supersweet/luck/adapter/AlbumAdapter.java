package com.supersweet.luck.adapter;

import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;
import com.supersweet.luck.myphoto.ImageBean0;
import com.supersweet.luck.ui.ChooseAlbumActivity;
import com.supersweet.luck.utils.DensityUtil;
import com.supersweet.luck.utils.DensityUtils;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.List;
import static androidx.core.content.FileProvider.getUriForFile;


/**
 * Author: fanchunlei
 * Date:2017/4/14.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.VH> implements View.OnClickListener {

    private ChooseAlbumActivity mAlbumActivity;

    private List<ImageBean0> mImageBeen;

    private int mBucketIndex;
    private static final int INTENT_ZOOM = 101;

    // 剪切后图像文件
    private Uri mDestinationUri;
    private final float screenWidth;
    private final float screenHight;
    private final float crop_width;
    private final float cropo_hight;
    private final float radio_hight;

    public AlbumAdapter(ChooseAlbumActivity albumActivity, int bucketIndex) {
        mAlbumActivity = albumActivity;
        mBucketIndex = bucketIndex;
        mImageBeen = ChooseAlbumActivity.albumPicker.getBuckets().get(bucketIndex).getImageBeen();
        mDestinationUri = Uri.fromFile(new File(mAlbumActivity.getExternalCacheDir(), mAlbumActivity.getString(R.string.ysq_album_zoom)));
        screenWidth = DensityUtils.getDisplayWidth(albumActivity);
        screenHight = DensityUtils.getDisplayHeight(albumActivity);
        crop_width = screenWidth- DensityUtil.dp2px(15);
        cropo_hight = screenHight - (screenHight / 3.2f);
        radio_hight = cropo_hight / crop_width;
    }


    @Override
    public AlbumAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(mAlbumActivity).inflate(R.layout.ysq_recyclerview_album1, parent, false));
    }

    @Override
    public void onBindViewHolder(final AlbumAdapter.VH holder, int position) {
        Glide.with(mAlbumActivity).load(mImageBeen.get(position).getImage_path())
                .into(holder.imageView);
        ((VH) holder).imageView.setTag(R.id.tag_path, mImageBeen.get(position).getImage_path());
        holder.imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String path = (String) v.getTag(R.id.tag_path);
        Uri uri = getUri(new File(path));
        startCropActivity(uri);
    }



    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startCropActivity(Uri uri) {

    }

    private Uri getUri(File file) {
        if (Build.VERSION.SDK_INT < 24) {
            return Uri.fromFile(file);
        } else {
            return getUriForFile(mAlbumActivity,
                    mAlbumActivity.getPackageName() + ".ysq.fileprovider", file);
        }
    }

    @Override
    public int getItemCount() {
        return mImageBeen.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView imageView;

        private VH(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }


}