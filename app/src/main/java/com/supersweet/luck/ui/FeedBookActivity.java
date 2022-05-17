package com.supersweet.luck.ui;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.MinImagePickerAdapter;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.ImageItem;
import com.supersweet.luck.dialog.NoTitleDialog;
import com.supersweet.luck.dialog.SingleConfireDialog;
import com.supersweet.luck.dialog.TimePickerDialog;
import com.supersweet.luck.imageloader.GlideImageLoader;
import com.supersweet.luck.mvp.present.FeedBookPresenter;
import com.supersweet.luck.mvp.view.FeedBookView;
import com.supersweet.luck.utils.AppManager;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.view.CropImageView;
import com.supersweet.luck.widget.AppBar;
import com.supersweet.luck.widget.CustomToast;
import com.supersweet.luck.widget.ImagePicker;
import com.supersweet.luck.widget.MyDatas;
import com.supersweet.luck.widget.SelectDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.supersweet.luck.application.MyApplication.getContext;

public class FeedBookActivity extends BaseMvpActivity<FeedBookView, FeedBookPresenter> implements FeedBookView, MinImagePickerAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.iv_guide)
    RelativeLayout iv_guide;
    @BindView(R.id.et_reason)
    TextView et_reason;
    @BindView(R.id.et_question)
    EditText et_question;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.appBar)
    AppBar appBar;
    private String et_questions;
    private String et_subjects;
    private MinImagePickerAdapter adapter;
    private int maxImgCount = 4;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    ArrayList<ImageItem> images = null;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    public static final int IMAGE_ITEM_ADD = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_book;
    }

    @Override
    protected void initView() {
        appBar.setCenterText("feedback");
        initImagePicker();
        selImageList = new ArrayList<>();
        adapter = new MinImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(1000);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(1600);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
        imagePicker.setMultiMode(false);
    }

    @OnClick({R.id.iv_guide, R.id.et_question, R.id.submitBtn})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < selImageList.size(); x++) {
                    String path = selImageList.get(x).path;
                    if (x == selImageList.size() - 1) {
                        sb.append(path).append("");
                    } else {
                        sb.append(path).append(",");
                    }
                }
                et_subjects = et_reason.getText().toString().trim();
                et_questions = et_question.getText().toString().trim();
                if (TextUtils.isEmpty(et_subjects)){
                    CustomToast.makeText(MyApplication.getContext(), "Please describe your title.", R.drawable.ic_toast_warming).show();
                    return;
                }

                if (TextUtils.isEmpty(et_questions)){
                    CustomToast.makeText(MyApplication.getContext(), "Please describe proble content.", R.drawable.ic_toast_warming).show();
                    return;
                }
                mPresenter.submitFeedbook(et_subjects, et_questions, sb.toString());
                break;
            case R.id.iv_guide:
                TimePickerDialog.newInstance(new TimePickerDialog.DoneCallback() {
                    @Override
                    public void onDone(TimePickerDialog dialog, String data, int mPosition) {
                        et_reason.setText(data);
                        dialog.dismissAllowingStateLoss();
                    }

                }).setdata(MyDatas.getSubject())
                        .show(getSupportFragmentManager(), "time");

                break;
            default:
                break;
        }
    }


    @Override
    protected void initListener() {

    }


    @Override
    protected FeedBookPresenter initPresenter() {
        return new FeedBookPresenter();
    }

    @Override
    public void success(Object data) {

        SingleConfireDialog singleConfireDialog = new SingleConfireDialog(this, "Thank you for your feedback.You should receive a response within 24 hours.");
        singleConfireDialog.setGravity(Gravity.CENTER);
        singleConfireDialog.setOnSureClick("OK ", new SingleConfireDialog.OnSureClick() {
            @Override
            public void click(Dialog dialog) {
                AppManager.getAppManager().finishActivity();
                dialog.dismiss();
            }
        });
        singleConfireDialog.show();
    }

    @Override
    public void errorShake(String msg) {
        ToastUtils.showLongToast("submit errror");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    images.get(0).type = "Location";
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("Take a Photo");
                names.add("Choose from Album");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                /**
                                 * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                                 *
                                 * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                                 *
                                 * 如果实在有所需要，请直接下载源码引用。
                                 */
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(getContext(), ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(getContext(), ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }

                    }
                }, names);


                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(FeedBookActivity.this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style.transparentFrameWindowStyle, listener, names);
        if (!isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

}
