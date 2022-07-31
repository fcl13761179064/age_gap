package com.stone.card.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Point;
import android.graphics.Rect;
import android.renderscript.Int4;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 卡片滑动面板，主要逻辑实现类
 *
 * @author xmuSistone
 */
@SuppressLint({"HandlerLeak", "NewApi", "ClickableViewAccessibility"})
public class CardSlidePanel extends ViewGroup {
    private List<CardItemView> viewList = new ArrayList<>(); // 存放的是每一层的view，从顶到底
    private List<View> releasedViewList = new ArrayList<>(); // 手指松开后存放的view列表
    private List<Int4> deleteViewLocationList = new ArrayList<>(); // 删除掉的View信息列表，用与撤销

    /* 拖拽工具类 */
    private final ViewDragHelper mDragHelper; // 这个跟原生的ViewDragHelper差不多，我仅仅只是修改了Interpolator
    private int initCenterViewX = 0, initCenterViewY = 0; // 最初时，中间View的x位置,y位置
    private int allWidth = 0; // 面板的宽度
    private int allHeight = 0; // 面板的高度
    private int childWith = 0; // 每一个子View对应的宽度

    private static final float SCALE_STEP = 0.008f; // view叠加缩放的步长
    private static final int MAX_SLIDE_DISTANCE_LINKAGE = 500; // 水平距离+垂直距离

    private int itemMarginTop = 10; // 卡片距离顶部的偏移量
    private int bottomMarginTop = 0; // 底部按钮与卡片的margin值
    private int yOffsetStep = 0; // view叠加垂直偏移量的步长
    private int mTouchSlop = 5; // 判定为滑动的阈值，单位是像素
    //保存删除的上限个数
    private final int MAX_CACHE_VIEW = 20;
    //动画移动的时间
    private int SCROLL_DURATION = 800;


    private static final int VIEW_COUNT = 4;

    private static final int X_VEL_THRESHOLD = 800;
    //TODO 触发阈值
    private static final int X_DISTANCE_THRESHOLD = 300;
    //最大的移动距离
    private static final int MAX_MOVING_DISTANCE = 600;

    public static final int VANISH_TYPE_LEFT = 0;
    public static final int VANISH_TYPE_RIGHT = 1;

    private CardSwitchListener cardSwitchListener; // 回调接口
    private int isShowing = 0; // 当前正在显示的小项
    private boolean btnLock = false;
    private GestureDetectorCompat moveDetector;
    private Point downPoint = new Point();
    private CardAdapter adapter;
    private Rect draggableArea;
    private WeakReference<Object> savedFirstItemData;
    private Rect leftRect;
    private Rect rightRect;
    private Rect retractRect;

    private LayoutParams initLayoutParams;
    private int nowFinalX;
    private int nowFinalY;
    private int nowFinalType;
    private int viewWidthAll;

    public CardSlidePanel(Context context) {
        this(context, null);
    }

