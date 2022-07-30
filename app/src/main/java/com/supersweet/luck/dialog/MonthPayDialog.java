package com.supersweet.luck.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.supersweet.luck.R;
import com.supersweet.luck.base.BaseDialog;
import com.supersweet.luck.google.BillingManager;
import com.supersweet.luck.google.listener.BaseBillingUpdateListener;
import com.supersweet.luck.google.listener.SimpleBillingUpdateListener;
import com.supersweet.luck.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;

public class MonthPayDialog extends BaseDialog{

    private int currentPosition = 2;
    private final int[] imageIds = {R.mipmap.dialog_google_pay_one, R.mipmap.dialog_google_pay_two, R.mipmap.dialog_google_pay_three, R.mipmap.dialog_google_pay_four, R.mipmap.dialog_google_pay_five, R.mipmap.dialog_google_pay_six};
    private List<ImageView> imageList;
    private BillingManager billingManager;
    private  ViewPager viewPager;
    private  LinearLayout pointGroup;
    private  TextView tv_text_pay;
    private  LinearLayout ll_one_layout;
    private  LinearLayout ll_two_layout;
    private  LinearLayout ll_two_layout_unsert;
    private  LinearLayout ll_three_layout;
    private OnSureClick onSureClick;
    private ImageView close;
    /**
     * 上一个页面的位置
     */
    protected int lastPosition;
    private static final String TAG = MonthPayDialog.class.getSimpleName();

    public MonthPayDialog(Context context) {
        super(context);
    }

    public void setOnSureClick(OnSureClick onSureClick) {
        this.onSureClick = onSureClick;
    }


    public  void setGravity(int gravity){
        WindowManager m = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.75); // 高度设置为屏幕的0.5
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为整个屏幕宽度
        p.gravity=gravity;
        getWindow().setAttributes(p);//

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_evrey_month_pay;
    }

    @Override
    protected void initViews(View view) {
         viewPager = view.findViewById(R.id.banner);
         pointGroup = view.findViewById(R.id.point_group);
        tv_text_pay = view.findViewById(R.id.tv_text_pay);
        ll_one_layout = view.findViewById(R.id.ll_one_layout);
        ll_two_layout = view.findViewById(R.id.ll_two_layout_select);
        ll_two_layout_unsert = view.findViewById(R.id.ll_two_layout_unsert);
        ll_three_layout = view.findViewById(R.id.ll_three_layout);

        ll_three_layout = view.findViewById(R.id.ll_three_layout);
        ll_three_layout = view.findViewById(R.id.ll_three_layout);
        ll_three_layout = view.findViewById(R.id.ll_three_layout);
        close = view.findViewById(R.id.close);

        ll_one_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMonthPay();
                currentPosition=1;
                ll_one_layout.setBackgroundResource(R.mipmap.icon_one_subscribe_select);
            }
        });

        ll_two_layout_unsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMonthPay();
                currentPosition=2;
                ll_two_layout.setVisibility(View.VISIBLE);
                ll_two_layout_unsert.setVisibility(View.GONE);
            }
        });
        ll_three_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMonthPay();
                currentPosition=3;
                ll_three_layout.setBackgroundResource(R.mipmap.icon_one_subscribe_select);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            // 初始化图片资源
            ImageView image = new ImageView(context);
            image.setBackgroundResource(imageIds[i]);
            imageList.add(image);

            // 添加指示点
            ImageView point = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.rightMargin = 20;
            point.setLayoutParams(params);
            point.setImageDrawable(getContext().getDrawable(R.drawable.point_bg));
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
        tv_text_pay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                billingManager = new BillingManager(context, billingUpdateListener);
                billingManager.startServiceConnection(null);


            }
        });

    }


    public  void  selectMonthPay(){
        ll_one_layout.setBackgroundResource(R.mipmap.icon_one_subscribe_unselect);
        ll_two_layout_unsert.setVisibility(View.VISIBLE);
        ll_two_layout.setVisibility(View.GONE);
        ll_two_layout_unsert.setBackgroundResource(R.mipmap.icon_one_subscribe_unselect);
        ll_three_layout.setBackgroundResource(R.mipmap.icon_one_subscribe_unselect);
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
                if (currentPosition == 1) {
                    skuId = "one_month_01";
                } else if (currentPosition == 2) {
                    skuId = "three_month_03";
                } else {
                    skuId = "six_month_06";
                }
                billingManager.launchBillingFlow(skuId, BillingClient.SkuType.SUBS);
            }
        }


        @Override
        public void onPurchasesUpdated(List<Purchase> purchases) {
            Toast.makeText(context, "Purchase successful", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "购买成功：" + purchases.get(0).toString());
            LogUtil.i(TAG, "购买成功，开始消耗商品");
            billingManager.consumeAsync(purchases.get(0));
        }


        @Override
        public void onPurchasesCancel() {
            Toast.makeText(context, "Cancel purchase", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "取消购买");
        }

        @Override
        public void onPurchasesFailure(int errorCode, String message) {
            Toast.makeText(context, "Purchase failed [code：" + errorCode + ",message：" + message + "]", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "购买失败[code：" + errorCode + ",message：" + message + "]");
        }

        @Override
        public void onConsumeFinished(String token, BillingResult result, String purchaseTokenJson) {//消耗商品完成
            super.onConsumeFinished(token, result, purchaseTokenJson);
            if (result.getResponseCode() == 0) {
                Log.e(TAG, "已经消耗了商品");
                billingManager.MonthPay(purchaseTokenJson);
            }
        }
    };

    public interface OnSureClick {
        void click(Dialog dialog);
    }
}
