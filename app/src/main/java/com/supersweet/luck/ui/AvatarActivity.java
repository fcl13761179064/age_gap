package com.supersweet.luck.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.supersweet.luck.R;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.mvp.present.AvaterPresenter;
import com.supersweet.luck.mvp.view.AvaterView;
import com.supersweet.luck.pictureselector.PictureBean;
import com.supersweet.luck.pictureselector.PictureSelector;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
import butterknife.BindView;
import static com.supersweet.luck.pictureselector.PictureSelector.SELECT_GUASSIAN;

public class AvatarActivity extends BaseMvpActivity<AvaterView, AvaterPresenter> implements AvaterView {

    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_take_photo)
    ImageView iv_take_photo;
    public static final String TAG = "PictureSelector";
    private String img_path = "";
    private String avatar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_avatha;
    }

    @Override
    protected void initView() {
        iv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector
                        .create(AvatarActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true);
            }
        });
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(img_path)) {
                    Intent intent = new Intent(AvatarActivity.this, AbountActivity.class);
                    intent.putExtras(getIntent());
                    intent.putExtra("avatar",avatar);
                    startActivity(intent);
                } else {
                    CustomToast.makeText(AvatarActivity.this, "Please upload a profile photo", R.drawable.ic_toast_warming).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            /*结果回调*/
            if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
                if (data != null) {
                    PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                    Log.i(TAG, "是否裁剪: " + pictureBean.isCut());
                    Log.i(TAG, "原图地址: " + pictureBean.getPath());
                    Log.i(TAG, "图片 Uri: " + pictureBean.getUri());
                    if (pictureBean.isCut()) {
                        Intent intent = new Intent(this, GuassianFilterActivity.class);
                        intent.putExtra("guassianImg", pictureBean.getPath());
                        startActivityForResult(intent, SELECT_GUASSIAN);
                    } else {
                        Intent intent = new Intent(this, GuassianFilterActivity.class);
                        intent.putExtra("guassianImg", pictureBean.getPath());
                        startActivityForResult(intent, SELECT_GUASSIAN);
                    }

                }
            } else if (requestCode == PictureSelector.SELECT_GUASSIAN) {
                Bitmap BproductImg = AppData.BitMap;
                if (BproductImg == null) {
                    return;
                }
                iv_take_photo.setImageBitmap(BproductImg);
                String guassianImg = data.getStringExtra("guassian_url");
                if (!TextUtils.isEmpty(guassianImg)) {
                    mPresenter.upHead(guassianImg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void success(String msg) {
        avatar = msg;
        this.img_path = msg;
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {
        CustomToast.makeText(AvatarActivity.this, "Upload failed", R.drawable.ic_toast_warming).show();
    }

    @Override
    protected AvaterPresenter initPresenter() {
        return new AvaterPresenter();
    }

}
