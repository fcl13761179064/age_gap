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
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.doublebar.MySeekBar;
import com.supersweet.luck.mvp.model.RequestModel;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.wheelview.common.WheelData;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.MyDatas;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchFilterDialog extends DialogFragment {

    private String title;
    private int minAge = 18;
    private int maxAge = 88;
    private static String sex;
    private String select_distance="";

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
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RelativeLayout one = (RelativeLayout) view.findViewById(R.id.one);
        RelativeLayout two = (RelativeLayout) view.findViewById(R.id.two);
        RelativeLayout three = (RelativeLayout) view.findViewById(R.id.three);
        RelativeLayout four = (RelativeLayout) view.findViewById(R.id.four);
        RelativeLayout five = (RelativeLayout) view.findViewById(R.id.five);
        RelativeLayout six = (RelativeLayout) view.findViewById(R.id.six);
        RelativeLayout seven = (RelativeLayout) view.findViewById(R.id.seven);
        RelativeLayout eight = (RelativeLayout) view.findViewById(R.id.eight);
        RelativeLayout nine = (RelativeLayout) view.findViewById(R.id.nine);

        TextView tv_myinfo_one = (TextView) view.findViewById(R.id.tv_myinfo_one);
        TextView tv_myinfo_two = (TextView) view.findViewById(R.id.tv_myinfo_two);
        TextView tv_myinfo_three = (TextView) view.findViewById(R.id.tv_myinfo_three);
        TextView tv_myinfo_four = (TextView) view.findViewById(R.id.tv_myinfo_four);
        TextView tv_myinfo_five = (TextView) view.findViewById(R.id.tv_myinfo_five);
        TextView tv_myinfo_seven = (TextView) view.findViewById(R.id.tv_myinfo_seven);
        TextView tv_myinfo_six = (TextView) view.findViewById(R.id.tv_myinfo_six);
        TextView tv_myinfo_eight = (TextView) view.findViewById(R.id.tv_myinfo_eight);
        TextView tv_myinfo_nine = (TextView) view.findViewById(R.id.tv_myinfo_nine);

        TextView tv_one = (TextView) view.findViewById(R.id.tv_one);
        TextView tv_two = (TextView) view.findViewById(R.id.tv_two);
        TextView tv_three = (TextView) view.findViewById(R.id.tv_three);
        TextView tv_four = (TextView) view.findViewById(R.id.tv_four);
        TextView tv_five = (TextView) view.findViewById(R.id.tv_five);
        TextView tv_six = (TextView) view.findViewById(R.id.tv_six);
        TextView tv_seven = (TextView) view.findViewById(R.id.tv_seven);
        TextView tv_eight = (TextView) view.findViewById(R.id.tv_eight);
        TextView tv_nine = (TextView) view.findViewById(R.id.tv_nine);

        RadioGroup location_radiogroup = view.findViewById(R.id.location_radiogroup);
        RadioButton live_in = view.findViewById(R.id.live_in);
        RadioButton distance = view.findViewById(R.id.distance);
        SeekBar seekbar_distance = view.findViewById(R.id.seekbar_distance);
        seekbar_distance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                select_distance = progress+"";
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        location_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (live_in.getId() == checkedId) {
                    live_in.setChecked(true);
                    rl_location.setVisibility(View.VISIBLE);
                    seekbar_distance.setVisibility(View.GONE);
                }
                if (distance.getId() == checkedId) {
                    distance.setChecked(true);
                    rl_location.setVisibility(View.GONE);
                    seekbar_distance.setVisibility(View.VISIBLE);
                }
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilterData(0,tv_myinfo_one,tv_one);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilterData(1,tv_myinfo_two,tv_two);
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilterData(2,tv_myinfo_three,tv_three);
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilterData(3,tv_myinfo_four,tv_four);
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilterData(4,tv_myinfo_five,tv_five);
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilterData(5,tv_myinfo_six,tv_six);
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilterData(6,tv_myinfo_seven,tv_seven);
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilterData(7,tv_myinfo_eight,tv_eight);
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilterData(8,tv_myinfo_nine,tv_nine);
            }
        });


        if (!TextUtils.isEmpty(AppData.Filter_country) && !TextUtils.isEmpty(AppData.Filter_city)) {
            tv_current_location.setText(AppData.Filter_country + "  " + AppData.Filter_city);
        }


        rangeSeekBar.setPos(AppData.Filter_minAge - 18, AppData.Filter_maxAge - 18);
        tv_min_age.setText(AppData.Filter_minAge + "");
        tv_max_age.setText(AppData.Filter_maxAge + "");

        tv_current_location.setVisibility(View.VISIBLE);
        seekbar_distance.setVisibility(View.GONE);
        live_in.setChecked(true);
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
                    doneCallback.onDone(SearchFilterDialog.this, sex, minAge, maxAge,tv_one.getText().toString()
                    ,tv_two.getText().toString()
                    ,tv_three.getText().toString()
                    ,tv_four.getText().toString()
                    ,tv_five.getText().toString()
                    ,tv_six.getText().toString()
                    ,tv_seven.getText().toString()
                    ,tv_eight.getText().toString()
                    ,tv_nine.getText().toString(),select_distance);
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
        void onDone(SearchFilterDialog dialog, String sex, int minAge, int maxAge, String tv_myinfo_Text, String tv_myinfo_twoText, String tv_myinfo_threeText, String tv_myinfo_fourText, String tv_myinfo_fiveText, String tv_myinfo_sixText, String tv_myinfo_sevenText, String tv_myinfo_eightText, String tv_myinfo_nineText,String distance);

        void onCancel(SearchFilterDialog dialog);

        void location(View dialog, TextView tv_current_location);
    }

    public List<WheelData> selectData(int position) {
        ArrayList<WheelData> datas = null;
        if (position == 0) {
            datas = createDatasOne();
        } else if (position == 1) {
            datas = MyDatas.bodyTypes();
        } else if (position == 2) {
            datas = MyDatas.HairTypes();
        } else if (position == 3) {
            datas = MyDatas.RelationShipTypes();
        } else if (position == 4) {
            datas = MyDatas.educationTypes();
        } else if (position == 5) {
            datas = MyDatas.ethnicitys();
        } else if (position == 6) {
            datas = MyDatas.drinkings();
        } else if (position == 7) {
            datas = MyDatas.smokings();
        } else if (position == 8) {
            datas = MyDatas.childs();
        }

        return datas;

    }

    private ArrayList<WheelData> createDatasOne() {
        ArrayList<WheelData> list = new ArrayList<>();
        WheelData item;
        for (int i = 100; i < 220; i++) {
            item = new WheelData();
            item.setName(i + "cm");
            list.add(item);
        }
        return list;
    }

   public  void  setFilterData(int position,  TextView view,TextView tv_one){
       List<WheelData> data = selectData(position);
       TimePickerDialog
               .newInstance(new TimePickerDialog.DoneCallback() {
                   @Override
                   public void onDone(TimePickerDialog dialog, String data, int mPosition) {
                       if (position == 0) {
                           view.setText(data + "cm");
                           tv_one.setText(data);
                       } else {
                           tv_one.setText("00"+(mPosition+1));
                           view.setText(data);
                       }
                       dialog.dismissAllowingStateLoss();
                   }

               })
               .setdata(data)
               .show(getActivity().getSupportFragmentManager(), "time");
   }
}
