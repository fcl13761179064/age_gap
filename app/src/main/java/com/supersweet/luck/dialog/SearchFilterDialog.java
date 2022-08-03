package com.supersweet.luck.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.doublebar.MySeekBar;
import com.supersweet.luck.fragment.ProfileFragment;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.wheelview.common.WheelData;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.MyDatas;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterDialog extends DialogFragment {

    private String title;
    private int minAge = 18;
    private int maxAge = 88;
    private static String sex;
    private List<String> titles;

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
        titles = new ArrayList<String>();
        titles.add("Height");
        titles.add("Body Type");
        titles.add("Hair Color");
        titles.add("Relationship");
        titles.add("Education");
        titles.add("Ethnicity");
        titles.add("Drinking");
        titles.add("Smoking");
        titles.add("Children");
        ReAdapter reAdapter = new ReAdapter(getActivity(), titles);
        recyclerView.setAdapter(reAdapter);

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

    private class ReAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String> data;
        private final Activity activity;

        public ReAdapter(Activity activity, List data) {
            this.activity = activity;
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = View.inflate(activity, R.layout.fragment_profile, null);
            MyHolder myHolder = new MyHolder(inflate);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            if (holder instanceof MyHolder) {
                MyHolder myHolder = (MyHolder) holder;
                String type = data.get(position);
                myHolder.tv_type.setText(type);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<WheelData> data = selectData(position);
                        TimePickerDialog
                                .newInstance(new TimePickerDialog.DoneCallback() {
                                    @Override
                                    public void onDone(TimePickerDialog dialog, String data, int mPosition) {
                                        if (position == 0) {
                                            myHolder.tv_type.setText(data + "cm");
                                        } else {
                                            myHolder.tv_type.setText(data);
                                        }

                                        dialog.dismissAllowingStateLoss();
                                    }

                                })
                                .setdata(data)
                                .show(getActivity().getSupportFragmentManager(), "time");
                    }
                });

            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_myinfo_content, tv_type;

        public MyHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_myinfo_content = (TextView) itemView.findViewById(R.id.tv_myinfo_content);
        }
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

    public interface Callback {
        void onDone(SearchFilterDialog dialog, String sex, int minAge, int maxAge);

        void onCancel(SearchFilterDialog dialog);

        void location(View dialog, TextView tv_current_location);
    }


}
