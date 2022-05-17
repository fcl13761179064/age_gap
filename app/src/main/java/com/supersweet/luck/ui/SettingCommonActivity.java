package com.supersweet.luck.ui;

import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.FavotiteDetailAdapter;
import com.supersweet.luck.adapter.SettingCommonAdapter;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.base.BasicActivity;
import com.supersweet.luck.bean.CompanyBean;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.mvp.present.PolicyPresenter;
import com.supersweet.luck.mvp.present.RegisterPresenter;
import com.supersweet.luck.mvp.present.SettingPresenter;
import com.supersweet.luck.mvp.view.PolicyView;
import com.supersweet.luck.mvp.view.RegisterView;
import com.supersweet.luck.mvp.view.SettingView;
import com.supersweet.luck.utils.AppManager;
import com.supersweet.luck.widget.AppBar;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
import com.supersweet.luck.widget.MyDatas;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SettingCommonActivity extends BaseMvpActivity <PolicyView, PolicyPresenter> implements PolicyView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerview;
    @BindView(R.id.appBar)
    AppBar appBar;
    private SettingCommonAdapter settingCommonAdapter;
    private List<String> titles;
    private CompanyBean s;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_common_page;
    }

    @Override
    protected void initView() {
        titles = new ArrayList<String>();
        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        settingCommonAdapter = new SettingCommonAdapter();
        recyclerview.setAdapter(settingCommonAdapter);
        settingCommonAdapter.bindToRecyclerView(recyclerview);
        settingCommonAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }


    @Override
    protected void initListener() {
        String type = getIntent().getStringExtra("type");
        if ("1".equals(type)) {
            titles.add("Privacy policy");
            titles.add("Terms of use");
            appBar.setCenterText("About");
            View inflate = LayoutInflater.from(this).inflate(R.layout.version_layout, null, false);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(0, 50, 0, 0);// 设置间距
            inflate.setLayoutParams(lp);
            TextView tv_des = inflate.findViewById(R.id.tv_des);
            TextView version_code = inflate.findViewById(R.id.version_code);

            settingCommonAdapter.addData(titles);
            settingCommonAdapter.addFooterView(inflate);

            settingCommonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (position == 0) {
                        Intent intent = new Intent(SettingCommonActivity.this, PolicyActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("url", "1");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SettingCommonActivity.this, PolicyActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("url", "2");
                        startActivity(intent);
                    }

                }
            });
        } else {
            titles.add("Change Email Address");
            titles.add("Change Password");
            appBar.setCenterText("Account");
            settingCommonAdapter.addData(titles);
            settingCommonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (position == 0) {
                        Intent intent = new Intent(SettingCommonActivity.this, AlterAccountEmailActivity.class);
                        intent.putExtra("type", "1");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SettingCommonActivity.this, AlterAccoutPasswordActivity.class);
                        intent.putExtra("type", "2");
                        startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    protected PolicyPresenter initPresenter() {
        return new PolicyPresenter();
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void PolicySuccess(CompanyBean data) {

    }

}
