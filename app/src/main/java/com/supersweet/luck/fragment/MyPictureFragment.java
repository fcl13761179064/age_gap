package com.supersweet.luck.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;
import com.supersweet.luck.adapter.ImagePickerAdapter;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.base.BaseMvpFragment;
import com.supersweet.luck.bean.ImageItem;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.SelfAlbumBean;
import com.supersweet.luck.bean.UpImgBean;
import com.supersweet.luck.imageloader.GlideImageLoader;
import com.supersweet.luck.mvp.present.MyPicturePresenter;
import com.supersweet.luck.mvp.view.MyPictureView;
import com.supersweet.luck.ui.ImageGridActivity;
import com.supersweet.luck.ui.ImagePreviewDelActivity;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.view.CropImageView;
import com.supersweet.luck.widget.ImagePicker;
import com.supersweet.luck.widget.SelectDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyPictureFragment extends BaseMvpFragment<MyPictureView, MyPicturePresenter> implements MyPictureView, ImagePickerAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public static final int IMAGE_ITEM_ADD = -1;
    private int maxImgCount = 12;               //允许选择图片最大数
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    ArrayList<ImageItem> images = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mypic;
    }

    @Override
    protected void initView(View view) {
        initImagePicker();
        initWidget();
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

    private void initWidget() {
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(getContext(), selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        mPresenter.getSelfAlbum();
    }

    @Override
    protected void initData() {


    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void success(UpImgBean data) {

    }

    @Override
    public void SelfAbulmsuccess(List<SelfAlbumBean> data) {
        for (SelfAlbumBean s:data) {
            String photoPath = s.getPhotoPath();
            ImageItem m= new ImageItem();
            m.path = Constance.getBaseUrl()+photoPath;
            m.type ="Network";
            m.id =s.getId();
            selImageList.add(m);
            adapter.setImages(selImageList);
        }

    }


    @Override
    protected MyPicturePresenter initPresenter() {
        return new MyPicturePresenter();
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
                                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(getActivity(), ImageGridActivity.class);
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
                Intent intentPreview = new Intent(getActivity(), ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(getActivity(), R.style.transparentFrameWindowStyle, listener, names);
        if (!getActivity().isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    images.get(0).type="Location";
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                    mPresenter.uploadImg(getActivity(), images.get(0).path);
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
}
