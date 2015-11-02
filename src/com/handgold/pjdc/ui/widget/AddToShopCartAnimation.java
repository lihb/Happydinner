package com.handgold.pjdc.ui.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by lihb on 15/10/14.
 */
public class AddToShopCartAnimation extends Animation {

    private float mFromXValue = 0.0f;
    private float mToXValue = 0.0f;

    private float mFromYValue = 0.0f;
    private float mToYValue = 0.0f;

    public AddToShopCartAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        mFromXValue = fromXDelta;
        mToXValue = toXDelta;
        mFromYValue = fromYDelta;
        mToYValue = toYDelta;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float tempXValue = mToXValue * 0.6f;
        int topHeight = 150;
        if (interpolatedTime < 0.6) {
            t.getMatrix().setTranslate(tempXValue * interpolatedTime, (float) (-Math.sin(interpolatedTime * Math.PI * 5 / 6) * topHeight));
        }else {
            float dx = mToXValue - tempXValue;
            float dy = -topHeight -  mToYValue;
            if (mToXValue != tempXValue) {
                dx = tempXValue + ((mToXValue - tempXValue) * interpolatedTime);
            }
            if (mFromYValue != mToYValue) {
                dy = -topHeight + ((mToYValue + topHeight) * interpolatedTime);
            }
            t.getMatrix().setTranslate(dx, dy);
        }

    }
}
