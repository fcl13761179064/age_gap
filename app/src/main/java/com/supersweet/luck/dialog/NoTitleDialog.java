package com.supersweet.luck.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.supersweet.luck.R;
import com.supersweet.luck.utils.DensityUtils;


public class NoTitleDialog extends DialogFragment {
    public static NoTitleDialog newInstance(Callback doneCallback) {

        Bundle args = new Bundle();
        NoTitleDialog fragment = new NoTitleDialog();
        fragment.setArguments(args);
        fragment.doneCallback = doneCallback;
        return fragment;
    }

    private Callback doneCallback;

    private String title;

    public NoTitleDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    private String content;

    public NoTitleDialog setContent(String content) {
        this.content = content;
        return this;
    }

    private String cancelText;

    public NoTitleDialog setCancelText(String cancelText) {
        this.cancelText = cancelText;
        return this;
    }

    private String ensureText;

    public NoTitleDialog setEnsureText(String ensureText) {
        this.ensureText = ensureText;
        return this;
    }


    public  void setGravity(int gravity){
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.width = (int) (DensityUtils.getDisplayWidth(getContext()) * 0.9f);
        lp.gravity= gravity;
        getActivity().getWindow().setAttributes(lp);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_no_title, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView ensureTextView = view.findViewById(R.id.v_done);
        ensureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doneCallback != null) {
                    doneCallback.onDone(NoTitleDialog.this);
                }
            }
        });
        if (!TextUtils.isEmpty(ensureText)) {
            ensureTextView.setText(ensureText);
        }
        TextView cancelTextView = view.findViewById(R.id.v_cancel);
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doneCallback != null) {
                    doneCallback.onCancel(NoTitleDialog.this);
                }
            }
        });
        if (!TextUtils.isEmpty(cancelText)) {
            cancelTextView.setText(cancelText);
        }
        TextView titleTextView = view.findViewById(R.id.tv_title);
        TextView contentTextView = view.findViewById(R.id.tv_content);
        if (TextUtils.isEmpty(title)) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }
        contentTextView.setText(content);
    }

    public interface Callback {
        void onDone(NoTitleDialog dialog);

        void onCancel(NoTitleDialog dialog);
    }
}
