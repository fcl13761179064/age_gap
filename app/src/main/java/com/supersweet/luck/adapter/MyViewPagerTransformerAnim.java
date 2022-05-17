package com.supersweet.luck.adapter;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.graphics.RenderNode;
import android.util.AttributeSet;
import android.view.View;
import androidx.viewpager.widget.ViewPager;


/*
 * 自定义viewpager实现动画切换效果：
 *
 * 1、需要拿到当前切换的两个view
 *
 * 2、一个动画的梯度值
 */
public class MyViewPagerTransformerAnim extends ViewPager {

    private View mLeft;
    private View mRight;

    private float mTrans;
    private float mScale;

    private static final float MIN_SCALE = 0.6f;
    private Map<Integer, View> mChildren = new HashMap<Integer, View>();
    private RenderNode ViewHelper;

    /*
     * 要有两个构造方法
     */
    public MyViewPagerTransformerAnim(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewPagerTransformerAnim(Context context) {
        super(context);
    }

    /*
     * 设置put的方法
     */
    public void setViewForPosition(View view, int position) {
        mChildren.put(position, view);
    }

    /*
     * remove的方法
     */
    public void removeViewFromPosition(Integer position) {
        mChildren.remove(position);
    }

    /**
     * 重写的方法
     */
    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {

        // Log.e("TAG", "position =" + position + ",offset = " + offset);
        mLeft = mChildren.get(position);
        mRight = mChildren.get(position + 1);

        animStack(mLeft, mRight, offset, offsetPixels);// 创建动画效果

        super.onPageScrolled(position, offset, offsetPixels);
    }

    private void animStack(View left, View right, float offset, int offsetPixels) {
        if (right != null) {

            // 从0-1页，offset:0`1
            mScale = (1 - MIN_SCALE) * offset + MIN_SCALE;
            mTrans = -getWidth() - getPageMargin() + offsetPixels;

            ViewHelper.setScaleX( mScale);
            ViewHelper.setScaleY(mScale);
            ViewHelper.setTranslationX( mTrans);
        }
        if (left != null) {
            left.bringToFront();
        }
    }
}