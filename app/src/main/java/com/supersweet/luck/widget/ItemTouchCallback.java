package com.supersweet.luck.widget;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchCallback extends ItemTouchHelper.SimpleCallback {
    private static final String TAG = "RenRen";
    private static final int MAX_ROTATION = 15;
    OnSwipeListener mSwipeListener;
    boolean isSwipeAnim = false;

    public ItemTouchCallback() {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    public void setSwipeListener(OnSwipeListener swipeListener) {
        mSwipeListener = swipeListener;
    }

    //水平方向是否可以被回收掉的阈值
    public float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //2016 12 26 考虑 探探垂直上下方向滑动，不删除卡片，这里参照源码写死0.5f
        return recyclerView.getWidth() * /*getSwipeThreshold(viewHolder)*/ 0.5f;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        viewHolder.itemView.setRotation(0);//恢复最后一次的旋转状态
        if (mSwipeListener != null) {
            mSwipeListener.onSwipeTo(viewHolder, 0);
        }
        notifyListener(viewHolder.getAdapterPosition(), direction);
    }

    private void notifyListener(int position, int direction) {
        Log.w(TAG, "onSwiped: " + position + " " + direction);
        if (mSwipeListener != null) {
            mSwipeListener.onSwiped(position, direction);
        }
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        //滑动的比例达到多少之后, 视为滑动
        return 0.3f;
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        Log.i(TAG, "onChildDraw: dx:" + dX + " dy:" + dY);
        //人人影视的效果
        //if (isCurrentlyActive) {
        //先根据滑动的dxdy 算出现在动画的比例系数fraction
        float swipeValue = (float) Math.sqrt(dX * dX + dY * dY);
        final float threshold = getThreshold(recyclerView, viewHolder);
        float fraction = swipeValue / threshold;
        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1;
        } else if (fraction < -1) {
            fraction = -1;
        }
        //对每个ChildView进行缩放 位移
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
            int level = childCount - i - 1;
            if (level > 0) {
           /*     child.setScaleX(1 - SCALE_GAP * level + fraction * SCALE_GAP);

                if (level < MAX_SHOW_COUNT - 1) {
                    child.setScaleY(1 - SCALE_GAP * level + fraction * SCALE_GAP);
                    child.setTranslationY(TRANS_Y_GAP * level - fraction * TRANS_Y_GAP);
                } else {
                    //child.setTranslationY((float) (mTranslationYGap * (level - 1) - fraction * mTranslationYGap));
                }*/
            } else {
                //最上层
                //rotate
                if (dX < -50) {
                    child.setRotation(-fraction * MAX_ROTATION);
                } else if (dX > 50) {
                    child.setRotation(fraction * MAX_ROTATION);
                } else {
                    child.setRotation(0);
                }

                if (mSwipeListener != null) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    final int adapterPosition = params.getViewAdapterPosition();
                    mSwipeListener.onSwipeTo(recyclerView.findViewHolderForAdapterPosition(adapterPosition), dX);
                }
            }
        }
    }

    public void toLeft(RecyclerView recyclerView) {
        if (check(recyclerView,false)) {
            animTo(recyclerView, false);
        }
    }

    public void toRight(RecyclerView recyclerView) {
        if (check(recyclerView,true)) {
            animTo(recyclerView, true);
        }
    }

    private void animTo(final RecyclerView recyclerView, boolean right) {
        if (recyclerView == null) {
            return;
        }

        final int position = recyclerView.getAdapter().getItemCount() - 1;
        final RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder == null) {
            return;
        }

        final View view = viewHolder.itemView;

        /*
         *  创建一个AnimationSet，它能够同时执行多个动画效果
         *  构造方法的入参如果是“true”，则代表使用默认的interpolator，如果是“false”则代表使用自定义interpolator
         */
        AnimationSet mAnimationSet = new AnimationSet(true);
        //透明度动画，从完全透明到不透明，我们的动画都是float型的，所以，在写数字的时候，要加f
        //AlphaAnimation alphAnima = new AlphaAnimation(1f, 0f);
        /*
         *  创建一个旋转动画对象
         *  入参列表含义如下：
         *  1.fromDegrees：从哪个角度开始旋转
         *  2.toDegrees：旋转到哪个角度结束
         *  3.pivotXType：旋转所围绕的圆心的x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
         *  4.pivotXValue：旋转所围绕的圆心的x轴坐标,0.5f表明是以自身这个控件的一半长度为x轴
         *  5.pivotYType：y轴坐标的类型
         *  6.pivotYValue：y轴坐标
         */
        RotateAnimation rotateAnim = new RotateAnimation(0f, right ? 30f : -30f, Animation.RELATIVE_TO_SELF, right ? 0.5f:0f ,
                Animation.RELATIVE_TO_SELF,  right ? 0.5f:0f);

        /*
         *  创建一个缩放效果的动画
         *  入参列表含义如下：
         *  fromX：x轴的初始值
         *  toX：x轴缩放后的值
         *  fromY：y轴的初始值
         *  toY：y轴缩放后的值
         *  pivotXType：x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
         *  pivotXValue：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
         *  pivotYType：y轴坐标的类型
         *  pivotYValue：轴的值，0.5f表明是以自身这个控件的一半长度为y轴
         */
       // ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF,  right ? 0.5f:-0.5f, Animation.RELATIVE_TO_SELF,  right ? 0.5f:-0.5f);
        /*
         *  创建一个移动动画效果
         *  入参的含义如下：
         *  fromXType：移动前的x轴坐标的类型
         *  fromXValue：移动前的x轴的坐标
         *  toXType：移动后的x轴的坐标的类型
         *  toXValue：移动后的x轴的坐标
         *  fromYType：移动前的y轴的坐标的类型
         *  fromYValue：移动前的y轴的坐标
         *  toYType：移动后的y轴的坐标的类型
         *  toYValue：移动后的y轴的坐标
         */
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, right ? 1.3f : -1.3f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, right ? 0f : 0.2f);
     /*   TranslateAnimation translateAnimation =new  TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.ABSOLUTE,360f,
                    Animation.RELATIVE_TO_SELF,0f,Animation.ABSOLUTE,360f);*/

       // mAnimationSet.addAnimation(alphAnima);
        mAnimationSet.addAnimation(rotateAnim);
     //   mAnimationSet.addAnimation(scaleAnimation);
        mAnimationSet.addAnimation(translateAnimation);
        mAnimationSet.setDuration(300);//动画持续时间时间
        mAnimationSet.setInterpolator(new DecelerateInterpolator()); //添加插值器，下面会有说明
        mAnimationSet.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isSwipeAnim = false;
                Log.d("chat_chat", "11111111111111111");
                recyclerView.removeView(view);
                notifyListener(position, right
                        ?
                        ItemTouchHelper.RIGHT : ItemTouchHelper.LEFT);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(mAnimationSet);

       /* TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, right ? 1f : -1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1.3f);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isSwipeAnim = false;
                recyclerView.removeView(view);
                notifyListener(position,
                        x > view.getMeasuredWidth() / 2
                                ?
                                ItemTouchHelper.RIGHT : ItemTouchHelper.LEFT);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(translateAnimation);*/
    }

    private boolean check(RecyclerView recyclerView, boolean left_right) {
        if (recyclerView == null || recyclerView.getAdapter() == null) {
            return false;
        }
        if (recyclerView.getAdapter().getItemCount() == 1) {
            if (mSwipeListener != null) {
                mSwipeListener.noCardData();
                notifyListener(0, left_right
                        ?
                        ItemTouchHelper.RIGHT : ItemTouchHelper.LEFT);
            }
        } else if (recyclerView.getAdapter().getItemCount() == 0) {
            return false;
        }
        isSwipeAnim = true;
        return true;
    }

    public interface OnSwipeListener {

        /**
         * @param direction {@link ItemTouchHelper#LEFT} / {@link ItemTouchHelper#RIGHT}
         *                  {@link ItemTouchHelper#UP} or {@link ItemTouchHelper#DOWN}).
         */
        void onSwiped(int adapterPosition, int direction);

        /**
         * 最上层View滑动时回调.
         *
         * @param viewHolder 最上层的ViewHolder
         * @param offset     距离原始位置的偏移量
         */
        void onSwipeTo(RecyclerView.ViewHolder viewHolder, float offset);


        //表示没有数据了

        void noCardData();
    }

    public static class SimpleSwipeCallback implements OnSwipeListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onSwiped(int adapterPosition, int direction) {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onSwipeTo(RecyclerView.ViewHolder viewHolder, float offset) {

        }

        @Override
        public void noCardData() {

        }
    }
}
