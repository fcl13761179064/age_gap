package com.supersweet.luck.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;

import java.util.ArrayList;

public class CommonFragment  extends Fragment {
        private RecyclerView recyclerView;
        private Activity activity;
        private ArrayList<String> datas;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_common, container, false);
            initdata();
            activity = getActivity();
            recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(new ReAdapter(activity));
            return view;
        }

        private void initdata() {
            datas = new ArrayList<>();
            for (int i = 0;i < 50; i++){
                datas.add(String.valueOf(i));
            }
        }

        private class ReAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
            public ReAdapter(Activity activity) {

            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate = View.inflate(activity, R.layout.item_common,null);
                MyHolder myHolder = new MyHolder(inflate);
                return myHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                if (holder instanceof MyHolder){
                    MyHolder myHolder = (MyHolder) holder;
                    myHolder.text.setText(datas.get(position));
                }
            }

            @Override
            public int getItemCount() {
                return datas.size();
            }
        }
        class MyHolder extends RecyclerView.ViewHolder{
            TextView text;
            public MyHolder(View itemView) {
                super(itemView);
                text = (TextView)itemView.findViewById(R.id.text);
            }
        }
}
