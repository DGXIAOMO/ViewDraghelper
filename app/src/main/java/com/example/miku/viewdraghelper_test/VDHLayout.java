package com.example.miku.viewdraghelper_test;

import android.content.Context;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by miku on 2017/4/14.
 */
public class VDHLayout extends LinearLayout{
    private ViewDragHelper helper;
    private View DirView, BackView,EdgeView;
    private int top,left;
    private Context context;
    public VDHLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        helper = ViewDragHelper.create(this, 1.0f, callback);
    }
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
            //表示对子view进行移动处理，也可以让子view单独移动。
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }
           /* @Override
            public int getViewHorizontalDragRange(View child)
            {
                return getMeasuredWidth()-child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child)
            {
                return getMeasuredHeight()-child.getMeasuredHeight();
            }
            */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if(releasedChild==BackView)
                {
                    helper.settleCapturedViewAt(left,top);
                    invalidate();
                }
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound =getWidth() - child.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
                //return super.clampViewPositionHorizontal(child, left, dx);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getBottom()-child.getHeight()-topBound;
                final int newTop = Math.min(Math.max(topBound,top),bottomBound);
                return newTop;
                //return super.clampViewPositionVertical(child, top, dy);
            }
        };
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        left = BackView.getLeft();
        top = BackView.getTop();
    }

    @Override
    public void computeScroll() {
       if (helper.continueSettling(true))
        {
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        DirView = getChildAt(0);
        BackView = getChildAt(1);
        EdgeView = getChildAt(2);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }
}
