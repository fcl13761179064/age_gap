package com.supersweet.luck.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.ImageItem;
import com.supersweet.luck.fragment.MyPictureFragment;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.ui.MainActivity;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.ImagePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：ikkong （ikkong@163.com），修改 jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：微信图片选择的Adapter, 感谢 ikkong 的提交
 * ================================================
 */
public class MinImagePickerAdapter extends RecyclerView.Adapter<MinImagePickerAdapter.SelectedPicViewHolder> {
    private int maxImgCount;
    private Context mContext;
    private List<ImageItem> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;
    private boolean isAdded;   //是否额外添加了最后一个图片

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setImages(List<ImageItem> data) {
        mData = new ArrayList<>(data);
        if (getItemCount() < maxImgCount) {
            mData.add(new ImageItem());
            isAdded = true;
        } else {
            isAdded = false;
        }
        notifyDataSetChanged();
    }

    public List<ImageItem> getImages() {
        //由于图片未选满时，最后一张显示添加图片，因此这个方法返回真正的已选图片
        if (isAdded) return new ArrayList<>(mData.subList(0, mData.size() - 1));
        else return mData;
    }

    public MinImagePickerAdapter(Context mContext, List<ImageItem> data, int maxImgCount) {
        this.mContext = mContext;
        this.maxImgCount = maxImgCount;
        this.mInflater = LayoutInflater.from(mContext);
        setImages(data);
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.min_list_item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_img;
        private int clickPosition;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }

        public void bind(int position) {
            //设置条目的点击事件
            itemView.setOnClickListener(this);
            //根据条目位置设置图片
            ImageItem item = mData.get(position);
            if (isAdded && position == getItemCount() - 1) {
                iv_img.setImageResource(R.drawable.selector_image_add);
                clickPosition = MyPictureFragment.IMAGE_ITEM_ADD;
            } else {
                if ("Network".equalsIgnoreCase(item.type)) {
                    //设置图片圆角角度
                    RoundedCorners roundedCorners = new RoundedCorners(360);
                    //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(190, 190);
                    String sex = SharePreferenceUtils.getString(MyApplication.getContext(), Constance.SP_SEX, "1");
                    GlideLocalImageUtils.displayBigOrSmallShadowImage(MyApplication.getContext(), iv_img,sex,item.path, "big");
                } else {
                    ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, iv_img, 0, 0);
                }
                clickPosition = position;
            }
        }

        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, clickPosition);
        }
    }
}