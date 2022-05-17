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


public class HighingConsumeCoinDialog extends DialogFragment {
    private String allCoins;
    private String reduce_coin;

    public static HighingConsumeCoinDialog newInstance(Callback doneCallback) {

        Bundle args = new Bundle();
        HighingConsumeCoinDialog fragment = new HighingConsumeCoinDialog();
        fragment.setArguments(args);
        fragment.doneCallback = doneCallback;
        return fragment;
    }

    private Callback doneCallback;

    private String title;

    public HighingConsumeCoinDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public HighingConsumeCoinDialog setContent(String reduce_coin,String allCoins) {
        this.reduce_coin = reduce_coin;
        this.allCoins = allCoins;
        return this;
    }

    private String cancelText;

    public HighingConsumeCoinDialog setCancelText(String cancelText) {
        this.cancelText = cancelText;
        return this;
    }

    private String ensureText;

    public HighingConsumeCoinDialog setEnsureText(String ensureText) {
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
        return inflater.inflate(R.layout.dialog_highing_consume_coin_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView ensureTextView = view.findViewById(R.id.v_done);
        TextView mutch_coins = view.findViewById(R.id.mutch_coins);
        TextView reduce_coins = view.findViewById(R.id.reduce_coins);
        ensureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doneCallback != null) {
                    doneCallback.onDone(HighingConsumeCoinDialog.this);
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
                    doneCallback.onCancel(HighingConsumeCoinDialog.this);
                }
            }
        });
        if (!TextUtils.isEmpty(allCoins)){
            mutch_coins.setText(allCoins);
        }

        if (!TextUtils.isEmpty(reduce_coin)){
            reduce_coins.setText(reduce_coin);
        }
        if (!TextUtils.isEmpty(cancelText)) {
            cancelTextView.setText(cancelText);
        }
    }

    public interface Callback {
        void onDone(HighingConsumeCoinDialog dialog);

        void onCancel(HighingConsumeCoinDialog dialog);
    }
}
