package com.supersweet.luck.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.base.BasicActivity;
import com.supersweet.luck.utils.FastClickUtils;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseSexActivity extends BasicActivity {
    @BindView(R.id.iv_female)
    CheckBox iv_female;
    @BindView(R.id.iv_male)
    CheckBox iv_male;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_male_sex_color)
    TextView tv_male_sex_color;
    @BindView(R.id.tv_female_sex_color)
    TextView tv_female_sex_color;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    private String sex;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_sex;
    }

    @Override
    protected void initView() {
        iv_female.setSelected(false);
        iv_male.setSelected(false);
       String sex = "";
    }


    @OnClick({R.id.iv_female, R.id.iv_male, R.id.tv_next})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_female:
                sex = "2";
                iv_female.setSelected(true);
                iv_male.setSelected(false);
                tv_female_sex_color.setTextColor(getResources().getColor(R.color.tv_six_female_color));
                tv_male_sex_color.setTextColor(getResources().getColor(R.color.tv_six_no_select_color));
                SharePreferenceUtils.saveString(this, Constance.SP_SEX, "2");
                break;
            case R.id.iv_male:
                 sex = "1";
                iv_female.setSelected(false);
                iv_male.setSelected(true);
                tv_female_sex_color.setTextColor(getResources().getColor(R.color.tv_six_no_select_color));
                tv_male_sex_color.setTextColor(getResources().getColor(R.color.tv_six_male_color));
                SharePreferenceUtils.saveString(this, Constance.SP_SEX, "1");
                break;
            case R.id.tv_next:
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                if (TextUtils.isEmpty(sex)) {
                    CustomToast.makeText(this, "Sex is required", R.drawable.ic_toast_warming).show();
                    return;
                }
                Intent intent2 = new Intent(ChooseSexActivity.this, ChooseAgeActivity.class);
                intent2.putExtras(getIntent());
                intent2.putExtra("sex",sex);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }


    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
