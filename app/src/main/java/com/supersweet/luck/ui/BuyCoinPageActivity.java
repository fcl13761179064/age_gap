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

public class BuyCoinPageActivity extends BaseMvpActivity implements GoogleBuyCoinView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.banner)
    ViewPager viewPager;
    @BindView(R.id.point_group)
    LinearLayout pointGroup;
    @BindView(R.id.appBar)
    AppBar appBar;

    private CoinSelectAdapter coinSelectAdapter;
    private List<String> bodyType;
    private int currentPosition = 1;
    private final int[] imageIds = {R.mipmap.dialog_google_pay_one, R.mipmap.dialog_google_pay_two, R.mipmap.dialog_google_pay_three, R.mipmap.dialog_google_pay_four, R.mipmap.dialog_google_pay_five, R.mipmap.dialog_google_pay_six};
    private List<ImageView> imageList;
    private BillingManager billingManager;
    /**
     * ????????????????????????
     */
    protected int lastPosition;
    private static final String TAG = BuyCoinPageActivity.class.getSimpleName();

    @Override
    protected GoogleBuyCoinPresenter initPresenter() {
        return new GoogleBuyCoinPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_coin_page;
    }

    @Override
    protected void initView() {
        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            // ?????????????????????
            ImageView image = new ImageView(this);
            image.setBackgroundResource(imageIds[i]);
            imageList.add(image);

            // ???????????????
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
             * ?????????????????????
             * position ??????????????????
             */
            public void onPageSelected(int position) {

                position = position % imageList.size();

                // ????????????????????????
                // ????????????enbale ???true
                pointGroup.getChildAt(position).setEnabled(true);
                // ?????????????????????false
                pointGroup.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;

            }

            @Override
            /**
             * ????????????????????????????????????
             */
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            /**
             * ?????????????????????????????????????????????
             */
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*
         * ??????????????? 1???????????????Timer 2??????????????? while true ?????? 3???ColckManager 4??? ???handler
         * ?????????????????????????????????
         */
        isRunning = true;
        // ???????????????????????????
        handler.sendEmptyMessageDelayed(0, 3000);
        initDat();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);
        coinSelectAdapter = new CoinSelectAdapter();
        coinSelectAdapter.addData(bodyType);
        recyclerview.setAdapter(coinSelectAdapter);
        coinSelectAdapter.bindToRecyclerView(recyclerview);
        coinSelectAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
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
     * ????????????????????????
     */
    private boolean isRunning = false;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
           try {
               if (isRunning) {
                   handler.sendEmptyMessageDelayed(0, 3000);
                   // ???viewPager ??????????????????
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
         * ?????????????????????
         */
        public int getCount() {
            return Integer.MAX_VALUE; // ????????????????????????
        }

        @Override
        /**
         * ????????????????????????view
         * container view????????????????????????viewpager??????
         * position  ???????????????
         */
        public Object instantiateItem(ViewGroup container, int position) {

            // System.out.println("instantiateItem ::" + position);

            // ??? container ????????????view
            container.addView(imageList.get(position % imageList.size()));
            // ??????????????????view?????????object
            return imageList.get(position % imageList.size());
        }

        @Override
        /**
         * ?????? view???object???????????????
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
         * ????????????????????????object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
            // System.out.println("destroyItem ::" + position);

            container.removeView((View) object);
            object = null;
        }
    }


    private void initDat() {
        bodyType = new ArrayList<>();
        bodyType.add("100,$29.99");
        bodyType.add("500,$69.99");
        bodyType.add("1000,$119.99");
    }


    // Google??????????????????
    BaseBillingUpdateListener billingUpdateListener = new SimpleBillingUpdateListener() {

        private String skuId;

        @Override
        public void onBillingClientSetupFinished() {
            if (billingManager != null) {
                if (currentPosition == 0) {
                    skuId = "agegap_523_131400";
                } else if (currentPosition == 1) {
                    skuId = "agegap_521_131400";
                } else {
                    skuId = "agegap_522_131400";
                }
                billingManager.launchBillingFlow(skuId, BillingClient.SkuType.INAPP);
            }
        }

        //  skuDetails ????????????
//                        {
//                            "skuDetailsToken":"AEuhp4K_N_DyvTtZXkguU4XHEfLN2y54NJwxl9B5XxyVk1cvJ7Vkh-cZHpKEApKj3-il",
//                                "productId":"f0908u_",
//                                "type":"inapp",
//                                "price":"US$1.34",
//                                "price_amount_micros":1339320,
//                                "price_currency_code":"USD",
//                                "title":"VIP (Funchat)",
//                                "description":"Become VIP chatting with girls you like"
//                        }
        @Override
        public void onPurchasesUpdated(List<Purchase> purchases) {
            Toast.makeText(BuyCoinPageActivity.this, "Purchase successful", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "???????????????" + purchases.get(0).toString());
            LogUtil.i(TAG, "?????????????????????????????????");
            billingManager.consumeAsync(purchases.get(0));
        }


        @Override
        public void onPurchasesCancel() {
            Toast.makeText(BuyCoinPageActivity.this, "Cancel purchase", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "????????????");
        }

        @Override
        public void onPurchasesFailure(int errorCode, String message) {
            Toast.makeText(BuyCoinPageActivity.this, "Purchase failed [code???" + errorCode + ",message???" + message + "]", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "????????????[code???" + errorCode + ",message???" + message + "]");
        }

        @Override
        public void onConsumeFinished(String token, BillingResult result, String purchaseTokenJson) {//??????????????????
            super.onConsumeFinished(token, result, purchaseTokenJson);
            if (result.getResponseCode() == 0) {
                Log.e(TAG, "?????????????????????");
                billingManager.buyCoin(purchaseTokenJson);
            }
        }
    };

    @Override
    protected void initListener() {
        appBar.rightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyCoinPageActivity.this, GooglePayRecordActivity.class);
                intent.putExtras(getIntent());
                startActivity(intent);
            }
        });
        tv_text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                billingManager = new BillingManager(BuyCoinPageActivity.this, billingUpdateListener);
                billingManager.startServiceConnection(null);


            }
        });

        coinSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            private String botyType;

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                botyType = (String) adapter.getData().get(position);
                //????????????
                currentPosition = position;
                //???????????????item??????????????????
                adapter.notifyDataSetChanged();
            }
        });

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
