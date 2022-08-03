package com.supersweet.luck.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.base.BaseMvpFragment;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.dialog.TimePickerDialog;
import com.supersweet.luck.mvp.present.ProfilePresenter;
import com.supersweet.luck.mvp.view.ProfileView;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.wheelview.common.WheelData;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.MyDatas;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends BaseMvpFragment<ProfileView, ProfilePresenter> implements ProfileView {
    private RecyclerView recyclerView;
    private Activity activity;
    private List<String> arrayList;
    private List<String> titles;
    private MyInfoBean myInfobean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common;
    }

    @Override
    protected void initView(View view) {
        activity = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        arrayList = new ArrayList<String>();
        titles = new ArrayList<String>();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        mPresenter.getMyinfo();
    }

    @Override
    protected ProfilePresenter initPresenter() {
        return new ProfilePresenter();
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void MyInfo_success(MyInfoBean data) {
        if (data == null || data.getUser() == null) {
            return;
        }
        try {
            String about = data.getUser().getAbout();
            String height = data.getUser().getHeight();
            String body = data.getUser().getBody();
            String hair = data.getUser().getHair();
            String userStatus = data.getUser().getUserStatus();
            String education = data.getUser().getEducation();
            String ethnicity = data.getUser().getEthnicity();
            String drinking = data.getUser().getDrinking();
            String smoking = data.getUser().getSmoking();
            String children = data.getUser().getChildren();
            String sex = data.getUser().getSex();
            String userName = data.getUser().getUserName();
            int age = data.getUser().getAge();
            String country = data.getUser().getCountry();
            String updateAbout = data.getUser().getUpdateAbout();
            int isShow = data.getUser().getIsShow();
            int vagueLevel = data.getUser().getVagueLevel();
            AppData.country = country;
            AppData.updataAbount = updateAbout;
            AppData.isShow = isShow;
            AppData.vagueLevel = vagueLevel + "";

            String one = MyDatas.bodyType(body);
            String two = MyDatas.HairType(hair);
            String three = MyDatas.RelationShipType(userStatus);
            String four = MyDatas.educationType(education);
            String five = MyDatas.ethnicity(ethnicity);
            String six = MyDatas.drinking(drinking);
            String seven = MyDatas.smoking(smoking);
            String eight = MyDatas.child(children);

            arrayList.add(height);
            arrayList.add(one);
            arrayList.add(two);
            arrayList.add(three);
            arrayList.add(four);
            arrayList.add(five);
            arrayList.add(six);
            arrayList.add(seven);
            arrayList.add(eight);

            titles.add("Height");
            titles.add("Body Type");
            titles.add("Hair Color");
            titles.add("Relationship");
            titles.add("Education");
            titles.add("Ethnicity");
            titles.add("Drinking");
            titles.add("Smoking");
            titles.add("Children");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ReAdapter reAdapter = new ReAdapter(activity, arrayList);
        recyclerView.setAdapter(reAdapter);
    }

    @Override
    public void editMyInfoProgress(MyInfoBean s) {
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
            ProfileFragment.MyHolder myHolder = new ProfileFragment.MyHolder(inflate);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            if (holder instanceof ProfileFragment.MyHolder) {
                ProfileFragment.MyHolder myHolder = (ProfileFragment.MyHolder) holder;
                String type = data.get(position);
                String title = titles.get(position);
                if (position == 0) {
                    myHolder.tv_myinfo_content.setText(type + "cm");
                } else {
                    myHolder.tv_myinfo_content.setText(type);
                }
                myHolder.tv_type.setText(title);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<WheelData> data = selectData(position);
                        TimePickerDialog
                                .newInstance(new TimePickerDialog.DoneCallback() {
                                    @Override
                                    public void onDone(TimePickerDialog dialog, String data, int mPosition) {
                                        if (position == 0) {
                                            myHolder.tv_myinfo_content.setText(data + "cm");
                                            mPresenter.editMyinfo(position, data);
                                        } else {
                                            myHolder.tv_myinfo_content.setText(data);
                                            mPresenter.editMyinfo(position, "00" + (AppData.WheelPositon + 1));
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


    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_myinfo_content, tv_type;

        public MyHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_myinfo_content = (TextView) itemView.findViewById(R.id.tv_myinfo_content);
        }
    }
}