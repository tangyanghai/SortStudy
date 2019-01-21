package com.tyh.java.sortstudy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.tyh.java.lib_base.util.timer.TimeObservable;

import java.lang.ref.WeakReference;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/18
 * 描述:
 */
public class SortViewGroup extends ViewGroup{

    private int gapX = 15;
    private int width;
    private int height;
    private SparseArray<ViewHolder> childs;
    private OnViewStateChangedListener onViewStateChangedListener;


    public SortViewGroup(Context context) {
        this(context, null);
    }

    public SortViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = getPaddingLeft();
        int childMaxHeight = 0;
        MarginLayoutParams childParams;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            childParams = (MarginLayoutParams) childAt.getLayoutParams();
            measureChildWithMargins(childAt, widthMeasureSpec, widthUsed, heightMeasureSpec, getPaddingTop());
            int childWidth = childAt.getMeasuredWidth();
            int childHeight = childAt.getMeasuredHeight();
            widthUsed += (childWidth + childParams.leftMargin + childParams.rightMargin + gapX);
            childMaxHeight = Math.max(childMaxHeight, childHeight + childParams.topMargin + childParams.bottomMargin);
        }
        //将最后多加的gapX去掉,加上paddingRight
        widthUsed += (getPaddingRight() - gapX);
        width = measureWidth(widthMeasureSpec, widthUsed);
        height = measureHeight(heightMeasureSpec, childMaxHeight + getPaddingBottom() + getPaddingTop());
        setMeasuredDimension(width, height);
    }

    private int measureHeight(int heightMeasureSpec, int maxChildHeight) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = maxChildHeight;
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec, int maxChildWidth) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = maxChildWidth;
        }
        return result;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        TimeObservable.getInstance().stop();
        childs.clear();
        childs = null;
        onViewStateChangedListener = null;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = getPaddingLeft();
        int right, top, bottom, childH;
        MarginLayoutParams childParams;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child == null) {
                break;
            }
            childParams = (MarginLayoutParams) child.getLayoutParams();
            left += childParams.leftMargin + i == 0 ? 0 : gapX;
            right = left + child.getMeasuredWidth() + childParams.rightMargin;
            childH = child.getMeasuredHeight() + childParams.topMargin + childParams.bottomMargin;
            top = (int) ((height - childH) / 2f);
            bottom = top + childH;
            getChildAt(i).layout(left, top, right, bottom);
            if (childs == null) {
                childs = new SparseArray<>();
            }
            childs.put(i, new ViewHolder(this,child, i, left));
            //更新左边距离
            left = right;
        }
    }


    public void changeChild(int i, int j) {
        try {
            ViewHolder holderI = childs.get(i);
            ViewHolder holderJ = childs.get(j);
            int tempIndex = holderJ.curIndex;
            int tempLeft = holderJ.curLeft;
            holderJ.resetPosition(holderI.curIndex, holderI.curLeft);
            holderI.resetPosition(tempIndex, tempLeft);
            childs.put(i, holderJ);
            childs.put(j, holderI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public View getChildAtIndex(int realIndex) {
        return childs.get(realIndex).getView();
    }

    // 继承自margin，支持子视图android:layout_margin属性
    public static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }
    }

    private static class ViewHolder{

        int timeTotal = 300;
        int timeDelay = 16;
        float moveDis = 10;
        boolean stop;
        int curIndex;
        int curLeft;
        WeakReference<View> weakView;
        WeakReference<SortViewGroup> weakParent;

        public ViewHolder(SortViewGroup sortViewGroup, View view, int curIndex, int curLeft) {
            weakParent = new WeakReference<>(sortViewGroup);
            weakView = new WeakReference<>(view);
            this.curIndex = curIndex;
            this.curLeft = curLeft;
        }

        /**
         * 重置当前view的位置
         *
         * @param index 位置
         * @param left  view的左边
         */
        void resetPosition(int index, int left) {
            if (isViewAttached()) {
                int dis = left - curLeft;
                if (dis == 0) {
                    return;
                }
                moveDis = timeDelay * Math.abs(dis) / (timeTotal * 1f);

                curLeft = left;
                curIndex = index;

                move();
            }
        }

        private View getView() {
            if (weakView == null) {
                return null;
            }
            return weakView.get();
        }

        private boolean isViewAttached() {
            return getView() != null;
        }

        /**
         * @return
         */
        void move() {
            View view = getView();

            if (view == null) {
                return;
            }


            stop = false;
            int l = view.getLeft();
            int t;
            if (l != curLeft) {
                t = (int) (view.getLeft() + (l > curLeft ? -1 : 1) * moveDis);
                if (l > curLeft && t <= curLeft) {
                    t = curLeft;
                    stop = true;
                } else if (l <= curLeft && t >= curLeft) {
                    t = curLeft;
                    stop = true;
                }

                notifyStateChanged(State.moving);
                view.layout(t, view.getTop(), t + view.getMeasuredWidth(), view.getBottom());
                if (!stop) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            move();
                        }
                    }, timeDelay);
                } else {
                    notifyStateChanged(State.finished);
                    Log.e("移动完成", "move: " + curIndex);
                }
            }
        }

        void notifyStateChanged(State state){
            if (weakParent!=null&&weakParent.get()!=null&&isViewAttached()&&getView()!=null) {
                SortViewGroup p = weakParent.get();
                p.changeState(state,getView());
            }
        }

    }

    private void changeState(State state, View view) {
        if (onViewStateChangedListener == null) {
            return;
        }
        switch (state) {
            case moving:
                onViewStateChangedListener.onMoving(view);
                break;
            case finished:
                onViewStateChangedListener.onFinishedMove(view);
                break;
        }
    }

    public enum State{
        moving,
        finished
    }

    /**
     * 要排序的View状态变化
     */
    public interface OnViewStateChangedListener{
        /**
         * 移动中
         */
        void onMoving(View v);

        /**
         * 移动结束
         */
        void onFinishedMove(View v);
    }

    public OnViewStateChangedListener getOnViewStateChangedListener() {
        return onViewStateChangedListener;
    }

    public void setOnViewStateChangedListener(OnViewStateChangedListener onViewStateChangedListener) {
        this.onViewStateChangedListener = onViewStateChangedListener;
    }
}
