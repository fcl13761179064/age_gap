package com.supersweet.luck.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.StaggerPhotoAdapter;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpFragment;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.PhotoBean;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.mvp.present.VerrifyPhotoPresenter;
import com.supersweet.luck.mvp.view.VerrifyPhotoView;
import com.supersweet.luck.pictureselector.PictureBean;
import com.supersweet.luck.pictureselector.PictureSelector;
import com.supersweet.luck.rxbus.Config;
import com.supersweet.luck.rxbus.Myinfo;
import com.supersweet.luck.rxbus.RxBus;
import com.supersweet.luck.ui.FavoriteDetailActivity;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
import com.supersweet.luck.widget.VerifyPhotoDialog;

import java.io.Serializable;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.CompletionService;

import butterknife.BindView;


/**
 * Created by fanchunlei on 2020/10/1.
 * Blog:http://blog.csdn.net/student9128
 * Describe：the first fragment
 */

public class FragmentFour extends BaseMvpFragment<VerrifyPhotoView, VerrifyPhotoPresenter> implements VerrifyPhotoView {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_updata)
    TextView tv_updata;
    @BindView(R.id.verify_photo)
    LinearLayout verify_photo;
    @BindView(R.id.desc_one)
    TextView desc_one;
    @BindView(R.id.desc_two)
    TextView desc_two;
    private StaggerPhotoAdapter favoritesAdapter;
    private FragmentFour.fragmentFourToMainActivity fragmentFourToMainActivity;
    private String minAge = "18";
    private String maxAge = "88";
    private String sex;
    private String countryCode = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentFourToMainActivity = (fragmentFourToMainActivity) getActivity();
    }


    public interface fragmentFourToMainActivity {
        void fromFragmentFour();
    }


    public void verifyPhoto() {
        if (AppData.MyInfoBean != null && AppData.MyInfoBean.getUser() != null) {
            if (-1 == AppData.MyInfoBean.getUser().getVerifyStatus()) {
                verify_photo.setVisibility(View.VISIBLE);
                refreshLayout.setVisibility(View.GONE);
                tv_updata.setVisibility(View.VISIBLE);
                fragmentFourToMainActivity.fromFragmentFour();
            } else if (AppData.MyInfoBean != null && 2 == AppData.MyInfoBean.getUser().getVerifyStatus()) {
                refreshLayout.setVisibility(View.VISIBLE);
                verify_photo.setVisibility(View.GONE);
                refreshLayout.autoRefresh();
            } else if (AppData.MyInfoBean != null && 1 == AppData.MyInfoBean.getUser().getVerifyStatus()) {
                verify_photo.setVisibility(View.VISIBLE);
                desc_one.setText("You verification request is");
                desc_two.setText("processing.Please check back later");
                tv_updata.setVisibility(View.GONE);
                fragmentFourToMainActivity.fromFragmentFour();
            } else if (AppData.MyInfoBean != null && 3 == AppData.MyInfoBean.getUser().getVerifyStatus()) {
                verify_photo.setVisibility(View.VISIBLE);
                refreshLayout.setVisibility(View.GONE);
                tv_updata.setVisibility(View.VISIBLE);
                fragmentFourToMainActivity.fromFragmentFour();
                desc_one.setText("Only verified members can");
                desc_two.setText("access to this feature");
            }
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verify_photo;
    }

    @Override
    protected void initView(View view) {
        //列数为两列
        int spanCount = 2;
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(mLayoutManager);
        favoritesAdapter = new StaggerPhotoAdapter();
        recyclerview.setAdapter(favoritesAdapter);
        favoritesAdapter.bindToRecyclerView(recyclerview);
        favoritesAdapter.setEmptyView(R.layout.empty_big_page);
        favoritesAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    @Override
    protected void initListener() {
        sex = SharePreferenceUtils.getString(getContext(), Constance.SP_SEX, "1");
        if ("1".equals(sex)) {
            sex = "2";
        } else {
            sex = "1";
        }
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    if (favoritesAdapter != null && favoritesAdapter.getData() != null && favoritesAdapter.getData().size() > 0) {
                        favoritesAdapter.getData().clear();
                    }
                    mPresenter.loadFistPage(countryCode, sex, "1", minAge, maxAge);
                }
                refreshLayout.setEnableLoadMore(true);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    mPresenter.loadNextPage(countryCode, sex, "1", minAge, maxAge);
                }
            }
        });
        tv_updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyPhotoDialog.newInstance(new VerifyPhotoDialog.Callback() {
                    @Override
                    public void onDone(VerifyPhotoDialog dialog) {
                        PictureSelector
                                .create(FragmentFour.this, PictureSelector.SELECT_REQUEST_CODE)
                                .selectPicture(false);
                        dialog.dismissAllowingStateLoss();
                    }

                    @Override
                    public void onCancel(VerifyPhotoDialog dialog) {
                        dialog.dismissAllowingStateLoss();
                    }


                }).setTitle("")
                        .setContent("").show(getFragmentManager(), null);

            }
        });
        favoritesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SeachPeopleBean seachPeopleBean = (SeachPeopleBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), FavoriteDetailActivity.class);
                intent.putExtra("UserId", seachPeopleBean.getUserId());
                startActivity(intent);
            }
        });

        RxBus.getDefault().subscribe(this, "filter_condition", new RxBus.Callback<Myinfo>() {

            @Override
            public void onEvent(Myinfo chooseSex) {
                sex = chooseSex.getChooseSex();
                maxAge = chooseSex.getMaxAge();
                minAge = chooseSex.getMinAge();
                countryCode = chooseSex.getChooseCountryCode();
                mPresenter.loadFistPage(countryCode, sex, "1", minAge, maxAge);
            }
        });


        RxBus.getDefault().subscribe(this, "refresh_data", new RxBus.Callback<String>() {

            @Override
            public void onEvent(String s) {
                if (AppData.MyInfoBean != null && AppData.MyInfoBean.getUser() != null) {
                    if (-1 == AppData.MyInfoBean.getUser().getVerifyStatus()) {
                        verify_photo.setVisibility(View.VISIBLE);
                        refreshLayout.setVisibility(View.GONE);
                        tv_updata.setVisibility(View.VISIBLE);
                    } else if (AppData.MyInfoBean != null && 2 == AppData.MyInfoBean.getUser().getVerifyStatus()) {
                        refreshLayout.setVisibility(View.VISIBLE);
                        verify_photo.setVisibility(View.GONE);
                        refreshLayout.autoRefresh();
                    } else if (AppData.MyInfoBean != null && 1 == AppData.MyInfoBean.getUser().getVerifyStatus()) {
                        verify_photo.setVisibility(View.VISIBLE);
                        desc_one.setText("You verification request is");
                        desc_two.setText("processing.Please check back later");
                        tv_updata.setVisibility(View.GONE);
                    } else if (AppData.MyInfoBean != null && 3 == AppData.MyInfoBean.getUser().getVerifyStatus()) {
                        verify_photo.setVisibility(View.VISIBLE);
                        refreshLayout.setVisibility(View.GONE);
                        tv_updata.setVisibility(View.VISIBLE);
                        fragmentFourToMainActivity.fromFragmentFour();
                        desc_one.setText("Only verified members can");
                        desc_two.setText("access to this feature");
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        refreshLayout.autoRefresh();
    }


    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void VerifyPhotoSuccess(UpHeadBean data) {
        CustomToast.makeText(MyApplication.getContext(), "Upload successfully", R.drawable.ic_toast_warming).show();
        desc_one.setText("You verification request is");
        desc_two.setText("processing.Please check back later");
        tv_updata.setVisibility(View.GONE);
        AppData.MyInfoBean.getUser().setVerifyStatus(1);
    }

    @Override
    public void card_serch_success(List<SeachPeopleBean> data) {
        favoritesAdapter.getData().clear();
        favoritesAdapter.addData(data);
        loadDataFinish();
    }

    @Override
    public void card_serch_more_success(List<SeachPeopleBean> data) {
        if (data != null && data.size() > 0) {
            favoritesAdapter.addData(data);
        }
        loadDataFinish();
        if (data.size() == 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
        }
    }


    public void loadDataFinish() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    protected VerrifyPhotoPresenter initPresenter() {
        return new VerrifyPhotoPresenter();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                if (pictureBean.isCut()) {
                    mPresenter.verfyUpdataPhoto(pictureBean.getPath());
                } else {
                    mPresenter.verfyUpdataPhoto(pictureBean.getPath());
                }

            }
        }
    }

}
