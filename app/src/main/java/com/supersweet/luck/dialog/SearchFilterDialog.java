package com.supersweet.luck.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.doublebar.MySeekBar;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppData;

public class SearchFilterDialog extends DialogFragment {

    private String title;
    private int minAge = 18;
    private int maxAge = 88;
    private static String sex;

    public static SearchFilterDialog newInstance(Callback doneCallback) {
        Bundle args = new Bundle();
        SearchFilterDialog fragment = new SearchFilterDialog();
        fragment.setArguments(args);
        fragment.doneCallback = doneCallback;
        return fragment;
    }


    public SearchFilterDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    private String content;

    public SearchFilterDialog setContent(String content) {
        this.content = content;
        return this;
    }

    private Callback doneCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_serch_filter, container, false);
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
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton filter_male = view.findViewById(R.id.filter_male);
        RadioButton filter_female = view.findViewById(R.id.filter_female);
        RelativeLayout rl_location = view.findViewById(R.id.rl_location);
        TextView tv_current_location = view.findViewById(R.id.tv_current_location);
        TextView tv_min_age = view.findViewById(R.id.tv_min_age);
        TextView tv_max_age = view.findViewById(R.id.tv_max_age);
        MySeekBar rangeSeekBar = (MySeekBar) view.findViewById(R.id.rangeSeekBar);

        if (!TextUtils.isEmpty(AppData.Filter_country) && !TextUtils.isEmpty(AppData.Filter_city)) {
            tv_current_location.setText(AppData.Filter_country + "  " + AppData.Filter_city);
        }


        rangeSeekBar.setPos(AppData.Filter_minAge - 18, AppData.Filter_maxAge - 18);
        tv_min_age.setText(AppData.Filter_minAge + "");
        tv_max_age.setText(AppData.Filter_maxAge + "");

        if (!AppData.is_come_in) {
            sex = SharePreferenceUtils.getString(getContext(), Constance.SP_SEX, "1");
            if ("1".equals(sex)) {
                filter_female.setChecked(true);
            } else {
                filter_male.setChecked(true);
            }
        } else {
            if ("1".equals(sex)) {
                filter_male.setChecked(true);
            } else {
                filter_female.setChecked(true);
            }
        }

        if (filter_female.isChecked()) {
            sex = "2";
        } else {
            sex = "1";
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (filter_male.getId() == checkedId) {
                    filter_male.setChecked(true);
                    sex = "1";
                }
                if (filter_female.getId() == checkedId) {
                    filter_female.setChecked(true);
                    sex = "2";
                }
            }
        });
        view.findViewById(R.id.tv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (doneCallback != null) {
                    doneCallback.onDone(SearchFilterDialog.this, sex, minAge, maxAge);
                }
            }
        });
        view.findViewById(R.id.filter_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doneCallback != null) {
                    doneCallback.onCancel(SearchFilterDialog.this);
                }
            }
        });
        rl_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doneCallback != null) {
                    doneCallback.location(v, tv_current_location);
                }
            }
        });

        rangeSeekBar.setListener(new MySeekBar.OnSeekFinishListener() {
            @Override
            public void seekPos(MySeekBar.CircleIndicator left, MySeekBar.CircleIndicator right) {
                tv_min_age.setText(left.getPoint().getMark() + "");
                tv_max_age.setText(right.getPoint().getMark() + "");
                minAge = left.getPoint().getMark();
                maxAge = right.getPoint().getMark();
            }
        });
    }


    public interface Callback {
        void onDone(SearchFilterDialog dialog, String sex, int minAge, int maxAge);

        void onCancel(SearchFilterDialog dialog);

        void location(View dialog, TextView tv_current_location);
    }


}
