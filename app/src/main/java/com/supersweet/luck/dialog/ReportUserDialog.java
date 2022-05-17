package com.supersweet.luck.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;
import com.supersweet.luck.adapter.MinImagePickerAdapter;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.bean.ImageItem;
import com.supersweet.luck.imageloader.GlideImageLoader;
import com.supersweet.luck.ui.EditProfileActivity;
import com.supersweet.luck.ui.ImageGridActivity;
import com.supersweet.luck.ui.ImagePreviewDelActivity;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.view.CropImageView;
import com.supersweet.luck.wheelview.common.WheelData;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.ImagePicker;
import com.supersweet.luck.widget.MyDatas;
import com.supersweet.luck.widget.SelectDialog;

import java.util.ArrayList;
import java.util.List;

public class ReportUserDialog extends DialogFragment implements MinImagePickerAdapter.OnRecyclerViewItemClickListener {

    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private MinImagePickerAdapter adapter;
    ArrayList<ImageItem> images = null;
    private static int maxImgCount = 4;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    public static final int IMAGE_ITEM_ADD = -1;


    public static ReportUserDialog newInstance(Callback doneCallback) {
        initImagePicker();
        Bundle args = new Bundle();
        ReportUserDialog fragment = new ReportUserDialog();
        fragment.setArguments(args);
        fragment.doneCallback = doneCallback;

        return fragment;
    }


    private static void initImagePicker() {
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

    private String title;

    public ReportUserDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    private String content;

    public ReportUserDialog setContent(String content) {
        this.content = content;
        return this;
    }

    private Callback doneCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_report_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Window dialogWindow = getDialog().getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.horizontalMargin = 0;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setAttributes(lp);
        dialogWindow.getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
        TextView et_reason = view.findViewById(R.id.et_reason);
        EditText et_question = view.findViewById(R.id.et_question);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ImageView close = view.findViewById(R.id.close);
        TextView submitBtn = view.findViewById(R.id.submitBtn);
        RelativeLayout iv_guide = view.findViewById(R.id.iv_guide);
        selImageList = new ArrayList<>();
        adapter = new MinImagePickerAdapter(getContext(), selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        iv_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.newInstance(new TimePickerDialog.DoneCallback() {

                    @Override
                    public void onDone(TimePickerDialog dialog, String data, int mPosition) {
                        et_reason.setText( data);
                        dialog.dismissAllowingStateLoss();
                    }

                }).setdata(MyDatas.getReason())
                        .show(getFragmentManager(), "time");
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doneCallback != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int x = 0; x < selImageList.size(); x++) {
                        String path = selImageList.get(x).path;
                        if (x == selImageList.size() - 1) {
                            sb.append(path).append("");
                        } else {
                            sb.append(path).append(",");
                        }
                    }
                    doneCallback.onDone(ReportUserDialog.this, et_reason.getText().toString().trim(), et_question.getText().toString().trim(), sb.toString());
                }
            }
        });
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
                Intent intentPreview = new Intent(getContext(), ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }


    public interface Callback {
        void onDone(ReportUserDialog reportUserDialog, String reason, String question, String imgString);
    }


    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(getActivity(), R.style.transparentFrameWindowStyle, listener, names);
        if (!getActivity().isFinishing()) {
            dialog.show();
        }
        return dialog;
    }
}
