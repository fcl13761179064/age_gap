package com.supersweet.luck.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;
import com.supersweet.luck.base.BaseMvpFragment;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.mvp.present.AbountPresenter;
import com.supersweet.luck.mvp.view.AbountView;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;

import java.util.ArrayList;
import java.util.List;

public class AbountFragment extends BaseMvpFragment<AbountView, AbountPresenter> implements AbountView {

    private RecyclerView recyclerView;
    private Activity activity;
    private List<String> arrayList;


    @Override
    protected AbountPresenter initPresenter() {
        return new AbountPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment_abount;
    }

    @Override
    protected void initView(View view) {
        activity = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        arrayList = new ArrayList<String>();
        arrayList.add("Aount");

    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void initData() {
        ReAdapter reAdapter = new ReAdapter(activity, arrayList);
        recyclerView.setAdapter(reAdapter);
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void AbountSuccess(MyInfoBean data) {
        CustomToast.makeText(getContext(), "update success...", R.drawable.ic_toast_warming).show();
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
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_about, parent, false);//解决宽度不能铺满
            MyHolder myHolder = new MyHolder(inflate);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyHolder) {
                ((MyHolder) holder).et_des.setText(AppData.updataAbount);
                ((MyHolder) holder).tv_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.editMyinfo(((MyHolder) holder).et_des.getText().toString().trim());
                        AppData.updataAbount = (((MyHolder) holder).et_des.getText().toString().trim());
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

        public TextView tv_next;
        public EditText et_des;


        public MyHolder(View itemView) {
            super(itemView);
            tv_next = itemView.findViewById(R.id.tv_next);
            et_des = itemView.findViewById(R.id.et_des);

        }
    }
}
