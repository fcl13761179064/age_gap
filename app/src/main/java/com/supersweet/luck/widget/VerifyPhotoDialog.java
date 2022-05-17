package com.supersweet.luck.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.supersweet.luck.R;

public class VerifyPhotoDialog extends DialogFragment {
    public static VerifyPhotoDialog newInstance(Callback doneCallback) {

        Bundle args = new Bundle();
        VerifyPhotoDialog fragment = new VerifyPhotoDialog();
        fragment.setArguments(args);
        fragment.doneCallback = doneCallback;
        return fragment;
    }


    private String title;

    public VerifyPhotoDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    private String content;

    public VerifyPhotoDialog setContent(String content) {
        this.content = content;
        return this;
    }

    private Callback doneCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_verify_photo, container, false);
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

        view.findViewById(R.id.tv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doneCallback != null) {
                    doneCallback.onDone(VerifyPhotoDialog.this);
                }
            }
        });
        view.findViewById(R.id.verify_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doneCallback != null) {
                    doneCallback.onCancel(VerifyPhotoDialog.this);
                }
            }
        });

    }

    public interface Callback {
        void onDone(VerifyPhotoDialog dialog);

        void onCancel(VerifyPhotoDialog dialog);

    }


}
