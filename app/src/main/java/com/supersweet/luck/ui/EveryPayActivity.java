package com.supersweet.luck.ui;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.CoinSelectAdapter;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.google.BillingManager;
import com.supersweet.luck.google.listener.BaseBillingUpdateListener;
import com.supersweet.luck.google.listener.SimpleBillingUpdateListener;
import com.supersweet.luck.mvp.present.GoogleBuyCoinPresenter;
import com.supersweet.luck.mvp.view.GoogleBuyCoinView;
import com.supersweet.luck.utils.LogUtil;
import com.supersweet.luck.widget.AppBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Description:月支付界面
 * @Author: vi1zen
 * @CreateDate: 2022/3/8 13:36
 */
public class EveryPayActivity extends BaseMvpActivity implements GoogleBuyCoinView {

    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.banner)
    ViewPager viewPager;
    @BindView(R.id.point_group)
    LinearLayout pointGroup;
    @BindView(R.id.ll_one_layout)
    LinearLayout ll_one_layout;
    @BindView(R.id.ll_two_layout)
    LinearLayout ll_two_layout;
    @BindView(R.id.ll_three_layout)
    LinearLayout ll_three_layout;

    private int currentPosition = 1;
    private final int[] imageIds = {R.mipmap.dialog_google_pay_one, R.mipmap.dialog_google_pay_two, R.mipmap.dialog_google_pay_three, R.mipmap.dialog_google_pay_four, R.mipmap.dialog_google_pay_five, R.mipmap.dialog_google_pay_six};
    private List<ImageView> imageList;
    private BillingManager billingManager;
    /**
     * 上一个页面的位置
     */
    protected int lastPosition;
    private static final String TAG = EveryPayActivity.class.getSimpleName();

    @Override
    protected GoogleBuyCoinPresenter initPresenter() {
        return new GoogleBuyCoinPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_evrey_month_pay;
    }

    @Override
    protected void initView() {
        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            // 初始化图片资源
            ImageView image = new ImageView(this);
            image.setBackgroundResource(imageIds[i]);
            imageList.add(image);

            // 添加指示点
            ImageView point = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.rightMargin = 20;
            point.setLayoutParams(params);
            point.setImageDrawable(getResources().getDrawable(R.drawable.point_bg));
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            pointGroup.addView(point);
        }
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            /**
             * 页面切换后调用
             * position 新的页面位置
             */
            public void onPageSelected(int position) {

                position = position % imageList.size();

                // 改变指示点的状态
                // 把当前点enbale 为true
                pointGroup.getChildAt(position).setEnabled(true);
                // 把上一个点设为false
                pointGroup.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;

            }

            @Override
            /**
             * 页面正在滑动的时候，回调
             */
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            /**
             * 当页面状态发生变化的时候，回调
             */
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*
         * 自动循环： 1、定时器：Timer 2、开子线程 while true 循环 3、ColckManager 4、 用handler
         * 发送延时信息，实现循环
         */
        isRunning = true;
        // 设置图片的自动滑动
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void appBarLeftIvClicked() {
        super.appBarLeftIvClicked();
        isRunning = false;
        handler = null;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isRunning = false;
        handler.removeMessages(0);
        handler = null;
    }

    /**
     * 判断是否自动滚动
     */
    private boolean isRunning = false;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
           try {
               if (isRunning) {
                   handler.sendEmptyMessageDelayed(0, 3000);
                   // 让viewPager 滑动到下一页
                   if (viewPager !=null) {
                       viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                   }
               }
           }catch (Exception e){
               e.printStackTrace();
           }
        }
    };


    private class MyPagerAdapter extends PagerAdapter {

        @Override
        /**
         * 获得页面的总数
         */
        public int getCount() {
            return Integer.MAX_VALUE; // 使得图片可以循环
        }

        @Override
        /**
         * 获得相应位置上的view
         * container view的容器，其实就是viewpager自身
         * position  相应的位置
         */
        public Object instantiateItem(ViewGroup container, int position) {

            // System.out.println("instantiateItem ::" + position);

            // 给 container 添加一个view
            container.addView(imageList.get(position % imageList.size()));
            // 返回一个和该view相对的object
            return imageList.get(position % imageList.size());
        }

        @Override
        /**
         * 判断 view和object的对应关系
         */
        public boolean isViewFromObject(View view, Object object) {
            if (view == object) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        /**
         * 销毁对应位置上的object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
            // System.out.println("destroyItem ::" + position);

            container.removeView((View) object);
            object = null;
        }
    }




    // Google原生支付回调
    BaseBillingUpdateListener billingUpdateListener = new SimpleBillingUpdateListener() {

        private String skuId;

        @Override
        public void onBillingClientSetupFinished() {
            if (billingManager != null) {
                if (currentPosition == 0) {
                    skuId = "one_month_01";
                } else if (currentPosition == 1) {
                    skuId = "three_month_03";
                } else {
                    skuId = "six_month_06";
                }
                billingManager.launchBillingFlow(skuId, BillingClient.SkuType.INAPP);
            }
        }


        @Override
        public void onPurchasesUpdated(List<Purchase> purchases) {
            Toast.makeText(EveryPayActivity.this, "Purchase successful", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "购买成功：" + purchases.get(0).toString());
            LogUtil.i(TAG, "购买成功，开始消耗商品");
            billingManager.consumeAsync(purchases.get(0));
        }


        @Override
        public void onPurchasesCancel() {
            Toast.makeText(EveryPayActivity.this, "Cancel purchase", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "取消购买");
        }

        @Override
        public void onPurchasesFailure(int errorCode, String message) {
            Toast.makeText(EveryPayActivity.this, "Purchase failed [code：" + errorCode + ",message：" + message + "]", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "购买失败[code：" + errorCode + ",message：" + message + "]");
        }

        @Override
        public void onConsumeFinished(String token, BillingResult result, String purchaseTokenJson) {//消耗商品完成
            super.onConsumeFinished(token, result, purchaseTokenJson);
            if (result.getResponseCode() == 0) {
                Log.e(TAG, "已经消耗了商品");
                billingManager.buyCoin(purchaseTokenJson);
            }
        }
    };

    @Override
    protected void initListener() {
        tv_text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                billingManager = new BillingManager(EveryPayActivity.this, billingUpdateListener);
                billingManager.startServiceConnection(null);


            }
        });

     ;

        coinSelectAdapter.setItemSelectedCallBack(new CoinSelectAdapter.ItemSelectedCallBack() {
            @Override
            public void convert(BaseViewHolder holder, int position, String data) {
                ImageView view = holder.getView(R.id.iv_bestdeal);
                TextView bg = holder.getView(R.id.tv_hundry_coin);
                TextView tv_num_coin = holder.getView(R.id.tv_num_coin);
                TextView tv_hundry_coin = holder.getView(R.id.tv_hundry_coin);
                if (currentPosition == position) {
                    if (position == 1) {
                        view.setVisibility(View.VISIBLE);
                    } else {
                        view.setVisibility(View.GONE);
                    }
                    bg.setBackgroundResource(R.drawable.pay_select_checkable_shape);
                    tv_num_coin.setTextColor(getResources().getColor(R.color.white));
                    tv_hundry_coin.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setVisibility(View.GONE);
                    bg.setBackgroundResource(R.drawable.locaion_uncheckable_shape);
                    tv_num_coin.setTextColor(getResources().getColor(R.color.color_333333));
                    tv_hundry_coin.setTextColor(getResources().getColor(R.color.color_333333));
                }
            }

        });
    }
}
