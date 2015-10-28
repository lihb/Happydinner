package com.handgold.pjdc.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/10/28.
 */
public class AnimateView extends TextView {

    public static final int RADIUS = 20;
    public int duration = 400;

    private MyPoint currentPoint;

    private Paint mPaint;

    MyPoint mStartPoint;
    MyPoint mEndPoint;


    public AnimateView(Context context, MyPoint startPoint, MyPoint endPoint) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xff2ec45e);
        mStartPoint = startPoint;
        mEndPoint = endPoint;
    }

    public AnimateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xff2ec45e);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new MyPoint(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        mPaint.setColor(0xff2ec45e);
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void startAnimation() {
        ValueAnimator anim = ValueAnimator.ofObject(new AddToCartIntEvaluator(), mStartPoint, mEndPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (MyPoint) animation.getAnimatedValue();
                Log.i("wwww", "x = " + currentPoint.getX()+", y = " + currentPoint.getY());
                invalidate();
            }

        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                    ViewGroup group = (ViewGroup) getParent();
                    if (group != null) {
                        group.removeView(AnimateView.this);
                    }
            }

        } );
        anim.setDuration(duration);
        anim.start();
    }

}
