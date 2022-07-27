package com.supersweet.luck.ui

import android.app.Activity
import android.content.Context
import butterknife.BindView
import com.supersweet.luck.R
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import android.widget.LinearLayout
import com.supersweet.luck.google.BillingManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import android.view.ViewGroup
import android.widget.ImageView
import com.supersweet.luck.google.listener.BaseBillingUpdateListener
import com.supersweet.luck.google.listener.SimpleBillingUpdateListener
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import android.widget.Toast
import com.supersweet.luck.ui.EveryMonthPayDialog
import com.android.billingclient.api.BillingResult
import com.supersweet.luck.base.BaseDialog
import com.supersweet.luck.utils.LogUtil
import java.lang.Exception
import java.util.ArrayList

/**
 * @Description:月支付界面
 * @Author: vi1zen
 * @CreateDate: 2022/3/8 13:36
 */
class EveryMonthPayDialog(mActivity: Activity?) : BaseDialog(mActivity) {
    @BindView(R.id.tv_text_pay)
    var tv_text_pay: TextView? = null
    @BindView(R.id.banner)
    var viewPager: ViewPager? = null
    @BindView(R.id.point_group)
    var pointGroup: LinearLayout? = null
    @BindView(R.id.ll_one_layout)
    var ll_one_layout: LinearLayout? = null
    @BindView(R.id.ll_two_layout)
    var ll_two_layout: LinearLayout? = null
    @BindView(R.id.ll_three_layout)
    var ll_three_layout: LinearLayout? = null

    private val currentPosition = 1
    private val imageIds = intArrayOf(R.mipmap.dialog_google_pay_one,
        R.mipmap.dialog_google_pay_two,
        R.mipmap.dialog_google_pay_three,
        R.mipmap.dialog_google_pay_four,
        R.mipmap.dialog_google_pay_five,
        R.mipmap.dialog_google_pay_six)
    private var imageList: MutableList<ImageView>? = null
    private var billingManager: BillingManager? = null

    /**
     * 上一个页面的位置
     */
    protected var lastPosition = 0
    override fun getLayoutId(): Int {
        return R.layout.dialog_evrey_month_pay
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
    }

    override fun initViews(view: View) {
        imageList = mutableListOf()
        for (i in imageIds.indices) {
            // 初始化图片资源
            val image = ImageView(context)
            image.setBackgroundResource(imageIds[i])
            imageList!!.add(image)

            // 添加指示点
            val point = ImageView(context)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            params.rightMargin = 20
            point.layoutParams = params
            point.setImageDrawable(context.getResources().getDrawable(R.drawable.point_bg))
            if (i == 0) {
                point.isEnabled = true
            } else {
                point.isEnabled = false
            }
            pointGroup!!.addView(point)
        }
        viewPager!!.adapter = MyPagerAdapter()
        viewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            /**
             * 页面切换后调用
             * position 新的页面位置
             */
            override fun onPageSelected(position: Int) {
                var position = position
                position = position % imageList!!.size

                // 改变指示点的状态
                // 把当前点enbale 为true
                pointGroup!!.getChildAt(position).isEnabled = true
                // 把上一个点设为false
                pointGroup!!.getChildAt(lastPosition).isEnabled = false
                lastPosition = position
            }

            /**
             * 页面正在滑动的时候，回调
             */
            override fun onPageScrolled(
                position: Int, positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            /**
             * 当页面状态发生变化的时候，回调
             */
            override fun onPageScrollStateChanged(state: Int) {}
        })

        /*
         * 自动循环： 1、定时器：Timer 2、开子线程 while true 循环 3、ColckManager 4、 用handler
         * 发送延时信息，实现循环
         */isRunning = true
        // 设置图片的自动滑动
        handler!!.sendEmptyMessageDelayed(0, 3000)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        isRunning = false
        handler!!.removeMessages(0)
        handler = null
    }

    /**
     * 判断是否自动滚动
     */
    private var isRunning = false
    private var handler: Handler? = object : Handler() {
        override fun handleMessage(msg: Message) {
            try {
                if (isRunning) {
                    this.sendEmptyMessageDelayed(0, 3000)
                    // 让viewPager 滑动到下一页
                    if (viewPager != null) {
                        viewPager!!.currentItem = viewPager!!.currentItem + 1
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private inner class MyPagerAdapter : PagerAdapter() {
        /**
         * 获得页面的总数
         */
        override fun getCount(): Int {
            return Int.MAX_VALUE // 使得图片可以循环
        }

        /**
         * 获得相应位置上的view
         * container view的容器，其实就是viewpager自身
         * position  相应的位置
         */
        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            // System.out.println("instantiateItem ::" + position);

            // 给 container 添加一个view
            container.addView(imageList!![position % imageList!!.size])
            // 返回一个和该view相对的object
            return imageList!![position % imageList!!.size]
        }

        /**
         * 判断 view和object的对应关系
         */
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return if (view === `object`) {
                true
            } else {
                false
            }
        }

        /**
         * 销毁对应位置上的object
         */
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            // System.out.println("destroyItem ::" + position);
            var `object`: Any? = `object`
            container.removeView(`object` as View?)
            `object` = null
        }
    }

    // Google原生支付回调
    var billingUpdateListener: BaseBillingUpdateListener = object : SimpleBillingUpdateListener() {
        private var skuId: String? = null
        override fun onBillingClientSetupFinished() {
            if (billingManager != null) {
                skuId = if (currentPosition == 0) {
                    "one_month_01"
                } else if (currentPosition == 1) {
                    "three_month_03"
                } else {
                    "six_month_06"
                }
                billingManager!!.launchBillingFlow(skuId, BillingClient.SkuType.INAPP)
            }
        }

        override fun onPurchasesUpdated(purchases: List<Purchase>) {
            Toast.makeText(context, "Purchase successful", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "购买成功：" + purchases[0].toString())
            LogUtil.i(TAG, "购买成功，开始消耗商品")
            billingManager!!.consumeAsync(purchases[0])
        }

        override fun onPurchasesCancel() {
            Toast.makeText(context, "Cancel purchase", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "取消购买")
        }

        override fun onPurchasesFailure(errorCode: Int, message: String) {
            Toast.makeText(context,
                "Purchase failed [code：$errorCode,message：$message]",
                Toast.LENGTH_SHORT).show()
            Log.e(TAG, "购买失败[code：$errorCode,message：$message]")
        }

        override fun onConsumeFinished(
            token: String,
            result: BillingResult,
            purchaseTokenJson: String
        ) { //消耗商品完成
            super.onConsumeFinished(token, result, purchaseTokenJson)
            if (result.responseCode == 0) {
                Log.e(TAG, "已经消耗了商品")
                billingManager!!.buyCoin(purchaseTokenJson)
            }
        }
    }

    protected fun initListener() {
        tv_text_pay!!.setOnClickListener {
            billingManager = BillingManager(context, billingUpdateListener)
            billingManager!!.startServiceConnection(null)
        }

        /*coinSelectAdapter.setItemSelectedCallBack(new CoinSelectAdapter.ItemSelectedCallBack() {
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

        });*/
    }

    companion object {
        private val TAG = EveryMonthPayDialog::class.java.simpleName
    }
}