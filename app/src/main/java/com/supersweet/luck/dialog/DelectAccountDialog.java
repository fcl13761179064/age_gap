package com.supersweet.luck.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.supersweet.luck.R;


public class DelectAccountDialog extends DialogFragment {
    public static DelectAccountDialog newInstance(Callback doneCallback) {

        Bundle args = new Bundle();
        DelectAccountDialog fragment = new DelectAccountDialog();
        fragment.setArguments(args);
        fragment.doneCallback = doneCallback;
        return fragment;
    }

    private Callback doneCallback;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         return inflater.inflate(R.layout.dialog_delect_account, container, false);
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
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        dialogWindow.getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
        TextView ensureTextView = view.findViewById(R.id.login_submitBtn);
       EditText Leaving_reason = view.findViewById(R.id.Leaving_reason);
       EditText et_password = view.findViewById(R.id.et_password);
       ImageView close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ensureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doneCallback != null) {
                    doneCallback.onDone(DelectAccountDialog.this, et_password.getText().toString().trim(), Leaving_reason.getText().toString().trim());
                }
            }
        });
    }

    public interface Callback {
        void onDone(DelectAccountDialog delectAccountDialog, String password, String learnResion);
    }
}
