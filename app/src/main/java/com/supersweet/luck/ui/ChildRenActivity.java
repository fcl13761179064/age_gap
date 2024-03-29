package com.supersweet.luck.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.CommonAdapter;
import com.supersweet.luck.base.BasicActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.supersweet.luck.utils.FastClickUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;

import java.util.ArrayList;

import butterknife.BindView;

public class ChildRenActivity extends BasicActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    private ArrayList<String> bodyType;
    private CommonAdapter bodyTypeActivity;
    private int currentPosition;
    private String children;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_children;
    }

    @Override
    protected void initView() {
        initDat();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);
        bodyTypeActivity = new CommonAdapter();
        bodyTypeActivity.addData(bodyType);
        recyclerview.setAdapter(bodyTypeActivity);
        bodyTypeActivity.bindToRecyclerView(recyclerview);
        bodyTypeActivity.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    private void initDat() {
        bodyType = new ArrayList<>();
        bodyType.add("No child");
        bodyType.add("One child");
        bodyType.add("Two children");
        bodyType.add("Three children");
        bodyType.add("Four children");
        bodyType.add("Over four children");
    }


    @Override
    protected void initListener() {
        bodyTypeActivity.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String  botyType = (String) adapter.getData().get(position);
                //这里赋值
                currentPosition = position;
                //每点击一次item就刷新适配器
                adapter.notifyDataSetChanged();
            }
        });
        bodyTypeActivity.setItemSelectedCallBack(new CommonAdapter.ItemSelectedCallBack() {
            @Override
            public void convert(BaseViewHolder holder, int position, String title) {
                TextView view = holder.getView(R.id.tv_body_type);
                if (position==currentPosition){
                    children = "00"+(position+1);
                    view.setBackgroundResource(R.drawable.locaion_checkable_shape);
                    view.setTextColor(getResources().getColor(R.color.location_select));
                }else {
                    view.setBackgroundResource(R.drawable.locaion_uncheckable_shape);
                    view.setTextColor(getResources().getColor(R.color.location_unselect));
                }
            }

        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                if (TextUtils.isEmpty(children)) {
                    CustomToast.makeText(ChildRenActivity.this, "children is required", R.drawable.ic_toast_warming).show();
                    return;
                }
                Intent intent = new Intent(ChildRenActivity.this, AvatarActivity.class);
                intent.putExtras(getIntent());
                intent.putExtra("children",children);
                startActivity(intent);
            }
        });
    }
}