    public CardSlidePanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardSlidePanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.card);

        itemMarginTop = (int) a.getDimension(R.styleable.card_itemMarginTop, itemMarginTop);
        bottomMarginTop = (int) a.getDimension(R.styleable.card_bottomMarginTop, bottomMarginTop);
        yOffsetStep = (int) a.getDimension(R.styleable.card_yOffsetStep, yOffsetStep);
        // 滑动相关类
        mDragHelper = ViewDragHelper
                .create(this, 10f, new DragHelperCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);
        a.recycle();

        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        moveDetector = new GestureDetectorCompat(context,
                new MoveDetector());
        moveDetector.setIsLongpressEnabled(false);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getChildCount() != VIEW_COUNT) {
                    doBindAdapter();
                }
            }
        });

    }

    private void doBindAdapter() {
        if (adapter == null || allWidth <= 0 || allHeight <= 0) {
            return;
        }

        // 1. addView添加到ViewGroup中
        for (int i = 0; i < VIEW_COUNT; i++) {
            CardItemView itemView = new CardItemView(getContext());
            itemView.bindLayoutResId(adapter.getLayoutId());
            itemView.setParentView(this);
            addView(itemView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            if (i == 0) {
                itemView.setAlpha(0);
            }
        }

        // 2. viewList初始化
        viewList.clear();
        for (int i = 0; i < VIEW_COUNT; i++) {
            viewList.add((CardItemView) getChildAt(VIEW_COUNT - 1 - i));
        }

        // 3. 填充数据
        int count = adapter.getCount();
        for (int i = 0; i < VIEW_COUNT; i++) {
            if (i < count) {
                CardItemView view = viewList.get(i);
                adapter.bindView(view, i);
                if (i == 0) {
                    savedFirstItemData = new WeakReference<>(adapter.getItem(i));
                }
            } else {
                viewList.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initRectLeftRight(CardItemView view) {
        int[] ints = new int[2];

        View touch_left = view.findViewById(R.id.touch_left);
        touch_left.getLocationOnScreen(ints);
        leftRect = new Rect(ints[0], ints[1], ints[0] + touch_left.getWidth(), ints[1] + touch_left.getHeight());

        View touch_right = view.findViewById(R.id.touch_right);
        touch_right.getLocationOnScreen(ints);
        rightRect = new Rect(ints[0], ints[1], ints[0] + touch_right.getWidth(), ints[1] + touch_right.getHeight());

        View touch_retract = view.findViewById(R.id.touch_retract);
        touch_retract.getLocationOnScreen(ints);
        retractRect = new Rect(ints[0], ints[1], ints[0] + touch_right.getWidth(), ints[1] + touch_right.getHeight());

    }

    class MoveDetector extends SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx,
                                float dy) {
            // 拖动了，touch不往下传递
            return Math.abs(dy) + Math.abs(dx) > mTouchSlop;
        }
    }


    /**
     * 这是viewdraghelper拖拽效果的主要逻辑
     */
    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            onViewPosChanged((CardItemView) changedView);
            //移动时进行回调
            if (cardSwitchListener!=null){
                float percentage = ((float) (changedView.getLeft() - initCenterViewX)) / MAX_MOVING_DISTANCE;
                View oldCard = getChildCount() > 2 ? getChildAt(getChildCount()-2):null;
                cardSwitchListener.onCardMove(percentage,oldCard,getChildAt(getChildCount()-1));
            }
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            // 如果数据List为空，或者子View不可见，则不予处理

            if (adapter == null || adapter.getCount() == 0
                    || child.getVisibility() != View.VISIBLE || child.getScaleX() <= 1.0f - SCALE_STEP) {
                // 一般来讲，如果拖动的是第三层、或者第四层的View，则直接禁止
                // 此处用getScale的用法来巧妙回避
                return false;
            }

            if (btnLock) {
                return false;
            }

            // 1. 只有顶部的View才允许滑动
            int childIndex = viewList.indexOf(child);
            if (childIndex > 0) {
                return false;
            }

            // 2. 获取可滑动区域
            ((CardItemView) child).onStartDragging();
            if (draggableArea == null) {
                draggableArea = adapter.obtainDraggableArea(child);
            }


            // 3. 判断是否可滑动
            boolean shouldCapture = true;
            if (null != draggableArea) {
                shouldCapture = draggableArea.contains(downPoint.x, downPoint.y);
            }

            // 4. 如果确定要滑动，就让touch事件交给自己消费
            if (shouldCapture) {
                getParent().requestDisallowInterceptTouchEvent(shouldCapture);
            }
            return shouldCapture;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            // 这个用来控制拖拽过程中松手后，自动滑行的速度
            return 256;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            animToSide((CardItemView) releasedChild, (int) xvel, (int) yvel);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }


    }


    public void onViewPosChanged(CardItemView changedView) {
        // 调用offsetLeftAndRight导致viewPosition改变，会调到此处，所以此处对index做保护处理
        int index = viewList.indexOf(changedView);
        if (index + 2 > viewList.size()) {
            return;
        }

        processLinkageView(changedView);
    }

    /**
     * 对View重新排序
     * @param mode: 1:删除后刷新 2：撤回刷新
     */
    private void orderViewStack(int mode) {
        if (mode==1){
            orderViewStackDel();
        }else if (mode==2){
            orderViewStackRetract();
        }
    }

    private void orderViewStackRetract() {
        if (deleteViewLocationList.isEmpty()){
            return;
        }
        //获取位置信息
        Int4 locationInfo = deleteViewLocationList.get(deleteViewLocationList.size() - 1);
        CardItemView cardItemView = viewList.get(viewList.size() - 1);
        //1. 装载之前数据
        adapter.bindView(cardItemView, locationInfo.w);
        //2. 进行动画
        cardItemView.setAlpha(1);
        cardItemView.setVisibility(VISIBLE);
        cardItemView.setLayoutParams(initLayoutParams);
        cardItemView.setScreenX(locationInfo.x);
        cardItemView.setScreenY(locationInfo.y);
        removeViewInLayout(cardItemView);
        addViewInLayout(cardItemView, -1, initLayoutParams, true);
        if (mDragHelper.smoothSlideViewTo(cardItemView, initCenterViewX, initCenterViewY, SCROLL_DURATION)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }

        //3. viewList中的卡片view的位次调整
        viewList.add(0,cardItemView);
        viewList.remove(viewList.size()-1);

        //4.调整其他卡片动画

        processLinkageView(cardItemView);

        //5. 更新showIndex、接口回调
        isShowing=locationInfo.w;
        deleteViewLocationList.remove(deleteViewLocationList.size()-1);
        Log.e("------",deleteViewLocationList.size()+"--删除了撤回的");

        if (null != cardSwitchListener) {
            cardSwitchListener.onCardRetract(1,locationInfo.y);
            cardSwitchListener.onShow(isShowing);
        }
    }

    private void orderViewStackDel() {
        if (releasedViewList.size() == 0) {
            return;
        }

        CardItemView changedView = (CardItemView) releasedViewList.get(0);
        if (changedView.getLeft() == initCenterViewX) {
            releasedViewList.remove(0);
            return;
        }

        // 1. 消失的卡片View位置重置，由于大多手机会重新调用onLayout函数，所以此处大可以不做处理，不信你注释掉看看
        changedView.offsetLeftAndRight(initCenterViewX
                - changedView.getLeft());
        changedView.offsetTopAndBottom(initCenterViewY
                - changedView.getTop() + yOffsetStep * 2);
        if (initLayoutParams==null){
            initLayoutParams=changedView.getLayoutParams();
        }
        float scale = 1.0f - SCALE_STEP * 2;
        changedView.setScaleX(scale);
        changedView.setScaleY(scale);
        changedView.setAlpha(0);

        // 2. 卡片View在ViewGroup中的顺次调整
        LayoutParams lp = changedView.getLayoutParams();
        removeViewInLayout(changedView);
        addViewInLayout(changedView, 0, lp, true);

        // 3. changedView填充新数据
        int newIndex = isShowing + 4;
        if (newIndex < adapter.getCount()) {
            adapter.bindView(changedView, newIndex);
        } else {
            changedView.setVisibility(View.INVISIBLE);
        }

        // 4. viewList中的卡片view的位次调整
        viewList.remove(changedView);
        viewList.add(changedView);
        releasedViewList.remove(0);

        // 5. 添加到删除列表,更新showIndex、接口回调
        addDeleteView(nowFinalX, nowFinalY,nowFinalType,isShowing);

        if (isShowing + 1 < adapter.getCount()) {
            isShowing++;
        }

        if (null != cardSwitchListener) {
            cardSwitchListener.onShow(isShowing);
        }


    }

    /**
     * 顶层卡片View位置改变，底层的位置需要调整
     *
     * @param changedView 顶层的卡片view
     */
    private void processLinkageView(View changedView) {
        int changeViewLeft = changedView.getLeft();
        int changeViewTop = changedView.getTop();
        int distance = Math.abs(changeViewTop - initCenterViewY)
                + Math.abs(changeViewLeft - initCenterViewX);
        float rate = distance / (float) MAX_SLIDE_DISTANCE_LINKAGE;

        float rate1 = rate;
        float rate2 = rate - 0.1f;

        if (rate > 1) {
            rate1 = 1;
        }

        if (rate2 < 0) {
            rate2 = 0;
        } else if (rate2 > 1) {
            rate2 = 1;
        }

        ajustLinkageViewItem(changedView, rate1, 1);
        ajustLinkageViewItem(changedView, rate2, 2);

        CardItemView bottomCardView = viewList.get(viewList.size() - 1);
        bottomCardView.setAlpha(rate2);
    }

    // 由index对应view变成index-1对应的view
    private void ajustLinkageViewItem(View changedView, float rate, int index) {
        int changeIndex = viewList.indexOf(changedView);
        int initPosY = yOffsetStep * index;
        float initScale = 1 - SCALE_STEP * index;

        int nextPosY = yOffsetStep * (index - 1);
        float nextScale = 1 - SCALE_STEP * (index - 1);

        int offset = (int) (initPosY + (nextPosY - initPosY) * rate);
        float scale = initScale + (nextScale - initScale) * rate;

        View ajustView = viewList.get(changeIndex + index);
        ajustView.offsetTopAndBottom(offset - ajustView.getTop()
                + initCenterViewY);
        ajustView.setScaleX(scale);
        ajustView.setScaleY(scale);
    }

    /**
     * 松手时处理滑动到边缘的动画
     */
    private void animToSide(CardItemView changedView, int xvel, int yvel) {
        int finalX = initCenterViewX;
        int finalY = initCenterViewY;
        int flyType = -1;

        // 1. 下面这一坨计算finalX和finalY，要读懂代码需要建立一个比较清晰的数学模型才能理解，不信拉倒
        int dx = changedView.getLeft() - initCenterViewX;
        int dy = changedView.getTop() - initCenterViewY;

        // yvel < xvel * xyRate则允许以速度计算偏移
        final float xyRate = 3f;
        if (xvel > X_VEL_THRESHOLD && Math.abs(yvel) < xvel * xyRate) {
            // x正方向的速度足够大，向右滑动消失
            finalX = allWidth;
            finalY = yvel * (childWith + changedView.getLeft()) / xvel + changedView.getTop();
            flyType = VANISH_TYPE_RIGHT;
        } else if (xvel < -X_VEL_THRESHOLD && Math.abs(yvel) < -xvel * xyRate) {
            // x负方向的速度足够大，向左滑动消失
            finalX = -childWith;
            finalY = yvel * (childWith + changedView.getLeft()) / (-xvel) + changedView.getTop();
            flyType = VANISH_TYPE_LEFT;
        } else if (dx > X_DISTANCE_THRESHOLD && Math.abs(dy) < dx * xyRate) {
            // x正方向的位移足够大，向右滑动消失
            finalX = allWidth;
            finalY = dy * (childWith + initCenterViewX) / dx + initCenterViewY;
            flyType = VANISH_TYPE_RIGHT;
        } else if (dx < -X_DISTANCE_THRESHOLD && Math.abs(dy) < -dx * xyRate) {
            // x负方向的位移足够大，向左滑动消失
            finalX = -childWith;
            finalY = dy * (childWith + initCenterViewX) / (-dx) + initCenterViewY;
            flyType = VANISH_TYPE_LEFT;
        }

        // 如果斜率太高，就折中处理
        if (finalY > allHeight) {
            finalY = allHeight;
        } else if (finalY < -allHeight / 2) {
            finalY = -allHeight / 2;
        }

        // 如果没有飞向两侧，而是回到了中间，需要谨慎处理
        if (finalX == initCenterViewX) {
            changedView.animTo(initCenterViewX, initCenterViewY);
            if (cardSwitchListener!=null){
                View oldCard = getChildCount() > 2 ? getChildAt(getChildCount()-2):null;
                cardSwitchListener.onCardMove(-1,oldCard,getChildAt(getChildCount()-1));
            }
        } else {
            // 2. 向两边消失的动画
            releasedViewList.add(changedView);
            if (mDragHelper.smoothSlideViewTo(changedView, finalX, finalY,SCROLL_DURATION)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }

            // 3. 消失动画即将进行，listener回调
            if (flyType >= 0 && cardSwitchListener != null) {
                cardSwitchListener.onCardVanish(isShowing, flyType);
            }
            nowFinalX =finalX;
            nowFinalY =finalY;
            nowFinalType =flyType;
        }
    }

    /**
     * 添加删除掉的View
     * @param finalX: 删除掉的动画移除X位置
     * @param finalY: 删除掉的动画移除X位置
     * @param type : 之前删除的方向位置
     * @param point : 删除的显示数据位置
     */
    private void addDeleteView(int finalX, int finalY,int type,int point) {
        if (deleteViewLocationList!=null){
            if (deleteViewLocationList.size()>= MAX_CACHE_VIEW){
                deleteViewLocationList.remove(0);
                Log.e("------",deleteViewLocationList.size()+"--移除了多余的");
            }
            deleteViewLocationList.add(new Int4(finalX,finalY,type,point));
            Log.e("------",deleteViewLocationList.size()+"--添加了");
        }
    }

    /**
     * 点击按钮消失动画
     */
    private void vanishOnBtnClick(int type) {
//        0b001：右删除
//*       0b010：左删除
        int point=type==VANISH_TYPE_LEFT?1:0;
        if (((cardSwitchListener.onFunctionEnabled()>>point)&1)!=1){
            return;
        }

        View animateView = viewList.get(0);
        if (animateView.getVisibility() != View.VISIBLE || releasedViewList.contains(animateView)) {
            return;
        }

        int finalX = 0;
        int extraVanishDistance = 100; // 为加快vanish的速度，额外添加消失的距离
        if (type == VANISH_TYPE_LEFT) {
            finalX = -childWith - extraVanishDistance;
        } else if (type == VANISH_TYPE_RIGHT) {
            finalX = allWidth + extraVanishDistance;
        }
        int finalY = initCenterViewY + allHeight / 2;
        if (finalX != 0) {
            releasedViewList.add(animateView);
            if (mDragHelper.smoothSlideViewTo(animateView, finalX, finalY, SCROLL_DURATION)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        if (type >= 0 && cardSwitchListener != null) {
            cardSwitchListener.onCardVanish(isShowing, type);
        }
        nowFinalX =finalX;
        nowFinalY =finalY;
        nowFinalType =type;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            // 动画结束
            if (mDragHelper.getViewDragState() == ViewDragHelper.STATE_IDLE) {
                orderViewStack(1);
                btnLock = false;
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        // 按下时保存坐标信息
        if (action == MotionEvent.ACTION_DOWN) {
            this.downPoint.x = (int) ev.getX();
            this.downPoint.y = (int) ev.getY();
        }
        return super.dispatchTouchEvent(ev);
    }

    /* touch事件的拦截与处理都交给mDraghelper来处理 */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getRawX();
        float y = ev.getRawY();
        int action = ev.getActionMasked();
        //对功能按钮的处理
        if (processClickFunBtn(x, y, action)) return true;

        boolean shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev);
        boolean moveFlag = moveDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_DOWN) {
            // ACTION_DOWN的时候就对view重新排序
            if (mDragHelper.getViewDragState() == ViewDragHelper.STATE_SETTLING) {
                mDragHelper.abort();
            }
            orderViewStack(1);

            // 保存初次按下时arrowFlagView的Y坐标
            // action_down时就让mDragHelper开始工作，否则有时候导致异常
            mDragHelper.processTouchEvent(ev);
        }


        return shouldIntercept && moveFlag;
    }

    /**
     * 对功能按钮的处理
     */
    private boolean processClickFunBtn(float x, float y, int action) {
        if (leftRect == null || rightRect == null || retractRect==null) {
            //TODO 修改的地方
            if (!viewList.isEmpty()) {
                initRectLeftRight(viewList.get(0));
            }
        } else if (action == MotionEvent.ACTION_UP) {
            if (x > leftRect.left && x < leftRect.right
                    && y > leftRect.top && y < leftRect.bottom) {
                vanishOnBtnClick(VANISH_TYPE_LEFT);
                return true;
            } else if (x > rightRect.left && x < rightRect.right
                    && y > rightRect.top && y < rightRect.bottom) {
                vanishOnBtnClick(VANISH_TYPE_RIGHT);
                return true;
            } else if (x > retractRect.left && x < retractRect.right
                    && y > retractRect.top && y < retractRect.bottom){
                clickRetractBtn();
                return true;
            }
        }
        return false;
    }

    /**
     * 点击了撤回按钮的按钮
     */
    private void clickRetractBtn() {
        //0b100：撤回
        if (((cardSwitchListener.onFunctionEnabled()>>2)&1)!=1){
            return;
        }
        if (deleteViewLocationList==null){
            return;
        }

        if (deleteViewLocationList.isEmpty()&&cardSwitchListener!=null){
            cardSwitchListener.onCardRetract(2,2);
            return;
        }

        orderViewStack(2);


    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        try {
            // 统一交给mDragHelper处理，由DragHelperCallback实现拖动效果
            // 该行代码可能会抛异常，正式发布时请将这行代码加上try catch
            mDragHelper.processTouchEvent(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(
                resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));

        allWidth = getMeasuredWidth();
        allHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View viewItem = viewList.get(i);
            // 1. 先layout出来
            int childHeight = viewItem.getMeasuredHeight();
            int viewLeft = (getWidth() - viewItem.getMeasuredWidth()) / 2;
            viewItem.layout(viewLeft, itemMarginTop, viewLeft + viewItem.getMeasuredWidth(), itemMarginTop + childHeight);

            // 2. 调整位置
            int offset = yOffsetStep * i;
            float scale = 1 - SCALE_STEP * i;
            if (i > 2) {
                // 备用的view
                offset = yOffsetStep * 2;
                scale = 1 - SCALE_STEP * 2;
            }
            viewItem.offsetTopAndBottom(offset);

            // 3. 调整缩放、重心等
            viewItem.setPivotY(viewItem.getMeasuredHeight());
            viewItem.setPivotX(viewItem.getMeasuredWidth() / 2);
            viewItem.setScaleX(scale);
            viewItem.setScaleY(scale);
        }

        if (childCount > 0) {
            // 初始化一些中间参数
            initCenterViewX = viewList.get(0).getLeft();
            initCenterViewY = viewList.get(0).getTop();
            viewWidthAll=getMeasuredWidth();
            childWith = viewList.get(0).getMeasuredWidth();
        }
    }

    public void setAdapter(final CardAdapter adapter) {
        this.adapter = adapter;
        doBindAdapter();

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                orderViewStack(1);

                boolean reset = false;
                if (adapter.getCount() > 0) {
                    Object firstObj = adapter.getItem(0);
                    if (null == savedFirstItemData) {
                        // 此前就没有数据，需要保存第一条数据
                        savedFirstItemData = new WeakReference<>(firstObj);
                        isShowing = 0;
                    } else {
                        Object savedObj = savedFirstItemData.get();
                        if (firstObj != savedObj) {
                            // 如果第一条数据不等的话，需要重置
                            isShowing = 0;
                            reset = true;
                            savedFirstItemData = new WeakReference<>(firstObj);
                        }
                    }
                }

                int delay = 0;
                for (int i = 0; i < VIEW_COUNT; i++) {
                    CardItemView itemView = viewList.get(i);
                    if (isShowing + i < adapter.getCount()) {
                        if (itemView.getVisibility() == View.VISIBLE) {
                            if (!reset) {
                                continue;
                            }
                        } else if (i == 0) {
                            if (isShowing > 0) {
                                isShowing++;
                            }
                            cardSwitchListener.onShow(isShowing);
                        }
                        if (i == VIEW_COUNT - 1) {
                            itemView.setAlpha(0);
                            itemView.setVisibility(View.VISIBLE);
                        } else {
                            itemView.setVisibilityWithAnimation(View.VISIBLE, delay++);
                        }
                        adapter.bindView(itemView, isShowing + i);
                    } else {
                        itemView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    public CardAdapter getAdapter() {
        return adapter;
    }

    /**
     * 设置卡片操作回调
     */
    public void setCardSwitchListener(CardSwitchListener cardSwitchListener) {
        this.cardSwitchListener = cardSwitchListener;
    }

    /**
     * 撤回Item
     */
    public void retractItem() {
        clickRetractBtn();
    }

    /**
     * 删除item
     * @param type: {@link #VANISH_TYPE_LEFT}或{@link #VANISH_TYPE_RIGHT}
     */
    public void delItem(int type) {
        if (viewList!=null&&!viewList.isEmpty()){
            vanishOnBtnClick(type);
        }
    }

    /**
     * 卡片回调接口
     */
    public interface CardSwitchListener {
        /**
         * 新卡片显示回调
         *
         * @param index 最顶层显示的卡片的index
         */
        public void onShow(int index);

        /**
         * 卡片飞向两侧回调
         *
         * @param index 飞向两侧的卡片数据index
         * @param type  飞向哪一侧{@link #VANISH_TYPE_LEFT}或{@link #VANISH_TYPE_RIGHT}
         */
        public void onCardVanish(int index, int type);

        /**
         * 卡片撤回的回调
         *
         * @param status :1：撤回成功 2：已经没有可以撤回的数据
         * @param type :之前飞向哪一侧{@link #VANISH_TYPE_LEFT}或{@link #VANISH_TYPE_RIGHT}
         */
        public void onCardRetract(int status, int type);
        /**
         * 卡片功能按钮的监听
         *
         * @return :
         *      0: off  1: on
         *          撤 左 右
         *      0b  0  0  0
         *
         *      列：只能撤回--->0b100
         */
        public int onFunctionEnabled();
        /**
         * 卡片移动距离的回调 最边上的距离为 {@link MAX_MOVING_DISTANCE}
         *
         * @param percentage:移动距离的百分比：
         *                  -1:为松开手指 回到中心
         *                  中心--->删除  0---->1
         *                  +-表示方向
         *                  +：右   -：左
         * @param oldCard：移动的卡片下面一层的卡片
         * @param moveCard：移动的卡片
         */
        public void onCardMove(float percentage,View oldCard,View moveCard);
    }
}